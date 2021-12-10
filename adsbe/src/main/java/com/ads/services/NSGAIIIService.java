package com.ads.services;

import com.ads.manager.algorithms.TimetableProblem;
import com.ads.manager.criteria.Criteria;
import com.ads.models.internal.ClassRoom;
import com.ads.models.internal.Reservation;
import com.ads.models.internal.Timetable;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.springframework.stereotype.Service;
import org.uma.jmetal.algorithm.multiobjective.nsgaiii.NSGAIII;
import org.uma.jmetal.algorithm.multiobjective.nsgaiii.NSGAIIIBuilder;
import org.uma.jmetal.example.AlgorithmRunner;
import org.uma.jmetal.operator.crossover.impl.IntegerSBXCrossover;
import org.uma.jmetal.operator.mutation.impl.IntegerPolynomialMutation;
import org.uma.jmetal.operator.selection.impl.BestSolutionSelection;
import org.uma.jmetal.solution.integersolution.impl.DefaultIntegerSolution;

import java.util.List;
import java.util.Random;

/**
 * JMA - 27/11/2021 12:29
 * NSGAIII Algorithm
 **/
@Service
@Log4j2
public class NSGAIIIService {

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
    public List<DefaultIntegerSolution> process(List<ClassRoom> classRooms, List<Timetable> timetables, int maxGenerations, int populationSize, List<Class<? extends Criteria>> objectives, ArrayListValuedHashMap<ClassRoom, Reservation> occupation) {
        NSGAIII algorithm = new NSGAIIIBuilder(new TimetableProblem(timetables.size(), classRooms, timetables, objectives, occupation))
                .setCrossoverOperator(new IntegerSBXCrossover(new Random().nextDouble(0, 1), 2.0))
                .setMutationOperator(new IntegerPolynomialMutation(new Random().nextDouble(0, 1), 20.0))
                .setPopulationSize(populationSize)
                .setSelectionOperator(new BestSolutionSelection(
                        (o1, o2) -> Double.compare(((DefaultIntegerSolution) o1).objectives()[0], ((DefaultIntegerSolution) o2).objectives()[0])
                ))
                .setMaxIterations(maxGenerations).build();

        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute();

        List<DefaultIntegerSolution> population = algorithm.getResult();
        long computingTime = algorithmRunner.getComputingTime();

        log.info("Total execution time: " + computingTime + "ms");

        return population;
    }
}
