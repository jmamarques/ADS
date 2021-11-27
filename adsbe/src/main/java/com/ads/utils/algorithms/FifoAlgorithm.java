package com.ads.utils.algorithms;

import com.ads.models.ClassRoom;
import com.ads.models.Reservation;
import com.ads.models.Timetable;
import com.ads.utils.converter.TimeUtils;
import com.ads.utils.mapper.TimetableMapper;
import lombok.NonNull;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * JMA - 21/11/2021 09:18
 * Algorithm - doesn't matter the qualities, only apply the rule first in first out
 **/
public class FifoAlgorithm implements Algorithm {

    /**
     * keeps track of all the classroomlist in a list.
     * For each room verify the algoritm isValidClass, in case of being a valid class makes the reservation.
     * Hereafter checks if  the room, reservation and occupation is available.
     * IF yes, it will save in the timetable the room.
     *
     * @param classRoomList
     * @param timetableList
     * @param qualities
     * @return list of timetable
     * @throws java.lang.Exception
     */
    @Override
    public List<Timetable> apply(@NonNull List<ClassRoom> classRoomList, @NonNull List<Timetable> timetableList, @NonNull List<String> qualities) {
        Map<String, ClassRoom> classRoomMap = classRoomList.stream().collect(Collectors.toMap(ClassRoom::getRoomName, Function.identity()));
        ArrayListValuedHashMap<ClassRoom, Reservation> occupation = new ArrayListValuedHashMap<>();
        // clone all list
        List<Timetable> timetables = TimetableMapper.toTimetableList(timetableList);
        //populate initial occupation Map
        timetables.stream()
                .filter(timetable -> StringUtils.isNotBlank(timetable.getClassRoom()))
                .forEach(timetable -> {
                    if (classRoomMap.containsKey(timetable.getClassRoom())) {
                        Reservation reservation = Reservation.builder()
                                .begin(TimeUtils.convertToLocalDateTime(timetable.getDay(), timetable.getBegin()))
                                .end(TimeUtils.convertToLocalDateTime(timetable.getDay(), timetable.getEnd()))
                                .build();
                        ClassRoom room = classRoomMap.get(timetable.getClassRoom());
                        occupation.put(room, reservation);
                    }
                });
        // iterate and change the classroom
        timetables.stream()
                .filter(timetable -> !timetable.isHasError() && StringUtils.isBlank(timetable.getClassRoom()))
                .forEach(timetable -> {
                    try {

                        for (ClassRoom room : classRoomList) {
                            if (AlgorithmUtil.isValidClass(timetable, room)) {
                                Reservation reservation = Reservation.builder()
                                        .begin(TimeUtils.convertToLocalDateTime(timetable.getDay(), timetable.getBegin()))
                                        .end(TimeUtils.convertToLocalDateTime(timetable.getDay(), timetable.getEnd()))
                                        .build();
                                if (AlgorithmUtil.isAvailable(room, reservation, occupation)) {
                                    occupation.put(room, reservation);
                                    timetable.setClassRoom(room.getRoomName());
                                    break;
                                }
                            }
                        }
                    } catch (Exception e) {
                        timetable.setHasError(true);
                        timetable.setError("During Execution of " + FifoAlgorithm.class.getName() + " has got an error. Exception: " + e.getMessage());
                    }
                });
        return timetables;
    }
}
