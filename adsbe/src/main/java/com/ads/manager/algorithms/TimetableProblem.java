package com.ads.manager.algorithms;

import com.ads.manager.criteria.ConflictCriteria;
import com.ads.manager.criteria.Criteria;
import com.ads.models.internal.ClassRoom;
import com.ads.models.internal.Reservation;
import com.ads.models.internal.Timetable;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
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
    private final int length;
    private final List<ClassRoom> classRoomList;
    private final List<Timetable> timetableList;
    private final List<Class<? extends Criteria>> objectivesClass;
    private final ArrayListValuedHashMap<ClassRoom, Reservation> occupation;

    /**
     * associates the values of length, classroom list, timetable list and objectives class to the variables
     * This method also, created two list's
     * One list consists in a mapping and returns a colection consisting of the elements of the stream, in this case range until range and mapping by the operand
     * Second list consists in a mapping and returns a colection consisting of the elements of the stream, in this case range until range and mapping by the classroom size
     *
     * @param length          - size
     * @param classRoomList   - classroom list
     * @param timetableList   - timetable list
     * @param objectivesClass - objectives list
     */
    public TimetableProblem(int length,
                            @NonNull List<ClassRoom> classRoomList,
                            @NonNull List<Timetable> timetableList,
                            @NonNull List<Class<? extends Criteria>> objectivesClass,
                            @NonNull ArrayListValuedHashMap<ClassRoom, Reservation> occupation) {
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
        this.occupation = new ArrayListValuedHashMap<>(occupation);
    }

    /**
     * evaluates the objectives class
     * apply the criteria to each one of the objectives class based on the classroom and timetable lists.
     *
     * @param solution - Integer solution
     * @return solution
     */
    @Override
    public IntegerSolution evaluate(IntegerSolution solution) {
        for (int i = 0; i < objectivesClass.size(); i++) {
            Class<? extends Criteria> aClass = objectivesClass.get(i);
            try {
                Criteria criteria = aClass.getDeclaredConstructor().newInstance();
                solution.objectives()[i] = criteria.applyCriteria(classRoomList, timetableList, solution.variables(), occupation);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                log.error(e.getMessage());
            }
        }
        this.evaluateConstraints(solution);
        return solution;
    }

    public void evaluateConstraints(IntegerSolution solution) {
        solution.constraints()[0] = new ConflictCriteria().applyCriteria(classRoomList, timetableList, solution.variables(), occupation);
    }

    @Override
    public IntegerSolution createSolution() {
        return new DefaultIntegerSolution(getNumberOfObjectives(), getNumberOfConstraints(), getBoundsForVariables());
    }
}
