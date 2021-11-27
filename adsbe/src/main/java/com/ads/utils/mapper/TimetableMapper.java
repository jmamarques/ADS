package com.ads.utils.mapper;

import com.ads.dto.TimetableDTO;
import com.ads.models.Timetable;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JMA - 17/11/2021 23:07
 **/
@Log4j2
public class TimetableMapper {

    /**
     * Maps timetable dto into timetable
     *
     * @param timetableDTO timetable dto
     * @return timetable mapped
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
                .classRoom(timetableDTO.getClassRoom())
                .capacity(timetableDTO.getCapacity())
                .realFeatures(timetableDTO.getRealFeatures())
                .build();
    }

    /**
     * Maps the timetable
     *
     * @param timetableDTOS - list of timetables dto
     * @return list of timetables - objects
     */
    public static List<Timetable> toTimetable(@NonNull List<TimetableDTO> timetableDTOS) {
        return timetableDTOS.stream().map(TimetableMapper::toTimetable).collect(Collectors.toList());
    }

    /**
     * Build the timetable according with some features
     *
     * @param timetable - timetable target
     * @return new timetable
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
                .classRoom(timetable.getClassRoom())
                .realFeatures(timetable.getRealFeatures())
                .build();
    }

    /**
     * list of timetable mapped
     *
     * @param timetableList timetable list
     * @return List of timetable without classroon filled
     */
    public static List<Timetable> toTimetableList(@NonNull List<Timetable> timetableList) {
        return timetableList.stream().map(TimetableMapper::toTimetable).collect(Collectors.toList());
    }

    /**
     * list of timetable mapped without class
     *
     * @param timetableList timetable list
     * @return List of timetable without classroom filled
     */
    public static List<Timetable> toTimetableWithoutClassList(@NonNull List<Timetable> timetableList) {
        return timetableList.stream().map(TimetableMapper::toTimetable).peek(timetable -> timetable.setClassRoom(null)).collect(Collectors.toList());
    }

    /**
     * Split timetable based on Time in period of 30minutes
     *
     * @param timetableList target timetable
     * @return List of timetable of 30 minutes
     */
    public static List<Timetable> splitTimetableBasedOnTime(@NonNull List<Timetable> timetableList) {
        ArrayList<Timetable> resultList = new ArrayList<>();
        timetableList.stream()
                .forEach(timetable -> {
                    if (timetable.isHasError()) {
                        resultList.add(TimetableMapper.toTimetable(timetable));
                    } else if (timetable.getBegin() == null || timetable.getEnd() == null) {
                        Timetable timetableTemp = TimetableMapper.toTimetable(timetable);
                        timetableTemp.setHasError(true);
                        timetableTemp.setError("Begin Date and/or End Date is/are null");
                        resultList.add(timetableTemp);
                    } else {
                        LocalTime begin = timetable.getBegin();
                        LocalTime end = timetable.getEnd();
                        while (begin.isBefore(end)) {
                            LocalTime tempEnd = begin.plusMinutes(30);
                            Timetable timetableTemp = TimetableMapper.toTimetable(timetable);
                            timetableTemp.setBegin(begin);
                            timetableTemp.setEnd(tempEnd);
                            resultList.add(timetableTemp);
                            begin = tempEnd;
                        }
                    }
                });
        return resultList;
    }
}
