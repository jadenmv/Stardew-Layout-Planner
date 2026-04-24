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
//import androidx.compose.ui.graphics.drawscope.drawImage
//import androidx.compose.ui.graphics.drawscope.drawLine
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntSize
import com.example.stardewlayoutplanner.data.model.GridPosition
import com.example.stardewlayoutplanner.data.model.PlaceableItem
import com.example.stardewlayoutplanner.util.gridToWorld
import com.example.stardewlayoutplanner.util.toGrid

@Composable
fun CreationFarmCanvas(
    placeableItems: List<PlaceableItem>,
    onPlaceItem: (GridPosition) -> Unit,
    gridSize: Float = 32f
) {
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    val imageCache = remember { mutableMapOf<Int, ImageBitmap>() }

    val resourceIds = remember(placeableItems) {
        placeableItems.mapNotNull { it.category.imageRes }.distinct()
    }

    // Cache images
    resourceIds.forEach { resId ->
        if (!imageCache.containsKey(resId)) {
            imageCache[resId] = ImageBitmap.imageResource(resId)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFEFEF))

            // Zoom + Pan
            .pointerInput(Unit) {
                detectTransformGestures { centroid, pan, zoom, _ ->
                    val oldScale = scale
                    scale = (scale * zoom).coerceIn(0.5f, 3f)

                    offset = (offset + centroid) - (centroid * (scale / oldScale))
                    offset += pan
                }
            }

            // Tap to place
            .pointerInput(Unit) {
                detectTapGestures { tapOffset ->
                    val world = (tapOffset - offset) / scale
                    val gridPos = world.toGrid(gridSize)
                    onPlaceItem(gridPos)
                }
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {

            val width = size.width
            val height = size.height

            val scaledGrid = gridSize * scale

            // Anchor grid to offset for smooth panning
            val startX = offset.x % scaledGrid
            val startY = offset.y % scaledGrid

            val cols = (width / scaledGrid).toInt() + 2
            val rows = (height / scaledGrid).toInt() + 2

            // Draw vertical lines
            for (x in 0..cols) {
                val xPos = startX + x * scaledGrid
                drawLine(
                    color = Color.LightGray.copy(alpha = 0.3f),
                    start = Offset(xPos, 0f),
                    end = Offset(xPos, height)
                )
            }

            // Draw horizontal lines
            for (y in 0..rows) {
                val yPos = startY + y * scaledGrid
                drawLine(
                    color = Color.LightGray.copy(alpha = 0.3f),
                    start = Offset(0f, yPos),
                    end = Offset(width, yPos)
                )
            }

            // Draw items
            placeableItems.forEach { item ->
                val resId = item.category.imageRes ?: return@forEach
                val bitmap = imageCache[resId] ?: return@forEach

                val world = gridToWorld(item.position, gridSize)

                val screenX = world.x * scale + offset.x
                val screenY = world.y * scale + offset.y

                drawImage(
                    image = bitmap,
                    topLeft = Offset(screenX, screenY),
                    dstSize = IntSize(
                        (bitmap.width * scale).toInt(),
                        (bitmap.height * scale).toInt()
                    )
                )
            }
        }
    }
}