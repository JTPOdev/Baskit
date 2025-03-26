-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 26, 2025 at 06:07 AM
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
(4, 'admin', '$2y$10$MdaURUvT6D4454Y0MpEVc.GwLNrkJnDaIDz2FvxFJHOfBxZhwDnYC', 'No Update', '2025-03-25 23:47:18');

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
(1, 'http://192.168.100.111:8000/uploads/uploads/announcements/67e3277e373d3_slider1.png', 'http://192.168.100.111:8000/uploads/uploads/announcements/67e327ad63d69_slider2.png', 'http://192.168.100.111:8000/uploads/uploads/announcements/67e327b71e7d7_slider3.png', '2025-03-25 21:42:43');

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
(37, 37, 'Jorose Store', 'Jorose', '09444444444', '664 Malued', 'DAGUPAN', 0, 'Standard', 'Jorose', '664 Malued', 'http://192.168.100.111:8000/uploads/certificates/67c964c296771_Certificate-of-Registration.jpg', 'http://192.168.100.111:8000/uploads/valid_ids/67c964c296bb0_Drivers-License.jpg', 'http://192.168.100.111:8000/uploads/store_images/67d6f91d32b7f_1000000036.jpg', '2025-03-06 09:03:09');

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
(53, 37, 'Jorose Store', 'Jorose', '09444444444', '664 Malued', 'DAGUPAN', 0, 'Standard', 'Jorose', '664 Malued', 'http://192.168.100.111:8000/uploads/certificates/67c964c296771_Certificate-of-Registration.jpg', 'http://192.168.100.111:8000/uploads/valid_ids/67c964c296bb0_Drivers-License.jpg', 'approved', '2025-03-06 09:02:58');

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
(38, 'Victoria', 'jorosespotify@gmail.com', '09987654322', '$2y$10$kMGHNhaL3XmyWqjocFEEz.SvRMh3AuWaeUpkRaxXFL2oNLjOcC/JS', 'Victoria', 'Catabay', 20, 'Verified', 'Tagabili', '2025-03-06 09:05:53');

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1008;

--
-- AUTO_INCREMENT for table `admins`
--
ALTER TABLE `admins`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `admin_access_tokens`
--
ALTER TABLE `admin_access_tokens`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=105;

--
-- AUTO_INCREMENT for table `announcement`
--
ALTER TABLE `announcement`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `cart`
--
ALTER TABLE `cart`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=124;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=85;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=44;

--
-- AUTO_INCREMENT for table `stores`
--
ALTER TABLE `stores`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=42;

--
-- AUTO_INCREMENT for table `store_requests`
--
ALTER TABLE `store_requests`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=58;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=47;

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
