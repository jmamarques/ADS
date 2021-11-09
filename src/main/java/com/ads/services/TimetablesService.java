package com.ads.services;

import com.ads.dto.ClassDTO;
import com.ads.models.Timetable;
import com.ads.utils.mapper.TimetableMapper;
import com.ads.utils.validators.TimetableValidator;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * JMA - 07/11/2021 17:06
 **/
@Service
public class TimetablesService {

    public void processJsonTimetable(List<ClassDTO> rooms) {
        // validate data received
        boolean validClassDTO = TimetableValidator.isValidClassDTO(rooms);
        if (validClassDTO) {
            // Convert internally to a specific structure
            List<Timetable> timetables = TimetableMapper.toTimetable(rooms);
        }

    }
}
