package pl.edu.uwr.pum.lista2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

// FragmentA - Main
// FragmentB - Login
// FragmentC - Register
// FragmentD - Welcome

public class MainActivity extends AppCompatActivity {

    private User[] allUsers = new User[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}