package com.example.neis_job_app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.neis_job_app.ui.screens.*
import com.example.neis_job_app.ui.theme.NEIS_job_appTheme
import com.example.neis_job_app.viewmodel.AuthViewModel
import com.example.neis_job_app.viewmodel.AuthViewModelFactory
import com.example.neis_job_app.viewmodel.JobViewModel
import com.example.neis_job_app.viewmodel.ApplicationViewModel
import com.example.neis_job_app.utils.SessionManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val sessionManager = SessionManager(this)
        setContent {
            NEIS_job_appTheme {
                NEISApp(sessionManager)
            }
        }
    }
}

@Composable
fun NEISApp(
    sessionManager: SessionManager,
    authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory(sessionManager)),
    jobViewModel: JobViewModel = viewModel()
) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val userId by authViewModel.userId
    val jobs by jobViewModel.jobs
    
    LaunchedEffect(userId) {
        if (userId != null) {
            // If we have a userId, we should be on the dashboard
            // But we only want to auto-navigate if we are currently at login/register
            val currentRoute = navController.currentBackStackEntry?.destination?.route
            if (currentRoute == "login" || currentRoute == "register") {
                navController.navigate("dashboard") {
                    popUpTo(currentRoute) { inclusive = true }
                }
            }
        }
    }

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                viewModel = authViewModel,
                onLoginSuccess = {
                    navController.navigate("dashboard") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate("register")
                }
            )
        }
        
        composable("register") {
            RegistrationScreen(
                viewModel = authViewModel,
                onRegisterSuccess = {
                    Toast.makeText(context, "Registration Successful! Please login.", Toast.LENGTH_SHORT).show()
                    navController.navigate("login") {
                        popUpTo("register") { inclusive = true }
                    }
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("dashboard") {
            DashboardScreen(
                viewModel = jobViewModel,
                onJobClick = { job ->
                    navController.navigate("jobDetail/${job.id}")
                },
                onViewApplications = {
                    navController.navigate("applications")
                },
                onLogout = {
                    authViewModel.logout()
                    navController.navigate("login") {
                        popUpTo("dashboard") { inclusive = true }
                    }
                }
            )
        }

        composable("applications") {
            val applicationViewModel: ApplicationViewModel = viewModel()
            ApplicationsStatusScreen(
                userId = userId ?: "",
                onNavigateBack = { navController.popBackStack() },
                viewModel = applicationViewModel
            )
        }

        composable(
            route = "jobDetail/{jobId}",
            arguments = listOf(navArgument("jobId") { type = NavType.StringType })
        ) { backStackEntry ->
            val jobId = backStackEntry.arguments?.getString("jobId")
            val job = jobs.find { it.id == jobId }
            
            if (job != null) {
                JobDetailScreen(
                    job = job,
                    onNavigateBack = { navController.popBackStack() },
                    onApplyClick = { navController.navigate("apply/${job.id}") }
                )
            }
        }

        composable(
            route = "apply/{jobId}",
            arguments = listOf(navArgument("jobId") { type = NavType.StringType })
        ) { backStackEntry ->
            val jobId = backStackEntry.arguments?.getString("jobId")
            val job = jobs.find { it.id == jobId }
            val applicationViewModel: ApplicationViewModel = viewModel()
            
            if (job != null) {
                JobApplicationScreen(
                    job = job,
                    userId = userId ?: "",
                    onNavigateBack = { navController.popBackStack() },
                    onApplicationSubmitted = {
                        Toast.makeText(context, "Application submitted for ${job.title}!", Toast.LENGTH_LONG).show()
                        navController.popBackStack("dashboard", inclusive = false)
                    },
                    viewModel = applicationViewModel
                )
            }
        }
    }
}
