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

--
-- Table structure for table `access_level`
--

DROP TABLE IF EXISTS `deltacom`.`access_level`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `deltacom`.`access_level` (
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

LOCK TABLES `deltacom`.`access_level` WRITE;
/*!40000 ALTER TABLE `deltacom`.`access_level` DISABLE KEYS */;
INSERT INTO `deltacom`.`access_level` VALUES (3,'ROLE_ADMIN'),(2,'ROLE_MANAGER'),(1,'ROLE_USER');
/*!40000 ALTER TABLE `deltacom`.`access_level` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `deltacom`.`client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `deltacom`.`client` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(20) NOT NULL,
  `lastName` varchar(20) NOT NULL,
  `birthDate` date NOT NULL,
  `passport` varchar(20) NOT NULL,
  `address` varchar(70) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(60) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idClient_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `deltacom`.`client` WRITE;
/*!40000 ALTER TABLE `deltacom`.`client` DISABLE KEYS */;
INSERT INTO `deltacom`.`client` VALUES (5,'Даниил','Панкратов','1995-06-29','паспорт','адрес','mobigod0@gmail.com','$2a$11$2F/Dn2gUwF2B.GZ/nxwaZONsUeFTLReCoHSHZ4Q9dGG36abBuIgQG'),(6,'Admin','Adminov','1970-01-01','1337','1337','admin@admin.com','$2a$11$GtkB3K53SJDMutXNew1KhO.E0EKgEARgj2V3ZqKVxjsFuEZgnq.Xy'),(7,'Manager','Manager','2000-05-05','212','456','manager@manager.com','$2a$11$21jfNHnUJ6wRF0HNZbMXyudZFXR8RMouZluUrCsdOovYcGTtDJY/u');
/*!40000 ALTER TABLE `deltacom`.`client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clients_access_levels`
--

DROP TABLE IF EXISTS `deltacom`.`clients_access_levels`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `deltacom`.`clients_access_levels` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `client_id` int(11) NOT NULL,
  `access_level_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_clients_access_levels_client1_idx` (`client_id`),
  KEY `fk_clients_access_levels_access_level1_idx` (`access_level_id`),
  CONSTRAINT `fk_clients_access_levels_access_level1` FOREIGN KEY (`access_level_id`) REFERENCES `deltacom`.`access_level` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_clients_access_levels_client1` FOREIGN KEY (`client_id`) REFERENCES `deltacom`.`client` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clients_access_levels`
--

LOCK TABLES `deltacom`.`clients_access_levels` WRITE;
/*!40000 ALTER TABLE `deltacom`.`clients_access_levels` DISABLE KEYS */;
INSERT INTO `deltacom`.`clients_access_levels` VALUES (5,6,3),(6,7,2),(7,8,1);
/*!40000 ALTER TABLE `deltacom`.`clients_access_levels` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `compatible_options`
--

DROP TABLE IF EXISTS `deltacom`.`compatible_options`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `deltacom`.`compatible_options` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idOption1` int(11) NOT NULL,
  `idOption2` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_compatible_options_option1_idx` (`idOption1`),
  KEY `fk_compatible_options_option2_idx` (`idOption2`),
  CONSTRAINT `fk_compatible_options_option1` FOREIGN KEY (`idOption1`) REFERENCES `deltacom`.`option` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_compatible_options_option2` FOREIGN KEY (`idOption2`) REFERENCES `deltacom`.`option` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `compatible_options`
--

LOCK TABLES `deltacom`.`compatible_options` WRITE;
/*!40000 ALTER TABLE `deltacom`.`compatible_options` DISABLE KEYS */;
INSERT INTO `deltacom`.`compatible_options` VALUES (4,3,4);
/*!40000 ALTER TABLE `deltacom`.`compatible_options` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `number_pool`
--
DROP TABLE IF EXISTS `deltacom`.`number_pool` ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE IF NOT EXISTS `deltacom`.`number_pool` (
  `number` VARCHAR(12) NOT NULL,
  `used` TINYINT(1) NOT NULL,
  PRIMARY KEY (`number`),
  UNIQUE INDEX `number_UNIQUE` (`number` ASC))
ENGINE = InnoDB;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `number_pool`
--

LOCK TABLES `deltacom`.`number_pool` WRITE;
/*!40000 ALTER TABLE `deltacom`.`number_pool` DISABLE KEYS */;
INSERT INTO `deltacom`.`number_pool` VALUES ('89219999999',1),('89212923412',0),('89314523412',0);
/*!40000 ALTER TABLE `deltacom`.`number_pool` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `contract`
--

DROP TABLE IF EXISTS `deltacom`.`contract`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `deltacom`.`contract` (
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
  CONSTRAINT `fk_contract_Client1` FOREIGN KEY (`idClient`) REFERENCES `deltacom`.`client` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_contract_tariff1` FOREIGN KEY (`idTariff`) REFERENCES `deltacom`.`tariff` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_contract_numberPool1` FOREIGN KEY (`number`) REFERENCES `deltacom`.`number_pool` (`number`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contract`
--

LOCK TABLES `deltacom`.`contract` WRITE;
/*!40000 ALTER TABLE `deltacom`.`contract` DISABLE KEYS */;
INSERT INTO `deltacom`.`contract` VALUES (3,'89219999999',1,5,600,0,0);
/*!40000 ALTER TABLE `deltacom`.`contract` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contract_option`
--

DROP TABLE IF EXISTS `deltacom`.`contract_option`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `deltacom`.`contract_option` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idOption` int(11) NOT NULL,
  `idContract` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idContract_Option_UNIQUE` (`id`),
  KEY `fk_contract_has_option_option1_idx` (`idOption`),
  KEY `fk_contract_option_contract1_idx` (`idContract`),
  CONSTRAINT `fk_contract_has_option_option1` FOREIGN KEY (`idOption`) REFERENCES `deltacom`.`option` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_contract_option_contract1` FOREIGN KEY (`idContract`) REFERENCES `deltacom`.`contract` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contract_option`
--

LOCK TABLES `deltacom`.`contract_option` WRITE;
/*!40000 ALTER TABLE `deltacom`.`contract_option` DISABLE KEYS */;
INSERT INTO `deltacom`.`contract_option` VALUES (9,1,3),(10,2,3);
/*!40000 ALTER TABLE `deltacom`.`contract_option` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `incompatible_options`
--

DROP TABLE IF EXISTS `deltacom`.`incompatible_options`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `deltacom`.`incompatible_options` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idOption1` int(11) NOT NULL,
  `idOption2` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_incompatible_options_option1_idx` (`idOption1`),
  KEY `fk_incompatible_options_option2_idx` (`idOption2`),
  CONSTRAINT `fk_incompatible_options_option1` FOREIGN KEY (`idOption1`) REFERENCES `deltacom`.`option` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_incompatible_options_option2` FOREIGN KEY (`idOption2`) REFERENCES `deltacom`.`option` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `incompatible_options`
--

LOCK TABLES `deltacom`.`incompatible_options` WRITE;
/*!40000 ALTER TABLE `deltacom`.`incompatible_options` DISABLE KEYS */;
INSERT INTO `deltacom`.`incompatible_options` VALUES (4,2,3);
/*!40000 ALTER TABLE `deltacom`.`incompatible_options` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `option`
--

DROP TABLE IF EXISTS `deltacom`.`option`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `deltacom`.`option` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `price` float NOT NULL,
  `connectionCost` float NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idOption_UNIQUE` (`id`),
  UNIQUE KEY `nameOption_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `option`
--

LOCK TABLES `deltacom`.`option` WRITE;
/*!40000 ALTER TABLE `deltacom`.`option` DISABLE KEYS */;
INSERT INTO `deltacom`.`option` VALUES (1,'Option1',500,200),(2,'Option2',600,100),(3,'Option3',100,0),(4,'Option4',22,424),(5,'Oprion5',654,21);
/*!40000 ALTER TABLE `deltacom`.`option` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persistent_logins`
--

DROP TABLE IF EXISTS `deltacom`.`persistent_logins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `deltacom`.`persistent_logins` (
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
-- Dumping data for table `persistent_logins`
--

LOCK TABLES `deltacom`.`persistent_logins` WRITE;
/*!40000 ALTER TABLE `deltacom`.`persistent_logins` DISABLE KEYS */;
/*!40000 ALTER TABLE `deltacom`.`persistent_logins` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tariff`
--

DROP TABLE IF EXISTS `deltacom`.`tariff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `deltacom`.`tariff` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `price` float NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idTariff_UNIQUE` (`id`),
  UNIQUE KEY `nameTariff_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tariff`
--

LOCK TABLES `deltacom`.`tariff` WRITE;
/*!40000 ALTER TABLE `deltacom`.`tariff` DISABLE KEYS */;
INSERT INTO `deltacom`.`tariff` VALUES (1,'Tariff1',200),(2,'Tariff2',150),(3,'Tariff3',70);
/*!40000 ALTER TABLE `deltacom`.`tariff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tariff_option`
--

DROP TABLE IF EXISTS `deltacom`.`tariff_option`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `deltacom`.`tariff_option` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idTariff` int(11) NOT NULL,
  `idOption` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idTariff_Option_UNIQUE` (`id`),
  KEY `fk_tariff_option_option1_idx` (`idOption`),
  KEY `fk_tariff_option_tariff1` (`idTariff`),
  CONSTRAINT `fk_tariff_option_option1` FOREIGN KEY (`idOption`) REFERENCES `deltacom`.`option` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tariff_option_tariff1` FOREIGN KEY (`idTariff`) REFERENCES `deltacom`.`tariff` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tariff_option`
--

LOCK TABLES `deltacom`.`tariff_option` WRITE;
/*!40000 ALTER TABLE `deltacom`.`tariff_option` DISABLE KEYS */;
INSERT INTO `deltacom`.`tariff_option` VALUES (16,1,1),(17,1,2),(18,2,1),(19,3,4),(20,3,3);
/*!40000 ALTER TABLE `deltacom`.`tariff_option` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-10-06 18:38:31
