package pl.edu.uwr.pum.lista2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.edu.uwr.pum.lista2.databinding.FragmentABinding;

public class FragmentA extends Fragment {

    public FragmentA() {
        // Required empty public constructor
    }

    private FragmentABinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentABinding.inflate(inflater);

        binding.loginButton.setOnClickListener(view -> {
            NavDirections action = FragmentADirections.actionFragmentAToFragmentB();
            Navigation.findNavController(requireView()).navigate(action);
        });

        binding.registerButton.setOnClickListener(view -> {
            NavDirections action = FragmentADirections.actionFragmentAToFragmentC();
            Navigation.findNavController(requireView()).navigate(action);
        });

        return binding.getRoot();
    }
}