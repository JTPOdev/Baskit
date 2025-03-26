<?php 

class Announcement {
    
    public static function updateSlideImage($conn, $imageColumn, $imagePath) {
        $allowedColumns = ['slideimage_1', 'slideimage_2', 'slideimage_3'];
        if (!in_array($imageColumn, $allowedColumns)) {
            return false;
        }
    
        $checkSql = "SELECT id FROM announcement WHERE id = 1";
        $checkStmt = $conn->prepare($checkSql);
        $checkStmt->execute();
        $result = $checkStmt->get_result();
    
        if ($result->num_rows === 0) {
            $insertSql = "INSERT INTO announcement (id, slideimage_1, slideimage_2, slideimage_3) VALUES (1, '', '', '')";
            $insertStmt = $conn->prepare($insertSql);
            $insertStmt->execute();
        }

        $sql = "UPDATE announcement SET $imageColumn = ? WHERE id = 1";
        $stmt = $conn->prepare($sql);
        $stmt->bind_param("s", $imagePath);
    
        if (!$stmt->execute()) {
            header("HTTP/1.1 500 Internal Server Error");
            echo json_encode(['message' => 'Database update failed', 'error' => $stmt->error]);
            exit;
        }
    
        return true;
    }

    public static function getSlideImages($conn) {
        $sql = "SELECT slideimage_1, slideimage_2, slideimage_3 FROM announcement WHERE id = 1";
        $stmt = $conn->prepare($sql);
        $stmt->execute();
        $result = $stmt->get_result()->fetch_assoc();
    
        if (!$result) {
            header("HTTP/1.1 404 Not Found");
            return ['message' => 'No announcement images found'];
        }
    
        return $result;
    }
}
?>