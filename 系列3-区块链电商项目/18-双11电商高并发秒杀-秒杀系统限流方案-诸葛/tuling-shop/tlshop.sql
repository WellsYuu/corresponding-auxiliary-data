/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : jeeshop

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-03-29 19:54:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_account`
-- ----------------------------
DROP TABLE IF EXISTS `t_account`;
CREATE TABLE `t_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `nickname` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `city` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `postcode` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `cardType` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `cardNO` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `grade` int(11) DEFAULT '1',
  `amount` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tel` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `emailIsActive` varchar(2) COLLATE utf8_unicode_ci DEFAULT 'n',
  `freeze` varchar(2) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'n',
  `lastLoginTime` datetime DEFAULT NULL,
  `lastLoginIp` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `lastLoginArea` varchar(25) COLLATE utf8_unicode_ci DEFAULT NULL,
  `diffAreaLogin` char(1) COLLATE utf8_unicode_ci DEFAULT 'n',
  `regeistDate` datetime DEFAULT NULL,
  `freezeStartdate` date DEFAULT NULL,
  `freezeEnddate` date DEFAULT NULL,
  `openId` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `accessToken` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `alipayUseId` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sinaWeiboID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sex` varchar(2) COLLATE utf8_unicode_ci DEFAULT 's',
  `trueName` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `province` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `accountType` varchar(25) COLLATE utf8_unicode_ci DEFAULT NULL,
  `rank` varchar(5) COLLATE utf8_unicode_ci DEFAULT 'R1',
  `score` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `account_UNIQUE` (`account`),
  UNIQUE KEY `alipayUseId_UNIQUE` (`alipayUseId`),
  UNIQUE KEY `openId_UNIQUE` (`openId`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `sinaWeiboID_UNIQUE` (`sinaWeiboID`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_account
-- ----------------------------
INSERT INTO `t_account` VALUES ('32', 'test1', 'test1', 'e10adc3949ba59abbe56e057f20f883e', null, null, null, null, null, '1', null, null, '543089122@qq.com', 'y', 'n', '2014-06-30 21:23:23', '127.0.0.1', '[]', 'n', '2014-03-26 15:27:50', null, null, null, null, null, null, 's', null, null, null, null, 'R3', '1816');
INSERT INTO `t_account` VALUES ('33', '_out_1395897863364', '_out_', null, '340500', null, null, null, null, '1', null, null, null, 'n', 'n', null, null, null, 'n', '2014-03-27 13:24:23', null, null, null, null, null, '2496035714', 's', null, null, '340000', 'sinawb', 'R1', '0');
INSERT INTO `t_account` VALUES ('34', '_out_1395897992912', '_out_', null, null, null, null, null, null, '1', null, null, null, 'n', 'n', null, null, null, 'n', '2014-03-27 13:26:32', null, null, null, null, '2088402374896120', null, 's', null, null, null, 'alipay', 'R1', '0');
INSERT INTO `t_account` VALUES ('35', 'ceshi01', 'ceshi01', 'e10adc3949ba59abbe56e057f20f883e', null, null, null, null, null, '1', null, null, '2533037475@qq.com', 'y', 'n', '2014-04-08 14:26:34', '115.236.52.106', '浙江省杭州市[电信]', 'n', '2014-03-31 14:20:59', null, null, null, null, null, null, 's', null, null, null, null, 'R1', '0');
INSERT INTO `t_account` VALUES ('36', '_out_1396246903068', '_out_', null, null, null, null, null, null, '1', null, null, null, 'n', 'n', null, null, null, 'n', '2014-03-31 14:21:43', null, null, null, null, '2088102613270011', null, 's', null, null, null, 'alipay', 'R1', '0');
INSERT INTO `t_account` VALUES ('37', '_out_1397288196687', '_out_', null, null, null, null, null, null, '1', null, null, null, 'n', 'n', null, null, null, 'n', '2014-04-12 15:36:36', null, null, '974EFAD675ED80DD59826A396E3EEE18', null, null, null, 's', null, null, null, 'qq', 'R1', '0');
INSERT INTO `t_account` VALUES ('38', '_out_1397461177517', '_out_', null, null, null, null, null, null, '1', null, null, null, 'n', 'n', null, null, null, 'n', '2014-04-14 15:39:37', null, null, '59844635636D0E5872A219616A71BD90', null, null, null, 's', null, null, null, 'qq', 'R1', '0');
INSERT INTO `t_account` VALUES ('39', '_out_1398580073687', '_out_', null, null, null, null, null, null, '1', null, null, null, 'n', 'n', null, null, null, 'n', '2014-04-27 14:27:53', null, null, '52F5308B7A5E8A8C9637AD35DF1007B7', null, null, null, 's', null, null, null, 'qq', 'R1', '0');
INSERT INTO `t_account` VALUES ('40', 'lip1', '沐舞', '4297f44b13955235245b2497399d7a93', '440500', null, null, null, null, '1', null, null, '422453219@qq.com', 'y', 'n', '2014-05-04 10:17:27', '180.175.184.161', '上海市上海市[电信]', 'n', '2014-05-04 10:10:26', null, null, null, null, null, null, 's', null, null, '440000', null, 'R1', '0');
INSERT INTO `t_account` VALUES ('41', '_out_1399301353546', '_out_', null, null, null, null, null, null, '1', null, null, null, 'n', 'n', null, null, null, 'n', '2014-05-05 22:49:13', null, null, '57D1C7AF95F53290F1968974E26734F3', null, null, null, 's', null, null, null, 'qq', 'R1', '0');
INSERT INTO `t_account` VALUES ('42', '_out_1402305688002', '_out_', null, null, null, null, null, null, '1', null, null, null, 'n', 'n', null, null, null, 'n', '2014-06-09 17:21:28', null, null, 'B961FBB041EE409F7A49E3359F38E673', null, null, null, 's', null, null, null, 'qq', 'R1', '0');
INSERT INTO `t_account` VALUES ('43', '_out_1402324498066', '_out_', null, null, null, null, null, null, '1', null, null, null, 'n', 'n', null, null, null, 'n', '2014-06-09 22:34:58', null, null, 'EF5401E028F567C4D4B57D9DB9587E6A', null, null, null, 's', null, null, null, 'qq', 'R1', '0');
INSERT INTO `t_account` VALUES ('44', 'wukong', 'wukong', 'eabd8ce9404507aa8c22714d3f5eada9', '430100', null, null, null, null, '1', null, null, '245553999@qq.com', 'y', 'n', '2018-03-23 21:31:21', '127.0.0.1', '内网IP内网IP[local]', 'n', '2018-03-04 21:39:41', null, null, null, null, null, null, 'f', '悟空', '2018-03-18', '430000', null, 'R1', '0');

-- ----------------------------
-- Table structure for `t_accountrank`
-- ----------------------------
DROP TABLE IF EXISTS `t_accountrank`;
CREATE TABLE `t_accountrank` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `minScore` int(11) DEFAULT NULL,
  `maxScore` int(11) DEFAULT NULL,
  `remark` varchar(445) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_accountrank
-- ----------------------------
INSERT INTO `t_accountrank` VALUES ('1', 'R1', '普通会员', '0', '0', '1');
INSERT INTO `t_accountrank` VALUES ('2', 'R2', '铜牌会员', '0', '999', null);
INSERT INTO `t_accountrank` VALUES ('3', 'R3', '银牌会员', '1000', '1999', null);
INSERT INTO `t_accountrank` VALUES ('4', 'R4', '金牌会员', '2000', '4000', null);
INSERT INTO `t_accountrank` VALUES ('5', 'R5', '钻石会员', '4000', '0', null);

-- ----------------------------
-- Table structure for `t_activity`
-- ----------------------------
DROP TABLE IF EXISTS `t_activity`;
CREATE TABLE `t_activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `accountRange` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `startDate` datetime NOT NULL,
  `endDate` datetime NOT NULL,
  `content` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` char(1) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'n',
  `catalogs` varchar(245) COLLATE utf8_unicode_ci DEFAULT NULL,
  `discountType` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `discount` decimal(8,2) DEFAULT NULL,
  `minprice` decimal(8,2) DEFAULT NULL,
  `maxprice` decimal(8,2) DEFAULT NULL,
  `maxSellCount` int(11) DEFAULT NULL,
  `productID` varchar(245) COLLATE utf8_unicode_ci DEFAULT NULL,
  `activityType` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `exchangeScore` int(11) DEFAULT NULL,
  `minGroupCount` int(11) DEFAULT NULL,
  `tuanPrice` decimal(8,2) DEFAULT NULL,
  `hasBuyGroupPerson` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_activity
-- ----------------------------
INSERT INTO `t_activity` VALUES ('1', '降价促销', 'R1,R2', '2018-03-23 21:12:24', '2018-10-24 21:12:14', null, 'y', null, 'r', '20.00', null, null, '10', '10317|10316|10315|10314|10313', 'c', '0', '0', null, '0');
INSERT INTO `t_activity` VALUES ('2', '打折促销', 'R1,R3,R4', '2018-03-23 21:12:07', '2018-05-16 21:11:58', null, 'y', null, 'd', '12.00', null, null, '3', '10312|10311|10310|10309', 'c', '0', '0', null, '0');
INSERT INTO `t_activity` VALUES ('3', '买就赠送双倍积分', 'R2,R3', '2018-03-23 21:12:37', '2018-11-22 21:12:30', null, 'y', null, 's', '300.00', null, null, '12', '10306|10305|10304|10303|10302|10301|10300|10299', 'c', '0', '0', null, '0');
INSERT INTO `t_activity` VALUES ('6', '积分商城1980', 'R1,R2', '2018-03-23 21:11:32', '2018-06-23 21:11:21', null, 'y', null, '-', null, null, null, '6', '10271|10272|10273|10274|10282', 'j', '1980', '0', null, '0');

-- ----------------------------
-- Table structure for `t_address`
-- ----------------------------
DROP TABLE IF EXISTS `t_address`;
CREATE TABLE `t_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `address` varchar(245) COLLATE utf8_unicode_ci NOT NULL,
  `zip` varchar(6) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone` varchar(25) COLLATE utf8_unicode_ci DEFAULT NULL,
  `mobile` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  `isdefault` varchar(2) COLLATE utf8_unicode_ci DEFAULT 'n',
  `province` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `city` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `area` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `pcadetail` varchar(75) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_address
-- ----------------------------
INSERT INTO `t_address` VALUES ('1', '222', '2222', '2', '2', '2', '222', 'y', null, null, null, null);
INSERT INTO `t_address` VALUES ('4', 'test1', '黄飞', '普陀区真关路真元小区10号楼301', '433221', '15000748603', '15000748603', 'y', '330000', '330500', null, null);
INSERT INTO `t_address` VALUES ('5', 'test1', '黄飞', '普陀区真关路真元小区10号楼301', '433221', '15000748603', '15000748603', 'n', '140000', '140200', null, null);
INSERT INTO `t_address` VALUES ('7', 'test2', '张飞', '回复考虑好联合利华', '321312', '312312313', '15000748603', 'y', null, null, null, null);
INSERT INTO `t_address` VALUES ('8', 'test2', '李飞', '4234', '4234', '234234', '15000748603', 'n', null, null, null, null);
INSERT INTO `t_address` VALUES ('9', 'test2', '53453', '535', '3535', '353', '15000748603', 'n', null, null, null, null);
INSERT INTO `t_address` VALUES ('10', '_out_1392903252104', 'rw', 'r', 'werwer', 'ewrr', '15000748603', 'y', null, null, null, null);
INSERT INTO `t_address` VALUES ('11', '_out_1393056233281', '李飞', '恶的风景的是辣的叫老垃圾的辣的叫阿里的骄傲', '333333', '3232132', '15000748603', 'y', '140000', '140300', null, null);
INSERT INTO `t_address` VALUES ('12', '_out_1393056233281', '岳飞', '宋朝四平路的萨达十大大大大大大大', '321321', '32132132', '15000748603', 'n', '360000', '360600', null, null);
INSERT INTO `t_address` VALUES ('13', '_out_1395013499587', '张三丰', '新年里201', '111111', '15000748603', '15000748603', 'y', '120000', '120100', '120110', null);
INSERT INTO `t_address` VALUES ('14', '_out_1393560390544', '张三', '花开花落后路', '111111', '15000748603', '15000748603', 'y', '120000', '120100', '120103', null);
INSERT INTO `t_address` VALUES ('15', '_out_1395897992912', '435345', '535', '535535', '13111111111', '13111111111', 'y', '620000', '620600', '620623', null);
INSERT INTO `t_address` VALUES ('16', 'ceshi01', '张运峰', '张运峰', '100000', '010-12345678', '13145378912', 'n', '620000', '620700', '620725', '甘肃省 张掖市 山丹县');
INSERT INTO `t_address` VALUES ('17', 'lip1', '测试', '测试', '123410', '021-5555555555', '13211111111', 'y', '310000', '310100', '310105', '上海 上海市 长宁区');
INSERT INTO `t_address` VALUES ('18', '_out_1399301353546', '的的 啊', '打牌快点  ', '265362', '655485', '15985662565', 'y', '140000', '140200', '140222', '山西省 大同市 天镇县');
INSERT INTO `t_address` VALUES ('19', '_out_1397461177517', '23', '3213', '312312', '13111111111', '13111111111', 'y', '120000', '120100', '120105', '天津 天津市 河北区');
INSERT INTO `t_address` VALUES ('20', 'wukong', '孙悟空', '岳麓区企业广场2', '410100', '1212', '13787063330', 'y', '430000', '430100', '430104', '湖南省 长沙市 岳麓区');

-- ----------------------------
-- Table structure for `t_advert`
-- ----------------------------
DROP TABLE IF EXISTS `t_advert`;
CREATE TABLE `t_advert` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `code` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `remark` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `html` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `startdate` date DEFAULT NULL,
  `enddate` date DEFAULT NULL,
  `status` varchar(2) COLLATE utf8_unicode_ci DEFAULT 'y',
  `useImagesRandom` varchar(2) COLLATE utf8_unicode_ci DEFAULT 'n',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_advert
-- ----------------------------
INSERT INTO `t_advert` VALUES ('10', '首页头部广告', 'index_top', null, '<div>\r\n	<a href=\"http://www.aliyun.com/?spm=5176.914900.2.1.YbCBQO\" target=\"_blank\" title=\"首页头部广告\"><img alt=\"mycms-logo\" style=\"width:100%;\" src=\"http://info-database.csdn.net/Upload/2013-12-03/qnx-728-90-1202.jpg\" /></a> \r\n</div>', '2014-01-09', '2014-01-31', 'y', null);
INSERT INTO `t_advert` VALUES ('11', '首页右侧头部广告', 'index_right_top', null, '<div>\r\n	<a href=\"http://www.aliyun.com/?spm=5176.914900.2.1.YbCBQO\" target=\"_blank\"><img alt=\"mycms-logo\" style=\"width:100%;\" src=\"http://images.csdn.net/20140108/%E6%9C%AA%E5%91%BD%E5%90%8D.jpg\" /> </a>\r\n</div>', '2014-01-09', '2014-01-31', 'y', null);
INSERT INTO `t_advert` VALUES ('12', '首页右侧底部广告', 'index_right_bottom', null, '<div>\r\n	<a href=\"http://www.aliyun.com/?spm=5176.914900.2.1.YbCBQO\" target=\"_blank\">\r\n<img alt=\"mycms-logo\" style=\"width:100%;\" src=\"http://market.21cn.com/w/free/meiti/2013new/213.jpg\" />\r\n</a></div>', null, null, 'y', null);
INSERT INTO `t_advert` VALUES ('13', '新闻页面-右侧头部广告', 'newslist_right_top', null, null, null, null, 'y', null);
INSERT INTO `t_advert` VALUES ('14', '登陆页面广告', 'advert_login_page', null, '<div class=\"row\" style=\"text-align:left;\">\r\n	<p>\r\n		<b>IT-else软件技术论坛</b> \r\n	</p>\r\n</div>\r\n<div class=\"row\" style=\"text-align:left;\">\r\n	<h4>\r\n		<p>\r\n			来到这里，你可以：\r\n		</p>\r\n	</h4>\r\n</div>\r\n<div class=\"row\" style=\"text-align:left;\">\r\n	<p>\r\n		1、可以和大家分享技术心得\r\n	</p>\r\n</div>\r\n<div class=\"row\" style=\"text-align:left;\">\r\n	<p>\r\n		2、可以交到更多的朋友\r\n	</p>\r\n</div>\r\n<div class=\"row\" style=\"text-align:left;\">\r\n	<p>\r\n		3、可以和这里的小伙伴们分享技术以外的事情\r\n	</p>\r\n</div>\r\n<div class=\"row\" style=\"text-align:left;\">\r\n	<p>\r\n		4、有趣的，无趣的，搞笑的，烦<span style=\"line-height:1.5;\">恼的，这些是我们的初衷。</span> \r\n	</p>\r\n</div>\r\n<div class=\"row\" style=\"text-align:left;\">\r\n	<p>\r\n		5、当然，还有很多...我们正在开发<span style=\"line-height:1.5;\">完善当中...</span><img src=\"http://i.mmcdn.cn/simba/img/T1M6L9FcFgXXb1upjX.jpg\" width=\"100%\" height=\"100\" alt=\"\" /> \r\n	</p>\r\n</div>', null, null, 'y', 'y');
INSERT INTO `t_advert` VALUES ('15', '注册页面广告', 'advert_register_page', null, null, null, null, 'y', 'y');

-- ----------------------------
-- Table structure for `t_area`
-- ----------------------------
DROP TABLE IF EXISTS `t_area`;
CREATE TABLE `t_area` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(45) NOT NULL,
  `pcode` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `desc1` varchar(245) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16870 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_area
-- ----------------------------
INSERT INTO `t_area` VALUES ('13729', '621227', '621200', '徽县', null);
INSERT INTO `t_area` VALUES ('13730', '621228', '621200', '两当县', null);
INSERT INTO `t_area` VALUES ('13731', '621225', '621200', '西和县', null);
INSERT INTO `t_area` VALUES ('13732', '621226', '621200', '礼县', null);
INSERT INTO `t_area` VALUES ('13733', '431230', '431200', '通道侗族自治县', null);
INSERT INTO `t_area` VALUES ('13734', '320282', '320200', '江阴市', null);
INSERT INTO `t_area` VALUES ('13735', '320282', '320200', '宜兴市', null);
INSERT INTO `t_area` VALUES ('13736', '431222', '431200', '沅陵县', null);
INSERT INTO `t_area` VALUES ('13737', '431223', '431200', '辰溪县', null);
INSERT INTO `t_area` VALUES ('13738', '431224', '431200', '溆浦县', null);
INSERT INTO `t_area` VALUES ('13739', '431225', '431200', '会同县', null);
INSERT INTO `t_area` VALUES ('13740', '431226', '431200', '麻阳苗族自治县', null);
INSERT INTO `t_area` VALUES ('13741', '431227', '431200', '新晃侗族自治县', null);
INSERT INTO `t_area` VALUES ('13742', '431228', '431200', '芷江侗族自治县', null);
INSERT INTO `t_area` VALUES ('13743', '431229', '431200', '靖州苗族侗族自治县', null);
INSERT INTO `t_area` VALUES ('13744', '320296', '320200', '新区', null);
INSERT INTO `t_area` VALUES ('13745', '341126', '341100', '凤阳县', null);
INSERT INTO `t_area` VALUES ('13746', '341125', '341100', '定远县', null);
INSERT INTO `t_area` VALUES ('13747', '341124', '341100', '全椒县', null);
INSERT INTO `t_area` VALUES ('13748', '431221', '431200', '中方县', null);
INSERT INTO `t_area` VALUES ('13749', '341122', '341100', '来安县', null);
INSERT INTO `t_area` VALUES ('13750', '511702', '511700', '通川区', null);
INSERT INTO `t_area` VALUES ('13751', '511700', '510000', '达州市', null);
INSERT INTO `t_area` VALUES ('13752', '350825', '350800', '连城县', null);
INSERT INTO `t_area` VALUES ('13753', '350824', '350800', '武平县', null);
INSERT INTO `t_area` VALUES ('13754', '350823', '350800', '上杭县', null);
INSERT INTO `t_area` VALUES ('13755', '621222', '621200', '文县', null);
INSERT INTO `t_area` VALUES ('13756', '350822', '350800', '永定县', null);
INSERT INTO `t_area` VALUES ('13757', '621221', '621200', '成县', null);
INSERT INTO `t_area` VALUES ('13758', '350821', '350800', '长汀县', null);
INSERT INTO `t_area` VALUES ('13759', '621224', '621200', '康县', null);
INSERT INTO `t_area` VALUES ('13760', '621223', '621200', '宕昌县', null);
INSERT INTO `t_area` VALUES ('13761', '430121', '430100', '长沙县', null);
INSERT INTO `t_area` VALUES ('13762', '230621', '230600', '肇州县', null);
INSERT INTO `t_area` VALUES ('13763', '230622', '230600', '肇源县', null);
INSERT INTO `t_area` VALUES ('13764', '230623', '230600', '林甸县', null);
INSERT INTO `t_area` VALUES ('13765', '230624', '230600', '杜尔伯特蒙古族自治县', null);
INSERT INTO `t_area` VALUES ('13766', '371581', '371500', '临清市', null);
INSERT INTO `t_area` VALUES ('13767', '430111', '430100', '雨花区', null);
INSERT INTO `t_area` VALUES ('13768', '341102', '341100', '琅琊区', null);
INSERT INTO `t_area` VALUES ('13769', '341100', '340000', '滁州市', null);
INSERT INTO `t_area` VALUES ('13770', '140581', '140500', '高平市', null);
INSERT INTO `t_area` VALUES ('13771', '341103', '341100', '南谯区', null);
INSERT INTO `t_area` VALUES ('13772', '232700', '230000', '大兴安岭地区', null);
INSERT INTO `t_area` VALUES ('13773', '430105', '430100', '开福区', null);
INSERT INTO `t_area` VALUES ('13774', '621200', '620000', '陇南市', null);
INSERT INTO `t_area` VALUES ('13775', '430104', '430100', '岳麓区', null);
INSERT INTO `t_area` VALUES ('13776', '621202', '621200', '武都区', null);
INSERT INTO `t_area` VALUES ('13777', '430100', '430000', '长沙市', null);
INSERT INTO `t_area` VALUES ('13778', '430103', '430100', '天心区', null);
INSERT INTO `t_area` VALUES ('13779', '430102', '430100', '芙蓉区', null);
INSERT INTO `t_area` VALUES ('13780', '131023', '131000', '永清县', null);
INSERT INTO `t_area` VALUES ('13781', '131024', '131000', '香河县', null);
INSERT INTO `t_area` VALUES ('13782', '540126', '540100', '达孜县', null);
INSERT INTO `t_area` VALUES ('13783', '131022', '131000', '固安县', null);
INSERT INTO `t_area` VALUES ('13784', '540127', '540100', '墨竹工卡县', null);
INSERT INTO `t_area` VALUES ('13785', '540124', '540100', '曲水县', null);
INSERT INTO `t_area` VALUES ('13786', '340711', '340700', '郊区', null);
INSERT INTO `t_area` VALUES ('13787', '150105', '150100', '赛罕区', null);
INSERT INTO `t_area` VALUES ('13788', '131028', '131000', '大厂回族自治县', null);
INSERT INTO `t_area` VALUES ('13789', '540125', '540100', '堆龙德庆县', null);
INSERT INTO `t_area` VALUES ('13790', '150104', '150100', '玉泉区', null);
INSERT INTO `t_area` VALUES ('13791', '131025', '131000', '大城县', null);
INSERT INTO `t_area` VALUES ('13792', '540122', '540100', '当雄县', null);
INSERT INTO `t_area` VALUES ('13793', '150103', '150100', '回民区', null);
INSERT INTO `t_area` VALUES ('13794', '131026', '131000', '文安县', null);
INSERT INTO `t_area` VALUES ('13795', '540123', '540100', '尼木县', null);
INSERT INTO `t_area` VALUES ('13796', '150102', '150100', '新城区', null);
INSERT INTO `t_area` VALUES ('13797', '150100', '150000', '呼和浩特市', null);
INSERT INTO `t_area` VALUES ('13798', '230606', '230600', '大同区', null);
INSERT INTO `t_area` VALUES ('13799', '230605', '230600', '红岗区', null);
INSERT INTO `t_area` VALUES ('13800', '230604', '230600', '让胡路区', null);
INSERT INTO `t_area` VALUES ('13801', '230603', '230600', '龙凤区', null);
INSERT INTO `t_area` VALUES ('13802', '341182', '341100', '明光市', null);
INSERT INTO `t_area` VALUES ('13803', '230602', '230600', '萨尔图区', null);
INSERT INTO `t_area` VALUES ('13804', '341181', '341100', '天长市', null);
INSERT INTO `t_area` VALUES ('13805', '230600', '230000', '大庆市', null);
INSERT INTO `t_area` VALUES ('13806', '231202', '231200', '北林区', null);
INSERT INTO `t_area` VALUES ('13807', '231200', '230000', '绥化市', null);
INSERT INTO `t_area` VALUES ('13808', '632522', '632500', '同德县', null);
INSERT INTO `t_area` VALUES ('13809', '632523', '632500', '贵德县', null);
INSERT INTO `t_area` VALUES ('13810', '632524', '632500', '兴海县', null);
INSERT INTO `t_area` VALUES ('13811', '632525', '632500', '贵南县', null);
INSERT INTO `t_area` VALUES ('13812', '340721', '340700', '铜陵县', null);
INSERT INTO `t_area` VALUES ('13813', '533400', '530000', '迪庆藏族自治州', null);
INSERT INTO `t_area` VALUES ('13814', '540121', '540100', '林周县', null);
INSERT INTO `t_area` VALUES ('13815', '131003', '131000', '广阳区', null);
INSERT INTO `t_area` VALUES ('13816', '131002', '131000', '安次区', null);
INSERT INTO `t_area` VALUES ('13817', '131000', '130000', '廊坊市', null);
INSERT INTO `t_area` VALUES ('13818', '231222', '231200', '兰西县', null);
INSERT INTO `t_area` VALUES ('13819', '231223', '231200', '青冈县', null);
INSERT INTO `t_area` VALUES ('13820', '231221', '231200', '望奎县', null);
INSERT INTO `t_area` VALUES ('13821', '231226', '231200', '绥棱县', null);
INSERT INTO `t_area` VALUES ('13822', '231224', '231200', '庆安县', null);
INSERT INTO `t_area` VALUES ('13823', '231225', '231200', '明水县', null);
INSERT INTO `t_area` VALUES ('13824', '340703', '340700', '狮子山区', null);
INSERT INTO `t_area` VALUES ('13825', '340702', '340700', '铜官山区', null);
INSERT INTO `t_area` VALUES ('13826', '511721', '511700', '达县', null);
INSERT INTO `t_area` VALUES ('13827', '511722', '511700', '宣汉县', null);
INSERT INTO `t_area` VALUES ('13828', '340700', '340000', '铜陵市', null);
INSERT INTO `t_area` VALUES ('13829', '511723', '511700', '开江县', null);
INSERT INTO `t_area` VALUES ('13830', '511724', '511700', '大竹县', null);
INSERT INTO `t_area` VALUES ('13831', '511725', '511700', '渠县', null);
INSERT INTO `t_area` VALUES ('13832', '431281', '431200', '洪江市', null);
INSERT INTO `t_area` VALUES ('13833', '350800', '350000', '龙岩市', null);
INSERT INTO `t_area` VALUES ('13834', '210782', '210700', '北镇市', null);
INSERT INTO `t_area` VALUES ('13835', '350802', '350800', '新罗区', null);
INSERT INTO `t_area` VALUES ('13836', '533422', '533400', '德钦县', null);
INSERT INTO `t_area` VALUES ('13837', '533421', '533400', '香格里拉县', null);
INSERT INTO `t_area` VALUES ('13838', '430802', '430800', '永定区', null);
INSERT INTO `t_area` VALUES ('13839', '430800', '430000', '张家界市', null);
INSERT INTO `t_area` VALUES ('13840', '430181', '430100', '浏阳市', null);
INSERT INTO `t_area` VALUES ('13841', '451381', '451300', '合山市', null);
INSERT INTO `t_area` VALUES ('13842', '370523', '370500', '广饶县', null);
INSERT INTO `t_area` VALUES ('13843', '370521', '370500', '垦利县', null);
INSERT INTO `t_area` VALUES ('13844', '370522', '370500', '利津县', null);
INSERT INTO `t_area` VALUES ('13845', '320204', '320200', '北塘区', null);
INSERT INTO `t_area` VALUES ('13846', '450921', '450900', '容县', null);
INSERT INTO `t_area` VALUES ('13847', '320203', '320200', '南长区', null);
INSERT INTO `t_area` VALUES ('13848', '320206', '320200', '惠山区', null);
INSERT INTO `t_area` VALUES ('13849', '450923', '450900', '博白县', null);
INSERT INTO `t_area` VALUES ('13850', '320205', '320200', '锡山区', null);
INSERT INTO `t_area` VALUES ('13851', '450922', '450900', '陆川县', null);
INSERT INTO `t_area` VALUES ('13852', '320200', '320000', '无锡市', null);
INSERT INTO `t_area` VALUES ('13853', '430811', '430800', '武陵源区', null);
INSERT INTO `t_area` VALUES ('13854', '450924', '450900', '兴业县', null);
INSERT INTO `t_area` VALUES ('13855', '320202', '320200', '崇安区', null);
INSERT INTO `t_area` VALUES ('13856', '430821', '430800', '慈利县', null);
INSERT INTO `t_area` VALUES ('13857', '430822', '430800', '桑植县', null);
INSERT INTO `t_area` VALUES ('13858', '410306', '410300', '吉利区', null);
INSERT INTO `t_area` VALUES ('13859', '410307', '410300', '洛龙区', null);
INSERT INTO `t_area` VALUES ('13860', '410304', '410300', '廛河回族区', null);
INSERT INTO `t_area` VALUES ('13861', '410305', '410300', '涧西区', null);
INSERT INTO `t_area` VALUES ('13862', '410302', '410300', '老城区', null);
INSERT INTO `t_area` VALUES ('13863', '410303', '410300', '西工区', null);
INSERT INTO `t_area` VALUES ('13864', '410300', '410000', '洛阳市', null);
INSERT INTO `t_area` VALUES ('13865', '540100', '540000', '拉萨市', null);
INSERT INTO `t_area` VALUES ('13866', '542338', '542300', '岗巴县', null);
INSERT INTO `t_area` VALUES ('13867', '540102', '540100', '城关区', null);
INSERT INTO `t_area` VALUES ('13868', '542337', '542300', '萨嘎县', null);
INSERT INTO `t_area` VALUES ('13869', '542336', '542300', '聂拉木县', null);
INSERT INTO `t_area` VALUES ('13870', '542335', '542300', '吉隆县', null);
INSERT INTO `t_area` VALUES ('13871', '542334', '542300', '亚东县', null);
INSERT INTO `t_area` VALUES ('13872', '542333', '542300', '仲巴县', null);
INSERT INTO `t_area` VALUES ('13873', '370502', '370500', '东营区', null);
INSERT INTO `t_area` VALUES ('13874', '370500', '370000', '东营市', null);
INSERT INTO `t_area` VALUES ('13875', '370503', '370500', '河口区', null);
INSERT INTO `t_area` VALUES ('13876', '410325', '410300', '嵩县', null);
INSERT INTO `t_area` VALUES ('13877', '410324', '410300', '栾川县', null);
INSERT INTO `t_area` VALUES ('13878', '410323', '410300', '新安县', null);
INSERT INTO `t_area` VALUES ('13879', '410322', '410300', '孟津县', null);
INSERT INTO `t_area` VALUES ('13880', '410329', '410300', '伊川县', null);
INSERT INTO `t_area` VALUES ('13881', '410328', '410300', '洛宁县', null);
INSERT INTO `t_area` VALUES ('13882', '410327', '410300', '宜阳县', null);
INSERT INTO `t_area` VALUES ('13883', '410326', '410300', '汝阳县', null);
INSERT INTO `t_area` VALUES ('13884', '430124', '430100', '宁乡县', null);
INSERT INTO `t_area` VALUES ('13885', '430122', '430100', '望城县', null);
INSERT INTO `t_area` VALUES ('13886', '232724', '232700', '加格达奇区', null);
INSERT INTO `t_area` VALUES ('13887', '232721', '232700', '呼玛县', null);
INSERT INTO `t_area` VALUES ('13888', '232723', '232700', '漠河县', null);
INSERT INTO `t_area` VALUES ('13889', '232722', '232700', '塔河县', null);
INSERT INTO `t_area` VALUES ('13890', '542330', '542300', '仁布县', null);
INSERT INTO `t_area` VALUES ('13891', '542332', '542300', '定结县', null);
INSERT INTO `t_area` VALUES ('13892', '542331', '542300', '康马县', null);
INSERT INTO `t_area` VALUES ('13893', '542322', '542300', '南木林县', null);
INSERT INTO `t_area` VALUES ('13894', '542323', '542300', '江孜县', null);
INSERT INTO `t_area` VALUES ('13895', '542324', '542300', '定日县', null);
INSERT INTO `t_area` VALUES ('13896', '542325', '542300', '萨迦县', null);
INSERT INTO `t_area` VALUES ('13897', '542326', '542300', '拉孜县', null);
INSERT INTO `t_area` VALUES ('13898', '542327', '542300', '昂仁县', null);
INSERT INTO `t_area` VALUES ('13899', '542328', '542300', '谢通门县', null);
INSERT INTO `t_area` VALUES ('13900', '542329', '542300', '白朗县', null);
INSERT INTO `t_area` VALUES ('13901', '320211', '320200', '滨湖区', null);
INSERT INTO `t_area` VALUES ('13902', '431202', '431200', '鹤城区', null);
INSERT INTO `t_area` VALUES ('13903', '431200', '430000', '怀化市', null);
INSERT INTO `t_area` VALUES ('13904', '450981', '450900', '北流市', null);
INSERT INTO `t_area` VALUES ('13905', '542300', '540000', '日喀则地区', null);
INSERT INTO `t_area` VALUES ('13906', '542301', '542300', '日喀则市', null);
INSERT INTO `t_area` VALUES ('13907', '522622', '522600', '黄平县', null);
INSERT INTO `t_area` VALUES ('13908', '522624', '522600', '三穗县', null);
INSERT INTO `t_area` VALUES ('13909', '522623', '522600', '施秉县', null);
INSERT INTO `t_area` VALUES ('13910', '522626', '522600', '岑巩县', null);
INSERT INTO `t_area` VALUES ('13911', '522625', '522600', '镇远县', null);
INSERT INTO `t_area` VALUES ('13912', '522628', '522600', '锦屏县', null);
INSERT INTO `t_area` VALUES ('13913', '522627', '522600', '天柱县', null);
INSERT INTO `t_area` VALUES ('13914', '522629', '522600', '剑河县', null);
INSERT INTO `t_area` VALUES ('13915', '451302', '451300', '兴宾区', null);
INSERT INTO `t_area` VALUES ('13916', '440507', '440500', '龙湖区', null);
INSERT INTO `t_area` VALUES ('13917', '451300', '450000', '来宾市', null);
INSERT INTO `t_area` VALUES ('13918', '610481', '610400', '兴平市', null);
INSERT INTO `t_area` VALUES ('13919', '441224', '441200', '怀集县', null);
INSERT INTO `t_area` VALUES ('13920', '370481', '370400', '滕州市', null);
INSERT INTO `t_area` VALUES ('13921', '441223', '441200', '广宁县', null);
INSERT INTO `t_area` VALUES ('13922', '440500', '440000', '汕头市', null);
INSERT INTO `t_area` VALUES ('13923', '441226', '441200', '德庆县', null);
INSERT INTO `t_area` VALUES ('13924', '522630', '522600', '台江县', null);
INSERT INTO `t_area` VALUES ('13925', '441225', '441200', '封开县', null);
INSERT INTO `t_area` VALUES ('13926', '522631', '522600', '黎平县', null);
INSERT INTO `t_area` VALUES ('13927', '340000', '0', '安徽省', null);
INSERT INTO `t_area` VALUES ('13928', '211481', '211400', '兴城市', null);
INSERT INTO `t_area` VALUES ('13929', '341800', '340000', '宣城市', null);
INSERT INTO `t_area` VALUES ('13930', '341802', '341800', '宣州区', null);
INSERT INTO `t_area` VALUES ('13931', '440514', '440500', '潮南区', null);
INSERT INTO `t_area` VALUES ('13932', '440515', '440500', '澄海区', null);
INSERT INTO `t_area` VALUES ('13933', '440512', '440500', '濠江区', null);
INSERT INTO `t_area` VALUES ('13934', '440513', '440500', '潮阳区', null);
INSERT INTO `t_area` VALUES ('13935', '440511', '440500', '金平区', null);
INSERT INTO `t_area` VALUES ('13936', '152202', '152200', '阿尔山市', null);
INSERT INTO `t_area` VALUES ('13937', '510302', '510300', '自流井区', null);
INSERT INTO `t_area` VALUES ('13938', '152200', '150000', '兴安盟', null);
INSERT INTO `t_area` VALUES ('13939', '510303', '510300', '贡井区', null);
INSERT INTO `t_area` VALUES ('13940', '152201', '152200', '乌兰浩特市', null);
INSERT INTO `t_area` VALUES ('13941', '510304', '510300', '大安区', null);
INSERT INTO `t_area` VALUES ('13942', '451321', '451300', '忻城县', null);
INSERT INTO `t_area` VALUES ('13943', '451322', '451300', '象州县', null);
INSERT INTO `t_area` VALUES ('13944', '451323', '451300', '武宣县', null);
INSERT INTO `t_area` VALUES ('13945', '451324', '451300', '金秀瑶族自治县', null);
INSERT INTO `t_area` VALUES ('13946', '510311', '510300', '沿滩区', null);
INSERT INTO `t_area` VALUES ('13947', '530630', '530600', '水富县', null);
INSERT INTO `t_area` VALUES ('13948', '522636', '522600', '丹寨县', null);
INSERT INTO `t_area` VALUES ('13949', '522635', '522600', '麻江县', null);
INSERT INTO `t_area` VALUES ('13950', '522634', '522600', '雷山县', null);
INSERT INTO `t_area` VALUES ('13951', '522633', '522600', '从江县', null);
INSERT INTO `t_area` VALUES ('13952', '522632', '522600', '榕江县', null);
INSERT INTO `t_area` VALUES ('13953', '130399', '130300', '经济技术开发区', null);
INSERT INTO `t_area` VALUES ('13954', '510300', '510000', '自贡市', null);
INSERT INTO `t_area` VALUES ('13955', '530629', '530600', '威信县', null);
INSERT INTO `t_area` VALUES ('13956', '530628', '530600', '彝良县', null);
INSERT INTO `t_area` VALUES ('13957', '530627', '530600', '镇雄县', null);
INSERT INTO `t_area` VALUES ('13958', '530626', '530600', '绥江县', null);
INSERT INTO `t_area` VALUES ('13959', '530625', '530600', '永善县', null);
INSERT INTO `t_area` VALUES ('13960', '152224', '152200', '突泉县', null);
INSERT INTO `t_area` VALUES ('13961', '530624', '530600', '大关县', null);
INSERT INTO `t_area` VALUES ('13962', '152223', '152200', '扎赉特旗', null);
INSERT INTO `t_area` VALUES ('13963', '530623', '530600', '盐津县', null);
INSERT INTO `t_area` VALUES ('13964', '152222', '152200', '科尔沁右翼中旗', null);
INSERT INTO `t_area` VALUES ('13965', '530622', '530600', '巧家县', null);
INSERT INTO `t_area` VALUES ('13966', '152221', '152200', '科尔沁右翼前旗', null);
INSERT INTO `t_area` VALUES ('13967', '530621', '530600', '鲁甸县', null);
INSERT INTO `t_area` VALUES ('13968', '611000', '610000', '商洛市', null);
INSERT INTO `t_area` VALUES ('13969', '611002', '611000', '商州区', null);
INSERT INTO `t_area` VALUES ('13970', '522600', '520000', '黔东南苗族侗族自治州', null);
INSERT INTO `t_area` VALUES ('13971', '530602', '530600', '昭阳区', null);
INSERT INTO `t_area` VALUES ('13972', '522601', '522600', '凯里市', null);
INSERT INTO `t_area` VALUES ('13973', '530600', '530000', '昭通市', null);
INSERT INTO `t_area` VALUES ('13974', '441203', '441200', '鼎湖区', null);
INSERT INTO `t_area` VALUES ('13975', '440523', '440500', '南澳县', null);
INSERT INTO `t_area` VALUES ('13976', '441202', '441200', '端州区', null);
INSERT INTO `t_area` VALUES ('13977', '441200', '440000', '肇庆市', null);
INSERT INTO `t_area` VALUES ('13978', '611024', '611000', '山阳县', null);
INSERT INTO `t_area` VALUES ('13979', '611025', '611000', '镇安县', null);
INSERT INTO `t_area` VALUES ('13980', '611026', '611000', '柞水县', null);
INSERT INTO `t_area` VALUES ('13981', '611021', '611000', '洛南县', null);
INSERT INTO `t_area` VALUES ('13982', '611022', '611000', '丹凤县', null);
INSERT INTO `t_area` VALUES ('13983', '611023', '611000', '商南县', null);
INSERT INTO `t_area` VALUES ('13984', '341822', '341800', '广德县', null);
INSERT INTO `t_area` VALUES ('13985', '341823', '341800', '泾县', null);
INSERT INTO `t_area` VALUES ('13986', '341824', '341800', '绩溪县', null);
INSERT INTO `t_area` VALUES ('13987', '341825', '341800', '旌德县', null);
INSERT INTO `t_area` VALUES ('13988', '360281', '360200', '乐平市', null);
INSERT INTO `t_area` VALUES ('13989', '341821', '341800', '郎溪县', null);
INSERT INTO `t_area` VALUES ('13990', '420684', '420600', '宜城市', null);
INSERT INTO `t_area` VALUES ('13991', '330523', '330500', '安吉县', null);
INSERT INTO `t_area` VALUES ('13992', '610400', '610000', '咸阳市', null);
INSERT INTO `t_area` VALUES ('13993', '360222', '360200', '浮梁县', null);
INSERT INTO `t_area` VALUES ('13994', '350782', '350700', '武夷山市', null);
INSERT INTO `t_area` VALUES ('13995', '330521', '330500', '德清县', null);
INSERT INTO `t_area` VALUES ('13996', '350781', '350700', '邵武市', null);
INSERT INTO `t_area` VALUES ('13997', '330522', '330500', '长兴县', null);
INSERT INTO `t_area` VALUES ('13998', '350784', '350700', '建阳市', null);
INSERT INTO `t_area` VALUES ('13999', '131081', '131000', '霸州市', null);
INSERT INTO `t_area` VALUES ('14000', '350783', '350700', '建瓯市', null);
INSERT INTO `t_area` VALUES ('14001', '131082', '131000', '三河市', null);
INSERT INTO `t_area` VALUES ('14002', '321003', '321000', '邗江区', null);
INSERT INTO `t_area` VALUES ('14003', '321002', '321000', '广陵区', null);
INSERT INTO `t_area` VALUES ('14004', '211400', '210000', '葫芦岛市', null);
INSERT INTO `t_area` VALUES ('14005', '211403', '211400', '龙港区', null);
INSERT INTO `t_area` VALUES ('14006', '211402', '211400', '连山区', null);
INSERT INTO `t_area` VALUES ('14007', '533323', '533300', '福贡县', null);
INSERT INTO `t_area` VALUES ('14008', '211404', '211400', '南票区', null);
INSERT INTO `t_area` VALUES ('14009', '533321', '533300', '泸水县', null);
INSERT INTO `t_area` VALUES ('14010', '321011', '321000', '维扬区', null);
INSERT INTO `t_area` VALUES ('14011', '340800', '340000', '安庆市', null);
INSERT INTO `t_area` VALUES ('14012', '140603', '140600', '平鲁区', null);
INSERT INTO `t_area` VALUES ('14013', '340802', '340800', '迎江区', null);
INSERT INTO `t_area` VALUES ('14014', '340803', '340800', '大观区', null);
INSERT INTO `t_area` VALUES ('14015', '140602', '140600', '朔城区', null);
INSERT INTO `t_area` VALUES ('14016', '632600', '630000', '果洛藏族自治州', null);
INSERT INTO `t_area` VALUES ('14017', '420683', '420600', '枣阳市', null);
INSERT INTO `t_area` VALUES ('14018', '140600', '140000', '朔州市', null);
INSERT INTO `t_area` VALUES ('14019', '420682', '420600', '老河口市', null);
INSERT INTO `t_area` VALUES ('14020', '511681', '511600', '华蓥市', null);
INSERT INTO `t_area` VALUES ('14021', '511682', '511600', '市辖区', null);
INSERT INTO `t_area` VALUES ('14022', '532822', '532800', '勐海县', null);
INSERT INTO `t_area` VALUES ('14023', '532823', '532800', '勐腊县', null);
INSERT INTO `t_area` VALUES ('14024', '341881', '341800', '宁国市', null);
INSERT INTO `t_area` VALUES ('14025', '210781', '210700', '凌海市', null);
INSERT INTO `t_area` VALUES ('14026', '321023', '321000', '宝应县', null);
INSERT INTO `t_area` VALUES ('14027', '120102', '120100', '河东区', null);
INSERT INTO `t_area` VALUES ('14028', '120103', '120100', '河西区', null);
INSERT INTO `t_area` VALUES ('14029', '120104', '120100', '南开区', null);
INSERT INTO `t_area` VALUES ('14030', '120105', '120100', '河北区', null);
INSERT INTO `t_area` VALUES ('14031', '610422', '610400', '三原县', null);
INSERT INTO `t_area` VALUES ('14032', '330502', '330500', '吴兴区', null);
INSERT INTO `t_area` VALUES ('14033', '120100', '120000', '天津市', null);
INSERT INTO `t_area` VALUES ('14034', '330500', '330000', '湖州市', null);
INSERT INTO `t_area` VALUES ('14035', '120101', '120100', '和平区', null);
INSERT INTO `t_area` VALUES ('14036', '330503', '330500', '南浔区', null);
INSERT INTO `t_area` VALUES ('14037', '120106', '120100', '红桥区', null);
INSERT INTO `t_area` VALUES ('14038', '211421', '211400', '绥中县', null);
INSERT INTO `t_area` VALUES ('14039', '120107', '120100', '塘沽区', null);
INSERT INTO `t_area` VALUES ('14040', '120108', '120100', '汉沽区', null);
INSERT INTO `t_area` VALUES ('14041', '120109', '120100', '大港区', null);
INSERT INTO `t_area` VALUES ('14042', '211422', '211400', '建昌县', null);
INSERT INTO `t_area` VALUES ('14043', '632624', '632600', '达日县', null);
INSERT INTO `t_area` VALUES ('14044', '140623', '140600', '右玉县', null);
INSERT INTO `t_area` VALUES ('14045', '130323', '130300', '抚宁县', null);
INSERT INTO `t_area` VALUES ('14046', '632623', '632600', '甘德县', null);
INSERT INTO `t_area` VALUES ('14047', '140624', '140600', '怀仁县', null);
INSERT INTO `t_area` VALUES ('14048', '130324', '130300', '卢龙县', null);
INSERT INTO `t_area` VALUES ('14049', '632622', '632600', '班玛县', null);
INSERT INTO `t_area` VALUES ('14050', '140621', '140600', '山阴县', null);
INSERT INTO `t_area` VALUES ('14051', '130321', '130300', '青龙满族自治县', null);
INSERT INTO `t_area` VALUES ('14052', '632621', '632600', '玛沁县', null);
INSERT INTO `t_area` VALUES ('14053', '140622', '140600', '应县', null);
INSERT INTO `t_area` VALUES ('14054', '130322', '130300', '昌黎县', null);
INSERT INTO `t_area` VALUES ('14055', '632626', '632600', '玛多县', null);
INSERT INTO `t_area` VALUES ('14056', '632625', '632600', '久治县', null);
INSERT INTO `t_area` VALUES ('14057', '371622', '371600', '阳信县', null);
INSERT INTO `t_area` VALUES ('14058', '371621', '371600', '惠民县', null);
INSERT INTO `t_area` VALUES ('14059', '371624', '371600', '沾化县', null);
INSERT INTO `t_area` VALUES ('14060', '371623', '371600', '无棣县', null);
INSERT INTO `t_area` VALUES ('14061', '371626', '371600', '邹平县', null);
INSERT INTO `t_area` VALUES ('14062', '131051', '131000', '开发区', null);
INSERT INTO `t_area` VALUES ('14063', '371625', '371600', '博兴县', null);
INSERT INTO `t_area` VALUES ('14064', '131052', '131000', '燕郊经济技术开发区', null);
INSERT INTO `t_area` VALUES ('14065', '150000', '0', '内蒙古自治区', null);
INSERT INTO `t_area` VALUES ('14066', '620602', '620600', '凉州区', null);
INSERT INTO `t_area` VALUES ('14067', '321000', '320000', '扬州市', null);
INSERT INTO `t_area` VALUES ('14068', '533325', '533300', '兰坪白族普米族自治县', null);
INSERT INTO `t_area` VALUES ('14069', '533324', '533300', '贡山独龙族怒族自治县', null);
INSERT INTO `t_area` VALUES ('14070', '620600', '620000', '武威市', null);
INSERT INTO `t_area` VALUES ('14071', '610402', '610400', '秦都区', null);
INSERT INTO `t_area` VALUES ('14072', '610403', '610400', '杨陵区', null);
INSERT INTO `t_area` VALUES ('14073', '610404', '610400', '渭城区', null);
INSERT INTO `t_area` VALUES ('14074', '120121', '120100', '宁河县', null);
INSERT INTO `t_area` VALUES ('14075', '210727', '210700', '义县', null);
INSERT INTO `t_area` VALUES ('14076', '210726', '210700', '黑山县', null);
INSERT INTO `t_area` VALUES ('14077', '120123', '120100', '静海县', null);
INSERT INTO `t_area` VALUES ('14078', '120125', '120100', '蓟县', null);
INSERT INTO `t_area` VALUES ('14079', '231281', '231200', '安达市', null);
INSERT INTO `t_area` VALUES ('14080', '130302', '130300', '海港区', null);
INSERT INTO `t_area` VALUES ('14081', '231283', '231200', '海伦市', null);
INSERT INTO `t_area` VALUES ('14082', '130300', '130000', '秦皇岛市', null);
INSERT INTO `t_area` VALUES ('14083', '231282', '231200', '肇东市', null);
INSERT INTO `t_area` VALUES ('14084', '130304', '130300', '北戴河区', null);
INSERT INTO `t_area` VALUES ('14085', '130303', '130300', '山海关区', null);
INSERT INTO `t_area` VALUES ('14086', '420626', '420600', '保康县', null);
INSERT INTO `t_area` VALUES ('14087', '120112', '120100', '津南区', null);
INSERT INTO `t_area` VALUES ('14088', '610430', '610400', '淳化县', null);
INSERT INTO `t_area` VALUES ('14089', '120111', '120100', '西青区', null);
INSERT INTO `t_area` VALUES ('14090', '610431', '610400', '武功县', null);
INSERT INTO `t_area` VALUES ('14091', '120110', '120100', '东丽区', null);
INSERT INTO `t_area` VALUES ('14092', '120116', '120100', '滨海新区', null);
INSERT INTO `t_area` VALUES ('14093', '420625', '420600', '谷城县', null);
INSERT INTO `t_area` VALUES ('14094', '120115', '120100', '宝坻区', null);
INSERT INTO `t_area` VALUES ('14095', '371600', '370000', '滨州市', null);
INSERT INTO `t_area` VALUES ('14096', '420624', '420600', '南漳县', null);
INSERT INTO `t_area` VALUES ('14097', '120114', '120100', '武清区', null);
INSERT INTO `t_area` VALUES ('14098', '120113', '120100', '北辰区', null);
INSERT INTO `t_area` VALUES ('14099', '371602', '371600', '滨城区', null);
INSERT INTO `t_area` VALUES ('14100', '610428', '610400', '长武县', null);
INSERT INTO `t_area` VALUES ('14101', '610427', '610400', '彬县', null);
INSERT INTO `t_area` VALUES ('14102', '610429', '610400', '旬邑县', null);
INSERT INTO `t_area` VALUES ('14103', '610424', '610400', '乾县', null);
INSERT INTO `t_area` VALUES ('14104', '610423', '610400', '泾阳县', null);
INSERT INTO `t_area` VALUES ('14105', '610426', '610400', '永寿县', null);
INSERT INTO `t_area` VALUES ('14106', '610425', '610400', '礼泉县', null);
INSERT INTO `t_area` VALUES ('14107', '620621', '620600', '民勤县', null);
INSERT INTO `t_area` VALUES ('14108', '620622', '620600', '古浪县', null);
INSERT INTO `t_area` VALUES ('14109', '620623', '620600', '天祝藏族自治县', null);
INSERT INTO `t_area` VALUES ('14110', '360203', '360200', '珠山区', null);
INSERT INTO `t_area` VALUES ('14111', '210700', '210000', '锦州市', null);
INSERT INTO `t_area` VALUES ('14112', '360200', '360000', '景德镇市', null);
INSERT INTO `t_area` VALUES ('14113', '360202', '360200', '昌江区', null);
INSERT INTO `t_area` VALUES ('14114', '210703', '210700', '凌河区', null);
INSERT INTO `t_area` VALUES ('14115', '210702', '210700', '古塔区', null);
INSERT INTO `t_area` VALUES ('14116', '210711', '210700', '太和区', null);
INSERT INTO `t_area` VALUES ('14117', '420607', '420600', '襄州区', null);
INSERT INTO `t_area` VALUES ('14118', '420606', '420600', '樊城区', null);
INSERT INTO `t_area` VALUES ('14119', '420602', '420600', '襄城区', null);
INSERT INTO `t_area` VALUES ('14120', '220000', '0', '吉林省', null);
INSERT INTO `t_area` VALUES ('14121', '420600', '420000', '襄阳市', null);
INSERT INTO `t_area` VALUES ('14122', '460105', '460100', '秀英区', null);
INSERT INTO `t_area` VALUES ('14123', '460106', '460100', '龙华区', null);
INSERT INTO `t_area` VALUES ('14124', '460107', '460100', '琼山区', null);
INSERT INTO `t_area` VALUES ('14125', '460108', '460100', '美兰区', null);
INSERT INTO `t_area` VALUES ('14126', '532801', '532800', '景洪市', null);
INSERT INTO `t_area` VALUES ('14127', '532800', '530000', '西双版纳傣族自治州', null);
INSERT INTO `t_area` VALUES ('14128', '441284', '441200', '四会市', null);
INSERT INTO `t_area` VALUES ('14129', '441283', '441200', '高要市', null);
INSERT INTO `t_area` VALUES ('14130', '460100', '460000', '海口市', null);
INSERT INTO `t_area` VALUES ('14131', '430000', '0', '湖南省', null);
INSERT INTO `t_area` VALUES ('14132', '320381', '320300', '新沂市', null);
INSERT INTO `t_area` VALUES ('14133', '320382', '320300', '邳州市', null);
INSERT INTO `t_area` VALUES ('14134', '431127', '431100', '蓝山县', null);
INSERT INTO `t_area` VALUES ('14135', '431128', '431100', '新田县', null);
INSERT INTO `t_area` VALUES ('14136', '431129', '431100', '江华瑶族自治县', null);
INSERT INTO `t_area` VALUES ('14137', '431123', '431100', '双牌县', null);
INSERT INTO `t_area` VALUES ('14138', '431124', '431100', '道县', null);
INSERT INTO `t_area` VALUES ('14139', '431125', '431100', '江永县', null);
INSERT INTO `t_area` VALUES ('14140', '431126', '431100', '宁远县', null);
INSERT INTO `t_area` VALUES ('14141', '511800', '510000', '雅安市', null);
INSERT INTO `t_area` VALUES ('14142', '511802', '511800', '雨城区', null);
INSERT INTO `t_area` VALUES ('14143', '431122', '431100', '东安县', null);
INSERT INTO `t_area` VALUES ('14144', '341222', '341200', '太和县', null);
INSERT INTO `t_area` VALUES ('14145', '431121', '431100', '祁阳县', null);
INSERT INTO `t_area` VALUES ('14146', '341221', '341200', '临泉县', null);
INSERT INTO `t_area` VALUES ('14147', '340621', '340600', '濉溪县', null);
INSERT INTO `t_area` VALUES ('14148', '341226', '341200', '颍上县', null);
INSERT INTO `t_area` VALUES ('14149', '341225', '341200', '阜南县', null);
INSERT INTO `t_area` VALUES ('14150', '350724', '350700', '松溪县', null);
INSERT INTO `t_area` VALUES ('14151', '350723', '350700', '光泽县', null);
INSERT INTO `t_area` VALUES ('14152', '350722', '350700', '浦城县', null);
INSERT INTO `t_area` VALUES ('14153', '350721', '350700', '顺昌县', null);
INSERT INTO `t_area` VALUES ('14154', '360403', '360400', '浔阳区', null);
INSERT INTO `t_area` VALUES ('14155', '360402', '360400', '庐山区', null);
INSERT INTO `t_area` VALUES ('14156', '350725', '350700', '政和县', null);
INSERT INTO `t_area` VALUES ('14157', '360400', '360000', '九江市', null);
INSERT INTO `t_area` VALUES ('14158', '330483', '330400', '桐乡市', null);
INSERT INTO `t_area` VALUES ('14159', '330482', '330400', '平湖市', null);
INSERT INTO `t_area` VALUES ('14160', '330481', '330400', '海宁市', null);
INSERT INTO `t_area` VALUES ('14161', '140430', '140400', '沁县', null);
INSERT INTO `t_area` VALUES ('14162', '140431', '140400', '沁源县', null);
INSERT INTO `t_area` VALUES ('14163', '431102', '431100', '零陵区', null);
INSERT INTO `t_area` VALUES ('14164', '431103', '431100', '冷水滩区', null);
INSERT INTO `t_area` VALUES ('14165', '420703', '420700', '华容区', null);
INSERT INTO `t_area` VALUES ('14166', '420704', '420700', '鄂城区', null);
INSERT INTO `t_area` VALUES ('14167', '420700', '420000', '鄂州市', null);
INSERT INTO `t_area` VALUES ('14168', '420702', '420700', '梁子湖区', null);
INSERT INTO `t_area` VALUES ('14169', '511824', '511800', '石棉县', null);
INSERT INTO `t_area` VALUES ('14170', '511823', '511800', '汉源县', null);
INSERT INTO `t_area` VALUES ('14171', '511826', '511800', '芦山县', null);
INSERT INTO `t_area` VALUES ('14172', '511825', '511800', '天全县', null);
INSERT INTO `t_area` VALUES ('14173', '511822', '511800', '荥经县', null);
INSERT INTO `t_area` VALUES ('14174', '511821', '511800', '名山县', null);
INSERT INTO `t_area` VALUES ('14175', '431100', '430000', '永州市', null);
INSERT INTO `t_area` VALUES ('14176', '511827', '511800', '宝兴县', null);
INSERT INTO `t_area` VALUES ('14177', '350702', '350700', '延平区', null);
INSERT INTO `t_area` VALUES ('14178', '350700', '350000', '南平市', null);
INSERT INTO `t_area` VALUES ('14179', '450881', '450800', '桂平市', null);
INSERT INTO `t_area` VALUES ('14180', '230708', '230700', '美溪区', null);
INSERT INTO `t_area` VALUES ('14181', '230709', '230700', '金山屯区', null);
INSERT INTO `t_area` VALUES ('14182', '210681', '210600', '东港市', null);
INSERT INTO `t_area` VALUES ('14183', '150200', '150000', '包头市', null);
INSERT INTO `t_area` VALUES ('14184', '210682', '210600', '凤城市', null);
INSERT INTO `t_area` VALUES ('14185', '150205', '150200', '石拐区', null);
INSERT INTO `t_area` VALUES ('14186', '150204', '150200', '青山区', null);
INSERT INTO `t_area` VALUES ('14187', '150203', '150200', '昆都仑区', null);
INSERT INTO `t_area` VALUES ('14188', '150202', '150200', '东河区', null);
INSERT INTO `t_area` VALUES ('14189', '150207', '150200', '九原区', null);
INSERT INTO `t_area` VALUES ('14190', '150206', '150200', '白云矿区', null);
INSERT INTO `t_area` VALUES ('14191', '231102', '231100', '爱辉区', null);
INSERT INTO `t_area` VALUES ('14192', '231100', '230000', '黑河市', null);
INSERT INTO `t_area` VALUES ('14193', '430903', '430900', '赫山区', null);
INSERT INTO `t_area` VALUES ('14194', '510181', '510100', '都江堰市', null);
INSERT INTO `t_area` VALUES ('14195', '430902', '430900', '资阳区', null);
INSERT INTO `t_area` VALUES ('14196', '510182', '510100', '彭州市', null);
INSERT INTO `t_area` VALUES ('14197', '430900', '430000', '益阳市', null);
INSERT INTO `t_area` VALUES ('14198', '230703', '230700', '南岔区', null);
INSERT INTO `t_area` VALUES ('14199', '230702', '230700', '伊春区', null);
INSERT INTO `t_area` VALUES ('14200', '510183', '510100', '邛崃市', null);
INSERT INTO `t_area` VALUES ('14201', '230700', '230000', '伊春市', null);
INSERT INTO `t_area` VALUES ('14202', '510184', '510100', '崇州市', null);
INSERT INTO `t_area` VALUES ('14203', '230707', '230700', '新青区', null);
INSERT INTO `t_area` VALUES ('14204', '341282', '341200', '界首市', null);
INSERT INTO `t_area` VALUES ('14205', '230706', '230700', '翠峦区', null);
INSERT INTO `t_area` VALUES ('14206', '230705', '230700', '西林区', null);
INSERT INTO `t_area` VALUES ('14207', '230704', '230700', '友好区', null);
INSERT INTO `t_area` VALUES ('14208', '230710', '230700', '五营区', null);
INSERT INTO `t_area` VALUES ('14209', '230712', '230700', '汤旺河区', null);
INSERT INTO `t_area` VALUES ('14210', '230711', '230700', '乌马河区', null);
INSERT INTO `t_area` VALUES ('14211', '230714', '230700', '乌伊岭区', null);
INSERT INTO `t_area` VALUES ('14212', '230713', '230700', '带岭区', null);
INSERT INTO `t_area` VALUES ('14213', '230716', '230700', '上甘岭区', null);
INSERT INTO `t_area` VALUES ('14214', '230715', '230700', '红星区', null);
INSERT INTO `t_area` VALUES ('14215', '533300', '530000', '怒江傈僳族自治州', null);
INSERT INTO `t_area` VALUES ('14216', '150223', '150200', '达尔罕茂明安联合旗', null);
INSERT INTO `t_area` VALUES ('14217', '150222', '150200', '固阳县', null);
INSERT INTO `t_area` VALUES ('14218', '150221', '150200', '土默特右旗', null);
INSERT INTO `t_area` VALUES ('14219', '230722', '230700', '嘉荫县', null);
INSERT INTO `t_area` VALUES ('14220', '340600', '340000', '淮北市', null);
INSERT INTO `t_area` VALUES ('14221', '140482', '140400', '城区', null);
INSERT INTO `t_area` VALUES ('14222', '140481', '140400', '潞城市', null);
INSERT INTO `t_area` VALUES ('14223', '140484', '140400', '高新区', null);
INSERT INTO `t_area` VALUES ('14224', '140483', '140400', '郊区', null);
INSERT INTO `t_area` VALUES ('14225', '340604', '340600', '烈山区', null);
INSERT INTO `t_area` VALUES ('14226', '340603', '340600', '相山区', null);
INSERT INTO `t_area` VALUES ('14227', '340602', '340600', '杜集区', null);
INSERT INTO `t_area` VALUES ('14228', '370602', '370600', '芝罘区', null);
INSERT INTO `t_area` VALUES ('14229', '540000', '0', '西藏自治区', null);
INSERT INTO `t_area` VALUES ('14230', '370600', '370000', '烟台市', null);
INSERT INTO `t_area` VALUES ('14231', '340191', '340100', '中区', null);
INSERT INTO `t_area` VALUES ('14232', '542221', '542200', '乃东县', null);
INSERT INTO `t_area` VALUES ('14233', '542222', '542200', '扎囊县', null);
INSERT INTO `t_area` VALUES ('14234', '370611', '370600', '福山区', null);
INSERT INTO `t_area` VALUES ('14235', '370612', '370600', '牟平区', null);
INSERT INTO `t_area` VALUES ('14236', '370613', '370600', '莱山区', null);
INSERT INTO `t_area` VALUES ('14237', '410200', '410000', '开封市', null);
INSERT INTO `t_area` VALUES ('14238', '320300', '320000', '徐州市', null);
INSERT INTO `t_area` VALUES ('14239', '320303', '320300', '云龙区', null);
INSERT INTO `t_area` VALUES ('14240', '320302', '320300', '鼓楼区', null);
INSERT INTO `t_area` VALUES ('14241', '450821', '450800', '平南县', null);
INSERT INTO `t_area` VALUES ('14242', '320305', '320300', '贾汪区', null);
INSERT INTO `t_area` VALUES ('14243', '320304', '320300', '九里区', null);
INSERT INTO `t_area` VALUES ('14244', '542231', '542200', '隆子县', null);
INSERT INTO `t_area` VALUES ('14245', '542232', '542200', '错那县', null);
INSERT INTO `t_area` VALUES ('14246', '542233', '542200', '浪卡子县', null);
INSERT INTO `t_area` VALUES ('14247', '542228', '542200', '洛扎县', null);
INSERT INTO `t_area` VALUES ('14248', '542227', '542200', '措美县', null);
INSERT INTO `t_area` VALUES ('14249', '542229', '542200', '加查县', null);
INSERT INTO `t_area` VALUES ('14250', '542224', '542200', '桑日县', null);
INSERT INTO `t_area` VALUES ('14251', '411729', '411700', '新蔡县', null);
INSERT INTO `t_area` VALUES ('14252', '542223', '542200', '贡嘎县', null);
INSERT INTO `t_area` VALUES ('14253', '411728', '411700', '遂平县', null);
INSERT INTO `t_area` VALUES ('14254', '542226', '542200', '曲松县', null);
INSERT INTO `t_area` VALUES ('14255', '411727', '411700', '汝南县', null);
INSERT INTO `t_area` VALUES ('14256', '542225', '542200', '琼结县', null);
INSERT INTO `t_area` VALUES ('14257', '411726', '411700', '泌阳县', null);
INSERT INTO `t_area` VALUES ('14258', '411725', '411700', '确山县', null);
INSERT INTO `t_area` VALUES ('14259', '411724', '411700', '正阳县', null);
INSERT INTO `t_area` VALUES ('14260', '411723', '411700', '平舆县', null);
INSERT INTO `t_area` VALUES ('14261', '411722', '411700', '上蔡县', null);
INSERT INTO `t_area` VALUES ('14262', '411721', '411700', '西平县', null);
INSERT INTO `t_area` VALUES ('14263', '510122', '510100', '双流县', null);
INSERT INTO `t_area` VALUES ('14264', '510121', '510100', '金堂县', null);
INSERT INTO `t_area` VALUES ('14265', '510124', '510100', '郫县', null);
INSERT INTO `t_area` VALUES ('14266', '320311', '320300', '泉山区', null);
INSERT INTO `t_area` VALUES ('14267', '430921', '430900', '南县', null);
INSERT INTO `t_area` VALUES ('14268', '430922', '430900', '桃江县', null);
INSERT INTO `t_area` VALUES ('14269', '430923', '430900', '安化县', null);
INSERT INTO `t_area` VALUES ('14270', '370634', '370600', '长岛县', null);
INSERT INTO `t_area` VALUES ('14271', '510114', '510100', '新都区', null);
INSERT INTO `t_area` VALUES ('14272', '510115', '510100', '温江区', null);
INSERT INTO `t_area` VALUES ('14273', '450800', '450000', '贵港市', null);
INSERT INTO `t_area` VALUES ('14274', '320324', '320300', '睢宁县', null);
INSERT INTO `t_area` VALUES ('14275', '450802', '450800', '港北区', null);
INSERT INTO `t_area` VALUES ('14276', '510132', '510100', '新津县', null);
INSERT INTO `t_area` VALUES ('14277', '320321', '320300', '丰县', null);
INSERT INTO `t_area` VALUES ('14278', '450804', '450800', '覃塘区', null);
INSERT INTO `t_area` VALUES ('14279', '510131', '510100', '蒲江县', null);
INSERT INTO `t_area` VALUES ('14280', '450803', '450800', '港南区', null);
INSERT INTO `t_area` VALUES ('14281', '320323', '320300', '铜山县', null);
INSERT INTO `t_area` VALUES ('14282', '320322', '320300', '沛县', null);
INSERT INTO `t_area` VALUES ('14283', '411700', '410000', '驻马店市', null);
INSERT INTO `t_area` VALUES ('14284', '510129', '510100', '大邑县', null);
INSERT INTO `t_area` VALUES ('14285', '530700', '530000', '丽江市', null);
INSERT INTO `t_area` VALUES ('14286', '411702', '411700', '驿城区', null);
INSERT INTO `t_area` VALUES ('14287', '530702', '530700', '古城区', null);
INSERT INTO `t_area` VALUES ('14288', '230781', '230700', '铁力市', null);
INSERT INTO `t_area` VALUES ('14289', '421003', '421000', '荆州区', null);
INSERT INTO `t_area` VALUES ('14290', '430981', '430900', '沅江市', null);
INSERT INTO `t_area` VALUES ('14291', '510100', '510000', '成都市', null);
INSERT INTO `t_area` VALUES ('14292', '340151', '340100', '高新区', null);
INSERT INTO `t_area` VALUES ('14293', '410225', '410200', '兰考县', null);
INSERT INTO `t_area` VALUES ('14294', '410224', '410200', '开封县', null);
INSERT INTO `t_area` VALUES ('14295', '410223', '410200', '尉氏县', null);
INSERT INTO `t_area` VALUES ('14296', '451481', '451400', '凭祥市', null);
INSERT INTO `t_area` VALUES ('14297', '440487', '440400', '南湾区', null);
INSERT INTO `t_area` VALUES ('14298', '440486', '440400', '金唐区', null);
INSERT INTO `t_area` VALUES ('14299', '510112', '510100', '龙泉驿区', null);
INSERT INTO `t_area` VALUES ('14300', '510113', '510100', '青白江区', null);
INSERT INTO `t_area` VALUES ('14301', '510106', '510100', '金牛区', null);
INSERT INTO `t_area` VALUES ('14302', '510105', '510100', '青羊区', null);
INSERT INTO `t_area` VALUES ('14303', '510104', '510100', '锦江区', null);
INSERT INTO `t_area` VALUES ('14304', '510108', '510100', '成华区', null);
INSERT INTO `t_area` VALUES ('14305', '510107', '510100', '武侯区', null);
INSERT INTO `t_area` VALUES ('14306', '421002', '421000', '沙市区', null);
INSERT INTO `t_area` VALUES ('14307', '421000', '420000', '荆州市', null);
INSERT INTO `t_area` VALUES ('14308', '542200', '540000', '山南地区', null);
INSERT INTO `t_area` VALUES ('14309', '410211', '410200', '金明区', null);
INSERT INTO `t_area` VALUES ('14310', '410204', '410200', '鼓楼区', null);
INSERT INTO `t_area` VALUES ('14311', '410203', '410200', '顺河回族区', null);
INSERT INTO `t_area` VALUES ('14312', '410202', '410200', '龙亭区', null);
INSERT INTO `t_area` VALUES ('14313', '410205', '410200', '禹王台区', null);
INSERT INTO `t_area` VALUES ('14314', '370686', '370600', '海阳市', null);
INSERT INTO `t_area` VALUES ('14315', '370686', '370600', '栖霞市', null);
INSERT INTO `t_area` VALUES ('14316', '370685', '370600', '招远市', null);
INSERT INTO `t_area` VALUES ('14317', '370684', '370600', '蓬莱市', null);
INSERT INTO `t_area` VALUES ('14318', '370683', '370600', '莱州市', null);
INSERT INTO `t_area` VALUES ('14319', '370682', '370600', '莱阳市', null);
INSERT INTO `t_area` VALUES ('14320', '370681', '370600', '龙口市', null);
INSERT INTO `t_area` VALUES ('14321', '410221', '410200', '杞县', null);
INSERT INTO `t_area` VALUES ('14322', '410222', '410200', '通许县', null);
INSERT INTO `t_area` VALUES ('14323', '370589', '370500', '西城区', null);
INSERT INTO `t_area` VALUES ('14324', '340104', '340100', '蜀山区', null);
INSERT INTO `t_area` VALUES ('14325', '340102', '340100', '瑶海区', null);
INSERT INTO `t_area` VALUES ('14326', '141102', '141100', '离石区', null);
INSERT INTO `t_area` VALUES ('14327', '340103', '340100', '庐阳区', null);
INSERT INTO `t_area` VALUES ('14328', '340100', '340000', '合肥市', null);
INSERT INTO `t_area` VALUES ('14329', '141100', '140000', '吕梁市', null);
INSERT INTO `t_area` VALUES ('14330', '211381', '211300', '北票市', null);
INSERT INTO `t_area` VALUES ('14331', '211382', '211300', '凌源市', null);
INSERT INTO `t_area` VALUES ('14332', '130283', '130200', '迁安市', null);
INSERT INTO `t_area` VALUES ('14333', '130281', '130200', '遵化市', null);
INSERT INTO `t_area` VALUES ('14334', '441900', '440000', '东莞市', null);
INSERT INTO `t_area` VALUES ('14335', '340111', '340100', '包河区', null);
INSERT INTO `t_area` VALUES ('14336', '622901', '622900', '临夏市', null);
INSERT INTO `t_area` VALUES ('14337', '622900', '620000', '临夏回族自治州', null);
INSERT INTO `t_area` VALUES ('14338', '451424', '451400', '大新县', null);
INSERT INTO `t_area` VALUES ('14339', '451425', '451400', '天等县', null);
INSERT INTO `t_area` VALUES ('14340', '451422', '451400', '宁明县', null);
INSERT INTO `t_area` VALUES ('14341', '451423', '451400', '龙州县', null);
INSERT INTO `t_area` VALUES ('14342', '451421', '451400', '扶绥县', null);
INSERT INTO `t_area` VALUES ('14343', '340122', '340100', '肥东县', null);
INSERT INTO `t_area` VALUES ('14344', '340123', '340100', '肥西县', null);
INSERT INTO `t_area` VALUES ('14345', '522732', '522700', '三都水族自治县', null);
INSERT INTO `t_area` VALUES ('14346', '340121', '340100', '长丰县', null);
INSERT INTO `t_area` VALUES ('14347', '522731', '522700', '惠水县', null);
INSERT INTO `t_area` VALUES ('14348', '370590', '370500', '东城区', null);
INSERT INTO `t_area` VALUES ('14349', '622921', '622900', '临夏县', null);
INSERT INTO `t_area` VALUES ('14350', '622922', '622900', '康乐县', null);
INSERT INTO `t_area` VALUES ('14351', '522725', '522700', '瓮安县', null);
INSERT INTO `t_area` VALUES ('14352', '522726', '522700', '独山县', null);
INSERT INTO `t_area` VALUES ('14353', '522727', '522700', '平塘县', null);
INSERT INTO `t_area` VALUES ('14354', '622925', '622900', '和政县', null);
INSERT INTO `t_area` VALUES ('14355', '530724', '530700', '宁蒗彝族自治县', null);
INSERT INTO `t_area` VALUES ('14356', '622926', '622900', '东乡族自治县', null);
INSERT INTO `t_area` VALUES ('14357', '530723', '530700', '华坪县', null);
INSERT INTO `t_area` VALUES ('14358', '622923', '622900', '永靖县', null);
INSERT INTO `t_area` VALUES ('14359', '522722', '522700', '荔波县', null);
INSERT INTO `t_area` VALUES ('14360', '530722', '530700', '永胜县', null);
INSERT INTO `t_area` VALUES ('14361', '622924', '622900', '广河县', null);
INSERT INTO `t_area` VALUES ('14362', '522723', '522700', '贵定县', null);
INSERT INTO `t_area` VALUES ('14363', '530721', '530700', '玉龙纳西族自治县', null);
INSERT INTO `t_area` VALUES ('14364', '622927', '622900', '积石山保安族东乡族撒拉族自治县', null);
INSERT INTO `t_area` VALUES ('14365', '522728', '522700', '罗甸县', null);
INSERT INTO `t_area` VALUES ('14366', '522729', '522700', '长顺县', null);
INSERT INTO `t_area` VALUES ('14367', '440404', '440400', '金湾区', null);
INSERT INTO `t_area` VALUES ('14368', '451402', '451400', '江洲区', null);
INSERT INTO `t_area` VALUES ('14369', '451400', '450000', '崇左市', null);
INSERT INTO `t_area` VALUES ('14370', '450900', '450000', '玉林市', null);
INSERT INTO `t_area` VALUES ('14371', '440400', '440000', '珠海市', null);
INSERT INTO `t_area` VALUES ('14372', '440403', '440400', '斗门区', null);
INSERT INTO `t_area` VALUES ('14373', '522730', '522700', '龙里县', null);
INSERT INTO `t_area` VALUES ('14374', '440402', '440400', '香洲区', null);
INSERT INTO `t_area` VALUES ('14375', '450902', '450900', '玉州区', null);
INSERT INTO `t_area` VALUES ('14376', '341700', '340000', '池州市', null);
INSERT INTO `t_area` VALUES ('14377', '331181', '331100', '龙泉市', null);
INSERT INTO `t_area` VALUES ('14378', '341702', '341700', '贵池区', null);
INSERT INTO `t_area` VALUES ('14379', '130230', '130200', '唐海县', null);
INSERT INTO `t_area` VALUES ('14380', '522700', '520000', '黔南布依族苗族自治州', null);
INSERT INTO `t_area` VALUES ('14381', '522701', '522700', '都匀市', null);
INSERT INTO `t_area` VALUES ('14382', '522702', '522700', '福泉市', null);
INSERT INTO `t_area` VALUES ('14383', '410181', '410100', '巩义市', null);
INSERT INTO `t_area` VALUES ('14384', '410186', '410100', '郑东新区', null);
INSERT INTO `t_area` VALUES ('14385', '410187', '410100', '高新区', null);
INSERT INTO `t_area` VALUES ('14386', '410184', '410100', '新郑市', null);
INSERT INTO `t_area` VALUES ('14387', '410185', '410100', '登封市', null);
INSERT INTO `t_area` VALUES ('14388', '410182', '410100', '荥阳市', null);
INSERT INTO `t_area` VALUES ('14389', '410183', '410100', '新密市', null);
INSERT INTO `t_area` VALUES ('14390', '341721', '341700', '东至县', null);
INSERT INTO `t_area` VALUES ('14391', '341722', '341700', '石台县', null);
INSERT INTO `t_area` VALUES ('14392', '341723', '341700', '青阳县', null);
INSERT INTO `t_area` VALUES ('14393', '220187', '220000', '净月旅游开发区', null);
INSERT INTO `t_area` VALUES ('14394', '210624', '210600', '宽甸满族自治县', null);
INSERT INTO `t_area` VALUES ('14395', '211303', '211300', '龙城区', null);
INSERT INTO `t_area` VALUES ('14396', '211300', '210000', '朝阳市', null);
INSERT INTO `t_area` VALUES ('14397', '211302', '211300', '双塔区', null);
INSERT INTO `t_area` VALUES ('14398', '331123', '331100', '遂昌县', null);
INSERT INTO `t_area` VALUES ('14399', '331122', '331100', '缙云县', null);
INSERT INTO `t_area` VALUES ('14400', '331125', '331100', '云和县', null);
INSERT INTO `t_area` VALUES ('14401', '331124', '331100', '松阳县', null);
INSERT INTO `t_area` VALUES ('14402', '350681', '350600', '龙海市', null);
INSERT INTO `t_area` VALUES ('14403', '331127', '331100', '景宁畲族自治县', null);
INSERT INTO `t_area` VALUES ('14404', '331126', '331100', '庆元县', null);
INSERT INTO `t_area` VALUES ('14405', '610300', '610000', '宝鸡市', null);
INSERT INTO `t_area` VALUES ('14406', '140502', '140500', '城区', null);
INSERT INTO `t_area` VALUES ('14407', '130202', '130200', '路南区', null);
INSERT INTO `t_area` VALUES ('14408', '130203', '130200', '路北区', null);
INSERT INTO `t_area` VALUES ('14409', '140500', '140000', '晋城市', null);
INSERT INTO `t_area` VALUES ('14410', '130200', '130000', '唐山市', null);
INSERT INTO `t_area` VALUES ('14411', '220181', '220000', '九台市', null);
INSERT INTO `t_area` VALUES ('14412', '220182', '220000', '榆树市', null);
INSERT INTO `t_area` VALUES ('14413', '220183', '220000', '德惠市', null);
INSERT INTO `t_area` VALUES ('14414', '220184', '220000', '高新技术产业开发区', null);
INSERT INTO `t_area` VALUES ('14415', '220185', '220000', '汽车产业开发区', null);
INSERT INTO `t_area` VALUES ('14416', '431081', '431000', '资兴市', null);
INSERT INTO `t_area` VALUES ('14417', '220186', '220000', '经济技术开发区', null);
INSERT INTO `t_area` VALUES ('14418', '130208', '130200', '丰润区', null);
INSERT INTO `t_area` VALUES ('14419', '130207', '130200', '丰南区', null);
INSERT INTO `t_area` VALUES ('14420', '130204', '130200', '古冶区', null);
INSERT INTO `t_area` VALUES ('14421', '130205', '130200', '开平区', null);
INSERT INTO `t_area` VALUES ('14422', '511781', '511700', '万源市', null);
INSERT INTO `t_area` VALUES ('14423', '150125', '150100', '武川县', null);
INSERT INTO `t_area` VALUES ('14424', '150121', '150100', '土默特左旗', null);
INSERT INTO `t_area` VALUES ('14425', '620724', '620700', '高台县', null);
INSERT INTO `t_area` VALUES ('14426', '150122', '150100', '托克托县', null);
INSERT INTO `t_area` VALUES ('14427', '620723', '620700', '临泽县', null);
INSERT INTO `t_area` VALUES ('14428', '150123', '150100', '和林格尔县', null);
INSERT INTO `t_area` VALUES ('14429', '150124', '150100', '清水河县', null);
INSERT INTO `t_area` VALUES ('14430', '620725', '620700', '山丹县', null);
INSERT INTO `t_area` VALUES ('14431', '620722', '620700', '民乐县', null);
INSERT INTO `t_area` VALUES ('14432', '620721', '620700', '肃南裕固族自治县', null);
INSERT INTO `t_area` VALUES ('14433', '230811', '230800', '郊区', null);
INSERT INTO `t_area` VALUES ('14434', '141181', '141100', '孝义市', null);
INSERT INTO `t_area` VALUES ('14435', '141182', '141100', '汾阳市', null);
INSERT INTO `t_area` VALUES ('14436', '330402', '330400', '南湖区', null);
INSERT INTO `t_area` VALUES ('14437', '330400', '330000', '嘉兴市', null);
INSERT INTO `t_area` VALUES ('14438', '331100', '330000', '丽水市', null);
INSERT INTO `t_area` VALUES ('14439', '331102', '331100', '莲都区', null);
INSERT INTO `t_area` VALUES ('14440', '632726', '632700', '曲麻莱县', null);
INSERT INTO `t_area` VALUES ('14441', '632725', '632700', '囊谦县', null);
INSERT INTO `t_area` VALUES ('14442', '632724', '632700', '治多县', null);
INSERT INTO `t_area` VALUES ('14443', '632723', '632700', '称多县', null);
INSERT INTO `t_area` VALUES ('14444', '130224', '130200', '滦南县', null);
INSERT INTO `t_area` VALUES ('14445', '632722', '632700', '杂多县', null);
INSERT INTO `t_area` VALUES ('14446', '130225', '130200', '乐亭县', null);
INSERT INTO `t_area` VALUES ('14447', '632721', '632700', '玉树县', null);
INSERT INTO `t_area` VALUES ('14448', '130223', '130200', '滦县', null);
INSERT INTO `t_area` VALUES ('14449', '532930', '532900', '洱源县', null);
INSERT INTO `t_area` VALUES ('14450', '130229', '130200', '玉田县', null);
INSERT INTO `t_area` VALUES ('14451', '532931', '532900', '剑川县', null);
INSERT INTO `t_area` VALUES ('14452', '130227', '130200', '迁西县', null);
INSERT INTO `t_area` VALUES ('14453', '532932', '532900', '鹤庆县', null);
INSERT INTO `t_area` VALUES ('14454', '230800', '230000', '佳木斯市', null);
INSERT INTO `t_area` VALUES ('14455', '230802', '230800', '永红区', null);
INSERT INTO `t_area` VALUES ('14456', '230803', '230800', '向阳区', null);
INSERT INTO `t_area` VALUES ('14457', '230804', '230800', '前进区', null);
INSERT INTO `t_area` VALUES ('14458', '230805', '230800', '东风区', null);
INSERT INTO `t_area` VALUES ('14459', '231121', '231100', '嫩江县', null);
INSERT INTO `t_area` VALUES ('14460', '231124', '231100', '孙吴县', null);
INSERT INTO `t_area` VALUES ('14461', '231123', '231100', '逊克县', null);
INSERT INTO `t_area` VALUES ('14462', '331121', '331100', '青田县', null);
INSERT INTO `t_area` VALUES ('14463', '620700', '620000', '张掖市', null);
INSERT INTO `t_area` VALUES ('14464', '620702', '620700', '甘州区', null);
INSERT INTO `t_area` VALUES ('14465', '460000', '0', '海南省', null);
INSERT INTO `t_area` VALUES ('14466', '330421', '330400', '嘉善县', null);
INSERT INTO `t_area` VALUES ('14467', '360321', '360300', '莲花县', null);
INSERT INTO `t_area` VALUES ('14468', '330424', '330400', '海盐县', null);
INSERT INTO `t_area` VALUES ('14469', '360323', '360300', '芦溪县', null);
INSERT INTO `t_area` VALUES ('14470', '360322', '360300', '上栗县', null);
INSERT INTO `t_area` VALUES ('14471', '632700', '630000', '玉树藏族自治州', null);
INSERT INTO `t_area` VALUES ('14472', '231182', '231100', '五大连池市', null);
INSERT INTO `t_area` VALUES ('14473', '231181', '231100', '北安市', null);
INSERT INTO `t_area` VALUES ('14474', '371700', '370000', '荷泽市', null);
INSERT INTO `t_area` VALUES ('14475', '220122', '220000', '农安县', null);
INSERT INTO `t_area` VALUES ('14476', '610330', '610300', '凤县', null);
INSERT INTO `t_area` VALUES ('14477', '371702', '371700', '牡丹区', null);
INSERT INTO `t_area` VALUES ('14478', '610331', '610300', '太白县', null);
INSERT INTO `t_area` VALUES ('14479', '330411', '330400', '秀洲区', null);
INSERT INTO `t_area` VALUES ('14480', '360313', '360300', '湘东区', null);
INSERT INTO `t_area` VALUES ('14481', '532922', '532900', '漾濞彝族自治县', null);
INSERT INTO `t_area` VALUES ('14482', '532924', '532900', '宾川县', null);
INSERT INTO `t_area` VALUES ('14483', '532923', '532900', '祥云县', null);
INSERT INTO `t_area` VALUES ('14484', '532929', '532900', '云龙县', null);
INSERT INTO `t_area` VALUES ('14485', '610324', '610300', '扶风县', null);
INSERT INTO `t_area` VALUES ('14486', '610327', '610300', '陇县', null);
INSERT INTO `t_area` VALUES ('14487', '610326', '610300', '眉县', null);
INSERT INTO `t_area` VALUES ('14488', '532926', '532900', '南涧彝族自治县', null);
INSERT INTO `t_area` VALUES ('14489', '610329', '610300', '麟游县', null);
INSERT INTO `t_area` VALUES ('14490', '532925', '532900', '弥渡县', null);
INSERT INTO `t_area` VALUES ('14491', '610328', '610300', '千阳县', null);
INSERT INTO `t_area` VALUES ('14492', '532928', '532900', '永平县', null);
INSERT INTO `t_area` VALUES ('14493', '532927', '532900', '巍山彝族回族自治县', null);
INSERT INTO `t_area` VALUES ('14494', '120000', '0', '天津', null);
INSERT INTO `t_area` VALUES ('14495', '610322', '610300', '凤翔县', null);
INSERT INTO `t_area` VALUES ('14496', '610323', '610300', '岐山县', null);
INSERT INTO `t_area` VALUES ('14497', '210604', '210600', '振安区', null);
INSERT INTO `t_area` VALUES ('14498', '360300', '360000', '萍乡市', null);
INSERT INTO `t_area` VALUES ('14499', '210603', '210600', '振兴区', null);
INSERT INTO `t_area` VALUES ('14500', '220112', '220000', '双阳区', null);
INSERT INTO `t_area` VALUES ('14501', '350622', '350600', '云霄县', null);
INSERT INTO `t_area` VALUES ('14502', '211321', '211300', '朝阳县', null);
INSERT INTO `t_area` VALUES ('14503', '350623', '350600', '漳浦县', null);
INSERT INTO `t_area` VALUES ('14504', '211322', '211300', '建平县', null);
INSERT INTO `t_area` VALUES ('14505', '350624', '350600', '诏安县', null);
INSERT INTO `t_area` VALUES ('14506', '350625', '350600', '长泰县', null);
INSERT INTO `t_area` VALUES ('14507', '211324', '211300', '喀喇沁左翼蒙古族自治县', null);
INSERT INTO `t_area` VALUES ('14508', '350626', '350600', '东山县', null);
INSERT INTO `t_area` VALUES ('14509', '210602', '210600', '元宝区', null);
INSERT INTO `t_area` VALUES ('14510', '360302', '360300', '安源区', null);
INSERT INTO `t_area` VALUES ('14511', '350627', '350600', '南靖县', null);
INSERT INTO `t_area` VALUES ('14512', '350628', '350600', '平和县', null);
INSERT INTO `t_area` VALUES ('14513', '210600', '210000', '丹东市', null);
INSERT INTO `t_area` VALUES ('14514', '350629', '350600', '华安县', null);
INSERT INTO `t_area` VALUES ('14515', '140521', '140500', '沁水县', null);
INSERT INTO `t_area` VALUES ('14516', '341200', '340000', '阜阳市', null);
INSERT INTO `t_area` VALUES ('14517', '141130', '141100', '交口县', null);
INSERT INTO `t_area` VALUES ('14518', '341202', '341200', '颍州区', null);
INSERT INTO `t_area` VALUES ('14519', '341203', '341200', '颍东区', null);
INSERT INTO `t_area` VALUES ('14520', '341204', '341200', '颍泉区', null);
INSERT INTO `t_area` VALUES ('14521', '140525', '140500', '泽州县', null);
INSERT INTO `t_area` VALUES ('14522', '140524', '140500', '陵川县', null);
INSERT INTO `t_area` VALUES ('14523', '140522', '140500', '阳城县', null);
INSERT INTO `t_area` VALUES ('14524', '220105', '220000', '二道区', null);
INSERT INTO `t_area` VALUES ('14525', '371724', '371700', '巨野县', null);
INSERT INTO `t_area` VALUES ('14526', '220106', '220000', '绿园区', null);
INSERT INTO `t_area` VALUES ('14527', '371725', '371700', '郓城县', null);
INSERT INTO `t_area` VALUES ('14528', '220103', '220000', '宽城区', null);
INSERT INTO `t_area` VALUES ('14529', '371726', '371700', '鄄城县', null);
INSERT INTO `t_area` VALUES ('14530', '220104', '220000', '朝阳区', null);
INSERT INTO `t_area` VALUES ('14531', '371727', '371700', '定陶县', null);
INSERT INTO `t_area` VALUES ('14532', '220102', '220000', '南关区', null);
INSERT INTO `t_area` VALUES ('14533', '371721', '371700', '曹县', null);
INSERT INTO `t_area` VALUES ('14534', '371722', '371700', '单县', null);
INSERT INTO `t_area` VALUES ('14535', '500000', '0', '重庆', null);
INSERT INTO `t_area` VALUES ('14536', '220100', '220000', '长春市', null);
INSERT INTO `t_area` VALUES ('14537', '371723', '371700', '成武县', null);
INSERT INTO `t_area` VALUES ('14538', '141121', '141100', '文水县', null);
INSERT INTO `t_area` VALUES ('14539', '141122', '141100', '交城县', null);
INSERT INTO `t_area` VALUES ('14540', '141123', '141100', '兴县', null);
INSERT INTO `t_area` VALUES ('14541', '532900', '530000', '大理白族自治州', null);
INSERT INTO `t_area` VALUES ('14542', '141124', '141100', '临县', null);
INSERT INTO `t_area` VALUES ('14543', '141125', '141100', '柳林县', null);
INSERT INTO `t_area` VALUES ('14544', '141126', '141100', '石楼县', null);
INSERT INTO `t_area` VALUES ('14545', '532901', '532900', '大理市', null);
INSERT INTO `t_area` VALUES ('14546', '141127', '141100', '岚县', null);
INSERT INTO `t_area` VALUES ('14547', '141128', '141100', '方山县', null);
INSERT INTO `t_area` VALUES ('14548', '141129', '141100', '中阳县', null);
INSERT INTO `t_area` VALUES ('14549', '610303', '610300', '金台区', null);
INSERT INTO `t_area` VALUES ('14550', '371728', '371700', '东明县', null);
INSERT INTO `t_area` VALUES ('14551', '610302', '610300', '渭滨区', null);
INSERT INTO `t_area` VALUES ('14552', '610304', '610300', '陈仓区', null);
INSERT INTO `t_area` VALUES ('14553', '371322', '371300', '郯城县', null);
INSERT INTO `t_area` VALUES ('14554', '371323', '371300', '沂水县', null);
INSERT INTO `t_area` VALUES ('14555', '371321', '371300', '沂南县', null);
INSERT INTO `t_area` VALUES ('14556', '371326', '371300', '平邑县', null);
INSERT INTO `t_area` VALUES ('14557', '371327', '371300', '莒南县', null);
INSERT INTO `t_area` VALUES ('14558', '371324', '371300', '苍山县', null);
INSERT INTO `t_area` VALUES ('14559', '371325', '371300', '费县', null);
INSERT INTO `t_area` VALUES ('14560', '632300', '630000', '黄南藏族自治州', null);
INSERT INTO `t_area` VALUES ('14561', '371328', '371300', '蒙阴县', null);
INSERT INTO `t_area` VALUES ('14562', '371329', '371300', '临沭县', null);
INSERT INTO `t_area` VALUES ('14563', '370724', '370700', '临朐县', null);
INSERT INTO `t_area` VALUES ('14564', '370725', '370700', '昌乐县', null);
INSERT INTO `t_area` VALUES ('14565', '411628', '411600', '鹿邑县', null);
INSERT INTO `t_area` VALUES ('14566', '411621', '411600', '扶沟县', null);
INSERT INTO `t_area` VALUES ('14567', '411622', '411600', '西华县', null);
INSERT INTO `t_area` VALUES ('14568', '411623', '411600', '商水县', null);
INSERT INTO `t_area` VALUES ('14569', '411624', '411600', '沈丘县', null);
INSERT INTO `t_area` VALUES ('14570', '411625', '411600', '郸城县', null);
INSERT INTO `t_area` VALUES ('14571', '411626', '411600', '淮阳县', null);
INSERT INTO `t_area` VALUES ('14572', '411627', '411600', '太康县', null);
INSERT INTO `t_area` VALUES ('14573', '371300', '370000', '临沂市', null);
INSERT INTO `t_area` VALUES ('14574', '230822', '230800', '桦南县', null);
INSERT INTO `t_area` VALUES ('14575', '230828', '230800', '汤原县', null);
INSERT INTO `t_area` VALUES ('14576', '230826', '230800', '桦川县', null);
INSERT INTO `t_area` VALUES ('14577', '632323', '632300', '泽库县', null);
INSERT INTO `t_area` VALUES ('14578', '632322', '632300', '尖扎县', null);
INSERT INTO `t_area` VALUES ('14579', '632321', '632300', '同仁县', null);
INSERT INTO `t_area` VALUES ('14580', '371302', '371300', '兰山区', null);
INSERT INTO `t_area` VALUES ('14581', '410506', '410500', '龙安区', null);
INSERT INTO `t_area` VALUES ('14582', '410503', '410500', '北关区', null);
INSERT INTO `t_area` VALUES ('14583', '410502', '410500', '文峰区', null);
INSERT INTO `t_area` VALUES ('14584', '410505', '410500', '殷都区', null);
INSERT INTO `t_area` VALUES ('14585', '410500', '410000', '安阳市', null);
INSERT INTO `t_area` VALUES ('14586', '230833', '230800', '抚远县', null);
INSERT INTO `t_area` VALUES ('14587', '371311', '371300', '罗庄区', null);
INSERT INTO `t_area` VALUES ('14588', '371312', '371300', '河东区', null);
INSERT INTO `t_area` VALUES ('14589', '370700', '370000', '潍坊市', null);
INSERT INTO `t_area` VALUES ('14590', '370702', '370700', '潍城区', null);
INSERT INTO `t_area` VALUES ('14591', '370704', '370700', '坊子区', null);
INSERT INTO `t_area` VALUES ('14592', '370703', '370700', '寒亭区', null);
INSERT INTO `t_area` VALUES ('14593', '370705', '370700', '奎文区', null);
INSERT INTO `t_area` VALUES ('14594', '370786', '370700', '昌邑市', null);
INSERT INTO `t_area` VALUES ('14595', '361181', '361100', '德兴市', null);
INSERT INTO `t_area` VALUES ('14596', '361102', '361100', '信州区', null);
INSERT INTO `t_area` VALUES ('14597', '450000', '0', '广西壮族自治区', null);
INSERT INTO `t_area` VALUES ('14598', '230881', '230800', '同江市', null);
INSERT INTO `t_area` VALUES ('14599', '230882', '230800', '富锦市', null);
INSERT INTO `t_area` VALUES ('14600', '511011', '511000', '东兴区', null);
INSERT INTO `t_area` VALUES ('14601', '361100', '360000', '上饶市', null);
INSERT INTO `t_area` VALUES ('14602', '150300', '150000', '乌海市', null);
INSERT INTO `t_area` VALUES ('14603', '445121', '445100', '潮安县', null);
INSERT INTO `t_area` VALUES ('14604', '140311', '140300', '郊区', null);
INSERT INTO `t_area` VALUES ('14605', '445122', '445100', '饶平县', null);
INSERT INTO `t_area` VALUES ('14606', '620302', '620300', '金川区', null);
INSERT INTO `t_area` VALUES ('14607', '150303', '150300', '海南区', null);
INSERT INTO `t_area` VALUES ('14608', '150304', '150300', '乌达区', null);
INSERT INTO `t_area` VALUES ('14609', '150302', '150300', '海勃湾区', null);
INSERT INTO `t_area` VALUES ('14610', '511024', '511000', '威远县', null);
INSERT INTO `t_area` VALUES ('14611', '511025', '511000', '资中县', null);
INSERT INTO `t_area` VALUES ('14612', '360502', '360500', '渝水区', null);
INSERT INTO `t_area` VALUES ('14613', '360500', '360000', '新余市', null);
INSERT INTO `t_area` VALUES ('14614', '620321', '620300', '永昌县', null);
INSERT INTO `t_area` VALUES ('14615', '140321', '140300', '平定县', null);
INSERT INTO `t_area` VALUES ('14616', '140322', '140300', '盂县', null);
INSERT INTO `t_area` VALUES ('14617', '632324', '632300', '河南蒙古族自治县', null);
INSERT INTO `t_area` VALUES ('14618', '220203', '220200', '龙潭区', null);
INSERT INTO `t_area` VALUES ('14619', '220202', '220200', '昌邑区', null);
INSERT INTO `t_area` VALUES ('14620', '361122', '361100', '广丰县', null);
INSERT INTO `t_area` VALUES ('14621', '361121', '361100', '上饶县', null);
INSERT INTO `t_area` VALUES ('14622', '220204', '220200', '船营区', null);
INSERT INTO `t_area` VALUES ('14623', '361124', '361100', '铅山县', null);
INSERT INTO `t_area` VALUES ('14624', '361123', '361100', '玉山县', null);
INSERT INTO `t_area` VALUES ('14625', '361126', '361100', '弋阳县', null);
INSERT INTO `t_area` VALUES ('14626', '361125', '361100', '横峰县', null);
INSERT INTO `t_area` VALUES ('14627', '220200', '220000', '吉林市', null);
INSERT INTO `t_area` VALUES ('14628', '361128', '361100', '鄱阳县', null);
INSERT INTO `t_area` VALUES ('14629', '361127', '361100', '余干县', null);
INSERT INTO `t_area` VALUES ('14630', '361129', '361100', '万年县', null);
INSERT INTO `t_area` VALUES ('14631', '411681', '411600', '项城市', null);
INSERT INTO `t_area` VALUES ('14632', '520100', '520000', '贵阳市', null);
INSERT INTO `t_area` VALUES ('14633', '445100', '440000', '潮州市', null);
INSERT INTO `t_area` VALUES ('14634', '445102', '445100', '湘桥区', null);
INSERT INTO `t_area` VALUES ('14635', '511028', '511000', '隆昌县', null);
INSERT INTO `t_area` VALUES ('14636', '360521', '360500', '分宜县', null);
INSERT INTO `t_area` VALUES ('14637', '361130', '361100', '婺源县', null);
INSERT INTO `t_area` VALUES ('14638', '220211', '220200', '丰满区', null);
INSERT INTO `t_area` VALUES ('14639', '350603', '350600', '龙文区', null);
INSERT INTO `t_area` VALUES ('14640', '350602', '350600', '芗城区', null);
INSERT INTO `t_area` VALUES ('14641', '350600', '350000', '漳州市', null);
INSERT INTO `t_area` VALUES ('14642', '520111', '520100', '花溪区', null);
INSERT INTO `t_area` VALUES ('14643', '520112', '520100', '乌当区', null);
INSERT INTO `t_area` VALUES ('14644', '520103', '520100', '云岩区', null);
INSERT INTO `t_area` VALUES ('14645', '520102', '520100', '南明区', null);
INSERT INTO `t_area` VALUES ('14646', '511921', '511900', '通江县', null);
INSERT INTO `t_area` VALUES ('14647', '511923', '511900', '平昌县', null);
INSERT INTO `t_area` VALUES ('14648', '511922', '511900', '南江县', null);
INSERT INTO `t_area` VALUES ('14649', '320482', '320400', '金坛市', null);
INSERT INTO `t_area` VALUES ('14650', '320481', '320400', '溧阳市', null);
INSERT INTO `t_area` VALUES ('14651', '522301', '522300', '兴义市', null);
INSERT INTO `t_area` VALUES ('14652', '522300', '520000', '黔西南布依族苗族自治州', null);
INSERT INTO `t_area` VALUES ('14653', '430600', '430000', '岳阳市', null);
INSERT INTO `t_area` VALUES ('14654', '430602', '430600', '岳阳楼区', null);
INSERT INTO `t_area` VALUES ('14655', '430603', '430600', '云溪区', null);
INSERT INTO `t_area` VALUES ('14656', '421102', '421100', '黄州区', null);
INSERT INTO `t_area` VALUES ('14657', '152502', '152500', '锡林浩特市', null);
INSERT INTO `t_area` VALUES ('14658', '511902', '511900', '巴州区', null);
INSERT INTO `t_area` VALUES ('14659', '152501', '152500', '二连浩特市', null);
INSERT INTO `t_area` VALUES ('14660', '421100', '420000', '黄冈市', null);
INSERT INTO `t_area` VALUES ('14661', '511900', '510000', '巴中市', null);
INSERT INTO `t_area` VALUES ('14662', '152500', '150000', '锡林郭勒盟', null);
INSERT INTO `t_area` VALUES ('14663', '450722', '450700', '浦北县', null);
INSERT INTO `t_area` VALUES ('14664', '410581', '410500', '林州市', null);
INSERT INTO `t_area` VALUES ('14665', '430611', '430600', '君山区', null);
INSERT INTO `t_area` VALUES ('14666', '451100', '450000', '贺州市', null);
INSERT INTO `t_area` VALUES ('14667', '451102', '451100', '八步区', null);
INSERT INTO `t_area` VALUES ('14668', '450721', '450700', '灵山县', null);
INSERT INTO `t_area` VALUES ('14669', '430621', '430600', '岳阳县', null);
INSERT INTO `t_area` VALUES ('14670', '430624', '430600', '湘阴县', null);
INSERT INTO `t_area` VALUES ('14671', '430623', '430600', '华容县', null);
INSERT INTO `t_area` VALUES ('14672', '522323', '522300', '普安县', null);
INSERT INTO `t_area` VALUES ('14673', '522322', '522300', '兴仁县', null);
INSERT INTO `t_area` VALUES ('14674', '421124', '421100', '英山县', null);
INSERT INTO `t_area` VALUES ('14675', '421125', '421100', '浠水县', null);
INSERT INTO `t_area` VALUES ('14676', '421126', '421100', '蕲春县', null);
INSERT INTO `t_area` VALUES ('14677', '421127', '421100', '黄梅县', null);
INSERT INTO `t_area` VALUES ('14678', '430626', '430600', '平江县', null);
INSERT INTO `t_area` VALUES ('14679', '152528', '152500', '镶黄旗', null);
INSERT INTO `t_area` VALUES ('14680', '152527', '152500', '太仆寺旗', null);
INSERT INTO `t_area` VALUES ('14681', '152529', '152500', '正镶白旗', null);
INSERT INTO `t_area` VALUES ('14682', '152524', '152500', '苏尼特右旗', null);
INSERT INTO `t_area` VALUES ('14683', '421123', '421100', '罗田县', null);
INSERT INTO `t_area` VALUES ('14684', '152523', '152500', '苏尼特左旗', null);
INSERT INTO `t_area` VALUES ('14685', '421122', '421100', '红安县', null);
INSERT INTO `t_area` VALUES ('14686', '152526', '152500', '西乌珠穆沁旗', null);
INSERT INTO `t_area` VALUES ('14687', '421121', '421100', '团风县', null);
INSERT INTO `t_area` VALUES ('14688', '152525', '152500', '东乌珠穆沁旗', null);
INSERT INTO `t_area` VALUES ('14689', '522326', '522300', '望谟县', null);
INSERT INTO `t_area` VALUES ('14690', '522327', '522300', '册亨县', null);
INSERT INTO `t_area` VALUES ('14691', '152522', '152500', '阿巴嘎旗', null);
INSERT INTO `t_area` VALUES ('14692', '522324', '522300', '晴隆县', null);
INSERT INTO `t_area` VALUES ('14693', '522325', '522300', '贞丰县', null);
INSERT INTO `t_area` VALUES ('14694', '522328', '522300', '安龙县', null);
INSERT INTO `t_area` VALUES ('14695', '370784', '370700', '安丘市', null);
INSERT INTO `t_area` VALUES ('14696', '370783', '370700', '寿光市', null);
INSERT INTO `t_area` VALUES ('14697', '370782', '370700', '诸城市', null);
INSERT INTO `t_area` VALUES ('14698', '370781', '370700', '青州市', null);
INSERT INTO `t_area` VALUES ('14699', '511000', '510000', '内江市', null);
INSERT INTO `t_area` VALUES ('14700', '370785', '370700', '高密市', null);
INSERT INTO `t_area` VALUES ('14701', '451122', '451100', '钟山县', null);
INSERT INTO `t_area` VALUES ('14702', '451121', '451100', '昭平县', null);
INSERT INTO `t_area` VALUES ('14703', '511002', '511000', '市中区', null);
INSERT INTO `t_area` VALUES ('14704', '451123', '451100', '富川瑶族自治县', null);
INSERT INTO `t_area` VALUES ('14705', '410522', '410500', '安阳县', null);
INSERT INTO `t_area` VALUES ('14706', '410523', '410500', '汤阴县', null);
INSERT INTO `t_area` VALUES ('14707', '410526', '410500', '滑县', null);
INSERT INTO `t_area` VALUES ('14708', '410527', '410500', '内黄县', null);
INSERT INTO `t_area` VALUES ('14709', '542122', '542100', '江达县', null);
INSERT INTO `t_area` VALUES ('14710', '542123', '542100', '贡觉县', null);
INSERT INTO `t_area` VALUES ('14711', '542121', '542100', '昌都县', null);
INSERT INTO `t_area` VALUES ('14712', '510000', '0', '四川省', null);
INSERT INTO `t_area` VALUES ('14713', '370751', '370700', '开发区', null);
INSERT INTO `t_area` VALUES ('14714', '542125', '542100', '丁青县', null);
INSERT INTO `t_area` VALUES ('14715', '542124', '542100', '类乌齐县', null);
INSERT INTO `t_area` VALUES ('14716', '542127', '542100', '八宿县', null);
INSERT INTO `t_area` VALUES ('14717', '542126', '542100', '察雅县', null);
INSERT INTO `t_area` VALUES ('14718', '542129', '542100', '芒康县', null);
INSERT INTO `t_area` VALUES ('14719', '542128', '542100', '左贡县', null);
INSERT INTO `t_area` VALUES ('14720', '542132', '542100', '洛隆县', null);
INSERT INTO `t_area` VALUES ('14721', '542133', '542100', '边坝县', null);
INSERT INTO `t_area` VALUES ('14722', '445185', '445100', '枫溪区', null);
INSERT INTO `t_area` VALUES ('14723', '542100', '540000', '昌都地区', null);
INSERT INTO `t_area` VALUES ('14724', '320411', '320400', '新北区', null);
INSERT INTO `t_area` VALUES ('14725', '320412', '320400', '武进区', null);
INSERT INTO `t_area` VALUES ('14726', '411602', '411600', '川汇区', null);
INSERT INTO `t_area` VALUES ('14727', '411600', '410000', '周口市', null);
INSERT INTO `t_area` VALUES ('14728', '450700', '450000', '钦州市', null);
INSERT INTO `t_area` VALUES ('14729', '450703', '450700', '钦北区', null);
INSERT INTO `t_area` VALUES ('14730', '450702', '450700', '钦南区', null);
INSERT INTO `t_area` VALUES ('14731', '330727', '330700', '磐安县', null);
INSERT INTO `t_area` VALUES ('14732', '320400', '320000', '常州市', null);
INSERT INTO `t_area` VALUES ('14733', '440781', '440700', '台山市', null);
INSERT INTO `t_area` VALUES ('14734', '440784', '440700', '鹤山市', null);
INSERT INTO `t_area` VALUES ('14735', '440783', '440700', '开平市', null);
INSERT INTO `t_area` VALUES ('14736', '330723', '330700', '武义县', null);
INSERT INTO `t_area` VALUES ('14737', '320404', '320400', '钟楼区', null);
INSERT INTO `t_area` VALUES ('14738', '440785', '440700', '恩平市', null);
INSERT INTO `t_area` VALUES ('14739', '330726', '330700', '浦江县', null);
INSERT INTO `t_area` VALUES ('14740', '320402', '320400', '天宁区', null);
INSERT INTO `t_area` VALUES ('14741', '350583', '350500', '南安市', null);
INSERT INTO `t_area` VALUES ('14742', '350581', '350500', '石狮市', null);
INSERT INTO `t_area` VALUES ('14743', '320405', '320400', '戚墅堰区', null);
INSERT INTO `t_area` VALUES ('14744', '350582', '350500', '晋江市', null);
INSERT INTO `t_area` VALUES ('14745', '331023', '331000', '天台县', null);
INSERT INTO `t_area` VALUES ('14746', '331024', '331000', '仙居县', null);
INSERT INTO `t_area` VALUES ('14747', '130133', '130100', '赵县', null);
INSERT INTO `t_area` VALUES ('14748', '130132', '130100', '元氏县', null);
INSERT INTO `t_area` VALUES ('14749', '130131', '130100', '平山县', null);
INSERT INTO `t_area` VALUES ('14750', '130130', '130100', '无极县', null);
INSERT INTO `t_area` VALUES ('14751', '532628', '532600', '富宁县', null);
INSERT INTO `t_area` VALUES ('14752', '341602', '341600', '谯城区', null);
INSERT INTO `t_area` VALUES ('14753', '130983', '130900', '黄骅市', null);
INSERT INTO `t_area` VALUES ('14754', '130984', '130900', '河间市', null);
INSERT INTO `t_area` VALUES ('14755', '130981', '130900', '泊头市', null);
INSERT INTO `t_area` VALUES ('14756', '130982', '130900', '任丘市', null);
INSERT INTO `t_area` VALUES ('14757', '211281', '211200', '调兵山市', null);
INSERT INTO `t_area` VALUES ('14758', '532623', '532600', '西畴县', null);
INSERT INTO `t_area` VALUES ('14759', '532622', '532600', '砚山县', null);
INSERT INTO `t_area` VALUES ('14760', '341600', '340000', '亳州市', null);
INSERT INTO `t_area` VALUES ('14761', '532621', '532600', '文山县', null);
INSERT INTO `t_area` VALUES ('14762', '211282', '211200', '开原市', null);
INSERT INTO `t_area` VALUES ('14763', '532627', '532600', '广南县', null);
INSERT INTO `t_area` VALUES ('14764', '532626', '532600', '丘北县', null);
INSERT INTO `t_area` VALUES ('14765', '532625', '532600', '马关县', null);
INSERT INTO `t_area` VALUES ('14766', '532624', '532600', '麻栗坡县', null);
INSERT INTO `t_area` VALUES ('14767', '330702', '330700', '婺城区', null);
INSERT INTO `t_area` VALUES ('14768', '330703', '330700', '金东区', null);
INSERT INTO `t_area` VALUES ('14769', '330700', '330000', '金华市', null);
INSERT INTO `t_area` VALUES ('14770', '130124', '130100', '栾城县', null);
INSERT INTO `t_area` VALUES ('14771', '130123', '130100', '正定县', null);
INSERT INTO `t_area` VALUES ('14772', '421087', '421000', '松滋市', null);
INSERT INTO `t_area` VALUES ('14773', '130126', '130100', '灵寿县', null);
INSERT INTO `t_area` VALUES ('14774', '130125', '130100', '行唐县', null);
INSERT INTO `t_area` VALUES ('14775', '421083', '421000', '洪湖市', null);
INSERT INTO `t_area` VALUES ('14776', '130121', '130100', '井陉县', null);
INSERT INTO `t_area` VALUES ('14777', '421081', '421000', '石首市', null);
INSERT INTO `t_area` VALUES ('14778', '130128', '130100', '深泽县', null);
INSERT INTO `t_area` VALUES ('14779', '130127', '130100', '高邑县', null);
INSERT INTO `t_area` VALUES ('14780', '130129', '130100', '赞皇县', null);
INSERT INTO `t_area` VALUES ('14781', '210922', '210900', '彰武县', null);
INSERT INTO `t_area` VALUES ('14782', '210111', '210100', '苏家屯区', null);
INSERT INTO `t_area` VALUES ('14783', '210112', '210100', '东陵区', null);
INSERT INTO `t_area` VALUES ('14784', '210113', '210100', '新城子区', null);
INSERT INTO `t_area` VALUES ('14785', '210114', '210100', '于洪区', null);
INSERT INTO `t_area` VALUES ('14786', '210921', '210900', '阜新蒙古族自治县', null);
INSERT INTO `t_area` VALUES ('14787', '530426', '530400', '峨山彝族自治县', null);
INSERT INTO `t_area` VALUES ('14788', '130930', '130900', '孟村回族自治县', null);
INSERT INTO `t_area` VALUES ('14789', '530427', '530400', '新平彝族傣族自治县', null);
INSERT INTO `t_area` VALUES ('14790', '530424', '530400', '华宁县', null);
INSERT INTO `t_area` VALUES ('14791', '530425', '530400', '易门县', null);
INSERT INTO `t_area` VALUES ('14792', '530422', '530400', '澄江县', null);
INSERT INTO `t_area` VALUES ('14793', '530423', '530400', '通海县', null);
INSERT INTO `t_area` VALUES ('14794', '530421', '530400', '江川县', null);
INSERT INTO `t_area` VALUES ('14795', '130185', '130100', '鹿泉市', null);
INSERT INTO `t_area` VALUES ('14796', '421022', '421000', '公安县', null);
INSERT INTO `t_area` VALUES ('14797', '421023', '421000', '监利县', null);
INSERT INTO `t_area` VALUES ('14798', '530428', '530400', '元江哈尼族彝族傣族自治县', null);
INSERT INTO `t_area` VALUES ('14799', '421024', '421000', '江陵县', null);
INSERT INTO `t_area` VALUES ('14800', '441802', '441800', '清城区', null);
INSERT INTO `t_area` VALUES ('14801', '130184', '130100', '新乐市', null);
INSERT INTO `t_area` VALUES ('14802', '130183', '130100', '晋州市', null);
INSERT INTO `t_area` VALUES ('14803', '130182', '130100', '藁城市', null);
INSERT INTO `t_area` VALUES ('14804', '130121', '130100', '辛集市', null);
INSERT INTO `t_area` VALUES ('14805', '210102', '210100', '和平区', null);
INSERT INTO `t_area` VALUES ('14806', '210103', '210100', '沈河区', null);
INSERT INTO `t_area` VALUES ('14807', '210100', '210000', '沈阳市', null);
INSERT INTO `t_area` VALUES ('14808', '210106', '210100', '铁西区', null);
INSERT INTO `t_area` VALUES ('14809', '429021', '420000', '神农架林区', null);
INSERT INTO `t_area` VALUES ('14810', '210104', '210100', '大东区', null);
INSERT INTO `t_area` VALUES ('14811', '441800', '440000', '清远市', null);
INSERT INTO `t_area` VALUES ('14812', '210105', '210100', '皇姑区', null);
INSERT INTO `t_area` VALUES ('14813', '331081', '331000', '温岭市', null);
INSERT INTO `t_area` VALUES ('14814', '331082', '331000', '临海市', null);
INSERT INTO `t_area` VALUES ('14815', '210904', '210900', '太平区', null);
INSERT INTO `t_area` VALUES ('14816', '210905', '210900', '清河门区', null);
INSERT INTO `t_area` VALUES ('14817', '210900', '210000', '阜新市', null);
INSERT INTO `t_area` VALUES ('14818', '210902', '210900', '海州区', null);
INSERT INTO `t_area` VALUES ('14819', '210903', '210900', '新邱区', null);
INSERT INTO `t_area` VALUES ('14820', '530400', '530000', '玉溪市', null);
INSERT INTO `t_area` VALUES ('14821', '522401', '522400', '毕节市', null);
INSERT INTO `t_area` VALUES ('14822', '430682', '430600', '临湘市', null);
INSERT INTO `t_area` VALUES ('14823', '530402', '530400', '红塔区', null);
INSERT INTO `t_area` VALUES ('14824', '430681', '430600', '汨罗市', null);
INSERT INTO `t_area` VALUES ('14825', '210911', '210900', '细河区', null);
INSERT INTO `t_area` VALUES ('14826', '522400', '520000', '毕节地区', null);
INSERT INTO `t_area` VALUES ('14827', '130929', '130900', '献县', null);
INSERT INTO `t_area` VALUES ('14828', '210124', '210100', '法库县', null);
INSERT INTO `t_area` VALUES ('14829', '130927', '130900', '南皮县', null);
INSERT INTO `t_area` VALUES ('14830', '210122', '210100', '辽中县', null);
INSERT INTO `t_area` VALUES ('14831', '130928', '130900', '吴桥县', null);
INSERT INTO `t_area` VALUES ('14832', '210123', '210100', '康平县', null);
INSERT INTO `t_area` VALUES ('14833', '130926', '130900', '肃宁县', null);
INSERT INTO `t_area` VALUES ('14834', '130925', '130900', '盐山县', null);
INSERT INTO `t_area` VALUES ('14835', '130924', '130900', '海兴县', null);
INSERT INTO `t_area` VALUES ('14836', '130923', '130900', '东光县', null);
INSERT INTO `t_area` VALUES ('14837', '130922', '130900', '青县', null);
INSERT INTO `t_area` VALUES ('14838', '130921', '130900', '沧县', null);
INSERT INTO `t_area` VALUES ('14839', '630102', '630100', '城东区', null);
INSERT INTO `t_area` VALUES ('14840', '630103', '630100', '城中区', null);
INSERT INTO `t_area` VALUES ('14841', '630104', '630100', '城西区', null);
INSERT INTO `t_area` VALUES ('14842', '630105', '630100', '城北区', null);
INSERT INTO `t_area` VALUES ('14843', '140400', '140000', '长治市', null);
INSERT INTO `t_area` VALUES ('14844', '341321', '341300', '砀山县', null);
INSERT INTO `t_area` VALUES ('14845', '341322', '341300', '萧县', null);
INSERT INTO `t_area` VALUES ('14846', '341323', '341300', '灵璧县', null);
INSERT INTO `t_area` VALUES ('14847', '341324', '341300', '泗县', null);
INSERT INTO `t_area` VALUES ('14848', '360428', '360400', '都昌县', null);
INSERT INTO `t_area` VALUES ('14849', '211200', '210000', '铁岭市', null);
INSERT INTO `t_area` VALUES ('14850', '360427', '360400', '星子县', null);
INSERT INTO `t_area` VALUES ('14851', '350502', '350500', '鲤城区', null);
INSERT INTO `t_area` VALUES ('14852', '350503', '350500', '丰泽区', null);
INSERT INTO `t_area` VALUES ('14853', '211202', '211200', '银州区', null);
INSERT INTO `t_area` VALUES ('14854', '360429', '360400', '湖口县', null);
INSERT INTO `t_area` VALUES ('14855', '350504', '350500', '洛江区', null);
INSERT INTO `t_area` VALUES ('14856', '360424', '360400', '修水县', null);
INSERT INTO `t_area` VALUES ('14857', '350505', '350500', '泉港区', null);
INSERT INTO `t_area` VALUES ('14858', '211204', '211200', '清河区', null);
INSERT INTO `t_area` VALUES ('14859', '440700', '440000', '江门市', null);
INSERT INTO `t_area` VALUES ('14860', '360423', '360400', '武宁县', null);
INSERT INTO `t_area` VALUES ('14861', '533124', '533100', '陇川县', null);
INSERT INTO `t_area` VALUES ('14862', '360426', '360400', '德安县', null);
INSERT INTO `t_area` VALUES ('14863', '533123', '533100', '盈江县', null);
INSERT INTO `t_area` VALUES ('14864', '360425', '360400', '永修县', null);
INSERT INTO `t_area` VALUES ('14865', '533122', '533100', '梁河县', null);
INSERT INTO `t_area` VALUES ('14866', '440704', '440700', '江海区', null);
INSERT INTO `t_area` VALUES ('14867', '440703', '440700', '蓬江区', null);
INSERT INTO `t_area` VALUES ('14868', '610202', '610200', '王益区', null);
INSERT INTO `t_area` VALUES ('14869', '360421', '360400', '九江县', null);
INSERT INTO `t_area` VALUES ('14870', '610200', '610000', '铜川市', null);
INSERT INTO `t_area` VALUES ('14871', '350500', '350000', '泉州市', null);
INSERT INTO `t_area` VALUES ('14872', '440705', '440700', '新会区', null);
INSERT INTO `t_area` VALUES ('14873', '513229', '513200', '马尔康县', null);
INSERT INTO `t_area` VALUES ('14874', '513227', '513200', '小金县', null);
INSERT INTO `t_area` VALUES ('14875', '513228', '513200', '黑水县', null);
INSERT INTO `t_area` VALUES ('14876', '445202', '445200', '榕城区', null);
INSERT INTO `t_area` VALUES ('14877', '445200', '440000', '揭阳市', null);
INSERT INTO `t_area` VALUES ('14878', '130900', '130000', '沧州市', null);
INSERT INTO `t_area` VALUES ('14879', '130902', '130900', '新华区', null);
INSERT INTO `t_area` VALUES ('14880', '130903', '130900', '运河区', null);
INSERT INTO `t_area` VALUES ('14881', '520000', '0', '贵州省', null);
INSERT INTO `t_area` VALUES ('14882', '630123', '630100', '湟源县', null);
INSERT INTO `t_area` VALUES ('14883', '513233', '513200', '红原县', null);
INSERT INTO `t_area` VALUES ('14884', '630122', '630100', '湟中县', null);
INSERT INTO `t_area` VALUES ('14885', '513232', '513200', '若尔盖县', null);
INSERT INTO `t_area` VALUES ('14886', '630121', '630100', '大通回族土族自治县', null);
INSERT INTO `t_area` VALUES ('14887', '513231', '513200', '阿坝县', null);
INSERT INTO `t_area` VALUES ('14888', '220221', '220200', '永吉县', null);
INSERT INTO `t_area` VALUES ('14889', '513230', '513200', '壤塘县', null);
INSERT INTO `t_area` VALUES ('14890', '140426', '140400', '黎城县', null);
INSERT INTO `t_area` VALUES ('14891', '140425', '140400', '平顺县', null);
INSERT INTO `t_area` VALUES ('14892', '140424', '140400', '屯留县', null);
INSERT INTO `t_area` VALUES ('14893', '140423', '140400', '襄垣县', null);
INSERT INTO `t_area` VALUES ('14894', '140429', '140400', '武乡县', null);
INSERT INTO `t_area` VALUES ('14895', '140428', '140400', '长子县', null);
INSERT INTO `t_area` VALUES ('14896', '140427', '140400', '壶关县', null);
INSERT INTO `t_area` VALUES ('14897', '341302', '341300', '埇桥区', null);
INSERT INTO `t_area` VALUES ('14898', '210184', '210100', '沈北新区', null);
INSERT INTO `t_area` VALUES ('14899', '210182', '210100', '浑南新区', null);
INSERT INTO `t_area` VALUES ('14900', '140421', '140400', '长治县', null);
INSERT INTO `t_area` VALUES ('14901', '210183', '210100', '张士开发区', null);
INSERT INTO `t_area` VALUES ('14902', '341300', '340000', '宿州市', null);
INSERT INTO `t_area` VALUES ('14903', '210181', '210100', '新民市', null);
INSERT INTO `t_area` VALUES ('14904', '350527', '350500', '金门县', null);
INSERT INTO `t_area` VALUES ('14905', '620421', '620400', '靖远县', null);
INSERT INTO `t_area` VALUES ('14906', '350524', '350500', '安溪县', null);
INSERT INTO `t_area` VALUES ('14907', '211223', '211200', '西丰县', null);
INSERT INTO `t_area` VALUES ('14908', '350525', '350500', '永春县', null);
INSERT INTO `t_area` VALUES ('14909', '211224', '211200', '昌图县', null);
INSERT INTO `t_area` VALUES ('14910', '350526', '350500', '德化县', null);
INSERT INTO `t_area` VALUES ('14911', '330784', '330700', '永康市', null);
INSERT INTO `t_area` VALUES ('14912', '330783', '330700', '东阳市', null);
INSERT INTO `t_area` VALUES ('14913', '350521', '350500', '惠安县', null);
INSERT INTO `t_area` VALUES ('14914', '330782', '330700', '义乌市', null);
INSERT INTO `t_area` VALUES ('14915', '330781', '330700', '兰溪市', null);
INSERT INTO `t_area` VALUES ('14916', '610222', '610200', '宜君县', null);
INSERT INTO `t_area` VALUES ('14917', '610204', '610200', '耀州区', null);
INSERT INTO `t_area` VALUES ('14918', '610203', '610200', '印台区', null);
INSERT INTO `t_area` VALUES ('14919', '211221', '211200', '铁岭县', null);
INSERT INTO `t_area` VALUES ('14920', '620400', '620000', '白银市', null);
INSERT INTO `t_area` VALUES ('14921', '620402', '620400', '白银区', null);
INSERT INTO `t_area` VALUES ('14922', '620403', '620400', '平川区', null);
INSERT INTO `t_area` VALUES ('14923', '441821', '441800', '佛冈县', null);
INSERT INTO `t_area` VALUES ('14924', '441827', '441800', '清新县', null);
INSERT INTO `t_area` VALUES ('14925', '429006', '420000', '天门市', null);
INSERT INTO `t_area` VALUES ('14926', '441825', '441800', '连山壮族瑶族自治县', null);
INSERT INTO `t_area` VALUES ('14927', '429005', '420000', '潜江市', null);
INSERT INTO `t_area` VALUES ('14928', '441826', '441800', '连南瑶族自治县', null);
INSERT INTO `t_area` VALUES ('14929', '429004', '420000', '仙桃市', null);
INSERT INTO `t_area` VALUES ('14930', '441823', '441800', '阳山县', null);
INSERT INTO `t_area` VALUES ('14931', '360430', '360400', '彭泽县', null);
INSERT INTO `t_area` VALUES ('14932', '130107', '130100', '井陉矿区', null);
INSERT INTO `t_area` VALUES ('14933', '130108', '130100', '裕华区', null);
INSERT INTO `t_area` VALUES ('14934', '130105', '130100', '新华区', null);
INSERT INTO `t_area` VALUES ('14935', '220282', '220200', '桦甸市', null);
INSERT INTO `t_area` VALUES ('14936', '130100', '130000', '石家庄市', null);
INSERT INTO `t_area` VALUES ('14937', '220283', '220200', '舒兰市', null);
INSERT INTO `t_area` VALUES ('14938', '220284', '220200', '磐石市', null);
INSERT INTO `t_area` VALUES ('14939', '130103', '130100', '桥东区', null);
INSERT INTO `t_area` VALUES ('14940', '130104', '130100', '桥西区', null);
INSERT INTO `t_area` VALUES ('14941', '130102', '130100', '长安区', null);
INSERT INTO `t_area` VALUES ('14942', '220281', '220200', '蛟河市', null);
INSERT INTO `t_area` VALUES ('14943', '513200', '510000', '阿坝藏族羌族自治州', null);
INSERT INTO `t_area` VALUES ('14944', '331000', '330000', '台州市', null);
INSERT INTO `t_area` VALUES ('14945', '371402', '371400', '德城区', null);
INSERT INTO `t_area` VALUES ('14946', '230921', '230900', '勃利县', null);
INSERT INTO `t_area` VALUES ('14947', '371400', '370000', '德州市', null);
INSERT INTO `t_area` VALUES ('14948', '441882', '441800', '连州市', null);
INSERT INTO `t_area` VALUES ('14949', '441881', '441800', '英德市', null);
INSERT INTO `t_area` VALUES ('14950', '360481', '360400', '瑞昌市', null);
INSERT INTO `t_area` VALUES ('14951', '331002', '331000', '椒江区', null);
INSERT INTO `t_area` VALUES ('14952', '331004', '331000', '路桥区', null);
INSERT INTO `t_area` VALUES ('14953', '513221', '513200', '汶川县', null);
INSERT INTO `t_area` VALUES ('14954', '331003', '331000', '黄岩区', null);
INSERT INTO `t_area` VALUES ('14955', '513222', '513200', '理县', null);
INSERT INTO `t_area` VALUES ('14956', '513223', '513200', '茂县', null);
INSERT INTO `t_area` VALUES ('14957', '513224', '513200', '松潘县', null);
INSERT INTO `t_area` VALUES ('14958', '513225', '513200', '九寨沟县', null);
INSERT INTO `t_area` VALUES ('14959', '230000', '0', '黑龙江省', null);
INSERT INTO `t_area` VALUES ('14960', '513226', '513200', '金川县', null);
INSERT INTO `t_area` VALUES ('14961', '533102', '533100', '瑞丽市', null);
INSERT INTO `t_area` VALUES ('14962', '533103', '533100', '潞西市', null);
INSERT INTO `t_area` VALUES ('14963', '533100', '530000', '德宏傣族景颇族自治州', null);
INSERT INTO `t_area` VALUES ('14964', '341623', '341600', '利辛县', null);
INSERT INTO `t_area` VALUES ('14965', '341622', '341600', '蒙城县', null);
INSERT INTO `t_area` VALUES ('14966', '341621', '341600', '涡阳县', null);
INSERT INTO `t_area` VALUES ('14967', '532600', '530000', '文山壮族苗族自治州', null);
INSERT INTO `t_area` VALUES ('14968', '331021', '331000', '玉环县', null);
INSERT INTO `t_area` VALUES ('14969', '331022', '331000', '三门县', null);
INSERT INTO `t_area` VALUES ('14970', '230902', '230900', '新兴区', null);
INSERT INTO `t_area` VALUES ('14971', '230903', '230900', '桃山区', null);
INSERT INTO `t_area` VALUES ('14972', '230904', '230900', '茄子河区', null);
INSERT INTO `t_area` VALUES ('14973', '230900', '230000', '七台河市', null);
INSERT INTO `t_area` VALUES ('14974', '371427', '371400', '夏津县', null);
INSERT INTO `t_area` VALUES ('14975', '371428', '371400', '武城县', null);
INSERT INTO `t_area` VALUES ('14976', '371425', '371400', '齐河县', null);
INSERT INTO `t_area` VALUES ('14977', '371426', '371400', '平原县', null);
INSERT INTO `t_area` VALUES ('14978', '371423', '371400', '庆云县', null);
INSERT INTO `t_area` VALUES ('14979', '371424', '371400', '临邑县', null);
INSERT INTO `t_area` VALUES ('14980', '370811', '370800', '任城区', null);
INSERT INTO `t_area` VALUES ('14981', '371421', '371400', '陵县', null);
INSERT INTO `t_area` VALUES ('14982', '371422', '371400', '宁津县', null);
INSERT INTO `t_area` VALUES ('14983', '131128', '131100', '阜城县', null);
INSERT INTO `t_area` VALUES ('14984', '340811', '340800', '宜秀区', null);
INSERT INTO `t_area` VALUES ('14985', '131126', '131100', '故城县', null);
INSERT INTO `t_area` VALUES ('14986', '131127', '131100', '景县', null);
INSERT INTO `t_area` VALUES ('14987', '230181', '230100', '阿城市', null);
INSERT INTO `t_area` VALUES ('14988', '131124', '131100', '饶阳县', null);
INSERT INTO `t_area` VALUES ('14989', '131125', '131100', '安平县', null);
INSERT INTO `t_area` VALUES ('14990', '131122', '131100', '武邑县', null);
INSERT INTO `t_area` VALUES ('14991', '230184', '230100', '五常市', null);
INSERT INTO `t_area` VALUES ('14992', '131123', '131100', '武强县', null);
INSERT INTO `t_area` VALUES ('14993', '230185', '230100', '阿城市', null);
INSERT INTO `t_area` VALUES ('14994', '230182', '230100', '双城市', null);
INSERT INTO `t_area` VALUES ('14995', '131121', '131100', '枣强县', null);
INSERT INTO `t_area` VALUES ('14996', '230183', '230100', '尚志市', null);
INSERT INTO `t_area` VALUES ('14997', '360602', '360600', '月湖区', null);
INSERT INTO `t_area` VALUES ('14998', '360600', '360000', '鹰潭市', null);
INSERT INTO `t_area` VALUES ('14999', '411525', '411500', '固始县', null);
INSERT INTO `t_area` VALUES ('15000', '411526', '411500', '潢川县', null);
INSERT INTO `t_area` VALUES ('15001', '411527', '411500', '淮滨县', null);
INSERT INTO `t_area` VALUES ('15002', '411528', '411500', '息县', null);
INSERT INTO `t_area` VALUES ('15003', '150430', '150400', '敖汉旗', null);
INSERT INTO `t_area` VALUES ('15004', '411521', '411500', '罗山县', null);
INSERT INTO `t_area` VALUES ('15005', '422822', '422800', '建始县', null);
INSERT INTO `t_area` VALUES ('15006', '441781', '441700', '阳春市', null);
INSERT INTO `t_area` VALUES ('15007', '411522', '411500', '光山县', null);
INSERT INTO `t_area` VALUES ('15008', '422823', '422800', '巴东县', null);
INSERT INTO `t_area` VALUES ('15009', '411523', '411500', '新县', null);
INSERT INTO `t_area` VALUES ('15010', '411524', '411500', '商城县', null);
INSERT INTO `t_area` VALUES ('15011', '422825', '422800', '宣恩县', null);
INSERT INTO `t_area` VALUES ('15012', '370826', '370800', '微山县', null);
INSERT INTO `t_area` VALUES ('15013', '340824', '340800', '潜山县', null);
INSERT INTO `t_area` VALUES ('15014', '422826', '422800', '咸丰县', null);
INSERT INTO `t_area` VALUES ('15015', '370827', '370800', '鱼台县', null);
INSERT INTO `t_area` VALUES ('15016', '340823', '340800', '枞阳县', null);
INSERT INTO `t_area` VALUES ('15017', '422827', '422800', '来凤县', null);
INSERT INTO `t_area` VALUES ('15018', '370828', '370800', '金乡县', null);
INSERT INTO `t_area` VALUES ('15019', '340822', '340800', '怀宁县', null);
INSERT INTO `t_area` VALUES ('15020', '422828', '422800', '鹤峰县', null);
INSERT INTO `t_area` VALUES ('15021', '370829', '370800', '嘉祥县', null);
INSERT INTO `t_area` VALUES ('15022', '340828', '340800', '岳西县', null);
INSERT INTO `t_area` VALUES ('15023', '340827', '340800', '望江县', null);
INSERT INTO `t_area` VALUES ('15024', '340826', '340800', '宿松县', null);
INSERT INTO `t_area` VALUES ('15025', '340825', '340800', '太湖县', null);
INSERT INTO `t_area` VALUES ('15026', '370832', '370800', '梁山县', null);
INSERT INTO `t_area` VALUES ('15027', '370831', '370800', '泗水县', null);
INSERT INTO `t_area` VALUES ('15028', '370830', '370800', '汶上县', null);
INSERT INTO `t_area` VALUES ('15029', '511181', '511100', '峨眉山市', null);
INSERT INTO `t_area` VALUES ('15030', '371451', '371400', '开发区', null);
INSERT INTO `t_area` VALUES ('15031', '431381', '431300', '冷水江市', null);
INSERT INTO `t_area` VALUES ('15032', '411500', '410000', '信阳市', null);
INSERT INTO `t_area` VALUES ('15033', '431382', '431300', '涟源市', null);
INSERT INTO `t_area` VALUES ('15034', '411502', '411500', '浉河区', null);
INSERT INTO `t_area` VALUES ('15035', '411503', '411500', '平桥区', null);
INSERT INTO `t_area` VALUES ('15036', '220323', '220300', '伊通满族自治县', null);
INSERT INTO `t_area` VALUES ('15037', '220322', '220300', '梨树县', null);
INSERT INTO `t_area` VALUES ('15038', '150402', '150400', '红山区', null);
INSERT INTO `t_area` VALUES ('15039', '150403', '150400', '元宝山区', null);
INSERT INTO `t_area` VALUES ('15040', '150400', '150000', '赤峰市', null);
INSERT INTO `t_area` VALUES ('15041', '150404', '150400', '松山区', null);
INSERT INTO `t_area` VALUES ('15042', '140212', '140200', '新荣区', null);
INSERT INTO `t_area` VALUES ('15043', '140211', '140200', '南郊区', null);
INSERT INTO `t_area` VALUES ('15044', '140225', '140200', '浑源县', null);
INSERT INTO `t_area` VALUES ('15045', '140226', '140200', '左云县', null);
INSERT INTO `t_area` VALUES ('15046', '140227', '140200', '大同县', null);
INSERT INTO `t_area` VALUES ('15047', '140221', '140200', '阳高县', null);
INSERT INTO `t_area` VALUES ('15048', '140222', '140200', '天镇县', null);
INSERT INTO `t_area` VALUES ('15049', '140223', '140200', '广灵县', null);
INSERT INTO `t_area` VALUES ('15050', '140224', '140200', '灵丘县', null);
INSERT INTO `t_area` VALUES ('15051', '371482', '371400', '禹城市', null);
INSERT INTO `t_area` VALUES ('15052', '371481', '371400', '乐陵市', null);
INSERT INTO `t_area` VALUES ('15053', '450123', '450100', '隆安县', null);
INSERT INTO `t_area` VALUES ('15054', '450122', '450100', '武鸣县', null);
INSERT INTO `t_area` VALUES ('15055', '220300', '220000', '四平市', null);
INSERT INTO `t_area` VALUES ('15056', '511133', '511100', '马边彝族自治县', null);
INSERT INTO `t_area` VALUES ('15057', '220302', '220300', '铁西区', null);
INSERT INTO `t_area` VALUES ('15058', '450127', '450100', '横县', null);
INSERT INTO `t_area` VALUES ('15059', '450126', '450100', '宾阳县', null);
INSERT INTO `t_area` VALUES ('15060', '511132', '511100', '峨边彝族自治县', null);
INSERT INTO `t_area` VALUES ('15061', '450125', '450100', '上林县', null);
INSERT INTO `t_area` VALUES ('15062', '220303', '220300', '铁东区', null);
INSERT INTO `t_area` VALUES ('15063', '450124', '450100', '马山县', null);
INSERT INTO `t_area` VALUES ('15064', '150428', '150400', '喀喇沁旗', null);
INSERT INTO `t_area` VALUES ('15065', '131100', '130000', '衡水市', null);
INSERT INTO `t_area` VALUES ('15066', '150429', '150400', '宁城县', null);
INSERT INTO `t_area` VALUES ('15067', '150426', '150400', '翁牛特旗', null);
INSERT INTO `t_area` VALUES ('15068', '150424', '150400', '林西县', null);
INSERT INTO `t_area` VALUES ('15069', '511129', '511100', '沐川县', null);
INSERT INTO `t_area` VALUES ('15070', '150425', '150400', '克什克腾旗', null);
INSERT INTO `t_area` VALUES ('15071', '150422', '150400', '巴林左旗', null);
INSERT INTO `t_area` VALUES ('15072', '131102', '131100', '桃城区', null);
INSERT INTO `t_area` VALUES ('15073', '150423', '150400', '巴林右旗', null);
INSERT INTO `t_area` VALUES ('15074', '511126', '511100', '夹江县', null);
INSERT INTO `t_area` VALUES ('15075', '150421', '150400', '阿鲁科尔沁旗', null);
INSERT INTO `t_area` VALUES ('15076', '620423', '620400', '景泰县', null);
INSERT INTO `t_area` VALUES ('15077', '620422', '620400', '会宁县', null);
INSERT INTO `t_area` VALUES ('15078', '360622', '360600', '余江县', null);
INSERT INTO `t_area` VALUES ('15079', '370800', '370000', '济宁市', null);
INSERT INTO `t_area` VALUES ('15080', '370802', '370800', '市中区', null);
INSERT INTO `t_area` VALUES ('15081', '140203', '140200', '矿区', null);
INSERT INTO `t_area` VALUES ('15082', '422801', '422800', '恩施市', null);
INSERT INTO `t_area` VALUES ('15083', '422800', '420000', '恩施土家族苗族自治州', null);
INSERT INTO `t_area` VALUES ('15084', '422802', '422800', '利川市', null);
INSERT INTO `t_area` VALUES ('15085', '340881', '340800', '桐城市', null);
INSERT INTO `t_area` VALUES ('15086', '140200', '140000', '大同市', null);
INSERT INTO `t_area` VALUES ('15087', '140202', '140200', '城区', null);
INSERT INTO `t_area` VALUES ('15088', '320583', '320500', '昆山市', null);
INSERT INTO `t_area` VALUES ('15089', '320582', '320500', '张家港市', null);
INSERT INTO `t_area` VALUES ('15090', '320581', '320500', '常熟市', null);
INSERT INTO `t_area` VALUES ('15091', '511112', '511100', '五通桥区', null);
INSERT INTO `t_area` VALUES ('15092', '511111', '511100', '沙湾区', null);
INSERT INTO `t_area` VALUES ('15093', '511113', '511100', '金口河区', null);
INSERT INTO `t_area` VALUES ('15094', '431322', '431300', '新化县', null);
INSERT INTO `t_area` VALUES ('15095', '431321', '431300', '双峰县', null);
INSERT INTO `t_area` VALUES ('15096', '320594', '320500', '新区', null);
INSERT INTO `t_area` VALUES ('15097', '450621', '450600', '东兴市', null);
INSERT INTO `t_area` VALUES ('15098', '511124', '511100', '井研县', null);
INSERT INTO `t_area` VALUES ('15099', '511123', '511100', '犍为县', null);
INSERT INTO `t_area` VALUES ('15100', '320584', '320500', '吴江市', null);
INSERT INTO `t_area` VALUES ('15101', '320585', '320500', '太仓市', null);
INSERT INTO `t_area` VALUES ('15102', '410481', '410400', '舞钢市', null);
INSERT INTO `t_area` VALUES ('15103', '410482', '410400', '汝州市', null);
INSERT INTO `t_area` VALUES ('15104', '430725', '430700', '桃源县', null);
INSERT INTO `t_area` VALUES ('15105', '430724', '430700', '临澧县', null);
INSERT INTO `t_area` VALUES ('15106', '430726', '430700', '石门县', null);
INSERT INTO `t_area` VALUES ('15107', '421223', '421200', '崇阳县', null);
INSERT INTO `t_area` VALUES ('15108', '421224', '421200', '通山县', null);
INSERT INTO `t_area` VALUES ('15109', '431302', '431300', '娄星区', null);
INSERT INTO `t_area` VALUES ('15110', '431300', '430000', '娄底市', null);
INSERT INTO `t_area` VALUES ('15111', '450612', '450600', '上思县', null);
INSERT INTO `t_area` VALUES ('15112', '522422', '522400', '大方县', null);
INSERT INTO `t_area` VALUES ('15113', '430721', '430700', '安乡县', null);
INSERT INTO `t_area` VALUES ('15114', '320595', '320500', '园区', null);
INSERT INTO `t_area` VALUES ('15115', '430723', '430700', '澧县', null);
INSERT INTO `t_area` VALUES ('15116', '430722', '430700', '汉寿县', null);
INSERT INTO `t_area` VALUES ('15117', '522427', '522400', '威宁彝族回族苗族自治县', null);
INSERT INTO `t_area` VALUES ('15118', '522428', '522400', '赫章县', null);
INSERT INTO `t_area` VALUES ('15119', '522425', '522400', '织金县', null);
INSERT INTO `t_area` VALUES ('15120', '522426', '522400', '纳雍县', null);
INSERT INTO `t_area` VALUES ('15121', '522423', '522400', '黔西县', null);
INSERT INTO `t_area` VALUES ('15122', '522424', '522400', '金沙县', null);
INSERT INTO `t_area` VALUES ('15123', '421222', '421200', '通城县', null);
INSERT INTO `t_area` VALUES ('15124', '421221', '421200', '嘉鱼县', null);
INSERT INTO `t_area` VALUES ('15125', '451221', '451200', '南丹县', null);
INSERT INTO `t_area` VALUES ('15126', '511102', '511100', '市中区', null);
INSERT INTO `t_area` VALUES ('15127', '451223', '451200', '凤山县', null);
INSERT INTO `t_area` VALUES ('15128', '451222', '451200', '天峨县', null);
INSERT INTO `t_area` VALUES ('15129', '511100', '510000', '乐山市', null);
INSERT INTO `t_area` VALUES ('15130', '451225', '451200', '罗城仫佬族自治县', null);
INSERT INTO `t_area` VALUES ('15131', '451224', '451200', '东兰县', null);
INSERT INTO `t_area` VALUES ('15132', '451227', '451200', '巴马瑶族自治县', null);
INSERT INTO `t_area` VALUES ('15133', '450600', '450000', '防城港市', null);
INSERT INTO `t_area` VALUES ('15134', '451226', '451200', '环江毛南族自治县', null);
INSERT INTO `t_area` VALUES ('15135', '451229', '451200', '大化瑶族自治县', null);
INSERT INTO `t_area` VALUES ('15136', '450602', '450600', '港口区', null);
INSERT INTO `t_area` VALUES ('15137', '451228', '451200', '都安瑶族自治县', null);
INSERT INTO `t_area` VALUES ('15138', '450603', '450600', '防城区', null);
INSERT INTO `t_area` VALUES ('15139', '451281', '451200', '宜州市', null);
INSERT INTO `t_area` VALUES ('15140', '410425', '410400', '郏县', null);
INSERT INTO `t_area` VALUES ('15141', '410423', '410400', '鲁山县', null);
INSERT INTO `t_area` VALUES ('15142', '410421', '410400', '宝丰县', null);
INSERT INTO `t_area` VALUES ('15143', '410422', '410400', '叶县', null);
INSERT INTO `t_area` VALUES ('15144', '430700', '430000', '常德市', null);
INSERT INTO `t_area` VALUES ('15145', '421202', '421200', '咸安区', null);
INSERT INTO `t_area` VALUES ('15146', '430702', '430700', '武陵区', null);
INSERT INTO `t_area` VALUES ('15147', '430703', '430700', '鼎城区', null);
INSERT INTO `t_area` VALUES ('15148', '421200', '420000', '咸宁市', null);
INSERT INTO `t_area` VALUES ('15149', '211081', '211000', '灯塔市', null);
INSERT INTO `t_area` VALUES ('15150', '410402', '410400', '新华区', null);
INSERT INTO `t_area` VALUES ('15151', '410400', '410000', '平顶山市', null);
INSERT INTO `t_area` VALUES ('15152', '410403', '410400', '卫东区', null);
INSERT INTO `t_area` VALUES ('15153', '410404', '410400', '石龙区', null);
INSERT INTO `t_area` VALUES ('15154', '410411', '410400', '湛河区', null);
INSERT INTO `t_area` VALUES ('15155', '370882', '370800', '兖州市', null);
INSERT INTO `t_area` VALUES ('15156', '370883', '370800', '邹城市', null);
INSERT INTO `t_area` VALUES ('15157', '370881', '370800', '曲阜市', null);
INSERT INTO `t_area` VALUES ('15158', '320506', '320500', '吴中区', null);
INSERT INTO `t_area` VALUES ('15159', '320507', '320500', '相城区', null);
INSERT INTO `t_area` VALUES ('15160', '320504', '320500', '金阊区', null);
INSERT INTO `t_area` VALUES ('15161', '320505', '320500', '虎丘区', null);
INSERT INTO `t_area` VALUES ('15162', '513300', '510000', '甘孜藏族自治州', null);
INSERT INTO `t_area` VALUES ('15163', '320502', '320500', '沧浪区', null);
INSERT INTO `t_area` VALUES ('15164', '220800', '220000', '白城市', null);
INSERT INTO `t_area` VALUES ('15165', '320503', '320500', '平江区', null);
INSERT INTO `t_area` VALUES ('15166', '320500', '320000', '苏州市', null);
INSERT INTO `t_area` VALUES ('15167', '220802', '220800', '洮北区', null);
INSERT INTO `t_area` VALUES ('15168', '420583', '420500', '枝江市', null);
INSERT INTO `t_area` VALUES ('15169', '420581', '420500', '宜都市', null);
INSERT INTO `t_area` VALUES ('15170', '420582', '420500', '当阳市', null);
INSERT INTO `t_area` VALUES ('15171', '130000', '0', '河北省', null);
INSERT INTO `t_area` VALUES ('15172', '350481', '350400', '永安市', null);
INSERT INTO `t_area` VALUES ('15173', '330600', '330000', '绍兴市', null);
INSERT INTO `t_area` VALUES ('15174', '330602', '330600', '越城区', null);
INSERT INTO `t_area` VALUES ('15175', '530502', '530500', '隆阳区', null);
INSERT INTO `t_area` VALUES ('15176', '530500', '530000', '保山市', null);
INSERT INTO `t_area` VALUES ('15177', '421181', '421100', '麻城市', null);
INSERT INTO `t_area` VALUES ('15178', '421182', '421100', '武穴市', null);
INSERT INTO `t_area` VALUES ('15179', '440600', '440000', '佛山市', null);
INSERT INTO `t_area` VALUES ('15180', '440606', '440600', '顺德区', null);
INSERT INTO `t_area` VALUES ('15181', '451200', '450000', '河池市', null);
INSERT INTO `t_area` VALUES ('15182', '440607', '440600', '三水区', null);
INSERT INTO `t_area` VALUES ('15183', '440608', '440600', '高明区', null);
INSERT INTO `t_area` VALUES ('15184', '440604', '440600', '禅城区', null);
INSERT INTO `t_area` VALUES ('15185', '451202', '451200', '金城江区', null);
INSERT INTO `t_area` VALUES ('15186', '440605', '440600', '南海区', null);
INSERT INTO `t_area` VALUES ('15187', '530521', '530500', '施甸县', null);
INSERT INTO `t_area` VALUES ('15188', '530522', '530500', '腾冲县', null);
INSERT INTO `t_area` VALUES ('15189', '530523', '530500', '龙陵县', null);
INSERT INTO `t_area` VALUES ('15190', '530524', '530500', '昌宁县', null);
INSERT INTO `t_area` VALUES ('15191', '610126', '610100', '高陵县', null);
INSERT INTO `t_area` VALUES ('15192', '220822', '220800', '通榆县', null);
INSERT INTO `t_area` VALUES ('15193', '410381', '410300', '偃师市', null);
INSERT INTO `t_area` VALUES ('15194', '210800', '210000', '营口市', null);
INSERT INTO `t_area` VALUES ('15195', '210802', '210800', '站前区', null);
INSERT INTO `t_area` VALUES ('15196', '610122', '610100', '蓝田县', null);
INSERT INTO `t_area` VALUES ('15197', '210803', '210800', '西市区', null);
INSERT INTO `t_area` VALUES ('15198', '220821', '220800', '镇赉县', null);
INSERT INTO `t_area` VALUES ('15199', '610125', '610100', '户县', null);
INSERT INTO `t_area` VALUES ('15200', '210804', '210800', '鲅鱼圈区', null);
INSERT INTO `t_area` VALUES ('15201', '610124', '610100', '周至县', null);
INSERT INTO `t_area` VALUES ('15202', '610115', '610100', '临潼区', null);
INSERT INTO `t_area` VALUES ('15203', '610116', '610100', '长安区', null);
INSERT INTO `t_area` VALUES ('15204', '430781', '430700', '津市市', null);
INSERT INTO `t_area` VALUES ('15205', '210000', '0', '辽宁省', null);
INSERT INTO `t_area` VALUES ('15206', '210811', '210800', '老边区', null);
INSERT INTO `t_area` VALUES ('15207', '610114', '610100', '阎良区', null);
INSERT INTO `t_area` VALUES ('15208', '610113', '610100', '雁塔区', null);
INSERT INTO `t_area` VALUES ('15209', '610112', '610100', '未央区', null);
INSERT INTO `t_area` VALUES ('15210', '610111', '610100', '灞桥区', null);
INSERT INTO `t_area` VALUES ('15211', '610104', '610100', '莲湖区', null);
INSERT INTO `t_area` VALUES ('15212', '152530', '152500', '正蓝旗', null);
INSERT INTO `t_area` VALUES ('15213', '152531', '152500', '多伦县', null);
INSERT INTO `t_area` VALUES ('15214', '211121', '211100', '大洼县', null);
INSERT INTO `t_area` VALUES ('15215', '211122', '211100', '盘山县', null);
INSERT INTO `t_area` VALUES ('15216', '341422', '340200', '无为县', null);
INSERT INTO `t_area` VALUES ('15217', '140300', '140000', '阳泉市', null);
INSERT INTO `t_area` VALUES ('15218', '341421', '340100', '庐江县', null);
INSERT INTO `t_area` VALUES ('15219', '140303', '140300', '矿区', null);
INSERT INTO `t_area` VALUES ('15220', '140302', '140300', '城区', null);
INSERT INTO `t_area` VALUES ('15221', '220382', '220300', '双辽市', null);
INSERT INTO `t_area` VALUES ('15222', '220381', '220300', '公主岭市', null);
INSERT INTO `t_area` VALUES ('15223', '220882', '220800', '大安市', null);
INSERT INTO `t_area` VALUES ('15224', '350400', '350000', '三明市', null);
INSERT INTO `t_area` VALUES ('15225', '220881', '220800', '洮南市', null);
INSERT INTO `t_area` VALUES ('15226', '610102', '610100', '新城区', null);
INSERT INTO `t_area` VALUES ('15227', '610103', '610100', '碑林区', null);
INSERT INTO `t_area` VALUES ('15228', '610100', '610000', '西安市', null);
INSERT INTO `t_area` VALUES ('15229', '350402', '350400', '梅列区', null);
INSERT INTO `t_area` VALUES ('15230', '350403', '350400', '三元区', null);
INSERT INTO `t_area` VALUES ('15231', '211102', '211100', '双台子区', null);
INSERT INTO `t_area` VALUES ('15232', '211103', '211100', '兴隆台区', null);
INSERT INTO `t_area` VALUES ('15233', '211100', '210000', '盘锦市', null);
INSERT INTO `t_area` VALUES ('15234', '620525', '620500', '张家川回族自治县', null);
INSERT INTO `t_area` VALUES ('15235', '620521', '620500', '清水县', null);
INSERT INTO `t_area` VALUES ('15236', '620522', '620500', '秦安县', null);
INSERT INTO `t_area` VALUES ('15237', '620523', '620500', '甘谷县', null);
INSERT INTO `t_area` VALUES ('15238', '620524', '620500', '武山县', null);
INSERT INTO `t_area` VALUES ('15239', '450100', '450000', '南宁市', null);
INSERT INTO `t_area` VALUES ('15240', '450102', '450100', '兴宁区', null);
INSERT INTO `t_area` VALUES ('15241', '450103', '450100', '青秀区', null);
INSERT INTO `t_area` VALUES ('15242', '441702', '441700', '江城区', null);
INSERT INTO `t_area` VALUES ('15243', '450105', '450100', '江南区', null);
INSERT INTO `t_area` VALUES ('15244', '411481', '411400', '永城市', null);
INSERT INTO `t_area` VALUES ('15245', '441700', '440000', '阳江市', null);
INSERT INTO `t_area` VALUES ('15246', '450107', '450100', '西乡塘区', null);
INSERT INTO `t_area` VALUES ('15247', '450108', '450100', '良庆区', null);
INSERT INTO `t_area` VALUES ('15248', '450109', '450100', '邕宁区', null);
INSERT INTO `t_area` VALUES ('15249', '3306083', '330600', '嵊州市', null);
INSERT INTO `t_area` VALUES ('15250', '330682', '330600', '上虞市', null);
INSERT INTO `t_area` VALUES ('15251', '460200', '460000', '三亚市', null);
INSERT INTO `t_area` VALUES ('15252', '620500', '620000', '天水市', null);
INSERT INTO `t_area` VALUES ('15253', '620502', '620500', '秦州区', null);
INSERT INTO `t_area` VALUES ('15254', '620503', '620500', '麦积区', null);
INSERT INTO `t_area` VALUES ('15255', '330681', '330600', '诸暨市', null);
INSERT INTO `t_area` VALUES ('15256', '420500', '420000', '宜昌市', null);
INSERT INTO `t_area` VALUES ('15257', '420504', '420500', '点军区', null);
INSERT INTO `t_area` VALUES ('15258', '420503', '420500', '伍家岗区', null);
INSERT INTO `t_area` VALUES ('15259', '420502', '420500', '西陵区', null);
INSERT INTO `t_area` VALUES ('15260', '420506', '420500', '夷陵区', null);
INSERT INTO `t_area` VALUES ('15261', '420505', '420500', '猇亭区', null);
INSERT INTO `t_area` VALUES ('15262', '441723', '441700', '阳东县', null);
INSERT INTO `t_area` VALUES ('15263', '441721', '441700', '阳西县', null);
INSERT INTO `t_area` VALUES ('15264', '513338', '513300', '得荣县', null);
INSERT INTO `t_area` VALUES ('15265', '513337', '513300', '稻城县', null);
INSERT INTO `t_area` VALUES ('15266', '632500', '630000', '海南藏族自治州', null);
INSERT INTO `t_area` VALUES ('15267', '330624', '330600', '新昌县', null);
INSERT INTO `t_area` VALUES ('15268', '230128', '230100', '通河县', null);
INSERT INTO `t_area` VALUES ('15269', '230129', '230100', '延寿县', null);
INSERT INTO `t_area` VALUES ('15270', '230126', '230100', '巴彦县', null);
INSERT INTO `t_area` VALUES ('15271', '131181', '131100', '冀州市', null);
INSERT INTO `t_area` VALUES ('15272', '230127', '230100', '木兰县', null);
INSERT INTO `t_area` VALUES ('15273', '230124', '230100', '方正县', null);
INSERT INTO `t_area` VALUES ('15274', '230125', '230100', '宾县', null);
INSERT INTO `t_area` VALUES ('15275', '230123', '230100', '依兰县', null);
INSERT INTO `t_area` VALUES ('15276', '131182', '131100', '深州市', null);
INSERT INTO `t_area` VALUES ('15277', '330621', '330600', '绍兴县', null);
INSERT INTO `t_area` VALUES ('15278', '513329', '513300', '新龙县', null);
INSERT INTO `t_area` VALUES ('15279', '513328', '513300', '甘孜县', null);
INSERT INTO `t_area` VALUES ('15280', '513327', '513300', '炉霍县', null);
INSERT INTO `t_area` VALUES ('15281', '341503', '341500', '裕安区', null);
INSERT INTO `t_area` VALUES ('15282', '513326', '513300', '道孚县', null);
INSERT INTO `t_area` VALUES ('15283', '341502', '341500', '金安区', null);
INSERT INTO `t_area` VALUES ('15284', '341500', '340000', '六安市', null);
INSERT INTO `t_area` VALUES ('15285', '420529', '420500', '五峰土家族自治县', null);
INSERT INTO `t_area` VALUES ('15286', '420527', '420500', '秭归县', null);
INSERT INTO `t_area` VALUES ('15287', '420528', '420500', '长阳土家族自治县', null);
INSERT INTO `t_area` VALUES ('15288', '420525', '420500', '远安县', null);
INSERT INTO `t_area` VALUES ('15289', '513331', '513300', '白玉县', null);
INSERT INTO `t_area` VALUES ('15290', '371500', '370000', '聊城市', null);
INSERT INTO `t_area` VALUES ('15291', '420526', '420500', '兴山县', null);
INSERT INTO `t_area` VALUES ('15292', '513332', '513300', '石渠县', null);
INSERT INTO `t_area` VALUES ('15293', '371502', '371500', '东昌府区', null);
INSERT INTO `t_area` VALUES ('15294', '513330', '513300', '德格县', null);
INSERT INTO `t_area` VALUES ('15295', '350430', '350400', '建宁县', null);
INSERT INTO `t_area` VALUES ('15296', '513335', '513300', '巴塘县', null);
INSERT INTO `t_area` VALUES ('15297', '513336', '513300', '乡城县', null);
INSERT INTO `t_area` VALUES ('15298', '513333', '513300', '色达县', null);
INSERT INTO `t_area` VALUES ('15299', '632521', '632500', '共和县', null);
INSERT INTO `t_area` VALUES ('15300', '513334', '513300', '理塘县', null);
INSERT INTO `t_area` VALUES ('15301', '341400', '340100', '巢湖市', null);
INSERT INTO `t_area` VALUES ('15302', '341402', '340100', '居巢区', null);
INSERT INTO `t_area` VALUES ('15303', '350425', '350400', '大田县', null);
INSERT INTO `t_area` VALUES ('15304', '230108', '230100', '平房区', null);
INSERT INTO `t_area` VALUES ('15305', '350424', '350400', '宁化县', null);
INSERT INTO `t_area` VALUES ('15306', '230109', '230100', '松北区', null);
INSERT INTO `t_area` VALUES ('15307', '350427', '350400', '沙县', null);
INSERT INTO `t_area` VALUES ('15308', '210881', '210800', '盖州市', null);
INSERT INTO `t_area` VALUES ('15309', '230106', '230100', '香坊区', null);
INSERT INTO `t_area` VALUES ('15310', '350426', '350400', '尤溪县', null);
INSERT INTO `t_area` VALUES ('15311', '210882', '210800', '大石桥市', null);
INSERT INTO `t_area` VALUES ('15312', '230107', '230100', '动力区', null);
INSERT INTO `t_area` VALUES ('15313', '350429', '350400', '泰宁县', null);
INSERT INTO `t_area` VALUES ('15314', '350428', '350400', '将乐县', null);
INSERT INTO `t_area` VALUES ('15315', '230100', '230000', '哈尔滨市', null);
INSERT INTO `t_area` VALUES ('15316', '420552', '420500', '开发区', null);
INSERT INTO `t_area` VALUES ('15317', '513322', '513300', '泸定县', null);
INSERT INTO `t_area` VALUES ('15318', '513323', '513300', '丹巴县', null);
INSERT INTO `t_area` VALUES ('15319', '513324', '513300', '九龙县', null);
INSERT INTO `t_area` VALUES ('15320', '513325', '513300', '雅江县', null);
INSERT INTO `t_area` VALUES ('15321', '350421', '350400', '明溪县', null);
INSERT INTO `t_area` VALUES ('15322', '230104', '230100', '道外区', null);
INSERT INTO `t_area` VALUES ('15323', '350423', '350400', '清流县', null);
INSERT INTO `t_area` VALUES ('15324', '230102', '230100', '道里区', null);
INSERT INTO `t_area` VALUES ('15325', '230103', '230100', '南岗区', null);
INSERT INTO `t_area` VALUES ('15326', '513321', '513300', '康定县', null);
INSERT INTO `t_area` VALUES ('15327', '420551', '420500', '葛洲坝区', null);
INSERT INTO `t_area` VALUES ('15328', '341525', '341500', '霍山县', null);
INSERT INTO `t_area` VALUES ('15329', '341524', '341500', '金寨县', null);
INSERT INTO `t_area` VALUES ('15330', '341523', '341500', '舒城县', null);
INSERT INTO `t_area` VALUES ('15331', '341522', '341500', '霍邱县', null);
INSERT INTO `t_area` VALUES ('15332', '341521', '341500', '寿县', null);
INSERT INTO `t_area` VALUES ('15333', '371521', '371500', '阳谷县', null);
INSERT INTO `t_area` VALUES ('15334', '371526', '371500', '高唐县', null);
INSERT INTO `t_area` VALUES ('15335', '230111', '230100', '呼兰区', null);
INSERT INTO `t_area` VALUES ('15336', '371523', '371500', '茌平县', null);
INSERT INTO `t_area` VALUES ('15337', '371522', '371500', '莘县', null);
INSERT INTO `t_area` VALUES ('15338', '371525', '371500', '冠县', null);
INSERT INTO `t_area` VALUES ('15339', '371524', '371500', '东阿县', null);
INSERT INTO `t_area` VALUES ('15340', '441625', '441600', '东源县', null);
INSERT INTO `t_area` VALUES ('15341', '441621', '441600', '紫金县', null);
INSERT INTO `t_area` VALUES ('15342', '441622', '441600', '龙川县', null);
INSERT INTO `t_area` VALUES ('15343', '441623', '441600', '连平县', null);
INSERT INTO `t_area` VALUES ('15344', '520321', '520300', '遵义县', null);
INSERT INTO `t_area` VALUES ('15345', '441624', '441600', '和平县', null);
INSERT INTO `t_area` VALUES ('15346', '360781', '360700', '瑞金市', null);
INSERT INTO `t_area` VALUES ('15347', '350322', '350300', '仙游县', null);
INSERT INTO `t_area` VALUES ('15348', '360782', '360700', '南康市', null);
INSERT INTO `t_area` VALUES ('15349', '520330', '520300', '习水县', null);
INSERT INTO `t_area` VALUES ('15350', '520328', '520300', '湄潭县', null);
INSERT INTO `t_area` VALUES ('15351', '520329', '520300', '余庆县', null);
INSERT INTO `t_area` VALUES ('15352', '520326', '520300', '务川仡佬族苗族自治县', null);
INSERT INTO `t_area` VALUES ('15353', '520327', '520300', '凤冈县', null);
INSERT INTO `t_area` VALUES ('15354', '520324', '520300', '正安县', null);
INSERT INTO `t_area` VALUES ('15355', '520325', '520300', '道真仡佬族苗族自治县', null);
INSERT INTO `t_area` VALUES ('15356', '520322', '520300', '桐梓县', null);
INSERT INTO `t_area` VALUES ('15357', '520323', '520300', '绥阳县', null);
INSERT INTO `t_area` VALUES ('15358', '130635', '130600', '蠡县', null);
INSERT INTO `t_area` VALUES ('15359', '130636', '130600', '顺平县', null);
INSERT INTO `t_area` VALUES ('15360', '130633', '130600', '易县', null);
INSERT INTO `t_area` VALUES ('15361', '130634', '130600', '曲阳县', null);
INSERT INTO `t_area` VALUES ('15362', '130637', '130600', '博野县', null);
INSERT INTO `t_area` VALUES ('15363', '130638', '130600', '雄县', null);
INSERT INTO `t_area` VALUES ('15364', '130631', '130600', '望都县', null);
INSERT INTO `t_area` VALUES ('15365', '130632', '130600', '安新县', null);
INSERT INTO `t_area` VALUES ('15366', '130630', '130600', '涞源县', null);
INSERT INTO `t_area` VALUES ('15367', '350302', '350300', '城厢区', null);
INSERT INTO `t_area` VALUES ('15368', '350300', '350000', '莆田市', null);
INSERT INTO `t_area` VALUES ('15369', '350305', '350300', '秀屿区', null);
INSERT INTO `t_area` VALUES ('15370', '140181', '140100', '古交市', null);
INSERT INTO `t_area` VALUES ('15371', '350303', '350300', '涵江区', null);
INSERT INTO `t_area` VALUES ('15372', '350304', '350300', '荔城区', null);
INSERT INTO `t_area` VALUES ('15373', '330183', '330100', '富阳市', null);
INSERT INTO `t_area` VALUES ('15374', '330185', '330100', '临安市', null);
INSERT INTO `t_area` VALUES ('15375', '330182', '330100', '建德市', null);
INSERT INTO `t_area` VALUES ('15376', '130698', '130600', '高开区', null);
INSERT INTO `t_area` VALUES ('15377', '340322', '340300', '五河县', null);
INSERT INTO `t_area` VALUES ('15378', '340323', '340300', '固镇县', null);
INSERT INTO `t_area` VALUES ('15379', '340321', '340300', '怀远县', null);
INSERT INTO `t_area` VALUES ('15380', '510683', '510600', '绵竹市', null);
INSERT INTO `t_area` VALUES ('15381', '510682', '510600', '什邡市', null);
INSERT INTO `t_area` VALUES ('15382', '510681', '510600', '广汉市', null);
INSERT INTO `t_area` VALUES ('15383', '150500', '150000', '通辽市', null);
INSERT INTO `t_area` VALUES ('15384', '150502', '150500', '科尔沁区', null);
INSERT INTO `t_area` VALUES ('15385', '340311', '340300', '淮上区', null);
INSERT INTO `t_area` VALUES ('15386', '441600', '440000', '河源市', null);
INSERT INTO `t_area` VALUES ('15387', '441602', '441600', '源城区', null);
INSERT INTO `t_area` VALUES ('15388', '340304', '340300', '禹会区', null);
INSERT INTO `t_area` VALUES ('15389', '340300', '340000', '蚌埠市', null);
INSERT INTO `t_area` VALUES ('15390', '340302', '340300', '龙子湖区', null);
INSERT INTO `t_area` VALUES ('15391', '340303', '340300', '蚌山区', null);
INSERT INTO `t_area` VALUES ('15392', '130681', '130600', '涿州市', null);
INSERT INTO `t_area` VALUES ('15393', '130683', '130600', '安国市', null);
INSERT INTO `t_area` VALUES ('15394', '130682', '130600', '定州市', null);
INSERT INTO `t_area` VALUES ('15395', '130684', '130600', '高碑店市', null);
INSERT INTO `t_area` VALUES ('15396', '520303', '520300', '汇川区', null);
INSERT INTO `t_area` VALUES ('15397', '520302', '520300', '红花岗区', null);
INSERT INTO `t_area` VALUES ('15398', '520300', '520000', '遵义市', null);
INSERT INTO `t_area` VALUES ('15399', '411422', '411400', '睢县', null);
INSERT INTO `t_area` VALUES ('15400', '411423', '411400', '宁陵县', null);
INSERT INTO `t_area` VALUES ('15401', '411424', '411400', '柘城县', null);
INSERT INTO `t_area` VALUES ('15402', '411425', '411400', '虞城县', null);
INSERT INTO `t_area` VALUES ('15403', '411426', '411400', '夏邑县', null);
INSERT INTO `t_area` VALUES ('15404', '533423', '533400', '维西傈僳族自治县', null);
INSERT INTO `t_area` VALUES ('15405', '330122', '330100', '桐庐县', null);
INSERT INTO `t_area` VALUES ('15406', '330127', '330100', '淳安县', null);
INSERT INTO `t_area` VALUES ('15407', '210381', '210300', '海城市', null);
INSERT INTO `t_area` VALUES ('15408', '610902', '610900', '汉滨区', null);
INSERT INTO `t_area` VALUES ('15409', '421282', '421200', '温泉城区', null);
INSERT INTO `t_area` VALUES ('15410', '421281', '421200', '赤壁市', null);
INSERT INTO `t_area` VALUES ('15411', '610900', '610000', '安康市', null);
INSERT INTO `t_area` VALUES ('15412', '330110', '330100', '余杭区', null);
INSERT INTO `t_area` VALUES ('15413', '321324', '321300', '泗洪县', null);
INSERT INTO `t_area` VALUES ('15414', '411421', '411400', '民权县', null);
INSERT INTO `t_area` VALUES ('15415', '321323', '321300', '泗阳县', null);
INSERT INTO `t_area` VALUES ('15416', '321322', '321300', '沭阳县', null);
INSERT INTO `t_area` VALUES ('15417', '140100', '140000', '太原市', null);
INSERT INTO `t_area` VALUES ('15418', '210351', '210300', '高新区', null);
INSERT INTO `t_area` VALUES ('15419', '140109', '140100', '万柏林区', null);
INSERT INTO `t_area` VALUES ('15420', '140108', '140100', '尖草坪区', null);
INSERT INTO `t_area` VALUES ('15421', '140105', '140100', '小店区', null);
INSERT INTO `t_area` VALUES ('15422', '140107', '140100', '杏花岭区', null);
INSERT INTO `t_area` VALUES ('15423', '140106', '140100', '迎泽区', null);
INSERT INTO `t_area` VALUES ('15424', '330104', '330100', '江干区', null);
INSERT INTO `t_area` VALUES ('15425', '330103', '330100', '下城区', null);
INSERT INTO `t_area` VALUES ('15426', '330106', '330100', '西湖区', null);
INSERT INTO `t_area` VALUES ('15427', '330105', '330100', '拱墅区', null);
INSERT INTO `t_area` VALUES ('15428', '330100', '330000', '杭州市', null);
INSERT INTO `t_area` VALUES ('15429', '330102', '330100', '上城区', null);
INSERT INTO `t_area` VALUES ('15430', '321311', '321300', '宿豫区', null);
INSERT INTO `t_area` VALUES ('15431', '330108', '330100', '滨江区', null);
INSERT INTO `t_area` VALUES ('15432', '330109', '330100', '萧山区', null);
INSERT INTO `t_area` VALUES ('15433', '610929', '610900', '白河县', null);
INSERT INTO `t_area` VALUES ('15434', '610928', '610900', '旬阳县', null);
INSERT INTO `t_area` VALUES ('15435', '610927', '610900', '镇坪县', null);
INSERT INTO `t_area` VALUES ('15436', '610926', '610900', '平利县', null);
INSERT INTO `t_area` VALUES ('15437', '150524', '150500', '库伦旗', null);
INSERT INTO `t_area` VALUES ('15438', '610925', '610900', '岚皋县', null);
INSERT INTO `t_area` VALUES ('15439', '150523', '150500', '开鲁县', null);
INSERT INTO `t_area` VALUES ('15440', '610924', '610900', '紫阳县', null);
INSERT INTO `t_area` VALUES ('15441', '150522', '150500', '科尔沁左翼后旗', null);
INSERT INTO `t_area` VALUES ('15442', '610923', '610900', '宁陕县', null);
INSERT INTO `t_area` VALUES ('15443', '150521', '150500', '科尔沁左翼中旗', null);
INSERT INTO `t_area` VALUES ('15444', '610922', '610900', '石泉县', null);
INSERT INTO `t_area` VALUES ('15445', '610921', '610900', '汉阴县', null);
INSERT INTO `t_area` VALUES ('15446', '150526', '150500', '扎鲁特旗', null);
INSERT INTO `t_area` VALUES ('15447', '150525', '150500', '奈曼旗', null);
INSERT INTO `t_area` VALUES ('15448', '321302', '321300', '宿城区', null);
INSERT INTO `t_area` VALUES ('15449', '321300', '320000', '宿迁市', null);
INSERT INTO `t_area` VALUES ('15450', '320600', '320000', '南通市', null);
INSERT INTO `t_area` VALUES ('15451', '320602', '320600', '崇川区', null);
INSERT INTO `t_area` VALUES ('15452', '140121', '140100', '清徐县', null);
INSERT INTO `t_area` VALUES ('15453', '140122', '140100', '阳曲县', null);
INSERT INTO `t_area` VALUES ('15454', '140123', '140100', '娄烦县', null);
INSERT INTO `t_area` VALUES ('15455', '320612', '320600', '通州区', null);
INSERT INTO `t_area` VALUES ('15456', '320611', '320600', '港闸区', null);
INSERT INTO `t_area` VALUES ('15457', '210321', '210300', '台安县', null);
INSERT INTO `t_area` VALUES ('15458', '210323', '210300', '岫岩满族自治县', null);
INSERT INTO `t_area` VALUES ('15459', '230291', '230200', '讷河市', null);
INSERT INTO `t_area` VALUES ('15460', '140110', '140100', '晋源区', null);
INSERT INTO `t_area` VALUES ('15461', '150581', '150500', '霍林郭勒市', null);
INSERT INTO `t_area` VALUES ('15462', '320621', '320600', '海安县', null);
INSERT INTO `t_area` VALUES ('15463', '320623', '320600', '如东县', null);
INSERT INTO `t_area` VALUES ('15464', '411400', '410000', '商丘市', null);
INSERT INTO `t_area` VALUES ('15465', '411403', '411400', '睢阳区', null);
INSERT INTO `t_area` VALUES ('15466', '411402', '411400', '梁园区', null);
INSERT INTO `t_area` VALUES ('15467', '210300', '210000', '鞍山市', null);
INSERT INTO `t_area` VALUES ('15468', '210302', '210300', '铁东区', null);
INSERT INTO `t_area` VALUES ('15469', '210303', '210300', '铁西区', null);
INSERT INTO `t_area` VALUES ('15470', '210304', '210300', '立山区', null);
INSERT INTO `t_area` VALUES ('15471', '623001', '623000', '合作市', null);
INSERT INTO `t_area` VALUES ('15472', '623000', '620000', '甘南藏族自治州', null);
INSERT INTO `t_area` VALUES ('15473', '210311', '210300', '千山区', null);
INSERT INTO `t_area` VALUES ('15474', '230231', '230200', '拜泉县', null);
INSERT INTO `t_area` VALUES ('15475', '513435', '513400', '甘洛县', null);
INSERT INTO `t_area` VALUES ('15476', '370983', '370900', '肥城市', null);
INSERT INTO `t_area` VALUES ('15477', '230230', '230200', '克东县', null);
INSERT INTO `t_area` VALUES ('15478', '513434', '513400', '越西县', null);
INSERT INTO `t_area` VALUES ('15479', '513433', '513400', '冕宁县', null);
INSERT INTO `t_area` VALUES ('15480', '513432', '513400', '喜德县', null);
INSERT INTO `t_area` VALUES ('15481', '513431', '513400', '昭觉县', null);
INSERT INTO `t_area` VALUES ('15482', '513430', '513400', '金阳县', null);
INSERT INTO `t_area` VALUES ('15483', '411381', '411300', '邓州市', null);
INSERT INTO `t_area` VALUES ('15484', '370982', '370900', '新泰市', null);
INSERT INTO `t_area` VALUES ('15485', '513429', '513400', '布拖县', null);
INSERT INTO `t_area` VALUES ('15486', '513427', '513400', '宁南县', null);
INSERT INTO `t_area` VALUES ('15487', '513428', '513400', '普格县', null);
INSERT INTO `t_area` VALUES ('15488', '421321', '421300', '随县', null);
INSERT INTO `t_area` VALUES ('15489', '513425', '513400', '会理县', null);
INSERT INTO `t_area` VALUES ('15490', '513426', '513400', '会东县', null);
INSERT INTO `t_area` VALUES ('15491', '623026', '623000', '碌曲县', null);
INSERT INTO `t_area` VALUES ('15492', '440902', '440900', '茂南区', null);
INSERT INTO `t_area` VALUES ('15493', '623025', '623000', '玛曲县', null);
INSERT INTO `t_area` VALUES ('15494', '230221', '230200', '龙江县', null);
INSERT INTO `t_area` VALUES ('15495', '440900', '440000', '茂名市', null);
INSERT INTO `t_area` VALUES ('15496', '623027', '623000', '夏河县', null);
INSERT INTO `t_area` VALUES ('15497', '623022', '623000', '卓尼县', null);
INSERT INTO `t_area` VALUES ('15498', '623021', '623000', '临潭县', null);
INSERT INTO `t_area` VALUES ('15499', '230225', '230200', '甘南县', null);
INSERT INTO `t_area` VALUES ('15500', '623024', '623000', '迭部县', null);
INSERT INTO `t_area` VALUES ('15501', '230224', '230200', '泰来县', null);
INSERT INTO `t_area` VALUES ('15502', '623023', '623000', '舟曲县', null);
INSERT INTO `t_area` VALUES ('15503', '230223', '230200', '依安县', null);
INSERT INTO `t_area` VALUES ('15504', '440903', '440900', '茂港区', null);
INSERT INTO `t_area` VALUES ('15505', '230229', '230200', '克山县', null);
INSERT INTO `t_area` VALUES ('15506', '230227', '230200', '富裕县', null);
INSERT INTO `t_area` VALUES ('15507', '513436', '513400', '美姑县', null);
INSERT INTO `t_area` VALUES ('15508', '513437', '513400', '雷波县', null);
INSERT INTO `t_area` VALUES ('15509', '440104', '440100', '越秀区', null);
INSERT INTO `t_area` VALUES ('15510', '440103', '440100', '荔湾区', null);
INSERT INTO `t_area` VALUES ('15511', '440106', '440100', '天河区', null);
INSERT INTO `t_area` VALUES ('15512', '440105', '440100', '海珠区', null);
INSERT INTO `t_area` VALUES ('15513', '410782', '410700', '辉县市', null);
INSERT INTO `t_area` VALUES ('15514', '440100', '440000', '广州市', null);
INSERT INTO `t_area` VALUES ('15515', '320683', '320600', '通州市', null);
INSERT INTO `t_area` VALUES ('15516', '320684', '320600', '海门市', null);
INSERT INTO `t_area` VALUES ('15517', '410781', '410700', '卫辉市', null);
INSERT INTO `t_area` VALUES ('15518', '320693', '320600', '开发区', null);
INSERT INTO `t_area` VALUES ('15519', '421302', '421300', '曾都区', null);
INSERT INTO `t_area` VALUES ('15520', '421300', '420000', '随州市', null);
INSERT INTO `t_area` VALUES ('15521', '440116', '440100', '萝岗区', null);
INSERT INTO `t_area` VALUES ('15522', '440115', '440100', '南沙区', null);
INSERT INTO `t_area` VALUES ('15523', '440114', '440100', '花都区', null);
INSERT INTO `t_area` VALUES ('15524', '440113', '440100', '番禺区', null);
INSERT INTO `t_area` VALUES ('15525', '440112', '440100', '黄埔区', null);
INSERT INTO `t_area` VALUES ('15526', '440111', '440100', '白云区', null);
INSERT INTO `t_area` VALUES ('15527', '510704', '510700', '游仙区', null);
INSERT INTO `t_area` VALUES ('15528', '510703', '510700', '涪城区', null);
INSERT INTO `t_area` VALUES ('15529', '320682', '320600', '如皋市', null);
INSERT INTO `t_area` VALUES ('15530', '320681', '320600', '启东市', null);
INSERT INTO `t_area` VALUES ('15531', '510700', '510000', '绵阳市', null);
INSERT INTO `t_area` VALUES ('15532', '510923', '510900', '大英县', null);
INSERT INTO `t_area` VALUES ('15533', '510922', '510900', '射洪县', null);
INSERT INTO `t_area` VALUES ('15534', '510921', '510900', '蓬溪县', null);
INSERT INTO `t_area` VALUES ('15535', '224026', '224000', '安图县', null);
INSERT INTO `t_area` VALUES ('15536', '450205', '450200', '柳北区', null);
INSERT INTO `t_area` VALUES ('15537', '450202', '450200', '城中区', null);
INSERT INTO `t_area` VALUES ('15538', '450204', '450200', '柳南区', null);
INSERT INTO `t_area` VALUES ('15539', '450203', '450200', '鱼峰区', null);
INSERT INTO `t_area` VALUES ('15540', '450200', '450000', '柳州市', null);
INSERT INTO `t_area` VALUES ('15541', '510900', '510000', '遂宁市', null);
INSERT INTO `t_area` VALUES ('15542', '510903', '510900', '船山区', null);
INSERT INTO `t_area` VALUES ('15543', '510904', '510900', '安居区', null);
INSERT INTO `t_area` VALUES ('15544', '360800', '360000', '吉安市', null);
INSERT INTO `t_area` VALUES ('15545', '360803', '360800', '青原区', null);
INSERT INTO `t_area` VALUES ('15546', '360802', '360800', '吉州区', null);
INSERT INTO `t_area` VALUES ('15547', '450222', '450200', '柳城县', null);
INSERT INTO `t_area` VALUES ('15548', '450221', '450200', '柳江县', null);
INSERT INTO `t_area` VALUES ('15549', '450224', '450200', '融安县', null);
INSERT INTO `t_area` VALUES ('15550', '450223', '450200', '鹿寨县', null);
INSERT INTO `t_area` VALUES ('15551', '450226', '450200', '三江侗族自治县', null);
INSERT INTO `t_area` VALUES ('15552', '450225', '450200', '融水苗族自治县', null);
INSERT INTO `t_area` VALUES ('15553', '632127', '632100', '化隆回族自治县', null);
INSERT INTO `t_area` VALUES ('15554', '632126', '632100', '互助土族自治县', null);
INSERT INTO `t_area` VALUES ('15555', '632128', '632100', '循化撒拉族自治县', null);
INSERT INTO `t_area` VALUES ('15556', '310230', '310100', '崇明县', null);
INSERT INTO `t_area` VALUES ('15557', '230207', '230200', '碾子山区', null);
INSERT INTO `t_area` VALUES ('15558', '230208', '230200', '梅里斯达斡尔族区', null);
INSERT INTO `t_area` VALUES ('15559', '230205', '230200', '昂昂溪区', null);
INSERT INTO `t_area` VALUES ('15560', '230206', '230200', '富拉尔基区', null);
INSERT INTO `t_area` VALUES ('15561', '230203', '230200', '建华区', null);
INSERT INTO `t_area` VALUES ('15562', '230204', '230200', '铁锋区', null);
INSERT INTO `t_area` VALUES ('15563', '230202', '230200', '龙沙区', null);
INSERT INTO `t_area` VALUES ('15564', '440923', '440900', '电白县', null);
INSERT INTO `t_area` VALUES ('15565', '230200', '230000', '齐齐哈尔市', null);
INSERT INTO `t_area` VALUES ('15566', '130702', '130700', '桥东区', null);
INSERT INTO `t_area` VALUES ('15567', '410711', '410700', '牧野区', null);
INSERT INTO `t_area` VALUES ('15568', '130700', '130000', '张家口市', null);
INSERT INTO `t_area` VALUES ('15569', '360828', '360800', '万安县', null);
INSERT INTO `t_area` VALUES ('15570', '130705', '130700', '宣化区', null);
INSERT INTO `t_area` VALUES ('15571', '360827', '360800', '遂川县', null);
INSERT INTO `t_area` VALUES ('15572', '130706', '130700', '下花园区', null);
INSERT INTO `t_area` VALUES ('15573', '130703', '130700', '桥西区', null);
INSERT INTO `t_area` VALUES ('15574', '360829', '360800', '安福县', null);
INSERT INTO `t_area` VALUES ('15575', '211000', '210000', '辽阳市', null);
INSERT INTO `t_area` VALUES ('15576', '370903', '370900', '岱岳区', null);
INSERT INTO `t_area` VALUES ('15577', '370902', '370900', '泰山区', null);
INSERT INTO `t_area` VALUES ('15578', '430408', '430400', '蒸湘区', null);
INSERT INTO `t_area` VALUES ('15579', '430407', '430400', '石鼓区', null);
INSERT INTO `t_area` VALUES ('15580', '370900', '370000', '泰安市', null);
INSERT INTO `t_area` VALUES ('15581', '430406', '430400', '雁峰区', null);
INSERT INTO `t_area` VALUES ('15582', '430405', '430400', '珠晖区', null);
INSERT INTO `t_area` VALUES ('15583', '360823', '360800', '峡江县', null);
INSERT INTO `t_area` VALUES ('15584', '360824', '360800', '新干县', null);
INSERT INTO `t_area` VALUES ('15585', '360825', '360800', '永丰县', null);
INSERT INTO `t_area` VALUES ('15586', '360826', '360800', '泰和县', null);
INSERT INTO `t_area` VALUES ('15587', '430400', '430000', '衡阳市', null);
INSERT INTO `t_area` VALUES ('15588', '620102', '620100', '城关区', null);
INSERT INTO `t_area` VALUES ('15589', '360821', '360800', '吉安县', null);
INSERT INTO `t_area` VALUES ('15590', '620100', '620000', '兰州市', null);
INSERT INTO `t_area` VALUES ('15591', '360822', '360800', '吉水县', null);
INSERT INTO `t_area` VALUES ('15592', '410703', '410700', '卫滨区', null);
INSERT INTO `t_area` VALUES ('15593', '410702', '410700', '红旗区', null);
INSERT INTO `t_area` VALUES ('15594', '410700', '410000', '新乡市', null);
INSERT INTO `t_area` VALUES ('15595', '440183', '440100', '增城市', null);
INSERT INTO `t_area` VALUES ('15596', '211011', '211000', '太子河区', null);
INSERT INTO `t_area` VALUES ('15597', '440184', '440100', '从化市', null);
INSERT INTO `t_area` VALUES ('15598', '420281', '420200', '大冶市', null);
INSERT INTO `t_area` VALUES ('15599', '410704', '410700', '凤泉区', null);
INSERT INTO `t_area` VALUES ('15600', '632123', '632100', '乐都县', null);
INSERT INTO `t_area` VALUES ('15601', '632122', '632100', '民和回族土族自治县', null);
INSERT INTO `t_area` VALUES ('15602', '220700', '220000', '松原市', null);
INSERT INTO `t_area` VALUES ('15603', '371203', '371200', '钢城区', null);
INSERT INTO `t_area` VALUES ('15604', '632121', '632100', '平安县', null);
INSERT INTO `t_area` VALUES ('15605', '371200', '370000', '莱芜市', null);
INSERT INTO `t_area` VALUES ('15606', '211004', '211000', '宏伟区', null);
INSERT INTO `t_area` VALUES ('15607', '211005', '211000', '弓长岭区', null);
INSERT INTO `t_area` VALUES ('15608', '430412', '430400', '南岳区', null);
INSERT INTO `t_area` VALUES ('15609', '371202', '371200', '莱城区', null);
INSERT INTO `t_area` VALUES ('15610', '211002', '211000', '白塔区', null);
INSERT INTO `t_area` VALUES ('15611', '440982', '440900', '化州市', null);
INSERT INTO `t_area` VALUES ('15612', '211003', '211000', '文圣区', null);
INSERT INTO `t_area` VALUES ('15613', '440981', '440900', '高州市', null);
INSERT INTO `t_area` VALUES ('15614', '370000', '0', '山东省', null);
INSERT INTO `t_area` VALUES ('15615', '220702', '220700', '宁江区', null);
INSERT INTO `t_area` VALUES ('15616', '440983', '440900', '信宜市', null);
INSERT INTO `t_area` VALUES ('15617', '211021', '211000', '辽阳县', null);
INSERT INTO `t_area` VALUES ('15618', '370923', '370900', '东平县', null);
INSERT INTO `t_area` VALUES ('15619', '370921', '370900', '宁阳县', null);
INSERT INTO `t_area` VALUES ('15620', '430422', '430400', '衡南县', null);
INSERT INTO `t_area` VALUES ('15621', '430421', '430400', '衡阳县', null);
INSERT INTO `t_area` VALUES ('15622', '510781', '510700', '江油市', null);
INSERT INTO `t_area` VALUES ('15623', '430426', '430400', '祁东县', null);
INSERT INTO `t_area` VALUES ('15624', '430424', '430400', '衡东县', null);
INSERT INTO `t_area` VALUES ('15625', '440188', '440100', '东山区', null);
INSERT INTO `t_area` VALUES ('15626', '430423', '430400', '衡山县', null);
INSERT INTO `t_area` VALUES ('15627', '632100', '630000', '海东地区', null);
INSERT INTO `t_area` VALUES ('15628', '220721', '220700', '前郭尔罗斯蒙古族自治县', null);
INSERT INTO `t_area` VALUES ('15629', '220722', '220700', '长岭县', null);
INSERT INTO `t_area` VALUES ('15630', '220723', '220700', '乾安县', null);
INSERT INTO `t_area` VALUES ('15631', '220724', '220700', '扶余县', null);
INSERT INTO `t_area` VALUES ('15632', '360830', '360800', '永新县', null);
INSERT INTO `t_area` VALUES ('15633', '420222', '420200', '阳新县', null);
INSERT INTO `t_area` VALUES ('15634', '510727', '510700', '平武县', null);
INSERT INTO `t_area` VALUES ('15635', '445381', '445300', '罗定市', null);
INSERT INTO `t_area` VALUES ('15636', '130731', '130700', '涿鹿县', null);
INSERT INTO `t_area` VALUES ('15637', '130730', '130700', '怀来县', null);
INSERT INTO `t_area` VALUES ('15638', '130733', '130700', '崇礼县', null);
INSERT INTO `t_area` VALUES ('15639', '130732', '130700', '赤城县', null);
INSERT INTO `t_area` VALUES ('15640', '510722', '510700', '三台县', null);
INSERT INTO `t_area` VALUES ('15641', '510723', '510700', '盐亭县', null);
INSERT INTO `t_area` VALUES ('15642', '510724', '510700', '安县', null);
INSERT INTO `t_area` VALUES ('15643', '510725', '510700', '梓潼县', null);
INSERT INTO `t_area` VALUES ('15644', '510726', '510700', '北川羌族自治县', null);
INSERT INTO `t_area` VALUES ('15645', '513401', '513400', '西昌市', null);
INSERT INTO `t_area` VALUES ('15646', '513400', '510000', '凉山彝族自治州', null);
INSERT INTO `t_area` VALUES ('15647', '130728', '130700', '怀安县', null);
INSERT INTO `t_area` VALUES ('15648', '130727', '130700', '阳原县', null);
INSERT INTO `t_area` VALUES ('15649', '130726', '130700', '蔚县', null);
INSERT INTO `t_area` VALUES ('15650', '130725', '130700', '尚义县', null);
INSERT INTO `t_area` VALUES ('15651', '130724', '130700', '沽源县', null);
INSERT INTO `t_area` VALUES ('15652', '130723', '130700', '康保县', null);
INSERT INTO `t_area` VALUES ('15653', '130722', '130700', '张北县', null);
INSERT INTO `t_area` VALUES ('15654', '130721', '130700', '宣化县', null);
INSERT INTO `t_area` VALUES ('15655', '620123', '620100', '榆中县', null);
INSERT INTO `t_area` VALUES ('15656', '620121', '620100', '永登县', null);
INSERT INTO `t_area` VALUES ('15657', '620122', '620100', '皋兰县', null);
INSERT INTO `t_area` VALUES ('15658', '130729', '130700', '万全县', null);
INSERT INTO `t_area` VALUES ('15659', '420204', '420200', '下陆区', null);
INSERT INTO `t_area` VALUES ('15660', '420205', '420200', '铁山区', null);
INSERT INTO `t_area` VALUES ('15661', '420200', '420000', '黄石市', null);
INSERT INTO `t_area` VALUES ('15662', '360881', '360800', '井冈山市', null);
INSERT INTO `t_area` VALUES ('15663', '420202', '420200', '黄石港区', null);
INSERT INTO `t_area` VALUES ('15664', '420203', '420200', '西塞山区', null);
INSERT INTO `t_area` VALUES ('15665', '410728', '410700', '长垣县', null);
INSERT INTO `t_area` VALUES ('15666', '410726', '410700', '延津县', null);
INSERT INTO `t_area` VALUES ('15667', '410727', '410700', '封丘县', null);
INSERT INTO `t_area` VALUES ('15668', '410721', '410700', '新乡县', null);
INSERT INTO `t_area` VALUES ('15669', '410724', '410700', '获嘉县', null);
INSERT INTO `t_area` VALUES ('15670', '430482', '430400', '常宁市', null);
INSERT INTO `t_area` VALUES ('15671', '620104', '620100', '西固区', null);
INSERT INTO `t_area` VALUES ('15672', '410725', '410700', '原阳县', null);
INSERT INTO `t_area` VALUES ('15673', '430481', '430400', '耒阳市', null);
INSERT INTO `t_area` VALUES ('15674', '620103', '620100', '七里河区', null);
INSERT INTO `t_area` VALUES ('15675', '620105', '620100', '安宁区', null);
INSERT INTO `t_area` VALUES ('15676', '620111', '620100', '红古区', null);
INSERT INTO `t_area` VALUES ('15677', '510751', '510700', '高新区', null);
INSERT INTO `t_area` VALUES ('15678', '530181', '530100', '安宁市', null);
INSERT INTO `t_area` VALUES ('15679', '513423', '513400', '盐源县', null);
INSERT INTO `t_area` VALUES ('15680', '513424', '513400', '德昌县', null);
INSERT INTO `t_area` VALUES ('15681', '513422', '513400', '木里藏族自治县', null);
INSERT INTO `t_area` VALUES ('15682', '441521', '441500', '海丰县', null);
INSERT INTO `t_area` VALUES ('15683', '441523', '441500', '陆河县', null);
INSERT INTO `t_area` VALUES ('15684', '520222', '520200', '盘县', null);
INSERT INTO `t_area` VALUES ('15685', '530381', '530300', '宣威市', null);
INSERT INTO `t_area` VALUES ('15686', '520221', '520200', '水城县', null);
INSERT INTO `t_area` VALUES ('15687', '340222', '340200', '繁昌县', null);
INSERT INTO `t_area` VALUES ('15688', '340221', '340200', '芜湖县', null);
INSERT INTO `t_area` VALUES ('15689', '340223', '340200', '南陵县', null);
INSERT INTO `t_area` VALUES ('15690', '360681', '360600', '贵溪市', null);
INSERT INTO `t_area` VALUES ('15691', '441500', '440000', '汕尾市', null);
INSERT INTO `t_area` VALUES ('15692', '441502', '441500', '城区', null);
INSERT INTO `t_area` VALUES ('15693', '520200', '520000', '六盘水市', null);
INSERT INTO `t_area` VALUES ('15694', '522230', '522200', '万山特区', null);
INSERT INTO `t_area` VALUES ('15695', '522229', '522200', '松桃苗族自治县', null);
INSERT INTO `t_area` VALUES ('15696', '522227', '522200', '德江县', null);
INSERT INTO `t_area` VALUES ('15697', '522228', '522200', '沿河土家族自治县', null);
INSERT INTO `t_area` VALUES ('15698', '522225', '522200', '思南县', null);
INSERT INTO `t_area` VALUES ('15699', '522226', '522200', '印江土家族苗族自治县', null);
INSERT INTO `t_area` VALUES ('15700', '522222', '522200', '江口县', null);
INSERT INTO `t_area` VALUES ('15701', '522224', '522200', '石阡县', null);
INSERT INTO `t_area` VALUES ('15702', '522223', '522200', '玉屏侗族自治县', null);
INSERT INTO `t_area` VALUES ('15703', '350206', '350200', '湖里区', null);
INSERT INTO `t_area` VALUES ('15704', '350205', '350200', '海沧区', null);
INSERT INTO `t_area` VALUES ('15705', '350203', '350200', '思明区', null);
INSERT INTO `t_area` VALUES ('15706', '350200', '350000', '厦门市', null);
INSERT INTO `t_area` VALUES ('15707', '512022', '512000', '乐至县', null);
INSERT INTO `t_area` VALUES ('15708', '512021', '512000', '安岳县', null);
INSERT INTO `t_area` VALUES ('15709', '140981', '140900', '原平市', null);
INSERT INTO `t_area` VALUES ('15710', '520203', '520200', '六枝特区', null);
INSERT INTO `t_area` VALUES ('15711', '520201', '520200', '钟山区', null);
INSERT INTO `t_area` VALUES ('15712', '430511', '430500', '北塔区', null);
INSERT INTO `t_area` VALUES ('15713', '445321', '445300', '新兴县', null);
INSERT INTO `t_area` VALUES ('15714', '445322', '445300', '郁南县', null);
INSERT INTO `t_area` VALUES ('15715', '445323', '445300', '云安县', null);
INSERT INTO `t_area` VALUES ('15716', '512000', '510000', '资阳市', null);
INSERT INTO `t_area` VALUES ('15717', '512002', '512000', '雁江区', null);
INSERT INTO `t_area` VALUES ('15718', '430500', '430000', '邵阳市', null);
INSERT INTO `t_area` VALUES ('15719', '430502', '430500', '双清区', null);
INSERT INTO `t_area` VALUES ('15720', '430503', '430500', '大祥区', null);
INSERT INTO `t_area` VALUES ('15721', '522200', '520000', '铜仁地区', null);
INSERT INTO `t_area` VALUES ('15722', '522201', '522200', '铜仁市', null);
INSERT INTO `t_area` VALUES ('15723', '210282', '210200', '普兰店市', null);
INSERT INTO `t_area` VALUES ('15724', '210281', '210200', '瓦房店市', null);
INSERT INTO `t_area` VALUES ('15725', '210283', '210200', '庄河市', null);
INSERT INTO `t_area` VALUES ('15726', '150600', '150000', '鄂尔多斯市', null);
INSERT INTO `t_area` VALUES ('15727', '150602', '150600', '东胜区', null);
INSERT INTO `t_area` VALUES ('15728', '370205', '370200', '四方区', null);
INSERT INTO `t_area` VALUES ('15729', '370202', '370200', '市南区', null);
INSERT INTO `t_area` VALUES ('15730', '370203', '370200', '市北区', null);
INSERT INTO `t_area` VALUES ('15731', '370200', '370000', '青岛市', null);
INSERT INTO `t_area` VALUES ('15732', '210297', '210200', '岭前区', null);
INSERT INTO `t_area` VALUES ('15733', '340202', '340200', '镜湖区', null);
INSERT INTO `t_area` VALUES ('15734', '340203', '340200', '弋江区', null);
INSERT INTO `t_area` VALUES ('15735', '445300', '440000', '云浮市', null);
INSERT INTO `t_area` VALUES ('15736', '340200', '340000', '芜湖市', null);
INSERT INTO `t_area` VALUES ('15737', '340207', '340200', '鸠江区', null);
INSERT INTO `t_area` VALUES ('15738', '340208', '340200', '三山区', null);
INSERT INTO `t_area` VALUES ('15739', '445302', '445300', '云城区', null);
INSERT INTO `t_area` VALUES ('15740', '620300', '620000', '金昌市', null);
INSERT INTO `t_area` VALUES ('15741', '530323', '530300', '师宗县', null);
INSERT INTO `t_area` VALUES ('15742', '530324', '530300', '罗平县', null);
INSERT INTO `t_area` VALUES ('15743', '530321', '530300', '马龙县', null);
INSERT INTO `t_area` VALUES ('15744', '530322', '530300', '陆良县', null);
INSERT INTO `t_area` VALUES ('15745', '530328', '530300', '沾益县', null);
INSERT INTO `t_area` VALUES ('15746', '530325', '530300', '富源县', null);
INSERT INTO `t_area` VALUES ('15747', '530326', '530300', '会泽县', null);
INSERT INTO `t_area` VALUES ('15748', '150624', '150600', '鄂托克旗', null);
INSERT INTO `t_area` VALUES ('15749', '150625', '150600', '杭锦旗', null);
INSERT INTO `t_area` VALUES ('15750', '150626', '150600', '乌审旗', null);
INSERT INTO `t_area` VALUES ('15751', '150627', '150600', '伊金霍洛旗', null);
INSERT INTO `t_area` VALUES ('15752', '150621', '150600', '达拉特旗', null);
INSERT INTO `t_area` VALUES ('15753', '150622', '150600', '准格尔旗', null);
INSERT INTO `t_area` VALUES ('15754', '150623', '150600', '鄂托克前旗', null);
INSERT INTO `t_area` VALUES ('15755', '411327', '411300', '社旗县', null);
INSERT INTO `t_area` VALUES ('15756', '411328', '411300', '唐河县', null);
INSERT INTO `t_area` VALUES ('15757', '411329', '411300', '新野县', null);
INSERT INTO `t_area` VALUES ('15758', '411323', '411300', '西峡县', null);
INSERT INTO `t_area` VALUES ('15759', '224024', '224000', '汪清县', null);
INSERT INTO `t_area` VALUES ('15760', '411324', '411300', '镇平县', null);
INSERT INTO `t_area` VALUES ('15761', '411325', '411300', '内乡县', null);
INSERT INTO `t_area` VALUES ('15762', '411326', '411300', '淅川县', null);
INSERT INTO `t_area` VALUES ('15763', '411330', '411300', '桐柏县', null);
INSERT INTO `t_area` VALUES ('15764', '210224', '210200', '长海县', null);
INSERT INTO `t_area` VALUES ('15765', '421381', '421300', '广水市', null);
INSERT INTO `t_area` VALUES ('15766', '610802', '610800', '榆阳区', null);
INSERT INTO `t_area` VALUES ('15767', '610800', '610000', '榆林市', null);
INSERT INTO `t_area` VALUES ('15768', '530300', '530000', '曲靖市', null);
INSERT INTO `t_area` VALUES ('15769', '530302', '530300', '麒麟区', null);
INSERT INTO `t_area` VALUES ('15770', '224004', '224000', '珲春市', null);
INSERT INTO `t_area` VALUES ('15771', '411322', '411300', '方城县', null);
INSERT INTO `t_area` VALUES ('15772', '224005', '224000', '龙井市', null);
INSERT INTO `t_area` VALUES ('15773', '411321', '411300', '南召县', null);
INSERT INTO `t_area` VALUES ('15774', '224006', '224000', '和龙市', null);
INSERT INTO `t_area` VALUES ('15775', '320721', '320700', '赣榆县', null);
INSERT INTO `t_area` VALUES ('15776', '320722', '320700', '东海县', null);
INSERT INTO `t_area` VALUES ('15777', '320723', '320700', '灌云县', null);
INSERT INTO `t_area` VALUES ('15778', '320724', '320700', '灌南县', null);
INSERT INTO `t_area` VALUES ('15779', '630100', '630000', '西宁市', null);
INSERT INTO `t_area` VALUES ('15780', '224003', '224000', '敦化市', null);
INSERT INTO `t_area` VALUES ('15781', '210251', '210200', '开发区', null);
INSERT INTO `t_area` VALUES ('15782', '224002', '224000', '图们市', null);
INSERT INTO `t_area` VALUES ('15783', '411302', '411300', '宛城区', null);
INSERT INTO `t_area` VALUES ('15784', '224001', '224000', '延吉市', null);
INSERT INTO `t_area` VALUES ('15785', '411303', '411300', '卧龙区', null);
INSERT INTO `t_area` VALUES ('15786', '224000', '220000', '延边朝鲜族自治州', null);
INSERT INTO `t_area` VALUES ('15787', '330000', '0', '浙江省', null);
INSERT INTO `t_area` VALUES ('15788', '140902', '140900', '忻府区', null);
INSERT INTO `t_area` VALUES ('15789', '140900', '140000', '忻州市', null);
INSERT INTO `t_area` VALUES ('15790', '320700', '320000', '连云港市', null);
INSERT INTO `t_area` VALUES ('15791', '411300', '410000', '南阳市', null);
INSERT INTO `t_area` VALUES ('15792', '320706', '320700', '海州区', null);
INSERT INTO `t_area` VALUES ('15793', '320703', '320700', '连云区', null);
INSERT INTO `t_area` VALUES ('15794', '320705', '320700', '新浦区', null);
INSERT INTO `t_area` VALUES ('15795', '350213', '350200', '翔安区', null);
INSERT INTO `t_area` VALUES ('15796', '350212', '350200', '同安区', null);
INSERT INTO `t_area` VALUES ('15797', '350211', '350200', '集美区', null);
INSERT INTO `t_area` VALUES ('15798', '140924', '140900', '繁峙县', null);
INSERT INTO `t_area` VALUES ('15799', '140925', '140900', '宁武县', null);
INSERT INTO `t_area` VALUES ('15800', '140922', '140900', '五台县', null);
INSERT INTO `t_area` VALUES ('15801', '140923', '140900', '代县', null);
INSERT INTO `t_area` VALUES ('15802', '140921', '140900', '定襄县', null);
INSERT INTO `t_area` VALUES ('15803', '140929', '140900', '岢岚县', null);
INSERT INTO `t_area` VALUES ('15804', '140928', '140900', '五寨县', null);
INSERT INTO `t_area` VALUES ('15805', '140927', '140900', '神池县', null);
INSERT INTO `t_area` VALUES ('15806', '140926', '140900', '静乐县', null);
INSERT INTO `t_area` VALUES ('15807', '140000', '0', '山西省', null);
INSERT INTO `t_area` VALUES ('15808', '610831', '610800', '清涧县', null);
INSERT INTO `t_area` VALUES ('15809', '140930', '140900', '河曲县', null);
INSERT INTO `t_area` VALUES ('15810', '610832', '610800', '子洲县', null);
INSERT INTO `t_area` VALUES ('15811', '140931', '140900', '保德县', null);
INSERT INTO `t_area` VALUES ('15812', '140932', '140900', '偏关县', null);
INSERT INTO `t_area` VALUES ('15813', '210203', '210200', '西岗区', null);
INSERT INTO `t_area` VALUES ('15814', '210204', '210200', '沙河口区', null);
INSERT INTO `t_area` VALUES ('15815', '210200', '210000', '大连市', null);
INSERT INTO `t_area` VALUES ('15816', '210202', '210200', '中山区', null);
INSERT INTO `t_area` VALUES ('15817', '610829', '610800', '吴堡县', null);
INSERT INTO `t_area` VALUES ('15818', '610827', '610800', '米脂县', null);
INSERT INTO `t_area` VALUES ('15819', '610828', '610800', '佳县', null);
INSERT INTO `t_area` VALUES ('15820', '610821', '610800', '神木县', null);
INSERT INTO `t_area` VALUES ('15821', '610822', '610800', '府谷县', null);
INSERT INTO `t_area` VALUES ('15822', '610825', '610800', '定边县', null);
INSERT INTO `t_area` VALUES ('15823', '610826', '610800', '绥德县', null);
INSERT INTO `t_area` VALUES ('15824', '610823', '610800', '横山县', null);
INSERT INTO `t_area` VALUES ('15825', '610824', '610800', '靖边县', null);
INSERT INTO `t_area` VALUES ('15826', '511400', '510000', '眉山市', null);
INSERT INTO `t_area` VALUES ('15827', '610000', '0', '陕西省', null);
INSERT INTO `t_area` VALUES ('15828', '210212', '210200', '旅顺口区', null);
INSERT INTO `t_area` VALUES ('15829', '210213', '210200', '金州区', null);
INSERT INTO `t_area` VALUES ('15830', '210211', '210200', '甘井子区', null);
INSERT INTO `t_area` VALUES ('15831', '450304', '450300', '象山区', null);
INSERT INTO `t_area` VALUES ('15832', '411282', '411200', '灵宝市', null);
INSERT INTO `t_area` VALUES ('15833', '450305', '450300', '七星区', null);
INSERT INTO `t_area` VALUES ('15834', '451025', '451000', '靖西县', null);
INSERT INTO `t_area` VALUES ('15835', '451024', '451000', '德保县', null);
INSERT INTO `t_area` VALUES ('15836', '451023', '451000', '平果县', null);
INSERT INTO `t_area` VALUES ('15837', '451022', '451000', '田东县', null);
INSERT INTO `t_area` VALUES ('15838', '511300', '510000', '南充市', null);
INSERT INTO `t_area` VALUES ('15839', '450300', '450000', '桂林市', null);
INSERT INTO `t_area` VALUES ('15840', '451029', '451000', '田林县', null);
INSERT INTO `t_area` VALUES ('15841', '451028', '451000', '乐业县', null);
INSERT INTO `t_area` VALUES ('15842', '411281', '411200', '义马市', null);
INSERT INTO `t_area` VALUES ('15843', '450302', '450300', '秀峰区', null);
INSERT INTO `t_area` VALUES ('15844', '451027', '451000', '凌云县', null);
INSERT INTO `t_area` VALUES ('15845', '450303', '450300', '叠彩区', null);
INSERT INTO `t_area` VALUES ('15846', '451026', '451000', '那坡县', null);
INSERT INTO `t_area` VALUES ('15847', '230381', '230300', '虎林市', null);
INSERT INTO `t_area` VALUES ('15848', '230382', '230300', '密山市', null);
INSERT INTO `t_area` VALUES ('15849', '451031', '451000', '隆林各族自治县', null);
INSERT INTO `t_area` VALUES ('15850', '451030', '451000', '西林县', null);
INSERT INTO `t_area` VALUES ('15851', '370181', '370100', '章丘市', null);
INSERT INTO `t_area` VALUES ('15852', '469025', '460000', '定安县', null);
INSERT INTO `t_area` VALUES ('15853', '469026', '460000', '屯昌县', null);
INSERT INTO `t_area` VALUES ('15854', '469027', '460000', '澄迈县', null);
INSERT INTO `t_area` VALUES ('15855', '469028', '460000', '临高县', null);
INSERT INTO `t_area` VALUES ('15856', '450311', '450300', '雁山区', null);
INSERT INTO `t_area` VALUES ('15857', '510802', '510800', '利州区', null);
INSERT INTO `t_area` VALUES ('15858', '510800', '510000', '广元市', null);
INSERT INTO `t_area` VALUES ('15859', '469030', '460000', '白沙黎族自治县', null);
INSERT INTO `t_area` VALUES ('15860', '469031', '460000', '昌江黎族自治县', null);
INSERT INTO `t_area` VALUES ('15861', '451021', '451000', '田阳县', null);
INSERT INTO `t_area` VALUES ('15862', '469034', '460000', '陵水黎族自治县', null);
INSERT INTO `t_area` VALUES ('15863', '469035', '460000', '保亭黎族苗族自治县', null);
INSERT INTO `t_area` VALUES ('15864', '469033', '460000', '乐东黎族自治县', null);
INSERT INTO `t_area` VALUES ('15865', '469038', '460000', '南沙群岛', null);
INSERT INTO `t_area` VALUES ('15866', '469039', '460000', '中沙群岛的岛礁及其海域', null);
INSERT INTO `t_area` VALUES ('15867', '469036', '460000', '琼中黎族苗族自治县', null);
INSERT INTO `t_area` VALUES ('15868', '469037', '460000', '西沙群岛', null);
INSERT INTO `t_area` VALUES ('15869', '420302', '420300', '茅箭区', null);
INSERT INTO `t_area` VALUES ('15870', '420300', '420000', '十堰市', null);
INSERT INTO `t_area` VALUES ('15871', '511321', '511300', '南部县', null);
INSERT INTO `t_area` VALUES ('15872', '511323', '511300', '蓬安县', null);
INSERT INTO `t_area` VALUES ('15873', '420303', '420300', '张湾区', null);
INSERT INTO `t_area` VALUES ('15874', '511322', '511300', '营山县', null);
INSERT INTO `t_area` VALUES ('15875', '220681', '220600', '临江市', null);
INSERT INTO `t_area` VALUES ('15876', '510812', '510800', '朝天区', null);
INSERT INTO `t_area` VALUES ('15877', '510811', '510800', '元坝区', null);
INSERT INTO `t_area` VALUES ('15878', '510821', '510800', '旺苍县', null);
INSERT INTO `t_area` VALUES ('15879', '511304', '511300', '嘉陵区', null);
INSERT INTO `t_area` VALUES ('15880', '511302', '511300', '顺庆区', null);
INSERT INTO `t_area` VALUES ('15881', '511303', '511300', '高坪区', null);
INSERT INTO `t_area` VALUES ('15882', '510824', '510800', '苍溪县', null);
INSERT INTO `t_area` VALUES ('15883', '510823', '510800', '剑阁县', null);
INSERT INTO `t_area` VALUES ('15884', '510822', '510800', '青川县', null);
INSERT INTO `t_area` VALUES ('15885', '440811', '440800', '麻章区', null);
INSERT INTO `t_area` VALUES ('15886', '440804', '440800', '坡头区', null);
INSERT INTO `t_area` VALUES ('15887', '440802', '440800', '赤坎区', null);
INSERT INTO `t_area` VALUES ('15888', '230321', '230300', '鸡东县', null);
INSERT INTO `t_area` VALUES ('15889', '440803', '440800', '霞山区', null);
INSERT INTO `t_area` VALUES ('15890', '440800', '440000', '湛江市', null);
INSERT INTO `t_area` VALUES ('15891', '511325', '511300', '西充县', null);
INSERT INTO `t_area` VALUES ('15892', '511324', '511300', '仪陇县', null);
INSERT INTO `t_area` VALUES ('15893', '450323', '450300', '灵川县', null);
INSERT INTO `t_area` VALUES ('15894', '450322', '450300', '临桂县', null);
INSERT INTO `t_area` VALUES ('15895', '450325', '450300', '兴安县', null);
INSERT INTO `t_area` VALUES ('15896', '450324', '450300', '全州县', null);
INSERT INTO `t_area` VALUES ('15897', '451002', '451000', '右江区', null);
INSERT INTO `t_area` VALUES ('15898', '450321', '450300', '阳朔县', null);
INSERT INTO `t_area` VALUES ('15899', '451000', '450000', '百色市', null);
INSERT INTO `t_area` VALUES ('15900', '630000', '0', '青海省', null);
INSERT INTO `t_area` VALUES ('15901', '440000', '0', '广东省', null);
INSERT INTO `t_area` VALUES ('15902', '411221', '411200', '渑池县', null);
INSERT INTO `t_area` VALUES ('15903', '411222', '411200', '陕县', null);
INSERT INTO `t_area` VALUES ('15904', '450327', '450300', '灌阳县', null);
INSERT INTO `t_area` VALUES ('15905', '450326', '450300', '永福县', null);
INSERT INTO `t_area` VALUES ('15906', '450329', '450300', '资源县', null);
INSERT INTO `t_area` VALUES ('15907', '450328', '450300', '龙胜各族自治县', null);
INSERT INTO `t_area` VALUES ('15908', '442000', '440000', '中山市', null);
INSERT INTO `t_area` VALUES ('15909', '220623', '220600', '长白朝鲜族自治县', null);
INSERT INTO `t_area` VALUES ('15910', '220622', '220600', '靖宇县', null);
INSERT INTO `t_area` VALUES ('15911', '440825', '440800', '徐闻县', null);
INSERT INTO `t_area` VALUES ('15912', '220621', '220600', '抚松县', null);
INSERT INTO `t_area` VALUES ('15913', '440823', '440800', '遂溪县', null);
INSERT INTO `t_area` VALUES ('15914', '230302', '230300', '鸡冠区', null);
INSERT INTO `t_area` VALUES ('15915', '450332', '450300', '恭城瑶族自治县', null);
INSERT INTO `t_area` VALUES ('15916', '230303', '230300', '恒山区', null);
INSERT INTO `t_area` VALUES ('15917', '450331', '450300', '荔浦县', null);
INSERT INTO `t_area` VALUES ('15918', '230300', '230000', '鸡西市', null);
INSERT INTO `t_area` VALUES ('15919', '450330', '450300', '平乐县', null);
INSERT INTO `t_area` VALUES ('15920', '230306', '230300', '城子河区', null);
INSERT INTO `t_area` VALUES ('15921', '230307', '230300', '麻山区', null);
INSERT INTO `t_area` VALUES ('15922', '230304', '230300', '滴道区', null);
INSERT INTO `t_area` VALUES ('15923', '230305', '230300', '梨树区', null);
INSERT INTO `t_area` VALUES ('15924', '220625', '220600', '江源市', null);
INSERT INTO `t_area` VALUES ('15925', '411224', '411200', '卢氏县', null);
INSERT INTO `t_area` VALUES ('15926', '360729', '360700', '全南县', null);
INSERT INTO `t_area` VALUES ('15927', '130804', '130800', '鹰手营子矿区', null);
INSERT INTO `t_area` VALUES ('15928', '360728', '360700', '定南县', null);
INSERT INTO `t_area` VALUES ('15929', '130802', '130800', '双桥区', null);
INSERT INTO `t_area` VALUES ('15930', '130803', '130800', '双滦区', null);
INSERT INTO `t_area` VALUES ('15931', '130800', '130000', '承德市', null);
INSERT INTO `t_area` VALUES ('15932', '511381', '511300', '阆中市', null);
INSERT INTO `t_area` VALUES ('15933', '360721', '360700', '赣县', null);
INSERT INTO `t_area` VALUES ('15934', '620200', '620000', '嘉峪关市', null);
INSERT INTO `t_area` VALUES ('15935', '360722', '360700', '信丰县', null);
INSERT INTO `t_area` VALUES ('15936', '360723', '360700', '大余县', null);
INSERT INTO `t_area` VALUES ('15937', '360724', '360700', '上犹县', null);
INSERT INTO `t_area` VALUES ('15938', '360725', '360700', '崇义县', null);
INSERT INTO `t_area` VALUES ('15939', '360726', '360700', '安远县', null);
INSERT INTO `t_area` VALUES ('15940', '360727', '360700', '龙南县', null);
INSERT INTO `t_area` VALUES ('15941', '420381', '420300', '丹江口市', null);
INSERT INTO `t_area` VALUES ('15942', '420382', '420300', '城区', null);
INSERT INTO `t_area` VALUES ('15943', '370100', '370000', '济南市', null);
INSERT INTO `t_area` VALUES ('15944', '220602', '220600', '八道江区', null);
INSERT INTO `t_area` VALUES ('15945', '370102', '370100', '历下区', null);
INSERT INTO `t_area` VALUES ('15946', '520181', '520100', '清镇市', null);
INSERT INTO `t_area` VALUES ('15947', '632222', '632200', '祁连县', null);
INSERT INTO `t_area` VALUES ('15948', '370104', '370100', '槐荫区', null);
INSERT INTO `t_area` VALUES ('15949', '632221', '632200', '门源回族自治县', null);
INSERT INTO `t_area` VALUES ('15950', '370103', '370100', '市中区', null);
INSERT INTO `t_area` VALUES ('15951', '632224', '632200', '刚察县', null);
INSERT INTO `t_area` VALUES ('15952', '220600', '220000', '白山市', null);
INSERT INTO `t_area` VALUES ('15953', '632223', '632200', '海晏县', null);
INSERT INTO `t_area` VALUES ('15954', '370105', '370100', '天桥区', null);
INSERT INTO `t_area` VALUES ('15955', '542623', '542600', '米林县', null);
INSERT INTO `t_area` VALUES ('15956', '542624', '542600', '墨脱县', null);
INSERT INTO `t_area` VALUES ('15957', '542625', '542600', '波密县', null);
INSERT INTO `t_area` VALUES ('15958', '542626', '542600', '察隅县', null);
INSERT INTO `t_area` VALUES ('15959', '542621', '542600', '林芝县', null);
INSERT INTO `t_area` VALUES ('15960', '542622', '542600', '工布江达县', null);
INSERT INTO `t_area` VALUES ('15961', '130822', '130800', '兴隆县', null);
INSERT INTO `t_area` VALUES ('15962', '130823', '130800', '平泉县', null);
INSERT INTO `t_area` VALUES ('15963', '130821', '130800', '承德县', null);
INSERT INTO `t_area` VALUES ('15964', '130826', '130800', '丰宁满族自治县', null);
INSERT INTO `t_area` VALUES ('15965', '441581', '441500', '陆丰市', null);
INSERT INTO `t_area` VALUES ('15966', '542627', '542600', '朗县', null);
INSERT INTO `t_area` VALUES ('15967', '130827', '130800', '宽城满族自治县', null);
INSERT INTO `t_area` VALUES ('15968', '130824', '130800', '滦平县', null);
INSERT INTO `t_area` VALUES ('15969', '130825', '130800', '隆化县', null);
INSERT INTO `t_area` VALUES ('15970', '360702', '360700', '章贡区', null);
INSERT INTO `t_area` VALUES ('15971', '430525', '430500', '洞口县', null);
INSERT INTO `t_area` VALUES ('15972', '430524', '430500', '隆回县', null);
INSERT INTO `t_area` VALUES ('15973', '430523', '430500', '邵阳县', null);
INSERT INTO `t_area` VALUES ('15974', '130828', '130800', '围场满族蒙古族自治县', null);
INSERT INTO `t_area` VALUES ('15975', '430522', '430500', '新邵县', null);
INSERT INTO `t_area` VALUES ('15976', '370113', '370100', '长清区', null);
INSERT INTO `t_area` VALUES ('15977', '430521', '430500', '邵东县', null);
INSERT INTO `t_area` VALUES ('15978', '370112', '370100', '历城区', null);
INSERT INTO `t_area` VALUES ('15979', '360700', '360000', '赣州市', null);
INSERT INTO `t_area` VALUES ('15980', '430529', '430500', '城步苗族自治县', null);
INSERT INTO `t_area` VALUES ('15981', '430528', '430500', '新宁县', null);
INSERT INTO `t_area` VALUES ('15982', '430527', '430500', '绥宁县', null);
INSERT INTO `t_area` VALUES ('15983', '445224', '445200', '惠来县', null);
INSERT INTO `t_area` VALUES ('15984', '532532', '532500', '河口瑶族自治县', null);
INSERT INTO `t_area` VALUES ('15985', '445222', '445200', '揭西县', null);
INSERT INTO `t_area` VALUES ('15986', '445221', '445200', '揭东县', null);
INSERT INTO `t_area` VALUES ('15987', '370124', '370100', '平阴县', null);
INSERT INTO `t_area` VALUES ('15988', '370126', '370100', '商河县', null);
INSERT INTO `t_area` VALUES ('15989', '361000', '360000', '抚州市', null);
INSERT INTO `t_area` VALUES ('15990', '370125', '370100', '济阳县', null);
INSERT INTO `t_area` VALUES ('15991', '361002', '361000', '临川区', null);
INSERT INTO `t_area` VALUES ('15992', '532527', '532500', '泸西县', null);
INSERT INTO `t_area` VALUES ('15993', '532528', '532500', '元阳县', null);
INSERT INTO `t_area` VALUES ('15994', '532525', '532500', '石屏县', null);
INSERT INTO `t_area` VALUES ('15995', '410611', '410600', '淇滨区', null);
INSERT INTO `t_area` VALUES ('15996', '532526', '532500', '弥勒县', null);
INSERT INTO `t_area` VALUES ('15997', '532523', '532500', '屏边苗族自治县', null);
INSERT INTO `t_area` VALUES ('15998', '532524', '532500', '建水县', null);
INSERT INTO `t_area` VALUES ('15999', '532522', '532500', '蒙自县', null);
INSERT INTO `t_area` VALUES ('16000', '542600', '540000', '林芝地区', null);
INSERT INTO `t_area` VALUES ('16001', '532529', '532500', '红河县', null);
INSERT INTO `t_area` VALUES ('16002', '420325', '420300', '房县', null);
INSERT INTO `t_area` VALUES ('16003', '420321', '420300', '郧县', null);
INSERT INTO `t_area` VALUES ('16004', '420322', '420300', '郧西县', null);
INSERT INTO `t_area` VALUES ('16005', '420323', '420300', '竹山县', null);
INSERT INTO `t_area` VALUES ('16006', '420324', '420300', '竹溪县', null);
INSERT INTO `t_area` VALUES ('16007', '532531', '532500', '绿春县', null);
INSERT INTO `t_area` VALUES ('16008', '532530', '532500', '金平苗族瑶族傣族自治县', null);
INSERT INTO `t_area` VALUES ('16009', '512081', '512000', '简阳市', null);
INSERT INTO `t_area` VALUES ('16010', '520151', '520100', '金阳开发区', null);
INSERT INTO `t_area` VALUES ('16011', '410600', '410000', '鹤壁市', null);
INSERT INTO `t_area` VALUES ('16012', '510626', '510600', '罗江县', null);
INSERT INTO `t_area` VALUES ('16013', '445284', '445200', '东山区', null);
INSERT INTO `t_area` VALUES ('16014', '410603', '410600', '山城区', null);
INSERT INTO `t_area` VALUES ('16015', '410602', '410600', '鹤山区', null);
INSERT INTO `t_area` VALUES ('16016', '510623', '510600', '中江县', null);
INSERT INTO `t_area` VALUES ('16017', '361030', '361000', '广昌县', null);
INSERT INTO `t_area` VALUES ('16018', '445281', '445200', '普宁市', null);
INSERT INTO `t_area` VALUES ('16019', '361027', '361000', '金溪县', null);
INSERT INTO `t_area` VALUES ('16020', '361026', '361000', '宜黄县', null);
INSERT INTO `t_area` VALUES ('16021', '361025', '361000', '乐安县', null);
INSERT INTO `t_area` VALUES ('16022', '361024', '361000', '崇仁县', null);
INSERT INTO `t_area` VALUES ('16023', '361023', '361000', '南丰县', null);
INSERT INTO `t_area` VALUES ('16024', '361022', '361000', '黎川县', null);
INSERT INTO `t_area` VALUES ('16025', '360751', '360700', '黄金区', null);
INSERT INTO `t_area` VALUES ('16026', '361021', '361000', '南城县', null);
INSERT INTO `t_area` VALUES ('16027', '371100', '370000', '日照市', null);
INSERT INTO `t_area` VALUES ('16028', '440881', '440800', '廉江市', null);
INSERT INTO `t_area` VALUES ('16029', '371102', '371100', '东港区', null);
INSERT INTO `t_area` VALUES ('16030', '361029', '361000', '东乡县', null);
INSERT INTO `t_area` VALUES ('16031', '440882', '440800', '雷州市', null);
INSERT INTO `t_area` VALUES ('16032', '371103', '371100', '岚山区', null);
INSERT INTO `t_area` VALUES ('16033', '361028', '361000', '资溪县', null);
INSERT INTO `t_area` VALUES ('16034', '440883', '440800', '吴川市', null);
INSERT INTO `t_area` VALUES ('16035', '469006', '460000', '万宁市', null);
INSERT INTO `t_area` VALUES ('16036', '532501', '532500', '个旧市', null);
INSERT INTO `t_area` VALUES ('16037', '469005', '460000', '文昌市', null);
INSERT INTO `t_area` VALUES ('16038', '532502', '532500', '开远市', null);
INSERT INTO `t_area` VALUES ('16039', '469003', '460000', '儋州市', null);
INSERT INTO `t_area` VALUES ('16040', '532500', '530000', '红河哈尼族彝族自治州', null);
INSERT INTO `t_area` VALUES ('16041', '469002', '460000', '琼海市', null);
INSERT INTO `t_area` VALUES ('16042', '469001', '460000', '五指山市', null);
INSERT INTO `t_area` VALUES ('16043', '430581', '430500', '武冈市', null);
INSERT INTO `t_area` VALUES ('16044', '510600', '510000', '德阳市', null);
INSERT INTO `t_area` VALUES ('16045', '520114', '520100', '小河区', null);
INSERT INTO `t_area` VALUES ('16046', '520113', '520100', '白云区', null);
INSERT INTO `t_area` VALUES ('16047', '410621', '410600', '浚县', null);
INSERT INTO `t_area` VALUES ('16048', '410622', '410600', '淇县', null);
INSERT INTO `t_area` VALUES ('16049', '510603', '510600', '旌阳区', null);
INSERT INTO `t_area` VALUES ('16050', '360730', '360700', '宁都县', null);
INSERT INTO `t_area` VALUES ('16051', '632200', '630000', '海北藏族自治州', null);
INSERT INTO `t_area` VALUES ('16052', '371122', '371100', '莒县', null);
INSERT INTO `t_area` VALUES ('16053', '360735', '360700', '石城县', null);
INSERT INTO `t_area` VALUES ('16054', '360734', '360700', '寻乌县', null);
INSERT INTO `t_area` VALUES ('16055', '469007', '460000', '东方市', null);
INSERT INTO `t_area` VALUES ('16056', '520122', '520100', '息烽县', null);
INSERT INTO `t_area` VALUES ('16057', '360733', '360700', '会昌县', null);
INSERT INTO `t_area` VALUES ('16058', '520123', '520100', '修文县', null);
INSERT INTO `t_area` VALUES ('16059', '360732', '360700', '兴国县', null);
INSERT INTO `t_area` VALUES ('16060', '371121', '371100', '五莲县', null);
INSERT INTO `t_area` VALUES ('16061', '360731', '360700', '于都县', null);
INSERT INTO `t_area` VALUES ('16062', '520121', '520100', '开阳县', null);
INSERT INTO `t_area` VALUES ('16063', '410902', '410900', '华龙区', null);
INSERT INTO `t_area` VALUES ('16064', '410900', '410000', '濮阳市', null);
INSERT INTO `t_area` VALUES ('16065', '370321', '370300', '桓台县', null);
INSERT INTO `t_area` VALUES ('16066', '621022', '621000', '环县', null);
INSERT INTO `t_area` VALUES ('16067', '370322', '370300', '高青县', null);
INSERT INTO `t_area` VALUES ('16068', '621021', '621000', '庆城县', null);
INSERT INTO `t_area` VALUES ('16069', '621026', '621000', '宁县', null);
INSERT INTO `t_area` VALUES ('16070', '621025', '621000', '正宁县', null);
INSERT INTO `t_area` VALUES ('16071', '621024', '621000', '合水县', null);
INSERT INTO `t_area` VALUES ('16072', '410100', '410000', '郑州市', null);
INSERT INTO `t_area` VALUES ('16073', '621023', '621000', '华池县', null);
INSERT INTO `t_area` VALUES ('16074', '370323', '370300', '沂源县', null);
INSERT INTO `t_area` VALUES ('16075', '621027', '621000', '镇原县', null);
INSERT INTO `t_area` VALUES ('16076', '320000', '0', '江苏省', null);
INSERT INTO `t_area` VALUES ('16077', '130430', '130400', '邱县', null);
INSERT INTO `t_area` VALUES ('16078', '433101', '433100', '吉首市', null);
INSERT INTO `t_area` VALUES ('16079', '433100', '430000', '湘西土家族苗族自治州', null);
INSERT INTO `t_area` VALUES ('16080', '130434', '130400', '魏县', null);
INSERT INTO `t_area` VALUES ('16081', '130433', '130400', '馆陶县', null);
INSERT INTO `t_area` VALUES ('16082', '130432', '130400', '广平县', null);
INSERT INTO `t_area` VALUES ('16083', '130431', '130400', '鸡泽县', null);
INSERT INTO `t_area` VALUES ('16084', '130435', '130400', '曲周县', null);
INSERT INTO `t_area` VALUES ('16085', '321112', '321100', '丹徒区', null);
INSERT INTO `t_area` VALUES ('16086', '621002', '621000', '西峰区', null);
INSERT INTO `t_area` VALUES ('16087', '410122', '410100', '中牟县', null);
INSERT INTO `t_area` VALUES ('16088', '621000', '620000', '庆阳市', null);
INSERT INTO `t_area` VALUES ('16089', '370300', '370000', '淄博市', null);
INSERT INTO `t_area` VALUES ('16090', '370303', '370300', '张店区', null);
INSERT INTO `t_area` VALUES ('16091', '370304', '370300', '博山区', null);
INSERT INTO `t_area` VALUES ('16092', '370302', '370300', '淄川区', null);
INSERT INTO `t_area` VALUES ('16093', '370305', '370300', '临淄区', null);
INSERT INTO `t_area` VALUES ('16094', '370306', '370300', '周村区', null);
INSERT INTO `t_area` VALUES ('16095', '150721', '150700', '阿荣旗', null);
INSERT INTO `t_area` VALUES ('16096', '150722', '150700', '莫力达瓦达斡尔族自治旗', null);
INSERT INTO `t_area` VALUES ('16097', '150723', '150700', '鄂伦春自治旗', null);
INSERT INTO `t_area` VALUES ('16098', '150724', '150700', '鄂温克族自治旗', null);
INSERT INTO `t_area` VALUES ('16099', '150725', '150700', '陈巴尔虎旗', null);
INSERT INTO `t_area` VALUES ('16100', '150726', '150700', '新巴尔虎左旗', null);
INSERT INTO `t_area` VALUES ('16101', '140881', '140800', '永济市', null);
INSERT INTO `t_area` VALUES ('16102', '410108', '410100', '惠济区', null);
INSERT INTO `t_area` VALUES ('16103', '140882', '140800', '河津市', null);
INSERT INTO `t_area` VALUES ('16104', '410106', '410100', '上街区', null);
INSERT INTO `t_area` VALUES ('16105', '410104', '410100', '管城回族区', null);
INSERT INTO `t_area` VALUES ('16106', '410105', '410100', '金水区', null);
INSERT INTO `t_area` VALUES ('16107', '321111', '321100', '润州区', null);
INSERT INTO `t_area` VALUES ('16108', '410102', '410100', '中原区', null);
INSERT INTO `t_area` VALUES ('16109', '410103', '410100', '二七区', null);
INSERT INTO `t_area` VALUES ('16110', '321102', '321100', '京口区', null);
INSERT INTO `t_area` VALUES ('16111', '330824', '330800', '开化县', null);
INSERT INTO `t_area` VALUES ('16112', '320800', '320000', '淮安市', null);
INSERT INTO `t_area` VALUES ('16113', '330825', '330800', '龙游县', null);
INSERT INTO `t_area` VALUES ('16114', '330822', '330800', '常山县', null);
INSERT INTO `t_area` VALUES ('16115', '320804', '320800', '淮阴区', null);
INSERT INTO `t_area` VALUES ('16116', '320803', '320800', '楚州区', null);
INSERT INTO `t_area` VALUES ('16117', '150727', '150700', '新巴尔虎右旗', null);
INSERT INTO `t_area` VALUES ('16118', '320802', '320800', '清河区', null);
INSERT INTO `t_area` VALUES ('16119', '321183', '321100', '句容市', null);
INSERT INTO `t_area` VALUES ('16120', '530821', '530800', '宁洱哈尼族彝族自治县', null);
INSERT INTO `t_area` VALUES ('16121', '321181', '321100', '丹阳市', null);
INSERT INTO `t_area` VALUES ('16122', '530823', '530800', '景东彝族自治县', null);
INSERT INTO `t_area` VALUES ('16123', '321182', '321100', '扬中市', null);
INSERT INTO `t_area` VALUES ('16124', '530822', '530800', '墨江哈尼族自治县', null);
INSERT INTO `t_area` VALUES ('16125', '530825', '530800', '镇沅彝族哈尼族拉祜族自治县', null);
INSERT INTO `t_area` VALUES ('16126', '530824', '530800', '景谷傣族彝族自治县', null);
INSERT INTO `t_area` VALUES ('16127', '530827', '530800', '孟连傣族拉祜族佤族自治县', null);
INSERT INTO `t_area` VALUES ('16128', '530826', '530800', '江城哈尼族彝族自治县', null);
INSERT INTO `t_area` VALUES ('16129', '530829', '530800', '西盟佤族自治县', null);
INSERT INTO `t_area` VALUES ('16130', '530828', '530800', '澜沧拉祜族自治县', null);
INSERT INTO `t_area` VALUES ('16131', '420981', '420900', '应城市', null);
INSERT INTO `t_area` VALUES ('16132', '420982', '420900', '安陆市', null);
INSERT INTO `t_area` VALUES ('16133', '420984', '420900', '汉川市', null);
INSERT INTO `t_area` VALUES ('16134', '530000', '0', '云南省', null);
INSERT INTO `t_area` VALUES ('16135', '130428', '130400', '肥乡县', null);
INSERT INTO `t_area` VALUES ('16136', '130429', '130400', '永年县', null);
INSERT INTO `t_area` VALUES ('16137', '130424', '130400', '成安县', null);
INSERT INTO `t_area` VALUES ('16138', '130425', '130400', '大名县', null);
INSERT INTO `t_area` VALUES ('16139', '130426', '130400', '涉县', null);
INSERT INTO `t_area` VALUES ('16140', '130427', '130400', '磁县', null);
INSERT INTO `t_area` VALUES ('16141', '150702', '150700', '海拉尔区', null);
INSERT INTO `t_area` VALUES ('16142', '130421', '130400', '邯郸县', null);
INSERT INTO `t_area` VALUES ('16143', '130423', '130400', '临漳县', null);
INSERT INTO `t_area` VALUES ('16144', '150700', '150000', '呼伦贝尔市', null);
INSERT INTO `t_area` VALUES ('16145', '330803', '330800', '衢江区', null);
INSERT INTO `t_area` VALUES ('16146', '330802', '330800', '柯城区', null);
INSERT INTO `t_area` VALUES ('16147', '330800', '330000', '衢州市', null);
INSERT INTO `t_area` VALUES ('16148', '530800', '530000', '普洱市', null);
INSERT INTO `t_area` VALUES ('16149', '530802', '530800', '思茅区', null);
INSERT INTO `t_area` VALUES ('16150', '140830', '140800', '芮城县', null);
INSERT INTO `t_area` VALUES ('16151', '441400', '440000', '梅州市', null);
INSERT INTO `t_area` VALUES ('16152', '441402', '441400', '梅江区', null);
INSERT INTO `t_area` VALUES ('16153', '130402', '130400', '邯山区', null);
INSERT INTO `t_area` VALUES ('16154', '130403', '130400', '丛台区', null);
INSERT INTO `t_area` VALUES ('16155', '130404', '130400', '复兴区', null);
INSERT INTO `t_area` VALUES ('16156', '130406', '130400', '峰峰矿区', null);
INSERT INTO `t_area` VALUES ('16157', '130400', '130000', '邯郸市', null);
INSERT INTO `t_area` VALUES ('16158', '110000', '0', '北京', null);
INSERT INTO `t_area` VALUES ('16159', '350111', '350100', '晋安区', null);
INSERT INTO `t_area` VALUES ('16160', '511500', '510000', '宜宾市', null);
INSERT INTO `t_area` VALUES ('16161', '511502', '511500', '翠屏区', null);
INSERT INTO `t_area` VALUES ('16162', '431023', '431000', '永兴县', null);
INSERT INTO `t_area` VALUES ('16163', '431022', '431000', '宜章县', null);
INSERT INTO `t_area` VALUES ('16164', '431021', '431000', '桂阳县', null);
INSERT INTO `t_area` VALUES ('16165', '431024', '431000', '嘉禾县', null);
INSERT INTO `t_area` VALUES ('16166', '431025', '431000', '临武县', null);
INSERT INTO `t_area` VALUES ('16167', '431026', '431000', '汝城县', null);
INSERT INTO `t_area` VALUES ('16168', '431027', '431000', '桂东县', null);
INSERT INTO `t_area` VALUES ('16169', '350128', '350100', '平潭县', null);
INSERT INTO `t_area` VALUES ('16170', '431028', '431000', '安仁县', null);
INSERT INTO `t_area` VALUES ('16171', '350124', '350100', '闽清县', null);
INSERT INTO `t_area` VALUES ('16172', '350123', '350100', '罗源县', null);
INSERT INTO `t_area` VALUES ('16173', '140829', '140800', '平陆县', null);
INSERT INTO `t_area` VALUES ('16174', '511521', '511500', '宜宾县', null);
INSERT INTO `t_area` VALUES ('16175', '140828', '140800', '夏县', null);
INSERT INTO `t_area` VALUES ('16176', '350125', '350100', '永泰县', null);
INSERT INTO `t_area` VALUES ('16177', '140827', '140800', '垣曲县', null);
INSERT INTO `t_area` VALUES ('16178', '350122', '350100', '连江县', null);
INSERT INTO `t_area` VALUES ('16179', '350121', '350100', '闽侯县', null);
INSERT INTO `t_area` VALUES ('16180', '140821', '140800', '临猗县', null);
INSERT INTO `t_area` VALUES ('16181', '610700', '610000', '汉中市', null);
INSERT INTO `t_area` VALUES ('16182', '140822', '140800', '万荣县', null);
INSERT INTO `t_area` VALUES ('16183', '140825', '140800', '新绛县', null);
INSERT INTO `t_area` VALUES ('16184', '140826', '140800', '绛县', null);
INSERT INTO `t_area` VALUES ('16185', '140823', '140800', '闻喜县', null);
INSERT INTO `t_area` VALUES ('16186', '610702', '610700', '汉台区', null);
INSERT INTO `t_area` VALUES ('16187', '140824', '140800', '稷山县', null);
INSERT INTO `t_area` VALUES ('16188', '350105', '350100', '马尾区', null);
INSERT INTO `t_area` VALUES ('16189', '440300', '440000', '深圳市', null);
INSERT INTO `t_area` VALUES ('16190', '440303', '440300', '罗湖区', null);
INSERT INTO `t_area` VALUES ('16191', '440304', '440300', '福田区', null);
INSERT INTO `t_area` VALUES ('16192', '440307', '440300', '龙岗区', null);
INSERT INTO `t_area` VALUES ('16193', '610730', '610700', '佛坪县', null);
INSERT INTO `t_area` VALUES ('16194', '440308', '440300', '盐田区', null);
INSERT INTO `t_area` VALUES ('16195', '350100', '350000', '福州市', null);
INSERT INTO `t_area` VALUES ('16196', '440305', '440300', '南山区', null);
INSERT INTO `t_area` VALUES ('16197', '440306', '440300', '宝安区', null);
INSERT INTO `t_area` VALUES ('16198', '350102', '350100', '鼓楼区', null);
INSERT INTO `t_area` VALUES ('16199', '350104', '350100', '仓山区', null);
INSERT INTO `t_area` VALUES ('16200', '350103', '350100', '台江区', null);
INSERT INTO `t_area` VALUES ('16201', '610726', '610700', '宁强县', null);
INSERT INTO `t_area` VALUES ('16202', '610727', '610700', '略阳县', null);
INSERT INTO `t_area` VALUES ('16203', '330382', '330300', '乐清市', null);
INSERT INTO `t_area` VALUES ('16204', '610724', '610700', '西乡县', null);
INSERT INTO `t_area` VALUES ('16205', '330381', '330300', '瑞安市', null);
INSERT INTO `t_area` VALUES ('16206', '140802', '140800', '盐湖区', null);
INSERT INTO `t_area` VALUES ('16207', '610725', '610700', '勉县', null);
INSERT INTO `t_area` VALUES ('16208', '610722', '610700', '城固县', null);
INSERT INTO `t_area` VALUES ('16209', '140800', '140000', '运城市', null);
INSERT INTO `t_area` VALUES ('16210', '610723', '610700', '洋县', null);
INSERT INTO `t_area` VALUES ('16211', '610721', '610700', '南郑县', null);
INSERT INTO `t_area` VALUES ('16212', '610728', '610700', '镇巴县', null);
INSERT INTO `t_area` VALUES ('16213', '610729', '610700', '留坝县', null);
INSERT INTO `t_area` VALUES ('16214', '320811', '320800', '清浦区', null);
INSERT INTO `t_area` VALUES ('16215', '340523', '340500', '含山县', null);
INSERT INTO `t_area` VALUES ('16216', '340521', '340500', '当涂县', null);
INSERT INTO `t_area` VALUES ('16217', '321100', '320000', '镇江市', null);
INSERT INTO `t_area` VALUES ('16218', '340524', '340500', '和县', null);
INSERT INTO `t_area` VALUES ('16219', '330881', '330800', '江山市', null);
INSERT INTO `t_area` VALUES ('16220', '320829', '320800', '洪泽县', null);
INSERT INTO `t_area` VALUES ('16221', '320826', '320800', '涟水县', null);
INSERT INTO `t_area` VALUES ('16222', '150784', '150700', '额尔古纳市', null);
INSERT INTO `t_area` VALUES ('16223', '150783', '150700', '扎兰屯市', null);
INSERT INTO `t_area` VALUES ('16224', '150782', '150700', '牙克石市', null);
INSERT INTO `t_area` VALUES ('16225', '150781', '150700', '满洲里市', null);
INSERT INTO `t_area` VALUES ('16226', '150785', '150700', '根河市', null);
INSERT INTO `t_area` VALUES ('16227', '130481', '130400', '武安市', null);
INSERT INTO `t_area` VALUES ('16228', '320830', '320800', '盱眙县', null);
INSERT INTO `t_area` VALUES ('16229', '320831', '320800', '金湖县', null);
INSERT INTO `t_area` VALUES ('16230', '431000', '430000', '郴州市', null);
INSERT INTO `t_area` VALUES ('16231', '411202', '411200', '湖滨区', null);
INSERT INTO `t_area` VALUES ('16232', '340500', '340000', '马鞍山市', null);
INSERT INTO `t_area` VALUES ('16233', '340504', '340500', '雨山区', null);
INSERT INTO `t_area` VALUES ('16234', '340503', '340500', '花山区', null);
INSERT INTO `t_area` VALUES ('16235', '340502', '340500', '金家庄区', null);
INSERT INTO `t_area` VALUES ('16236', '411200', '410000', '三门峡市', null);
INSERT INTO `t_area` VALUES ('16237', '431003', '431000', '苏仙区', null);
INSERT INTO `t_area` VALUES ('16238', '431002', '431000', '北湖区', null);
INSERT INTO `t_area` VALUES ('16239', '500115', '500100', '长寿区', null);
INSERT INTO `t_area` VALUES ('16240', '411100', '410000', '漯河市', null);
INSERT INTO `t_area` VALUES ('16241', '411102', '411100', '源汇区', null);
INSERT INTO `t_area` VALUES ('16242', '500122', '500100', '綦江县', null);
INSERT INTO `t_area` VALUES ('16243', '500123', '500100', '潼南县', null);
INSERT INTO `t_area` VALUES ('16244', '500124', '500100', '铜梁县', null);
INSERT INTO `t_area` VALUES ('16245', '500125', '500100', '大足县', null);
INSERT INTO `t_area` VALUES ('16246', '632802', '632800', '德令哈市', null);
INSERT INTO `t_area` VALUES ('16247', '632801', '632800', '格尔木市', null);
INSERT INTO `t_area` VALUES ('16248', '632800', '630000', '海西蒙古族藏族自治州', null);
INSERT INTO `t_area` VALUES ('16249', '500129', '500100', '城口县', null);
INSERT INTO `t_area` VALUES ('16250', '500128', '500100', '梁平县', null);
INSERT INTO `t_area` VALUES ('16251', '500127', '500100', '璧山县', null);
INSERT INTO `t_area` VALUES ('16252', '500126', '500100', '荣昌县', null);
INSERT INTO `t_area` VALUES ('16253', '310000', '0', '上海', null);
INSERT INTO `t_area` VALUES ('16254', '411104', '411100', '召陵区', null);
INSERT INTO `t_area` VALUES ('16255', '411103', '411100', '郾城区', null);
INSERT INTO `t_area` VALUES ('16256', '330329', '330300', '泰顺县', null);
INSERT INTO `t_area` VALUES ('16257', '330327', '330300', '苍南县', null);
INSERT INTO `t_area` VALUES ('16258', '330328', '330300', '文成县', null);
INSERT INTO `t_area` VALUES ('16259', '500135', '500100', '云阳县', null);
INSERT INTO `t_area` VALUES ('16260', '330326', '330300', '平阳县', null);
INSERT INTO `t_area` VALUES ('16261', '500136', '500100', '奉节县', null);
INSERT INTO `t_area` VALUES ('16262', '220500', '220500', '集安市', null);
INSERT INTO `t_area` VALUES ('16263', '500133', '500100', '忠县', null);
INSERT INTO `t_area` VALUES ('16264', '330324', '330300', '永嘉县', null);
INSERT INTO `t_area` VALUES ('16265', '500134', '500100', '开县', null);
INSERT INTO `t_area` VALUES ('16266', '350982', '350900', '福鼎市', null);
INSERT INTO `t_area` VALUES ('16267', '500131', '500100', '垫江县', null);
INSERT INTO `t_area` VALUES ('16268', '350981', '350900', '福安市', null);
INSERT INTO `t_area` VALUES ('16269', '330322', '330300', '洞头县', null);
INSERT INTO `t_area` VALUES ('16270', '500132', '500100', '武隆县', null);
INSERT INTO `t_area` VALUES ('16271', '230421', '230400', '萝北县', null);
INSERT INTO `t_area` VALUES ('16272', '230422', '230400', '绥滨县', null);
INSERT INTO `t_area` VALUES ('16273', '500130', '500100', '丰都县', null);
INSERT INTO `t_area` VALUES ('16274', '500138', '500100', '巫溪县', null);
INSERT INTO `t_area` VALUES ('16275', '500137', '500100', '巫山县', null);
INSERT INTO `t_area` VALUES ('16276', '371082', '371000', '荣成市', null);
INSERT INTO `t_area` VALUES ('16277', '371081', '371000', '文登市', null);
INSERT INTO `t_area` VALUES ('16278', '371083', '371000', '乳山市', null);
INSERT INTO `t_area` VALUES ('16279', '411121', '411100', '舞阳县', null);
INSERT INTO `t_area` VALUES ('16280', '411122', '411100', '临颍县', null);
INSERT INTO `t_area` VALUES ('16281', '500140', '500100', '石柱土家族自治县', null);
INSERT INTO `t_area` VALUES ('16282', '500141', '500100', '秀山土家族苗族自治县', null);
INSERT INTO `t_area` VALUES ('16283', '500142', '500100', '酉阳土家族苗族自治县', null);
INSERT INTO `t_area` VALUES ('16284', '500143', '500100', '彭水苗族土家族自治县', null);
INSERT INTO `t_area` VALUES ('16285', '632822', '632800', '都兰县', null);
INSERT INTO `t_area` VALUES ('16286', '350181', '350100', '福清市', null);
INSERT INTO `t_area` VALUES ('16287', '632821', '632800', '乌兰县', null);
INSERT INTO `t_area` VALUES ('16288', '350182', '350100', '长乐市', null);
INSERT INTO `t_area` VALUES ('16289', '450481', '450400', '岑溪市', null);
INSERT INTO `t_area` VALUES ('16290', '632823', '632800', '天峻县', null);
INSERT INTO `t_area` VALUES ('16291', '330300', '330000', '温州市', null);
INSERT INTO `t_area` VALUES ('16292', '330303', '330300', '龙湾区', null);
INSERT INTO `t_area` VALUES ('16293', '330304', '330300', '瓯海区', null);
INSERT INTO `t_area` VALUES ('16294', '330302', '330300', '鹿城区', null);
INSERT INTO `t_area` VALUES ('16295', '620821', '620800', '泾川县', null);
INSERT INTO `t_area` VALUES ('16296', '620822', '620800', '灵台县', null);
INSERT INTO `t_area` VALUES ('16297', '610629', '610600', '洛川县', null);
INSERT INTO `t_area` VALUES ('16298', '620823', '620800', '崇信县', null);
INSERT INTO `t_area` VALUES ('16299', '620824', '620800', '华亭县', null);
INSERT INTO `t_area` VALUES ('16300', '620825', '620800', '庄浪县', null);
INSERT INTO `t_area` VALUES ('16301', '231081', '231000', '绥芬河市', null);
INSERT INTO `t_area` VALUES ('16302', '610626', '610600', '吴起县', null);
INSERT INTO `t_area` VALUES ('16303', '620826', '620800', '静宁县', null);
INSERT INTO `t_area` VALUES ('16304', '610625', '610600', '志丹县', null);
INSERT INTO `t_area` VALUES ('16305', '231083', '231000', '海林市', null);
INSERT INTO `t_area` VALUES ('16306', '610628', '610600', '富县', null);
INSERT INTO `t_area` VALUES ('16307', '610627', '610600', '甘泉县', null);
INSERT INTO `t_area` VALUES ('16308', '231085', '231000', '穆棱市', null);
INSERT INTO `t_area` VALUES ('16309', '610622', '610600', '延川县', null);
INSERT INTO `t_area` VALUES ('16310', '231084', '231000', '宁安市', null);
INSERT INTO `t_area` VALUES ('16311', '610621', '610600', '延长县', null);
INSERT INTO `t_area` VALUES ('16312', '610624', '610600', '安塞县', null);
INSERT INTO `t_area` VALUES ('16313', '610623', '610600', '子长县', null);
INSERT INTO `t_area` VALUES ('16314', '450400', '450000', '梧州市', null);
INSERT INTO `t_area` VALUES ('16315', '610630', '610600', '宜川县', null);
INSERT INTO `t_area` VALUES ('16316', '610631', '610600', '黄龙县', null);
INSERT INTO `t_area` VALUES ('16317', '440320', '440300', '光明新区', null);
INSERT INTO `t_area` VALUES ('16318', '440322', '440300', '大鹏新区', null);
INSERT INTO `t_area` VALUES ('16319', '440321', '440300', '坪山新区', null);
INSERT INTO `t_area` VALUES ('16320', '450403', '450400', '万秀区', null);
INSERT INTO `t_area` VALUES ('16321', '440323', '440300', '龙华新区', null);
INSERT INTO `t_area` VALUES ('16322', '450404', '450400', '蝶山区', null);
INSERT INTO `t_area` VALUES ('16323', '450405', '450400', '长洲区', null);
INSERT INTO `t_area` VALUES ('16324', '511425', '511400', '青神县', null);
INSERT INTO `t_area` VALUES ('16325', '511423', '511400', '洪雅县', null);
INSERT INTO `t_area` VALUES ('16326', '511424', '511400', '丹棱县', null);
INSERT INTO `t_area` VALUES ('16327', '610632', '610600', '黄陵县', null);
INSERT INTO `t_area` VALUES ('16328', '210521', '210500', '本溪满族自治县', null);
INSERT INTO `t_area` VALUES ('16329', '210522', '210500', '桓仁满族自治县', null);
INSERT INTO `t_area` VALUES ('16330', '500184', '500100', '南川区', null);
INSERT INTO `t_area` VALUES ('16331', '511422', '511400', '彭山县', null);
INSERT INTO `t_area` VALUES ('16332', '511421', '511400', '仁寿县', null);
INSERT INTO `t_area` VALUES ('16333', '450421', '450400', '苍梧县', null);
INSERT INTO `t_area` VALUES ('16334', '450422', '450400', '藤县', null);
INSERT INTO `t_area` VALUES ('16335', '450423', '450400', '蒙山县', null);
INSERT INTO `t_area` VALUES ('16336', '500181', '500100', '江津区', null);
INSERT INTO `t_area` VALUES ('16337', '500183', '500100', '永川区', null);
INSERT INTO `t_area` VALUES ('16338', '500182', '500100', '合川区', null);
INSERT INTO `t_area` VALUES ('16339', '430281', '430200', '醴陵市', null);
INSERT INTO `t_area` VALUES ('16340', '511402', '511400', '东坡区', null);
INSERT INTO `t_area` VALUES ('16341', '360000', '0', '江西省', null);
INSERT INTO `t_area` VALUES ('16342', '210505', '210500', '南芬区', null);
INSERT INTO `t_area` VALUES ('16343', '210504', '210500', '明山区', null);
INSERT INTO `t_area` VALUES ('16344', '210503', '210500', '溪湖区', null);
INSERT INTO `t_area` VALUES ('16345', '210502', '210500', '平山区', null);
INSERT INTO `t_area` VALUES ('16346', '210500', '210000', '本溪市', null);
INSERT INTO `t_area` VALUES ('16347', '430203', '430200', '芦淞区', null);
INSERT INTO `t_area` VALUES ('16348', '430204', '430200', '石峰区', null);
INSERT INTO `t_area` VALUES ('16349', '430202', '430200', '荷塘区', null);
INSERT INTO `t_area` VALUES ('16350', '430200', '430000', '株洲市', null);
INSERT INTO `t_area` VALUES ('16351', '152923', '152900', '额济纳旗', null);
INSERT INTO `t_area` VALUES ('16352', '321092', '321000', '经济开发区', null);
INSERT INTO `t_area` VALUES ('16353', '130500', '130000', '邢台市', null);
INSERT INTO `t_area` VALUES ('16354', '152921', '152900', '阿拉善左旗', null);
INSERT INTO `t_area` VALUES ('16355', '152922', '152900', '阿拉善右旗', null);
INSERT INTO `t_area` VALUES ('16356', '130503', '130500', '桥西区', null);
INSERT INTO `t_area` VALUES ('16357', '130502', '130500', '桥东区', null);
INSERT INTO `t_area` VALUES ('16358', '370281', '370200', '胶州市', null);
INSERT INTO `t_area` VALUES ('16359', '370282', '370200', '即墨市', null);
INSERT INTO `t_area` VALUES ('16360', '441422', '441400', '大埔县', null);
INSERT INTO `t_area` VALUES ('16361', '371002', '371000', '环翠区', null);
INSERT INTO `t_area` VALUES ('16362', '370283', '370200', '平度市', null);
INSERT INTO `t_area` VALUES ('16363', '441421', '441400', '梅县', null);
INSERT INTO `t_area` VALUES ('16364', '370284', '370200', '胶南市', null);
INSERT INTO `t_area` VALUES ('16365', '370285', '370200', '莱西市', null);
INSERT INTO `t_area` VALUES ('16366', '441426', '441400', '平远县', null);
INSERT INTO `t_area` VALUES ('16367', '430211', '430200', '天元区', null);
INSERT INTO `t_area` VALUES ('16368', '441424', '441400', '五华县', null);
INSERT INTO `t_area` VALUES ('16369', '371000', '370000', '威海市', null);
INSERT INTO `t_area` VALUES ('16370', '441423', '441400', '丰顺县', null);
INSERT INTO `t_area` VALUES ('16371', '441427', '441400', '蕉岭县', null);
INSERT INTO `t_area` VALUES ('16372', '420000', '0', '湖北省', null);
INSERT INTO `t_area` VALUES ('16373', '430223', '430200', '攸县', null);
INSERT INTO `t_area` VALUES ('16374', '430224', '430200', '茶陵县', null);
INSERT INTO `t_area` VALUES ('16375', '430221', '430200', '株洲县', null);
INSERT INTO `t_area` VALUES ('16376', '370251', '370200', '开发区', null);
INSERT INTO `t_area` VALUES ('16377', '430225', '430200', '炎陵县', null);
INSERT INTO `t_area` VALUES ('16378', '152900', '150000', '阿拉善盟', null);
INSERT INTO `t_area` VALUES ('16379', '620802', '620800', '崆峒区', null);
INSERT INTO `t_area` VALUES ('16380', '620800', '620000', '平凉市', null);
INSERT INTO `t_area` VALUES ('16381', '420923', '420900', '云梦县', null);
INSERT INTO `t_area` VALUES ('16382', '420922', '420900', '大悟县', null);
INSERT INTO `t_area` VALUES ('16383', '420921', '420900', '孝昌县', null);
INSERT INTO `t_area` VALUES ('16384', '321081', '321000', '仪征市', null);
INSERT INTO `t_area` VALUES ('16385', '510503', '510500', '纳溪区', null);
INSERT INTO `t_area` VALUES ('16386', '510504', '510500', '龙马潭区', null);
INSERT INTO `t_area` VALUES ('16387', '321088', '321000', '江都市', null);
INSERT INTO `t_area` VALUES ('16388', '510500', '510000', '泸州市', null);
INSERT INTO `t_area` VALUES ('16389', '321084', '321000', '高邮市', null);
INSERT INTO `t_area` VALUES ('16390', '510502', '510500', '江阳区', null);
INSERT INTO `t_area` VALUES ('16391', '410004', '410300', '高新区', null);
INSERT INTO `t_area` VALUES ('16392', '542500', '540000', '阿里地区', null);
INSERT INTO `t_area` VALUES ('16393', '231000', '230000', '牡丹江市', null);
INSERT INTO `t_area` VALUES ('16394', '231002', '231000', '东安区', null);
INSERT INTO `t_area` VALUES ('16395', '231003', '231000', '阳明区', null);
INSERT INTO `t_area` VALUES ('16396', '231004', '231000', '爱民区', null);
INSERT INTO `t_area` VALUES ('16397', '231005', '231000', '西安区', null);
INSERT INTO `t_area` VALUES ('16398', '420900', '420000', '孝感市', null);
INSERT INTO `t_area` VALUES ('16399', '420902', '420900', '孝南区', null);
INSERT INTO `t_area` VALUES ('16400', '510522', '510500', '合江县', null);
INSERT INTO `t_area` VALUES ('16401', '510521', '510500', '泸县', null);
INSERT INTO `t_area` VALUES ('16402', '510524', '510500', '叙永县', null);
INSERT INTO `t_area` VALUES ('16403', '510525', '510500', '古蔺县', null);
INSERT INTO `t_area` VALUES ('16404', '500103', '500100', '渝中区', null);
INSERT INTO `t_area` VALUES ('16405', '500102', '500100', '涪陵区', null);
INSERT INTO `t_area` VALUES ('16406', '370214', '370200', '城阳区', null);
INSERT INTO `t_area` VALUES ('16407', '500101', '500100', '万州区', null);
INSERT INTO `t_area` VALUES ('16408', '370213', '370200', '李沧区', null);
INSERT INTO `t_area` VALUES ('16409', '500100', '500000', '重庆市', null);
INSERT INTO `t_area` VALUES ('16410', '370212', '370200', '崂山区', null);
INSERT INTO `t_area` VALUES ('16411', '370211', '370200', '黄岛区', null);
INSERT INTO `t_area` VALUES ('16412', '441481', '441400', '兴宁市', null);
INSERT INTO `t_area` VALUES ('16413', '542521', '542500', '普兰县', null);
INSERT INTO `t_area` VALUES ('16414', '542522', '542500', '札达县', null);
INSERT INTO `t_area` VALUES ('16415', '542523', '542500', '噶尔县', null);
INSERT INTO `t_area` VALUES ('16416', '542524', '542500', '日土县', null);
INSERT INTO `t_area` VALUES ('16417', '542525', '542500', '革吉县', null);
INSERT INTO `t_area` VALUES ('16418', '542526', '542500', '改则县', null);
INSERT INTO `t_area` VALUES ('16419', '542527', '542500', '措勤县', null);
INSERT INTO `t_area` VALUES ('16420', '230400', '230000', '鹤岗市', null);
INSERT INTO `t_area` VALUES ('16421', '230402', '230400', '向阳区', null);
INSERT INTO `t_area` VALUES ('16422', '500110', '500100', '万盛区', null);
INSERT INTO `t_area` VALUES ('16423', '500112', '500100', '渝北区', null);
INSERT INTO `t_area` VALUES ('16424', '500111', '500100', '双桥区', null);
INSERT INTO `t_area` VALUES ('16425', '231024', '231000', '东宁县', null);
INSERT INTO `t_area` VALUES ('16426', '500114', '500100', '黔江区', null);
INSERT INTO `t_area` VALUES ('16427', '231025', '231000', '林口县', null);
INSERT INTO `t_area` VALUES ('16428', '500113', '500100', '巴南区', null);
INSERT INTO `t_area` VALUES ('16429', '230407', '230400', '兴山区', null);
INSERT INTO `t_area` VALUES ('16430', '230404', '230400', '南山区', null);
INSERT INTO `t_area` VALUES ('16431', '230403', '230400', '工农区', null);
INSERT INTO `t_area` VALUES ('16432', '230406', '230400', '东山区', null);
INSERT INTO `t_area` VALUES ('16433', '230405', '230400', '兴安区', null);
INSERT INTO `t_area` VALUES ('16434', '410923', '410900', '南乐县', null);
INSERT INTO `t_area` VALUES ('16435', '410922', '410900', '清丰县', null);
INSERT INTO `t_area` VALUES ('16436', '410927', '410900', '台前县', null);
INSERT INTO `t_area` VALUES ('16437', '500104', '500100', '大渡口区', null);
INSERT INTO `t_area` VALUES ('16438', '410926', '410900', '范县', null);
INSERT INTO `t_area` VALUES ('16439', '500105', '500100', '江北区', null);
INSERT INTO `t_area` VALUES ('16440', '500106', '500100', '沙坪坝区', null);
INSERT INTO `t_area` VALUES ('16441', '500107', '500100', '九龙坡区', null);
INSERT INTO `t_area` VALUES ('16442', '500108', '500100', '南岸区', null);
INSERT INTO `t_area` VALUES ('16443', '500109', '500100', '北碚区', null);
INSERT INTO `t_area` VALUES ('16444', '410928', '410900', '濮阳县', null);
INSERT INTO `t_area` VALUES ('16445', '340421', '340400', '凤台县', null);
INSERT INTO `t_area` VALUES ('16446', '410000', '0', '河南省', null);
INSERT INTO `t_area` VALUES ('16447', '621125', '621100', '漳县', null);
INSERT INTO `t_area` VALUES ('16448', '320100', '320000', '南京市', null);
INSERT INTO `t_area` VALUES ('16449', '621124', '621100', '临洮县', null);
INSERT INTO `t_area` VALUES ('16450', '320103', '320100', '白下区', null);
INSERT INTO `t_area` VALUES ('16451', '530100', '530000', '昆明市', null);
INSERT INTO `t_area` VALUES ('16452', '621123', '621100', '渭源县', null);
INSERT INTO `t_area` VALUES ('16453', '320102', '320100', '玄武区', null);
INSERT INTO `t_area` VALUES ('16454', '621122', '621100', '陇西县', null);
INSERT INTO `t_area` VALUES ('16455', '320105', '320100', '建邺区', null);
INSERT INTO `t_area` VALUES ('16456', '621121', '621100', '通渭县', null);
INSERT INTO `t_area` VALUES ('16457', '320104', '320100', '秦淮区', null);
INSERT INTO `t_area` VALUES ('16458', '320107', '320100', '下关区', null);
INSERT INTO `t_area` VALUES ('16459', '320106', '320100', '鼓楼区', null);
INSERT INTO `t_area` VALUES ('16460', '520424', '520400', '关岭布依族苗族自治县', null);
INSERT INTO `t_area` VALUES ('16461', '520423', '520400', '镇宁布依族苗族自治县', null);
INSERT INTO `t_area` VALUES ('16462', '520422', '520400', '普定县', null);
INSERT INTO `t_area` VALUES ('16463', '520421', '520400', '平坝县', null);
INSERT INTO `t_area` VALUES ('16464', '530103', '530100', '盘龙区', null);
INSERT INTO `t_area` VALUES ('16465', '621126', '621100', '岷县', null);
INSERT INTO `t_area` VALUES ('16466', '520425', '520400', '紫云苗族布依族自治县', null);
INSERT INTO `t_area` VALUES ('16467', '530102', '530100', '五华区', null);
INSERT INTO `t_area` VALUES ('16468', '320925', '320900', '建湖县', null);
INSERT INTO `t_area` VALUES ('16469', '320924', '320900', '射阳县', null);
INSERT INTO `t_area` VALUES ('16470', '320923', '320900', '阜宁县', null);
INSERT INTO `t_area` VALUES ('16471', '320922', '320900', '滨海县', null);
INSERT INTO `t_area` VALUES ('16472', '530111', '530100', '官渡区', null);
INSERT INTO `t_area` VALUES ('16473', '141081', '141000', '侯马市', null);
INSERT INTO `t_area` VALUES ('16474', '320921', '320900', '响水县', null);
INSERT INTO `t_area` VALUES ('16475', '141082', '141000', '霍州市', null);
INSERT INTO `t_area` VALUES ('16476', '150981', '150900', '丰镇市', null);
INSERT INTO `t_area` VALUES ('16477', '530900', '530000', '临沧市', null);
INSERT INTO `t_area` VALUES ('16478', '530902', '530900', '临翔区', null);
INSERT INTO `t_area` VALUES ('16479', '320125', '320100', '高淳县', null);
INSERT INTO `t_area` VALUES ('16480', '320124', '320100', '溧水县', null);
INSERT INTO `t_area` VALUES ('16481', '140781', '140700', '介休市', null);
INSERT INTO `t_area` VALUES ('16482', '520402', '520400', '西秀区', null);
INSERT INTO `t_area` VALUES ('16483', '520400', '520000', '安顺市', null);
INSERT INTO `t_area` VALUES ('16484', '130582', '130500', '沙河市', null);
INSERT INTO `t_area` VALUES ('16485', '130581', '130500', '南宫市', null);
INSERT INTO `t_area` VALUES ('16486', '320903', '320900', '盐都区', null);
INSERT INTO `t_area` VALUES ('16487', '320902', '320900', '亭湖区', null);
INSERT INTO `t_area` VALUES ('16488', '320900', '320000', '盐城市', null);
INSERT INTO `t_area` VALUES ('16489', '320116', '320100', '六合区', null);
INSERT INTO `t_area` VALUES ('16490', '510322', '510300', '富顺县', null);
INSERT INTO `t_area` VALUES ('16491', '320115', '320100', '江宁区', null);
INSERT INTO `t_area` VALUES ('16492', '510321', '510300', '荣县', null);
INSERT INTO `t_area` VALUES ('16493', '320114', '320100', '雨花台区', null);
INSERT INTO `t_area` VALUES ('16494', '320113', '320100', '栖霞区', null);
INSERT INTO `t_area` VALUES ('16495', '320111', '320100', '浦口区', null);
INSERT INTO `t_area` VALUES ('16496', '150924', '150900', '兴和县', null);
INSERT INTO `t_area` VALUES ('16497', '150923', '150900', '商都县', null);
INSERT INTO `t_area` VALUES ('16498', '530927', '530900', '沧源佤族自治县', null);
INSERT INTO `t_area` VALUES ('16499', '141029', '141000', '乡宁县', null);
INSERT INTO `t_area` VALUES ('16500', '150922', '150900', '化德县', null);
INSERT INTO `t_area` VALUES ('16501', '141028', '141000', '吉县', null);
INSERT INTO `t_area` VALUES ('16502', '150921', '150900', '卓资县', null);
INSERT INTO `t_area` VALUES ('16503', '530924', '530900', '镇康县', null);
INSERT INTO `t_area` VALUES ('16504', '530923', '530900', '永德县', null);
INSERT INTO `t_area` VALUES ('16505', '530926', '530900', '耿马傣族佤族自治县', null);
INSERT INTO `t_area` VALUES ('16506', '530925', '530900', '双江拉祜族佤族布朗族傣族自治县', null);
INSERT INTO `t_area` VALUES ('16507', '321282', '321200', '靖江市', null);
INSERT INTO `t_area` VALUES ('16508', '141023', '141000', '襄汾县', null);
INSERT INTO `t_area` VALUES ('16509', '321283', '321200', '泰兴市', null);
INSERT INTO `t_area` VALUES ('16510', '141022', '141000', '翼城县', null);
INSERT INTO `t_area` VALUES ('16511', '141021', '141000', '曲沃县', null);
INSERT INTO `t_area` VALUES ('16512', '530922', '530900', '云县', null);
INSERT INTO `t_area` VALUES ('16513', '321281', '321200', '兴化市', null);
INSERT INTO `t_area` VALUES ('16514', '530921', '530900', '凤庆县', null);
INSERT INTO `t_area` VALUES ('16515', '141027', '141000', '浮山县', null);
INSERT INTO `t_area` VALUES ('16516', '141026', '141000', '安泽县', null);
INSERT INTO `t_area` VALUES ('16517', '321284', '321200', '姜堰市', null);
INSERT INTO `t_area` VALUES ('16518', '141025', '141000', '古县', null);
INSERT INTO `t_area` VALUES ('16519', '141024', '141000', '洪洞县', null);
INSERT INTO `t_area` VALUES ('16520', '110114', '110100', '昌平区', null);
INSERT INTO `t_area` VALUES ('16521', '110115', '110100', '大兴区', null);
INSERT INTO `t_area` VALUES ('16522', '110116', '110100', '怀柔区', null);
INSERT INTO `t_area` VALUES ('16523', '110117', '110100', '平谷区', null);
INSERT INTO `t_area` VALUES ('16524', '141030', '141000', '大宁县', null);
INSERT INTO `t_area` VALUES ('16525', '110111', '110100', '房山区', null);
INSERT INTO `t_area` VALUES ('16526', '110112', '110100', '通州区', null);
INSERT INTO `t_area` VALUES ('16527', '110113', '110100', '顺义区', null);
INSERT INTO `t_area` VALUES ('16528', '150929', '150900', '四子王旗', null);
INSERT INTO `t_area` VALUES ('16529', '150927', '150900', '察哈尔右翼中旗', null);
INSERT INTO `t_area` VALUES ('16530', '150928', '150900', '察哈尔右翼后旗', null);
INSERT INTO `t_area` VALUES ('16531', '150925', '150900', '凉城县', null);
INSERT INTO `t_area` VALUES ('16532', '150926', '150900', '察哈尔右翼前旗', null);
INSERT INTO `t_area` VALUES ('16533', '140725', '140700', '寿阳县', null);
INSERT INTO `t_area` VALUES ('16534', '140724', '140700', '昔阳县', null);
INSERT INTO `t_area` VALUES ('16535', '140727', '140700', '祁县', null);
INSERT INTO `t_area` VALUES ('16536', '140726', '140700', '太谷县', null);
INSERT INTO `t_area` VALUES ('16537', '140721', '140700', '榆社县', null);
INSERT INTO `t_area` VALUES ('16538', '150800', '150000', '巴彦淖尔市', null);
INSERT INTO `t_area` VALUES ('16539', '140723', '140700', '和顺县', null);
INSERT INTO `t_area` VALUES ('16540', '130521', '130500', '邢台县', null);
INSERT INTO `t_area` VALUES ('16541', '140722', '140700', '左权县', null);
INSERT INTO `t_area` VALUES ('16542', '130522', '130500', '临城县', null);
INSERT INTO `t_area` VALUES ('16543', '150802', '150800', '临河区', null);
INSERT INTO `t_area` VALUES ('16544', '141032', '141000', '永和县', null);
INSERT INTO `t_area` VALUES ('16545', '130523', '130500', '内丘县', null);
INSERT INTO `t_area` VALUES ('16546', '141031', '141000', '隰县', null);
INSERT INTO `t_area` VALUES ('16547', '130524', '130500', '柏乡县', null);
INSERT INTO `t_area` VALUES ('16548', '141034', '141000', '汾西县', null);
INSERT INTO `t_area` VALUES ('16549', '130525', '130500', '隆尧县', null);
INSERT INTO `t_area` VALUES ('16550', '141033', '141000', '蒲县', null);
INSERT INTO `t_area` VALUES ('16551', '130526', '130500', '任县', null);
INSERT INTO `t_area` VALUES ('16552', '130527', '130500', '南和县', null);
INSERT INTO `t_area` VALUES ('16553', '130528', '130500', '宁晋县', null);
INSERT INTO `t_area` VALUES ('16554', '130529', '130500', '巨鹿县', null);
INSERT INTO `t_area` VALUES ('16555', '110128', '110100', '密云县', null);
INSERT INTO `t_area` VALUES ('16556', '330903', '330900', '普陀区', null);
INSERT INTO `t_area` VALUES ('16557', '330902', '330900', '定海区', null);
INSERT INTO `t_area` VALUES ('16558', '330900', '330000', '舟山市', null);
INSERT INTO `t_area` VALUES ('16559', '140728', '140700', '平遥县', null);
INSERT INTO `t_area` VALUES ('16560', '140729', '140700', '灵石县', null);
INSERT INTO `t_area` VALUES ('16561', '110129', '110100', '延庆县', null);
INSERT INTO `t_area` VALUES ('16562', '130532', '130500', '平乡县', null);
INSERT INTO `t_area` VALUES ('16563', '130533', '130500', '威县', null);
INSERT INTO `t_area` VALUES ('16564', '130530', '130500', '新河县', null);
INSERT INTO `t_area` VALUES ('16565', '130531', '130500', '广宗县', null);
INSERT INTO `t_area` VALUES ('16566', '530113', '530100', '东川区', null);
INSERT INTO `t_area` VALUES ('16567', '530112', '530100', '西山区', null);
INSERT INTO `t_area` VALUES ('16568', '141002', '141000', '尧都区', null);
INSERT INTO `t_area` VALUES ('16569', '141000', '140000', '临汾市', null);
INSERT INTO `t_area` VALUES ('16570', '130534', '130500', '清河县', null);
INSERT INTO `t_area` VALUES ('16571', '130535', '130500', '临西县', null);
INSERT INTO `t_area` VALUES ('16572', '530121', '530100', '呈贡县', null);
INSERT INTO `t_area` VALUES ('16573', '530122', '530100', '晋宁县', null);
INSERT INTO `t_area` VALUES ('16574', '370406', '370400', '山亭区', null);
INSERT INTO `t_area` VALUES ('16575', '370405', '370400', '台儿庄区', null);
INSERT INTO `t_area` VALUES ('16576', '370404', '370400', '峄城区', null);
INSERT INTO `t_area` VALUES ('16577', '370403', '370400', '薛城区', null);
INSERT INTO `t_area` VALUES ('16578', '370402', '370400', '市中区', null);
INSERT INTO `t_area` VALUES ('16579', '370400', '370000', '枣庄市', null);
INSERT INTO `t_area` VALUES ('16580', '150823', '150800', '乌拉特前旗', null);
INSERT INTO `t_area` VALUES ('16581', '420881', '420800', '钟祥市', null);
INSERT INTO `t_area` VALUES ('16582', '150822', '150800', '磴口县', null);
INSERT INTO `t_area` VALUES ('16583', '150825', '150800', '乌拉特后旗', null);
INSERT INTO `t_area` VALUES ('16584', '150824', '150800', '乌拉特中旗', null);
INSERT INTO `t_area` VALUES ('16585', '150821', '150800', '五原县', null);
INSERT INTO `t_area` VALUES ('16586', '530126', '530100', '石林彝族自治县', null);
INSERT INTO `t_area` VALUES ('16587', '530125', '530100', '宜良县', null);
INSERT INTO `t_area` VALUES ('16588', '530124', '530100', '富民县', null);
INSERT INTO `t_area` VALUES ('16589', '530129', '530100', '寻甸回族彝族自治县', null);
INSERT INTO `t_area` VALUES ('16590', '530128', '530100', '禄劝彝族苗族自治县', null);
INSERT INTO `t_area` VALUES ('16591', '530127', '530100', '嵩明县', null);
INSERT INTO `t_area` VALUES ('16592', '330922', '330900', '嵊泗县', null);
INSERT INTO `t_area` VALUES ('16593', '330921', '330900', '岱山县', null);
INSERT INTO `t_area` VALUES ('16594', '150826', '150800', '杭锦后旗', null);
INSERT INTO `t_area` VALUES ('16595', '350922', '350900', '古田县', null);
INSERT INTO `t_area` VALUES ('16596', '350921', '350900', '霞浦县', null);
INSERT INTO `t_area` VALUES ('16597', '210421', '210400', '抚顺县', null);
INSERT INTO `t_area` VALUES ('16598', '210422', '210400', '新宾满族自治县', null);
INSERT INTO `t_area` VALUES ('16599', '350926', '350900', '柘荣县', null);
INSERT INTO `t_area` VALUES ('16600', '210423', '210400', '清原满族自治县', null);
INSERT INTO `t_area` VALUES ('16601', '350925', '350900', '周宁县', null);
INSERT INTO `t_area` VALUES ('16602', '350924', '350900', '寿宁县', null);
INSERT INTO `t_area` VALUES ('16603', '350923', '350900', '屏南县', null);
INSERT INTO `t_area` VALUES ('16604', '341024', '341000', '祁门县', null);
INSERT INTO `t_area` VALUES ('16605', '341023', '341000', '黟县', null);
INSERT INTO `t_area` VALUES ('16606', '341022', '341000', '休宁县', null);
INSERT INTO `t_area` VALUES ('16607', '511600', '510000', '广安市', null);
INSERT INTO `t_area` VALUES ('16608', '511602', '511600', '广安区', null);
INSERT INTO `t_area` VALUES ('16609', '610602', '610600', '宝塔区', null);
INSERT INTO `t_area` VALUES ('16610', '610600', '610000', '延安市', null);
INSERT INTO `t_area` VALUES ('16611', '350902', '350900', '蕉城区', null);
INSERT INTO `t_area` VALUES ('16612', '350900', '350000', '宁德市', null);
INSERT INTO `t_area` VALUES ('16613', '511622', '511600', '武胜县', null);
INSERT INTO `t_area` VALUES ('16614', '511621', '511600', '岳池县', null);
INSERT INTO `t_area` VALUES ('16615', '511623', '511600', '邻水县', null);
INSERT INTO `t_area` VALUES ('16616', '350000', '0', '福建省', null);
INSERT INTO `t_area` VALUES ('16617', '320981', '320900', '东台市', null);
INSERT INTO `t_area` VALUES ('16618', '320982', '320900', '大丰市', null);
INSERT INTO `t_area` VALUES ('16619', '330281', '330200', '余姚市', null);
INSERT INTO `t_area` VALUES ('16620', '140700', '140000', '晋中市', null);
INSERT INTO `t_area` VALUES ('16621', '330283', '330200', '奉化市', null);
INSERT INTO `t_area` VALUES ('16622', '140702', '140700', '榆次区', null);
INSERT INTO `t_area` VALUES ('16623', '330282', '330200', '慈溪市', null);
INSERT INTO `t_area` VALUES ('16624', '330225', '330200', '象山县', null);
INSERT INTO `t_area` VALUES ('16625', '330226', '330200', '宁海县', null);
INSERT INTO `t_area` VALUES ('16626', '330212', '330200', '鄞州区', null);
INSERT INTO `t_area` VALUES ('16627', '330211', '330200', '镇海区', null);
INSERT INTO `t_area` VALUES ('16628', '621100', '620000', '定西市', null);
INSERT INTO `t_area` VALUES ('16629', '621102', '621100', '安定区', null);
INSERT INTO `t_area` VALUES ('16630', '340406', '340400', '潘集区', null);
INSERT INTO `t_area` VALUES ('16631', '340405', '340400', '八公山区', null);
INSERT INTO `t_area` VALUES ('16632', '340404', '340400', '谢家集区', null);
INSERT INTO `t_area` VALUES ('16633', '340403', '340400', '田家庵区', null);
INSERT INTO `t_area` VALUES ('16634', '340402', '340400', '大通区', null);
INSERT INTO `t_area` VALUES ('16635', '341004', '341000', '徽州区', null);
INSERT INTO `t_area` VALUES ('16636', '340400', '340000', '淮南市', null);
INSERT INTO `t_area` VALUES ('16637', '341002', '341000', '屯溪区', null);
INSERT INTO `t_area` VALUES ('16638', '341003', '341000', '黄山区', null);
INSERT INTO `t_area` VALUES ('16639', '341000', '340000', '黄山市', null);
INSERT INTO `t_area` VALUES ('16640', '341021', '341000', '歙县', null);
INSERT INTO `t_area` VALUES ('16641', '321203', '321200', '高港区', null);
INSERT INTO `t_area` VALUES ('16642', '321202', '321200', '海陵区', null);
INSERT INTO `t_area` VALUES ('16643', '321200', '320000', '泰州市', null);
INSERT INTO `t_area` VALUES ('16644', '360926', '360900', '铜鼓县', null);
INSERT INTO `t_area` VALUES ('16645', '310110', '310100', '杨浦区', null);
INSERT INTO `t_area` VALUES ('16646', '310109', '310100', '虹口区', null);
INSERT INTO `t_area` VALUES ('16647', '360921', '360900', '奉新县', null);
INSERT INTO `t_area` VALUES ('16648', '310103', '310100', '卢湾区', null);
INSERT INTO `t_area` VALUES ('16649', '310104', '310100', '徐汇区', null);
INSERT INTO `t_area` VALUES ('16650', '310101', '310100', '黄浦区', null);
INSERT INTO `t_area` VALUES ('16651', '360925', '360900', '靖安县', null);
INSERT INTO `t_area` VALUES ('16652', '310107', '310100', '普陀区', null);
INSERT INTO `t_area` VALUES ('16653', '411000', '410000', '许昌市', null);
INSERT INTO `t_area` VALUES ('16654', '360924', '360900', '宜丰县', null);
INSERT INTO `t_area` VALUES ('16655', '310108', '310100', '闸北区', null);
INSERT INTO `t_area` VALUES ('16656', '360923', '360900', '上高县', null);
INSERT INTO `t_area` VALUES ('16657', '310105', '310100', '长宁区', null);
INSERT INTO `t_area` VALUES ('16658', '411002', '411000', '魏都区', null);
INSERT INTO `t_area` VALUES ('16659', '360922', '360900', '万载县', null);
INSERT INTO `t_area` VALUES ('16660', '310106', '310100', '静安区', null);
INSERT INTO `t_area` VALUES ('16661', '411003', '411000', '许昌县', null);
INSERT INTO `t_area` VALUES ('16662', '411005', '411000', '襄城县', null);
INSERT INTO `t_area` VALUES ('16663', '411004', '411000', '鄢陵县', null);
INSERT INTO `t_area` VALUES ('16664', '440282', '440200', '南雄市', null);
INSERT INTO `t_area` VALUES ('16665', '440281', '440200', '乐昌市', null);
INSERT INTO `t_area` VALUES ('16666', '310100', '310000', '上海市', null);
INSERT INTO `t_area` VALUES ('16667', '610582', '610500', '华阴市', null);
INSERT INTO `t_area` VALUES ('16668', '610581', '610500', '韩城市', null);
INSERT INTO `t_area` VALUES ('16669', '220403', '220400', '西安区', null);
INSERT INTO `t_area` VALUES ('16670', '220402', '220400', '龙山区', null);
INSERT INTO `t_area` VALUES ('16671', '350881', '350800', '漳平市', null);
INSERT INTO `t_area` VALUES ('16672', '220400', '220000', '辽源市', null);
INSERT INTO `t_area` VALUES ('16673', '542429', '542400', '巴青县', null);
INSERT INTO `t_area` VALUES ('16674', '542428', '542400', '班戈县', null);
INSERT INTO `t_area` VALUES ('16675', '542427', '542400', '索县', null);
INSERT INTO `t_area` VALUES ('16676', '542426', '542400', '申扎县', null);
INSERT INTO `t_area` VALUES ('16677', '542425', '542400', '安多县', null);
INSERT INTO `t_area` VALUES ('16678', '542424', '542400', '聂荣县', null);
INSERT INTO `t_area` VALUES ('16679', '542423', '542400', '比如县', null);
INSERT INTO `t_area` VALUES ('16680', '542422', '542400', '嘉黎县', null);
INSERT INTO `t_area` VALUES ('16681', '542421', '542400', '那曲县', null);
INSERT INTO `t_area` VALUES ('16682', '542430', '542400', '尼玛县', null);
INSERT INTO `t_area` VALUES ('16683', '360902', '360900', '袁州区', null);
INSERT INTO `t_area` VALUES ('16684', '360900', '360000', '宜春市', null);
INSERT INTO `t_area` VALUES ('16685', '310120', '310100', '奉贤区', null);
INSERT INTO `t_area` VALUES ('16686', '330204', '330200', '江东区', null);
INSERT INTO `t_area` VALUES ('16687', '330205', '330200', '江北区', null);
INSERT INTO `t_area` VALUES ('16688', '330203', '330200', '海曙区', null);
INSERT INTO `t_area` VALUES ('16689', '330200', '330000', '宁波市', null);
INSERT INTO `t_area` VALUES ('16690', '310116', '310100', '金山区', null);
INSERT INTO `t_area` VALUES ('16691', '310117', '310100', '松江区', null);
INSERT INTO `t_area` VALUES ('16692', '310118', '310100', '青浦区', null);
INSERT INTO `t_area` VALUES ('16693', '310119', '310100', '南汇区', null);
INSERT INTO `t_area` VALUES ('16694', '310112', '310100', '闵行区', null);
INSERT INTO `t_area` VALUES ('16695', '310113', '310100', '宝山区', null);
INSERT INTO `t_area` VALUES ('16696', '330206', '330200', '北仑区', null);
INSERT INTO `t_area` VALUES ('16697', '310114', '310100', '嘉定区', null);
INSERT INTO `t_area` VALUES ('16698', '310115', '310100', '浦东新区', null);
INSERT INTO `t_area` VALUES ('16699', '610523', '610500', '大荔县', null);
INSERT INTO `t_area` VALUES ('16700', '610522', '610500', '潼关县', null);
INSERT INTO `t_area` VALUES ('16701', '610525', '610500', '澄城县', null);
INSERT INTO `t_area` VALUES ('16702', '610524', '610500', '合阳县', null);
INSERT INTO `t_area` VALUES ('16703', '610527', '610500', '白水县', null);
INSERT INTO `t_area` VALUES ('16704', '610526', '610500', '蒲城县', null);
INSERT INTO `t_area` VALUES ('16705', '610528', '610500', '富平县', null);
INSERT INTO `t_area` VALUES ('16706', '310152', '310100', '川沙区', null);
INSERT INTO `t_area` VALUES ('16707', '440224', '440200', '仁化县', null);
INSERT INTO `t_area` VALUES ('16708', '440222', '440200', '始兴县', null);
INSERT INTO `t_area` VALUES ('16709', '440229', '440200', '翁源县', null);
INSERT INTO `t_area` VALUES ('16710', '360111', '360100', '青山湖区', null);
INSERT INTO `t_area` VALUES ('16711', '511528', '511500', '兴文县', null);
INSERT INTO `t_area` VALUES ('16712', '511529', '511500', '屏山县', null);
INSERT INTO `t_area` VALUES ('16713', '511526', '511500', '珙县', null);
INSERT INTO `t_area` VALUES ('16714', '511527', '511500', '筠连县', null);
INSERT INTO `t_area` VALUES ('16715', '511524', '511500', '长宁县', null);
INSERT INTO `t_area` VALUES ('16716', '511525', '511500', '高县', null);
INSERT INTO `t_area` VALUES ('16717', '511522', '511500', '南溪县', null);
INSERT INTO `t_area` VALUES ('16718', '511523', '511500', '江安县', null);
INSERT INTO `t_area` VALUES ('16719', '410883', '410800', '孟州市', null);
INSERT INTO `t_area` VALUES ('16720', '360127', '360100', '昌北区', null);
INSERT INTO `t_area` VALUES ('16721', '620982', '620900', '敦煌市', null);
INSERT INTO `t_area` VALUES ('16722', '360126', '360100', '经济技术开发区', null);
INSERT INTO `t_area` VALUES ('16723', '620981', '620900', '玉门市', null);
INSERT INTO `t_area` VALUES ('16724', '440233', '440200', '新丰县', null);
INSERT INTO `t_area` VALUES ('16725', '440232', '440200', '乳源瑶族自治县', null);
INSERT INTO `t_area` VALUES ('16726', '360121', '360100', '南昌县', null);
INSERT INTO `t_area` VALUES ('16727', '360123', '360100', '安义县', null);
INSERT INTO `t_area` VALUES ('16728', '360122', '360100', '新建县', null);
INSERT INTO `t_area` VALUES ('16729', '360125', '360100', '红谷滩新区', null);
INSERT INTO `t_area` VALUES ('16730', '360124', '360100', '进贤县', null);
INSERT INTO `t_area` VALUES ('16731', '610500', '610000', '渭南市', null);
INSERT INTO `t_area` VALUES ('16732', '610502', '610500', '临渭区', null);
INSERT INTO `t_area` VALUES ('16733', '210411', '210400', '顺城区', null);
INSERT INTO `t_area` VALUES ('16734', '440200', '440000', '韶关市', null);
INSERT INTO `t_area` VALUES ('16735', '440203', '440200', '武江区', null);
INSERT INTO `t_area` VALUES ('16736', '440205', '440200', '曲江区', null);
INSERT INTO `t_area` VALUES ('16737', '440204', '440200', '浈江区', null);
INSERT INTO `t_area` VALUES ('16738', '450521', '450500', '合浦县', null);
INSERT INTO `t_area` VALUES ('16739', '430382', '430300', '韶山市', null);
INSERT INTO `t_area` VALUES ('16740', '430381', '430300', '湘乡市', null);
INSERT INTO `t_area` VALUES ('16741', '210400', '210000', '抚顺市', null);
INSERT INTO `t_area` VALUES ('16742', '360105', '360100', '湾里区', null);
INSERT INTO `t_area` VALUES ('16743', '210404', '210400', '望花区', null);
INSERT INTO `t_area` VALUES ('16744', '360104', '360100', '青云谱区', null);
INSERT INTO `t_area` VALUES ('16745', '210403', '210400', '东洲区', null);
INSERT INTO `t_area` VALUES ('16746', '210402', '210400', '新抚区', null);
INSERT INTO `t_area` VALUES ('16747', '360100', '360000', '南昌市', null);
INSERT INTO `t_area` VALUES ('16748', '610521', '610500', '华县', null);
INSERT INTO `t_area` VALUES ('16749', '360103', '360100', '西湖区', null);
INSERT INTO `t_area` VALUES ('16750', '220422', '220400', '东辽县', null);
INSERT INTO `t_area` VALUES ('16751', '360102', '360100', '东湖区', null);
INSERT INTO `t_area` VALUES ('16752', '220421', '220400', '东丰县', null);
INSERT INTO `t_area` VALUES ('16753', '411082', '411000', '长葛市', null);
INSERT INTO `t_area` VALUES ('16754', '450500', '450000', '北海市', null);
INSERT INTO `t_area` VALUES ('16755', '411081', '411000', '禹州市', null);
INSERT INTO `t_area` VALUES ('16756', '450503', '450500', '银海区', null);
INSERT INTO `t_area` VALUES ('16757', '620000', '0', '甘肃省', null);
INSERT INTO `t_area` VALUES ('16758', '450502', '450500', '海城区', null);
INSERT INTO `t_area` VALUES ('16759', '620924', '620900', '阿克塞哈萨克族自治县', null);
INSERT INTO `t_area` VALUES ('16760', '620923', '620900', '肃北蒙古族自治县', null);
INSERT INTO `t_area` VALUES ('16761', '620922', '620900', '安西县', null);
INSERT INTO `t_area` VALUES ('16762', '130604', '130600', '南市区', null);
INSERT INTO `t_area` VALUES ('16763', '620921', '620900', '金塔县', null);
INSERT INTO `t_area` VALUES ('16764', '130603', '130600', '北市区', null);
INSERT INTO `t_area` VALUES ('16765', '130602', '130600', '新市区', null);
INSERT INTO `t_area` VALUES ('16766', '130600', '130000', '保定市', null);
INSERT INTO `t_area` VALUES ('16767', '420800', '420000', '荆门市', null);
INSERT INTO `t_area` VALUES ('16768', '450512', '450500', '铁山港区', null);
INSERT INTO `t_area` VALUES ('16769', '441324', '441300', '龙门县', null);
INSERT INTO `t_area` VALUES ('16770', '441323', '441300', '惠东县', null);
INSERT INTO `t_area` VALUES ('16771', '520382', '520300', '仁怀市', null);
INSERT INTO `t_area` VALUES ('16772', '420804', '420800', '掇刀区', null);
INSERT INTO `t_area` VALUES ('16773', '441322', '441300', '博罗县', null);
INSERT INTO `t_area` VALUES ('16774', '420802', '420800', '东宝区', null);
INSERT INTO `t_area` VALUES ('16775', '520381', '520300', '赤水市', null);
INSERT INTO `t_area` VALUES ('16776', '420100', '420000', '武汉市', null);
INSERT INTO `t_area` VALUES ('16777', '360981', '360900', '丰城市', null);
INSERT INTO `t_area` VALUES ('16778', '420102', '420100', '江岸区', null);
INSERT INTO `t_area` VALUES ('16779', '360983', '360900', '高安市', null);
INSERT INTO `t_area` VALUES ('16780', '420103', '420100', '江汉区', null);
INSERT INTO `t_area` VALUES ('16781', '360982', '360900', '樟树市', null);
INSERT INTO `t_area` VALUES ('16782', '420104', '420100', '硚口区', null);
INSERT INTO `t_area` VALUES ('16783', '110108', '110100', '海淀区', null);
INSERT INTO `t_area` VALUES ('16784', '420105', '420100', '汉阳区', null);
INSERT INTO `t_area` VALUES ('16785', '110107', '110100', '石景山区', null);
INSERT INTO `t_area` VALUES ('16786', '420106', '420100', '武昌区', null);
INSERT INTO `t_area` VALUES ('16787', '420107', '420100', '青山区', null);
INSERT INTO `t_area` VALUES ('16788', '110109', '110100', '门头沟区', null);
INSERT INTO `t_area` VALUES ('16789', '110104', '110100', '宣武区', null);
INSERT INTO `t_area` VALUES ('16790', '110103', '110100', '崇文区', null);
INSERT INTO `t_area` VALUES ('16791', '110106', '110100', '丰台区', null);
INSERT INTO `t_area` VALUES ('16792', '410882', '410800', '沁阳市', null);
INSERT INTO `t_area` VALUES ('16793', '110105', '110100', '朝阳区', null);
INSERT INTO `t_area` VALUES ('16794', '410881', '410000', '济源市', null);
INSERT INTO `t_area` VALUES ('16795', '110100', '110000', '北京市', null);
INSERT INTO `t_area` VALUES ('16796', '110102', '110100', '西城区', null);
INSERT INTO `t_area` VALUES ('16797', '110101', '110100', '东城区', null);
INSERT INTO `t_area` VALUES ('16798', '430321', '430300', '湘潭县', null);
INSERT INTO `t_area` VALUES ('16799', '130625', '130600', '徐水县', null);
INSERT INTO `t_area` VALUES ('16800', '130624', '130600', '阜平县', null);
INSERT INTO `t_area` VALUES ('16801', '130623', '130600', '涞水县', null);
INSERT INTO `t_area` VALUES ('16802', '130622', '130600', '清苑县', null);
INSERT INTO `t_area` VALUES ('16803', '130629', '130600', '容城县', null);
INSERT INTO `t_area` VALUES ('16804', '620902', '620900', '肃州区', null);
INSERT INTO `t_area` VALUES ('16805', '130628', '130600', '高阳县', null);
INSERT INTO `t_area` VALUES ('16806', '130627', '130600', '唐县', null);
INSERT INTO `t_area` VALUES ('16807', '620900', '620000', '酒泉市', null);
INSERT INTO `t_area` VALUES ('16808', '130626', '130600', '定兴县', null);
INSERT INTO `t_area` VALUES ('16809', '130621', '130600', '满城县', null);
INSERT INTO `t_area` VALUES ('16810', '420111', '420100', '洪山区', null);
INSERT INTO `t_area` VALUES ('16811', '420114', '420100', '蔡甸区', null);
INSERT INTO `t_area` VALUES ('16812', '420115', '420100', '江夏区', null);
INSERT INTO `t_area` VALUES ('16813', '420112', '420100', '东西湖区', null);
INSERT INTO `t_area` VALUES ('16814', '420113', '420100', '汉南区', null);
INSERT INTO `t_area` VALUES ('16815', '420822', '420800', '沙洋县', null);
INSERT INTO `t_area` VALUES ('16816', '420116', '420100', '黄陂区', null);
INSERT INTO `t_area` VALUES ('16817', '420821', '420800', '京山县', null);
INSERT INTO `t_area` VALUES ('16818', '420117', '420100', '新洲区', null);
INSERT INTO `t_area` VALUES ('16819', '441300', '440000', '惠州市', null);
INSERT INTO `t_area` VALUES ('16820', '441303', '441300', '惠阳区', null);
INSERT INTO `t_area` VALUES ('16821', '441302', '441300', '惠城区', null);
INSERT INTO `t_area` VALUES ('16822', '150900', '150000', '乌兰察布市', null);
INSERT INTO `t_area` VALUES ('16823', '150902', '150900', '集宁区', null);
INSERT INTO `t_area` VALUES ('16824', '430300', '430000', '湘潭市', null);
INSERT INTO `t_area` VALUES ('16825', '510421', '510400', '米易县', null);
INSERT INTO `t_area` VALUES ('16826', '532331', '532300', '禄丰县', null);
INSERT INTO `t_area` VALUES ('16827', '430304', '430300', '岳塘区', null);
INSERT INTO `t_area` VALUES ('16828', '430302', '430300', '雨湖区', null);
INSERT INTO `t_area` VALUES ('16829', '542400', '540000', '那曲地区', null);
INSERT INTO `t_area` VALUES ('16830', '532329', '532300', '武定县', null);
INSERT INTO `t_area` VALUES ('16831', '532328', '532300', '元谋县', null);
INSERT INTO `t_area` VALUES ('16832', '532327', '532300', '永仁县', null);
INSERT INTO `t_area` VALUES ('16833', '410811', '410800', '山阳区', null);
INSERT INTO `t_area` VALUES ('16834', '532326', '532300', '大姚县', null);
INSERT INTO `t_area` VALUES ('16835', '532325', '532300', '姚安县', null);
INSERT INTO `t_area` VALUES ('16836', '532324', '532300', '南华县', null);
INSERT INTO `t_area` VALUES ('16837', '510411', '510400', '仁和区', null);
INSERT INTO `t_area` VALUES ('16838', '532323', '532300', '牟定县', null);
INSERT INTO `t_area` VALUES ('16839', '532322', '532300', '双柏县', null);
INSERT INTO `t_area` VALUES ('16840', '230524', '230500', '饶河县', null);
INSERT INTO `t_area` VALUES ('16841', '230521', '230500', '集贤县', null);
INSERT INTO `t_area` VALUES ('16842', '230523', '230500', '宝清县', null);
INSERT INTO `t_area` VALUES ('16843', '230522', '230500', '友谊县', null);
INSERT INTO `t_area` VALUES ('16844', '410804', '410800', '马村区', null);
INSERT INTO `t_area` VALUES ('16845', '410803', '410800', '中站区', null);
INSERT INTO `t_area` VALUES ('16846', '410802', '410800', '解放区', null);
INSERT INTO `t_area` VALUES ('16847', '510422', '510400', '盐边县', null);
INSERT INTO `t_area` VALUES ('16848', '410800', '410000', '焦作市', null);
INSERT INTO `t_area` VALUES ('16849', '433127', '433100', '永顺县', null);
INSERT INTO `t_area` VALUES ('16850', '433126', '433100', '古丈县', null);
INSERT INTO `t_area` VALUES ('16851', '433125', '433100', '保靖县', null);
INSERT INTO `t_area` VALUES ('16852', '433130', '433100', '龙山县', null);
INSERT INTO `t_area` VALUES ('16853', '532301', '532300', '楚雄市', null);
INSERT INTO `t_area` VALUES ('16854', '230503', '230500', '岭东区', null);
INSERT INTO `t_area` VALUES ('16855', '230502', '230500', '尖山区', null);
INSERT INTO `t_area` VALUES ('16856', '230505', '230500', '四方台区', null);
INSERT INTO `t_area` VALUES ('16857', '230506', '230500', '宝山区', null);
INSERT INTO `t_area` VALUES ('16858', '532300', '530000', '楚雄彝族自治州', null);
INSERT INTO `t_area` VALUES ('16859', '230500', '230000', '双鸭山市', null);
INSERT INTO `t_area` VALUES ('16860', '433122', '433100', '泸溪县', null);
INSERT INTO `t_area` VALUES ('16861', '433123', '433100', '凤凰县', null);
INSERT INTO `t_area` VALUES ('16862', '433124', '433100', '花垣县', null);
INSERT INTO `t_area` VALUES ('16863', '410825', '410800', '温县', null);
INSERT INTO `t_area` VALUES ('16864', '410823', '410800', '武陟县', null);
INSERT INTO `t_area` VALUES ('16865', '510400', '510000', '攀枝花市', null);
INSERT INTO `t_area` VALUES ('16866', '410822', '410800', '博爱县', null);
INSERT INTO `t_area` VALUES ('16867', '510403', '510400', '西区', null);
INSERT INTO `t_area` VALUES ('16868', '410821', '410800', '修武县', null);
INSERT INTO `t_area` VALUES ('16869', '510402', '510400', '东区', null);

-- ----------------------------
-- Table structure for `t_attribute`
-- ----------------------------
DROP TABLE IF EXISTS `t_attribute`;
CREATE TABLE `t_attribute` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `catalogID` int(11) NOT NULL,
  `pid` int(11) DEFAULT '0',
  `order1` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=303 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_attribute
-- ----------------------------
INSERT INTO `t_attribute` VALUES ('1', '味道', '57', '0', null);
INSERT INTO `t_attribute` VALUES ('6', '种类', '57', '0', null);
INSERT INTO `t_attribute` VALUES ('21', '参数', '57', '-1', null);
INSERT INTO `t_attribute` VALUES ('63', '酸', '0', '1', null);
INSERT INTO `t_attribute` VALUES ('64', '甜', '0', '1', null);
INSERT INTO `t_attribute` VALUES ('74', '中文学名', '0', '21', null);
INSERT INTO `t_attribute` VALUES ('75', '拉丁学名', '0', '21', null);
INSERT INTO `t_attribute` VALUES ('76', '界', '0', '21', null);
INSERT INTO `t_attribute` VALUES ('77', '门', '0', '21', null);
INSERT INTO `t_attribute` VALUES ('78', '纲', '0', '21', null);
INSERT INTO `t_attribute` VALUES ('79', '亚    纲', '0', '21', null);
INSERT INTO `t_attribute` VALUES ('80', '目', '0', '21', null);
INSERT INTO `t_attribute` VALUES ('81', '分布区域', '0', '21', null);
INSERT INTO `t_attribute` VALUES ('82', '主要生产地', '0', '21', null);
INSERT INTO `t_attribute` VALUES ('98', '区域', '58', '0', null);
INSERT INTO `t_attribute` VALUES ('172', '类别', '58', '0', null);
INSERT INTO `t_attribute` VALUES ('173', '零食小吃', '0', '172', null);
INSERT INTO `t_attribute` VALUES ('174', '茶水酒饮', '0', '172', null);
INSERT INTO `t_attribute` VALUES ('175', '鲜果时蔬', '0', '172', null);
INSERT INTO `t_attribute` VALUES ('176', '禽畜肉蛋', '0', '172', null);
INSERT INTO `t_attribute` VALUES ('177', '粮油米面', '0', '172', null);
INSERT INTO `t_attribute` VALUES ('178', '南北干货', '0', '172', null);
INSERT INTO `t_attribute` VALUES ('179', '酱菜/调味料', '0', '172', null);
INSERT INTO `t_attribute` VALUES ('180', '水产海鲜', '0', '172', null);
INSERT INTO `t_attribute` VALUES ('181', '产品参数', '58', '-1', null);
INSERT INTO `t_attribute` VALUES ('220', '产品标准号', '0', '181', null);
INSERT INTO `t_attribute` VALUES ('221', '厂名', '0', '181', null);
INSERT INTO `t_attribute` VALUES ('222', '厂址', '0', '181', null);
INSERT INTO `t_attribute` VALUES ('223', '厂家联系方式', '0', '181', null);
INSERT INTO `t_attribute` VALUES ('224', '配料表', '0', '181', null);
INSERT INTO `t_attribute` VALUES ('225', '储藏方法', '0', '181', null);
INSERT INTO `t_attribute` VALUES ('226', '保质期', '0', '181', null);
INSERT INTO `t_attribute` VALUES ('227', '产品', '0', '181', null);
INSERT INTO `t_attribute` VALUES ('228', '包装方式', '0', '181', null);
INSERT INTO `t_attribute` VALUES ('229', '品牌', '0', '181', null);
INSERT INTO `t_attribute` VALUES ('230', '系列', '0', '181', null);
INSERT INTO `t_attribute` VALUES ('231', '重量(g)', '0', '181', null);
INSERT INTO `t_attribute` VALUES ('232', '鱼的种类', '0', '181', null);
INSERT INTO `t_attribute` VALUES ('233', '产地', '0', '181', null);
INSERT INTO `t_attribute` VALUES ('234', '省份', '0', '181', null);
INSERT INTO `t_attribute` VALUES ('235', '城市', '0', '181', null);
INSERT INTO `t_attribute` VALUES ('236', '特产品类', '0', '181', null);
INSERT INTO `t_attribute` VALUES ('237', '北京', '0', '98', null);
INSERT INTO `t_attribute` VALUES ('238', '天津', '0', '98', null);
INSERT INTO `t_attribute` VALUES ('239', '上海', '0', '98', null);
INSERT INTO `t_attribute` VALUES ('240', '重庆', '0', '98', null);
INSERT INTO `t_attribute` VALUES ('241', '江苏', '0', '98', null);
INSERT INTO `t_attribute` VALUES ('242', '黑龙江', '0', '98', null);
INSERT INTO `t_attribute` VALUES ('243', '吉林', '0', '98', null);
INSERT INTO `t_attribute` VALUES ('244', '辽宁', '0', '98', null);
INSERT INTO `t_attribute` VALUES ('245', '东北', '0', '98', null);
INSERT INTO `t_attribute` VALUES ('246', '内蒙古', '0', '98', null);
INSERT INTO `t_attribute` VALUES ('247', '河北', '0', '98', null);
INSERT INTO `t_attribute` VALUES ('248', '新疆', '0', '98', null);
INSERT INTO `t_attribute` VALUES ('249', '青海', '0', '98', null);
INSERT INTO `t_attribute` VALUES ('250', '陕西', '0', '98', null);
INSERT INTO `t_attribute` VALUES ('251', '宁夏', '0', '98', null);
INSERT INTO `t_attribute` VALUES ('252', '河南', '0', '98', null);
INSERT INTO `t_attribute` VALUES ('253', '山西', '0', '98', null);
INSERT INTO `t_attribute` VALUES ('254', '湖北', '0', '98', null);
INSERT INTO `t_attribute` VALUES ('255', '湖南', '0', '98', null);
INSERT INTO `t_attribute` VALUES ('256', '四川', '0', '98', null);
INSERT INTO `t_attribute` VALUES ('257', '云南', '0', '98', null);
INSERT INTO `t_attribute` VALUES ('258', '广西', '0', '98', null);
INSERT INTO `t_attribute` VALUES ('259', '浙江', '0', '98', null);
INSERT INTO `t_attribute` VALUES ('260', '江西', '0', '98', null);
INSERT INTO `t_attribute` VALUES ('261', '广东', '0', '98', null);
INSERT INTO `t_attribute` VALUES ('262', '福建', '0', '98', null);
INSERT INTO `t_attribute` VALUES ('263', '台湾', '0', '98', null);
INSERT INTO `t_attribute` VALUES ('264', '海南', '0', '98', null);
INSERT INTO `t_attribute` VALUES ('265', '香港', '0', '98', null);
INSERT INTO `t_attribute` VALUES ('266', '澳门', '0', '98', null);
INSERT INTO `t_attribute` VALUES ('267', '苹果', '0', '6', '0');
INSERT INTO `t_attribute` VALUES ('268', '橘子', '0', '6', '0');
INSERT INTO `t_attribute` VALUES ('269', '香蕉', '0', '6', '0');
INSERT INTO `t_attribute` VALUES ('270', '柿子', '0', '6', '0');
INSERT INTO `t_attribute` VALUES ('271', '草莓', '0', '6', '0');
INSERT INTO `t_attribute` VALUES ('272', '李子', '0', '6', '0');
INSERT INTO `t_attribute` VALUES ('273', '葡萄', '0', '6', '0');
INSERT INTO `t_attribute` VALUES ('274', '荔枝', '0', '6', '0');
INSERT INTO `t_attribute` VALUES ('275', '青梅', '0', '6', '0');
INSERT INTO `t_attribute` VALUES ('276', '火龙果', '0', '6', '0');
INSERT INTO `t_attribute` VALUES ('277', '桃子', '0', '6', '0');
INSERT INTO `t_attribute` VALUES ('278', '金桔', '0', '6', '0');
INSERT INTO `t_attribute` VALUES ('279', '青苹果', '0', '6', '0');
INSERT INTO `t_attribute` VALUES ('280', '柚子', '0', '6', '0');
INSERT INTO `t_attribute` VALUES ('281', '作者', '60', '0', '1');
INSERT INTO `t_attribute` VALUES ('284', '鲁迅', '0', '281', '0');
INSERT INTO `t_attribute` VALUES ('285', '沈从文', '0', '281', '0');
INSERT INTO `t_attribute` VALUES ('286', '韩寒', '0', '281', '0');
INSERT INTO `t_attribute` VALUES ('287', '作者国籍', '60', '0', '2');
INSERT INTO `t_attribute` VALUES ('288', '中国', '0', '287', '0');
INSERT INTO `t_attribute` VALUES ('289', '外国', '0', '287', '0');
INSERT INTO `t_attribute` VALUES ('290', '语言', '77', '0', '1');
INSERT INTO `t_attribute` VALUES ('291', '汇编', '0', '290', '0');
INSERT INTO `t_attribute` VALUES ('292', 'c/c++', '0', '290', '0');
INSERT INTO `t_attribute` VALUES ('293', 'Java', '0', '290', '0');
INSERT INTO `t_attribute` VALUES ('294', 'Net', '0', '290', '0');
INSERT INTO `t_attribute` VALUES ('295', 'Python', '0', '290', '0');
INSERT INTO `t_attribute` VALUES ('296', 'Javascript', '0', '290', '0');
INSERT INTO `t_attribute` VALUES ('297', 'SQL', '0', '290', '0');
INSERT INTO `t_attribute` VALUES ('298', '手机', '90', '-1', '0');
INSERT INTO `t_attribute` VALUES ('299', '内存', '0', '298', '0');
INSERT INTO `t_attribute` VALUES ('300', '规格', '0', '298', '0');
INSERT INTO `t_attribute` VALUES ('301', '手机', '90', '0', '0');
INSERT INTO `t_attribute` VALUES ('302', '颜色', '0', '301', '0');

-- ----------------------------
-- Table structure for `t_attribute_link`
-- ----------------------------
DROP TABLE IF EXISTS `t_attribute_link`;
CREATE TABLE `t_attribute_link` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `attrID` int(11) DEFAULT '0',
  `productID` int(11) DEFAULT '0',
  `value` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=462 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_attribute_link
-- ----------------------------
INSERT INTO `t_attribute_link` VALUES ('51', '159', '10010', null);
INSERT INTO `t_attribute_link` VALUES ('52', '180', '10010', null);
INSERT INTO `t_attribute_link` VALUES ('53', '220', '10010', 'Q/WJB03-2010');
INSERT INTO `t_attribute_link` VALUES ('54', '221', '10010', '武汉市江夏金口八一渔业食品有限公司');
INSERT INTO `t_attribute_link` VALUES ('55', '222', '10010', '湖北省武汉市江夏区金口街金槐路');
INSERT INTO `t_attribute_link` VALUES ('56', '223', '10010', '18071711207');
INSERT INTO `t_attribute_link` VALUES ('57', '224', '10010', '武昌鱼、黄豆、食用植物油、料酒、食用盐、生姜、大蒜');
INSERT INTO `t_attribute_link` VALUES ('58', '225', '10010', '请置于阴凉干燥处(冷藏效果更佳)');
INSERT INTO `t_attribute_link` VALUES ('59', '226', '10010', '240');
INSERT INTO `t_attribute_link` VALUES ('60', '227', '10010', '其它');
INSERT INTO `t_attribute_link` VALUES ('61', '228', '10010', '包装');
INSERT INTO `t_attribute_link` VALUES ('62', '229', '10010', 'Keke Food/可可哥');
INSERT INTO `t_attribute_link` VALUES ('63', '230', '10010', '700g礼提');
INSERT INTO `t_attribute_link` VALUES ('64', '231', '10010', '700');
INSERT INTO `t_attribute_link` VALUES ('65', '232', '10010', '其它');
INSERT INTO `t_attribute_link` VALUES ('66', '233', '10010', '中国大陆');
INSERT INTO `t_attribute_link` VALUES ('67', '234', '10010', '湖北');
INSERT INTO `t_attribute_link` VALUES ('68', '235', '10010', '武汉');
INSERT INTO `t_attribute_link` VALUES ('69', '236', '10010', '武昌鱼');
INSERT INTO `t_attribute_link` VALUES ('93', '159', '10011', null);
INSERT INTO `t_attribute_link` VALUES ('94', '174', '10011', null);
INSERT INTO `t_attribute_link` VALUES ('95', '220', '10011', '22');
INSERT INTO `t_attribute_link` VALUES ('96', '221', '10011', '2');
INSERT INTO `t_attribute_link` VALUES ('97', '222', '10011', '2');
INSERT INTO `t_attribute_link` VALUES ('98', '223', '10011', '2');
INSERT INTO `t_attribute_link` VALUES ('99', '224', '10011', '2');
INSERT INTO `t_attribute_link` VALUES ('100', '225', '10011', '2');
INSERT INTO `t_attribute_link` VALUES ('101', '226', '10011', '2');
INSERT INTO `t_attribute_link` VALUES ('102', '227', '10011', '2');
INSERT INTO `t_attribute_link` VALUES ('103', '228', '10011', '2');
INSERT INTO `t_attribute_link` VALUES ('104', '229', '10011', '2');
INSERT INTO `t_attribute_link` VALUES ('105', '230', '10011', '2');
INSERT INTO `t_attribute_link` VALUES ('106', '231', '10011', '2');
INSERT INTO `t_attribute_link` VALUES ('107', '232', '10011', '2');
INSERT INTO `t_attribute_link` VALUES ('108', '233', '10011', '2');
INSERT INTO `t_attribute_link` VALUES ('109', '234', '10011', '2');
INSERT INTO `t_attribute_link` VALUES ('110', '235', '10011', '2');
INSERT INTO `t_attribute_link` VALUES ('111', '236', '10011', '2');
INSERT INTO `t_attribute_link` VALUES ('124', '146', '10013', null);
INSERT INTO `t_attribute_link` VALUES ('125', '173', '10013', null);
INSERT INTO `t_attribute_link` VALUES ('132', '64', '10007', null);
INSERT INTO `t_attribute_link` VALUES ('133', '87', '10007', null);
INSERT INTO `t_attribute_link` VALUES ('403', '64', '10253', null);
INSERT INTO `t_attribute_link` VALUES ('404', '267', '10253', null);
INSERT INTO `t_attribute_link` VALUES ('405', '74', '10253', '香甜可口的苹果');
INSERT INTO `t_attribute_link` VALUES ('406', '75', '10253', '香甜可口的苹果');
INSERT INTO `t_attribute_link` VALUES ('407', '76', '10253', '香甜可口的苹果');
INSERT INTO `t_attribute_link` VALUES ('408', '77', '10253', '香甜可口的苹果');
INSERT INTO `t_attribute_link` VALUES ('409', '78', '10253', '香甜可口的苹果');
INSERT INTO `t_attribute_link` VALUES ('410', '79', '10253', '香甜可口的苹果');
INSERT INTO `t_attribute_link` VALUES ('411', '80', '10253', '香甜可口的苹果');
INSERT INTO `t_attribute_link` VALUES ('412', '81', '10253', '香甜可口的苹果');
INSERT INTO `t_attribute_link` VALUES ('413', '82', '10253', '香甜可口的苹果');
INSERT INTO `t_attribute_link` VALUES ('433', '64', '10252', null);
INSERT INTO `t_attribute_link` VALUES ('434', '267', '10252', null);
INSERT INTO `t_attribute_link` VALUES ('435', '176', '10012', null);
INSERT INTO `t_attribute_link` VALUES ('443', '64', '10008', null);
INSERT INTO `t_attribute_link` VALUES ('444', '278', '10008', null);
INSERT INTO `t_attribute_link` VALUES ('445', '74', '10008', '苹果');
INSERT INTO `t_attribute_link` VALUES ('446', '75', '10008', 'Malus domestica');
INSERT INTO `t_attribute_link` VALUES ('447', '76', '10008', '植物界');
INSERT INTO `t_attribute_link` VALUES ('448', '77', '10008', '被子植物门');
INSERT INTO `t_attribute_link` VALUES ('449', '78', '10008', '双子叶植物纲');
INSERT INTO `t_attribute_link` VALUES ('450', '79', '10008', '蔷薇亚纲');
INSERT INTO `t_attribute_link` VALUES ('451', '80', '10008', '蔷薇目');
INSERT INTO `t_attribute_link` VALUES ('452', '81', '10008', '东北、华北、华东、西北甘肃静宁和四川、云南等地');
INSERT INTO `t_attribute_link` VALUES ('453', '82', '10008', '山东栖霞亭口');
INSERT INTO `t_attribute_link` VALUES ('460', '284', '10316', null);
INSERT INTO `t_attribute_link` VALUES ('461', '288', '10316', null);

-- ----------------------------
-- Table structure for `t_attributelink`
-- ----------------------------
DROP TABLE IF EXISTS `t_attributelink`;
CREATE TABLE `t_attributelink` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `attrID` int(11) DEFAULT '0',
  `productID` int(11) DEFAULT '0',
  `value` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=500 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_attributelink
-- ----------------------------
INSERT INTO `t_attributelink` VALUES ('497', '302', '10310', null);
INSERT INTO `t_attributelink` VALUES ('498', '299', '10310', '16');
INSERT INTO `t_attributelink` VALUES ('499', '300', '10310', '12');

-- ----------------------------
-- Table structure for `t_catalog`
-- ----------------------------
DROP TABLE IF EXISTS `t_catalog`;
CREATE TABLE `t_catalog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `pid` int(11) DEFAULT '0',
  `order1` int(11) DEFAULT NULL,
  `type` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `code` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `showInNav` varchar(2) COLLATE utf8_unicode_ci DEFAULT 'n',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_UNIQUE` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_catalog
-- ----------------------------
INSERT INTO `t_catalog` VALUES ('20', '图书', '0', '1', 'p', 'tushu', 'y');
INSERT INTO `t_catalog` VALUES ('21', '男装', '0', '2', 'p', 'nanzhuang', 'y');
INSERT INTO `t_catalog` VALUES ('23', '女装', '0', '3', 'p', 'nvzhuang', 'y');
INSERT INTO `t_catalog` VALUES ('25', '电子数码', '0', '5', 'p', 'dianzishuma', 'y');
INSERT INTO `t_catalog` VALUES ('26', '笔记本', '25', '0', 'p', 'bijiben', 'n');
INSERT INTO `t_catalog` VALUES ('28', '电子书', '20', '1', 'p', 'dianzishu', 'n');
INSERT INTO `t_catalog` VALUES ('29', '鞋子', '21', '3', 'p', 'xiezi', 'n');
INSERT INTO `t_catalog` VALUES ('30', '西服', '21', '1', 'p', 'xifu', 'n');
INSERT INTO `t_catalog` VALUES ('31', '秋衣', '21', '3', 'p', 'qiuyi', 'n');
INSERT INTO `t_catalog` VALUES ('32', '裙子', '23', '1', 'p', 'qunzi', 'n');
INSERT INTO `t_catalog` VALUES ('33', '女鞋', '23', '2', 'p', 'nvxie', 'n');
INSERT INTO `t_catalog` VALUES ('37', '鼠标', '25', '3', 'p', 'shubiao', 'n');
INSERT INTO `t_catalog` VALUES ('39', '儿童婴儿', '0', '6', 'p', 'baby', 'n');
INSERT INTO `t_catalog` VALUES ('40', '儿童书', '39', '1', 'p', 'ertongshu', 'n');
INSERT INTO `t_catalog` VALUES ('41', '绘画', '39', '2', 'p', 'huihua', 'n');
INSERT INTO `t_catalog` VALUES ('46', '新手上路', '0', '1', 'a', '1', 'n');
INSERT INTO `t_catalog` VALUES ('47', '购物指南', '0', '2', 'a', '2', 'n');
INSERT INTO `t_catalog` VALUES ('48', '支付方式', '0', '3', 'a', '3', 'n');
INSERT INTO `t_catalog` VALUES ('51', '配送方式', '0', '5', 'a', '5', 'n');
INSERT INTO `t_catalog` VALUES ('54', '特色服务', '0', '8', 'a', '8', 'n');
INSERT INTO `t_catalog` VALUES ('55', '关于我们', '0', '9', 'a', '9', 'n');
INSERT INTO `t_catalog` VALUES ('56', '美食吃喝', '0', '7', 'p', 'meishi', 'y');
INSERT INTO `t_catalog` VALUES ('57', '新鲜水果', '56', '1', 'p', 'shuiguo', 'n');
INSERT INTO `t_catalog` VALUES ('58', '特产干货', '56', '2', 'p', 'dichanganhuo', 'n');
INSERT INTO `t_catalog` VALUES ('59', '地方菜', '56', '3', 'p', 'difangcai', 'n');
INSERT INTO `t_catalog` VALUES ('60', '小说', '20', '1', 'p', 'xiaoshuo', 'n');
INSERT INTO `t_catalog` VALUES ('61', '育儿书', '20', '3', 'p', 'yuershu', 'n');
INSERT INTO `t_catalog` VALUES ('63', '女包', '23', '3', 'p', 'nvbaobao', 'n');
INSERT INTO `t_catalog` VALUES ('64', '儿童玩具', '39', '3', 'p', 'wanju', 'n');
INSERT INTO `t_catalog` VALUES ('65', '相机', '25', '5', 'p', 'xiangji', 'n');
INSERT INTO `t_catalog` VALUES ('67', '路由器', '25', '7', 'p', 'luyouqi', 'n');
INSERT INTO `t_catalog` VALUES ('69', '短外套', '23', '4', 'p', 'duanwaitao', 'n');
INSERT INTO `t_catalog` VALUES ('70', '休闲裤', '23', '5', 'p', 'xiuxianku', 'n');
INSERT INTO `t_catalog` VALUES ('71', '牛仔裤', '21', '4', 'p', 'niuzaiku', 'n');
INSERT INTO `t_catalog` VALUES ('72', '西装裤', '21', '5', 'p', 'xizhuangku', 'n');
INSERT INTO `t_catalog` VALUES ('73', '工装裤', '21', '6', 'p', 'gongzhuangku', 'n');
INSERT INTO `t_catalog` VALUES ('74', '小米3', '25', '10', 'p', 'xiaomi3', 'n');
INSERT INTO `t_catalog` VALUES ('75', '红米', '25', '10', 'p', 'hongmi', 'n');
INSERT INTO `t_catalog` VALUES ('76', '台式机', '25', '1', 'p', 'taishiji', 'n');
INSERT INTO `t_catalog` VALUES ('77', '程序设计', '20', '4', 'p', 'chengxu', 'n');
INSERT INTO `t_catalog` VALUES ('79', '硬盘', '25', '3', 'p', 'yingpan2', 'n');
INSERT INTO `t_catalog` VALUES ('80', '平板电脑', '25', '3', 'p', 'pingban', 'n');
INSERT INTO `t_catalog` VALUES ('82', '手机', '25', '10', 'p', 'shouji', 'n');
INSERT INTO `t_catalog` VALUES ('83', '毛呢外套', '23', null, 'p', 'maoniwaitao', 'n');
INSERT INTO `t_catalog` VALUES ('84', '衬衫', '23', null, 'p', 'chenshan', 'n');
INSERT INTO `t_catalog` VALUES ('85', '羽绒服', '23', null, 'p', 'yurongfu', 'n');
INSERT INTO `t_catalog` VALUES ('87', '牛仔裤', '23', null, 'p', 'niuzaikunv', 'n');
INSERT INTO `t_catalog` VALUES ('88', '战略管理', '20', '6', 'p', 'zhanlue', 'n');
INSERT INTO `t_catalog` VALUES ('89', '手机', '0', null, 'p', 'shouji1', 'y');
INSERT INTO `t_catalog` VALUES ('90', '苹果手机', '89', '1', 'p', 'pingguoshouji', 'y');

-- ----------------------------
-- Table structure for `t_comment`
-- ----------------------------
DROP TABLE IF EXISTS `t_comment`;
CREATE TABLE `t_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `productID` varchar(45) DEFAULT NULL,
  `account` varchar(45) DEFAULT NULL,
  `orderID` varchar(45) DEFAULT NULL,
  `orderdetailID` varchar(45) DEFAULT NULL,
  `content` varchar(500) NOT NULL,
  `createdate` datetime DEFAULT NULL,
  `star` int(11) DEFAULT NULL,
  `nickname` varchar(45) DEFAULT NULL,
  `status` varchar(2) DEFAULT 'y',
  `reply` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_comment
-- ----------------------------
INSERT INTO `t_comment` VALUES ('11', '10078', 'test1', '10084', '120', '评论测试！', '2014-02-08 21:31:29', '3', '举起手来2', 'y', null);
INSERT INTO `t_comment` VALUES ('12', '10078', 'test1', '10074', '107', '评论测试！44', '2014-02-08 21:31:54', '3', '举起手来2', 'y', null);
INSERT INTO `t_comment` VALUES ('13', '10005', 'test1', '10082', '116', '评论测试！', '2014-02-08 21:32:04', '3', '举起手来2', 'y', null);
INSERT INTO `t_comment` VALUES ('14', '10082', 'test1', '10224', '286', '评论测试！', '2014-03-03 14:09:42', '3', '举起手来2', 'y', '<span style=\"color:#333333;font-family:\'Helvetica Neue\', Helvetica, Arial, sans-serif;line-height:20px;background-color:#FFFFFF;\">评论测试！<span style=\"color:#333333;font-family:\'Helvetica Neue\', Helvetica, Arial, sans-serif;line-height:20px;background-color:#FFFFFF;\">评论测试！<span style=\"color:#333333;font-family:\'Helvetica Neue\', Helvetica, Arial, sans-serif;line-height:20px;background-color:#FFFFFF;\">评论测试！</span></span></span>');
INSERT INTO `t_comment` VALUES ('15', '10317', 'test1', '10250', '318', '这本书还可以，只是不知道价格为什么这个高呢？', '2014-03-26 22:26:23', '5', 'test1', 'y', '123131231231313');
INSERT INTO `t_comment` VALUES ('16', '10267', 'test1', '10254', '323', '231312', '2014-04-11 09:43:22', '5', 'test1', 'y', 'asdafa');

-- ----------------------------
-- Table structure for `t_commenttype`
-- ----------------------------
DROP TABLE IF EXISTS `t_commenttype`;
CREATE TABLE `t_commenttype` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `code` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` varchar(2) COLLATE utf8_unicode_ci DEFAULT 'y',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_commenttype
-- ----------------------------
INSERT INTO `t_commenttype` VALUES ('1', '系统内置评论功能', 'default', 'y');

-- ----------------------------
-- Table structure for `t_email`
-- ----------------------------
DROP TABLE IF EXISTS `t_email`;
CREATE TABLE `t_email` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sign` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `account` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `type` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `url` varchar(245) COLLATE utf8_unicode_ci DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  `starttime` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `endtime` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `newEmail` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` varchar(1) COLLATE utf8_unicode_ci DEFAULT 'n',
  `sendStatus` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_email
-- ----------------------------
INSERT INTO `t_email` VALUES ('1', '5579d6f03213a3322a9d93df324d274f', 'test1', 'forget', 'http://127.0.0.1:8080/jeeshopFront/user/reset.html?sign=5579d6f03213a3322a9d93df324d274f', '2014-02-24 16:38:20', null, null, null, 'n', null);
INSERT INTO `t_email` VALUES ('2', 'fda5d5111750d822d251c78cce572858', 'test1', 'forget', 'http://127.0.0.1:8080/jeeshopFront/user/reset.html?sign=fda5d5111750d822d251c78cce572858', '2014-02-24 17:00:28', '1393232428411', '1393239628411', null, 'n', null);
INSERT INTO `t_email` VALUES ('3', '893627594413d16011cc3c89028affdf', 'test1', 'forget', 'http://127.0.0.1:8080/jeeshopFront/user/reset.html?sign=893627594413d16011cc3c89028affdf', '2014-02-24 17:18:43', '1393233522972', '1393240722972', null, 'n', null);
INSERT INTO `t_email` VALUES ('4', '6d1e89c10839c9ddf9c4ed0119eb03c8', 'test1', 'forget', 'http://127.0.0.1:8080/jeeshopFront/user/reset.html?sign=6d1e89c10839c9ddf9c4ed0119eb03c8', '2014-02-24 17:38:58', '1393234738674', '1393241938674', null, 'n', null);
INSERT INTO `t_email` VALUES ('5', '72c4a53bfcce17c517bb0c11794dcba0', 'test1', 'forget', 'http://127.0.0.1:8080/jeeshopFront/user/reset.html?sign=72c4a53bfcce17c517bb0c11794dcba0', '2014-02-24 17:49:25', '1393235365035', '1393242565035', null, 'n', null);
INSERT INTO `t_email` VALUES ('6', 'ba55d3d16d392aec5c1d4a41a429ebc7', 'test1', 'forget', 'http://127.0.0.1:8080/jeeshopFront/user/reset.html?sign=ba55d3d16d392aec5c1d4a41a429ebc7', '2014-02-24 18:08:53', '1393236533227', '1393243733227', null, 'n', null);
INSERT INTO `t_email` VALUES ('7', '6bacb2c1635cf4914681cdf1a246e931', 'test1', 'forget', 'http://127.0.0.1:8080/jeeshopFront/user/reset.html?sign=6bacb2c1635cf4914681cdf1a246e931', '2014-02-24 20:38:11', '1393245491103', '1393252691103', null, 'n', null);
INSERT INTO `t_email` VALUES ('8', '3afea3768a1e7f8b15f551c900e050a3', 'test1', 'forget', 'http://127.0.0.1:8080/jeeshopFront/user/reset.html?sign=3afea3768a1e7f8b15f551c900e050a3', '2014-02-24 20:59:41', '1393246781918', '1393253981918', null, 'n', null);
INSERT INTO `t_email` VALUES ('9', 'a196d34bf56cc6d1a97da31a3e7e0c5e', 'test1', 'forget', 'http://127.0.0.1:8080/jeeshopFront/user/reset.html?sign=a196d34bf56cc6d1a97da31a3e7e0c5e', '2014-02-24 21:16:17', '1393247777002', '1393254977002', null, 'n', null);
INSERT INTO `t_email` VALUES ('10', '6f89c51e47b31631346e4cf5f72d2e85', 'test1', 'forget', 'http://127.0.0.1:8080/jeeshopFront/user/reset.html?sign=6f89c51e47b31631346e4cf5f72d2e85', '2014-02-24 21:29:42', '1393248582068', '1393255782068', null, 'n', null);
INSERT INTO `t_email` VALUES ('11', 'e3aa97cf535fbc5ab0beabe5a10f9854', 'test1', 'forget', 'http://127.0.0.1:8080/jeeshopFront/user/reset.html?sign=e3aa97cf535fbc5ab0beabe5a10f9854', '2014-02-24 21:30:53', '1393248653511', '1393255853511', null, 'n', null);
INSERT INTO `t_email` VALUES ('12', '472a95959803ba41b6a3da07d5b6c9d5', 'test1', 'forget', 'http://127.0.0.1:8080/jeeshopFront/user/reset.html?sign=472a95959803ba41b6a3da07d5b6c9d5', '2014-02-25 14:08:48', '1393308528552', '1393315728552', null, 'n', null);
INSERT INTO `t_email` VALUES ('13', '7079dd037de485e27533253b3ebba893', 'test1', 'forget', 'http://127.0.0.1:8080/jeeshopFront/user/reset.html?sign=7079dd037de485e27533253b3ebba893', '2014-02-25 14:09:08', '1393308548264', '1393315748264', null, 'n', null);
INSERT INTO `t_email` VALUES ('14', 'e8e612a96b3e57ed3133b34a2e942a94', 'test1', 'forget', 'http://127.0.0.1:8080/jeeshopFront/user/reset.html?sign=e8e612a96b3e57ed3133b34a2e942a94', '2014-02-25 14:09:08', '1393308548555', '1393315748555', null, 'n', null);
INSERT INTO `t_email` VALUES ('15', 'cdd2b2c992f036d503c4438a694b26cd', 'test1', 'forget', 'http://127.0.0.1:8080/jeeshopFront/user/reset.html?sign=cdd2b2c992f036d503c4438a694b26cd', '2014-02-25 14:09:08', '1393308548866', '1393315748866', null, 'n', null);
INSERT INTO `t_email` VALUES ('16', 'e19a2a9dfe6281820ecb6191eb98a3f2', 'test1', 'forget', 'http://127.0.0.1:8080/jeeshopFront/user/reset.html?sign=e19a2a9dfe6281820ecb6191eb98a3f2', '2014-02-25 14:09:09', '1393308549120', '1393315749120', null, 'n', null);
INSERT INTO `t_email` VALUES ('17', '7eda0afa840a308e8024a6738ddfae68', 'test1', 'forget', 'http://127.0.0.1:8080/jeeshopFront/user/reset.html?sign=7eda0afa840a308e8024a6738ddfae68', '2014-02-25 14:11:21', '1393308681199', '1393315881199', null, 'n', null);
INSERT INTO `t_email` VALUES ('18', '212d3e5eabeaca46bcfd4a6d778229a4', 'test1', 'forget', 'http://127.0.0.1:8080/jeeshopFront/user/reset.html?sign=212d3e5eabeaca46bcfd4a6d778229a4', '2014-02-25 14:11:21', '1393308681353', '1393315881353', null, 'n', null);
INSERT INTO `t_email` VALUES ('19', 'accf6cca074ae401ab4bd2ad81e97046', 'test1', 'forget', 'http://127.0.0.1:8080/jeeshopFront/user/reset.html?sign=accf6cca074ae401ab4bd2ad81e97046', '2014-02-25 14:11:21', '1393308681572', '1393315881572', null, 'n', null);
INSERT INTO `t_email` VALUES ('20', 'b2987f75ea4bef38ec415932df935dd6', 'test1', 'forget', 'http://127.0.0.1:8080/jeeshopFront/user/reset.html?sign=b2987f75ea4bef38ec415932df935dd6', '2014-02-25 14:11:21', '1393308681749', '1393315881749', null, 'n', null);
INSERT INTO `t_email` VALUES ('21', 'fbc60bd8a08eac81f40ad9e2fa641383', 'test1', 'forget', 'http://127.0.0.1:8080/jeeshopFront/user/reset.html?sign=fbc60bd8a08eac81f40ad9e2fa641383', '2014-02-25 14:11:21', '1393308681988', '1393315881988', null, 'n', null);
INSERT INTO `t_email` VALUES ('22', 'ced3864a63cf565747d4b77adc3420ad', 'test1', 'forget', 'http://127.0.0.1:8080/jeeshopFront/user/reset.html?sign=ced3864a63cf565747d4b77adc3420ad', '2014-02-25 14:11:22', '1393308682164', '1393315882164', null, 'n', null);
INSERT INTO `t_email` VALUES ('23', '8af391f64a2c3fd494d05a9dbd9b4126', 'test1', 'forget', 'http://127.0.0.1:8080/jeeshopFront/user/reset.html?sign=8af391f64a2c3fd494d05a9dbd9b4126', '2014-02-25 14:11:22', '1393308682355', '1393315882355', null, 'n', null);
INSERT INTO `t_email` VALUES ('24', '982178bca83b7668e1eef51780787c74', 'test1', 'forget', 'http://127.0.0.1:8080/jeeshopFront/user/reset.html?sign=982178bca83b7668e1eef51780787c74', '2014-02-25 14:11:22', '1393308682610', '1393315882610', null, 'n', null);
INSERT INTO `t_email` VALUES ('25', 'f42e8ca41d50678f9199e4788a4f748b', 'test1', 'forget', 'http://127.0.0.1:8080/jeeshopFront/user/reset.html?sign=f42e8ca41d50678f9199e4788a4f748b', '2014-02-25 14:11:23', '1393308683664', '1393315883664', null, 'n', null);
INSERT INTO `t_email` VALUES ('26', '22a61e0b0eedf067e2bd896b7af70610', 'test1', 'forget', 'http://127.0.0.1:8080/jeeshopFront/user/reset.html?sign=22a61e0b0eedf067e2bd896b7af70610', '2014-02-25 14:11:23', '1393308683943', '1393315883943', null, 'n', null);
INSERT INTO `t_email` VALUES ('27', 'b6a150ad97838e10c4a218c98f860ef2', 'test1', 'forget', 'http://127.0.0.1:8080/jeeshopFront/user/reset.html?sign=b6a150ad97838e10c4a218c98f860ef2', '2014-02-25 14:11:24', '1393308684125', '1393315884125', null, 'n', null);
INSERT INTO `t_email` VALUES ('28', 'e10f8510db63512f7f298c18c6f11b67', 'test1', 'forget', 'http://127.0.0.1:8080/jeeshopFront/user/reset.html?sign=e10f8510db63512f7f298c18c6f11b67', '2014-02-25 14:11:24', '1393308684352', '1393315884352', null, 'n', null);
INSERT INTO `t_email` VALUES ('29', '2cc6be9f135cd923d4d0d872f7aed5be', 'test1', 'forget', 'http://127.0.0.1:8080/jeeshopFront/user/reset.html?sign=2cc6be9f135cd923d4d0d872f7aed5be', '2014-02-25 14:11:24', '1393308684810', '1393315884810', null, 'n', null);
INSERT INTO `t_email` VALUES ('30', '431677243a1e152cdf468d0d4d1560ea', 'test1', 'forget', 'http://127.0.0.1:8080/jeeshopFront/user/reset.html?sign=431677243a1e152cdf468d0d4d1560ea', '2014-02-25 14:11:24', '1393308684988', '1393315884988', null, 'n', null);
INSERT INTO `t_email` VALUES ('31', '387a06b4d01eb5b443da2bfb478284eb', 'test1', 'forget', 'http://127.0.0.1:8080/jeeshopFront/user/reset.html?sign=387a06b4d01eb5b443da2bfb478284eb', '2014-02-25 14:11:25', '1393308685212', '1393315885212', null, 'n', null);
INSERT INTO `t_email` VALUES ('32', '8a56d59a7498ef4141e443abe45d7fea', 'test1', 'forget', 'http://127.0.0.1:8080/jeeshopFront/user/reset.html?sign=8a56d59a7498ef4141e443abe45d7fea', '2014-02-25 14:11:25', '1393308685405', '1393315885405', null, 'n', null);
INSERT INTO `t_email` VALUES ('33', '4b880ca02fca99140c6ca56a489dbfa7', 'test1', 'forget', 'http://127.0.0.1:8080/jeeshopFront/user/reset.html?sign=4b880ca02fca99140c6ca56a489dbfa7', '2014-02-25 14:48:42', '1393310922421', '1393318122421', null, 'n', null);
INSERT INTO `t_email` VALUES ('34', 'dcded771845e52032531cca553bc134d', 'test1', 'forget', 'http://127.0.0.1:8080/jeeshopFront/user/reset.html?sign=dcded771845e52032531cca553bc134d', '2014-02-25 14:48:43', '1393310923922', '1393318123922', null, 'n', null);
INSERT INTO `t_email` VALUES ('35', '3e578a5d8a8b452bb8a56e86dc10b2d3', 'test1', 'forget', 'http://127.0.0.1:8080/jeeshopFront/user/reset.html?sign=3e578a5d8a8b452bb8a56e86dc10b2d3', '2014-02-25 14:48:45', '1393310925285', '1393318125285', null, 'n', null);
INSERT INTO `t_email` VALUES ('36', '8c53daca0c50f724bef96e123e6f6182', 'test1', 'forget', 'http://127.0.0.1:8080/jeeshopFront/user/reset.html?sign=8c53daca0c50f724bef96e123e6f6182', '2014-02-25 14:51:38', '1393311098037', '1393318298037', null, 'n', null);
INSERT INTO `t_email` VALUES ('37', '02f954b0ba72178318b77b4cdb939a69', 'test1', 'forget', 'http://127.0.0.1:8080/jeeshopFront/user/reset.html?sign=02f954b0ba72178318b77b4cdb939a69', '2014-02-25 14:51:38', '1393311098449', '1393318298449', null, 'n', null);
INSERT INTO `t_email` VALUES ('38', '0ac2ee008f7a0162c210025c9cb827d5', 'test1', 'forget', 'http://127.0.0.1:8080/jeeshopFront/user/reset.html?sign=0ac2ee008f7a0162c210025c9cb827d5', '2014-02-25 14:51:38', '1393311098821', '1393318298821', null, 'n', null);
INSERT INTO `t_email` VALUES ('39', '349af46a0794f1d1eca546e429026033', 'test1', 'forget', 'http://127.0.0.1:8080/jeeshopFront/user/reset.html?sign=349af46a0794f1d1eca546e429026033', '2014-02-25 14:51:39', '1393311099177', '1393318299177', null, 'n', null);
INSERT INTO `t_email` VALUES ('40', '36555abf4d7162d05125986174fe36d1', 'test1', 'forget', 'http://127.0.0.1:8080/jeeshopFront/user/reset.html?sign=36555abf4d7162d05125986174fe36d1', '2014-02-25 14:51:39', '1393311099529', '1393318299529', null, 'n', null);
INSERT INTO `t_email` VALUES ('41', '017db57b9ec5e0fa501b0dd3dfc8cb90', 'test1', 'forget', 'http://127.0.0.1:8080/jeeshopFront/user/reset.html?sign=017db57b9ec5e0fa501b0dd3dfc8cb90', '2014-02-25 14:51:39', '1393311099898', '1393318299898', null, 'n', null);
INSERT INTO `t_email` VALUES ('42', 'adc918d60b12276f045c9eef7fc4a3b4', 'test1', 'forget', 'http://127.0.0.1:8080/jeeshopFront/user/reset.html?sign=adc918d60b12276f045c9eef7fc4a3b4', '2014-02-25 14:54:17', '1393311257006', '1393318457006', null, 'n', null);
INSERT INTO `t_email` VALUES ('43', 'b228235570b00dab3c18fd23b4c872ca', 'test1', 'forget', 'http://127.0.0.1:8080/jeeshopFront/user/reset.html?sign=b228235570b00dab3c18fd23b4c872ca', '2014-02-25 15:04:23', '1393311863288', '1393319063288', null, 'n', null);
INSERT INTO `t_email` VALUES ('44', 'b3329865b40f48d2b1ca7da1f15f19c3', 'test1', 'forget', 'http://127.0.0.1:8080/jeeshopFront/user/reset.html?sign=b3329865b40f48d2b1ca7da1f15f19c3', '2014-02-25 20:37:42', '1393331862751', '1393339062751', null, 'n', null);
INSERT INTO `t_email` VALUES ('45', '9b7be356201436a28d35177bc96d1585', 'test1', 'forget', 'http://127.0.0.1:8080/jeeshopFront/user/reset.html?sign=9b7be356201436a28d35177bc96d1585', '2014-02-25 20:43:53', '1393332233820', '1393339433820', null, 'n', null);
INSERT INTO `t_email` VALUES ('46', 'e2025f377003c4c576f48d9562caaae8', 'test1', 'forget', 'http://127.0.0.1:8080/jeeshopFront/user/reset.html?sign=e2025f377003c4c576f48d9562caaae8', '2014-02-25 20:44:56', '1393332296709', '1393339496709', null, 'n', null);
INSERT INTO `t_email` VALUES ('47', '9e5a28f7fe41b78b2b24e58497586af2', 'test1', 'forget', 'http://127.0.0.1:8080/jeeshopFront/user/reset.html?sign=9e5a28f7fe41b78b2b24e58497586af2', '2014-02-25 20:45:23', '1393332323279', '1393339523279', null, 'n', null);
INSERT INTO `t_email` VALUES ('48', '9bfb587e335c9447c8759620391a7598', 'test1', 'forget', 'http://127.0.0.1:8080/jeeshopFront/user/reset.html?sign=9bfb587e335c9447c8759620391a7598', '2014-02-25 20:46:27', '1393332387294', '1393339587294', null, 'n', null);
INSERT INTO `t_email` VALUES ('49', 'fb092705a15cabdebf58c1178dd5f571', 'test1', 'forget', 'http://127.0.0.1:8080/jeeshopFront/user/reset.html?sign=fb092705a15cabdebf58c1178dd5f571', '2014-02-25 20:47:19', '1393332439302', '1393339639302', null, 'n', null);
INSERT INTO `t_email` VALUES ('50', 'f364ee9634502593791dd6d351b645dc', 'test1', 'forget', 'http://127.0.0.1:8080/jeeshopFront/user/reset.html?sign=f364ee9634502593791dd6d351b645dc', '2014-02-25 20:48:48', '1393332528828', '1393339728828', null, 'n', null);
INSERT INTO `t_email` VALUES ('51', '23ed7673380160ff63b9d6f522ca1103', 'test1', 'forget', 'http://127.0.0.1:8080/jeeshopFront/user/reset.html?sign=23ed7673380160ff63b9d6f522ca1103', '2014-02-26 21:27:48', '1393421268503', '1393428468503', null, 'n', null);
INSERT INTO `t_email` VALUES ('52', '4c4c74609cf72a46ff417a6b0f30bf0d', 'test1', 'forget', 'http://jeeshop.itelse.com/user/reset.html?sign=4c4c74609cf72a46ff417a6b0f30bf0d', '2014-03-05 21:50:35', '1394027435429', '1394034635429', null, 'n', null);
INSERT INTO `t_email` VALUES ('53', 'fda84a9130bb746365ab31c2e1aaa9a0', 'test1', 'forget', 'http://jeeshop.itelse.com/user/reset.html?sign=fda84a9130bb746365ab31c2e1aaa9a0', '2014-03-05 21:54:59', '1394027699245', '1394034899245', null, 'n', null);
INSERT INTO `t_email` VALUES ('54', 'd36e4ce61b4c3a31b31f6c2cf960bd50', 'test1', 'forget', 'http://jeeshop.itelse.com/user/reset.html?sign=d36e4ce61b4c3a31b31f6c2cf960bd50', '2014-03-05 21:55:49', '1394027749511', '1394034949511', null, 'n', null);
INSERT INTO `t_email` VALUES ('55', '10d381495a302604772656bdb62d1e91', 'test1', 'forget', 'http://jeeshop.itelse.com/user/reset.html?sign=10d381495a302604772656bdb62d1e91', '2014-03-07 09:56:16', '1394157376048', '1394164576048', null, 'n', null);
INSERT INTO `t_email` VALUES ('56', '9ace7f55480db67c189486ed6cd69048', 'test1', 'email_reg', 'http://localhost:8080/jeeshop/user/activeAccount.html?sign=9ace7f55480db67c189486ed6cd69048', '2014-03-26 15:27:50', '1395818870206', '1395826070206', null, 'n', null);
INSERT INTO `t_email` VALUES ('57', '9a797803fc8b52a46392717e7175c173', 'test1', 'email_reg', 'http://localhost:8080/jeeshop/user/activeAccount.html?sign=9a797803fc8b52a46392717e7175c173', '2014-03-26 15:28:30', '1395818910035', '1395826110035', null, 'n', null);
INSERT INTO `t_email` VALUES ('58', '0eba7942283f123eca62592aef553713', 'ceshi01', 'email_reg', 'http://localhost:8080/jeeshop/user/activeAccount.html?sign=0eba7942283f123eca62592aef553713', '2014-03-31 14:21:05', '1396246859801', '1396254059801', null, 'y', 'y');
INSERT INTO `t_email` VALUES ('59', '02e30a02f5a6f6e7678ed89dcbe5bb35', 'lip1', 'email_reg', 'http://localhost:8080/jeeshop/user/activeAccount.html?sign=02e30a02f5a6f6e7678ed89dcbe5bb35', '2014-05-04 10:10:29', '1399169426857', '1399176626857', null, 'y', 'y');
INSERT INTO `t_email` VALUES ('60', 'd3e6d769fecb899d663677a366fc534f', 'wukong', 'email_reg', 'http://localhost:8080/jshop/account/activeAccount.html?sign=d3e6d769fecb899d663677a366fc534f', '2018-03-04 21:39:42', '1520170781293', '1520177981293', null, 'n', 'n');

-- ----------------------------
-- Table structure for `t_emailnotifyproduct`
-- ----------------------------
DROP TABLE IF EXISTS `t_emailnotifyproduct`;
CREATE TABLE `t_emailnotifyproduct` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `receiveEmail` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `productID` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `productName` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `createdate` datetime DEFAULT NULL,
  `notifydate` datetime DEFAULT NULL,
  `status` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sendFailureCount` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_emailnotifyproduct
-- ----------------------------

-- ----------------------------
-- Table structure for `t_express`
-- ----------------------------
DROP TABLE IF EXISTS `t_express`;
CREATE TABLE `t_express` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `fee` decimal(9,2) DEFAULT NULL,
  `order1` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_express
-- ----------------------------
INSERT INTO `t_express` VALUES ('1', 'EXPRESS', '快递', '5.00', '2');
INSERT INTO `t_express` VALUES ('2', 'POST', '平邮', '0.00', '1');
INSERT INTO `t_express` VALUES ('3', 'EMS', 'EMS', '15.00', '3');

-- ----------------------------
-- Table structure for `t_favorite`
-- ----------------------------
DROP TABLE IF EXISTS `t_favorite`;
CREATE TABLE `t_favorite` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  `productID` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `createtime` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_favorite
-- ----------------------------
INSERT INTO `t_favorite` VALUES ('55', 'test1', '10003', '2014-02-26');
INSERT INTO `t_favorite` VALUES ('56', 'test1', '10007', '2014-02-26');
INSERT INTO `t_favorite` VALUES ('57', '_out_1395897992912', '10252', '2014-03-27');
INSERT INTO `t_favorite` VALUES ('58', '_out_1395897992912', '10281', '2014-03-27');
INSERT INTO `t_favorite` VALUES ('59', '_out_1395897992912', '10280', '2014-03-27');
INSERT INTO `t_favorite` VALUES ('60', '_out_1395897992912', '10282', '2014-03-27');
INSERT INTO `t_favorite` VALUES ('61', '_out_1395897992912', '10317', '2014-03-27');
INSERT INTO `t_favorite` VALUES ('62', '_out_1395897992912', '10313', '2014-03-27');
INSERT INTO `t_favorite` VALUES ('63', '_out_1395897992912', '10312', '2014-03-27');
INSERT INTO `t_favorite` VALUES ('64', '_out_1395897992912', '10314', '2014-03-27');

-- ----------------------------
-- Table structure for `t_gift`
-- ----------------------------
DROP TABLE IF EXISTS `t_gift`;
CREATE TABLE `t_gift` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `giftName` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `giftPrice` decimal(9,2) DEFAULT NULL,
  `createAccount` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `updateAccount` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `updatetime` datetime DEFAULT NULL,
  `status` varchar(5) COLLATE utf8_unicode_ci DEFAULT 'down',
  `picture` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_gift
-- ----------------------------
INSERT INTO `t_gift` VALUES ('1', '赠送小米3', '1999.00', 'admin', '2014-05-22 16:53:34', null, null, 'up', '111');

-- ----------------------------
-- Table structure for `t_hotquery`
-- ----------------------------
DROP TABLE IF EXISTS `t_hotquery`;
CREATE TABLE `t_hotquery` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `key1` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `url` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_hotquery
-- ----------------------------

-- ----------------------------
-- Table structure for `t_index_img`
-- ----------------------------
DROP TABLE IF EXISTS `t_index_img`;
CREATE TABLE `t_index_img` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(245) NOT NULL,
  `picture` varchar(100) NOT NULL,
  `order1` int(11) NOT NULL,
  `desc1` varchar(1145) DEFAULT NULL,
  `link` varchar(145) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_index_img
-- ----------------------------
INSERT INTO `t_index_img` VALUES ('16', 'TOPOT 2014春装新款休闲加厚衬衣 韩版修身磨毛格子男士长袖衬衫', '/image/20180322/1521717982645_3.jpg', '10', '', 'http://localhost:8080/jeeshop/product/10251.html');
INSERT INTO `t_index_img` VALUES ('17', '苹果3', '/image/20180322/1521723506470_3.png', '3', '', 'http://localhost:8080/jeeshop/product/10253.html');
INSERT INTO `t_index_img` VALUES ('18', '小米3', '/image/20180322/1521717982645_3.jpg', '2', '', 'http://localhost:8080/shop-web/product/10263.html');

-- ----------------------------
-- Table structure for `t_indexmenu`
-- ----------------------------
DROP TABLE IF EXISTS `t_indexmenu`;
CREATE TABLE `t_indexmenu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `displayOrder` int(11) DEFAULT NULL,
  `url` varchar(100) NOT NULL,
  `target` varchar(25) NOT NULL,
  `remark` varchar(145) DEFAULT NULL,
  `catalogID` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_indexmenu
-- ----------------------------
INSERT INTO `t_indexmenu` VALUES ('1', '首页', '1', 'index', '_blank', null, '0');
INSERT INTO `t_indexmenu` VALUES ('2', '一楼', '2', '1f', '_blank', null, '1');
INSERT INTO `t_indexmenu` VALUES ('3', '二楼', '3', '2f', '_blank', null, '2');
INSERT INTO `t_indexmenu` VALUES ('4', '三楼', '4', '3f', '_blank', null, '3');
INSERT INTO `t_indexmenu` VALUES ('5', '5楼', '4', '1', '_blank', null, '4');

-- ----------------------------
-- Table structure for `t_keyvalue`
-- ----------------------------
DROP TABLE IF EXISTS `t_keyvalue`;
CREATE TABLE `t_keyvalue` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `key1` varchar(45) NOT NULL,
  `value` varchar(145) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `key_UNIQUE` (`key1`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_keyvalue
-- ----------------------------
INSERT INTO `t_keyvalue` VALUES ('3', 'order_status_init', '未审核');
INSERT INTO `t_keyvalue` VALUES ('4', 'order_status_pass', '已审核');
INSERT INTO `t_keyvalue` VALUES ('5', 'order_status_send', '已发货');
INSERT INTO `t_keyvalue` VALUES ('6', 'order_status_sign', '已签收');
INSERT INTO `t_keyvalue` VALUES ('7', 'order_status_cancel', '已取消');
INSERT INTO `t_keyvalue` VALUES ('8', 'product_orderBy_1', '热门');
INSERT INTO `t_keyvalue` VALUES ('9', 'product_orderBy_2', '价格');
INSERT INTO `t_keyvalue` VALUES ('10', 'product_orderBy_3', '最新');
INSERT INTO `t_keyvalue` VALUES ('11', 'orderpay_paystatus_y', '支付成功');
INSERT INTO `t_keyvalue` VALUES ('12', 'orderpay_paystatus_n', '支付失败');
INSERT INTO `t_keyvalue` VALUES ('13', 'orderpay_paymethod_zfb', '支付宝');
INSERT INTO `t_keyvalue` VALUES ('14', 'orderpay_paymethod_cft', '财付通');
INSERT INTO `t_keyvalue` VALUES ('15', 'orderpay_paymethod_gsyhxyk', '工商银行信用卡');
INSERT INTO `t_keyvalue` VALUES ('16', 'orderpay_paymethod_orderpay_paymethod_cft', '银联在线');
INSERT INTO `t_keyvalue` VALUES ('17', 'order_status_file', '已归档');
INSERT INTO `t_keyvalue` VALUES ('18', 'order_paystatus_n', '未支付');
INSERT INTO `t_keyvalue` VALUES ('19', 'order_paystatus_p', '部分支付');
INSERT INTO `t_keyvalue` VALUES ('20', 'order_paystatus_y', '全部支付');
INSERT INTO `t_keyvalue` VALUES ('21', 'news_status_y', '显示');
INSERT INTO `t_keyvalue` VALUES ('22', 'news_status_n', '不显示');
INSERT INTO `t_keyvalue` VALUES ('23', 'pay_code_alipay', '支付宝支付');
INSERT INTO `t_keyvalue` VALUES ('24', 'pay_code_cft', '财付通');
INSERT INTO `t_keyvalue` VALUES ('25', 'pay_code_yinlian', '银联支付');
INSERT INTO `t_keyvalue` VALUES ('26', 'oss_code_aliyun', '阿里云存储');
INSERT INTO `t_keyvalue` VALUES ('27', 'oss_code_baiduyun', '百度云');
INSERT INTO `t_keyvalue` VALUES ('28', 'oss_code_centenoss', '腾讯云');
INSERT INTO `t_keyvalue` VALUES ('29', 'commentType_code_default', '系统内置评论功能');
INSERT INTO `t_keyvalue` VALUES ('30', 'commentType_code_duoshuo', '多说评论插件');
INSERT INTO `t_keyvalue` VALUES ('31', 'orderpay_paymethod_alipayescow', '支付宝纯担保交易');
INSERT INTO `t_keyvalue` VALUES ('32', 'order_refundStatus_WAIT_SELLER_AGREE', '买家申请退款');
INSERT INTO `t_keyvalue` VALUES ('33', 'order_refundStatus_WAIT_BUYER_RETURN_GOODS', '退款协议达成，等待买家退货');
INSERT INTO `t_keyvalue` VALUES ('34', 'order_refundStatus_WAIT_SELLER_CONFIRM_GOODS', '买家已退货，等待卖家收到退货');
INSERT INTO `t_keyvalue` VALUES ('35', 'order_refundStatus_REFUND_SUCCESS', '退款成功');
INSERT INTO `t_keyvalue` VALUES ('36', 'order_refundStatus_REFUND_CLOSED', '退款关闭');
INSERT INTO `t_keyvalue` VALUES ('37', 'order_refundStatus_SELLER_REFUSE_BUYER', '卖家不同意协议，等待买家修改');
INSERT INTO `t_keyvalue` VALUES ('38', 'account_accountType_qq', 'QQ登陆');
INSERT INTO `t_keyvalue` VALUES ('39', 'account_accountType_sinawb', '新浪微博登陆');
INSERT INTO `t_keyvalue` VALUES ('40', 'account_accountType_alipay', '支付宝快捷登陆');
INSERT INTO `t_keyvalue` VALUES ('41', 'account_rank_R1', '普通会员');
INSERT INTO `t_keyvalue` VALUES ('42', 'account_rank_R2', '铜牌会员');
INSERT INTO `t_keyvalue` VALUES ('43', 'account_rank_R3', '银牌会员');
INSERT INTO `t_keyvalue` VALUES ('44', 'account_rank_R4', '金牌会员');
INSERT INTO `t_keyvalue` VALUES ('45', 'account_rank_R5', '钻石会员');

-- ----------------------------
-- Table structure for `t_lable`
-- ----------------------------
DROP TABLE IF EXISTS `t_lable`;
CREATE TABLE `t_lable` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_lable
-- ----------------------------
INSERT INTO `t_lable` VALUES ('23', '好玩');
INSERT INTO `t_lable` VALUES ('24', '好看');
INSERT INTO `t_lable` VALUES ('25', '好笑');
INSERT INTO `t_lable` VALUES ('26', '好吃');

-- ----------------------------
-- Table structure for `t_menu`
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) NOT NULL,
  `url` varchar(100) CHARACTER SET utf8 NOT NULL,
  `name` varchar(45) COLLATE utf8_unicode_ci NOT NULL COMMENT '111',
  `orderNum` int(11) NOT NULL DEFAULT '0',
  `type` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=95 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_menu
-- ----------------------------
INSERT INTO `t_menu` VALUES ('1', '0', '', '系统管理', '20', 'module');
INSERT INTO `t_menu` VALUES ('2', '1', '/manage/menu/selectList?init=y', '资源管理', '3', 'page');
INSERT INTO `t_menu` VALUES ('3', '1', '/manage/user/selectList?init=y', '用户管理', '1', 'page');
INSERT INTO `t_menu` VALUES ('4', '1', '/manage/role/selectList?init=y', '角色管理', '2', 'page');
INSERT INTO `t_menu` VALUES ('5', '1', '/manage/user/toChangePwd', '修改密码', '4', 'page');
INSERT INTO `t_menu` VALUES ('6', '0', '', '内容管理', '4', 'module');
INSERT INTO `t_menu` VALUES ('7', '6', '/manage/news/selectList?init=y&type=help', '文章管理', '3', 'page');
INSERT INTO `t_menu` VALUES ('9', '6', '/manage/indexImg/selectList', '门户滚动图片', '6', 'page');
INSERT INTO `t_menu` VALUES ('11', '0', '', '商品管理', '1', 'module');
INSERT INTO `t_menu` VALUES ('14', '11', '/manage/product/selectList?init=y', '商品管理', '4', 'page');
INSERT INTO `t_menu` VALUES ('18', '0', '', '订单管理', '2', 'module');
INSERT INTO `t_menu` VALUES ('19', '18', '/manage/order/selectList?init=y', '订单管理', '1', 'page');
INSERT INTO `t_menu` VALUES ('23', '0', '', '会员管理', '3', 'module');
INSERT INTO `t_menu` VALUES ('24', '23', '/manage/account/selectList?init=y', '会员管理', '1', 'page');
INSERT INTO `t_menu` VALUES ('32', '0', '', '报表统计', '6', 'module');
INSERT INTO `t_menu` VALUES ('34', '32', '/manage/report/orderSales?init=y', '订单销售统计', '2', 'page');
INSERT INTO `t_menu` VALUES ('35', '32', '/manage/report/productSales?init=y', '商品销售统计', '3', 'page');
INSERT INTO `t_menu` VALUES ('36', '1', '/manage/systemlog/selectList?init=y', '日志管理', '8', 'page');
INSERT INTO `t_menu` VALUES ('38', '1', '/manage/express/selectList?init=y', '配送方式', '6', 'page');
INSERT INTO `t_menu` VALUES ('40', '6', '/manage/advert/selectList?init=y', '广告管理', '8', 'page');
INSERT INTO `t_menu` VALUES ('43', '6', '/manage/catalog/selectList?init=y&type=a', '文章分类', '1', 'page');
INSERT INTO `t_menu` VALUES ('45', '6', '/manage/navigation/selectList?init=y', '友情链接', '5', 'page');
INSERT INTO `t_menu` VALUES ('46', '23', '/manage/comment/selectList?init=y', '评论管理', '3', 'page');
INSERT INTO `t_menu` VALUES ('48', '1', '/manage/area/areaTree', '区域管理', '5', 'page');
INSERT INTO `t_menu` VALUES ('49', '1', '/manage/systemSetting/toEdit?init=y', '系统设置', '9', 'page');
INSERT INTO `t_menu` VALUES ('51', '1', '/manage/keyvalue/selectList?init=y', '键值对管理', '11', 'page');
INSERT INTO `t_menu` VALUES ('52', '3', '/manage/user/selectList', '查询', '1', 'button');
INSERT INTO `t_menu` VALUES ('55', '3', '/manage/user/insert', '添加', '2', 'button');
INSERT INTO `t_menu` VALUES ('56', '3', '/manage/user/deletes', '删除', '3', 'button');
INSERT INTO `t_menu` VALUES ('57', '0', '/manage/user/initManageIndex', '首页', '-100', 'page');
INSERT INTO `t_menu` VALUES ('58', '4', '/manage/role/selectList', '查询', '1', 'button');
INSERT INTO `t_menu` VALUES ('59', '4', '/manage/role/insert', '添加', '2', 'button');
INSERT INTO `t_menu` VALUES ('60', '4', '/manage/role/deletes', '删除', '3', 'button');
INSERT INTO `t_menu` VALUES ('61', '2', '/manage/menu/selectList', '查询', '1', 'button');
INSERT INTO `t_menu` VALUES ('62', '2', '/manage/menu/insert', '添加', '2', 'button');
INSERT INTO `t_menu` VALUES ('63', '2', '/manage/menu/deletes', '删除', '3', 'button');
INSERT INTO `t_menu` VALUES ('64', '11', '/manage/attribute/selectList?init=y&pid=0', '商品属性', '5', 'page');
INSERT INTO `t_menu` VALUES ('65', '11', '/manage/catalog/selectList?init=y&type=p', '商品目录', '2', 'page');
INSERT INTO `t_menu` VALUES ('66', '11', '/manage/attribute/selectList?init=y&pid=-1', '商品参数', '6', 'page');
INSERT INTO `t_menu` VALUES ('69', '1', '/manage/cache/cache.jsp', '缓存管理', '12', 'page');
INSERT INTO `t_menu` VALUES ('71', '6', '/manage/news/selectList?init=y&type=notice', '公告管理', '2', 'page');
INSERT INTO `t_menu` VALUES ('73', '14', '/manage/product/selectList', '查询', '1', 'button');
INSERT INTO `t_menu` VALUES ('74', '14', '/manage/product/toAdd', '添加', '2', 'button');
INSERT INTO `t_menu` VALUES ('75', '14', '/manage/product/deletes', '删除', '3', 'button');
INSERT INTO `t_menu` VALUES ('76', '14', '/manage/product/up', '上架', '4', 'button');
INSERT INTO `t_menu` VALUES ('77', '14', '/manage/product/down', '下架', '5', 'button');
INSERT INTO `t_menu` VALUES ('78', '14', '/manage/product/toEdit', '编辑', '1', 'button');
INSERT INTO `t_menu` VALUES ('80', '0', '', '第三方插件', '8', 'module');
INSERT INTO `t_menu` VALUES ('81', '80', '/manage/pay/selectList?init=y', '支付管理', '1', 'page');
INSERT INTO `t_menu` VALUES ('82', '80', '/manage/oss/selectList?init=y', '存储插件管理', '2', 'page');
INSERT INTO `t_menu` VALUES ('83', '80', '/manage/commentType/selectList?init=y', '评论插件管理', '3', 'page');
INSERT INTO `t_menu` VALUES ('84', '18', '/manage/order/selectList?init=y&refundStatus=WAIT_SELLER_AGREE', '退款管理', '2', 'page');
INSERT INTO `t_menu` VALUES ('85', '18', '/manage/order/selectList?init=y&refundStatus=WAIT_SELLER_CONFIRM_GOODS', '退货管理', '3', 'page');
INSERT INTO `t_menu` VALUES ('86', '11', '/manage/emailNotifyProduct/selectList?init=y', '到货通知', '11', 'page');
INSERT INTO `t_menu` VALUES ('87', '6', '/manage/notifyTemplate/selectList?init=y', '通知模板管理', '13', 'page');
INSERT INTO `t_menu` VALUES ('88', '6', '/manage/email/selectList?init=y', '发送邮件列表', '14', 'page');
INSERT INTO `t_menu` VALUES ('90', '23', '/manage/accountRank/selectList?init=y', '等级管理', '0', 'page');
INSERT INTO `t_menu` VALUES ('91', '0', '', '活动管理', '19', 'module');
INSERT INTO `t_menu` VALUES ('92', '91', '/manage/activity/selectList?init=y', '商品促销', '1', 'page');
INSERT INTO `t_menu` VALUES ('93', '11', '/manage/gift/selectList?init=y', '赠品管理', '8', 'page');
INSERT INTO `t_menu` VALUES ('94', '6', '/manage/hotquery/selectList?init=y', '热门查询管理', '20', 'page');

-- ----------------------------
-- Table structure for `t_navigation`
-- ----------------------------
DROP TABLE IF EXISTS `t_navigation`;
CREATE TABLE `t_navigation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `target` varchar(45) NOT NULL DEFAULT '_blank',
  `position` varchar(45) NOT NULL DEFAULT 'bottom',
  `desc1` varchar(145) DEFAULT NULL,
  `order1` int(11) DEFAULT NULL,
  `http` varchar(70) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_navigation
-- ----------------------------
INSERT INTO `t_navigation` VALUES ('2', '淘宝', '_blank', 'bottom', null, '1', 'www.taobao.com');
INSERT INTO `t_navigation` VALUES ('3', '京东商城', '_blank', 'bottom', null, '2', 'www.jd.com');
INSERT INTO `t_navigation` VALUES ('4', '易讯', '_blank', 'bottom', null, '3', 'www.yixun.com');
INSERT INTO `t_navigation` VALUES ('5', '阿里巴巴', '_blank', 'bottom', null, '4', 'www.alibaba.com');
INSERT INTO `t_navigation` VALUES ('6', '阿里巴中国站', '_blank', 'bottom', null, '0', 'www.1688.com');
INSERT INTO `t_navigation` VALUES ('7', '支付宝', '_blank', 'bottom', null, '5', 'www.alipay.com');
INSERT INTO `t_navigation` VALUES ('8', '阿里云计算', '_blank', 'bottom', null, '6', 'www.aliyun.com');
INSERT INTO `t_navigation` VALUES ('9', '来往', '_blank', 'bottom', null, '7', 'www.laiwang.com');
INSERT INTO `t_navigation` VALUES ('11', '万网', '_blank', 'bottom', null, '9', 'www.net.cn');
INSERT INTO `t_navigation` VALUES ('12', '一号店', '_blank', 'bottom', null, '10', 'www.yhd.com');
INSERT INTO `t_navigation` VALUES ('13', '天猫商城', '_blank', 'bottom', null, '11', 'www.tmall.com');
INSERT INTO `t_navigation` VALUES ('14', '当当网', '_blank', 'bottom', null, '12', 'www.dangdang.com');
INSERT INTO `t_navigation` VALUES ('15', '苏宁', '_blank', 'bottom', null, '13', 'www.suning.com');
INSERT INTO `t_navigation` VALUES ('16', '银泰网', '_blank', 'bottom', null, '15', 'www.yintai.com');
INSERT INTO `t_navigation` VALUES ('17', '梦芭莎', '_blank', 'bottom', null, '16', 'www.moonbasa.com');
INSERT INTO `t_navigation` VALUES ('18', '红孩子', '_blank', 'bottom', null, '17', 'redbaby.suning.com');
INSERT INTO `t_navigation` VALUES ('19', '亚马逊', '_blank', 'bottom', null, '18', 'www.amazon.cn');
INSERT INTO `t_navigation` VALUES ('20', '财付通', '_blank', 'bottom', null, '19', 'www.tenpay.com');
INSERT INTO `t_navigation` VALUES ('21', '快递查询', '_blank', 'bottom', null, '20', 'www.kuaidi100.com');

-- ----------------------------
-- Table structure for `t_news`
-- ----------------------------
DROP TABLE IF EXISTS `t_news`;
CREATE TABLE `t_news` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(45) DEFAULT NULL,
  `title` varchar(45) DEFAULT NULL,
  `title2` varchar(45) DEFAULT NULL,
  `content` longtext,
  `createtime` datetime DEFAULT NULL,
  `updatetime` datetime DEFAULT NULL,
  `readerCount` int(11) DEFAULT '0',
  `status` varchar(2) DEFAULT 'n',
  `catalogID` varchar(15) DEFAULT NULL,
  `lableID` varchar(100) DEFAULT '0',
  `createAccount` varchar(45) NOT NULL,
  `order1` int(11) DEFAULT '0',
  `code` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_UNIQUE` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_news
-- ----------------------------
INSERT INTO `t_news` VALUES ('53', 'help', '购物流程', '', '购物流程', '2013-10-20 23:34:26', '2014-04-21 11:51:44', '0', 'y', '47', null, 'admin', '1', 'gwlc');
INSERT INTO `t_news` VALUES ('54', 'help', '新手帮助', '', '<p class=\"p0\" style=\"text-align:justify;\">\r\n	1、登陆\r\n</p>\r\n<p class=\"p0\" style=\"text-align:justify;\">\r\n	可以点击顶上角的登陆链接，转到登陆页面。如果没有账号则需要注册。或者使用第三方信任登陆。\r\n</p>\r\n<p class=\"p0\" style=\"text-align:justify;\">\r\n	<img src=\"http://myshopxx.oss.aliyuncs.com/attached/image/20140421/1398051870352_3.png\" width=\"553\" height=\"258\" alt=\"\" />&nbsp;\r\n</p>\r\n<p class=\"p0\" style=\"text-align:justify;\">\r\n	&nbsp;\r\n</p>\r\n<p class=\"p0\" style=\"text-align:justify;\">\r\n	2<span>、用户注册页面如下。</span> \r\n</p>\r\n<p class=\"p0\" style=\"text-align:justify;\">\r\n	<img src=\"http://myshopxx.oss.aliyuncs.com/attached/image/20140421/1398051878430_3.png\" width=\"554\" height=\"367\" alt=\"\" />&nbsp;\r\n</p>\r\n<p class=\"p0\" style=\"text-align:justify;\">\r\n	&nbsp;\r\n</p>\r\n<p class=\"p0\" style=\"text-align:justify;\">\r\n	3、如果您忘记了密码，则可以通过登陆页面的&nbsp;“密码找回？”功能，找回密码。系统会发送密码找回的邮件到您的邮箱。\r\n</p>\r\n<p class=\"p0\" style=\"text-align:justify;\">\r\n	&nbsp;\r\n</p>\r\n<p class=\"p0\" style=\"text-align:justify;\">\r\n	&nbsp;\r\n</p>\r\n<p class=\"p0\" style=\"text-align:justify;\">\r\n	<img src=\"http://myshopxx.oss.aliyuncs.com/attached/image/20140421/1398051862399_3.png\" width=\"554\" height=\"269\" alt=\"\" />&nbsp;\r\n</p>\r\n<p class=\"p0\" style=\"text-align:justify;\">\r\n	&nbsp;\r\n</p>\r\n<p class=\"p0\" style=\"text-align:justify;\">\r\n	&nbsp;\r\n</p>\r\n<p class=\"p0\" style=\"text-align:justify;\">\r\n	4、查找商品\r\n</p>\r\n<p class=\"p0\" style=\"text-align:justify;\">\r\n	您可以将鼠标移动到左侧的吸附菜单上，或直接点击导航栏来选择您想要购买的商品类目。\r\n</p>\r\n<p class=\"p0\" style=\"text-align:justify;\">\r\n	<img src=\"http://myshopxx.oss.aliyuncs.com/attached/image/20140421/1398051857586_3.png\" width=\"554\" height=\"376\" alt=\"\" />&nbsp;\r\n</p>\r\n<p class=\"p0\" style=\"text-align:justify;\">\r\n	&nbsp;\r\n</p>\r\n<p class=\"p0\" style=\"text-align:justify;\">\r\n	&nbsp;\r\n</p>\r\n<p class=\"p0\" style=\"text-align:justify;\">\r\n	&nbsp;\r\n</p>\r\n<p class=\"p0\" style=\"text-align:justify;\">\r\n	5<span>、商品收藏。可以点击商品明细页的收藏按钮，来添加您想要的商品到您的收藏夹，不过此功能需要登录。</span> \r\n</p>\r\n<p class=\"p0\" style=\"text-align:justify;\">\r\n	<img src=\"http://myshopxx.oss.aliyuncs.com/attached/image/20140421/1398051894024_3.png\" width=\"554\" height=\"333\" alt=\"\" />&nbsp;\r\n</p>\r\n<p class=\"p0\" style=\"text-align:justify;\">\r\n	&nbsp;\r\n</p>\r\n<p class=\"p0\" style=\"text-align:justify;\">\r\n	&nbsp;\r\n</p>\r\n<p class=\"p0\" style=\"text-align:justify;\">\r\n	&nbsp;\r\n</p>\r\n<p class=\"p0\" style=\"text-align:justify;\">\r\n	&nbsp;\r\n</p>\r\n<p class=\"p0\" style=\"text-align:justify;\">\r\n	&nbsp;\r\n</p>\r\n<p class=\"p0\" style=\"text-align:justify;\">\r\n	&nbsp;\r\n</p>\r\n<p class=\"p0\" style=\"text-align:justify;\">\r\n	6<span>、咨询卖家（店小二）。同样的，如果您在购买的过程中对商品有任何的疑问，您也可以咨询卖家，点击“给我留言”将弹出卖家的</span><span>QQ</span><span>临时会话框，您可以方便的和卖家进行沟通。</span> \r\n</p>\r\n<p class=\"p0\" style=\"text-align:justify;\">\r\n	<img src=\"http://myshopxx.oss.aliyuncs.com/attached/image/20140421/1398051885352_3.png\" width=\"554\" height=\"360\" alt=\"\" />&nbsp;\r\n</p>\r\n<p class=\"p0\" style=\"text-align:justify;\">\r\n	&nbsp;\r\n</p>\r\n<p class=\"p0\" style=\"text-align:justify;\">\r\n	&nbsp;\r\n</p>\r\n<p class=\"p0\" style=\"text-align:justify;\">\r\n	&nbsp;\r\n</p>\r\n<p class=\"p0\" style=\"text-align:justify;\">\r\n	&nbsp;\r\n</p>\r\n<p class=\"p0\" style=\"text-align:justify;\">\r\n	7<span>、购物车操作。您可以在提交订单前对购物车中的商品进行任何的增减操作，或者删除某个商品。确认无误后点击提交订单按钮。</span> \r\n</p>\r\n<p class=\"p0\" style=\"text-align:justify;\">\r\n	&nbsp;\r\n</p>\r\n<p class=\"p0\" style=\"text-align:justify;\">\r\n	<img src=\"http://myshopxx.oss.aliyuncs.com/attached/image/20140421/1398051844211_3.png\" width=\"554\" height=\"280\" alt=\"\" />&nbsp;\r\n</p>\r\n<p class=\"p0\" style=\"text-align:justify;\">\r\n	&nbsp;\r\n</p>\r\n<p class=\"p0\" style=\"text-align:justify;\">\r\n	&nbsp;\r\n</p>\r\n<p class=\"p0\" style=\"text-align:justify;\">\r\n	&nbsp;\r\n</p>\r\n<p class=\"p0\" style=\"text-align:justify;\">\r\n	9<span>、确认订单页面，选择配送方式后点击提交订单则系统会跳转到支付宝网关进行安全支付。同时在此页面也能输入您的附加要求，卖家在处理您的订单时候会和您进行确认。</span> \r\n</p>\r\n<p class=\"p0\" style=\"text-align:justify;\">\r\n	&nbsp;\r\n</p>\r\n<p class=\"p0\" style=\"text-align:justify;\">\r\n	<img src=\"http://myshopxx.oss.aliyuncs.com/attached/image/20140421/1398051873055_3.png\" width=\"554\" height=\"386\" alt=\"\" />&nbsp;\r\n</p>\r\n<p class=\"p0\" style=\"text-align:justify;\">\r\n	&nbsp;\r\n</p>\r\n<p class=\"p0\" style=\"text-align:justify;\">\r\n	&nbsp;\r\n</p>\r\n<p class=\"p0\" style=\"text-align:justify;\">\r\n	&nbsp;\r\n</p>', '2013-12-01 01:11:21', '2014-04-21 11:55:05', '0', 'y', '46', null, 'admin', '1', 'xsbz');
INSERT INTO `t_news` VALUES ('56', 'help', '支付宝支付', '', '支付宝支付', '2013-12-01 17:08:30', '2014-02-25 10:03:47', '0', 'y', '48', null, 'admin', '1', 'zfbzf');
INSERT INTO `t_news` VALUES ('58', 'help', '关于我们', '', '关于我们', '2013-12-01 17:09:02', '2014-01-25 17:32:00', '0', 'y', '55', null, 'admin', '1', 'gywm');
INSERT INTO `t_news` VALUES ('62', 'notice', '2013双十一大量打折促销活动，2013双十一大量打折促销活动，', '', '2013双十一大量打折促销活动', '2014-01-27 15:47:18', '2014-01-27 16:40:51', '0', 'y', null, null, 'admin', '0', null);
INSERT INTO `t_news` VALUES ('63', 'notice', '2013双十二大量打折促销活动', '', '2013双十二大量打折促销活动', '2014-01-27 16:40:57', '2014-01-27 16:40:57', '0', 'y', null, null, 'admin', '0', null);
INSERT INTO `t_news` VALUES ('64', 'notice', '2013元旦又有打折惊喜了，时间不多欢迎速速抢购', '', '2013元旦又有打折惊喜了，时间不多欢迎速速抢购', '2014-01-27 16:41:03', '2014-01-27 16:41:03', '0', 'y', null, null, 'admin', '0', null);
INSERT INTO `t_news` VALUES ('65', 'notice', '马年到年货疯抢，大部分商品半价', '', '马年到年货疯抢，大部分商品半价', '2014-01-27 16:41:09', '2014-01-27 16:41:09', '0', 'y', null, null, 'admin', '0', null);
INSERT INTO `t_news` VALUES ('66', 'notice', '201309至201311大量水果打折促销活动，欢迎抢购', '', '201309至201311大量水果打折促销活动，欢迎抢购', '2014-01-27 16:41:14', '2014-01-27 16:41:14', '0', 'y', null, null, 'admin', '0', null);
INSERT INTO `t_news` VALUES ('67', 'notice', '工行信用卡18元抢购水果活动', '', '工行信用卡18元抢购水果活动', '2014-01-27 16:41:20', '2014-01-27 16:41:20', '0', 'y', null, null, 'admin', '0', null);
INSERT INTO `t_news` VALUES ('68', 'notice', '2014爱在夏季水果大甩卖活动开启了', '', '2014爱在夏季水果大甩卖活动开启了', '2014-01-27 16:41:25', '2014-01-27 16:41:25', '0', 'y', null, null, 'admin', '0', null);
INSERT INTO `t_news` VALUES ('69', 'notice', '201402问卷调查赢取新年大礼包啦！', '', '<p>\r\n	201402问卷调查赢取新年大礼包啦！\r\n</p>\r\n<p>\r\n	<br />\r\n</p>\r\n<p>\r\n	活动参与方式，点击链接<a href=\"http://127.0.0.1:8080/jeeshopFront/question/show.html?id=1\" target=\"_blank\">http://127.0.0.1:8080/jeeshopFront/question/show.html?id=1</a>进行答题，答题完后将有机会进行抽奖，还有机会赢取IPHONE4S哦。\r\n</p>\r\n<p>\r\n	快快来参与吧。\r\n</p>', '2014-02-09 15:35:43', '2014-04-13 18:18:07', '0', 'y', null, null, 'admin', '0', null);
INSERT INTO `t_news` VALUES ('70', null, '1', '', '11', '2014-02-24 22:21:38', '2014-02-24 22:21:38', '0', 'n', '46', null, 'admin', '1', '1');
INSERT INTO `t_news` VALUES ('72', 'help', '常见问题', '', '常见问题', '2014-02-25 10:01:57', '2014-04-21 11:54:33', '0', 'y', '47', null, 'admin', '2', 'cjwt');
INSERT INTO `t_news` VALUES ('73', 'help', '快递送货', '', '快递送货', '2014-02-25 14:05:12', '2014-04-21 11:53:34', '0', 'y', '51', null, 'admin', '1', 'kdsh');
INSERT INTO `t_news` VALUES ('74', 'help', '注册协议', null, '<span style=\"color:#333333;font-family:Ubuntu, Tahoma, \'Helvetica Neue\', Helvetica, Arial, sans-serif;line-height:2;background-color:#FFFFFF;font-size:14px;\">尊敬的用户欢迎您注册成为本网站会员。请用户仔细阅读以下全部内容。如用户不同意本服务条款任意内容，请不要注册或使用本网站服务。如用户通过本网站注册程序，即表示用户与本网站已达成协议，自愿接受本服务条款的所有内容。此后，用户不得以未阅读本服务条款内容作任何形式的抗辩。</span><br />\r\n<span style=\"color:#333333;font-family:Ubuntu, Tahoma, \'Helvetica Neue\', Helvetica, Arial, sans-serif;line-height:2;background-color:#FFFFFF;font-size:14px;\">一、本站服务条款的确认和接纳</span><br />\r\n<span style=\"color:#333333;font-family:Ubuntu, Tahoma, \'Helvetica Neue\', Helvetica, Arial, sans-serif;line-height:2;background-color:#FFFFFF;font-size:14px;\">本网站涉及的各项服务的所有权和运作权归本网站所有。本网站所提供的服务必须按照其发布的服务条款和操作规则严格执行。本服务条款的效力范围及于本网站的一切产品和服务，用户在享受本网站的任何服务时，应当受本服务条款的约束。</span><br />\r\n<span style=\"color:#333333;font-family:Ubuntu, Tahoma, \'Helvetica Neue\', Helvetica, Arial, sans-serif;line-height:2;background-color:#FFFFFF;font-size:14px;\">二、服务简介</span><br />\r\n<span style=\"color:#333333;font-family:Ubuntu, Tahoma, \'Helvetica Neue\', Helvetica, Arial, sans-serif;line-height:2;background-color:#FFFFFF;font-size:14px;\">本网站运用自己的操作系统通过国际互联网络为用户提供各项服务。用户必须: 1. 提供设备，如个人电脑、手机或其他上网设备。 2. 个人上网和支付与此服务有关的费用。</span><br />\r\n<span style=\"color:#333333;font-family:Ubuntu, Tahoma, \'Helvetica Neue\', Helvetica, Arial, sans-serif;line-height:2;background-color:#FFFFFF;font-size:14px;\">三、用户在不得在本网站上发布下列违法信息</span><br />\r\n<span style=\"color:#333333;font-family:Ubuntu, Tahoma, \'Helvetica Neue\', Helvetica, Arial, sans-serif;line-height:2;background-color:#FFFFFF;font-size:14px;\">1. 反对宪法所确定的基本原则的； 2. 危害国家安全，泄露国家秘密，颠覆国家政权，破坏国家统一的； 3. 损害国家荣誉和利益的； 4. 煽动民族仇恨、民族歧视，破坏民族团结的； 5. 破坏国家宗教政策，宣扬邪教和封建迷信的； 6. 散布谣言，扰乱社会秩序，破坏社会稳定的； 7. 散布淫秽、色情、赌博、暴力、凶杀、恐怖或者教唆犯罪的； 8. 侮辱或者诽谤他人，侵害他人合法权益的； 9. 含有法律、行政法规禁止的其他内容的。</span><br />\r\n<span style=\"color:#333333;font-family:Ubuntu, Tahoma, \'Helvetica Neue\', Helvetica, Arial, sans-serif;line-height:2;background-color:#FFFFFF;font-size:14px;\">四、有关个人资料</span><br />\r\n<span style=\"color:#333333;font-family:Ubuntu, Tahoma, \'Helvetica Neue\', Helvetica, Arial, sans-serif;line-height:2;background-color:#FFFFFF;font-size:14px;\">用户同意: 1. 提供及时、详尽及准确的个人资料。 2. 同意接收来自本网站的信息。 3. 不断更新注册资料，符合及时、详尽准确的要求。所有原始键入的资料将引用为注册资料。 4. 本网站不公开用户的姓名、地址、电子邮箱和笔名。除以下情况外: a) 用户授权本站透露这些信息。 b) 相应的法律及程序要求本站提供用户的个人资料。</span><br />\r\n<span style=\"color:#333333;font-family:Ubuntu, Tahoma, \'Helvetica Neue\', Helvetica, Arial, sans-serif;line-height:2;background-color:#FFFFFF;font-size:14px;\">五、服务条款的修改</span><br />\r\n<span style=\"color:#333333;font-family:Ubuntu, Tahoma, \'Helvetica Neue\', Helvetica, Arial, sans-serif;line-height:2;background-color:#FFFFFF;font-size:14px;\">本网站有权在必要时修改服务条款，一旦条款及服务内容产生变动，本网站将会在重要页面上提示修改内容。如果不同意所改动的内容，用户可以主动取消获得的本网站信息服务。如果用户继续享用本网站信息服务，则视为接受服务条款的变动。</span><br />\r\n<span style=\"color:#333333;font-family:Ubuntu, Tahoma, \'Helvetica Neue\', Helvetica, Arial, sans-serif;line-height:2;background-color:#FFFFFF;font-size:14px;\">六、用户隐私制度</span><br />\r\n<span style=\"color:#333333;font-family:Ubuntu, Tahoma, \'Helvetica Neue\', Helvetica, Arial, sans-serif;line-height:2;background-color:#FFFFFF;font-size:14px;\">尊重用户个人隐私是本网站的一项基本政策。所以，本网站一定不会在未经合法用户授权时公开、编辑或透露其注册资料及保存在本网站中的非公开内容，除非有法律许可要求或本网站在诚信的基础上认为透露这些信息在以下四种情况是必要的: 1. 遵守有关法律规定，遵从本网站合法服务程序。 2. 保持维护本网站的商标所有权。 3. 在紧急情况下竭力维护用户个人和社会大众的隐私安全。 4. 符合其他相关的要求。</span><br />\r\n<span style=\"color:#333333;font-family:Ubuntu, Tahoma, \'Helvetica Neue\', Helvetica, Arial, sans-serif;line-height:2;background-color:#FFFFFF;font-size:14px;\">七、用户的帐号、密码和安全性</span><br />\r\n<span style=\"color:#333333;font-family:Ubuntu, Tahoma, \'Helvetica Neue\', Helvetica, Arial, sans-serif;line-height:2;background-color:#FFFFFF;font-size:14px;\">用户一旦注册成功，将获得一个密码和用户名。用户需谨慎合理的保存、使用用户名和密码。如果你不保管好自己的帐号和密码安全，将负全部责任。另外，每个用户都要对其帐户中的所有活动和事件负全责。你可随时根据指示改变你的密码。用户若发现任何非法使用用户帐号或存在安全漏洞的情况，请立即通告本网站。 八、 拒绝提供担保 用户明确同意信息服务的使用由用户个人承担风险。本网站不担保服务不会受中断，对服务的及时性，安全性，出错发生都不作担保，但会在能力范围内，避免出错。</span><br />\r\n<span style=\"color:#333333;font-family:Ubuntu, Tahoma, \'Helvetica Neue\', Helvetica, Arial, sans-serif;line-height:2;background-color:#FFFFFF;font-size:14px;\">九、有限责任</span><br />\r\n<span style=\"color:#333333;font-family:Ubuntu, Tahoma, \'Helvetica Neue\', Helvetica, Arial, sans-serif;line-height:2;background-color:#FFFFFF;font-size:14px;\">如因不可抗力或其它本站无法控制的原因使本站销售系统崩溃或无法正常使用导致网上交易无法完成或丢失有关的信息、记录等本站会尽可能合理地协助处理善后事宜，并努力使客户免受经济损失，同时会尽量避免这种损害的发生。</span><br />\r\n<span style=\"color:#333333;font-family:Ubuntu, Tahoma, \'Helvetica Neue\', Helvetica, Arial, sans-serif;line-height:2;background-color:#FFFFFF;font-size:14px;\">十、用户信息的储存和限制</span><br />\r\n<span style=\"color:#333333;font-family:Ubuntu, Tahoma, \'Helvetica Neue\', Helvetica, Arial, sans-serif;line-height:2;background-color:#FFFFFF;font-size:14px;\">本站有判定用户的行为是否符合国家法律法规规定及本站服务条款权利，如果用户违背本网站服务条款的规定，本网站有权中断对其提供服务的权利。</span><br />\r\n<span style=\"color:#333333;font-family:Ubuntu, Tahoma, \'Helvetica Neue\', Helvetica, Arial, sans-serif;line-height:2;background-color:#FFFFFF;font-size:14px;\">十一、用户管理</span><br />\r\n<span style=\"color:#333333;font-family:Ubuntu, Tahoma, \'Helvetica Neue\', Helvetica, Arial, sans-serif;line-height:2;background-color:#FFFFFF;font-size:14px;\">用户单独承担发布内容的责任。用户对服务的使用是根据所有适用于本站的国家法律、地方法律和国际法律标准的。用户必须遵循: 1. 使用网络服务不作非法用途。 2. 不干扰或混乱网络服务。 3. 遵守所有使用网络服务的网络协议、规定、程序和惯例。 用户须承诺不传输任何非法的、骚扰性的、中伤他人的、辱骂性的、恐性的、伤害性的、庸俗的，淫秽等信息资料。另外，用户也不能传输何教唆他人构成犯罪行为的资料；不能传输助长国内不利条件和涉及国家安全的资料；不能传输任何不符合当地法规、国家法律和国际法律的资料。未经许可而非法进入其它电脑系统是禁止的。 若用户的行为不符合以上提到的服务条款，本站将作出独立判断立即取消用户服务帐号。用户需对自己在网上的行为承担法律责任。用户若在本站上散布和传播反动、色情或其它违反国家法律的信息，本站的系统记录有可能作为用户违反法律的证据。</span><br />\r\n<span style=\"color:#333333;font-family:Ubuntu, Tahoma, \'Helvetica Neue\', Helvetica, Arial, sans-serif;line-height:2;background-color:#FFFFFF;font-size:14px;\">十二、通告</span><br />\r\n<span style=\"color:#333333;font-family:Ubuntu, Tahoma, \'Helvetica Neue\', Helvetica, Arial, sans-serif;line-height:2;background-color:#FFFFFF;font-size:14px;\">所有发给用户的通告都可通过重要页面的公告或电子邮件或常规的信件传送。服务条款的修改、服务变更、或其它重要事件的通告都会以此形式进行。</span><br />\r\n<span style=\"color:#333333;font-family:Ubuntu, Tahoma, \'Helvetica Neue\', Helvetica, Arial, sans-serif;line-height:2;background-color:#FFFFFF;font-size:14px;\">十三、信息内容的所有权</span><br />\r\n<span style=\"color:#333333;font-family:Ubuntu, Tahoma, \'Helvetica Neue\', Helvetica, Arial, sans-serif;line-height:2;background-color:#FFFFFF;font-size:14px;\">本网站定义的信息内容包括: 文字、软件、声音、相片、录象、图表；在广告中全部内容；本网站为用户提供的其它信息。所有这些内容受版权、商标、标签和其它财产所有权法律的保护。所以，用户只能在本网站和广告商授权下才能使用这些内容，而不能擅自复制、再造这些内容、或创造与内容有关的派生产品。本站所有的文章版权归原文作者和本站共同所有，任何人需要转载本站的文章，必须征得原文作者或本站授权。</span><br />\r\n<span style=\"color:#333333;font-family:Ubuntu, Tahoma, \'Helvetica Neue\', Helvetica, Arial, sans-serif;line-height:2;background-color:#FFFFFF;font-size:14px;\">十四、法律</span><br />\r\n<span style=\"color:#333333;font-family:Ubuntu, Tahoma, \'Helvetica Neue\', Helvetica, Arial, sans-serif;line-height:2;background-color:#FFFFFF;font-size:14px;\">本协议的订立、执行和解释及争议的解决均应适用中华人民共和国的法律。用户和本网站一致同意服从本网站所在地有管辖权的法院管辖。如发生本网站服务条款与中华人民共和国法律相抵触时，则这些条款将完全按法律规定重新解释，而其它条款则依旧保持对用户的约束力。</span>', '2014-05-06 13:30:20', '2014-05-06 13:30:20', '0', 'n', '46', null, 'admin', '2', 'zcxy');

-- ----------------------------
-- Table structure for `t_notice`
-- ----------------------------
DROP TABLE IF EXISTS `t_notice`;
CREATE TABLE `t_notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `content` varchar(4000) COLLATE utf8_unicode_ci NOT NULL,
  `createtime` varchar(45) COLLATE utf8_unicode_ci DEFAULT 'now()',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_notice
-- ----------------------------
INSERT INTO `t_notice` VALUES ('2', '2013双十一大量打折促销活动', '2013双十一大量打折促销活动', '2014-01-25 20:13:03');
INSERT INTO `t_notice` VALUES ('3', '2013双十二大量打折促销活动', '2013双十二大量打折促销活动', '2014-01-25 20:13:27');
INSERT INTO `t_notice` VALUES ('4', '2013元旦又有打折惊喜了，时间不多欢迎速速抢购', '2013元旦又有打折惊喜了，时间不多欢迎速速抢购', '2014-01-27 13:45:19');
INSERT INTO `t_notice` VALUES ('5', '马年到年货疯抢，大部分商品半价', '马年到年货疯抢，大部分商品半价', '2014-01-27 13:54:39');
INSERT INTO `t_notice` VALUES ('6', '201309至201311大量水果打折促销活动，欢迎抢购', '201309至201311大量水果打折促销活动，欢迎抢购', '2014-01-27 13:55:45');
INSERT INTO `t_notice` VALUES ('7', '工行信用卡18元抢购水果活动', '工行信用卡18元抢购水果活动', '2014-01-27 13:59:31');
INSERT INTO `t_notice` VALUES ('8', '2014爱在夏季水果大甩卖活动开启了', '2014爱在夏季水果大甩卖活动开启了', '2014-01-27 14:00:19');

-- ----------------------------
-- Table structure for `t_notifytemplate`
-- ----------------------------
DROP TABLE IF EXISTS `t_notifytemplate`;
CREATE TABLE `t_notifytemplate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `code` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `template` text COLLATE utf8_unicode_ci NOT NULL,
  `remark` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_notifytemplate
-- ----------------------------
INSERT INTO `t_notifytemplate` VALUES ('1', 'email', 'email_reg', '<span><span style=\"font-size:14px;line-height:normal;background-color:#FFFFFF;\"> \r\n<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"620\" class=\"ke-zeroborder\" style=\"color:#000000;font-size:14px;background-color:#FFFFFF;font-family:arial, verdana, sans-serif;\">\r\n	<tbody>\r\n		<tr>\r\n			<td style=\"font-family:arial, verdana, sans-serif;\">\r\n				<strong>亲爱的会员：${nickname}您好！</strong> \r\n			</td>\r\n		</tr>\r\n		<tr>\r\n			<td style=\"font-family:arial, verdana, sans-serif;\">\r\n				恭喜您在${system}注册成功，请您立即点击下面的链接激活账号：<br />\r\n<u><a href=\"${url}\" target=\"_blank\">${url}</a></u> \r\n			</td>\r\n		</tr>\r\n		<tr>\r\n			<td style=\"font-family:arial, verdana, sans-serif;\">\r\n				<p>\r\n					此邮件为本系统自动发出，请勿回复！\r\n				</p>\r\n			</td>\r\n		</tr>\r\n		<tr>\r\n		</tr>\r\n		<tr>\r\n			<td style=\"font-family:arial, verdana, sans-serif;\">\r\n				<p>\r\n					如有任何疑问，请联系myshop客服，客服热线：${servicesPhone}\r\n				</p>\r\n				<p>\r\n					官方邮箱：${systemEmail}\r\n				</p>\r\n				<p>\r\n					帮助中心：${helpUrl}\r\n				</p>\r\n			</td>\r\n		</tr>\r\n	</tbody>\r\n</table>\r\n</span></span>', '${nickname}:会员昵称;${system}:系统名称;${url}:链接地址，会员可以点击的。;${servicesPhone}:客服热线;${systemEmail}:官方系统邮箱;${helpUrl}:帮助中心链接地址', '注册成功之邮件通知');
INSERT INTO `t_notifytemplate` VALUES ('2', 'email', 'email_forget_password', '<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"620\" class=\"ke-zeroborder\" style=\"background-color:#FFFFFF;color:#000000;font-size:14px;font-family:arial, verdana, sans-serif;\">\r\n	<tbody>\r\n		<tr>\r\n			<td style=\"font-family:arial, verdana, sans-serif;\">\r\n				<strong>亲爱的会员：${nickname}您好！</strong> \r\n			</td>\r\n		</tr>\r\n	</tbody>\r\n</table>\r\n<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"620\" class=\"ke-zeroborder\" style=\"color:#000000;font-size:14px;background-color:#FFFFFF;font-family:arial, verdana, sans-serif;\">\r\n	<tbody>\r\n		<tr>\r\n			<td style=\"font-family:arial, verdana, sans-serif;\">\r\n				<strong></strong> \r\n			</td>\r\n		</tr>\r\n		<tr>\r\n			<td style=\"font-family:arial, verdana, sans-serif;\">\r\n				您在${system}点击了“忘记密码”按钮，故系统自动为您发送了这封邮件。您可以点击以下链接修改您的密码：<br />\r\n<u><a href=\"http://127.0.0.1:8080/myshopFront/user/reset.html?sign=1d0cb6b99b12464c8c13cc9be99c5245\" target=\"_blank\">$</a>{url}</u> \r\n			</td>\r\n		</tr>\r\n		<tr>\r\n			<td style=\"font-family:arial, verdana, sans-serif;\">\r\n				此链接有效期为两个小时，请在两小时内点击链接进行修改，每天最多允许找回5次密码。如果您不需要修改密码，或者您从未点击过“忘记密码”按钮，请忽略本邮件。&nbsp;<br />\r\n			</td>\r\n		</tr>\r\n		<tr>\r\n		</tr>\r\n		<tr>\r\n			<td style=\"font-family:arial, verdana, sans-serif;\">\r\n				<p>\r\n					<span style=\"line-height:1.5;\">如有任何</span><span style=\"line-height:1.5;\">疑问，请联系myshop客服，客服热线：${servicesPhone}</span>\r\n				</p>\r\n				<p style=\"font-family:arial, verdana, sans-serif;background-color:#FFFFFF;\">\r\n					官方邮箱：${systemEmail}\r\n				</p>\r\n				<p style=\"font-family:arial, verdana, sans-serif;background-color:#FFFFFF;\">\r\n					帮助中心：${helpUrl}\r\n				</p>\r\n			</td>\r\n		</tr>\r\n	</tbody>\r\n</table>', '${nickname}:会员昵称;${system}:系统名称;${url}:链接地址，会员可以点击的。;${servicesPhone}:客服热线;${systemEmail}:官方系统邮箱;${helpUrl}:帮助中心链接地址', '找回密码之邮件通知');
INSERT INTO `t_notifytemplate` VALUES ('3', 'email', 'email_change_email', '<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"620\" class=\"ke-zeroborder\" style=\"background-color:#FFFFFF;color:#000000;font-size:14px;font-family:arial, verdana, sans-serif;\">\r\n	<tbody>\r\n		<tr>\r\n			<td style=\"font-family:arial, verdana, sans-serif;\">\r\n				<strong>亲爱的会员：${nickname}您好！</strong>\r\n			</td>\r\n		</tr>\r\n	</tbody>\r\n</table>\r\n<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"620\" class=\"ke-zeroborder\" style=\"color:#000000;font-size:14px;background-color:#FFFFFF;font-family:arial, verdana, sans-serif;\">\r\n	<tbody>\r\n		<tr>\r\n			<td style=\"font-family:arial, verdana, sans-serif;\">\r\n				<strong></strong>\r\n			</td>\r\n		</tr>\r\n		<tr>\r\n			<td style=\"font-family:arial, verdana, sans-serif;\">\r\n				您在${system}点击了“修改邮箱”链接，故系统自动为您发送了这封邮件。您可以点击以下链接来激活您的新邮箱：<br />\r\n<u><a href=\"http://127.0.0.1:8080/myshopFront/user/reset.html?sign=1d0cb6b99b12464c8c13cc9be99c5245\" target=\"_blank\">$</a>{url}</u>\r\n			</td>\r\n		</tr>\r\n		<tr>\r\n			<td style=\"font-family:arial, verdana, sans-serif;\">\r\n				此链接有效期为两个小时，请在两小时内点击链接进行修改，每天最多允许找回5次密码。如果您不需要修改密码，或者您从未点击过“忘记密码”按钮，请忽略本邮件。&nbsp;<br />\r\n			</td>\r\n		</tr>\r\n		<tr>\r\n		</tr>\r\n		<tr>\r\n			<td style=\"font-family:arial, verdana, sans-serif;\">\r\n				<p>\r\n					<span style=\"font-family:\'sans serif\', tahoma, verdana, helvetica;line-height:1.5;\">如有任何疑问，请联系myshop客服，客服热线：${servicesPhone}</span>\r\n				</p>\r\n				<p>\r\n					官方邮箱：${systemEmail}\r\n				</p>\r\n				<p>\r\n					帮助中心：${helpUrl}\r\n				</p>\r\n			</td>\r\n		</tr>\r\n	</tbody>\r\n</table>', '${nickname}:会员昵称;${system}:系统名称;${url}:链接地址，会员可以点击的。;${servicesPhone}:客服热线;${systemEmail}:官方系统邮箱;${helpUrl}:帮助中心链接地址', '修改邮箱之邮件通知');
INSERT INTO `t_notifytemplate` VALUES ('4', 'email', 'email_notify_product', '<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"620\" class=\"ke-zeroborder\" style=\"background-color:#FFFFFF;color:#000000;font-size:14px;font-family:arial, verdana, sans-serif;\">\r\n	<tbody>\r\n		<tr>\r\n			<td style=\"font-family:arial, verdana, sans-serif;\">\r\n				<strong>亲爱的会员：${nickname}您好！</strong> \r\n			</td>\r\n		</tr>\r\n	</tbody>\r\n</table>\r\n<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"620\" class=\"ke-zeroborder\" style=\"color:#000000;font-size:14px;background-color:#FFFFFF;font-family:arial, verdana, sans-serif;\">\r\n	<tbody>\r\n		<tr>\r\n			<td style=\"font-family:arial, verdana, sans-serif;\">\r\n				<strong></strong> \r\n			</td>\r\n		</tr>\r\n		<tr>\r\n			<td style=\"font-family:arial, verdana, sans-serif;\">\r\n				<p>\r\n					<br />\r\n				</p>\r\n				<p>\r\n					您关注的商品：<a href=\"${url}\" target=\"_blank\">${productName}</a>&nbsp;已经到货，欢迎您的长期关注。\r\n				</p>\r\n				<p>\r\n					<br />\r\n				</p>\r\n			</td>\r\n		</tr>\r\n		<tr>\r\n			<td style=\"font-family:arial, verdana, sans-serif;\">\r\n				此邮件为系统自动发送，请勿回复！\r\n			</td>\r\n		</tr>\r\n		<tr>\r\n		</tr>\r\n		<tr>\r\n			<td style=\"font-family:arial, verdana, sans-serif;\">\r\n				<p>\r\n					<span style=\"font-family:\'sans serif\', tahoma, verdana, helvetica;line-height:1.5;\">如有任何疑问，请联系myshop客服，客服热线：${servicesPhone}</span> \r\n				</p>\r\n				<p>\r\n					官方邮箱：${systemEmail}\r\n				</p>\r\n				<p>\r\n					帮助中心：${helpUrl}\r\n				</p>\r\n			</td>\r\n		</tr>\r\n	</tbody>\r\n</table>', '${nickname}:会员昵称;${system}:系统名称;${url}:链接地址，会员可以点击的。;${servicesPhone}:客服热线;${systemEmail}:官方系统邮箱;${helpUrl}:帮助中心链接地址;${productName}:商品名称;${productID}:商品ID', '到货通知之邮件通知');
INSERT INTO `t_notifytemplate` VALUES ('5', 'sms', 'sms_sendProductNotify', '亲，订单号为：${<span>orderId</span>}的商品【商品名称】卖家已发货！【jeeshop】', '${orderId}:订单编号', '系统发货短信通知');

-- ----------------------------
-- Table structure for `t_order`
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `payType` int(11) DEFAULT NULL,
  `carry` int(11) DEFAULT NULL,
  `rebate` decimal(10,2) DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  `status` varchar(10) COLLATE utf8_unicode_ci DEFAULT 'init',
  `refundStatus` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `amount` decimal(20,2) DEFAULT '0.00',
  `fee` decimal(20,2) DEFAULT '0.00',
  `ptotal` decimal(20,2) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `paystatus` varchar(2) COLLATE utf8_unicode_ci DEFAULT 'n',
  `updateAmount` varchar(1) COLLATE utf8_unicode_ci DEFAULT 'n',
  `expressCode` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `expressName` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `otherRequirement` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `remark` varchar(545) COLLATE utf8_unicode_ci DEFAULT NULL,
  `expressNo` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `expressCompanyName` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `lowStocks` varchar(1) COLLATE utf8_unicode_ci DEFAULT 'n',
  `confirmSendProductRemark` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `closedComment` varchar(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `order_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=10274 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_order
-- ----------------------------
INSERT INTO `t_order` VALUES ('10202', 'test1', null, null, '1.00', '2014-02-24 12:14:51', 'cancel', null, '36.00', '15.00', '21.00', '1', 'n', 'n', 'EMS', 'EMS', null, '爱笑会议室', null, null, 'n', null, null, null);
INSERT INTO `t_order` VALUES ('10203', 'test1', null, null, '1.00', '2014-02-24 13:51:43', 'cancel', null, '11181.00', '15.00', '11166.00', '2', 'n', 'n', 'EMS', 'EMS', null, '合并|2笔订单', null, null, 'n', null, null, null);
INSERT INTO `t_order` VALUES ('10228', 'test1', null, null, '1.00', '2014-03-04 17:50:57', 'cancel', null, '1085.00', '15.00', '1070.00', '4', 'n', 'n', 'EMS', 'EMS', null, '合并|4笔订单', null, null, 'n', null, null, null);
INSERT INTO `t_order` VALUES ('10229', '_out_1393056233281', null, null, '1.00', '2014-03-16 22:00:11', 'cancel', null, '59.00', '15.00', '44.00', '1', 'n', 'n', 'EMS', 'EMS', null, '致我们终将逝去的青春', null, null, 'n', null, null, null);
INSERT INTO `t_order` VALUES ('10230', '_out_1395013499587', null, null, '1.00', '2014-03-17 15:04:23', 'cancel', null, '10903.00', '15.00', '10888.00', '1', 'n', 'n', 'EMS', 'EMS', null, 'Acer/宏基 P3-171-3322Y2G06as 11.6英寸 超极本 Win8 6', null, null, 'n', null, null, null);
INSERT INTO `t_order` VALUES ('10231', '_out_1395013499587', null, null, '1.00', '2014-03-17 15:05:44', 'cancel', null, '10903.00', '15.00', '10888.00', '1', 'n', 'n', 'EMS', 'EMS', null, 'Acer/宏基 P3-171-3322Y2G06as 11.6英寸 超极本 Win8 6', null, null, 'n', null, null, null);
INSERT INTO `t_order` VALUES ('10232', '_out_1395013499587', null, null, '1.00', '2014-03-17 15:07:25', 'cancel', null, '10903.00', '15.00', '10888.00', '1', 'n', 'n', 'EMS', 'EMS', null, 'Acer/宏基 P3-171-3322Y2G06as 11.6英寸 超极本 Win8 6', null, null, 'n', null, null, null);
INSERT INTO `t_order` VALUES ('10233', '_out_1395013499587', null, null, '1.00', '2014-03-18 14:58:43', 'pass', null, '0.01', '0.00', '0.01', '1', 'y', 'y', 'EMS', 'EMS', null, 'Acer/宏基 P3-171-3322Y2G06as 11.6英寸 超极本 Win8 6', null, null, 'n', null, null, null);
INSERT INTO `t_order` VALUES ('10234', '_out_1395013499587', null, null, '1.00', '2014-03-21 12:04:34', 'cancel', null, '0.01', '0.00', '0.01', '1', 'n', 'y', 'EMS', 'EMS', null, 'Acer/宏基 P3-171-3322Y2G06as 11.6英寸 超极本 Win8 6', null, null, 'n', null, null, null);
INSERT INTO `t_order` VALUES ('10235', '_out_1395013499587', null, null, '1.00', '2014-03-21 12:21:16', 'pass', null, '59.00', '15.00', '44.00', '1', 'y', 'n', 'EMS', 'EMS', null, '致我们终将逝去的青春', null, null, 'n', null, null, null);
INSERT INTO `t_order` VALUES ('10236', '_out_1395013499587', null, null, '1.00', '2014-03-21 12:22:16', 'cancel', null, '0.01', '0.00', '0.01', '2', 'n', 'y', 'EMS', 'EMS', null, '合并|2笔订单', null, null, 'n', null, null, null);
INSERT INTO `t_order` VALUES ('10237', '_out_1393560390544', null, null, '1.00', '2014-03-21 12:30:03', 'cancel', null, '0.01', '0.00', '0.01', '1', 'n', 'y', 'EMS', 'EMS', null, 'Acer/宏基 P3-171-3322Y2G06as 11.6英寸 超极本 Win8 6', null, null, 'n', null, null, null);
INSERT INTO `t_order` VALUES ('10238', '_out_1395013499587', null, null, '1.00', '2014-03-21 12:57:18', 'sign', null, '0.01', '0.00', '0.01', '1', 'y', 'y', 'EMS', 'EMS', null, 'Acer/宏基 P3-171-3322Y2G06as 11.6英寸 超极本 Win8 6', '2222333333', 'shunfeng', 'n', null, null, null);
INSERT INTO `t_order` VALUES ('10239', '_out_1395013499587', null, null, '1.00', '2014-03-21 14:35:39', 'send', 'REFUND_SUCCESS', '0.01', '0.00', '0.01', '1', 'y', 'y', 'EMS', 'EMS', null, 'Acer/宏基 P3-171-3322Y2G06as 11.6英寸 超极本 Win8 6', '11111111111', 'zhongtong', 'n', null, null, null);
INSERT INTO `t_order` VALUES ('10240', '_out_1395013499587', null, null, '1.00', '2014-03-21 15:52:12', 'send', 'REFUND_SUCCESS', '0.01', '0.00', '0.01', '1', 'y', 'y', 'EMS', 'EMS', null, 'Acer/宏基 P3-171-3322Y2G06as 11.6英寸 超极本 Win8 6', '22222222222', 'zhongtong', 'n', null, null, null);
INSERT INTO `t_order` VALUES ('10241', '_out_1395013499587', null, null, '1.00', '2014-03-21 16:15:04', 'send', 'REFUND_SUCCESS', '0.01', '0.00', '0.01', '1', 'y', 'y', 'EXPRESS', '快递', null, '【送米兔+贴膜】MIUI/小米 小米手机3代M3MI3小米3米联通移动官网', '11111111111', 'zhongtong', 'n', null, null, null);
INSERT INTO `t_order` VALUES ('10242', '_out_1395013499587', null, null, '1.00', '2014-03-21 17:32:37', 'send', 'REFUND_CLOSED', '0.01', '0.00', '0.01', '1', 'y', 'y', 'EMS', 'EMS', null, '【送米兔+贴膜】MIUI/小米 小米手机3代M3MI3小米3米联通移动官网', '22222222222', 'zhongtong', 'n', null, null, null);
INSERT INTO `t_order` VALUES ('10243', '_out_1395013499587', null, null, '1.00', '2014-03-26 10:48:20', 'pass', null, '10903.00', '15.00', '10888.00', '1', 'y', 'n', 'EMS', 'EMS', null, 'Acer/宏基 P3-171-3322Y2G06as 11.6英寸 超极本 Win8 6', null, null, 'n', null, null, null);
INSERT INTO `t_order` VALUES ('10244', '_out_1395013499587', null, null, '1.00', '2014-03-26 10:54:06', 'init', null, '0.01', '0.00', '0.01', '1', 'y', 'y', 'EMS', 'EMS', null, '【送米兔+贴膜】MIUI/小米 小米手机3代M3MI3小米3米联通移动官网', null, null, 'n', null, null, null);
INSERT INTO `t_order` VALUES ('10245', '_out_1395013499587', null, null, '1.00', '2014-03-26 10:56:10', 'init', null, '0.01', '0.00', '0.01', '1', 'y', 'y', 'EMS', 'EMS', null, '香甜可口的苹果', null, null, 'n', null, null, null);
INSERT INTO `t_order` VALUES ('10246', '_out_1395013499587', null, null, '1.00', '2014-03-26 12:28:08', 'cancel', null, '108836463.00', '15.00', '108836448.00', '1', 'n', 'n', 'EMS', 'EMS', null, 'Acer/宏基 P3-171-3322Y2G06as 11.6英寸 超极本 Win8 6', null, null, 'n', null, null, null);
INSERT INTO `t_order` VALUES ('10247', '_out_1395013499587', null, null, '1.00', '2014-03-26 12:30:34', 'cancel', null, '108836463.00', '15.00', '108836448.00', '1', 'n', 'n', 'EMS', 'EMS', null, 'Acer/宏基 P3-171-3322Y2G06as 11.6英寸 超极本 Win8 6', null, null, 'n', null, null, null);
INSERT INTO `t_order` VALUES ('10248', '_out_1395013499587', null, null, '1.00', '2014-03-26 12:31:25', 'cancel', null, '108836463.00', '15.00', '108836448.00', '1', 'n', 'n', 'EMS', 'EMS', null, 'Acer/宏基 P3-171-3322Y2G06as 11.6英寸 超极本 Win8 6', null, null, 'n', null, null, null);
INSERT INTO `t_order` VALUES ('10249', '_out_1395013499587', null, null, '1.00', '2014-03-26 12:34:35', 'init', null, '0.01', '0.00', '0.01', '1', 'y', 'y', 'EMS', 'EMS', null, '【送米兔+贴膜】MIUI/小米 小米手机3代M3MI3小米3米联通移动官网', null, null, 'n', null, null, null);
INSERT INTO `t_order` VALUES ('10250', 'test1', null, null, '1.00', '2014-03-26 16:31:46', 'sign', null, '0.02', '0.00', '0.02', '1', 'y', 'y', 'EMS', 'EMS', null, '边城（沈从文著中篇小说）', '11111111111', 'shunfeng', 'n', null, 'y', '123');
INSERT INTO `t_order` VALUES ('10251', 'ceshi01', null, null, '1.00', '2014-04-08 14:27:24', 'cancel', null, '19559.00', '5.00', '19554.00', '2', 'n', 'n', 'EXPRESS', '快递', null, '合并|2笔订单', null, null, 'n', null, null, null);
INSERT INTO `t_order` VALUES ('10252', 'test1', null, null, '1.00', '2014-04-11 09:26:40', 'init', null, '0.01', '0.00', '0.01', '1', 'y', 'y', 'EMS', 'EMS', null, 'Acer/宏基 P3-171-3322Y2G06as 11.6英寸 超极本 Win8 6', null, null, 'n', null, null, '238');
INSERT INTO `t_order` VALUES ('10253', 'test1', null, null, '1.00', '2014-04-11 09:33:40', 'cancel', null, '0.01', '0.00', '0.01', '1', 'n', 'y', 'EMS', 'EMS', null, '致我们终将逝去的青春', null, null, 'n', null, null, null);
INSERT INTO `t_order` VALUES ('10254', 'test1', null, null, '1.00', '2014-04-11 09:34:53', 'sign', null, '0.01', '0.00', '0.01', '1', 'y', 'y', 'EMS', 'EMS', null, 'Acer/宏基 P3-171-3322Y2G06as 11.6英寸 超极本 Win8 6', '11111111111111', 'zhongtong', 'n', null, 'y', '238');
INSERT INTO `t_order` VALUES ('10255', 'lip1', null, null, '1.00', '2014-05-04 10:20:52', 'cancel', null, '0.01', '0.00', '0.01', '1', 'n', 'y', 'POST', '平邮', null, '阿Q正传（呐喊—鲁迅小说）', null, null, 'n', null, null, null);
INSERT INTO `t_order` VALUES ('10256', '_out_1399301353546', null, null, '1.00', '2014-05-05 22:50:55', 'cancel', null, '52011.00', '15.00', '51996.00', '1', 'n', 'n', 'EMS', 'EMS', null, 'Lenovo/联想 Lenovo G480A-ITH(D) G470 I3I5I714寸', null, null, 'n', null, null, null);
INSERT INTO `t_order` VALUES ('10257', '_out_1397461177517', null, null, '1.00', '2014-06-14 08:05:11', 'cancel', null, '48.00', '5.00', '43.00', '1', 'n', 'n', 'EXPRESS', '快递', null, '我所理解的生活', null, null, 'n', null, null, null);
INSERT INTO `t_order` VALUES ('10258', 'test1', null, null, '1.00', '2014-06-17 15:56:02', 'cancel', null, '49.00', '5.00', '44.00', '1', 'n', 'n', 'EXPRESS', '快递', null, '香甜可口的苹果', null, null, 'n', null, null, null);
INSERT INTO `t_order` VALUES ('10259', 'test1', null, null, '1.00', '2014-06-30 21:23:46', 'init', null, '95.00', '5.00', '90.00', '1', 'n', 'n', 'EXPRESS', '快递', null, '钢铁是怎样炼成的', null, null, 'n', null, null, null);
INSERT INTO `t_order` VALUES ('10264', 'wukong', null, null, '1.00', '2018-03-18 17:19:20', 'init', null, '60.00', '0.00', '60.00', '1', 'n', 'n', 'POST', '平邮', '我是悟空', '鲁滨孙漂流记', null, null, 'n', null, null, null);
INSERT INTO `t_order` VALUES ('10265', 'wukong', null, null, '1.00', '2018-03-18 17:26:02', 'init', null, '60.00', '0.00', '60.00', '1', 'n', 'n', 'POST', '平邮', null, '鲁滨孙漂流记', null, null, 'n', null, null, null);
INSERT INTO `t_order` VALUES ('10266', 'wukong', null, null, '1.00', '2018-03-18 17:27:41', 'init', null, '16.00', '0.00', '16.00', '1', 'n', 'n', 'POST', '平邮', null, '正版预售 不畏将来 不念过去 谁的青春不迷茫 文学 励志书籍 博库网', null, null, 'n', null, null, null);
INSERT INTO `t_order` VALUES ('10267', 'wukong', null, null, '1.00', '2018-03-18 19:29:17', 'init', null, '16.00', '0.00', '16.00', '1', 'n', 'n', 'POST', '平邮', null, '正版预售 不畏将来 不念过去 谁的青春不迷茫 文学 励志书籍 博库网', null, null, 'n', null, null, null);
INSERT INTO `t_order` VALUES ('10268', 'wukong', null, null, '1.00', '2018-03-18 19:38:43', 'init', null, null, '0.00', null, '1', 'y', 'n', 'POST', '平邮', null, '致我们终将逝去的青春', null, null, 'n', null, null, null);
INSERT INTO `t_order` VALUES ('10269', 'wukong', null, null, '1.00', '2018-03-18 19:42:03', 'init', null, '16.00', '0.00', '16.00', '1', 'n', 'n', 'POST', '平邮', null, '正版预售 不畏将来 不念过去 谁的青春不迷茫 文学 励志书籍 博库网', null, null, 'n', null, null, null);
INSERT INTO `t_order` VALUES ('10270', 'wukong', null, null, '1.00', '2018-03-18 19:43:08', 'init', null, '75.00', '15.00', '60.00', '1', 'n', 'n', 'EMS', 'EMS', null, '鲁滨孙漂流记', null, null, 'n', null, null, null);
INSERT INTO `t_order` VALUES ('10272', 'wukong', null, null, '1.00', '2018-03-19 22:14:39', 'init', null, '3003.00', '5.00', '2998.00', '1', 'n', 'n', 'EXPRESS', '快递', null, '【送米兔+贴膜】MIUI/小米 小米手机3代M3MI3小米3米联通移动官网', null, null, 'n', null, null, null);
INSERT INTO `t_order` VALUES ('10273', 'wukong', null, null, '1.00', '2018-03-22 22:28:15', 'init', null, '3042.00', '0.00', '3042.00', '2', 'n', 'n', 'POST', '平邮', null, '合并|2笔订单', null, null, 'n', null, null, null);

-- ----------------------------
-- Table structure for `t_orderdetail`
-- ----------------------------
DROP TABLE IF EXISTS `t_orderdetail`;
CREATE TABLE `t_orderdetail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `orderID` int(11) DEFAULT NULL,
  `productID` int(11) DEFAULT NULL,
  `price` decimal(20,2) DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `productName` varchar(145) COLLATE utf8_unicode_ci DEFAULT NULL,
  `fee` decimal(20,2) DEFAULT NULL,
  `total0` decimal(20,2) DEFAULT NULL,
  `isComment` varchar(2) COLLATE utf8_unicode_ci DEFAULT 'n',
  `lowStocks` varchar(1) COLLATE utf8_unicode_ci DEFAULT 'n',
  `score` int(11) DEFAULT NULL,
  `specInfo` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `giftID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=344 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_orderdetail
-- ----------------------------
INSERT INTO `t_orderdetail` VALUES ('93', '10064', '10081', '67.00', '1', '山楂糕_32', '5.00', null, 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('94', '10065', '10080', '67.00', '2', '山楂糕_31', '5.00', null, 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('95', '10066', '10007', '32.00', '1', '草莓', '5.00', null, 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('96', '10067', '10082', '67.00', '1', '山楂糕_33', '5.00', null, 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('97', '10067', '10081', '67.00', '1', '山楂糕_32', '5.00', null, 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('98', '10067', '10080', '67.00', '22', '山楂糕_31', '5.00', null, 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('99', '10068', '10082', '67.00', '1', '山楂糕_33', '5.00', null, 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('100', '10068', '10081', '67.00', '1', '山楂糕_32', '5.00', null, 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('101', '10069', '10082', '67.00', '2', '山楂糕_33', '5.00', null, 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('102', '10069', '10081', '67.00', '1', '山楂糕_32', '5.00', null, 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('103', '10070', '10078', '67.00', '1', '山楂糕_29', '5.00', null, 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('104', '10071', '10078', '67.00', '1', '山楂糕_29', '5.00', null, 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('105', '10072', '10079', '67.00', '1', '山楂糕_30', '5.00', null, 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('106', '10073', '10079', '67.00', '1', '山楂糕_30', '5.00', null, 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('107', '10074', '10078', '67.00', '1', '山楂糕_29', '5.00', null, 'y', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('108', '10075', '10007', '22.00', '1', '草莓', '5.00', null, 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('109', '10075', '10078', '44.00', '12', '山楂糕_29', '5.00', null, 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('110', '10076', '10005', '23.00', '1', '十万个为什么888', '5.00', null, 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('111', '10077', '10005', '23.00', '1', '十万个为什么888', '5.00', null, 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('112', '10078', '10078', '44.00', '12', '山楂糕_29', '5.00', null, 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('113', '10079', '10005', '23.00', '1', '十万个为什么888', '5.00', null, 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('114', '10080', '10005', '23.00', '1', '十万个为什么888', '5.00', null, 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('115', '10081', '10005', '23.00', '1', '十万个为什么888', '5.00', null, 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('116', '10082', '10005', '23.00', '1', '十万个为什么888', '5.00', null, 'y', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('117', '10083', '10079', '44.00', '1', '山楂糕_30', '5.00', null, 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('118', '10083', '10078', '44.00', '1', '山楂糕_29', '5.00', null, 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('119', '10084', '10005', '23.00', '1', '十万个为什么888', '5.00', null, 'y', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('120', '10084', '10078', '44.00', '22', '山楂糕_29', '5.00', null, 'y', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('121', '10085', '10007', '22.00', '1', '草莓', '5.00', null, 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('122', '10085', '10074', '44.00', '1', '山楂糕_25', '5.00', null, 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('123', '10086', '8', '2.00', '1', '三国志', '5.00', '7.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('124', '10087', '10078', '44.00', '1', '山楂糕_29', '5.00', '49.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('125', '10087', '10082', '44.00', '12', '山楂糕_33', '5.00', '533.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('126', '10088', '8', '2.00', '12', '三国志', '5.00', '29.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('127', '10088', '10077', '44.00', '3', '山楂糕_28', '5.00', '137.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('128', '10089', '10250', '11122.00', '1', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', '5.00', '11127.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('129', '10089', '10079', '44.00', '3', '山楂糕_30', '5.00', '137.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('130', '10089', '10075', '44.00', '111', '山楂糕_26', '5.00', '4889.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('131', '10090', '10003', '21.00', '1', '爱笑会议室', '5.00', '26.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('132', '10091', '10250', '11122.00', '1', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', '5.00', '11127.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('133', '10092', '10078', '44.00', '1', '山楂糕_29', '5.00', '49.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('134', '10093', '10079', '44.00', '1', '山楂糕_30', '5.00', '49.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('135', '10094', '10007', '22.00', '1', '草莓', '5.00', '27.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('136', '10095', '10078', '44.00', '1', '山楂糕_29', '5.00', '49.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('137', '10096', '10079', '44.00', '1', '山楂糕_30', '5.00', '49.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('138', '10097', '10007', '22.00', '1', '草莓', '5.00', '27.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('139', '10098', '10082', '44.00', '1', '山楂糕_33', '5.00', '49.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('140', '10099', '10079', '44.00', '1', '山楂糕_30', '5.00', '49.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('141', '10100', '10082', '44.00', '1', '山楂糕_33', '5.00', '49.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('142', '10101', '10003', '21.00', '1', '爱笑会议室', '5.00', '26.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('143', '10102', '10003', '21.00', '1', '爱笑会议室', '5.00', '26.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('144', '10103', '10003', '21.00', '1', '爱笑会议室', '5.00', '26.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('145', '10104', '10078', '44.00', '1', '山楂糕_29', '5.00', '49.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('146', '10105', '10078', '44.00', '1', '山楂糕_29', '5.00', '49.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('147', '10106', '10079', '44.00', '1', '山楂糕_30', '5.00', '49.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('148', '10107', '10079', '44.00', '1', '山楂糕_30', '5.00', '49.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('149', '10108', '10079', '44.00', '1', '山楂糕_30', '5.00', '49.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('150', '10109', '10079', '44.00', '1', '山楂糕_30', '5.00', '49.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('151', '10110', '10079', '44.00', '1', '山楂糕_30', '5.00', '49.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('152', '10111', '10079', '44.00', '1', '山楂糕_30', '5.00', '49.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('153', '10112', '10079', '44.00', '1', '山楂糕_30', '5.00', '49.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('154', '10112', '10250', '11122.00', '1', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', '5.00', '11127.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('155', '10113', '10079', '44.00', '1', '山楂糕_30', '5.00', '49.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('156', '10113', '10250', '11122.00', '1', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', '5.00', '11127.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('157', '10114', '10079', '44.00', '1', '山楂糕_30', '5.00', '49.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('158', '10114', '10250', '11122.00', '1', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', '5.00', '11127.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('159', '10115', '10082', '44.00', '2', '山楂糕_33', '5.00', '93.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('160', '10115', '10250', '11122.00', '4', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', '5.00', '44493.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('161', '10116', '10082', '44.00', '3', '山楂糕_33', '5.00', '137.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('162', '10116', '10250', '11122.00', '4', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', '5.00', '44493.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('163', '10117', '10082', '44.00', '3', '山楂糕_33', '5.00', '137.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('164', '10118', '10250', '11122.00', '1', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', '5.00', '11127.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('165', '10119', '10082', '44.00', '1', '山楂糕_33', '5.00', '49.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('166', '10120', '10082', '44.00', '1', '山楂糕_33', '5.00', '49.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('167', '10121', '10082', '44.00', '1', '山楂糕_33', '5.00', '49.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('168', '10121', '10079', '44.00', '2', '山楂糕_30', '5.00', '93.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('169', '10122', '10010', '48.00', '1', '武昌鱼', '5.00', '53.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('170', '10123', '10250', '11122.00', '1', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', '5.00', '11127.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('171', '10124', '10010', '48.00', '1', '武昌鱼', '5.00', '53.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('172', '10125', '10071', '44.00', '1', '山楂糕_22', '5.00', '49.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('173', '10126', '10078', '44.00', '1', '山楂糕_29', '5.00', '49.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('174', '10127', '10078', '44.00', '2', '山楂糕_29', '5.00', '93.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('175', '10128', '10003', '21.00', '1', '爱笑会议室', '5.00', '26.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('176', '10129', '10250', '11122.00', '2', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', '5.00', '22249.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('177', '10130', '10250', '11122.00', '1', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', '5.00', '11127.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('178', '10131', '10250', '11122.00', '1', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', '5.00', '11127.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('179', '10132', '10250', '11122.00', '1', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', '5.00', '11127.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('180', '10133', '10250', '11122.00', '1', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', '5.00', '11127.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('181', '10134', '10250', '11122.00', '1', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', '5.00', '11127.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('182', '10135', '10082', '44.00', '1', '山楂糕_33', '5.00', '49.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('183', '10136', '10003', '21.00', '1', '爱笑会议室', '5.00', '26.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('184', '10137', '10007', '22.00', '1', '草莓', '5.00', '27.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('185', '10138', '10007', '22.00', '1', '草莓', '5.00', '27.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('186', '10139', '10003', '21.00', '1', '爱笑会议室', '5.00', '26.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('187', '10140', '10003', '21.00', '1', '爱笑会议室', '5.00', '26.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('188', '10141', '10250', '11122.00', '1', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', '5.00', '11127.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('189', '10142', '10250', '11122.00', '1', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', '5.00', '11127.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('190', '10143', '10250', '11122.00', '1', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', '5.00', '11127.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('191', '10144', '10250', '11122.00', '1', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', '5.00', '11127.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('192', '10144', '10082', '44.00', '1', '山楂糕_33', '5.00', '49.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('193', '10145', '10250', '11122.00', '2', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', '5.00', '22249.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('194', '10145', '10082', '44.00', '1', '山楂糕_33', '5.00', '49.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('195', '10146', '10250', '11122.00', '1', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', '5.00', '11127.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('196', '10147', '10250', '11122.00', '1', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', '5.00', '11127.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('197', '10148', '10250', '11122.00', '1', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', '5.00', '11127.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('198', '10149', '10007', '22.00', '1', '草莓', '5.00', '27.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('199', '10150', '10007', '22.00', '1', '草莓', '5.00', '27.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('200', '10151', '10250', '11122.00', '1', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', '5.00', '11127.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('201', '10152', '10082', '44.00', '1', '山楂糕_33', '5.00', '49.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('202', '10153', '10250', '11122.00', '2', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', '5.00', '22249.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('203', '10153', '10079', '44.00', '1', '山楂糕_30', '5.00', '49.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('204', '10153', '10078', '44.00', '1', '山楂糕_29', '5.00', '49.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('205', '10153', '8', '2.00', '1', '三国志', '5.00', '7.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('206', '10154', '10003', '21.00', '4', '爱笑会议室', '5.00', '89.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('207', '10154', '10075', '44.00', '1', '山楂糕_26', '5.00', '49.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('208', '10154', '10067', '44.00', '4', '山楂糕_18', '5.00', '181.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('209', '10155', '10250', '11122.00', '1', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', '5.00', '11127.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('210', '10156', '10250', '11122.00', '1', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', '5.00', '11127.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('211', '10157', '10250', '11122.00', '1', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', '5.00', '11127.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('212', '10158', '10250', '11122.00', '1', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', '5.00', '11127.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('213', '10159', '10250', '11122.00', '1', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', null, '11122.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('214', '10160', '10250', '11122.00', '1', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', null, '11122.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('215', '10161', '10250', '11122.00', '1', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', null, '11122.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('216', '10162', '10250', '11122.00', '1', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', null, '11122.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('217', '10163', '10250', '11122.00', '1', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', null, '11122.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('218', '10164', '10250', '11122.00', '1', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', null, '11122.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('219', '10165', '10250', '11122.00', '1', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', null, '11122.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('220', '10166', '10250', '11122.00', '1', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', null, '11122.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('221', '10167', '10250', '11122.00', '2', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', null, '22244.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('222', '10168', '10250', '11122.00', '2', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', null, '22244.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('223', '10169', '10250', '11122.00', '2', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', null, '22244.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('224', '10170', '10250', '11122.00', '2', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', null, '22244.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('225', '10171', '10250', '11122.00', '2', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', null, '22244.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('226', '10172', '10250', '11122.00', '2', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', null, '22244.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('227', '10173', '10250', '11122.00', '2', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', null, '22244.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('228', '10174', '10250', '11122.00', '2', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', null, '22244.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('229', '10175', '10250', '11122.00', '2', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', null, '22244.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('230', '10176', '10250', '11122.00', '2', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', null, '22244.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('231', '10177', '10250', '11122.00', '2', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', null, '22244.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('232', '10178', '10250', '11122.00', '2', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', null, '22244.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('233', '10179', '10250', '11122.00', '2', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', null, '22244.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('234', '10180', '10250', '11122.00', '2', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', null, '22244.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('235', '10181', '10250', '11122.00', '2', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', null, '22244.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('236', '10182', '10250', '11122.00', '2', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', null, '22244.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('237', '10183', '10250', '11122.00', '2', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', null, '22244.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('238', '10184', '10250', '11122.00', '2', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', null, '22244.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('239', '10185', '10250', '11122.00', '2', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', null, '22244.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('240', '10186', '10250', '11122.00', '2', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', null, '22244.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('241', '10187', '10082', '44.00', '3', '山楂糕_33', null, '132.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('242', '10188', '10082', '44.00', '1', '山楂糕_33', null, '44.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('243', '10189', '10082', '44.00', '1', '山楂糕_33', null, '44.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('244', '10190', '10082', '44.00', '1', '山楂糕_33', null, '44.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('245', '10191', '10250', '11122.00', '1', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', null, '11122.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('246', '10192', '10079', '44.00', '1', '山楂糕_30', null, '44.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('247', '10193', '10079', '44.00', '1', '山楂糕_30', null, '44.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('248', '10194', '10079', '44.00', '1', '山楂糕_30', null, '44.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('249', '10195', '10079', '44.00', '1', '山楂糕_30', null, '44.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('250', '10196', '10082', '44.00', '2', '山楂糕_33', null, '88.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('251', '10196', '10074', '44.00', '4', '山楂糕_25', null, '176.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('252', '10197', '10250', '11122.00', '1', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', null, '11122.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('253', '10198', '10250', '11122.00', '1', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', null, '11122.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('254', '10199', '10250', '11122.00', '1', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', null, '11122.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('255', '10200', '10250', '11122.00', '1', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', null, '11122.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('256', '10201', '10250', '11122.00', '1', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', null, '11122.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('257', '10202', '10003', '21.00', '1', '爱笑会议室', null, '21.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('258', '10203', '10250', '11122.00', '1', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', null, '11122.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('259', '10203', '10079', '44.00', '1', '山楂糕_30', null, '44.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('260', '10204', '10082', '44.00', '1', '山楂糕_33', null, '44.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('261', '10204', '10078', '44.00', '1', '山楂糕_29', null, '44.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('262', '10204', '10071', '44.00', '1', '山楂糕_22', null, '44.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('263', '10204', '10063', '44.00', '1', '山楂糕_14', null, '44.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('264', '10205', '10003', '21.00', '1', '爱笑会议室', null, '21.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('265', '10206', '10003', '21.00', '1', '爱笑会议室', null, '21.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('266', '10207', '10082', '44.00', '3', '山楂糕_33', null, '132.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('267', '10207', '10079', '44.00', '4', '山楂糕_30', null, '176.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('268', '10208', '10082', '44.00', '3', '山楂糕_33', null, '132.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('269', '10208', '10079', '44.00', '4', '山楂糕_30', null, '176.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('270', '10209', '10082', '44.00', '11', '山楂糕_33', null, '484.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('271', '10210', '10082', '44.00', '2', '山楂糕_33', null, '88.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('272', '10211', '10082', '44.00', '1', '山楂糕_33', null, '44.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('273', '10211', '10007', '22.00', '5', '草莓', null, '110.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('274', '10212', '10082', '44.00', '1', '山楂糕_33', null, '44.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('275', '10213', '10007', '22.00', '1', '草莓', null, '22.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('276', '10214', '10007', '22.00', '1', '草莓', null, '22.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('277', '10215', '10007', '22.00', '1', '草莓', null, '22.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('278', '10216', '10007', '22.00', '1', '草莓', null, '22.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('279', '10217', '10079', '44.00', '1', '山楂糕_30', null, '44.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('280', '10218', '10250', '11122.00', '1', '山楂糕山楂糕山楂糕山楂糕山楂糕糕山楂糕山楂糕楂糕山楂糕糕山楂糕山楂糕山楂糕糕山楂糕山楂糕', null, '11122.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('281', '10219', '10007', '22.00', '1', '草莓', null, '22.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('282', '10220', '10007', '22.00', '1', '草莓', null, '22.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('283', '10221', '10082', '44.00', '1', '山楂糕_33', null, '44.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('284', '10222', '10082', '44.00', '1', '山楂糕_33', null, '44.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('285', '10223', '10082', '44.00', '1', '山楂糕_33', null, '44.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('286', '10224', '10082', '44.00', '1', '山楂糕_33', null, '44.00', 'y', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('287', '10225', '10007', '22.00', '1', '草莓', null, '22.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('288', '10226', '10262', '12.00', '1', '儿童 新生婴儿纯棉防水围脖 围嘴 口水巾 宝宝超可爱绣花围兜围嘴', null, '12.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('289', '10226', '10258', '44.00', '1', '致我们终将逝去的青春', null, '44.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('290', '10227', '10262', '12.00', '1', '儿童 新生婴儿纯棉防水围脖 围嘴 口水巾 宝宝超可爱绣花围兜围嘴', null, '12.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('291', '10227', '10258', '44.00', '1', '致我们终将逝去的青春', null, '44.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('292', '10227', '10269', '16.00', '1', '玛尚N6000 USB笔记本电脑有线鼠标 女生可爱鼠标有线 USB鼠标', null, '16.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('293', '10228', '10262', '12.00', '1', '儿童 新生婴儿纯棉防水围脖 围嘴 口水巾 宝宝超可爱绣花围兜围嘴', null, '12.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('294', '10228', '10258', '44.00', '1', '致我们终将逝去的青春', null, '44.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('295', '10228', '10269', '16.00', '1', '玛尚N6000 USB笔记本电脑有线鼠标 女生可爱鼠标有线 USB鼠标', null, '16.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('296', '10228', '10261', '998.00', '1', '笑巴喜儿童餐椅 多功能实木无漆宝宝餐椅 高度两档调节婴儿餐椅', null, '998.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('297', '10229', '10258', '44.00', '1', '致我们终将逝去的青春', null, '44.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('298', '10230', '10267', '10888.00', '1', 'Acer/宏基 P3-171-3322Y2G06as 11.6英寸 超极本 Win8 6', null, '10888.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('299', '10231', '10267', '10888.00', '1', 'Acer/宏基 P3-171-3322Y2G06as 11.6英寸 超极本 Win8 6', null, '10888.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('300', '10232', '10267', '10888.00', '1', 'Acer/宏基 P3-171-3322Y2G06as 11.6英寸 超极本 Win8 6', null, '10888.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('301', '10233', '10267', '10888.00', '1', 'Acer/宏基 P3-171-3322Y2G06as 11.6英寸 超极本 Win8 6', null, '10888.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('302', '10234', '10267', '10888.00', '1', 'Acer/宏基 P3-171-3322Y2G06as 11.6英寸 超极本 Win8 6', null, '10888.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('303', '10235', '10258', '44.00', '1', '致我们终将逝去的青春', null, '44.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('304', '10236', '10258', '44.00', '1', '致我们终将逝去的青春', null, '44.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('305', '10236', '10263', '2998.00', '1', '【送米兔+贴膜】MIUI/小米 小米手机3代M3MI3小米3米联通移动官网', null, '2998.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('306', '10237', '10267', '10888.00', '1', 'Acer/宏基 P3-171-3322Y2G06as 11.6英寸 超极本 Win8 6', null, '10888.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('307', '10238', '10267', '10888.00', '1', 'Acer/宏基 P3-171-3322Y2G06as 11.6英寸 超极本 Win8 6', null, '10888.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('308', '10239', '10267', '10888.00', '1', 'Acer/宏基 P3-171-3322Y2G06as 11.6英寸 超极本 Win8 6', null, '10888.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('309', '10240', '10267', '10888.00', '1', 'Acer/宏基 P3-171-3322Y2G06as 11.6英寸 超极本 Win8 6', null, '10888.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('310', '10241', '10263', '2998.00', '1', '【送米兔+贴膜】MIUI/小米 小米手机3代M3MI3小米3米联通移动官网', null, '2998.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('311', '10242', '10263', '2998.00', '2', '【送米兔+贴膜】MIUI/小米 小米手机3代M3MI3小米3米联通移动官网', null, '5996.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('312', '10243', '10267', '10888.00', '1', 'Acer/宏基 P3-171-3322Y2G06as 11.6英寸 超极本 Win8 6', null, '10888.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('313', '10244', '10263', '2998.00', '1', '【送米兔+贴膜】MIUI/小米 小米手机3代M3MI3小米3米联通移动官网', null, '2998.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('314', '10245', '10253', '44.00', '2', '香甜可口的苹果', null, '88.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('315', '10247', '10267', '10888.00', '9996', 'Acer/宏基 P3-171-3322Y2G06as 11.6英寸 超极本 Win8 6', null, '108836448.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('316', '10248', '10267', '10888.00', '9996', 'Acer/宏基 P3-171-3322Y2G06as 11.6英寸 超极本 Win8 6', null, '108836448.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('317', '10249', '10263', '2998.00', '996', '【送米兔+贴膜】MIUI/小米 小米手机3代M3MI3小米3米联通移动官网', null, '2986008.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('318', '10250', '10317', '44.00', '222', '边城（沈从文著中篇小说）', null, '9768.00', 'y', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('319', '10251', '10267', '10888.00', '1', 'Acer/宏基 P3-171-3322Y2G06as 11.6英寸 超极本 Win8 6', null, '10888.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('320', '10251', '10265', '8666.00', '1', 'Lenovo/联想 Lenovo G480A-ITH(D) G470 I3I5I714寸', null, '8666.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('321', '10252', '10267', '10888.00', '12', 'Acer/宏基 P3-171-3322Y2G06as 11.6英寸 超极本 Win8 6', null, '130656.00', 'n', 'n', '238', null, null);
INSERT INTO `t_orderdetail` VALUES ('322', '10253', '10258', '44.00', '21', '致我们终将逝去的青春', null, '924.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('323', '10254', '10267', '10888.00', '21', 'Acer/宏基 P3-171-3322Y2G06as 11.6英寸 超极本 Win8 6', null, '228648.00', 'y', 'n', '238', null, null);
INSERT INTO `t_orderdetail` VALUES ('324', '10255', '10316', '86.00', '1', '阿Q正传（呐喊—鲁迅小说）', null, '86.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('325', '10256', '10265', '8666.00', '6', 'Lenovo/联想 Lenovo G480A-ITH(D) G470 I3I5I714寸', null, '51996.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('326', '10257', '10259', '43.00', '1', '我所理解的生活', null, '43.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('327', '10258', '10253', '44.00', '1', '香甜可口的苹果', null, '44.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('328', '10259', '10308', '90.00', '1', '钢铁是怎样炼成的', null, '90.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('329', '10260', '10263', '2998.00', '1', '【送米兔+贴膜】MIUI/小米 小米手机3代M3MI3小米3米联通移动官网', null, '2998.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('330', '10261', '10263', '2998.00', '1', '【送米兔+贴膜】MIUI/小米 小米手机3代M3MI3小米3米联通移动官网', null, '2998.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('331', '10262', '10263', '2998.00', '2', '【送米兔+贴膜】MIUI/小米 小米手机3代M3MI3小米3米联通移动官网', null, '5996.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('332', '10263', '10307', '60.00', '1', '鲁滨孙漂流记', null, '60.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('333', '10264', '10307', '60.00', '1', '鲁滨孙漂流记', null, '60.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('334', '10265', '10307', '60.00', '1', '鲁滨孙漂流记', null, '60.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('335', '10266', '10286', '16.00', '1', '正版预售 不畏将来 不念过去 谁的青春不迷茫 文学 励志书籍 博库网', null, '16.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('336', '10267', '10286', '16.00', '1', '正版预售 不畏将来 不念过去 谁的青春不迷茫 文学 励志书籍 博库网', null, '16.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('337', '10268', '10258', '44.00', '1', '致我们终将逝去的青春', null, '44.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('338', '10269', '10286', '16.00', '1', '正版预售 不畏将来 不念过去 谁的青春不迷茫 文学 励志书籍 博库网', null, '16.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('339', '10270', '10307', '60.00', '1', '鲁滨孙漂流记', null, '60.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('340', '10271', '10263', '2998.00', '1', '【送米兔+贴膜】MIUI/小米 小米手机3代M3MI3小米3米联通移动官网', null, '2998.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('341', '10272', '10263', '2998.00', '1', '【送米兔+贴膜】MIUI/小米 小米手机3代M3MI3小米3米联通移动官网', null, '2998.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('342', '10273', '10263', '2998.00', '1', '【送米兔+贴膜】MIUI/小米 小米手机3代M3MI3小米3米联通移动官网', null, '2998.00', 'n', 'n', null, null, null);
INSERT INTO `t_orderdetail` VALUES ('343', '10273', '10258', '44.00', '1', '致我们终将逝去的青春', null, '44.00', 'n', 'n', null, null, null);

-- ----------------------------
-- Table structure for `t_orderlog`
-- ----------------------------
DROP TABLE IF EXISTS `t_orderlog`;
CREATE TABLE `t_orderlog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `orderid` varchar(11) COLLATE utf8_unicode_ci NOT NULL,
  `account` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `createdate` datetime DEFAULT NULL,
  `content` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `accountType` varchar(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=760 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_orderlog
-- ----------------------------
INSERT INTO `t_orderlog` VALUES ('1', '10061', 'admin', '2014-01-19 19:11:07', '【增加支付记录】增22.0;', null);
INSERT INTO `t_orderlog` VALUES ('2', '10061', 'admin', '2014-01-19 19:18:27', '【增加支付记录】增33.0;', null);
INSERT INTO `t_orderlog` VALUES ('3', '10062', 'admin', '2014-01-22 14:44:10', '【增加支付记录】增22.0￥;', null);
INSERT INTO `t_orderlog` VALUES ('4', '10062', 'admin', '2014-01-22 14:44:32', '【增加支付记录】增31.0￥;', null);
INSERT INTO `t_orderlog` VALUES ('5', '10062', 'admin', '2014-01-22 14:45:29', '【增加支付记录】增3.0￥;', null);
INSERT INTO `t_orderlog` VALUES ('6', '10062', 'admin', '2014-01-22 14:46:19', '【增加支付记录】增2.0￥;', null);
INSERT INTO `t_orderlog` VALUES ('7', '10062', 'admin', '2014-01-22 14:46:25', '【增加支付记录】增333.0￥;', null);
INSERT INTO `t_orderlog` VALUES ('8', '10063', 'admin', '2014-01-22 17:32:52', '【增加支付记录】增44.0￥;', null);
INSERT INTO `t_orderlog` VALUES ('9', '10065', 'admin', '2014-01-22 19:07:31', '【增加支付记录】增4.0￥;', null);
INSERT INTO `t_orderlog` VALUES ('10', '10066', 'admin', '2014-01-25 13:33:42', '【增加支付记录】增22.0￥;', null);
INSERT INTO `t_orderlog` VALUES ('11', '10066', 'admin', '2014-01-25 13:39:39', '【已发货】', null);
INSERT INTO `t_orderlog` VALUES ('12', '10066', 'admin', '2014-01-25 13:39:48', '【已签收】', null);
INSERT INTO `t_orderlog` VALUES ('13', '10066', 'admin', '2014-01-25 13:39:58', '【已归档】', null);
INSERT INTO `t_orderlog` VALUES ('14', '10067', 'admin', '2014-01-26 19:38:49', '【增加支付记录】增1111.0￥;', null);
INSERT INTO `t_orderlog` VALUES ('15', '10068', 'admin', '2014-01-26 19:43:18', '【增加支付记录】增344.0￥;', null);
INSERT INTO `t_orderlog` VALUES ('16', '10069', 'admin', '2014-01-27 19:24:29', '【增加支付记录】增323.0￥;', null);
INSERT INTO `t_orderlog` VALUES ('17', '10069', 'admin', '2014-01-27 19:25:23', '【审核通过】', null);
INSERT INTO `t_orderlog` VALUES ('18', '10069', 'admin', '2014-01-27 19:25:39', '【已发货】', null);
INSERT INTO `t_orderlog` VALUES ('19', '10069', 'admin', '2014-01-27 19:25:45', '【已签收】', null);
INSERT INTO `t_orderlog` VALUES ('20', '10069', 'admin', '2014-01-27 19:25:56', '【已归档】', null);
INSERT INTO `t_orderlog` VALUES ('21', '10073', 'admin', '2014-01-27 19:55:14', '【取消订单】', null);
INSERT INTO `t_orderlog` VALUES ('22', '10074', 'test1', '2014-01-27 20:09:20', '【创建订单】用户创建订单', null);
INSERT INTO `t_orderlog` VALUES ('23', '10075', 'test1', '2014-01-27 20:23:12', '【创建订单】用户创建订单', null);
INSERT INTO `t_orderlog` VALUES ('24', '10075', 'admin', '2014-01-27 20:24:02', '【增加支付记录】增560.0￥;', null);
INSERT INTO `t_orderlog` VALUES ('25', '10075', 'admin', '2014-01-27 20:28:45', '【审核通过】', null);
INSERT INTO `t_orderlog` VALUES ('26', '10075', 'admin', '2014-01-27 20:28:51', '【已发货】', null);
INSERT INTO `t_orderlog` VALUES ('27', '10075', 'admin', '2014-01-27 20:29:02', '【已签收】', null);
INSERT INTO `t_orderlog` VALUES ('28', '10074', 'admin', '2014-01-27 21:13:24', '【增加支付记录】增21.0￥;', null);
INSERT INTO `t_orderlog` VALUES ('29', '10074', 'admin', '2014-01-27 21:13:30', '【审核通过】', null);
INSERT INTO `t_orderlog` VALUES ('30', '10074', 'admin', '2014-01-27 21:13:33', '【已发货】', null);
INSERT INTO `t_orderlog` VALUES ('31', '10075', 'admin', '2014-01-27 21:22:32', '【已归档】', null);
INSERT INTO `t_orderlog` VALUES ('32', '10071', 'admin', '2014-01-29 10:33:29', '【修改订单总金额】总金额修改为：44.0，修改人输入备注：修改订单金额备注', null);
INSERT INTO `t_orderlog` VALUES ('33', '10071', 'admin', '2014-01-29 10:43:39', '【修改订单总金额】总金额修改为：32.0', null);
INSERT INTO `t_orderlog` VALUES ('34', '10076', 'test1', '2014-01-29 11:06:26', '【创建订单】用户创建订单', null);
INSERT INTO `t_orderlog` VALUES ('35', '10077', 'test1', '2014-01-29 11:09:23', '【创建订单】用户创建订单', null);
INSERT INTO `t_orderlog` VALUES ('36', '10078', 'test1', '2014-01-29 11:09:42', '【创建订单】用户创建订单', null);
INSERT INTO `t_orderlog` VALUES ('37', '10078', 'admin', '2014-01-29 11:19:06', '【修改订单总金额】总金额修改为：31.0', null);
INSERT INTO `t_orderlog` VALUES ('38', '10073', 'admin', '2014-01-29 11:19:45', '【修改订单总金额】总金额修改为：323.0', null);
INSERT INTO `t_orderlog` VALUES ('39', '10081', 'test1', '2014-01-29 11:38:49', '【创建订单】用户创建订单。订单总金额：28.0', null);
INSERT INTO `t_orderlog` VALUES ('40', '10082', 'test1', '2014-01-29 11:40:05', '【创建订单】用户创建订单。订单总金额：28.0', 'w');
INSERT INTO `t_orderlog` VALUES ('41', '10082', 'admin', '2014-01-29 11:40:41', '【修改订单总金额】总金额修改为：34.0', null);
INSERT INTO `t_orderlog` VALUES ('42', '10082', 'admin', '2014-01-29 11:41:58', '【修改订单总金额】总金额修改为：322.0', 'm');
INSERT INTO `t_orderlog` VALUES ('43', '10082', 'admin', '2014-01-29 11:42:09', '【修改订单总金额】总金额修改为：32131.0', 'm');
INSERT INTO `t_orderlog` VALUES ('45', '10082', 'admin', '2014-01-29 11:42:32', '【修改订单总金额】总金额修改为：500000.0', 'm');
INSERT INTO `t_orderlog` VALUES ('48', '10082', 'admin', '2014-01-29 11:43:52', '【修改订单总金额】总金额修改为：5000000.0', 'm');
INSERT INTO `t_orderlog` VALUES ('49', '10082', 'admin', '2014-01-29 11:44:11', '【增加支付记录】增5000000.0￥;', null);
INSERT INTO `t_orderlog` VALUES ('50', '10083', 'test1', '2014-01-29 20:00:31', '【创建订单】用户创建订单。订单总金额：98.0', 'w');
INSERT INTO `t_orderlog` VALUES ('51', '10083', 'admin', '2014-02-03 11:08:35', '【取消订单】', 'm');
INSERT INTO `t_orderlog` VALUES ('52', '10082', 'admin', '2014-02-04 19:59:23', '【审核通过】', 'm');
INSERT INTO `t_orderlog` VALUES ('53', '10082', 'admin', '2014-02-04 19:59:36', '【已发货】', 'm');
INSERT INTO `t_orderlog` VALUES ('54', '10084', 'test1', '2014-02-04 21:29:02', '【创建订单】用户创建订单。订单总金额：1001.0', 'w');
INSERT INTO `t_orderlog` VALUES ('55', '10084', 'admin', '2014-02-04 21:29:59', '【修改订单总金额】总金额修改为：1.0', 'm');
INSERT INTO `t_orderlog` VALUES ('56', '10084', 'admin', '2014-02-04 21:31:06', '【增加支付记录】增1.0￥;', 'm');
INSERT INTO `t_orderlog` VALUES ('57', '10084', 'admin', '2014-02-04 21:31:21', '【审核通过】', 'm');
INSERT INTO `t_orderlog` VALUES ('58', '10084', 'admin', '2014-02-04 21:31:29', '【已发货】', 'm');
INSERT INTO `t_orderlog` VALUES ('59', '10084', 'admin', '2014-02-04 21:32:06', '【已签收】', 'm');
INSERT INTO `t_orderlog` VALUES ('60', '10085', 'test2', '2014-02-04 21:43:29', '【创建订单】用户创建订单。订单总金额：76.0', 'w');
INSERT INTO `t_orderlog` VALUES ('61', '10086', 'test2', '2014-02-05 19:47:32', '【创建订单】用户创建订单。订单总金额：7.0', 'w');
INSERT INTO `t_orderlog` VALUES ('62', '10087', 'test2', '2014-02-05 19:48:34', '【创建订单】用户创建订单。订单总金额：582.0', 'w');
INSERT INTO `t_orderlog` VALUES ('63', '10088', 'test2', '2014-02-05 20:31:53', '【创建订单】用户创建订单。订单总金额：166.0', 'w');
INSERT INTO `t_orderlog` VALUES ('64', '10088', 'admin', '2014-02-10 10:34:51', '【增加支付记录】增45.0￥;', 'm');
INSERT INTO `t_orderlog` VALUES ('65', '10088', 'admin', '2014-02-12 10:19:47', '【审核通过】', 'm');
INSERT INTO `t_orderlog` VALUES ('66', '10089', 'zhangfei', '2014-02-13 16:25:21', '【创建订单】用户创建订单。订单总金额：16153.0', 'w');
INSERT INTO `t_orderlog` VALUES ('67', '10083', 'admin', '2014-02-14 16:17:16', '【已归档】', 'm');
INSERT INTO `t_orderlog` VALUES ('68', '10083', 'admin', '2014-02-14 16:17:19', '【已签收】', 'm');
INSERT INTO `t_orderlog` VALUES ('69', '10083', 'admin', '2014-02-14 16:17:20', '【已发货】', 'm');
INSERT INTO `t_orderlog` VALUES ('70', '10089', 'admin', '2014-02-14 18:10:43', '【增加支付记录】增44.0￥;', 'm');
INSERT INTO `t_orderlog` VALUES ('71', '10089', 'admin', '2014-02-14 18:11:12', '【审核通过】', 'm');
INSERT INTO `t_orderlog` VALUES ('72', '10089', 'admin', '2014-02-14 18:11:15', '【已发货】', 'm');
INSERT INTO `t_orderlog` VALUES ('73', '10089', 'admin', '2014-02-14 18:11:16', '【已签收】', 'm');
INSERT INTO `t_orderlog` VALUES ('74', '10089', 'admin', '2014-02-14 18:11:17', '【已归档】', 'm');
INSERT INTO `t_orderlog` VALUES ('75', '10087', 'admin', '2014-02-14 18:21:25', '【增加支付记录】增2.0￥;', 'm');
INSERT INTO `t_orderlog` VALUES ('76', '10087', 'admin', '2014-02-14 18:25:45', '【审核通过】', 'm');
INSERT INTO `t_orderlog` VALUES ('77', '10087', 'admin', '2014-02-14 18:31:07', '【审核通过】', 'm');
INSERT INTO `t_orderlog` VALUES ('78', '10087', 'admin', '2014-02-14 18:31:45', '【已发货】', 'm');
INSERT INTO `t_orderlog` VALUES ('79', '10087', 'admin', '2014-02-14 18:31:56', '【已签收】', 'm');
INSERT INTO `t_orderlog` VALUES ('80', '10087', 'admin', '2014-02-14 18:31:59', '【已归档】', 'm');
INSERT INTO `t_orderlog` VALUES ('81', '10087', 'admin', '2014-02-14 18:32:28', '【已发货】', 'm');
INSERT INTO `t_orderlog` VALUES ('82', '10087', 'admin', '2014-02-14 18:32:49', '【已签收】', 'm');
INSERT INTO `t_orderlog` VALUES ('83', '10087', 'admin', '2014-02-14 18:32:56', '【已归档】', 'm');
INSERT INTO `t_orderlog` VALUES ('84', '10087', 'admin', '2014-02-14 18:33:29', '【已发货】', 'm');
INSERT INTO `t_orderlog` VALUES ('85', '10087', 'admin', '2014-02-14 18:41:04', '【审核通过】', 'm');
INSERT INTO `t_orderlog` VALUES ('86', '10087', 'admin', '2014-02-14 18:41:05', '【已发货】', 'm');
INSERT INTO `t_orderlog` VALUES ('87', '10087', 'admin', '2014-02-14 18:41:46', '【审核通过】', 'm');
INSERT INTO `t_orderlog` VALUES ('88', '10090', 'zhangfei', '2014-02-15 09:42:41', '【创建订单】用户创建订单。订单总金额：26.0', 'w');
INSERT INTO `t_orderlog` VALUES ('89', '10064', 'system', '2014-02-15 12:40:35', '【系统取消】一周内未完全支付并且未审核的订单系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('90', '10070', 'system', '2014-02-15 12:40:35', '【系统取消】一周内未完全支付并且未审核的订单系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('91', '10071', 'system', '2014-02-15 12:40:35', '【系统取消】一周内未完全支付并且未审核的订单系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('92', '10072', 'system', '2014-02-15 12:40:36', '【系统取消】一周内未完全支付并且未审核的订单系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('93', '10076', 'system', '2014-02-15 12:40:36', '【系统取消】一周内未完全支付并且未审核的订单系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('94', '10077', 'system', '2014-02-15 12:40:36', '【系统取消】一周内未完全支付并且未审核的订单系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('95', '10078', 'system', '2014-02-15 12:40:36', '【系统取消】一周内未完全支付并且未审核的订单系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('96', '10079', 'system', '2014-02-15 12:40:37', '【系统取消】一周内未完全支付并且未审核的订单系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('97', '10080', 'system', '2014-02-15 12:40:37', '【系统取消】一周内未完全支付并且未审核的订单系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('98', '10081', 'system', '2014-02-15 12:40:37', '【系统取消】一周内未完全支付并且未审核的订单系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('99', '10085', 'system', '2014-02-15 12:40:37', '【系统取消】一周内未完全支付并且未审核的订单系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('100', '10086', 'system', '2014-02-15 12:40:38', '【系统取消】一周内未完全支付并且未审核的订单系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('101', '10091', 'test1', '2014-02-17 11:04:43', '【创建订单】用户创建订单。订单总金额：11127.0', 'w');
INSERT INTO `t_orderlog` VALUES ('102', '10092', 'test1', '2014-02-17 11:05:19', '【创建订单】用户创建订单。订单总金额：49.0', 'w');
INSERT INTO `t_orderlog` VALUES ('103', '10093', 'test1', '2014-02-17 11:15:45', '【创建订单】用户创建订单。订单总金额：49.0', 'w');
INSERT INTO `t_orderlog` VALUES ('104', '10094', 'test1', '2014-02-17 11:21:08', '【创建订单】用户创建订单。订单总金额：27.0', 'w');
INSERT INTO `t_orderlog` VALUES ('105', '10095', 'test1', '2014-02-17 11:24:24', '【创建订单】用户创建订单。订单总金额：49.0', 'w');
INSERT INTO `t_orderlog` VALUES ('106', '10096', 'test1', '2014-02-17 11:26:05', '【创建订单】用户创建订单。订单总金额：49.0', 'w');
INSERT INTO `t_orderlog` VALUES ('107', '10097', 'test1', '2014-02-17 12:01:29', '【创建订单】用户创建订单。订单总金额：27.0', 'w');
INSERT INTO `t_orderlog` VALUES ('108', '10098', 'test1', '2014-02-17 12:08:15', '【创建订单】用户创建订单。订单总金额：49.0', 'w');
INSERT INTO `t_orderlog` VALUES ('109', '10099', 'test1', '2014-02-17 12:18:58', '【创建订单】用户创建订单。订单总金额：49.0', 'w');
INSERT INTO `t_orderlog` VALUES ('110', '10100', 'test1', '2014-02-17 12:21:55', '【创建订单】用户创建订单。订单总金额：49.0', 'w');
INSERT INTO `t_orderlog` VALUES ('111', '10101', 'test1', '2014-02-17 13:32:30', '【创建订单】用户创建订单。订单总金额：26.0', 'w');
INSERT INTO `t_orderlog` VALUES ('112', '10102', 'test1', '2014-02-17 13:34:14', '【创建订单】用户创建订单。订单总金额：26.0', 'w');
INSERT INTO `t_orderlog` VALUES ('113', '10103', 'test1', '2014-02-17 13:35:10', '【创建订单】用户创建订单。订单总金额：26.0', 'w');
INSERT INTO `t_orderlog` VALUES ('114', '10104', 'test1', '2014-02-17 13:37:23', '【创建订单】用户创建订单。订单总金额：49.0', 'w');
INSERT INTO `t_orderlog` VALUES ('115', '10105', 'test1', '2014-02-17 13:39:58', '【创建订单】用户创建订单。订单总金额：49.0', 'w');
INSERT INTO `t_orderlog` VALUES ('116', '10106', 'test1', '2014-02-17 13:41:57', '【创建订单】用户创建订单。订单总金额：49.0', 'w');
INSERT INTO `t_orderlog` VALUES ('117', '10107', 'test1', '2014-02-17 13:44:37', '【创建订单】用户创建订单。订单总金额：49.0', 'w');
INSERT INTO `t_orderlog` VALUES ('118', '10108', 'test1', '2014-02-17 13:45:17', '【创建订单】用户创建订单。订单总金额：49.0', 'w');
INSERT INTO `t_orderlog` VALUES ('119', '10109', 'test1', '2014-02-17 13:52:12', '【创建订单】用户创建订单。订单总金额：49.0', 'w');
INSERT INTO `t_orderlog` VALUES ('120', '10110', 'test1', '2014-02-17 13:55:12', '【创建订单】用户创建订单。订单总金额：49.0', 'w');
INSERT INTO `t_orderlog` VALUES ('121', '10111', 'test1', '2014-02-17 14:02:54', '【创建订单】用户创建订单。订单总金额：49.0', 'w');
INSERT INTO `t_orderlog` VALUES ('122', '10112', 'test1', '2014-02-17 14:03:17', '【创建订单】用户创建订单。订单总金额：11176.0', 'w');
INSERT INTO `t_orderlog` VALUES ('123', '10113', 'test1', '2014-02-17 14:25:54', '【创建订单】用户创建订单。订单总金额：11176.0', 'w');
INSERT INTO `t_orderlog` VALUES ('124', '10114', 'test1', '2014-02-17 14:33:37', '【创建订单】用户创建订单。订单总金额：11176.0', 'w');
INSERT INTO `t_orderlog` VALUES ('125', '10115', 'test2', '2014-02-18 11:21:59', '【创建订单】用户创建订单。订单总金额：44586.0', 'w');
INSERT INTO `t_orderlog` VALUES ('126', '10116', 'test2', '2014-02-18 11:29:58', '【创建订单】用户创建订单。订单总金额：44630.0', 'w');
INSERT INTO `t_orderlog` VALUES ('127', '10117', 'test2', '2014-02-18 11:31:54', '【创建订单】用户创建订单。订单总金额：137.0', 'w');
INSERT INTO `t_orderlog` VALUES ('128', '10118', 'test2', '2014-02-18 11:32:12', '【创建订单】用户创建订单。订单总金额：11127.0', 'w');
INSERT INTO `t_orderlog` VALUES ('129', '10119', 'test2', '2014-02-18 11:38:42', '【创建订单】用户创建订单。订单总金额：49.0', 'w');
INSERT INTO `t_orderlog` VALUES ('130', '10120', 'test2', '2014-02-18 11:42:30', '【创建订单】用户创建订单。订单总金额：49.0', 'w');
INSERT INTO `t_orderlog` VALUES ('131', '10121', 'test2', '2014-02-18 14:06:37', '【创建订单】用户创建订单。订单总金额：142.0', 'w');
INSERT INTO `t_orderlog` VALUES ('132', '10122', 'test1', '2014-02-18 16:19:45', '【创建订单】用户创建订单。订单总金额：53.0', 'w');
INSERT INTO `t_orderlog` VALUES ('133', '10122', 'admin', '2014-02-19 09:49:17', '【修改订单总金额】总金额修改为：1.0', 'm');
INSERT INTO `t_orderlog` VALUES ('134', '10122', 'admin', '2014-02-19 09:49:23', '【修改订单总金额】总金额修改为：0.1', 'm');
INSERT INTO `t_orderlog` VALUES ('135', '10123', 'test1', '2014-02-19 10:22:59', '【创建订单】用户创建订单。订单总金额：11127.0', 'w');
INSERT INTO `t_orderlog` VALUES ('136', '10123', 'admin', '2014-02-19 10:23:19', '【修改订单总金额】总金额修改为：1.0', 'm');
INSERT INTO `t_orderlog` VALUES ('137', '10124', 'test1', '2014-02-19 10:34:54', '【创建订单】用户创建订单。订单总金额：53.0', 'w');
INSERT INTO `t_orderlog` VALUES ('138', '10124', 'admin', '2014-02-19 10:35:11', '【修改订单总金额】总金额修改为：0.1', 'm');
INSERT INTO `t_orderlog` VALUES ('139', '10125', 'test1', '2014-02-19 10:38:15', '【创建订单】用户创建订单。订单总金额：49.0', 'w');
INSERT INTO `t_orderlog` VALUES ('140', '10125', 'admin', '2014-02-19 10:38:39', '【修改订单总金额】总金额修改为：0.1', 'm');
INSERT INTO `t_orderlog` VALUES ('141', '10126', 'test1', '2014-02-19 10:42:31', '【创建订单】用户创建订单。订单总金额：49.0', 'w');
INSERT INTO `t_orderlog` VALUES ('142', '10126', 'admin', '2014-02-19 10:42:41', '【修改订单总金额】总金额修改为：0.1', 'm');
INSERT INTO `t_orderlog` VALUES ('143', '10127', 'test1', '2014-02-19 10:43:37', '【创建订单】用户创建订单。订单总金额：93.0', 'w');
INSERT INTO `t_orderlog` VALUES ('144', '10127', 'admin', '2014-02-19 10:43:49', '【修改订单总金额】总金额修改为：0.1', 'm');
INSERT INTO `t_orderlog` VALUES ('145', '10128', 'test1', '2014-02-19 12:14:18', '【创建订单】用户创建订单。订单总金额：26.0', 'w');
INSERT INTO `t_orderlog` VALUES ('146', '10128', 'admin', '2014-02-19 12:14:36', '【修改订单总金额】总金额修改为：0.1', 'm');
INSERT INTO `t_orderlog` VALUES ('147', '10129', 'test1', '2014-02-19 12:17:21', '【创建订单】用户创建订单。订单总金额：22249.0', 'w');
INSERT INTO `t_orderlog` VALUES ('148', '10129', 'admin', '2014-02-19 12:17:31', '【修改订单总金额】总金额修改为：0.1', 'm');
INSERT INTO `t_orderlog` VALUES ('149', '10130', 'test1', '2014-02-19 16:00:29', '【创建订单】用户创建订单。订单总金额：11127.0', 'w');
INSERT INTO `t_orderlog` VALUES ('150', '10130', 'admin', '2014-02-19 16:00:56', '【修改订单总金额】总金额修改为：0.01', 'm');
INSERT INTO `t_orderlog` VALUES ('151', '10131', 'test1', '2014-02-19 16:05:02', '【创建订单】用户创建订单。订单总金额：11127.0', 'w');
INSERT INTO `t_orderlog` VALUES ('152', '10131', 'admin', '2014-02-19 16:05:15', '【修改订单总金额】总金额修改为：0.01', 'm');
INSERT INTO `t_orderlog` VALUES ('153', '10133', 'test1', '2014-02-19 16:59:55', '【创建订单】用户创建订单。订单总金额：11127.0', 'w');
INSERT INTO `t_orderlog` VALUES ('154', '10133', 'admin', '2014-02-19 17:00:44', '【修改订单总金额】总金额修改为：0.01', 'm');
INSERT INTO `t_orderlog` VALUES ('155', '10134', 'test1', '2014-02-19 17:10:51', '【创建订单】用户创建订单。订单总金额：11127.0', 'w');
INSERT INTO `t_orderlog` VALUES ('156', '10134', 'admin', '2014-02-19 17:23:50', '【修改订单总金额】总金额修改为：0.01', 'm');
INSERT INTO `t_orderlog` VALUES ('157', '10135', 'test1', '2014-02-19 17:49:15', '【创建订单】用户创建订单。订单总金额：49.0', 'w');
INSERT INTO `t_orderlog` VALUES ('158', '10135', 'admin', '2014-02-19 17:49:37', '【修改订单总金额】总金额修改为：0.01', 'm');
INSERT INTO `t_orderlog` VALUES ('159', '10136', 'test1', '2014-02-19 17:56:35', '【创建订单】用户创建订单。订单总金额：26.0', 'w');
INSERT INTO `t_orderlog` VALUES ('160', '10136', 'admin', '2014-02-19 17:56:58', '【修改订单总金额】总金额修改为：0.01', 'm');
INSERT INTO `t_orderlog` VALUES ('161', '10136', 'admin', '2014-02-19 18:56:02', '【审核通过】', 'm');
INSERT INTO `t_orderlog` VALUES ('162', '10136', 'admin', '2014-02-19 18:56:09', '【已发货】', 'm');
INSERT INTO `t_orderlog` VALUES ('163', '10137', '_out_1392903252104', '2014-02-20 21:36:46', '【创建订单】用户创建订单。订单总金额：27.0', 'w');
INSERT INTO `t_orderlog` VALUES ('164', '10138', '_out_1392903252104', '2014-02-20 21:40:19', '【创建订单】用户创建订单。订单总金额：27.0', 'w');
INSERT INTO `t_orderlog` VALUES ('165', '10139', 'test1', '2014-02-21 09:03:03', '【创建订单】用户创建订单。订单总金额：26.0', 'w');
INSERT INTO `t_orderlog` VALUES ('166', '10140', 'test1', '2014-02-21 09:03:49', '【创建订单】用户创建订单。订单总金额：26.0', 'w');
INSERT INTO `t_orderlog` VALUES ('167', '10141', 'test1', '2014-02-22 10:32:58', '【创建订单】用户创建订单。订单总金额：11127.0', 'w');
INSERT INTO `t_orderlog` VALUES ('168', '10142', 'test1', '2014-02-22 10:34:12', '【创建订单】用户创建订单。订单总金额：11127.0', 'w');
INSERT INTO `t_orderlog` VALUES ('169', '10143', '_out_1393056233281', '2014-02-22 17:26:15', '【创建订单】用户创建订单。订单总金额：11127.0', 'w');
INSERT INTO `t_orderlog` VALUES ('170', '10144', '_out_1393056233281', '2014-02-22 17:27:44', '【创建订单】用户创建订单。订单总金额：11176.0', 'w');
INSERT INTO `t_orderlog` VALUES ('171', '10145', '_out_1393056233281', '2014-02-22 17:28:49', '【创建订单】用户创建订单。订单总金额：22298.0', 'w');
INSERT INTO `t_orderlog` VALUES ('172', '10146', '_out_1392903252104', '2014-02-22 20:27:56', '【创建订单】用户创建订单。订单总金额：11127.0', 'w');
INSERT INTO `t_orderlog` VALUES ('173', '10147', '_out_1392903252104', '2014-02-22 20:29:29', '【创建订单】用户创建订单。订单总金额：11127.0', 'w');
INSERT INTO `t_orderlog` VALUES ('174', '10148', '_out_1392903252104', '2014-02-22 20:32:18', '【创建订单】用户创建订单。订单总金额：11127.0', 'w');
INSERT INTO `t_orderlog` VALUES ('175', '10149', 'test1', '2014-02-22 20:34:12', '【创建订单】用户创建订单。订单总金额：27.0', 'w');
INSERT INTO `t_orderlog` VALUES ('176', '10150', 'test1', '2014-02-22 20:37:50', '【创建订单】用户创建订单。订单总金额：27.0', 'w');
INSERT INTO `t_orderlog` VALUES ('177', '10152', 'test1', '2014-02-22 21:46:03', '【创建订单】用户创建订单。订单总金额：49.0', 'w');
INSERT INTO `t_orderlog` VALUES ('178', '10153', 'test1', '2014-02-22 23:30:55', '【创建订单】用户创建订单。订单总金额：22354.0', 'w');
INSERT INTO `t_orderlog` VALUES ('179', '10154', 'test1', '2014-02-22 23:35:43', '【创建订单】用户创建订单。订单总金额：319.0', 'w');
INSERT INTO `t_orderlog` VALUES ('180', '10155', '_out_1393056233281', '2014-02-23 09:28:35', '【创建订单】用户创建订单。订单总金额：11127.0', 'w');
INSERT INTO `t_orderlog` VALUES ('181', '10156', '_out_1393056233281', '2014-02-23 09:29:12', '【创建订单】用户创建订单。订单总金额：11127.0', 'w');
INSERT INTO `t_orderlog` VALUES ('182', '10157', '_out_1393056233281', '2014-02-23 09:35:50', '【创建订单】用户创建订单。订单总金额：11127.0', 'w');
INSERT INTO `t_orderlog` VALUES ('183', '10158', '_out_1393056233281', '2014-02-23 09:52:50', '【创建订单】用户创建订单。订单总金额：11127.0', 'w');
INSERT INTO `t_orderlog` VALUES ('184', '10159', 'test1', '2014-02-23 10:29:00', '【创建订单】用户创建订单。订单总金额：11127.0', 'w');
INSERT INTO `t_orderlog` VALUES ('185', '10160', 'test1', '2014-02-23 10:29:41', '【创建订单】用户创建订单。订单总金额：11137.0', 'w');
INSERT INTO `t_orderlog` VALUES ('186', '10161', 'test1', '2014-02-23 10:35:32', '【创建订单】用户创建订单。订单总金额：11137.0', 'w');
INSERT INTO `t_orderlog` VALUES ('187', '10162', 'test1', '2014-02-23 10:38:35', '【创建订单】用户创建订单。订单总金额：11137.0', 'w');
INSERT INTO `t_orderlog` VALUES ('188', '10163', 'test1', '2014-02-23 10:38:50', '【创建订单】用户创建订单。订单总金额：11137.0', 'w');
INSERT INTO `t_orderlog` VALUES ('189', '10164', 'test1', '2014-02-23 10:39:12', '【创建订单】用户创建订单。订单总金额：11137.0', 'w');
INSERT INTO `t_orderlog` VALUES ('190', '10165', 'test1', '2014-02-23 10:39:41', '【创建订单】用户创建订单。订单总金额：11137.0', 'w');
INSERT INTO `t_orderlog` VALUES ('191', '10166', 'test1', '2014-02-23 10:40:18', '【创建订单】用户创建订单。订单总金额：11137.0', 'w');
INSERT INTO `t_orderlog` VALUES ('192', '10167', 'test1', '2014-02-23 10:40:55', '【创建订单】用户创建订单。订单总金额：22249.0', 'w');
INSERT INTO `t_orderlog` VALUES ('193', '10168', 'test1', '2014-02-23 10:41:10', '【创建订单】用户创建订单。订单总金额：22249.0', 'w');
INSERT INTO `t_orderlog` VALUES ('194', '10169', 'test1', '2014-02-23 10:41:42', '【创建订单】用户创建订单。订单总金额：22249.0', 'w');
INSERT INTO `t_orderlog` VALUES ('195', '10170', 'test1', '2014-02-23 10:41:51', '【创建订单】用户创建订单。订单总金额：22249.0', 'w');
INSERT INTO `t_orderlog` VALUES ('196', '10171', 'test1', '2014-02-23 10:42:16', '【创建订单】用户创建订单。订单总金额：22249.0', 'w');
INSERT INTO `t_orderlog` VALUES ('197', '10172', 'test1', '2014-02-23 10:42:22', '【创建订单】用户创建订单。订单总金额：22259.0', 'w');
INSERT INTO `t_orderlog` VALUES ('198', '10173', 'test1', '2014-02-23 10:42:40', '【创建订单】用户创建订单。订单总金额：22259.0', 'w');
INSERT INTO `t_orderlog` VALUES ('199', '10174', 'test1', '2014-02-23 10:43:10', '【创建订单】用户创建订单。订单总金额：22259.0', 'w');
INSERT INTO `t_orderlog` VALUES ('200', '10175', 'test1', '2014-02-23 10:44:03', '【创建订单】用户创建订单。订单总金额：22259.0', 'w');
INSERT INTO `t_orderlog` VALUES ('201', '10176', 'test1', '2014-02-23 10:44:17', '【创建订单】用户创建订单。订单总金额：22259.0', 'w');
INSERT INTO `t_orderlog` VALUES ('202', '10177', 'test1', '2014-02-23 10:44:49', '【创建订单】用户创建订单。订单总金额：22259.0', 'w');
INSERT INTO `t_orderlog` VALUES ('203', '10178', 'test1', '2014-02-23 10:45:01', '【创建订单】用户创建订单。订单总金额：22259.0', 'w');
INSERT INTO `t_orderlog` VALUES ('204', '10179', 'test1', '2014-02-23 10:45:16', '【创建订单】用户创建订单。订单总金额：22259.0', 'w');
INSERT INTO `t_orderlog` VALUES ('205', '10180', 'test1', '2014-02-23 10:45:25', '【创建订单】用户创建订单。订单总金额：22259.0', 'w');
INSERT INTO `t_orderlog` VALUES ('206', '10181', 'test1', '2014-02-23 10:45:34', '【创建订单】用户创建订单。订单总金额：22259.0', 'w');
INSERT INTO `t_orderlog` VALUES ('207', '10182', 'test1', '2014-02-23 10:45:42', '【创建订单】用户创建订单。订单总金额：22259.0', 'w');
INSERT INTO `t_orderlog` VALUES ('208', '10183', 'test1', '2014-02-23 10:46:09', '【创建订单】用户创建订单。订单总金额：22259.0', 'w');
INSERT INTO `t_orderlog` VALUES ('209', '10184', 'test1', '2014-02-23 10:46:16', '【创建订单】用户创建订单。订单总金额：22259.0', 'w');
INSERT INTO `t_orderlog` VALUES ('210', '10185', 'test1', '2014-02-23 10:46:45', '【创建订单】用户创建订单。订单总金额：22259.0', 'w');
INSERT INTO `t_orderlog` VALUES ('211', '10186', 'test1', '2014-02-23 10:47:00', '【创建订单】用户创建订单。订单总金额：22259.0', 'w');
INSERT INTO `t_orderlog` VALUES ('212', '10187', 'test1', '2014-02-23 14:39:28', '【创建订单】用户创建订单。订单总金额：147.0', 'w');
INSERT INTO `t_orderlog` VALUES ('213', '10188', 'test1', '2014-02-23 14:45:43', '【创建订单】用户创建订单。订单总金额：59.0', 'w');
INSERT INTO `t_orderlog` VALUES ('214', '10189', 'test1', '2014-02-23 14:47:48', '【创建订单】用户创建订单。订单总金额：49.0', 'w');
INSERT INTO `t_orderlog` VALUES ('215', '10190', 'test1', '2014-02-23 14:49:47', '【创建订单】用户创建订单。订单总金额：59.0', 'w');
INSERT INTO `t_orderlog` VALUES ('216', '10191', 'test1', '2014-02-23 17:02:31', '【创建订单】用户创建订单。订单总金额：11127.0', 'w');
INSERT INTO `t_orderlog` VALUES ('217', '10192', 'test1', '2014-02-23 19:09:17', '【创建订单】用户创建订单。订单总金额：49.0', 'w');
INSERT INTO `t_orderlog` VALUES ('218', '10193', 'test1', '2014-02-23 19:10:40', '【创建订单】用户创建订单。订单总金额：59.0', 'w');
INSERT INTO `t_orderlog` VALUES ('219', '10194', 'test1', '2014-02-23 19:10:46', '【创建订单】用户创建订单。订单总金额：59.0', 'w');
INSERT INTO `t_orderlog` VALUES ('220', '10195', 'test1', '2014-02-23 19:10:53', '【创建订单】用户创建订单。订单总金额：44.0', 'w');
INSERT INTO `t_orderlog` VALUES ('221', '10090', 'system', '2014-02-23 20:01:22', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('222', '10196', 'test1', '2014-02-23 21:13:59', '【创建订单】用户创建订单。订单总金额：279.0', 'w');
INSERT INTO `t_orderlog` VALUES ('223', '10197', 'test1', '2014-02-23 22:22:45', '【创建订单】用户创建订单。订单总金额：11137.0', 'w');
INSERT INTO `t_orderlog` VALUES ('224', '10198', 'test1', '2014-02-23 22:24:11', '【创建订单】用户创建订单。订单总金额：11137.0', 'w');
INSERT INTO `t_orderlog` VALUES ('225', '10199', 'test1', '2014-02-23 22:25:37', '【创建订单】用户创建订单。订单总金额：11137.0', 'w');
INSERT INTO `t_orderlog` VALUES ('226', '10200', 'test1', '2014-02-23 22:27:24', '【创建订单】用户创建订单。订单总金额：11137.0', 'w');
INSERT INTO `t_orderlog` VALUES ('227', '10201', 'test1', '2014-02-23 22:28:56', '【创建订单】用户创建订单。订单总金额：11127.0', 'w');
INSERT INTO `t_orderlog` VALUES ('228', '10202', 'test1', '2014-02-24 12:14:53', '【创建订单】用户创建订单。订单总金额：36.0', 'w');
INSERT INTO `t_orderlog` VALUES ('229', '10203', 'test1', '2014-02-24 13:51:44', '【创建订单】用户创建订单。订单总金额：11181.0', 'w');
INSERT INTO `t_orderlog` VALUES ('230', '10204', 'test1', '2014-02-24 14:22:02', '【创建订单】用户创建订单。订单总金额：191.0', 'w');
INSERT INTO `t_orderlog` VALUES ('231', '10091', 'system', '2014-02-25 09:24:40', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('232', '10092', 'system', '2014-02-25 09:24:40', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('233', '10093', 'system', '2014-02-25 09:24:40', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('234', '10094', 'system', '2014-02-25 09:24:40', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('235', '10095', 'system', '2014-02-25 09:24:40', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('236', '10096', 'system', '2014-02-25 09:24:41', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('237', '10097', 'system', '2014-02-25 09:24:41', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('238', '10098', 'system', '2014-02-25 09:24:41', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('239', '10099', 'system', '2014-02-25 09:24:41', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('240', '10100', 'system', '2014-02-25 09:24:41', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('241', '10101', 'system', '2014-02-25 09:24:41', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('242', '10102', 'system', '2014-02-25 09:24:41', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('243', '10103', 'system', '2014-02-25 09:24:41', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('244', '10104', 'system', '2014-02-25 09:24:42', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('245', '10105', 'system', '2014-02-25 09:24:42', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('246', '10106', 'system', '2014-02-25 09:24:42', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('247', '10107', 'system', '2014-02-25 09:24:42', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('248', '10108', 'system', '2014-02-25 09:24:42', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('249', '10109', 'system', '2014-02-25 09:24:42', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('250', '10110', 'system', '2014-02-25 09:24:43', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('251', '10111', 'system', '2014-02-25 09:24:43', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('252', '10112', 'system', '2014-02-25 09:24:43', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('253', '10113', 'system', '2014-02-25 09:24:43', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('254', '10114', 'system', '2014-02-25 09:24:43', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('255', '10205', 'test1', '2014-02-25 16:04:02', '【创建订单】用户创建订单。订单总金额：36.0', 'w');
INSERT INTO `t_orderlog` VALUES ('256', '10206', 'test1', '2014-02-25 16:05:52', '【创建订单】用户创建订单。订单总金额：26.0', 'w');
INSERT INTO `t_orderlog` VALUES ('257', '10115', 'system', '2014-02-26 21:48:51', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('258', '10116', 'system', '2014-02-26 21:48:51', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('259', '10117', 'system', '2014-02-26 21:48:52', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('260', '10118', 'system', '2014-02-26 21:48:52', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('261', '10119', 'system', '2014-02-26 21:48:53', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('262', '10120', 'system', '2014-02-26 21:48:53', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('263', '10121', 'system', '2014-02-26 21:48:53', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('264', '10122', 'system', '2014-02-26 21:48:54', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('265', '10123', 'system', '2014-02-27 09:16:10', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('266', '10124', 'system', '2014-02-27 09:16:10', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('267', '10125', 'system', '2014-02-27 09:16:10', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('268', '10126', 'system', '2014-02-27 09:16:10', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('269', '10127', 'system', '2014-02-27 09:16:11', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('270', '10128', 'system', '2014-02-27 09:16:11', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('271', '10129', 'system', '2014-02-27 09:16:11', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('272', '10130', 'system', '2014-02-27 09:16:11', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('273', '10131', 'system', '2014-02-27 09:16:12', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('274', '10132', 'system', '2014-02-27 09:16:12', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('275', '10133', 'system', '2014-02-27 09:16:12', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('276', '10134', 'system', '2014-02-27 09:16:12', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('277', '10135', 'system', '2014-02-27 09:16:12', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('278', '10137', 'system', '2014-02-28 11:12:56', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('279', '10138', 'system', '2014-02-28 11:12:56', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('280', '10207', 'test1', '2014-02-28 12:22:25', '【创建订单】用户创建订单。订单总金额：313.0', 'w');
INSERT INTO `t_orderlog` VALUES ('281', '10208', 'test1', '2014-02-28 12:26:53', '【创建订单】用户创建订单。订单总金额：313.0', 'w');
INSERT INTO `t_orderlog` VALUES ('282', '10209', '_out_1393056233281', '2014-02-28 12:29:46', '【创建订单】用户创建订单。订单总金额：499.0', 'w');
INSERT INTO `t_orderlog` VALUES ('283', '10210', '_out_1393056233281', '2014-02-28 12:43:42', '【创建订单】用户创建订单。订单总金额：103.0', 'w');
INSERT INTO `t_orderlog` VALUES ('284', '10211', 'test1', '2014-02-28 13:13:12', '【创建订单】用户创建订单。订单总金额：169.0', 'w');
INSERT INTO `t_orderlog` VALUES ('285', '10139', 'system', '2014-03-01 16:09:06', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('286', '10140', 'system', '2014-03-01 16:09:07', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('287', '10211', 'admin', '2014-03-01 16:09:41', '【修改订单总金额】总金额修改为：0.01', 'm');
INSERT INTO `t_orderlog` VALUES ('288', '10211', 'admin', '2014-03-01 16:15:18', '【修改订单总金额】总金额修改为：0.01', 'm');
INSERT INTO `t_orderlog` VALUES ('289', '10211', 'admin', '2014-03-01 16:30:35', '【修改订单总金额】总金额修改为：0.01', 'm');
INSERT INTO `t_orderlog` VALUES ('290', '10212', 'test1', '2014-03-01 16:39:58', '【创建订单】用户创建订单。订单总金额：44.0', 'w');
INSERT INTO `t_orderlog` VALUES ('291', '10213', 'test1', '2014-03-01 20:12:31', '【创建订单】用户创建订单。订单总金额：37.0', 'w');
INSERT INTO `t_orderlog` VALUES ('292', '10213', 'admin', '2014-03-01 20:12:56', '【修改订单总金额】总金额修改为：0.01', 'm');
INSERT INTO `t_orderlog` VALUES ('293', '10213', 'alipay_notify', '2014-03-01 20:13:40', '【支付宝异步通知】等待买家付款。', 'm');
INSERT INTO `t_orderlog` VALUES ('294', '10213', 'alipay_notify', '2014-03-01 20:14:12', '【支付宝异步通知】已付款，等待卖家发货。', 'm');
INSERT INTO `t_orderlog` VALUES ('295', '10213', 'alipay_notify', '2014-03-01 20:14:20', '【支付宝异步通知】已付款，等待卖家发货。', 'm');
INSERT INTO `t_orderlog` VALUES ('296', '10214', 'test1', '2014-03-01 20:28:22', '【创建订单】用户创建订单。订单总金额：37.0', 'w');
INSERT INTO `t_orderlog` VALUES ('297', '10214', 'admin', '2014-03-01 20:28:41', '【修改订单总金额】总金额修改为：0.01', 'm');
INSERT INTO `t_orderlog` VALUES ('298', '10214', 'alipay_notify', '2014-03-01 20:29:17', '【支付宝异步通知】等待买家付款。', 'm');
INSERT INTO `t_orderlog` VALUES ('299', '10214', 'alipay_notify', '2014-03-01 20:29:55', '【支付宝异步通知】已付款，等待卖家发货。', 'm');
INSERT INTO `t_orderlog` VALUES ('300', '10214', 'admin', '2014-03-01 20:31:41', '【审核通过】', 'm');
INSERT INTO `t_orderlog` VALUES ('301', '10214', 'alipay_notify', '2014-03-01 20:41:04', '【支付宝异步通知】已发货，等待买家确认收货。', 'm');
INSERT INTO `t_orderlog` VALUES ('302', '10214', 'alipay_notify', '2014-03-01 20:49:25', '【支付宝异步通知】交易完成。', 'm');
INSERT INTO `t_orderlog` VALUES ('303', '10214', 'admin', '2014-03-01 20:59:35', '【已归档】', 'm');
INSERT INTO `t_orderlog` VALUES ('304', '10215', 'test1', '2014-03-01 21:11:13', '【创建订单】用户创建订单。订单总金额：37.0', 'w');
INSERT INTO `t_orderlog` VALUES ('305', '10215', 'admin', '2014-03-01 21:11:26', '【修改订单总金额】总金额修改为：0.01', 'm');
INSERT INTO `t_orderlog` VALUES ('306', '10215', 'alipay_notify', '2014-03-01 21:12:08', '【支付宝异步通知】等待买家付款。', 'm');
INSERT INTO `t_orderlog` VALUES ('307', '10215', 'alipay_notify', '2014-03-01 21:12:48', '【支付宝异步通知】已付款，等待卖家发货。', 'm');
INSERT INTO `t_orderlog` VALUES ('308', '10215', 'admin', '2014-03-01 21:13:36', '【审核通过】', 'm');
INSERT INTO `t_orderlog` VALUES ('309', '10215', 'alipay_notify', '2014-03-01 21:13:46', '【支付宝异步通知】已发货，等待买家确认收货。', 'm');
INSERT INTO `t_orderlog` VALUES ('310', '10215', 'alipay_notify', '2014-03-01 21:16:53', '【支付宝异步通知】已发货，等待买家确认收货。', 'm');
INSERT INTO `t_orderlog` VALUES ('311', '10141', 'system', '2014-03-02 20:13:36', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('312', '10142', 'system', '2014-03-02 20:13:37', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('313', '10143', 'system', '2014-03-02 20:13:37', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('314', '10144', 'system', '2014-03-02 20:13:37', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('315', '10145', 'system', '2014-03-02 20:13:38', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('316', '10146', 'system', '2014-03-02 20:13:38', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('317', '10147', 'system', '2014-03-02 20:13:38', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('318', '10148', 'system', '2014-03-02 20:13:39', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('319', '10149', 'system', '2014-03-02 20:13:39', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('320', '10150', 'system', '2014-03-02 20:13:39', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('321', '10151', 'system', '2014-03-02 20:13:39', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('322', '10152', 'system', '2014-03-02 20:13:40', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('323', '10153', 'system', '2014-03-02 20:13:40', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('324', '10154', 'system', '2014-03-02 20:13:40', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('325', '10213', 'alipay_notify', '2014-03-02 20:14:30', '【支付宝异步通知】已付款，等待卖家发货。', 'm');
INSERT INTO `t_orderlog` VALUES ('326', '10155', 'system', '2014-03-03 11:01:02', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('327', '10156', 'system', '2014-03-03 11:01:02', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('328', '10157', 'system', '2014-03-03 11:01:02', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('329', '10158', 'system', '2014-03-03 11:01:03', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('330', '10159', 'system', '2014-03-03 11:01:03', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('331', '10160', 'system', '2014-03-03 11:01:03', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('332', '10161', 'system', '2014-03-03 11:01:04', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('333', '10162', 'system', '2014-03-03 11:01:04', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('334', '10163', 'system', '2014-03-03 11:01:04', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('335', '10164', 'system', '2014-03-03 11:01:04', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('336', '10165', 'system', '2014-03-03 11:01:04', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('337', '10166', 'system', '2014-03-03 11:01:04', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('338', '10167', 'system', '2014-03-03 11:01:05', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('339', '10168', 'system', '2014-03-03 11:01:05', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('340', '10169', 'system', '2014-03-03 11:01:05', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('341', '10170', 'system', '2014-03-03 11:01:05', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('342', '10171', 'system', '2014-03-03 11:01:05', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('343', '10172', 'system', '2014-03-03 11:01:05', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('344', '10173', 'system', '2014-03-03 11:01:05', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('345', '10174', 'system', '2014-03-03 11:01:05', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('346', '10175', 'system', '2014-03-03 11:01:05', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('347', '10176', 'system', '2014-03-03 11:01:06', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('348', '10177', 'system', '2014-03-03 11:01:06', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('349', '10178', 'system', '2014-03-03 11:01:06', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('350', '10179', 'system', '2014-03-03 11:01:06', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('351', '10180', 'system', '2014-03-03 11:01:06', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('352', '10181', 'system', '2014-03-03 11:01:06', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('353', '10182', 'system', '2014-03-03 11:01:06', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('354', '10183', 'system', '2014-03-03 11:01:06', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('355', '10184', 'system', '2014-03-03 11:01:06', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('356', '10185', 'system', '2014-03-03 11:01:06', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('357', '10186', 'system', '2014-03-03 11:01:06', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('358', '10187', 'system', '2014-03-03 11:01:06', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('359', '10188', 'system', '2014-03-03 11:01:06', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('360', '10189', 'system', '2014-03-03 11:01:06', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('361', '10190', 'system', '2014-03-03 11:01:06', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('362', '10191', 'system', '2014-03-03 11:01:06', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('363', '10192', 'system', '2014-03-03 11:01:06', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('364', '10193', 'system', '2014-03-03 11:01:07', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('365', '10194', 'system', '2014-03-03 11:01:07', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('366', '10195', 'system', '2014-03-03 11:01:07', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('367', '10196', 'system', '2014-03-03 11:01:07', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('368', '10197', 'system', '2014-03-03 11:01:07', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('369', '10198', 'system', '2014-03-03 11:01:07', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('370', '10199', 'system', '2014-03-03 11:01:07', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('371', '10200', 'system', '2014-03-03 11:01:07', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('372', '10201', 'system', '2014-03-03 11:01:07', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('373', '10216', 'test1', '2014-03-03 11:03:25', '【创建订单】用户创建订单。订单总金额：27.0', 'w');
INSERT INTO `t_orderlog` VALUES ('374', '10216', 'admin', '2014-03-03 11:03:48', '【修改订单总金额】总金额修改为：0.01', 'm');
INSERT INTO `t_orderlog` VALUES ('375', '10216', 'admin', '2014-03-03 11:13:24', '【修改订单总金额】总金额修改为：0.01', 'm');
INSERT INTO `t_orderlog` VALUES ('376', '10217', 'test1', '2014-03-03 11:21:58', '【创建订单】用户创建订单。订单总金额：59.0', 'w');
INSERT INTO `t_orderlog` VALUES ('377', '10217', 'admin', '2014-03-03 11:22:38', '【修改订单总金额】总金额修改为：0.01', 'm');
INSERT INTO `t_orderlog` VALUES ('378', '10218', 'test1', '2014-03-03 11:34:37', '【创建订单】用户创建订单。订单总金额：11137.0', 'w');
INSERT INTO `t_orderlog` VALUES ('379', '10218', 'huangf', '2014-03-03 11:35:05', '【修改订单总金额】总金额修改为：0.01', 'm');
INSERT INTO `t_orderlog` VALUES ('380', '10217', 'alipay_notify', '2014-03-03 11:42:32', '【支付宝异步通知】已付款，等待卖家发货(WAIT_SELLER_SEND_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('381', '10218', 'alipay_notify', '2014-03-03 11:43:08', '【支付宝异步通知】等待买家付款(WAIT_BUYER_PAY)。', 'p');
INSERT INTO `t_orderlog` VALUES ('382', '10218', 'alipay_notify', '2014-03-03 11:43:20', '【支付宝异步通知】已付款，等待卖家发货(WAIT_SELLER_SEND_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('383', '10219', 'test1', '2014-03-03 11:43:30', '【创建订单】用户创建订单。订单总金额：37.0', 'w');
INSERT INTO `t_orderlog` VALUES ('384', '10219', 'alipay_notify', '2014-03-03 11:43:34', '【支付宝异步通知】等待买家付款(WAIT_BUYER_PAY)。', 'p');
INSERT INTO `t_orderlog` VALUES ('385', '10219', 'huangf', '2014-03-03 11:43:44', '【修改订单总金额】总金额修改为：0.01', 'm');
INSERT INTO `t_orderlog` VALUES ('386', '10219', 'alipay_notify', '2014-03-03 11:44:01', '【支付宝异步通知】等待买家付款(WAIT_BUYER_PAY)。', 'p');
INSERT INTO `t_orderlog` VALUES ('387', '10219', 'alipay_notify', '2014-03-03 11:44:25', '【支付宝异步通知】已付款，等待卖家发货(WAIT_SELLER_SEND_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('388', '10217', 'alipay_notify', '2014-03-03 11:46:15', '【支付宝异步通知】等待买家付款(WAIT_BUYER_PAY)。', 'p');
INSERT INTO `t_orderlog` VALUES ('389', '10217', 'alipay_notify', '2014-03-03 11:46:31', '【支付宝异步通知】等待买家付款(WAIT_BUYER_PAY)。', 'p');
INSERT INTO `t_orderlog` VALUES ('390', '10219', 'huangf', '2014-03-03 11:46:39', '【审核通过】', 'm');
INSERT INTO `t_orderlog` VALUES ('391', '10217', 'alipay_notify', '2014-03-03 11:47:19', '【支付宝异步通知】已付款，等待卖家发货(WAIT_SELLER_SEND_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('392', '10217', 'alipay_notify', '2014-03-03 11:47:47', '【支付宝异步通知】等待买家付款(WAIT_BUYER_PAY)。', 'p');
INSERT INTO `t_orderlog` VALUES ('393', '10218', 'alipay_notify', '2014-03-03 11:48:34', '【支付宝异步通知】等待买家付款(WAIT_BUYER_PAY)。', 'p');
INSERT INTO `t_orderlog` VALUES ('394', '10218', 'alipay_notify', '2014-03-03 11:49:22', '【支付宝异步通知】等待买家付款(WAIT_BUYER_PAY)。', 'p');
INSERT INTO `t_orderlog` VALUES ('395', '10218', 'alipay_notify', '2014-03-03 11:49:48', '【支付宝异步通知】已付款，等待卖家发货(WAIT_SELLER_SEND_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('396', '10219', 'alipay_notify', '2014-03-03 11:51:07', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('397', '10217', 'alipay_notify', '2014-03-03 11:52:36', '【支付宝异步通知】等待买家付款(WAIT_BUYER_PAY)。', 'p');
INSERT INTO `t_orderlog` VALUES ('398', '10219', 'alipay_notify', '2014-03-03 11:54:29', '【支付宝异步通知-->退款流程】等待卖家同意退款(WAIT_SELLER_AGREE)。', 'p');
INSERT INTO `t_orderlog` VALUES ('399', '10219', 'alipay_notify', '2014-03-03 11:55:15', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('400', '10219', 'alipay_notify', '2014-03-03 11:55:23', '【支付宝异步通知-->退款流程】未知。refund_status = SELLER_REFUSE_BUYER', 'p');
INSERT INTO `t_orderlog` VALUES ('401', '10219', 'alipay_notify', '2014-03-03 11:58:04', '【支付宝异步通知-->退款流程】等待卖家同意退款(WAIT_SELLER_AGREE)。', 'p');
INSERT INTO `t_orderlog` VALUES ('402', '10219', 'alipay_notify', '2014-03-03 11:59:45', '【支付宝异步通知-->退款流程】未知。refund_status = SELLER_REFUSE_BUYER', 'p');
INSERT INTO `t_orderlog` VALUES ('403', '10219', 'alipay_notify', '2014-03-03 12:05:58', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('404', '10219', 'alipay_notify', '2014-03-03 12:08:16', '【支付宝异步通知-->退款流程】等待卖家同意退款(WAIT_SELLER_AGREE)。', 'p');
INSERT INTO `t_orderlog` VALUES ('405', '10219', 'alipay_notify', '2014-03-03 12:09:53', '【支付宝异步通知-->退款流程】未知。refund_status = SELLER_REFUSE_BUYER', 'p');
INSERT INTO `t_orderlog` VALUES ('406', '10219', 'alipay_notify', '2014-03-03 12:15:52', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('407', '10219', 'alipay_notify', '2014-03-03 12:18:52', '【支付宝异步通知-->退款流程】等待卖家同意退款(WAIT_SELLER_AGREE)。', 'p');
INSERT INTO `t_orderlog` VALUES ('408', '10219', 'alipay_notify', '2014-03-03 12:19:03', '【支付宝异步通知-->退款流程】未知。refund_status = SELLER_REFUSE_BUYER', 'p');
INSERT INTO `t_orderlog` VALUES ('409', '10216', 'alipay_notify', '2014-03-03 12:40:53', '【支付宝异步通知】等待买家付款(WAIT_BUYER_PAY)。', 'p');
INSERT INTO `t_orderlog` VALUES ('410', '10216', 'alipay_notify', '2014-03-03 12:41:36', '【支付宝异步通知】已付款，等待卖家发货(WAIT_SELLER_SEND_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('411', '10220', 'test1', '2014-03-03 12:51:06', '【创建订单】用户创建订单。订单总金额：37.0', 'w');
INSERT INTO `t_orderlog` VALUES ('412', '10220', 'huangf', '2014-03-03 12:51:32', '【修改订单总金额】总金额修改为：0.01', 'm');
INSERT INTO `t_orderlog` VALUES ('413', '10220', 'alipay_notify', '2014-03-03 12:52:03', '【支付宝异步通知】等待买家付款(WAIT_BUYER_PAY)。', 'p');
INSERT INTO `t_orderlog` VALUES ('414', '10220', 'alipay_notify', '2014-03-03 12:53:35', '【支付宝异步通知】已付款，等待卖家发货(WAIT_SELLER_SEND_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('415', '10220', 'huangf', '2014-03-03 12:59:14', '【审核通过】', 'm');
INSERT INTO `t_orderlog` VALUES ('416', '10220', 'alipay_notify', '2014-03-03 12:59:26', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('417', '10220', 'alipay_notify', '2014-03-03 13:03:34', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('418', '10220', 'alipay_notify', '2014-03-03 13:13:04', '【支付宝异步通知】交易完成(TRADE_FINISHED)。', 'p');
INSERT INTO `t_orderlog` VALUES ('419', '10220', 'alipay_notify', '2014-03-03 13:13:38', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('420', '10219', 'alipay_notify', '2014-03-03 13:15:03', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('421', '10219', 'alipay_notify', '2014-03-03 13:18:45', '【支付宝异步通知-->退款流程】等待卖家同意退款(WAIT_SELLER_AGREE)。', 'p');
INSERT INTO `t_orderlog` VALUES ('422', '10219', 'alipay_notify', '2014-03-03 13:19:03', '【支付宝异步通知-->退款流程】卖家拒绝退款申请(SELLER_REFUSE_BUYER)。', 'p');
INSERT INTO `t_orderlog` VALUES ('423', '10220', 'alipay_notify', '2014-03-03 13:23:21', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('424', '10221', 'test1', '2014-03-03 13:36:57', '【创建订单】用户创建订单。订单总金额：59.0', 'w');
INSERT INTO `t_orderlog` VALUES ('425', '10222', 'test1', '2014-03-03 13:37:01', '【创建订单】用户创建订单。订单总金额：59.0', 'w');
INSERT INTO `t_orderlog` VALUES ('426', '10223', 'test1', '2014-03-03 13:37:52', '【创建订单】用户创建订单。订单总金额：59.0', 'w');
INSERT INTO `t_orderlog` VALUES ('427', '10223', 'huangf', '2014-03-03 13:38:07', '【修改订单总金额】总金额修改为：0.01', 'm');
INSERT INTO `t_orderlog` VALUES ('428', '10223', 'alipay_notify', '2014-03-03 13:42:42', '【支付宝异步通知】等待买家付款(WAIT_BUYER_PAY)。', 'p');
INSERT INTO `t_orderlog` VALUES ('429', '10223', 'alipay_notify', '2014-03-03 13:42:50', '【支付宝异步通知】等待买家付款(WAIT_BUYER_PAY)。', 'p');
INSERT INTO `t_orderlog` VALUES ('430', '10223', 'alipay_notify', '2014-03-03 13:43:46', '【支付宝异步通知】已付款，等待卖家发货(WAIT_SELLER_SEND_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('431', '10223', 'alipay_notify', '2014-03-03 13:44:10', '【支付宝异步通知】已付款，等待卖家发货(WAIT_SELLER_SEND_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('432', '10223', 'huangf', '2014-03-03 13:47:25', '【审核通过】', 'm');
INSERT INTO `t_orderlog` VALUES ('433', '10224', 'test1', '2014-03-03 13:52:36', '【创建订单】用户创建订单。订单总金额：59.0', 'w');
INSERT INTO `t_orderlog` VALUES ('434', '10224', 'alipay_notify', '2014-03-03 13:52:39', '【支付宝异步通知】等待买家付款(WAIT_BUYER_PAY)。', 'p');
INSERT INTO `t_orderlog` VALUES ('435', '10224', 'huangf', '2014-03-03 13:52:56', '【修改订单总金额】总金额修改为：0.01', 'm');
INSERT INTO `t_orderlog` VALUES ('436', '10224', 'alipay_notify', '2014-03-03 13:53:16', '【支付宝异步通知】等待买家付款(WAIT_BUYER_PAY)。', 'p');
INSERT INTO `t_orderlog` VALUES ('437', '10224', 'alipay_notify', '2014-03-03 13:55:07', '【支付宝异步通知】等待买家付款(WAIT_BUYER_PAY)。', 'p');
INSERT INTO `t_orderlog` VALUES ('438', '10224', 'alipay_notify', '2014-03-03 13:55:31', '【支付宝异步通知】等待买家付款(WAIT_BUYER_PAY)。', 'p');
INSERT INTO `t_orderlog` VALUES ('439', '10224', 'alipay_notify', '2014-03-03 13:56:39', '【支付宝异步通知】等待买家付款(WAIT_BUYER_PAY)。', 'p');
INSERT INTO `t_orderlog` VALUES ('440', '10224', 'alipay_notify', '2014-03-03 13:57:48', '【支付宝异步通知】已付款，等待卖家发货(WAIT_SELLER_SEND_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('441', '10224', 'huangf', '2014-03-03 13:58:15', '【审核通过】', 'm');
INSERT INTO `t_orderlog` VALUES ('442', '10224', 'alipay_notify', '2014-03-03 13:58:24', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('443', '10224', 'alipay_notify', '2014-03-03 14:00:21', '【支付宝异步通知】交易完成(TRADE_FINISHED)。', 'p');
INSERT INTO `t_orderlog` VALUES ('444', '10224', 'alipay_notify', '2014-03-03 14:02:52', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('445', '10223', 'alipay_notify', '2014-03-03 14:06:12', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('446', '10224', 'huangf', '2014-03-03 14:08:11', '【已归档】', 'm');
INSERT INTO `t_orderlog` VALUES ('447', '10223', 'alipay_notify', '2014-03-03 14:10:10', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('448', '10224', 'alipay_notify', '2014-03-03 14:12:58', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('449', '10223', 'alipay_notify', '2014-03-03 14:20:21', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('450', '10224', 'alipay_notify', '2014-03-03 14:22:16', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('451', '10220', 'alipay_notify', '2014-03-03 14:23:51', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('452', '10223', 'alipay_notify', '2014-03-03 14:30:42', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('453', '10219', 'alipay_notify', '2014-03-03 15:15:18', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('454', '10219', 'alipay_notify', '2014-03-03 15:18:58', '【支付宝异步通知-->退款流程】等待卖家同意退款(WAIT_SELLER_AGREE)。', 'p');
INSERT INTO `t_orderlog` VALUES ('455', '10219', 'alipay_notify', '2014-03-03 15:19:46', '【支付宝异步通知-->退款流程】卖家拒绝退款申请(SELLER_REFUSE_BUYER)。', 'p');
INSERT INTO `t_orderlog` VALUES ('456', '10224', 'alipay_notify', '2014-03-03 15:22:10', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('457', '10223', 'alipay_notify', '2014-03-03 15:30:55', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('458', '10225', 'test1', '2014-03-03 15:56:36', '【创建订单】用户创建订单。订单总金额：37.0', 'w');
INSERT INTO `t_orderlog` VALUES ('459', '10225', 'huangf', '2014-03-03 15:57:00', '【修改订单总金额】总金额修改为：0.01', 'm');
INSERT INTO `t_orderlog` VALUES ('460', '10225', 'alipay_notify', '2014-03-03 15:57:23', '【支付宝异步通知】等待买家付款(WAIT_BUYER_PAY)。', 'p');
INSERT INTO `t_orderlog` VALUES ('461', '10225', 'alipay_notify', '2014-03-03 15:58:33', '【支付宝异步通知】已付款，等待卖家发货(WAIT_SELLER_SEND_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('462', '10220', 'alipay_notify', '2014-03-03 16:23:03', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('463', '10225', 'huangf', '2014-03-03 17:03:53', '【审核通过】', 'm');
INSERT INTO `t_orderlog` VALUES ('464', '10225', 'alipay_notify', '2014-03-03 17:04:03', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('465', '10225', 'alipay_notify', '2014-03-03 17:08:20', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('466', '10225', 'alipay_notify', '2014-03-03 17:18:16', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('467', '10224', 'alipay_notify', '2014-03-03 17:22:16', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('468', '10225', 'alipay_notify', '2014-03-03 17:28:39', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('469', '10223', 'alipay_notify', '2014-03-03 17:30:25', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('470', '10225', 'alipay_notify', '2014-03-03 18:28:24', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('471', '10225', 'alipay_notify', '2014-03-03 20:28:07', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('472', '10219', 'alipay_notify', '2014-03-03 21:15:17', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('473', '10219', 'alipay_notify', '2014-03-03 21:18:10', '【支付宝异步通知-->退款流程】等待卖家同意退款(WAIT_SELLER_AGREE)。', 'p');
INSERT INTO `t_orderlog` VALUES ('474', '10219', 'alipay_notify', '2014-03-03 21:19:15', '【支付宝异步通知-->退款流程】卖家拒绝退款申请(SELLER_REFUSE_BUYER)。', 'p');
INSERT INTO `t_orderlog` VALUES ('475', '10220', 'alipay_notify', '2014-03-03 22:23:34', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('476', '10224', 'alipay_notify', '2014-03-03 23:22:21', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('477', '10223', 'alipay_notify', '2014-03-03 23:30:29', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('478', '10225', 'alipay_notify', '2014-03-04 02:28:07', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('479', '10202', 'system', '2014-03-04 09:57:40', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('480', '10203', 'system', '2014-03-04 09:57:40', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('481', '10204', 'system', '2014-03-04 09:57:40', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('482', '10219', 'alipay_notify', '2014-03-04 12:15:07', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('483', '10219', 'alipay_notify', '2014-03-04 12:18:46', '【支付宝异步通知-->退款流程】等待卖家同意退款(WAIT_SELLER_AGREE)。', 'p');
INSERT INTO `t_orderlog` VALUES ('484', '10219', 'alipay_notify', '2014-03-04 12:19:10', '【支付宝异步通知-->退款流程】卖家拒绝退款申请(SELLER_REFUSE_BUYER)。', 'p');
INSERT INTO `t_orderlog` VALUES ('485', '10220', 'alipay_notify', '2014-03-04 13:23:04', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('486', '10224', 'alipay_notify', '2014-03-04 14:22:04', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('487', '10223', 'alipay_notify', '2014-03-04 14:30:02', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('488', '10226', 'test1', '2014-03-04 17:26:36', '【创建订单】用户创建订单。订单总金额：71.0', 'w');
INSERT INTO `t_orderlog` VALUES ('489', '10225', 'alipay_notify', '2014-03-04 17:28:56', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('490', '10227', 'test1', '2014-03-04 17:38:39', '【创建订单】用户创建订单。订单总金额：87.0', 'w');
INSERT INTO `t_orderlog` VALUES ('491', '10228', 'test1', '2014-03-04 17:50:57', '【创建订单】用户创建订单。订单总金额：1085.0', 'w');
INSERT INTO `t_orderlog` VALUES ('492', '10205', 'system', '2014-03-05 16:47:59', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('493', '10206', 'system', '2014-03-05 16:47:59', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('494', '10207', 'system', '2014-03-08 12:25:57', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('495', '10208', 'system', '2014-03-08 12:25:57', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('496', '10209', 'system', '2014-03-08 12:25:57', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('497', '10210', 'system', '2014-03-08 12:25:57', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('498', '10215', 'alipay_notify', '2014-03-08 21:17:33', '【支付宝异步通知-->退款流程】卖家收到退货，退款成功，交易关闭(REFUND_SUCCESS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('499', '10212', 'system', '2014-03-09 10:54:50', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('500', '10217', 'alipay_notify', '2014-03-10 11:23:03', '【支付宝异步通知】未知。trade_status = TRADE_CLOSED', 'p');
INSERT INTO `t_orderlog` VALUES ('501', '10217', 'alipay_notify', '2014-03-10 11:23:03', '【支付宝异步通知】未知。trade_status = TRADE_CLOSED', 'p');
INSERT INTO `t_orderlog` VALUES ('502', '10218', 'alipay_notify', '2014-03-10 11:35:00', '【支付宝异步通知】未知。trade_status = TRADE_CLOSED', 'p');
INSERT INTO `t_orderlog` VALUES ('503', '10219', 'alipay_notify', '2014-03-10 11:44:00', '【支付宝异步通知】未知。trade_status = TRADE_CLOSED', 'p');
INSERT INTO `t_orderlog` VALUES ('504', '10224', 'alipay_notify', '2014-03-10 13:53:00', '【支付宝异步通知】未知。trade_status = TRADE_CLOSED', 'p');
INSERT INTO `t_orderlog` VALUES ('505', '10224', 'alipay_notify', '2014-03-10 13:54:00', '【支付宝异步通知】未知。trade_status = TRADE_CLOSED', 'p');
INSERT INTO `t_orderlog` VALUES ('506', '10224', 'alipay_notify', '2014-03-10 13:56:00', '【支付宝异步通知】未知。trade_status = TRADE_CLOSED', 'p');
INSERT INTO `t_orderlog` VALUES ('507', '10224', 'alipay_notify', '2014-03-10 13:56:00', '【支付宝异步通知】未知。trade_status = TRADE_CLOSED', 'p');
INSERT INTO `t_orderlog` VALUES ('508', '10221', 'system', '2014-03-11 17:00:06', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('509', '10222', 'system', '2014-03-11 17:00:06', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('510', '10226', 'system', '2014-03-12 11:38:39', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('511', '10227', 'system', '2014-03-12 11:38:39', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('512', '10228', 'system', '2014-03-12 11:38:39', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('513', '10223', 'alipay_notify', '2014-03-13 14:07:04', '【支付宝异步通知】交易完成(TRADE_FINISHED)。', 'p');
INSERT INTO `t_orderlog` VALUES ('514', '10225', 'alipay_notify', '2014-03-13 17:05:00', '【支付宝异步通知】交易完成(TRADE_FINISHED)。', 'p');
INSERT INTO `t_orderlog` VALUES ('515', '10232', '_out_1395013499587', '2014-03-17 15:07:25', '【创建订单】用户创建订单。订单总金额：10903.0', 'w');
INSERT INTO `t_orderlog` VALUES ('516', '10233', '_out_1395013499587', '2014-03-18 14:58:43', '【创建订单】用户创建订单。订单总金额：10903.0', 'w');
INSERT INTO `t_orderlog` VALUES ('517', '10233', 'huangf', '2014-03-18 16:42:16', '【修改订单总金额】总金额修改为：0.01', 'm');
INSERT INTO `t_orderlog` VALUES ('518', '10233', 'alipay_notify', '2014-03-18 16:52:51', '【支付宝异步通知】等待买家付款(WAIT_BUYER_PAY)。', 'p');
INSERT INTO `t_orderlog` VALUES ('519', '10233', 'huangf', '2014-03-21 12:00:18', '【增加支付记录】增1.0￥;', 'm');
INSERT INTO `t_orderlog` VALUES ('520', '10233', 'huangf', '2014-03-21 12:00:38', '【审核通过】', 'm');
INSERT INTO `t_orderlog` VALUES ('521', '10234', '_out_1395013499587', '2014-03-21 12:04:34', '【创建订单】用户创建订单。订单总金额：10903.0', 'w');
INSERT INTO `t_orderlog` VALUES ('522', '10234', 'huangf', '2014-03-21 12:05:12', '【修改订单总金额】总金额修改为：0.01', 'm');
INSERT INTO `t_orderlog` VALUES ('523', '10234', 'alipay_notify', '2014-03-21 12:05:36', '【支付宝异步通知】等待买家付款(WAIT_BUYER_PAY)。', 'p');
INSERT INTO `t_orderlog` VALUES ('524', '10235', '_out_1395013499587', '2014-03-21 12:21:16', '【创建订单】用户创建订单。订单总金额：59.0', 'w');
INSERT INTO `t_orderlog` VALUES ('525', '10235', 'huangf', '2014-03-21 12:21:43', '【增加支付记录】增0.1￥;', 'm');
INSERT INTO `t_orderlog` VALUES ('526', '10236', '_out_1395013499587', '2014-03-21 12:22:17', '【创建订单】用户创建订单。订单总金额：3057.0', 'w');
INSERT INTO `t_orderlog` VALUES ('527', '10236', 'huangf', '2014-03-21 12:22:27', '【修改订单总金额】总金额修改为：0.01', 'm');
INSERT INTO `t_orderlog` VALUES ('528', '10236', 'alipay_notify', '2014-03-21 12:22:54', '【支付宝异步通知】等待买家付款(WAIT_BUYER_PAY)。', 'p');
INSERT INTO `t_orderlog` VALUES ('529', '10237', '_out_1393560390544', '2014-03-21 12:30:03', '【创建订单】用户创建订单。订单总金额：10903.0', 'w');
INSERT INTO `t_orderlog` VALUES ('530', '10237', 'huangf', '2014-03-21 12:30:19', '【修改订单总金额】总金额修改为：0.01', 'm');
INSERT INTO `t_orderlog` VALUES ('531', '10237', 'alipay_notify', '2014-03-21 12:30:41', '【支付宝异步通知】等待买家付款(WAIT_BUYER_PAY)。', 'p');
INSERT INTO `t_orderlog` VALUES ('532', '10238', '_out_1395013499587', '2014-03-21 12:57:18', '【创建订单】用户创建订单。订单总金额：10903.0', 'w');
INSERT INTO `t_orderlog` VALUES ('533', '10238', 'huangf', '2014-03-21 12:57:29', '【修改订单总金额】总金额修改为：0.01', 'm');
INSERT INTO `t_orderlog` VALUES ('534', '10238', 'alipay_notify', '2014-03-21 12:57:52', '【支付宝异步通知】等待买家付款(WAIT_BUYER_PAY)。', 'p');
INSERT INTO `t_orderlog` VALUES ('535', '10238', 'alipay_notify', '2014-03-21 12:58:19', '【支付宝异步通知】已付款，等待卖家发货(WAIT_SELLER_SEND_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('536', '10238', 'huangf', '2014-03-21 13:00:10', '【审核通过】', 'm');
INSERT INTO `t_orderlog` VALUES ('537', '10238', 'alipay_notify', '2014-03-21 13:05:21', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('538', '10238', 'alipay_notify', '2014-03-21 13:09:11', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('539', '10238', 'alipay_notify', '2014-03-21 13:11:06', '【支付宝异步通知】交易完成(TRADE_FINISHED)。', 'p');
INSERT INTO `t_orderlog` VALUES ('540', '10238', 'alipay_notify', '2014-03-21 13:19:36', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('541', '10238', 'alipay_notify', '2014-03-21 13:29:40', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('542', '10239', '_out_1395013499587', '2014-03-21 14:35:39', '【创建订单】用户创建订单。订单总金额：10903.0', 'w');
INSERT INTO `t_orderlog` VALUES ('543', '10239', 'huangf', '2014-03-21 14:36:51', '【修改订单总金额】总金额修改为：0.01', 'm');
INSERT INTO `t_orderlog` VALUES ('544', '10239', 'alipay_notify', '2014-03-21 14:37:19', '【支付宝异步通知】等待买家付款(WAIT_BUYER_PAY)。', 'p');
INSERT INTO `t_orderlog` VALUES ('545', '10239', 'alipay_notify', '2014-03-21 14:37:42', '【支付宝异步通知】已付款，等待卖家发货(WAIT_SELLER_SEND_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('546', '10239', 'huangf', '2014-03-21 14:38:25', '【审核通过】', 'm');
INSERT INTO `t_orderlog` VALUES ('547', '10239', 'alipay_notify', '2014-03-21 14:43:48', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('548', '10240', '_out_1395013499587', '2014-03-21 15:52:12', '【创建订单】用户创建订单。订单总金额：10903.0', 'w');
INSERT INTO `t_orderlog` VALUES ('549', '10240', 'huangf', '2014-03-21 15:52:35', '【修改订单总金额】总金额修改为：0.01', 'm');
INSERT INTO `t_orderlog` VALUES ('550', '10240', 'alipay_notify', '2014-03-21 15:52:58', '【支付宝异步通知】等待买家付款(WAIT_BUYER_PAY)。', 'p');
INSERT INTO `t_orderlog` VALUES ('551', '10240', 'alipay_notify', '2014-03-21 15:53:23', '【支付宝异步通知】已付款，等待卖家发货(WAIT_SELLER_SEND_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('552', '10240', 'huangf', '2014-03-21 16:02:26', '【审核通过】', 'm');
INSERT INTO `t_orderlog` VALUES ('553', '10240', 'alipay_notify', '2014-03-21 16:03:27', '【支付宝异步通知-->退款流程】等待卖家同意退款(WAIT_SELLER_AGREE)。', 'p');
INSERT INTO `t_orderlog` VALUES ('554', '10240', 'alipay_notify', '2014-03-21 16:07:05', '【支付宝异步通知-->退款流程】等待卖家同意退款(WAIT_SELLER_AGREE)。', 'p');
INSERT INTO `t_orderlog` VALUES ('555', '10239', 'alipay_notify', '2014-03-21 16:08:27', '【支付宝异步通知-->退款流程】等待卖家同意退款(WAIT_SELLER_AGREE)。', 'p');
INSERT INTO `t_orderlog` VALUES ('556', '10241', '_out_1395013499587', '2014-03-21 16:15:04', '【创建订单】用户创建订单。订单总金额：3003.0', 'w');
INSERT INTO `t_orderlog` VALUES ('557', '10241', 'huangf', '2014-03-21 16:15:41', '【修改订单总金额】总金额修改为：0.01', 'm');
INSERT INTO `t_orderlog` VALUES ('558', '10240', 'alipay_notify', '2014-03-21 16:16:10', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('559', '10240', 'alipay_notify', '2014-03-21 16:17:34', '【支付宝异步通知-->退款流程】等待卖家同意退款(WAIT_SELLER_AGREE)。', 'p');
INSERT INTO `t_orderlog` VALUES ('560', '10241', 'alipay_notify', '2014-03-21 16:20:01', '【支付宝异步通知】等待买家付款(WAIT_BUYER_PAY)。', 'p');
INSERT INTO `t_orderlog` VALUES ('561', '10241', 'alipay_notify', '2014-03-21 16:21:44', '【支付宝异步通知】已付款，等待卖家发货(WAIT_SELLER_SEND_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('562', '10241', 'huangf', '2014-03-21 16:25:16', '【审核通过】', 'm');
INSERT INTO `t_orderlog` VALUES ('563', '10241', 'alipay_notify', '2014-03-21 16:25:29', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('564', '10240', 'alipay_notify', '2014-03-21 16:26:44', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('565', '10240', 'alipay_notify', '2014-03-21 16:28:04', '【支付宝异步通知-->退款流程】等待卖家同意退款(WAIT_SELLER_AGREE)。', 'p');
INSERT INTO `t_orderlog` VALUES ('566', '10240', 'alipay_notify', '2014-03-21 16:28:24', '【支付宝异步通知-->退款流程】等待卖家同意退款(WAIT_SELLER_AGREE)。', 'p');
INSERT INTO `t_orderlog` VALUES ('567', '10238', 'alipay_notify', '2014-03-21 16:29:21', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('568', '10241', 'alipay_notify', '2014-03-21 16:29:52', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('569', '10241', 'alipay_notify', '2014-03-21 16:39:01', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('570', '10241', 'alipay_notify', '2014-03-21 16:48:46', '【支付宝异步通知-->退款流程】等待卖家同意退款(WAIT_SELLER_AGREE)。', 'p');
INSERT INTO `t_orderlog` VALUES ('571', '10241', 'alipay_notify', '2014-03-21 16:49:57', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('572', '10241', 'alipay_notify', '2014-03-21 16:52:05', '【支付宝异步通知-->退款流程】等待卖家同意退款(WAIT_SELLER_AGREE)。', 'p');
INSERT INTO `t_orderlog` VALUES ('573', '10241', 'alipay_notify', '2014-03-21 17:02:19', '【支付宝异步通知-->退款流程】等待卖家同意退款(WAIT_SELLER_AGREE)。', 'p');
INSERT INTO `t_orderlog` VALUES ('574', '10241', 'alipay_notify', '2014-03-21 17:12:29', '【支付宝异步通知-->退款流程】等待卖家同意退款(WAIT_SELLER_AGREE)。', 'p');
INSERT INTO `t_orderlog` VALUES ('575', '10240', 'alipay_notify', '2014-03-21 17:26:04', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('576', '10242', '_out_1395013499587', '2014-03-21 17:32:37', '【创建订单】用户创建订单。订单总金额：6011.0', 'w');
INSERT INTO `t_orderlog` VALUES ('577', '10242', 'huangf', '2014-03-21 17:33:10', '【修改订单总金额】总金额修改为：0.01', 'm');
INSERT INTO `t_orderlog` VALUES ('578', '10242', 'alipay_notify', '2014-03-21 17:33:31', '【支付宝异步通知】等待买家付款(WAIT_BUYER_PAY)。', 'p');
INSERT INTO `t_orderlog` VALUES ('579', '10242', 'alipay_notify', '2014-03-21 17:33:57', '【支付宝异步通知】已付款，等待卖家发货(WAIT_SELLER_SEND_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('580', '10242', 'huangf', '2014-03-21 17:34:44', '【审核通过】', 'm');
INSERT INTO `t_orderlog` VALUES ('581', '10242', 'alipay_notify', '2014-03-21 17:34:49', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('582', '10242', 'alipay_notify', '2014-03-21 17:36:38', '【支付宝异步通知-->退款流程】等待卖家同意退款(WAIT_SELLER_AGREE)。', 'p');
INSERT INTO `t_orderlog` VALUES ('583', '10242', 'alipay_notify', '2014-03-21 17:38:34', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('584', '10242', 'alipay_notify', '2014-03-21 17:40:17', '【支付宝异步通知-->退款流程】卖家不同意协议，等待买家修改(SELLER_REFUSE_BUYER)。', 'p');
INSERT INTO `t_orderlog` VALUES ('585', '10242', 'alipay_notify', '2014-03-21 17:40:41', '【支付宝异步通知-->退款流程】等待卖家同意退款(WAIT_SELLER_AGREE)。', 'p');
INSERT INTO `t_orderlog` VALUES ('586', '10242', 'alipay_notify', '2014-03-21 17:43:10', '【支付宝异步通知-->退款流程】等待卖家同意退款(WAIT_SELLER_AGREE)。', 'p');
INSERT INTO `t_orderlog` VALUES ('587', '10242', 'alipay_notify', '2014-03-21 17:44:18', '【支付宝异步通知-->退款流程】卖家不同意协议，等待买家修改(SELLER_REFUSE_BUYER)。', 'p');
INSERT INTO `t_orderlog` VALUES ('588', '10242', 'alipay_notify', '2014-03-21 17:44:49', '【支付宝异步通知-->退款流程】卖家不同意协议，等待买家修改(SELLER_REFUSE_BUYER)。', 'p');
INSERT INTO `t_orderlog` VALUES ('589', '10242', 'alipay_notify', '2014-03-21 17:46:36', '【支付宝异步通知-->退款流程】等待卖家同意退款(WAIT_SELLER_AGREE)。', 'p');
INSERT INTO `t_orderlog` VALUES ('590', '10242', 'alipay_notify', '2014-03-21 17:47:40', '【支付宝异步通知-->退款流程】等待卖家同意退款(WAIT_SELLER_AGREE)。', 'p');
INSERT INTO `t_orderlog` VALUES ('591', '10242', 'alipay_notify', '2014-03-21 17:48:02', '【支付宝异步通知-->退款流程】卖家不同意协议，等待买家修改(SELLER_REFUSE_BUYER)。', 'p');
INSERT INTO `t_orderlog` VALUES ('592', '10242', 'alipay_notify', '2014-03-21 17:48:52', '【支付宝异步通知-->退款流程】退款协议达成，等待买家退货(WAIT_BUYER_RETURN_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('593', '10242', 'alipay_notify', '2014-03-21 17:48:53', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('594', '10241', 'alipay_notify', '2014-03-21 17:49:48', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('595', '10242', 'alipay_notify', '2014-03-21 17:50:14', '【支付宝异步通知-->退款流程】等待卖家同意退款(WAIT_SELLER_AGREE)。', 'p');
INSERT INTO `t_orderlog` VALUES ('596', '10242', 'alipay_notify', '2014-03-21 17:50:25', '【支付宝异步通知-->退款流程】等待卖家同意退款(WAIT_SELLER_AGREE)。', 'p');
INSERT INTO `t_orderlog` VALUES ('597', '10242', 'alipay_notify', '2014-03-21 17:52:56', '【支付宝异步通知-->退款流程】退款协议达成，等待买家退货(WAIT_BUYER_RETURN_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('598', '10242', 'alipay_notify', '2014-03-21 17:54:14', '【支付宝异步通知-->退款流程】卖家不同意协议，等待买家修改(SELLER_REFUSE_BUYER)。', 'p');
INSERT INTO `t_orderlog` VALUES ('599', '10242', 'alipay_notify', '2014-03-21 17:57:37', '【支付宝异步通知-->退款流程】等待卖家同意退款(WAIT_SELLER_AGREE)。', 'p');
INSERT INTO `t_orderlog` VALUES ('600', '10242', 'alipay_notify', '2014-03-21 17:58:12', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('601', '10242', 'alipay_notify', '2014-03-21 17:58:17', '【支付宝异步通知-->退款流程】卖家不同意协议，等待买家修改(SELLER_REFUSE_BUYER)。', 'p');
INSERT INTO `t_orderlog` VALUES ('602', '10242', 'alipay_notify', '2014-03-21 18:00:38', '【支付宝异步通知-->退款流程】等待卖家同意退款(WAIT_SELLER_AGREE)。', 'p');
INSERT INTO `t_orderlog` VALUES ('603', '10242', 'alipay_notify', '2014-03-21 18:00:42', '【支付宝异步通知-->退款流程】等待卖家同意退款(WAIT_SELLER_AGREE)。', 'p');
INSERT INTO `t_orderlog` VALUES ('604', '10242', 'alipay_notify', '2014-03-21 18:02:38', '【支付宝异步通知-->退款流程】退款协议达成，等待买家退货(WAIT_BUYER_RETURN_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('605', '10242', 'alipay_notify', '2014-03-21 18:04:39', '【支付宝异步通知-->退款流程】卖家不同意协议，等待买家修改(SELLER_REFUSE_BUYER)。', 'p');
INSERT INTO `t_orderlog` VALUES ('606', '10239', 'alipay_notify', '2014-03-21 18:07:34', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('607', '10242', 'alipay_notify', '2014-03-21 18:07:42', '【支付宝异步通知-->退款流程】等待卖家同意退款(WAIT_SELLER_AGREE)。', 'p');
INSERT INTO `t_orderlog` VALUES ('608', '10242', 'alipay_notify', '2014-03-21 18:08:01', '【支付宝异步通知-->退款流程】卖家不同意协议，等待买家修改(SELLER_REFUSE_BUYER)。', 'p');
INSERT INTO `t_orderlog` VALUES ('609', '10239', 'alipay_notify', '2014-03-21 18:08:44', '【支付宝异步通知-->退款流程】等待卖家同意退款(WAIT_SELLER_AGREE)。', 'p');
INSERT INTO `t_orderlog` VALUES ('610', '10242', 'alipay_notify', '2014-03-21 18:10:39', '【支付宝异步通知-->退款流程】等待卖家同意退款(WAIT_SELLER_AGREE)。', 'p');
INSERT INTO `t_orderlog` VALUES ('611', '10242', 'alipay_notify', '2014-03-21 18:12:41', '【支付宝异步通知-->退款流程】退款协议达成，等待买家退货(WAIT_BUYER_RETURN_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('612', '10241', 'alipay_notify', '2014-03-21 18:12:59', '【支付宝异步通知-->退款流程】等待卖家同意退款(WAIT_SELLER_AGREE)。', 'p');
INSERT INTO `t_orderlog` VALUES ('613', '10242', 'alipay_notify', '2014-03-21 18:58:35', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('614', '10242', 'alipay_notify', '2014-03-21 19:00:53', '【支付宝异步通知-->退款流程】等待卖家同意退款(WAIT_SELLER_AGREE)。', 'p');
INSERT INTO `t_orderlog` VALUES ('615', '10242', 'alipay_notify', '2014-03-21 19:04:08', '【支付宝异步通知-->退款流程】卖家不同意协议，等待买家修改(SELLER_REFUSE_BUYER)。', 'p');
INSERT INTO `t_orderlog` VALUES ('616', '10242', 'alipay_notify', '2014-03-21 19:07:30', '【支付宝异步通知-->退款流程】等待卖家同意退款(WAIT_SELLER_AGREE)。', 'p');
INSERT INTO `t_orderlog` VALUES ('617', '10242', 'alipay_notify', '2014-03-21 19:08:52', '【支付宝异步通知-->退款流程】卖家不同意协议，等待买家修改(SELLER_REFUSE_BUYER)。', 'p');
INSERT INTO `t_orderlog` VALUES ('618', '10242', 'alipay_notify', '2014-03-21 19:10:05', '【支付宝异步通知-->退款流程】等待卖家同意退款(WAIT_SELLER_AGREE)。', 'p');
INSERT INTO `t_orderlog` VALUES ('619', '10242', 'alipay_notify', '2014-03-21 19:12:48', '【支付宝异步通知-->退款流程】退款协议达成，等待买家退货(WAIT_BUYER_RETURN_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('620', '10240', 'alipay_notify', '2014-03-21 19:26:45', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('621', '10240', 'alipay_notify', '2014-03-21 19:27:11', '【支付宝异步通知-->退款流程】等待卖家同意退款(WAIT_SELLER_AGREE)。', 'p');
INSERT INTO `t_orderlog` VALUES ('622', '10241', 'alipay_notify', '2014-03-21 19:49:20', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('623', '10241', 'alipay_notify', '2014-03-21 20:12:08', '【支付宝异步通知-->退款流程】等待卖家同意退款(WAIT_SELLER_AGREE)。', 'p');
INSERT INTO `t_orderlog` VALUES ('624', '10242', 'alipay_notify', '2014-03-21 20:59:00', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('625', '10242', 'alipay_notify', '2014-03-21 20:59:04', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('626', '10242', 'alipay_notify', '2014-03-21 21:00:12', '【支付宝异步通知-->退款流程】等待卖家同意退款(WAIT_SELLER_AGREE)。', 'p');
INSERT INTO `t_orderlog` VALUES ('627', '10242', 'alipay_notify', '2014-03-21 21:04:02', '【支付宝异步通知-->退款流程】卖家不同意协议，等待买家修改(SELLER_REFUSE_BUYER)。', 'p');
INSERT INTO `t_orderlog` VALUES ('628', '10242', 'alipay_notify', '2014-03-21 21:07:45', '【支付宝异步通知-->退款流程】等待卖家同意退款(WAIT_SELLER_AGREE)。', 'p');
INSERT INTO `t_orderlog` VALUES ('629', '10242', 'alipay_notify', '2014-03-21 21:08:17', '【支付宝异步通知-->退款流程】卖家不同意协议，等待买家修改(SELLER_REFUSE_BUYER)。', 'p');
INSERT INTO `t_orderlog` VALUES ('630', '10242', 'alipay_notify', '2014-03-21 21:10:44', '【支付宝异步通知-->退款流程】等待卖家同意退款(WAIT_SELLER_AGREE)。', 'p');
INSERT INTO `t_orderlog` VALUES ('631', '10242', 'alipay_notify', '2014-03-21 21:12:33', '【支付宝异步通知-->退款流程】退款协议达成，等待买家退货(WAIT_BUYER_RETURN_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('632', '10238', 'alipay_notify', '2014-03-21 22:29:03', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('633', '10239', 'alipay_notify', '2014-03-22 00:07:06', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('634', '10239', 'alipay_notify', '2014-03-22 00:08:32', '【支付宝异步通知-->退款流程】等待卖家同意退款(WAIT_SELLER_AGREE)。', 'p');
INSERT INTO `t_orderlog` VALUES ('635', '10240', 'alipay_notify', '2014-03-22 01:26:56', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('636', '10240', 'alipay_notify', '2014-03-22 01:27:28', '【支付宝异步通知-->退款流程】等待卖家同意退款(WAIT_SELLER_AGREE)。', 'p');
INSERT INTO `t_orderlog` VALUES ('637', '10241', 'alipay_notify', '2014-03-22 01:49:19', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('638', '10241', 'alipay_notify', '2014-03-22 02:12:24', '【支付宝异步通知-->退款流程】等待卖家同意退款(WAIT_SELLER_AGREE)。', 'p');
INSERT INTO `t_orderlog` VALUES ('639', '10242', 'alipay_notify', '2014-03-22 03:00:26', '【支付宝异步通知-->退款流程】等待卖家同意退款(WAIT_SELLER_AGREE)。', 'p');
INSERT INTO `t_orderlog` VALUES ('640', '10242', 'alipay_notify', '2014-03-22 03:04:02', '【支付宝异步通知-->退款流程】卖家不同意协议，等待买家修改(SELLER_REFUSE_BUYER)。', 'p');
INSERT INTO `t_orderlog` VALUES ('641', '10242', 'alipay_notify', '2014-03-22 03:07:12', '【支付宝异步通知-->退款流程】等待卖家同意退款(WAIT_SELLER_AGREE)。', 'p');
INSERT INTO `t_orderlog` VALUES ('642', '10242', 'alipay_notify', '2014-03-22 03:08:14', '【支付宝异步通知-->退款流程】卖家不同意协议，等待买家修改(SELLER_REFUSE_BUYER)。', 'p');
INSERT INTO `t_orderlog` VALUES ('643', '10242', 'alipay_notify', '2014-03-22 03:10:23', '【支付宝异步通知-->退款流程】等待卖家同意退款(WAIT_SELLER_AGREE)。', 'p');
INSERT INTO `t_orderlog` VALUES ('644', '10242', 'alipay_notify', '2014-03-22 03:12:19', '【支付宝异步通知-->退款流程】退款协议达成，等待买家退货(WAIT_BUYER_RETURN_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('645', '10235', 'huangf', '2014-03-22 09:22:08', '【审核通过】', 'm');
INSERT INTO `t_orderlog` VALUES ('646', '10241', 'alipay_notify', '2014-03-22 17:12:20', '【支付宝异步通知-->退款流程】等待卖家同意退款(WAIT_SELLER_AGREE)。', 'p');
INSERT INTO `t_orderlog` VALUES ('647', '10242', 'alipay_notify', '2014-03-22 17:58:56', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('648', '10242', 'alipay_notify', '2014-03-22 18:00:37', '【支付宝异步通知-->退款流程】等待卖家同意退款(WAIT_SELLER_AGREE)。', 'p');
INSERT INTO `t_orderlog` VALUES ('649', '10242', 'alipay_notify', '2014-03-22 18:04:23', '【支付宝异步通知-->退款流程】卖家不同意协议，等待买家修改(SELLER_REFUSE_BUYER)。', 'p');
INSERT INTO `t_orderlog` VALUES ('650', '10242', 'alipay_notify', '2014-03-22 18:07:28', '【支付宝异步通知-->退款流程】等待卖家同意退款(WAIT_SELLER_AGREE)。', 'p');
INSERT INTO `t_orderlog` VALUES ('651', '10242', 'alipay_notify', '2014-03-22 18:08:34', '【支付宝异步通知-->退款流程】卖家不同意协议，等待买家修改(SELLER_REFUSE_BUYER)。', 'p');
INSERT INTO `t_orderlog` VALUES ('652', '10242', 'alipay_notify', '2014-03-22 18:10:56', '【支付宝异步通知-->退款流程】等待卖家同意退款(WAIT_SELLER_AGREE)。', 'p');
INSERT INTO `t_orderlog` VALUES ('653', '10242', 'alipay_notify', '2014-03-22 18:12:47', '【支付宝异步通知-->退款流程】退款协议达成，等待买家退货(WAIT_BUYER_RETURN_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('654', '10229', 'system', '2014-03-24 20:35:18', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('655', '10230', 'system', '2014-03-25 20:35:19', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('656', '10231', 'system', '2014-03-25 20:35:19', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('657', '10232', 'system', '2014-03-25 20:35:19', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('658', '10243', '_out_1395013499587', '2014-03-26 10:48:20', '【创建订单】用户创建订单。订单总金额：10903.0', 'w');
INSERT INTO `t_orderlog` VALUES ('659', '10243', 'huangf', '2014-03-26 10:51:47', '【增加支付记录】增22.0￥;', 'm');
INSERT INTO `t_orderlog` VALUES ('660', '10243', 'huangf', '2014-03-26 10:52:12', '【审核通过】', 'm');
INSERT INTO `t_orderlog` VALUES ('661', '10244', '_out_1395013499587', '2014-03-26 10:54:07', '【创建订单】用户创建订单。订单总金额：3013.0', 'w');
INSERT INTO `t_orderlog` VALUES ('662', '10245', '_out_1395013499587', '2014-03-26 10:56:10', '【创建订单】用户创建订单。订单总金额：103.0', 'w');
INSERT INTO `t_orderlog` VALUES ('663', '10245', 'huangf', '2014-03-26 11:54:20', '【修改订单总金额】总金额修改为：0.01', 'm');
INSERT INTO `t_orderlog` VALUES ('664', '10245', 'alipay_notify', '2014-03-26 12:01:50', '【支付宝异步通知】等待买家付款(WAIT_BUYER_PAY)。', 'p');
INSERT INTO `t_orderlog` VALUES ('665', '10244', 'huangf', '2014-03-26 12:14:25', '【修改订单总金额】总金额修改为：0.01', 'm');
INSERT INTO `t_orderlog` VALUES ('666', '10244', 'alipay_notify', '2014-03-26 12:15:22', '【支付宝异步通知】等待买家付款(WAIT_BUYER_PAY)。', 'p');
INSERT INTO `t_orderlog` VALUES ('667', '10248', '_out_1395013499587', '2014-03-26 12:31:25', '【创建订单】用户创建订单。订单总金额：1.08836463E8', 'w');
INSERT INTO `t_orderlog` VALUES ('668', '10249', '_out_1395013499587', '2014-03-26 12:34:35', '【创建订单】用户创建订单。订单总金额：2986023.0', 'w');
INSERT INTO `t_orderlog` VALUES ('669', '10249', 'huangf', '2014-03-26 12:34:58', '【修改订单总金额】总金额修改为：0.01', 'm');
INSERT INTO `t_orderlog` VALUES ('670', '10249', 'alipay_notify', '2014-03-26 12:36:00', '【支付宝异步通知】等待买家付款(WAIT_BUYER_PAY)。', 'p');
INSERT INTO `t_orderlog` VALUES ('671', '10250', 'test1', '2014-03-26 16:31:46', '【创建订单】用户创建订单。订单总金额：9783.0', 'w');
INSERT INTO `t_orderlog` VALUES ('672', '10250', 'huangf', '2014-03-26 16:33:21', '【修改订单总金额】总金额修改为：0.01', 'm');
INSERT INTO `t_orderlog` VALUES ('673', '10250', 'alipay_notify', '2014-03-26 16:33:50', '【支付宝异步通知】等待买家付款(WAIT_BUYER_PAY)。', 'p');
INSERT INTO `t_orderlog` VALUES ('674', '10250', 'alipay_notify', '2014-03-26 16:34:13', '【支付宝异步通知】已付款，等待卖家发货(WAIT_SELLER_SEND_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('675', '10250', 'huangf', '2014-03-26 16:36:58', '【修改订单总金额】总金额修改为：0.02', 'm');
INSERT INTO `t_orderlog` VALUES ('676', '10250', 'huangf', '2014-03-26 16:43:01', '【审核通过】', 'm');
INSERT INTO `t_orderlog` VALUES ('677', '10250', 'huangf', '2014-03-26 16:47:12', '【审核通过】', 'm');
INSERT INTO `t_orderlog` VALUES ('678', '10250', 'alipay_notify', '2014-03-26 16:56:41', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('679', '10250', 'alipay_notify', '2014-03-26 17:00:30', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('680', '10250', 'alipay_notify', '2014-03-26 17:10:21', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('681', '10250', 'alipay_notify', '2014-03-26 17:20:19', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('682', '10250', 'alipay_notify', '2014-03-26 20:20:12', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('683', '10250', 'alipay_notify', '2014-03-26 21:10:17', '【支付宝异步通知】交易完成(TRADE_FINISHED)。', 'p');
INSERT INTO `t_orderlog` VALUES ('684', '10250', 'alipay_notify', '2014-03-27 02:20:40', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('685', '10250', 'alipay_notify', '2014-03-27 17:20:01', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('686', '10239', 'alipay_notify', '2014-03-28 14:45:33', '【支付宝异步通知-->退款流程】退款成功(REFUND_SUCCESS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('687', '10240', 'alipay_notify', '2014-03-28 16:03:31', '【支付宝异步通知-->退款流程】退款成功(REFUND_SUCCESS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('688', '10241', 'alipay_notify', '2014-03-28 16:49:30', '【支付宝异步通知-->退款流程】退款成功(REFUND_SUCCESS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('689', '10234', 'system', '2014-03-29 12:03:54', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('690', '10236', 'system', '2014-03-29 12:03:54', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('691', '10237', 'system', '2014-03-29 12:03:54', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('692', '10246', 'system', '2014-04-03 12:03:59', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('693', '10247', 'system', '2014-04-03 12:03:59', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('694', '10248', 'system', '2014-04-03 12:03:59', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('695', '10242', 'alipay_notify', '2014-04-05 17:49:33', '【支付宝异步通知-->退款流程】退款关闭(REFUND_CLOSED)。', 'p');
INSERT INTO `t_orderlog` VALUES ('696', '10242', 'alipay_notify', '2014-04-05 17:53:56', '【支付宝异步通知-->退款流程】退款关闭(REFUND_CLOSED)。', 'p');
INSERT INTO `t_orderlog` VALUES ('697', '10242', 'alipay_notify', '2014-04-05 18:03:52', '【支付宝异步通知-->退款流程】退款关闭(REFUND_CLOSED)。', 'p');
INSERT INTO `t_orderlog` VALUES ('698', '10242', 'alipay_notify', '2014-04-05 18:13:40', '【支付宝异步通知-->退款流程】退款关闭(REFUND_CLOSED)。', 'p');
INSERT INTO `t_orderlog` VALUES ('699', '10242', 'alipay_notify', '2014-04-05 19:13:42', '【支付宝异步通知-->退款流程】退款关闭(REFUND_CLOSED)。', 'p');
INSERT INTO `t_orderlog` VALUES ('700', '10242', 'alipay_notify', '2014-04-05 21:13:34', '【支付宝异步通知-->退款流程】退款关闭(REFUND_CLOSED)。', 'p');
INSERT INTO `t_orderlog` VALUES ('701', '10242', 'alipay_notify', '2014-04-06 03:13:19', '【支付宝异步通知-->退款流程】退款关闭(REFUND_CLOSED)。', 'p');
INSERT INTO `t_orderlog` VALUES ('702', '10242', 'alipay_notify', '2014-04-06 18:13:57', '【支付宝异步通知-->退款流程】退款关闭(REFUND_CLOSED)。', 'p');
INSERT INTO `t_orderlog` VALUES ('703', '10251', 'ceshi01', '2014-04-08 14:27:24', '【创建订单】用户创建订单。订单总金额：19559.0', 'w');
INSERT INTO `t_orderlog` VALUES ('704', '10251', 'alipay_notify', '2014-04-08 14:27:34', '【支付宝异步通知】等待买家付款(WAIT_BUYER_PAY)。', 'p');
INSERT INTO `t_orderlog` VALUES ('705', '10252', 'test1', '2014-04-11 09:26:40', '【创建订单】用户创建订单。订单总金额：130671.0', 'w');
INSERT INTO `t_orderlog` VALUES ('706', '10252', 'admin', '2014-04-11 09:27:41', '【修改订单总金额】总金额修改为：0.01', 'm');
INSERT INTO `t_orderlog` VALUES ('707', '10252', 'alipay_notify', '2014-04-11 09:28:24', '【支付宝异步通知】等待买家付款(WAIT_BUYER_PAY)。', 'p');
INSERT INTO `t_orderlog` VALUES ('708', '10252', 'alipay_notify', '2014-04-11 09:28:47', '【支付宝异步通知】已付款，等待卖家发货(WAIT_SELLER_SEND_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('709', '10253', 'test1', '2014-04-11 09:33:40', '【创建订单】用户创建订单。订单总金额：939.0', 'w');
INSERT INTO `t_orderlog` VALUES ('710', '10253', 'alipay_notify', '2014-04-11 09:33:43', '【支付宝异步通知】等待买家付款(WAIT_BUYER_PAY)。', 'p');
INSERT INTO `t_orderlog` VALUES ('711', '10253', 'admin', '2014-04-11 09:34:00', '【修改订单总金额】总金额修改为：0.01', 'm');
INSERT INTO `t_orderlog` VALUES ('712', '10253', 'alipay_notify', '2014-04-11 09:34:08', '【支付宝异步通知】等待买家付款(WAIT_BUYER_PAY)。', 'p');
INSERT INTO `t_orderlog` VALUES ('713', '10254', 'test1', '2014-04-11 09:34:54', '【创建订单】用户创建订单。订单总金额：228663.0', 'w');
INSERT INTO `t_orderlog` VALUES ('714', '10254', 'alipay_notify', '2014-04-11 09:34:56', '【支付宝异步通知】等待买家付款(WAIT_BUYER_PAY)。', 'p');
INSERT INTO `t_orderlog` VALUES ('715', '10254', 'admin', '2014-04-11 09:35:09', '【修改订单总金额】总金额修改为：0.01', 'm');
INSERT INTO `t_orderlog` VALUES ('716', '10254', 'alipay_notify', '2014-04-11 09:35:20', '【支付宝异步通知】等待买家付款(WAIT_BUYER_PAY)。', 'p');
INSERT INTO `t_orderlog` VALUES ('717', '10254', 'alipay_notify', '2014-04-11 09:35:55', '【支付宝异步通知】已付款，等待卖家发货(WAIT_SELLER_SEND_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('718', '10254', 'admin', '2014-04-11 09:37:56', '【审核通过】', 'm');
INSERT INTO `t_orderlog` VALUES ('719', '10254', 'alipay_notify', '2014-04-11 09:38:04', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('720', '10254', 'alipay_notify', '2014-04-11 09:38:50', '【支付宝异步通知】交易完成(TRADE_FINISHED)。', 'p');
INSERT INTO `t_orderlog` VALUES ('721', '10254', 'alipay_notify', '2014-04-11 09:42:56', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('722', '10254', 'alipay_notify', '2014-04-11 09:52:28', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('723', '10254', 'alipay_notify', '2014-04-11 10:02:53', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('724', '10254', 'alipay_notify', '2014-04-11 11:03:01', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('725', '10254', 'alipay_notify', '2014-04-11 11:03:41', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('726', '10254', 'alipay_notify', '2014-04-11 19:02:29', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('727', '10254', 'alipay_notify', '2014-04-12 10:02:18', '【支付宝异步通知】已发货，等待买家确认收货(WAIT_BUYER_CONFIRM_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('728', '10251', 'alipay_notify', '2014-04-15 14:28:01', '【支付宝异步通知】未知。trade_status = TRADE_CLOSED', 'p');
INSERT INTO `t_orderlog` VALUES ('729', '10251', 'system', '2014-04-16 00:29:57', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('730', '10253', 'alipay_notify', '2014-04-18 09:34:04', '【支付宝异步通知】未知。trade_status = TRADE_CLOSED', 'p');
INSERT INTO `t_orderlog` VALUES ('731', '10254', 'alipay_notify', '2014-04-18 09:35:00', '【支付宝异步通知】未知。trade_status = TRADE_CLOSED', 'p');
INSERT INTO `t_orderlog` VALUES ('732', '10253', 'alipay_notify', '2014-04-18 09:35:00', '【支付宝异步通知】未知。trade_status = TRADE_CLOSED', 'p');
INSERT INTO `t_orderlog` VALUES ('733', '10253', 'system', '2014-04-19 00:30:00', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('734', '10255', 'lip1', '2014-05-04 10:20:52', '【创建订单】用户创建订单。订单总金额：86.00', 'w');
INSERT INTO `t_orderlog` VALUES ('735', '10255', 'lip', '2014-05-04 10:22:46', '【修改订单总金额】总金额修改为：0.01，修改人输入备注：测试支付', 'm');
INSERT INTO `t_orderlog` VALUES ('736', '10256', '_out_1399301353546', '2014-05-05 22:50:55', '【创建订单】用户创建订单。订单总金额：52011.00', 'w');
INSERT INTO `t_orderlog` VALUES ('737', '10255', 'system', '2014-05-12 00:32:51', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('738', '10256', 'system', '2014-05-13 00:10:40', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('739', '10257', '_out_1397461177517', '2014-06-14 08:05:11', '【创建订单】用户创建订单。订单总金额：48.00', 'w');
INSERT INTO `t_orderlog` VALUES ('740', '10258', 'test1', '2014-06-17 15:56:02', '【创建订单】用户创建订单。订单总金额：49.00', 'w');
INSERT INTO `t_orderlog` VALUES ('741', '10257', 'system', '2014-06-22 00:48:50', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('742', '10259', 'test1', '2014-06-30 21:23:46', '【创建订单】用户创建订单。订单总金额：95.00', 'w');
INSERT INTO `t_orderlog` VALUES ('743', '10258', 'system', '2014-07-01 11:41:09', '【系统取消】一周内未完全支付并且未审核的订单，系统自动将其取消。', 'm');
INSERT INTO `t_orderlog` VALUES ('744', '10260', 'wukong', '2018-03-04 21:53:00', '【创建订单】用户创建订单。订单总金额：2998.00', 'w');
INSERT INTO `t_orderlog` VALUES ('745', '10260', 'alipay_notify', '2018-03-04 21:53:03', '【支付宝异步通知】已付款，等待卖家发货(WAIT_SELLER_SEND_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('746', '10261', 'wukong', '2018-03-16 16:01:14', '【创建订单】用户创建订单。订单总金额：2998.00', 'w');
INSERT INTO `t_orderlog` VALUES ('747', '10262', 'wukong', '2018-03-18 17:02:07', '【创建订单】用户创建订单。订单总金额：6001.00', 'w');
INSERT INTO `t_orderlog` VALUES ('748', '10263', 'wukong', '2018-03-18 17:10:39', '【创建订单】用户创建订单。订单总金额：60.00', 'w');
INSERT INTO `t_orderlog` VALUES ('749', '10264', 'wukong', '2018-03-18 17:19:20', '【创建订单】用户创建订单。订单总金额：60.00', 'w');
INSERT INTO `t_orderlog` VALUES ('750', '10265', 'wukong', '2018-03-18 17:26:02', '【创建订单】用户创建订单。订单总金额：60.00', 'w');
INSERT INTO `t_orderlog` VALUES ('751', '10266', 'wukong', '2018-03-18 17:27:41', '【创建订单】用户创建订单。订单总金额：16.00', 'w');
INSERT INTO `t_orderlog` VALUES ('752', '10267', 'wukong', '2018-03-18 19:29:17', '【创建订单】用户创建订单。订单总金额：16.00', 'w');
INSERT INTO `t_orderlog` VALUES ('753', '10268', 'wukong', '2018-03-18 19:38:43', '【创建订单】用户创建订单。订单总金额：44.00', 'w');
INSERT INTO `t_orderlog` VALUES ('754', '10268', 'alipay_notify', '2018-03-18 19:41:14', '【支付宝异步通知】已付款，等待卖家发货(WAIT_SELLER_SEND_GOODS)。', 'p');
INSERT INTO `t_orderlog` VALUES ('755', '10269', 'wukong', '2018-03-18 19:42:03', '【创建订单】用户创建订单。订单总金额：16.00', 'w');
INSERT INTO `t_orderlog` VALUES ('756', '10270', 'wukong', '2018-03-18 19:43:08', '【创建订单】用户创建订单。订单总金额：75.00', 'w');
INSERT INTO `t_orderlog` VALUES ('757', '10271', 'wukong', '2018-03-18 20:28:28', '【创建订单】用户创建订单。订单总金额：2998.00', 'w');
INSERT INTO `t_orderlog` VALUES ('758', '10272', 'wukong', '2018-03-19 22:14:39', '【创建订单】用户创建订单。订单总金额：3003.00', 'w');
INSERT INTO `t_orderlog` VALUES ('759', '10273', 'wukong', '2018-03-22 22:28:16', '【创建订单】用户创建订单。订单总金额：3042.00', 'w');

-- ----------------------------
-- Table structure for `t_orderpay`
-- ----------------------------
DROP TABLE IF EXISTS `t_orderpay`;
CREATE TABLE `t_orderpay` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `orderid` varchar(11) COLLATE utf8_unicode_ci NOT NULL,
  `paystatus` varchar(2) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'n',
  `payamount` decimal(20,2) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `paymethod` varchar(22) COLLATE utf8_unicode_ci DEFAULT NULL,
  `confirmdate` datetime DEFAULT NULL,
  `confirmuser` varchar(11) COLLATE utf8_unicode_ci DEFAULT NULL,
  `remark` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tradeNo` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11208 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_orderpay
-- ----------------------------
INSERT INTO `t_orderpay` VALUES ('1', '10061', 'y', '32.00', '2014-01-19 18:22:28', 'zfb', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('2', '10061', 'y', '231321.00', '2014-01-19 18:22:35', 'zfb', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('3', '10061', 'y', '453.00', '2014-01-19 18:22:46', 'ylzx', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('4', '10061', 'y', '23.00', '2014-01-19 18:29:44', 'zfb', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('5', '10061', 'y', '322.00', '2014-01-19 18:30:32', 'zfb', null, null, '后台添加', null);
INSERT INTO `t_orderpay` VALUES ('6', '10061', 'y', '22.00', '2014-01-19 19:06:56', 'zfb', null, null, '后台添加', null);
INSERT INTO `t_orderpay` VALUES ('7', '10061', 'y', '32.00', '2014-01-19 19:08:40', 'zfb', null, null, '后台添加', null);
INSERT INTO `t_orderpay` VALUES ('8', '10061', 'y', '22.00', '2014-01-19 19:11:07', 'zfb', null, null, '后台添加', null);
INSERT INTO `t_orderpay` VALUES ('9', '10061', 'y', '33.00', '2014-01-19 19:18:26', 'zfb', null, null, '后台添加', null);
INSERT INTO `t_orderpay` VALUES ('10', '10062', 'y', '22.00', '2014-01-22 14:44:10', 'zfb', null, null, '后台添加', null);
INSERT INTO `t_orderpay` VALUES ('11', '10062', 'y', '31.00', '2014-01-22 14:44:32', 'zfb', null, null, '后台添加', null);
INSERT INTO `t_orderpay` VALUES ('12', '10062', 'y', '3.00', '2014-01-22 14:45:29', 'ylzx', null, null, '后台添加', null);
INSERT INTO `t_orderpay` VALUES ('13', '10062', 'y', '2.00', '2014-01-22 14:46:19', 'ylzx', null, null, '后台添加', null);
INSERT INTO `t_orderpay` VALUES ('14', '10062', 'y', '333.00', '2014-01-22 14:46:25', 'zfb', null, null, '后台添加', null);
INSERT INTO `t_orderpay` VALUES ('15', '10063', 'y', '44.00', '2014-01-22 17:32:52', 'zfb', null, null, '后台添加', null);
INSERT INTO `t_orderpay` VALUES ('16', '10065', 'y', '4.00', '2014-01-22 19:07:31', 'zfb', null, null, '后台添加', null);
INSERT INTO `t_orderpay` VALUES ('17', '10066', 'y', '22.00', '2014-01-25 13:33:41', 'zfb', null, null, '后台添加', null);
INSERT INTO `t_orderpay` VALUES ('18', '10067', 'y', '1111.00', '2014-01-26 19:38:49', 'zfb', null, null, '后台添加', null);
INSERT INTO `t_orderpay` VALUES ('19', '10068', 'y', '344.00', '2014-01-26 19:43:18', 'zfb', null, null, '后台添加', null);
INSERT INTO `t_orderpay` VALUES ('20', '10069', 'y', '323.00', '2014-01-27 19:24:29', 'zfb', null, null, '后台添加', null);
INSERT INTO `t_orderpay` VALUES ('21', '10075', 'y', '560.00', '2014-01-27 20:24:02', 'zfb', null, null, '后台添加', null);
INSERT INTO `t_orderpay` VALUES ('22', '10074', 'y', '21.00', '2014-01-27 21:13:24', 'zfb', null, null, '后台添加', null);
INSERT INTO `t_orderpay` VALUES ('23', '10082', 'y', '5000000.00', '2014-01-29 11:44:11', 'zfb', null, null, '后台添加', null);
INSERT INTO `t_orderpay` VALUES ('24', '10084', 'y', '1.00', '2014-02-04 21:31:06', 'zfb', null, null, '后台添加', null);
INSERT INTO `t_orderpay` VALUES ('25', '10088', 'y', '45.00', '2014-02-10 10:34:51', 'zfb', null, null, '后台添加', null);
INSERT INTO `t_orderpay` VALUES ('26', '10089', 'y', '44.00', '2014-02-14 18:10:43', 'zfb', null, null, '后台添加', null);
INSERT INTO `t_orderpay` VALUES ('27', '10087', 'y', '2.00', '2014-02-14 18:21:25', 'zfb', null, null, '后台添加', null);
INSERT INTO `t_orderpay` VALUES ('28', '10133', 'n', '11127.00', '2014-02-19 16:59:53', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('29', '10134', 'n', '11127.00', '2014-02-19 17:10:50', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('30', '10134', 'n', '11127.00', '2014-02-19 17:11:15', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('31', '10134', 'n', '0.01', '2014-02-19 17:23:59', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('32', '10135', 'n', '49.00', '2014-02-19 17:49:15', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('33', '10135', 'n', '0.01', '2014-02-19 17:49:45', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10000', '10136', 'n', '26.00', '2014-02-19 17:56:35', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10001', '10136', 'y', '0.01', '2014-02-19 17:57:02', 'alipayescow', null, null, null, '2014021937569568');
INSERT INTO `t_orderpay` VALUES ('10002', '10135', 'n', '0.01', '2014-02-19 22:27:23', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10003', '10134', 'n', '0.01', '2014-02-19 22:27:31', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10004', '10134', 'n', '0.01', '2014-02-19 22:28:49', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10005', '10137', 'n', '27.00', '2014-02-20 21:36:45', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10006', '10137', 'n', '27.00', '2014-02-20 21:37:09', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10007', '10138', 'n', '27.00', '2014-02-20 21:40:18', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10008', '10138', 'n', '27.00', '2014-02-21 09:02:20', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10009', '10139', 'n', '26.00', '2014-02-21 09:03:03', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10010', '10140', 'n', '26.00', '2014-02-21 09:03:48', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10011', '10141', 'n', '11127.00', '2014-02-22 10:32:58', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10012', '10142', 'n', '11127.00', '2014-02-22 10:34:12', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10013', '10143', 'n', '11127.00', '2014-02-22 17:26:14', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10014', '10143', 'n', '11127.00', '2014-02-22 17:26:25', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10015', '10144', 'n', '11176.00', '2014-02-22 17:27:44', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10016', '10145', 'n', '22298.00', '2014-02-22 17:28:49', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10017', '10142', 'n', '11127.00', '2014-02-22 20:07:50', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10018', '10146', 'n', '11127.00', '2014-02-22 20:27:56', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10019', '10147', 'n', '11127.00', '2014-02-22 20:29:29', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10020', '10148', 'n', '11127.00', '2014-02-22 20:32:18', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10021', '10149', 'n', '27.00', '2014-02-22 20:34:12', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10022', '10150', 'n', '27.00', '2014-02-22 20:37:50', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10023', '10151', 'n', '11127.00', '2014-02-22 21:29:28', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10024', '10152', 'n', '49.00', '2014-02-22 21:46:01', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10025', '10153', 'n', '22354.00', '2014-02-22 23:30:55', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10026', '10154', 'n', '319.00', '2014-02-22 23:35:43', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10027', '10155', 'n', '11127.00', '2014-02-23 09:28:34', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10028', '10156', 'n', '11127.00', '2014-02-23 09:29:12', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10029', '10157', 'n', '11127.00', '2014-02-23 09:35:50', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10030', '10158', 'n', '11127.00', '2014-02-23 09:52:50', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10031', '10159', 'n', '11127.00', '2014-02-23 10:29:00', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10032', '10160', 'n', '11137.00', '2014-02-23 10:29:41', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10033', '10161', 'n', '11137.00', '2014-02-23 10:35:32', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10034', '10162', 'n', '11137.00', '2014-02-23 10:38:35', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10035', '10163', 'n', '11137.00', '2014-02-23 10:38:50', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10036', '10164', 'n', '11137.00', '2014-02-23 10:39:11', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10037', '10165', 'n', '11137.00', '2014-02-23 10:39:40', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10038', '10166', 'n', '11137.00', '2014-02-23 10:40:17', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10039', '10167', 'n', '22249.00', '2014-02-23 10:40:54', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10040', '10168', 'n', '22249.00', '2014-02-23 10:41:09', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10041', '10169', 'n', '22249.00', '2014-02-23 10:41:41', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10042', '10170', 'n', '22249.00', '2014-02-23 10:41:51', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10043', '10171', 'n', '22249.00', '2014-02-23 10:42:16', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10044', '10172', 'n', '22259.00', '2014-02-23 10:42:22', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10045', '10173', 'n', '22259.00', '2014-02-23 10:42:40', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10046', '10174', 'n', '22259.00', '2014-02-23 10:43:10', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10047', '10175', 'n', '22259.00', '2014-02-23 10:44:02', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10048', '10176', 'n', '22259.00', '2014-02-23 10:44:17', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10049', '10177', 'n', '22259.00', '2014-02-23 10:44:49', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10050', '10178', 'n', '22259.00', '2014-02-23 10:45:01', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10051', '10179', 'n', '22259.00', '2014-02-23 10:45:16', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10052', '10180', 'n', '22259.00', '2014-02-23 10:45:25', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10053', '10181', 'n', '22259.00', '2014-02-23 10:45:34', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10054', '10182', 'n', '22259.00', '2014-02-23 10:45:42', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10055', '10183', 'n', '22259.00', '2014-02-23 10:46:09', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10056', '10184', 'n', '22259.00', '2014-02-23 10:46:16', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10057', '10185', 'n', '22259.00', '2014-02-23 10:46:45', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10058', '10186', 'n', '22259.00', '2014-02-23 10:47:00', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10059', '10186', 'n', '22259.00', '2014-02-23 10:48:01', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10060', '10187', 'n', '147.00', '2014-02-23 14:39:28', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10061', '10188', 'n', '59.00', '2014-02-23 14:45:43', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10062', '10189', 'n', '49.00', '2014-02-23 14:47:48', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10063', '10190', 'n', '59.00', '2014-02-23 14:49:47', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10064', '10191', 'n', '11127.00', '2014-02-23 17:02:30', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10065', '10192', 'n', '49.00', '2014-02-23 19:09:17', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10066', '10193', 'n', '59.00', '2014-02-23 19:10:40', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10067', '10194', 'n', '59.00', '2014-02-23 19:10:46', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10068', '10195', 'n', '44.00', '2014-02-23 19:10:53', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10069', '10196', 'n', '279.00', '2014-02-23 21:13:59', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10070', '10197', 'n', '11137.00', '2014-02-23 22:22:45', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10071', '10198', 'n', '11137.00', '2014-02-23 22:24:11', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10072', '10199', 'n', '11137.00', '2014-02-23 22:25:37', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10073', '10200', 'n', '11137.00', '2014-02-23 22:27:23', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10074', '10201', 'n', '11127.00', '2014-02-23 22:28:55', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10075', '10201', 'n', '11127.00', '2014-02-24 09:56:25', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10076', '10202', 'n', '36.00', '2014-02-24 12:14:52', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10077', '10203', 'n', '11181.00', '2014-02-24 13:51:44', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10078', '10204', 'n', '191.00', '2014-02-24 14:22:01', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10079', '10205', 'n', '36.00', '2014-02-25 16:04:01', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10080', '10206', 'n', '26.00', '2014-02-25 16:05:51', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10081', '10207', 'n', '313.00', '2014-02-28 12:22:25', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10082', '10208', 'n', '313.00', '2014-02-28 12:26:53', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10083', '10209', 'n', '499.00', '2014-02-28 12:29:46', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10084', '10210', 'n', '103.00', '2014-02-28 12:43:42', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10085', '10187', 'n', '147.00', '2014-02-28 12:59:39', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10086', '10186', 'n', '22259.00', '2014-02-28 12:59:45', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10087', '10185', 'n', '22259.00', '2014-02-28 12:59:49', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10088', '10211', 'n', '169.00', '2014-02-28 13:13:12', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10089', '10211', 'n', '0.01', '2014-03-01 16:38:39', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10090', '10212', 'n', '44.00', '2014-03-01 16:39:58', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10091', '10212', 'n', '44.00', '2014-03-01 16:57:08', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10100', '10212', 'n', '44.00', '2014-03-01 17:00:48', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10101', '10211', 'n', '0.01', '2014-03-01 17:48:07', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10102', '10211', 'y', '0.01', '2014-03-01 17:49:02', 'alipayescow', null, null, null, '2014030107812668');
INSERT INTO `t_orderpay` VALUES ('10103', '10212', 'n', '44.00', '2014-03-01 19:54:13', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10104', '10213', 'n', '37.00', '2014-03-01 20:12:30', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10105', '10213', 'y', '0.01', '2014-03-01 20:13:06', 'alipayescow', null, null, null, '2014030108780868');
INSERT INTO `t_orderpay` VALUES ('10106', '10214', 'n', '37.00', '2014-03-01 20:28:22', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10107', '10214', 'y', '0.01', '2014-03-01 20:28:57', 'alipayescow', null, null, null, '2014030108877868');
INSERT INTO `t_orderpay` VALUES ('10108', '10215', 'n', '37.00', '2014-03-01 21:11:13', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10109', '10215', 'y', '0.01', '2014-03-01 21:11:46', 'alipayescow', null, null, null, '2014030109149468');
INSERT INTO `t_orderpay` VALUES ('10110', '10216', 'n', '27.00', '2014-03-03 11:03:25', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10111', '10216', 'y', '0.01', '2014-03-03 11:15:33', 'alipayescow', null, null, null, '2014030317202668');
INSERT INTO `t_orderpay` VALUES ('10112', '10217', 'n', '59.00', '2014-03-03 11:21:58', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10113', '10217', 'n', '0.01', '2014-03-03 11:22:46', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10114', '10217', 'y', '0.01', '2014-03-03 11:28:30', 'alipayescow', null, null, null, '2014030317269068');
INSERT INTO `t_orderpay` VALUES ('10115', '10217', 'y', '0.01', '2014-03-03 11:33:03', 'alipayescow', null, null, null, '2014030317293268');
INSERT INTO `t_orderpay` VALUES ('10116', '10218', 'n', '11137.00', '2014-03-03 11:34:37', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10117', '10218', 'y', '0.01', '2014-03-03 11:35:13', 'alipayescow', null, null, null, '2014030317306268');
INSERT INTO `t_orderpay` VALUES ('10118', '10218', 'y', '0.01', '2014-03-03 11:38:58', 'alipayescow', null, null, null, '2014030317328268');
INSERT INTO `t_orderpay` VALUES ('10119', '10219', 'n', '37.00', '2014-03-03 11:43:30', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10120', '10219', 'y', '0.01', '2014-03-03 11:43:59', 'alipayescow', null, null, null, '2014030317356368');
INSERT INTO `t_orderpay` VALUES ('10121', '10220', 'n', '37.00', '2014-03-03 12:51:06', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10122', '10220', 'y', '0.01', '2014-03-03 12:51:49', 'alipayescow', null, null, null, '2014030317751368');
INSERT INTO `t_orderpay` VALUES ('10123', '10221', 'n', '59.00', '2014-03-03 13:36:57', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('10124', '10222', 'n', '59.00', '2014-03-03 13:37:01', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11125', '10223', 'n', '59.00', '2014-03-03 13:37:52', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11127', '10223', 'y', '0.01', '2014-03-03 13:42:47', 'alipayescow', null, null, null, '2014030318034668');
INSERT INTO `t_orderpay` VALUES ('11128', '10224', 'n', '59.00', '2014-03-03 13:52:36', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11129', '10224', 'n', '0.01', '2014-03-03 13:53:15', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11130', '10224', 'n', '0.01', '2014-03-03 13:55:03', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11131', '10224', 'n', '0.01', '2014-03-03 13:55:30', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11132', '10224', 'y', '0.01', '2014-03-03 13:56:35', 'alipayescow', null, null, null, '2014030318109768');
INSERT INTO `t_orderpay` VALUES ('11133', '10225', 'n', '37.00', '2014-03-03 15:56:36', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11134', '10225', 'y', '0.01', '2014-03-03 15:57:07', 'alipayescow', null, null, null, '2014030318739768');
INSERT INTO `t_orderpay` VALUES ('11135', '10226', 'n', '71.00', '2014-03-04 17:26:36', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11136', '10227', 'n', '87.00', '2014-03-04 17:38:39', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11137', '10228', 'n', '1085.00', '2014-03-04 17:50:57', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11138', '10228', 'n', '1085.00', '2014-03-05 19:02:30', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11139', '10226', 'n', '71.00', '2014-03-05 19:03:43', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11140', '10222', 'n', '59.00', '2014-03-05 19:04:08', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11141', '10229', 'n', '59.00', '2014-03-16 22:00:11', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11142', '10230', 'n', '10903.00', '2014-03-17 15:04:23', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11143', '10231', 'n', '10903.00', '2014-03-17 15:05:45', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11144', '10232', 'n', '10903.00', '2014-03-17 15:07:25', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11145', '10233', 'n', '10903.00', '2014-03-18 14:58:43', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11146', '10233', 'y', '0.01', '2014-03-18 16:43:03', 'alipayescow', null, null, null, '2014031813379068');
INSERT INTO `t_orderpay` VALUES ('11147', '10233', 'y', '1.00', '2014-03-21 12:00:18', 'zfb', null, null, '后台添加', 'tradeNoTest');
INSERT INTO `t_orderpay` VALUES ('11148', '10234', 'n', '10903.00', '2014-03-21 12:04:34', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11149', '10234', 'n', '10903.00', '2014-03-21 12:04:54', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11150', '10234', 'y', '0.01', '2014-03-21 12:05:20', 'alipayescow', null, null, null, '2014032126928968');
INSERT INTO `t_orderpay` VALUES ('11151', '10235', 'n', '59.00', '2014-03-21 12:21:16', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11152', '10235', 'y', '0.10', '2014-03-21 12:21:43', 'zfb', null, null, '后台添加', 'tradeNoTest');
INSERT INTO `t_orderpay` VALUES ('11153', '10236', 'n', '3057.00', '2014-03-21 12:22:17', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11154', '10236', 'y', '0.01', '2014-03-21 12:22:37', 'alipayescow', null, null, null, '2014032127020068');
INSERT INTO `t_orderpay` VALUES ('11155', '10237', 'n', '10903.00', '2014-03-21 12:30:03', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11156', '10237', 'y', '0.01', '2014-03-21 12:30:27', 'alipayescow', null, null, null, '2014032127063968');
INSERT INTO `t_orderpay` VALUES ('11157', '10238', 'n', '10903.00', '2014-03-21 12:57:18', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11158', '10238', 'y', '0.01', '2014-03-21 12:57:39', 'alipayescow', null, null, null, '2014032127205168');
INSERT INTO `t_orderpay` VALUES ('11159', '10239', 'n', '10903.00', '2014-03-21 14:35:39', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11160', '10239', 'y', '0.01', '2014-03-21 14:37:05', 'alipayescow', null, null, null, '2014032127731068');
INSERT INTO `t_orderpay` VALUES ('11161', '10240', 'n', '10903.00', '2014-03-21 15:52:12', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11162', '10240', 'y', '0.01', '2014-03-21 15:52:43', 'alipayescow', null, null, null, '2014032128144668');
INSERT INTO `t_orderpay` VALUES ('11163', '10241', 'n', '3003.00', '2014-03-21 16:15:04', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11164', '10241', 'n', '0.01', '2014-03-21 16:15:58', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11165', '10241', 'y', '0.01', '2014-03-21 16:17:44', 'alipayescow', null, null, null, '2014032128303668');
INSERT INTO `t_orderpay` VALUES ('11166', '10242', 'n', '6011.00', '2014-03-21 17:32:37', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11167', '10242', 'y', '0.01', '2014-03-21 17:33:17', 'alipayescow', null, null, null, '2014032128774768');
INSERT INTO `t_orderpay` VALUES ('11168', '10243', 'n', '10903.00', '2014-03-26 10:48:20', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11169', '10243', 'y', '22.00', '2014-03-26 10:51:47', 'zfb', null, null, '后台添加', 'tradeNoTest');
INSERT INTO `t_orderpay` VALUES ('11170', '10244', 'n', '3013.00', '2014-03-26 10:54:06', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11171', '10245', 'n', '103.00', '2014-03-26 10:56:10', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11172', '10245', 'y', '0.01', '2014-03-26 11:56:09', 'alipayescow', null, null, null, '2014032650342068');
INSERT INTO `t_orderpay` VALUES ('11173', '10244', 'y', '0.01', '2014-03-26 12:14:35', 'alipayescow', null, null, null, '2014032650450168');
INSERT INTO `t_orderpay` VALUES ('11174', '10248', 'n', '108836463.00', '2014-03-26 12:31:25', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11175', '10249', 'n', '2986023.00', '2014-03-26 12:34:35', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11176', '10249', 'y', '0.01', '2014-03-26 12:35:10', 'alipayescow', null, null, null, '2014032650573968');
INSERT INTO `t_orderpay` VALUES ('11177', '10250', 'n', '9783.00', '2014-03-26 16:31:46', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11178', '10250', 'y', '0.01', '2014-03-26 16:33:31', 'alipayescow', null, null, null, '2014032651645368');
INSERT INTO `t_orderpay` VALUES ('11179', '10251', 'n', '19559.00', '2014-04-08 14:27:24', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11180', '10252', 'n', '130671.00', '2014-04-11 09:26:40', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11181', '10252', 'y', '0.01', '2014-04-11 09:27:57', 'alipayescow', null, null, null, '2014041144056068');
INSERT INTO `t_orderpay` VALUES ('11182', '10253', 'n', '939.00', '2014-04-11 09:33:40', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11183', '10253', 'n', '0.01', '2014-04-11 09:34:06', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11184', '10254', 'n', '228663.00', '2014-04-11 09:34:54', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11185', '10254', 'y', '0.01', '2014-04-11 09:35:16', 'alipayescow', null, null, null, '2014041144082468');
INSERT INTO `t_orderpay` VALUES ('11186', '10255', 'n', '86.00', '2014-05-04 10:20:52', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11187', '10255', 'n', '0.01', '2014-05-04 10:23:27', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11188', '10255', 'n', '0.01', '2014-05-04 10:25:46', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11189', '10256', 'n', '52011.00', '2014-05-05 22:50:55', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11190', '10257', 'n', '48.00', '2014-06-14 08:05:11', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11191', '10257', 'n', '48.00', '2014-06-14 08:17:39', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11192', '10258', 'n', '49.00', '2014-06-17 15:56:02', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11193', '10259', 'n', '95.00', '2014-06-30 21:23:46', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11194', '10260', 'y', '2998.00', '2018-03-04 21:53:00', 'alipayescow', null, null, null, '2791959689326291');
INSERT INTO `t_orderpay` VALUES ('11195', '10261', 'n', '2998.00', '2018-03-16 16:01:14', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11196', '10262', 'n', '6001.00', '2018-03-18 17:02:07', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11197', '10263', 'n', '60.00', '2018-03-18 17:10:39', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11198', '10264', 'n', '60.00', '2018-03-18 17:19:20', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11199', '10265', 'n', '60.00', '2018-03-18 17:26:02', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11200', '10266', 'n', '16.00', '2018-03-18 17:27:41', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11201', '10267', 'n', '16.00', '2018-03-18 19:29:17', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11202', '10268', 'y', '44.00', '2018-03-18 19:38:43', 'alipayescow', null, null, null, '711917953102222');
INSERT INTO `t_orderpay` VALUES ('11203', '10269', 'n', '16.00', '2018-03-18 19:42:03', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11204', '10270', 'n', '75.00', '2018-03-18 19:43:08', 'alipayescow', null, null, null, null);
INSERT INTO `t_orderpay` VALUES ('11205', '10271', 'y', '2998.00', '2018-03-18 20:28:28', 'alipayescow', null, null, null, '714921008764839');
INSERT INTO `t_orderpay` VALUES ('11206', '10272', 'y', '3003.00', '2018-03-19 22:14:39', 'alipayescow', null, null, null, '807574826176395');
INSERT INTO `t_orderpay` VALUES ('11207', '10273', 'y', '3042.00', '2018-03-22 22:28:16', 'alipayescow', null, null, null, '27076494079609');

-- ----------------------------
-- Table structure for `t_ordership`
-- ----------------------------
DROP TABLE IF EXISTS `t_ordership`;
CREATE TABLE `t_ordership` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `orderid` varchar(11) COLLATE utf8_unicode_ci NOT NULL,
  `shipname` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `shipaddress` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `provinceCode` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `province` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `cityCode` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `city` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `areaCode` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `area` varchar(25) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `tel` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `zip` varchar(6) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sex` varchar(2) COLLATE utf8_unicode_ci DEFAULT NULL,
  `remark` varchar(70) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=196 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_ordership
-- ----------------------------
INSERT INTO `t_ordership` VALUES ('1', '10051', '1', '1', null, '1', null, '1', null, '1', '1', '1', '1', '1', '1');
INSERT INTO `t_ordership` VALUES ('2', '10073', '张三', '地址地址地址地址地址地址地址地址', null, '湖北省', null, '洪湖市', null, '大通湖', '15000748603', '88888888', '111111', '1', null);
INSERT INTO `t_ordership` VALUES ('3', '10074', '张三', '地址', null, '湖北省', null, '洪湖市', null, '大通湖', '15000748603', '88888888', '111111', '1', null);
INSERT INTO `t_ordership` VALUES ('4', '10075', '张三', '地址', null, '湖北省', null, '洪湖市', null, '大通湖', '15000748603', '88888888', '111111', '1', null);
INSERT INTO `t_ordership` VALUES ('5', '10076', '张三', '地址', null, '湖北省', null, '洪湖市', null, '大通湖', '15000748603', '88888888', '111111', '1', null);
INSERT INTO `t_ordership` VALUES ('6', '10077', '张三', '地址', null, '湖北省', null, '洪湖市', null, '大通湖', '15000748603', '88888888', '111111', '1', null);
INSERT INTO `t_ordership` VALUES ('7', '10078', '张三', '地址', null, '湖北省', null, '洪湖市', null, '大通湖', '15000748603', '88888888', '111111', '1', null);
INSERT INTO `t_ordership` VALUES ('8', '10079', '张三', '地址', null, '湖北省', null, '洪湖市', null, '大通湖', '15000748603', '88888888', '111111', '1', null);
INSERT INTO `t_ordership` VALUES ('9', '10080', '张三', '地址', null, '湖北省', null, '洪湖市', null, '大通湖', '15000748603', '88888888', '111111', '1', null);
INSERT INTO `t_ordership` VALUES ('10', '10081', '张三', '地址', null, '湖北省', null, '洪湖市', null, '大通湖', '15000748603', '88888888', '111111', '1', null);
INSERT INTO `t_ordership` VALUES ('11', '10082', '张三', '地址', null, '湖北省', null, '洪湖市', null, '大通湖', '15000748603', '88888888', '111111', '1', null);
INSERT INTO `t_ordership` VALUES ('12', '10083', '张三', '地址', null, '湖北省', null, '洪湖市', null, '大通湖', '15000748603', '88888888', '111111', '1', null);
INSERT INTO `t_ordership` VALUES ('13', '10084', '张三', '地址', null, '湖北省', null, '洪湖市', null, '大通湖', '15000748603', '88888888', '111111', '1', null);
INSERT INTO `t_ordership` VALUES ('14', '10085', '张三', '地址', null, '湖北省', null, '洪湖市', null, '大通湖', '15000748603', '88888888', '111111', '1', null);
INSERT INTO `t_ordership` VALUES ('15', '10086', '张三', '地址', null, '湖北省', null, '洪湖市', null, '大通湖', '15000748603', '88888888', '111111', '1', null);
INSERT INTO `t_ordership` VALUES ('16', '10087', '张三', '地址', null, '湖北省', null, '洪湖市', null, '大通湖', '15000748603', '88888888', '111111', '1', null);
INSERT INTO `t_ordership` VALUES ('17', '10088', '张三', '地址', null, '湖北省', null, '洪湖市', null, '大通湖', '15000748603', '88888888', '111111', '1', null);
INSERT INTO `t_ordership` VALUES ('18', '10089', '张三', '地址', null, '湖北省', null, '洪湖市', null, '大通湖', '15000748603', '88888888', '111111', '1', null);
INSERT INTO `t_ordership` VALUES ('19', '10090', '张三', '地址', null, '湖北省', null, '洪湖市', null, '大通湖', '15000748603', '88888888', '111111', '1', null);
INSERT INTO `t_ordership` VALUES ('20', '10091', '张三', '地址', null, '湖北省', null, '洪湖市', null, '大通湖', '15000748603', '88888888', '111111', '1', null);
INSERT INTO `t_ordership` VALUES ('21', '10092', '张三', '地址', null, '湖北省', null, '洪湖市', null, '大通湖', '15000748603', '88888888', '111111', '1', null);
INSERT INTO `t_ordership` VALUES ('22', '10093', '张三', '地址', null, '湖北省', null, '洪湖市', null, '大通湖', '15000748603', '88888888', '111111', '1', null);
INSERT INTO `t_ordership` VALUES ('23', '10094', '张三', '地址', null, '湖北省', null, '洪湖市', null, '大通湖', '15000748603', '88888888', '111111', '1', null);
INSERT INTO `t_ordership` VALUES ('24', '10095', '张三', '地址', null, '湖北省', null, '洪湖市', null, '大通湖', '15000748603', '88888888', '111111', '1', null);
INSERT INTO `t_ordership` VALUES ('25', '10096', '张三', '地址', null, '湖北省', null, '洪湖市', null, '大通湖', '15000748603', '88888888', '111111', '1', null);
INSERT INTO `t_ordership` VALUES ('26', '10097', '1', '1', null, '1', null, '1', null, '1', '15000748603', '88888888', '111111', '1', null);
INSERT INTO `t_ordership` VALUES ('27', '10098', '1', '1', null, '1', null, '1', null, '1', '15000748603', '88888888', '111111', '1', null);
INSERT INTO `t_ordership` VALUES ('28', '10099', '1', '1', null, '1', null, '1', null, '1', '15000748603', '88888888', '111111', '1', '1111');
INSERT INTO `t_ordership` VALUES ('29', '10100', '1', '1', null, '1', null, '1', null, '1', '15000748603', '88888888', '111111', '1', '1111');
INSERT INTO `t_ordership` VALUES ('30', '10101', '张三', 'XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号', null, '湖北省', null, '洪湖市', null, '大通湖', '13312341234', '0571-88158090', '123456', '1', '11111');
INSERT INTO `t_ordership` VALUES ('31', '10102', '张三', 'XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号', null, '湖北省', null, '洪湖市', null, '大通湖', '13312341234', '0571-88158090', '123456', '1', '11111');
INSERT INTO `t_ordership` VALUES ('32', '10103', '张三', 'XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号', null, '湖北省', null, '洪湖市', null, '大通湖', '13312341234', '0571-88158090', '123456', '1', '11111');
INSERT INTO `t_ordership` VALUES ('33', '10104', '张三', 'XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号', null, '湖北省', null, '洪湖市', null, '大通湖', '13312341234', '0571-88158090', '123456', '1', '描述');
INSERT INTO `t_ordership` VALUES ('34', '10105', '张三', 'XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号', null, '湖北省', null, '洪湖市', null, '大通湖', '13312341234', '0571-88158090', '123456', '1', '描述');
INSERT INTO `t_ordership` VALUES ('35', '10106', '张三', 'XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号', null, '湖北省', null, '洪湖市', null, '大通湖', '13312341234', '0571-88158090', '123456', '1', '描述');
INSERT INTO `t_ordership` VALUES ('36', '10107', '张三', 'XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号', null, '湖北省', null, '洪湖市', null, '大通湖', '13312341234', '0571-88158090', '123456', '1', '描述');
INSERT INTO `t_ordership` VALUES ('37', '10108', '张三', 'XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号', null, '湖北省', null, '洪湖市', null, '大通湖', '13312341234', '0571-88158090', '123456', '1', '描述');
INSERT INTO `t_ordership` VALUES ('38', '10109', '张三', 'XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号', null, '湖北省', null, '洪湖市', null, '大通湖', '13312341234', '0571-88158090', '123456', '1', '描述');
INSERT INTO `t_ordership` VALUES ('39', '10110', '张三', 'XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号', null, '湖北省', null, '洪湖市', null, '大通湖', '13312341234', '0571-88158090', '123456', '1', '描述');
INSERT INTO `t_ordership` VALUES ('40', '10111', '张三', 'XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号', null, '湖北省', null, '洪湖市', null, '大通湖', '13312341234', '0571-88158090', '123456', '1', '描述');
INSERT INTO `t_ordership` VALUES ('41', '10112', '张三', 'XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号', null, '湖北省', null, '洪湖市', null, '大通湖', '13312341234', '0571-88158090', '123456', '1', '描述');
INSERT INTO `t_ordership` VALUES ('42', '10113', '张三', 'XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号', null, '湖北省', null, '洪湖市', null, '大通湖', '13312341234', '0571-88158090', '123456', '1', '描述');
INSERT INTO `t_ordership` VALUES ('43', '10114', '张三', 'XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号', null, '湖北省', null, '洪湖市', null, '大通湖', '13312341234', '0571-88158090', '123456', '1', '描述');
INSERT INTO `t_ordership` VALUES ('44', '10115', '张三', 'XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号', null, '湖北省', null, '洪湖市', null, '大通湖', '13312341234', '0571-88158090', '123456', '1', '描述');
INSERT INTO `t_ordership` VALUES ('45', '10116', '张三', 'XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号', null, '湖北省', null, '洪湖市', null, '大通湖', '13312341234', '0571-88158090', '123456', '1', '描述');
INSERT INTO `t_ordership` VALUES ('46', '10117', '张三', 'XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号', null, '湖北省', null, '洪湖市', null, '大通湖', '13312341234', '0571-88158090', '123456', '1', '描述');
INSERT INTO `t_ordership` VALUES ('47', '10118', '张三', 'XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号', null, '湖北省', null, '洪湖市', null, '大通湖', '13312341234', '0571-88158090', '123456', '1', '描述');
INSERT INTO `t_ordership` VALUES ('48', '10119', '张三', 'XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号', null, '湖北省', null, '洪湖市', null, '大通湖', '13312341234', '0571-88158090', '123456', '1', '描述');
INSERT INTO `t_ordership` VALUES ('49', '10120', '张三', 'XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号', null, '湖北省', null, '洪湖市', null, '大通湖', '13312341234', '0571-88158090', '123456', '1', '描述');
INSERT INTO `t_ordership` VALUES ('50', '10121', '张三', 'XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号', null, '湖北省', null, '洪湖市', null, '大通湖', '13312341234', '0571-88158090', '123456', '1', '描述');
INSERT INTO `t_ordership` VALUES ('51', '10122', '张三', 'XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号', null, '湖北省', null, '洪湖市', null, '大通湖', '13312341234', '0571-88158090', '123456', '1', '描述');
INSERT INTO `t_ordership` VALUES ('52', '10123', '张三', 'XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号', null, '湖北省', null, '洪湖市', null, '大通湖', '13312341234', '0571-88158090', '123456', '1', '描述');
INSERT INTO `t_ordership` VALUES ('53', '10124', '张三', 'XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号', null, '湖北省', null, '洪湖市', null, '大通湖', '13312341234', '0571-88158090', '123456', '1', '描述');
INSERT INTO `t_ordership` VALUES ('54', '10125', '张三', 'XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号', null, '湖北省', null, '洪湖市', null, '大通湖', '13312341234', '0571-88158090', '123456', '1', '描述');
INSERT INTO `t_ordership` VALUES ('55', '10126', '张三', 'XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号', null, '湖北省', null, '洪湖市', null, '大通湖', '13312341234', '0571-88158090', '123456', '1', '描述');
INSERT INTO `t_ordership` VALUES ('56', '10127', '张三', 'XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号', null, '湖北省', null, '洪湖市', null, '大通湖', '13312341234', '0571-88158090', '123456', '1', '描述');
INSERT INTO `t_ordership` VALUES ('57', '10128', '张三', 'XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号', null, '湖北省', null, '洪湖市', null, '大通湖', '13312341234', '0571-88158090', '123456', '1', '描述');
INSERT INTO `t_ordership` VALUES ('58', '10129', '张三', 'XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号', null, '湖北省', null, '洪湖市', null, '大通湖', '13312341234', '0571-88158090', '123456', '1', '描述');
INSERT INTO `t_ordership` VALUES ('59', '10130', '张三', 'XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号', null, '湖北省', null, '洪湖市', null, '大通湖', '13312341234', '0571-88158090', '123456', '1', '描述');
INSERT INTO `t_ordership` VALUES ('60', '10131', '张三', 'XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号', null, '湖北省', null, '洪湖市', null, '大通湖', '13312341234', '0571-88158090', '123456', '1', '描述');
INSERT INTO `t_ordership` VALUES ('61', '10133', '张三', 'XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号', null, '湖北省', null, '洪湖市', null, '大通湖', '13312341234', '0571-88158090', '123456', '1', '描述');
INSERT INTO `t_ordership` VALUES ('62', '10134', '张三', 'XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号', null, '湖北省', null, '洪湖市', null, '大通湖', '13312341234', '0571-88158090', '123456', '1', '描述');
INSERT INTO `t_ordership` VALUES ('63', '10135', '张三', 'XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号', null, '湖北省', null, '洪湖市', null, '大通湖', '13312341234', '0571-88158090', '123456', '1', '描述');
INSERT INTO `t_ordership` VALUES ('64', '10136', '张三', 'XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号', null, '湖北省', null, '洪湖市', null, '大通湖', '13312341234', '0571-88158090', '123456', '1', '描述');
INSERT INTO `t_ordership` VALUES ('65', '10137', '张三', 'XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号', null, '湖北省', null, '洪湖市', null, '大通湖', '13312341234', '0571-88158090', '123456', '1', '描述');
INSERT INTO `t_ordership` VALUES ('66', '10138', '张三', 'XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号', null, '湖北省', null, '洪湖市', null, '大通湖', '13312341234', '0571-88158090', '123456', '1', '描述');
INSERT INTO `t_ordership` VALUES ('67', '10139', '张三', 'XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号', null, '湖北省', null, '洪湖市', null, '大通湖', '13312341234', '0571-88158090', '123456', '1', '描述');
INSERT INTO `t_ordership` VALUES ('68', '10140', '张三', 'XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号', null, '湖北省', null, '洪湖市', null, '大通湖', '13312341234', '0571-88158090', '123456', '1', '描述');
INSERT INTO `t_ordership` VALUES ('69', '10141', '张三', 'XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号', null, '湖北省', null, '洪湖市', null, '大通湖', '13312341234', '0571-88158090', '123456', '1', '描述');
INSERT INTO `t_ordership` VALUES ('70', '10142', '张三', 'XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号', null, '湖北省', null, '洪湖市', null, '大通湖', '13312341234', '0571-88158090', '123456', '1', '描述');
INSERT INTO `t_ordership` VALUES ('71', '10143', '张三', 'XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号', null, '湖北省', null, '洪湖市', null, '大通湖', '13312341234', '0571-88158090', '123456', '1', '描述');
INSERT INTO `t_ordership` VALUES ('72', '10144', '张三', 'XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号', null, '湖北省', null, '洪湖市', null, '大通湖', '13312341234', '0571-88158090', '123456', '1', '描述');
INSERT INTO `t_ordership` VALUES ('73', '10145', '张三', 'XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号', null, '湖北省', null, '洪湖市', null, '大通湖', '13312341234', '0571-88158090', '123456', '1', '描述');
INSERT INTO `t_ordership` VALUES ('74', '10146', '张三', 'XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号', null, '湖北省', null, '洪湖市', null, '大通湖', '13312341234', '0571-88158090', '123456', '1', '描述');
INSERT INTO `t_ordership` VALUES ('75', '10147', '张三', 'XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号', null, '湖北省', null, '洪湖市', null, '大通湖', '13312341234', '0571-88158090', '123456', '1', '描述');
INSERT INTO `t_ordership` VALUES ('76', '10148', '张三', 'XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号', null, '湖北省', null, '洪湖市', null, '大通湖', '13312341234', '0571-88158090', '123456', '1', '描述');
INSERT INTO `t_ordership` VALUES ('77', '10149', '张三', 'XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号', null, '湖北省', null, '洪湖市', null, '大通湖', '13312341234', '0571-88158090', '123456', '1', '描述');
INSERT INTO `t_ordership` VALUES ('78', '10150', '张三', 'XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号', null, '湖北省', null, '洪湖市', null, '大通湖', '13312341234', '0571-88158090', '123456', '1', '描述');
INSERT INTO `t_ordership` VALUES ('79', '10152', '黄飞', '140000140200普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('80', '10153', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('81', '10154', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('82', '10155', '李飞', '山西省阳泉市恶的风景的是辣的叫老垃圾的辣的叫阿里的骄傲', null, '山西省', null, '阳泉市', null, null, '3232132', '15000748603', '333333', '1', null);
INSERT INTO `t_ordership` VALUES ('83', '10156', '李飞', '山西省阳泉市恶的风景的是辣的叫老垃圾的辣的叫阿里的骄傲', null, '山西省', null, '阳泉市', null, null, '3232132', '15000748603', '333333', '1', null);
INSERT INTO `t_ordership` VALUES ('84', '10157', '李飞', '山西省阳泉市恶的风景的是辣的叫老垃圾的辣的叫阿里的骄傲', null, '山西省', null, '阳泉市', null, null, '3232132', '15000748603', '333333', '1', null);
INSERT INTO `t_ordership` VALUES ('85', '10158', '李飞', '山西省阳泉市恶的风景的是辣的叫老垃圾的辣的叫阿里的骄傲', null, '山西省', null, '阳泉市', null, null, '3232132', '15000748603', '333333', '1', null);
INSERT INTO `t_ordership` VALUES ('86', '10159', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('87', '10160', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('88', '10161', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('89', '10162', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('90', '10163', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('91', '10164', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('92', '10165', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('93', '10166', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('94', '10167', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('95', '10168', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('96', '10169', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('97', '10170', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('98', '10171', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('99', '10172', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('100', '10173', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('101', '10174', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('102', '10175', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('103', '10176', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('104', '10177', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('105', '10178', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('106', '10179', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('107', '10180', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('108', '10181', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('109', '10182', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('110', '10183', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('111', '10184', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('112', '10185', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('113', '10186', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('114', '10187', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('115', '10188', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('116', '10189', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('117', '10190', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('118', '10191', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('119', '10192', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('120', '10193', '黄飞', '浙江省湖州市普陀区真关路真元小区10号楼301', null, '浙江省', null, '湖州市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('121', '10194', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('122', '10195', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('123', '10196', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('124', '10197', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('125', '10198', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('126', '10199', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('127', '10200', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('128', '10201', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('129', '10202', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('130', '10203', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('131', '10204', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('132', '10205', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('133', '10206', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('134', '10207', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('135', '10208', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('136', '10209', '李飞', '山西省阳泉市恶的风景的是辣的叫老垃圾的辣的叫阿里的骄傲', null, '山西省', null, '阳泉市', null, null, '3232132', '15000748603', '333333', '1', null);
INSERT INTO `t_ordership` VALUES ('137', '10210', '李飞', '山西省阳泉市恶的风景的是辣的叫老垃圾的辣的叫阿里的骄傲', null, '山西省', null, '阳泉市', null, null, '3232132', '15000748603', '333333', '1', null);
INSERT INTO `t_ordership` VALUES ('138', '10211', '黄飞', '浙江省湖州市普陀区真关路真元小区10号楼301', null, '浙江省', null, '湖州市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('139', '10212', '黄飞', '浙江省湖州市普陀区真关路真元小区10号楼301', null, '浙江省', null, '湖州市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('140', '10213', '黄飞', '浙江省湖州市普陀区真关路真元小区10号楼301', null, '浙江省', null, '湖州市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('141', '10214', '黄飞', '浙江省湖州市普陀区真关路真元小区10号楼301', null, '浙江省', null, '湖州市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('142', '10215', '黄飞', '浙江省湖州市普陀区真关路真元小区10号楼301', null, '浙江省', null, '湖州市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('143', '10216', '黄飞', '浙江省湖州市普陀区真关路真元小区10号楼301', null, '浙江省', null, '湖州市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('144', '10217', '黄飞', '浙江省湖州市普陀区真关路真元小区10号楼301', null, '浙江省', null, '湖州市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('145', '10218', '黄飞', '浙江省湖州市普陀区真关路真元小区10号楼301', null, '浙江省', null, '湖州市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('146', '10219', '黄飞', '浙江省湖州市普陀区真关路真元小区10号楼301', null, '浙江省', null, '湖州市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('147', '10220', '黄飞', '浙江省湖州市普陀区真关路真元小区10号楼301', null, '浙江省', null, '湖州市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('148', '10221', '黄飞', '浙江省湖州市普陀区真关路真元小区10号楼301', null, '浙江省', null, '湖州市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('149', '10222', '黄飞', '浙江省湖州市普陀区真关路真元小区10号楼301', null, '浙江省', null, '湖州市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('150', '10223', '黄飞', '浙江省湖州市普陀区真关路真元小区10号楼301', null, '浙江省', null, '湖州市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('151', '10224', '黄飞', '浙江省湖州市普陀区真关路真元小区10号楼301', null, '浙江省', null, '湖州市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('152', '10225', '黄飞', '浙江省湖州市普陀区真关路真元小区10号楼301', null, '浙江省', null, '湖州市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('153', '10226', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('154', '10227', '黄飞', '山西省大同市普陀区真关路真元小区10号楼301', null, '山西省', null, '大同市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('155', '10228', '黄飞', '浙江省湖州市普陀区真关路真元小区10号楼301', null, '浙江省', null, '湖州市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('156', '10232', '张三丰', '天津天津市新年里201', '120000', '天津', '120100', '天津市', '120110', '蓟县', '15000748603', '15000748603', '111111', '1', null);
INSERT INTO `t_ordership` VALUES ('157', '10233', '张三丰', '天津天津市新年里201', '120000', '天津', '120100', '天津市', '120110', '蓟县', '15000748603', '15000748603', '111111', '1', null);
INSERT INTO `t_ordership` VALUES ('158', '10234', '张三丰', '天津天津市新年里201', '120000', '天津', '120100', '天津市', '120110', '蓟县', '15000748603', '15000748603', '111111', '1', null);
INSERT INTO `t_ordership` VALUES ('159', '10235', '张三丰', '天津天津市新年里201', '120000', '天津', '120100', '天津市', '120110', '蓟县', '15000748603', '15000748603', '111111', '1', null);
INSERT INTO `t_ordership` VALUES ('160', '10236', '张三丰', '天津天津市新年里201', '120000', '天津', '120100', '天津市', '120110', '蓟县', '15000748603', '15000748603', '111111', '1', null);
INSERT INTO `t_ordership` VALUES ('161', '10237', '张三', '天津天津市花开花落后路', '120000', '天津', '120100', '天津市', '120103', '蓟县', '15000748603', '15000748603', '111111', '1', null);
INSERT INTO `t_ordership` VALUES ('162', '10238', '张三丰', '天津天津市新年里201', '120000', '天津', '120100', '天津市', '120110', '蓟县', '15000748603', '15000748603', '111111', '1', null);
INSERT INTO `t_ordership` VALUES ('163', '10239', '张三丰', '天津天津市新年里201', '120000', '天津', '120100', '天津市', '120110', '蓟县', '15000748603', '15000748603', '111111', '1', null);
INSERT INTO `t_ordership` VALUES ('164', '10240', '张三丰', '天津天津市新年里201', '120000', '天津', '120100', '天津市', '120110', '蓟县', '15000748603', '15000748603', '111111', '1', null);
INSERT INTO `t_ordership` VALUES ('165', '10241', '张三丰', '天津天津市新年里201', '120000', '天津', '120100', '天津市', '120110', '蓟县', '15000748603', '15000748603', '111111', '1', null);
INSERT INTO `t_ordership` VALUES ('166', '10242', '张三丰', '天津天津市新年里201', '120000', '天津', '120100', '天津市', '120110', '蓟县', '15000748603', '15000748603', '111111', '1', null);
INSERT INTO `t_ordership` VALUES ('167', '10243', '张三丰', '天津天津市新年里201', '120000', '天津', '120100', '天津市', '120110', '蓟县', '15000748603', '15000748603', '111111', '1', null);
INSERT INTO `t_ordership` VALUES ('168', '10244', '张三丰', '天津天津市新年里201', '120000', '天津', '120100', '天津市', '120110', '蓟县', '15000748603', '15000748603', '111111', '1', null);
INSERT INTO `t_ordership` VALUES ('169', '10245', '张三丰', '天津天津市新年里201', '120000', '天津', '120100', '天津市', '120110', '蓟县', '15000748603', '15000748603', '111111', '1', null);
INSERT INTO `t_ordership` VALUES ('170', '10248', '张三丰', '天津天津市新年里201', '120000', '天津', '120100', '天津市', '120110', '蓟县', '15000748603', '15000748603', '111111', '1', null);
INSERT INTO `t_ordership` VALUES ('171', '10249', '张三丰', '天津天津市新年里201', '120000', '天津', '120100', '天津市', '120110', '蓟县', '15000748603', '15000748603', '111111', '1', null);
INSERT INTO `t_ordership` VALUES ('172', '10250', '黄飞', '浙江省湖州市普陀区真关路真元小区10号楼301', '330000', '浙江省', '330500', '湖州市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('173', '10251', '张运峰', '甘肃省张掖市张运峰', '620000', '甘肃省', '620700', '张掖市', '620725', '山丹县', '010-12345678', '13145378912', '100000', '1', null);
INSERT INTO `t_ordership` VALUES ('174', '10252', '黄飞', '浙江省湖州市普陀区真关路真元小区10号楼301', '330000', '浙江省', '330500', '湖州市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('175', '10253', '黄飞', '浙江省湖州市普陀区真关路真元小区10号楼301', '330000', '浙江省', '330500', '湖州市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('176', '10254', '黄飞', '浙江省湖州市普陀区真关路真元小区10号楼301', '330000', '浙江省', '330500', '湖州市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('177', '10255', '测试', '上海上海市测试', '310000', '上海', '310100', '上海市', '310105', '崇明县', '021-5555555555', '13211111111', '123410', '1', null);
INSERT INTO `t_ordership` VALUES ('178', '10256', '的的 啊', '山西省大同市打牌快点  ', '140000', '山西省', '140200', '大同市', '140222', '大同县', '655485', '15985662565', '265362', '1', null);
INSERT INTO `t_ordership` VALUES ('179', '10257', '23', '天津天津市3213', '120000', '天津', '120100', '天津市', '120105', '蓟县', '13111111111', '13111111111', '312312', '1', null);
INSERT INTO `t_ordership` VALUES ('180', '10258', '黄飞', '浙江省湖州市普陀区真关路真元小区10号楼301', '330000', '浙江省', '330500', '湖州市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('181', '10259', '黄飞', '浙江省湖州市普陀区真关路真元小区10号楼301', '330000', '浙江省', '330500', '湖州市', null, null, '15000748603', '15000748603', '433221', '1', null);
INSERT INTO `t_ordership` VALUES ('182', '10260', '孙悟空', '湖南省长沙市岳麓区企业广场2', '430000', '湖南省', '430100', '长沙市', '430104', '浏阳市', '1212', '13787063330', '410100', '1', null);
INSERT INTO `t_ordership` VALUES ('183', '10261', '孙悟空', '湖南省长沙市岳麓区企业广场2', '430000', '湖南省', '430100', '长沙市', '430104', '浏阳市', '1212', '13787063330', '410100', '1', null);
INSERT INTO `t_ordership` VALUES ('184', '10262', '孙悟空', '湖南省长沙市岳麓区企业广场2', '430000', '湖南省', '430100', '长沙市', '430104', '浏阳市', '1212', '13787063330', '410100', '1', null);
INSERT INTO `t_ordership` VALUES ('185', '10263', '孙悟空', '湖南省长沙市岳麓区企业广场2', '430000', '湖南省', '430100', '长沙市', '430104', '浏阳市', '1212', '13787063330', '410100', '1', null);
INSERT INTO `t_ordership` VALUES ('186', '10264', '孙悟空', '湖南省长沙市岳麓区企业广场2', '430000', '湖南省', '430100', '长沙市', '430104', '浏阳市', '1212', '13787063330', '410100', '1', null);
INSERT INTO `t_ordership` VALUES ('187', '10265', '孙悟空', '湖南省长沙市岳麓区企业广场2', '430000', '湖南省', '430100', '长沙市', '430104', '浏阳市', '1212', '13787063330', '410100', '1', null);
INSERT INTO `t_ordership` VALUES ('188', '10266', '孙悟空', '湖南省长沙市岳麓区企业广场2', '430000', '湖南省', '430100', '长沙市', '430104', '浏阳市', '1212', '13787063330', '410100', '1', null);
INSERT INTO `t_ordership` VALUES ('189', '10267', '孙悟空', '湖南省长沙市岳麓区企业广场2', '430000', '湖南省', '430100', '长沙市', '430104', '浏阳市', '1212', '13787063330', '410100', '1', null);
INSERT INTO `t_ordership` VALUES ('190', '10268', '孙悟空', '湖南省长沙市岳麓区企业广场2', '430000', '湖南省', '430100', '长沙市', '430104', '浏阳市', '1212', '13787063330', '410100', '1', null);
INSERT INTO `t_ordership` VALUES ('191', '10269', '孙悟空', '湖南省长沙市岳麓区企业广场2', '430000', '湖南省', '430100', '长沙市', '430104', '浏阳市', '1212', '13787063330', '410100', '1', null);
INSERT INTO `t_ordership` VALUES ('192', '10270', '孙悟空', '湖南省长沙市岳麓区企业广场2', '430000', '湖南省', '430100', '长沙市', '430104', '浏阳市', '1212', '13787063330', '410100', '1', null);
INSERT INTO `t_ordership` VALUES ('193', '10271', '孙悟空', '湖南省长沙市岳麓区企业广场2', '430000', '湖南省', '430100', '长沙市', '430104', '浏阳市', '1212', '13787063330', '410100', '1', null);
INSERT INTO `t_ordership` VALUES ('194', '10272', '孙悟空', '湖南省长沙市岳麓区企业广场2', '430000', '湖南省', '430100', '长沙市', '430104', '浏阳市', '1212', '13787063330', '410100', '1', null);
INSERT INTO `t_ordership` VALUES ('195', '10273', '孙悟空', '湖南省长沙市岳麓区企业广场2', '430000', '湖南省', '430100', '长沙市', '430104', '浏阳市', '1212', '13787063330', '410100', '1', null);

-- ----------------------------
-- Table structure for `t_oss`
-- ----------------------------
DROP TABLE IF EXISTS `t_oss`;
CREATE TABLE `t_oss` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `code` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `status` varchar(2) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'y',
  `ossJsonInfo` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_oss
-- ----------------------------
INSERT INTO `t_oss` VALUES ('1', '阿里云存储', 'aliyun', 'y', '{\"ACCESS_ID\":\"xxx\",\"ACCESS_KEY\":\"xxx\",\"OSS_ENDPOINT\":\"http://oss.aliyuncs.com/\",\"bucketName\":\"xxx\"}');

-- ----------------------------
-- Table structure for `t_pay`
-- ----------------------------
DROP TABLE IF EXISTS `t_pay`;
CREATE TABLE `t_pay` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `code` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `seller` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `order1` int(11) DEFAULT NULL,
  `status` varchar(2) COLLATE utf8_unicode_ci DEFAULT 'y',
  `partner` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `key1` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `icon` varchar(245) COLLATE utf8_unicode_ci DEFAULT NULL,
  `picture` varchar(245) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_pay
-- ----------------------------
INSERT INTO `t_pay` VALUES ('1', '支付宝担保交易', 'alipayescow', 'xxx', '1', 'y', 'xxx', 'xxx', null, '/attached/image/20140616/1402924030591_3.png');

-- ----------------------------
-- Table structure for `t_privilege`
-- ----------------------------
DROP TABLE IF EXISTS `t_privilege`;
CREATE TABLE `t_privilege` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rid` int(11) NOT NULL,
  `mid` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2733 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_privilege
-- ----------------------------
INSERT INTO `t_privilege` VALUES ('2044', '2', '11');
INSERT INTO `t_privilege` VALUES ('2045', '2', '14');
INSERT INTO `t_privilege` VALUES ('2046', '2', '64');
INSERT INTO `t_privilege` VALUES ('2047', '2', '1');
INSERT INTO `t_privilege` VALUES ('2048', '2', '5');
INSERT INTO `t_privilege` VALUES ('2049', '2', '57');
INSERT INTO `t_privilege` VALUES ('2572', '6', '57');
INSERT INTO `t_privilege` VALUES ('2573', '6', '11');
INSERT INTO `t_privilege` VALUES ('2574', '6', '65');
INSERT INTO `t_privilege` VALUES ('2575', '6', '14');
INSERT INTO `t_privilege` VALUES ('2576', '6', '73');
INSERT INTO `t_privilege` VALUES ('2577', '6', '78');
INSERT INTO `t_privilege` VALUES ('2578', '6', '74');
INSERT INTO `t_privilege` VALUES ('2579', '6', '75');
INSERT INTO `t_privilege` VALUES ('2580', '6', '76');
INSERT INTO `t_privilege` VALUES ('2581', '6', '77');
INSERT INTO `t_privilege` VALUES ('2582', '6', '64');
INSERT INTO `t_privilege` VALUES ('2583', '6', '66');
INSERT INTO `t_privilege` VALUES ('2584', '6', '86');
INSERT INTO `t_privilege` VALUES ('2585', '6', '18');
INSERT INTO `t_privilege` VALUES ('2586', '6', '19');
INSERT INTO `t_privilege` VALUES ('2587', '6', '84');
INSERT INTO `t_privilege` VALUES ('2588', '6', '85');
INSERT INTO `t_privilege` VALUES ('2589', '6', '23');
INSERT INTO `t_privilege` VALUES ('2590', '6', '90');
INSERT INTO `t_privilege` VALUES ('2591', '6', '24');
INSERT INTO `t_privilege` VALUES ('2592', '6', '46');
INSERT INTO `t_privilege` VALUES ('2593', '6', '6');
INSERT INTO `t_privilege` VALUES ('2594', '6', '43');
INSERT INTO `t_privilege` VALUES ('2595', '6', '71');
INSERT INTO `t_privilege` VALUES ('2596', '6', '7');
INSERT INTO `t_privilege` VALUES ('2597', '6', '45');
INSERT INTO `t_privilege` VALUES ('2598', '6', '9');
INSERT INTO `t_privilege` VALUES ('2599', '6', '40');
INSERT INTO `t_privilege` VALUES ('2600', '6', '87');
INSERT INTO `t_privilege` VALUES ('2601', '6', '88');
INSERT INTO `t_privilege` VALUES ('2602', '6', '32');
INSERT INTO `t_privilege` VALUES ('2603', '6', '34');
INSERT INTO `t_privilege` VALUES ('2604', '6', '35');
INSERT INTO `t_privilege` VALUES ('2605', '6', '80');
INSERT INTO `t_privilege` VALUES ('2606', '6', '81');
INSERT INTO `t_privilege` VALUES ('2607', '6', '82');
INSERT INTO `t_privilege` VALUES ('2608', '6', '83');
INSERT INTO `t_privilege` VALUES ('2609', '6', '1');
INSERT INTO `t_privilege` VALUES ('2610', '6', '48');
INSERT INTO `t_privilege` VALUES ('2611', '6', '38');
INSERT INTO `t_privilege` VALUES ('2612', '6', '49');
INSERT INTO `t_privilege` VALUES ('2613', '6', '51');
INSERT INTO `t_privilege` VALUES ('2614', '6', '69');
INSERT INTO `t_privilege` VALUES ('2615', '1', '57');
INSERT INTO `t_privilege` VALUES ('2616', '1', '11');
INSERT INTO `t_privilege` VALUES ('2617', '1', '65');
INSERT INTO `t_privilege` VALUES ('2618', '1', '14');
INSERT INTO `t_privilege` VALUES ('2619', '1', '73');
INSERT INTO `t_privilege` VALUES ('2620', '1', '78');
INSERT INTO `t_privilege` VALUES ('2621', '1', '74');
INSERT INTO `t_privilege` VALUES ('2622', '1', '75');
INSERT INTO `t_privilege` VALUES ('2623', '1', '76');
INSERT INTO `t_privilege` VALUES ('2624', '1', '77');
INSERT INTO `t_privilege` VALUES ('2625', '1', '64');
INSERT INTO `t_privilege` VALUES ('2626', '1', '66');
INSERT INTO `t_privilege` VALUES ('2627', '1', '93');
INSERT INTO `t_privilege` VALUES ('2628', '1', '86');
INSERT INTO `t_privilege` VALUES ('2629', '1', '18');
INSERT INTO `t_privilege` VALUES ('2630', '1', '19');
INSERT INTO `t_privilege` VALUES ('2631', '1', '84');
INSERT INTO `t_privilege` VALUES ('2632', '1', '85');
INSERT INTO `t_privilege` VALUES ('2633', '1', '23');
INSERT INTO `t_privilege` VALUES ('2634', '1', '90');
INSERT INTO `t_privilege` VALUES ('2635', '1', '24');
INSERT INTO `t_privilege` VALUES ('2636', '1', '46');
INSERT INTO `t_privilege` VALUES ('2637', '1', '6');
INSERT INTO `t_privilege` VALUES ('2638', '1', '43');
INSERT INTO `t_privilege` VALUES ('2639', '1', '71');
INSERT INTO `t_privilege` VALUES ('2640', '1', '7');
INSERT INTO `t_privilege` VALUES ('2641', '1', '45');
INSERT INTO `t_privilege` VALUES ('2642', '1', '9');
INSERT INTO `t_privilege` VALUES ('2643', '1', '40');
INSERT INTO `t_privilege` VALUES ('2644', '1', '87');
INSERT INTO `t_privilege` VALUES ('2645', '1', '88');
INSERT INTO `t_privilege` VALUES ('2646', '1', '94');
INSERT INTO `t_privilege` VALUES ('2647', '1', '32');
INSERT INTO `t_privilege` VALUES ('2648', '1', '34');
INSERT INTO `t_privilege` VALUES ('2649', '1', '35');
INSERT INTO `t_privilege` VALUES ('2650', '1', '80');
INSERT INTO `t_privilege` VALUES ('2651', '1', '81');
INSERT INTO `t_privilege` VALUES ('2652', '1', '82');
INSERT INTO `t_privilege` VALUES ('2653', '1', '83');
INSERT INTO `t_privilege` VALUES ('2654', '1', '91');
INSERT INTO `t_privilege` VALUES ('2655', '1', '92');
INSERT INTO `t_privilege` VALUES ('2656', '1', '1');
INSERT INTO `t_privilege` VALUES ('2657', '1', '3');
INSERT INTO `t_privilege` VALUES ('2658', '1', '52');
INSERT INTO `t_privilege` VALUES ('2659', '1', '55');
INSERT INTO `t_privilege` VALUES ('2660', '1', '56');
INSERT INTO `t_privilege` VALUES ('2661', '1', '4');
INSERT INTO `t_privilege` VALUES ('2662', '1', '58');
INSERT INTO `t_privilege` VALUES ('2663', '1', '59');
INSERT INTO `t_privilege` VALUES ('2664', '1', '60');
INSERT INTO `t_privilege` VALUES ('2665', '1', '2');
INSERT INTO `t_privilege` VALUES ('2666', '1', '61');
INSERT INTO `t_privilege` VALUES ('2667', '1', '62');
INSERT INTO `t_privilege` VALUES ('2668', '1', '63');
INSERT INTO `t_privilege` VALUES ('2669', '1', '5');
INSERT INTO `t_privilege` VALUES ('2670', '1', '48');
INSERT INTO `t_privilege` VALUES ('2671', '1', '38');
INSERT INTO `t_privilege` VALUES ('2672', '1', '36');
INSERT INTO `t_privilege` VALUES ('2673', '1', '49');
INSERT INTO `t_privilege` VALUES ('2674', '1', '51');
INSERT INTO `t_privilege` VALUES ('2675', '1', '69');
INSERT INTO `t_privilege` VALUES ('2676', '7', '57');
INSERT INTO `t_privilege` VALUES ('2677', '7', '11');
INSERT INTO `t_privilege` VALUES ('2678', '7', '65');
INSERT INTO `t_privilege` VALUES ('2679', '7', '14');
INSERT INTO `t_privilege` VALUES ('2680', '7', '73');
INSERT INTO `t_privilege` VALUES ('2681', '7', '78');
INSERT INTO `t_privilege` VALUES ('2682', '7', '74');
INSERT INTO `t_privilege` VALUES ('2683', '7', '75');
INSERT INTO `t_privilege` VALUES ('2684', '7', '76');
INSERT INTO `t_privilege` VALUES ('2685', '7', '77');
INSERT INTO `t_privilege` VALUES ('2686', '7', '64');
INSERT INTO `t_privilege` VALUES ('2687', '7', '66');
INSERT INTO `t_privilege` VALUES ('2688', '7', '93');
INSERT INTO `t_privilege` VALUES ('2689', '7', '86');
INSERT INTO `t_privilege` VALUES ('2690', '7', '18');
INSERT INTO `t_privilege` VALUES ('2691', '7', '19');
INSERT INTO `t_privilege` VALUES ('2692', '7', '84');
INSERT INTO `t_privilege` VALUES ('2693', '7', '85');
INSERT INTO `t_privilege` VALUES ('2694', '7', '23');
INSERT INTO `t_privilege` VALUES ('2695', '7', '90');
INSERT INTO `t_privilege` VALUES ('2696', '7', '24');
INSERT INTO `t_privilege` VALUES ('2697', '7', '46');
INSERT INTO `t_privilege` VALUES ('2698', '7', '6');
INSERT INTO `t_privilege` VALUES ('2699', '7', '43');
INSERT INTO `t_privilege` VALUES ('2700', '7', '71');
INSERT INTO `t_privilege` VALUES ('2701', '7', '7');
INSERT INTO `t_privilege` VALUES ('2702', '7', '45');
INSERT INTO `t_privilege` VALUES ('2703', '7', '9');
INSERT INTO `t_privilege` VALUES ('2704', '7', '40');
INSERT INTO `t_privilege` VALUES ('2705', '7', '87');
INSERT INTO `t_privilege` VALUES ('2706', '7', '88');
INSERT INTO `t_privilege` VALUES ('2707', '7', '94');
INSERT INTO `t_privilege` VALUES ('2708', '7', '32');
INSERT INTO `t_privilege` VALUES ('2709', '7', '34');
INSERT INTO `t_privilege` VALUES ('2710', '7', '35');
INSERT INTO `t_privilege` VALUES ('2711', '7', '80');
INSERT INTO `t_privilege` VALUES ('2712', '7', '81');
INSERT INTO `t_privilege` VALUES ('2713', '7', '82');
INSERT INTO `t_privilege` VALUES ('2714', '7', '83');
INSERT INTO `t_privilege` VALUES ('2715', '7', '91');
INSERT INTO `t_privilege` VALUES ('2716', '7', '92');
INSERT INTO `t_privilege` VALUES ('2717', '7', '1');
INSERT INTO `t_privilege` VALUES ('2718', '7', '3');
INSERT INTO `t_privilege` VALUES ('2719', '7', '52');
INSERT INTO `t_privilege` VALUES ('2720', '7', '55');
INSERT INTO `t_privilege` VALUES ('2721', '7', '56');
INSERT INTO `t_privilege` VALUES ('2722', '7', '4');
INSERT INTO `t_privilege` VALUES ('2723', '7', '58');
INSERT INTO `t_privilege` VALUES ('2724', '7', '59');
INSERT INTO `t_privilege` VALUES ('2725', '7', '60');
INSERT INTO `t_privilege` VALUES ('2726', '7', '5');
INSERT INTO `t_privilege` VALUES ('2727', '7', '48');
INSERT INTO `t_privilege` VALUES ('2728', '7', '38');
INSERT INTO `t_privilege` VALUES ('2729', '7', '36');
INSERT INTO `t_privilege` VALUES ('2730', '7', '49');
INSERT INTO `t_privilege` VALUES ('2731', '7', '51');
INSERT INTO `t_privilege` VALUES ('2732', '7', '69');

-- ----------------------------
-- Table structure for `t_product`
-- ----------------------------
DROP TABLE IF EXISTS `t_product`;
CREATE TABLE `t_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `introduce` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL,
  `price` decimal(9,2) DEFAULT NULL,
  `nowPrice` decimal(9,2) DEFAULT NULL,
  `picture` varchar(245) COLLATE utf8_unicode_ci DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `createAccount` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `updateAccount` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `updatetime` datetime DEFAULT NULL,
  `isnew` char(1) COLLATE utf8_unicode_ci DEFAULT 'n',
  `sale` char(1) COLLATE utf8_unicode_ci DEFAULT 'n',
  `hit` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0',
  `productHTML` longtext COLLATE utf8_unicode_ci,
  `maxPicture` varchar(245) COLLATE utf8_unicode_ci DEFAULT NULL,
  `images` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `catalogID` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sellcount` int(11) DEFAULT '0',
  `stock` int(11) DEFAULT '0',
  `searchKey` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `title` varchar(80) COLLATE utf8_unicode_ci DEFAULT NULL,
  `description` varchar(145) COLLATE utf8_unicode_ci DEFAULT NULL,
  `keywords` varchar(145) COLLATE utf8_unicode_ci DEFAULT NULL,
  `activityID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `unit` varchar(25) COLLATE utf8_unicode_ci DEFAULT 'item',
  `score` int(11) DEFAULT '0',
  `isTimePromotion` char(1) COLLATE utf8_unicode_ci DEFAULT 'n',
  `giftID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `product_name` (`name`),
  KEY `product_catalog` (`catalogID`),
  KEY `product_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=10311 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_product
-- ----------------------------
INSERT INTO `t_product` VALUES ('10263', '【送米兔+贴膜】MIUI/小米 小米手机3代M3MI3小米3米联通移动官网', '【送米兔+贴膜】MIUI/小米 小米手机3代M3MI3小米3米联通移动官网', '2999.00', '2998.00', '/image/20180322/1521726687776_3.png', '2014-03-04 11:00:25', 'wukong', 'admin', '2018-03-22 13:33:26', 'y', 'n', '150', '2', '<p>\r\n	<img src=\"https://img20.360buyimg.com/vc/jfs/t3937/143/153243628/130774/7722db38/583e6ab1Nde4a861a.jpg\" alt=\"\" /> \r\n</p>\r\n<p>\r\n	<img src=\"https://img20.360buyimg.com/vc/jfs/t3271/109/4320983438/178616/62aacfb5/583e6ab2N156454f0.jpg\" alt=\"\" /> \r\n</p>\r\n<p>\r\n	<img src=\"https://img20.360buyimg.com/vc/jfs/t3274/94/4279630839/214239/2b4c979d/583e6ab2N56d0f4d7.jpg\" alt=\"\" />\r\n</p>\r\n<p>\r\n	<img src=\"https://img20.360buyimg.com/vc/jfs/t3058/81/4340587444/476967/e1523473/583e6ab4N1634dcae.jpg\" alt=\"\" />\r\n</p>\r\n<p>\r\n	<img src=\"https://img20.360buyimg.com/vc/jfs/t3058/81/4340587444/476967/e1523473/583e6ab4N1634dcae.jpg\" alt=\"\" />\r\n</p>\r\n<p>\r\n	<img src=\"https://img20.360buyimg.com/vc/jfs/t3412/337/1983826965/324468/4d1dfba2/583e7ac8N61e7480f.jpg\" alt=\"\" />\r\n</p>\r\n<p>\r\n	<img src=\"https://img13.360buyimg.com/cms/jfs/t3448/244/2067261924/599337/eb4077bc/583ffe2aNf70c4ff4.jpg\" alt=\"\" />\r\n</p>\r\n<p>\r\n	<img src=\"https://img20.360buyimg.com/vc/jfs/t3325/27/1903571558/115397/18139cf1/583e6ad4N7e34eea6.jpg\" alt=\"\" />\r\n</p>\r\n<p>\r\n	<img src=\"https://img20.360buyimg.com/vc/jfs/t8230/100/1845600311/150686/c23ba61f/59c0ba02Nd4223b82.jpg\" alt=\"\" />\r\n</p>', null, '/image/20180322/1521723506470_1.png,image/20180322/1521726687776_3.png', '74', '10000', '9', null, null, null, null, null, 'item', '0', 'n', '');
INSERT INTO `t_product` VALUES ('10265', 'Lenovo/联想 Lenovo G480A-ITH(D) G470 I3I5I714寸', 'Lenovo/联想 Lenovo G480A-ITH(D) G470 I3I5I714寸笔记本电脑', '8999.00', '8666.00', '/image/20180323/1521811190521_3.png', '2014-03-04 15:38:16', 'wukong', 'admin', '2018-03-23 21:21:26', 'y', 'y', '44', '2', '<p>\r\n	<img src=\"Lenovo/联想 Lenovo G480A-ITH(D) G470 I3I5I714寸\" alt=\"\" /><img src=\"https://img20.360buyimg.com/vc/jfs/t13903/4/1378668717/1716315/c499987d/5a1f7534N601662ae.jpg\" alt=\"\" />\r\n</p>', null, ',', '26', '0', '1212', null, 'Lenovo/联想 Lenovo G480A-ITH(D) G470 I3I5I714寸', 'Lenovo/联想 Lenovo G480A-ITH(D) G470 I3I5I714寸', 'Lenovo/联想 Lenovo G480A-ITH(D) G470 I3I5I714寸', null, 'item', '0', 'n', '');
INSERT INTO `t_product` VALUES ('10270', '全国包邮海豚三公主USB有线鼠标 超可爱女生笔记本电脑鼠标', '全国包邮海豚三公主USB有线鼠标 超可爱女生笔记本电脑鼠标', '35.00', '30.00', '/image/20180323/1521810102008_3.png', '2014-03-04 16:20:45', 'wukong', 'admin', '2018-03-23 21:26:47', 'y', 'y', '29', '2', '<p>\r\n	<img src=\"https://img20.360buyimg.com/vc/jfs/t3937/143/153243628/130774/7722db38/583e6ab1Nde4a861a.jpg\" alt=\"\" /> \r\n</p>\r\n<p>\r\n	<img src=\"https://img20.360buyimg.com/vc/jfs/t3271/109/4320983438/178616/62aacfb5/583e6ab2N156454f0.jpg\" alt=\"\" /> \r\n</p>\r\n<p>\r\n	<img src=\"https://img20.360buyimg.com/vc/jfs/t3274/94/4279630839/214239/2b4c979d/583e6ab2N56d0f4d7.jpg\" alt=\"\" /> \r\n</p>\r\n<p>\r\n	<img src=\"https://img20.360buyimg.com/vc/jfs/t3058/81/4340587444/476967/e1523473/583e6ab4N1634dcae.jpg\" alt=\"\" /> \r\n</p>\r\n<p>\r\n	<img src=\"https://img20.360buyimg.com/vc/jfs/t3058/81/4340587444/476967/e1523473/583e6ab4N1634dcae.jpg\" alt=\"\" /> \r\n</p>\r\n<p>\r\n	<img src=\"https://img20.360buyimg.com/vc/jfs/t3412/337/1983826965/324468/4d1dfba2/583e7ac8N61e7480f.jpg\" alt=\"\" /> \r\n</p>\r\n<p>\r\n	<img src=\"https://img13.360buyimg.com/cms/jfs/t3448/244/2067261924/599337/eb4077bc/583ffe2aNf70c4ff4.jpg\" alt=\"\" /> \r\n</p>\r\n<p>\r\n	<img src=\"https://img20.360buyimg.com/vc/jfs/t3325/27/1903571558/115397/18139cf1/583e6ad4N7e34eea6.jpg\" alt=\"\" /> \r\n</p>\r\n<p>\r\n	<img src=\"https://img20.360buyimg.com/vc/jfs/t8230/100/1845600311/150686/c23ba61f/59c0ba02Nd4223b82.jpg\" alt=\"\" /> \r\n</p>', null, ',', '37', '0', '0', null, null, null, null, null, 'item', '0', 'n', '');
INSERT INTO `t_product` VALUES ('10272', '最新24寸一体机,联想款,一体机电脑电视,真四核,4G,高清 低价包邮', '最新24寸一体机,联想款,一体机电脑电视,真四核,4G,高清 低价包邮', '2200.00', '1860.00', '/image/20180323/1521811364317_3.png', '2014-03-06 10:01:34', 'wukong', 'admin', '2018-03-23 21:23:48', 'y', 'y', '19', '2', '<p>\r\n	<img src=\"https://img30.360buyimg.com/popWaterMark/jfs/t14710/318/773454757/71072/bc1263c0/5a388343N647f111c.jpg\" alt=\"\" />\r\n</p>', null, 'image/20180323/1521811364317_3.png,/image/20180322/1521723506470_1.png,image/20180322/1521726687776_3.png', '26', '100', '1000', null, null, null, null, '6', 'item', '0', 'n', '');
INSERT INTO `t_product` VALUES ('10273', '特价现货送大礼MIUI/小米 红米电信版红米1S电信超值服务红米电信', '现货抢购！双模四核 红米1S 电信版舍我其谁！！！ 送专属贴膜硅胶套。限时特价！！红米电信尊享服务------加1元OTG线，免费升级最新系统指导（需和客服联系，升级版可以让你的爱机更流畅 优化了系统的个别问题）', '1328.00', '1195.20', '/image/20180323/1521811711188_3.png', '2014-03-06 10:18:46', 'wukong', 'admin', '2018-03-23 21:28:39', 'y', 'y', '29', '2', '<p>\r\n	<img src=\"https://img20.360buyimg.com/vc/jfs/t3937/143/153243628/130774/7722db38/583e6ab1Nde4a861a.jpg\" alt=\"\" /> \r\n</p>\r\n<p>\r\n	<img src=\"https://img20.360buyimg.com/vc/jfs/t3271/109/4320983438/178616/62aacfb5/583e6ab2N156454f0.jpg\" alt=\"\" /> \r\n</p>\r\n<p>\r\n	<img src=\"https://img20.360buyimg.com/vc/jfs/t3274/94/4279630839/214239/2b4c979d/583e6ab2N56d0f4d7.jpg\" alt=\"\" /> \r\n</p>\r\n<p>\r\n	<img src=\"https://img20.360buyimg.com/vc/jfs/t3058/81/4340587444/476967/e1523473/583e6ab4N1634dcae.jpg\" alt=\"\" /> \r\n</p>\r\n<p>\r\n	<img src=\"https://img20.360buyimg.com/vc/jfs/t3058/81/4340587444/476967/e1523473/583e6ab4N1634dcae.jpg\" alt=\"\" /> \r\n</p>\r\n<p>\r\n	<img src=\"https://img20.360buyimg.com/vc/jfs/t3412/337/1983826965/324468/4d1dfba2/583e7ac8N61e7480f.jpg\" alt=\"\" /> \r\n</p>\r\n<p>\r\n	<img src=\"https://img13.360buyimg.com/cms/jfs/t3448/244/2067261924/599337/eb4077bc/583ffe2aNf70c4ff4.jpg\" alt=\"\" /> \r\n</p>\r\n<p>\r\n	<img src=\"https://img20.360buyimg.com/vc/jfs/t3325/27/1903571558/115397/18139cf1/583e6ad4N7e34eea6.jpg\" alt=\"\" /> \r\n</p>\r\n<p>\r\n	<img src=\"https://img20.360buyimg.com/vc/jfs/t8230/100/1845600311/150686/c23ba61f/59c0ba02Nd4223b82.jpg\" alt=\"\" /> \r\n</p>', null, 'image/20180323/1521811711188_3.png', '74', '0', '0', null, null, null, null, '6', 'item', '0', 'n', '');
INSERT INTO `t_product` VALUES ('10278', '全国包邮 Seagate/希捷 ST500DM002 500G 台式机 硬盘 SATA3', '全国包邮 Seagate/希捷 ST500DM002 500G 台式机 硬盘 SATA3', '350.00', '309.00', '/image/20180323/1521810102008_3.png', '2014-03-07 09:14:44', 'wukong', 'admin', '2018-03-23 21:08:21', 'y', 'y', '12', '2', '<p>\r\n	<img src=\"https://img20.360buyimg.com/vc/jfs/t16456/241/1845732336/1519689/88ea4864/5a6690ecNa7fb9349.jpg\" alt=\"\" /> \r\n</p>\r\n<p>\r\n	<br />\r\n</p>\r\n<p>\r\n	\\\r\n</p>\r\n<p>\r\n	<br />\r\n</p>\r\n<p>\r\n	<br />\r\n</p>\r\n<p>\r\n	<br />\r\n<span id=\"__kindeditor_bookmark_start_5__\"></span> \r\n</p>\r\n<p>\r\n	<br />\r\n</p>', null, 'image/20180323/1521810102008_3.png', '79', '0', '12', null, null, null, null, null, 'item', '0', 'n', '');
INSERT INTO `t_product` VALUES ('10279', 'Samsung/三星 GALAXY Tab3 SM-T310 WIFI 16GB三星平板', 'Samsung/三星 GALAXY Tab3 SM-T310 WIFI 16GB三星平板电脑正品 ', '1800.00', '1545.13', '/image/20180322/1521726687776_3.png', '2014-03-07 09:20:13', 'wukong', null, '2014-03-20 17:54:21', 'y', 'y', '12', '2', '<p>\r\n	<img src=\"https://img20.360buyimg.com/vc/jfs/t3937/143/153243628/130774/7722db38/583e6ab1Nde4a861a.jpg\" alt=\"\" /> \r\n</p>\r\n<p>\r\n	<img src=\"https://img20.360buyimg.com/vc/jfs/t3271/109/4320983438/178616/62aacfb5/583e6ab2N156454f0.jpg\" alt=\"\" /> \r\n</p>\r\n<p>\r\n	<img src=\"https://img20.360buyimg.com/vc/jfs/t3274/94/4279630839/214239/2b4c979d/583e6ab2N56d0f4d7.jpg\" alt=\"\" />\r\n</p>\r\n<p>\r\n	<img src=\"https://img20.360buyimg.com/vc/jfs/t3058/81/4340587444/476967/e1523473/583e6ab4N1634dcae.jpg\" alt=\"\" />\r\n</p>\r\n<p>\r\n	<img src=\"https://img20.360buyimg.com/vc/jfs/t3058/81/4340587444/476967/e1523473/583e6ab4N1634dcae.jpg\" alt=\"\" />\r\n</p>\r\n<p>\r\n	<img src=\"https://img20.360buyimg.com/vc/jfs/t3412/337/1983826965/324468/4d1dfba2/583e7ac8N61e7480f.jpg\" alt=\"\" />\r\n</p>\r\n<p>\r\n	<img src=\"https://img13.360buyimg.com/cms/jfs/t3448/244/2067261924/599337/eb4077bc/583ffe2aNf70c4ff4.jpg\" alt=\"\" />\r\n</p>\r\n<p>\r\n	<img src=\"https://img20.360buyimg.com/vc/jfs/t3325/27/1903571558/115397/18139cf1/583e6ad4N7e34eea6.jpg\" alt=\"\" />\r\n</p>\r\n<p>\r\n	<img src=\"https://img20.360buyimg.com/vc/jfs/t8230/100/1845600311/150686/c23ba61f/59c0ba02Nd4223b82.jpg\" alt=\"\" />\r\n</p>', null, '/image/20180322/1521723506470_1.png,image/20180322/1521726687776_3.png', '80', '22', '11', null, null, null, null, null, 'item', '0', 'n', null);
INSERT INTO `t_product` VALUES ('10280', 'SAMSUNG/三星 GALAXY Note 10.1 2014 Edition SM-', 'SAMSUNG/三星 GALAXY Note 10.1 2014 Edition SM-P601 32GB平板', '4999.00', '4349.13', '/image/20180322/1521726687776_3.png', '2014-03-07 09:34:42', 'wukong', 'admin', '2018-03-22 13:58:11', 'y', 'y', '19', '2', '<p>\r\n	<img src=\"https://img20.360buyimg.com/vc/jfs/t3937/143/153243628/130774/7722db38/583e6ab1Nde4a861a.jpg\" alt=\"\" /> \r\n</p>\r\n<p>\r\n	<img src=\"https://img20.360buyimg.com/vc/jfs/t3271/109/4320983438/178616/62aacfb5/583e6ab2N156454f0.jpg\" alt=\"\" /> \r\n</p>\r\n<p>\r\n	<img src=\"https://img20.360buyimg.com/vc/jfs/t3274/94/4279630839/214239/2b4c979d/583e6ab2N56d0f4d7.jpg\" alt=\"\" /> \r\n</p>\r\n<p>\r\n	<img src=\"https://img20.360buyimg.com/vc/jfs/t3058/81/4340587444/476967/e1523473/583e6ab4N1634dcae.jpg\" alt=\"\" /> \r\n</p>\r\n<p>\r\n	<img src=\"https://img20.360buyimg.com/vc/jfs/t3058/81/4340587444/476967/e1523473/583e6ab4N1634dcae.jpg\" alt=\"\" /> \r\n</p>\r\n<p>\r\n	<img src=\"https://img20.360buyimg.com/vc/jfs/t3412/337/1983826965/324468/4d1dfba2/583e7ac8N61e7480f.jpg\" alt=\"\" /> \r\n</p>\r\n<p>\r\n	<img src=\"https://img13.360buyimg.com/cms/jfs/t3448/244/2067261924/599337/eb4077bc/583ffe2aNf70c4ff4.jpg\" alt=\"\" /> \r\n</p>\r\n<p>\r\n	<img src=\"https://img20.360buyimg.com/vc/jfs/t3325/27/1903571558/115397/18139cf1/583e6ad4N7e34eea6.jpg\" alt=\"\" /> \r\n</p>\r\n<p>\r\n	<img src=\"https://img20.360buyimg.com/vc/jfs/t8230/100/1845600311/150686/c23ba61f/59c0ba02Nd4223b82.jpg\" alt=\"\" /> \r\n</p>', null, '/image/20180322/1521723506470_1.png,image/20180322/1521726687776_3.png', '80', '22', '222', null, null, null, null, null, 'item', '0', 'n', '');
INSERT INTO `t_product` VALUES ('10282', 'Acer/宏碁 R7-571G-73538G75ass i7 GT750 2G独显 触控', 'Acer/宏碁 R7-571G-73538G75ass i7 GT750 2G独显 触控变形超级本 ', '9999.00', '8888.00', '/image/20180323/1521810573426_3.png', '2014-03-07 10:10:04', 'wukong', 'admin', '2018-03-23 21:10:42', 'y', 'y', '21', '2', '<p>\r\n	<img src=\"https://img14.360buyimg.com/cms/jfs/t3907/34/1415440056/152666/c1867f70/5875da5dN4f2c8fa2.jpg\" alt=\"\" /> \r\n</p>\r\n<p>\r\n	<img src=\"https://img14.360buyimg.com/cms/jfs/t16036/93/1635195524/133858/b1d953c1/5a56d0faN3303af11.jpg\" alt=\"\" /> \r\n</p>', null, 'image/20180323/1521810573426_3.png', '26', '0', '12', null, null, null, null, '6', 'item', '0', 'n', '');
INSERT INTO `t_product` VALUES ('10310', 'Apple 苹果 iPhone8 Plus 手机 深空色 全网通 64GB', '从\"为什么\"开始:乔布斯让Apple红遍世界的黄金圈法则', '5780.00', '5780.00', '/image/20180322/1521726687776_3.png', '2014-03-14 09:11:08', 'wukong', 'admin', '2018-03-23 21:12:56', 'y', 'n', '74', '2', '<p>\r\n	<img src=\"https://img10.360buyimg.com/imgzone/jfs/t7549/295/4518126821/284169/66e01e2b/5a026840Nefae706f.jpg\" alt=\"\" /> \r\n</p>\r\n<p>\r\n	<img src=\"https://img30.360buyimg.com/popWaterMark/jfs/t7387/82/2932693833/385973/730b68aa/59b8daf4N8517bd0f.jpg\" alt=\"\" /> \r\n</p>\r\n<p>\r\n	<img src=\"https://img30.360buyimg.com/popWaterMark/jfs/t8947/276/1384688896/305062/5776ffa1/59b8daf4N9652e8cf.jpg\" alt=\"\" /> \r\n</p>\r\n<p>\r\n	<img src=\"https://img10.360buyimg.com/imgzone/jfs/t8656/137/2055632701/59411/b2c81b28/59c37f36N9c415f6d.jpg\" alt=\"\" /> \r\n</p>\r\n<p>\r\n	<img src=\"https://img10.360buyimg.com/imgzone/jfs/t13486/78/403201903/616149/d6b31d7b/5a0aae64N84adf882.jpg\" alt=\"\" /> \r\n</p>\r\n<p>\r\n	<br />\r\n</p>', null, '/image/20180322/1521723506470_1.png,image/20180322/1521726687776_3.png', '90', '55', '55', null, 'Apple 苹果 iPhone8 Plus 手机 深空色 全网通 64GB', 'Apple 苹果 iPhone8 Plus 手机 深空色 全网通 64GB', 'Apple 苹果 iPhone8 Plus 手机 深空色 全网通 64GB', '2', 'item', '0', 'n', '1');

-- ----------------------------
-- Table structure for `t_questionnaire`
-- ----------------------------
DROP TABLE IF EXISTS `t_questionnaire`;
CREATE TABLE `t_questionnaire` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `title2` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  `updatedate` datetime DEFAULT NULL,
  `createAccount` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `updateAccount` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` varchar(2) COLLATE utf8_unicode_ci DEFAULT NULL,
  `showStartDate` date DEFAULT NULL,
  `showEndDate` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_questionnaire
-- ----------------------------
INSERT INTO `t_questionnaire` VALUES ('1', '问卷调查1', '321312的是否是否是方式', '2011-11-11 00:00:00', '2014-02-18 19:00:25', '1', 'admin', 'y', '2011-11-11', '2011-11-18');
INSERT INTO `t_questionnaire` VALUES ('2', '3232321', '321312', null, '2014-02-15 09:54:32', null, null, 'y', '2014-02-06', '2014-02-13');
INSERT INTO `t_questionnaire` VALUES ('3', '22', '3333', null, null, null, null, 'n', null, null);
INSERT INTO `t_questionnaire` VALUES ('4', '4234', '42435555', '2014-02-10 10:33:32', '2014-02-10 10:34:21', 'admin', 'admin', 'n', '2014-01-31', null);
INSERT INTO `t_questionnaire` VALUES ('5', '5555', '555555', '2014-02-10 12:01:12', null, 'select', null, 'n', null, null);

-- ----------------------------
-- Table structure for `t_questionnaireitem`
-- ----------------------------
DROP TABLE IF EXISTS `t_questionnaireitem`;
CREATE TABLE `t_questionnaireitem` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `qid` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `subject` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `option1` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `type` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `order1` int(11) DEFAULT '0',
  `display` varchar(15) COLLATE utf8_unicode_ci DEFAULT 'lines',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=141 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_questionnaireitem
-- ----------------------------
INSERT INTO `t_questionnaireitem` VALUES ('13', '2', '4234', '423', 'radio', '0', 'lines');
INSERT INTO `t_questionnaireitem` VALUES ('14', '2', '4234', '423', 'radio', '0', 'lines');
INSERT INTO `t_questionnaireitem` VALUES ('15', '2', '4234', '423', 'radio', '0', 'lines');
INSERT INTO `t_questionnaireitem` VALUES ('16', '2', '4234', '23423423', 'radio', '0', 'lines');
INSERT INTO `t_questionnaireitem` VALUES ('19', '2', 'fdgfd', 'gfdgdf', 'radio', '0', 'lines');
INSERT INTO `t_questionnaireitem` VALUES ('20', '2', 'fdgfd', 'gfdgfgfgf', 'radio', '0', 'lines');
INSERT INTO `t_questionnaireitem` VALUES ('66', '1', '456', '64646', 'radio', '0', 'lines');
INSERT INTO `t_questionnaireitem` VALUES ('67', '1', '456', '46', 'radio', '0', 'lines');
INSERT INTO `t_questionnaireitem` VALUES ('114', '1', '海阔天空是哪位歌星唱的？', '刘德华', 'radio', '0', 'inline');
INSERT INTO `t_questionnaireitem` VALUES ('115', '1', '海阔天空是哪位歌星唱的？', '张学友', 'radio', '0', 'inline');
INSERT INTO `t_questionnaireitem` VALUES ('116', '1', '海阔天空是哪位歌星唱的？', 'beyang', 'radio', '0', 'inline');
INSERT INTO `t_questionnaireitem` VALUES ('117', '1', '海阔天空是哪位歌星唱的？', '张卫健', 'radio', '0', 'inline');
INSERT INTO `t_questionnaireitem` VALUES ('118', '1', '新中国是哪一年成立的？', '1994', 'checkbox', '0', 'inline');
INSERT INTO `t_questionnaireitem` VALUES ('119', '1', '新中国是哪一年成立的？', '2008', 'checkbox', '0', 'inline');
INSERT INTO `t_questionnaireitem` VALUES ('120', '1', '新中国是哪一年成立的？', '2014', 'checkbox', '0', 'inline');
INSERT INTO `t_questionnaireitem` VALUES ('121', '1', '新中国是哪一年成立的？', '2002', 'checkbox', '0', 'inline');
INSERT INTO `t_questionnaireitem` VALUES ('122', '1', '2222222222222222', '1111', 'radio', '0', 'lines');
INSERT INTO `t_questionnaireitem` VALUES ('123', '1', '2222222222222222', '撒大大', 'radio', '0', 'lines');
INSERT INTO `t_questionnaireitem` VALUES ('124', '1', '2222222222222222', 'dasdasdada', 'radio', '0', 'lines');
INSERT INTO `t_questionnaireitem` VALUES ('126', '1', '2112121', '-', 'text', '10', 'inline');
INSERT INTO `t_questionnaireitem` VALUES ('134', '1', '世界经济危机爆发于哪一年？', '哈哈哈哈哈哈哈哈哈哈', 'radio', '1', 'inline');
INSERT INTO `t_questionnaireitem` VALUES ('135', '1', '世界经济危机爆发于哪一年？', '嘿嘿俄方的十分的十分上的发生', 'radio', '1', 'inline');
INSERT INTO `t_questionnaireitem` VALUES ('136', '1', '世界经济危机爆发于哪一年？', '范德萨发放松放松的方式费', 'radio', '1', 'inline');
INSERT INTO `t_questionnaireitem` VALUES ('137', '1', '世界经济危机爆发于哪一年？', '的撒的发的方式53453是非得失方式的方式的费法', 'radio', '1', 'inline');
INSERT INTO `t_questionnaireitem` VALUES ('138', '1', '世界经济危机爆发于哪一年？', '43534', 'radio', '1', 'inline');
INSERT INTO `t_questionnaireitem` VALUES ('139', '1', '世界经济危机爆发于哪一年？', '5555555', 'radio', '1', 'inline');
INSERT INTO `t_questionnaireitem` VALUES ('140', '1', '世界经济危机爆发于哪一年？', '1111', 'radio', '1', 'inline');

-- ----------------------------
-- Table structure for `t_questionnaireresult`
-- ----------------------------
DROP TABLE IF EXISTS `t_questionnaireresult`;
CREATE TABLE `t_questionnaireresult` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `qid` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `account` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `qItemID` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `text` varchar(145) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_questionnaireresult
-- ----------------------------

-- ----------------------------
-- Table structure for `t_reply`
-- ----------------------------
DROP TABLE IF EXISTS `t_reply`;
CREATE TABLE `t_reply` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `commentID` int(11) NOT NULL,
  `content` varchar(140) NOT NULL,
  `createdate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_reply
-- ----------------------------

-- ----------------------------
-- Table structure for `t_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(45) NOT NULL,
  `role_desc` varchar(45) DEFAULT NULL,
  `role_dbPrivilege` varchar(45) DEFAULT NULL,
  `status` varchar(2) DEFAULT 'y',
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_name_UNIQUE` (`role_name`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('1', '超级管理员', '系统角色，不可删除', 'select,insert,update,delete', 'y');
INSERT INTO `t_role` VALUES ('2', '商品管理员', '维护商品信息', 'select,insert', 'y');
INSERT INTO `t_role` VALUES ('6', '查看权限', '查看权限-测试', 'select', 'y');
INSERT INTO `t_role` VALUES ('7', '测试', '测试', 'select,insert,update,delete', 'y');

-- ----------------------------
-- Table structure for `t_sessioncount`
-- ----------------------------
DROP TABLE IF EXISTS `t_sessioncount`;
CREATE TABLE `t_sessioncount` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `address` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `starttime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1105 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_sessioncount
-- ----------------------------
INSERT INTO `t_sessioncount` VALUES ('1', '127.0.0.1', null, '2013-08-06 23:46:36');
INSERT INTO `t_sessioncount` VALUES ('2', '127.0.0.1', null, '2013-08-06 23:49:02');
INSERT INTO `t_sessioncount` VALUES ('3', '127.0.0.1', null, '2013-08-06 23:49:29');
INSERT INTO `t_sessioncount` VALUES ('4', '127.0.0.1', null, '2013-08-06 23:49:35');
INSERT INTO `t_sessioncount` VALUES ('5', '127.0.0.1', null, '2013-08-06 23:50:22');
INSERT INTO `t_sessioncount` VALUES ('6', '127.0.0.1', null, '2013-08-10 23:05:47');
INSERT INTO `t_sessioncount` VALUES ('7', '127.0.0.1', null, '2013-08-10 23:36:58');
INSERT INTO `t_sessioncount` VALUES ('8', '127.0.0.1', null, '2013-08-11 10:02:35');
INSERT INTO `t_sessioncount` VALUES ('9', '127.0.0.1', null, '2013-08-12 11:17:30');
INSERT INTO `t_sessioncount` VALUES ('10', '127.0.0.1', null, '2013-08-12 11:18:13');
INSERT INTO `t_sessioncount` VALUES ('11', '127.0.0.1', null, '2013-08-12 11:18:46');
INSERT INTO `t_sessioncount` VALUES ('12', '127.0.0.1', null, '2013-08-12 11:19:33');
INSERT INTO `t_sessioncount` VALUES ('13', '127.0.0.1', null, '2013-08-12 11:20:16');
INSERT INTO `t_sessioncount` VALUES ('14', '127.0.0.1', null, '2013-07-18 11:35:39');
INSERT INTO `t_sessioncount` VALUES ('15', '127.0.0.1', null, '2013-07-18 11:41:45');
INSERT INTO `t_sessioncount` VALUES ('16', '127.0.0.1', null, '2013-07-18 11:46:17');
INSERT INTO `t_sessioncount` VALUES ('17', '127.0.0.1', null, '2013-07-18 11:47:19');
INSERT INTO `t_sessioncount` VALUES ('18', '127.0.0.1', null, '2013-07-18 12:02:13');
INSERT INTO `t_sessioncount` VALUES ('19', '127.0.0.1', null, '2013-07-18 12:05:28');
INSERT INTO `t_sessioncount` VALUES ('20', '127.0.0.1', null, '2013-07-18 12:06:43');
INSERT INTO `t_sessioncount` VALUES ('21', '127.0.0.1', null, '2013-08-14 21:23:49');
INSERT INTO `t_sessioncount` VALUES ('22', '127.0.0.1', null, '2013-08-18 15:16:55');
INSERT INTO `t_sessioncount` VALUES ('23', '127.0.0.1', null, '2013-08-24 12:21:56');
INSERT INTO `t_sessioncount` VALUES ('24', '127.0.0.1', null, '2013-08-25 20:28:49');
INSERT INTO `t_sessioncount` VALUES ('25', '127.0.0.1', null, '2013-08-25 21:00:24');
INSERT INTO `t_sessioncount` VALUES ('26', '127.0.0.1', null, '2013-08-25 21:02:10');
INSERT INTO `t_sessioncount` VALUES ('27', '127.0.0.1', null, '2013-08-25 21:19:57');
INSERT INTO `t_sessioncount` VALUES ('28', '127.0.0.1', null, '2013-08-25 21:22:52');
INSERT INTO `t_sessioncount` VALUES ('29', '127.0.0.1', null, '2013-08-25 21:27:46');
INSERT INTO `t_sessioncount` VALUES ('30', '0:0:0:0:0:0:0:1', null, '2013-08-25 21:38:28');
INSERT INTO `t_sessioncount` VALUES ('31', '127.0.0.1', null, '2013-08-25 21:43:30');
INSERT INTO `t_sessioncount` VALUES ('32', '127.0.0.1', null, '2013-08-25 21:48:08');
INSERT INTO `t_sessioncount` VALUES ('33', '127.0.0.1', null, '2013-08-25 21:52:02');
INSERT INTO `t_sessioncount` VALUES ('34', '127.0.0.1', null, '2013-08-25 22:08:16');
INSERT INTO `t_sessioncount` VALUES ('35', '127.0.0.1', null, '2013-08-25 22:13:12');
INSERT INTO `t_sessioncount` VALUES ('36', '127.0.0.1', null, '2013-08-25 22:15:38');
INSERT INTO `t_sessioncount` VALUES ('37', '127.0.0.1', null, '2013-08-25 22:25:40');
INSERT INTO `t_sessioncount` VALUES ('38', '127.0.0.1', null, '2013-08-25 22:30:05');
INSERT INTO `t_sessioncount` VALUES ('39', '127.0.0.1', null, '2013-08-25 22:35:03');
INSERT INTO `t_sessioncount` VALUES ('40', '127.0.0.1', null, '2013-08-25 23:05:12');
INSERT INTO `t_sessioncount` VALUES ('41', '127.0.0.1', null, '2013-08-25 23:29:09');
INSERT INTO `t_sessioncount` VALUES ('42', '127.0.0.1', null, '2013-08-25 23:32:29');
INSERT INTO `t_sessioncount` VALUES ('43', '127.0.0.1', null, '2013-08-25 23:35:36');
INSERT INTO `t_sessioncount` VALUES ('44', '127.0.0.1', null, '2013-08-25 23:40:34');
INSERT INTO `t_sessioncount` VALUES ('45', '127.0.0.1', null, '2013-08-25 23:47:33');
INSERT INTO `t_sessioncount` VALUES ('46', '127.0.0.1', null, '2013-08-26 20:26:55');
INSERT INTO `t_sessioncount` VALUES ('47', '127.0.0.1', null, '2013-08-26 20:57:14');
INSERT INTO `t_sessioncount` VALUES ('48', '127.0.0.1', null, '2013-08-26 21:15:41');
INSERT INTO `t_sessioncount` VALUES ('49', '127.0.0.1', null, '2013-08-26 21:54:42');
INSERT INTO `t_sessioncount` VALUES ('50', '127.0.0.1', null, '2013-08-26 21:55:48');
INSERT INTO `t_sessioncount` VALUES ('51', '127.0.0.1', null, '2013-08-29 22:18:34');
INSERT INTO `t_sessioncount` VALUES ('52', '127.0.0.1', null, '2013-08-29 23:36:34');
INSERT INTO `t_sessioncount` VALUES ('53', '127.0.0.1', null, '2013-08-29 23:45:05');
INSERT INTO `t_sessioncount` VALUES ('54', '127.0.0.1', null, '2013-08-29 23:49:10');
INSERT INTO `t_sessioncount` VALUES ('55', '127.0.0.1', null, '2013-08-29 23:54:46');
INSERT INTO `t_sessioncount` VALUES ('56', '127.0.0.1', null, '2013-08-29 23:58:35');
INSERT INTO `t_sessioncount` VALUES ('57', '127.0.0.1', null, '2013-08-30 00:14:32');
INSERT INTO `t_sessioncount` VALUES ('58', '127.0.0.1', null, '2013-08-30 00:17:28');
INSERT INTO `t_sessioncount` VALUES ('59', '127.0.0.1', null, '2013-08-30 00:27:26');
INSERT INTO `t_sessioncount` VALUES ('60', '127.0.0.1', null, '2013-08-30 00:33:35');
INSERT INTO `t_sessioncount` VALUES ('61', '127.0.0.1', null, '2013-08-30 00:35:44');
INSERT INTO `t_sessioncount` VALUES ('62', '127.0.0.1', null, '2013-08-30 20:55:45');
INSERT INTO `t_sessioncount` VALUES ('63', '127.0.0.1', null, '2013-08-30 21:39:33');
INSERT INTO `t_sessioncount` VALUES ('64', '127.0.0.1', null, '2013-08-30 21:43:23');
INSERT INTO `t_sessioncount` VALUES ('65', '127.0.0.1', null, '2013-08-30 21:44:00');
INSERT INTO `t_sessioncount` VALUES ('66', '127.0.0.1', null, '2013-08-30 21:46:05');
INSERT INTO `t_sessioncount` VALUES ('67', '127.0.0.1', null, '2013-08-30 21:54:26');
INSERT INTO `t_sessioncount` VALUES ('68', '127.0.0.1', null, '2013-08-30 22:01:47');
INSERT INTO `t_sessioncount` VALUES ('69', '127.0.0.1', null, '2013-08-30 22:03:52');
INSERT INTO `t_sessioncount` VALUES ('70', '127.0.0.1', null, '2013-08-30 22:04:57');
INSERT INTO `t_sessioncount` VALUES ('71', '127.0.0.1', null, '2013-08-30 22:10:01');
INSERT INTO `t_sessioncount` VALUES ('72', '127.0.0.1', null, '2013-08-30 22:11:51');
INSERT INTO `t_sessioncount` VALUES ('73', '127.0.0.1', null, '2013-08-30 22:12:59');
INSERT INTO `t_sessioncount` VALUES ('74', '127.0.0.1', null, '2013-08-30 22:18:55');
INSERT INTO `t_sessioncount` VALUES ('75', '127.0.0.1', null, '2013-08-30 22:22:43');
INSERT INTO `t_sessioncount` VALUES ('76', '127.0.0.1', null, '2013-08-30 22:27:38');
INSERT INTO `t_sessioncount` VALUES ('77', '127.0.0.1', null, '2013-08-30 22:30:15');
INSERT INTO `t_sessioncount` VALUES ('78', '127.0.0.1', null, '2013-08-30 22:47:40');
INSERT INTO `t_sessioncount` VALUES ('79', '127.0.0.1', null, '2013-08-31 07:19:06');
INSERT INTO `t_sessioncount` VALUES ('80', '127.0.0.1', null, '2013-08-31 19:29:27');
INSERT INTO `t_sessioncount` VALUES ('81', '127.0.0.1', null, '2013-09-08 19:21:47');
INSERT INTO `t_sessioncount` VALUES ('82', '127.0.0.1', null, '2013-09-08 19:48:42');
INSERT INTO `t_sessioncount` VALUES ('83', '127.0.0.1', null, '2013-09-08 20:36:39');
INSERT INTO `t_sessioncount` VALUES ('84', '127.0.0.1', null, '2013-09-08 20:58:34');
INSERT INTO `t_sessioncount` VALUES ('85', '127.0.0.1', null, '2013-09-08 21:31:17');
INSERT INTO `t_sessioncount` VALUES ('86', '127.0.0.1', null, '2013-09-08 21:33:25');
INSERT INTO `t_sessioncount` VALUES ('87', '127.0.0.1', null, '2013-09-08 21:37:37');
INSERT INTO `t_sessioncount` VALUES ('88', '127.0.0.1', null, '2013-09-08 22:02:59');
INSERT INTO `t_sessioncount` VALUES ('89', '127.0.0.1', null, '2013-09-08 22:17:10');
INSERT INTO `t_sessioncount` VALUES ('90', '127.0.0.1', null, '2013-09-08 22:35:52');
INSERT INTO `t_sessioncount` VALUES ('91', '127.0.0.1', null, '2013-09-08 22:35:53');
INSERT INTO `t_sessioncount` VALUES ('92', '127.0.0.1', null, '2013-09-08 22:57:22');
INSERT INTO `t_sessioncount` VALUES ('93', '127.0.0.1', null, '2013-09-08 23:14:57');
INSERT INTO `t_sessioncount` VALUES ('94', '127.0.0.1', null, '2013-09-08 23:24:22');
INSERT INTO `t_sessioncount` VALUES ('95', '127.0.0.1', null, '2013-09-08 23:31:40');
INSERT INTO `t_sessioncount` VALUES ('96', '127.0.0.1', null, '2013-09-08 23:40:37');
INSERT INTO `t_sessioncount` VALUES ('97', '127.0.0.1', null, '2013-09-09 07:01:29');
INSERT INTO `t_sessioncount` VALUES ('98', '127.0.0.1', null, '2013-09-09 07:01:34');
INSERT INTO `t_sessioncount` VALUES ('99', '127.0.0.1', null, '2013-09-09 07:29:09');
INSERT INTO `t_sessioncount` VALUES ('100', '127.0.0.1', null, '2013-09-09 07:49:26');
INSERT INTO `t_sessioncount` VALUES ('101', '127.0.0.1', null, '2013-09-09 07:51:23');
INSERT INTO `t_sessioncount` VALUES ('102', '127.0.0.1', null, '2013-09-10 06:42:59');
INSERT INTO `t_sessioncount` VALUES ('103', '127.0.0.1', null, '2013-09-10 06:43:04');
INSERT INTO `t_sessioncount` VALUES ('104', '127.0.0.1', null, '2013-09-10 19:41:27');
INSERT INTO `t_sessioncount` VALUES ('105', '127.0.0.1', null, '2013-09-10 19:48:13');
INSERT INTO `t_sessioncount` VALUES ('106', '127.0.0.1', null, '2013-09-10 20:26:10');
INSERT INTO `t_sessioncount` VALUES ('107', '127.0.0.1', null, '2013-09-10 21:04:43');
INSERT INTO `t_sessioncount` VALUES ('108', '127.0.0.1', null, '2013-09-10 21:29:14');
INSERT INTO `t_sessioncount` VALUES ('109', '127.0.0.1', null, '2013-09-10 21:30:35');
INSERT INTO `t_sessioncount` VALUES ('110', '127.0.0.1', null, '2013-09-10 21:33:25');
INSERT INTO `t_sessioncount` VALUES ('111', '127.0.0.1', null, '2013-09-10 21:34:56');
INSERT INTO `t_sessioncount` VALUES ('112', '127.0.0.1', null, '2013-09-10 21:39:15');
INSERT INTO `t_sessioncount` VALUES ('113', '127.0.0.1', null, '2013-09-10 21:48:41');
INSERT INTO `t_sessioncount` VALUES ('114', '127.0.0.1', null, '2013-09-10 22:09:36');
INSERT INTO `t_sessioncount` VALUES ('115', '127.0.0.1', null, '2013-09-10 22:17:34');
INSERT INTO `t_sessioncount` VALUES ('116', '127.0.0.1', null, '2013-09-10 22:21:47');
INSERT INTO `t_sessioncount` VALUES ('117', '127.0.0.1', null, '2013-09-10 22:23:49');
INSERT INTO `t_sessioncount` VALUES ('118', '127.0.0.1', null, '2013-09-10 22:30:26');
INSERT INTO `t_sessioncount` VALUES ('119', '127.0.0.1', null, '2013-09-10 23:14:35');
INSERT INTO `t_sessioncount` VALUES ('120', '127.0.0.1', null, '2013-09-10 23:17:07');
INSERT INTO `t_sessioncount` VALUES ('121', '127.0.0.1', null, '2013-09-10 23:17:43');
INSERT INTO `t_sessioncount` VALUES ('122', '127.0.0.1', null, '2013-09-10 23:19:24');
INSERT INTO `t_sessioncount` VALUES ('123', '127.0.0.1', null, '2013-09-10 23:21:34');
INSERT INTO `t_sessioncount` VALUES ('124', '127.0.0.1', null, '2013-09-10 23:23:11');
INSERT INTO `t_sessioncount` VALUES ('125', '127.0.0.1', null, '2013-09-10 23:23:20');
INSERT INTO `t_sessioncount` VALUES ('126', '127.0.0.1', null, '2013-09-10 23:24:09');
INSERT INTO `t_sessioncount` VALUES ('127', '127.0.0.1', null, '2013-09-10 23:30:17');
INSERT INTO `t_sessioncount` VALUES ('128', '127.0.0.1', null, '2013-09-10 23:33:57');
INSERT INTO `t_sessioncount` VALUES ('129', '127.0.0.1', null, '2013-09-10 23:34:32');
INSERT INTO `t_sessioncount` VALUES ('130', '127.0.0.1', null, '2013-09-10 23:54:58');
INSERT INTO `t_sessioncount` VALUES ('131', '127.0.0.1', null, '2013-09-10 23:57:13');
INSERT INTO `t_sessioncount` VALUES ('132', '127.0.0.1', null, '2013-09-11 07:21:40');
INSERT INTO `t_sessioncount` VALUES ('133', '127.0.0.1', null, '2013-09-11 07:28:22');
INSERT INTO `t_sessioncount` VALUES ('134', '127.0.0.1', null, '2013-09-11 07:29:18');
INSERT INTO `t_sessioncount` VALUES ('135', '127.0.0.1', null, '2013-09-11 07:29:31');
INSERT INTO `t_sessioncount` VALUES ('136', '127.0.0.1', null, '2013-09-11 07:30:22');
INSERT INTO `t_sessioncount` VALUES ('137', '127.0.0.1', null, '2013-09-11 07:33:14');
INSERT INTO `t_sessioncount` VALUES ('138', '127.0.0.1', null, '2013-09-11 07:43:50');
INSERT INTO `t_sessioncount` VALUES ('139', '127.0.0.1', null, '2013-09-11 07:44:35');
INSERT INTO `t_sessioncount` VALUES ('140', '127.0.0.1', null, '2013-09-11 07:48:49');
INSERT INTO `t_sessioncount` VALUES ('141', '127.0.0.1', null, '2013-09-11 07:54:59');
INSERT INTO `t_sessioncount` VALUES ('142', '127.0.0.1', null, '2013-09-11 08:00:01');
INSERT INTO `t_sessioncount` VALUES ('143', '127.0.0.1', null, '2013-09-11 08:00:48');
INSERT INTO `t_sessioncount` VALUES ('144', '127.0.0.1', null, '2013-09-11 08:02:24');
INSERT INTO `t_sessioncount` VALUES ('145', '127.0.0.1', null, '2013-09-11 20:54:57');
INSERT INTO `t_sessioncount` VALUES ('146', '127.0.0.1', null, '2013-09-11 21:24:53');
INSERT INTO `t_sessioncount` VALUES ('147', '127.0.0.1', null, '2013-09-11 21:39:06');
INSERT INTO `t_sessioncount` VALUES ('148', '127.0.0.1', null, '2013-09-11 21:40:20');
INSERT INTO `t_sessioncount` VALUES ('149', '127.0.0.1', null, '2013-09-11 22:32:31');
INSERT INTO `t_sessioncount` VALUES ('150', '127.0.0.1', null, '2013-09-11 22:53:18');
INSERT INTO `t_sessioncount` VALUES ('151', '127.0.0.1', null, '2013-09-11 23:00:52');
INSERT INTO `t_sessioncount` VALUES ('152', '127.0.0.1', null, '2013-09-11 23:12:07');
INSERT INTO `t_sessioncount` VALUES ('153', '127.0.0.1', null, '2013-09-13 22:38:15');
INSERT INTO `t_sessioncount` VALUES ('154', '127.0.0.1', null, '2013-09-13 22:44:14');
INSERT INTO `t_sessioncount` VALUES ('155', '127.0.0.1', null, '2013-09-13 23:25:54');
INSERT INTO `t_sessioncount` VALUES ('156', '127.0.0.1', null, '2013-09-13 23:42:09');
INSERT INTO `t_sessioncount` VALUES ('157', '127.0.0.1', null, '2013-09-13 23:44:30');
INSERT INTO `t_sessioncount` VALUES ('158', '127.0.0.1', null, '2013-09-13 23:46:39');
INSERT INTO `t_sessioncount` VALUES ('159', '127.0.0.1', null, '2013-09-13 23:53:45');
INSERT INTO `t_sessioncount` VALUES ('160', '127.0.0.1', null, '2013-09-13 23:59:54');
INSERT INTO `t_sessioncount` VALUES ('161', '127.0.0.1', null, '2013-09-14 07:44:03');
INSERT INTO `t_sessioncount` VALUES ('162', '127.0.0.1', null, '2013-09-18 22:05:23');
INSERT INTO `t_sessioncount` VALUES ('163', '127.0.0.1', null, '2013-09-24 22:43:17');
INSERT INTO `t_sessioncount` VALUES ('164', '127.0.0.1', null, '2013-09-24 22:59:30');
INSERT INTO `t_sessioncount` VALUES ('165', '127.0.0.1', null, '2013-09-24 22:59:30');
INSERT INTO `t_sessioncount` VALUES ('166', '127.0.0.1', null, '2013-09-24 23:12:15');
INSERT INTO `t_sessioncount` VALUES ('167', '127.0.0.1', null, '2013-09-24 23:12:26');
INSERT INTO `t_sessioncount` VALUES ('168', '127.0.0.1', null, '2013-09-24 23:41:54');
INSERT INTO `t_sessioncount` VALUES ('169', '127.0.0.1', null, '2013-09-24 23:41:59');
INSERT INTO `t_sessioncount` VALUES ('170', '127.0.0.1', null, '2013-09-24 23:43:52');
INSERT INTO `t_sessioncount` VALUES ('171', '127.0.0.1', null, '2013-09-25 00:36:14');
INSERT INTO `t_sessioncount` VALUES ('172', '127.0.0.1', null, '2013-09-25 07:05:52');
INSERT INTO `t_sessioncount` VALUES ('173', '127.0.0.1', null, '2013-09-25 07:16:29');
INSERT INTO `t_sessioncount` VALUES ('174', '127.0.0.1', null, '2013-09-25 07:17:20');
INSERT INTO `t_sessioncount` VALUES ('175', '127.0.0.1', null, '2013-09-25 07:24:43');
INSERT INTO `t_sessioncount` VALUES ('176', '127.0.0.1', null, '2013-09-25 07:26:01');
INSERT INTO `t_sessioncount` VALUES ('177', '127.0.0.1', null, '2013-09-25 07:26:01');
INSERT INTO `t_sessioncount` VALUES ('178', '127.0.0.1', null, '2013-09-25 07:26:01');
INSERT INTO `t_sessioncount` VALUES ('179', '127.0.0.1', null, '2013-09-25 07:26:01');
INSERT INTO `t_sessioncount` VALUES ('180', '127.0.0.1', null, '2013-09-25 07:26:01');
INSERT INTO `t_sessioncount` VALUES ('181', '127.0.0.1', null, '2013-09-25 07:26:01');
INSERT INTO `t_sessioncount` VALUES ('182', '127.0.0.1', null, '2013-09-25 07:26:02');
INSERT INTO `t_sessioncount` VALUES ('183', '127.0.0.1', null, '2013-09-25 07:26:02');
INSERT INTO `t_sessioncount` VALUES ('184', '127.0.0.1', null, '2013-09-25 07:26:02');
INSERT INTO `t_sessioncount` VALUES ('185', '127.0.0.1', null, '2013-09-25 07:26:02');
INSERT INTO `t_sessioncount` VALUES ('186', '127.0.0.1', null, '2013-09-25 07:39:08');
INSERT INTO `t_sessioncount` VALUES ('187', '127.0.0.1', null, '2013-09-25 21:54:20');
INSERT INTO `t_sessioncount` VALUES ('188', '127.0.0.1', null, '2013-09-25 22:12:12');
INSERT INTO `t_sessioncount` VALUES ('189', '127.0.0.1', null, '2013-09-25 22:12:17');
INSERT INTO `t_sessioncount` VALUES ('190', '127.0.0.1', null, '2013-09-25 22:18:47');
INSERT INTO `t_sessioncount` VALUES ('191', '127.0.0.1', null, '2013-09-25 22:42:01');
INSERT INTO `t_sessioncount` VALUES ('192', '127.0.0.1', null, '2013-09-25 22:52:14');
INSERT INTO `t_sessioncount` VALUES ('193', '127.0.0.1', null, '2013-09-25 23:49:18');
INSERT INTO `t_sessioncount` VALUES ('194', '127.0.0.1', null, '2013-09-25 23:55:38');
INSERT INTO `t_sessioncount` VALUES ('195', '127.0.0.1', null, '2013-09-26 00:02:12');
INSERT INTO `t_sessioncount` VALUES ('196', '127.0.0.1', null, '2013-09-26 00:18:10');
INSERT INTO `t_sessioncount` VALUES ('197', '127.0.0.1', null, '2013-09-26 00:37:32');
INSERT INTO `t_sessioncount` VALUES ('198', '127.0.0.1', null, '2013-09-26 00:39:28');
INSERT INTO `t_sessioncount` VALUES ('199', '127.0.0.1', null, '2013-09-26 00:44:26');
INSERT INTO `t_sessioncount` VALUES ('200', '127.0.0.1', null, '2013-09-26 00:49:10');
INSERT INTO `t_sessioncount` VALUES ('201', '127.0.0.1', null, '2013-09-26 21:13:09');
INSERT INTO `t_sessioncount` VALUES ('202', '127.0.0.1', null, '2013-09-26 21:31:06');
INSERT INTO `t_sessioncount` VALUES ('203', '127.0.0.1', null, '2013-09-26 21:55:24');
INSERT INTO `t_sessioncount` VALUES ('204', '127.0.0.1', null, '2013-09-26 21:55:28');
INSERT INTO `t_sessioncount` VALUES ('205', '127.0.0.1', null, '2013-09-26 22:04:25');
INSERT INTO `t_sessioncount` VALUES ('206', '127.0.0.1', null, '2013-09-26 22:34:41');
INSERT INTO `t_sessioncount` VALUES ('207', '127.0.0.1', null, '2013-09-26 22:34:46');
INSERT INTO `t_sessioncount` VALUES ('208', '127.0.0.1', null, '2013-09-26 23:07:44');
INSERT INTO `t_sessioncount` VALUES ('209', '127.0.0.1', null, '2013-09-26 23:13:19');
INSERT INTO `t_sessioncount` VALUES ('210', '127.0.0.1', null, '2013-09-26 23:17:41');
INSERT INTO `t_sessioncount` VALUES ('211', '127.0.0.1', null, '2013-09-26 23:22:56');
INSERT INTO `t_sessioncount` VALUES ('212', '127.0.0.1', null, '2013-09-26 23:30:36');
INSERT INTO `t_sessioncount` VALUES ('213', '127.0.0.1', null, '2013-09-26 23:30:59');
INSERT INTO `t_sessioncount` VALUES ('214', '127.0.0.1', null, '2013-09-26 23:47:11');
INSERT INTO `t_sessioncount` VALUES ('215', '127.0.0.1', null, '2013-09-26 23:50:10');
INSERT INTO `t_sessioncount` VALUES ('216', '127.0.0.1', null, '2013-09-26 23:57:46');
INSERT INTO `t_sessioncount` VALUES ('217', '127.0.0.1', null, '2013-09-26 23:59:52');
INSERT INTO `t_sessioncount` VALUES ('218', '127.0.0.1', null, '2013-09-27 00:03:17');
INSERT INTO `t_sessioncount` VALUES ('219', '127.0.0.1', null, '2013-09-27 00:04:06');
INSERT INTO `t_sessioncount` VALUES ('220', '127.0.0.1', null, '2013-09-27 00:15:32');
INSERT INTO `t_sessioncount` VALUES ('221', '127.0.0.1', null, '2013-09-27 00:49:51');
INSERT INTO `t_sessioncount` VALUES ('222', '127.0.0.1', null, '2013-09-27 00:49:55');
INSERT INTO `t_sessioncount` VALUES ('223', '127.0.0.1', null, '2013-09-27 06:30:25');
INSERT INTO `t_sessioncount` VALUES ('224', '127.0.0.1', null, '2013-09-27 06:30:35');
INSERT INTO `t_sessioncount` VALUES ('225', '127.0.0.1', null, '2013-09-27 22:23:33');
INSERT INTO `t_sessioncount` VALUES ('226', '127.0.0.1', null, '2013-09-27 23:21:17');
INSERT INTO `t_sessioncount` VALUES ('227', '127.0.0.1', null, '2013-09-28 00:28:47');
INSERT INTO `t_sessioncount` VALUES ('228', '127.0.0.1', null, '2013-09-28 00:35:00');
INSERT INTO `t_sessioncount` VALUES ('229', '127.0.0.1', null, '2013-09-28 00:43:52');
INSERT INTO `t_sessioncount` VALUES ('230', '127.0.0.1', null, '2013-09-28 00:46:22');
INSERT INTO `t_sessioncount` VALUES ('231', '127.0.0.1', null, '2013-09-28 00:46:29');
INSERT INTO `t_sessioncount` VALUES ('232', '127.0.0.1', null, '2013-09-28 09:13:14');
INSERT INTO `t_sessioncount` VALUES ('233', '127.0.0.1', null, '2013-09-28 10:45:09');
INSERT INTO `t_sessioncount` VALUES ('234', '127.0.0.1', null, '2013-09-28 10:47:14');
INSERT INTO `t_sessioncount` VALUES ('235', '127.0.0.1', null, '2013-09-28 10:53:06');
INSERT INTO `t_sessioncount` VALUES ('236', '127.0.0.1', null, '2013-09-28 11:00:48');
INSERT INTO `t_sessioncount` VALUES ('237', '127.0.0.1', null, '2013-09-28 16:18:48');
INSERT INTO `t_sessioncount` VALUES ('238', '127.0.0.1', null, '2013-09-28 16:42:39');
INSERT INTO `t_sessioncount` VALUES ('239', '127.0.0.1', null, '2013-09-28 17:21:24');
INSERT INTO `t_sessioncount` VALUES ('240', '127.0.0.1', null, '2013-09-28 22:08:09');
INSERT INTO `t_sessioncount` VALUES ('241', '127.0.0.1', null, '2013-09-28 22:11:12');
INSERT INTO `t_sessioncount` VALUES ('242', '127.0.0.1', null, '2013-09-28 22:28:41');
INSERT INTO `t_sessioncount` VALUES ('243', '127.0.0.1', null, '2013-09-28 22:38:50');
INSERT INTO `t_sessioncount` VALUES ('244', '127.0.0.1', null, '2013-09-28 22:50:51');
INSERT INTO `t_sessioncount` VALUES ('245', '127.0.0.1', null, '2013-09-29 00:05:52');
INSERT INTO `t_sessioncount` VALUES ('246', '127.0.0.1', null, '2013-09-29 00:11:49');
INSERT INTO `t_sessioncount` VALUES ('247', '127.0.0.1', null, '2013-10-01 12:13:35');
INSERT INTO `t_sessioncount` VALUES ('248', '127.0.0.1', null, '2013-10-01 12:57:47');
INSERT INTO `t_sessioncount` VALUES ('249', '127.0.0.1', null, '2013-10-01 12:59:30');
INSERT INTO `t_sessioncount` VALUES ('250', '127.0.0.1', null, '2013-10-01 15:04:13');
INSERT INTO `t_sessioncount` VALUES ('251', '127.0.0.1', null, '2013-10-01 15:07:16');
INSERT INTO `t_sessioncount` VALUES ('252', '127.0.0.1', null, '2013-10-01 15:39:37');
INSERT INTO `t_sessioncount` VALUES ('253', '127.0.0.1', null, '2013-10-01 15:43:32');
INSERT INTO `t_sessioncount` VALUES ('254', '127.0.0.1', null, '2013-10-01 15:45:09');
INSERT INTO `t_sessioncount` VALUES ('255', '127.0.0.1', null, '2013-10-01 15:49:51');
INSERT INTO `t_sessioncount` VALUES ('256', '127.0.0.1', null, '2013-10-01 15:52:40');
INSERT INTO `t_sessioncount` VALUES ('257', '127.0.0.1', null, '2013-10-01 16:42:00');
INSERT INTO `t_sessioncount` VALUES ('258', '127.0.0.1', null, '2013-10-01 16:44:44');
INSERT INTO `t_sessioncount` VALUES ('259', '127.0.0.1', null, '2013-10-01 17:03:01');
INSERT INTO `t_sessioncount` VALUES ('260', '127.0.0.1', null, '2013-10-01 17:10:21');
INSERT INTO `t_sessioncount` VALUES ('261', '127.0.0.1', null, '2013-10-01 17:34:19');
INSERT INTO `t_sessioncount` VALUES ('262', '127.0.0.1', null, '2013-10-01 17:38:54');
INSERT INTO `t_sessioncount` VALUES ('263', '127.0.0.1', null, '2013-10-01 17:43:20');
INSERT INTO `t_sessioncount` VALUES ('264', '127.0.0.1', null, '2013-10-01 17:54:12');
INSERT INTO `t_sessioncount` VALUES ('265', '127.0.0.1', null, '2013-10-01 17:56:26');
INSERT INTO `t_sessioncount` VALUES ('266', '127.0.0.1', null, '2013-10-01 18:01:50');
INSERT INTO `t_sessioncount` VALUES ('267', '127.0.0.1', null, '2013-10-01 18:13:19');
INSERT INTO `t_sessioncount` VALUES ('268', '127.0.0.1', null, '2013-10-01 20:41:08');
INSERT INTO `t_sessioncount` VALUES ('269', '127.0.0.1', null, '2013-10-01 20:42:36');
INSERT INTO `t_sessioncount` VALUES ('270', '127.0.0.1', null, '2013-10-01 21:12:03');
INSERT INTO `t_sessioncount` VALUES ('271', '127.0.0.1', null, '2013-10-01 21:44:56');
INSERT INTO `t_sessioncount` VALUES ('272', '127.0.0.1', null, '2013-10-01 21:46:18');
INSERT INTO `t_sessioncount` VALUES ('273', '127.0.0.1', null, '2013-10-01 22:01:16');
INSERT INTO `t_sessioncount` VALUES ('274', '127.0.0.1', null, '2013-10-01 22:15:47');
INSERT INTO `t_sessioncount` VALUES ('275', '127.0.0.1', null, '2013-10-01 22:17:36');
INSERT INTO `t_sessioncount` VALUES ('276', '127.0.0.1', null, '2013-10-01 22:21:08');
INSERT INTO `t_sessioncount` VALUES ('277', '127.0.0.1', null, '2013-10-01 22:35:26');
INSERT INTO `t_sessioncount` VALUES ('278', '127.0.0.1', null, '2013-10-01 22:45:52');
INSERT INTO `t_sessioncount` VALUES ('279', '127.0.0.1', null, '2013-10-01 22:45:52');
INSERT INTO `t_sessioncount` VALUES ('280', '127.0.0.1', null, '2013-10-01 22:52:31');
INSERT INTO `t_sessioncount` VALUES ('281', '127.0.0.1', null, '2013-10-01 23:00:39');
INSERT INTO `t_sessioncount` VALUES ('282', '127.0.0.1', null, '2013-10-01 23:01:13');
INSERT INTO `t_sessioncount` VALUES ('283', '127.0.0.1', null, '2013-10-02 07:26:57');
INSERT INTO `t_sessioncount` VALUES ('284', '127.0.0.1', null, '2013-10-02 08:27:09');
INSERT INTO `t_sessioncount` VALUES ('285', '127.0.0.1', null, '2013-10-02 08:28:26');
INSERT INTO `t_sessioncount` VALUES ('286', '127.0.0.1', null, '2013-10-02 08:37:05');
INSERT INTO `t_sessioncount` VALUES ('287', '127.0.0.1', null, '2013-10-02 08:43:56');
INSERT INTO `t_sessioncount` VALUES ('288', '127.0.0.1', null, '2013-10-02 08:54:34');
INSERT INTO `t_sessioncount` VALUES ('289', '127.0.0.1', null, '2013-10-02 08:54:40');
INSERT INTO `t_sessioncount` VALUES ('290', '127.0.0.1', null, '2013-10-02 08:55:00');
INSERT INTO `t_sessioncount` VALUES ('291', '127.0.0.1', null, '2013-10-02 08:55:00');
INSERT INTO `t_sessioncount` VALUES ('292', '127.0.0.1', null, '2013-10-02 09:00:34');
INSERT INTO `t_sessioncount` VALUES ('293', '127.0.0.1', null, '2013-10-02 09:05:24');
INSERT INTO `t_sessioncount` VALUES ('294', '127.0.0.1', null, '2013-10-02 09:48:34');
INSERT INTO `t_sessioncount` VALUES ('295', '127.0.0.1', null, '2013-10-02 09:50:54');
INSERT INTO `t_sessioncount` VALUES ('296', '127.0.0.1', null, '2013-10-02 09:56:36');
INSERT INTO `t_sessioncount` VALUES ('297', '127.0.0.1', null, '2013-10-02 10:04:18');
INSERT INTO `t_sessioncount` VALUES ('298', '127.0.0.1', null, '2013-10-02 10:06:53');
INSERT INTO `t_sessioncount` VALUES ('299', '127.0.0.1', null, '2013-10-02 10:12:01');
INSERT INTO `t_sessioncount` VALUES ('300', '127.0.0.1', null, '2013-10-02 10:13:42');
INSERT INTO `t_sessioncount` VALUES ('301', '127.0.0.1', null, '2013-10-02 10:20:32');
INSERT INTO `t_sessioncount` VALUES ('302', '127.0.0.1', null, '2013-10-02 10:32:19');
INSERT INTO `t_sessioncount` VALUES ('303', '127.0.0.1', null, '2013-10-02 10:38:49');
INSERT INTO `t_sessioncount` VALUES ('304', '127.0.0.1', null, '2013-10-02 10:39:48');
INSERT INTO `t_sessioncount` VALUES ('305', '127.0.0.1', null, '2013-10-02 10:41:28');
INSERT INTO `t_sessioncount` VALUES ('306', '127.0.0.1', null, '2013-10-02 10:42:27');
INSERT INTO `t_sessioncount` VALUES ('307', '127.0.0.1', null, '2013-10-02 10:47:58');
INSERT INTO `t_sessioncount` VALUES ('308', '127.0.0.1', null, '2013-10-02 11:02:53');
INSERT INTO `t_sessioncount` VALUES ('309', '127.0.0.1', null, '2013-10-02 11:28:32');
INSERT INTO `t_sessioncount` VALUES ('310', '127.0.0.1', null, '2013-10-02 11:29:59');
INSERT INTO `t_sessioncount` VALUES ('311', '127.0.0.1', null, '2013-10-02 11:31:34');
INSERT INTO `t_sessioncount` VALUES ('312', '127.0.0.1', null, '2013-10-02 11:34:16');
INSERT INTO `t_sessioncount` VALUES ('313', '127.0.0.1', null, '2013-10-02 11:35:28');
INSERT INTO `t_sessioncount` VALUES ('314', '0:0:0:0:0:0:0:1', null, '2013-10-02 12:08:53');
INSERT INTO `t_sessioncount` VALUES ('315', '127.0.0.1', null, '2013-10-02 12:12:04');
INSERT INTO `t_sessioncount` VALUES ('316', '127.0.0.1', null, '2013-10-02 12:13:37');
INSERT INTO `t_sessioncount` VALUES ('317', '127.0.0.1', null, '2013-10-02 12:18:20');
INSERT INTO `t_sessioncount` VALUES ('318', '127.0.0.1', null, '2013-10-02 12:19:20');
INSERT INTO `t_sessioncount` VALUES ('319', '127.0.0.1', null, '2013-10-02 12:26:38');
INSERT INTO `t_sessioncount` VALUES ('320', '127.0.0.1', null, '2013-10-02 12:35:46');
INSERT INTO `t_sessioncount` VALUES ('321', '127.0.0.1', null, '2013-10-02 12:37:00');
INSERT INTO `t_sessioncount` VALUES ('322', '127.0.0.1', null, '2013-10-02 13:01:19');
INSERT INTO `t_sessioncount` VALUES ('323', '127.0.0.1', null, '2013-10-02 13:03:09');
INSERT INTO `t_sessioncount` VALUES ('324', '127.0.0.1', null, '2013-10-02 13:55:32');
INSERT INTO `t_sessioncount` VALUES ('325', '127.0.0.1', null, '2013-10-02 14:16:58');
INSERT INTO `t_sessioncount` VALUES ('326', '127.0.0.1', null, '2013-10-02 15:46:18');
INSERT INTO `t_sessioncount` VALUES ('327', '127.0.0.1', null, '2013-10-02 16:02:47');
INSERT INTO `t_sessioncount` VALUES ('328', '127.0.0.1', null, '2013-10-02 16:25:54');
INSERT INTO `t_sessioncount` VALUES ('329', '127.0.0.1', null, '2013-10-02 16:32:23');
INSERT INTO `t_sessioncount` VALUES ('330', '127.0.0.1', null, '2013-10-02 16:34:28');
INSERT INTO `t_sessioncount` VALUES ('331', '127.0.0.1', null, '2013-10-02 16:39:06');
INSERT INTO `t_sessioncount` VALUES ('332', '127.0.0.1', null, '2013-10-02 16:41:33');
INSERT INTO `t_sessioncount` VALUES ('333', '127.0.0.1', null, '2013-10-02 16:43:04');
INSERT INTO `t_sessioncount` VALUES ('334', '127.0.0.1', null, '2013-10-02 16:44:42');
INSERT INTO `t_sessioncount` VALUES ('335', '127.0.0.1', null, '2013-10-02 16:45:41');
INSERT INTO `t_sessioncount` VALUES ('336', '127.0.0.1', null, '2013-10-02 16:53:46');
INSERT INTO `t_sessioncount` VALUES ('337', '127.0.0.1', null, '2013-10-02 16:57:05');
INSERT INTO `t_sessioncount` VALUES ('338', '127.0.0.1', null, '2013-10-02 17:06:54');
INSERT INTO `t_sessioncount` VALUES ('339', '127.0.0.1', null, '2013-10-02 17:07:13');
INSERT INTO `t_sessioncount` VALUES ('340', '127.0.0.1', null, '2013-10-02 19:53:41');
INSERT INTO `t_sessioncount` VALUES ('341', '127.0.0.1', null, '2013-10-02 19:55:59');
INSERT INTO `t_sessioncount` VALUES ('342', '127.0.0.1', null, '2013-10-02 20:05:21');
INSERT INTO `t_sessioncount` VALUES ('343', '127.0.0.1', null, '2013-10-02 20:19:29');
INSERT INTO `t_sessioncount` VALUES ('344', '127.0.0.1', null, '2013-10-02 20:42:03');
INSERT INTO `t_sessioncount` VALUES ('345', '127.0.0.1', null, '2013-10-02 20:43:53');
INSERT INTO `t_sessioncount` VALUES ('346', '127.0.0.1', null, '2013-10-02 20:58:04');
INSERT INTO `t_sessioncount` VALUES ('347', '127.0.0.1', null, '2013-10-02 21:04:12');
INSERT INTO `t_sessioncount` VALUES ('348', '127.0.0.1', null, '2013-10-02 21:09:22');
INSERT INTO `t_sessioncount` VALUES ('349', '127.0.0.1', null, '2013-10-02 21:09:23');
INSERT INTO `t_sessioncount` VALUES ('350', '127.0.0.1', null, '2013-10-02 21:09:23');
INSERT INTO `t_sessioncount` VALUES ('351', '127.0.0.1', null, '2013-10-02 21:09:23');
INSERT INTO `t_sessioncount` VALUES ('352', '127.0.0.1', null, '2013-10-02 21:09:23');
INSERT INTO `t_sessioncount` VALUES ('353', '127.0.0.1', null, '2013-10-02 21:09:52');
INSERT INTO `t_sessioncount` VALUES ('354', '127.0.0.1', null, '2013-10-02 21:30:37');
INSERT INTO `t_sessioncount` VALUES ('355', '127.0.0.1', null, '2013-10-02 21:35:56');
INSERT INTO `t_sessioncount` VALUES ('356', '127.0.0.1', null, '2013-10-02 21:43:09');
INSERT INTO `t_sessioncount` VALUES ('357', '127.0.0.1', null, '2013-10-02 21:52:29');
INSERT INTO `t_sessioncount` VALUES ('358', '127.0.0.1', null, '2013-10-03 06:56:57');
INSERT INTO `t_sessioncount` VALUES ('359', '127.0.0.1', null, '2013-10-03 06:57:05');
INSERT INTO `t_sessioncount` VALUES ('360', '127.0.0.1', null, '2013-10-03 07:04:48');
INSERT INTO `t_sessioncount` VALUES ('361', '127.0.0.1', null, '2013-10-03 17:44:25');
INSERT INTO `t_sessioncount` VALUES ('362', '127.0.0.1', null, '2013-10-03 17:48:43');
INSERT INTO `t_sessioncount` VALUES ('363', '127.0.0.1', null, '2013-10-03 17:50:00');
INSERT INTO `t_sessioncount` VALUES ('364', '127.0.0.1', null, '2013-10-03 18:01:48');
INSERT INTO `t_sessioncount` VALUES ('365', '127.0.0.1', null, '2013-10-03 19:05:39');
INSERT INTO `t_sessioncount` VALUES ('366', '127.0.0.1', null, '2013-10-03 19:09:59');
INSERT INTO `t_sessioncount` VALUES ('367', '127.0.0.1', null, '2013-10-03 19:11:44');
INSERT INTO `t_sessioncount` VALUES ('368', '127.0.0.1', null, '2013-10-03 19:13:15');
INSERT INTO `t_sessioncount` VALUES ('369', '127.0.0.1', null, '2013-10-03 19:35:59');
INSERT INTO `t_sessioncount` VALUES ('370', '127.0.0.1', null, '2013-10-03 19:43:13');
INSERT INTO `t_sessioncount` VALUES ('371', '127.0.0.1', null, '2013-10-03 19:43:35');
INSERT INTO `t_sessioncount` VALUES ('372', '127.0.0.1', null, '2013-10-03 19:49:14');
INSERT INTO `t_sessioncount` VALUES ('373', '127.0.0.1', null, '2013-10-03 19:49:32');
INSERT INTO `t_sessioncount` VALUES ('374', '127.0.0.1', null, '2013-10-03 19:52:26');
INSERT INTO `t_sessioncount` VALUES ('375', '127.0.0.1', null, '2013-10-03 19:52:26');
INSERT INTO `t_sessioncount` VALUES ('376', '127.0.0.1', null, '2013-10-03 19:52:26');
INSERT INTO `t_sessioncount` VALUES ('377', '127.0.0.1', null, '2013-10-03 19:52:26');
INSERT INTO `t_sessioncount` VALUES ('378', '127.0.0.1', null, '2013-10-03 19:52:26');
INSERT INTO `t_sessioncount` VALUES ('379', '127.0.0.1', null, '2013-10-03 19:52:26');
INSERT INTO `t_sessioncount` VALUES ('380', '127.0.0.1', null, '2013-10-03 19:52:26');
INSERT INTO `t_sessioncount` VALUES ('381', '127.0.0.1', null, '2013-10-03 19:56:08');
INSERT INTO `t_sessioncount` VALUES ('382', '127.0.0.1', null, '2013-10-03 19:56:08');
INSERT INTO `t_sessioncount` VALUES ('383', '127.0.0.1', null, '2013-10-03 19:56:08');
INSERT INTO `t_sessioncount` VALUES ('384', '127.0.0.1', null, '2013-10-03 19:56:08');
INSERT INTO `t_sessioncount` VALUES ('385', '127.0.0.1', null, '2013-10-03 19:56:08');
INSERT INTO `t_sessioncount` VALUES ('386', '127.0.0.1', null, '2013-10-03 19:56:08');
INSERT INTO `t_sessioncount` VALUES ('387', '127.0.0.1', null, '2013-10-03 19:56:08');
INSERT INTO `t_sessioncount` VALUES ('388', '127.0.0.1', null, '2013-10-03 20:18:02');
INSERT INTO `t_sessioncount` VALUES ('389', '127.0.0.1', null, '2013-10-03 20:31:17');
INSERT INTO `t_sessioncount` VALUES ('390', '127.0.0.1', null, '2013-10-03 20:42:47');
INSERT INTO `t_sessioncount` VALUES ('391', '127.0.0.1', null, '2013-10-04 07:08:29');
INSERT INTO `t_sessioncount` VALUES ('392', '127.0.0.1', null, '2013-10-04 07:12:05');
INSERT INTO `t_sessioncount` VALUES ('393', '127.0.0.1', null, '2013-10-04 08:45:06');
INSERT INTO `t_sessioncount` VALUES ('394', '127.0.0.1', null, '2013-10-04 09:01:05');
INSERT INTO `t_sessioncount` VALUES ('395', '127.0.0.1', null, '2013-10-04 09:58:44');
INSERT INTO `t_sessioncount` VALUES ('396', '127.0.0.1', null, '2013-10-04 10:05:17');
INSERT INTO `t_sessioncount` VALUES ('397', '127.0.0.1', null, '2013-10-04 11:24:10');
INSERT INTO `t_sessioncount` VALUES ('398', '127.0.0.1', null, '2013-10-04 11:26:54');
INSERT INTO `t_sessioncount` VALUES ('399', '127.0.0.1', null, '2013-10-04 11:28:12');
INSERT INTO `t_sessioncount` VALUES ('400', '127.0.0.1', null, '2013-10-04 11:29:34');
INSERT INTO `t_sessioncount` VALUES ('401', '127.0.0.1', null, '2013-10-04 11:36:01');
INSERT INTO `t_sessioncount` VALUES ('402', '127.0.0.1', null, '2013-10-04 11:38:46');
INSERT INTO `t_sessioncount` VALUES ('403', '127.0.0.1', null, '2013-10-04 11:50:05');
INSERT INTO `t_sessioncount` VALUES ('404', '127.0.0.1', null, '2013-10-04 12:10:01');
INSERT INTO `t_sessioncount` VALUES ('405', '127.0.0.1', null, '2013-10-04 12:29:10');
INSERT INTO `t_sessioncount` VALUES ('406', '127.0.0.1', null, '2013-10-04 13:03:25');
INSERT INTO `t_sessioncount` VALUES ('407', '127.0.0.1', null, '2013-10-04 13:06:13');
INSERT INTO `t_sessioncount` VALUES ('408', '127.0.0.1', null, '2013-10-04 13:08:02');
INSERT INTO `t_sessioncount` VALUES ('409', '127.0.0.1', null, '2013-10-04 13:09:09');
INSERT INTO `t_sessioncount` VALUES ('410', '127.0.0.1', null, '2013-10-04 13:10:51');
INSERT INTO `t_sessioncount` VALUES ('411', '127.0.0.1', null, '2013-10-04 13:13:48');
INSERT INTO `t_sessioncount` VALUES ('412', '127.0.0.1', null, '2013-10-04 13:15:02');
INSERT INTO `t_sessioncount` VALUES ('413', '127.0.0.1', null, '2013-10-04 13:20:02');
INSERT INTO `t_sessioncount` VALUES ('414', '127.0.0.1', null, '2013-10-04 14:07:21');
INSERT INTO `t_sessioncount` VALUES ('415', '127.0.0.1', null, '2013-10-04 14:21:29');
INSERT INTO `t_sessioncount` VALUES ('416', '127.0.0.1', null, '2013-10-04 14:35:16');
INSERT INTO `t_sessioncount` VALUES ('417', '127.0.0.1', null, '2013-10-04 14:35:38');
INSERT INTO `t_sessioncount` VALUES ('418', '127.0.0.1', null, '2013-10-04 14:35:38');
INSERT INTO `t_sessioncount` VALUES ('419', '127.0.0.1', null, '2013-10-04 14:35:38');
INSERT INTO `t_sessioncount` VALUES ('420', '127.0.0.1', null, '2013-10-04 14:35:38');
INSERT INTO `t_sessioncount` VALUES ('421', '127.0.0.1', null, '2013-10-04 14:35:38');
INSERT INTO `t_sessioncount` VALUES ('422', '127.0.0.1', null, '2013-10-04 14:35:38');
INSERT INTO `t_sessioncount` VALUES ('423', '127.0.0.1', null, '2013-10-04 14:35:38');
INSERT INTO `t_sessioncount` VALUES ('424', '127.0.0.1', null, '2013-10-04 14:35:38');
INSERT INTO `t_sessioncount` VALUES ('425', '127.0.0.1', null, '2013-10-04 14:41:46');
INSERT INTO `t_sessioncount` VALUES ('426', '127.0.0.1', null, '2013-10-04 14:48:19');
INSERT INTO `t_sessioncount` VALUES ('427', '127.0.0.1', null, '2013-10-04 14:48:24');
INSERT INTO `t_sessioncount` VALUES ('428', '127.0.0.1', null, '2013-10-04 14:48:24');
INSERT INTO `t_sessioncount` VALUES ('429', '127.0.0.1', null, '2013-10-04 14:48:24');
INSERT INTO `t_sessioncount` VALUES ('430', '127.0.0.1', null, '2013-10-04 14:48:24');
INSERT INTO `t_sessioncount` VALUES ('431', '127.0.0.1', null, '2013-10-04 14:48:24');
INSERT INTO `t_sessioncount` VALUES ('432', '127.0.0.1', null, '2013-10-04 14:48:24');
INSERT INTO `t_sessioncount` VALUES ('433', '127.0.0.1', null, '2013-10-04 14:48:26');
INSERT INTO `t_sessioncount` VALUES ('434', '127.0.0.1', null, '2013-10-04 14:58:49');
INSERT INTO `t_sessioncount` VALUES ('435', '127.0.0.1', null, '2013-10-04 15:04:06');
INSERT INTO `t_sessioncount` VALUES ('436', '127.0.0.1', null, '2013-10-04 15:05:58');
INSERT INTO `t_sessioncount` VALUES ('437', '127.0.0.1', null, '2013-10-04 15:25:31');
INSERT INTO `t_sessioncount` VALUES ('438', '127.0.0.1', null, '2013-10-04 15:25:35');
INSERT INTO `t_sessioncount` VALUES ('439', '127.0.0.1', null, '2013-10-04 15:25:35');
INSERT INTO `t_sessioncount` VALUES ('440', '127.0.0.1', null, '2013-10-04 15:25:35');
INSERT INTO `t_sessioncount` VALUES ('441', '127.0.0.1', null, '2013-10-04 15:25:35');
INSERT INTO `t_sessioncount` VALUES ('442', '127.0.0.1', null, '2013-10-04 15:25:35');
INSERT INTO `t_sessioncount` VALUES ('443', '127.0.0.1', null, '2013-10-04 15:25:35');
INSERT INTO `t_sessioncount` VALUES ('444', '127.0.0.1', null, '2013-10-04 15:25:36');
INSERT INTO `t_sessioncount` VALUES ('445', '127.0.0.1', null, '2013-10-04 15:55:41');
INSERT INTO `t_sessioncount` VALUES ('446', '127.0.0.1', null, '2013-10-04 16:04:37');
INSERT INTO `t_sessioncount` VALUES ('447', '127.0.0.1', null, '2013-10-04 16:21:32');
INSERT INTO `t_sessioncount` VALUES ('448', '127.0.0.1', null, '2013-10-04 16:22:19');
INSERT INTO `t_sessioncount` VALUES ('449', '127.0.0.1', null, '2013-10-04 16:24:51');
INSERT INTO `t_sessioncount` VALUES ('450', '127.0.0.1', null, '2013-10-04 16:27:33');
INSERT INTO `t_sessioncount` VALUES ('451', '127.0.0.1', null, '2013-10-04 17:25:09');
INSERT INTO `t_sessioncount` VALUES ('452', '127.0.0.1', null, '2013-10-05 08:29:51');
INSERT INTO `t_sessioncount` VALUES ('453', '127.0.0.1', null, '2013-10-05 08:40:15');
INSERT INTO `t_sessioncount` VALUES ('454', '127.0.0.1', null, '2013-10-05 09:16:00');
INSERT INTO `t_sessioncount` VALUES ('455', '127.0.0.1', null, '2013-10-05 09:16:00');
INSERT INTO `t_sessioncount` VALUES ('456', '127.0.0.1', null, '2013-10-05 09:16:00');
INSERT INTO `t_sessioncount` VALUES ('457', '127.0.0.1', null, '2013-10-05 09:16:00');
INSERT INTO `t_sessioncount` VALUES ('458', '127.0.0.1', null, '2013-10-05 09:16:00');
INSERT INTO `t_sessioncount` VALUES ('459', '127.0.0.1', null, '2013-10-05 09:16:00');
INSERT INTO `t_sessioncount` VALUES ('460', '127.0.0.1', null, '2013-10-05 09:16:00');
INSERT INTO `t_sessioncount` VALUES ('461', '127.0.0.1', null, '2013-10-05 09:16:00');
INSERT INTO `t_sessioncount` VALUES ('462', '127.0.0.1', null, '2013-10-05 10:24:30');
INSERT INTO `t_sessioncount` VALUES ('463', '127.0.0.1', null, '2013-10-05 13:07:52');
INSERT INTO `t_sessioncount` VALUES ('464', '127.0.0.1', null, '2013-10-05 13:42:04');
INSERT INTO `t_sessioncount` VALUES ('465', '127.0.0.1', null, '2013-10-05 13:51:29');
INSERT INTO `t_sessioncount` VALUES ('466', '127.0.0.1', null, '2013-10-05 14:34:58');
INSERT INTO `t_sessioncount` VALUES ('467', '127.0.0.1', null, '2013-10-05 14:44:02');
INSERT INTO `t_sessioncount` VALUES ('468', '127.0.0.1', null, '2013-10-05 14:45:20');
INSERT INTO `t_sessioncount` VALUES ('469', '127.0.0.1', null, '2013-10-05 14:47:46');
INSERT INTO `t_sessioncount` VALUES ('470', '127.0.0.1', null, '2013-10-05 14:48:35');
INSERT INTO `t_sessioncount` VALUES ('471', '127.0.0.1', null, '2013-10-05 16:26:08');
INSERT INTO `t_sessioncount` VALUES ('472', '127.0.0.1', null, '2013-10-06 21:11:44');
INSERT INTO `t_sessioncount` VALUES ('473', '127.0.0.1', null, '2013-10-06 21:11:44');
INSERT INTO `t_sessioncount` VALUES ('474', '127.0.0.1', null, '2013-10-06 21:11:44');
INSERT INTO `t_sessioncount` VALUES ('475', '127.0.0.1', null, '2013-10-06 21:40:07');
INSERT INTO `t_sessioncount` VALUES ('476', '127.0.0.1', null, '2013-10-07 23:37:26');
INSERT INTO `t_sessioncount` VALUES ('477', '127.0.0.1', null, '2013-10-08 21:03:37');
INSERT INTO `t_sessioncount` VALUES ('478', '127.0.0.1', null, '2013-10-08 21:58:08');
INSERT INTO `t_sessioncount` VALUES ('479', '127.0.0.1', null, '2013-10-08 22:02:16');
INSERT INTO `t_sessioncount` VALUES ('480', '127.0.0.1', null, '2013-10-08 22:11:17');
INSERT INTO `t_sessioncount` VALUES ('481', '127.0.0.1', null, '2013-10-08 22:15:28');
INSERT INTO `t_sessioncount` VALUES ('482', '127.0.0.1', null, '2013-10-08 22:19:02');
INSERT INTO `t_sessioncount` VALUES ('483', '127.0.0.1', null, '2013-10-08 22:23:17');
INSERT INTO `t_sessioncount` VALUES ('484', '127.0.0.1', null, '2013-10-08 22:28:06');
INSERT INTO `t_sessioncount` VALUES ('485', '127.0.0.1', null, '2013-10-08 22:48:02');
INSERT INTO `t_sessioncount` VALUES ('486', '127.0.0.1', null, '2013-10-08 22:48:57');
INSERT INTO `t_sessioncount` VALUES ('487', '127.0.0.1', null, '2013-10-08 22:49:39');
INSERT INTO `t_sessioncount` VALUES ('488', '127.0.0.1', null, '2013-10-08 23:51:09');
INSERT INTO `t_sessioncount` VALUES ('489', '127.0.0.1', null, '2013-10-08 23:53:16');
INSERT INTO `t_sessioncount` VALUES ('490', '127.0.0.1', null, '2013-10-08 23:58:29');
INSERT INTO `t_sessioncount` VALUES ('491', '127.0.0.1', null, '2013-10-09 00:06:10');
INSERT INTO `t_sessioncount` VALUES ('492', '127.0.0.1', null, '2013-10-09 06:56:22');
INSERT INTO `t_sessioncount` VALUES ('493', '127.0.0.1', null, '2013-10-09 07:15:36');
INSERT INTO `t_sessioncount` VALUES ('494', '127.0.0.1', null, '2013-10-20 00:08:40');
INSERT INTO `t_sessioncount` VALUES ('495', '127.0.0.1', null, '2013-10-20 00:15:46');
INSERT INTO `t_sessioncount` VALUES ('496', '127.0.0.1', null, '2013-10-20 00:15:46');
INSERT INTO `t_sessioncount` VALUES ('497', '127.0.0.1', null, '2013-10-20 00:15:46');
INSERT INTO `t_sessioncount` VALUES ('498', '127.0.0.1', null, '2013-10-20 00:44:56');
INSERT INTO `t_sessioncount` VALUES ('499', '127.0.0.1', null, '2013-10-20 00:44:56');
INSERT INTO `t_sessioncount` VALUES ('500', '127.0.0.1', null, '2013-10-20 00:44:56');
INSERT INTO `t_sessioncount` VALUES ('501', '127.0.0.1', null, '2013-10-20 09:31:10');
INSERT INTO `t_sessioncount` VALUES ('502', '127.0.0.1', null, '2013-10-20 09:35:43');
INSERT INTO `t_sessioncount` VALUES ('503', '127.0.0.1', null, '2013-10-20 09:44:21');
INSERT INTO `t_sessioncount` VALUES ('504', '127.0.0.1', null, '2013-10-20 09:46:45');
INSERT INTO `t_sessioncount` VALUES ('505', '127.0.0.1', null, '2013-10-20 09:51:29');
INSERT INTO `t_sessioncount` VALUES ('506', '127.0.0.1', null, '2013-10-20 09:52:32');
INSERT INTO `t_sessioncount` VALUES ('507', '127.0.0.1', null, '2013-10-20 09:55:45');
INSERT INTO `t_sessioncount` VALUES ('508', '127.0.0.1', null, '2013-10-20 10:01:27');
INSERT INTO `t_sessioncount` VALUES ('509', '127.0.0.1', null, '2013-10-20 10:05:00');
INSERT INTO `t_sessioncount` VALUES ('510', '127.0.0.1', null, '2013-10-20 10:09:29');
INSERT INTO `t_sessioncount` VALUES ('511', '127.0.0.1', null, '2013-10-20 10:37:51');
INSERT INTO `t_sessioncount` VALUES ('512', '127.0.0.1', null, '2013-10-20 10:37:51');
INSERT INTO `t_sessioncount` VALUES ('513', '127.0.0.1', null, '2013-10-20 10:37:51');
INSERT INTO `t_sessioncount` VALUES ('514', '127.0.0.1', null, '2013-10-20 11:11:34');
INSERT INTO `t_sessioncount` VALUES ('515', '127.0.0.1', null, '2013-10-20 11:11:34');
INSERT INTO `t_sessioncount` VALUES ('516', '127.0.0.1', null, '2013-10-20 11:52:56');
INSERT INTO `t_sessioncount` VALUES ('517', '127.0.0.1', null, '2013-10-20 11:52:56');
INSERT INTO `t_sessioncount` VALUES ('518', '127.0.0.1', null, '2013-10-20 11:52:56');
INSERT INTO `t_sessioncount` VALUES ('519', '127.0.0.1', null, '2013-10-20 11:52:56');
INSERT INTO `t_sessioncount` VALUES ('520', '127.0.0.1', null, '2013-10-20 14:33:32');
INSERT INTO `t_sessioncount` VALUES ('521', '127.0.0.1', null, '2013-10-20 15:44:49');
INSERT INTO `t_sessioncount` VALUES ('522', '127.0.0.1', null, '2013-10-20 16:52:12');
INSERT INTO `t_sessioncount` VALUES ('523', '127.0.0.1', null, '2013-10-20 16:56:39');
INSERT INTO `t_sessioncount` VALUES ('524', '127.0.0.1', null, '2013-10-20 17:05:01');
INSERT INTO `t_sessioncount` VALUES ('525', '127.0.0.1', null, '2013-10-20 17:18:59');
INSERT INTO `t_sessioncount` VALUES ('526', '127.0.0.1', null, '2013-10-20 17:21:44');
INSERT INTO `t_sessioncount` VALUES ('527', '127.0.0.1', null, '2013-10-20 17:50:02');
INSERT INTO `t_sessioncount` VALUES ('528', '127.0.0.1', null, '2013-10-20 18:01:47');
INSERT INTO `t_sessioncount` VALUES ('529', '127.0.0.1', null, '2013-10-20 18:03:32');
INSERT INTO `t_sessioncount` VALUES ('530', '127.0.0.1', null, '2013-10-20 18:07:52');
INSERT INTO `t_sessioncount` VALUES ('531', '127.0.0.1', null, '2013-10-20 18:13:50');
INSERT INTO `t_sessioncount` VALUES ('532', '127.0.0.1', null, '2013-10-20 18:18:46');
INSERT INTO `t_sessioncount` VALUES ('533', '127.0.0.1', null, '2013-10-20 19:46:34');
INSERT INTO `t_sessioncount` VALUES ('534', '127.0.0.1', null, '2013-10-20 20:22:51');
INSERT INTO `t_sessioncount` VALUES ('535', '127.0.0.1', null, '2013-10-20 21:14:20');
INSERT INTO `t_sessioncount` VALUES ('536', '127.0.0.1', null, '2013-10-20 21:27:55');
INSERT INTO `t_sessioncount` VALUES ('537', '127.0.0.1', null, '2013-10-20 22:33:06');
INSERT INTO `t_sessioncount` VALUES ('538', '127.0.0.1', null, '2013-10-20 22:41:25');
INSERT INTO `t_sessioncount` VALUES ('539', '127.0.0.1', null, '2013-10-20 22:53:31');
INSERT INTO `t_sessioncount` VALUES ('540', '127.0.0.1', null, '2013-10-20 22:55:19');
INSERT INTO `t_sessioncount` VALUES ('541', '127.0.0.1', null, '2013-10-20 23:02:32');
INSERT INTO `t_sessioncount` VALUES ('542', '127.0.0.1', null, '2013-10-20 23:04:37');
INSERT INTO `t_sessioncount` VALUES ('543', '127.0.0.1', null, '2013-10-20 23:06:30');
INSERT INTO `t_sessioncount` VALUES ('544', '127.0.0.1', null, '2013-10-20 23:08:34');
INSERT INTO `t_sessioncount` VALUES ('545', '127.0.0.1', null, '2013-10-20 23:11:44');
INSERT INTO `t_sessioncount` VALUES ('546', '127.0.0.1', null, '2013-10-20 23:19:09');
INSERT INTO `t_sessioncount` VALUES ('547', '127.0.0.1', null, '2013-10-21 21:45:44');
INSERT INTO `t_sessioncount` VALUES ('548', '127.0.0.1', null, '2013-10-22 00:14:03');
INSERT INTO `t_sessioncount` VALUES ('549', '127.0.0.1', null, '2013-10-22 00:14:32');
INSERT INTO `t_sessioncount` VALUES ('550', '127.0.0.1', null, '2013-10-22 07:41:33');
INSERT INTO `t_sessioncount` VALUES ('551', '127.0.0.1', null, '2013-10-22 07:55:46');
INSERT INTO `t_sessioncount` VALUES ('552', '127.0.0.1', null, '2013-10-22 22:35:53');
INSERT INTO `t_sessioncount` VALUES ('553', '127.0.0.1', null, '2013-10-22 22:48:01');
INSERT INTO `t_sessioncount` VALUES ('554', '127.0.0.1', null, '2013-10-22 22:53:32');
INSERT INTO `t_sessioncount` VALUES ('555', '127.0.0.1', null, '2013-10-22 22:58:06');
INSERT INTO `t_sessioncount` VALUES ('556', '127.0.0.1', null, '2013-10-22 23:11:08');
INSERT INTO `t_sessioncount` VALUES ('557', '0:0:0:0:0:0:0:1', null, '2013-10-22 23:18:23');
INSERT INTO `t_sessioncount` VALUES ('558', '127.0.0.1', null, '2013-10-22 23:18:36');
INSERT INTO `t_sessioncount` VALUES ('559', '127.0.0.1', null, '2013-10-22 23:23:35');
INSERT INTO `t_sessioncount` VALUES ('560', '127.0.0.1', null, '2013-10-22 23:29:23');
INSERT INTO `t_sessioncount` VALUES ('561', '127.0.0.1', null, '2013-10-22 23:49:31');
INSERT INTO `t_sessioncount` VALUES ('562', '127.0.0.1', null, '2013-10-23 00:04:46');
INSERT INTO `t_sessioncount` VALUES ('563', '127.0.0.1', null, '2013-10-23 00:07:54');
INSERT INTO `t_sessioncount` VALUES ('564', '127.0.0.1', null, '2013-10-23 00:10:45');
INSERT INTO `t_sessioncount` VALUES ('565', '127.0.0.1', null, '2013-10-23 00:12:59');
INSERT INTO `t_sessioncount` VALUES ('566', '127.0.0.1', null, '2013-10-23 00:16:38');
INSERT INTO `t_sessioncount` VALUES ('567', '127.0.0.1', null, '2013-10-23 20:29:52');
INSERT INTO `t_sessioncount` VALUES ('568', '127.0.0.1', null, '2013-10-23 20:52:34');
INSERT INTO `t_sessioncount` VALUES ('569', '127.0.0.1', null, '2013-10-23 21:34:00');
INSERT INTO `t_sessioncount` VALUES ('570', '127.0.0.1', null, '2013-10-23 21:35:43');
INSERT INTO `t_sessioncount` VALUES ('571', '127.0.0.1', null, '2013-10-23 21:36:51');
INSERT INTO `t_sessioncount` VALUES ('572', '127.0.0.1', null, '2013-10-23 21:59:15');
INSERT INTO `t_sessioncount` VALUES ('573', '127.0.0.1', null, '2013-10-23 22:07:25');
INSERT INTO `t_sessioncount` VALUES ('574', '127.0.0.1', null, '2013-10-23 22:10:20');
INSERT INTO `t_sessioncount` VALUES ('575', '127.0.0.1', null, '2013-10-23 22:21:35');
INSERT INTO `t_sessioncount` VALUES ('576', '127.0.0.1', null, '2013-10-23 22:43:22');
INSERT INTO `t_sessioncount` VALUES ('577', '127.0.0.1', null, '2013-10-23 22:50:16');
INSERT INTO `t_sessioncount` VALUES ('578', '127.0.0.1', null, '2013-10-23 22:53:00');
INSERT INTO `t_sessioncount` VALUES ('579', '127.0.0.1', null, '2013-10-23 22:54:51');
INSERT INTO `t_sessioncount` VALUES ('580', '127.0.0.1', null, '2013-10-23 23:17:34');
INSERT INTO `t_sessioncount` VALUES ('581', '127.0.0.1', null, '2013-10-23 23:24:08');
INSERT INTO `t_sessioncount` VALUES ('582', '127.0.0.1', null, '2013-10-23 23:26:00');
INSERT INTO `t_sessioncount` VALUES ('583', '127.0.0.1', null, '2013-10-23 23:27:47');
INSERT INTO `t_sessioncount` VALUES ('584', '127.0.0.1', null, '2013-10-23 23:37:49');
INSERT INTO `t_sessioncount` VALUES ('585', '127.0.0.1', null, '2013-10-23 23:44:12');
INSERT INTO `t_sessioncount` VALUES ('586', '127.0.0.1', null, '2013-10-23 23:51:38');
INSERT INTO `t_sessioncount` VALUES ('587', '127.0.0.1', null, '2013-10-23 23:54:36');
INSERT INTO `t_sessioncount` VALUES ('588', '127.0.0.1', null, '2013-10-23 23:58:10');
INSERT INTO `t_sessioncount` VALUES ('589', '127.0.0.1', null, '2013-10-23 23:59:35');
INSERT INTO `t_sessioncount` VALUES ('590', '127.0.0.1', null, '2013-10-24 00:02:33');
INSERT INTO `t_sessioncount` VALUES ('591', '127.0.0.1', null, '2013-10-24 00:04:48');
INSERT INTO `t_sessioncount` VALUES ('592', '127.0.0.1', null, '2013-10-24 00:06:32');
INSERT INTO `t_sessioncount` VALUES ('593', '127.0.0.1', null, '2013-10-24 23:35:30');
INSERT INTO `t_sessioncount` VALUES ('594', '127.0.0.1', null, '2013-10-25 00:31:40');
INSERT INTO `t_sessioncount` VALUES ('595', '127.0.0.1', null, '2013-10-25 00:52:46');
INSERT INTO `t_sessioncount` VALUES ('596', '127.0.0.1', null, '2013-10-25 00:57:48');
INSERT INTO `t_sessioncount` VALUES ('597', '127.0.0.1', null, '2013-10-25 00:59:09');
INSERT INTO `t_sessioncount` VALUES ('598', '127.0.0.1', null, '2013-10-25 01:15:36');
INSERT INTO `t_sessioncount` VALUES ('599', '127.0.0.1', null, '2013-10-25 01:17:33');
INSERT INTO `t_sessioncount` VALUES ('600', '127.0.0.1', null, '2013-10-26 11:13:59');
INSERT INTO `t_sessioncount` VALUES ('601', '127.0.0.1', null, '2013-10-26 12:27:39');
INSERT INTO `t_sessioncount` VALUES ('602', '127.0.0.1', null, '2013-10-26 12:30:43');
INSERT INTO `t_sessioncount` VALUES ('603', '0:0:0:0:0:0:0:1', null, '2013-10-26 12:43:04');
INSERT INTO `t_sessioncount` VALUES ('604', '127.0.0.1', null, '2013-10-26 12:44:59');
INSERT INTO `t_sessioncount` VALUES ('605', '127.0.0.1', null, '2013-10-26 12:56:32');
INSERT INTO `t_sessioncount` VALUES ('606', '127.0.0.1', null, '2013-10-26 13:04:35');
INSERT INTO `t_sessioncount` VALUES ('607', '127.0.0.1', null, '2013-10-26 19:37:57');
INSERT INTO `t_sessioncount` VALUES ('608', '127.0.0.1', null, '2013-10-26 20:12:08');
INSERT INTO `t_sessioncount` VALUES ('609', '127.0.0.1', null, '2013-10-26 20:45:02');
INSERT INTO `t_sessioncount` VALUES ('610', '127.0.0.1', null, '2013-10-26 20:55:49');
INSERT INTO `t_sessioncount` VALUES ('611', '127.0.0.1', null, '2013-10-26 20:57:49');
INSERT INTO `t_sessioncount` VALUES ('612', '127.0.0.1', null, '2013-10-26 22:06:31');
INSERT INTO `t_sessioncount` VALUES ('613', '127.0.0.1', null, '2013-10-26 22:17:21');
INSERT INTO `t_sessioncount` VALUES ('614', '127.0.0.1', null, '2013-10-26 22:26:55');
INSERT INTO `t_sessioncount` VALUES ('615', '127.0.0.1', null, '2013-10-26 23:33:05');
INSERT INTO `t_sessioncount` VALUES ('616', '127.0.0.1', null, '2013-10-26 23:36:09');
INSERT INTO `t_sessioncount` VALUES ('617', '127.0.0.1', null, '2013-10-26 23:42:56');
INSERT INTO `t_sessioncount` VALUES ('618', '127.0.0.1', null, '2013-10-27 00:00:40');
INSERT INTO `t_sessioncount` VALUES ('619', '127.0.0.1', null, '2013-10-27 00:01:52');
INSERT INTO `t_sessioncount` VALUES ('620', '127.0.0.1', null, '2013-10-27 00:04:14');
INSERT INTO `t_sessioncount` VALUES ('621', '127.0.0.1', null, '2013-10-27 00:08:25');
INSERT INTO `t_sessioncount` VALUES ('622', '127.0.0.1', null, '2013-10-27 00:16:45');
INSERT INTO `t_sessioncount` VALUES ('623', '127.0.0.1', null, '2013-10-27 00:17:56');
INSERT INTO `t_sessioncount` VALUES ('624', '127.0.0.1', null, '2013-10-27 00:35:31');
INSERT INTO `t_sessioncount` VALUES ('625', '127.0.0.1', null, '2013-10-27 00:38:00');
INSERT INTO `t_sessioncount` VALUES ('626', '127.0.0.1', null, '2013-10-27 00:47:56');
INSERT INTO `t_sessioncount` VALUES ('627', '127.0.0.1', null, '2013-10-27 00:58:44');
INSERT INTO `t_sessioncount` VALUES ('628', '127.0.0.1', null, '2013-10-27 01:04:07');
INSERT INTO `t_sessioncount` VALUES ('629', '127.0.0.1', null, '2013-10-27 01:05:27');
INSERT INTO `t_sessioncount` VALUES ('630', '127.0.0.1', null, '2013-10-27 11:06:35');
INSERT INTO `t_sessioncount` VALUES ('631', '127.0.0.1', null, '2013-10-27 11:16:01');
INSERT INTO `t_sessioncount` VALUES ('632', '127.0.0.1', null, '2013-10-27 11:17:59');
INSERT INTO `t_sessioncount` VALUES ('633', '127.0.0.1', null, '2013-10-27 11:20:55');
INSERT INTO `t_sessioncount` VALUES ('634', '127.0.0.1', null, '2013-10-27 13:32:47');
INSERT INTO `t_sessioncount` VALUES ('635', '127.0.0.1', null, '2013-10-27 13:32:47');
INSERT INTO `t_sessioncount` VALUES ('636', '127.0.0.1', null, '2013-10-27 13:32:47');
INSERT INTO `t_sessioncount` VALUES ('637', '127.0.0.1', null, '2013-10-27 13:32:47');
INSERT INTO `t_sessioncount` VALUES ('638', '127.0.0.1', null, '2013-10-27 13:32:47');
INSERT INTO `t_sessioncount` VALUES ('639', '127.0.0.1', null, '2013-10-27 13:32:47');
INSERT INTO `t_sessioncount` VALUES ('640', '127.0.0.1', null, '2013-10-27 13:32:47');
INSERT INTO `t_sessioncount` VALUES ('641', '127.0.0.1', null, '2013-10-27 13:32:47');
INSERT INTO `t_sessioncount` VALUES ('642', '127.0.0.1', null, '2013-10-27 13:32:47');
INSERT INTO `t_sessioncount` VALUES ('643', '127.0.0.1', null, '2013-10-27 13:32:47');
INSERT INTO `t_sessioncount` VALUES ('644', '127.0.0.1', null, '2013-10-27 13:32:47');
INSERT INTO `t_sessioncount` VALUES ('645', '127.0.0.1', null, '2013-10-27 13:36:32');
INSERT INTO `t_sessioncount` VALUES ('646', '127.0.0.1', null, '2013-10-27 13:36:32');
INSERT INTO `t_sessioncount` VALUES ('647', '127.0.0.1', null, '2013-10-27 13:36:32');
INSERT INTO `t_sessioncount` VALUES ('648', '127.0.0.1', null, '2013-10-27 13:36:32');
INSERT INTO `t_sessioncount` VALUES ('649', '127.0.0.1', null, '2013-10-27 13:36:32');
INSERT INTO `t_sessioncount` VALUES ('650', '127.0.0.1', null, '2013-10-27 13:36:32');
INSERT INTO `t_sessioncount` VALUES ('651', '127.0.0.1', null, '2013-10-27 13:36:32');
INSERT INTO `t_sessioncount` VALUES ('652', '127.0.0.1', null, '2013-10-27 16:22:03');
INSERT INTO `t_sessioncount` VALUES ('653', '127.0.0.1', null, '2013-10-27 16:26:45');
INSERT INTO `t_sessioncount` VALUES ('654', '127.0.0.1', null, '2013-10-27 16:26:46');
INSERT INTO `t_sessioncount` VALUES ('655', '127.0.0.1', null, '2013-10-27 16:26:46');
INSERT INTO `t_sessioncount` VALUES ('656', '127.0.0.1', null, '2013-10-27 16:26:46');
INSERT INTO `t_sessioncount` VALUES ('657', '127.0.0.1', null, '2013-10-27 16:26:46');
INSERT INTO `t_sessioncount` VALUES ('658', '127.0.0.1', null, '2013-10-27 16:26:46');
INSERT INTO `t_sessioncount` VALUES ('659', '127.0.0.1', null, '2013-10-27 16:26:46');
INSERT INTO `t_sessioncount` VALUES ('660', '127.0.0.1', null, '2013-10-27 16:26:46');
INSERT INTO `t_sessioncount` VALUES ('661', '127.0.0.1', null, '2013-10-27 16:26:46');
INSERT INTO `t_sessioncount` VALUES ('662', '127.0.0.1', null, '2013-10-27 16:26:46');
INSERT INTO `t_sessioncount` VALUES ('663', '127.0.0.1', null, '2013-10-27 16:26:46');
INSERT INTO `t_sessioncount` VALUES ('664', '127.0.0.1', null, '2013-10-27 20:15:27');
INSERT INTO `t_sessioncount` VALUES ('665', '127.0.0.1', null, '2013-10-27 20:15:35');
INSERT INTO `t_sessioncount` VALUES ('666', '127.0.0.1', null, '2013-10-27 20:15:35');
INSERT INTO `t_sessioncount` VALUES ('667', '127.0.0.1', null, '2013-10-27 20:15:35');
INSERT INTO `t_sessioncount` VALUES ('668', '127.0.0.1', null, '2013-10-27 20:15:35');
INSERT INTO `t_sessioncount` VALUES ('669', '127.0.0.1', null, '2013-10-27 20:15:35');
INSERT INTO `t_sessioncount` VALUES ('670', '127.0.0.1', null, '2013-10-27 20:15:35');
INSERT INTO `t_sessioncount` VALUES ('671', '127.0.0.1', null, '2013-10-27 20:20:12');
INSERT INTO `t_sessioncount` VALUES ('672', '127.0.0.1', null, '2013-10-27 20:24:50');
INSERT INTO `t_sessioncount` VALUES ('673', '127.0.0.1', null, '2013-10-31 23:12:06');
INSERT INTO `t_sessioncount` VALUES ('674', '127.0.0.1', null, '2013-10-31 23:12:06');
INSERT INTO `t_sessioncount` VALUES ('675', '127.0.0.1', null, '2013-10-31 23:12:06');
INSERT INTO `t_sessioncount` VALUES ('676', '127.0.0.1', null, '2013-10-31 23:12:06');
INSERT INTO `t_sessioncount` VALUES ('677', '127.0.0.1', null, '2013-10-31 23:12:06');
INSERT INTO `t_sessioncount` VALUES ('678', '127.0.0.1', null, '2013-10-31 23:12:06');
INSERT INTO `t_sessioncount` VALUES ('679', '127.0.0.1', null, '2013-10-31 23:12:06');
INSERT INTO `t_sessioncount` VALUES ('680', '127.0.0.1', null, '2013-10-31 23:12:06');
INSERT INTO `t_sessioncount` VALUES ('681', '127.0.0.1', null, '2013-10-31 23:12:06');
INSERT INTO `t_sessioncount` VALUES ('682', '127.0.0.1', null, '2013-10-31 23:12:06');
INSERT INTO `t_sessioncount` VALUES ('683', '127.0.0.1', null, '2013-10-31 23:12:06');
INSERT INTO `t_sessioncount` VALUES ('684', '127.0.0.1', null, '2013-11-09 22:44:23');
INSERT INTO `t_sessioncount` VALUES ('685', '127.0.0.1', null, '2013-11-09 23:15:43');
INSERT INTO `t_sessioncount` VALUES ('686', '127.0.0.1', null, '2013-11-09 23:39:04');
INSERT INTO `t_sessioncount` VALUES ('687', '127.0.0.1', null, '2013-11-09 23:39:04');
INSERT INTO `t_sessioncount` VALUES ('688', '127.0.0.1', null, '2013-11-09 23:39:04');
INSERT INTO `t_sessioncount` VALUES ('689', '127.0.0.1', null, '2013-11-09 23:39:04');
INSERT INTO `t_sessioncount` VALUES ('690', '127.0.0.1', null, '2013-11-09 23:39:04');
INSERT INTO `t_sessioncount` VALUES ('691', '127.0.0.1', null, '2013-11-09 23:39:04');
INSERT INTO `t_sessioncount` VALUES ('692', '127.0.0.1', null, '2013-11-09 23:39:04');
INSERT INTO `t_sessioncount` VALUES ('693', '127.0.0.1', null, '2013-11-09 23:39:04');
INSERT INTO `t_sessioncount` VALUES ('694', '127.0.0.1', null, '2013-11-09 23:39:04');
INSERT INTO `t_sessioncount` VALUES ('695', '127.0.0.1', null, '2013-11-09 23:39:04');
INSERT INTO `t_sessioncount` VALUES ('696', '127.0.0.1', null, '2013-11-09 23:39:04');
INSERT INTO `t_sessioncount` VALUES ('697', '127.0.0.1', null, '2013-11-23 14:18:34');
INSERT INTO `t_sessioncount` VALUES ('698', '127.0.0.1', null, '2013-11-23 14:18:34');
INSERT INTO `t_sessioncount` VALUES ('699', '127.0.0.1', null, '2013-11-23 14:18:34');
INSERT INTO `t_sessioncount` VALUES ('700', '127.0.0.1', null, '2013-11-23 14:18:34');
INSERT INTO `t_sessioncount` VALUES ('701', '127.0.0.1', null, '2013-11-23 14:18:34');
INSERT INTO `t_sessioncount` VALUES ('702', '127.0.0.1', null, '2013-11-23 14:18:34');
INSERT INTO `t_sessioncount` VALUES ('703', '127.0.0.1', null, '2013-11-23 14:18:34');
INSERT INTO `t_sessioncount` VALUES ('704', '127.0.0.1', null, '2013-11-23 14:18:34');
INSERT INTO `t_sessioncount` VALUES ('705', '127.0.0.1', null, '2013-11-23 14:18:34');
INSERT INTO `t_sessioncount` VALUES ('706', '127.0.0.1', null, '2013-11-23 14:18:34');
INSERT INTO `t_sessioncount` VALUES ('707', '127.0.0.1', null, '2013-11-23 14:18:34');
INSERT INTO `t_sessioncount` VALUES ('708', '127.0.0.1', null, '2013-11-23 19:23:08');
INSERT INTO `t_sessioncount` VALUES ('709', '127.0.0.1', null, '2013-11-23 19:23:08');
INSERT INTO `t_sessioncount` VALUES ('710', '127.0.0.1', null, '2013-11-23 19:23:08');
INSERT INTO `t_sessioncount` VALUES ('711', '127.0.0.1', null, '2013-11-23 19:23:08');
INSERT INTO `t_sessioncount` VALUES ('712', '127.0.0.1', null, '2013-11-23 19:23:08');
INSERT INTO `t_sessioncount` VALUES ('713', '127.0.0.1', null, '2013-11-23 19:23:08');
INSERT INTO `t_sessioncount` VALUES ('714', '127.0.0.1', null, '2013-11-23 19:23:08');
INSERT INTO `t_sessioncount` VALUES ('715', '127.0.0.1', null, '2013-11-23 19:23:08');
INSERT INTO `t_sessioncount` VALUES ('716', '127.0.0.1', null, '2013-11-23 19:23:08');
INSERT INTO `t_sessioncount` VALUES ('717', '127.0.0.1', null, '2013-11-23 19:23:08');
INSERT INTO `t_sessioncount` VALUES ('718', '127.0.0.1', null, '2013-11-23 23:35:26');
INSERT INTO `t_sessioncount` VALUES ('719', '127.0.0.1', null, '2013-11-23 23:47:37');
INSERT INTO `t_sessioncount` VALUES ('720', '127.0.0.1', null, '2013-11-24 00:05:56');
INSERT INTO `t_sessioncount` VALUES ('721', '127.0.0.1', null, '2013-11-24 00:08:56');
INSERT INTO `t_sessioncount` VALUES ('722', '127.0.0.1', null, '2013-11-24 00:36:31');
INSERT INTO `t_sessioncount` VALUES ('723', '127.0.0.1', null, '2013-11-28 22:57:13');
INSERT INTO `t_sessioncount` VALUES ('724', '127.0.0.1', null, '2013-11-28 23:19:50');
INSERT INTO `t_sessioncount` VALUES ('725', '127.0.0.1', null, '2013-11-29 00:09:09');
INSERT INTO `t_sessioncount` VALUES ('726', '127.0.0.1', null, '2013-11-29 22:25:50');
INSERT INTO `t_sessioncount` VALUES ('727', '127.0.0.1', null, '2013-11-29 22:32:27');
INSERT INTO `t_sessioncount` VALUES ('728', '127.0.0.1', null, '2013-11-29 23:04:30');
INSERT INTO `t_sessioncount` VALUES ('729', '127.0.0.1', null, '2013-11-29 23:05:13');
INSERT INTO `t_sessioncount` VALUES ('730', '127.0.0.1', null, '2013-11-29 23:05:45');
INSERT INTO `t_sessioncount` VALUES ('731', '127.0.0.1', null, '2013-11-29 23:11:55');
INSERT INTO `t_sessioncount` VALUES ('732', '127.0.0.1', null, '2013-11-29 23:22:19');
INSERT INTO `t_sessioncount` VALUES ('733', '127.0.0.1', null, '2013-11-29 23:24:18');
INSERT INTO `t_sessioncount` VALUES ('734', '127.0.0.1', null, '2013-11-30 00:16:31');
INSERT INTO `t_sessioncount` VALUES ('735', '127.0.0.1', null, '2013-11-30 00:20:29');
INSERT INTO `t_sessioncount` VALUES ('736', '127.0.0.1', null, '2013-11-30 00:22:09');
INSERT INTO `t_sessioncount` VALUES ('737', '127.0.0.1', null, '2013-11-30 00:28:40');
INSERT INTO `t_sessioncount` VALUES ('738', '127.0.0.1', null, '2013-11-30 00:30:25');
INSERT INTO `t_sessioncount` VALUES ('739', '127.0.0.1', null, '2013-11-30 00:32:34');
INSERT INTO `t_sessioncount` VALUES ('740', '127.0.0.1', null, '2013-11-30 00:55:34');
INSERT INTO `t_sessioncount` VALUES ('741', '127.0.0.1', null, '2013-11-30 01:23:38');
INSERT INTO `t_sessioncount` VALUES ('742', '127.0.0.1', null, '2013-11-30 01:28:14');
INSERT INTO `t_sessioncount` VALUES ('743', '127.0.0.1', null, '2013-11-30 16:36:52');
INSERT INTO `t_sessioncount` VALUES ('744', '127.0.0.1', null, '2013-11-30 17:04:05');
INSERT INTO `t_sessioncount` VALUES ('745', '127.0.0.1', null, '2013-11-30 17:19:53');
INSERT INTO `t_sessioncount` VALUES ('746', '127.0.0.1', null, '2013-11-30 17:35:08');
INSERT INTO `t_sessioncount` VALUES ('747', '127.0.0.1', null, '2013-11-30 17:39:43');
INSERT INTO `t_sessioncount` VALUES ('748', '127.0.0.1', null, '2013-11-30 17:48:13');
INSERT INTO `t_sessioncount` VALUES ('749', '127.0.0.1', null, '2013-11-30 17:48:13');
INSERT INTO `t_sessioncount` VALUES ('750', '127.0.0.1', null, '2013-11-30 17:48:13');
INSERT INTO `t_sessioncount` VALUES ('751', '127.0.0.1', null, '2013-11-30 17:48:13');
INSERT INTO `t_sessioncount` VALUES ('752', '127.0.0.1', null, '2013-11-30 17:48:13');
INSERT INTO `t_sessioncount` VALUES ('753', '127.0.0.1', null, '2013-11-30 17:48:13');
INSERT INTO `t_sessioncount` VALUES ('754', '127.0.0.1', null, '2013-11-30 17:48:13');
INSERT INTO `t_sessioncount` VALUES ('755', '127.0.0.1', null, '2013-11-30 17:48:13');
INSERT INTO `t_sessioncount` VALUES ('756', '127.0.0.1', null, '2013-11-30 17:48:13');
INSERT INTO `t_sessioncount` VALUES ('757', '127.0.0.1', null, '2013-11-30 17:48:13');
INSERT INTO `t_sessioncount` VALUES ('758', '127.0.0.1', null, '2013-11-30 19:13:18');
INSERT INTO `t_sessioncount` VALUES ('759', '127.0.0.1', null, '2013-11-30 19:13:18');
INSERT INTO `t_sessioncount` VALUES ('760', '127.0.0.1', null, '2013-11-30 19:13:18');
INSERT INTO `t_sessioncount` VALUES ('761', '127.0.0.1', null, '2013-11-30 19:13:18');
INSERT INTO `t_sessioncount` VALUES ('762', '127.0.0.1', null, '2013-11-30 19:13:18');
INSERT INTO `t_sessioncount` VALUES ('763', '127.0.0.1', null, '2013-11-30 19:13:18');
INSERT INTO `t_sessioncount` VALUES ('764', '127.0.0.1', null, '2013-11-30 19:47:23');
INSERT INTO `t_sessioncount` VALUES ('765', '127.0.0.1', null, '2013-11-30 19:47:23');
INSERT INTO `t_sessioncount` VALUES ('766', '127.0.0.1', null, '2013-11-30 19:47:23');
INSERT INTO `t_sessioncount` VALUES ('767', '127.0.0.1', null, '2013-11-30 19:47:23');
INSERT INTO `t_sessioncount` VALUES ('768', '127.0.0.1', null, '2013-11-30 19:47:23');
INSERT INTO `t_sessioncount` VALUES ('769', '127.0.0.1', null, '2013-11-30 19:47:23');
INSERT INTO `t_sessioncount` VALUES ('770', '127.0.0.1', null, '2013-11-30 19:47:23');
INSERT INTO `t_sessioncount` VALUES ('771', '127.0.0.1', null, '2013-11-30 19:47:23');
INSERT INTO `t_sessioncount` VALUES ('772', '127.0.0.1', null, '2013-11-30 19:47:23');
INSERT INTO `t_sessioncount` VALUES ('773', '127.0.0.1', null, '2013-11-30 19:47:23');
INSERT INTO `t_sessioncount` VALUES ('774', '127.0.0.1', null, '2013-11-30 19:47:23');
INSERT INTO `t_sessioncount` VALUES ('775', '127.0.0.1', null, '2013-11-30 19:47:24');
INSERT INTO `t_sessioncount` VALUES ('776', '127.0.0.1', null, '2013-11-30 20:26:14');
INSERT INTO `t_sessioncount` VALUES ('777', '127.0.0.1', null, '2013-11-30 20:26:14');
INSERT INTO `t_sessioncount` VALUES ('778', '127.0.0.1', null, '2013-11-30 21:07:36');
INSERT INTO `t_sessioncount` VALUES ('779', '127.0.0.1', null, '2013-11-30 21:10:43');
INSERT INTO `t_sessioncount` VALUES ('780', '127.0.0.1', null, '2013-11-30 21:13:42');
INSERT INTO `t_sessioncount` VALUES ('781', '127.0.0.1', null, '2013-11-30 21:13:42');
INSERT INTO `t_sessioncount` VALUES ('782', '127.0.0.1', null, '2013-11-30 21:13:42');
INSERT INTO `t_sessioncount` VALUES ('783', '127.0.0.1', null, '2013-11-30 21:13:42');
INSERT INTO `t_sessioncount` VALUES ('784', '127.0.0.1', null, '2013-11-30 21:13:42');
INSERT INTO `t_sessioncount` VALUES ('785', '127.0.0.1', null, '2013-11-30 21:25:41');
INSERT INTO `t_sessioncount` VALUES ('786', '127.0.0.1', null, '2013-11-30 21:25:41');
INSERT INTO `t_sessioncount` VALUES ('787', '127.0.0.1', null, '2013-11-30 21:25:41');
INSERT INTO `t_sessioncount` VALUES ('788', '127.0.0.1', null, '2013-11-30 21:25:41');
INSERT INTO `t_sessioncount` VALUES ('789', '127.0.0.1', null, '2013-11-30 21:25:41');
INSERT INTO `t_sessioncount` VALUES ('790', '127.0.0.1', null, '2013-11-30 21:25:41');
INSERT INTO `t_sessioncount` VALUES ('791', '127.0.0.1', null, '2013-11-30 21:25:41');
INSERT INTO `t_sessioncount` VALUES ('792', '127.0.0.1', null, '2013-11-30 21:41:59');
INSERT INTO `t_sessioncount` VALUES ('793', '127.0.0.1', null, '2013-11-30 21:41:59');
INSERT INTO `t_sessioncount` VALUES ('794', '127.0.0.1', null, '2013-11-30 21:49:57');
INSERT INTO `t_sessioncount` VALUES ('795', '127.0.0.1', null, '2013-11-30 21:49:57');
INSERT INTO `t_sessioncount` VALUES ('796', '127.0.0.1', null, '2013-11-30 22:05:04');
INSERT INTO `t_sessioncount` VALUES ('797', '127.0.0.1', null, '2013-11-30 22:15:27');
INSERT INTO `t_sessioncount` VALUES ('798', '127.0.0.1', null, '2013-12-01 00:10:42');
INSERT INTO `t_sessioncount` VALUES ('799', '127.0.0.1', null, '2013-12-01 00:40:52');
INSERT INTO `t_sessioncount` VALUES ('800', '127.0.0.1', null, '2013-12-01 00:46:50');
INSERT INTO `t_sessioncount` VALUES ('801', '127.0.0.1', null, '2013-12-01 00:46:50');
INSERT INTO `t_sessioncount` VALUES ('802', '127.0.0.1', null, '2013-12-01 00:46:50');
INSERT INTO `t_sessioncount` VALUES ('803', '127.0.0.1', null, '2013-12-01 00:46:50');
INSERT INTO `t_sessioncount` VALUES ('804', '127.0.0.1', null, '2013-12-01 00:46:50');
INSERT INTO `t_sessioncount` VALUES ('805', '127.0.0.1', null, '2013-12-01 00:46:50');
INSERT INTO `t_sessioncount` VALUES ('806', '127.0.0.1', null, '2013-12-01 00:46:50');
INSERT INTO `t_sessioncount` VALUES ('807', '127.0.0.1', null, '2013-12-01 00:57:41');
INSERT INTO `t_sessioncount` VALUES ('808', '127.0.0.1', null, '2013-12-01 01:19:04');
INSERT INTO `t_sessioncount` VALUES ('809', '127.0.0.1', null, '2013-12-01 01:22:14');
INSERT INTO `t_sessioncount` VALUES ('810', '127.0.0.1', null, '2013-12-01 01:24:19');
INSERT INTO `t_sessioncount` VALUES ('811', '127.0.0.1', null, '2013-12-01 13:45:13');
INSERT INTO `t_sessioncount` VALUES ('812', '127.0.0.1', null, '2013-12-01 17:01:21');
INSERT INTO `t_sessioncount` VALUES ('813', '127.0.0.1', null, '2013-12-01 17:27:25');
INSERT INTO `t_sessioncount` VALUES ('814', '127.0.0.1', null, '2013-12-01 17:27:25');
INSERT INTO `t_sessioncount` VALUES ('815', '127.0.0.1', null, '2013-12-01 17:45:58');
INSERT INTO `t_sessioncount` VALUES ('816', '127.0.0.1', null, '2013-12-01 20:13:29');
INSERT INTO `t_sessioncount` VALUES ('817', '127.0.0.1', null, '2013-12-01 20:13:29');
INSERT INTO `t_sessioncount` VALUES ('818', '127.0.0.1', null, '2013-12-01 20:22:25');
INSERT INTO `t_sessioncount` VALUES ('819', '127.0.0.1', null, '2013-12-01 21:20:46');
INSERT INTO `t_sessioncount` VALUES ('820', '127.0.0.1', null, '2013-12-01 21:22:20');
INSERT INTO `t_sessioncount` VALUES ('821', '127.0.0.1', null, '2013-12-01 21:23:27');
INSERT INTO `t_sessioncount` VALUES ('822', '127.0.0.1', null, '2013-12-01 21:30:06');
INSERT INTO `t_sessioncount` VALUES ('823', '127.0.0.1', null, '2013-12-01 22:11:50');
INSERT INTO `t_sessioncount` VALUES ('824', '127.0.0.1', null, '2013-12-01 22:12:43');
INSERT INTO `t_sessioncount` VALUES ('825', '127.0.0.1', null, '2013-12-01 22:14:49');
INSERT INTO `t_sessioncount` VALUES ('826', '127.0.0.1', null, '2013-12-01 22:41:57');
INSERT INTO `t_sessioncount` VALUES ('827', '127.0.0.1', null, '2013-12-01 22:46:37');
INSERT INTO `t_sessioncount` VALUES ('828', '127.0.0.1', null, '2013-12-01 23:15:04');
INSERT INTO `t_sessioncount` VALUES ('829', '127.0.0.1', null, '2013-12-01 23:26:10');
INSERT INTO `t_sessioncount` VALUES ('830', '127.0.0.1', null, '2013-12-06 21:22:41');
INSERT INTO `t_sessioncount` VALUES ('831', '127.0.0.1', null, '2013-12-06 22:26:01');
INSERT INTO `t_sessioncount` VALUES ('832', '127.0.0.1', null, '2013-12-06 22:26:01');
INSERT INTO `t_sessioncount` VALUES ('833', '127.0.0.1', null, '2013-12-07 19:07:40');
INSERT INTO `t_sessioncount` VALUES ('834', '127.0.0.1', null, '2013-12-08 10:35:09');
INSERT INTO `t_sessioncount` VALUES ('835', '127.0.0.1', null, '2013-12-08 10:35:09');
INSERT INTO `t_sessioncount` VALUES ('836', '127.0.0.1', null, '2013-12-08 10:35:09');
INSERT INTO `t_sessioncount` VALUES ('837', '127.0.0.1', null, '2013-12-08 10:35:09');
INSERT INTO `t_sessioncount` VALUES ('838', '127.0.0.1', null, '2013-12-08 10:35:09');
INSERT INTO `t_sessioncount` VALUES ('839', '127.0.0.1', null, '2013-12-08 10:35:09');
INSERT INTO `t_sessioncount` VALUES ('840', '127.0.0.1', null, '2013-12-08 10:35:09');
INSERT INTO `t_sessioncount` VALUES ('841', '127.0.0.1', null, '2013-12-08 10:35:09');
INSERT INTO `t_sessioncount` VALUES ('842', '127.0.0.1', null, '2013-12-08 10:48:42');
INSERT INTO `t_sessioncount` VALUES ('843', '127.0.0.1', null, '2013-12-08 10:48:42');
INSERT INTO `t_sessioncount` VALUES ('844', '127.0.0.1', null, '2013-12-08 10:48:42');
INSERT INTO `t_sessioncount` VALUES ('845', '127.0.0.1', null, '2013-12-08 11:22:44');
INSERT INTO `t_sessioncount` VALUES ('846', '127.0.0.1', null, '2013-12-08 11:22:44');
INSERT INTO `t_sessioncount` VALUES ('847', '127.0.0.1', null, '2013-12-08 11:22:44');
INSERT INTO `t_sessioncount` VALUES ('848', '127.0.0.1', null, '2013-12-08 11:22:44');
INSERT INTO `t_sessioncount` VALUES ('849', '127.0.0.1', null, '2013-12-08 11:22:44');
INSERT INTO `t_sessioncount` VALUES ('850', '127.0.0.1', null, '2013-12-08 11:22:44');
INSERT INTO `t_sessioncount` VALUES ('851', '127.0.0.1', null, '2013-12-08 13:12:41');
INSERT INTO `t_sessioncount` VALUES ('852', '127.0.0.1', null, '2013-12-08 13:12:41');
INSERT INTO `t_sessioncount` VALUES ('853', '127.0.0.1', null, '2013-12-08 18:45:41');
INSERT INTO `t_sessioncount` VALUES ('854', '127.0.0.1', null, '2013-12-08 20:16:57');
INSERT INTO `t_sessioncount` VALUES ('855', '127.0.0.1', null, '2013-12-08 20:16:57');
INSERT INTO `t_sessioncount` VALUES ('856', '127.0.0.1', null, '2013-12-08 20:16:58');
INSERT INTO `t_sessioncount` VALUES ('857', '127.0.0.1', null, '2013-12-08 20:16:58');
INSERT INTO `t_sessioncount` VALUES ('858', '127.0.0.1', null, '2013-12-08 20:16:58');
INSERT INTO `t_sessioncount` VALUES ('859', '127.0.0.1', null, '2013-12-08 20:16:58');
INSERT INTO `t_sessioncount` VALUES ('860', '127.0.0.1', null, '2013-12-08 20:16:58');
INSERT INTO `t_sessioncount` VALUES ('861', '127.0.0.1', null, '2013-12-08 20:16:58');
INSERT INTO `t_sessioncount` VALUES ('862', '127.0.0.1', null, '2013-12-16 23:45:13');
INSERT INTO `t_sessioncount` VALUES ('863', '127.0.0.1', null, '2013-12-16 23:45:13');
INSERT INTO `t_sessioncount` VALUES ('864', '127.0.0.1', null, '2013-12-16 23:45:14');
INSERT INTO `t_sessioncount` VALUES ('865', '127.0.0.1', null, '2013-12-16 23:45:14');
INSERT INTO `t_sessioncount` VALUES ('866', '127.0.0.1', null, '2013-12-16 23:45:14');
INSERT INTO `t_sessioncount` VALUES ('867', '127.0.0.1', null, '2013-12-16 23:45:14');
INSERT INTO `t_sessioncount` VALUES ('868', '127.0.0.1', null, '2013-12-16 23:45:14');
INSERT INTO `t_sessioncount` VALUES ('869', '127.0.0.1', null, '2013-12-16 23:45:14');
INSERT INTO `t_sessioncount` VALUES ('870', '127.0.0.1', null, '2013-12-18 22:05:22');
INSERT INTO `t_sessioncount` VALUES ('871', '127.0.0.1', null, '2013-12-18 22:05:22');
INSERT INTO `t_sessioncount` VALUES ('872', '127.0.0.1', null, '2013-12-18 22:05:22');
INSERT INTO `t_sessioncount` VALUES ('873', '127.0.0.1', null, '2013-12-18 22:05:23');
INSERT INTO `t_sessioncount` VALUES ('874', '127.0.0.1', null, '2013-12-18 22:05:22');
INSERT INTO `t_sessioncount` VALUES ('875', '127.0.0.1', null, '2013-12-18 22:05:22');
INSERT INTO `t_sessioncount` VALUES ('876', '127.0.0.1', null, '2013-12-18 22:05:23');
INSERT INTO `t_sessioncount` VALUES ('877', '127.0.0.1', null, '2013-12-18 22:05:23');
INSERT INTO `t_sessioncount` VALUES ('878', '127.0.0.1', null, '2013-12-18 22:05:23');
INSERT INTO `t_sessioncount` VALUES ('879', '127.0.0.1', null, '2013-12-18 22:05:23');
INSERT INTO `t_sessioncount` VALUES ('880', '127.0.0.1', null, '2013-12-18 22:05:23');
INSERT INTO `t_sessioncount` VALUES ('881', '127.0.0.1', null, '2013-12-19 23:08:46');
INSERT INTO `t_sessioncount` VALUES ('882', '127.0.0.1', null, '2013-12-19 23:08:46');
INSERT INTO `t_sessioncount` VALUES ('883', '127.0.0.1', null, '2013-12-19 23:08:46');
INSERT INTO `t_sessioncount` VALUES ('884', '127.0.0.1', null, '2013-12-19 23:08:46');
INSERT INTO `t_sessioncount` VALUES ('885', '127.0.0.1', null, '2013-12-19 23:08:46');
INSERT INTO `t_sessioncount` VALUES ('886', '127.0.0.1', null, '2013-12-19 23:08:46');
INSERT INTO `t_sessioncount` VALUES ('887', '127.0.0.1', null, '2013-12-19 23:08:47');
INSERT INTO `t_sessioncount` VALUES ('888', '127.0.0.1', null, '2013-12-19 23:08:47');
INSERT INTO `t_sessioncount` VALUES ('889', '127.0.0.1', null, '2013-12-19 23:08:47');
INSERT INTO `t_sessioncount` VALUES ('890', '127.0.0.1', null, '2013-12-19 23:08:47');
INSERT INTO `t_sessioncount` VALUES ('891', '127.0.0.1', null, '2013-12-21 14:30:36');
INSERT INTO `t_sessioncount` VALUES ('892', '127.0.0.1', null, '2013-12-21 14:38:16');
INSERT INTO `t_sessioncount` VALUES ('893', '127.0.0.1', null, '2013-12-21 14:54:49');
INSERT INTO `t_sessioncount` VALUES ('894', '127.0.0.1', null, '2013-12-21 15:07:30');
INSERT INTO `t_sessioncount` VALUES ('895', '127.0.0.1', null, '2013-12-21 15:07:30');
INSERT INTO `t_sessioncount` VALUES ('896', '127.0.0.1', null, '2013-12-21 15:07:30');
INSERT INTO `t_sessioncount` VALUES ('897', '127.0.0.1', null, '2013-12-21 15:07:30');
INSERT INTO `t_sessioncount` VALUES ('898', '127.0.0.1', null, '2013-12-21 15:17:12');
INSERT INTO `t_sessioncount` VALUES ('899', '127.0.0.1', null, '2013-12-21 15:17:12');
INSERT INTO `t_sessioncount` VALUES ('900', '127.0.0.1', null, '2013-12-21 15:17:12');
INSERT INTO `t_sessioncount` VALUES ('901', '127.0.0.1', null, '2013-12-21 15:17:12');
INSERT INTO `t_sessioncount` VALUES ('902', '127.0.0.1', null, '2013-12-21 15:17:12');
INSERT INTO `t_sessioncount` VALUES ('903', '127.0.0.1', null, '2013-12-21 15:17:12');
INSERT INTO `t_sessioncount` VALUES ('904', '127.0.0.1', null, '2013-12-21 15:17:12');
INSERT INTO `t_sessioncount` VALUES ('905', '127.0.0.1', null, '2013-12-21 15:17:12');
INSERT INTO `t_sessioncount` VALUES ('906', '127.0.0.1', null, '2013-12-21 15:17:13');
INSERT INTO `t_sessioncount` VALUES ('907', '127.0.0.1', null, '2013-12-21 15:17:13');
INSERT INTO `t_sessioncount` VALUES ('908', '127.0.0.1', null, '2013-12-21 15:17:13');
INSERT INTO `t_sessioncount` VALUES ('909', '127.0.0.1', null, '2013-12-21 15:17:13');
INSERT INTO `t_sessioncount` VALUES ('910', '127.0.0.1', null, '2013-12-21 15:17:13');
INSERT INTO `t_sessioncount` VALUES ('911', '127.0.0.1', null, '2013-12-21 15:17:13');
INSERT INTO `t_sessioncount` VALUES ('912', '127.0.0.1', null, '2013-12-21 15:17:13');
INSERT INTO `t_sessioncount` VALUES ('913', '127.0.0.1', null, '2013-12-21 15:43:48');
INSERT INTO `t_sessioncount` VALUES ('914', '127.0.0.1', null, '2013-12-21 15:58:44');
INSERT INTO `t_sessioncount` VALUES ('915', '127.0.0.1', null, '2013-12-21 15:58:44');
INSERT INTO `t_sessioncount` VALUES ('916', '127.0.0.1', null, '2013-12-21 15:58:44');
INSERT INTO `t_sessioncount` VALUES ('917', '127.0.0.1', null, '2013-12-22 10:55:40');
INSERT INTO `t_sessioncount` VALUES ('918', '127.0.0.1', null, '2013-12-22 11:06:49');
INSERT INTO `t_sessioncount` VALUES ('919', '127.0.0.1', null, '2013-12-22 11:12:25');
INSERT INTO `t_sessioncount` VALUES ('920', '127.0.0.1', null, '2013-12-22 11:24:14');
INSERT INTO `t_sessioncount` VALUES ('921', '127.0.0.1', null, '2013-12-22 11:46:07');
INSERT INTO `t_sessioncount` VALUES ('922', '127.0.0.1', null, '2013-12-22 11:46:07');
INSERT INTO `t_sessioncount` VALUES ('923', '127.0.0.1', null, '2013-12-22 12:22:19');
INSERT INTO `t_sessioncount` VALUES ('924', '127.0.0.1', null, '2013-12-22 12:35:00');
INSERT INTO `t_sessioncount` VALUES ('925', '127.0.0.1', null, '2013-12-22 12:49:19');
INSERT INTO `t_sessioncount` VALUES ('926', '127.0.0.1', null, '2013-12-22 13:01:48');
INSERT INTO `t_sessioncount` VALUES ('927', '127.0.0.1', null, '2013-12-22 13:23:25');
INSERT INTO `t_sessioncount` VALUES ('928', '127.0.0.1', null, '2013-12-22 15:11:17');
INSERT INTO `t_sessioncount` VALUES ('929', '127.0.0.1', null, '2013-12-22 15:14:17');
INSERT INTO `t_sessioncount` VALUES ('930', '127.0.0.1', null, '2013-12-22 15:18:27');
INSERT INTO `t_sessioncount` VALUES ('931', '127.0.0.1', null, '2013-12-22 15:21:01');
INSERT INTO `t_sessioncount` VALUES ('932', '127.0.0.1', null, '2013-12-22 16:02:23');
INSERT INTO `t_sessioncount` VALUES ('933', '127.0.0.1', null, '2013-12-22 16:06:54');
INSERT INTO `t_sessioncount` VALUES ('934', '127.0.0.1', null, '2013-12-22 16:13:03');
INSERT INTO `t_sessioncount` VALUES ('935', '127.0.0.1', null, '2013-12-22 16:42:34');
INSERT INTO `t_sessioncount` VALUES ('936', '127.0.0.1', null, '2013-12-22 16:57:48');
INSERT INTO `t_sessioncount` VALUES ('937', '127.0.0.1', null, '2013-12-22 16:59:10');
INSERT INTO `t_sessioncount` VALUES ('938', '127.0.0.1', null, '2013-12-22 17:04:46');
INSERT INTO `t_sessioncount` VALUES ('939', '127.0.0.1', null, '2013-12-22 17:11:45');
INSERT INTO `t_sessioncount` VALUES ('940', '127.0.0.1', null, '2013-12-22 17:45:54');
INSERT INTO `t_sessioncount` VALUES ('941', '127.0.0.1', null, '2013-12-22 17:47:49');
INSERT INTO `t_sessioncount` VALUES ('942', '127.0.0.1', null, '2013-12-22 17:50:45');
INSERT INTO `t_sessioncount` VALUES ('943', '127.0.0.1', null, '2013-12-22 17:52:08');
INSERT INTO `t_sessioncount` VALUES ('944', '127.0.0.1', null, '2013-12-22 17:56:16');
INSERT INTO `t_sessioncount` VALUES ('945', '127.0.0.1', null, '2013-12-22 17:57:38');
INSERT INTO `t_sessioncount` VALUES ('946', '127.0.0.1', null, '2013-12-22 18:01:14');
INSERT INTO `t_sessioncount` VALUES ('947', '127.0.0.1', null, '2013-12-22 19:23:49');
INSERT INTO `t_sessioncount` VALUES ('948', '127.0.0.1', null, '2013-12-22 19:43:05');
INSERT INTO `t_sessioncount` VALUES ('949', '127.0.0.1', null, '2013-12-22 19:44:40');
INSERT INTO `t_sessioncount` VALUES ('950', '127.0.0.1', null, '2013-12-22 19:45:25');
INSERT INTO `t_sessioncount` VALUES ('951', '127.0.0.1', null, '2013-12-22 20:15:13');
INSERT INTO `t_sessioncount` VALUES ('952', '127.0.0.1', null, '2013-12-22 20:19:35');
INSERT INTO `t_sessioncount` VALUES ('953', '127.0.0.1', null, '2013-12-22 20:24:23');
INSERT INTO `t_sessioncount` VALUES ('954', '127.0.0.1', null, '2013-12-22 20:52:38');
INSERT INTO `t_sessioncount` VALUES ('955', '127.0.0.1', null, '2013-12-22 21:22:10');
INSERT INTO `t_sessioncount` VALUES ('956', '127.0.0.1', null, '2013-12-22 21:24:40');
INSERT INTO `t_sessioncount` VALUES ('957', '127.0.0.1', null, '2013-12-22 21:31:35');
INSERT INTO `t_sessioncount` VALUES ('958', '127.0.0.1', null, '2013-12-22 21:33:35');
INSERT INTO `t_sessioncount` VALUES ('959', '127.0.0.1', null, '2013-12-22 21:36:06');
INSERT INTO `t_sessioncount` VALUES ('960', '127.0.0.1', null, '2013-12-22 21:59:30');
INSERT INTO `t_sessioncount` VALUES ('961', '127.0.0.1', null, '2013-12-22 22:17:45');
INSERT INTO `t_sessioncount` VALUES ('962', '127.0.0.1', null, '2013-12-23 22:03:27');
INSERT INTO `t_sessioncount` VALUES ('963', '127.0.0.1', null, '2013-12-23 22:30:22');
INSERT INTO `t_sessioncount` VALUES ('964', '127.0.0.1', null, '2013-12-23 22:30:22');
INSERT INTO `t_sessioncount` VALUES ('965', '127.0.0.1', null, '2013-12-28 01:31:36');
INSERT INTO `t_sessioncount` VALUES ('966', '127.0.0.1', null, '2013-12-28 02:03:49');
INSERT INTO `t_sessioncount` VALUES ('967', '127.0.0.1', null, '2013-12-28 02:11:30');
INSERT INTO `t_sessioncount` VALUES ('968', '127.0.0.1', null, '2013-12-29 02:08:14');
INSERT INTO `t_sessioncount` VALUES ('969', '127.0.0.1', null, '2013-12-29 20:07:12');
INSERT INTO `t_sessioncount` VALUES ('970', '127.0.0.1', null, '2013-12-29 20:07:12');
INSERT INTO `t_sessioncount` VALUES ('971', '127.0.0.1', null, '2013-12-29 20:07:12');
INSERT INTO `t_sessioncount` VALUES ('972', '127.0.0.1', null, '2013-12-29 20:07:12');
INSERT INTO `t_sessioncount` VALUES ('973', '127.0.0.1', null, '2013-12-29 20:07:12');
INSERT INTO `t_sessioncount` VALUES ('974', '127.0.0.1', null, '2013-12-29 20:07:12');
INSERT INTO `t_sessioncount` VALUES ('975', '127.0.0.1', null, '2013-12-29 20:07:12');
INSERT INTO `t_sessioncount` VALUES ('976', '127.0.0.1', null, '2013-12-29 20:07:12');
INSERT INTO `t_sessioncount` VALUES ('977', '127.0.0.1', null, '2013-12-29 20:07:12');
INSERT INTO `t_sessioncount` VALUES ('978', '127.0.0.1', null, '2013-12-29 20:07:12');
INSERT INTO `t_sessioncount` VALUES ('979', '127.0.0.1', null, '2013-12-30 00:16:07');
INSERT INTO `t_sessioncount` VALUES ('980', '127.0.0.1', null, '2013-12-30 00:16:07');
INSERT INTO `t_sessioncount` VALUES ('981', '127.0.0.1', null, '2013-12-30 00:16:07');
INSERT INTO `t_sessioncount` VALUES ('982', '127.0.0.1', null, '2013-12-30 00:16:07');
INSERT INTO `t_sessioncount` VALUES ('983', '127.0.0.1', null, '2013-12-30 00:16:07');
INSERT INTO `t_sessioncount` VALUES ('984', '127.0.0.1', null, '2013-12-30 00:16:07');
INSERT INTO `t_sessioncount` VALUES ('985', '127.0.0.1', null, '2013-12-30 00:16:08');
INSERT INTO `t_sessioncount` VALUES ('986', '127.0.0.1', null, '2013-12-30 00:16:08');
INSERT INTO `t_sessioncount` VALUES ('987', '127.0.0.1', null, '2014-01-03 15:46:02');
INSERT INTO `t_sessioncount` VALUES ('988', '127.0.0.1', null, '2014-01-05 14:39:33');
INSERT INTO `t_sessioncount` VALUES ('989', '127.0.0.1', null, '2014-01-05 14:43:14');
INSERT INTO `t_sessioncount` VALUES ('990', '127.0.0.1', null, '2014-01-05 14:43:14');
INSERT INTO `t_sessioncount` VALUES ('991', '127.0.0.1', null, '2014-01-05 14:43:14');
INSERT INTO `t_sessioncount` VALUES ('992', '127.0.0.1', null, '2014-01-05 14:43:14');
INSERT INTO `t_sessioncount` VALUES ('993', '127.0.0.1', null, '2014-01-05 14:43:14');
INSERT INTO `t_sessioncount` VALUES ('994', '127.0.0.1', null, '2014-01-05 14:43:14');
INSERT INTO `t_sessioncount` VALUES ('995', '127.0.0.1', null, '2014-01-05 14:43:14');
INSERT INTO `t_sessioncount` VALUES ('996', '127.0.0.1', null, '2014-01-05 14:43:14');
INSERT INTO `t_sessioncount` VALUES ('997', '127.0.0.1', null, '2014-01-05 14:43:14');
INSERT INTO `t_sessioncount` VALUES ('998', '127.0.0.1', null, '2014-01-05 14:43:14');
INSERT INTO `t_sessioncount` VALUES ('999', '127.0.0.1', null, '2014-01-05 14:45:45');
INSERT INTO `t_sessioncount` VALUES ('1000', '127.0.0.1', null, '2014-01-05 16:52:31');
INSERT INTO `t_sessioncount` VALUES ('1001', '127.0.0.1', null, '2014-01-05 17:08:30');
INSERT INTO `t_sessioncount` VALUES ('1002', '127.0.0.1', null, '2014-01-05 17:20:15');
INSERT INTO `t_sessioncount` VALUES ('1003', '127.0.0.1', null, '2014-01-05 17:20:15');
INSERT INTO `t_sessioncount` VALUES ('1004', '127.0.0.1', null, '2014-01-05 17:50:50');
INSERT INTO `t_sessioncount` VALUES ('1005', '127.0.0.1', null, '2014-01-07 20:17:34');
INSERT INTO `t_sessioncount` VALUES ('1006', '127.0.0.1', null, '2014-01-16 20:17:12');
INSERT INTO `t_sessioncount` VALUES ('1007', '127.0.0.1', null, '2014-01-16 20:53:57');
INSERT INTO `t_sessioncount` VALUES ('1008', '127.0.0.1', null, '2014-01-16 22:58:50');
INSERT INTO `t_sessioncount` VALUES ('1009', '127.0.0.1', null, '2014-01-16 23:05:30');
INSERT INTO `t_sessioncount` VALUES ('1010', '127.0.0.1', null, '2014-01-16 23:08:19');
INSERT INTO `t_sessioncount` VALUES ('1011', '127.0.0.1', null, '2014-01-16 23:21:08');
INSERT INTO `t_sessioncount` VALUES ('1012', '127.0.0.1', null, '2014-01-17 00:16:59');
INSERT INTO `t_sessioncount` VALUES ('1013', '127.0.0.1', null, '2014-01-17 07:45:13');
INSERT INTO `t_sessioncount` VALUES ('1014', '127.0.0.1', null, '2014-01-17 08:14:10');
INSERT INTO `t_sessioncount` VALUES ('1015', '127.0.0.1', null, '2014-01-17 08:35:28');
INSERT INTO `t_sessioncount` VALUES ('1016', '127.0.0.1', null, '2014-01-17 08:52:06');
INSERT INTO `t_sessioncount` VALUES ('1017', '127.0.0.1', null, '2014-01-17 09:10:40');
INSERT INTO `t_sessioncount` VALUES ('1018', '127.0.0.1', null, '2014-01-17 09:10:40');
INSERT INTO `t_sessioncount` VALUES ('1019', '127.0.0.1', null, '2014-01-17 09:10:40');
INSERT INTO `t_sessioncount` VALUES ('1020', '127.0.0.1', null, '2014-01-17 09:10:40');
INSERT INTO `t_sessioncount` VALUES ('1021', '127.0.0.1', null, '2014-01-17 09:10:40');
INSERT INTO `t_sessioncount` VALUES ('1022', '127.0.0.1', null, '2014-01-17 09:10:41');
INSERT INTO `t_sessioncount` VALUES ('1023', '127.0.0.1', null, '2014-01-17 12:51:16');
INSERT INTO `t_sessioncount` VALUES ('1024', '127.0.0.1', null, '2014-01-17 12:56:33');
INSERT INTO `t_sessioncount` VALUES ('1025', '127.0.0.1', null, '2014-01-17 13:27:50');
INSERT INTO `t_sessioncount` VALUES ('1026', '127.0.0.1', null, '2014-01-17 13:41:19');
INSERT INTO `t_sessioncount` VALUES ('1027', '127.0.0.1', null, '2014-01-17 15:15:48');
INSERT INTO `t_sessioncount` VALUES ('1028', '127.0.0.1', null, '2014-01-17 15:58:08');
INSERT INTO `t_sessioncount` VALUES ('1029', '127.0.0.1', null, '2014-01-17 16:54:39');
INSERT INTO `t_sessioncount` VALUES ('1030', '127.0.0.1', null, '2014-01-17 17:00:34');
INSERT INTO `t_sessioncount` VALUES ('1031', '127.0.0.1', null, '2014-01-19 11:52:19');
INSERT INTO `t_sessioncount` VALUES ('1032', '127.0.0.1', null, '2014-01-19 12:09:27');
INSERT INTO `t_sessioncount` VALUES ('1033', '127.0.0.1', null, '2014-01-19 12:19:47');
INSERT INTO `t_sessioncount` VALUES ('1034', '127.0.0.1', null, '2014-01-19 12:42:19');
INSERT INTO `t_sessioncount` VALUES ('1035', '127.0.0.1', null, '2014-01-19 13:31:59');
INSERT INTO `t_sessioncount` VALUES ('1036', '127.0.0.1', null, '2014-01-19 13:38:07');
INSERT INTO `t_sessioncount` VALUES ('1037', '127.0.0.1', null, '2014-01-19 13:45:31');
INSERT INTO `t_sessioncount` VALUES ('1038', '127.0.0.1', null, '2014-01-19 16:17:31');
INSERT INTO `t_sessioncount` VALUES ('1039', '127.0.0.1', null, '2014-01-19 16:27:20');
INSERT INTO `t_sessioncount` VALUES ('1040', '127.0.0.1', null, '2014-01-19 16:32:34');
INSERT INTO `t_sessioncount` VALUES ('1041', '127.0.0.1', null, '2014-01-19 16:47:11');
INSERT INTO `t_sessioncount` VALUES ('1042', '127.0.0.1', null, '2014-01-19 16:56:38');
INSERT INTO `t_sessioncount` VALUES ('1043', '127.0.0.1', null, '2014-01-19 17:00:23');
INSERT INTO `t_sessioncount` VALUES ('1044', '127.0.0.1', null, '2014-01-19 17:02:42');
INSERT INTO `t_sessioncount` VALUES ('1045', '127.0.0.1', null, '2014-01-19 17:07:09');
INSERT INTO `t_sessioncount` VALUES ('1046', '127.0.0.1', null, '2014-01-19 17:08:18');
INSERT INTO `t_sessioncount` VALUES ('1047', '127.0.0.1', null, '2014-01-19 18:06:40');
INSERT INTO `t_sessioncount` VALUES ('1048', '127.0.0.1', null, '2014-01-19 18:14:19');
INSERT INTO `t_sessioncount` VALUES ('1049', '127.0.0.1', null, '2014-01-19 18:17:12');
INSERT INTO `t_sessioncount` VALUES ('1050', '127.0.0.1', null, '2014-01-19 18:30:22');
INSERT INTO `t_sessioncount` VALUES ('1051', '127.0.0.1', null, '2014-01-19 19:05:33');
INSERT INTO `t_sessioncount` VALUES ('1052', '127.0.0.1', null, '2014-01-19 19:08:15');
INSERT INTO `t_sessioncount` VALUES ('1053', '127.0.0.1', null, '2014-01-19 19:10:49');
INSERT INTO `t_sessioncount` VALUES ('1054', '127.0.0.1', null, '2014-01-19 19:23:01');
INSERT INTO `t_sessioncount` VALUES ('1055', '127.0.0.1', null, '2014-01-19 21:48:15');
INSERT INTO `t_sessioncount` VALUES ('1056', '127.0.0.1', null, '2014-01-20 08:21:46');
INSERT INTO `t_sessioncount` VALUES ('1057', '127.0.0.1', null, '2014-01-20 09:03:09');
INSERT INTO `t_sessioncount` VALUES ('1058', '127.0.0.1', null, '2014-01-20 09:04:52');
INSERT INTO `t_sessioncount` VALUES ('1059', '127.0.0.1', null, '2014-01-20 09:05:46');
INSERT INTO `t_sessioncount` VALUES ('1060', '127.0.0.1', null, '2014-01-20 09:10:58');
INSERT INTO `t_sessioncount` VALUES ('1061', '127.0.0.1', null, '2014-01-20 09:50:37');
INSERT INTO `t_sessioncount` VALUES ('1062', '127.0.0.1', null, '2014-01-20 16:10:00');
INSERT INTO `t_sessioncount` VALUES ('1063', '127.0.0.1', null, '2014-01-20 21:26:52');
INSERT INTO `t_sessioncount` VALUES ('1064', '127.0.0.1', null, '2014-01-20 21:45:47');
INSERT INTO `t_sessioncount` VALUES ('1065', '127.0.0.1', null, '2014-01-20 21:51:34');
INSERT INTO `t_sessioncount` VALUES ('1066', '127.0.0.1', null, '2014-01-20 21:54:31');
INSERT INTO `t_sessioncount` VALUES ('1067', '127.0.0.1', null, '2014-01-21 19:18:43');
INSERT INTO `t_sessioncount` VALUES ('1068', '127.0.0.1', null, '2014-01-22 14:41:14');
INSERT INTO `t_sessioncount` VALUES ('1069', '127.0.0.1', null, '2014-01-22 16:46:29');
INSERT INTO `t_sessioncount` VALUES ('1070', '127.0.0.1', null, '2014-01-22 16:54:11');
INSERT INTO `t_sessioncount` VALUES ('1071', '127.0.0.1', null, '2014-01-22 17:17:30');
INSERT INTO `t_sessioncount` VALUES ('1072', '127.0.0.1', null, '2014-01-22 17:29:45');
INSERT INTO `t_sessioncount` VALUES ('1073', '127.0.0.1', null, '2014-01-22 19:01:28');
INSERT INTO `t_sessioncount` VALUES ('1074', '127.0.0.1', null, '2014-01-25 13:20:00');
INSERT INTO `t_sessioncount` VALUES ('1075', '127.0.0.1', null, '2014-01-25 16:23:08');
INSERT INTO `t_sessioncount` VALUES ('1076', '127.0.0.1', null, '2014-01-25 16:38:19');
INSERT INTO `t_sessioncount` VALUES ('1077', '127.0.0.1', null, '2014-01-25 16:47:30');
INSERT INTO `t_sessioncount` VALUES ('1078', '127.0.0.1', null, '2014-01-25 17:23:31');
INSERT INTO `t_sessioncount` VALUES ('1079', '127.0.0.1', null, '2014-01-25 17:27:40');
INSERT INTO `t_sessioncount` VALUES ('1080', '127.0.0.1', null, '2014-01-25 17:44:40');
INSERT INTO `t_sessioncount` VALUES ('1081', '127.0.0.1', null, '2014-01-25 17:46:12');
INSERT INTO `t_sessioncount` VALUES ('1082', '127.0.0.1', null, '2014-01-25 17:47:57');
INSERT INTO `t_sessioncount` VALUES ('1083', '127.0.0.1', null, '2014-01-25 17:49:03');
INSERT INTO `t_sessioncount` VALUES ('1084', '127.0.0.1', null, '2014-01-25 19:51:39');
INSERT INTO `t_sessioncount` VALUES ('1085', '127.0.0.1', null, '2014-01-25 20:12:22');
INSERT INTO `t_sessioncount` VALUES ('1086', '127.0.0.1', null, '2014-01-25 20:19:04');
INSERT INTO `t_sessioncount` VALUES ('1087', '127.0.0.1', null, '2014-01-26 14:28:37');
INSERT INTO `t_sessioncount` VALUES ('1088', '127.0.0.1', null, '2014-01-26 17:55:45');
INSERT INTO `t_sessioncount` VALUES ('1089', '127.0.0.1', null, '2014-01-26 19:37:10');
INSERT INTO `t_sessioncount` VALUES ('1090', '127.0.0.1', null, '2014-01-26 20:31:12');
INSERT INTO `t_sessioncount` VALUES ('1091', '127.0.0.1', null, '2014-01-26 21:12:10');
INSERT INTO `t_sessioncount` VALUES ('1092', '127.0.0.1', null, '2014-01-27 10:28:34');
INSERT INTO `t_sessioncount` VALUES ('1093', '127.0.0.1', null, '2014-01-27 10:53:30');
INSERT INTO `t_sessioncount` VALUES ('1094', '127.0.0.1', null, '2014-01-27 13:34:57');
INSERT INTO `t_sessioncount` VALUES ('1095', '127.0.0.1', null, '2014-01-27 14:51:41');
INSERT INTO `t_sessioncount` VALUES ('1096', '127.0.0.1', null, '2014-01-27 15:06:11');
INSERT INTO `t_sessioncount` VALUES ('1097', '127.0.0.1', null, '2014-01-27 15:07:05');
INSERT INTO `t_sessioncount` VALUES ('1098', '127.0.0.1', null, '2014-01-27 15:08:14');
INSERT INTO `t_sessioncount` VALUES ('1099', '127.0.0.1', null, '2014-01-27 15:20:29');
INSERT INTO `t_sessioncount` VALUES ('1100', '127.0.0.1', null, '2014-01-27 15:26:02');
INSERT INTO `t_sessioncount` VALUES ('1101', '127.0.0.1', null, '2014-01-27 15:40:31');
INSERT INTO `t_sessioncount` VALUES ('1102', '127.0.0.1', null, '2014-01-27 15:42:07');
INSERT INTO `t_sessioncount` VALUES ('1103', '127.0.0.1', null, '2014-01-27 15:43:32');
INSERT INTO `t_sessioncount` VALUES ('1104', '127.0.0.1', null, '2014-01-27 15:45:20');

-- ----------------------------
-- Table structure for `t_sms`
-- ----------------------------
DROP TABLE IF EXISTS `t_sms`;
CREATE TABLE `t_sms` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phone` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  `content` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `sendTime` datetime DEFAULT NULL,
  `type` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `returnCode` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sendStatus` varchar(2) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_sms
-- ----------------------------

-- ----------------------------
-- Table structure for `t_spec`
-- ----------------------------
DROP TABLE IF EXISTS `t_spec`;
CREATE TABLE `t_spec` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `productID` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `specColor` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `specSize` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `specStock` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `specPrice` decimal(8,2) DEFAULT NULL,
  `specStatus` char(1) COLLATE utf8_unicode_ci DEFAULT 'n',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_spec
-- ----------------------------
INSERT INTO `t_spec` VALUES ('1', '10316', '红色', '红色2.1', '123', '122.00', 'n');
INSERT INTO `t_spec` VALUES ('2', '10316', '蓝色', '蓝色3.1', '11', '121.00', 'n');
INSERT INTO `t_spec` VALUES ('3', '10280', '12', '1', '12', '12.00', 'y');
INSERT INTO `t_spec` VALUES ('4', '10280', '13', '12', '21', '21.00', 'y');
INSERT INTO `t_spec` VALUES ('5', '10310', '金色', '1', '12', '5755.00', 'y');
INSERT INTO `t_spec` VALUES ('6', '10310', '银色', '1', '12', '5800.00', 'y');

-- ----------------------------
-- Table structure for `t_systemlog`
-- ----------------------------
DROP TABLE IF EXISTS `t_systemlog`;
CREATE TABLE `t_systemlog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(45) NOT NULL,
  `content` varchar(500) NOT NULL,
  `type` int(11) DEFAULT NULL,
  `account` varchar(45) DEFAULT NULL,
  `loginIP` varchar(15) DEFAULT NULL,
  `logintime` datetime DEFAULT NULL,
  `loginArea` varchar(45) DEFAULT NULL,
  `diffAreaLogin` char(1) DEFAULT 'n',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=926 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_systemlog
-- ----------------------------
INSERT INTO `t_systemlog` VALUES ('861', 'login', 'login', '1', 'admin', '127.0.0.1', '2018-03-04 21:23:54', null, 'n');
INSERT INTO `t_systemlog` VALUES ('862', 'login', 'login', '1', 'admin', '127.0.0.1', '2018-03-05 15:31:36', null, 'n');
INSERT INTO `t_systemlog` VALUES ('863', 'login', 'login', '1', 'admin', '127.0.0.1', '2018-03-06 13:38:19', null, 'n');
INSERT INTO `t_systemlog` VALUES ('864', 'login', 'login', '1', 'admin', '127.0.0.1', '2018-03-06 13:59:19', null, 'n');
INSERT INTO `t_systemlog` VALUES ('865', 'login', 'login', '1', 'admin', '127.0.0.1', '2018-03-06 14:01:53', null, 'n');
INSERT INTO `t_systemlog` VALUES ('866', 'login', 'login', '1', 'admin', '127.0.0.1', '2018-03-06 14:10:16', null, 'n');
INSERT INTO `t_systemlog` VALUES ('867', 'login', 'login', '1', 'admin', '127.0.0.1', '2018-03-06 14:25:29', null, 'n');
INSERT INTO `t_systemlog` VALUES ('868', 'loginOut', 'loginOut', '1', 'admin', '127.0.0.1', '2018-03-06 15:49:19', null, 'n');
INSERT INTO `t_systemlog` VALUES ('869', 'login', 'login', '1', 'admin', '127.0.0.1', '2018-03-06 15:50:02', null, 'n');
INSERT INTO `t_systemlog` VALUES ('870', 'loginOut', 'loginOut', '1', 'admin', '127.0.0.1', '2018-03-06 15:50:05', null, 'n');
INSERT INTO `t_systemlog` VALUES ('871', 'login', 'login', '1', 'admin', '127.0.0.1', '2018-03-06 15:50:50', null, 'n');
INSERT INTO `t_systemlog` VALUES ('872', 'loginOut', 'loginOut', '1', 'admin', '127.0.0.1', '2018-03-06 15:50:54', null, 'n');
INSERT INTO `t_systemlog` VALUES ('873', 'login', 'login', '1', 'admin', '127.0.0.1', '2018-03-06 17:00:57', null, 'n');
INSERT INTO `t_systemlog` VALUES ('874', 'login', 'login', '1', 'admin', '127.0.0.1', '2018-03-06 17:03:26', null, 'n');
INSERT INTO `t_systemlog` VALUES ('875', 'login', 'login', '1', 'admin', '127.0.0.1', '2018-03-07 13:24:41', null, 'n');
INSERT INTO `t_systemlog` VALUES ('876', 'login', 'login', '1', 'admin', '0:0:0:0:0:0:0:1', '2018-03-09 14:01:34', '内网IP内网IP[local]', 'y');
INSERT INTO `t_systemlog` VALUES ('877', 'login', 'login', '1', 'admin', '0:0:0:0:0:0:0:1', '2018-03-10 16:23:09', '内网IP内网IP[local]', 'n');
INSERT INTO `t_systemlog` VALUES ('878', 'login', 'login', '1', 'admin', '127.0.0.1', '2018-03-12 13:54:16', null, 'n');
INSERT INTO `t_systemlog` VALUES ('879', 'login', 'login', '1', 'admin', '0:0:0:0:0:0:0:1', '2018-03-16 16:55:42', '内网IP内网IP[local]', 'y');
INSERT INTO `t_systemlog` VALUES ('880', 'login', 'login', '1', 'admin', '0:0:0:0:0:0:0:1', '2018-03-16 21:17:23', '内网IP内网IP[local]', 'n');
INSERT INTO `t_systemlog` VALUES ('881', 'loginOut', 'loginOut', '1', 'admin', '0:0:0:0:0:0:0:1', '2018-03-16 21:17:38', '内网IP内网IP[local]', 'n');
INSERT INTO `t_systemlog` VALUES ('882', 'login', 'login', '1', 'admin', '0:0:0:0:0:0:0:1', '2018-03-16 21:17:40', '内网IP内网IP[local]', 'n');
INSERT INTO `t_systemlog` VALUES ('883', 'login', 'login', '1', 'admin', '192.168.0.108', '2018-03-16 21:25:12', '内网IP内网IP[local]', 'n');
INSERT INTO `t_systemlog` VALUES ('884', 'login', 'login', '1', 'admin', '0:0:0:0:0:0:0:1', '2018-03-18 21:30:59', '内网IP内网IP[local]', 'n');
INSERT INTO `t_systemlog` VALUES ('885', 'login', 'login', '1', 'admin', '0:0:0:0:0:0:0:1', '2018-03-18 21:36:50', '内网IP内网IP[local]', 'n');
INSERT INTO `t_systemlog` VALUES ('886', 'login', 'login', '1', 'admin', '0:0:0:0:0:0:0:1', '2018-03-18 21:40:03', '内网IP内网IP[local]', 'n');
INSERT INTO `t_systemlog` VALUES ('887', 'login', 'login', '1', 'admin', '0:0:0:0:0:0:0:1', '2018-03-18 21:56:21', '内网IP内网IP[local]', 'n');
INSERT INTO `t_systemlog` VALUES ('888', 'login', 'login', '1', 'admin', '0:0:0:0:0:0:0:1', '2018-03-18 22:21:30', '内网IP内网IP[local]', 'n');
INSERT INTO `t_systemlog` VALUES ('889', 'login', 'login', '1', 'admin', '0:0:0:0:0:0:0:1', '2018-03-19 16:46:58', '内网IP内网IP[local]', 'n');
INSERT INTO `t_systemlog` VALUES ('890', 'login', 'login', '1', 'admin', '192.168.0.59', '2018-03-19 22:29:19', '内网IP内网IP[local]', 'n');
INSERT INTO `t_systemlog` VALUES ('891', 'login', 'login', '1', 'admin', '192.168.0.59', '2018-03-20 13:07:51', '内网IP内网IP[local]', 'n');
INSERT INTO `t_systemlog` VALUES ('892', 'login', 'login', '1', 'admin', '0:0:0:0:0:0:0:1', '2018-03-20 13:14:25', '内网IP内网IP[local]', 'n');
INSERT INTO `t_systemlog` VALUES ('893', 'login', 'login', '1', 'admin', '0:0:0:0:0:0:0:1', '2018-03-21 16:13:17', '内网IP内网IP[local]', 'n');
INSERT INTO `t_systemlog` VALUES ('894', 'login', 'login', '1', 'admin', '0:0:0:0:0:0:0:1', '2018-03-21 16:15:58', '内网IP内网IP[local]', 'n');
INSERT INTO `t_systemlog` VALUES ('895', 'login', 'login', '1', 'admin', '0:0:0:0:0:0:0:1', '2018-03-21 16:20:40', '内网IP内网IP[local]', 'n');
INSERT INTO `t_systemlog` VALUES ('896', 'login', 'login', '1', 'admin', '0:0:0:0:0:0:0:1', '2018-03-21 16:23:18', '内网IP内网IP[local]', 'n');
INSERT INTO `t_systemlog` VALUES ('897', 'login', 'login', '1', 'admin', '0:0:0:0:0:0:0:1', '2018-03-21 16:27:51', '内网IP内网IP[local]', 'n');
INSERT INTO `t_systemlog` VALUES ('898', 'login', 'login', '1', 'admin', '0:0:0:0:0:0:0:1', '2018-03-21 16:30:13', '内网IP内网IP[local]', 'n');
INSERT INTO `t_systemlog` VALUES ('899', 'login', 'login', '1', 'admin', '0:0:0:0:0:0:0:1', '2018-03-21 16:45:44', '内网IP内网IP[local]', 'n');
INSERT INTO `t_systemlog` VALUES ('900', 'login', 'login', '1', 'admin', '0:0:0:0:0:0:0:1', '2018-03-21 17:33:49', '内网IP内网IP[local]', 'n');
INSERT INTO `t_systemlog` VALUES ('901', 'login', 'login', '1', 'admin', '0:0:0:0:0:0:0:1', '2018-03-22 13:14:28', '内网IP内网IP[local]', 'n');
INSERT INTO `t_systemlog` VALUES ('902', 'login', 'login', '1', 'admin', '0:0:0:0:0:0:0:1', '2018-03-22 13:14:40', '内网IP内网IP[local]', 'n');
INSERT INTO `t_systemlog` VALUES ('903', 'login', 'login', '1', 'admin', '0:0:0:0:0:0:0:1', '2018-03-22 13:15:58', '内网IP内网IP[local]', 'n');
INSERT INTO `t_systemlog` VALUES ('904', 'login', 'login', '1', 'admin', '0:0:0:0:0:0:0:1', '2018-03-22 13:16:18', '内网IP内网IP[local]', 'n');
INSERT INTO `t_systemlog` VALUES ('905', 'login', 'login', '1', 'admin', '0:0:0:0:0:0:0:1', '2018-03-22 13:29:58', '内网IP内网IP[local]', 'n');
INSERT INTO `t_systemlog` VALUES ('906', 'login', 'login', '1', 'admin', '0:0:0:0:0:0:0:1', '2018-03-22 17:19:52', '内网IP内网IP[local]', 'n');
INSERT INTO `t_systemlog` VALUES ('907', 'login', 'login', '1', 'admin', '0:0:0:0:0:0:0:1', '2018-03-22 17:31:21', null, 'n');
INSERT INTO `t_systemlog` VALUES ('908', 'login', 'login', '1', 'admin', '0:0:0:0:0:0:0:1', '2018-03-22 17:36:53', '内网IP内网IP[local]', 'n');
INSERT INTO `t_systemlog` VALUES ('909', 'login', 'login', '1', 'admin', '0:0:0:0:0:0:0:1', '2018-03-22 17:41:39', '内网IP内网IP[local]', 'n');
INSERT INTO `t_systemlog` VALUES ('910', 'login', 'login', '1', 'admin', '0:0:0:0:0:0:0:1', '2018-03-22 17:51:05', '内网IP内网IP[local]', 'n');
INSERT INTO `t_systemlog` VALUES ('911', 'login', 'login', '1', 'admin', '0:0:0:0:0:0:0:1', '2018-03-22 17:53:41', '内网IP内网IP[local]', 'n');
INSERT INTO `t_systemlog` VALUES ('912', 'login', 'login', '1', 'admin', '0:0:0:0:0:0:0:1', '2018-03-22 19:13:58', '内网IP内网IP[local]', 'n');
INSERT INTO `t_systemlog` VALUES ('913', 'login', 'login', '1', 'admin', '0:0:0:0:0:0:0:1', '2018-03-22 19:20:55', '内网IP内网IP[local]', 'n');
INSERT INTO `t_systemlog` VALUES ('914', 'login', 'login', '1', 'admin', '0:0:0:0:0:0:0:1', '2018-03-22 19:23:09', null, 'n');
INSERT INTO `t_systemlog` VALUES ('915', 'login', 'login', '1', 'admin', '0:0:0:0:0:0:0:1', '2018-03-22 19:26:12', '内网IP内网IP[local]', 'n');
INSERT INTO `t_systemlog` VALUES ('916', 'login', 'login', '1', 'admin', '0:0:0:0:0:0:0:1', '2018-03-22 19:51:57', '内网IP内网IP[local]', 'n');
INSERT INTO `t_systemlog` VALUES ('917', 'login', 'login', '1', 'admin', '0:0:0:0:0:0:0:1', '2018-03-22 19:59:43', '内网IP内网IP[local]', 'n');
INSERT INTO `t_systemlog` VALUES ('918', 'login', 'login', '1', 'admin', '0:0:0:0:0:0:0:1', '2018-03-22 20:30:40', '内网IP内网IP[local]', 'n');
INSERT INTO `t_systemlog` VALUES ('919', 'login', 'login', '1', 'admin', '0:0:0:0:0:0:0:1', '2018-03-22 20:53:37', '内网IP内网IP[local]', 'n');
INSERT INTO `t_systemlog` VALUES ('920', 'login', 'login', '1', 'admin', '0:0:0:0:0:0:0:1', '2018-03-22 20:57:05', '内网IP内网IP[local]', 'n');
INSERT INTO `t_systemlog` VALUES ('921', 'login', 'login', '1', 'admin', '0:0:0:0:0:0:0:1', '2018-03-22 21:51:09', '内网IP内网IP[local]', 'n');
INSERT INTO `t_systemlog` VALUES ('922', 'login', 'login', '1', 'admin', '0:0:0:0:0:0:0:1', '2018-03-22 21:53:45', '内网IP内网IP[local]', 'n');
INSERT INTO `t_systemlog` VALUES ('923', 'login', 'login', '1', 'admin', '0:0:0:0:0:0:0:1', '2018-03-22 21:58:34', '内网IP内网IP[local]', 'n');
INSERT INTO `t_systemlog` VALUES ('924', 'login', 'login', '1', 'admin', '0:0:0:0:0:0:0:1', '2018-03-23 19:55:49', '内网IP内网IP[local]', 'n');
INSERT INTO `t_systemlog` VALUES ('925', 'login', 'login', '1', 'admin', '0:0:0:0:0:0:0:1', '2018-03-23 20:43:49', '内网IP内网IP[local]', 'n');

-- ----------------------------
-- Table structure for `t_systemsetting`
-- ----------------------------
DROP TABLE IF EXISTS `t_systemsetting`;
CREATE TABLE `t_systemsetting` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `systemCode` varchar(45) DEFAULT NULL,
  `name` varchar(100) NOT NULL,
  `www` varchar(100) NOT NULL,
  `log` varchar(100) DEFAULT NULL,
  `title` varchar(45) NOT NULL,
  `description` varchar(45) NOT NULL,
  `keywords` varchar(100) NOT NULL,
  `shortcuticon` varchar(100) NOT NULL,
  `address` varchar(100) DEFAULT NULL,
  `tel` varchar(100) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `icp` varchar(45) NOT NULL,
  `isopen` varchar(8) NOT NULL DEFAULT 'y',
  `closeMsg` varchar(500) DEFAULT NULL,
  `qq` varchar(25) DEFAULT NULL,
  `imageRootPath` varchar(45) DEFAULT NULL,
  `manageHttp` varchar(45) DEFAULT NULL,
  `defaultProductImg` varchar(145) DEFAULT NULL,
  `style` varchar(20) DEFAULT 'default',
  `version` varchar(145) DEFAULT NULL,
  `qqHelpHtml` text,
  `images` text,
  `manageLeftTreeLeafIcon` varchar(100) DEFAULT NULL,
  `statisticsCode` varchar(1000) DEFAULT NULL,
  `openResponsive` varchar(1) DEFAULT 'y',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_systemsetting
-- ----------------------------
INSERT INTO `t_systemsetting` VALUES ('1', '图灵学院双十一电商平台', '图灵学院双十一电商平台', 'http://localhost:8080/shop-web', 'http://localhost:8080/shop-web/resource/images/letter.gif', '图灵学院双十一电商平台', '图灵学院双十一电商平台', '图灵学院双十一电商平台', 'http://localhost:8080/jshop/resource/images/favicon.png', '湖南省长沙市岳麓区企业广场', '400-666-8888', '245553999@qq.com', '©京ICP备1302819号', 'true', '网站维护中 图灵学院 http://tuling.ke.qq.com', null, 'http://localhost:8082/shop-admin/', 'http://localhost:8080/shop-web', 'http://localhost:8080/shop-web/resource/images/loading7.gif', 'united', '系统版本号:1.1.0.0000.bate.', '<div>\r\n	<a target=\"_blank\" href=\"http://wpa.qq.com/msgrd?v=3&uin=543089122&site=qq&menu=yes\"><img border=\"0\" src=\"http://wpa.qq.com/pa?p=2:543089122:51\" alt=\"QQ店小二,点击这里给我发消息\" title=\"QQ店小二,点击这里给我发消息\" /></a> <a target=\"_blank\" href=\"http://wpa.qq.com/msgrd?v=3&uin=543089122&site=qq&menu=yes\"><img border=\"0\" src=\"http://wpa.qq.com/pa?p=2:543089122:51\" alt=\"QQ店小三,点击这里给我发消息\" title=\"QQ店小三,点击这里给我发消息\" /></a> <a target=\"_blank\" href=\"http://wpa.qq.com/msgrd?v=3&uin=543089122&site=qq&menu=yes\"><img border=\"0\" src=\"http://wpa.qq.com/pa?p=2:543089122:51\" alt=\"QQ店小四,点击这里给我发消息\" title=\"QQ店小四,点击这里给我发消息\" /></a> \r\n</div>', '/attached/image/20140304/1393899445649_3.jpg,/attached/image/20140304/1393900297582_3.jpg,/attached/image/20140304/1393902455326_3.jpg,/attached/image/20140304/1393900298738_3.jpg', 'http://localhost:8080/shop-web/resource/images/letter.gif', '图灵学院 http://tuling.ke.qq.com', 'y');

-- ----------------------------
-- Table structure for `t_task`
-- ----------------------------
DROP TABLE IF EXISTS `t_task`;
CREATE TABLE `t_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `sleep` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `unit` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `nextWorkTime` datetime NOT NULL,
  `currentStatus` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `clz` varchar(145) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_task
-- ----------------------------
INSERT INTO `t_task` VALUES ('1', 'SystemAutoNotifyTask', '系统自动到货通知定时器', '1', 'HOURS', '2012-10-11 00:00:00', 'wait', 'net.jeeshop.core.task.SystemAutoNotifyTask');
INSERT INTO `t_task` VALUES ('2', 'ManageIndexReportTask', '后台首页统计数据定时器', '15', 'MINUTES', '2012-10-11 00:00:00', 'wait', 'net.jeeshop.core.task.ManageIndexReportTask');

-- ----------------------------
-- Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(100) NOT NULL,
  `createtime` datetime DEFAULT NULL,
  `updatetime` datetime DEFAULT NULL,
  `createAccount` varchar(45) DEFAULT NULL,
  `updateAccount` varchar(45) DEFAULT NULL,
  `status` varchar(2) DEFAULT 'y',
  `rid` varchar(45) DEFAULT NULL,
  `nickname` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `nickname_UNIQUE` (`nickname`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '2013-07-30 20:53:09', '2014-03-17 21:24:59', null, 'admin', 'y', '1', '超级管理员', null);
INSERT INTO `t_user` VALUES ('8', 'select', 'e10adc3949ba59abbe56e057f20f883e', '2013-10-05 13:46:59', '2014-03-17 21:25:42', null, 'admin', 'n', '6', '查看权限', null);
INSERT INTO `t_user` VALUES ('12', 'test1', 'e10adc3949ba59abbe56e057f20f883e', '2014-02-13 12:22:42', '2014-04-14 20:18:58', 'admin', null, 'y', '6', '测试账号', null);
