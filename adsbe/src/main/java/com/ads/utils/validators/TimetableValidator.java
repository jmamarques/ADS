package com.ads.utils.validators;

import com.ads.models.dto.ClassDTO;
import com.ads.models.internal.ClassRoom;
import com.ads.models.internal.Timetable;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * JMA - 07/11/2021 20:53
 * class that validates the timetable
 **/
public class TimetableValidator {

    /**
     * verify if is valid class
     * @param classDTOs
     * @return
     */
    public static boolean isValidClassDTO(List<ClassDTO> classDTOs) {
        // is not valid if found that not respect TimetableValidator::isValidClassDTO
        return classDTOs != null && classDTOs.stream().map(TimetableValidator::isValidClassDTO).anyMatch(aBoolean -> !aBoolean);
    }

    /**
     * verify if is valid class
     * @param classDTO
     * @return
     */
    public static boolean isValidClassDTO(ClassDTO classDTO) {
        // not null
        boolean res = classDTO != null;
        // TODO add specific error message
        // mandatory fields fill
        if (res) {
            res = StringUtils.isNotBlank(classDTO.getBuilding());
            res &= StringUtils.isNotBlank(classDTO.getRoomName());
            res &= classDTO.getExamCapacity() > 0;
            res &= classDTO.getNormalCapacity() > 0;
            res &= classDTO.getFeatures() >= 0;
        }
        return res;
    }


    /**
     * verify if is valid timetable
     * @param timetable
     * @return
     */
    public static boolean isValidTimetable(Timetable timetable) {
        return timetable != null && timetable.getEnd() != null && timetable.getBegin() != null && timetable.getDay() != null;
    }

    /**
     * verify if is valid timetable
     * @param timetables
     * @return
     */
    public static List<Timetable> validationTimetables(@NonNull List<Timetable> timetables) {
        return timetables.stream().peek(timetable -> {
            if (!TimetableValidator.isValidTimetable(timetable)) {
                timetable.setHasError(true);
                timetable.setError("It does not has begin, end and day filled");
            }
        }).collect(Collectors.toList());
    }

    /**
     * verify if is valid class
     * @param classRoom
     * @return
     */
    public static boolean isValidClassRoom(ClassRoom classRoom) {
        return classRoom != null && StringUtils.isNotBlank(classRoom.getRoomName());
    }

    /**
     * @param classRooms
     * @return
     */
    public static List<ClassRoom> validationClassRooms(@NonNull List<ClassRoom> classRooms) {
        return classRooms.stream().peek(classRoom -> {
            if (!TimetableValidator.isValidClassRoom(classRoom)) {
                classRoom.setHasError(true);
                classRoom.setError("Class Room Name is not filled");
            }
        }).collect(Collectors.toList());
    }
}
