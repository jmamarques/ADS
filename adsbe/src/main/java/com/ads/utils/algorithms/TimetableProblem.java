package com.ads.utils.algorithms;

import com.ads.models.ClassRoom;
import com.ads.models.Timetable;
import com.ads.utils.criteria.Criteria;
import lombok.NonNull;
import org.uma.jmetal.problem.permutationproblem.impl.AbstractIntegerPermutationProblem;
import org.uma.jmetal.solution.permutationsolution.PermutationSolution;

import java.util.List;

/**
 * JMA - 23/11/2021 22:15
 * Structure for our problem
 **/
public class TimetableProblem extends AbstractIntegerPermutationProblem {
    private int length;
    private List<ClassRoom> classRoomList;
    private List<Timetable> timetableList;
    private List<Class<? extends Criteria>> objectivesClass;
    private List<Class<? extends Criteria>> constraintsClass;

    public TimetableProblem() {
    }

    public TimetableProblem(int length,
                            @NonNull List<ClassRoom> classRoomList,
                            @NonNull List<Timetable> timetableList,
                            @NonNull List<Class<? extends Criteria>> objectivesClass,
                            @NonNull List<Class<? extends Criteria>> constraintsClass) {
        this.length = length;
        this.classRoomList = classRoomList;
        this.timetableList = timetableList;
        this.objectivesClass = objectivesClass;
        this.constraintsClass = constraintsClass;
        setNumberOfObjectives(objectivesClass.size());
        setNumberOfConstraints(constraintsClass.size());
        setName("ads");
    }

    public TimetableProblem(int length, List<ClassRoom> classRoomList, List<Timetable> timetableList) {
        this.length = length;
        this.classRoomList = classRoomList;
        this.timetableList = timetableList;
        setNumberOfObjectives(1);
        setNumberOfConstraints(1);
        setName("ads");
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public PermutationSolution<Integer> evaluate(PermutationSolution<Integer> solution) {
        solution.objectives()[0] = solution.variables().get(0) % 5;
        return solution;
    }
}
