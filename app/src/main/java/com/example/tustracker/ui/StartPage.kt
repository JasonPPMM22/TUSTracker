package com.example.tustracker.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tustracker.R
import com.example.tustracker.ui.theme.TUSTrackerTheme
import java.util.Locale

@Composable
fun StartPage(navController: NavHostController, viewModel: StartPageViewModel = viewModel()) {
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    if (isPortrait) {
        PortraitLayout(navController, viewModel)
    } else {
        LandscapeLayout(navController, viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortraitLayout(navController: NavHostController, viewModel: StartPageViewModel) {
    var kemail by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
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
            modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "TUSTracker",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = kemail,
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { kemail = it },
                    label = { Text(text = "K-Number", fontSize = 18.sp) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.surface,
                        unfocusedBorderColor = MaterialTheme.colorScheme.surface
                    )
                )

                OutlinedTextField(
                    value = password,
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { password = it },
                    label = { Text(text = "Password", fontSize = 18.sp) },
                    visualTransformation = PasswordVisualTransformation(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.surface,
                        unfocusedBorderColor = MaterialTheme.colorScheme.surface
                    )
                )

                Button(
                    onClick = {
                        viewModel.loginUser(
                            kemail.lowercase(Locale.getDefault()), password
                        ) { // calling the viewModel to login the user
                            navController.navigate("mainPage")
                        }
                    }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black, contentColor = Color.White
                    )
                ) {
                    Text(text = "Sign In")
                }

                Button(
                    onClick = {
                        navController.navigate("registerPage")
                    }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black, contentColor = Color.White
                    )
                ) {
                    Text(text = "Register")
                }
            }
        }

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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandscapeLayout(navController: NavHostController, viewModel: StartPageViewModel) {
    var kemail by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFA49461)),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.mipmap.ic_launcher_foreground),
                contentDescription = "TUS HEADER",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Text(
                text = "TUSTracker",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Column(
            modifier = Modifier
                .weight(2f)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = kemail,
                onValueChange = { kemail = it },
                label = { Text("K-Number") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.loginUser(kemail.lowercase(Locale.getDefault()), password) {
                        navController.navigate("mainPage")
                    }
                }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF000000), contentColor = Color.White
                )
            ) {
                Text("Sign In")
            }

            Button(
                onClick = { navController.navigate("registerPage") },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF000000), contentColor = Color.White
                )
            ) {
                Text("Register")
            }
        }
    }
}


@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun PortraitPreview() {
    TUSTrackerTheme {
        StartPage(navController = rememberNavController())
    }
}

@Preview(showBackground = true, widthDp = 640, heightDp = 360)
@Composable
fun LandscapePreview() {
    TUSTrackerTheme {
        StartPage(navController = rememberNavController())
    }
}
