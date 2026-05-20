<?php
header("Content-Type: application/json");
require_once 'db_config.php';

if (isset($_GET['user_id'])) {
    $userId = $_GET['user_id'];

    try {
        $stmt = $conn->prepare("SELECT * FROM applications WHERE user_id = ? ORDER BY date_submitted DESC");
        $stmt->execute([$userId]);
        $applications = $stmt->fetchAll(PDO::FETCH_ASSOC);

        // Map database fields to Kotlin JobApplication model fields
        $result = [];
        foreach ($applications as $app) {
            $result[] = [
                "id" => $app['id'],
                "userId" => (string)$app['user_id'],
                "jobTitle" => $app['job_title'],
                "company" => $app['company'],
                "applicantName" => $app['applicant_name'],
                "status" => $app['status'],
                "dateSubmitted" => $app['date_submitted']
            ];
        }

        echo json_encode($result);
    } catch (PDOException $e) {
        echo json_encode(["error" => $e->getMessage()]);
    }
} else {
    echo json_encode([]);
}
?>
