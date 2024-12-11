package com.example.smartpantry.data.api

// RecipeResponse.kt
data class RecipeResponse(
    val results: List<Recipe>
)

data class Recipe(
    val title: String,
    val image: String,
    val id: Int
)
