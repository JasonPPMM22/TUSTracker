package com.example.tustracker.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.tustracker.R


@Composable
fun MainPage(navController: NavHostController) {
    val isPortrait =
        androidx.compose.ui.platform.LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(color = Color(0xFFA49461))
    ) {
        if (isPortrait) {
            Image(
                painter = painterResource(R.mipmap.ic_launcher_foreground),
                contentDescription = "TUS HEADER",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "TUSTracker",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome back!",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Layout Adjustment based on Orientation
        if (isPortrait) {
            PortraitLayout(navController)
        } else {
            LandscapeLayout(navController)
        }
    }
}


@Composable
fun PortraitLayout(navController: NavHostController) {
    Button(
        onClick = { /* Navigate to Mood Tracking page */ },
        colors = ButtonDefaults.buttonColors(Color.Black),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = "Mood Tracking", color = Color.White, fontSize = 18.sp
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    Button(
        onClick = { navController.navigate("goalSetting") },
        colors = ButtonDefaults.buttonColors(Color.Black),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = "Goal Setting", color = Color.White, fontSize = 18.sp
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    Button(
        onClick = { navController.navigate("wellnessPage") },
        colors = ButtonDefaults.buttonColors(Color.Black),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = "Wellness Map", color = Color.White, fontSize = 18.sp
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    Button(
        onClick = { navController.navigate("journalPage") },
        colors = ButtonDefaults.buttonColors(Color.Black),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = "Journal", color = Color.White, fontSize = 18.sp
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    Button(
        onClick = { navController.navigate("contactPage") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(Color.Black)
    ) {
        Text(
            text = "Contact Us",
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }

    Spacer(modifier = Modifier.height(32.dp))

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
fun LandscapeLayout(navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFA49461))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .padding(end = 16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = { /* Navigate to Mood Tracking page */ },
                colors = ButtonDefaults.buttonColors(Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(text = "Mood Tracking", color = Color.White, fontSize = 18.sp)
            }

            Button(
                onClick = { navController.navigate("goalSetting") },
                colors = ButtonDefaults.buttonColors(Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(text = "Goal Setting", color = Color.White, fontSize = 18.sp)
            }

            Button(
                onClick = { navController.navigate("wellnessPage") },
                colors = ButtonDefaults.buttonColors(Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(text = "Wellness Map", color = Color.White, fontSize = 18.sp)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = { navController.navigate("journalPage") },
                    colors = ButtonDefaults.buttonColors(Color.Black),
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                ) {
                    Text(text = "Journal", color = Color.White, fontSize = 18.sp)
                }

                Button(
                    onClick = { navController.navigate("contactPage") },
                    colors = ButtonDefaults.buttonColors(Color.Black),
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                ) {
                    Text(
                        text = "Contact Us",
                        fontWeight = FontWeight.Normal,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.mipmap.ic_launcher_foreground),
                contentDescription = "TUS HEADER",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
        }
    }
}





