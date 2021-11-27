package com.ads;

import com.ads.models.ClassRoom;
import com.ads.models.Timetable;
import com.ads.utils.CaseUtil;
import com.ads.utils.algorithms.TimetableProblem;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAII;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.lab.experiment.Experiment;
import org.uma.jmetal.lab.experiment.ExperimentBuilder;
import org.uma.jmetal.lab.experiment.component.impl.ExecuteAlgorithms;
import org.uma.jmetal.lab.experiment.util.ExperimentAlgorithm;
import org.uma.jmetal.lab.experiment.util.ExperimentProblem;
import org.uma.jmetal.operator.crossover.impl.IntegerSBXCrossover;
import org.uma.jmetal.operator.mutation.impl.IntegerPolynomialMutation;
import org.uma.jmetal.qualityindicator.impl.*;
import org.uma.jmetal.qualityindicator.impl.hypervolume.impl.PISAHypervolume;
import org.uma.jmetal.solution.integersolution.IntegerSolution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * JMA - 25/11/2021 19:28
 * https://github.com/inaciomdrs/minicursoProgramacaoBioinspiradaJMetal/blob/master/src/br/ufrn/aplicacao/Aplicacao.java
 **/
public class TestNSGAII {

    public static final int INDEPENDENT_RUNS = 2;
    public static final int MAX_EVALUATIONS = 1000;

    public static void main(String[] args) {
        String experimentBaseDirectory = "adsbe";
        List<ExperimentProblem<IntegerSolution>> problemList = new ArrayList();
        List<ClassRoom> classRooms = CaseUtil.getClassRooms();
        List<Timetable> timetables = CaseUtil.getTimetables();
        problemList.add(new ExperimentProblem(new TimetableProblem(timetables.size(), classRooms, timetables)));

        List<ExperimentAlgorithm<IntegerSolution, List<IntegerSolution>>> algorithmList = configureAlgorithmList(problemList);
        Experiment<IntegerSolution, List<IntegerSolution>> experiment = (
                new ExperimentBuilder("NSGAIIStudy"))
                .setAlgorithmList(algorithmList).setProblemList(problemList)
                .setExperimentBaseDirectory(experimentBaseDirectory)
                .setOutputParetoFrontFileName("FUN")
                .setOutputParetoSetFileName("VAR")
                .setReferenceFrontDirectory("resourcesreferenceFrontsCSV")
                .setIndicatorList(Arrays.asList(new Epsilon(), new Spread(), new GenerationalDistance(), new PISAHypervolume(), new NormalizedHypervolume(), new InvertedGenerationalDistance(), new InvertedGenerationalDistancePlus()))
                .setIndependentRuns(INDEPENDENT_RUNS).setNumberOfCores(16).build();
        (new ExecuteAlgorithms(experiment)).run();
    }

    static List<ExperimentAlgorithm<IntegerSolution, List<IntegerSolution>>> configureAlgorithmList(List<ExperimentProblem<IntegerSolution>> problemList) {
        List<ExperimentAlgorithm<IntegerSolution, List<IntegerSolution>>> algorithms = new ArrayList();

        for (int run = 0; run < INDEPENDENT_RUNS; ++run) {
            int i;
            NSGAII algorithm;
            for (i = 0; i < problemList.size(); ++i) {
                algorithm = (
                        new NSGAIIBuilder(((ExperimentProblem) problemList.get(i)).getProblem(),
//                                new PMXCrossover(new Random().nextDouble(0, 1)),
                                new IntegerSBXCrossover(new Random().nextDouble(0, 1), 2.0),
//                                new PermutationSwapMutation(new Random().nextDouble(0, 1)), 100)).setMaxEvaluations(25000).build();
                                new IntegerPolynomialMutation(new Random().nextDouble(0, 1), 20.0), 100)).setMaxEvaluations(MAX_EVALUATIONS).build();
                algorithms.add(new ExperimentAlgorithm(algorithm, "NSGAIIa", problemList.get(i), run));
            }

            /*for (i = 0; i < problemList.size(); ++i) {
                algorithm = (new NSGAIIBuilder(((ExperimentProblem) problemList.get(i)).getProblem(), new SBXCrossover(1.0D, 20.0D), new PolynomialMutation(1.0D / (double) ((ExperimentProblem) problemList.get(i)).getProblem().getNumberOfVariables(), 20.0D), 100)).setMaxEvaluations(25000).build();
                algorithms.add(new ExperimentAlgorithm(algorithm, "NSGAIIb", (ExperimentProblem) problemList.get(i), run));
            }*/
        }

        return algorithms;
    }
}
