package com.example.stardewlayoutplanner.ui

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.stardewlayoutplanner.data.IFarmRepository
import com.example.stardewlayoutplanner.data.impl.FarmDatabaseRepository
import com.example.stardewlayoutplanner.data.model.Farm
import kotlinx.coroutines.launch

class FarmViewModel(app: Application) : AndroidViewModel(app) {
    private val _farms: MutableState<List<Farm>> = mutableStateOf(listOf())
    val farms: State<List<Farm>> = _farms

    private val _selected: MutableState<Farm?> = mutableStateOf(null)
    val selectedFarm: State<Farm?> = _selected

    private val _waiting: MutableState<Boolean> = mutableStateOf(false)
    val waiting: State<Boolean> = _waiting

    private val _repository: IFarmRepository = FarmDatabaseRepository(app)

    init {
        loadFarms()
    }

    private fun loadFarms() {
        viewModelScope.launch {
            _waiting.value = true
            _farms.value = _repository.getFarms()
            _waiting.value = false
        }
    }

    fun createNewFarm(newFarm: Farm) {
        viewModelScope.launch {
            _waiting.value = true
            _repository.addFarm(newFarm)
            _farms.value = _repository.getFarms()
            _waiting.value = false
        }
    }

    fun deleteFarm(farm: Farm) {
        viewModelScope.launch {
            _waiting.value = true
            _repository.deleteFarm(farm)
            _farms.value = _repository.getFarms()
            _waiting.value = false
        }
    }

    fun setFarm(farm: Farm?) {
        _selected.value = farm
    }
}
