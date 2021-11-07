package com.ads.utils.validators;

import com.ads.dto.ClassDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * JMA - 07/11/2021 20:53
 **/
public class TimetableValidator {

    public static boolean isValidClassDTO(List<ClassDTO> classDTOs) {
        // is not valid if found that not respect TimetableValidator::isValidClassDTO
        return classDTOs != null && classDTOs.stream().map(TimetableValidator::isValidClassDTO).anyMatch(aBoolean -> !aBoolean);
    }

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
}
