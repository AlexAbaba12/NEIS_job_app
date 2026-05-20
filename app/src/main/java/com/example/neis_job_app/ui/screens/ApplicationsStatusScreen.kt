package com.example.neis_job_app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.neis_job_app.model.JobApplication
import com.example.neis_job_app.viewmodel.ApplicationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationsStatusScreen(
    userId: String,
    onNavigateBack: () -> Unit,
    viewModel: ApplicationViewModel = viewModel()
) {
    val applications by viewModel.applications
    val isLoading by viewModel.isLoading
    val error by viewModel.error

    LaunchedEffect(userId) {
        viewModel.fetchApplications(userId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Applications") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (error != null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = error!!, color = MaterialTheme.colorScheme.error)
                        Button(onClick = { viewModel.fetchApplications(userId) }) {
                            Text("Retry")
                        }
                    }
                }
            } else {
                // Table Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(8.dp)
                ) {
                    Text(
                        text = "Job / Company",
                        modifier = Modifier.weight(2f),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "Status",
                        modifier = Modifier.weight(1f),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "Date",
                        modifier = Modifier.weight(1f),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }

                if (applications.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No applications found.")
                    }
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(applications) { app ->
                            ApplicationRow(app)
                            HorizontalDivider()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ApplicationRow(app: JobApplication) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(2f)) {
            Text(text = app.jobTitle, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            Text(text = app.company, fontSize = 12.sp, color = MaterialTheme.colorScheme.outline)
        }
        
        val statusColor = when (app.status) {
            "Accepted" -> Color(0xFF4CAF50)
            "Rejected" -> Color(0xFFF44336)
            "Reviewing" -> Color(0xFFFF9800)
            else -> MaterialTheme.colorScheme.onSurface
        }

        Text(
            text = app.status,
            modifier = Modifier.weight(1f),
            color = statusColor,
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp
        )
        
        Text(
            text = app.dateSubmitted,
            modifier = Modifier.weight(1f),
            fontSize = 12.sp
        )
    }
}
