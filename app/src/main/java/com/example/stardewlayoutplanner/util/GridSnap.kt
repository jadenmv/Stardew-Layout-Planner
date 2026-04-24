package com.example.stardewlayoutplanner.util

import androidx.compose.ui.geometry.Offset
import com.example.stardewlayoutplanner.data.model.GridPosition
import kotlin.math.floor

fun Offset.toGrid(gridSize: Float): GridPosition {
    return GridPosition(
        col = floor(x / gridSize).toInt(),
        row = floor(y / gridSize).toInt()
    )
}

fun gridToWorld(position: GridPosition, gridSize: Float): Offset {
    return Offset(
        x = position.col * gridSize,
        y = position.row * gridSize
    )
}