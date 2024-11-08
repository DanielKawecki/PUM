package pl.edu.uwr.pum.lista2;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.edu.uwr.pum.lista2.databinding.FragmentBBinding;

public class FragmentB extends Fragment {

    private FragmentBBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentBBinding.inflate(inflater);

        binding.loginButton.setOnClickListener(v -> {
            String username = String.valueOf(binding.textLogin.getText());
            String password = String.valueOf(binding.textPassword.getText());

            if (UserDatabase.getInstance().validate(username, password)) {
                NavDirections action = FragmentBDirections.actionFragmentBToFragmentD(username);
                Navigation.findNavController(requireView()).navigate(action);
            }
            else {
                binding.message.setText("Login or password incorrect");
                binding.textPassword.setText("");
            }
        });

        binding.registerButton.setOnClickListener(v -> {
            NavDirections action = FragmentBDirections.actionFragmentBToFragmentC2();
            Navigation.findNavController(requireView()).navigate(action);
        });

        return binding.getRoot();
    }
}