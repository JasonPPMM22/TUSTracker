package com.example.tustracker.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tustracker.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalTrackingPage(navController: NavController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val firestore = FirebaseFirestore.getInstance()

    // goals data from Firebase
    var goals by remember { mutableStateOf<List<Map<String, Any>>>(emptyList()) }

    // Fetch goals from Firebase
    LaunchedEffect(Unit) {
        firestore.collection("goals")
            .get()
            .addOnSuccessListener { result ->
                val fetchedGoals = result.documents.map { doc ->
                    mapOf(
                        "id" to doc.id,
                        "task" to (doc.getString("task") ?: "Unnamed Task"),
                        "dueDate" to (doc.getString("dueDate") ?: "No Due Date"),
                        "completed" to (doc.getBoolean("completed") ?: false)
                    )
                }
                goals = fetchedGoals
            }
    }

    Navigation(
        drawerState = drawerState,
        navController = navController
    ) {
        Scaffold(topBar = {
            TopAppBar(title = {
                Text("Goal Setting", color = Color.White)
            }, navigationIcon = {
                IconButton(onClick = {
                    coroutineScope.launch { drawerState.open() }
                }) {
                    Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.White)
                }
            }, colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Black))
        }) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                GoalTrackingPageContent(
                    navController = navController,
                    goals = goals,
                    onGoalClick = { goal ->
                        val goalId = goal["id"] as String
                        val currentCompleted = goal["completed"] as Boolean

                        // Update Firestore when goal is clicked (complete an activity)
                        firestore.collection("goals").document(goalId)
                            .update("completed", !currentCompleted)
                            .addOnSuccessListener {
                                goals = goals.map {
                                    if (it["id"] == goalId) it.toMutableMap().apply {
                                        this["completed"] = !currentCompleted
                                    } else it
                                }
                            }
                    }
                )
            }
        }
    }
}

@Composable
fun GoalTrackingPageContent(
    navController: NavController,
    goals: List<Map<String, Any>>,
    onGoalClick: (Map<String, Any>) -> Unit
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        Row(
            modifier = Modifier
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
            )
            {
                Text(
                    text = "Add Goal +",
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier
                        .padding(16.dp)
                        .background(Color.Black)
                        .padding(8.dp)
                        .clickable { navController.navigate("addGoalPage") }
                )

                Spacer(modifier = Modifier.height(16.dp))

                goals.forEach { goal ->
                    val task = goal["task"] as String
                    val dueDate = goal["dueDate"] as String
                    val completed = goal["completed"] as Boolean

                    val backgroundColor = if (completed) Color.Green else Color.Red

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .background(backgroundColor)
                            .clickable { onGoalClick(goal) }
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = task,
                                fontSize = 18.sp,
                                color = Color.White
                            )
                            Text(
                                text = "Due: $dueDate",
                                fontSize = 14.sp,
                                color = Color.White
                            )
                        }
                    }
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
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFFA49461))
                .verticalScroll(rememberScrollState()),
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
                text = "Add Goal +",
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.Black)
                    .padding(8.dp)
                    .clickable { navController.navigate("addGoalPage") }
            )

            goals.forEach { goal ->
                val task = goal["task"] as String
                val dueDate = goal["dueDate"] as String
                val completed = goal["completed"] as Boolean

                val backgroundColor = if (completed) Color.Green else Color.Red

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(backgroundColor)
                        .clickable { onGoalClick(goal) }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = task,
                            fontSize = 18.sp,
                            color = Color.White
                        )
                        Text(
                            text = "Due: $dueDate",
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
