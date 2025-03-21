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
        // Fetch user_id and check if the order exists and is accepted
        $sql = "SELECT user_id FROM orders WHERE order_code = ? AND status = 'Accepted' LIMIT 1";
        $stmt = $conn->prepare($sql);
        if (!$stmt) {
            return ['message' => 'SQL Error: ' . $conn->error];
        }
    
        $stmt->bind_param("s", $orderCode);
        $stmt->execute();
        $result = $stmt->get_result();
    
        if ($result->num_rows === 0) {
            $stmt->close();
            return ['message' => 'Invalid order code or order not accepted'];
        }
    
        $row = $result->fetch_assoc();
        $userId = $row['user_id'];
        $stmt->close();
    
        // Update the order status to 'Completed'
        $sql = "UPDATE orders SET status = 'Completed' WHERE order_code = ?";
        $stmt = $conn->prepare($sql);
        if (!$stmt) {
            return ['message' => 'SQL Error: ' . $conn->error];
        }
    
        $stmt->bind_param("s", $orderCode);
        if ($stmt->execute()) {
            $stmt->close();
    
            // Clear the cart for the user since order is completed
            return Order::clearCart($userId, $conn) 
                ? ['message' => 'Order marked as completed and cart cleared']
                : ['message' => 'Order marked as completed, but failed to clear cart'];
        } else {
            $error = $stmt->error;
            $stmt->close();
            return ['message' => 'Failed to update order', 'error' => $error];
        }
    }
    

    public static function getUserOrders($userId, $conn) {
        $sql = "SELECT o.*, u.firstname, u.lastname, u.mobile_number 
                FROM orders o
                JOIN users u ON o.user_id = u.id
                WHERE o.user_id = ?";
        
        $stmt = $conn->prepare($sql);
        $stmt->bind_param("i", $userId);
        $stmt->execute();
        $result = $stmt->get_result();
    
        $orders = [];
        while ($row = $result->fetch_assoc()) {
            $orders[] = $row;
        }
    
        // Count total orders
        $totalOrders = count($orders);
    
        return [
            'total_orders' => $totalOrders,
            'orders' => $orders
        ];
    }

    public static function getAllUsersOrders($conn) {
        $sql = "SELECT u.id as user_id, u.firstname, u.lastname, u.mobile_number, 
                       COUNT(o.id) AS total_orders, o.product_origin
                FROM users u
                INNER JOIN orders o ON u.id = o.user_id
                GROUP BY u.id, o.product_origin";
        
        $stmt = $conn->prepare($sql);
        $stmt->execute();
        $result = $stmt->get_result();
    
        $orders = [];
        while ($row = $result->fetch_assoc()) {
            $orders[] = $row;
        }
    
        return $orders;
    }

    public static function getTotalOrdersByLocation($conn) {
        $sql = "SELECT 
                    COUNT(DISTINCT CASE WHEN o.product_origin = 'DAGUPAN' THEN o.user_id END) AS total_dagupan_orders,
                    COUNT(DISTINCT CASE WHEN o.product_origin = 'CALASIAO' THEN o.user_id END) AS total_calasiao_orders
                FROM orders o";
        
        $stmt = $conn->prepare($sql);
        $stmt->execute();
        $result = $stmt->get_result();
        
        return $result->fetch_assoc();
    }
}
?>