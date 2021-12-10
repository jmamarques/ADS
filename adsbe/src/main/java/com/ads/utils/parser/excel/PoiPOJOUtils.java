package com.ads.utils.parser.excel;

import com.ads.utils.enums.GenericType;
import com.ads.utils.parser.csv.CsvDayOfWeekField;
import com.ads.utils.parser.csv.CsvLocalTimeField;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * JMA - 25/10/2021 21:38
 * Helps to convert the Excel format in java class and vice versa (abstraction)
 **/
@Log4j2
public class PoiPOJOUtils {

    /**
     * Convert Excel in Java Object
     *
     * @param sheet     Excel sheet
     * @param beanClass class to convert
     * @param <T>       Generic parameter
     * @return List of java object from Excel sheet
     * @throws Exception if any error occur during the conversion
     */
    public static <T> List<T> sheetToPOJO(Sheet sheet, Class<T> beanClass) throws Exception {

        DataFormatter formatter = new DataFormatter(java.util.Locale.US);
        FormulaEvaluator evaluator = sheet.getWorkbook().getCreationHelper().createFormulaEvaluator();

        int headerRowNum = sheet.getFirstRowNum();

        // collecting the column headers as a Map of header names to column indexes
        Map<Integer, String> colHeaders = new HashMap<>();
        Row row = sheet.getRow(headerRowNum);
        for (Cell cell : row) {
            int colIdx = cell.getColumnIndex();
            String value = formatter.formatCellValue(cell, evaluator);
            colHeaders.put(colIdx, value);
        }

        // collecting the content rows
        List<T> result = new ArrayList<>();
        String cellValue = "";
        java.util.Date date = null;
        Double num = null;
        for (int r = headerRowNum + 1; r <= sheet.getLastRowNum(); r++) {
            row = sheet.getRow(r);
            if (row == null) row = sheet.createRow(r);
            T bean = beanClass.getDeclaredConstructor().newInstance();

            for (Map.Entry<Integer, String> entry : colHeaders.entrySet()) {
                int colIdx = entry.getKey();
                Cell cell = row.getCell(colIdx);
                if (cell == null) cell = row.createCell(colIdx);
                cellValue = formatter.formatCellValue(cell, evaluator); // string values and formatted numbers
                // make some differences for numeric or formula content
                date = null;
                num = null;
                if (cell.getCellType() == CellType.NUMERIC) {
                    if (DateUtil.isCellDateFormatted(cell)) { // date
                        date = cell.getDateCellValue();
                    } else { // other numbers
                        num = cell.getNumericCellValue();
                    }
                } else if (cell.getCellType() == CellType.FORMULA) {
                    // if formula evaluates to numeric
                    if (evaluator.evaluateFormulaCell(cell) == CellType.NUMERIC) {
                        if (DateUtil.isCellDateFormatted(cell)) { // date
                            date = cell.getDateCellValue();
                        } else { // other numbers
                            num = cell.getNumericCellValue();
                        }
                    }
                }

                // fill the bean
                StringJoiner errorBuilder = new StringJoiner("\n");
                for (Field f : beanClass.getDeclaredFields()) {
                    if (!f.isAnnotationPresent(ExcelColumn.class)) {
                        continue;
                    }
                    try {
                        ExcelColumn ec = f.getAnnotation(ExcelColumn.class);
                        if ((StringUtils.isBlank(ec.name()) && ec.index() == colIdx) || entry.getValue().equals(ec.name())) {
                            f.setAccessible(true);
                            if (GenericType.BOOLEAN == ec.type()) {
                                f.set(bean, StringUtils.isNoneBlank(cellValue));
                            } else if (GenericType.INT == ec.type()) {
                                f.set(bean, Integer.valueOf(cellValue));
                            } else if (f.getType() == String.class) {
                                f.set(bean, cellValue);
                            } else if (f.getType() == Double.class) {
                                f.set(bean, num);
                            } else if (f.getType() == java.util.Date.class) {
                                f.set(bean, date);
                            } else { // this is for all other; Integer, Boolean, ...
                                if (!"".equals(cellValue)) {
                                    Method valueOf = f.getType().getDeclaredMethod("valueOf", String.class);
                                    f.set(bean, valueOf.invoke(f.getType(), cellValue));
                                }
                            }
                        }
                    } catch (Exception e) {
                        errorBuilder.add("Field Error: " + f.getName() + " type:" + f.getType() + " Exception:" + e.getMessage());
                    }
                }
                if (errorBuilder.length() > 0) {
                    log.warn(errorBuilder.toString());
                }
            }
            result.add(bean);
        }
        return result;

    }

    /**
     * Convert a list of object in a Excel sheet
     *
     * @param sheet Excel sheet
     * @param rows  List of objects to create the Excel
     * @param <T>   Generic parameter
     * @throws Exception if any error occur during the conversion
     */
    public static <T> void pojoToSheet(Sheet sheet, List<T> rows) throws Exception {
        if (rows.size() > 0) {
            Row row = null;
            Cell cell = null;
            int r = 0;
            int c = 0;
            int colCount = 0;
            Map<String, Object> properties = null;
            DataFormat dataFormat = sheet.getWorkbook().createDataFormat();

            Class beanClass = rows.get(0).getClass();

            // header row
            row = sheet.createRow(r++);
            for (Field f : beanClass.getDeclaredFields()) {
                if (!f.isAnnotationPresent(ExcelColumn.class)) {
                    continue;
                }
                ExcelColumn ec = f.getAnnotation(ExcelColumn.class);
                cell = row.createCell(c++);
                // do formatting the header row
                properties = new HashMap<>();
                properties.put(CellUtil.FILL_PATTERN, FillPatternType.SOLID_FOREGROUND);
                properties.put(CellUtil.FILL_FOREGROUND_COLOR, IndexedColors.GREY_25_PERCENT.getIndex());
                CellUtil.setCellStyleProperties(cell, properties);
                cell.setCellValue(ec.name());
            }

            colCount = c;

            // contents
            for (T bean : rows) {
                c = 0;
                row = sheet.createRow(r++);
                for (Field f : beanClass.getDeclaredFields()) {
                    cell = row.createCell(c++);
                    if (!f.isAnnotationPresent(ExcelColumn.class)) {
                        continue;
                    }
                    ExcelColumn ec = f.getAnnotation(ExcelColumn.class);
                    // do number formatting the contents
                    String numberFormat = ec.numberFormat();
                    properties = new HashMap<>();
                    properties.put(CellUtil.DATA_FORMAT, dataFormat.getFormat(numberFormat));
                    CellUtil.setCellStyleProperties(cell, properties);

                    f.setAccessible(true);
                    Object value = f.get(bean);
                    if (value != null) {
                        if (value instanceof String) {
                            cell.setCellValue((String) value);
                        } else if (value instanceof Double) {
                            cell.setCellValue((Double) value);
                        } else if (value instanceof Integer) {
                            cell.setCellValue((Integer) value);
                        } else if (value instanceof java.util.Date) {
                            cell.setCellValue((java.util.Date) value);
                        } else if (value instanceof Boolean) {
                            cell.setCellValue((Boolean) value);
                        }
                    }
                }
            }

            // auto size columns
            for (int col = 0; col < colCount; col++) {
                sheet.autoSizeColumn(col);
            }
        }
    }

    /**
     * Convert Excel in Java Object based on mapping
     *
     * @param sheet     Excel sheet
     * @param beanClass class to convert
     * @param <T>       Generic parameter
     * @return List of java object from Excel sheet
     * @throws Exception if any error occur during the conversion
     */
    public static <T> List<T> sheetToPOJO(Sheet sheet, Class<T> beanClass, List<String> headers, String[] mainHeaders, String splitter) throws Exception {

        DataFormatter formatter = new DataFormatter(java.util.Locale.US);
        FormulaEvaluator evaluator = sheet.getWorkbook().getCreationHelper().createFormulaEvaluator();

        int headerRowNum = sheet.getFirstRowNum();

        // collecting the column headers as a Map of header names to column indexes
        Map<Integer, String> colHeaders = new HashMap<>();
        Row row = sheet.getRow(headerRowNum);
        for (Cell cell : row) {
            int colIdx = cell.getColumnIndex();
            String value = formatter.formatCellValue(cell, evaluator);
            colHeaders.put(colIdx, value);
        }

        // collecting the content rows
        List<T> result = new ArrayList<>();
        String cellValue = "";
        java.util.Date date = null;
        Double num = null;
        for (int r = headerRowNum + 1; r <= sheet.getLastRowNum(); r++) {
            row = sheet.getRow(r);
            if (row == null) row = sheet.createRow(r);
            T bean = beanClass.getDeclaredConstructor().newInstance();

            for (Map.Entry<Integer, String> entry : colHeaders.entrySet()) {
                int colIdx = entry.getKey();
                Cell cell = row.getCell(colIdx);
                if (cell == null) cell = row.createCell(colIdx);
                cellValue = formatter.formatCellValue(cell, evaluator); // string values and formatted numbers
                // make some differences for numeric or formula content
                date = null;
                num = null;
                if (cell.getCellType() == CellType.NUMERIC) {
                    if (DateUtil.isCellDateFormatted(cell)) { // date
                        date = cell.getDateCellValue();
                    } else { // other numbers
                        num = cell.getNumericCellValue();
                    }
                } else if (cell.getCellType() == CellType.FORMULA) {
                    // if formula evaluates to numeric
                    if (evaluator.evaluateFormulaCell(cell) == CellType.NUMERIC) {
                        if (DateUtil.isCellDateFormatted(cell)) { // date
                            date = cell.getDateCellValue();
                        } else { // other numbers
                            num = cell.getNumericCellValue();
                        }
                    }
                }

                // fill the bean
                HashMap<String, Integer> mappingColumns = mappingColumns(headers, mainHeaders);
                StringJoiner errorBuilder = new StringJoiner("\n");
                for (Field f : beanClass.getDeclaredFields()) {
                    if (!f.isAnnotationPresent(ExcelColumn.class)) {
                        continue;
                    }
                    try {
                        ExcelColumn ec = f.getAnnotation(ExcelColumn.class);
                        if ((StringUtils.isBlank(ec.name()) && ec.index() == mappingColumns.get(headers.get(colIdx))) || entry.getValue().equals(ec.name())) {
                            f.setAccessible(true);
                            if (GenericType.BOOLEAN == ec.type()) {
                                f.set(bean, StringUtils.isNoneBlank(cellValue));
                            } else if (GenericType.INT == ec.type()) {
                                f.set(bean, Integer.valueOf(cellValue));
                            } else if (f.getType() == String.class) {
                                f.set(bean, cellValue);
                            } else if (f.getType() == Double.class) {
                                f.set(bean, num);
                            } else if (f.getType() == java.util.Date.class) {
                                f.set(bean, date);
                            } else if (f.getType() == java.time.LocalTime.class) {
                                f.set(bean, CsvLocalTimeField.parseTo(cellValue));
                            } else if (f.getType() == Integer.class) {
                                f.set(bean, Integer.valueOf(cellValue));
                            } else if (f.getType() == Boolean.class) {
                                f.set(bean, Boolean.parseBoolean(cellValue));
                            } else if (f.getType() == java.util.List.class) {
                                f.set(bean, Arrays.stream(StringUtils.split(cellValue, splitter)).map(String::trim).toList());
                            } else if (f.getType() == com.ads.utils.enums.DayOfWeek.class) {
                                f.set(bean, CsvDayOfWeekField.parseTo(cellValue));
                            } else { // this is for all other; Integer, Boolean, ...
                                if (!"".equals(cellValue)) {
                                    Method valueOf = f.getType().getDeclaredMethod("valueOf", String.class);
                                    f.set(bean, valueOf.invoke(f.getType(), cellValue));
                                }
                            }
                        }
                    } catch (Exception e) {
                        errorBuilder.add("Field Error: " + f.getName() + " type:" + f.getType() + " Exception:" + e.getMessage());
                    }
                }
                if (errorBuilder.length() > 0) {
                    log.warn(errorBuilder.toString());
                }
            }
            result.add(bean);
        }
        return result;

    }

    private static HashMap<String, Integer> mappingColumns(List<String> headers, String[] mainValues) {
        HashMap<String, Integer> result = new HashMap<>();
        headers.forEach(s -> result.put(s, Arrays.stream(mainValues).toList().indexOf(s)));
        return result;
    }

}
