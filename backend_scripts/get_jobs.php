<?php
header("Content-Type: application/json");
require_once 'db_config.php';

try {
    $stmt = $conn->query("SELECT * FROM jobs");
    $jobs = $stmt->fetchAll(PDO::FETCH_ASSOC);

    // Convert requirements JSON string back to array for each job
    foreach ($jobs as &$job) {
        $job['requirements'] = json_decode($job['requirements'], true);
        $job['id'] = (string)$job['id']; // Ensure ID is a string to match Kotlin model
    }

    echo json_encode($jobs);
} catch (PDOException $e) {
    echo json_encode(["error" => $e->getMessage()]);
}
?>
