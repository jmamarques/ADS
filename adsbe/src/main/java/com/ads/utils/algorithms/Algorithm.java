package com.ads.utils.algorithms;

import com.ads.models.ClassRoom;
import com.ads.models.Timetable;
import lombok.NonNull;

import java.util.List;

/**
 * JMA - 21/11/2021 09:14
 **/
public interface Algorithm {
    /**
     * Apply algorithm to get a Timetable with class filled
     *
     * @param classRoomList - class available
     * @param timetableList - timetables to fill
     * @param qualities     - metrics of quality
     * @return list of timetable with classroom assign
     */
    List<Timetable> apply(@NonNull List<ClassRoom> classRoomList, @NonNull List<Timetable> timetableList, @NonNull List<String> qualities);
}
