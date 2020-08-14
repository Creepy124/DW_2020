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
-- Dumping routines for database 'datawarehouse'
--
/*!50003 DROP PROCEDURE IF EXISTS `LoadClassDim` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `LoadClassDim`(in MaLH varchar(50),in MaMH int,in NamHoc varchar(50))
BEGIN
DECLARE fact_MaMH INT(11);
SET fact_MaMH = (SELECT mh.SK_MONHOC FROM datawarehouse.monhoc mh WHERE mh.MAMH = MaMH and mh.DT_EXPIRED = '9999-12-31');
IF (fact_MaMH IS NOT NULL) THEN
	if not exists(SELECT * FROM datawarehouse.lophoc lh WHERE lh.MALH = MaLH and lh.SK_MONHOC = fact_MaMH and lh.NAMHOC = NamHoc)
	then
		IF EXISTS (SELECT * FROM datawarehouse.lophoc WHERE lophoc.SK_MONHOC = fact_MaMH and lophoc.DT_EXPIRED='9999-12-31') 
		THEN
			update datawarehouse.lophoc set lophoc.DT_EXPIRED=curdate() where lophoc.SK_MONHOC = fact_MaMH and lophoc.DT_EXPIRED='9999-12-31';
			insert into datawarehouse.lophoc(MALH,`SK_MONHOC`,NAMHOC,DT_EXPIRED,DT_LASTCHANGE) values(MaLH,fact_MaMH,NamHoc,'9999-12-31',curdate());
		ELSE
			insert into datawarehouse.lophoc(MALH,`SK_MONHOC`,NAMHOC,DT_EXPIRED,DT_LASTCHANGE) values(MaLH,fact_MaMH,NamHoc,'9999-12-31',curdate());
		END IF;
	END IF;
END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `loaddangky` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `loaddangky`()
BEGIN
DECLARE MADK VARCHAR(255);
DECLARE MSSV VARCHAR(255);
DECLARE MALH VARCHAR(255);
DECLARE THOIGIANDK VARCHAR(255);

DECLARE finished INTEGER DEFAULT 0;

DECLARE register_cursor CURSOR FOR SELECT dk.MADK,dk.MSSV,dk.MALH,dk.THOIGIANDK FROM staging.dangky dk;
DECLARE CONTINUE HANDLER FOR NOT FOUND SET finished = 1;

OPEN register_cursor;
getRegister: LOOP
	FETCH register_cursor INTO MADK,MSSV,MALH,THOIGIANDK;
	CALL LoadRegisterDim(MADK,MSSV,MALH,THOIGIANDK);
	IF finished = 1 THEN 
			LEAVE getRegister;
		END IF;
END LOOP getRegister;
CLOSE register_cursor;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `loadlophoc` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `loadlophoc`()
BEGIN
DECLARE MALH VARCHAR(255);
DECLARE MAMH VARCHAR(255);
DECLARE NAMHOC VARCHAR(255);

DECLARE finished INTEGER DEFAULT 0;

DECLARE class_cursor CURSOR FOR SELECT lh.MaLH,lh.MaMH,lh.Namhoc FROM staging.lophoc lh;
DECLARE CONTINUE HANDLER FOR NOT FOUND SET finished = 1;

OPEN class_cursor;
getClass: LOOP
	FETCH class_cursor INTO MALH,MAMH,NAMHOC;
	CALL LoadClassDim(MALH,MAMH,NAMHOC);
	IF finished = 1 THEN 
			LEAVE getClass;
		END IF;
END LOOP getClass;
CLOSE class_cursor;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `loadmonhoc` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `loadmonhoc`()
BEGIN
DECLARE MAMH VARCHAR(255);
DECLARE TENMH VARCHAR(255);
DECLARE TINCHI VARCHAR(255);
DECLARE BMQUANLY VARCHAR(255);
DECLARE BMDANGSUDUNG VARCHAR(255);
DECLARE GHICHU VARCHAR(255);

DECLARE finished INTEGER DEFAULT 0;

DECLARE subject_cursor CURSOR FOR SELECT mh.MaMH,mh.TenMH,mh.TinChi,mh.BMQuanLy,mh.BMDangSuDung,mh.GhiChu FROM staging.monhoc mh;
DECLARE CONTINUE HANDLER FOR NOT FOUND SET finished = 1;

OPEN subject_cursor;
getSubject: LOOP
	FETCH subject_cursor INTO MAMH,TENMH,TINCHI,BMQUANLY,BMDANGSUDUNG,GHICHU;
	CALL LoadSubjectDim(MAMH,TENMH,TINCHI,BMQUANLY,BMDANGSUDUNG,GHICHU);
	IF finished = 1 THEN 
			LEAVE getSubject;
		END IF;
END LOOP getSubject;
CLOSE subject_cursor;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `LoadRegisterDim` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `LoadRegisterDim`(in MaDK varchar(255),in MSSV int,in MaLH varchar(255),in ThoiGianDK varchar(50))
begin
  DECLARE fact_MSSV int(11);
	DECLARE fact_MaLH int(11);
	
	SET fact_MSSV = (SELECT sv.SK_SINHVIEN FROM datawarehouse.sinhvien sv WHERE sv.MSSV = MSSV AND sv.DT_EXPIRED='9999-12-31');
	SET fact_MaLH = (SELECT lh.SK_LOPHOC FROM datawarehouse.lophoc lh WHERE lh.MALH = MaLH AND lh.DT_EXPIRED='9999-12-31');
	
	IF (fact_MSSV IS NOT NULL and fact_MaLH IS NOT NULL) THEN
		IF NOT EXISTS(SELECT * FROM datawarehouse.dangky dk WHERE dk.MADK = MaDK AND dk.SK_SINHVIEN = fact_MSSV AND dk.SK_LOPHOC = fact_MaLH AND dk.THOIGIANDK = ThoiGianDK)
		THEN
			IF EXISTS (SELECT * FROM datawarehouse.dangky WHERE dangky.MADK = MaDK AND dangky.SK_SINHVIEN = fact_MSSV AND dangky.THOIGIANDK = ThoiGianDK)
			THEN
				UPDATE datawarehouse.dangky SET dangky.DT_EXPIRED = curdate() WHERE dangky.MADK = MaDK AND dangky.SK_SINHVIEN = fact_MSSV AND dangky.THOIGIANDK = ThoiGianDK;
				INSERT INTO datawarehouse.dangky(MADK,`SK_SINHVIEN`,`SK_LOPHOC`,THOIGIANDK,DT_EXPIRED,DT_LASTCHANGE) VALUES(MaDK,fact_MSSV,fact_MaLH,ThoiGianDK,'9999-12-31', curDate());
            ELSE
				INSERT INTO datawarehouse.dangky(MADK,`SK_SINHVIEN`,`SK_LOPHOC`,THOIGIANDK,DT_EXPIRED,DT_LASTCHANGE) VALUES(MaDK,fact_MSSV,fact_MaLH,ThoiGianDK,'9999-12-31', curDate());	
			END IF;
        END IF;
	END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `loadsinhvien` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `loadsinhvien`()
BEGIN
DECLARE MSSV VARCHAR(255);
DECLARE HO VARCHAR(255);
DECLARE TEN VARCHAR(255);
DECLARE NGAYSINH VARCHAR(255);
DECLARE MALOP VARCHAR(255);
DECLARE TENLOP VARCHAR(255);
DECLARE SDT VARCHAR(255);
DECLARE EMAIL VARCHAR(255);
DECLARE QUEQUAN VARCHAR(255);
DECLARE GHICHU VARCHAR(255);

DECLARE finished INTEGER DEFAULT 0;

DECLARE student_cursor CURSOR FOR SELECT sv.mssv,sv.ho,sv.ten,sv.ngaysinh,sv.malop,sv.tenlop,sv.sdt,sv.email,sv.quequan,sv.ghichu FROM staging.sinhvien sv;
DECLARE CONTINUE HANDLER FOR NOT FOUND SET finished = 1;

OPEN student_cursor;
getStudent: LOOP
	FETCH student_cursor INTO MSSV,HO,TEN,NGAYSINH,MALOP,TENLOP,SDT,EMAIL,QUEQUAN,GHICHU;
	CALL LoadStudentDim(MSSV,HO,TEN,NGAYSINH,MALOP,TENLOP,SDT,EMAIL,QUEQUAN,GHICHU);
	IF finished = 1 THEN 
			LEAVE getStudent;
		END IF;
END LOOP getStudent;
CLOSE student_cursor;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `LoadStudentDim` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `LoadStudentDim`(in mssv varchar(50),in ho varchar(50),in ten varchar(50),in ngaysinh varchar(50),in malop varchar(50),in tenlop varchar(50),in sdt varchar(50),in email varchar(50),in quequan varchar(50),in ghichu varchar(50))
begin

if not exists(SELECT * FROM datawarehouse.sinhvien sv WHERE sv.mssv = mssv and sv.ho = ho and sv.ten = ten and sv.ngaysinh = ngaysinh and sv.malop = malop and sv.tenlop = tenlop and sv.sdt = sdt and sv.email = email and sv.quequan = quequan and sv.ghichu = ghichu)
	then
If EXISTS (SELECT * FROM sinhvien WHERE sinhvien.mssv= mssv and sinhvien.dt_expired='9999-12-31') 
then
	update sinhvien set sinhvien.dt_expired=curdate() where sinhvien.mssv= mssv and sinhvien.dt_expired='9999-12-31';
	insert into sinhvien(mssv,ho,ten,ngaysinh,malop,tenlop,sdt,email,quequan,ghichu,dt_expired,dt_lastchange) 
			values(mssv,ho,ten,ngaysinh,malop,tenlop,sdt,email,quequan,ghichu,'9999-12-31',curdate());
ELSE
   insert into sinhvien(mssv,ho,ten,ngaysinh,malop,tenlop,sdt,email,quequan,ghichu,dt_expired,dt_lastchange) 
			values(mssv,ho,ten,ngaysinh,malop,tenlop,sdt,email,quequan,ghichu,'9999-12-31',curdate());
end if;
end if;
end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `LoadSubjectDim` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `LoadSubjectDim`(in MaMH varchar(225),in TenMH varchar(225),in TinChi varchar(225),in BMQuanLy varchar(225),in BMDangSuDung varchar(225),in GhiChu varchar(225))
begin
if not exists(SELECT * FROM warehouse.monhoc mh WHERE mh.MaMH = MaMH and mh.TenMH = TenMH and mh.TinChi = TinChi and mh.BMQuanLy = BMQuanLy and mh.BMDangSuDung = BMDangSuDung and mh.GhiChu = GhiChu)
	then
If EXISTS (SELECT * FROM monhoc WHERE monhoc.tenmh= TenMH and monhoc.dt_expired='9999-12-31') then
	update monhoc set monhoc.dt_expired=curdate() where monhoc.tenmh= TenMH and monhoc.dt_expired='9999-12-31';
	insert into monhoc(MaMH,TenMH,TinChi,BMQuanLy,BMDangSuDung,GhiChu,dt_expired,dt_lastchange) values(MaMH,TenMH,TinChi,BMQuanLy,BMDangSuDung,GhiChu,'9999-12-31',curdate());
ELSE
   insert into monhoc(MaMH,TenMH,TinChi,BMQuanLy,BMDangSuDung,GhiChu,dt_expired,dt_lastchange) values(MaMH,TenMH,TinChi,BMQuanLy,BMDangSuDung,GhiChu,'9999-12-31',curdate());
end if;
end if;
end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-08-14 15:46:12
