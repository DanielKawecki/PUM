package pl.edu.uwr.pum.lista8

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Entity(tableName = "grades")
data class Grade(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val subject: String,
    val value: Float
)

@Dao
interface GradeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGrade(grade: Grade)

    @Delete
    suspend fun deleteGrade(grade: Grade)

    @Query("SELECT * FROM grades WHERE id = :gradeId")
    fun getGradeById(gradeId: Int): Flow<Grade>

    @Query("SELECT * FROM grades ORDER BY subject ASC")
    fun getAllGrades(): Flow<List<Grade>>

    @Query("DELETE FROM grades WHERE id = :gradeId")
    suspend fun deleteGradeById(gradeId: Int)

    @Query("UPDATE grades SET subject = :subject, value = :value WHERE id = :gradeId")
    suspend fun updateGrade(gradeId: Int, subject: String, value: Float)
}

@Database(entities = [Grade::class], version = 1, exportSchema = false)
abstract class GradeDatabase : RoomDatabase() {
    abstract fun userDao(): GradeDao

    companion object {
        @Volatile
        private var Instance: GradeDatabase? = null

        fun getDatabase(context: Context): GradeDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, GradeDatabase::class.java, "user_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}

class GradeRepository(private val gradeDao: GradeDao) {
    fun getGrades() = gradeDao.getAllGrades()

    suspend fun insertGrade(grade: Grade) {
        gradeDao.insertGrade(grade)
    }

    suspend fun updateGrade(grade: Grade) {
        gradeDao.updateGrade(grade.id, grade.subject, grade.value)
    }

    suspend fun deleteGradeById(gradeId: Int) {
        gradeDao.deleteGradeById(gradeId)
    }

    fun getGradeById(gradeId: Int): Flow<Grade> {
        return gradeDao.getGradeById(gradeId)
    }
}

class GradeViewModelFactory(val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GradeViewModel(application) as T
    }
}

class GradeViewModel(application: Application) : ViewModel() {

    private val repository: GradeRepository
    private val _gradeState = MutableStateFlow<List<Grade>>(emptyList())
    val gradeState: StateFlow<List<Grade>>
        get() = _gradeState

    init {
        val db = GradeDatabase.getDatabase(application)
        val dao = db.userDao()
        repository = GradeRepository(dao)

        fetchGrades()
    }

    private fun fetchGrades() {
        viewModelScope.launch {
            repository.getGrades().collect { grades ->
                _gradeState.value = grades
            }
        }
    }

    fun addGrade(grade: Grade) {
        viewModelScope.launch {
            repository.insertGrade(grade)
        }
    }

    fun deleteGrade(gradeId: Int) {
        viewModelScope.launch {
            repository.deleteGradeById(gradeId)
        }
    }

    fun updateGrade(grade: Grade) {
        viewModelScope.launch {
            repository.updateGrade(grade)
        }
    }

    fun getGradeById(gradeId: Int): StateFlow<Grade?> {
        val gradeFlow = MutableStateFlow<Grade?>(null)

        viewModelScope.launch {
            repository.getGradeById(gradeId).collect { grade ->
                gradeFlow.value = grade
            }
        }

        return gradeFlow
    }
}