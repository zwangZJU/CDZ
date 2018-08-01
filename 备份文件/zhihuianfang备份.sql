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

/*Table structure for table `user_data` */

DROP TABLE IF EXISTS `user_data`;

CREATE TABLE `user_data` (
  `id_` varchar(255) NOT NULL COMMENT '序号',
  `status` varchar(255) DEFAULT NULL COMMENT '状态',
  `arrange_withdraw` varchar(255) DEFAULT NULL COMMENT '布撤防',
  `userId_` varchar(255) DEFAULT NULL COMMENT '用户编号',
  `user_name` varchar(255) DEFAULT NULL COMMENT '用户名称',
  `user_address` varchar(400) DEFAULT NULL COMMENT '用户地址',
  `sub_center` varchar(255) DEFAULT NULL COMMENT '分中心',
  `cross_road` varchar(255) DEFAULT NULL COMMENT '交叉路',
  `user_type` varchar(255) DEFAULT NULL COMMENT '用户类型',
  `user_type2` varchar(255) DEFAULT NULL COMMENT '用户类型2',
  `phone` varchar(255) DEFAULT NULL COMMENT '电话',
  `fax` varchar(255) DEFAULT NULL COMMENT '传真',
  `host_type` varchar(255) DEFAULT NULL COMMENT '主机类型',
  `video_linkage` varchar(255) DEFAULT NULL COMMENT '视频联动',
  `head` varchar(255) DEFAULT NULL COMMENT '负责人',
  `head_phone` varchar(255) DEFAULT NULL COMMENT '负责人电话',
  `test_period` varchar(255) DEFAULT NULL COMMENT '测试间隔',
  `pay_date` datetime DEFAULT NULL COMMENT '缴费截止日期',
  `guarantee_time` datetime DEFAULT NULL COMMENT '保修截止日期',
  `check_status` varchar(255) DEFAULT NULL COMMENT '核查状态',
  `arrearage` varchar(255) DEFAULT NULL COMMENT '欠费',
  `shut_down` varchar(255) DEFAULT NULL COMMENT '停机',
  `town` varchar(255) DEFAULT NULL COMMENT '镇所',
  `town_phone` varchar(255) DEFAULT NULL COMMENT '镇所电话',
  `police_station` varchar(255) DEFAULT NULL COMMENT '所属派出所',
  `network` varchar(400) DEFAULT NULL COMMENT '网络',
  `police_phone` varchar(255) DEFAULT NULL COMMENT '派出所电话',
  `host_address` varchar(255) DEFAULT NULL COMMENT '主机位置',
  `insatll_date` datetime DEFAULT NULL COMMENT '安装日期',
  `code42` varchar(255) DEFAULT NULL COMMENT '42代码',
  `withdraw_date` datetime DEFAULT NULL COMMENT '撤防时间',
  `arrange_date` datetime DEFAULT NULL COMMENT '布防时间',
  `last_date` datetime DEFAULT NULL COMMENT '最后来信号时间',
  `builders` varchar(255) DEFAULT NULL COMMENT '施工人员',
  `police_unit` varchar(255) DEFAULT NULL COMMENT '出警单位',
  `host_phone` varchar(255) DEFAULT NULL COMMENT '主机电话',
  `prewithdraw_date` datetime DEFAULT NULL COMMENT '预定撤防时间',
  `prearrange_date` datetime DEFAULT NULL COMMENT '预定布防时间',
  `group_` varchar(255) DEFAULT NULL COMMENT '组',
  `entry_clerk` varchar(255) DEFAULT NULL COMMENT '录入员',
  `inspection_staff` varchar(255) DEFAULT NULL COMMENT '巡检人员',
  `sms_code` varchar(255) DEFAULT NULL COMMENT '短信号码',
  `downtime` datetime DEFAULT NULL COMMENT '停机时间',
  `phone1` varchar(255) DEFAULT NULL COMMENT '电话1',
  `phone2` varchar(255) DEFAULT NULL COMMENT '电话2',
  `phone3` varchar(255) DEFAULT NULL COMMENT '电话3',
  `head_phone2` varchar(255) DEFAULT NULL COMMENT '负责人电话2',
  `net_date` datetime DEFAULT NULL COMMENT '入网日期',
  `communication_line` varchar(255) DEFAULT NULL COMMENT '通讯线路',
  `power_address` varchar(255) DEFAULT NULL COMMENT '电源位置',
  `phone_address` varchar(255) DEFAULT NULL COMMENT '电话位置',
  `keyboard_address` varchar(255) DEFAULT NULL COMMENT '键盘位置',
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户资料';

/*Data for the table `user_data` */

insert  into `user_data`(`id_`,`status`,`arrange_withdraw`,`userId_`,`user_name`,`user_address`,`sub_center`,`cross_road`,`user_type`,`user_type2`,`phone`,`fax`,`host_type`,`video_linkage`,`head`,`head_phone`,`test_period`,`pay_date`,`guarantee_time`,`check_status`,`arrearage`,`shut_down`,`town`,`town_phone`,`police_station`,`network`,`police_phone`,`host_address`,`insatll_date`,`code42`,`withdraw_date`,`arrange_date`,`last_date`,`builders`,`police_unit`,`host_phone`,`prewithdraw_date`,`prearrange_date`,`group_`,`entry_clerk`,`inspection_staff`,`sms_code`,`downtime`,`phone1`,`phone2`,`phone3`,`head_phone2`,`net_date`,`communication_line`,`power_address`,`phone_address`,`keyboard_address`) values ('1','sss','ss','11111111111111111','刘','','','','','','','','','','','','',NULL,NULL,'','','','','','','','','',NULL,'',NULL,NULL,NULL,'','','',NULL,NULL,'','','','',NULL,'','','','',NULL,'','','',''),('a92f0adfa3974b2d8c2c80240e97636a','sss',NULL,'sss','ss',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
