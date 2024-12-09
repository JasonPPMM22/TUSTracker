package com.example.tustracker.ui

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
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
import androidx.compose.material3.TextFieldDefaults.textFieldColors as textFieldColors1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddJournalPage(
    navController: NavController,
    viewModel: StartPageViewModel = androidx.lifecycle.viewmodel.compose.viewModel() // Explicitly call viewModel() with proper import
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    Navigation(
        drawerState = drawerState, navController = navController
    ) {
        Scaffold(topBar = {
            TopAppBar(title = {
                Text(
                    "Add Journal", color = Color.White
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
            AddJournalPageContent(
                modifier = Modifier.padding(innerPadding),
                navController = navController,
                viewModel = viewModel // Pass the viewModel explicitly to the content function
            )
        }
    }
}

@Composable
fun AddJournalPageContent(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: StartPageViewModel // Accept viewModel as a parameter
) {
    val title = remember { mutableStateOf("") }
    val content = remember { mutableStateOf("") }
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) { // Checking for what orientation phone is in
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
            ) {
                Text(
                    text = "Create a New Journal Entry",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(value = title.value,
                    onValueChange = { title.value = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = content.value,
                    onValueChange = { content.value = it },
                    label = { Text("Content") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (title.value.isNotEmpty() && content.value.isNotEmpty()) {
                            viewModel.saveJournalToFirebase(title = title.value,
                                content = content.value,
                                onSuccess = {
                                    Toast.makeText(context, "Journal Saved", Toast.LENGTH_SHORT)
                                        .show()
                                    navController.popBackStack()
                                },
                                onFailure = { error ->
                                    Toast.makeText(
                                        context, "Error saving journal: $error", Toast.LENGTH_SHORT
                                    ).show()
                                })
                        } else {
                            Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT)
                                .show()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                ) {
                    Text(
                        text = "Save",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Image(
                painter = painterResource(R.mipmap.ic_launcher_foreground),
                contentDescription = "TUS HEADER",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
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
                text = "Create a New Journal Entry",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = title.value,
                onValueChange = { title.value = it },
                label = { Text("Title") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = content.value,
                onValueChange = { content.value = it },
                label = { Text("Content") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(200.dp),
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (title.value.isNotEmpty() && content.value.isNotEmpty()) {
                        viewModel.saveJournalToFirebase(title = title.value,
                            content = content.value,
                            onSuccess = {
                                Toast.makeText(context, "Journal Saved", Toast.LENGTH_SHORT).show()
                                navController.popBackStack()
                            },
                            onFailure = { error ->
                                Toast.makeText(
                                    context, "Error saving journal: $error", Toast.LENGTH_SHORT
                                ).show()
                            })
                    } else {
                        Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT)
                            .show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                Text(
                    text = "Save",
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


