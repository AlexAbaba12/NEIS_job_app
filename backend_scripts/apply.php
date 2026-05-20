<?php
header("Content-Type: application/json");
require_once 'db_config.php';

$data = json_decode(file_get_contents("php://input"), true);

if (isset($data['id']) && isset($data['userId']) && isset($data['jobTitle'])) {
    $id = $data['id'];
    $userId = $data['userId'];
    $jobTitle = $data['jobTitle'];
    $company = $data['company'];
    $applicantName = $data['applicantName'];
    $status = $data['status'];
    $dateSubmitted = $data['dateSubmitted'];

    try {
        $stmt = $conn->prepare("INSERT INTO applications (id, user_id, job_title, company, applicant_name, status, date_submitted) VALUES (?, ?, ?, ?, ?, ?, ?)");
        if ($stmt->execute([$id, $userId, $jobTitle, $company, $applicantName, $status, $dateSubmitted])) {
            echo json_encode(["success" => true, "message" => "Application submitted successfully"]);
        } else {
            echo json_encode(["success" => false, "message" => "Failed to submit application"]);
        }
    } catch (PDOException $e) {
        echo json_encode(["success" => false, "message" => $e->getMessage()]);
    }
} else {
    echo json_encode(["success" => false, "message" => "Invalid input"]);
}
?>
