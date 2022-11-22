/*
 Navicat Premium Data Transfer

 Source Server         : mysql-1
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : localhost:3306
 Source Schema         : staff_management

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 22/11/2022 19:27:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_admin
-- ----------------------------
DROP TABLE IF EXISTS `t_admin`;
CREATE TABLE `t_admin`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `admin_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs NOT NULL COMMENT '管理员账号',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs NOT NULL COMMENT '密码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_as_cs ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_admin
-- ----------------------------
INSERT INTO `t_admin` VALUES (1, 'admin', '123456');

-- ----------------------------
-- Table structure for t_staff
-- ----------------------------
DROP TABLE IF EXISTS `t_staff`;
CREATE TABLE `t_staff`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键id(员工号)',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs NOT NULL COMMENT '员工姓名',
  `sex` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs NOT NULL COMMENT '性别',
  `department` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs NOT NULL COMMENT '部门名称',
  `phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs NOT NULL COMMENT '手机号',
  `birth` date NOT NULL COMMENT '出身日期',
  `hire_time` date NOT NULL COMMENT '雇佣日期',
  `salary` double(10, 2) NOT NULL COMMENT '月薪',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1003 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_as_cs ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_staff
-- ----------------------------
INSERT INTO `t_staff` VALUES (1000, 'wang', '女', '董事会', '13762203152', '1990-02-15', '2022-11-20', 2000.00);
INSERT INTO `t_staff` VALUES (1001, 'wang2', '女', 'NASA', '13762203152', '1999-01-02', '2022-11-20', 400.00);
INSERT INTO `t_staff` VALUES (1002, '张三', '男', 'NASA', '13762203152', '1999-02-19', '2022-11-19', 500.00);
INSERT INTO `t_staff` VALUES (1007, '王五', '女', 'FBI', '13762203152', '2000-10-28', '2022-11-22', 500.00);
INSERT INTO `t_staff` VALUES (1008, '赵六', '男', 'FBI', '13973279129', '2000-10-20', '2022-11-21', 5000.00);

SET FOREIGN_KEY_CHECKS = 1;
