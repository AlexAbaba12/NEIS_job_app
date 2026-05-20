# NEIS - Naga Employment Information System (Job Application Process Module)

NEIS is a modern, full-stack job application platform designed specifically for the Naga City community. It consists of a feature-rich Android mobile application for job seekers and a comprehensive web-based admin panel for employers and administrators.

## Features

###  Android Application (Job Seekers)
- **User Authentication**: Secure Login and Registration.
- **Job Dashboard**: Browse available job openings in Naga City.
- **Detailed Job Views**: See requirements, salary ranges, and job descriptions.
- **Easy Application**: Apply for jobs with just a few taps.
- **Application Tracking**: Check the real-time status of your applications (Pending, Shortlisted, Accepted, Rejected).
- **Remember Me**: Quick login functionality for returning users.

### Admin Panel (Web)
- **Dashboard**: Overview of all active listings and received applications.
- **Job Management**: Add, update, or delete job postings.
- **Application Processing**: Review applicants and update their status.
- **Secure Access**: Dedicated admin login system.

##  Tech Stack

- **Mobile**: Kotlin, Jetpack Compose, Retrofit 2, Coroutines, DataStore.
- **Backend**: PHP (PDO), MySQL.
- **Design**: Material 3 Design Guidelines.

##  Getting Started

### Prerequisites
- Android Studio (Ladybug or newer)
- XAMPP / WAMP / MAMP (for local database and API)
- PHP 7.4+ & MySQL

### 1. Database Setup
1. Import the `backend_scripts/database.sql` file into your MySQL database (e.g., via phpMyAdmin).
2. Ensure you have a database named `neis_db`.

### 2. Backend Setup
1. Move all files from the `backend_scripts/` folder to your local server directory (e.g., `C:/xampp/htdocs/neis_api/`).
2. Update `db_config.php` if your MySQL credentials differ from the defaults (root/no password).

### 3. Android App Setup
1. Open the project in Android Studio.
2. If running on an emulator, the API points to `10.0.2.2`. If running on a physical device, update `RetrofitClient.kt` with your computer's local IP address.
3. Build and run the app! Thank you:)


