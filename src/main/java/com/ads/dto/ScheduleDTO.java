package com.ads.dto;

import lombok.*;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * JMA - 17/11/2021 22:59
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
