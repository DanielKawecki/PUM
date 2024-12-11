package pl.edu.uwr.pum.lista3;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SubjectSummary {
    private String subjectName;
    private Float averageGrade;
    private Integer listCount;

//    String subjectName, Float averageGrade, Integer listCount
    public SubjectSummary(String subjectName, ArrayList<ExerciseList> exerciseLists) {
        this.subjectName = subjectName;

        Float grade = 0.f;
        for (int i = 0; i < exerciseLists.size(); i++) {
            grade += exerciseLists.get(i).getGrade();
        }

        averageGrade = grade / exerciseLists.size();
        averageGrade = (Math.round(averageGrade * 100.0f) / 100.0f);

        listCount = exerciseLists.size();
    }

    public String getSubjectName() {
        return subjectName;
    }

    public Float getAverageGrade() {
        return averageGrade;
    }

    public Integer getListCount() {
        return listCount;
    }
}
