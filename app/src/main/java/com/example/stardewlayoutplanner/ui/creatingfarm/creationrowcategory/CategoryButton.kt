package com.example.stardewlayoutplanner.ui.creatingfarm.creationrowcategory

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign
import com.example.stardewlayoutplanner.data.model.Category

@Composable
fun CategoryButton(
    category: Category,
    isSelected: Boolean,
    onClick: () -> Unit
){
    Button(
        onClick = onClick,
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier.size(80.dp)
    ) {
        if (category.imageRes != null && category.imageRes != 0) {
            Image(
                painter = painterResource(id = category.imageRes),
                contentDescription = category.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Text(
                text = category.name,
                modifier = Modifier.fillMaxSize(),
                textAlign = TextAlign.Center,
                maxLines = 2
            )
        }
    }
}