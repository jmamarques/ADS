package com.ads.manager.algorithms;

import com.ads.models.internal.ClassRoom;
import com.ads.models.internal.Timetable;
import lombok.NonNull;

import java.util.List;

/**
 * JMA - 21/11/2021 09:14
 * Generic Interface for Algorithms
 **/
public interface Algorithm {
    /**
     * Apply algorithm to get a Timetable with class filled
     *
     * @param classRoomList - List of classroom available
     * @param timetableList - List of timetables to fill the classroom
     * @param qualities     - List of Textual representation of qualities
     * @return list of timetable with classroom assign
     */
    List<Timetable> apply(@NonNull List<ClassRoom> classRoomList, @NonNull List<Timetable> timetableList, @NonNull List<String> qualities);
}
