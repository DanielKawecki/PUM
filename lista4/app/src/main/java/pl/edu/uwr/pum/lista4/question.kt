package pl.edu.uwr.pum.lista4

data class Question (
    val questionText: String,
    val answers: List<String>,
    val correctAnswer: Int
)