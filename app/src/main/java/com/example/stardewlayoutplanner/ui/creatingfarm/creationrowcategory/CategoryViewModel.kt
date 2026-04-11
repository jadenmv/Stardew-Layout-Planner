package com.example.stardewlayoutplanner.ui.creatingfarm.creationrowcategory

import androidx.lifecycle.ViewModel
import com.example.stardewlayoutplanner.data.categories
import com.example.stardewlayoutplanner.data.model.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CategoryViewModel : ViewModel() {

    private val _currentItems = MutableStateFlow(categories)
    val currentItems: StateFlow<List<Category>> = _currentItems

    private val stack = mutableListOf<List<Category>>() // for back navigation

    fun onCategoryClick(category: Category) {
        if (category.subItems.isNotEmpty()) {
            stack.add(_currentItems.value)
            _currentItems.value = category.subItems
        }
    }

    fun goBack() {
        if (stack.isNotEmpty()) {
            _currentItems.value = stack.removeLast()
        }
    }
}