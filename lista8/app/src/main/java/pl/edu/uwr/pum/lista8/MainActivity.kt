package pl.edu.uwr.pum.lista8

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pl.edu.uwr.pum.lista8.ui.theme.Lista8Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Navigation()
        }
    }

    @Composable
    fun Navigation(){
//        val sharedViewModel: SharedViewModel = viewModel()
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = Screens.GradesScreen.route) {
            composable(route = Screens.GradesScreen.route) {
                GradesScreen(navController)
            }

            composable(route = Screens.EditScreen.route + "/{gradeIndex}") {
                val gradeIndex = it.arguments?.getString("gradeIndex")
                EditScreen(gradeIndex, navController)
            }

            composable(route = Screens.AddScreen.route) {
                AddScreen(navController)
            }
        }
    }
}


