package com.example.stardewlayoutplanner.ui.creatingfarm

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import kotlin.math.min

@Composable
fun CreationFarmCanvas(
    backgroundRes: Int? = null,
) {
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    var viewportSize by remember { mutableStateOf(IntSize.Zero) }

    val context = LocalContext.current
    val imageCache = remember { mutableMapOf<Int, ImageBitmap>() }

    val bgBitmap = remember(backgroundRes) {
        backgroundRes?.let { resId ->
            imageCache.getOrPut(resId) {
                ImageBitmap.imageResource(context.resources, resId)
            }
        }
    }

    val imgW = bgBitmap?.width?.toFloat() ?: 1f
    val imgH = bgBitmap?.height?.toFloat() ?: 1f

    // the larger the number, the more you can zoom in
    val maxScale = 8f

    // camera clamp for zoom and pan boundary
    fun clamp(off: Offset, scale: Float): Offset {
        val vw = viewportSize.width.toFloat()
        val vh = viewportSize.height.toFloat()

        val worldW = imgW * scale
        val worldH = imgH * scale

        val minX = if (worldW <= vw) (vw - worldW) / 2f else vw - worldW
        val maxX = if (worldW <= vw) (vw - worldW) / 2f else 0f

        val minY = if (worldH <= vh) (vh - worldH) / 2f else vh - worldH
        val maxY = if (worldH <= vh) (vh - worldH) / 2f else 0f

        return Offset(
            off.x.coerceIn(minX, maxX),
            off.y.coerceIn(minY, maxY)
        )
    }

    // "camera" on max zoom when farm is loaded
    var initialized by remember { mutableStateOf(false) }

    LaunchedEffect(bgBitmap, viewportSize) {
        if (!initialized && viewportSize.width > 0 && viewportSize.height > 0) {

            val vw = viewportSize.width.toFloat()
            val vh = viewportSize.height.toFloat()

            val minScale = min(vw / imgW, vh / imgH)

            scale = minScale
            offset = clamp(Offset.Zero, scale)

            initialized = true
        }
    }

    // camera zoom and pan, from developer.android documentation: https://developer.android.com/develop/ui/compose/touch-input/pointer-input/multi-touch
    val transformState = rememberTransformableState { zoomChange, panChange, _ ->
        val vw = viewportSize.width.toFloat()
        val vh = viewportSize.height.toFloat()
        if (vw <= 0f || vh <= 0f) return@rememberTransformableState

        val minScale = min(vw / imgW, vh / imgH).coerceAtLeast(0.1f)

        val newScale = (scale * zoomChange).coerceIn(minScale, maxScale)

        scale = newScale
        offset = clamp(offset + panChange, newScale)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFEFEF))
            .onSizeChanged { viewportSize = it }
            .transformable(state = transformState)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {

            val worldW = imgW * scale
            val worldH = imgH * scale

            bgBitmap?.let { bmp ->
                drawImage(
                    image = bmp,
                    dstOffset = IntOffset(offset.x.toInt(), offset.y.toInt()),
                    dstSize = IntSize(worldW.toInt(), worldH.toInt())
                )
            }
        }
    }
}