package com.example.stardewlayoutplanner.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.stardewlayoutplanner.ui.loadfarm.LoadViewModel

@Composable
fun CreationScreen(
    nav: NavHostController,
    farmViewModel: FarmViewModel,
    vm: LoadViewModel = viewModel(),
) {
    val farm = farmViewModel.farm.value

    // visual, temporary
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "This is ${farm?.name ?: "Unknown"} farm")
        Text(text = "Farm Type: ${farm?.type ?: "Unknown"}")
    }
}
