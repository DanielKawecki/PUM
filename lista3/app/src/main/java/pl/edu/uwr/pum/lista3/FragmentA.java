package pl.edu.uwr.pum.lista3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.Random;

import pl.edu.uwr.pum.lista3.databinding.FragmentABinding;

public class FragmentA extends Fragment {
//    private final ArrayList<Subject> subjects = new ArrayList<>();
//    private final ArrayList<String> wordList = new ArrayList<>();
    private ArrayList<ExerciseList> exerciseLists = new ArrayList<>();

    public FragmentA() {
        // Required empty public constructor
    }

    private FragmentABinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentABinding.inflate(inflater);

//        generateData();
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            exerciseLists = mainActivity.getExerciseLists();
        }

        binding.recyclerView.setAdapter(new WordListAdapter(exerciseLists));
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        binding.recyclerView.setAdapter(new WordListAdapter(
                exerciseLists,
//                position -> Toast.makeText(getActivity(), "Clicked " + position, Toast.LENGTH_SHORT).show()
                position -> navigateToFragmentC(position)
                ));

        return binding.getRoot();
    }

    private void navigateToFragmentC(int position) {
        Bundle bundle = new Bundle();
//        bundle.putInt("position", position);
        bundle.putSerializable("exerciseList", exerciseLists.get(position));
        Navigation.findNavController(requireView()).navigate(R.id.action_fragmentA_to_fragmentC, bundle);
    }

//    private void generateData() {
//
//        subjects.add(new Subject("Math"));
//        subjects.add(new Subject("Electronics"));
//        subjects.add(new Subject("Physics"));
//        subjects.add(new Subject("Algorithms"));
//        subjects.add(new Subject("PUM"));
//
//        Random generator = new Random();
//
//        // Exercise Lists
//        for (int i = 0; i < 20; i++) {
//
//            ArrayList<Exercise> exercises = new ArrayList<>();
//            for (int j = 0; j < generator.nextInt(10) + 1; j++) {
//                exercises.add(new Exercise("Jakaś treść zadania nr " + j, generator.nextInt(2)));
//            }
//
//            Float grade = (float) (3.0 + 0.5 * generator.nextInt(5));
//            Subject subject = subjects.get(generator.nextInt(5));
//            exerciseLists.add(new ExerciseList(exercises, subject, grade));
//        }
//    }
}