package com.example.stardewlayoutplanner.ui.loadfarm

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.stardewlayoutplanner.data.model.Farm

class LoadViewModel : ViewModel() {

    private val _farmFiles = mutableStateListOf<Farm>()
    val farmFiles: List<Farm> get() = _farmFiles

    init {
        _farmFiles.addAll(
            listOf(
                Farm(1, "New Farm", "Beach"),
                Farm(2, "Cool farm", "Standard"),
                Farm(3, "Barn", "Hilltop"),
            )
        )
    }
}