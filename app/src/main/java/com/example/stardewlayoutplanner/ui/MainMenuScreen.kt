package com.example.stardewlayoutplanner.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.stardewlayoutplanner.ui.newfarm.NewFarmDialog
import com.example.stardewlayoutplanner.ui.nav.CreationScreen
import com.example.stardewlayoutplanner.ui.nav.LoadScreen
import com.example.stardewlayoutplanner.ui.nav.MainMenu

@Composable
fun MainMenuScreen(
    farmViewModel: FarmViewModel = viewModel(),
    nav: NavHostController = rememberNavController()
) {
    var showNewMenu by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF90CAF9))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Stardew Layout Planner",
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { showNewMenu = true },
                    modifier = Modifier
                        .width(180.dp)
                        .height(50.dp),
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Text("New")
                }

                Button(
                    onClick = {
                        nav.navigate(LoadScreen) {
                            launchSingleTop = true
                            popUpTo<MainMenu>()
                        }
                    },
                    modifier = Modifier
                        .width(180.dp)
                        .height(50.dp),
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Text("Load")
                }
            }

            Text(
                text = "Stardew Valley and its assets belong to ConcernedApe",
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp),
                textAlign = TextAlign.Center
            )
        }

        if (showNewMenu) {
            NewFarmDialog(
                farmViewModel = farmViewModel,
                onDismiss = { showNewMenu = false },
                onCreate = {
                    showNewMenu = false
                    nav.navigate(CreationScreen) {
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}
