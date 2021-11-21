package com.ads.utils.algorithms;

import com.ads.models.ClassRoom;
import com.ads.models.Reservation;
import com.ads.models.Timetable;
import lombok.NonNull;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * JMA - 21/11/2021 09:38
 * Class that helps to apply algorithms
 **/
public class AlgorithmUtil {

    /**
     * Check if the classroom is valid for the timetable
     *
     * @param timetable timetable
     * @param classRoom classroom
     * @return True if meets criteria (size and feature) otherwise False
     */
    public static boolean isValidClass(@NonNull Timetable timetable, @NonNull ClassRoom classRoom) {
        boolean isValid = false;
        // you must have at least one feature
        if (classRoom.getFeatures() != null) {
            isValid = classRoom.getFeatures()
                    .stream()
                    .anyMatch(feature ->
                            StringUtils.equalsIgnoreCase(timetable.getFeatures(), feature) ||
                                    timetable.getRealFeatures() != null && timetable.getRealFeatures()
                                            .stream().anyMatch(f -> StringUtils.equalsIgnoreCase(f, feature)));
        }
        // size should be respected
        if (timetable.getCapacity() > classRoom.getNormalCapacity()) {
            isValid = false;
        }
        return isValid;
    }

    /**
     * Check if the reservation is not between the start and end dates of all reservations present in the occupation object
     *
     * @param classRoom  - class
     * @param book       - reservation
     * @param occupation - occupation
     * @return True if it is available otherwise False
     */
    public static boolean isAvailable(ClassRoom classRoom, Reservation book, ArrayListValuedHashMap<ClassRoom, Reservation> occupation) {
        List<Reservation> reservations = occupation.get(classRoom);
        if (reservations.isEmpty()) {
            return true;
        }
        // reservation is not between the start and end dates of all reservations present in the occupancy
        return reservations.stream().noneMatch(r -> !(book.getBegin().isBefore(r.getBegin()) && book.getEnd().isBefore(r.getBegin()) ||
                book.getBegin().isAfter(r.getEnd()) && book.getEnd().isAfter(r.getEnd()))
        );

    }
}
