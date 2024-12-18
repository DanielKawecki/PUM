package pl.edu.uwr.pum.lista6

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

sealed class BottomBar(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object AllLists : BottomBar(Screens.AllListsScreen.route, "All Lists", Icons.Default.Home)
    object Grades : BottomBar(Screens.GradesScreen.route, "Grades", Icons.Default.Menu)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Navigation()
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Navigation(){
        val sharedViewModel: SharedViewModel = viewModel()
        val navController = rememberNavController()
        Scaffold(
            bottomBar = {BottomMenu(navController = navController)},
            content = { BottomNavGraph(navController = navController, shredViewModel = sharedViewModel) }
        )
    }

    @Composable
    fun BottomNavGraph(navController: NavHostController, shredViewModel: SharedViewModel){
        NavHost(
            navController = navController,
            startDestination = Screens.AllListsScreen.route
        ) {
            composable(route = Screens.AllListsScreen.route){ AllLists(navController = navController, sharedViewModel = shredViewModel) }
            composable(route = Screens.GradesScreen.route){ Grades(sharedViewModel = shredViewModel, navController) }

            composable(
                route = Screens.SingleListScreen.route + "/{listIndex}",
                arguments = listOf(navArgument("listIndex") { type = NavType.IntType })
            ) { backStackEntry ->
                val listIndex = backStackEntry.arguments?.getInt("listIndex")
                SingleList(listIndex, sharedViewModel = shredViewModel, navController)
            }
        }
    }
}
