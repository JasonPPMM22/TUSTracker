package com.example.tustracker.ui

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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tustracker.R
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalPage(navController: NavController, viewModel: StartPageViewModel = viewModel()) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    // Observing the journals LiveData from the ViewModel
    val journals by viewModel.journals.observeAsState(emptyList())

    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var selectedJournal by remember { mutableStateOf<Journal?>(null) }

    Navigation(
        drawerState = drawerState,
        navController = navController
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "Journal",
                            color = Color.White
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            coroutineScope.launch { drawerState.open() }
                        }) {
                            Icon(
                                Icons.Default.Menu,
                                contentDescription = "Menu",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = Color.Black
                    )
                )
            }
        ) { innerPadding ->
            JournalPageContent(
                modifier = Modifier.padding(innerPadding),
                navController = navController,
                journals = journals,
                onJournalClick = { journal ->
                    selectedJournal = journal
                    showDialog = true
                }
            )
        }

        // Pop-up Dialog to show journal content
        if (showDialog && selectedJournal != null) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = {
                    Text(text = "Journal: ${selectedJournal?.getFormattedDate()}")
                },
                text = {
                    Column {
                        Text(text = "Title: ${selectedJournal?.title}")
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Content: ${selectedJournal?.content}")
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Close")
                    }
                }
            )
        }
    }
}

@Composable
fun JournalPageContent(
    modifier: Modifier = Modifier,
    navController: NavController,
    journals: List<Journal>,
    onJournalClick: (Journal) -> Unit
) {
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

        // "Add Journal +" text
        Text(
            text = "Add Journal +",
            fontSize = 20.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(16.dp)
                .background(Color.Black)
                .padding(8.dp)
                .clickable {
                    navController.navigate("addJournalPage")
                }
        )

        // Display the list of journals
        journals.forEach { journal ->
            // Use the getFormattedDate function to display the journal date
            val formattedDate = journal.getFormattedDate()

            // Display each journal as a clickable box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.White)
                    .clickable { onJournalClick(journal) }
            ) {
                Text(
                    text = "Journal: $formattedDate",
                    modifier = Modifier.padding(16.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))  // Push footer to the bottom

        // Footer section
        Image(
            painter = painterResource(R.drawable.tusfooter),
            contentDescription = "TUS FOOTER",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        )
    }
}


data class Journal(
    val title: String,
    val content: String,
    val timestamp: Long, // Use Long for timestamp
    val userId: String
) {
    // Convert timestamp to Date when required
    fun getFormattedDate(): String {
        val date = Date(timestamp)
        val dateFormatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        return dateFormatter.format(date)
    }
}
