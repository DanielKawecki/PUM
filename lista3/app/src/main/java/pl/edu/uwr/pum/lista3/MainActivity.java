package pl.edu.uwr.pum.lista3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import pl.edu.uwr.pum.lista3.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private final ArrayList<Subject> subjects = new ArrayList<>();
    private ArrayList<ExerciseList> exerciseLists = new ArrayList<>();

    private ActivityMainBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        generateData();

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(binding.bottomNavView, navController);

        setContentView(binding.getRoot());

    }

    private void generateData() {

        subjects.add(new Subject("Math"));
        subjects.add(new Subject("Electronics"));
        subjects.add(new Subject("Physics"));
        subjects.add(new Subject("Algorithms"));
        subjects.add(new Subject("PUM"));

        List<Integer> lists_index = new ArrayList<>(
                Arrays.asList(0, 0, 0, 0, 0));

        Random generator = new Random();

        // Exercise Lists
        for (int i = 0; i < 20; i++) {

            ArrayList<Exercise> exercises = new ArrayList<>();
            for (int j = 0; j < generator.nextInt(10) + 1; j++) {
                exercises.add(new Exercise("Jakaś treść zadania nr " + (j + 1), generator.nextInt(2), j + 1));
            }

            Float grade = (float) (3.0 + 0.5 * generator.nextInt(5));

            int subject_index = generator.nextInt(5);
            Subject subject = subjects.get(subject_index);
            lists_index.set(subject_index, lists_index.get(subject_index) + 1);

            exerciseLists.add(new ExerciseList(exercises, subject, grade, lists_index.get(subject_index)));
        }
    }

    public ArrayList<ExerciseList> getExerciseLists() {
        return exerciseLists;
    }
}