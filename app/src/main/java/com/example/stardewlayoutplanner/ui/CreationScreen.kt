package com.example.stardewlayoutplanner.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun CreationScreen(
    nav: NavHostController,
    farmViewModel: FarmViewModel
) {
    val farm = farmViewModel.farm.value
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "This is ${farm?.name ?: "Unknown Farm"}")
    }
}
