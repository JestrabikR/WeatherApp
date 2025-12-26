package com.jestrabikr.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.jestrabikr.weather.ui.home.HomeScreen
import com.jestrabikr.weather.ui.theme.WeatherTheme
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.compose.composable
import com.jestrabikr.weather.ui.detail.DetailScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "home") {

                    composable("home") {
                        HomeScreen(navController)
                    }

                    composable(
                        "detail/{city}",
                        arguments = listOf(navArgument("city") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val city = backStackEntry.arguments?.getString("city") ?: ""
                        DetailScreen(city)
                    }
                }
            }
        }
    }
}
