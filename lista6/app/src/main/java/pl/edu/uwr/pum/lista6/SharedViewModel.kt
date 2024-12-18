package pl.edu.uwr.pum.lista6

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

data class Exercise(val content: String, val points: Int)
data class ExerciseList(val subject: String, val number: Int, val grade: Float, val exercises: List<Exercise>)

data class Summary(val subject: String, val averageGrade: Float, val listCount: Int)

fun createSummary(lists: List<ExerciseList>): List<Summary> {
    return lists
        .groupBy { it.subject }
        .map { (subject, exerciseLists) ->
            val averageGrade = exerciseLists.map { it.grade }.average().toFloat()
            val listCount = exerciseLists.size
            Summary(subject, averageGrade, listCount)
        }
}

class SharedViewModel : ViewModel() {
    private val _lists: MutableLiveData<MutableList<ExerciseList>> = MutableLiveData(mutableListOf())
    val lists: LiveData<MutableList<ExerciseList>> get() = _lists

    init {
        generateData()
    }

    private fun generateData() {

        val subjectList = listOf("PUM", "Algorithms", "Math", "Physics", "Electronics")
        val subjectCount = mutableListOf(0, 0, 0, 0, 0, 0)

        val newList = mutableListOf<ExerciseList>()
        repeat(20) {

            val listOfExercises = mutableListOf<Exercise>()
            repeat(Random.nextInt(1, 11)) { index ->
                listOfExercises.add(Exercise("Content of question ${index + 1}?", Random.nextInt(0, 2)))
            }

            val rand = Random.nextInt(0, subjectList.size)
            subjectCount[rand] = subjectCount[rand] + 1

            newList.add(
                ExerciseList(
                    subject = subjectList[rand],
                    number = subjectCount[rand],
                    grade = 3.0f + Random.nextInt(0, 5) * 0.5f,
                    exercises = listOfExercises
                )
            )
        }

        _lists.value = newList
    }

    fun summarize() : List<Summary> {
        val currentLists = _lists.value ?: return emptyList()
        return createSummary(currentLists)
    }
}