package com.example.tustracker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tustracker.ui.*
import com.example.tustracker.ui.theme.TUSTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            // Notification Channel
            val channelId = "default_channel"
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    channelId, "Default Channel", NotificationManager.IMPORTANCE_DEFAULT
                )
                val notificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }

            TUSTrackerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    // Pass the ViewModel to the NavGraph
                    NavGraph(navController)
                }
            }
        }
    }
}

@Composable
fun NavGraph(navController: NavHostController) {
    val startPageViewModel: StartPageViewModel =
        viewModel() // Keep existing StartPageViewModel setup
    NavHost(navController, startDestination = "startPage") {
        composable("startPage") {
            StartPage(navController, startPageViewModel)
        }
        composable("registerPage") {
            RegisterPage(navController, startPageViewModel)
        }
        composable("mainPage") {
            MainPage(navController)
        }
        composable("wellnessPage") {
            WellnessPage(navController)
        }
        composable("contactPage") {
            ContactPage(navController)
        }
        composable("journalPage") {
            JournalPage(navController)
        }
        composable("addJournalPage") {
            AddJournalPage(navController, startPageViewModel)
        }

    }
}

