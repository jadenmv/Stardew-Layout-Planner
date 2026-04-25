package com.example.stardewlayoutplanner.ui.loadfarm

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.stardewlayoutplanner.data.impl.FarmDatabaseRepository
import com.example.stardewlayoutplanner.data.model.Farm
import kotlinx.coroutines.launch

class LoadViewModel(app: Application) : AndroidViewModel(app) {

    private val repository = FarmDatabaseRepository(app)

    private val _farmFiles = mutableStateOf<List<Farm>>(emptyList())
    val farmFiles: State<List<Farm>> = _farmFiles

    var selectionMode = mutableStateOf(false)
        private set

    var showDialog = mutableStateOf(false)
        private set

    private val _selectedFarms = mutableStateListOf<Farm>()
    val selectedFarms: List<Farm> get() = _selectedFarms

    init {
        loadFarms()
    }

    fun loadFarms() {
        viewModelScope.launch {
            _farmFiles.value = repository.getFarms()
        }
    }

    fun toggleFarm(farm: Farm) {
        if (_selectedFarms.contains(farm)) {
            _selectedFarms.remove(farm)
        } else {
            _selectedFarms.add(farm)
        }
    }

    fun hasSelection(): Boolean {
        return _selectedFarms.isNotEmpty()
    }

    fun toggleSelectionMode() {
        selectionMode.value = !selectionMode.value

        if (!selectionMode.value) {
            _selectedFarms.clear()
        }
    }

    fun showDeleteDialog() {
        showDialog.value = true
    }

    fun hideDeleteDialog() {
        showDialog.value = false
    }

    fun deleteSelected() {
        viewModelScope.launch {
            _selectedFarms.forEach {
                repository.deleteFarm(it)
            }
            _selectedFarms.clear()
            loadFarms()
        }
    }
}
