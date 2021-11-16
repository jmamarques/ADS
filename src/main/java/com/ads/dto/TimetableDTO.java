package com.ads.dto;

import com.ads.utils.enums.DayOfWeek;
import com.ads.utils.enums.GenericType;
import com.ads.utils.parser.csv.*;
import com.ads.utils.parser.excel.ExcelColumn;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import lombok.Builder;
import lombok.Data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * JMA - 16/11/2021 22:39
 **/
@Data
@Builder
@XmlRootElement(name = "timetable")
public class TimetableDTO implements Serializable {
    @CsvBindByPosition(position = 0)
    @ExcelColumn(index = 0)
    @XmlElement
    private String course;


    @CsvBindByPosition(position = 1)
    @ExcelColumn(index = 1)
    @XmlElement
    private String unit;


    @CsvBindByPosition(position = 2)
    @ExcelColumn(index = 2)
    @XmlElement
    private String shift;


    @CsvCustomBindByPosition(position = 3, converter = CsvIntegerField.class)
    @ExcelColumn(index = 3)
    @XmlElement
    private int registeredShift;


    @CsvCustomBindByPosition(position = 4, converter = CsvBooleanStringField.class)
    @ExcelColumn(index = 4, type = GenericType.BOOLEAN_FROM)
    @XmlElement
    private boolean overflowShift;


    @CsvCustomBindByPosition(position = 5, converter = CsvBooleanStringField.class)
    @ExcelColumn(index = 5, type = GenericType.BOOLEAN_FROM)
    @XmlElement
    private boolean overflowEnrollment;


    @CsvCustomBindByPosition(position = 6, converter = CsvBooleanStringField.class)
    @ExcelColumn(index = 6, type = GenericType.DAY_OF_WEEK)
    @XmlElement
    private DayOfWeek dayOfWeek;


    @CsvCustomBindByPosition(position = 7, converter = CsvLocalTimeField.class)
    @ExcelColumn(index = 7, type = GenericType.LOCAL_TIME)
    @XmlElement
    private LocalTime begin;


    @CsvCustomBindByPosition(position = 8, converter = CsvLocalTimeField.class)
    @ExcelColumn(index = 8, type = GenericType.LOCAL_TIME)
    @XmlElement
    private LocalTime end;


    @CsvCustomBindByPosition(position = 9, converter = CsvLocalDateField.class)
    @ExcelColumn(index = 9, type = GenericType.LOCAL_DATE)
    @XmlElement
    private LocalDate day;


    @CsvBindByPosition(position = 10)
    @ExcelColumn(index = 10)
    @XmlElement
    private String features;


    @CsvBindByPosition(position = 11)
    @ExcelColumn(index = 11)
    @XmlElement
    private String classRoom;


    @CsvCustomBindByPosition(position = 12, converter = CsvIntegerField.class)
    @ExcelColumn(index = 12)
    @XmlElement
    private int capacity;


    @CsvCustomBindByPosition(position = 13, converter = CsvListCommaField.class)
    @ExcelColumn(index = 13, type = GenericType.LIST_COMMA)
    @XmlElement
    private List<String> realFeatures;
}
