package com.example.stardewlayoutplanner.ui


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.stardewlayoutplanner.data.model.Farm
import com.example.stardewlayoutplanner.data.impl.farmRepository

class FarmViewModel : ViewModel() {

    private val _farm = mutableStateOf<Farm?>(null)
    val farm: State<Farm?> = _farm

    fun createNewFarm(newFarm: Farm) {
        _farm.value = newFarm
        farmRepository.addFarm(newFarm)
    }

    fun setFarm(farm: Farm) {
        _farm.value = farm
    }

    fun clearFarm() {
        _farm.value = null
    }
}