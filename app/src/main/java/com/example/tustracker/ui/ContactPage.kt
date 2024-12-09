package com.example.tustracker.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.horizontalScroll
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
import android.content.res.Configuration
import androidx.compose.ui.platform.LocalConfiguration
import com.example.tustracker.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactPage(navController: NavController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    Navigation(
        drawerState = drawerState, navController = navController
    ) {
        Scaffold(topBar = {
            TopAppBar(title = {
                Text(
                    "Contact Us", color = Color.White
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
            ContactPageContent(Modifier.padding(innerPadding))
        }
    }
}

@Composable
fun ContactPageContent(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

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
            ) {
                Text(
                    text = "Need support? Reach out to us anytime.",
                    fontSize = 18.sp,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 32.dp),
                    lineHeight = 24.sp
                )

                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_DIAL).apply { // On click bring user to call with phone number insterted
                            data = Uri.parse("tel:0877022543")
                        }
                        context.startActivity(intent)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .wrapContentWidth()
                ) {
                    Text(
                        text = "Call Us Directly",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Or email us at: support@tustracker.com",
                    fontSize = 16.sp,
                    color = Color(0xFFF4E9D8),
                    modifier = Modifier.padding(top = 16.dp),
                    lineHeight = 22.sp
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
                text = "Need support? Reach out to us anytime.",
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 32.dp),
                lineHeight = 24.sp
            )

            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:0877022543")
                    }
                    context.startActivity(intent)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .wrapContentWidth()
            ) {
                Text(
                    text = "Call Us Directly",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.padding(16.dp))

            Text(
                text = "Or email us at: support@tustracker.com",
                fontSize = 16.sp,
                color = Color(0xFFF4E9D8),
                modifier = Modifier.padding(top = 16.dp),
                lineHeight = 22.sp
            )

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

