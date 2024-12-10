package com.example.tustracker.ui

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tustracker.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddGoalPage(
    navController: NavController,
    viewModel: StartPageViewModel = viewModel()
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    Navigation(
        drawerState = drawerState, navController = navController
    ) {
        Scaffold(topBar = {
            TopAppBar(title = {
                Text(
                    "Add Goal", color = Color.White
                )
            }, navigationIcon = {
                IconButton(onClick = {
                    coroutineScope.launch { drawerState.open() }
                }) {
                    Icon(
                        Icons.Default.Menu, contentDescription = "Menu", tint = Color.White
                    )
                }
            }, colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = Color.Black
            )
            )
        }) { innerPadding ->

            AddGoalPageContent(
                modifier = Modifier.padding(innerPadding),
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun AddGoalPageContent(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: StartPageViewModel
) {
    val task = remember { mutableStateOf("") }
    val dueDate = remember { mutableStateOf("") }
    val context = LocalContext.current
    val configuration = LocalConfiguration.current

    // Determine if the current orientation is landscape
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    // Layout for landscape orientation
    if (isLandscape) {
        Row(
            modifier = modifier
                .fillMaxSize()
                .background(color = Color(0xFFA49461))
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.Start
            ){
                Text(
                    text = "Add a New Goal",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = task.value,
                    onValueChange = { task.value = it },
                    label = { Text("Task") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = dueDate.value,
                    onValueChange = { dueDate.value = it },
                    label = { Text("Due Date (YYYY-MM-DD)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (task.value.isNotEmpty() && dueDate.value.isNotEmpty()) {
                            viewModel.saveGoalToFirebase(
                                task = task.value,
                                dueDate = dueDate.value,
                                onSuccess = {
                                    Toast.makeText(context, "Goal Saved", Toast.LENGTH_SHORT)
                                        .show()
                                    navController.popBackStack()
                                },
                                onFailure = { error ->
                                    Toast.makeText(
                                        context, "Error saving goal: $error", Toast.LENGTH_SHORT
                                    ).show()
                                }
                            )
                        } else {
                            Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                ) {
                    Text(
                        text = "Save Goal",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            Image(
                painter = painterResource(R.mipmap.ic_launcher_foreground),
                contentDescription = "TUS HEADER",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(color = Color(0xFFA49461)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.mipmap.ic_launcher_foreground),
                contentDescription = "TUS HEADER",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Add a New Goal",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = task.value,
                onValueChange = { task.value = it },
                label = { Text("Task") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = dueDate.value,
                onValueChange = { dueDate.value = it },
                label = { Text("Due Date (YYYY-MM-DD)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (task.value.isNotEmpty() && dueDate.value.isNotEmpty()) {
                        viewModel.saveGoalToFirebase(
                            task = task.value,
                            dueDate = dueDate.value,
                            onSuccess = {
                                Toast.makeText(context, "Goal Saved", Toast.LENGTH_SHORT).show()
                                navController.popBackStack()
                            },
                            onFailure = { error ->
                                Toast.makeText(
                                    context, "Error saving goal: $error", Toast.LENGTH_SHORT
                                ).show()
                            }
                        )
                    } else {
                        Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                Text(
                    text = "Save Goal",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(R.drawable.tusfooter),
                contentDescription = "TUS FOOTER",
                contentScale = ContentScale.FillWidth,
                colorFilter = ColorFilter.tint(Color.Transparent),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )
        }
    }
}



