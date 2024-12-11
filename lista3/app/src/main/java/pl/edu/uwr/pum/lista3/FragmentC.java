package pl.edu.uwr.pum.lista3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import pl.edu.uwr.pum.lista3.databinding.FragmentCBinding;


public class FragmentC extends Fragment {

    public FragmentC() {
        // Required empty public constructor
    }

    private FragmentCBinding binding;
    private ExerciseList exerciseList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            exerciseList = (ExerciseList) getArguments().getSerializable("exerciseList");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCBinding.inflate(inflater);
        binding.listName.setText(exerciseList.getSubject().getName() + " List " + exerciseList.getIndex());

        binding.recyclerView.setAdapter(new AdapterC(exerciseList));
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return binding.getRoot();
    }
}