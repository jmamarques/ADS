package com.ads.utils.criteria;

import com.ads.models.ClassRoom;
import com.ads.models.Timetable;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * JMA - 27/11/2021 18:44
 **/
public class ClassRoomSizeCriteria implements Criteria {
    @Override
    public double applyCriteria(@NonNull List<ClassRoom> classRoomList, @NonNull List<Timetable> timetableList, List<Integer> solution) {
        double result = 0.0;
        // process solution
        for (int i = 0; i < solution.size(); i++) {
            int classRoomIndex = solution.get(i);
            Timetable timetable = timetableList.get(i);
            ClassRoom classRoom = classRoomList.get(classRoomIndex);
            // we already have this occupation registered
            if (StringUtils.isNotBlank(timetable.getClassRoom())) {
                continue;
            }
            // give more punctuation when the number of persons is higher or lower
            result += Math.abs(classRoom.getNormalCapacity() - timetable.getCapacity()) * 10;
        }
        return result;
    }
}
