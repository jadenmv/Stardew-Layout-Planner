package com.example.stardewlayoutplanner.data

import com.example.stardewlayoutplanner.data.model.Category
import com.example.stardewlayoutplanner.R

val crops = Category(
    name = "Crops",
    imageRes = null, //R.drawable.
    subItems = listOf(
        Category(name = "pumpkin", imageRes = R.drawable.pumpkin),
        Category(name = "corn"),
        Category(name = "cranberry"),
        Category(name = "cauliflower"),
        Category(name = "pepper"),
    )
)

val equipment = Category(
    name = "Equipment",
    imageRes = null, //R.drawable.
    subItems = listOf(
        Category(name = "furnace"),
        Category(name = "chest"),
        Category(name = "scarecrow"),
        Category(name = "keg"),
        Category(name = "cask"),
    )
)

//val paths = Category(
//    name = "Paths",
//    imageRes = null, //R.drawable.
//    subItems = listOf(
//        Category(name = ""
//        )
//    )
//)

val furniture = Category(
    name = "Furniture",
    imageRes = null, //R.drawable.
    subItems = listOf(
        Category(name = "table"),
        Category(name = "chair"),
        Category(name = "lamp"),
        Category(name = "pot"),
        Category(name = "bed"),
    )
)

val categories = listOf(
    crops,
    equipment,
    furniture,
//    paths
)
