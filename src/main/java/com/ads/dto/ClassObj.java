package com.ads.dto;

import com.ads.utils.enums.GenericType;
import com.ads.utils.parser.csv.CsvBooleanField;
import com.ads.utils.parser.csv.CsvIntegerField;
import com.ads.utils.parser.excel.ExcelColumn;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * JMA - 25/10/2021 21:20
 **/
@Getter
@Setter
@ToString
public class ClassObj {

    @CsvBindByPosition(position = 0)
    @ExcelColumn(index = 0)
    private String building;
    @CsvBindByPosition(position = 1)
    @ExcelColumn(index = 1)
    private String roomName;
    @CsvCustomBindByPosition(position = 2, converter = CsvIntegerField.class)
    @ExcelColumn(index = 2, type = GenericType.INT)
    private int normalCapacity;
    @CsvCustomBindByPosition(position = 3, converter = CsvIntegerField.class)
    @ExcelColumn(index = 3, type = GenericType.INT)
    private int examCapacity;
    @CsvCustomBindByPosition(position = 4, converter = CsvIntegerField.class)
    @ExcelColumn(index = 4, type = GenericType.INT)
    private int features;
    @CsvCustomBindByPosition(position = 5, converter = CsvBooleanField.class)
    @ExcelColumn(index = 5, type = GenericType.BOOLEAN)
    private boolean amphitheaterClasses;
    @CsvCustomBindByPosition(position = 6, converter = CsvBooleanField.class)
    @ExcelColumn(index = 6, type = GenericType.BOOLEAN)
    private boolean technicalSupportForEvents;
    @CsvCustomBindByPosition(position = 7, converter = CsvBooleanField.class)
    @ExcelColumn(index = 7, type = GenericType.BOOLEAN)
    private boolean arq1;
    @CsvCustomBindByPosition(position = 8, converter = CsvBooleanField.class)
    @ExcelColumn(index = 8, type = GenericType.BOOLEAN)
    private boolean arq2;
    @CsvCustomBindByPosition(position = 9, converter = CsvBooleanField.class)
    @ExcelColumn(index = 9, type = GenericType.BOOLEAN)
    private boolean arq3;
    @CsvCustomBindByPosition(position = 10, converter = CsvBooleanField.class)
    @ExcelColumn(index = 10, type = GenericType.BOOLEAN)
    private boolean arch4;
    @CsvCustomBindByPosition(position = 11, converter = CsvBooleanField.class)
    @ExcelColumn(index = 11, type = GenericType.BOOLEAN)
    private boolean arch5;
    @CsvCustomBindByPosition(position = 12, converter = CsvBooleanField.class)
    @ExcelColumn(index = 12, type = GenericType.BOOLEAN)
    private boolean arq6;
    @CsvCustomBindByPosition(position = 13, converter = CsvBooleanField.class)
    @ExcelColumn(index = 13, type = GenericType.BOOLEAN)
    private boolean arq9;
    @CsvCustomBindByPosition(position = 14, converter = CsvBooleanField.class)
    @ExcelColumn(index = 14, type = GenericType.BOOLEAN)
    private boolean byod;
    @CsvCustomBindByPosition(position = 15, converter = CsvBooleanField.class)
    @ExcelColumn(index = 15, type = GenericType.BOOLEAN)
    private boolean focusGroup;
    @CsvCustomBindByPosition(position = 16, converter = CsvBooleanField.class)
    @ExcelColumn(index = 16, type = GenericType.BOOLEAN)
    private boolean scheduleVisiblePublicPortalRoom;
    @CsvCustomBindByPosition(position = 17, converter = CsvBooleanField.class)
    @ExcelColumn(index = 17, type = GenericType.BOOLEAN)
    private boolean computerArchLab1;
    @CsvCustomBindByPosition(position = 18, converter = CsvBooleanField.class)
    @ExcelColumn(index = 18, type = GenericType.BOOLEAN)
    private boolean computerArchLab2;
    @CsvCustomBindByPosition(position = 19, converter = CsvBooleanField.class)
    @ExcelColumn(index = 19, type = GenericType.BOOLEAN)
    private boolean engineeringBasesLab;
    @CsvCustomBindByPosition(position = 20, converter = CsvBooleanField.class)
    @ExcelColumn(index = 20, type = GenericType.BOOLEAN)
    private boolean electronicsLab;
    @CsvCustomBindByPosition(position = 21, converter = CsvBooleanField.class)
    @ExcelColumn(index = 21, type = GenericType.BOOLEAN)
    private boolean computerLab;
    @CsvCustomBindByPosition(position = 22, converter = CsvBooleanField.class)
    @ExcelColumn(index = 22, type = GenericType.BOOLEAN)
    private boolean journalismLab;
    @CsvCustomBindByPosition(position = 23, converter = CsvBooleanField.class)
    @ExcelColumn(index = 23, type = GenericType.BOOLEAN)
    private boolean computerNetworksLab1;
    @CsvCustomBindByPosition(position = 24, converter = CsvBooleanField.class)
    @ExcelColumn(index = 24, type = GenericType.BOOLEAN)
    private boolean computerNetworksLab3;
    @CsvCustomBindByPosition(position = 25, converter = CsvBooleanField.class)
    @ExcelColumn(index = 25, type = GenericType.BOOLEAN)
    private boolean telecommunicationsLab;
    @CsvCustomBindByPosition(position = 26, converter = CsvBooleanField.class)
    @ExcelColumn(index = 26, type = GenericType.BOOLEAN)
    private boolean masterClassroom;
    @CsvCustomBindByPosition(position = 27, converter = CsvBooleanField.class)
    @ExcelColumn(index = 27, type = GenericType.BOOLEAN)
    private boolean masterPlusClassroom;
    @CsvCustomBindByPosition(position = 28, converter = CsvBooleanField.class)
    @ExcelColumn(index = 28, type = GenericType.BOOLEAN)
    private boolean senRoom;
    @CsvCustomBindByPosition(position = 29, converter = CsvBooleanField.class)
    @ExcelColumn(index = 29, type = GenericType.BOOLEAN)
    private boolean evidenceRoom;
    @CsvCustomBindByPosition(position = 30, converter = CsvBooleanField.class)
    @ExcelColumn(index = 30, type = GenericType.BOOLEAN)
    private boolean meetingRoom;
    @CsvCustomBindByPosition(position = 31, converter = CsvBooleanField.class)
    @ExcelColumn(index = 31, type = GenericType.BOOLEAN)
    private boolean architectureRoom;
    @CsvCustomBindByPosition(position = 32, converter = CsvBooleanField.class)
    @ExcelColumn(index = 32, type = GenericType.BOOLEAN)
    private boolean normalClassroom;
    @CsvCustomBindByPosition(position = 33, converter = CsvBooleanField.class)
    @ExcelColumn(index = 33, type = GenericType.BOOLEAN)
    private boolean videoConference;
    @CsvCustomBindByPosition(position = 34, converter = CsvBooleanField.class)
    @ExcelColumn(index = 34, type = GenericType.BOOLEAN)
    private boolean atrium;
}
