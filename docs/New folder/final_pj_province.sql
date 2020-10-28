-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: localhost    Database: final_pj
-- ------------------------------------------------------
-- Server version	8.0.21

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `province`
--

DROP TABLE IF EXISTS `province`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `province` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `province`
--

LOCK TABLES `province` WRITE;
/*!40000 ALTER TABLE `province` DISABLE KEYS */;
INSERT INTO `province` VALUES (1,'Hồ Chí Minh','SG'),(2,'Hà Nội','HN'),(3,'Đà Nẵng','DDN'),(4,'Bình Dương','BD'),(5,'Đồng Nai','DNA'),(6,'Khánh Hòa','KH'),(7,'Hải Phòng','HP'),(8,'Long An','LA'),(9,'Quảng Nam','QNA'),(10,'Bà Rịa Vũng Tàu','VT'),(11,'Đắk Lắk','DDL'),(12,'Cần Thơ','CT'),(13,'Bình Thuận  ','BTH'),(14,'Lâm Đồng','LDD'),(15,'Thừa Thiên Huế','TTH'),(16,'Kiên Giang','KG'),(17,'Bắc Ninh','BN'),(18,'Quảng Ninh','QNI'),(19,'Thanh Hóa','TH'),(20,'Nghệ An','NA'),(21,'Hải Dương','HD'),(22,'Gia Lai','GL'),(23,'Bình Phước','BP'),(24,'Hưng Yên','HY'),(25,'Bình Định','BDD'),(26,'Tiền Giang','TG'),(27,'Thái Bình','TB'),(28,'Bắc Giang','BG'),(29,'Hòa Bình','HB'),(30,'An Giang','AG'),(31,'Vĩnh Phúc','VP'),(32,'Tây Ninh','TNI'),(33,'Thái Nguyên','TN'),(34,'Lào Cai','LCA'),(35,'Nam Định','NDD'),(36,'Quảng Ngãi','QNG'),(37,'Bến Tre','BTR'),(38,'Đắk Nông','DNO'),(39,'Cà Mau','CM'),(40,'Vĩnh Long','VL'),(41,'Ninh Bình','NB'),(42,'Phú Thọ','PT'),(43,'Ninh Thuận','NT'),(44,'Phú Yên','PY'),(45,'Hà Nam','HNA'),(46,'Hà Tĩnh','HT'),(47,'Đồng Tháp','DDT'),(48,'Sóc Trăng','ST'),(49,'Kon Tum','KT'),(50,'Quảng Bình','QB'),(51,'Quảng Trị','QT'),(52,'Trà Vinh','TV'),(53,'Hậu Giang','HGI'),(54,'Sơn La','SL'),(55,'Bạc Liêu','BL'),(56,'Yên Bái','YB'),(57,'Tuyên Quang','TQ'),(58,'Điện Biên','DDB'),(59,'Lai Châu','LCH'),(60,'Lạng Sơn','LS'),(61,'Hà Giang','HG'),(62,'Bắc Kạn','BK'),(63,'Cao Bằng','CB');
/*!40000 ALTER TABLE `province` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-10-28 21:26:37
