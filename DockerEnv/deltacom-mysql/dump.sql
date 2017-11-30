-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: deltacom
-- ------------------------------------------------------
-- Server version	5.7.19-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

create schema deltacom;
USE deltacom;

--
-- Table structure for table `access_level`
--

DROP TABLE IF EXISTS `access_level`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `access_level` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idAccess_level_UNIQUE` (`id`),
  UNIQUE KEY `nameAccess_level_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `access_level`
--

LOCK TABLES `access_level` WRITE;
/*!40000 ALTER TABLE `access_level` DISABLE KEYS */;
INSERT INTO `access_level` VALUES (3,'ROLE_ADMIN'),(2,'ROLE_MANAGER'),(1,'ROLE_USER');
/*!40000 ALTER TABLE `access_level` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `client` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(20) NOT NULL,
  `lastName` varchar(20) NOT NULL,
  `birthDate` date NOT NULL,
  `passport` varchar(20) NOT NULL,
  `address` varchar(70) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(60),
  `activated` tinyint(1) NOT NULL DEFAULT '0',
  `forgottenPassToken` varchar(60),
  `openIdToken` varchar(60),
  `twoFactorAuth` tinyint(1) NOT NULL DEFAULT '0',
  `smsCode` varchar(10),
  `smsSendDate` datetime,
  `twoFactorAuthNumber` varchar(16),
  PRIMARY KEY (`id`),
  UNIQUE KEY `idClient_UNIQUE` (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `forgottenPassToken_UNIQUE` (`forgottenPassToken`),
  UNIQUE KEY `openIdToken_UNIQUE` (`openIdToken`),
  UNIQUE KEY `smsCode_UNIQUE` (`smsCode`),
  UNIQUE KEY `twoFactorAuthNumber_UNIQUE` (`twoFactorAuthNumber`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` VALUES (5,'Даниил','Панкратов','1995-06-29','паспорт','адрес','mobigod0@gmail.com','$2a$11$2F/Dn2gUwF2B.GZ/nxwaZONsUeFTLReCoHSHZ4Q9dGG36abBuIgQG', '1', null, null, '0', null, null, null),(6,'Admin','Adminov','1970-01-01','1337','1337','admin@admin.com','$2a$11$GtkB3K53SJDMutXNew1KhO.E0EKgEARgj2V3ZqKVxjsFuEZgnq.Xy', '1', null, null, '0', null, null, null),(7,'Manager','Manager','2000-05-05','212','456','manager@manager.com','$2a$11$21jfNHnUJ6wRF0HNZbMXyudZFXR8RMouZluUrCsdOovYcGTtDJY/u', '1', null, null, '0', null, null, null),(26,'Daniil','doiufj','0002-02-12','123','123','1@ma.ri','$2a$11$BUW1XqfWDWRj4HJpS2fAeOghxhkliKSoKI./Cu/9beFAPyXUMudZS', '1', null, null, '0', null, null, null),(27,'Daniil','das','2222-02-22','dassa','fs','s@gmail.comas','$2a$11$H8Qjwp8Adr4EEoL8HffFE..kzI5jdLKyE1lMqpt1Ipi4R4LCfiWdm', '1', null, null, '0', null, null, null),(29,'sad','sda','2222-02-22','dsa','dsa','sadas@gm13ail.com','$2a$11$Rijt6gommkSMirLLP8rNzOaFWdZFX43f4nkAopZhZVQAl0E2rF2h6', '1', null, null, '0', null, null, null),(30,'Daniil','doiufj','1222-12-12','2123','1231','1@ma.ri2','$2a$11$R3T1QtoihyQz8cjR4u//yuSQaRAoZHzqrNF.gyp1BW6LqCUhz71IW', '1', null, null, '0', null, null, null),(31,'Daniil','doiufj','0022-02-12','asd1','a12d3','1a@dsa.as','$2a$11$S4FY4V2Zs3HJ3E.UbTC.Qege9f2jGap/LmU8PtNQu3eo2vtTMUFka', '1', null, null, '0', null, null, null),(32,'Daniil','doiufj','0031-12-12','asd54','ad654as','as@opfask.re','$2a$11$zJqzz9iKvrFt9MMq6bPwBuhBKwb6dvXub29EKKngRy8lxP.vxIE5.', '1', null, null, '0', null,null,  null),(33,'dasad','fj','1451-12-22','djfpp','faioj','sadij@ofh.sa','$2a$11$ptAmf7awk91B46nH/2ZYyeeYYYRPawau8qI88yZolO2UQw4MSTdqq', '1', null, null, '0', null, null, null),(35,'New','Client','1992-02-22','pass','addr','newuser@gmail.com','$2a$11$4.UhN3bDVU3UTEVeIG8XcuZR3nTKk04WYU6JSYBYp6cQKBffrRbna', '1', null, null, '0', null, null, null);
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client_location`
--

DROP TABLE IF EXISTS `client_location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `client_location` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `client_id` int(11) NOT NULL,
  `latitude` float NOT NULL,
  `longitude` float NOT NULL,
  `city` varchar(30) NOT NULL,
  `country` varchar(30) NOT NULL,
  `ip_address` varchar(15) NOT NULL,
  `entered_date` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_client_location_UNIQUE` (`id`),
  KEY `fk_client_location_client_idx` (`client_id`),
  CONSTRAINT `fk_client_location_client` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `clients_access_levels`
--

DROP TABLE IF EXISTS `clients_access_levels`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clients_access_levels` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `client_id` int(11) NOT NULL,
  `access_level_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_clients_access_levels_client1_idx` (`client_id`),
  KEY `fk_clients_access_levels_access_level1_idx` (`access_level_id`),
  CONSTRAINT `fk_clients_access_levels_access_level1` FOREIGN KEY (`access_level_id`) REFERENCES `access_level` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_clients_access_levels_client1` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clients_access_levels`
--

LOCK TABLES `clients_access_levels` WRITE;
/*!40000 ALTER TABLE `clients_access_levels` DISABLE KEYS */;
INSERT INTO `clients_access_levels` VALUES (5,6,3),(6,7,2),(15,26,1),(16,27,1),(17,29,1),(18,30,1),(19,31,1),(20,32,1),(21,33,1),(22,5,1),(24,35,1);
/*!40000 ALTER TABLE `clients_access_levels` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `compatible_options`
--

DROP TABLE IF EXISTS `compatible_options`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `compatible_options` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idOption1` int(11) NOT NULL,
  `idOption2` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_compatible_options_option1_idx` (`idOption1`),
  KEY `fk_compatible_options_option2_idx` (`idOption2`),
  CONSTRAINT `fk_compatible_options_option1` FOREIGN KEY (`idOption1`) REFERENCES `option` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_compatible_options_option2` FOREIGN KEY (`idOption2`) REFERENCES `option` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `compatible_options`
--

LOCK TABLES `compatible_options` WRITE;
/*!40000 ALTER TABLE `compatible_options` DISABLE KEYS */;
INSERT INTO `compatible_options` VALUES (64,5,1),(65,5,3),(66,4,1),(67,4,2),(87,11,1);
/*!40000 ALTER TABLE `compatible_options` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contract`
--

DROP TABLE IF EXISTS `contract`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contract` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `number` varchar(12) NOT NULL,
  `idTariff` int(11) NOT NULL,
  `idClient` int(11) NOT NULL,
  `balance` float NOT NULL DEFAULT '0',
  `blocked` tinyint(1) DEFAULT '0',
  `blockedByOperator` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idContract_UNIQUE` (`id`),
  UNIQUE KEY `numberContract_UNIQUE` (`number`),
  KEY `fk_contract_tariff1_idx` (`idTariff`),
  KEY `fk_contract_Client1_idx` (`idClient`),
  CONSTRAINT `fk_contract_Client1` FOREIGN KEY (`idClient`) REFERENCES `client` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_contract_numberPool1` FOREIGN KEY (`number`) REFERENCES `number_pool` (`number`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_contract_tariff1` FOREIGN KEY (`idTariff`) REFERENCES `tariff` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contract`
--

LOCK TABLES `contract` WRITE;
/*!40000 ALTER TABLE `contract` DISABLE KEYS */;
INSERT INTO `contract` VALUES (14,'89314523412',1,33,0,1,1),(16,'89219999999',1,5,0,1,1),(18,'89113111133',1,35,0,1,1),(19,'89222222222',2,5,0,0,0),(20,'89212923412',1,26,0,0,0);
/*!40000 ALTER TABLE `contract` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contract_option`
--

DROP TABLE IF EXISTS `contract_option`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contract_option` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idOption` int(11) NOT NULL,
  `idContract` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idContract_Option_UNIQUE` (`id`),
  KEY `fk_contract_has_option_option1_idx` (`idOption`),
  KEY `fk_contract_option_contract1_idx` (`idContract`),
  CONSTRAINT `fk_contract_has_option_option1` FOREIGN KEY (`idOption`) REFERENCES `option` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_contract_option_contract1` FOREIGN KEY (`idContract`) REFERENCES `contract` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contract_option`
--

LOCK TABLES `contract_option` WRITE;
/*!40000 ALTER TABLE `contract_option` DISABLE KEYS */;
INSERT INTO `contract_option` VALUES (73,2,18),(99,1,16),(100,2,16),(101,2,19),(102,1,20);
/*!40000 ALTER TABLE `contract_option` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `incompatible_options`
--

DROP TABLE IF EXISTS `incompatible_options`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `incompatible_options` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idOption1` int(11) NOT NULL,
  `idOption2` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_incompatible_options_option1_idx` (`idOption1`),
  KEY `fk_incompatible_options_option2_idx` (`idOption2`),
  CONSTRAINT `fk_incompatible_options_option1` FOREIGN KEY (`idOption1`) REFERENCES `option` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_incompatible_options_option2` FOREIGN KEY (`idOption2`) REFERENCES `option` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `incompatible_options`
--

LOCK TABLES `incompatible_options` WRITE;
/*!40000 ALTER TABLE `incompatible_options` DISABLE KEYS */;
INSERT INTO `incompatible_options` VALUES (57,5,4),(58,4,5),(60,2,3),(61,3,2);
/*!40000 ALTER TABLE `incompatible_options` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `number_pool`
--

DROP TABLE IF EXISTS `number_pool`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `number_pool` (
  `number` varchar(12) NOT NULL,
  `used` tinyint(1) NOT NULL,
  PRIMARY KEY (`number`),
  UNIQUE KEY `number_UNIQUE` (`number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `number_pool`
--

LOCK TABLES `number_pool` WRITE;
/*!40000 ALTER TABLE `number_pool` DISABLE KEYS */;
INSERT INTO `number_pool` VALUES ('89113111133',1),('89212923412',1),('89219999999',1),('89222222222',1),('89314523412',1),('89212345678',0),('89123456789',0),('89876543210',0);
/*!40000 ALTER TABLE `number_pool` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `option`
--

DROP TABLE IF EXISTS `option`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `option` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `price` float NOT NULL,
  `connectionCost` float NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idOption_UNIQUE` (`id`),
  UNIQUE KEY `nameOption_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `option`
--

LOCK TABLES `option` WRITE;
/*!40000 ALTER TABLE `option` DISABLE KEYS */;
INSERT INTO `option` VALUES (1,'Internet',500,500),(2,'100 minutes',100,100),(3,'200 minutes',200,200),(4,'Internet + 100 minutes',0,0),(5,'Internet + 200 minutes',0,0),(11,'New option',200,500);
/*!40000 ALTER TABLE `option` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persistent_logins`
--

DROP TABLE IF EXISTS `persistent_logins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `persistent_logins` (
  `username` varchar(64) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `series_UNIQUE` (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tariff`
--

DROP TABLE IF EXISTS `tariff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tariff` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `price` float NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idTariff_UNIQUE` (`id`),
  UNIQUE KEY `nameTariff_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tariff`
--

LOCK TABLES `tariff` WRITE;
/*!40000 ALTER TABLE `tariff` DISABLE KEYS */;
INSERT INTO `tariff` VALUES (1,'Tariff1',200),(2,'Tariff2',123),(4,'New tariff2',3005);
/*!40000 ALTER TABLE `tariff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tariff_option`
--

DROP TABLE IF EXISTS `tariff_option`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tariff_option` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idTariff` int(11) NOT NULL,
  `idOption` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idTariff_Option_UNIQUE` (`id`),
  KEY `fk_tariff_option_option1_idx` (`idOption`),
  KEY `fk_tariff_option_tariff1` (`idTariff`),
  CONSTRAINT `fk_tariff_option_option1` FOREIGN KEY (`idOption`) REFERENCES `option` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tariff_option_tariff1` FOREIGN KEY (`idTariff`) REFERENCES `tariff` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tariff_option`
--

LOCK TABLES `tariff_option` WRITE;
/*!40000 ALTER TABLE `tariff_option` DISABLE KEYS */;
INSERT INTO `tariff_option` VALUES (34,1,1),(35,1,2),(36,1,4),(37,2,1),(38,2,2),(39,2,3),(47,4,1),(48,4,2),(49,4,3),(50,4,4),(51,4,5);
/*!40000 ALTER TABLE `tariff_option` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-10-18 20:35:25