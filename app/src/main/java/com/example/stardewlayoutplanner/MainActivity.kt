package com.example.stardewlayoutplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.stardewlayoutplanner.ui.MainMenuScreen
import com.example.stardewlayoutplanner.ui.theme.StardewLayoutPlannerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StardewLayoutPlannerTheme {
                MainMenuScreen()
            }
        }
    }
}