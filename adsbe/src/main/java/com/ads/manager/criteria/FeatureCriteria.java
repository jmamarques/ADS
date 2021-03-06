package com.ads.manager.criteria;

import com.ads.models.internal.ClassRoom;
import com.ads.models.internal.Reservation;
import com.ads.models.internal.Timetable;
import com.ads.utils.mapper.TimetableMapper;
import lombok.NonNull;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * JMA - 27/11/2021 09:36
 * Class that feature the criteria applies if it needs of classroom
 **/
public class FeatureCriteria implements Criteria {
    @Override
    public double applyCriteria(@NonNull List<ClassRoom> classRoomList, @NonNull List<Timetable> timetableList, List<Integer> solution, ArrayListValuedHashMap<ClassRoom, Reservation> basedOccupation) {
        double result = 0;
        List<Timetable> timetables = TimetableMapper.toTimetableList(timetableList);
        //populate initial occupation Map
        // calculation
        for (int i = 0; i < solution.size(); i++) {
            int classRoomIndex = solution.get(i);
            ClassRoom classRoom = classRoomList.get(classRoomIndex);
            Timetable timetable = timetables.get(i);
            if (StringUtils.equalsIgnoreCase("Não necessita de sala", timetable.getFeatures())) {
                if (classRoom != null) {
                    result += 100;
                }
            } else if (StringUtils.isBlank(timetable.getFeatures())) {
                result += 0;
            } else if (classRoom.getFeatures() != null && !classRoom.getFeatures().contains(timetable.getFeatures())) {
                result += 100;

            }
        }
        return result;
    }
}
