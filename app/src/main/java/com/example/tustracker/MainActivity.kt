package com.example.tustracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
<<<<<<< Updated upstream
import com.example.tustracker.ui.RegisterPage
import com.example.tustracker.ui.StartPage
import com.example.tustracker.ui.StartPageViewModel
import com.mad.tusmoylishgym.ui.theme.TUSTrackerTheme
=======
import com.example.tustracker.ui.*
import com.example.tustracker.ui.theme.TUSTrackerTheme
>>>>>>> Stashed changes


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            TUSTrackerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavGraph(navController)
                }
            }
        }
    }
}

@Composable
fun NavGraph(navController: NavHostController) {
    val viewModel: StartPageViewModel = viewModel()
    NavHost(navController, startDestination = "startPage") {
        composable("startPage") {
            StartPage(navController, viewModel)
        }
        composable("registerPage") {
            RegisterPage(navController, viewModel)
        }
<<<<<<< Updated upstream
=======
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
            JournalPage(navController, startPageViewModel)
        }
        composable("addJournalPage") {
            AddJournalPage(navController, startPageViewModel)
        }

>>>>>>> Stashed changes
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TUSTrackerTheme {
        Greeting("Android")
    }
}