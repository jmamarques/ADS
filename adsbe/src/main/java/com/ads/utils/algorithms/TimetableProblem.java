package com.ads.utils.algorithms;

import org.uma.jmetal.problem.permutationproblem.impl.AbstractIntegerPermutationProblem;
import org.uma.jmetal.solution.permutationsolution.PermutationSolution;

/**
 * JMA - 23/11/2021 22:15
 * Structure for our problem
 **/
public class TimetableProblem extends AbstractIntegerPermutationProblem {

    public TimetableProblem() {
        setNumberOfObjectives(1);
        setName("Try");
        setNumberOfConstraints(0);
    }

    @Override
    public int getLength() {
        return 12;
    }

    @Override
    public PermutationSolution<Integer> evaluate(PermutationSolution<Integer> solution) {
        solution.objectives()[0] = solution.variables().get(0) % 5;
        return solution;
    }
}
