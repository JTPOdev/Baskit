<?php 
require_once __DIR__ . '/../model/Order.php';

class OrderController{

    public static function placeOrder($userId, $conn) {
        $cartItems = Order::getCartItems($userId, $conn);

        if (empty($cartItems)) {
            return ['message' => 'Cart is empty'];
        }

        $orderIds = [];
        foreach ($cartItems as $item) {
            $orderId = Order::createOrder($userId, $item, $conn);
            if ($orderId) {
                $orderIds[] = $orderId;
            }
        }
        Order::clearCart($userId, $conn);

        return [
            'message' => 'Order placed successfully',
            'order_ids' => $orderIds
        ];
    }

    public static function acceptAllOrders($tagabiliId, $orderCode, $conn) {

        $tagabiliDetails = Order::getTagabiliDetails($tagabiliId, $conn);
        
        if (!$tagabiliDetails) {
            return ['message' => 'Tagabili not found'];
        }
    
        return Order::updateOrdersWithTagabili($tagabiliId, $tagabiliDetails, $orderCode, $conn);
    }
}
?>