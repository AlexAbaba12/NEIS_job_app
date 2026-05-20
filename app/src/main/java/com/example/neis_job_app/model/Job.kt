package com.example.neis_job_app.model

data class Job(
    val id: String,
    val title: String,
    val company: String,
    val location: String,
    val salary: String,
    val description: String,
    val requirements: List<String>
)

val sampleJobs = listOf(
    Job(
        id = "1",
        title = "Software Engineer",
        company = "Naga Tech Solutions",
        location = "Naga City, Camarines Sur",
        salary = "₱30,000 - ₱50,000",
        description = "We are looking for a skilled Software Engineer to join our growing team. You will be responsible for developing high-quality applications.",
        requirements = listOf("Proficient in Kotlin", "Experience with Jetpack Compose", "Strong problem-solving skills")
    ),
    Job(
        id = "2",
        title = "Data Analyst",
        company = "Bicol Data Systems",
        location = "Pili, Camarines Sur",
        salary = "₱25,000 - ₱40,000",
        description = "Seeking a Data Analyst to interpret complex data sets and provide actionable insights for our clients.",
        requirements = listOf("Experience with SQL and Python", "Knowledge of data visualization tools", "Analytical mindset")
    ),
    Job(
        id = "3",
        title = "Administrative Assistant",
        company = "City Hall - Naga",
        location = "Naga City, Camarines Sur",
        salary = "₱15,000 - ₱20,000",
        description = "Join the city government team as an Administrative Assistant. You will support various departments with clerical and administrative tasks.",
        requirements = listOf("Bachelor's degree in any field", "Excellent communication skills", "Proficient in MS Office")
    )
)
