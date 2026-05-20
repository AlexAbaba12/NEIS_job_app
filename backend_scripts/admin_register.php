<?php
require_once 'db_config.php';

$message = "";
$error = "";

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $name = $_POST['name'];
    $email = $_POST['email'];
    $password = password_hash($_POST['password'], PASSWORD_DEFAULT);
    $role = 'admin';

    try {
        // Check if email already exists
        $checkStmt = $conn->prepare("SELECT id FROM users WHERE email = ?");
        $checkStmt->execute([$email]);
        if ($checkStmt->fetch()) {
            $error = "Error: Email already registered.";
        } else {
            $stmt = $conn->prepare("INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)");
            if ($stmt->execute([$name, $email, $password, $role])) {
                $message = "Admin registered successfully! <a href='admin_login.php'>Login here</a>";
            } else {
                $error = "Registration failed.";
            }
        }
    } catch (PDOException $e) {
        $error = "Database Error: " . $e->getMessage();
    }
}
?>

<!DOCTYPE html>
<html>
<head>
    <title>NEIS - Admin Registration</title>
    <link rel="stylesheet" href="admin_style.css">
</head>
<body>
    <div class="container" style="max-width: 400px; margin-top: 50px;">
        <h2>Create Admin Account</h2>

        <?php if ($message): ?>
            <div style="padding: 10px; background-color: #d4edda; color: #155724; border-radius: 4px; margin-bottom: 20px;">
                <?php echo $message; ?>
            </div>
        <?php endif; ?>

        <?php if ($error): ?>
            <div style="padding: 10px; background-color: #f8d7da; color: #721c24; border-radius: 4px; margin-bottom: 20px;">
                <?php echo $error; ?>
            </div>
        <?php endif; ?>

        <form method="POST">
            <div class="form-group">
                <label>Full Name</label>
                <input type="text" name="name" required placeholder="Enter full name">
            </div>
            <div class="form-group">
                <label>Admin Email</label>
                <input type="email" name="email" required placeholder="admin@example.com">
            </div>
            <div class="form-group">
                <label>Password</label>
                <input type="password" name="password" required placeholder="Choose a strong password">
            </div>
            <button type="submit" class="btn btn-add" style="width: 100%; margin-top: 10px;">Register Admin</button>
        </form>

        <p style="text-align: center; margin-top: 20px;">
            <a href="admin_login.php" style="color: #007bff; text-decoration: none;">Back to Login</a>
        </p>
    </div>
</body>
</html>
