package com.example.stardewlayoutplanner.ui.loadfarm

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.stardewlayoutplanner.ui.FarmViewModel
import com.example.stardewlayoutplanner.ui.nav.CreationScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoadFarmScreen(
    nav: NavHostController,
    vm: LoadViewModel = viewModel(),
    farmViewModel: FarmViewModel = viewModel()
) {
    LaunchedEffect(Unit) {
        vm.loadFarms()
    }

    val selectionMode by vm.selectionMode
    val hasSelection = vm.hasSelection()
    val showDialog by vm.showDialog
    val farms by vm.farmFiles

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                navigationIcon = {
                    IconButton(onClick = { nav.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            modifier = Modifier.size(45.dp)
                        )
                    }
                },
                // deleting farm
                actions = {
                    if (!selectionMode) {
                        TextButton(onClick = { vm.toggleSelectionMode() }) {
                            Text(
                                text = "Select",
                                fontSize = 24.sp,
                            )
                        }
                    } else {
                        if (hasSelection) {
                            IconButton(
                                onClick = { vm.showDeleteDialog() }
                            ) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    modifier = Modifier.size(45.dp)
                                )
                            }
                        }
                        TextButton(onClick = { vm.toggleSelectionMode() }) {
                            Text(
                                text = "Done",
                                fontSize = 24.sp,
                                )
                        }
                    }
                }
            )
        },
        containerColor = Color(0xFF90CAF9)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .fillMaxHeight(0.8f)
                    .background(Color.White)
                    .padding(vertical = 8.dp)
            ) {
                val listState = rememberLazyListState()

                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(
                        bottom = 16.dp,
                        top = 8.dp,
                        start = 12.dp,
                        end = 12.dp
                    )
                ) {
                    items(farms, key = { it.name }) { farm ->

                        val isSelected = vm.selectedFarms.contains(farm)

                        FarmFileRow(
                            farmFile = farm,
                            isSelected = isSelected,
                            onClick = {
                                if (selectionMode) {
                                    vm.toggleFarm(farm)
                                } else {
                                    farmViewModel.setFarm(farm)
                                    nav.navigate(CreationScreen)
                                }
                            }
                        )
                    }
                }
            }
        }

        // deletion dialog
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { vm.hideDeleteDialog() },
                title = { Text("Delete Farms?") },
                text = { Text("Are you sure you want to delete selected farms?") },
                confirmButton = {
                    Button(onClick = {
                        vm.deleteSelected()
                        vm.hideDeleteDialog()
                    }) {
                        Text("Delete")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { vm.hideDeleteDialog() }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
fun LoadFarmScreenPreview() {
    val context = LocalContext.current
    val dummyViewModel = LoadViewModel(app = context.applicationContext as Application)
    LoadFarmScreen(
        nav = rememberNavController(),
        vm = dummyViewModel
    )
}
