package com.example.stardewlayoutplanner.data

import com.example.stardewlayoutplanner.data.model.Farm

interface IFarmRepository {
    fun getFarms(): List<Farm>
    fun addFarm(farm: Farm)
    fun deleteFarm(farm: Farm)
}