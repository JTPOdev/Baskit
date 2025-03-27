<?php

class Cart
{   
    // --------- ADD TO CART/BASKIT -------- //
    public static function addToCart($userId, $productId, $price, $fee, $quantity, $portion, $conn, $productImageUrl)
    {
        $userQuery = "SELECT * FROM users WHERE id = ?";
        $stmt = $conn->prepare($userQuery);
        $stmt->bind_param("i", $userId);
        $stmt->execute();
        $userResult = $stmt->get_result();
    
        if ($userResult->num_rows === 0) {
            return ['message' => 'User not found'];
        }
    
        $product = Product::getProductById($productId, $conn);
        if (!$product) {
            return ['message' => 'Product not found'];
        }
    
        $sql = "SELECT * FROM cart WHERE user_id = ? AND product_id = ? AND product_portion = ?";
        $stmt = $conn->prepare($sql);
        $stmt->bind_param("iis", $userId, $productId, $portion);
        $stmt->execute();
        $result = $stmt->get_result();
    
        if ($result->num_rows > 0) {
            $sql = "UPDATE cart 
                    SET product_quantity = product_quantity + ?, product_price = ?, fee = ?, product_image = ? 
                    WHERE user_id = ? AND product_id = ? AND product_portion = ?";
            $stmt = $conn->prepare($sql);
            $stmt->bind_param("iddsiis", $quantity, $price, $fee, $productImageUrl, $userId, $productId, $portion);
        } else {
            $sql = "INSERT INTO cart 
                      (user_id, product_id, product_name, product_price, fee,  product_quantity, product_portion, product_origin, store_id, store_name, product_image) 
                      VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            $stmt = $conn->prepare($sql);
            $stmt->bind_param("iisddississ", 
                $userId, 
                $productId, 
                $product['product_name'], 
                $price,
                $fee, 
                $quantity, 
                $portion, 
                $product['product_origin'], 
                $product['store_id'], 
                $product['store_name'], 
                $productImageUrl
            );
        }
        
        return $stmt->execute() ? ['message' => 'Added to cart'] : ['message' => 'Failed to add to cart'];
    }
    
    // --------- GET USER CART BASICALLY KUNIN LAHAT NG LAMAN NG CART NG SPECIFIC USER -------- //
    public static function getUserCart($userId, $conn)
    {
        $sql = "SELECT product_id, 
        product_name, 
        product_price,
        fee, 
        product_quantity, 
        product_portion, 
        product_origin, 
        store_id, 
        store_name, 
        product_image, 
        order_status,
        status,
        tagabili_firstname,
        tagabili_lastname,
        tagabili_mobile,
        tagabili_email,
        order_code,
        is_ready 
        FROM cart WHERE user_id = ?";

        if ($stmt = $conn->prepare($sql)) {
            $stmt->bind_param("i", $userId);
            $stmt->execute();
            $result = $stmt->get_result();
            $cartItems = $result->fetch_all(MYSQLI_ASSOC);
            $stmt->close();

            return $cartItems ?: [];
        } 
        return [];
    }

    // --------- UPDATE CART -------- //
    public static function updateCart($userId, $productId, $quantity, $portion, $conn)
    {
        AuthMiddleware::checkAuth();
        $sql = "UPDATE cart SET product_quantity = ? WHERE user_id = ? AND product_id = ? AND product_portion = ?";
        $stmt = $conn->prepare($sql);
        $stmt->bind_param("iiis", $quantity, $userId, $productId, $portion);
        return $stmt->execute() ? ['message' => 'Cart updated'] : ['message' => 'Failed to update cart'];
    }

    // --------- REMOVE PRODUCT SA CART -------- //
    public static function removeFromCart($userId, $productId, $portion, $conn)
    {
        AuthMiddleware::checkAuth();
        $sql = "DELETE FROM cart WHERE user_id = ? AND product_id = ? AND product_portion = ?";
        $stmt = $conn->prepare($sql);
        $stmt->bind_param("iis", $userId, $productId, $portion);
        return $stmt->execute() ? ['message' => 'Removed from cart'] : ['message' => 'Failed to remove from cart'];
    }
}
?>