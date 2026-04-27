package com.example.stardewlayoutplanner.data.model


data class Category(
    val name: String,
    val imageRes: Int? = null,
    val subItems: List<Category> = emptyList()
)

// since the only placeable items will really be the things in
// the subcategories (category --> subcategory 'placeable')
// ie furniture --> chair, table, etc.
val Category.isPlaceable: Boolean
    get() = subItems.isEmpty()