package com.example.stardewlayoutplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.stardewlayoutplanner.ui.nav.NavGraph
import com.example.stardewlayoutplanner.ui.theme.StardewLayoutPlannerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StardewLayoutPlannerTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}