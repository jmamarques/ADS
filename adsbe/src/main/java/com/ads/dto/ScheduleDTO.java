package com.ads.dto;

import lombok.*;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * class that is a shedule with the caracterization of the rooms and timetables serializable
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@XmlRootElement(name = "root")
public class ScheduleDTO {
    @XmlElement
    @NonNull
    private List<ClassDTO> classDTOS;
    @XmlElement
    @NonNull
    private List<TimetableDTO> timetableDTOS;
}
