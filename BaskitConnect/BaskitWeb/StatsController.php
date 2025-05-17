<?php
require_once '../BaskitAPI/config/database.php';

session_start();

class StatsController {
    public static function handleRequest($conn) {
        header('Content-Type: application/json'); 
        $dataType = $_GET['data'] ?? 'summary'; 

        try {
            switch ($dataType) {
                case 'summary':
                    return self::getSummaryStats($conn);
                case 'products':
                    return self::getProductStats($conn, $_GET['type'] ?? 'category');
                case 'store_orders':
                    return self::getStoreOrdersStats($conn);
                case 'popular_stores':
                    return self::getPopularStoresStats($conn, $_GET['timeframe'] ?? 'weekly');
                case 'sales':
                    return self::getSalesStats($conn, $_GET['period'] ?? 'weekly');
                case 'orders':
                    return self::getOrdersStats($conn, $_GET['period'] ?? 'weekly');
                case 'user':
                    return self::getUserInfo($conn); 
                default:
                    return ['error' => 'Invalid data type'];
            }
        } catch (Exception $e) {
            return ['error' => $e->getMessage()];
        }
    }

    // New method to fetch the logged-in user's info
    private static function getUserInfo($conn) {
        if (!isset($_SESSION['user_id'])) {
            throw new Exception("User not logged in");
        }

        $userId = $_SESSION['user_id'];
        $sql = "SELECT firstname, lastname FROM users WHERE id = ?";
        $stmt = $conn->prepare($sql);
        if ($stmt === false) {
            throw new Exception("Error preparing user query: " . $conn->error);
        }

        $stmt->bind_param("i", $userId);
        $stmt->execute();
        $result = $stmt->get_result();

        if ($result->num_rows === 0) {
            throw new Exception("User not found");
        }

        $user = $result->fetch_assoc();
        $stmt->close();

        $fullName = trim($user['firstname'] . ' ' . $user['lastname']);
        return ['name' => $fullName];
    }

    // Summary stats (for cards)
    private static function getSummaryStats($conn) {
        // Total registered users
        $sqlUsers = "SELECT COUNT(*) as user_count FROM users";
        $resultUsers = $conn->query($sqlUsers);
        if ($resultUsers === false) {
            throw new Exception("Error in users query: " . $conn->error);
        }
        $userCount = $resultUsers->fetch_assoc()['user_count'];

        // Total orders today
        $sqlOrdersToday = "SELECT COUNT(*) as orders_today FROM orders WHERE DATE(created_at) = CURDATE()";
        $resultOrdersToday = $conn->query($sqlOrdersToday);
        if ($resultOrdersToday === false) {
            throw new Exception("Error in orders today query: " . $conn->error);
        }
        $ordersToday = $resultOrdersToday->fetch_assoc()['orders_today'];

        // Sales today (product_price * product_quantity)
        $sqlSalesToday = "SELECT COALESCE(SUM(product_price * product_quantity), 0) as sales_today FROM orders WHERE DATE(created_at) = CURDATE()";
        $resultSalesToday = $conn->query($sqlSalesToday);
        if ($resultSalesToday === false) {
            throw new Exception("Error in sales today query: " . $conn->error);
        }
        $salesToday = $resultSalesToday->fetch_assoc()['sales_today'];

        return [
            'todaySales' => $salesToday,
            'totalOrdersToday' => $ordersToday,
            'registeredUsers' => $userCount
        ];
    }

    // Product stats
    private static function getProductStats($conn, $type) {
        $sql = ($type === 'category') 
            ? "SELECT p.product_category AS label, SUM(o.product_quantity) AS count 
               FROM orders o 
               JOIN products p ON o.product_id = p.id 
               GROUP BY p.product_category"
            : "SELECT o.product_name AS label, SUM(o.product_quantity) AS count 
               FROM orders o 
               GROUP BY o.product_name";
        $result = $conn->query($sql);
        if ($result === false) {
            throw new Exception("Error in products query: " . $conn->error);
        }
        $labels = [];
        $data = [];
        while ($row = $result->fetch_assoc()) {
            $labels[] = $row['label'];
            $data[] = $row['count'];
        }
        return ['labels' => $labels, 'data' => $data];
    }

    // Store orders stats
    private static function getStoreOrdersStats($conn) {
        $sql = "SELECT store_name AS label, COUNT(*) AS count FROM orders GROUP BY store_name";
        $result = $conn->query($sql);
        if ($result === false) {
            throw new Exception("Error in store orders query: " . $conn->error);
        }
        $labels = [];
        $data = [];
        while ($row = $result->fetch_assoc()) {
            $labels[] = $row['label'];
            $data[] = $row['count'];
        }
        return ['labels' => $labels, 'data' => $data];
    }

    // Popular stores stats
    private static function getPopularStoresStats($conn, $timeframe) {
        $timeCondition = '';
        switch ($timeframe) {
            case 'weekly':
                $timeCondition = "WHERE created_at >= DATE_SUB(CURDATE(), INTERVAL 7 DAY)";
                break;
            case 'monthly':
                $timeCondition = "WHERE created_at >= DATE_SUB(CURDATE(), INTERVAL 1 MONTH)";
                break;
            case 'yearly':
                $timeCondition = "WHERE created_at >= DATE_SUB(CURDATE(), INTERVAL 1 YEAR)";
                break;
            default:
                $timeCondition = "";
        }
        $sql = "SELECT store_name AS label, COUNT(*) AS count FROM orders $timeCondition GROUP BY store_name";
        $result = $conn->query($sql);
        if ($result === false) {
            throw new Exception("Error in popular stores query: " . $conn->error);
        }
        $labels = [];
        $data = [];
        while ($row = $result->fetch_assoc()) {
            $labels[] = $row['label'];
            $data[] = $row['count'];
        }
        return ['labels' => $labels, 'data' => $data];
    }

    // Sales stats
    private static function getSalesStats($conn, $period) {
        $groupBy = '';
        switch ($period) {
            case 'weekly':
                $groupBy = "DAYNAME(created_at)";
                break;
            case 'monthly':
                $groupBy = "WEEK(created_at)";
                break;
            case 'yearly':
                $groupBy = "MONTHNAME(created_at)";
                break;
            default:
                $groupBy = "DAYNAME(created_at)";
        }
        $timeCondition = '';
        switch ($period) {
            case 'weekly':
                $timeCondition = "WHERE created_at >= DATE_SUB(CURDATE(), INTERVAL 7 DAY)";
                break;
            case 'monthly':
                $timeCondition = "WHERE created_at >= DATE_SUB(CURDATE(), INTERVAL 1 MONTH)";
                break;
            case 'yearly':
                $timeCondition = "WHERE created_at >= DATE_SUB(CURDATE(), INTERVAL 1 YEAR)";
                break;
            default:
                $timeCondition = "";
        }
        $sql = "SELECT $groupBy AS label, COALESCE(SUM(product_price * product_quantity), 0) AS amount 
                FROM orders $timeCondition 
                GROUP BY $groupBy 
                ORDER BY created_at";
        $result = $conn->query($sql);
        if ($result === false) {
            throw new Exception("Error in sales query: " . $conn->error);
        }
        $labels = [];
        $data = [];
        while ($row = $result->fetch_assoc()) {
            $labels[] = $row['label'];
            $data[] = $row['amount'];
        }
        return ['labels' => $labels, 'data' => $data];
    }

    // Orders stats
    private static function getOrdersStats($conn, $period) {
        $groupBy = '';
        switch ($period) {
            case 'weekly':
                $groupBy = "DAYNAME(created_at)";
                break;
            case 'monthly':
                $groupBy = "WEEK(created_at)";
                break;
            case 'yearly':
                $groupBy = "MONTHNAME(created_at)";
                break;
            default:
                $groupBy = "DAYNAME(created_at)";
        }
        $timeCondition = '';
        switch ($period) {
            case 'weekly':
                $timeCondition = "WHERE created_at >= DATE_SUB(CURDATE(), INTERVAL 7 DAY)";
                break;
            case 'monthly':
                $timeCondition = "WHERE created_at >= DATE_SUB(CURDATE(), INTERVAL 1 MONTH)";
                break;
            case 'yearly':
                $timeCondition = "WHERE created_at >= DATE_SUB(CURDATE(), INTERVAL 1 YEAR)";
                break;
            default:
                $timeCondition = "";
        }
        $sql = "SELECT $groupBy AS label, COUNT(*) AS count 
                FROM orders $timeCondition 
                GROUP BY $groupBy 
                ORDER BY created_at";
        $result = $conn->query($sql);
        if ($result === false) {
            throw new Exception("Error in orders query: " . $conn->error);
        }
        $labels = [];
        $data = [];
        while ($row = $result->fetch_assoc()) {
            $labels[] = $row['label'];
            $data[] = $row['count'];
        }
        return ['labels' => $labels, 'data' => $data];
    }
}

// Handle the request and output JSON
$conn = Database::getConnection();
$data = StatsController::handleRequest($conn);
echo json_encode($data);
$conn->close();
?>