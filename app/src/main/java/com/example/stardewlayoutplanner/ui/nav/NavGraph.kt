package com.example.stardewlayoutplanner.ui.nav


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.stardewlayoutplanner.ui.MainMenuScreen
import com.example.stardewlayoutplanner.ui.loadfarm.LoadFarmScreen
import com.example.stardewlayoutplanner.ui.newfarm.NewFarmViewModel

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    val newFarmViewModel: NewFarmViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = MainMenu,
        modifier = modifier
    ) {
        composable<MainMenu> {
            MainMenuScreen()
        }

        composable<LoadScreen> {
            LoadFarmScreen(navController = navController)
        }

    }
}