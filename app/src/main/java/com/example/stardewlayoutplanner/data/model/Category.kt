package com.example.stardewlayoutplanner.data.model

data class Category(
    val name: String,
    val imageRes: Int? = null,
    val subItems: List<Category> = emptyList()
)