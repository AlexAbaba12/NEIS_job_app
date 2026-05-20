package com.example.neis_job_app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.neis_job_app.model.Job

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobDetailScreen(
    job: Job,
    onNavigateBack: () -> Unit,
    onApplyClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Job Details") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            Surface(shadowElevation = 8.dp) {
                Button(
                    onClick = onApplyClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("Apply Now")
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = job.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = job.company,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text(
                text = job.location,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.outline
            )
            
            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
            
            Text(
                text = "Salary",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = job.salary,
                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
            )
            
            Text(
                text = "Description",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = job.description,
                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
            )
            
            Text(
                text = "Requirements",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            job.requirements.forEach { requirement ->
                Text(
                    text = "• $requirement",
                    modifier = Modifier.padding(top = 4.dp, start = 8.dp)
                )
            }
        }
    }
}
