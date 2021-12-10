package com.ads.utils.mapper;

import com.ads.models.dto.ClassDTO;
import com.ads.models.internal.ClassRoom;
import com.ads.utils.Tuple;
import com.ads.utils.parser.csv.CsvBooleanField;
import com.ads.utils.parser.csv.CsvIntegerField;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.ads.utils.constants.GeneralConst.*;

/**
 * JMA - 07/11/2021 18:44
 * class that mapps the classroom
 **/
@Log4j2
public class ClassRoomMapper {

    /**
     * build an variable of type classroom and adds to the features
     *
     * @param classDTO
     * @return
     */
    public static ClassRoom toClassRoom(@NonNull ClassDTO classDTO) {
        // Pattern Builder
        ClassRoom.ClassRoomBuilder bean = ClassRoom.builder()
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
            Feature feature = f.getAnnotation(Feature.class);
            // do not add the feature when we have troubles to get current value of property
            try {
                f.setAccessible(true);
                if (StringUtils.equals(f.getType().getName(), "boolean") && f.getBoolean(classDTO)) {
                    features.add(feature.name());
                }
            } catch (IllegalAccessException e) {
                log.warn("Error during process of @Feature to get boolean value", e);
            }

        }

        return bean.features(features).build();
    }

    /**
     * @param classDTOs
     * @return
     */
    public static List<ClassRoom> toClassRoom(@NonNull List<ClassDTO> classDTOs) {
        return classDTOs.stream().map(ClassRoomMapper::toClassRoom).collect(Collectors.toList());
    }

    /**
     * Create ClassDTO based on mapping configurations
     *
     * @param mapping - mapping configurations
     * @return ClassDTO
     */
    public static ClassDTO createObj(Tuple<String[], List<String>> mapping) {
        String[] t1 = mapping.t1;
        List<String> v1 = mapping.v1;
        ClassDTO.ClassDTOBuilder builder = ClassDTO.builder();
        if (v1.size() <= t1.length) {
            for (int i = 0; i < v1.size(); i++) {
                String attribute = v1.get(i);
                if (StringUtils.equalsIgnoreCase(F_C_1, attribute)) {
                    builder.building(t1[i]);
                } else if (StringUtils.equalsIgnoreCase(F_C_2, attribute)) {
                    builder.roomName(t1[i]);
                } else if (StringUtils.equalsIgnoreCase(F_C_3, attribute)) {
                    builder.normalCapacity(CsvIntegerField.parseToInt(t1[i]));
                } else if (StringUtils.equalsIgnoreCase(F_C_4, attribute)) {
                    builder.examCapacity(CsvIntegerField.parseToInt(t1[i]));
                } else if (StringUtils.equalsIgnoreCase(F_C_5, attribute)) {
                    builder.features(CsvIntegerField.parseToInt(t1[i]));
                } else if (StringUtils.equalsIgnoreCase(F_C_6, attribute)) {
                    builder.amphitheaterClasses(CsvBooleanField.parseToBoolean(t1[i]));
                } else if (StringUtils.equalsIgnoreCase(F_C_7, attribute)) {
                    builder.technicalSupportForEvents(CsvBooleanField.parseToBoolean(t1[i]));
                } else if (StringUtils.equalsIgnoreCase(F_C_8, attribute)) {
                    builder.arq1(CsvBooleanField.parseToBoolean(t1[i]));
                } else if (StringUtils.equalsIgnoreCase(F_C_9, attribute)) {
                    builder.arq2(CsvBooleanField.parseToBoolean(t1[i]));
                } else if (StringUtils.equalsIgnoreCase(F_C_10, attribute)) {
                    builder.arq3(CsvBooleanField.parseToBoolean(t1[i]));
                } else if (StringUtils.equalsIgnoreCase(F_C_11, attribute)) {
                    builder.arch4(CsvBooleanField.parseToBoolean(t1[i]));
                } else if (StringUtils.equalsIgnoreCase(F_C_12, attribute)) {
                    builder.arch5(CsvBooleanField.parseToBoolean(t1[i]));
                } else if (StringUtils.equalsIgnoreCase(F_C_13, attribute)) {
                    builder.arq6(CsvBooleanField.parseToBoolean(t1[i]));
                } else if (StringUtils.equalsIgnoreCase(F_C_14, attribute)) {
                    builder.arq9(CsvBooleanField.parseToBoolean(t1[i]));
                } else if (StringUtils.equalsIgnoreCase(F_C_15, attribute)) {
                    builder.byod(CsvBooleanField.parseToBoolean(t1[i]));
                } else if (StringUtils.equalsIgnoreCase(F_C_16, attribute)) {
                    builder.focusGroup(CsvBooleanField.parseToBoolean(t1[i]));
                } else if (StringUtils.equalsIgnoreCase(F_C_17, attribute)) {
                    builder.scheduleVisiblePublicPortalRoom(CsvBooleanField.parseToBoolean(t1[i]));
                } else if (StringUtils.equalsIgnoreCase(F_C_18, attribute)) {
                    builder.computerArchLab1(CsvBooleanField.parseToBoolean(t1[i]));
                } else if (StringUtils.equalsIgnoreCase(F_C_19, attribute)) {
                    builder.computerArchLab2(CsvBooleanField.parseToBoolean(t1[i]));
                } else if (StringUtils.equalsIgnoreCase(F_C_20, attribute)) {
                    builder.engineeringBasesLab(CsvBooleanField.parseToBoolean(t1[i]));
                } else if (StringUtils.equalsIgnoreCase(F_C_21, attribute)) {
                    builder.electronicsLab(CsvBooleanField.parseToBoolean(t1[i]));
                } else if (StringUtils.equalsIgnoreCase(F_C_22, attribute)) {
                    builder.computerLab(CsvBooleanField.parseToBoolean(t1[i]));
                } else if (StringUtils.equalsIgnoreCase(F_C_23, attribute)) {
                    builder.journalismLab(CsvBooleanField.parseToBoolean(t1[i]));
                } else if (StringUtils.equalsIgnoreCase(F_C_24, attribute)) {
                    builder.computerNetworksLab1(CsvBooleanField.parseToBoolean(t1[i]));
                } else if (StringUtils.equalsIgnoreCase(F_C_25, attribute)) {
                    builder.computerNetworksLab3(CsvBooleanField.parseToBoolean(t1[i]));
                } else if (StringUtils.equalsIgnoreCase(F_C_26, attribute)) {
                    builder.telecommunicationsLab(CsvBooleanField.parseToBoolean(t1[i]));
                } else if (StringUtils.equalsIgnoreCase(F_C_27, attribute)) {
                    builder.masterClassroom(CsvBooleanField.parseToBoolean(t1[i]));
                } else if (StringUtils.equalsIgnoreCase(F_C_28, attribute)) {
                    builder.masterPlusClassroom(CsvBooleanField.parseToBoolean(t1[i]));
                } else if (StringUtils.equalsIgnoreCase(F_C_29, attribute)) {
                    builder.senRoom(CsvBooleanField.parseToBoolean(t1[i]));
                } else if (StringUtils.equalsIgnoreCase(F_C_30, attribute)) {
                    builder.evidenceRoom(CsvBooleanField.parseToBoolean(t1[i]));
                } else if (StringUtils.equalsIgnoreCase(F_C_31, attribute)) {
                    builder.meetingRoom(CsvBooleanField.parseToBoolean(t1[i]));
                } else if (StringUtils.equalsIgnoreCase(F_C_32, attribute)) {
                    builder.architectureRoom(CsvBooleanField.parseToBoolean(t1[i]));
                } else if (StringUtils.equalsIgnoreCase(F_C_33, attribute)) {
                    builder.normalClassroom(CsvBooleanField.parseToBoolean(t1[i]));
                } else if (StringUtils.equalsIgnoreCase(F_C_34, attribute)) {
                    builder.videoConference(CsvBooleanField.parseToBoolean(t1[i]));
                } else if (StringUtils.equalsIgnoreCase(F_C_35, attribute)) {
                    builder.atrium(CsvBooleanField.parseToBoolean(t1[i]));
                } else if (StringUtils.equalsIgnoreCase(F_C_36, attribute)) {
                    builder.otherFeatures(Arrays.stream(StringUtils.split(t1[i], ",")).toList());
                } else if (StringUtils.equalsIgnoreCase(F_C_OTHERS, attribute)) {
                    builder.otherFeatures(Arrays.stream(StringUtils.split(t1[i], ",")).toList());
                }
            }
        }

        return builder.build();
    }
}
