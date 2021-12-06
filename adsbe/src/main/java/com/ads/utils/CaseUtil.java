package com.ads.utils;

import com.ads.models.ClassRoom;
import com.ads.models.Timetable;
import com.ads.utils.validators.TimetableValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JMA - 27/11/2021 10:42
 **/
@Log4j2
public class CaseUtil {
    /**
     * returns the classrooms
     * @return arraylist
     */
    public static List<ClassRoom> getClassRooms() {
        ObjectMapper obj = new ObjectMapper();
        obj.registerModule(new JavaTimeModule());
        try {
            InputStream inputStream = new ClassPathResource("static/classroom.json").getInputStream();
//            return obj.readValue(inputStream,new TypeReference<List<ClassRoom>>(){});
            List<ClassRoom> classRooms = obj.readerForListOf(ClassRoom.class).readValue(inputStream);
            return TimetableValidator.validationClassRooms(classRooms).stream().filter(ClassRoom::isNotError).collect(Collectors.toList());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return new ArrayList<>();
    }

    /**
     * returns the timetables
     * @return arraylist
     */
    public static List<Timetable> getTimetables() {
        ObjectMapper obj = new ObjectMapper();
        obj.registerModule(new JavaTimeModule());
        try {
            InputStream inputStream = new ClassPathResource("static/timetable.json").getInputStream();
            List<Timetable> timetables = obj.readerForListOf(Timetable.class).readValue(inputStream);
            return TimetableValidator.validationTimetables(timetables).stream().filter(Timetable::isNotError).collect(Collectors.toList());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return new ArrayList<>();
    }

    public static void main(String[] args) {
        List<ClassRoom> classRooms = CaseUtil.getClassRooms();
        List<Timetable> timetables = CaseUtil.getTimetables();
        classRooms.size();
    }

}
