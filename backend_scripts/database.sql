CREATE DATABASE IF NOT EXISTS neis_db;
USE neis_db;

CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) DEFAULT 'user',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS jobs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    company VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL,
    salary VARCHAR(100),
    description TEXT,
    requirements TEXT -- Stored as a JSON array or newline-separated string
);

CREATE TABLE IF NOT EXISTS applications (
    id VARCHAR(50) PRIMARY KEY,
    user_id INT NOT NULL,
    job_title VARCHAR(255) NOT NULL,
    company VARCHAR(255) NOT NULL,
    applicant_name VARCHAR(100) NOT NULL,
    status VARCHAR(50) DEFAULT 'Pending',
    date_submitted DATE,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Insert sample jobs
INSERT INTO jobs (title, company, location, salary, description, requirements) VALUES
('Software Engineer', 'Naga Tech Solutions', 'Naga City, Camarines Sur', '₱30,000 - ₱50,000', 'We are looking for a skilled Software Engineer to join our growing team.', '["Proficient in Kotlin", "Experience with Jetpack Compose", "Strong problem-solving skills"]'),
('Data Analyst', 'Bicol Data Systems', 'Pili, Camarines Sur', '₱25,000 - ₱40,000', 'Seeking a Data Analyst to interpret complex data sets and provide actionable insights.', '["Experience with SQL and Python", "Knowledge of data visualization tools", "Analytical mindset"]'),
('Administrative Assistant', 'City Hall - Naga', 'Naga City, Camarines Sur', '₱15,000 - ₱20,000', 'Join the city government team as an Administrative Assistant.', '["Bachelor\'s degree in any field", "Excellent communication skills", "Proficient in MS Office"]');
