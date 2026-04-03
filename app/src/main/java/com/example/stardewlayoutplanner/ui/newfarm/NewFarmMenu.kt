package com.example.stardewlayoutplanner.ui.newfarm

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stardewlayoutplanner.data.FarmTypes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewFarmMenu(
    newFarmViewModel: NewFarmViewModel,
    onDismiss: () -> Unit,
    onCreate: () -> Unit
) {
    val farmName by newFarmViewModel.farmName.collectAsState()
    val selectedFarmType by newFarmViewModel.farmType.collectAsState()
    var expanded by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Create New Farm") },
        text = {
            Column {
                TextField(
                    value = farmName,
                    onValueChange = newFarmViewModel::setFarmName,
                    label = { Text("Farm Name") },
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
                        value = selectedFarmType,
                        onValueChange = {},
                        label = { Text("Farm Type") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        FarmTypes.all.forEach { type ->
                            DropdownMenuItem(
                                text = { Text(type) },
                                onClick = {
                                    newFarmViewModel.setFarmType(type)
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = onCreate) {
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

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun NewFarmMenuPreview() {
    val viewModel = NewFarmViewModel()
    NewFarmMenu(
        newFarmViewModel = viewModel,
        onDismiss = {},
        onCreate = {}
    )
}
