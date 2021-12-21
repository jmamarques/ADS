package com.ads.services;

import com.ads.manager.algorithms.AlgorithmUtil;
import com.ads.manager.algorithms.FeatureAlgorithm;
import com.ads.manager.algorithms.FifoAlgorithm;
import com.ads.manager.algorithms.MatchesAlgorithm;
import com.ads.manager.criteria.*;
import com.ads.models.dto.RequestDTO;
import com.ads.models.internal.ClassRoom;
import com.ads.models.internal.Reservation;
import com.ads.models.internal.Timetable;
import com.ads.utils.converter.TimeUtils;
import com.ads.utils.exceptions.InvalidAlgorithmException;
import com.ads.utils.mapper.TimetableMapper;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.uma.jmetal.solution.integersolution.impl.DefaultIntegerSolution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.ads.utils.constants.GeneralConst.*;

/**
 * JMA - 10/12/2021 00:09
 **/
@Service
@Log4j2
public class AlgorithmService {

    public static final int MAX_FILES_FROM_ALGORITHMS = 3;
    private final FifoAlgorithm fifoAlgorithm;
    private final MatchesAlgorithm MatchesAlgorithm;
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
        MatchesAlgorithm = new MatchesAlgorithm();
    }

    /**
     * Process everything to get new timetables
     * the list of timetable already have some classes filled if the user desire
     *
     * @param classRoomList  - list of classrooms
     * @param timetablesList - list of timetables
     * @param qualities      - qualities
     * @param requestDTO     - algorithm configurations
     * @return list of results with timetables
     */
    public List<List<Timetable>> process(List<ClassRoom> classRoomList, List<Timetable> timetablesList, String[] qualities, @NonNull RequestDTO requestDTO) {
        ArrayList<List<Timetable>> result = new ArrayList<>();
        List<String> qualitiesList = Arrays.stream(qualities).collect(Collectors.toList());
        log.info("Run basic algorithms");
        // base algorithms, the most simple
        result.add(MatchesAlgorithm.apply(classRoomList, timetablesList, qualitiesList));
        result.add(fifoAlgorithm.apply(classRoomList, timetablesList, qualitiesList));
        result.add(featureAlgorithm.apply(classRoomList, timetablesList, qualitiesList));
        log.info("Prepare variables auxiliaries");
        // complex
        List<ClassRoom> classRooms = classRoomList.stream().filter(ClassRoom::isNotError).collect(Collectors.toList());
        List<Timetable> timetables = timetablesList.stream().filter(v -> v.isNotError() && StringUtils.isBlank(v.getClassRoom())).collect(Collectors.toList());
        List<Timetable> timetablesFilled = timetablesList.stream().filter(v -> !(v.isNotError() && StringUtils.isBlank(v.getClassRoom()))).collect(Collectors.toList());
        // occupation
        log.info("Prepare Occupation");
        ArrayListValuedHashMap<ClassRoom, Reservation> occupation = new ArrayListValuedHashMap<>();
        AlgorithmUtil.populateOccupation(
                timetablesList.stream().filter(v -> v.isNotError() && StringUtils.isNotBlank(v.getClassRoom())).collect(Collectors.toList()),
                occupation,
                classRooms.stream().collect(Collectors.toMap(ClassRoom::getRoomName, Function.identity()))
        );
        // set execution complexity
        int maxGenerations = requestDTO.getMaxGenerations();
        int populationSize = requestDTO.getPopulationSize();

        // get Objective functions
        List<Class<? extends Criteria>> qualitiesClass = getQualities(qualities);
        log.info("Query to database to search best algorithm");
        List<String> algorithmList = swrlapiService.algorithm(timetables.size());
        log.info("run algorithms:" + algorithmList.toString());
        for (String alg : algorithmList) {
            try {
                switch (alg) {
                    case ":nsgaiii" -> result.addAll(convertSolution(nsgaiiService.process(classRooms, timetables, maxGenerations, populationSize, qualitiesClass, occupation), classRooms, timetables, occupation, timetablesFilled));
                    case ":nsgaii" -> result.addAll(convertSolution(nsgaiiiService.process(classRooms, timetables, maxGenerations, populationSize, qualitiesClass, occupation), classRooms, timetables, occupation, timetablesFilled));
                    case ":smsemoa" -> result.addAll(convertSolution(smsemoaService.process(classRooms, timetables, maxGenerations, populationSize, qualitiesClass, occupation), classRooms, timetables, occupation, timetablesFilled));
                }
            } catch (Throwable e) {
                throw new InvalidAlgorithmException("Algorithm " + alg + " with error", e);
            }
        }
        log.info("Return " + result.size() + " timetables");
        return result;
    }

    /**
     * Convert solution into List of timetables
     *
     * @param solutions  - solution with all timetables assigned
     * @param classRooms - classroom
     * @param timetables - timetables
     * @param occupation - current occupation
     * @return list of timetables
     */
    private List<List<Timetable>> convertSolution(List<DefaultIntegerSolution> solutions, List<ClassRoom> classRooms, List<Timetable> timetables, ArrayListValuedHashMap<ClassRoom, Reservation> occupation, List<Timetable> timetablesFilled) {
        ArrayList<List<Timetable>> result = new ArrayList<>();
        for (int index = 0; index < Math.min(solutions.size(), MAX_FILES_FROM_ALGORITHMS); index++) {
            DefaultIntegerSolution solution = solutions.get(index);
            try {
                List<Timetable> timetablesCloned = TimetableMapper.toTimetableWithoutClassList(timetables);
                ArrayListValuedHashMap<ClassRoom, Reservation> occupationCloned = new ArrayListValuedHashMap<>(occupation);
                for (int i = 0; i < solution.variables().size(); i++) {
                    int classRoomIndex = solution.variables().get(i);
                    Timetable timetable = timetablesCloned.get(i);
                    ClassRoom classRoom = classRooms.get(classRoomIndex);
                    Reservation reservation = Reservation.builder()
                            .begin(TimeUtils.convertToLocalDateTime(timetable.getDay(), timetable.getBegin()))
                            .end(TimeUtils.convertToLocalDateTime(timetable.getDay(), timetable.getEnd()))
                            .build();
                    // ignore cases with conflict
                    if (AlgorithmUtil.isAvailable(classRoom, reservation, occupationCloned)) {
                        occupation.put(classRoom, reservation);
                        timetable.setClassRoom(classRoom.getRoomName());
                    }
                }
                timetablesCloned.addAll(timetablesFilled);
                result.add(timetablesCloned);
            } catch (Throwable e) {
                log.error(e);
            }
        }
        return result;
    }

    /**
     * Utility to get Qualities based on Strings
     *
     * @param qualities - text representation of qualities
     * @return List of class qualities
     */
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
