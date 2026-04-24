package com.example.stardewlayoutplanner.data

import com.example.stardewlayoutplanner.data.model.Farm

interface IFarmRepository {
    suspend fun getFarms(): List<Farm>
    suspend fun addFarm(farm: Farm)
    suspend fun deleteFarm(farm: Farm)

}