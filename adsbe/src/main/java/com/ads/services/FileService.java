package com.ads.services;

import com.ads.models.dto.ClassDTO;
import com.ads.models.dto.RequestDTO;
import com.ads.models.dto.TimetableDTO;
import com.ads.models.internal.ClassRoom;
import com.ads.models.internal.Timetable;
import com.ads.utils.Tuple;
import com.ads.utils.constants.GeneralConst;
import com.ads.utils.exceptions.InvalidFileException;
import com.ads.utils.exceptions.InvalidFormException;
import com.ads.utils.exceptions.InvalidFormatException;
import com.ads.utils.mapper.ClassRoomMapper;
import com.ads.utils.mapper.TimetableMapper;
import com.ads.utils.parser.csv.CsvPOJOUtils;
import com.ads.utils.parser.excel.PoiPOJOUtils;
import com.ads.utils.validators.TimetableValidator;
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
import java.util.StringJoiner;
import java.util.function.Function;

/**
 * class that will be essentially for get the headers of a file
 * In the methods to get the headers, the param will be of type MultipartFile
 * (A representation of an uploaded file received in a multipart request.
 * The file contents are either stored in memory or temporarily on disk.)
 **/
@Service
@Log4j2
public class FileService {
    private final AlgorithmService algorithmService;

    public FileService(AlgorithmService algorithmService) {
        this.algorithmService = algorithmService;
    }

    /**
     * Generic parser from file to a list of DTO's
     *
     * @param file        - file
     * @param mapping     - mapping configuration on file
     * @param targetClass - target class to create
     * @param createClass - function to create DTO object
     * @param headers     - headers on server - static configuration
     * @param <T>         Generic DTO
     * @return List of DTO's
     */
    public static <T> List<T> getDtoFromFile(@NonNull MultipartFile file, @NonNull List<String> mapping, Class<T> targetClass, Function<Tuple<String[], List<String>>, T> createClass, String[] headers, String splitter) {
        try {
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            if (StringUtils.equalsIgnoreCase(extension, GeneralConst.EXCEL_EXTENSION)
                    || StringUtils.equalsIgnoreCase(extension, GeneralConst.EXCEL_EXTENSION_XLSX)) {
                return processAbstractExcel(file, mapping, headers, targetClass, splitter);
            } else if (StringUtils.equalsIgnoreCase(extension, GeneralConst.CSV_EXTENSION)) {
                return processAbstractCsv(file, mapping, createClass, splitter);
            } else {
                throw new InvalidFormatException("Invalid Extension. (xlsx, xls or csv)");
            }
        } catch (Throwable e) {
            throw new InvalidFileException(e);
        }
    }

    /**
     * Process CSV File
     *
     * @param file        - CSV file
     * @param mapping     - mapping
     * @param createClass - function to create DTO object
     * @param <T>         - Generic DTO
     * @return List of DTO's
     * @throws IOException - Unhandled Exception
     */
    private static <T> List<T> processAbstractCsv(MultipartFile file, List<String> mapping, Function<Tuple<String[], List<String>>, T> createClass, String splitter) throws IOException {
        return CsvPOJOUtils.readDtoFromCSV(file.getInputStream(), createClass, mapping, splitter);
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

    /**
     * Process Excel File
     *
     * @param file        - Excel file
     * @param mapping     - mapping
     * @param headers     - headers on server - static configuration
     * @param targetClass - target class to create
     * @param <T>         - Generic DTO
     * @return List of DTO's
     * @throws Exception - Unhandled Exception
     */
    private static <T> List<T> processAbstractExcel(MultipartFile file, List<String> mapping, String[] headers, Class<T> targetClass, String splitter) throws Exception {
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        return PoiPOJOUtils.sheetToPOJO(sheet, targetClass, mapping, headers, splitter);
    }

    /**
     * list of headers of a csv/excel file
     *
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
     *
     * @param file - Csv file
     * @return List of headers
     */
    private List<String> processCsv(MultipartFile file) {
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            String[] values;
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
     * Validate, transform and compute algorithms on input values
     *
     * @param requestDTO    - mapping configurations
     * @param classFile     - class file
     * @param timetableFile - timetable file
     * @return Timetable list based on algorithms
     */
    public List<List<Timetable>> processForm(RequestDTO requestDTO, MultipartFile classFile, MultipartFile timetableFile) {
        String error = validateRequest(requestDTO, classFile, timetableFile);
        if (!StringUtils.isEmpty(error)) {
            throw new InvalidFileException(error);
        }
        try {
            // load from file
            log.info("Creating classDTO");
            List<ClassDTO> classRoomDTOList = loadClassFile(classFile, requestDTO.getMappingClass());
            log.info("Creating TimetableDTO");
            List<TimetableDTO> timetableDTOList = loadTimetableFile(timetableFile, requestDTO.getMappingTimetable());
            // map to internal object and validate the data
            log.info("Mapping classDTO to classroom");
            List<ClassRoom> classRoomList = TimetableValidator.validationClassRooms(ClassRoomMapper.toClassRoom(classRoomDTOList));
            log.info("Mapping TimetableDTO to Timetable");
            List<Timetable> timetablesList = TimetableValidator.validationTimetables(TimetableMapper.toTimetable(timetableDTOList));
            // parser qualities
            log.info("Prepare Qualities");
            String[] qualities = StringUtils.split(requestDTO.getQualities(), ";");
            boolean fast = requestDTO.getFast();
            // get results
            log.info("Prepare Algorithms");
            return algorithmService.process(classRoomList, timetablesList, qualities, requestDTO);
        } catch (Throwable e) {
            throw new InvalidFormException(e);
        }
    }

    /**
     * Load class file with specific information to it
     *
     * @param classFile    - class file
     * @param mappingClass - configurations
     * @return List of class from classfile
     */
    private List<ClassDTO> loadClassFile(MultipartFile classFile, String mappingClass) {
        return getDtoFromFile(
                classFile,
                Arrays.stream(StringUtils.split(mappingClass, ";")).toList(),
                ClassDTO.class,
                ClassRoomMapper::createObj,
                GeneralConst.CLASS_FIELD_MAPPING,
                ";");
    }

    /**
     * Load timetable file with specific information to it
     *
     * @param timetableFile - timetable file
     * @param mapping       - configurations
     * @return List of timetable from classfile
     */
    private List<TimetableDTO> loadTimetableFile(MultipartFile timetableFile, String mapping) {
        return getDtoFromFile(timetableFile,
                Arrays.stream(StringUtils.split(mapping, ";")).toList(),
                TimetableDTO.class,
                TimetableMapper::createObj,
                GeneralConst.TIMETABLE_FIELD_MAPPING,
                ";");
    }

    /**
     * Request Validation
     *
     * @param requestDTO    - Extra parameters
     * @param classFile     - classroom file
     * @param timetableFile - timetable file
     * @return error message - can be empty if there are not errors on request
     */
    private String validateRequest(RequestDTO requestDTO, MultipartFile classFile, MultipartFile timetableFile) {
        StringJoiner error = new StringJoiner(" \n ");
        if (classFile == null) {
            error.add("Class File is null");
        }
        if (timetableFile == null) {
            error.add("Timetable File is null");
        }
        if (requestDTO == null) {
            error.add("Mappings, execution time and criteria are null");
        }
        if (requestDTO != null && StringUtils.isBlank(requestDTO.getMappingClass())) {
            error.add("Mapping class is null");
        }
        if (requestDTO != null &&
                !Arrays.stream(StringUtils.split(requestDTO.getMappingClass(), ";"))
                        .allMatch(v -> Arrays.stream(GeneralConst.CLASS_FIELD_MAPPING).anyMatch(vv -> StringUtils.equalsIgnoreCase(vv, v)))) {
            error.add("Classroom headers don't match: " + requestDTO.getMappingClass());
        }
        if (requestDTO != null && StringUtils.isBlank(requestDTO.getMappingTimetable())) {
            error.add("Mapping timetable is null");
        }
        if (requestDTO != null &&
                !Arrays.stream(StringUtils.split(requestDTO.getMappingTimetable(), ";"))
                        .allMatch(v -> Arrays.stream(GeneralConst.TIMETABLE_FIELD_MAPPING).anyMatch(vv -> StringUtils.equalsIgnoreCase(vv, v)))) {
            error.add("Timetable headers don't match: " + requestDTO.getMappingTimetable());
        }
        if (requestDTO != null && StringUtils.isBlank(requestDTO.getQualities())) {
            error.add("Qualities is null");
        }
        if (requestDTO != null &&
                !Arrays.stream(StringUtils.split(requestDTO.getQualities(), ";"))
                        .allMatch(v -> Arrays.stream(GeneralConst.LIST_CRITERIA).anyMatch(vv -> StringUtils.equalsIgnoreCase(vv, v)))) {
            error.add("Qualities don't match: " + requestDTO.getQualities());
        }
        if (requestDTO != null && requestDTO.getFast() == null) {
            error.add("Execution time is null");
        }
        if (classFile != null && timetableFile != null) {
            try {
                if (Arrays.stream(GeneralConst.EXTENSION_ALLOWED)
                        .noneMatch(value -> StringUtils.equalsIgnoreCase(value, FilenameUtils.getExtension(classFile.getOriginalFilename())))) {
                    error.add("File don't have the expected extension");
                }
                if (Arrays.stream(GeneralConst.EXTENSION_ALLOWED)
                        .noneMatch(value -> StringUtils.equalsIgnoreCase(value, FilenameUtils.getExtension(timetableFile.getOriginalFilename())))) {
                    error.add("File don't have the expected extension");
                }
            } catch (Throwable e) {
                error.add("Error during file analysis");
            }
        }

        return error.toString();
    }
}
