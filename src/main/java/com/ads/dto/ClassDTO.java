package com.ads.dto;

import com.ads.utils.enums.GenericType;
import com.ads.utils.mapper.Feature;
import com.ads.utils.parser.csv.CsvBooleanField;
import com.ads.utils.parser.csv.CsvIntegerField;
import com.ads.utils.parser.excel.ExcelColumn;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import lombok.Builder;
import lombok.Data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * JMA - 25/10/2021 21:20
 **/
@Data
@Builder
@XmlRootElement(name = "class")
public class ClassDTO implements Serializable {

    @CsvBindByPosition(position = 0)
    @ExcelColumn(index = 0)
    @XmlElement
    private String building;
    @XmlElement
    @CsvBindByPosition(position = 1)
    @ExcelColumn(index = 1)
    private String roomName;
    @XmlElement
    @CsvCustomBindByPosition(position = 2, converter = CsvIntegerField.class)
    @ExcelColumn(index = 2, type = GenericType.INT)
    private int normalCapacity;
    @XmlElement
    @CsvCustomBindByPosition(position = 3, converter = CsvIntegerField.class)
    @ExcelColumn(index = 3, type = GenericType.INT)
    private int examCapacity;
    @XmlElement
    @CsvCustomBindByPosition(position = 4, converter = CsvIntegerField.class)
    @ExcelColumn(index = 4, type = GenericType.INT)
    private int features;
    @XmlElement
    @Feature
    @CsvCustomBindByPosition(position = 5, converter = CsvBooleanField.class)
    @ExcelColumn(index = 5, type = GenericType.BOOLEAN)
    private boolean amphitheaterClasses;
    @XmlElement
    @Feature
    @CsvCustomBindByPosition(position = 6, converter = CsvBooleanField.class)
    @ExcelColumn(index = 6, type = GenericType.BOOLEAN)
    private boolean technicalSupportForEvents;
    @XmlElement
    @Feature
    @CsvCustomBindByPosition(position = 7, converter = CsvBooleanField.class)
    @ExcelColumn(index = 7, type = GenericType.BOOLEAN)
    private boolean arq1;
    @XmlElement
    @Feature
    @CsvCustomBindByPosition(position = 8, converter = CsvBooleanField.class)
    @ExcelColumn(index = 8, type = GenericType.BOOLEAN)
    private boolean arq2;
    @XmlElement
    @Feature
    @CsvCustomBindByPosition(position = 9, converter = CsvBooleanField.class)
    @ExcelColumn(index = 9, type = GenericType.BOOLEAN)
    private boolean arq3;
    @XmlElement
    @Feature
    @CsvCustomBindByPosition(position = 10, converter = CsvBooleanField.class)
    @ExcelColumn(index = 10, type = GenericType.BOOLEAN)
    private boolean arch4;
    @XmlElement
    @Feature
    @CsvCustomBindByPosition(position = 11, converter = CsvBooleanField.class)
    @ExcelColumn(index = 11, type = GenericType.BOOLEAN)
    private boolean arch5;
    @XmlElement
    @Feature
    @CsvCustomBindByPosition(position = 12, converter = CsvBooleanField.class)
    @ExcelColumn(index = 12, type = GenericType.BOOLEAN)
    private boolean arq6;
    @XmlElement
    @Feature
    @CsvCustomBindByPosition(position = 13, converter = CsvBooleanField.class)
    @ExcelColumn(index = 13, type = GenericType.BOOLEAN)
    private boolean arq9;
    @XmlElement
    @Feature
    @CsvCustomBindByPosition(position = 14, converter = CsvBooleanField.class)
    @ExcelColumn(index = 14, type = GenericType.BOOLEAN)
    private boolean byod;
    @XmlElement
    @Feature
    @CsvCustomBindByPosition(position = 15, converter = CsvBooleanField.class)
    @ExcelColumn(index = 15, type = GenericType.BOOLEAN)
    private boolean focusGroup;
    @XmlElement
    @Feature
    @CsvCustomBindByPosition(position = 16, converter = CsvBooleanField.class)
    @ExcelColumn(index = 16, type = GenericType.BOOLEAN)
    private boolean scheduleVisiblePublicPortalRoom;
    @XmlElement
    @Feature
    @CsvCustomBindByPosition(position = 17, converter = CsvBooleanField.class)
    @ExcelColumn(index = 17, type = GenericType.BOOLEAN)
    private boolean computerArchLab1;
    @XmlElement
    @Feature
    @CsvCustomBindByPosition(position = 18, converter = CsvBooleanField.class)
    @ExcelColumn(index = 18, type = GenericType.BOOLEAN)
    private boolean computerArchLab2;
    @XmlElement
    @Feature
    @CsvCustomBindByPosition(position = 19, converter = CsvBooleanField.class)
    @ExcelColumn(index = 19, type = GenericType.BOOLEAN)
    private boolean engineeringBasesLab;
    @XmlElement
    @Feature
    @CsvCustomBindByPosition(position = 20, converter = CsvBooleanField.class)
    @ExcelColumn(index = 20, type = GenericType.BOOLEAN)
    private boolean electronicsLab;
    @XmlElement
    @Feature
    @CsvCustomBindByPosition(position = 21, converter = CsvBooleanField.class)
    @ExcelColumn(index = 21, type = GenericType.BOOLEAN)
    private boolean computerLab;
    @XmlElement
    @Feature
    @CsvCustomBindByPosition(position = 22, converter = CsvBooleanField.class)
    @ExcelColumn(index = 22, type = GenericType.BOOLEAN)
    private boolean journalismLab;
    @XmlElement
    @Feature
    @CsvCustomBindByPosition(position = 23, converter = CsvBooleanField.class)
    @ExcelColumn(index = 23, type = GenericType.BOOLEAN)
    private boolean computerNetworksLab1;
    @XmlElement
    @Feature
    @CsvCustomBindByPosition(position = 24, converter = CsvBooleanField.class)
    @ExcelColumn(index = 24, type = GenericType.BOOLEAN)
    private boolean computerNetworksLab3;
    @XmlElement
    @Feature
    @CsvCustomBindByPosition(position = 25, converter = CsvBooleanField.class)
    @ExcelColumn(index = 25, type = GenericType.BOOLEAN)
    private boolean telecommunicationsLab;
    @XmlElement
    @Feature
    @CsvCustomBindByPosition(position = 26, converter = CsvBooleanField.class)
    @ExcelColumn(index = 26, type = GenericType.BOOLEAN)
    private boolean masterClassroom;
    @XmlElement
    @Feature
    @CsvCustomBindByPosition(position = 27, converter = CsvBooleanField.class)
    @ExcelColumn(index = 27, type = GenericType.BOOLEAN)
    private boolean masterPlusClassroom;
    @XmlElement
    @Feature
    @CsvCustomBindByPosition(position = 28, converter = CsvBooleanField.class)
    @ExcelColumn(index = 28, type = GenericType.BOOLEAN)
    private boolean senRoom;
    @XmlElement
    @Feature
    @CsvCustomBindByPosition(position = 29, converter = CsvBooleanField.class)
    @ExcelColumn(index = 29, type = GenericType.BOOLEAN)
    private boolean evidenceRoom;
    @XmlElement
    @Feature
    @CsvCustomBindByPosition(position = 30, converter = CsvBooleanField.class)
    @ExcelColumn(index = 30, type = GenericType.BOOLEAN)
    private boolean meetingRoom;
    @XmlElement
    @Feature
    @CsvCustomBindByPosition(position = 31, converter = CsvBooleanField.class)
    @ExcelColumn(index = 31, type = GenericType.BOOLEAN)
    private boolean architectureRoom;
    @XmlElement
    @Feature
    @CsvCustomBindByPosition(position = 32, converter = CsvBooleanField.class)
    @ExcelColumn(index = 32, type = GenericType.BOOLEAN)
    private boolean normalClassroom;
    @XmlElement
    @Feature
    @CsvCustomBindByPosition(position = 33, converter = CsvBooleanField.class)
    @ExcelColumn(index = 33, type = GenericType.BOOLEAN)
    private boolean videoConference;
    @XmlElement
    @Feature
    @CsvCustomBindByPosition(position = 34, converter = CsvBooleanField.class)
    @ExcelColumn(index = 34, type = GenericType.BOOLEAN)
    private boolean atrium;
    private List<String> otherFeatures;
}
