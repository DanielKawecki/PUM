package pl.edu.uwr.pum.lista8

import android.app.Application
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import java.util.Locale

sealed class Screens(val route: String) {
    object GradesScreen : Screens("grades_screen")
    object EditScreen : Screens("edit_screen")
    object AddScreen : Screens("add_screen")
}

@Composable
fun GradesScreen(navController: NavController){

    val viewModel: GradeViewModel = viewModel(
        LocalViewModelStoreOwner.current!!,
        "GradeViewModel",
        GradeViewModelFactory(LocalContext.current.applicationContext as Application)
    )
    val grades by viewModel.gradeState.collectAsStateWithLifecycle()


    val average = if (grades.isNotEmpty()) {
        grades.map { it.value }.average().toFloat()
    } else {0.0f}


    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp),
            text = "My Grades",
            fontSize = 40.sp,
            textAlign = TextAlign.Center
        )

        LazyColumn(modifier = Modifier

            .padding(10.dp)) {
            items(grades.size) {
                    index ->
                if (index < grades.size) {
                    val grade = grades[index]

                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .background(Color(0xFFCDDAD6), shape = RoundedCornerShape(8.dp))
                            .padding(16.dp)
                            .clickable {
                                val gradeId = grades[index].id
                                navController.navigate(Screens.EditScreen.route + "/$gradeId")
                            },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = grade.subject,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${grade.value}",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
        Spacer(Modifier.weight(0.5F))
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .background(Color(0xFFCDDAD6), shape = RoundedCornerShape(8.dp))
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Average Grade",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = String.format("%.2f", average),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }
        Button(onClick = { navController.navigate(Screens.AddScreen.route) },
            modifier = Modifier
                .padding(horizontal = 5.dp).padding(bottom = 60.dp)) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                text = "Add Grade",
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun EditScreen(studentIndex: String?, navController: NavController){

    val viewModel: GradeViewModel = viewModel(
        LocalViewModelStoreOwner.current!!,
        "GradeViewModel",
        GradeViewModelFactory(LocalContext.current.applicationContext as Application)
    )
//    val grades by viewModel.gradeState.collectAsStateWithLifecycle()

    val strIndex = studentIndex!!
    val index = strIndex.toInt()
    val grade by viewModel.getGradeById(index).collectAsStateWithLifecycle()

    var subject by remember { mutableStateOf("") }
    var value by remember { mutableStateOf("") }

    LaunchedEffect(grade) {
        grade?.let {
            subject = it.subject
            value = it.value.toString()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp),
            text = "Edit Grade",
            fontSize = 40.sp,
            textAlign = TextAlign.Center
        )

        TextField(
            modifier = Modifier.padding(start = 4.dp),
            value = subject,
            onValueChange = { subject = it },
            label = { Text("Subject") }
        )

        TextField(
            modifier = Modifier.padding(start = 4.dp, top = 10.dp),
            value = value,
            onValueChange = { value = it },
            label = { Text("Grade") }
        )

        Button(onClick = {

            if ("$subject$grade".isNotBlank()) {
                viewModel.updateGrade(Grade(index, subject, value.toFloat()))
            }

            navController.navigate(Screens.GradesScreen.route) },

            modifier = Modifier
                .padding(horizontal = 5.dp).padding(bottom = 60.dp, top = 10.dp)) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                text = "Change Grade",
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }

        Button(onClick = {

            viewModel.deleteGrade(index)
            navController.navigate(Screens.GradesScreen.route)
                         },

            modifier = Modifier
                .padding(horizontal = 5.dp).padding(bottom = 60.dp, top = 10.dp)) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                text = "Delete Grade",
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun AddScreen(navController: NavController){

    val viewModel: GradeViewModel = viewModel(
        LocalViewModelStoreOwner.current!!,
        "GradeViewModel",
        GradeViewModelFactory(LocalContext.current.applicationContext as Application)
    )

    var subject by remember { mutableStateOf("") }
    var grade by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp),
            text = "Add New Grade",
            fontSize = 40.sp,
            textAlign = TextAlign.Center
        )

        TextField(
            modifier = Modifier.padding(start = 4.dp),
            value = subject,
            onValueChange = { subject = it },
            label = { Text("Subject") }
        )

        TextField(
            modifier = Modifier.padding(start = 4.dp, top = 10.dp),
            value = grade,
            onValueChange = { grade = it },
            label = { Text("Grade") }
        )

        Button(onClick = {

            if ("$subject$grade".isNotBlank()) {
//                viewModel.addGrade(Grade(subject =  subject, value = grade.toFloat()))
                viewModel.addGrade(Grade(0, subject, grade.toFloat()))
                subject = ""
                grade = ""
            }

            navController.navigate(Screens.GradesScreen.route) },

            modifier = Modifier
                .padding(horizontal = 5.dp).padding(bottom = 60.dp, top = 10.dp)) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                text = "Add",
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}