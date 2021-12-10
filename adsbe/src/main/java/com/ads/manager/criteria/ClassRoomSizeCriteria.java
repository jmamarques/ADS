package com.ads.manager.criteria;

import com.ads.models.internal.ClassRoom;
import com.ads.models.internal.Reservation;
import com.ads.models.internal.Timetable;
import lombok.NonNull;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * class based in the criteria classroom size
 **/
public class ClassRoomSizeCriteria implements Criteria {
    /**
     * objective : apply the criteria classroom size based on the classroom list, timetable, solution
     * First, process the solution and for each solution get the classroom and the timetable associated to the solution.
     * Inside of the loop for verify if the occupation is registered
     * Give pontuaction in case of the persons is higher or lower
     * If the capacity > 0 we must multiply by 10
     * else just return the value
     * @param classRoomList
     * @param timetableList
     * @param solution
     * @return integer of result
     */
    @Override
    public double applyCriteria(@NonNull List<ClassRoom> classRoomList, @NonNull List<Timetable> timetableList, List<Integer> solution, ArrayListValuedHashMap<ClassRoom, Reservation> basedOccupation) {
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
            int capacity = classRoom.getNormalCapacity() - timetable.getCapacity();
            if (capacity > 0) {
                result += Math.abs(capacity) * 10;
            } else {
                result += Math.abs(capacity);
            }
        }
        return result;
    }
}
