/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50138
 Source Host           : localhost:3306
 Source Schema         : project4

 Target Server Type    : MySQL
 Target Server Version : 50138
 File Encoding         : 65001

 Date: 29/07/2019 21:45:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'course_id',
  `course_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程代码',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程名称',
  `isExam` tinyint(1) NULL DEFAULT NULL COMMENT '考核方式',
  `proportion` float NULL DEFAULT NULL COMMENT '平时成绩占比',
  `introduction` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '课程简介',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES (1, 'JS4204CS115', 'Linux操作系统实验', 0, 0.4, '《操作系统实验（Linux内核）》课程是计算机科学与技术专业的一门专业课，该课程以开源的Linux操作系统为平台，在操作系统基本理论的指导下研究现代操作系统核心功能的具体实现方法。通过该课程的学习，使学生在学习操作系统原理的基础上，对操作系统各个主要功能部件的具体软件实现有一个较为清晰而完整的印象，帮助学生了解深入操作系统核心编程的基本方法，为后续的嵌入式系统设计打下一定的基础。 《操作系统实验（Linux内核）》课程的先行课程主要有《计算机组成原理》、《操作系统原理》和《C语言程序设计》，同时也是后续《嵌入式技术及应用》、《移动终端编程》等课程的基础。');
INSERT INTO `course` VALUES (2, 'JS6108CS112', '操作系统原理', 1, 0.3, '《操作系统原理》是计算机科学与技术专业的基础理论课程，属于核心必修课。先修课程为《数据结构》和《计算机组成原理》。课程内容为操作系统的概念和主要功能，包括进程管理、存储器管理、设备管理、文件系统和用户接口五大管理功能的基本原理和实现机制。全面了解操作系统的体系结构、任务管理模型、资源管理模式、任务调度算法以及资源分配算法等。使学生对系统软件有一个全面的认识，具备运用操作系统平台分析和解决实际问题，进行软件设计和开发的能力。');
INSERT INTO `course` VALUES (3, 'YS6108AL000', '概率论与数理统计A', 1, 0.3, '《概率论与数理统计A》是我校非数学类理工科本科专业一门重要的公共基础必修课，是研究随机现象统计规律性的一门数学学科，是对随机现象进行定量分析的重要工具，具有广泛的应用性。概率论把随机现象抽象为随机变量去研究它的一般规律，数理统计从实际问题中收集到的数据出发，对问题做出推断、预测与决策。概率论是数理统计的基础，数理统计是概率论的应用，同时推动概率论的研究。');
INSERT INTO `course` VALUES (4, 'WY7101AL000', '大学英语1', 1, 0.5, '《大学英语I》是为非英语专业本科一年级学生开设的一门必修公共基础课程。本课程以强化英语语言基础知识与提高语言基本应用能力为主要目标，以先进的外语教学理论为指导，集多种教学模式和教学手段为一体，通过综合课及听说课教学，帮助学生打下英语语言基础，培养学生具备初步的听、说、读、写、译综合应用能力; 让学生熟悉英语交流中的基本知识，能进行与个人信息相关的简单交流；帮助学生掌握良好的语言学习方法，培养英语自主学习能力，为学生在后续相关课程（如英语应用技能型课程、语言文化素养类课程、以及专业英语课程）的学习奠定基础。');
INSERT INTO `course` VALUES (5, 'JS8207CS112', '数据结构', 1, 0.4, '《数据结构》是介于数学、计算机硬件、计算机软件之间的一门核心必修课程，也是计算机学科中一门重要的专业基础课程。该门课程主要介绍如何合理的组织数据、有效地存储和处理数据及如何正确的设计算法并能对算法进行分析与评价。在计算机科学中，《数据结构》不仅是非数值计算程序设计的基础，而且是设计和实现编译程序、操作系统、数据库系统及大型应用程序的重要基础。掌握《数据结构》可为以后有关的计算机课程的学习和从事软件开发奠定良好的基础。');

-- ----------------------------
-- Table structure for grade
-- ----------------------------
DROP TABLE IF EXISTS `grade`;
CREATE TABLE `grade`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'grade_id',
  `user_course_id` int(11) NULL DEFAULT NULL COMMENT '学生课程id',
  `regular_grade` float NULL DEFAULT NULL COMMENT '平时成绩',
  `final_grade` float NULL DEFAULT NULL COMMENT '期末成绩',
  `total_grade` float NULL DEFAULT NULL COMMENT '总成绩',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_Reference_9`(`user_course_id`) USING BTREE,
  CONSTRAINT `FK_Reference_9` FOREIGN KEY (`user_course_id`) REFERENCES `student_course` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 54 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of grade
-- ----------------------------
INSERT INTO `grade` VALUES (28, 106, 83, 80, 81.2);
INSERT INTO `grade` VALUES (29, 111, 57, 75, 67.8);
INSERT INTO `grade` VALUES (30, 116, 63.6, 65, 64.44);
INSERT INTO `grade` VALUES (31, 121, 92.6, 77, 83.24);
INSERT INTO `grade` VALUES (32, 126, 89, 90, 89.6);
INSERT INTO `grade` VALUES (33, 107, 69.5, 90, 83.85);
INSERT INTO `grade` VALUES (34, 112, 64, 85, 78.7);
INSERT INTO `grade` VALUES (35, 117, 54, 60, 58.2);
INSERT INTO `grade` VALUES (36, 122, 74, 55, 60.7);
INSERT INTO `grade` VALUES (37, 127, 90, 90, 90);
INSERT INTO `grade` VALUES (38, 108, 89.5, 80, 83.8);
INSERT INTO `grade` VALUES (39, 113, 69.5, 90, 81.8);
INSERT INTO `grade` VALUES (40, 118, 31.5, 89, 66);
INSERT INTO `grade` VALUES (41, 123, 65.75, 80, 74.3);
INSERT INTO `grade` VALUES (42, 128, 73.6, 90, 83.44);
INSERT INTO `grade` VALUES (43, 109, 59, 80, 69.5);
INSERT INTO `grade` VALUES (44, 114, 59, 80, 69.5);
INSERT INTO `grade` VALUES (45, 119, 24, 80, 52);
INSERT INTO `grade` VALUES (46, 124, 59, 68, 63.5);
INSERT INTO `grade` VALUES (47, 129, 100, 89, 94.5);
INSERT INTO `grade` VALUES (48, 110, 40, 0, 12);
INSERT INTO `grade` VALUES (49, 115, 32.5, 0, 9.75);
INSERT INTO `grade` VALUES (50, 120, 40, 0, 12);
INSERT INTO `grade` VALUES (51, 125, 89.75, 0, 26.925);
INSERT INTO `grade` VALUES (52, 130, 90, 89, 89.3);
INSERT INTO `grade` VALUES (53, 131, 82.5, 40, 61.25);

-- ----------------------------
-- Table structure for grade_type
-- ----------------------------
DROP TABLE IF EXISTS `grade_type`;
CREATE TABLE `grade_type`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'type_id',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '成绩类型名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of grade_type
-- ----------------------------
INSERT INTO `grade_type` VALUES (1, '考勤');
INSERT INTO `grade_type` VALUES (2, '作业');
INSERT INTO `grade_type` VALUES (3, '实验');
INSERT INTO `grade_type` VALUES (4, '测试');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'menu_id',
  `url` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `path` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `component` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `requireAuth` tinyint(1) NULL DEFAULT NULL,
  `parentId` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `parentId`(`parentId`) USING BTREE,
  CONSTRAINT `menu_ibfk_1` FOREIGN KEY (`parentId`) REFERENCES `menu` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, '/', NULL, NULL, '所有', NULL, NULL);
INSERT INTO `menu` VALUES (2, '/', '/home', 'Home', '用户资料', 1, 1);
INSERT INTO `menu` VALUES (3, '/admin/basic/**', '/adm/basic', 'AdmBasic', '基本资料', 1, 2);
INSERT INTO `menu` VALUES (4, '/office/user/**', '/off/user', 'OffUser', '基本资料', 1, 2);
INSERT INTO `menu` VALUES (5, '/', '/home', 'Home', '课程资料', 1, 1);
INSERT INTO `menu` VALUES (6, '/office/course/**', '/off/course', 'OffCourse', '基本资料', 1, 5);
INSERT INTO `menu` VALUES (7, '/', '/home', 'Home', '课程分配', 1, 1);
INSERT INTO `menu` VALUES (8, '/office/teacher/**', '/off/teacher', 'OffTeacher', '教师分配', 1, 7);
INSERT INTO `menu` VALUES (9, '/studentcourse/**', '/off/student', 'OffStudentCourse', '学生分配', 1, 7);
INSERT INTO `menu` VALUES (10, '/', '/home', 'Home', '课程管理', 1, 1);
INSERT INTO `menu` VALUES (11, '/teacher/course/**', '/tea/course', 'TeaCourse', '我的课程', 1, 10);
INSERT INTO `menu` VALUES (12, '/student/course/**', '/stu/course', 'StuCourse', '我的课程', 1, 10);

-- ----------------------------
-- Table structure for menu_role
-- ----------------------------
DROP TABLE IF EXISTS `menu_role`;
CREATE TABLE `menu_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_id` int(11) NULL DEFAULT NULL,
  `role_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_Reference_3`(`menu_id`) USING BTREE,
  INDEX `FK_Reference_4`(`role_id`) USING BTREE,
  CONSTRAINT `FK_Reference_3` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_Reference_4` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of menu_role
-- ----------------------------
INSERT INTO `menu_role` VALUES (1, 3, 1);
INSERT INTO `menu_role` VALUES (2, 4, 2);
INSERT INTO `menu_role` VALUES (3, 6, 2);
INSERT INTO `menu_role` VALUES (4, 8, 2);
INSERT INTO `menu_role` VALUES (5, 9, 2);
INSERT INTO `menu_role` VALUES (6, 11, 3);
INSERT INTO `menu_role` VALUES (7, 12, 4);

-- ----------------------------
-- Table structure for regular_grade
-- ----------------------------
DROP TABLE IF EXISTS `regular_grade`;
CREATE TABLE `regular_grade`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_course_id` int(11) NULL DEFAULT NULL COMMENT '学生课程id',
  `number` int(11) NULL DEFAULT NULL COMMENT '成绩序号',
  `grade` float NULL DEFAULT NULL COMMENT '成绩',
  `type_id` int(11) NULL DEFAULT NULL COMMENT '成绩类型id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_Reference_10`(`type_id`) USING BTREE,
  INDEX `FK_Reference_12`(`user_course_id`) USING BTREE,
  CONSTRAINT `FK_Reference_10` FOREIGN KEY (`type_id`) REFERENCES `grade_type` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_Reference_12` FOREIGN KEY (`user_course_id`) REFERENCES `student_course` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 145 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of regular_grade
-- ----------------------------
INSERT INTO `regular_grade` VALUES (37, 106, 1, 100, 1);
INSERT INTO `regular_grade` VALUES (38, 111, 1, 0, 1);
INSERT INTO `regular_grade` VALUES (39, 116, 1, 100, 1);
INSERT INTO `regular_grade` VALUES (40, 121, 1, 100, 1);
INSERT INTO `regular_grade` VALUES (41, 126, 1, 100, 1);
INSERT INTO `regular_grade` VALUES (42, 106, 2, 100, 1);
INSERT INTO `regular_grade` VALUES (43, 111, 2, 100, 1);
INSERT INTO `regular_grade` VALUES (44, 116, 2, 0, 1);
INSERT INTO `regular_grade` VALUES (45, 121, 2, 100, 1);
INSERT INTO `regular_grade` VALUES (46, 126, 2, 100, 1);
INSERT INTO `regular_grade` VALUES (47, 106, 1, 50, 2);
INSERT INTO `regular_grade` VALUES (48, 111, 1, 60, 2);
INSERT INTO `regular_grade` VALUES (49, 116, 1, 70, 2);
INSERT INTO `regular_grade` VALUES (50, 121, 1, 90, 2);
INSERT INTO `regular_grade` VALUES (51, 126, 1, 80, 2);
INSERT INTO `regular_grade` VALUES (52, 106, 1, 90, 3);
INSERT INTO `regular_grade` VALUES (53, 111, 1, 70, 3);
INSERT INTO `regular_grade` VALUES (54, 116, 1, 88, 3);
INSERT INTO `regular_grade` VALUES (55, 121, 1, 78, 3);
INSERT INTO `regular_grade` VALUES (56, 126, 1, 75, 3);
INSERT INTO `regular_grade` VALUES (57, 107, 1, 100, 1);
INSERT INTO `regular_grade` VALUES (58, 107, 2, 0, 1);
INSERT INTO `regular_grade` VALUES (59, 107, 1, 80, 2);
INSERT INTO `regular_grade` VALUES (60, 107, 1, 75, 4);
INSERT INTO `regular_grade` VALUES (61, 112, 1, 0, 1);
INSERT INTO `regular_grade` VALUES (62, 112, 2, 0, 1);
INSERT INTO `regular_grade` VALUES (63, 112, 1, 100, 2);
INSERT INTO `regular_grade` VALUES (64, 112, 1, 80, 4);
INSERT INTO `regular_grade` VALUES (65, 117, 1, 0, 1);
INSERT INTO `regular_grade` VALUES (66, 117, 2, 100, 1);
INSERT INTO `regular_grade` VALUES (67, 117, 1, 60, 2);
INSERT INTO `regular_grade` VALUES (68, 117, 1, 50, 4);
INSERT INTO `regular_grade` VALUES (69, 122, 1, 100, 1);
INSERT INTO `regular_grade` VALUES (70, 122, 2, 0, 1);
INSERT INTO `regular_grade` VALUES (71, 122, 1, 80, 2);
INSERT INTO `regular_grade` VALUES (72, 122, 1, 90, 4);
INSERT INTO `regular_grade` VALUES (73, 127, 1, 100, 1);
INSERT INTO `regular_grade` VALUES (74, 127, 2, 100, 1);
INSERT INTO `regular_grade` VALUES (75, 127, 1, 90, 2);
INSERT INTO `regular_grade` VALUES (76, 127, 1, 80, 4);
INSERT INTO `regular_grade` VALUES (77, 108, 1, 100, 1);
INSERT INTO `regular_grade` VALUES (78, 108, 2, 100, 1);
INSERT INTO `regular_grade` VALUES (79, 108, 1, 90, 2);
INSERT INTO `regular_grade` VALUES (80, 113, 2, 80, 2);
INSERT INTO `regular_grade` VALUES (81, 108, 2, 80, 2);
INSERT INTO `regular_grade` VALUES (82, 108, 1, 80, 3);
INSERT INTO `regular_grade` VALUES (83, 108, 2, 80, 3);
INSERT INTO `regular_grade` VALUES (84, 113, 1, 0, 1);
INSERT INTO `regular_grade` VALUES (85, 113, 2, 100, 1);
INSERT INTO `regular_grade` VALUES (86, 113, 1, 60, 2);
INSERT INTO `regular_grade` VALUES (87, 113, 1, 100, 3);
INSERT INTO `regular_grade` VALUES (88, 113, 2, 90, 3);
INSERT INTO `regular_grade` VALUES (89, 118, 1, 0, 1);
INSERT INTO `regular_grade` VALUES (90, 118, 2, 0, 1);
INSERT INTO `regular_grade` VALUES (91, 118, 1, 50, 2);
INSERT INTO `regular_grade` VALUES (92, 118, 2, 80, 2);
INSERT INTO `regular_grade` VALUES (93, 118, 1, 0, 3);
INSERT INTO `regular_grade` VALUES (94, 118, 2, 80, 3);
INSERT INTO `regular_grade` VALUES (95, 123, 1, 0, 1);
INSERT INTO `regular_grade` VALUES (96, 123, 2, 100, 1);
INSERT INTO `regular_grade` VALUES (97, 123, 1, 70, 2);
INSERT INTO `regular_grade` VALUES (98, 123, 2, 75, 2);
INSERT INTO `regular_grade` VALUES (99, 123, 1, 90, 3);
INSERT INTO `regular_grade` VALUES (100, 123, 2, 70, 3);
INSERT INTO `regular_grade` VALUES (101, 128, 1, 100, 1);
INSERT INTO `regular_grade` VALUES (102, 128, 2, 100, 1);
INSERT INTO `regular_grade` VALUES (103, 128, 1, 79, 2);
INSERT INTO `regular_grade` VALUES (104, 128, 2, 0, 2);
INSERT INTO `regular_grade` VALUES (105, 128, 1, 55, 3);
INSERT INTO `regular_grade` VALUES (106, 128, 2, 90, 3);
INSERT INTO `regular_grade` VALUES (107, 109, 1, 0, 1);
INSERT INTO `regular_grade` VALUES (108, 109, 2, 100, 1);
INSERT INTO `regular_grade` VALUES (109, 109, 1, 80, 2);
INSERT INTO `regular_grade` VALUES (110, 114, 1, 0, 1);
INSERT INTO `regular_grade` VALUES (111, 114, 2, 100, 1);
INSERT INTO `regular_grade` VALUES (112, 114, 1, 80, 2);
INSERT INTO `regular_grade` VALUES (113, 119, 1, 0, 1);
INSERT INTO `regular_grade` VALUES (114, 119, 2, 0, 1);
INSERT INTO `regular_grade` VALUES (115, 119, 1, 80, 2);
INSERT INTO `regular_grade` VALUES (116, 124, 1, 100, 1);
INSERT INTO `regular_grade` VALUES (117, 124, 2, 0, 1);
INSERT INTO `regular_grade` VALUES (118, 124, 1, 80, 2);
INSERT INTO `regular_grade` VALUES (119, 129, 1, 100, 1);
INSERT INTO `regular_grade` VALUES (120, 129, 2, 100, 1);
INSERT INTO `regular_grade` VALUES (121, 129, 1, 100, 2);
INSERT INTO `regular_grade` VALUES (122, 110, 1, 0, 1);
INSERT INTO `regular_grade` VALUES (123, 110, 2, 0, 1);
INSERT INTO `regular_grade` VALUES (124, 110, 1, 80, 2);
INSERT INTO `regular_grade` VALUES (125, 110, 2, 80, 2);
INSERT INTO `regular_grade` VALUES (126, 115, 1, 0, 1);
INSERT INTO `regular_grade` VALUES (127, 115, 2, 0, 1);
INSERT INTO `regular_grade` VALUES (128, 115, 1, 60, 2);
INSERT INTO `regular_grade` VALUES (129, 115, 2, 70, 2);
INSERT INTO `regular_grade` VALUES (130, 120, 1, 0, 1);
INSERT INTO `regular_grade` VALUES (131, 120, 2, 0, 1);
INSERT INTO `regular_grade` VALUES (132, 120, 1, 70, 2);
INSERT INTO `regular_grade` VALUES (133, 120, 2, 90, 2);
INSERT INTO `regular_grade` VALUES (134, 125, 1, 100, 1);
INSERT INTO `regular_grade` VALUES (135, 125, 2, 100, 1);
INSERT INTO `regular_grade` VALUES (136, 125, 1, 80, 2);
INSERT INTO `regular_grade` VALUES (137, 125, 2, 79, 2);
INSERT INTO `regular_grade` VALUES (138, 130, 1, 100, 1);
INSERT INTO `regular_grade` VALUES (139, 130, 2, 100, 1);
INSERT INTO `regular_grade` VALUES (140, 130, 1, 80, 2);
INSERT INTO `regular_grade` VALUES (141, 130, 2, 80, 2);
INSERT INTO `regular_grade` VALUES (142, 131, 1, 100, 1);
INSERT INTO `regular_grade` VALUES (143, 131, 1, 75, 2);
INSERT INTO `regular_grade` VALUES (144, 131, 2, 55, 2);

-- ----------------------------
-- Table structure for regular_grade_allocation
-- ----------------------------
DROP TABLE IF EXISTS `regular_grade_allocation`;
CREATE TABLE `regular_grade_allocation`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_course_id` int(11) NULL DEFAULT NULL COMMENT '教师课程id',
  `type_id` int(11) NULL DEFAULT NULL COMMENT '成绩类型id',
  `allocation` float NULL DEFAULT NULL COMMENT '平时成绩分配占比',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_Reference_11`(`type_id`) USING BTREE,
  INDEX `FK_Reference_15`(`user_course_id`) USING BTREE,
  CONSTRAINT `FK_Reference_11` FOREIGN KEY (`type_id`) REFERENCES `grade_type` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_Reference_15` FOREIGN KEY (`user_course_id`) REFERENCES `teacher_course` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 48 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of regular_grade_allocation
-- ----------------------------
INSERT INTO `regular_grade_allocation` VALUES (28, 55, 1, 0.5);
INSERT INTO `regular_grade_allocation` VALUES (29, 55, 2, 0.3);
INSERT INTO `regular_grade_allocation` VALUES (30, 55, 3, 0.2);
INSERT INTO `regular_grade_allocation` VALUES (31, 56, 1, 0.3);
INSERT INTO `regular_grade_allocation` VALUES (32, 56, 2, 0.4);
INSERT INTO `regular_grade_allocation` VALUES (33, 56, 4, 0.3);
INSERT INTO `regular_grade_allocation` VALUES (34, 57, 1, 0.4);
INSERT INTO `regular_grade_allocation` VALUES (35, 57, 2, 0.3);
INSERT INTO `regular_grade_allocation` VALUES (36, 57, 3, 0.3);
INSERT INTO `regular_grade_allocation` VALUES (37, 58, 1, 0.7);
INSERT INTO `regular_grade_allocation` VALUES (38, 58, 2, 0.3);
INSERT INTO `regular_grade_allocation` VALUES (39, 59, 1, 0.5);
INSERT INTO `regular_grade_allocation` VALUES (40, 59, 2, 0.5);
INSERT INTO `regular_grade_allocation` VALUES (46, 60, 1, 0.5);
INSERT INTO `regular_grade_allocation` VALUES (47, 60, 2, 0.5);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'role_id',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `nameZh` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色中文名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'ROLE_admin', '管理员');
INSERT INTO `role` VALUES (2, 'ROLE_office', '教务处');
INSERT INTO `role` VALUES (3, 'ROLE_teacher', '教师');
INSERT INTO `role` VALUES (4, 'ROLE_student', '学生');

-- ----------------------------
-- Table structure for student_course
-- ----------------------------
DROP TABLE IF EXISTS `student_course`;
CREATE TABLE `student_course`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '学生课程id',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '学生id',
  `teacher_course_id` int(11) NULL DEFAULT NULL COMMENT '教师课程id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_Reference_5`(`user_id`) USING BTREE,
  INDEX `teacher_course_id`(`teacher_course_id`) USING BTREE,
  CONSTRAINT `FK_Reference_5` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `student_course_ibfk_1` FOREIGN KEY (`teacher_course_id`) REFERENCES `teacher_course` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 132 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of student_course
-- ----------------------------
INSERT INTO `student_course` VALUES (106, 12, 55);
INSERT INTO `student_course` VALUES (107, 12, 56);
INSERT INTO `student_course` VALUES (108, 12, 57);
INSERT INTO `student_course` VALUES (109, 12, 58);
INSERT INTO `student_course` VALUES (110, 12, 59);
INSERT INTO `student_course` VALUES (111, 13, 55);
INSERT INTO `student_course` VALUES (112, 13, 56);
INSERT INTO `student_course` VALUES (113, 13, 57);
INSERT INTO `student_course` VALUES (114, 13, 58);
INSERT INTO `student_course` VALUES (115, 13, 59);
INSERT INTO `student_course` VALUES (116, 14, 55);
INSERT INTO `student_course` VALUES (117, 14, 56);
INSERT INTO `student_course` VALUES (118, 14, 57);
INSERT INTO `student_course` VALUES (119, 14, 58);
INSERT INTO `student_course` VALUES (120, 14, 59);
INSERT INTO `student_course` VALUES (121, 15, 55);
INSERT INTO `student_course` VALUES (122, 15, 56);
INSERT INTO `student_course` VALUES (123, 15, 57);
INSERT INTO `student_course` VALUES (124, 15, 58);
INSERT INTO `student_course` VALUES (125, 15, 59);
INSERT INTO `student_course` VALUES (126, 26, 55);
INSERT INTO `student_course` VALUES (127, 26, 56);
INSERT INTO `student_course` VALUES (128, 26, 57);
INSERT INTO `student_course` VALUES (129, 26, 58);
INSERT INTO `student_course` VALUES (130, 26, 59);
INSERT INTO `student_course` VALUES (131, 64, 60);

-- ----------------------------
-- Table structure for teacher_course
-- ----------------------------
DROP TABLE IF EXISTS `teacher_course`;
CREATE TABLE `teacher_course`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '教师课程id',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '教师id',
  `course_id` int(11) NULL DEFAULT NULL COMMENT '课程id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_Reference_13`(`user_id`) USING BTREE,
  INDEX `FK_Reference_14`(`course_id`) USING BTREE,
  CONSTRAINT `FK_Reference_13` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_Reference_14` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 61 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of teacher_course
-- ----------------------------
INSERT INTO `teacher_course` VALUES (55, 9, 1);
INSERT INTO `teacher_course` VALUES (56, 7, 2);
INSERT INTO `teacher_course` VALUES (57, 8, 5);
INSERT INTO `teacher_course` VALUES (58, 10, 4);
INSERT INTO `teacher_course` VALUES (59, 6, 3);
INSERT INTO `teacher_course` VALUES (60, 65, 4);

-- ----------------------------
-- Table structure for upload
-- ----------------------------
DROP TABLE IF EXISTS `upload`;
CREATE TABLE `upload`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `upload_from` int(11) NULL DEFAULT NULL COMMENT '上传者',
  `upload_to` int(11) NULL DEFAULT NULL COMMENT '上传到',
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '路径',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件名',
  `time` datetime NULL DEFAULT NULL COMMENT '上传时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'user_id',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 66 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '$2a$10$kKp857hg3iHCycmGGOFGAObCwvloEPRMNVDhe8WVUIHQ1E3vbhOVi', '系统管理员');
INSERT INTO `user` VALUES (3, 'office2', '$2a$10$/UhD1c.nnk.Oc2ekwQok5.Riz9xQdmMdbXUqggeuJOi.vfgEUSaIG', '教务处2');
INSERT INTO `user` VALUES (4, 'office3', '$2a$10$vImJyTewmH4fOmC/in7vx.Umf0Ri2oTmnNkaplKfJLEkgT0TEth6G', '教务处3');
INSERT INTO `user` VALUES (6, 'tyxy', '$2a$10$jgfiFbAIFO1xmiS3qljXGeZEvnQ1hCXJ2S6MFxYaODPuBqpf3XdBe', '岳旭媛');
INSERT INTO `user` VALUES (7, 'tlll', '$2a$10$9Hz8jtadYyTTD6Yk2529yu9j2Jxa2iSFCYGFA68DPFhXIrhPlhrlW', '李丽莉');
INSERT INTO `user` VALUES (8, 'twtj', '$2a$10$nybPV63iplV53sBYtLhi6.slFxdZ1U.IETWBSfu7d44zZd2x/r/6m', '王铁军');
INSERT INTO `user` VALUES (9, 'tzyq', '$2a$10$aUGhe40egt9AHzvOZOUKPOFxyp/LWdQINKZ6zwDWGKxUyDsXSkW0C', '张永清');
INSERT INTO `user` VALUES (10, 'twt', '$2a$10$yHwO66vy5R0RTtR9f0SGZuIsuFT7sGQ/XzNGLD9gkyrZPs3YZ8FP.', '王婷');
INSERT INTO `user` VALUES (11, 'tzym', '$2a$10$yjuJoraSN9pKngx0mmL0t.fHm1aSTtOs3NMVDi02OHsvZdAZy2ohu', '张元茂');
INSERT INTO `user` VALUES (12, '2016051053', '$2a$10$8qihgHuDHvjwdaUAvq8yf.0O4w919YRAoLUDu9y44iA9KKWBxZ7FO', '姚胤');
INSERT INTO `user` VALUES (13, '2016051054', '$2a$10$i1RBxLUhQ6S/KsgFLXKQsuqilghVWwZJG.hrfxrO9zW2ajpxCNpWe', '蒋飞杰');
INSERT INTO `user` VALUES (14, '2016051055', '$2a$10$eeimNqEyGj9zWfhIKXghHOVY.yY1xRbfgphVFEvEqQfT/rQU9N8RC', '李盟');
INSERT INTO `user` VALUES (15, '2016051056', '$2a$10$wdMg1/DobcpQabU6T7jIvepJxby/3iMHe2hpwhw2KnHrVfpNOS90e', '李子嵩');
INSERT INTO `user` VALUES (17, '2016051057', '$2a$10$P2WupuVDof2RCDM/YPle7uxqM5jQEo0lwp1pvGXbj7.cF7QPl7EDi', '毛波');
INSERT INTO `user` VALUES (18, '2016051058', '$2a$10$k08egdhQQ6hpFEZLE86lo.lAzUBKe9Mai3oZEZ2ZrdeAnULxK0LXS', '张向田');
INSERT INTO `user` VALUES (26, '2016051059', '$2a$10$lyJEyVJ3M14iCoonawwBy.5WZrTvwzKCGi8Ywyo6mPoL6NDqtmsSi', '曹枭雄');
INSERT INTO `user` VALUES (27, '2016051078', '$2a$10$LL3LiI/K4vuLfShVx8Ob9.TvsVTJwaX.KiEhAxylhBVtXUo8/abU.', '林历祺');
INSERT INTO `user` VALUES (28, 's1', '$2a$10$z.YayEAufszhYd.LrivOfeI6WajSHBNxe6d.CWgLDEgFYA6qPDo9a', '学生1');
INSERT INTO `user` VALUES (31, 'o1', '$2a$10$n7EPZr3Wbbhx5JonpREVteGJVMdidL8G2HGGlnOcvDXVW5gQ43zYe', '教务处1');
INSERT INTO `user` VALUES (32, '2016051060', '$2a$10$aA03yjKAQVQ0qjyjHJ9GrecVI4Hif1Ek4B6viK0.3fn0TxLyUeJqq', '曾治钞');
INSERT INTO `user` VALUES (37, 't1', '$2a$10$4HeawX0GfvY/hmRfFfUrquQOmk6RnHmQJCS1sD9ZRH9I6K9i3ZEEa', '教师1');
INSERT INTO `user` VALUES (38, '2016051061', '$2a$10$Fv3Q9kibd9.t5pKIinio4OZj9cxob/dGg9b9xckTmJy7VxF8pGmh.', '古淦');
INSERT INTO `user` VALUES (49, '2016051062', '$2a$10$oFcrSwJYIlbJdGaMFzIi2uPZTrrMPiTA/g/AQePQ9yxKGu5TXZfH.', '熊浔');
INSERT INTO `user` VALUES (50, '2016051064', '$2a$10$yDmn9jFCLlxuEhCG5JTjsOlkpi4Ed5J3o9X4//NZW5sEyUrawDzNe', '黄鹏');
INSERT INTO `user` VALUES (51, 'tfa', '$2a$10$U0hW.bHNU6o2v6avUbTWh.udgOS9k0F5hdwdnNsDqj.LDlFxgo0Xm', '冯翱');
INSERT INTO `user` VALUES (52, 'tyb', '$2a$10$hPG2I3NLO2VVouPV/hWEEOk8legk//JzRsMJlt.8iViTuLrptY5gO', '叶斌');
INSERT INTO `user` VALUES (53, 'tssl', '$2a$10$WwXMVraSY/OAf4bw3z73NelS2gNI.E1gwB8gKENS0jsUVtPVsdbbK', '孙莎岚');
INSERT INTO `user` VALUES (54, 'tyh', '$2a$10$NQ3h4FN9/BY9AqqD3ByBz.P8FvxZ8UEyKV4QscFkQyt9LfluFLiBC', '杨昊');
INSERT INTO `user` VALUES (55, 'thm', '$2a$10$.RY69uBjXu0XHaMEMrTqcO8fScl.rG3x8HPXwDSzpB4q8HPnUaqAy', '黄敏');
INSERT INTO `user` VALUES (56, 'tlg', '$2a$10$6C8b/slIT9pZICbowlcMrOzTJ1e4SbVOdH3Ay2KDoaQ4Dm.nawE5y', '林岗');
INSERT INTO `user` VALUES (57, '2016051065', '$2a$10$vHSTcVsN9Bfyp8WFfXiqGusRmCjasnr5OkpCYr4Ve2XrAjnwUDVl6', '周峰');
INSERT INTO `user` VALUES (58, '2016051066', '$2a$10$SDfPBrii2D1JEObUaOztzeamererCZf1xaF4HPtjeAtgVRJYMHpMm', '陈阔');
INSERT INTO `user` VALUES (59, '2016051067', '$2a$10$7gCKXCRBj8Y42OBf8ZxIAuAw2RL7ZmUdYB0jX41t4nrCnT21I300K', '游鑫');
INSERT INTO `user` VALUES (60, '2016051068', '$2a$10$8XEHfhO2umVGX1SCpLMone3wghDX9kuRUh1I6XnmEsnHXosVEJAAy', '谭生源');
INSERT INTO `user` VALUES (61, '2016051069', '$2a$10$ImcrWU3EtY5wHKMX6gUY4.neOLFWsrNXKwFRpQ67fdX9GdeW/Dx9W', '张小勇');
INSERT INTO `user` VALUES (62, 't2', '$2a$10$Q/fTaHwFaU2Lsfkx8Ph2AeZbjnvF1Ytpy.f7WkDZD8oXPr1tQqdVO', '教师2');
INSERT INTO `user` VALUES (63, 's2', '$2a$10$QMDrLEhOryYbRf53VQwJbeY1ot7o/2JhEDi/Od5kKCS0D0Rh54Zc2', '学生2');
INSERT INTO `user` VALUES (64, 's3', '$2a$10$7YPE/xHOkwN/zIyQSKNlXuGx4mDSefIFB6D.7NC1X/E45o48UWwAu', '学生3');
INSERT INTO `user` VALUES (65, 't3', '$2a$10$d6MMj4xwCdCo5VKbxUD29ut61uA6k3cuhD9RcM1bgHIqhL3VJJRL6', '教师3');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NULL DEFAULT NULL COMMENT '角色id',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_Reference_1`(`role_id`) USING BTREE,
  INDEX `FK_Reference_2`(`user_id`) USING BTREE,
  CONSTRAINT `FK_Reference_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_Reference_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 131 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1, 1);
INSERT INTO `user_role` VALUES (3, 2, 3);
INSERT INTO `user_role` VALUES (4, 2, 4);
INSERT INTO `user_role` VALUES (48, 3, 37);
INSERT INTO `user_role` VALUES (49, 2, 31);
INSERT INTO `user_role` VALUES (50, 4, 28);
INSERT INTO `user_role` VALUES (91, 4, 12);
INSERT INTO `user_role` VALUES (92, 4, 13);
INSERT INTO `user_role` VALUES (93, 4, 14);
INSERT INTO `user_role` VALUES (94, 4, 15);
INSERT INTO `user_role` VALUES (95, 4, 17);
INSERT INTO `user_role` VALUES (97, 4, 18);
INSERT INTO `user_role` VALUES (99, 4, 27);
INSERT INTO `user_role` VALUES (100, 4, 32);
INSERT INTO `user_role` VALUES (101, 4, 38);
INSERT INTO `user_role` VALUES (102, 4, 49);
INSERT INTO `user_role` VALUES (103, 4, 50);
INSERT INTO `user_role` VALUES (104, 4, 57);
INSERT INTO `user_role` VALUES (105, 4, 58);
INSERT INTO `user_role` VALUES (106, 4, 59);
INSERT INTO `user_role` VALUES (107, 4, 60);
INSERT INTO `user_role` VALUES (108, 4, 61);
INSERT INTO `user_role` VALUES (114, 3, 11);
INSERT INTO `user_role` VALUES (115, 3, 51);
INSERT INTO `user_role` VALUES (116, 3, 52);
INSERT INTO `user_role` VALUES (117, 3, 53);
INSERT INTO `user_role` VALUES (118, 3, 54);
INSERT INTO `user_role` VALUES (119, 3, 55);
INSERT INTO `user_role` VALUES (120, 3, 56);
INSERT INTO `user_role` VALUES (121, 3, 62);
INSERT INTO `user_role` VALUES (122, 4, 63);
INSERT INTO `user_role` VALUES (123, 4, 26);
INSERT INTO `user_role` VALUES (124, 3, 6);
INSERT INTO `user_role` VALUES (125, 3, 7);
INSERT INTO `user_role` VALUES (126, 3, 8);
INSERT INTO `user_role` VALUES (127, 3, 9);
INSERT INTO `user_role` VALUES (128, 3, 10);
INSERT INTO `user_role` VALUES (129, 4, 64);
INSERT INTO `user_role` VALUES (130, 3, 65);

SET FOREIGN_KEY_CHECKS = 1;
