-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 27, 2025 at 12:50 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `baskitdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `access_tokens`
--

CREATE TABLE `access_tokens` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `access_token` varchar(255) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `access_tokens`
--

INSERT INTO `access_tokens` (`id`, `user_id`, `access_token`, `created_at`) VALUES
(1063, 38, '36182535799d71e6726550cf1c486eadd1f3d6a92dc4c3b92edbe2c059c6934e', '2025-03-27 11:22:11'),
(1064, 38, '4600c25e78b168c8b68e2ffa661517b6214193d40958fbba25ec53c7969db55b', '2025-03-27 11:22:14'),
(1065, 38, '3daec058d34e76e2850f5dd55171d33d98a5526ed51e967ccf40c4005ff54f14', '2025-03-27 11:22:16');

-- --------------------------------------------------------

--
-- Table structure for table `admins`
--

CREATE TABLE `admins` (
  `id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `has_updated` enum('No Update','Updated') DEFAULT 'No Update',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admins`
--

INSERT INTO `admins` (`id`, `username`, `password`, `has_updated`, `created_at`) VALUES
(2, 'Jorose', '$2y$10$RdUsV4OESJL.whedxj7eEOak7i7htqLKKnqxFU0EFxWpYEkDWxMH.', 'Updated', '2025-02-09 19:09:52'),
(3, 'Victoria', '$2y$10$6XTWRtfYebQYw/0FHL/vguWpGYGZKqcLdlHDAzCh5XETkbSJHqOLq', 'Updated', '2025-02-26 19:56:24'),
(4, 'Martin', '$2y$10$aQ2EFXCPHhUdRO75QHNeBODTO1j878VkU/xuABkHoZlstb.fvQjG.', 'Updated', '2025-03-25 23:47:18');

-- --------------------------------------------------------

--
-- Table structure for table `admin_access_tokens`
--

CREATE TABLE `admin_access_tokens` (
  `id` int(11) NOT NULL,
  `admin_id` int(11) NOT NULL,
  `access_token` varchar(64) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin_access_tokens`
--

INSERT INTO `admin_access_tokens` (`id`, `admin_id`, `access_token`, `created_at`) VALUES
(109, 4, '69b9a5a3b26a7711222cb96efaa9000023b7b1130bc621045c944246bef15b12', '2025-03-26 06:48:33'),
(110, 3, '44aa47d3affb116ca6b30531c8a66ebd16c00a55ad2c584f1fdd99dc32dd5f9e', '2025-03-26 11:26:46'),
(111, 3, '09a61fc5df0f2dec981eb0226e04a3ea10b0fe668e0312bfc9b2583f8cdaa131', '2025-03-26 11:40:00'),
(112, 3, '342a886f2d80d08009bae5f676ba50376b633ff4d91534282b40226688823616', '2025-03-26 12:19:05'),
(113, 3, '8ccb45d3d73563de052722ca05c31dff5587bb13f56a5eacac82000ef34f1958', '2025-03-26 12:20:09'),
(114, 3, '19e098a65e166b05ef8981fdcda2f34664baedefc93f0b7155d1e77581390d4a', '2025-03-26 12:39:52'),
(115, 3, '37b4d261724a14bad9fff919002fb7e619f252a3bee05ce58ef4bab34cfd16a1', '2025-03-26 13:06:37'),
(116, 3, '7e503d0e4666914202668c4e590440f02529b707956281a921ac2f4e20f806d2', '2025-03-26 13:11:03'),
(117, 3, '1c19000ec8a08bd96650318cebbfe6a89fce8bc1d6dd877748b6da3f2d594ddf', '2025-03-26 13:11:33'),
(118, 3, '0c67a4447badc4211fb6636649c353355957b3d2ad0c3b132d73bff2f24c2171', '2025-03-26 13:25:28'),
(119, 3, '60782c8ed2d5d24815c1ee62ddc101ab60787c86b605e9680da485b61a9a59ca', '2025-03-26 14:04:32'),
(120, 3, '7addd3b39689d7dfe6981efa8546ffcfdc615b00464c1b716c8b56ef505e9458', '2025-03-27 10:59:44'),
(121, 3, 'f63a83cc8d4dce063905cd578d75f08645694a84f0e07056c130aa4d44048b4a', '2025-03-27 11:28:01');

-- --------------------------------------------------------

--
-- Table structure for table `announcement`
--

CREATE TABLE `announcement` (
  `id` int(11) NOT NULL,
  `slideimage_1` varchar(255) NOT NULL,
  `slideimage_2` varchar(255) NOT NULL,
  `slideimage_3` varchar(255) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `announcement`
--

INSERT INTO `announcement` (`id`, `slideimage_1`, `slideimage_2`, `slideimage_3`, `created_at`) VALUES
(1, 'http://192.168.100.111:8000/uploads/uploads/announcements/67e3990e441b0_slider1.png', 'http://192.168.100.111:8000/uploads/uploads/announcements/67e327ad63d69_slider2.png', 'http://192.168.100.111:8000/uploads/uploads/announcements/67e327b71e7d7_slider3.png', '2025-03-25 21:42:43');

-- --------------------------------------------------------

--
-- Table structure for table `cart`
--

CREATE TABLE `cart` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `product_name` varchar(255) NOT NULL,
  `product_price` decimal(10,2) NOT NULL,
  `fee` decimal(10,2) NOT NULL DEFAULT 0.00,
  `product_quantity` int(11) NOT NULL,
  `product_portion` enum('1pc','1/4kg','1/2kg','1kg') NOT NULL,
  `product_origin` enum('DAGUPAN','CALASIAO') NOT NULL,
  `product_image` varchar(255) NOT NULL,
  `store_id` int(11) NOT NULL,
  `store_name` varchar(255) NOT NULL,
  `order_status` enum('Pending','Order Placed') DEFAULT 'Pending',
  `status` enum('Pending','Accepted') DEFAULT 'Pending',
  `tagabili_id` int(11) DEFAULT NULL,
  `tagabili_firstname` varchar(255) DEFAULT 'Pending',
  `tagabili_lastname` varchar(255) DEFAULT 'Pending',
  `tagabili_mobile` varchar(20) DEFAULT 'Pending',
  `tagabili_email` varchar(255) DEFAULT 'Pending',
  `order_code` varchar(100) DEFAULT 'Pending',
  `is_ready` enum('Pending','Ready') NOT NULL DEFAULT 'Pending',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `cart`
--

INSERT INTO `cart` (`id`, `user_id`, `product_id`, `product_name`, `product_price`, `fee`, `product_quantity`, `product_portion`, `product_origin`, `product_image`, `store_id`, `store_name`, `order_status`, `status`, `tagabili_id`, `tagabili_firstname`, `tagabili_lastname`, `tagabili_mobile`, `tagabili_email`, `order_code`, `is_ready`, `created_at`, `updated_at`) VALUES
(140, 37, 48, 'Ampalaya', 35.00, 2.00, 1, '1pc', 'DAGUPAN', 'http://192.168.100.111:8000/uploads/product_images/67e38f0b59bfa_1000000064.jpg', 37, 'Jorose Store', 'Order Placed', 'Pending', NULL, 'Pending', 'Pending', 'Pending', 'Pending', 'Pending', 'Pending', '2025-03-27 11:20:47', '2025-03-27 11:21:53'),
(141, 37, 52, 'Whole chicken', 170.00, 10.20, 1, '1kg', 'DAGUPAN', 'http://192.168.100.111:8000/uploads/product_images/67e38ff712777_1000000079.jpg', 37, 'Jorose Store', 'Order Placed', 'Pending', NULL, 'Pending', 'Pending', 'Pending', 'Pending', 'Pending', 'Pending', '2025-03-27 11:21:20', '2025-03-27 11:21:53');

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `product_name` varchar(255) DEFAULT NULL,
  `product_price` decimal(10,2) DEFAULT NULL,
  `fee` decimal(10,2) NOT NULL DEFAULT 0.00,
  `product_quantity` int(11) DEFAULT NULL,
  `product_portion` varchar(50) DEFAULT NULL,
  `product_origin` varchar(100) DEFAULT NULL,
  `store_id` int(11) DEFAULT NULL,
  `store_name` varchar(255) DEFAULT NULL,
  `product_image` varchar(255) DEFAULT NULL,
  `tagabili_id` int(11) DEFAULT NULL,
  `tagabili_firstname` varchar(100) DEFAULT 'Pending',
  `tagabili_lastname` varchar(100) DEFAULT 'Pending',
  `tagabili_mobile` varchar(20) DEFAULT 'Pending',
  `tagabili_email` varchar(255) DEFAULT 'Pending',
  `status` enum('Pending','Accepted','Completed') DEFAULT 'Pending',
  `order_code` varchar(10) DEFAULT NULL,
  `is_ready` enum('Pending','Ready') NOT NULL DEFAULT 'Pending',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`id`, `user_id`, `product_id`, `product_name`, `product_price`, `fee`, `product_quantity`, `product_portion`, `product_origin`, `store_id`, `store_name`, `product_image`, `tagabili_id`, `tagabili_firstname`, `tagabili_lastname`, `tagabili_mobile`, `tagabili_email`, `status`, `order_code`, `is_ready`, `created_at`) VALUES
(88, 37, 48, 'Ampalaya', 35.00, 2.00, 1, '1pc', 'DAGUPAN', 37, 'Jorose Store', 'http://192.168.100.111:8000/uploads/product_images/67e38f0b59bfa_1000000064.jpg', NULL, 'Pending', 'Pending', 'Pending', 'Pending', 'Pending', NULL, 'Pending', '2025-03-27 11:21:53'),
(89, 37, 52, 'Whole chicken', 170.00, 10.20, 1, '1kg', 'DAGUPAN', 37, 'Jorose Store', 'http://192.168.100.111:8000/uploads/product_images/67e38ff712777_1000000079.jpg', NULL, 'Pending', 'Pending', 'Pending', 'Pending', 'Pending', NULL, 'Pending', '2025-03-27 11:21:53');

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `id` int(11) NOT NULL,
  `product_name` varchar(255) NOT NULL,
  `product_price` decimal(10,2) NOT NULL,
  `product_category` enum('STORE','VEGETABLES','MEAT','FISH','FROZEN','SPICES','FRUITS') DEFAULT NULL,
  `product_origin` enum('DAGUPAN','CALASIAO') DEFAULT NULL,
  `store_id` int(11) NOT NULL,
  `store_name` varchar(255) DEFAULT NULL,
  `store_phone_number` varchar(50) DEFAULT NULL,
  `owner_name` varchar(255) DEFAULT NULL,
  `store_address` varchar(255) DEFAULT NULL,
  `product_image` varchar(500) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`id`, `product_name`, `product_price`, `product_category`, `product_origin`, `store_id`, `store_name`, `store_phone_number`, `owner_name`, `store_address`, `product_image`, `created_at`, `updated_at`) VALUES
(44, 'Apple', 12.00, 'FRUITS', 'DAGUPAN', 37, 'Jorose Store', '09444444444', 'Jorose', '664 Malued', 'http://192.168.100.111:8000/uploads/product_images/67e38ec9ea8ca_1000000065.jpg', '2025-03-26 05:21:13', '2025-03-26 05:44:22'),
(45, 'Grapes', 56.00, 'FRUITS', 'DAGUPAN', 37, 'Jorose Store', '09444444444', 'Jorose', '664 Malued', 'http://192.168.100.111:8000/uploads/product_images/67e38edb61461_1000000067.jpg', '2025-03-26 05:21:31', '2025-03-26 05:44:27'),
(46, 'Lemon', 34.00, 'FRUITS', 'DAGUPAN', 37, 'Jorose Store', '09444444444', 'Jorose', '664 Malued', 'http://192.168.100.111:8000/uploads/product_images/67e38ee7ace57_1000000068.jpg', '2025-03-26 05:21:43', '2025-03-26 05:44:30'),
(47, 'Tomato', 60.00, 'FRUITS', 'DAGUPAN', 37, 'Jorose Store', '09444444444', 'Jorose', '664 Malued', 'http://192.168.100.111:8000/uploads/product_images/67e38ef968aad_1000000052.jpg', '2025-03-26 05:22:01', '2025-03-26 05:44:32'),
(48, 'Ampalaya', 35.00, 'VEGETABLES', 'DAGUPAN', 37, 'Jorose Store', '09444444444', 'Jorose', '664 Malued', 'http://192.168.100.111:8000/uploads/product_images/67e38f0b59bfa_1000000064.jpg', '2025-03-26 05:22:19', '2025-03-26 05:44:35'),
(49, 'Okra', 22.00, 'FRUITS', 'DAGUPAN', 37, 'Jorose Store', '09444444444', 'Jorose', '664 Malued', 'http://192.168.100.111:8000/uploads/product_images/67e38f17ce5dc_1000000057.jpg', '2025-03-26 05:22:31', '2025-03-26 05:44:37'),
(50, 'String bean', 50.00, 'VEGETABLES', 'DAGUPAN', 37, 'Jorose Store', '09444444444', 'Jorose', '664 Malued', 'http://192.168.100.111:8000/uploads/product_images/67e38f353e5d7_1000000053.jpg', '2025-03-26 05:23:01', '2025-03-26 05:44:40'),
(51, 'Chicken', 111.00, 'MEAT', 'DAGUPAN', 37, 'Jorose Store', '09444444444', 'Jorose', '664 Malued', 'http://192.168.100.111:8000/uploads/product_images/67e38fcdb7498_1000000073.jpg', '2025-03-26 05:25:33', '2025-03-26 05:44:42'),
(52, 'Whole chicken', 140.00, 'MEAT', 'DAGUPAN', 37, 'Jorose Store', '09444444444', 'Jorose', '664 Malued', 'http://192.168.100.111:8000/uploads/product_images/67e38ff712777_1000000079.jpg', '2025-03-26 05:26:15', '2025-03-26 05:44:44'),
(53, 'Salmon head', 230.00, 'FISH', 'DAGUPAN', 37, 'Jorose Store', '09444444444', 'Jorose', '664 Malued', 'http://192.168.100.111:8000/uploads/product_images/67e3903978b22_1000000075.jpg', '2025-03-26 05:27:21', '2025-03-26 05:44:47'),
(54, 'Tilapia', 200.00, 'FISH', 'DAGUPAN', 37, 'Jorose Store', '09444444444', 'Jorose', '664 Malued', 'http://192.168.100.111:8000/uploads/product_images/67e3904b89272_1000000078.jpg', '2025-03-26 05:27:39', '2025-03-26 05:44:51'),
(55, 'Carrot', 10.00, 'VEGETABLES', 'DAGUPAN', 37, 'Jorose Store', '09444444444', 'Jorose', '664 Malued', 'http://192.168.100.111:8000/uploads/product_images/67e39a9c6ed60_1000000034.png', '2025-03-26 06:11:40', '2025-03-26 06:11:40');

-- --------------------------------------------------------

--
-- Table structure for table `stores`
--

CREATE TABLE `stores` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `store_name` varchar(255) NOT NULL,
  `owner_name` varchar(255) NOT NULL,
  `store_phone_number` varchar(20) NOT NULL,
  `store_address` text NOT NULL,
  `store_origin` enum('DAGUPAN','CALASIAO') NOT NULL,
  `store_rating` float DEFAULT 0,
  `store_status` enum('Standard','Partner') DEFAULT 'Standard',
  `registered_store_name` varchar(255) NOT NULL,
  `registered_store_address` text NOT NULL,
  `certificate_of_registration` varchar(255) NOT NULL,
  `valid_id` varchar(255) NOT NULL,
  `store_image` varchar(255) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `stores`
--

INSERT INTO `stores` (`id`, `user_id`, `store_name`, `owner_name`, `store_phone_number`, `store_address`, `store_origin`, `store_rating`, `store_status`, `registered_store_name`, `registered_store_address`, `certificate_of_registration`, `valid_id`, `store_image`, `created_at`) VALUES
(37, 37, 'Jorose Store', 'Jorose', '09444444444', '664 Malued', 'DAGUPAN', 0, 'Standard', 'Jorose', '664 Malued', 'http://192.168.100.111:8000/uploads/certificates/67c964c296771_Certificate-of-Registration.jpg', 'http://192.168.100.111:8000/uploads/valid_ids/67c964c296bb0_Drivers-License.jpg', 'http://192.168.100.111:8000/uploads/store_images/67d6f91d32b7f_1000000036.jpg', '2025-03-06 09:03:09'),
(42, 47, 'Coco Store', 'Maria', '09123235148', 'malued', 'DAGUPAN', 0, 'Partner', 'Coco Store', 'malued', 'http://192.168.100.111:8000/uploads/certificates/67e39e06e20d1_Screenshot_2025-02-14-02-10-34-273_com.android.deskclock.jpg', 'http://192.168.100.111:8000/uploads/valid_ids/67e39e06e2a99_order_code_1742798472045.png', NULL, '2025-03-26 06:30:20'),
(43, 37, 'VIctoria Store', 'Victoria', '09987654321', '664 Malued', 'DAGUPAN', 0, 'Partner', 'Jorose', '664 Malued', 'http://192.168.100.111:8000/uploads/certificates/67e405bad5214_Checkout.png', 'http://192.168.100.111:8000/uploads/valid_ids/67e405bad58e8_5.png', NULL, '2025-03-26 13:48:59'),
(44, 37, 'VIctoria Store', 'Victoria', '09987654321', '664 Malued', 'DAGUPAN', 0, 'Standard', 'Jorose', '664 Malued', 'http://192.168.100.111:8000/uploads/certificates/67e40a4285994_Checkout.png', 'http://192.168.100.111:8000/uploads/valid_ids/67e40a4285e64_5.png', NULL, '2025-03-26 14:08:18');

-- --------------------------------------------------------

--
-- Table structure for table `store_requests`
--

CREATE TABLE `store_requests` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `store_name` varchar(255) NOT NULL,
  `owner_name` varchar(255) NOT NULL,
  `store_phone_number` varchar(20) NOT NULL,
  `store_address` text NOT NULL,
  `store_origin` enum('DAGUPAN','CALASIAO') NOT NULL,
  `store_rating` float DEFAULT 0,
  `store_status` enum('Standard','Partner') DEFAULT 'Standard',
  `registered_store_name` varchar(255) NOT NULL,
  `registered_store_address` text NOT NULL,
  `certificate_of_registration` varchar(255) NOT NULL,
  `valid_id` varchar(255) NOT NULL,
  `request_status` enum('pending','approved','rejected') DEFAULT 'pending',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `store_requests`
--

INSERT INTO `store_requests` (`id`, `user_id`, `store_name`, `owner_name`, `store_phone_number`, `store_address`, `store_origin`, `store_rating`, `store_status`, `registered_store_name`, `registered_store_address`, `certificate_of_registration`, `valid_id`, `request_status`, `created_at`) VALUES
(53, 37, 'Jorose Store', 'Jorose', '09444444444', '664 Malued', 'DAGUPAN', 0, 'Standard', 'Jorose', '664 Malued', 'http://192.168.100.111:8000/uploads/certificates/67c964c296771_Certificate-of-Registration.jpg', 'http://192.168.100.111:8000/uploads/valid_ids/67c964c296bb0_Drivers-License.jpg', 'approved', '2025-03-06 09:02:58'),
(58, 47, 'Coco Store', 'Maria', '09123235148', 'malued', 'DAGUPAN', 0, 'Partner', 'Coco Store', 'malued', 'http://192.168.100.111:8000/uploads/certificates/67e39e06e20d1_Screenshot_2025-02-14-02-10-34-273_com.android.deskclock.jpg', 'http://192.168.100.111:8000/uploads/valid_ids/67e39e06e2a99_order_code_1742798472045.png', 'approved', '2025-03-26 06:26:14'),
(59, 37, 'VIctoria Store', 'Victoria', '09987654321', '664 Malued', 'DAGUPAN', 0, 'Standard', 'Jorose', '664 Malued', 'http://192.168.100.111:8000/uploads/certificates/67e3e7d59e64f_Checkout.png', 'http://192.168.100.111:8000/uploads/valid_ids/67e3e7d59edd4_5.png', 'rejected', '2025-03-26 11:41:09'),
(60, 37, 'VIctoria Store', 'Victoria', '09987654321', '664 Malued', 'DAGUPAN', 0, 'Standard', 'Jorose', '664 Malued', 'http://192.168.100.111:8000/uploads/certificates/67e3e825a9548_Checkout.png', 'http://192.168.100.111:8000/uploads/valid_ids/67e3e825a989b_5.png', 'rejected', '2025-03-26 11:42:29'),
(61, 37, 'VIctoria Store', 'Victoria', '09987654321', '664 Malued', 'DAGUPAN', 0, 'Standard', 'Jorose', '664 Malued', 'http://192.168.100.111:8000/uploads/certificates/67e3e829c4d76_Checkout.png', 'http://192.168.100.111:8000/uploads/valid_ids/67e3e829c508d_5.png', 'rejected', '2025-03-26 11:42:33'),
(62, 37, 'VIctoria Store', 'Victoria', '09987654321', '664 Malued', 'DAGUPAN', 0, 'Standard', 'Jorose', '664 Malued', 'http://192.168.100.111:8000/uploads/certificates/67e3e83640329_Checkout.png', 'http://192.168.100.111:8000/uploads/valid_ids/67e3e83640882_5.png', 'rejected', '2025-03-26 11:42:46'),
(63, 37, 'VIctoria Store', 'Victoria', '09987654321', '664 Malued', 'DAGUPAN', 0, 'Partner', 'Jorose', '664 Malued', 'http://192.168.100.111:8000/uploads/certificates/67e3e8dbcf4c0_Checkout.png', 'http://192.168.100.111:8000/uploads/valid_ids/67e3e8dbcf8c2_5.png', 'rejected', '2025-03-26 11:45:31'),
(64, 37, 'VIctoria Store', 'Victoria', '09987654321', '664 Malued', 'DAGUPAN', 0, 'Partner', 'Jorose', '664 Malued', 'http://192.168.100.111:8000/uploads/certificates/67e405bad5214_Checkout.png', 'http://192.168.100.111:8000/uploads/valid_ids/67e405bad58e8_5.png', 'approved', '2025-03-26 13:48:42'),
(65, 37, 'VIctoria Store', 'Victoria', '09987654321', '664 Malued', 'DAGUPAN', 0, 'Partner', 'Jorose', '664 Malued', 'http://192.168.100.111:8000/uploads/certificates/67e405bba13f0_Checkout.png', 'http://192.168.100.111:8000/uploads/valid_ids/67e405bba18a4_5.png', 'rejected', '2025-03-26 13:48:43'),
(66, 37, 'VIctoria Store', 'Victoria', '09987654321', '664 Malued', 'DAGUPAN', 0, 'Partner', 'Jorose', '664 Malued', 'http://192.168.100.111:8000/uploads/certificates/67e405bc5d632_Checkout.png', 'http://192.168.100.111:8000/uploads/valid_ids/67e405bc5da20_5.png', 'rejected', '2025-03-26 13:48:44'),
(67, 37, 'VIctoria Store', 'Victoria', '09987654321', '664 Malued', 'DAGUPAN', 0, 'Standard', 'Jorose', '664 Malued', 'http://192.168.100.111:8000/uploads/certificates/67e405c1c7867_Checkout.png', 'http://192.168.100.111:8000/uploads/valid_ids/67e405c1c7ca4_5.png', 'rejected', '2025-03-26 13:48:49'),
(68, 37, 'VIctoria Store', 'Victoria', '09987654321', '664 Malued', 'DAGUPAN', 0, 'Standard', 'Jorose', '664 Malued', 'http://192.168.100.111:8000/uploads/certificates/67e405c2730ad_Checkout.png', 'http://192.168.100.111:8000/uploads/valid_ids/67e405c273906_5.png', 'rejected', '2025-03-26 13:48:50'),
(69, 37, 'VIctoria Store', 'Victoria', '09987654321', '664 Malued', 'DAGUPAN', 0, 'Standard', 'Jorose', '664 Malued', 'http://192.168.100.111:8000/uploads/certificates/67e405c320ee1_Checkout.png', 'http://192.168.100.111:8000/uploads/valid_ids/67e405c321357_5.png', 'rejected', '2025-03-26 13:48:51'),
(70, 37, 'VIctoria Store', 'Victoria', '09987654321', '664 Malued', 'DAGUPAN', 0, 'Standard', 'Jorose', '664 Malued', 'http://192.168.100.111:8000/uploads/certificates/67e405c3b7dfc_Checkout.png', 'http://192.168.100.111:8000/uploads/valid_ids/67e405c3b81b7_5.png', 'rejected', '2025-03-26 13:48:51'),
(71, 37, 'VIctoria Store', 'Victoria', '09987654321', '664 Malued', 'DAGUPAN', 0, 'Standard', 'Jorose', '664 Malued', 'http://192.168.100.111:8000/uploads/certificates/67e408750b047_Checkout.png', 'http://192.168.100.111:8000/uploads/valid_ids/67e408750b43d_5.png', 'rejected', '2025-03-26 14:00:21'),
(72, 37, 'VIctoria Store', 'Victoria', '09987654321', '664 Malued', 'DAGUPAN', 0, 'Standard', 'Jorose', '664 Malued', 'http://192.168.100.111:8000/uploads/certificates/67e409818bb4e_Checkout.png', 'http://192.168.100.111:8000/uploads/valid_ids/67e409818bf48_5.png', 'rejected', '2025-03-26 14:04:49'),
(73, 37, 'VIctoria Store', 'Victoria', '09987654321', '664 Malued', 'DAGUPAN', 0, 'Standard', 'Jorose', '664 Malued', 'http://192.168.100.111:8000/uploads/certificates/67e409824eea9_Checkout.png', 'http://192.168.100.111:8000/uploads/valid_ids/67e409824f3a1_5.png', 'rejected', '2025-03-26 14:04:50'),
(74, 37, 'VIctoria Store', 'Victoria', '09987654321', '664 Malued', 'DAGUPAN', 0, 'Standard', 'Jorose', '664 Malued', 'http://192.168.100.111:8000/uploads/certificates/67e409e1ca6c7_Checkout.png', 'http://192.168.100.111:8000/uploads/valid_ids/67e409e1cac10_5.png', 'rejected', '2025-03-26 14:06:25'),
(75, 37, 'VIctoria Store', 'Victoria', '09987654321', '664 Malued', 'DAGUPAN', 0, 'Standard', 'Jorose', '664 Malued', 'http://192.168.100.111:8000/uploads/certificates/67e409f55f390_Checkout.png', 'http://192.168.100.111:8000/uploads/valid_ids/67e409f55f740_5.png', 'rejected', '2025-03-26 14:06:45'),
(76, 37, 'VIctoria Store', 'Victoria', '09987654321', '664 Malued', 'DAGUPAN', 0, 'Standard', 'Jorose', '664 Malued', 'http://192.168.100.111:8000/uploads/certificates/67e40a0c1fda7_Checkout.png', 'http://192.168.100.111:8000/uploads/valid_ids/67e40a0c202e9_5.png', 'rejected', '2025-03-26 14:07:08'),
(77, 37, 'VIctoria Store', 'Victoria', '09987654321', '664 Malued', 'DAGUPAN', 0, 'Standard', 'Jorose', '664 Malued', 'http://192.168.100.111:8000/uploads/certificates/67e40a0cc9955_Checkout.png', 'http://192.168.100.111:8000/uploads/valid_ids/67e40a0cc9d13_5.png', 'rejected', '2025-03-26 14:07:08'),
(78, 37, 'VIctoria Store', 'Victoria', '09987654321', '664 Malued', 'DAGUPAN', 0, 'Standard', 'Jorose', '664 Malued', 'http://192.168.100.111:8000/uploads/certificates/67e40a0d6d5a9_Checkout.png', 'http://192.168.100.111:8000/uploads/valid_ids/67e40a0d6dced_5.png', 'rejected', '2025-03-26 14:07:09'),
(79, 37, 'VIctoria Store', 'Victoria', '09987654321', '664 Malued', 'DAGUPAN', 0, 'Standard', 'Jorose', '664 Malued', 'http://192.168.100.111:8000/uploads/certificates/67e40a41c17e5_Checkout.png', 'http://192.168.100.111:8000/uploads/valid_ids/67e40a41c1c14_5.png', 'rejected', '2025-03-26 14:08:01'),
(80, 37, 'VIctoria Store', 'Victoria', '09987654321', '664 Malued', 'DAGUPAN', 0, 'Standard', 'Jorose', '664 Malued', 'http://192.168.100.111:8000/uploads/certificates/67e40a4285994_Checkout.png', 'http://192.168.100.111:8000/uploads/valid_ids/67e40a4285e64_5.png', 'approved', '2025-03-26 14:08:02');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `mobile_number` varchar(13) NOT NULL,
  `password` varchar(255) NOT NULL,
  `firstname` varchar(50) NOT NULL,
  `lastname` varchar(50) NOT NULL,
  `age` int(11) NOT NULL,
  `is_verified` enum('Pending','Verified') DEFAULT 'Pending',
  `role` enum('Consumer','Tagabili','Seller') DEFAULT 'Consumer',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `email`, `mobile_number`, `password`, `firstname`, `lastname`, `age`, `is_verified`, `role`, `created_at`) VALUES
(37, 'Jorose', 'jorosepooo@gmail.com', '09222222222', '$2y$10$uFBvfcPSzxEGjHThir.36O.6z4tON3OuzfMLehZxEPAqkCp.aPkyq', 'Jorose', 'Po', 20, 'Verified', 'Seller', '2025-03-06 09:01:43'),
(38, 'Victoria', 'jorosespotify@gmail.com', '09987654322', '$2y$10$kMGHNhaL3XmyWqjocFEEz.SvRMh3AuWaeUpkRaxXFL2oNLjOcC/JS', 'Victoria', 'Catabay', 20, 'Verified', 'Tagabili', '2025-03-06 09:05:53'),
(47, 'Vickyyy', 'mariavictoriacatabay@gmail.com', '09123456789', '$2y$10$AYKkRXIbhFK7/aEQa1SaT.23hw45SDTNWKT4vcHpn1O19NyGAbQ9u', 'Vicky', 'Catabay', 20, 'Verified', 'Seller', '2025-03-26 05:31:22');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `access_tokens`
--
ALTER TABLE `access_tokens`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `access_token` (`access_token`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `admins`
--
ALTER TABLE `admins`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `admin_access_tokens`
--
ALTER TABLE `admin_access_tokens`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `access_token` (`access_token`),
  ADD KEY `admin_id` (`admin_id`);

--
-- Indexes for table `announcement`
--
ALTER TABLE `announcement`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `cart`
--
ALTER TABLE `cart`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_cart_user` (`user_id`),
  ADD KEY `fk_cart_product` (`product_id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_products_store` (`store_id`);

--
-- Indexes for table `stores`
--
ALTER TABLE `stores`
  ADD PRIMARY KEY (`id`),
  ADD KEY `stores_ibfk_1` (`user_id`);

--
-- Indexes for table `store_requests`
--
ALTER TABLE `store_requests`
  ADD PRIMARY KEY (`id`),
  ADD KEY `store_requests_ibfk_1` (`user_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `mobile_number` (`mobile_number`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `access_tokens`
--
ALTER TABLE `access_tokens`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1066;

--
-- AUTO_INCREMENT for table `admins`
--
ALTER TABLE `admins`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `admin_access_tokens`
--
ALTER TABLE `admin_access_tokens`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=122;

--
-- AUTO_INCREMENT for table `announcement`
--
ALTER TABLE `announcement`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `cart`
--
ALTER TABLE `cart`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=142;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=90;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=56;

--
-- AUTO_INCREMENT for table `stores`
--
ALTER TABLE `stores`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=45;

--
-- AUTO_INCREMENT for table `store_requests`
--
ALTER TABLE `store_requests`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=81;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=48;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `access_tokens`
--
ALTER TABLE `access_tokens`
  ADD CONSTRAINT `access_tokens_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `admin_access_tokens`
--
ALTER TABLE `admin_access_tokens`
  ADD CONSTRAINT `admin_access_tokens_ibfk_1` FOREIGN KEY (`admin_id`) REFERENCES `admins` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `cart`
--
ALTER TABLE `cart`
  ADD CONSTRAINT `fk_cart_product` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_cart_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `fk_products_store` FOREIGN KEY (`store_id`) REFERENCES `stores` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `stores`
--
ALTER TABLE `stores`
  ADD CONSTRAINT `stores_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `store_requests`
--
ALTER TABLE `store_requests`
  ADD CONSTRAINT `store_requests_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
