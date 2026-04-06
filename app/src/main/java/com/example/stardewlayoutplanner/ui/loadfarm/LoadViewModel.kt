package com.example.stardewlayoutplanner.ui.loadfarm

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.stardewlayoutplanner.data.impl.farmRepository
import com.example.stardewlayoutplanner.data.model.Farm

class LoadViewModel : ViewModel() {

    private val repository = farmRepository

    val farmFiles: List<Farm>
        get() = repository.getFarms()

    private val _selectedFarm = mutableStateOf<Farm?>(null)
    val selectedFarm: State<Farm?> = _selectedFarm

    fun selectFarm(farm: Farm) {
        _selectedFarm.value = farm
    }

}
