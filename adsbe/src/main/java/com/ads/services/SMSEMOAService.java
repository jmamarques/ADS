package com.ads.services;

import com.ads.models.ClassRoom;
import com.ads.models.Timetable;
import com.ads.utils.CaseUtil;
import com.ads.utils.algorithms.TimetableProblem;
import com.ads.utils.criteria.ConflictCriteria;
import com.ads.utils.criteria.Criteria;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.uma.jmetal.algorithm.multiobjective.smsemoa.SMSEMOA;
import org.uma.jmetal.algorithm.multiobjective.smsemoa.SMSEMOABuilder;
import org.uma.jmetal.example.AlgorithmRunner;
import org.uma.jmetal.operator.crossover.impl.IntegerSBXCrossover;
import org.uma.jmetal.operator.mutation.impl.IntegerPolynomialMutation;
import org.uma.jmetal.solution.integersolution.impl.DefaultIntegerSolution;

import java.util.List;
import java.util.Random;

/**
 * JMA - 27/11/2021 20:15
 * SMSEMOA Algorithm
 **/
@Service
@Log4j2
public class SMSEMOAService {

    public static void main(String[] args) {
        List<DefaultIntegerSolution> process = new SMSEMOAService().processTest();
        log.info(process);
    }

    public List<DefaultIntegerSolution> processTest() {
        List<ClassRoom> classRooms = CaseUtil.getClassRooms();
        List<Timetable> timetables = CaseUtil.getTimetables();

        int maxGenerations = 1000;//25000;
        int populationSize = 100;
        return this.process(classRooms, timetables, maxGenerations, populationSize, List.of(ConflictCriteria.class));
    }

    /**
     * Provide solutions based on parameters
     *
     * @param classRooms     - classrooms
     * @param timetables     - timetables to assign classrooms
     * @param maxGenerations - parameter max generations
     * @param populationSize - population size for genetic algorithm
     * @param objectives     - objectives of algorithm
     * @return List of Solution after each run
     */
    public List<DefaultIntegerSolution> process(List<ClassRoom> classRooms, List<Timetable> timetables, int maxGenerations, int populationSize, List<Class<? extends Criteria>> objectives) {
        log.info("Started " + getClass().getName() + " Algorithm");
        SMSEMOABuilder integerSolutionSMSEMOABuilder = new SMSEMOABuilder<>(
                new TimetableProblem(timetables.size(), classRooms, timetables, objectives),
                new IntegerSBXCrossover(new Random().nextDouble(0, 1), 2.0),
                new IntegerPolynomialMutation(new Random().nextDouble(0, 1), 20.0)
        );
        SMSEMOA algorithm = integerSolutionSMSEMOABuilder
                .setPopulationSize(populationSize)
                .setMaxEvaluations(maxGenerations)
                .build();

        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute();

        List<DefaultIntegerSolution> population = algorithm.getResult();
        long computingTime = algorithmRunner.getComputingTime();

        log.info("Total execution time: " + computingTime + "ms");

        return population;
    }
}