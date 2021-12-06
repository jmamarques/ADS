package com.ads.services;

import com.ads.utils.constants.GeneralConst;
import com.ads.utils.exceptions.InvalidFileException;
import com.ads.utils.exceptions.InvalidFormatException;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * class thath will be essentially for get the headers of a file
 * In the methods to get the headers, the param will be of type MultipartFile
 * (A representation of an uploaded file received in a multipart request.
 * The file contents are either stored in memory or temporarily on disk.)
 *
 **/
@Service
@Log4j2
public class FileService {

    /**
     * list of headers of a csv/excel file
     * @param file - file(excel or csv)
     * @return List of headers
     */
    public List<String> getHeadersFromFile(MultipartFile file) {
        try {
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            if (StringUtils.equalsIgnoreCase(extension, GeneralConst.EXCEL_EXTENSION)) {
                return processExcel(file);
            } else if (StringUtils.equalsIgnoreCase(extension, GeneralConst.EXCEL_EXTENSION_XLSX)) {
                return processExcelXlsx(file);
            } else if (StringUtils.equalsIgnoreCase(extension, GeneralConst.CSV_EXTENSION)) {
                return processCsv(file);
            } else {
                throw new InvalidFormatException("Invalid Extension. (xlsx, xls or csv)");
            }
        } catch (Throwable e) {
            throw new InvalidFileException(e);
        }
    }

    /**
     * list of headers csv file
     * @param file - Csv file
     * @return List of headers
     */
    private List<String> processCsv(MultipartFile file) {
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            String[] values = null;
            if ((values = csvReader.readNext()) != null) {
                if (values.length == 1) {
                    return Arrays.asList(StringUtils.split(values[0], ";"));
                } else {
                    return Arrays.asList(values);
                }
            } else {
                throw new InvalidFileException("Do not have headers in this file");
            }

        } catch (IOException | CsvValidationException e) {
            throw new InvalidFileException("Problems to Read the CSV file", e);
        }
    }

    /**
     * list of headers of excel file
     *
     * @param file - Excel file
     * @return List of headers
     */
    private List<String> processExcel(MultipartFile file) {
        try {
            DataFormatter formatter = new DataFormatter(java.util.Locale.US);
            Workbook workbook = new HSSFWorkbook(file.getInputStream());
            return processGenericExcel(formatter, workbook);
        } catch (IOException e) {
            throw new InvalidFileException("Problems to Read the Excel file", e);
        }
    }

    /**
     * list of headers of xlsx file
     *
     * @param file - Excel file XLSX
     * @return List of headers
     */
    private List<String> processExcelXlsx(MultipartFile file) {
        try {
            DataFormatter formatter = new DataFormatter(java.util.Locale.US);
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            return processGenericExcel(formatter, workbook);
        } catch (IOException e) {
            throw new InvalidFileException("Problems to Read the Excel file", e);
        }
    }

    /**
     * process the format of data and returns the headers
     * @param formatter
     * @param workbook
     * @return
     */
    @NonNull
    private List<String> processGenericExcel(DataFormatter formatter, Workbook workbook) {
        Sheet sheet = workbook.getSheetAt(0);
        FormulaEvaluator evaluator = sheet.getWorkbook().getCreationHelper().createFormulaEvaluator();
        int headerRowNum = sheet.getFirstRowNum();

        // collecting the column headers as a Map of header names to column indexes
        ArrayList<String> headers = new ArrayList<>();
        Row row = sheet.getRow(headerRowNum);
        for (Cell cell : row) {
            String value = formatter.formatCellValue(cell, evaluator);
            headers.add(value);
        }
        return headers;
    }
}
