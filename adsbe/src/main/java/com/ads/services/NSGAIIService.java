package com.ads.services;

import com.ads.models.ClassRoom;
import com.ads.models.Timetable;
import com.ads.utils.CaseUtil;
import com.ads.utils.algorithms.TimetableProblem;
import com.ads.utils.criteria.ConflictCriteria;
import com.ads.utils.criteria.Criteria;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAII;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.example.AlgorithmRunner;
import org.uma.jmetal.operator.crossover.impl.IntegerSBXCrossover;
import org.uma.jmetal.operator.mutation.impl.IntegerPolynomialMutation;
import org.uma.jmetal.solution.integersolution.impl.DefaultIntegerSolution;

import java.util.List;
import java.util.Random;

/**
 * JMA - 27/11/2021 12:29
 **/
@Service
@Log4j2
public class NSGAIIService {

    public static void main(String[] args) {
        List<DefaultIntegerSolution> process = new NSGAIIService().processTest();
        log.info(process);
    }

    public List<DefaultIntegerSolution> processTest() {
        List<ClassRoom> classRooms = CaseUtil.getClassRooms();
        List<Timetable> timetables = CaseUtil.getTimetables();

        int maxGenerations = 1000;//25000;
        int populationSize = 100;
        return this.process(classRooms, timetables, maxGenerations, populationSize, List.of(ConflictCriteria.class));
    }

    public List<DefaultIntegerSolution> process(List<ClassRoom> classRooms, List<Timetable> timetables, int maxGenerations, int populationSize, List<Class<? extends Criteria>> objectives) {
        NSGAII algorithm = (new NSGAIIBuilder(new TimetableProblem(timetables.size(), classRooms, timetables, objectives),
                new IntegerSBXCrossover(new Random().nextDouble(0, 1), 2.0),
                new IntegerPolynomialMutation(new Random().nextDouble(0, 1), 20.0), populationSize))
                .setMaxEvaluations(maxGenerations)
                .build();

        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute();

        List<DefaultIntegerSolution> population = algorithm.getResult();
        long computingTime = algorithmRunner.getComputingTime();

        log.info("Total execution time: " + computingTime + "ms");

        return population;
    }
}
