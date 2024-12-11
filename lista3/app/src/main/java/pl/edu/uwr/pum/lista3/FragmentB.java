package pl.edu.uwr.pum.lista3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.Objects;

import pl.edu.uwr.pum.lista3.databinding.FragmentBBinding;


public class FragmentB extends Fragment {

    public FragmentB() {
        // Required empty public constructor
    }

    private FragmentBBinding binding;
    private ArrayList<ExerciseList> exerciseLists;
    private ArrayList<ExerciseList> AlgoLists = new ArrayList<>();
    private ArrayList<ExerciseList> ElecLists = new ArrayList<>();
    private ArrayList<ExerciseList> PhysLists = new ArrayList<>();
    private ArrayList<ExerciseList> MathLists = new ArrayList<>();
    private ArrayList<ExerciseList> PumLists = new ArrayList<>();
    private ArrayList<SubjectSummary> subjectSummaries = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBBinding.inflate(inflater);

        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            exerciseLists = mainActivity.getExerciseLists();
        }

        for (int i = 0; i < exerciseLists.size(); i++) {
            if (exerciseLists.get(i).getSubject().getName() == "PUM") {
                PumLists.add(exerciseLists.get(i));
            }
            if (exerciseLists.get(i).getSubject().getName() == "Electronics") {
                ElecLists.add(exerciseLists.get(i));
            }
            if (exerciseLists.get(i).getSubject().getName() == "Algorithms") {
                AlgoLists.add(exerciseLists.get(i));
            }
            if (exerciseLists.get(i).getSubject().getName() == "Physics") {
                PhysLists.add(exerciseLists.get(i));
            }
            if (exerciseLists.get(i).getSubject().getName() == "Math") {
                MathLists.add(exerciseLists.get(i));
            }
        }

        subjectSummaries.add(new SubjectSummary("PUM", PumLists));
        subjectSummaries.add(new SubjectSummary("Algorithms", AlgoLists));
        subjectSummaries.add(new SubjectSummary("Math", MathLists));
        subjectSummaries.add(new SubjectSummary("Physics", PhysLists));
        subjectSummaries.add(new SubjectSummary("Electronics", ElecLists));

        binding.recyclerView.setAdapter(new AdapterB(subjectSummaries));
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return binding.getRoot();
    }
}