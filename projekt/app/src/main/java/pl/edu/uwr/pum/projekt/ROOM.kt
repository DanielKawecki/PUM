package pl.edu.uwr.pum.projekt

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.sql.Date

@Entity(tableName = "product_table")
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val amount: Float,
    val calorie: Int,
    val date: Date
)

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}

@Dao
interface ProductDao {
    @Query("SELECT * FROM product_table ORDER BY date ASC")
    fun getProducts(): Flow<List<Product>>

    @Query("SELECT * FROM PRODUCT_TABLE WHERE id = :productId")
    fun getProductById(productId: Int): Flow<Product>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(product: Product)

    @Query("UPDATE product_table SET name = :name, calorie = :calorie WHERE id = :id")
    suspend fun updateProductById(id: Int, name: String, calorie: Int)

    @Query("DELETE FROM product_table WHERE id = :productId")
    suspend fun deleteProductById(productId: Int)

    @Query("DELETE FROM product_table")
    suspend fun deleteAll()
}

@Database(entities = [Product::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        private var Instance: ProductDatabase? = null

        fun getDatabase(context: Context): ProductDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, ProductDatabase::class.java, "product_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}

class ProductRepository(private val productDao: ProductDao, application: Application) {
    private val api = RetrofitInstance.api
    val sharedPreferences = application.getSharedPreferences("user_preferences", Context.MODE_PRIVATE)

    private var _budget: Int = sharedPreferences.getInt("calorie_budget", 100)
    val budget: Int
        get() = _budget

    fun getProducts() = productDao.getProducts()
    fun getProductById(productId: Int) = productDao.getProductById(productId)
    suspend fun updateProductById(productId: Int, name: String, calorie: Int) =
        productDao.updateProductById(productId, name, calorie)
    suspend fun deleteProductById(productId: Int) = productDao.deleteProductById(productId)
    suspend fun clear() = productDao.deleteAll()
//    suspend fun add(product: Product) = productDao.insert(product)

    suspend fun getNutrition(query: String): NutritionResponse {
//        val response = api.getNutrition(query)
        return api.getNutrition(query)
    }

    suspend fun addWithNutrition(name: String, amount: String, date: Date) {
        val response = api.getNutrition("$amount of $name")
        val calorie = response.items.sumOf { it.calories }
        productDao.insert(Product(0, name, 1.0f, calorie.toInt(), date))
    }

    fun setBudget(calorieBudget: Int) {
        val edit = sharedPreferences.edit()
        edit.putInt("calorie_budget", calorieBudget).apply()
        _budget = calorieBudget
    }
}

class ProductViewModelFactory(val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductViewModel(application) as T
    }
}

class ProductViewModel(application: Application) : ViewModel() {

    private val repository: ProductRepository

    private val _productState = MutableStateFlow<List<Product>>(emptyList())
    val productState: StateFlow<List<Product>>
        get() = _productState

    private val _budget: MutableStateFlow<Int> = MutableStateFlow(0)
    val budget: StateFlow<Int>
        get() = _budget

    init {
        val db = ProductDatabase.getDatabase(application)
        val dao = db.productDao()
        repository = ProductRepository(dao, application)

        _budget.value = repository.budget

        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            repository.getProducts().collect { users ->
                _productState.value = users
            }
        }
    }

    fun getProductById(productId: Int): StateFlow<Product?> {
        val productFlow = MutableStateFlow<Product?>(null)

        viewModelScope.launch {
            repository.getProductById(productId).collect {
                product -> productFlow.value = product
            }
        }

        return productFlow
    }

    fun updateProductById(productId: Int, name: String, calorie: Int) {
        viewModelScope.launch {
            repository.updateProductById(productId, name, calorie)
        }
    }

    fun deleteProductById(productId: Int) {
        viewModelScope.launch {
            repository.deleteProductById(productId)
        }
    }

    fun clearProducts() {
        viewModelScope.launch {
            repository.clear()
        }
    }

//    fun addProduct(product: Product) {
//        viewModelScope.launch {
//            repository.add(product)
//        }
//    }

    fun addWithNutrition(name: String, amount: String, date: Date) {
        viewModelScope.launch {
            repository.addWithNutrition(name, amount, date)
        }
    }

    // Retrofit fetch
    fun getNutrition(query: String): SharedFlow<NutritionResponse?> {
        val nutritionFlow = MutableSharedFlow<NutritionResponse?>()

        viewModelScope.launch {
            nutritionFlow.emit(repository.getNutrition(query))
        }
        return nutritionFlow
    }

    // SharedPreferences
    fun setCalorieBudget(calorieBudget: Int) {
        repository.setBudget(calorieBudget)
        _budget.value = calorieBudget
    }
}