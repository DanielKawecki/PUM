package pl.edu.uwr.pum.lista2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.edu.uwr.pum.lista2.databinding.FragmentDBinding;

public class FragmentD extends Fragment {

    private FragmentDBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDBinding.inflate(inflater);

        if (getArguments() != null)
            binding.welcome.setText("Welcome " + getArguments().getString("username"));

        binding.logoutButton.setOnClickListener(v -> {
            NavDirections action = FragmentDDirections.actionFragmentDToFragmentA();
            Navigation.findNavController(requireView()).navigate(action);
        });

        return binding.getRoot();
    }
}