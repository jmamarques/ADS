package com.ads.utils.algorithms;

import com.ads.models.ClassRoom;
import com.ads.models.Timetable;
import com.ads.utils.criteria.AllocationCriteria;
import com.ads.utils.criteria.ClassRoomSizeCriteria;
import com.ads.utils.criteria.ConflictCriteria;
import com.ads.utils.criteria.Criteria;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.uma.jmetal.problem.integerproblem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.integersolution.IntegerSolution;
import org.uma.jmetal.solution.integersolution.impl.DefaultIntegerSolution;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * JMA - 23/11/2021 22:15
 * Structure for the generic problem of project 3
 **/
@Log4j2
public class TimetableProblem extends AbstractIntegerProblem {
    private int length;
    private List<ClassRoom> classRoomList;
    private List<Timetable> timetableList;
    private List<Class<? extends Criteria>> objectivesClass;

    public TimetableProblem() {
    }

    /**
     * associates the values of length, classroom list, timetable list and objectives class to the variables
     * This method also, created two list's
     * One list consists in a mapping and returns a colection consisting of the elements of the stream, in this case range until range and mapping by the operand
     * Second list consists in a mapping and returns a colection consisting of the elements of the stream, in this case range until range and mapping by the classroom size
     * @param length
     * @param classRoomList
     * @param timetableList
     * @param objectivesClass
     */
    public TimetableProblem(int length,
                            @NonNull List<ClassRoom> classRoomList,
                            @NonNull List<Timetable> timetableList,
                            @NonNull List<Class<? extends Criteria>> objectivesClass) {
        this.length = length;
        this.classRoomList = classRoomList;
        this.timetableList = timetableList;
        this.objectivesClass = objectivesClass;
        List<Integer> values = IntStream.range(0, length).map(operand -> 0)
                .boxed()
                .collect(Collectors.toList());
        List<Integer> values2 = IntStream.range(0, length).map(operand -> classRoomList.size() - 1)
                .boxed()
                .collect(Collectors.toList());
        setVariableBounds(values, values2);
        setNumberOfObjectives(objectivesClass.size());
        setNumberOfConstraints(1);
        setName("ads");
    }

    /**
     * @param length
     * @param classRoomList
     * @param timetableList
     */
    public TimetableProblem(int length, List<ClassRoom> classRoomList, List<Timetable> timetableList) {
        this(length, classRoomList, timetableList, List.of(AllocationCriteria.class, ClassRoomSizeCriteria.class));
    }

    /**
     * evaluates the objectives class
     * apply the criteria to each one of the objectives class based on the classroom and timetable lists.
     * @param solution
     * @return solution
     */
    @Override
    public IntegerSolution evaluate(IntegerSolution solution) {
        for (int i = 0; i < objectivesClass.size(); i++) {
            Class<? extends Criteria> aClass = objectivesClass.get(i);
            try {
                Criteria criteria = aClass.getDeclaredConstructor().newInstance();
                solution.objectives()[i] = criteria.applyCriteria(classRoomList, timetableList, solution.variables());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                log.error(e.getMessage());
            }
        }
        this.evaluateConstraints(solution);
        return solution;
    }

    /**
     *
     * @param solution
     */
    public void evaluateConstraints(IntegerSolution solution) {
        solution.constraints()[0] = new ConflictCriteria().applyCriteria(classRoomList, timetableList, solution.variables());
    }

    /**
     * @return
     */
    @Override
    public IntegerSolution createSolution() {
        return new DefaultIntegerSolution(getNumberOfObjectives(), getNumberOfConstraints(), getBoundsForVariables());
    }
}
