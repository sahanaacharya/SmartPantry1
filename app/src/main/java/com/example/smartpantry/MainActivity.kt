package com.example.smartpantry

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class MainActivity : ComponentActivity() {
    private val apiKey = "beb333661d60423ba86abfd1ad32c992"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var recipes = emptyList<Recipe>()
        var errorMessage: String? = null

        lifecycleScope.launch {
            try {
                val response = RetrofitInstance.api.getRecipes(
                    ingredients = "apple,flour,sugar",
                    apiKey = apiKey
                )
                recipes = response.results
            } catch (e: Exception) {
                errorMessage = e.message
            }

            setContent {
                SmartPantryApp(recipes, errorMessage)
            }
        }
    }

    companion object {
        fun smartPantryApp() {

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmartPantryApp(recipes: List<Recipe>, errorMessage: String?) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("SmartPantry") }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            if (errorMessage != null) {
                Text(
                    text = "Error: $errorMessage",
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.error
                )
            } else if (recipes.isEmpty()) {
                Text(
                    text = "Loading recipes...",
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                RecipeList(recipes)
            }
        }
    }
}

@Composable
fun RecipeList(recipes: List<Recipe>) {
    LazyColumn {
        items(recipes) { recipe ->
            RecipeCard(recipe)
        }
    }
}

@Composable
fun RecipeCard(recipe: Recipe) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = recipe.title, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Preparation Time: ${recipe.prepTime} minutes", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

// Retrofit Setup
object RetrofitInstance {
    private const val BASE_URL = "https://api.spoonacular.com/" // Corrected base URL

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // Set the base URL
            .addConverterFactory(GsonConverterFactory.create()) // Add JSON converter
            .build()
    }

    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java) // Create the API interface
    }
}


interface ApiService {
    @GET("/recipes")
    suspend fun getRecipes(
        @Query("ingredients") ingredients: String,
        @Query("apiKey") apiKey: String
    ): RecipeResponse
}

data class RecipeResponse(val results: List<Recipe>)
data class Recipe(val title: String, val prepTime: Int)
