package com.ads.models;

import com.ads.utils.enums.DayOfWeek;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

/**
 * class that characterizes a timetable
 * Objects like : course, unit, classnumber, dayof week, capacity etc.
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Timetable {
    private String course;
    private String unit;
    private String shift;
    private String classNumber;
    private int registeredShift;
    private boolean overflowShift;
    private boolean overflowEnrollment;
    private DayOfWeek dayOfWeek;
    private LocalTime begin;
    private LocalTime end;
    private Date day;
    private String features;
    private String classRoom;
    private int capacity;
    private List<String> realFeatures;
    private boolean hasError;
    private String error;

    /**
     * returns if is an error
     * @return boolean
     */
    public boolean isNotError() {
        return !hasError;
    }
}
