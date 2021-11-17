package com.ads.utils.mapper;

import com.ads.dto.ClassDTO;
import com.ads.models.ClassRoom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * JMA - 07/11/2021 19:17
 **/
class TimetableMapperTest {

    @Test
    void toTimetable() {
        ClassDTO roomTest = ClassDTO.builder().roomName("room test").examCapacity(99).arq1(true).atrium(true).build();
        ClassRoom timetable = ClassRoomMapper.toTimetable(roomTest);
        Assertions.assertNotNull(timetable.getFeatures());
        Assertions.assertIterableEquals(Arrays.asList("arq1", "atrium"), timetable.getFeatures());
        Assertions.assertEquals(roomTest.getRoomName(), timetable.getRoomName());
        Assertions.assertEquals(roomTest.getExamCapacity(), timetable.getExamCapacity());
    }
}
