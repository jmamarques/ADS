package com.ads.models.dto;

import com.ads.utils.enums.GenericType;
import com.ads.utils.mapper.Feature;
import com.ads.utils.parser.csv.CsvBooleanField;
import com.ads.utils.parser.csv.CsvIntegerField;
import com.ads.utils.parser.excel.ExcelColumn;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * class that serialize objects to convert its state to a byte stream (this byte stream can be reverted too)
 * in this case the complete object with room caracterization
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    @Feature(name = "Anfiteatro aulas")
    @CsvCustomBindByPosition(position = 5, converter = CsvBooleanField.class)
    @ExcelColumn(index = 5, type = GenericType.BOOLEAN)
    private boolean amphitheaterClasses;
    @XmlElement
    @Feature(name = "Apoio técnico eventos")
    @CsvCustomBindByPosition(position = 6, converter = CsvBooleanField.class)
    @ExcelColumn(index = 6, type = GenericType.BOOLEAN)
    private boolean technicalSupportForEvents;
    @XmlElement
    @Feature(name = "Arq 1")
    @CsvCustomBindByPosition(position = 7, converter = CsvBooleanField.class)
    @ExcelColumn(index = 7, type = GenericType.BOOLEAN)
    private boolean arq1;
    @XmlElement
    @Feature(name = "Arq 2")
    @CsvCustomBindByPosition(position = 8, converter = CsvBooleanField.class)
    @ExcelColumn(index = 8, type = GenericType.BOOLEAN)
    private boolean arq2;
    @XmlElement
    @Feature(name = "Arq 3")
    @CsvCustomBindByPosition(position = 9, converter = CsvBooleanField.class)
    @ExcelColumn(index = 9, type = GenericType.BOOLEAN)
    private boolean arq3;
    @XmlElement
    @Feature(name = "Arq 4")
    @CsvCustomBindByPosition(position = 10, converter = CsvBooleanField.class)
    @ExcelColumn(index = 10, type = GenericType.BOOLEAN)
    private boolean arch4;
    @XmlElement
    @Feature(name = "Arq 5")
    @CsvCustomBindByPosition(position = 11, converter = CsvBooleanField.class)
    @ExcelColumn(index = 11, type = GenericType.BOOLEAN)
    private boolean arch5;
    @XmlElement
    @Feature(name = "Arq 6")
    @CsvCustomBindByPosition(position = 12, converter = CsvBooleanField.class)
    @ExcelColumn(index = 12, type = GenericType.BOOLEAN)
    private boolean arq6;
    @XmlElement
    @Feature(name = "Arq 9")
    @CsvCustomBindByPosition(position = 13, converter = CsvBooleanField.class)
    @ExcelColumn(index = 13, type = GenericType.BOOLEAN)
    private boolean arq9;
    @XmlElement
    @Feature(name = "BYOD (Bring Your Own Device)")
    @CsvCustomBindByPosition(position = 14, converter = CsvBooleanField.class)
    @ExcelColumn(index = 14, type = GenericType.BOOLEAN)
    private boolean byod;
    @XmlElement
    @Feature(name = "Focus Group")
    @CsvCustomBindByPosition(position = 15, converter = CsvBooleanField.class)
    @ExcelColumn(index = 15, type = GenericType.BOOLEAN)
    private boolean focusGroup;
    @XmlElement
    @Feature(name = "Horário sala visível portal público")
    @CsvCustomBindByPosition(position = 16, converter = CsvBooleanField.class)
    @ExcelColumn(index = 16, type = GenericType.BOOLEAN)
    private boolean scheduleVisiblePublicPortalRoom;
    @XmlElement
    @Feature(name = "Laboratório de Arquitectura de Computadores I")
    @CsvCustomBindByPosition(position = 17, converter = CsvBooleanField.class)
    @ExcelColumn(index = 17, type = GenericType.BOOLEAN)
    private boolean computerArchLab1;
    @XmlElement
    @Feature(name = "Laboratório de Arquitectura de Computadores II")
    @CsvCustomBindByPosition(position = 18, converter = CsvBooleanField.class)
    @ExcelColumn(index = 18, type = GenericType.BOOLEAN)
    private boolean computerArchLab2;
    @XmlElement
    @Feature(name = "Laboratório de Bases de Engenharia")
    @CsvCustomBindByPosition(position = 19, converter = CsvBooleanField.class)
    @ExcelColumn(index = 19, type = GenericType.BOOLEAN)
    private boolean engineeringBasesLab;
    @XmlElement
    @Feature(name = "Laboratório de Electrónica")
    @CsvCustomBindByPosition(position = 20, converter = CsvBooleanField.class)
    @ExcelColumn(index = 20, type = GenericType.BOOLEAN)
    private boolean electronicsLab;
    @XmlElement
    @Feature(name = "Laboratório de Informática")
    @CsvCustomBindByPosition(position = 21, converter = CsvBooleanField.class)
    @ExcelColumn(index = 21, type = GenericType.BOOLEAN)
    private boolean computerLab;
    @XmlElement
    @Feature(name = "Laboratório de Jornalismo")
    @CsvCustomBindByPosition(position = 22, converter = CsvBooleanField.class)
    @ExcelColumn(index = 22, type = GenericType.BOOLEAN)
    private boolean journalismLab;
    @XmlElement
    @Feature(name = "Laboratório de Redes de Computadores I")
    @CsvCustomBindByPosition(position = 23, converter = CsvBooleanField.class)
    @ExcelColumn(index = 23, type = GenericType.BOOLEAN)
    private boolean computerNetworksLab1;
    @XmlElement
    @Feature(name = "Laboratório de Redes de Computadores II")
    @CsvCustomBindByPosition(position = 24, converter = CsvBooleanField.class)
    @ExcelColumn(index = 24, type = GenericType.BOOLEAN)
    private boolean computerNetworksLab3;
    @XmlElement
    @Feature(name = "Laboratório de Telecomunicações")
    @CsvCustomBindByPosition(position = 25, converter = CsvBooleanField.class)
    @ExcelColumn(index = 25, type = GenericType.BOOLEAN)
    private boolean telecommunicationsLab;
    @XmlElement
    @Feature(name = "Sala Aulas Mestrado")
    @CsvCustomBindByPosition(position = 26, converter = CsvBooleanField.class)
    @ExcelColumn(index = 26, type = GenericType.BOOLEAN)
    private boolean masterClassroom;
    @XmlElement
    @Feature(name = "Sala Aulas Mestrado Plus")
    @CsvCustomBindByPosition(position = 27, converter = CsvBooleanField.class)
    @ExcelColumn(index = 27, type = GenericType.BOOLEAN)
    private boolean masterPlusClassroom;
    @XmlElement
    @Feature(name = "Sala NEE")
    @CsvCustomBindByPosition(position = 28, converter = CsvBooleanField.class)
    @ExcelColumn(index = 28, type = GenericType.BOOLEAN)
    private boolean senRoom;
    @XmlElement
    @Feature(name = "Sala Provas")
    @CsvCustomBindByPosition(position = 29, converter = CsvBooleanField.class)
    @ExcelColumn(index = 29, type = GenericType.BOOLEAN)
    private boolean evidenceRoom;
    @XmlElement
    @Feature(name = "Sala Reunião")
    @CsvCustomBindByPosition(position = 30, converter = CsvBooleanField.class)
    @ExcelColumn(index = 30, type = GenericType.BOOLEAN)
    private boolean meetingRoom;
    @XmlElement
    @Feature(name = "Sala de Arquitectura")
    @CsvCustomBindByPosition(position = 31, converter = CsvBooleanField.class)
    @ExcelColumn(index = 31, type = GenericType.BOOLEAN)
    private boolean architectureRoom;
    @XmlElement
    @Feature(name = "Sala de Aulas normal")
    @CsvCustomBindByPosition(position = 32, converter = CsvBooleanField.class)
    @ExcelColumn(index = 32, type = GenericType.BOOLEAN)
    private boolean normalClassroom;
    @XmlElement
    @Feature(name = "videoconferencia")
    @CsvCustomBindByPosition(position = 33, converter = CsvBooleanField.class)
    @ExcelColumn(index = 33, type = GenericType.BOOLEAN)
    private boolean videoConference;
    @XmlElement
    @Feature(name = "Átrio")
    @CsvCustomBindByPosition(position = 34, converter = CsvBooleanField.class)
    @ExcelColumn(index = 34, type = GenericType.BOOLEAN)
    private boolean atrium;
    private List<String> otherFeatures;
}
