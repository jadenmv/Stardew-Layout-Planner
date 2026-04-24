package com.example.stardewlayoutplanner.util

import androidx.compose.ui.geometry.Offset
import kotlin.math.floor

fun gridSnap(offset: Offset, gridSize: Float): Offset {
    val x = floor(offset.x / gridSize) * gridSize
    val y = floor(offset.y / gridSize) * gridSize
    return Offset(x, y)
}