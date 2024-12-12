package com.example.tustracker.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tustracker.viewModel.MoodViewModel
import com.example.tustracker.classes.MoodClass
import com.example.tustracker.repository.MoodRepository
import com.example.tustracker.DAO.MoodDatabase
import androidx.compose.ui.platform.LocalContext
import com.example.tustracker.factory.MoodViewModelFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tustracker.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoodTrackerScreen(navController: NavController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    Navigation(
        drawerState = drawerState, navController = navController
    ) {
        Scaffold(topBar = {
            TopAppBar(title = { Text("Mood Tracker", color = Color.White) }, navigationIcon = {
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
            MoodTrackerContent(
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(color = Color(0xFFA49461))
            )
        }
    }
}

@Composable
fun MoodTrackerContent(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    var moodValue by remember { mutableStateOf(0f) } // Tracks the mood value on the slider
    val moodRepository = MoodRepository(MoodDatabase.getDatabase(context).todoDao())

    // ViewModel is initialized with the repository
    val moodViewModel: MoodViewModel = viewModel(factory = MoodViewModelFactory(moodRepository))
    // Observing the list of moods from the ViewModel
    val moodList by moodViewModel.allMoods.observeAsState(emptyList())


    if (isLandscape) {
        Row(
            modifier = modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                Header() // Displays the header image
                MoodTrackerBody(moodValue = moodValue,
                    onMoodChange = { moodValue = it },
                    onSaveMood = {
                        // Saves the current mood in the repository
                        val newMood = MoodClass(mood = moodValue)
                        moodViewModel.insert(newMood)
                        moodValue = 0f
                    },
                    moodList = moodList,
                    onDeleteMood = { moodViewModel.delete(it) } // Deletes a mood entry
                )
            }
        }
    } else {
        Column(
            modifier = modifier.verticalScroll(rememberScrollState())
        ) {
            Header()
            MoodTrackerBody(moodValue = moodValue,
                onMoodChange = { moodValue = it },
                onSaveMood = {
                    // Saves the current mood in the repository
                    val newMood = MoodClass(mood = moodValue)
                    moodViewModel.insert(newMood)
                    moodValue = 0f
                },
                moodList = moodList,
                onDeleteMood = { moodViewModel.delete(it) } // Deletes mood entry
            )
            Footer()
        }
    }
}

@Composable
fun Header() {
    Image(
        painter = painterResource(R.mipmap.ic_launcher_foreground),
        contentDescription = "TUS HEADER",
        contentScale = ContentScale.FillWidth,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    )
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun Footer() {
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

@Composable
fun MoodTrackerBody(
    moodValue: Float,
    onMoodChange: (Float) -> Unit,
    onSaveMood: () -> Unit,
    moodList: List<MoodClass>,
    onDeleteMood: (MoodClass) -> Unit
) {
    // Determine the mood category based on the mood value
    val moodCategory = when {
        moodValue <= 0.33 -> "Sad"
        moodValue <= 0.66 -> "Content"
        else -> "Happy"
    }

    // Calculate the weekly average moods
    val weeklyAverages =
        moodList.chunked(7) // Split the list into weeks (7 days) to allow for average
            .map { weekMoods ->
                weekMoods.map { it.mood }.average().toFloat()
            } // Calculate average mood for each week

    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Mood Tracker",
            fontSize = 20.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }

    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        // Displays mood icons
        Text("😢", fontSize = 24.sp)
        Text("😐", fontSize = 24.sp)
        Text("😊", fontSize = 24.sp)
    }

    Slider(
        value = moodValue,
        onValueChange = onMoodChange,
        valueRange = 0f..1f, // Mood range from 0 (sad) to 1 (happy)
        modifier = Modifier.fillMaxWidth()
    )

    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Current Mood: $moodCategory",
            fontSize = 16.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }

    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = onSaveMood,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Save Mood", color = Color.White)
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Display weekly averages
    if (weeklyAverages.isNotEmpty()) {
        Text(
            text = "Weekly Averages:",
            fontSize = 16.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        weeklyAverages.forEachIndexed { index, average ->
            // Categorize the weekly average mood
            val weeklyMoodCategory = when {
                average <= 0.33 -> "Sad"
                average <= 0.66 -> "Content"
                else -> "Happy"
            }
            Text(
                text = "Week ${index + 1}: $weeklyMoodCategory (Average: ${"%.2f".format(average)})",
                fontSize = 14.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
    }

    // Loop through the list of moods and display them with a delete button
    for (mood in moodList) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Mood: ${
                    when {
                        mood.mood <= 0.33 -> "Sad"
                        mood.mood <= 0.66 -> "Content"
                        else -> "Happy"
                    }
                } (${"%.2f".format(mood.mood)})", color = Color.White
            )
            IconButton(onClick = { onDeleteMood(mood) }) {
                Icon(Icons.Default.Close, contentDescription = "Delete", tint = Color.White)
            }
        }
    }
}



