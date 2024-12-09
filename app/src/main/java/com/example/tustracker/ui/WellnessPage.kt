package com.example.tustracker.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
            WellnessPageContent(Modifier.padding(innerPadding))
        }
    }
}

@Composable
fun WellnessPageContent(modifier: Modifier = Modifier) {
    val orientation = LocalContext.current.resources.configuration.orientation

    if (orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE) {
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
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "TUSTracker",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Setting coordinates to TUS Mental Health building
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
                text = "TUSTracker",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

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
}
