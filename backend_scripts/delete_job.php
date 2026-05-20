<?php
session_start();
require_once 'db_config.php';

if (!isset($_SESSION['admin_id'])) {
    header("Location: admin_login.php");
    exit();
}

if (isset($_GET['id'])) {
    $id = $_GET['id'];
    try {
        $stmt = $conn->prepare("DELETE FROM jobs WHERE id = ?");
        $stmt->execute([$id]);
    } catch (PDOException $e) {
        // Handle error if needed
    }
}

header("Location: admin_dashboard.php");
exit();
