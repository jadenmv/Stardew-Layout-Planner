//import androidx.compose.ui.graphics.drawscope.drawImage
//import androidx.compose.ui.graphics.drawscope.drawLine
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
import androidx.compose.ui.input.pointer.pointerInput
import com.example.stardewlayoutplanner.data.model.Category
import com.example.stardewlayoutplanner.data.model.GridPosition
import com.example.stardewlayoutplanner.data.model.PlaceableItem
import com.example.stardewlayoutplanner.util.toGrid

@Composable
fun CreationFarmCanvas(
    placeableItems: List<PlaceableItem>,
    selectedCategory: Category?,
    onPlaceItem: (GridPosition, Category) -> Unit,
    gridSize: Float = 32f
) {
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

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

            // place item
            .pointerInput(selectedCategory) {
                detectTapGestures { tap ->
                    val world = (tap - offset) / scale
                    val grid = world.toGrid(gridSize)

                    val category = selectedCategory ?: return@detectTapGestures

                    onPlaceItem(grid, category)
                }
            }
    ) {

        Canvas(modifier = Modifier.fillMaxSize()) {

            val scaled = gridSize * scale

            val startX = offset.x % scaled
            val startY = offset.y % scaled

            val cols = (size.width / scaled).toInt() + 2
            val rows = (size.height / scaled).toInt() + 2

            for (x in 0..cols) {
                val xPos = startX + x * scaled
                drawLine(
                    Color.LightGray.copy(alpha = 0.3f),
                    Offset(xPos, 0f),
                    Offset(xPos, size.height)
                )
            }

            for (y in 0..rows) {
                val yPos = startY + y * scaled
                drawLine(
                    Color.LightGray.copy(alpha = 0.3f),
                    Offset(0f, yPos),
                    Offset(size.width, yPos)
                )
            }
        }
    }
}