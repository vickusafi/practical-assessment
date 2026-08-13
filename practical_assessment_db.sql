-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.41 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.10.0.7000
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for practical_assessment_db
DROP DATABASE IF EXISTS `practical_assessment_db`;
CREATE DATABASE IF NOT EXISTS `practical_assessment_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `practical_assessment_db`;

-- Dumping structure for table practical_assessment_db.accounts
DROP TABLE IF EXISTS `accounts`;
CREATE TABLE IF NOT EXISTS `accounts` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `account_number` varchar(20) NOT NULL,
  `balance` decimal(19,4) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `owner_name` varchar(255) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `version` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6kplolsdtr3slnvx97xsy2kc8` (`account_number`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table practical_assessment_db.accounts: ~0 rows (approximately)
INSERT INTO `accounts` (`id`, `account_number`, `balance`, `created_at`, `owner_name`, `updated_at`, `version`) VALUES
	(1, 'COOP1001', 118000.0000, '2026-08-13 10:05:33.716216', 'Victor Odhiambo', '2026-08-13 11:23:34.763682', 3),
	(2, 'COOP1002', 103000.0000, '2026-08-13 10:05:46.106773', 'John Doe', '2026-08-13 11:23:34.766651', 1),
	(3, 'COOP1003', 100000.0000, '2026-08-13 10:05:54.170055', 'Maxy Doe', '2026-08-13 10:05:54.170055', 0),
	(4, 'COOP1004', 100000.0000, '2026-08-13 10:06:12.811094', 'Peter Doe', '2026-08-13 10:06:12.811094', 0),
	(5, 'COOP1005', 100000.0000, '2026-08-13 10:07:12.021755', 'Jane Doe', '2026-08-13 10:07:12.021755', 0),
	(6, 'COOP1008', 100000.0000, '2026-08-13 10:20:39.417914', 'Jane Doe', '2026-08-13 10:20:39.417914', 0);

-- Dumping structure for table practical_assessment_db.transactions
DROP TABLE IF EXISTS `transactions`;
CREATE TABLE IF NOT EXISTS `transactions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount` decimal(19,4) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `from_account_number` varchar(255) DEFAULT NULL,
  `reference_number` varchar(40) NOT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `status` enum('FAILED','PENDING','SUCCESS') NOT NULL,
  `to_account_number` varchar(255) NOT NULL,
  `type` enum('DEPOSIT','TRANSFER','WITHDRAWAL') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK96ogkdl6a4f18ybi32m3mi6h0` (`reference_number`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table practical_assessment_db.transactions: ~2 rows (approximately)
INSERT INTO `transactions` (`id`, `amount`, `created_at`, `from_account_number`, `reference_number`, `remarks`, `status`, `to_account_number`, `type`) VALUES
	(1, 10500.0000, '2026-08-13 11:04:14.375409', NULL, 'TXN-3A103558', 'Salary', 'SUCCESS', 'COOP1001', 'DEPOSIT'),
	(2, 10500.0000, '2026-08-13 11:05:06.250979', NULL, 'TXN-BF09F7B4', 'Monthly Savings', 'SUCCESS', 'COOP1001', 'DEPOSIT'),
	(3, 3000.0000, '2026-08-13 11:23:34.729131', 'COOP1001', 'TXN-81073CE0', 'Debt Payment', 'SUCCESS', 'COOP1002', 'TRANSFER');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
