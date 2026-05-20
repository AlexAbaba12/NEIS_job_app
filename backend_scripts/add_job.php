<?php
session_start();
require_once 'db_config.php';

if (!isset($_SESSION['admin_id'])) {
    header("Location: admin_login.php");
    exit();
}

$message = "";

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $title = $_POST['title'];
    $company = $_POST['company'];
    $location = $_POST['location'];
    $salary = $_POST['salary'];
    $description = $_POST['description'];
    $requirements = json_encode(explode("\n", str_replace("\r", "", $_POST['requirements'])));

    try {
        $stmt = $conn->prepare("INSERT INTO jobs (title, company, location, salary, description, requirements) VALUES (?, ?, ?, ?, ?, ?)");
        $stmt->execute([$title, $company, $location, $salary, $description, $requirements]);
        $message = "Job added successfully!";
    } catch (PDOException $e) {
        $message = "Error: " . $e->getMessage();
    }
}
?>

<!DOCTYPE html>
<html>
<head>
    <title>Add Job - NEIS Admin</title>
    <link rel="stylesheet" href="admin_style.css">
</head>
<body>
    <div class="container">
        <div class="nav">
            <a href="admin_dashboard.php">Back to Dashboard</a>
        </div>

        <h1>Add New Job</h1>
        <?php if ($message): ?>
            <p><?php echo $message; ?></p>
        <?php endif; ?>

        <form method="POST">
            <div class="form-group">
                <label>Job Title</label>
                <input type="text" name="title" required>
            </div>
            <div class="form-group">
                <label>Company</label>
                <input type="text" name="company" required>
            </div>
            <div class="form-group">
                <label>Location</label>
                <input type="text" name="location" required>
            </div>
            <div class="form-group">
                <label>Salary</label>
                <input type="text" name="salary">
            </div>
            <div class="form-group">
                <label>Description</label>
                <textarea name="description" rows="5" required></textarea>
            </div>
            <div class="form-group">
                <label>Requirements (one per line)</label>
                <textarea name="requirements" rows="5" required></textarea>
            </div>
            <button type="submit" class="btn btn-add">Save Job</button>
        </form>
    </div>
</body>
</html>
