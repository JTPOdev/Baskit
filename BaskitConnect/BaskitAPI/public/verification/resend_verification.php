<?php
require_once __DIR__ . '/../../config/database.php';
require_once __DIR__ . '/../../controller/UserController.php';

// Enable error reporting for debugging
error_reporting(E_ALL);
ini_set('display_errors', 1);

// Set response headers
header('Access-Control-Allow-Origin: *');
header('Content-Type: application/json');
header('Access-Control-Allow-Methods: POST');
header('Access-Control-Allow-Headers: Content-Type, Authorization');

// Get JSON input
$data = json_decode(file_get_contents("php://input"), true);

if (!isset($data['email'])) {
    echo json_encode(['message' => 'Email is required']);
    http_response_code(400);
    exit;
}

$email = $data['email'];

// Check if email exists and is not verified
$conn = Database::getConnection();
$sql = "SELECT is_verified FROM users WHERE email = ?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("s", $email);
$stmt->execute();
$result = $stmt->get_result();

if ($result->num_rows === 0) {
    echo json_encode(['message' => 'Email not found']);
    http_response_code(404);
    exit;
}

$user = $result->fetch_assoc();
if ($user['is_verified'] == 'Verified') {
    echo json_encode(['message' => 'Your email is already verified']);
    http_response_code(200);
    exit;
}

// Resend verification email
$response = UserController::sendVerificationEmail($email);
http_response_code(200);
echo json_encode($response);
?>