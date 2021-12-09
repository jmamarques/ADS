package com.ads.manager.criteria;

import com.ads.models.internal.ClassRoom;
import com.ads.models.internal.Timetable;
import lombok.NonNull;

import java.util.List;

/**
 * JMA - 25/11/2021 21:56
 * Functional interface to Objective functions
 **/
public interface Criteria {

    /**
     * the smaller, the better it is
     *
     * @param classRoomList - classrooms
     * @param timetableList - current timetable
     * @param solution      - solution (index -> timetable, value-> classroom)
     * @return evaluation of solution
     */
    double applyCriteria(@NonNull List<ClassRoom> classRoomList, @NonNull List<Timetable> timetableList, List<Integer> solution);

}
