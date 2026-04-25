package com.example.stardewlayoutplanner.ui.loadfarm

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stardewlayoutplanner.data.model.Farm

@Composable
fun FarmFileRow(
    farmFile: Farm,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .background(
                if (isSelected) Color.Gray.copy(alpha = 0.3f)
                else Color(0xFFF5F5F5)
            )
            .padding(6.dp)
            .clickable{onClick()},
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .padding(end = 6.dp)
                .height(100.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Farm Name: \n${farmFile.name}",
                fontSize = 20.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Farm Type: ${farmFile.type}",
                fontSize = 18.sp,
                color = Color.DarkGray
            )
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            if (farmFile.imageRes != null) {
                Image(
                    painter = painterResource(id = farmFile.imageRes),
                    contentDescription = "Farm Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Text(text = "Image", color = Color.DarkGray)
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun FarmFileRowPreview() {
//    val sampleFarm = Farm(
//        name = "My Cool Farm",
//        type = "Standard",
//        imageRes = null
//    )
//    Surface {
//        FarmFileRow(farmFile = sampleFarm)
//    }
//}
