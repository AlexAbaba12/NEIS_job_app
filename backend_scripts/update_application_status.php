<?php
session_start();
require_once 'db_config.php';

if (!isset($_SESSION['admin_id'])) {
    header("Location: admin_login.php");
    exit();
}

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $app_id = $_POST['app_id'];
    $new_status = $_POST['status'];

    try {
        $stmt = $conn->prepare("UPDATE applications SET status = ? WHERE id = ?");
        $stmt->execute([$new_status, $app_id]);
    } catch (PDOException $e) {
        // Log error or handle as needed
    }
}

header("Location: admin_dashboard.php");
exit();
