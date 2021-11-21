package com.ads.utils.algorithms;

import com.ads.models.ClassRoom;
import com.ads.models.Reservation;
import com.ads.models.Timetable;
import com.ads.utils.converter.TimeUtils;
import com.ads.utils.mapper.TimetableMapper;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import java.util.List;

/**
 * JMA - 21/11/2021 09:18
 * Algorithm - doesn't matter the qualities, only apply the rule first in first out
 **/
public class FifoAlgorithm implements Algorithm {

    @Override
    public List<Timetable> apply(List<ClassRoom> classRoomList, List<Timetable> timetableList, List<String> qualities) {
        ArrayListValuedHashMap<ClassRoom, Reservation> occupation = new ArrayListValuedHashMap<>();
        // clone all list
        List<Timetable> timetables = TimetableMapper.toTimetableList(timetableList);
        // iterate and change the classroom
        timetables.stream().forEach(timetable -> {
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
