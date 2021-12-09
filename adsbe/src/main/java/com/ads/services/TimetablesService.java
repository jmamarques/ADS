package com.ads.services;

import com.ads.utils.constants.GeneralConst;
import org.springframework.stereotype.Service;

/**
 * JMA - 07/11/2021 17:06
 * class that process data json from timetable
 **/
@Service
public class TimetablesService {

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
