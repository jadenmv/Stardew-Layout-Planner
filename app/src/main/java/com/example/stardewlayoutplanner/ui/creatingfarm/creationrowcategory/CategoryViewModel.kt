package com.example.stardewlayoutplanner.ui.creatingfarm.creationrowcategory

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.stardewlayoutplanner.data.categories
import com.example.stardewlayoutplanner.data.model.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CategoryViewModel : ViewModel() {

    // Current visible categories (navigation state)
    private val _currentItems = MutableStateFlow(categories)
    val currentItems: StateFlow<List<Category>> = _currentItems

    private val _selectedItem = MutableStateFlow<Category?>(null)
    val selectedItem: StateFlow<Category?> = _selectedItem

    private val stack = mutableListOf<List<Category>>()

    fun onCategoryClick(category: Category) {
        if (category.subItems.isNotEmpty()) {
            // Navigate deeper into category tree
            stack.add(_currentItems.value)
            _currentItems.value = category.subItems
        } else {
            // Select item for placement on canvas
            _selectedItem.value = category
        }
    }

    fun goBack() {
        if (stack.isNotEmpty()) {
            _currentItems.value = stack.removeLast()
        }
    }

    fun clearSelection() {
        _selectedItem.value = null
    }
}