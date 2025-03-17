<?php
class Order {
    
    public static function getCartItems($userId, $conn) {
        $sql = "SELECT * FROM cart WHERE user_id = ?";
        $stmt = $conn->prepare($sql);
        $stmt->bind_param("i", $userId);
        $stmt->execute();
        $result = $stmt->get_result();

        $items = [];
        while ($row = $result->fetch_assoc()) {
            $items[] = $row;
        }
        return $items;
    }

    public static function createOrder($userId, $item, $conn) {
        $sql = "INSERT INTO orders (user_id, product_id, product_name, product_price, product_quantity, product_portion, product_origin, store_id, store_name, product_image, status) 
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'Pending')";
        $stmt = $conn->prepare($sql);
        if (!$stmt) {
            die(json_encode(['message' => 'SQL Error: ' . $conn->error]));
        }
        $stmt->bind_param("iisdississ", 
            $userId, 
            $item['product_id'], 
            $item['product_name'], 
            $item['product_price'], 
            $item['product_quantity'], 
            $item['product_portion'], 
            $item['product_origin'], 
            $item['store_id'], 
            $item['store_name'], 
            $item['product_image']
        );
        if (!$stmt->execute()) {
            die(json_encode(['message' => 'Execution Error: ' . $stmt->error]));
            return $conn->insert_id;
        }
    }

    public static function clearCart($userId, $conn) {
        $sql = "DELETE FROM cart WHERE user_id = ?";
        $stmt = $conn->prepare($sql);
        $stmt->bind_param("i", $userId);
        return $stmt->execute();
    }

    public static function getTagabiliDetails($tagabiliId, $conn) {
        $sql = "SELECT firstname, lastname, mobile_number, email FROM users WHERE id = ?";
        $stmt = $conn->prepare($sql);
        $stmt->bind_param("i", $tagabiliId);
        $stmt->execute();
        $result = $stmt->get_result();
        return $result->num_rows > 0 ? $result->fetch_assoc() : null;
    }

    public static function updateOrdersWithTagabili($tagabiliId, $tagabiliDetails, $orderCode, $conn) {
        $sql = "UPDATE orders 
                SET tagabili_firstname = ?, 
                    tagabili_lastname = ?, 
                    tagabili_mobile = ?, 
                    tagabili_email = ?, 
                    status = 'Accepted', 
                    order_code = ? 
                WHERE tagabili_firstname = 'Pending' 
                AND status = 'Pending'";
    
        $stmt = $conn->prepare($sql);
        
        $stmt->bind_param("sssss", 
            $tagabiliDetails['firstname'], 
            $tagabiliDetails['lastname'], 
            $tagabiliDetails['mobile_number'], 
            $tagabiliDetails['email'], 
            $orderCode
        );
    
        if ($stmt->execute()) {
            return ['message' => 'Updated ' . $stmt->affected_rows . ' orders with provided order code'];
        } else {
            return ['message' => 'Failed to accept orders', 'error' => $stmt->error];
        }
    }
    
    public static function completeOrderByCode($orderCode, $conn) {

        $sql = "SELECT id FROM orders WHERE order_code = ? AND status = 'Accepted'";
        $stmt = $conn->prepare($sql);
        $stmt->bind_param("s", $orderCode);
        $stmt->execute();
        $result = $stmt->get_result();
    
        if ($result->num_rows === 0) {
            return ['message' => 'Invalid order code or order not accepted'];
        }
        $sql = "UPDATE orders SET status = 'Completed' WHERE order_code = ?";
        $stmt = $conn->prepare($sql);
        $stmt->bind_param("s", $orderCode);
    
        if ($stmt->execute()) {
            return ['message' => 'Order marked as completed'];
        } else {
            return ['message' => 'Failed to update order', 'error' => $stmt->error];
        }
    }
}
?>