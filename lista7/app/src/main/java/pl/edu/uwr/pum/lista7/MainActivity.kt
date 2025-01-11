package pl.edu.uwr.pum.lista7

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Navigation()
        }
    }

    @Composable
    fun Navigation(){
        val sharedViewModel: SharedViewModel = viewModel()
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = Screens.ListScreen.route) {
            composable(route = Screens.ListScreen.route) {
                ListScreen(sharedViewModel, navController)
            }

            composable(route = Screens.DetailScreen.route + "/{studentIndex}") {
                val studentIndex = it.arguments?.getString("studentIndex")
                DetailScreen(studentIndex, sharedViewModel){navController.popBackStack()}
            }
        }
    }
}
