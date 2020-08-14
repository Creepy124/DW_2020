-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: localhost    Database: control
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
-- Table structure for table `log`
--

DROP TABLE IF EXISTS `log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `log` (
  `config_id` int NOT NULL,
  `file_name` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `file_type` varchar(5) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `action` varchar(5) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `status` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `file_timestamp` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `log`
--

LOCK TABLES `log` WRITE;
/*!40000 ALTER TABLE `log` DISABLE KEYS */;
INSERT INTO `log` VALUES (5,'sinhvien_chieu_nhom09.txt','txt','WH',NULL,'2020-08-09T17:59:19.740384900'),(5,'sinhvien_sang_nhom9.txt','txt','WH',NULL,'2020-08-09T17:59:19.772483300'),(6,'monhoc2013.xlsx','xlsx','WH',NULL,'2020-08-09T17:59:31.431997400'),(6,'monhoc2014.xlsx','xlsx','WH',NULL,'2020-08-09T17:59:31.454004900'),(7,'lophoc_chieu_nhom26_2020.txt','txt','WH',NULL,'2020-08-09T17:59:43.391436900'),(7,'lophoc_sang_nhom26_2020.txt','txt','WH',NULL,'2020-08-09T17:59:43.416501800'),(8,'dangky_chieu_nhom26_2020.txt','txt','WH',NULL,'2020-08-09T18:00:08.427208100'),(8,'dangky_sang_nhom26_2020.txt','txt','WH',NULL,'2020-08-09T18:00:08.453193900'),(7,'lophoc_chieu_nhom26_2020.txt','txt','WH',NULL,'2020-08-09T18:03:14.791920800'),(7,'lophoc_sang_nhom26_2020.txt','txt','WH',NULL,'2020-08-09T18:03:14.812910800'),(6,'monhoc2013.xlsx','xlsx','WH',NULL,'2020-08-09T18:04:55.617330800'),(6,'monhoc2014.xlsx','xlsx','WH',NULL,'2020-08-09T18:04:55.639318800'),(7,'lophoc_chieu_nhom26_2020.txt','txt','WH',NULL,'2020-08-09T18:05:32.227976700'),(7,'lophoc_sang_nhom26_2020.txt','txt','WH',NULL,'2020-08-09T18:05:32.247965200'),(8,'dangky_chieu_nhom26_2020.txt','txt','WH',NULL,'2020-08-09T18:36:48.696854100'),(8,'dangky_sang_nhom26_2020.txt','txt','WH',NULL,'2020-08-09T18:36:48.716842100'),(5,'sinhvien_chieu_nhom09.txt','txt','WH',NULL,'2020-08-09T18:50:26.188493500'),(5,'sinhvien_sang_nhom9.txt','txt','WH',NULL,'2020-08-09T18:50:26.211480200'),(6,'monhoc2013.xlsx','xlsx','WH',NULL,'2020-08-09T18:51:55.649255800'),(6,'monhoc2014.xlsx','xlsx','WH',NULL,'2020-08-09T18:51:55.666245600'),(7,'lophoc_chieu_nhom26_2020.txt','txt','WH',NULL,'2020-08-09T18:52:02.886485700'),(7,'lophoc_sang_nhom26_2020.txt','txt','WH',NULL,'2020-08-09T18:52:02.909472300'),(8,'dangky_chieu_nhom26_2020.txt','txt','WH',NULL,'2020-08-09T18:52:08.004189600'),(8,'dangky_sang_nhom26_2020.txt','txt','WH',NULL,'2020-08-09T18:52:08.025178600'),(6,'monhoc2013.xlsx','xlsx','WH',NULL,'2020-08-09T19:25:55.404385300'),(6,'monhoc2014.xlsx','xlsx','WH',NULL,'2020-08-09T19:25:55.442363600'),(5,'sinhvien_chieu_nhom09.txt','txt','WH',NULL,'2020-08-09T19:27:08.368177800'),(5,'sinhvien_sang_nhom9.txt','txt','WH',NULL,'2020-08-09T19:27:08.406162700'),(6,'monhoc2013.xlsx','xlsx','WH',NULL,'2020-08-09T19:27:13.792287800'),(6,'monhoc2014.xlsx','xlsx','WH',NULL,'2020-08-09T19:27:13.815274800'),(7,'lophoc_chieu_nhom26_2020.txt','txt','WH',NULL,'2020-08-09T19:27:19.150098400'),(7,'lophoc_sang_nhom26_2020.txt','txt','WH',NULL,'2020-08-09T19:27:19.182081600'),(8,'dangky_chieu_nhom26_2020.txt','txt','WH',NULL,'2020-08-09T19:27:25.313277400'),(8,'dangky_sang_nhom26_2020.txt','txt','WH',NULL,'2020-08-09T19:27:25.335265200'),(8,'dangky_chieu_nhom26_2020.txt','txt','WH',NULL,'2020-08-09T19:30:28.354695600'),(8,'dangky_sang_nhom26_2020.txt','txt','WH',NULL,'2020-08-09T19:30:28.471628400'),(1,'sinhvien_chieu_nhom09.txt','txt','TR',NULL,'2020-08-09T20:00:10.179782700'),(1,'sinhvien_sang_nhom9.txt','txt','TR',NULL,'2020-08-09T20:00:10.211028'),(1,'sinhvien_chieu_nhom09.txt','txt','TR',NULL,'2020-08-09T20:00:23.552031800'),(1,'sinhvien_sang_nhom9.txt','txt','TR',NULL,'2020-08-09T20:00:23.567655600'),(5,'sinhvien_chieu_nhom09.txt','txt','WH',NULL,'2020-08-09T20:06:25.657087400'),(5,'sinhvien_sang_nhom9.txt','txt','WH',NULL,'2020-08-09T20:06:25.677111900'),(6,'monhoc2013.xlsx','xlsx','WH',NULL,'2020-08-09T20:07:25.756316'),(6,'monhoc2014.xlsx','xlsx','WH',NULL,'2020-08-09T20:07:25.776570'),(7,'lophoc_chieu_nhom26_2020.txt','txt','WH',NULL,'2020-08-09T20:08:26.040948700'),(7,'lophoc_sang_nhom26_2020.txt','txt','WH',NULL,'2020-08-09T20:08:26.070555500'),(8,'dangky_chieu_nhom26_2020.txt','txt','WH',NULL,'2020-08-09T20:09:24.899078700'),(8,'dangky_sang_nhom26_2020.txt','txt','WH',NULL,'2020-08-09T20:09:24.928796'),(5,'sinhvien_chieu_nhom09.txt','txt','WH',NULL,'2020-08-09T20:51:05.620742800'),(5,'sinhvien_sang_nhom9.txt','txt','WH',NULL,'2020-08-09T20:51:05.648726600'),(6,'monhoc2013.xlsx','xlsx','WH',NULL,'2020-08-09T20:52:05.420958400'),(6,'monhoc2014.xlsx','xlsx','WH',NULL,'2020-08-09T20:52:05.446943600'),(7,'lophoc_chieu_nhom26_2020.txt','txt','WH',NULL,'2020-08-09T20:53:05.733685200'),(7,'lophoc_sang_nhom26_2020.txt','txt','WH',NULL,'2020-08-09T20:53:05.759670'),(8,'dangky_chieu_nhom26_2020.txt','txt','WH',NULL,'2020-08-09T20:54:05.556372700'),(8,'dangky_sang_nhom26_2020.txt','txt','WH',NULL,'2020-08-09T20:54:05.584355500');
/*!40000 ALTER TABLE `log` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-08-14 15:46:11
