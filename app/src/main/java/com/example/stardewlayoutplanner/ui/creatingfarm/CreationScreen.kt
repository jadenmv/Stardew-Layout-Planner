package com.example.stardewlayoutplanner.ui.creatingfarm

import android.annotation.SuppressLint
import android.app.Application
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.stardewlayoutplanner.data.categories
import com.example.stardewlayoutplanner.data.model.Category
import com.example.stardewlayoutplanner.data.model.Farm
import com.example.stardewlayoutplanner.ui.FarmViewModel
import com.example.stardewlayoutplanner.ui.creatingfarm.creationrowcategory.CategoryButton
import com.example.stardewlayoutplanner.ui.creatingfarm.creationrowcategory.CategoryViewModel
import com.example.stardewlayoutplanner.ui.loadfarm.LoadViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreationScreen(
    nav: NavHostController,
    farmViewModel: FarmViewModel,
    loadViewModel: LoadViewModel = viewModel(),
    categoryViewModel: CategoryViewModel = viewModel()
) {
    val farm = farmViewModel.selectedFarm.value

    var isCollapsed by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf<Category?>(null) }

    val items by categoryViewModel.currentItems.collectAsState()
    val isSubCategory = items != categories

    BackHandler {
        farmViewModel.saveDialog.showDialog()
    }

    // non-functional save dialog on exiting creation screen. connect to db later w/ json for items
    if (farmViewModel.saveDialog.showDialog.value) {
        AlertDialog(
            onDismissRequest = { farmViewModel.saveDialog.hideDialog() },
            title = { Text("Exit Farm?") },
            text = { Text("Would you like to save your farm before exiting?") },
            confirmButton = {
                Button(onClick = {
                    farmViewModel.saveDialog.hideDialog()
                    nav.popBackStack()
                }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    farmViewModel.saveDialog.hideDialog()
                    nav.popBackStack()
                }) {
                    Text("No")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = farm?.name ?: "Plan Your Farm") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = Color.Transparent
                ),
                navigationIcon = {
                    IconButton(onClick = { farmViewModel.saveDialog.showDialog() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .background(Color.Transparent),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (isSubCategory && !isCollapsed) {
                        Button(onClick = { categoryViewModel.goBack() }) {
                            Text("Back")
                        }
                    } else {
                        Spacer(modifier = Modifier.width(1.dp))
                    }

                    Button(onClick = { isCollapsed = !isCollapsed }) {
                        Text(if (isCollapsed) "Unhide" else "Hide")
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
                            CategoryButton(
                                category,
                                isSelected = category == selectedItem
                            ) {
                                if (category.subItems.isNotEmpty()) {
                                    categoryViewModel.onCategoryClick(category)
                                } else {
                                    selectedItem = category
                                }
                            }
                        }
                    }
                }
            }
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            CreationFarmCanvas(
                backgroundRes = farm?.imageRes
            )
        }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "Farm Type: ${farm?.type ?: "Unknown"}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
    }


@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun CreationScreenPreview() {
    val nav = rememberNavController()
    val context = LocalContext.current
    val farmViewModel = FarmViewModel(context.applicationContext as Application).apply {
        setFarm(Farm(name = "Preview Farm", type = "Standard"))
    }
    CreationScreen(
        nav = nav,
        farmViewModel = farmViewModel
    )
}
