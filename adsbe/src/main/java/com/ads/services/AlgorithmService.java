package com.ads.services;

import com.ads.models.internal.ClassRoom;
import com.ads.models.internal.Timetable;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * JMA - 10/12/2021 00:09
 **/
@Service
@Log4j2
public class AlgorithmService {

    public List<List<Timetable>> process(List<ClassRoom> classRoomList, List<Timetable> timetablesList, String[] qualities, boolean fast) {
        return null;
    }
}
