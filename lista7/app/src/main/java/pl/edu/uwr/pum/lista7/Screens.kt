package pl.edu.uwr.pum.lista7

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

sealed class Screens(val route: String) {
    object ListScreen : Screens("list_screen")
    object DetailScreen : Screens("detail_screen")
}

@Composable
fun ListScreen(sharedViewModel: SharedViewModel = viewModel(), navController: NavController){
    val list by sharedViewModel.lists.observeAsState(initial = mutableListOf())

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            text = "Students",
            fontSize = 40.sp,
            textAlign = TextAlign.Center
        )

        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)) {
                items(list.size) {
                    index -> val student = list[index]

                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .background(Color(0xFFCDDAD6), shape = RoundedCornerShape(8.dp))
                        .padding(16.dp)
                        .clickable {
//                            onClick = onDetailScreen
                            navController.navigate(Screens.DetailScreen.route + "/$index")
                        },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${student.indexNumber}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = student.name,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = student.lastName,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun DetailScreen(studentIndex: String?, sharedViewModel: SharedViewModel = viewModel(), onListScreen: () -> Unit){
    val list by sharedViewModel.lists.observeAsState(initial = mutableListOf())
//    val student = list[0]
//    println(studentIndex)

    val strIndex = studentIndex!!

    val student = list[strIndex.toInt()]

    val topPadding = 10.dp
    val botPadding = 10.dp
    val startEndPadding = 16.dp
    val textSize = 26.sp

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            text = "Student Details",
            fontSize = 40.sp,
            textAlign = TextAlign.Center
        )

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.5F)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = startEndPadding,
                        top = topPadding,
                        end = startEndPadding,
                        bottom = botPadding
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Index Number: $studentIndex",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = textSize
                )

                Text(
                    text = "${student.indexNumber}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = textSize
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = startEndPadding,
                        top = topPadding,
                        end = startEndPadding,
                        bottom = botPadding
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Name: ",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = textSize
                )

                Text(
                    text = student.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = textSize
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = startEndPadding,
                        top = topPadding,
                        end = startEndPadding,
                        bottom = botPadding
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Last Name: ",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = textSize
                )

                Text(
                    text = student.lastName,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = textSize
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = startEndPadding,
                        top = topPadding,
                        end = startEndPadding,
                        bottom = botPadding
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Average Grade: ",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = textSize
                )

                Text(
                    text = "${student.averageGrade}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = textSize
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = startEndPadding,
                        top = topPadding,
                        end = startEndPadding,
                        bottom = botPadding
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Academic Year: ",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = textSize
                )

                Text(
                    text = "${student.year}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = textSize
                )
            }
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            onClick = onListScreen,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCDDAD6)),

        ) {
            Text(
                color = Color.Black,
                text = "Back to list",
                fontSize = 20.sp
            )
        }
    }
}