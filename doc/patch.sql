/*
SQLyog Ultimate v10.0 
MySQL - 5.5.39 : Database - bageaxe_db
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`bageaxe_db` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `bageaxe_db`;

/*Table structure for table `associationvalueentry` */

DROP TABLE IF EXISTS `associationvalueentry`;

CREATE TABLE `associationvalueentry` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ASSOCIATIONKEY` varchar(255) DEFAULT NULL,
  `ASSOCIATIONVALUE` varchar(255) DEFAULT NULL,
  `SAGAID` varchar(255) DEFAULT NULL,
  `SAGATYPE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
