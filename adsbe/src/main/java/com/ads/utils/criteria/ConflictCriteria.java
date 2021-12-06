package com.ads.utils.criteria;

import com.ads.models.ClassRoom;
import com.ads.models.Reservation;
import com.ads.models.Timetable;
import com.ads.utils.algorithms.AlgorithmUtil;
import com.ads.utils.converter.TimeUtils;
import lombok.NonNull;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.ads.utils.algorithms.AlgorithmUtil.populateOccupation;

/**
 * JMA - 27/11/2021 08:54
 * Multiple timetables for the same class in the same period
 **/
public class ConflictCriteria implements Criteria {

    /**
     * @param classRoomList
     * @param timetableList
     * @param solution
     * @return
     */
    @Override
    public double applyCriteria(@NonNull List<ClassRoom> classRoomList, @NonNull List<Timetable> timetableList, List<Integer> solution) {
        double result = 0.0;
        ArrayListValuedHashMap<ClassRoom, Reservation> occupation = new ArrayListValuedHashMap<>();
        //populate initial occupation Map
        Map<String, ClassRoom> classRoomMap = classRoomList.stream().collect(Collectors.toMap(ClassRoom::getRoomName, Function.identity()));
        populateOccupation(timetableList, occupation, classRoomMap);
        // process solution
        for (int i = 0; i < solution.size(); i++) {
            int classRoomIndex = solution.get(i);
            Timetable timetable = timetableList.get(i);
            ClassRoom classRoom = classRoomList.get(classRoomIndex);
            // we already have this occupation registered
            if (StringUtils.isNotBlank(timetable.getClassRoom())) {
                continue;
            }
            Reservation reservation = Reservation.builder()
                    .begin(TimeUtils.convertToLocalDateTime(timetable.getDay(), timetable.getBegin()))
                    .end(TimeUtils.convertToLocalDateTime(timetable.getDay(), timetable.getEnd()))
                    .build();
            // conflict - multiple timetables for the same class in the same period
            if (AlgorithmUtil.isAvailable(classRoom, reservation, occupation)) {
                occupation.put(classRoom, reservation);
            } else {
                result += 1000;
            }
        }
        return result;
    }


}
