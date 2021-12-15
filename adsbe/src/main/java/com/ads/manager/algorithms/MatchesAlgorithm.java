package com.ads.manager.algorithms;

import com.ads.models.internal.ClassRoom;
import com.ads.models.internal.Reservation;
import com.ads.models.internal.Timetable;
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
 * Matches Algorithm - Algorithm - apply the rule first in first out without validation
 **/
public class MatchesAlgorithm implements Algorithm {

    /**
     * keeps track of all the classroomlist in a list.
     * For each room verify the algoritm isValidClass, in case of being a valid class makes the reservation.
     * Hereafter checks if  the room, reservation and occupation is available.
     * IF yes, it will save in the timetable the room.
     *
     * @param classRoomList - classroom list
     * @param timetableList - timetable list
     * @param qualities     - qualities
     * @return list of timetable
     */
    @Override
    public List<Timetable> apply(@NonNull List<ClassRoom> classRoomList, @NonNull List<Timetable> timetableList, @NonNull List<String> qualities) {
        ArrayListValuedHashMap<ClassRoom, Reservation> occupation = new ArrayListValuedHashMap<>();
        Map<String, ClassRoom> classRoomMap = classRoomList.stream().collect(Collectors.toMap(ClassRoom::getRoomName, Function.identity()));
        // clone all list
        List<Timetable> timetables = TimetableMapper.toTimetableList(timetableList);
        //populate initial occupation Map
        AlgorithmUtil.populateOccupation(timetables, occupation, classRoomMap);
        // iterate and change the classroom
        timetables.stream()
                .filter(timetable -> !timetable.isHasError() && StringUtils.isBlank(timetable.getClassRoom()))
                .forEach(timetable -> {
                    try {
                        if (!StringUtils.equalsIgnoreCase("Não necessita de sala", timetable.getFeatures())) {
                            for (ClassRoom room : classRoomList) {
                                Reservation reservation = Reservation.builder()
                                        .end(TimeUtils.convertToLocalDateTime(timetable.getDay(), timetable.getEnd()))
                                        .begin(TimeUtils.convertToLocalDateTime(timetable.getDay(), timetable.getBegin()))
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
                        timetable.setError("During Execution of " + MatchesAlgorithm.class.getName() + " has got an error. Exception: " + e.getMessage());
                    }
                });
        return timetables;
    }
}
