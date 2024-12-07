package com.example.tustracker.ui

import android.content.Intent
import android.net.Uri
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
import androidx.navigation.NavController
import com.example.tustracker.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactPage(navController: NavController) {
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
                            "Contact Us",
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
            ContactPageContent(Modifier.padding(innerPadding))
        }
    }
}

@Composable
fun ContactPageContent(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(color = Color(0xFFA49461)),
        horizontalAlignment = Alignment.CenterHorizontally // Centers content horizontally
    ) {
        // Header image
        Image(
            painter = painterResource(R.mipmap.ic_launcher_foreground),
            contentDescription = "TUS HEADER",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Descriptive message
        Text(
            text = "Need support ? Reach out to us anytime.",
            fontSize = 18.sp,
            color = Color.White,
            modifier = Modifier
                .padding(bottom = 32.dp),
            lineHeight = 24.sp
        )

        // Call Us Button
        Button(
            onClick = {
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:0877022543")
                }
                context.startActivity(intent)
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Call Us Directly",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }

        // Spacer for better layout separation
        Spacer(modifier = Modifier.padding(16.dp))

        // Additional Call-to-Action for email
        Text(
            text = "Or email us at: support@tustracker.com",
            fontSize = 16.sp,
            color = Color(0xFFF4E9D8),
            modifier = Modifier.padding(top = 16.dp),
            lineHeight = 22.sp
        )

        Spacer(modifier = Modifier.weight(1f))  // Push footer to the bottom

        // Footer section
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
