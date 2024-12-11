// ApiService.kt
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("recipes/complexSearch") // Define your endpoint here
    fun searchRecipes(
        @Query("query") query: String,
        @Query("apiKey") apiKey: String
    ): Call<RecipeResponse> // Define response type
}
