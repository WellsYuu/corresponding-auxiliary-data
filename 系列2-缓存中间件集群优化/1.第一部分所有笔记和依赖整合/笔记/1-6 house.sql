/*
SQLyog Ultimate v11.24 (32 bit)
MySQL - 5.6.27 : Database - guns
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `tbl_house` */

DROP TABLE IF EXISTS `tbl_house`;

CREATE TABLE `tbl_house` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `house_user` varchar(32) DEFAULT NULL,
  `house_address` varchar(50) DEFAULT NULL,
  `house_time` datetime DEFAULT NULL,
  `house_desc` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8 COMMENT='房屋管理表';

/*Data for the table `tbl_house` */

insert  into `tbl_house`(`id`,`house_user`,`house_address`,`house_time`,`house_desc`) values (61,'张三丰Test','五台山悬空寺','2017-12-28 00:00:00','五台山悬空寺'),(62,'狄仁杰Test','狄仁杰','2017-12-04 00:00:00','狄仁杰');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*
 * Copyright [$tody.year] [Wales Yu of copyright owner]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
