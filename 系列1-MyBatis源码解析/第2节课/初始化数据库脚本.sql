/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50523
Source Host           : localhost:3306
Source Database       : mybatis

Target Server Type    : MYSQL
Target Server Version : 50523
File Encoding         : 65001

Date: 2018-07-13 00:09:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_health_report_female`
-- ----------------------------
DROP TABLE IF EXISTS `t_health_report_female`;
CREATE TABLE `t_health_report_female` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `item` varchar(50) DEFAULT NULL,
  `score` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_health_report_female
-- ----------------------------
INSERT INTO `t_health_report_female` VALUES ('1', '女生项目1', '80.00');
INSERT INTO `t_health_report_female` VALUES ('2', '女生项目2', '60.00');
INSERT INTO `t_health_report_female` VALUES ('3', '女生项目3', '90.00');

-- ----------------------------
-- Table structure for `t_health_report_male`
-- ----------------------------
DROP TABLE IF EXISTS `t_health_report_male`;
CREATE TABLE `t_health_report_male` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `check_project` varchar(50) DEFAULT NULL,
  `detail` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_health_report_male
-- ----------------------------
INSERT INTO `t_health_report_male` VALUES ('1', '男人项目1', 'A达标');
INSERT INTO `t_health_report_male` VALUES ('2', '男人项目2', 'A达标');
INSERT INTO `t_health_report_male` VALUES ('3', '男人项目3', 'B达标');

-- ----------------------------
-- Table structure for `t_job_history`
-- ----------------------------
DROP TABLE IF EXISTS `t_job_history`;
CREATE TABLE `t_job_history` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `user_id` int(20) DEFAULT NULL,
  `comp_name` varchar(50) DEFAULT NULL,
  `years` int(3) DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_5` (`user_id`),
  CONSTRAINT `fk_5` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_job_history
-- ----------------------------
INSERT INTO `t_job_history` VALUES ('1', '1', '阿里', '2', '程序员');
INSERT INTO `t_job_history` VALUES ('2', '2', '百度', '4', '项目经理');
INSERT INTO `t_job_history` VALUES ('3', '2', '腾讯', '1', '程序员');
INSERT INTO `t_job_history` VALUES ('4', '3', '京东', '1', '测试');
INSERT INTO `t_job_history` VALUES ('5', '3', '网易', '2', '测试主管');
INSERT INTO `t_job_history` VALUES ('6', '3', '享学', '1', '讲师');

-- ----------------------------
-- Table structure for `t_position`
-- ----------------------------
DROP TABLE IF EXISTS `t_position`;
CREATE TABLE `t_position` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `post_name` varchar(20) DEFAULT NULL,
  `note` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_position
-- ----------------------------
INSERT INTO `t_position` VALUES ('1', '总经理', '负责公司日常事务');
INSERT INTO `t_position` VALUES ('2', '零时工', '背锅的');

-- ----------------------------
-- Table structure for `t_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(60) DEFAULT NULL COMMENT '角色名称',
  `note` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('1', '业务人员', '办理日常业务');
INSERT INTO `t_role` VALUES ('2', '管理员', '超级管理员');

-- ----------------------------
-- Table structure for `t_role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `t_role_permission`;
CREATE TABLE `t_role_permission` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `target` varchar(50) DEFAULT NULL,
  `permission` varchar(50) DEFAULT NULL,
  `role_id` int(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_3` (`role_id`),
  CONSTRAINT `fk_3` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role_permission
-- ----------------------------
INSERT INTO `t_role_permission` VALUES ('1', '金库', '增删改查', '2');
INSERT INTO `t_role_permission` VALUES ('2', '秘书', '安排工作', '2');
INSERT INTO `t_role_permission` VALUES ('3', '货物', '送货', '1');
INSERT INTO `t_role_permission` VALUES ('4', '打印机', '打印文档', '1');
INSERT INTO `t_role_permission` VALUES ('5', '电脑', '开关机', '1');

-- ----------------------------
-- Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(60) DEFAULT NULL COMMENT '用户名称',
  `real_name` varchar(60) DEFAULT NULL COMMENT '真实名称',
  `sex` tinyint(3) DEFAULT NULL COMMENT '姓名',
  `mobile` varchar(20) DEFAULT NULL COMMENT '电话',
  `email` varchar(60) DEFAULT NULL COMMENT '邮箱',
  `note` varchar(200) DEFAULT NULL COMMENT '备注',
  `position_id` int(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_4` (`position_id`),
  CONSTRAINT `fk_4` FOREIGN KEY (`position_id`) REFERENCES `t_position` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=111 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'lison', '李小宇', '1', '186995587411', 'lison@qq.com', 'lison的备注', '1');
INSERT INTO `t_user` VALUES ('2', 'james', '陈大雷', '1', '17365987455', 'james@qq.com', 'james的备注', '2');
INSERT INTO `t_user` VALUES ('3', 'cindy', '王美丽', '2', '18695988747', 'xxoo@163.com', 'cindy\'s note', '1');
INSERT INTO `t_user` VALUES ('32', 'mark', '毛毛', '1', '18695988747', 'xxoo@163.com', 'mark\'s note', '1');

-- ----------------------------
-- Table structure for `t_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `user_id` int(20) NOT NULL DEFAULT '0',
  `role_id` int(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`role_id`,`user_id`),
  KEY `fk_1` (`user_id`),
  CONSTRAINT `fk_1` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`),
  CONSTRAINT `fk_2` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES ('1', '1');
INSERT INTO `t_user_role` VALUES ('1', '2');
INSERT INTO `t_user_role` VALUES ('2', '1');
INSERT INTO `t_user_role` VALUES ('3', '2');
