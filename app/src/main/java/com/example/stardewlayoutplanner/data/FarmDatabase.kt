package com.example.stardewlayoutplanner.data

import androidx.room3.*
import com.example.stardewlayoutplanner.data.model.Farm

@Dao
interface FarmDao {
    @Query("select * from farm")
    suspend fun getFarms(): List<Farm>

    @Insert
    suspend fun addFarm(farm: Farm)

    @Delete
    suspend fun deleteFarm(farm: Farm)

    @Update
    suspend fun updateFarm(farm: Farm)
}

@Database(entities = [Farm::class], version = 2, exportSchema = false)
abstract class FarmDatabase : RoomDatabase() {
    abstract fun farmDao(): FarmDao
}
