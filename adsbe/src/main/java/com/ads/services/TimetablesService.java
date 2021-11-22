package com.ads.services;

import com.ads.dto.ScheduleDTO;
import com.ads.models.ClassRoom;
import com.ads.utils.mapper.ClassRoomMapper;
import com.ads.utils.validators.TimetableValidator;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * JMA - 07/11/2021 17:06
 **/
@Service
public class TimetablesService {

    public void processJsonTimetable(@NonNull ScheduleDTO scheduleDTO) {
        // validate data received
        boolean validClassDTO = TimetableValidator.isValidClassDTO(scheduleDTO.getClassDTOS());
        if (validClassDTO) {
            // Convert internally to a specific structure
            List<ClassRoom> classRooms = ClassRoomMapper.toClassRoom(scheduleDTO.getClassDTOS());
        }

    }
}
