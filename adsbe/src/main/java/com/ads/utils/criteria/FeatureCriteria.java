package com.ads.utils.criteria;

import com.ads.models.ClassRoom;
import com.ads.models.Timetable;
import lombok.NonNull;

import java.util.List;

/**
 * JMA - 27/11/2021 09:36
 **/
public class FeatureCriteria implements Criteria {
    @Override
    public double applyCriteria(@NonNull List<ClassRoom> classRoomList, @NonNull List<Timetable> timetableList, List<Integer> solution) {
        return 0;
    }
}
