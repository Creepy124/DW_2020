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

 Date: 12/07/2020 12:09:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log`  (
  `config_id` int(11) NOT NULL,
  `file_name` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `file_type` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `action` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `status` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `file_timestamp` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of log
-- ----------------------------
INSERT INTO `log` VALUES (1, 'sinhvien_chieu_nhom16.xlsx', 'xlsx', 'ER', NULL, '2020-06-25 07:03:00');
INSERT INTO `log` VALUES (1, 'sinhvien_chieu_nhom16.xlsx', 'xlsx', 'TR', NULL, '2020-06-25 07:33:02');
INSERT INTO `log` VALUES (1, 'sinhvien_sang_nhom1.txt', 'txt', 'ER', NULL, '2020-06-25 07:51:58');
INSERT INTO `log` VALUES (1, 'sinhvien_chieu_nhom16.xlsx', 'xlsx', 'ER', NULL, '2020-06-25 15:21:32');
INSERT INTO `log` VALUES (1, 'data_1999-12-10_018.txt', 'txt', 'ER', NULL, NULL);
INSERT INTO `log` VALUES (1, 'data_1999-12-10_018.txt', 'txt', 'TR', NULL, '2020-06-28 22:48:24');

SET FOREIGN_KEY_CHECKS = 1;
