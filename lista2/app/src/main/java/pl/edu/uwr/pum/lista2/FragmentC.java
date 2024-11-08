package pl.edu.uwr.pum.lista2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.edu.uwr.pum.lista2.databinding.FragmentCBinding;

public class FragmentC extends Fragment {

    private FragmentCBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCBinding.inflate(inflater);

        binding.registerButton.setOnClickListener(v -> {

            String username = String.valueOf(binding.textLogin.getText());
            String password1 = String.valueOf(binding.textPassword.getText());
            String password2 = String.valueOf(binding.textPassword2.getText());

            if (!password1.equals(password2)) {
                binding.message.setText("Passwords must be the same");
            }
            if (UserDatabase.getInstance().findUser(username) != null) {
                binding.message.setText("Username already in use");
            }

            if (UserDatabase.getInstance().findUser(username) == null && password1.equals(password2)) {
                UserDatabase.getInstance().addUser(username, password1);
                NavDirections action = FragmentCDirections.actionFragmentCToFragmentB();
                Navigation.findNavController(requireView()).navigate(action);
            }
        });

        return binding.getRoot();
    }
}