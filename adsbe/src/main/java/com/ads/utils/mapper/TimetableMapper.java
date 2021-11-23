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

    /**
     * @param timetableDTO
     * @return
     */
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

    /**
     * Maps the timetable
     * @param timetableDTOS
     * @return
     */
    public static List<Timetable> toTimetable(@NonNull List<TimetableDTO> timetableDTOS) {
        return timetableDTOS.stream().map(TimetableMapper::toTimetable).collect(Collectors.toList());
    }

    /**
     * Build the timetable according with some features
     * @param timetable
     * @return
     */
    public static Timetable toTimetable(@NonNull Timetable timetable) {
        return Timetable.builder()
                .course(timetable.getCourse())
                .unit(timetable.getUnit())
                .shift(timetable.getShift())
                .classNumber(timetable.getClassNumber())
                .registeredShift(timetable.getRegisteredShift())
                .overflowShift(timetable.isOverflowShift())
                .overflowEnrollment(timetable.isOverflowEnrollment())
                .dayOfWeek(timetable.getDayOfWeek())
                .begin(timetable.getBegin())
                .end(timetable.getEnd())
                .day(timetable.getDay())
                .features(timetable.getFeatures())
                .capacity(timetable.getCapacity())
                .realFeatures(timetable.getRealFeatures())
                .build();
    }

    /**
     * list of timetable mapped
     * @param timetableList
     * @return
     */
    public static List<Timetable> toTimetableList(@NonNull List<Timetable> timetableList) {
        return timetableList.stream().map(TimetableMapper::toTimetable).collect(Collectors.toList());
    }
}