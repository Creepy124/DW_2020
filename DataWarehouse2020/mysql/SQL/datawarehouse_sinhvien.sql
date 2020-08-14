-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: localhost    Database: datawarehouse
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
-- Table structure for table `sinhvien`
--

DROP TABLE IF EXISTS `sinhvien`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sinhvien` (
  `SK_SINHVIEN` int NOT NULL AUTO_INCREMENT,
  `MSSV` varchar(255) DEFAULT NULL,
  `HO` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `TEN` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `NGAYSINH` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `MALOP` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `TENLOP` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `SDT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `EMAIL` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `QUEQUAN` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `GHICHU` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `DT_EXPIRED` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `DT_LASTCHANGE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`SK_SINHVIEN`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sinhvien`
--

LOCK TABLES `sinhvien` WRITE;
/*!40000 ALTER TABLE `sinhvien` DISABLE KEYS */;
INSERT INTO `sinhvien` VALUES (1,' 17130001',' Nguyễn Văn',' A',' 1/4/1999',' DH17DTA',' Công nghệ thông tin A',' 0123456700',' 17130001@st.hcmuaf.edu.vn',' Bình Dương',' \r','2020-08-09','2020-08-09'),(2,' 17130002',' Nguyễn Văn',' B',' 2/4/1999',' DH17DTA',' Công nghệ thông tin A',' 0123456701',' 17130002@st.hcmuaf.edu.vn',' Bình Dương',' \r','9999-12-31','2020-08-09'),(3,' 17130003',' Nguyễn Văn',' C',' 3/4/1999',' DH17DTA',' Công nghệ thông tin A',' 0123456702',' 17130003@st.hcmuaf.edu.vn',' TPHCM',' \r','2020-08-09','2020-08-09'),(4,' 17130004',' Nguyễn Văn',' D',' 4/4/1999',' DH17DTA',' Công nghệ thông tin A',' 0123456703',' 17130004@st.hcmuaf.edu.vn',' TPHCM',' \r','9999-12-31','2020-08-09'),(5,' 17130005',' Nguyễn Văn',' E',' 5/4/1999',' DH17DTA',' Công nghệ thông tin A',' 0123456704',' 17130005@st.hcmuaf.edu.vn',' Lâm Đồng',' \r','9999-12-31','2020-08-09'),(6,' 17130006',' Nguyễn Văn',' F',' 6/4/1999',' DH17DTA',' Công nghệ thông tin B',' 0123456705',' 17130006@st.hcmuaf.edu.vn',' Lâm Đồng',' \r','9999-12-31','2020-08-09'),(7,' 17130007',' Trần Văn',' G',' 7/4/1999',' DH17DTB',' Công nghệ thông tin B',' 0123456706',' 17130007@st.hcmuaf.edu.vn',' Đồng Nai',' \r','9999-12-31','2020-08-09'),(8,' 17130008',' Trần Văn',' H',' 8/4/1999',' DH17DTB',' Công nghệ thông tin B',' 0123456707',' 17130008@st.hcmuaf.edu.vn',' Đồng Nai',' \r','9999-12-31','2020-08-09'),(9,' 17130009',' Trần Văn',' I',' 9/4/1999',' DH17DTB',' Công nghệ thông tin B',' 0123456708',' 17130009@st.hcmuaf.edu.vn',' Tây Ninh',' \r','9999-12-31','2020-08-09'),(10,' 17130010',' Trần Văn',' J',' 10/4/1999',' DH17DTB',' Công nghệ thông tin B',' 0123456709',' 17130010@st.hcmuaf.edu.vn',' Tây Ninh',' \r','9999-12-31','2020-08-09'),(11,' 17130011',' Trần Văn',' K',' 11/4/1999',' DH17DTC',' Công nghệ thông tin C',' 0123456710',' 17130011@st.hcmuaf.edu.vn',' Quãng Ngãi',' \r','9999-12-31','2020-08-09'),(12,' 17130012',' Nguyễn Thị',' L',' 12/4/1999',' DH17DTC',' Công nghệ thông tin C',' 0123456711',' 17130012@st.hcmuaf.edu.vn',' Quãng Ngãi',' \r','9999-12-31','2020-08-09'),(13,' 17130013',' Nguyễn Thị',' M',' 13/4/1999',' DH17DTC',' Công nghệ thông tin C',' 0123456712',' 17130013@st.hcmuaf.edu.vn',' Đà Nẵng',' \r','9999-12-31','2020-08-09'),(14,' 17130014',' Nguyễn Thị',' N',' 14/4/1999',' DH17DTC',' Công nghệ thông tin C',' 0123456713',' 17130014@st.hcmuaf.edu.vn',' Đà Nẵng',' \r','9999-12-31','2020-08-09'),(15,' 17130015',' Nguyễn Thị',' O',' 15/4/1999',' DH17DTC',' Công nghệ thông tin C',' 0123456714',' 17130015@st.hcmuaf.edu.vn',' Nha Trang',' \r','9999-12-31','2020-08-09'),(16,' 17130016',' Trần Thị',' P',' 16/4/1999',' DH17DTD',' Công nghệ thông tin D',' 0123456715',' 17130016@st.hcmuaf.edu.vn',' Nha Trang',' \r','9999-12-31','2020-08-09'),(17,' 17130017',' Trần Văn',' Q',' 17/4/1999',' DH17DTD',' Công nghệ thông tin D',' 0123456716',' 17130017@st.hcmuaf.edu.vn',' Đắk Lắk',' \r','9999-12-31','2020-08-09'),(18,' 17130018',' Trần Văn',' R',' 18/4/1999',' DH17DTD',' Công nghệ thông tin D',' 0123456717',' 17130018@st.hcmuaf.edu.vn',' Đắk Lắk',' \r','9999-12-31','2020-08-09'),(19,' 17130019',' Trần Văn',' S',' 19/4/1999',' DH17DTD',' Công nghệ thông tin D',' 0123456718',' 17130019@st.hcmuaf.edu.vn',' Kon Tum',' \r','9999-12-31','2020-08-09'),(20,' 17130020',' Trần Văn',' T',' 20/4/1999',' DH17DTD',' Công nghệ thông tin D',' 0123456719',' 17130020@st.hcmuaf.edu.vn',' Kon Tum',' \r','9999-12-31','2020-08-09'),(21,' 17130021',' Cục Súc',' U',' 21/4/1999',' DH17DTE',' Công nghệ thông tin E',' 0123456720',' 17130021@st.hcmuaf.edu.vn',' Plei Ku',' \r','9999-12-31','2020-08-09'),(22,' 17130022',' Cục Súc',' V',' 22/4/1999',' DH17DTE',' Công nghệ thông tin E',' 0123456721',' 17130022@st.hcmuaf.edu.vn',' Plei Ku',' \r','9999-12-31','2020-08-09'),(23,' 17130023',' Cục Súc',' W',' 23/4/1999',' DH17DTE',' Công nghệ thông tin E',' 0123456722',' 17130023@st.hcmuaf.edu.vn',' Tiền Giang',' \r','9999-12-31','2020-08-09'),(24,' 17130024',' Hết Tên',' X',' 24/4/1999',' DH17DTF',' Công nghệ thông tin F',' 0123456723',' 17130024@st.hcmuaf.edu.vn',' Tiền Giang',' \r','9999-12-31','2020-08-09'),(25,' 17130025',' Hết Tên',' Y',' 25/4/1999',' DH17DTF',' Công nghệ thông tin F',' 0123456724',' 17130025@st.hcmuaf.edu.vn',' Hậu Giang',' \r','9999-12-31','2020-08-09'),(26,' 17130026',' Hết Tên',' Z',' 26/4/1999',' DH17DTF',' Công nghệ thông tin F',' 0123456725',' 17130026@st.hcmuaf.edu.vn',' Hậu Giang',' ','9999-12-31','2020-08-09'),(27,' 17130001',' Nguyễn Văn',' A',' 1/4/1999',' DH17DTA',' Công nghệ thông tin A',' 0123456700',' 17130001@st.hcmuaf.edu.vn',' TPHCM',' \r','9999-12-31','2020-08-09'),(28,' 17130032',' Nguyễn Văn',' B',' 2/4/1999',' DH17DTA',' Công nghệ thông tin A',' 0123456701',' 17130002@st.hcmuaf.edu.vn',' Bình Dương',' \r','9999-12-31','2020-08-09'),(29,' 17130003',' Nguyễn Văn',' C',' 3/4/1999',' DH17DTA',' Công nghệ thông tin A',' 0123456702',' 17130003@st.hcmuaf.edu.vn',' Bình Dương',' \r','9999-12-31','2020-08-09'),(30,' 17130034',' Nguyễn Văn',' D',' 4/4/1999',' DH17DTA',' Công nghệ thông tin A',' 0123456703',' 17130004@st.hcmuaf.edu.vn',' TPHCM',' \r','9999-12-31','2020-08-09'),(31,' 17130036',' Nguyễn Văn',' F',' 6/4/1999',' DH17DTA',' Công nghệ thông tin B',' 0123456705',' 17130006@st.hcmuaf.edu.vn',' Lâm Đồng',' \r','9999-12-31','2020-08-09'),(32,' 17130107',' Trần Văn',' G',' 7/4/1999',' DH17DTB',' Công nghệ thông tin B',' 0123456706',' 17130007@st.hcmuaf.edu.vn',' Đồng Nai',' \r','9999-12-31','2020-08-09'),(33,' 17130029',' Trần Văn',' I',' 9/4/1999',' DH17DTB',' Công nghệ thông tin B',' 0123456708',' 17130009@st.hcmuaf.edu.vn',' Tây Ninh',' \r','9999-12-31','2020-08-09');
/*!40000 ALTER TABLE `sinhvien` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-08-14 15:46:12
