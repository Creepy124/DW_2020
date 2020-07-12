/*
 Navicat Premium Data Transfer

 Source Server         : Localhost
 Source Server Type    : MySQL
 Source Server Version : 100406
 Source Host           : localhost:3306
 Source Schema         : control

 Target Server Type    : MySQL
 Target Server Version : 100406
 File Encoding         : 65001

 Date: 12/07/2020 12:09:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for configuration
-- ----------------------------
DROP TABLE IF EXISTS `configuration`;
CREATE TABLE `configuration`  (
  `config_id` int(11) NOT NULL,
  `config_name` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `source_host` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `source_remote_path` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `source_username` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `source_password` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `source_port` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `file_name` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `file_column_list` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `value` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `download_path` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of configuration
-- ----------------------------
INSERT INTO `configuration` VALUES (1, 'sinhvien', 'drive.ecepvn.org', 'volume1/ECEP/song.nguyen/DW_2020/data/', 'guest_access', '123456', '2227', '17130005_Sang_Nhom03.csv', 'stt,mssv,ho,ten,ngaysinh,malop,tenlop,sdt,email,quequan,ghichu', 'int,int,varchar(50),varchar(50),varchar(50),varchar(50),varchar(50),varchar(50),varchar(50),varchar(50),varchar(50)', 'Local\\\\test');
INSERT INTO `configuration` VALUES (2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'mssv,ho,ten,gioitinh,malop,tenlop', 'varchar(50),varchar(50),varchar(50),varchar(50),varchar(50),varchar(50)', NULL);
INSERT INTO `configuration` VALUES (3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'mssv,ho,ten,ngaysinh,malop,tenlop,sdt,email,quequan,ghichu', 'int,varchar(50),varchar(50),varchar(50),varchar(50),varchar(50),varchar(50),varchar(50),varchar(50),varchar(50)', NULL);

SET FOREIGN_KEY_CHECKS = 1;
