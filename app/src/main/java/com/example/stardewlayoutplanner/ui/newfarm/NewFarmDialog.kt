package com.example.stardewlayoutplanner.ui.newfarm

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.stardewlayoutplanner.data.model.Farm
import com.example.stardewlayoutplanner.ui.FarmViewModel
import com.example.stardewlayoutplanner.ui.nav.CreationScreen
import com.example.stardewlayoutplanner.data.FarmTypes

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

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Create New Farm") },
        text = {
            Column {
                TextField(
                    value = farmName,
                    onValueChange = { farmName = it },
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
                        modifier = Modifier.menuAnchor().fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        FarmTypes.all.forEach { type ->
                            DropdownMenuItem(
                                text = { Text(type) },
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
                val newFarm = Farm(
                    name = farmName,
                    type = selectedFarmType
                )
                farmViewModel.createNewFarm(newFarm)

                onCreate()

                onDismiss()
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

//@SuppressLint("ViewModelConstructorInComposable")
//@Preview(showBackground = true)
//@Composable
//fun NewFarmMenuPreview() {
//    val viewModel = NewFarmViewModel()
//    NewFarmMenu(
//        newFarmViewModel = viewModel,
//        onDismiss = {},
//        onCreate = {}
//    )
//}
