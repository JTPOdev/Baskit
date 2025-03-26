<?php 
require_once __DIR__ . '/../model/Announcement.php';

class AnnouncementController
{
    public static function updateSlideImage($conn) {
        header('Content-Type: application/json');
    
        if (!isset($_POST['image_column']) || !isset($_FILES['slide_image'])) {
            header("HTTP/1.1 400 Bad Request");
            echo json_encode(['message' => 'Missing parameters']);
            exit;
        }
    
        $imageColumn = $_POST['image_column']; // slideimage_1, slideimage_2, or slideimage_3
        $allowedColumns = ['slideimage_1', 'slideimage_2', 'slideimage_3'];
    
        if (!in_array($imageColumn, $allowedColumns)) {
            header("HTTP/1.1 400 Bad Request");
            echo json_encode(['message' => 'Invalid image column']);
            exit;
        }
    
        $imagePath = self::uploadFile($_FILES['slide_image'] ?? null, 'uploads/announcements');
    
        if (isset($imagePath['error'])) {
            header('HTTP/1.1 400 Bad Request');
            echo json_encode(['message' => 'File upload failed']);
            exit;
        }
    
        if (Announcement::updateSlideImage($conn, $imageColumn, $imagePath['success'])) {
            header('HTTP/1.1 200 OK');
            echo json_encode(['message' => 'Slide image updated successfully.']);
        } else {
            header('HTTP/1.1 500 Internal Server Error');
            echo json_encode(['message' => 'Error updating slide image.']);
        }
    }

    public static function getSlideImages($conn) {
        header('Content-Type: application/json');
    
        $images = Announcement::getSlideImages($conn);
    
        echo json_encode($images);
    }

    private static function uploadFile($file, $folder)
    {
        if (!$file || empty($file['tmp_name'])) {
            return ['error' => 'No file uploaded'];
        }

        $allowedTypes = ['image/jpeg', 'image/png', 'application/pdf'];
        $fileType = mime_content_type($file['tmp_name']);
        $fileExtension = strtolower(pathinfo($file['name'], PATHINFO_EXTENSION));

        if (!in_array($fileType, $allowedTypes)) {
            return ['error' => 'Invalid file type. Allowed: JPG, PNG, PDF'];
        }

        $uploadDir = __DIR__ . "/../public/uploads/$folder/";
        if (!is_dir($uploadDir)) {
            mkdir($uploadDir, 0755, true);
        }

        $filename = uniqid() . "_" . basename($file['name']);
        $targetPath = $uploadDir . $filename;

        return move_uploaded_file($file['tmp_name'], $targetPath) ?
            ['success' => "http://".$_SERVER['HTTP_HOST']."/uploads/$folder/" . $filename] :
            ['error' => 'Failed to upload file'];
    }
    
}
?>