package pl.edu.uwr.pum.lista3;

import java.io.Serializable;
import java.util.ArrayList;

public class ExerciseList implements Serializable {
    private ArrayList<Exercise> exercises;
    private Subject subject;
    private Float grade;

    private Integer index;

    public ExerciseList(ArrayList<Exercise> _exercises, Subject _subject, Float _grade, Integer _index) {
        exercises = _exercises;
        subject = _subject;
        grade = _grade;
        index = _index;
    }

    public ArrayList<Exercise> getExercises() {
        return exercises;
    }
    public Subject getSubject() {
        return subject;
    }
    public Float getGrade() {
        return grade;
    }

    public Integer getIndex() {
        return index;
    }
}
