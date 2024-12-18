package pl.edu.uwr.pum.lista6

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

sealed class Screens(val route: String) {
    object AllListsScreen : Screens("all_lists")
    object GradesScreen : Screens("grades")
    object SingleListScreen : Screens("single_list")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllLists(sharedViewModel: SharedViewModel, navController: NavHostController){
    val lists by sharedViewModel.lists.observeAsState(initial = mutableListOf())

    Scaffold (
        topBar = { Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            text = "My Exercise Lists",
            fontSize = 40.sp,
            textAlign = TextAlign.Center
        )},
        content = {paddingValues ->
            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues), contentPadding = PaddingValues(16.dp)) {
                items(lists.size) {
                        index -> val list = lists[index]

                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .background(Color(0xFFCDDAD6), shape = RoundedCornerShape(8.dp))
                            .padding(16.dp)
                            .clickable {
                                navController.navigate(Screens.SingleListScreen.route + "/$index")
                            },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = list.subject,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Exercise Count: ${list.exercises.size}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray
                            )
                        }

                        Column(
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = "List ${list.number}",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Grade: ${list.grade}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        },
        bottomBar = {BottomMenu(navController = navController)}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Grades(sharedViewModel: SharedViewModel, navController: NavHostController){

    val summaries = sharedViewModel.summarize()

    Scaffold (
        topBar = {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                text = "My Grades",
                fontSize = 40.sp,
                textAlign = TextAlign.Center
            )},
        content = {
            paddingValues ->
            LazyColumn(modifier = Modifier.padding(paddingValues), contentPadding = PaddingValues(16.dp)){
                items(summaries.size) {
                        index -> val list = summaries[index]

                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .background(Color(0xFFCDDAD6), shape = RoundedCornerShape(8.dp))
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = list.subject,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "List Count: ${summaries[index].listCount}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray
                            )
                        }

                        Column(
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = "Average ${summaries[index].averageGrade}",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        },

        bottomBar = {BottomMenu(navController = navController)}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleList(listIndex: Int?, sharedViewModel: SharedViewModel, navController: NavHostController) {

    val lists by sharedViewModel.lists.observeAsState(initial = mutableListOf())
    val list = lists[listIndex!!]

    Scaffold (
        topBar = {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                text = "${list.subject}\nList ${list.number}",
                fontSize = 40.sp,
                textAlign = TextAlign.Center
            )
        },
        content = {
            paddingValues ->
            LazyColumn (modifier = Modifier.padding(paddingValues), contentPadding = PaddingValues(16.dp)) {
                items(list.exercises.size) {
                        index -> val exercise = list.exercises[index]

                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .background(Color(0xFFCDDAD6), shape = RoundedCornerShape(8.dp))
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Exercise ${index + 1}",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = list.exercises[index].content,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray
                            )
                        }

                        Column(
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = "${list.exercises[index].points} pkt",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        },
        bottomBar = {BottomMenu(navController = navController)}
    )
}

@Composable
fun BottomMenu(navController: NavHostController){
    val screens = listOf(
        BottomBar.AllLists, BottomBar.Grades
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar{
        screens.forEach{screen ->
            NavigationBarItem(
                label = { Text(text = screen.title)},
                icon = { Icon(imageVector = screen.icon, contentDescription = "icon") },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {navController.navigate(screen.route)}
            )
        }
    }
}