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

        $sql = "INSERT INTO orders (
        user_id, 
        product_id, 
        product_name, 
        product_price,
        fee, 
        product_quantity, 
        product_portion, 
        product_origin, 
        store_id, 
        store_name, 
        product_image, 
        status) 
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'Pending')";
        $stmt = $conn->prepare($sql);
        if (!$stmt) {
            die(json_encode(['message' => 'SQL Error: ' . $conn->error]));
        }
        $stmt->bind_param("iisddississ", 
            $userId, 
            $item['product_id'], 
            $item['product_name'], 
            $item['product_price'], 
            $item['fee'], 
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

    public static function updateOrdersWithTagabili($tagabiliId, $tagabiliDetails, $userId, $orderCode, $conn) {
        $conn->begin_transaction();
    
        try {
            $firstname = $tagabiliDetails['firstname'];
            $lastname = $tagabiliDetails['lastname'];
            $mobile = $tagabiliDetails['mobile_number'];
            $email = $tagabiliDetails['email'];
    
            $sql = "UPDATE orders 
                    SET tagabili_id = ?, 
                        tagabili_firstname = ?, 
                        tagabili_lastname = ?, 
                        tagabili_mobile = ?, 
                        tagabili_email = ?, 
                        order_code = ?, 
                        status = 'Accepted'
                    WHERE user_id = ? 
                    AND tagabili_firstname = 'Pending' 
                    AND status = 'Pending'";
    
            $stmt = $conn->prepare($sql);
            if (!$stmt) {
                throw new Exception("Prepare statement failed: " . $conn->error);
            }
    
            $stmt->bind_param("isssssi", $tagabiliId, $firstname, $lastname, $mobile, $email, $orderCode, $userId);
    
            if (!$stmt->execute()) {
                throw new Exception("Failed to update orders: " . $stmt->error);
            }
    
            $cartSql = "UPDATE cart 
                        SET status = 'Accepted',
                            tagabili_id = ?, 
                            tagabili_firstname = ?, 
                            tagabili_lastname = ?, 
                            tagabili_mobile = ?, 
                            tagabili_email = ?, 
                            order_code = ?
                        WHERE user_id = ? 
                        AND status = 'Pending'";
    
            $cartStmt = $conn->prepare($cartSql);
            if (!$cartStmt) {
                throw new Exception("Prepare statement failed: " . $conn->error);
            }
    
            $cartStmt->bind_param("isssssi", $tagabiliId, $firstname, $lastname, $mobile, $email, $orderCode, $userId);
    
            if (!$cartStmt->execute()) {
                throw new Exception("Failed to update cart: " . $cartStmt->error);
            }
    
            $conn->commit();
    
            return [
                'message' => 'Order accepted successfully',
                'updated_orders' => $stmt->affected_rows,
                'updated_cart' => $cartStmt->affected_rows,
                'order_code' => $orderCode,
                'user_id' => $userId,
                'tagabili' => [
                    'id' => $tagabiliId,
                    'firstname' => $firstname,
                    'lastname' => $lastname,
                    'mobile' => $mobile,
                    'email' => $email
                ]
            ];
        } catch (Exception $e) {
            $conn->rollback();
            return ['message' => 'Failed to accept order', 'error' => $e->getMessage()];
        }
    }
    
    public static function completeOrderByCode($orderCode, $conn) {
        
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
    
        $sql = "UPDATE orders SET status = 'Completed' WHERE order_code = ?";
        $stmt = $conn->prepare($sql);
        if (!$stmt) {
            return ['message' => 'SQL Error: ' . $conn->error];
        }
    
        $stmt->bind_param("s", $orderCode);
        if ($stmt->execute()) {
            $stmt->close();
    
            return Order::clearCart($userId, $conn) 
                ? ['message' => 'Order marked as completed and cart cleared']
                : ['message' => 'Order marked as completed, but failed to clear cart'];
        } else {
            $error = $stmt->error;
            $stmt->close();
            return ['message' => 'Failed to update order', 'error' => $error];
        }
    }

    public static function updateOrderReady($orderCode, $conn) {
        $conn->begin_transaction();
        try {
            $sql = "SELECT is_ready FROM orders WHERE order_code = ? AND is_ready = 'Pending'";
            $stmt = $conn->prepare($sql);
            if (!$stmt) {
                throw new Exception('SQL Error: ' . $conn->error);
            }
        
            $stmt->bind_param("s", $orderCode);
            $stmt->execute();
            $result = $stmt->get_result();
            
            if ($result->num_rows === 0) {
                $stmt->close();
                throw new Exception('Order is already marked as Ready or does not exist');
            }
        
            $stmt->close();
    
            $updateOrderSql = "UPDATE orders SET is_ready = 'Ready' WHERE order_code = ?";
            $updateOrderStmt = $conn->prepare($updateOrderSql);
            if (!$updateOrderStmt) {
                throw new Exception('SQL Error: ' . $conn->error);
            }
    
            $updateOrderStmt->bind_param("s", $orderCode);
            if (!$updateOrderStmt->execute()) {
                throw new Exception('Failed to update order: ' . $updateOrderStmt->error);
            }
            $updateOrderStmt->close();
    
            $updateCartSql = "UPDATE cart SET is_ready = 'Ready' WHERE order_code = ?";
            $updateCartStmt = $conn->prepare($updateCartSql);
            if (!$updateCartStmt) {
                throw new Exception('SQL Error: ' . $conn->error);
            }
    
            $updateCartStmt->bind_param("s", $orderCode);
            if (!$updateCartStmt->execute()) {
                throw new Exception('Failed to update cart: ' . $updateCartStmt->error);
            }
            $updateCartStmt->close();
            $conn->commit();
    
            return ['message' => 'Order and cart items are now marked as Ready'];
        } catch (Exception $e) {
            $conn->rollback();
            return ['message' => 'Failed to update', 'error' => $e->getMessage()];
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
    
        $totalOrders = count($orders);
    
        return [
            'total_orders' => $totalOrders,
            'orders' => $orders
        ];
    }

    public static function getAllUsersOrders($conn) {
        $sql = "SELECT u.id as user_id, u.firstname, u.lastname, u.mobile_number, 
                       COUNT(o.id) AS total_orders, o.product_origin, o.status
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
                    COUNT(DISTINCT CASE WHEN o.product_origin = 'DAGUPAN' AND o.status != 'completed' THEN o.user_id END) AS total_dagupan_orders,
                    COUNT(DISTINCT CASE WHEN o.product_origin = 'CALASIAO' AND o.status != 'completed' THEN o.user_id END) AS total_calasiao_orders
                FROM orders o";
        
        $stmt = $conn->prepare($sql);
        $stmt->execute();
        $result = $stmt->get_result();
        
        return $result->fetch_assoc();
    }

    public static function getAcceptedOrdersByTagabili($conn, $tagabiliId) {
        $sql = "SELECT u.id AS user_id, 
                       u.firstname, 
                       u.lastname, 
                       u.mobile_number, 
                       COUNT(o.id) AS total_orders, 
                       o.product_origin, 
                       o.status,
                       o.is_ready
                FROM orders o
                INNER JOIN users u ON o.user_id = u.id
                WHERE o.tagabili_id = ? AND o.status = 'Accepted'
                GROUP BY u.id, o.product_origin, o.status, o.is_ready";
    
        $stmt = $conn->prepare($sql);
        $stmt->bind_param("i", $tagabiliId);
        $stmt->execute();
        $result = $stmt->get_result();
    
        $orders = [];
        while ($row = $result->fetch_assoc()) {
            $orders[] = $row;
        }
    
        return $orders;
    }
    
}
?>