package com.ads;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GUI {

    // method to return the headers in case of being a xlsx file
    public List<String> getHeaderData(Sheet sheet) {
        List<String> headers = new ArrayList<>();
        Row row;
        int headerId = 0;
        if (sheet.getRow(headerId) != null && sheet.getRow(headerId).cellIterator() != null) {
            row = sheet.getRow(headerId);

            for (int h = 0; h <= row.getLastCellNum(); h++) {
                if (row.getCell((short) h) != null && row.getCell((short) h).toString().length() != 0) {
                    headers.add(row.getCell((short) h).toString());
                }
            }

        }
        return headers;
    }

    // method to return the headers in case of being a csv file
    public  List<List<String>> getHeaderDataCSV(String file) throws IOException {
        List<List<String>> headers = new ArrayList<>();
        String delimiter = ",";
        String line;
        BufferedReader br = new BufferedReader(new FileReader(file));
        List<String> values = Arrays.asList(br.readLine().split(delimiter));
        headers.add(values);
        return headers;
    }

    /*
    When call this 2 methods
     String ext1 = FilenameUtils.getExtension("pathhhh");
     verify if ends in xlsx or csv */
}
