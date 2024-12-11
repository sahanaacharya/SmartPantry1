import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApiService {
    @GET("recipes/complexSearch")
    suspend fun getRecipes(
        @Query("query") query: String?,
        @Query("ingredients") ingredients: String?,
        @Query("apiKey") apiKey: String
    ): RecipeResponse
}

data class RecipeResponse(
    val results: List<Recipe>,
    val totalResults: Int
)

data class Recipe(
    val id: Int,
    val title: String,
    val image: String
)
