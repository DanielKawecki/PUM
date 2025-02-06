package pl.edu.uwr.pum.projekt

import android.app.Application
import androidx.collection.arrayMapOf
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.WhitePoint
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import java.sql.Date
import java.time.LocalDate
import java.util.Locale
import kotlin.random.Random

sealed class Screens(val route: String) {
    data object HomeScreen : Screens("home")
    data object HistoryScreen : Screens("history")
    data object AddScreen : Screens("add")
    data object EditScreen : Screens("edit")
    data object OptionsScreen : Screens("options")
}

sealed class BottomBar(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    data object Home : BottomBar(Screens.HomeScreen.route, "Home", Icons.Default.Home)
    data object History : BottomBar(Screens.HistoryScreen.route, "History", Icons.Default.DateRange)
}

@Composable
fun BackgroundGradient() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(
                colors = listOf(Color(255, 255, 255),
                    Color(88,190,249))))
    ) {}
}

@Composable
fun ScreenTitle(title: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .padding(top = 30.dp),
        text = title,
        fontSize = 40.sp,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        color = Color.DarkGray

    )
}

@Composable
fun CustomButton(content: String , function: () -> Unit) {

    Button(
        modifier = Modifier.padding(4.dp)
            .fillMaxWidth()
            .shadow(
                elevation = 3.dp,
                shape = RoundedCornerShape(8.dp)
            )
            .background(Color.White)
            .size(60.dp)
            .background(Color.White, shape = RoundedCornerShape(5.dp)),
        onClick = { function() },
        colors = ButtonColors(
            contentColor = Color.DarkGray,
            containerColor = Color.Transparent,
            disabledContainerColor = Color.Red,
            disabledContentColor = Color.Red,
        )
    ) {
        Text(
            text = content,
            fontSize = 18.sp
        )
    }
}

@Composable
fun CustomTextField(
    label: String,
    content: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        modifier = Modifier.padding(4.dp)
            .fillMaxWidth()
            .shadow(
                elevation = 3.dp,
                shape = RoundedCornerShape(8.dp)
            )
            .background(Color.White)
            .size(60.dp)
            .background(Color.White, shape = RoundedCornerShape(5.dp)),
        value = content,
        onValueChange = onValueChange,
        label = { Text(label) },
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.DarkGray,
            unfocusedTextColor = Color.DarkGray,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedLabelColor = Color.DarkGray,
            unfocusedLabelColor = Color.DarkGray,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color.DarkGray
        ),
        textStyle = TextStyle(fontSize = 20.sp)
    )
}

@Composable
fun FAB(navController: NavHostController) {
    FloatingActionButton(
        onClick = {navController.navigate(Screens.AddScreen.route)},
        containerColor = Color.White
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add",
            tint = Color.DarkGray
        )
    }
}

@Composable
fun HomeScreen(navController: NavHostController) {

    val viewModel: ProductViewModel = viewModel(
        LocalViewModelStoreOwner.current!!,
        "ProductViewModel",
        ProductViewModelFactory(LocalContext.current.applicationContext as Application)
    )
    val products by viewModel.productState.collectAsStateWithLifecycle()
    val todayProducts = products.filter { it.date.toString() == Date(System.currentTimeMillis()).toString() }
    val total = todayProducts.sumOf { it.calorie }

    val budget by viewModel.budget.collectAsStateWithLifecycle()
    var remaining = budget - total
    if (remaining < 0) remaining = 0

    BackgroundGradient()

    Column (modifier = Modifier.fillMaxSize().background(Color(255,255,255, 150))) {

        ScreenTitle("Today")

        // Card with total calories

        Column(
            modifier = Modifier
                .padding(14.dp)
                .fillMaxWidth()
                .shadow(
                    elevation = 3.dp,
                    shape = RoundedCornerShape(8.dp)
                )
                .background(Color.White)
                .size(140.dp)
                .background(Color.White, shape = RoundedCornerShape(5.dp))
                .padding(16.dp)
                .clickable { navController.navigate(Screens.OptionsScreen.route + "/${budget}") },
        ) {

            Text(
                text = "Calories",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray,
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, top = 10.dp, end = 15.dp, bottom = 0.dp)
                    .height(8.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(Color(220, 220, 220))
            ) {
                LinearProgressIndicator(
                    progress = {
                        total.toFloat() / budget.toFloat()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp),
                    color = Color(88,190,249),
                    trackColor = Color.Transparent,
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$total",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Medium,
                    color = Color.DarkGray,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = " Eaten",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.weight(0.5f))

                Text(
                    text = "$remaining",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Medium,
                    color = Color.DarkGray,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = " Remaining",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        // List of products

        LazyColumn (
          modifier = Modifier
              .fillMaxSize()
              .padding(start = 10.dp, end = 10.dp, top = 0.dp, bottom = 0.dp)
        ) {
            items(todayProducts.size) {
                index -> val product = todayProducts[index]

                Row(
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                        .shadow(
                            elevation = 3.dp,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .background(Color.White)
                        .size(60.dp)
                        .background(Color.White, shape = RoundedCornerShape(5.dp))
                        .padding(16.dp)
                        .clickable {
                            navController.navigate(Screens.EditScreen.route
                                    + "/${product.id}/${product.name}/${product.calorie}")
                        },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = product.name,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.DarkGray
                    )
                    Text(
                        text = "${product.calorie} kcal",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun HistoryScreen(){

    val viewModel: ProductViewModel = viewModel(
        LocalViewModelStoreOwner.current!!,
        "ProductViewModel",
        ProductViewModelFactory(LocalContext.current.applicationContext as Application)
    )
    val products by viewModel.productState.collectAsStateWithLifecycle()

    // Create summaries
    val results = products
        .groupBy { it.date.toString()}
        .map { entry ->
            val date = entry.key
            val totalCalories = entry.value.sumOf { it.calorie }
            date to totalCalories
        }.reversed()

    BackgroundGradient()

    Column (modifier = Modifier.fillMaxSize().background(Color(255, 255, 255, 150))) {

        ScreenTitle("History")

        // List of Dates

        LazyColumn (
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            items(results.size) {
                    index -> val result = results[index]

                Row(
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                        .shadow(
                            elevation = 3.dp,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .background(Color.White)
                        .size(60.dp)
                        .background(Color.White, shape = RoundedCornerShape(5.dp))
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = result.first,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.DarkGray
                    )
                    Text(
                        text = "${result.second} kcal",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun AddScreen(navController: NavHostController) {

    val viewModel: ProductViewModel = viewModel(
        LocalViewModelStoreOwner.current!!,
        "ProductViewModel",
        ProductViewModelFactory(LocalContext.current.applicationContext as Application)
    )

    var productName by rememberSaveable { mutableStateOf("") }
    var amount by rememberSaveable { mutableStateOf("") }

    BackgroundGradient()

    Column (modifier = Modifier.fillMaxSize().background(Color(255, 255, 255, 150)).padding(10.dp)) {

        ScreenTitle("Add Product")

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        CustomTextField("Product", productName) {
                newText -> productName = newText
        }

        CustomTextField("Amount", amount) {
            newText -> amount = newText
        }

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        CustomButton("Add Product") {
            if (productName != "" && productName != "Enter product name") {
                viewModel.addWithNutrition(
                    name = capitalizeWords(productName),
                    amount = amount,
                    date = Date(System.currentTimeMillis())
                )
                navController.navigate(Screens.HomeScreen.route)
            }
            else {
                productName = "Enter product name"
            }
        }
    }
}


@Composable
fun EditScreen(index: String?, name: String?, calorie: String?, navController: NavHostController) {
    val productId = index!!.toInt()

    val viewModel: ProductViewModel = viewModel(
        LocalViewModelStoreOwner.current!!,
        "ProductViewModel",
        ProductViewModelFactory(LocalContext.current.applicationContext as Application)
    )

    var productName by rememberSaveable { mutableStateOf(name!!) }
    var productCalories by rememberSaveable { mutableStateOf(calorie!!) }

    BackgroundGradient()

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(255, 255, 255, 150))
            .padding(10.dp))
    {

        ScreenTitle("Edit Product")

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        CustomTextField("Product", productName) {
            newText -> productName = newText
        }

        CustomTextField("Calorie", productCalories) {
                newText -> productCalories = newText
        }

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        CustomButton("Update Product") {
            if (productCalories == "") productCalories = "0"
            viewModel.updateProductById(productId, productName, productCalories.toInt())
            navController.navigate(Screens.HomeScreen.route)
        }

        CustomButton("Delete Product") {
            viewModel.deleteProductById(productId)
            navController.navigate(Screens.HomeScreen.route)
        }
    }
}

@Composable
fun OptionsScreen(currentCalorie: String?, navController: NavHostController) {

    val viewModel: ProductViewModel = viewModel(
        LocalViewModelStoreOwner.current!!,
        "ProductViewModel",
        ProductViewModelFactory(LocalContext.current.applicationContext as Application)
    )

    var calorieBudget by rememberSaveable { mutableStateOf(currentCalorie!!) }

    BackgroundGradient()

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(255, 255, 255, 150))
            .padding(10.dp)) {

        ScreenTitle("Options")

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        CustomTextField("Calorie Budget", calorieBudget) {
            newText -> calorieBudget = newText
        }

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        CustomButton("Set Calorie Budget") {
            if (calorieBudget == "") calorieBudget = "0"
            viewModel.setCalorieBudget(calorieBudget.toInt())
            navController.navigate(Screens.HomeScreen.route)
        }

        CustomButton("Clear All Products") {
            viewModel.clearProducts()
            navController.navigate(Screens.HomeScreen.route)
        }
    }
}
