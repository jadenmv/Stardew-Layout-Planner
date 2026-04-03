package com.example.stardewlayoutplanner.ui.newfarm

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NewFarmViewModel : ViewModel() {
    private val _farmName = MutableStateFlow("")
    val farmName: StateFlow<String> = _farmName

    private val _farmType = MutableStateFlow("Standard")
    val farmType: StateFlow<String> = _farmType

    fun setFarmName(name: String) {
        _farmName.value = name
    }

    fun setFarmType(type: String) {
        _farmType.value = type
    }
}