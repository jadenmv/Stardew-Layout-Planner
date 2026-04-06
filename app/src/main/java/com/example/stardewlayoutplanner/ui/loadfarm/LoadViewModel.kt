package com.example.stardewlayoutplanner.ui.loadfarm


import androidx.lifecycle.ViewModel
import com.example.stardewlayoutplanner.data.model.Farm
import com.example.stardewlayoutplanner.data.impl.farmRepository

class LoadViewModel : ViewModel() {

    val farmFiles: List<Farm> get() = farmRepository.getFarms()
}