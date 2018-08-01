/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.22 : Database - aos
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`aos` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `aos`;

/*Table structure for table `liu_test` */

DROP TABLE IF EXISTS `liu_test`;

CREATE TABLE `liu_test` (
  `id_` varchar(60) NOT NULL DEFAULT '0' COMMENT 'ID',
  `name_` varchar(100) DEFAULT '0' COMMENT '姓名',
  `address_` varchar(100) DEFAULT '3' COMMENT '地址',
  `sex_` varchar(2) DEFAULT '0' COMMENT '性别',
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='刘';

/*Data for the table `liu_test` */

insert  into `liu_test`(`id_`,`name_`,`address_`,`sex_`) values ('1','liu','北京','1'),('2','li','合肥','2'),('3','wang','洛阳','2'),('4','sun','西安','1');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
