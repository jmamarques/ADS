package com.ads.utils.algorithms;

import com.ads.models.ClassRoom;
import com.ads.models.Reservation;
import com.ads.models.Timetable;
import com.ads.utils.converter.TimeUtils;
import com.ads.utils.mapper.TimetableMapper;
import lombok.NonNull;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * JMA - 21/11/2021 21:36
 **/
public class FeatureAlgorithm implements Algorithm {

    /**
     * Returns the list of timetables according to the occupation and take into account the space for specified class
     * @param classRoomList
     * @param timetableList
     * @param qualities
     * @return list of timetables
     */
    @Override
    public List<Timetable> apply(@NonNull List<ClassRoom> classRoomList, @NonNull List<Timetable> timetableList, @NonNull List<String> qualities) {
        ArrayListValuedHashMap<ClassRoom, Reservation> occupation = new ArrayListValuedHashMap<>();
        // clone all list
        List<Timetable> timetables = TimetableMapper.toTimetableList(timetableList);
        ArrayListValuedHashMap<String, ClassRoom> classRoomByFeature = new ArrayListValuedHashMap<>();
        // organize classRoom by feature
        classRoomList.forEach(classRoom -> {
            if (classRoom.getFeatures() != null) {
                classRoom.getFeatures().forEach(feature -> classRoomByFeature.put(feature, classRoom));
            }
        });// iterate and change the classroom
        timetables.stream().forEach(timetable -> {
            try {
                // defaults features
                if (!StringUtils.equalsIgnoreCase("Não necessita de sala", timetable.getFeatures())) {
                    // empty Feature
                    boolean isModified = StringUtils.isBlank(timetable.getFeatures()) && StringUtils.isBlank(timetable.getClassRoom());
                    if (isModified) {
                        timetable.setFeatures("Sala de Aulas normal");
                    }
                    Reservation reservation = Reservation.builder()
                            .begin(TimeUtils.convertToLocalDateTime(timetable.getDay(), timetable.getBegin()))
                            .end(TimeUtils.convertToLocalDateTime(timetable.getDay(), timetable.getEnd()))
                            .build();
                    // occupation
                    occupation(occupation, classRoomByFeature, timetable, reservation);

                    if (isModified) {
                        timetable.setFeatures("");
                    }
                }
            } catch (Exception e) {
                timetable.setHasError(true);
                timetable.setError("During Execution of " + FifoAlgorithm.class.getName() + " has got an error. Exception: " + e.getMessage());
            }
        });
        // Try recover missing timetables for lack of space // add 25% extra space
        timetables
                .stream()
                .filter(timetable -> !StringUtils.equalsIgnoreCase("Não necessita de sala", timetable.getFeatures()) && StringUtils.isBlank(timetable.getClassRoom()) && !timetable.isHasError())
                .forEach(timetable -> {
                    try {
                        Reservation reservation = Reservation.builder()
                                .begin(TimeUtils.convertToLocalDateTime(timetable.getDay(), timetable.getBegin()))
                                .end(TimeUtils.convertToLocalDateTime(timetable.getDay(), timetable.getEnd()))
                                .build();
                        // empty Feature
                        boolean isModified = StringUtils.isBlank(timetable.getFeatures()) && StringUtils.isBlank(timetable.getClassRoom());
                        if (isModified) {
                            timetable.setFeatures("Sala de Aulas normal");
                        }
                        // occupation
                        classRoomByFeature.get(timetable.getFeatures())
                                .stream()
                                .filter(classRoom -> AlgorithmUtil.isValidClassTolerance(timetable, classRoom))
                                .map(classRoom -> Arrays.asList(classRoom.getNormalCapacity(), classRoom))
                                .sorted(Comparator.comparingInt(o -> (int) o.get(0)))
                                .map(objects -> (ClassRoom) objects.get(1))
                                .filter(classRoom -> AlgorithmUtil.isAvailable(classRoom, reservation, occupation))
                                .findFirst()
                                .ifPresent(classRoom -> {
                                    occupation.put(classRoom, reservation);
                                    timetable.setClassRoom(classRoom.getRoomName());
                                });
                        if (isModified) {
                            timetable.setFeatures("");
                        }
                    } catch (Exception e) {
                        timetable.setHasError(true);
                        timetable.setError("During Execution of " + FifoAlgorithm.class.getName() + " has got an error. Exception: " + e.getMessage());
                    }
                });
        return timetables;
    }

    /**
     * verify the occupation for a specified classroom
     * @param occupation
     * @param classRoomByFeature
     * @param timetable
     * @param reservation
     */
    private void occupation(ArrayListValuedHashMap<ClassRoom, Reservation> occupation, ArrayListValuedHashMap<String, ClassRoom> classRoomByFeature, Timetable timetable, Reservation reservation) {
        classRoomByFeature.get(timetable.getFeatures())
                .stream()
                .filter(classRoom -> AlgorithmUtil.isValidClass(timetable, classRoom))
                .map(classRoom -> Arrays.asList(classRoom.getNormalCapacity(), classRoom))
                .sorted(Comparator.comparingInt(o -> (int) o.get(0)))
                .map(objects -> (ClassRoom) objects.get(1))
                .filter(classRoom -> AlgorithmUtil.isAvailable(classRoom, reservation, occupation))
                .findFirst()
                .ifPresent(classRoom -> {
                    occupation.put(classRoom, reservation);
                    timetable.setClassRoom(classRoom.getRoomName());
                });
    }
}
