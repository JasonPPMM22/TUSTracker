package com.example.tustracker.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(
    drawerState: DrawerState, navController: NavController, // Navigation controller
    content: @Composable () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState, drawerContent = {
            Box(
                modifier = Modifier
                    .width(240.dp)
                    .fillMaxHeight()
                    .background(Color.Black)
                    .padding(16.dp)
            ) {
                Column {
                    Text("Menu", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Divider()
                    // Main Page
                    DropdownMenuItem(
                        onClick = {
                            navController.navigate("mainPage")
                            coroutineScope.launch { drawerState.close() }
                        },
                        text = { Text("Main Page") },

                        )
                    // Wellness Page
                    DropdownMenuItem(
                        onClick = {
                            navController.navigate("wellnessPage")
                            coroutineScope.launch { drawerState.close() }
                        },
                        text = { Text("Wellness Page") },

                        )
                    // Contact Page
                    DropdownMenuItem(
                        onClick = {
                            navController.navigate("contactPage")
                            coroutineScope.launch { drawerState.close() }
                        },
                        text = { Text("Contact Page") },

                        )
                    // Mood Tracking
                    DropdownMenuItem(
                        onClick = {
                            navController.navigate("moodTracking")
                            coroutineScope.launch { drawerState.close() }
                        },
                        text = { Text("Mood Tracking") },

                        )
                    // Goal Setting
                    DropdownMenuItem(
                        onClick = {
                            navController.navigate("goalSetting")
                            coroutineScope.launch { drawerState.close() }
                        },
                        text = { Text("Goal Setting") },
                    )
                    // Journal Page
                    DropdownMenuItem(
                        onClick = {
                            navController.navigate("journalPage")
                            coroutineScope.launch { drawerState.close() }
                        },
                        text = { Text("Journal Page") },
                    )
                }
            }
        }, content = content
    )
}
