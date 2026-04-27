package com.example.stardewlayoutplanner.ui.newfarm

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.stardewlayoutplanner.data.FarmTypes
import com.example.stardewlayoutplanner.data.model.Farm
import com.example.stardewlayoutplanner.ui.FarmViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewFarmDialog(
    farmViewModel: FarmViewModel,
    onDismiss: () -> Unit,
    onCreate: () -> Unit
) {
    var farmName by remember { mutableStateOf("") }
    var selectedFarmType by remember { mutableStateOf(FarmTypes.all.first()) }
    var expanded by remember { mutableStateOf(false) }
    var isNameError by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Create New Farm") },
        text = {
            Column {
                TextField(
                    value = farmName,
                    onValueChange = { 
                        farmName = it
                        if (isNameError && it.isNotBlank()) {
                            isNameError = false
                        }
                    },
                    label = { Text("Farm Name") },
                    isError = isNameError,
                    supportingText = {
                        if (isNameError) {
                            Text("Please enter a farm name")
                        }
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    TextField(
                        readOnly = true,
                        value = selectedFarmType.name,
                        onValueChange = {},
                        label = { Text("Farm Type") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        FarmTypes.all.forEach { type ->
                            DropdownMenuItem(
                                text = { Text(type.name) },
                                onClick = {
                                    selectedFarmType = type
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        },

        confirmButton = {
            Button(onClick = {
                if (farmName.isBlank()) {
                    isNameError = true
                } else {
                    val newFarm = Farm(
                        name = farmName,
                        type = selectedFarmType.name,
                        imageRes = selectedFarmType.imageRes
                    )
                    farmViewModel.createNewFarm(newFarm)
                    farmViewModel.setFarm(newFarm) // Update the selected farm in ViewModel
                    onCreate()
                    onDismiss()
                }
            }) {
                Text("Create")
            }
        },

        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
