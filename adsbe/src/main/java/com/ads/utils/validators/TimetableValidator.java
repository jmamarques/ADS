package com.ads.utils.validators;

import com.ads.dto.ClassDTO;
import com.ads.models.ClassRoom;
import com.ads.models.Timetable;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * JMA - 07/11/2021 20:53
 **/
public class TimetableValidator {

    /**
     * @param classDTOs
     * @return
     */
    public static boolean isValidClassDTO(List<ClassDTO> classDTOs) {
        // is not valid if found that not respect TimetableValidator::isValidClassDTO
        return classDTOs != null && classDTOs.stream().map(TimetableValidator::isValidClassDTO).anyMatch(aBoolean -> !aBoolean);
    }

    /**
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


    public static boolean isValidTimetable(Timetable timetable) {
        return timetable != null && timetable.getEnd() != null && timetable.getBegin() != null && timetable.getDay() != null;
    }

    public static List<Timetable> validationTimetables(@NonNull List<Timetable> timetables) {
        return timetables.stream().peek(timetable -> {
            if (!TimetableValidator.isValidTimetable(timetable)) {
                timetable.setHasError(true);
                timetable.setError("It does not has begin, end and day filled");
            }
        }).collect(Collectors.toList());
    }

    public static boolean isValidClassRoom(ClassRoom classRoom) {
        return classRoom != null && StringUtils.isNotBlank(classRoom.getRoomName());
    }

    public static List<ClassRoom> validationClassRooms(@NonNull List<ClassRoom> classRooms) {
        return classRooms.stream().peek(classRoom -> {
            if (!TimetableValidator.isValidClassRoom(classRoom)) {
                classRoom.setHasError(true);
                classRoom.setError("Class Room Name is not filled");
            }
        }).collect(Collectors.toList());
    }
}
