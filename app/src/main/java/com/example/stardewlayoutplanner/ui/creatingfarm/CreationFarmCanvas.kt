package com.example.stardewlayoutplanner.ui.creatingfarm

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import com.example.stardewlayoutplanner.data.model.PlaceableItem
import com.example.stardewlayoutplanner.util.gridSnap

@Composable
fun CreationFarmCanvas(
    placeableItems: List<PlaceableItem>,
    onPlaceItem: (Offset) -> Unit,
    gridSize: Float = 32f
) {
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    val context = LocalContext.current
    val imageCache = remember { mutableMapOf<Int, ImageBitmap>() }

    val resourceIds = remember(placeableItems) {
        placeableItems.mapNotNull { it.category.imageRes }.distinct()
    }

    resourceIds.forEach { resId ->
        if (!imageCache.containsKey(resId)) {
            imageCache[resId] = ImageBitmap.imageResource(resId)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFEFEF))

            // zoom + pan
            .pointerInput(Unit) {
                detectTransformGestures { centroid, pan, zoom, _ ->
                    val oldScale = scale
                    scale = (scale * zoom).coerceIn(0.5f, 3f)

                    offset = (offset + centroid) - (centroid * (scale / oldScale))
                    offset += pan
                }
            }

            // tap to place
            .pointerInput(Unit) {
                detectTapGestures { tapOffset ->
                    val adjusted = (tapOffset - offset) / scale
                    val snapped = gridSnap(adjusted, gridSize)
                    onPlaceItem(snapped)
                }
            }
    ) {

        Canvas(modifier = Modifier.fillMaxSize()) {

            val width = size.width
            val height = size.height

            // grid
            val cols = (width / gridSize).toInt() + 1
            val rows = (height / gridSize).toInt() + 1

            for (x in 0..cols) {
                drawLine(
                    Color.LightGray.copy(alpha = 0.3f),
                    start = Offset(x * gridSize * scale + offset.x, 0f),
                    end = Offset(x * gridSize * scale + offset.x, height)
                )
            }

            for (y in 0..rows) {
                drawLine(
                    Color.LightGray.copy(alpha = 0.3f),
                    start = Offset(0f, y * gridSize * scale + offset.y),
                    end = Offset(width, y * gridSize * scale + offset.y)
                )
            }

            // items
            placeableItems.forEach { item ->
                item.category.imageRes?.let { resId ->
                    imageCache[resId]?.let { bitmap ->
                        val topLeft = item.position * gridSize * scale + offset

                        withTransform({
                            translate(topLeft.x, topLeft.y)
                            scale(scale, scale)
                        }) {
                            drawImage(bitmap)
                        }
                    }
                }
            }
        }
    }
}
