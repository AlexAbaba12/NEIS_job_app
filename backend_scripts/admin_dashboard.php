<?php
session_start();
require_once 'db_config.php';

if (!isset($_SESSION['admin_id'])) {
    header("Location: admin_login.php");
    exit();
}

// Fetch Jobs
$jobs_stmt = $conn->query("SELECT * FROM jobs");
$jobs = $jobs_stmt->fetchAll(PDO::FETCH_ASSOC);

// Fetch Applications
$apps_stmt = $conn->query("SELECT * FROM applications");
$applications = $apps_stmt->fetchAll(PDO::FETCH_ASSOC);
?>

<!DOCTYPE html>
<html>
<head>
    <title>NEIS Admin Dashboard</title>
    <link rel="stylesheet" href="admin_style.css">
</head>
<body>
    <div class="container">
        <div class="nav">
            <span>Welcome, <?php echo $_SESSION['admin_name']; ?></span> |
            <a href="admin_dashboard.php">Dashboard</a> |
            <a href="add_job.php">Add New Job</a> |
            <a href="admin_logout.php">Logout</a>
        </div>

        <h1>Admin Dashboard</h1>

        <h2>Job Listings</h2>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Title</th>
                    <th>Company</th>
                    <th>Location</th>
                    <th>Salary</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <?php foreach ($jobs as $job): ?>
                <tr>
                    <td><?php echo $job['id']; ?></td>
                    <td><?php echo $job['title']; ?></td>
                    <td><?php echo $job['company']; ?></td>
                    <td><?php echo $job['location']; ?></td>
                    <td><?php echo $job['salary']; ?></td>
                    <td>
                        <a href="delete_job.php?id=<?php echo $job['id']; ?>" class="btn btn-delete" onclick="return confirm('Are you sure?')">Delete</a>
                    </td>
                </tr>
                <?php endforeach; ?>
            </tbody>
        </table>

        <h2 style="margin-top: 40px;">Received Applications</h2>
        <table>
            <thead>
                <tr>
                    <th>App ID</th>
                    <th>Applicant</th>
                    <th>Job Title</th>
                    <th>Company</th>
                    <th>Status</th>
                    <th>Date</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <?php foreach ($applications as $app): ?>
                <tr>
                    <td><?php echo $app['id']; ?></td>
                    <td><?php echo $app['applicant_name']; ?></td>
                    <td><?php echo $app['job_title']; ?></td>
                    <td><?php echo $app['company']; ?></td>
                    <td>
                        <span class="status-badge <?php echo strtolower($app['status']); ?>">
                            <?php echo $app['status']; ?>
                        </span>
                    </td>
                    <td><?php echo $app['date_submitted']; ?></td>
                    <td>
                        <form action="update_application_status.php" method="POST" style="display: flex; gap: 5px;">
                            <input type="hidden" name="app_id" value="<?php echo $app['id']; ?>">
                            <select name="status" style="padding: 4px; width: auto;">
                                <option value="Pending" <?php if($app['status'] == 'Pending') echo 'selected'; ?>>Pending</option>
                                <option value="Shortlisted" <?php if($app['status'] == 'Shortlisted') echo 'selected'; ?>>Shortlisted</option>
                                <option value="Accepted" <?php if($app['status'] == 'Accepted') echo 'selected'; ?>>Accepted</option>
                                <option value="Rejected" <?php if($app['status'] == 'Rejected') echo 'selected'; ?>>Rejected</option>
                            </select>
                            <button type="submit" class="btn btn-edit" style="padding: 4px 8px;">Update</button>
                        </form>
                    </td>
                </tr>
                <?php endforeach; ?>
            </tbody>
        </table>
    </div>
</body>
</html>
