package com.example.stardewlayoutplanner.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.stardewlayoutplanner.ui.creatingfarm.CreationScreen
import com.example.stardewlayoutplanner.ui.FarmViewModel
import com.example.stardewlayoutplanner.ui.MainMenuScreen
import com.example.stardewlayoutplanner.ui.loadfarm.LoadFarmScreen
import com.example.stardewlayoutplanner.ui.loadfarm.LoadViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val farmViewModel: FarmViewModel = viewModel()
    val loadViewModel: LoadViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = MainMenu,
        modifier = modifier
    ) {

        composable<MainMenu> {
            MainMenuScreen(
                nav = navController,
                farmViewModel = farmViewModel
            )
        }

        composable<LoadScreen> {
            LoadFarmScreen(
                nav = navController,
                vm = loadViewModel,
                farmViewModel = farmViewModel
            )
        }

        composable<CreationScreen> {
             CreationScreen(
                 nav = navController,
                 farmViewModel = farmViewModel
             )
        }
    }
}
