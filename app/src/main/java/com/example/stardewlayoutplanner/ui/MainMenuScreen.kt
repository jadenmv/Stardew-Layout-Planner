package com.example.stardewlayoutplanner.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.stardewlayoutplanner.ui.newfarm.NewFarmMenu
import com.example.stardewlayoutplanner.ui.newfarm.NewFarmViewModel

@Preview(showBackground = true)
@Composable
fun MainMenuScreen(
    nav: NavHostController = rememberNavController()
) {
    val newFarmViewModel: NewFarmViewModel = viewModel()

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
            // Top logo/title box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Stardew Layout Planenr (placeholder text)",
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }

            // Buttons
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
                    shape = RoundedCornerShape(2.dp)
                ) {
                    Text("New Farm")
                }

                Button(
                    onClick = {
                        nav.navigate("loadFarm")
                    },
                    modifier = Modifier
                        .width(180.dp)
                        .height(50.dp),
                    shape = RoundedCornerShape(2.dp)
                ) {
                    Text("Load Farm")
                }
            }

            // Bottom text
            Text(
                text = "Stardew Valley and its assets belong to ConcernedApe",
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp),
                textAlign = TextAlign.Center
            )
        }
        if (showNewMenu) {
            NewFarmMenu(
                newFarmViewModel = newFarmViewModel,
                onDismiss = { showNewMenu = false },
                onCreate = {
                    showNewMenu = false
                    nav.navigate("creationScreen")
                }
            )
        }
    }
}