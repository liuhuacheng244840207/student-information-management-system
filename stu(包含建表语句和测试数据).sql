/*
 Navicat Premium Data Transfer

 Source Server         : liu
 Source Server Type    : MySQL
 Source Server Version : 90100
 Source Host           : localhost:3306
 Source Schema         : stu

 Target Server Type    : MySQL
 Target Server Version : 90100
 File Encoding         : 65001

 Date: 22/12/2025 19:55:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for 学生表
-- ----------------------------
DROP TABLE IF EXISTS `学生表`;
CREATE TABLE `学生表`  (
  `学生ID` int NOT NULL AUTO_INCREMENT COMMENT '学生唯一标识，自增主键',
  `姓名` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '学生姓名，不能为空',
  `性别` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学生性别，通常用“男”“女”表示',
  `学号` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '学生学号，唯一且不能为空',
  `班级` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学生所在班级',
  `联系方式` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学生联系电话等',
  PRIMARY KEY (`学生ID`) USING BTREE,
  UNIQUE INDEX `学号`(`学号`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '存储学生基本信息的表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of 学生表
-- ----------------------------
INSERT INTO `学生表` VALUES (1, '张三', '男', '2025001', '计科一班', '11111111');
INSERT INTO `学生表` VALUES (2, '李四', '男', '2025002', '信安二班', '22222222');
INSERT INTO `学生表` VALUES (3, '王五', '女', '2025003', '机器人一班', '33333333');
INSERT INTO `学生表` VALUES (4, '仇九', '男', '2025006', '大数据科学一班', '6596786');
INSERT INTO `学生表` VALUES (5, '柳贯一', '男', '2025005', '计科一班', '55555555');
INSERT INTO `学生表` VALUES (6, '古月方正', '男', '2025000', '物联网信息工程', '66666666');

-- ----------------------------
-- Table structure for 成绩表
-- ----------------------------
DROP TABLE IF EXISTS `成绩表`;
CREATE TABLE `成绩表`  (
  `成绩ID` int NOT NULL AUTO_INCREMENT COMMENT '成绩记录唯一标识，自增主键',
  `选课ID` int NOT NULL COMMENT '关联选课表的选课ID',
  `平时成绩` decimal(4, 1) NULL DEFAULT NULL COMMENT '课程的平时成绩（如百分制）',
  `期末成绩` decimal(4, 1) NULL DEFAULT NULL COMMENT '课程的期末成绩（如百分制）',
  `总评成绩` decimal(4, 1) NULL DEFAULT NULL COMMENT '综合平时与期末的最终成绩',
  PRIMARY KEY (`成绩ID`) USING BTREE,
  INDEX `选课ID`(`选课ID`) USING BTREE,
  CONSTRAINT `成绩表_ibfk_1` FOREIGN KEY (`选课ID`) REFERENCES `选课表` (`选课ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '记录学生课程成绩的表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of 成绩表
-- ----------------------------
INSERT INTO `成绩表` VALUES (14, 5, 90.0, 40.0, 55.0);
INSERT INTO `成绩表` VALUES (15, 4, 70.0, 90.0, 84.0);
INSERT INTO `成绩表` VALUES (17, 1, 50.0, 70.0, 64.0);
INSERT INTO `成绩表` VALUES (20, 11, 50.0, 40.0, 43.0);
INSERT INTO `成绩表` VALUES (23, 5, 70.0, 80.0, 77.0);
INSERT INTO `成绩表` VALUES (24, 2, 80.0, 70.0, 73.0);
INSERT INTO `成绩表` VALUES (25, 13, 60.0, 70.0, 67.0);

-- ----------------------------
-- Table structure for 课程表
-- ----------------------------
DROP TABLE IF EXISTS `课程表`;
CREATE TABLE `课程表`  (
  `课程ID` int NOT NULL AUTO_INCREMENT COMMENT '课程唯一标识，自增主键',
  `课程名称` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '课程的名称，不能为空',
  `学分` int NULL DEFAULT NULL COMMENT '课程对应的学分',
  `授课教师` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '负责授课的教师姓名',
  `上课时间` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '课程的授课时间（如“周一1-2节”）',
  `地点` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '课程的授课地点（如“教学楼302室”）',
  PRIMARY KEY (`课程ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '存储课程基本信息的表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of 课程表
-- ----------------------------
INSERT INTO `课程表` VALUES (1, '毛泽东思想', 2, '霍老师', '星期二上午第一节课', '一号楼一楼阶梯四');
INSERT INTO `课程表` VALUES (2, '习近平概论', 2, '费老师', '星期五下午第二节课', '一号楼二楼阶梯四');
INSERT INTO `课程表` VALUES (3, '线性代数', 2, '汤老师', '星期四下午第一节课', '一号楼二楼阶梯一');
INSERT INTO `课程表` VALUES (4, '电子技术基础', 5, '俞老师', '星期二下午第一节课', '七号楼二零二');
INSERT INTO `课程表` VALUES (5, '面向对象编程', 3, '杨老师', '星期一下午第三节课', '七号楼三零六');

-- ----------------------------
-- Table structure for 选课表
-- ----------------------------
DROP TABLE IF EXISTS `选课表`;
CREATE TABLE `选课表`  (
  `选课ID` int NOT NULL AUTO_INCREMENT COMMENT '选课记录唯一标识，自增主键',
  `学生ID` int NOT NULL COMMENT '关联学生表的学生ID',
  `课程ID` int NOT NULL COMMENT '关联课程表的课程ID',
  `选课时间` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `状态` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '选课状态（如“已选”“退选”“审核中”）',
  PRIMARY KEY (`选课ID`) USING BTREE,
  INDEX `学生ID`(`学生ID`) USING BTREE,
  INDEX `课程ID`(`课程ID`) USING BTREE,
  CONSTRAINT `选课表_ibfk_1` FOREIGN KEY (`学生ID`) REFERENCES `学生表` (`学生ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `选课表_ibfk_2` FOREIGN KEY (`课程ID`) REFERENCES `课程表` (`课程ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '记录学生选课信息的关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of 选课表
-- ----------------------------
INSERT INTO `选课表` VALUES (1, 1, 1, NULL, '已选');
INSERT INTO `选课表` VALUES (2, 1, 2, NULL, '已选');
INSERT INTO `选课表` VALUES (3, 2, 3, NULL, '已选');
INSERT INTO `选课表` VALUES (4, 2, 2, '2025-12-17 21:27:', '已选');
INSERT INTO `选课表` VALUES (5, 3, 4, '2025-09-24', '已选课');
INSERT INTO `选课表` VALUES (7, 4, 2, '2025-08-29', '已选课');
INSERT INTO `选课表` VALUES (10, 1, 3, '2025-09-01', '已选');
INSERT INTO `选课表` VALUES (11, 3, 1, '2025-9-2', '已选课');
INSERT INTO `选课表` VALUES (12, 5, 5, '2025-9-16', '已选课');
INSERT INTO `选课表` VALUES (13, 5, 3, '2025-8-28', '已选课');

SET FOREIGN_KEY_CHECKS = 1;
