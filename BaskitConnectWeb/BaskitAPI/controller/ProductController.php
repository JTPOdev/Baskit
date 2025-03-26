<?php 
require_once __DIR__ . '/../model/Product.php';
require_once __DIR__ . '/../model/Store.php';
require_once __DIR__ . '/../middleware/AuthMiddleware.php';

class ProductController
{
    public static function create($data, $conn)
    {
        header('Content-Type: application/json');

        $userId = AuthMiddleware::checkAuth(); 
        $store = Store::getStoreByUserId($conn, $userId);

        if (!$store) {
            http_response_code(404);
            echo json_encode(['message' => 'User does not have an associated store']);
            return;
        }
        $storeId = $store['id'];

        $name = $data['product_name'];
        $price = $data['product_price'];
        $category = strtoupper($data['product_category']);

        $requiredFields = ['product_name', 'product_price', 'product_category'];
        foreach ($requiredFields as $field) {
            if (!isset($data[$field]) || empty($data[$field])) {
                http_response_code(400);
                echo json_encode(['message' => "Missing required field: $field"]);
                return;
            }
        }

        $validCategories = ['FRUITS', 'VEGETABLES', 'MEAT', 'FISH', 'FROZEN', 'SPICES'];
        if (!in_array($category, $validCategories)) {
            http_response_code(400);
            echo json_encode(['message' => "Invalid category: $category"]);
            return;
        }

        $productImgPath = self::uploadFile($_FILES['product_image'] ?? null, 'product_images');
        if (isset($productImgPath['error'])) {
            http_response_code(400);
            echo json_encode(['message' => 'File upload failed']);
            return;
        }

        $success = Product::createProduct($conn, $name, $price, $category, $storeId, $productImgPath['success']);
        if ($success) {
            http_response_code(201);
            echo json_encode(['message' => 'Product created successfully']);
        } else {
            http_response_code(500);
            echo json_encode(['message' => 'Failed to create product']);
        }
    }


    public static function list($conn)
    {
        header('Content-Type: application/json');
        
        $products = Product::getProducts($conn);
        if ($products) {
            http_response_code(200);
            error_log('Products retrieved: ' . json_encode($products));
            return $products;
        }
        
        http_response_code(404);
        return ['message' => 'No products found'];
    }

    public static function getSpecificProductByid($id, $conn)
    {
        $product = Product::getProductById($id, $conn);
        if ($product) {
            header('HTTP/1.1 200 OK');
            return $product;
        }
        header('HTTP/1.1 404 Not Found');
        return ['message' => 'Product not found'];
    }

    public static function delete($productId, $conn)
    {
        if (!isset($productId) || empty($productId)) {
            http_response_code(400);
            return ['message' => 'Product ID is required'];
        }

        $product = Product::getProductById($productId, $conn);
        if (!$product || isset($product['message'])) {
            http_response_code(404);
            return ['message' => 'Product not found'];
        }

        if (Product::deleteProduct($conn, $productId)) {
            http_response_code(200);
            return ['message' => 'Product deleted successfully'];
        }

        http_response_code(500);
        return ['message' => 'Failed to delete product'];
    }

    public static function getAllProducts($conn)
    {
        header('Content-Type: application/json');
    
        $products = Product::getAllProducts($conn);
    
        if ($products) {
            http_response_code(200);
            echo json_encode($products);
        } else {
            http_response_code(404);
            echo json_encode(['message' => "No products found"]);
        }
    }


    // --------- GET ALL PRODUCTS BY STORE USING TOKEN -------- //
    public static function getAllProductsByStore($conn)
    {
        header('Content-Type: application/json');

        $userId = AuthMiddleware::checkAuth();
        if (!$userId) {
            http_response_code(401);
            echo json_encode(['message' => 'Unauthorized']);
            return;
        }

        $store = Store::getStoreByUserIds($conn, $userId);
        if (!$store) {
            http_response_code(404);
            echo json_encode(['message' => 'No store associated with this user']);
            return;
        }

        $storeId = $store['id'];
        $products = Product::getProductsByStore($conn, $storeId);

        if ($products) {
            http_response_code(200);
            echo json_encode($products);
        } else {
            http_response_code(404);
            echo json_encode(['message' => 'No products found for this store']);
        }
    }

    public static function getAllProductsByStoreId($conn, $storeId)
    {
        header('Content-Type: application/json');

        // Validate input
        if (!is_numeric($storeId)) {
            http_response_code(400);
            echo json_encode(['message' => 'Invalid store ID']);
            return;
        }

        $storeId = intval($storeId);

        // Check if store exists
        $store = Store::getStoreById($conn, $storeId);
        if (!$store) {
            http_response_code(404);
            echo json_encode(['message' => 'Store not found']);
            return;
        }

        // Fetch products for the given store
        $products = Product::getProductsByStore($conn, $storeId);

        if ($products) {
            http_response_code(200);
            echo json_encode($products);
        } else {
            http_response_code(404);
            echo json_encode(['message' => 'No products found for this store']);
        }
    }
    // --------- UPLOAD FILES -------- //
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
    
    

    //--------- GET ALL PRODUCTS FROM STORE BY CATEGORY---------// 
    public static function getProductsByCategoryFruit($conn, $storeId)
    {
        $products = Product::fetchByCategoryFruit($conn, $storeId);
        if ($products) {
            header('HTTP/1.1 200 OK');
            return $products;
        }
        header('HTTP/1.1 404 Not Found');
        return ['message' => 'No products found in FRUITS category'];
    }

    public static function getProductsByCategoryVegetable($conn, $storeId)
    {
        $products = Product::fetchByCategoryVegetable($conn, $storeId);
        if ($products) {
            header('HTTP/1.1 200 OK');
            return $products;
        }
        header('HTTP/1.1 404 Not Found');
        return ['message' => 'No products found in VEGETABLES category'];
    }

    public static function getProductsByCategoryMeat($conn, $storeId)
    {
        $products = Product::fetchByCategoryMeat($conn, $storeId);
        if ($products) {
            header('HTTP/1.1 200 OK');
            return $products;
        }
        header('HTTP/1.1 404 Not Found');
        return ['message' => 'No products found in MEAT category'];
    }

    public static function getProductsByCategoryFish($conn, $storeId)
    {
        $products = Product::fetchByCategoryFish($conn, $storeId);
        if ($products) {
            header('HTTP/1.1 200 OK');
            return $products;
        }
        header('HTTP/1.1 404 Not Found');
        return ['message' => 'No products found in FISH category'];
    }

    public static function getProductsByCategoryFrozen($conn, $storeId)
    {
        $products = Product::fetchByCategoryFrozen($conn, $storeId);
        if ($products) {
            header('HTTP/1.1 200 OK');
            return $products;
        }
        header('HTTP/1.1 404 Not Found');
        return ['message' => 'No products found in FROZEN category'];
    }

    public static function getProductsByCategorySpice($conn, $storeId)
    {
        $products = Product::fetchByCategorySpice($conn, $storeId);
        if ($products) {
            header('HTTP/1.1 200 OK');
            return $products;
        }
        header('HTTP/1.1 404 Not Found');
        return ['message' => 'No products found in SPICES category'];
    }


    //---------- GET ALL PRODUCTS BY CATEGORY --------//
    public static function getAllProductsByCategoryFruit($conn)
    {
        $products = Product::fetchAllFruitProducts($conn);
        if ($products) {
            header('HTTP/1.1 200 OK');
            return $products;
        }
        header('HTTP/1.1 404 Not Found');
        return ['message' => 'No products found in FRUITS category'];
    }

    public static function getAllProductsByCategoryVegetable($conn)
    {
        $products = Product::fetchAllVegetableProducts($conn);
        if ($products) {
            header('HTTP/1.1 200 OK');
            return $products;
        }
        header('HTTP/1.1 404 Not Found');
        return ['message' => 'No products found in VEGETABLES category'];
    }

    public static function getAllProductsByCategoryMeat($conn)
    {
        $products = Product::fetchAllMeatProducts($conn);
        if ($products) {
            header('HTTP/1.1 200 OK');
            return $products;
        }
        header('HTTP/1.1 404 Not Found');
        return ['message' => 'No products found in MEAT category'];
    }

    public static function getAllProductsByCategoryFish($conn)
    {
        $products = Product::fetchAllFishProducts($conn);
        if ($products) {
            header('HTTP/1.1 200 OK');
            return $products;
        }
        header('HTTP/1.1 404 Not Found');
        return ['message' => 'No products found in FISH category'];
    }

    public static function getAllProductsByCategoryFrozen($conn)
    {
        $products = Product::fetchAllFrozenProducts($conn);
        if ($products) {
            header('HTTP/1.1 200 OK');
            return $products;
        }
        header('HTTP/1.1 404 Not Found');
        return ['message' => 'No products found in FROZEN category'];
    }

    public static function getAllProductsByCategorySpice($conn)
    {
        $products = Product::fetchAllSpiceProducts($conn);
        if ($products) {
            header('HTTP/1.1 200 OK');
            return $products;
        }
        header('HTTP/1.1 404 Not Found');
        return ['message' => 'No products found in SPICES category'];
    }
}
?>
