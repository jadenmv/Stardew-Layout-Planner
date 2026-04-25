package com.example.stardewlayoutplanner.data.impl

import androidx.compose.runtime.mutableStateListOf
import com.example.stardewlayoutplanner.data.IFarmRepository
import com.example.stardewlayoutplanner.data.model.Farm
import kotlinx.coroutines.delay

val farmRepository: IFarmRepository = FarmRepository()
class FarmRepository : IFarmRepository {

    private val farms = mutableStateListOf<Farm>()

    override suspend fun getFarms(): List<Farm> {
        return farms
    }

    override suspend fun addFarm(farm: Farm) {
        farms.add(farm)
    }

    override suspend fun deleteFarm(farm: Farm) {
        delay(2000)
        farms.remove(farm)
    }
}