package com.ads;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.List;

public class GUI {
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
}
