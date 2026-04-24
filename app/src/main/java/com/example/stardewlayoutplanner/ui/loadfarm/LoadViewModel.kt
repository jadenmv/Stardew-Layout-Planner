package com.example.stardewlayoutplanner.ui.loadfarm

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.stardewlayoutplanner.data.impl.FarmDatabaseRepository
import com.example.stardewlayoutplanner.data.model.Farm
import kotlinx.coroutines.launch

class LoadViewModel(app: Application) : AndroidViewModel(app) {

    private val repository = FarmDatabaseRepository(app)

    private val _farmFiles = mutableStateOf<List<Farm>>(listOf())
    val farmFiles: List<Farm> get() = _farmFiles.value

    private val _selectedFarm = mutableStateOf<Farm?>(null)
    val selectedFarm: State<Farm?> = _selectedFarm

    init {
        loadFarms()
    }

    fun loadFarms() {
        viewModelScope.launch {
            _farmFiles.value = repository.getFarms()
        }
    }

    fun selectFarm(farm: Farm) {
        _selectedFarm.value = farm
    }
}
