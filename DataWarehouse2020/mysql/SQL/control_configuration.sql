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
-- Table structure for table `configuration`
--

DROP TABLE IF EXISTS `configuration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `configuration` (
  `config_id` int NOT NULL,
  `config_name` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `source_host` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `source_remote_path` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `source_username` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `source_password` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `source_port` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `file_name_pattern` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `file_column_list` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `file_variables` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `download_path` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `file_dilimiter` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `error_to_emails` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `local` varchar(1) DEFAULT NULL,
  `flag` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configuration`
--

LOCK TABLES `configuration` WRITE;
/*!40000 ALTER TABLE `configuration` DISABLE KEYS */;
INSERT INTO `configuration` VALUES (1,'sinhvien','drive.ecepvn.org','volume1/ECEP/song.nguyen/DW_2020/data/','guest_access','123456','2227','sinhvien_(sang|chieu)_nhom([0-9]|[0-9][0-9]).txt','stt,mssv,ho,ten,ngaysinh,malop,tenlop,sdt,email,quequan,ghichu','varchar(50), varchar(50,varchar(50),varchar(50),varchar(50),varchar(50),varchar(50),varchar(50),varchar(50),varchar(50),varchar(50)','E:\\\\Warehouse','|','thuyphuongnguyen0170@gmail.com,creepy120499@gmail.com','n',NULL),(2,'monhoc','drive.ecepvn.org','volume1/ECEP/song.nguyen/DW_2020/data/','guest_access','123456','2227','Monhoc2013.csv','STT,MaMH,TenMH,TinChi,BMQuanLy,BMDangSuDung,GhiChu','varchar(225),varchar(225),varchar(225),varchar(225),varchar(225),varchar(225),varchar(225)','E:\\\\Warehouse',',','thuyphuongnguyen0170@gmail.com,creepy120499@gmail.com','n',NULL),(3,'lophoc','drive.ecepvn.org','volume1/ECEP/song.nguyen/DW_2020/data/','guest_access','123456','2227','lophoc_(sang|chieu)_nhom([0-9]|[0-9][0-9])_2020.*','STT,MaLH,MaMH,NamHoc','varchar(50),varchar(50),varchar(50),varchar(50)','E:\\\\Warehouse','|','thuyphuongnguyen0170@gmail.com,creepy120499@gmail.com','n',NULL),(4,'dangky','drive.ecepvn.org','volume1/ECEP/song.nguyen/DW_2020/data/','guest_access','123456','2227','dangky_(sang|chieu)_nhom([0-9]|[0-9][0-9])_2020.*','STT,MaDK,MSSV,MaLH,ThoiGianDK','varchar(50),varchar(50),varchar(50),varchar(50),varchar(50)','E:\\\\Warehouse','|','thuyphuongnguyen0170@gmail.com,creepy120499@gmail.com','n',NULL),(5,'sinhvien','DESKTOP-P7RFKBN','E:/Warehouse2','asus','Langtutrunggio','22','sinhvien_(sang|chieu)_nhom([0-9]|[0-9][0-9]).txt','stt,mssv,ho,ten,ngaysinh,malop,tenlop,sdt,email,quequan,ghichu','varchar(50), varchar(50,varchar(50),varchar(50),varchar(50),varchar(50),varchar(50),varchar(50),varchar(50),varchar(50),varchar(50)','E:\\\\Warehouse','|','thuyphuongnguyen0170@gmail.com,creepy120499@gmail.com','y','Done Step 3'),(6,'monhoc','DESKTOP-P7RFKBN','E:/Warehouse2','asus','Langtutrunggio','22','monhoc*.*','STT,MaMH,TenMH,TinChi,BMQuanLy,BMDangSuDung,GhiChu','varchar(225),varchar(225),varchar(225),varchar(225),varchar(225),varchar(225),varchar(225)','E:\\\\Warehouse','|','thuyphuongnguyen0170@gmail.com,creepy120499@gmail.com','y','Done Step 3'),(7,'lophoc','DESKTOP-P7RFKBN','E:/Warehouse2','asus','Langtutrunggio','22','lophoc_(sang|chieu)_nhom([0-9]|[0-9][0-9])_2020.txt','STT,MaLH,MaMH,NamHoc','varchar(50),varchar(50),varchar(50),varchar(50)','E:\\\\Warehouse','|','thuyphuongnguyen0170@gmail.com,creepy120499@gmail.com','y','Done Step 3'),(8,'dangky','DESKTOP-P7RFKBN','E:/Warehouse2','asus','Langtutrunggio','22','dangky_(sang|chieu)_nhom([0-9]|[0-9][0-9])_2020.txt','STT,MaDK,MSSV,MaLH,ThoiGianDK','varchar(50),varchar(50),varchar(50),varchar(50),varchar(50)','E:\\\\Warehouse','|','thuyphuongnguyen0170@gmail.com,creepy120499@gmail.com','y','Done Step 3');
/*!40000 ALTER TABLE `configuration` ENABLE KEYS */;
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
