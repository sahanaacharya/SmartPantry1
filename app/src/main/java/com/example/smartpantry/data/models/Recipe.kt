package com.example.smartpantry.data.models

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.smartpantry.MainActivity

data class Recipe(val title: String, val prepTime: Int, val image: Int? = null)

@Preview(showBackground = true)
@Composable
fun PreviewSmartPantryApp() {
    listOf(
        Recipe("Apple Pie", 45, null),
        Recipe("Sugar Cookies", 30, null),
        Recipe("Flour less Chocolate Cake", 60, null)
    )
    MainActivity.smartPantryApp()
}
