package com.example.stardewlayoutplanner.data

import com.example.stardewlayoutplanner.R

data class FarmType(
    val name: String,
    val imageRes: Int
)

object FarmTypes {
    val all = listOf(
        FarmType("Standard", R.drawable.stardewvalley_farm_standard),
        FarmType("Hilltop", R.drawable.stardewvalley_farm_hilltop),
        FarmType("Riverland", R.drawable.stardewvalley_farm_riverland),
        FarmType("Forest", R.drawable.stardewvalley_farm_forest),
        FarmType("Beach", R.drawable.stardewvalley_farm_beach),
        FarmType("Four Corners", R.drawable.stardewvalley_farm_fourcorners)
    )
}
