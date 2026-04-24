package com.example.stardewlayoutplanner.data.model

import androidx.compose.ui.geometry.Offset

data class GridPosition(
    val col: Int,
    val row: Int
)

data class PlaceableItem(
    val id: String,
    val category: Category,
    val position: GridPosition
)