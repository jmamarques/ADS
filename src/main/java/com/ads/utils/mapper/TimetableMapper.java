package com.ads.utils.mapper;

import com.ads.dto.ClassDTO;
import com.ads.models.Timetable;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JMA - 07/11/2021 18:44
 **/
@Log4j2
public class TimetableMapper {

    public static Timetable toTimetable(@NonNull ClassDTO classDTO) {
        // Pattern Builder
        Timetable.TimetableBuilder bean = Timetable.builder()
                .building(classDTO.getBuilding())
                .roomName(classDTO.getRoomName())
                .examCapacity(classDTO.getExamCapacity())
                .normalCapacity(classDTO.getNormalCapacity())
                .featuresNumber(classDTO.getFeatures());
        ArrayList<String> features = new ArrayList<>();
        // add special cases Others values
        if (classDTO.getOtherFeatures() != null) {
            features.addAll(classDTO.getOtherFeatures());
        }
        // process normal cases
        for (Field f : ClassDTO.class.getDeclaredFields()) {
            if (!f.isAnnotationPresent(Feature.class)) {
                continue;
            }
            // do not add the feature when we have troubles to get current value of property
            try {
                f.setAccessible(true);
                if (StringUtils.equals(f.getType().getName(), "boolean") && f.getBoolean(classDTO)) {
                    features.add(f.getName());
                }
            } catch (IllegalAccessException e) {
                log.warn("Error during process of @Feature to get boolean value", e);
            }

        }

        return bean.features(features).build();
    }

    public static List<Timetable> toTimetable(@NonNull List<ClassDTO> classDTOs) {
        return classDTOs.stream().map(TimetableMapper::toTimetable).collect(Collectors.toList());
    }
}
