package com.ads.services;

import com.ads.manager.algorithms.FeatureAlgorithm;
import com.ads.manager.algorithms.FifoAlgorithm;
import com.ads.manager.criteria.*;
import com.ads.models.internal.ClassRoom;
import com.ads.models.internal.Timetable;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.ads.utils.constants.GeneralConst.*;

/**
 * JMA - 10/12/2021 00:09
 **/
@Service
@Log4j2
public class AlgorithmService {

    private final FifoAlgorithm fifoAlgorithm;
    private final FeatureAlgorithm featureAlgorithm;
    private final NSGAIIIService nsgaiiiService;
    private final NSGAIIService nsgaiiService;
    private final SMSEMOAService smsemoaService;
    private final SWRLAPIService swrlapiService;

    public AlgorithmService(NSGAIIIService nsgaiiiService, NSGAIIService nsgaiiService, SMSEMOAService smsemoaService, SWRLAPIService swrlapiService) {
        this.nsgaiiiService = nsgaiiiService;
        this.nsgaiiService = nsgaiiService;
        this.smsemoaService = smsemoaService;
        this.swrlapiService = swrlapiService;
        featureAlgorithm = new FeatureAlgorithm();
        fifoAlgorithm = new FifoAlgorithm();
    }

    public List<List<Timetable>> process(List<ClassRoom> classRoomList, List<Timetable> timetablesList, String[] qualities, boolean fast) {
        ArrayList<List<Timetable>> result = new ArrayList<>();
        List<String> qualitiesList = Arrays.stream(qualities).toList();
        // base algorithms, the most simple
        result.add(fifoAlgorithm.apply(classRoomList, timetablesList, qualitiesList));
        result.add(featureAlgorithm.apply(classRoomList, timetablesList, qualitiesList));
        // complex
        List<ClassRoom> classRooms = classRoomList.stream().filter(ClassRoom::isNotError).toList();
        List<Timetable> timetables = timetablesList.stream().filter(v -> v.isNotError() && StringUtils.isBlank(v.getClassRoom())).toList();
        // set execution complexity
        int maxGenerations = 1000;
        int populationSize = 50;
        if (!fast) {
            maxGenerations = 2000;
            populationSize = 100;
        }
        // get Objective functions
        List<Class<? extends Criteria>> qualitiesClass = getQualities(qualities);
        List<String> algorithmList = swrlapiService.algorithm(timetables.size());
        /*for (String alg : algorithmList) {
            switch ():
            case:
        }*/
//        return this.process(classRooms, timetables, maxGenerations, populationSize, List.of(ConflictCriteria.class));
        return result;
    }

    private List<Class<? extends Criteria>> getQualities(String[] qualities) {
        ArrayList<Class<? extends Criteria>> result = new ArrayList<>();
        for (String quality : qualities) {
            if (StringUtils.equalsIgnoreCase(CRITERIA_1, quality)) {
                result.add(ClassRoomSizeCriteria.class);
            } else if (StringUtils.equalsIgnoreCase(CRITERIA_2, quality)) {
                result.add(ConflictCriteria.class);
            } else if (StringUtils.equalsIgnoreCase(CRITERIA_3, quality)) {
                // not implemented yet
            } else if (StringUtils.equalsIgnoreCase(CRITERIA_4, quality)) {
                // not implemented yet
            } else if (StringUtils.equalsIgnoreCase(CRITERIA_5, quality)) {
                result.add(AllocationCriteria.class);
            } else if (StringUtils.equalsIgnoreCase(CRITERIA_6, quality)) {
                result.add(FeatureCriteria.class);
            }
        }
        if (result.isEmpty()) {
            result.add(AllocationCriteria.class);
        }
        return result;
    }
}
