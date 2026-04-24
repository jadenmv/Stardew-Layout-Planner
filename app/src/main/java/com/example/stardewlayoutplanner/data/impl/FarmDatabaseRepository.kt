package com.example.stardewlayoutplanner.data.impl

import android.app.Application
import androidx.room3.Room
import androidx.room3.RoomDatabase
import com.example.stardewlayoutplanner.data.FarmDatabase
import com.example.stardewlayoutplanner.data.IFarmRepository
import com.example.stardewlayoutplanner.data.model.Farm

class FarmDatabaseRepository(app: Application): IFarmRepository {

    private val farmDatabase: FarmDatabase

    init {
        farmDatabase = Room.databaseBuilder(
            context = app,
            klass = FarmDatabase::class.java,
            name = "farm.db"
        ).fallbackToDestructiveMigration(true).build()
    }
    override suspend fun getFarms(): List<Farm> {
        return farmDatabase.farmDao().getFarms()
    }

    override suspend fun addFarm(farm: Farm) {
        farmDatabase.farmDao().addFarm(farm)
    }

    override suspend fun deleteFarm(farm: Farm) {
        farmDatabase.farmDao().deleteFarm(farm)
    }
}

