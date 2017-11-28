-- MySQL dump 10.13  Distrib 5.7.19, for Win64 (x86_64)
--
-- Host: localhost    Database: deltacom
-- ------------------------------------------------------
-- Server version	5.7.19-log
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO,NO_KEY_OPTIONS,NO_TABLE_OPTIONS,NO_FIELD_OPTIONS,ANSI' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table "number_pool"
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
DROP TABLE "number_pool" IF EXISTS;
CREATE TABLE "number_pool" (
  "number" varchar(12) NOT NULL,
  "used" tinyint(1) NOT NULL,
  PRIMARY KEY ("number"),
  UNIQUE KEY "number_UNIQUE" ("number")
);
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table "option"
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
DROP TABLE "option" IF EXISTS;
CREATE TABLE "option" (
  "id" int(11) NOT NULL AUTO_INCREMENT,
  "name" varchar(50) NOT NULL,
  "price" float NOT NULL,
  "connectionCost" float NOT NULL,
  PRIMARY KEY ("id"),
  UNIQUE KEY "idOption_UNIQUE" ("id"),
  UNIQUE KEY "nameOption_UNIQUE" ("name")
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "tariff"
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
DROP TABLE "tariff" IF EXISTS;
CREATE TABLE "tariff" (
  "id" int(11) NOT NULL AUTO_INCREMENT,
  "name" varchar(50) NOT NULL,
  "price" float NOT NULL,
  PRIMARY KEY ("id"),
  UNIQUE KEY "idTariff_UNIQUE" ("id"),
  UNIQUE KEY "nameTariff_UNIQUE" ("name")
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "access_level"
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
DROP TABLE "access_level" IF EXISTS;
CREATE TABLE "access_level" (
  "id" int(11) NOT NULL AUTO_INCREMENT,
  "name" varchar(45) NOT NULL,
  PRIMARY KEY ("id"),
  UNIQUE KEY "idAccess_level_UNIQUE" ("id"),
  UNIQUE KEY "nameAccess_level_UNIQUE" ("name")
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "client"
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
DROP TABLE "client" IF EXISTS;
CREATE TABLE "client" (
  "id" int(11) NOT NULL AUTO_INCREMENT,
  "firstName" varchar(20) NOT NULL,
  "lastName" varchar(20) NOT NULL,
  "birthDate" date NOT NULL,
  "passport" varchar(20) NOT NULL,
  "address" varchar(70) NOT NULL,
  "email" varchar(50) NOT NULL,
  "password" varchar(60) NOT NULL,
  "activated" tinyint(1) NOT NULL DEFAULT '0',
  "forgottenPassToken" varchar(60),
  "openIdToken" varchar(60),
  "twoFactorAuth" tinyint(1) NOT NULL DEFAULT '0',
  "smsCode" varchar(10),
  "smsSendDate" datetime,
  "twoFactorAuthNumber" varchar(16),
  PRIMARY KEY ("id"),
  UNIQUE KEY "idClient_UNIQUE" ("id"),
  UNIQUE KEY "email_UNIQUE" ("email"),
  UNIQUE KEY "forgottenPassToken_UNIQUE" ("forgottenPassToken"),
  UNIQUE KEY "openIdToken_UNIQUE" ("openIdToken"),
  UNIQUE KEY "smsCode_UNIQUE" ("smsCode"),
  UNIQUE KEY "twoFactorAuthNumber_UNIQUE" ("twoFactorAuthNumber")
);
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table "client_location"
--

DROP TABLE IF EXISTS "client_location";
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "client_location" (
  "id" int(11) NOT NULL AUTO_INCREMENT,
  "client_id" int(11) NOT NULL,
  "latitude" float NOT NULL,
  "longitude" float NOT NULL,
  "city" varchar(30) NOT NULL,
  "country" varchar(30) NOT NULL,
  "ip_address" varchar(15) NOT NULL,
  "entered_date" timestamp NOT NULL,
  PRIMARY KEY ("id"),
  UNIQUE KEY "id_client_location_UNIQUE" ("id"),
  KEY "fk_client_location_client_idx" ("client_id"),
  CONSTRAINT "fk_client_location_client" FOREIGN KEY ("client_id") REFERENCES "client" ("id") ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table "clients_access_levels"
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
DROP TABLE "clients_access_levels" IF EXISTS;
CREATE TABLE "clients_access_levels" (
  "id" int(11) NOT NULL AUTO_INCREMENT,
  "client_id" int(11) NOT NULL,
  "access_level_id" int(11) NOT NULL,
  PRIMARY KEY ("id"),
  UNIQUE KEY "id_UNIQUE2" ("id"),
  KEY "fk_clients_access_levels_client1_idx" ("client_id"),
  KEY "fk_clients_access_levels_access_level1_idx" ("access_level_id"),
  CONSTRAINT "fk_clients_access_levels_access_level1" FOREIGN KEY ("access_level_id") REFERENCES "access_level" ("id") ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT "fk_clients_access_levels_client1" FOREIGN KEY ("client_id") REFERENCES "client" ("id") ON DELETE CASCADE ON UPDATE CASCADE
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "compatible_options"
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
DROP TABLE "compatible_options" IF EXISTS;
CREATE TABLE "compatible_options" (
  "id" int(11) NOT NULL AUTO_INCREMENT,
  "idOption1" int(11) NOT NULL,
  "idOption2" int(11) NOT NULL,
  PRIMARY KEY ("id"),
  UNIQUE KEY "id_UNIQUE3" ("id"),
  KEY "fk_compatible_options_option1_idx" ("idOption1"),
  KEY "fk_compatible_options_option2_idx" ("idOption2"),
  CONSTRAINT "fk_compatible_options_option1" FOREIGN KEY ("idOption1") REFERENCES "option" ("id") ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT "fk_compatible_options_option2" FOREIGN KEY ("idOption2") REFERENCES "option" ("id") ON DELETE CASCADE ON UPDATE CASCADE
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "contract"
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
DROP TABLE "contract" IF EXISTS;
CREATE TABLE "contract" (
  "id" int(11) NOT NULL AUTO_INCREMENT,
  "number" varchar(12) NOT NULL,
  "idTariff" int(11) NOT NULL,
  "idClient" int(11) NOT NULL,
  "balance" float NOT NULL DEFAULT '0',
  "blocked" tinyint(1) DEFAULT '0',
  "blockedByOperator" tinyint(1) DEFAULT '0',
  PRIMARY KEY ("id"),
  UNIQUE KEY "idContract_UNIQUE" ("id"),
  UNIQUE KEY "numberContract_UNIQUE" ("number"),
  KEY "fk_contract_tariff1_idx" ("idTariff"),
  KEY "fk_contract_Client1_idx" ("idClient"),
  CONSTRAINT "fk_contract_Client1" FOREIGN KEY ("idClient") REFERENCES "client" ("id") ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT "fk_contract_numberPool1" FOREIGN KEY ("number") REFERENCES "number_pool" ("number") ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT "fk_contract_tariff1" FOREIGN KEY ("idTariff") REFERENCES "tariff" ("id") ON DELETE CASCADE ON UPDATE CASCADE
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "contract_option"
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
DROP TABLE "contract_option" IF EXISTS;
CREATE TABLE "contract_option" (
  "id" int(11) NOT NULL AUTO_INCREMENT,
  "idOption" int(11) NOT NULL,
  "idContract" int(11) NOT NULL,
  PRIMARY KEY ("id"),
  UNIQUE KEY "idContract_Option_UNIQUE" ("id"),
  KEY "fk_contract_has_option_option1_idx" ("idOption"),
  KEY "fk_contract_option_contract1_idx" ("idContract"),
  CONSTRAINT "fk_contract_has_option_option1" FOREIGN KEY ("idOption") REFERENCES "option" ("id") ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT "fk_contract_option_contract1" FOREIGN KEY ("idContract") REFERENCES "contract" ("id") ON DELETE CASCADE ON UPDATE CASCADE
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table "incompatible_options"
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
DROP TABLE "incompatible_options" IF EXISTS;
CREATE TABLE "incompatible_options" (
  "id" int(11) NOT NULL AUTO_INCREMENT,
  "idOption1" int(11) NOT NULL,
  "idOption2" int(11) NOT NULL,
  PRIMARY KEY ("id"),
  UNIQUE KEY "id_UNIQUE1" ("id"),
  KEY "fk_incompatible_options_option1_idx" ("idOption1"),
  KEY "fk_incompatible_options_option2_idx" ("idOption2"),
  CONSTRAINT "fk_incompatible_options_option1" FOREIGN KEY ("idOption1") REFERENCES "option" ("id") ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT "fk_incompatible_options_option2" FOREIGN KEY ("idOption2") REFERENCES "option" ("id") ON DELETE CASCADE ON UPDATE CASCADE
);
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table "tariff_option"
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
DROP TABLE "tariff_option" IF EXISTS;
CREATE TABLE "tariff_option" (
  "id" int(11) NOT NULL AUTO_INCREMENT,
  "idTariff" int(11) NOT NULL,
  "idOption" int(11) NOT NULL,
  PRIMARY KEY ("id"),
  UNIQUE KEY "idTariff_Option_UNIQUE" ("id"),
  KEY "fk_tariff_option_option1_idx" ("idOption"),
  KEY "fk_tariff_option_tariff1" ("idTariff"),
  CONSTRAINT "fk_tariff_option_option1" FOREIGN KEY ("idOption") REFERENCES "option" ("id") ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT "fk_tariff_option_tariff1" FOREIGN KEY ("idTariff") REFERENCES "tariff" ("id") ON DELETE CASCADE ON UPDATE CASCADE
);
/*!40101 SET character_set_client = @saved_cs_client */;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-10-19 17:27:06
