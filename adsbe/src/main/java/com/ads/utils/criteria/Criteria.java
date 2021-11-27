package com.ads.utils.criteria;

import com.ads.models.ClassRoom;
import com.ads.models.Timetable;
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
     * @param solution      - solution
     * @return evaluation of solution
     */
    double applyCriteria(@NonNull List<ClassRoom> classRoomList, @NonNull List<Timetable> solution);

}