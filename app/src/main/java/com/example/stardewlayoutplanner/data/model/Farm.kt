package com.example.stardewlayoutplanner.data.model

import androidx.room3.*

@Entity(tableName = "farm")
data class Farm(
    @PrimaryKey
    val name: String,
    val type: String,
    val imageRes: Int? = null,
)
