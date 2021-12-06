package com.ads.services;

import com.ads.dto.ScheduleDTO;
import com.ads.models.ClassRoom;
import com.ads.utils.constants.GeneralConst;
import com.ads.utils.mapper.ClassRoomMapper;
import com.ads.utils.validators.TimetableValidator;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * JMA - 07/11/2021 17:06
 * class that process data json from timetable
 **/
@Service
public class TimetablesService {

    /**
     * process json timetable
     * @param scheduleDTO
     */
    public void processJsonTimetable(@NonNull ScheduleDTO scheduleDTO) {
        // validate data received
        boolean validClassDTO = TimetableValidator.isValidClassDTO(scheduleDTO.getClassDTOS());
        if (validClassDTO) {
            // Convert internally to a specific structure
            List<ClassRoom> classRooms = ClassRoomMapper.toClassRoom(scheduleDTO.getClassDTOS());
        }

    }

    /**
     * @return mapping of the class field
     */
    public String[] getClassRoomHeaders() {return GeneralConst.CLASS_FIELD_MAPPING;}

    /**
     * @return mapping of the class field
     */
    public String[] getTimetableHeaders() {
        return GeneralConst.TIMETABLE_FIELD_MAPPING;
    }
}
