package pl.edu.uwr.pum.lista4

import android.os.Bundle
import android.widget.ProgressBar
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.edu.uwr.pum.lista4.ui.theme.Lista4Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Quiz()
        }
    }
}

@Composable
fun Quiz() {
    val questions = listOf(
        Question("What is the capital city of Poland?", listOf("Berlin", "Paris", "Warsaw", "Madrit"), 2),
        Question("What is the capital city of Egypt?", listOf("Brno", "Kair", "Berlin", "Oslo"), 1),
        Question("What is the capital city of Yemen?", listOf("Canberra", "Sanaa", "Apia", "Brussels"), 1),
        Question("What is the capital city of Jordan?", listOf("Brno", "Kair", "Berlin", "Amman"), 3),
        Question("What is the capital city of Spain?", listOf("Madrit", "Dodoma", "Helsinki", "Hanoi"), 0),
        Question("What is the capital city of Turkey?", listOf("Ankara", "Kair", "Berlin", "Oslo"), 0),
        Question("What is the capital city of Philippines?", listOf("Minsk", "Maputo", "Amman", "Manila"), 3),
        Question("What is the capital city of Belarus?", listOf("Nairobi", "Minsk", "Amman", "Moscow"), 1),
        Question("What is the capital city of Ukraine?", listOf("Moscow", "Kyiv", "London", "Majuro"), 1),
        Question("What is the capital city of Panama?", listOf("Panama City", "Brno", "Oslo", "Manila"), 0)
    )
    var currentQuestionIndex by remember { mutableStateOf(0) }
    var selectedAnswer by remember { mutableStateOf("") }
    var currentScore by remember { mutableStateOf(0) }
    var showResult by remember { mutableStateOf(false) }

    if (showResult) {
        ResultScreen(
            totalQuestions = questions.size,
            score = currentScore,
            onRestart = {
                currentQuestionIndex = 0
                currentScore = 0
                showResult = false
            }
        )
    }
    else {
        val currentQuestion = questions[currentQuestionIndex]
        QuizScreen(
           question = currentQuestion,
            questionTotal = questions.size,
            currentIndex = currentQuestionIndex,
            onAnswerSelected = { answer -> selectedAnswer = answer },
            onSubmit = {

                val correctForCurrent = currentQuestion.answers[currentQuestion.correctAnswer]
                if (selectedAnswer == correctForCurrent) {
                    currentScore++
                }

                if (currentQuestionIndex < questions.size - 1) {
                    currentQuestionIndex++
                    selectedAnswer = ""
                }
                else {
                    showResult = true
                }
            },
            selectedAnswer = selectedAnswer
        )
    }

}

@Composable
fun ResultScreen (totalQuestions: Int, score: Int, onRestart: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text(
            text = "Quiz Finished",
            style = MaterialTheme.typography.bodySmall,
            fontSize = 26.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = "Score: $score/$totalQuestions",
            style = MaterialTheme.typography.bodySmall,
            fontSize = 22.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Button(
            onClick = onRestart,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Restart", fontSize = 26.sp)
        }
    }
}

@Composable
fun QuizScreen (
    question: Question,
    questionTotal: Int,
    currentIndex: Int,
    onAnswerSelected: (String) -> Unit,
    onSubmit: () -> Unit,
    selectedAnswer: String)
    {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = question.questionText,
                style = MaterialTheme.typography.bodySmall,
                fontSize = 22.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LinearProgressIndicator(
                progress = (currentIndex.toFloat() / questionTotal.toFloat()),
                modifier = Modifier.fillMaxWidth()
            )

            Column (modifier = Modifier.fillMaxSize()) {
                question.answers.forEach { answer ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        RadioButton(selected = selectedAnswer == answer, onClick = { onAnswerSelected(answer) })
                        Text(
                            text = answer,
                            modifier = Modifier.padding(start = 8.dp),
                            fontSize = 18.sp,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
                Spacer(modifier = Modifier.height(300.dp))
                Button(
                    onClick = onSubmit,
                    modifier = Modifier.fillMaxWidth().height(80.dp).padding(3.dp),
                    enabled = selectedAnswer.isNotEmpty()
                ) {
                    Text("Submit", fontSize = 22.sp)
                }
            }
        }
    }

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
            text = "Hello $name!",
            modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lista4Theme {
        Greeting("Android")
    }
}