package com.tariq.taskproject.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tariq.taskproject.presentation.screens.details_screen.MedicationDetailScreen
import com.tariq.taskproject.presentation.screens.greeting.DashboardScreen
import com.tariq.taskproject.presentation.screens.login.LoginScreen

@Composable
fun MedicationNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(

                viewModel = hiltViewModel(),
                onLoginSuccess = { username ->
                    navController.navigate("dashboard/$username") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = "dashboard/{username}", 
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            DashboardScreen(
                username = username,
                medicationViewModel = hiltViewModel(),
                onMedicationClick = { medication ->
                    navController.navigate("details/${medication.id}")
                }
            )
        }

        composable(
            route = "details/{medicationId}",
            arguments = listOf(navArgument("medicationId") { type = NavType.IntType })
        ) { backStackEntry ->
            val medicationId = backStackEntry.arguments?.getInt("medicationId")

            MedicationDetailScreen(
                medicationId = medicationId ?: -1,
                viewModel = hiltViewModel(),
                onBackClick = { navController.navigateUp() }
            )
        }
    }
}