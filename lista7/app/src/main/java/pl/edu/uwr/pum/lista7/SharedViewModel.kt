package pl.edu.uwr.pum.lista7

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

data class Student(val indexNumber: Int, val name: String, val lastName: String, val averageGrade: Float, val year: Int)

class SharedViewModel : ViewModel() {
    private val _data: MutableLiveData<MutableList<Student>> = MutableLiveData(mutableListOf())
    val lists: LiveData<MutableList<Student>> get() = _data

    init {
        generateData()
    }

    private fun generateData() {

        val newList = mutableListOf(
            Student(340649, "Ignacy", "Żywalewski", 4.5f, 3),
            Student(334075, "Karol", "Makarowski", 4.20f, 3),
            Student(339073, "Daniel", "Kawecki", 5.00f, 3)
        )

        _data.value = newList
    }
}