-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 23, 2025 at 01:13 PM
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
(786, 37, '4cd506d899eba330de8cfa97cd8971f6d4749b9866b82d99515ea8329e4efd0a', '2025-03-22 17:37:17'),
(787, 38, '6d176843ec685ba12db0e6a642007e3eb6c59573316e35cff2e860c3749b5704', '2025-03-22 18:02:21'),
(788, 37, 'f8e7dd8b806fc9d254aba71d58b3ae16d4efe15ccf6cb6be745daf3d92ac68d0', '2025-03-22 18:10:19'),
(789, 37, 'd1956651aaa544721706aeb49231a601e8c3faa42775a207a75e2c2d80bb6d86', '2025-03-22 18:30:43'),
(790, 37, '3fa955f2e6cc6eaae6271018bb50d9eb959c5d738451db3c323e02a7e5cd20de', '2025-03-22 19:01:01'),
(791, 37, 'bcd970e78ca2ffe144fe832ad0873cbc5d00218e96b0bf6b7a393c691a25cc11', '2025-03-22 19:34:35'),
(792, 37, '43c9a4b43f220e4827074193a6d5553182230375f9bd9d577e344db6e416f435', '2025-03-22 20:04:55'),
(793, 37, '29a51415d18df8941a8df40cdf3ae41890fcffe074e9bbfe6deaadfb45fed8aa', '2025-03-22 20:09:15'),
(794, 37, 'a0784da1188b474386215fe4b4b473cdd11178267f2cef502f3767ce72b3be3b', '2025-03-22 20:22:22'),
(795, 37, '925d9654e1678baa2adddbbdbce635330b2b3be310542ce3886fb52584450a00', '2025-03-22 20:26:15'),
(796, 37, '9bdfd93ff414e90e8c72579d928da65aa83f0013ca895c8ec78c280db3def395', '2025-03-23 09:26:16'),
(797, 37, 'ab765de579dca1f4082de09e5c5b2dc59071ca8ea59e1f1ad4dc275a79b88a49', '2025-03-23 09:54:30'),
(798, 37, '12aa8434e0ad5967b20172ee4823b6416f6c86ee4c7b394df2d9a36b7c31b7b4', '2025-03-23 09:58:42'),
(799, 37, 'edd400cee4bc852d8d436a127e379705e611fbc4623a7603f7459ba90fd8ff91', '2025-03-23 10:06:01'),
(800, 37, '1f496d817793acb07b70c6489231f48a6cdf63eaf4ecf07e6362e66f0d24cf1e', '2025-03-23 10:11:47'),
(801, 37, 'e4a50d00993b9f116e4863c186b08ba1f6f363e03c596f6305aeeb754fee1336', '2025-03-23 10:13:37'),
(802, 37, 'f4457f767899b2fbb56badf2fafff98129c76f8d239240a3ce65742f1b621e46', '2025-03-23 10:23:37'),
(803, 37, '311de311c7ab965eda249764eea0dead895d7770872703c83d538b10be41f2ff', '2025-03-23 10:39:13'),
(804, 37, '3b561d8e42651d37c3614d7fa7553e4e5710eef27f353c049770af10c577d9fb', '2025-03-23 10:41:11'),
(805, 37, '7365f20cabf1f3baa535d5fb13319a7bb2deb45992a3f955d7673e0b32a09531', '2025-03-23 10:47:10'),
(806, 37, '47c2c63b1de8b6fbe9fae363306902d61f6110f53d32327a1eec85cf35641ea4', '2025-03-23 10:58:59'),
(807, 37, '81109f7164a6331136f3d3321b12c2eb130ccf9efae98c90633353291df62b6b', '2025-03-23 11:02:11'),
(808, 37, 'bd005704a6b1ea62c45ad0689c4a8cbb55ad7c962735a781822f8b310b73a6f8', '2025-03-23 11:06:44'),
(809, 37, '9f00b882c2b90e8b924325983acbc83941ed3f5e98d0ea364f567532292d86c9', '2025-03-23 11:15:50');

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
(2, 'JoroseAdmin', '$2y$10$jsMPCCpoPMKo4J3w8w0bZ.HoZB5kQQfuqOQkv6PtRzjwLIGCwOGWi', 'No Update', '2025-02-09 19:09:52'),
(3, 'VictoriaAdmin', '$2y$10$uhyKS92cAMjy60xrNo.rT.JHBwudatu7KJti/6zjhtJVhA94LZd2i', 'Updated', '2025-02-26 19:56:24');

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
(39, 3, '4b4dabe9aae0184e7cbbfd965a6a97582b1a96b0576714c9db0f610cf86aada5', '2025-03-14 09:00:31'),
(40, 3, '8f1620cf96d75a5b712fd6a1ffd77d0738df9f4aef5e228afa89e434662a44b3', '2025-03-14 09:19:22'),
(41, 3, '457f5404fdc0eeb8edbdb8fc6eacfc4689dcecb1b8deb0191ef9d613525a7940', '2025-03-14 09:21:54'),
(42, 3, 'a7dea2057d8a713f4536ad89b1c64629830578d048caf708cdbc789b1ee5bac9', '2025-03-17 12:46:36'),
(43, 3, '5605102f34117dcc2654175ccfc8b74de70960eb527b52de14ca11c9fa7489ff', '2025-03-19 12:55:18'),
(44, 3, 'b8450d7f1a2f24f96f91e399e44853e81cb35b5be3aa09795a5e553d5678b7b8', '2025-03-22 12:00:17'),
(45, 3, 'd406fa17f8e12f7829c296ea2c02bce917b638cccd796c6ac4676cc7c5ded2c5', '2025-03-22 12:11:20'),
(46, 3, '5d6859dd7e2b125db002ed713562b40ef534e247e39e010f097de246d73de7e3', '2025-03-22 16:58:53'),
(47, 3, '4cd0591e1bb54be55f7e7dfb924e44672a73fb261a02190767c55927109863e8', '2025-03-22 17:41:56'),
(48, 3, '0818518aff8799d9638cefe786c338e9a18b2737a8d1af7acf4de69b8d6c103b', '2025-03-22 17:44:01'),
(49, 3, '4c0d638f3baf011765580b3c76c4a51fb0960f34be91b2fc967236e7bd304661', '2025-03-23 09:20:39'),
(50, 3, '146f167f39197634e8934d55fb75b54841512c8437b30612842e47b230082263', '2025-03-23 09:32:37'),
(51, 3, 'fc54ed428f8ed9f9a7720807316f945ff2fc57a4ce6d1f71bb6fc495a15c2850', '2025-03-23 10:24:48'),
(52, 3, 'd943de3ae225a7ec68c0ded423e7e926385e1318d8bbc0f69a3be17891df4147', '2025-03-23 10:25:33');

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
  `tagabili_firstname` varchar(255) DEFAULT 'Pending',
  `tagabili_lastname` varchar(255) DEFAULT 'Pending',
  `tagabili_mobile` varchar(20) DEFAULT 'Pending',
  `tagabili_email` varchar(255) DEFAULT 'Pending',
  `order_code` varchar(100) DEFAULT 'Pending',
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
  `tagabili_firstname` varchar(100) DEFAULT 'Pending',
  `tagabili_lastname` varchar(100) DEFAULT 'Pending',
  `tagabili_mobile` varchar(20) DEFAULT 'Pending',
  `tagabili_email` varchar(255) DEFAULT 'Pending',
  `status` enum('Pending','Accepted','Completed') DEFAULT 'Pending',
  `order_code` varchar(10) DEFAULT NULL,
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

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`id`, `product_name`, `product_price`, `product_category`, `product_origin`, `store_id`, `store_name`, `store_phone_number`, `owner_name`, `store_address`, `product_image`, `created_at`, `updated_at`) VALUES
(30, 'Banana', 10.00, 'FRUITS', 'DAGUPAN', 37, 'Jorose Store', '09444444444', 'Jorose', '664 Malued', 'http://192.168.100.111:8000/uploads/product_images/67cd813d35885_1000000033.jpg', '2025-03-08 07:31:40', '2025-03-09 14:40:43'),
(31, 'Lettuce', 24.00, 'VEGETABLES', 'DAGUPAN', 37, 'Jorose Store', '09444444444', 'Jorose', '664 Malued', 'http://192.168.100.111:8000/uploads/product_images/67cd6310b7571_IMG_2488 (1).png', '2025-03-09 09:44:48', '2025-03-12 19:58:24'),
(32, 'Apple', 11.00, 'FRUITS', 'DAGUPAN', 37, 'Jorose Store', '09444444444', 'Jorose', '664 Malued', 'http://192.168.100.111:8000/uploads/product_images/67cd813d35885_1000000033.jpg', '2025-03-09 11:53:33', '2025-03-12 19:58:47'),
(33, 'Bangus', 11.00, 'FISH', 'DAGUPAN', 37, 'Jorose Store', '09444444444', 'Jorose', '664 Malued', 'http://192.168.100.111:8000/uploads/product_images/67cd97d5d87ce_1000000033.jpg', '2025-03-09 13:29:57', '2025-03-12 19:58:53'),
(35, 'Orange', 11.00, 'FRUITS', 'DAGUPAN', 37, 'Jorose Store', '09444444444', 'Jorose', '664 Malued', 'http://192.168.100.111:8000/uploads/product_images/67d03fd7048d8_1000000036.jpg', '2025-03-11 13:51:19', '2025-03-12 19:59:04'),
(36, 'Carrot', 11.00, 'VEGETABLES', 'DAGUPAN', 37, 'Jorose Store', '09444444444', 'Jorose', '664 Malued', 'http://192.168.100.111:8000/uploads/product_images/67d15ed48bb80_1000000034.png', '2025-03-12 10:15:48', '2025-03-12 10:15:48'),
(37, 'Chicken', 111.00, 'MEAT', 'DAGUPAN', 37, 'Jorose Store', '09444444444', 'Jorose', '664 Malued', 'http://192.168.100.111:8000/uploads/product_images/67d16bb949e2a_1000000037.jpg', '2025-03-12 11:10:49', '2025-03-12 19:59:13'),
(38, 'Hotdog', 20.00, 'FROZEN', 'DAGUPAN', 37, 'Jorose Store', '09444444444', 'Jorose', '664 Malued', 'http://192.168.100.111:8000/uploads/product_images/67d16e876749c_1000000037.jpg', '2025-03-12 11:22:47', '2025-03-12 19:59:27'),
(39, 'Card', 20.00, 'VEGETABLES', 'DAGUPAN', 37, 'Jorose Store', '09444444444', 'Jorose', '664 Malued', 'http://192.168.100.111:8000/uploads/product_images/67d264fbd879f_1000000037.jpg', '2025-03-13 04:54:19', '2025-03-13 04:54:19'),
(40, 'new product', 20.00, 'FRUITS', 'DAGUPAN', 37, 'Jorose Store', '09444444444', 'Jorose', '664 Malued', 'http://192.168.100.111:8000/uploads/product_images/67d3f0882c99f_1000000036.jpg', '2025-03-14 09:02:00', '2025-03-14 09:02:00'),
(41, 'cocomelon', 24.00, 'VEGETABLES', 'CALASIAO', 41, 'Terrence Store', '09123234456', 'Terrence', '664 Calasiao', 'http://192.168.100.111:8000/uploads/product_images/67d3f800a60e2_image.png', '2025-03-14 09:33:52', '2025-03-14 09:33:52'),
(42, 'NEW', 10.00, 'FRUITS', 'DAGUPAN', 37, 'Jorose Store', '09444444444', 'Jorose', '664 Malued', 'http://192.168.100.111:8000/uploads/product_images/67d84d6c417f4_1000000034.png', '2025-03-17 16:27:24', '2025-03-17 16:27:24');

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
(41, 45, 'Terrence Store', 'Terrence', '09123234456', '664 Calasiao', 'CALASIAO', 0, 'Partner', 'Terrence Store', '664 Calasiao', 'http://192.168.100.111:8000/uploads/certificates/67d3f6f9f1d6a_Certificate-of-Registration.jpg', 'http://192.168.100.111:8000/uploads/valid_ids/67d3f6f9f2ab2_Drivers-License.jpg', 'http://192.168.100.111:8000/uploads/store_images/67d3f916e5d9e_1000000035.jpg', '2025-03-14 09:31:30');

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
(57, 45, 'Terrence Store', 'Terrence', '09123234456', '664 Calasiao', 'CALASIAO', 0, 'Partner', 'Terrence Store', '664 Calasiao', 'http://192.168.100.111:8000/uploads/certificates/67d3f6f9f1d6a_Certificate-of-Registration.jpg', 'http://192.168.100.111:8000/uploads/valid_ids/67d3f6f9f2ab2_Drivers-License.jpg', 'approved', '2025-03-14 09:29:29');

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
(45, 'Terrence', 'jota.po.up@phinmaed.com', '09234123456', '$2y$10$jJHlnl39lPwTu99qHm4W/e2xdySBIq0tV1tFVKuKINjnlG1iP8Ibm', 'Terrence', 'Lappay', 20, 'Verified', 'Seller', '2025-03-14 09:26:31');

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=810;

--
-- AUTO_INCREMENT for table `admins`
--
ALTER TABLE `admins`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `admin_access_tokens`
--
ALTER TABLE `admin_access_tokens`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=53;

--
-- AUTO_INCREMENT for table `cart`
--
ALTER TABLE `cart`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=101;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=60;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=43;

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
