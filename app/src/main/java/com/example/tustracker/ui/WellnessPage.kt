package com.example.tustracker.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.tustracker.R
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WellnessPage(navController: NavHostController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    Navigation(
        drawerState = drawerState,
        navController = navController
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "Wellness Page",
                            color = Color.White // Text color set to white for contrast
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            coroutineScope.launch { drawerState.open() }
                        }) {
                            Icon(
                                Icons.Default.Menu,
                                contentDescription = "Menu",
                                tint = Color.White // Icon color set to white
                            )
                        }
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = Color.Black // Background color set to black
                    )
                )
            }
        ) { innerPadding ->
            WellnessPageContent(Modifier.padding(innerPadding))
        }
    }
}


@Composable
fun WellnessPageContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(color = Color(0xFFA49461))
    ) {
        Image(
            painter = painterResource(R.mipmap.ic_launcher_foreground),
            contentDescription = "TUS HEADER",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "TUSTracker",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Setting TUS mental health facilities coordinates
        val tusCoordinates = LatLng(52.67439860312246, -8.646704085318401)
        val cameraPositionState = rememberCameraPositionState {
            position = com.google.android.gms.maps.model.CameraPosition.fromLatLngZoom(
                tusCoordinates, 15f
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(16.dp)
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            ) {
                Marker(
                    state = com.google.maps.android.compose.MarkerState(position = tusCoordinates),
                    title = "TUS Mental health facilities",
                    snippet = "Open to all TUS Students!"
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Spacer(Modifier.weight(1f))
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

