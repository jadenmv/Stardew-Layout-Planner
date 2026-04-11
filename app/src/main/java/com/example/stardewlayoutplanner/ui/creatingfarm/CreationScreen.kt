package com.example.stardewlayoutplanner.ui.creatingfarm

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.stardewlayoutplanner.data.categories
import com.example.stardewlayoutplanner.data.model.Farm
import com.example.stardewlayoutplanner.ui.FarmViewModel
import com.example.stardewlayoutplanner.ui.creatingfarm.creationrowcategory.CategoryButton
import com.example.stardewlayoutplanner.ui.creatingfarm.creationrowcategory.CategoryViewModel
import com.example.stardewlayoutplanner.ui.loadfarm.LoadViewModel

@Composable
fun CreationScreen(
    nav: NavHostController,
    farmViewModel: FarmViewModel,
    loadViewModel: LoadViewModel = viewModel(),
    categoryViewModel: CategoryViewModel = viewModel()
) {
    val farm = farmViewModel.farm.value

    var isCollapsed by remember { mutableStateOf(false) }

    val items by categoryViewModel.currentItems.collectAsState()
    val isSubCategory = items != categories

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "This is ${farm?.name ?: "Unknown"} farm")
            Text(text = "Farm Type: ${farm?.type ?: "Unknown"}")
            Spacer(modifier = Modifier.height(16.dp))
        }

// --- bottom nav row ---

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                    //.padding(horizontal = 8.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isSubCategory && !isCollapsed) {
                    Button(onClick = { categoryViewModel.goBack() }) {
                        Text("Back")
                    }
                } else {
                    Spacer(modifier = Modifier.width(1.dp))
                }

                Button(onClick = { isCollapsed = !isCollapsed }) {
                    Text(if (isCollapsed) "up" else "down")
                }
            }

            if (!isCollapsed) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .background(Color.LightGray.copy(alpha = 0.6f)),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(items) { category ->
                        CategoryButton(category) {
                            categoryViewModel.onCategoryClick(category)
                        }
                    }
                }
            }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun CreationScreenPreview() {
    val nav = rememberNavController()
    val farmViewModel = FarmViewModel().apply {
        setFarm(Farm(name = "Preview Farm", type = "Standard"))
    }
    CreationScreen(
        nav = nav,
        farmViewModel = farmViewModel
    )
}
