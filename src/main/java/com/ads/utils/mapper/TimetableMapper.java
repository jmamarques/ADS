package com.ads.utils.mapper;

import com.ads.dto.TimetableDTO;
import com.ads.models.Timetable;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.stream.Collectors;

/**
 * JMA - 17/11/2021 23:07
 **/
@Log4j2
public class TimetableMapper {

    public static Timetable toTimetable(@NonNull TimetableDTO timetableDTO) {
        return Timetable.builder()
                .course(timetableDTO.getCourse())
                .unit(timetableDTO.getUnit())
                .shift(timetableDTO.getShift())
                .classNumber(timetableDTO.getClassNumber())
                .registeredShift(timetableDTO.getRegisteredShift())
                .overflowShift(timetableDTO.isOverflowShift())
                .overflowEnrollment(timetableDTO.isOverflowEnrollment())
                .dayOfWeek(timetableDTO.getDayOfWeek())
                .begin(timetableDTO.getBegin())
                .end(timetableDTO.getEnd())
                .day(timetableDTO.getDay())
                .features(timetableDTO.getFeatures())
                // Do not copy the Class Room from EXCEL/API
                // .classRoom(timetableDTO.getClassRoom())
                .capacity(timetableDTO.getCapacity())
                .realFeatures(timetableDTO.getRealFeatures())
                .build();
    }

    public static List<Timetable> toTimetable(@NonNull List<TimetableDTO> timetableDTOS) {
        return timetableDTOS.stream().map(TimetableMapper::toTimetable).collect(Collectors.toList());
    }
}
