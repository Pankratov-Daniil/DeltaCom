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
-- Dumping data for table "number_pool"
--

INSERT INTO "number_pool" VALUES ('89113111133',1);
INSERT INTO "number_pool" VALUES ('89212923412',1);
INSERT INTO "number_pool" VALUES ('89219999999',1);
INSERT INTO "number_pool" VALUES ('89222222222',1);
INSERT INTO "number_pool" VALUES ('89314523412',0);

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
-- Dumping data for table "option"
--

INSERT INTO "option" VALUES (1,'Internet',500,500);
INSERT INTO "option" VALUES (2,'100 minutes',100,100);
INSERT INTO "option" VALUES (3,'200 minutes',200,200);
INSERT INTO "option" VALUES (4,'Internet + 100 minutes',0,0);
INSERT INTO "option" VALUES (5,'Internet + 200 minutes',0,0);
INSERT INTO "option" VALUES (11,'New option',200,500);


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
-- Dumping data for table "tariff"
--

INSERT INTO "tariff" VALUES (1,'Tariff1',200);
INSERT INTO "tariff" VALUES (2,'Tariff2',123);
INSERT INTO "tariff" VALUES (4,'New tariff2',3005);

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
-- Dumping data for table "access_level"
--

INSERT INTO "access_level" VALUES (3,'ROLE_ADMIN');
INSERT INTO "access_level" VALUES (2,'ROLE_MANAGER');
INSERT INTO "access_level" VALUES (1,'ROLE_USER');

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
  PRIMARY KEY ("id"),
  UNIQUE KEY "idClient_UNIQUE" ("id"),
  UNIQUE KEY "email_UNIQUE" ("email")
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table "client"
--

INSERT INTO "client" VALUES (5,'Даниил','Панкратов','1995-06-29','паспорт','адрес','mobigod0@gmail.com','$2a$11$2F/Dn2gUwF2B.GZ/nxwaZONsUeFTLReCoHSHZ4Q9dGG36abBuIgQG');
INSERT INTO "client" VALUES (6,'Admin','Adminov','1970-01-01','1337','1337','admin@admin.com','$2a$11$GtkB3K53SJDMutXNew1KhO.E0EKgEARgj2V3ZqKVxjsFuEZgnq.Xy');
INSERT INTO "client" VALUES (7,'Manager','Manager','2000-05-05','212','456','manager@manager.com','$2a$11$21jfNHnUJ6wRF0HNZbMXyudZFXR8RMouZluUrCsdOovYcGTtDJY/u');
INSERT INTO "client" VALUES (26,'Daniil','doiufj','0002-02-12','123','123','1@ma.ri','$2a$11$BUW1XqfWDWRj4HJpS2fAeOghxhkliKSoKI./Cu/9beFAPyXUMudZS');
INSERT INTO "client" VALUES (27,'Daniil','das','2222-02-22','dassa','fs','s@gmail.comas','$2a$11$H8Qjwp8Adr4EEoL8HffFE..kzI5jdLKyE1lMqpt1Ipi4R4LCfiWdm');
INSERT INTO "client" VALUES (29,'sad','sda','2222-02-22','dsa','dsa','sadas@gm13ail.com','$2a$11$Rijt6gommkSMirLLP8rNzOaFWdZFX43f4nkAopZhZVQAl0E2rF2h6');
INSERT INTO "client" VALUES (30,'Daniil','doiufj','1222-12-12','2123','1231','1@ma.ri2','$2a$11$R3T1QtoihyQz8cjR4u//yuSQaRAoZHzqrNF.gyp1BW6LqCUhz71IW');
INSERT INTO "client" VALUES (31,'Daniil','doiufj','0022-02-12','asd1','a12d3','1a@dsa.as','$2a$11$S4FY4V2Zs3HJ3E.UbTC.Qege9f2jGap/LmU8PtNQu3eo2vtTMUFka');
INSERT INTO "client" VALUES (32,'Daniil','doiufj','0031-12-12','asd54','ad654as','as@opfask.re','$2a$11$zJqzz9iKvrFt9MMq6bPwBuhBKwb6dvXub29EKKngRy8lxP.vxIE5.');
INSERT INTO "client" VALUES (33,'dasad','fj','1451-12-22','djfpp','faioj','sadij@ofh.sa','$2a$11$ptAmf7awk91B46nH/2ZYyeeYYYRPawau8qI88yZolO2UQw4MSTdqq');
INSERT INTO "client" VALUES (35,'New','Client','1992-02-22','pass','addr','newuser@gmail.com','$2a$11$4.UhN3bDVU3UTEVeIG8XcuZR3nTKk04WYU6JSYBYp6cQKBffrRbna');
INSERT INTO "client" VALUES (44,'Daniil','Pankratov','2222-02-22','2123','123','mobi@god.com','$2a$11$5MP4ZKyS5vL4/mX4zie4x.3Y5pJzTTs/v271zNIzLX4V6V2nLJa7K');
INSERT INTO "client" VALUES (45,'Daniil','Pankratov','1995-06-29','pass','addr','newuser@gmai.com','$2a$11$p8ND50qlW.J4T0Et9UMB2u/u70vlLPlgHJsPHehjfqUnJCJd3ZZom');

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
-- Dumping data for table "clients_access_levels"
--

INSERT INTO "clients_access_levels" VALUES (5,6,3);
INSERT INTO "clients_access_levels" VALUES (6,7,2);
INSERT INTO "clients_access_levels" VALUES (15,26,1);
INSERT INTO "clients_access_levels" VALUES (16,27,1);
INSERT INTO "clients_access_levels" VALUES (17,29,1);
INSERT INTO "clients_access_levels" VALUES (18,30,1);
INSERT INTO "clients_access_levels" VALUES (19,31,1);
INSERT INTO "clients_access_levels" VALUES (20,32,1);
INSERT INTO "clients_access_levels" VALUES (21,33,1);
INSERT INTO "clients_access_levels" VALUES (22,5,1);
INSERT INTO "clients_access_levels" VALUES (24,35,1);
INSERT INTO "clients_access_levels" VALUES (25,44,1);
INSERT INTO "clients_access_levels" VALUES (26,45,1);

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
-- Dumping data for table "compatible_options"
--

INSERT INTO "compatible_options" VALUES (64,5,1);
INSERT INTO "compatible_options" VALUES (65,5,3);
INSERT INTO "compatible_options" VALUES (66,4,1);
INSERT INTO "compatible_options" VALUES (67,4,2);
INSERT INTO "compatible_options" VALUES (87,11,1);

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
-- Dumping data for table "contract"
--

INSERT INTO "contract" VALUES (16,'89219999999',2,5,0,1,1);
INSERT INTO "contract" VALUES (21,'89222222222',1,5,0,0,0);
INSERT INTO "contract" VALUES (34,'89212923412',2,7,0,0,0);
INSERT INTO "contract" VALUES (35,'89113111133',2,45,0,0,0);

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
-- Dumping data for table "contract_option"
--

INSERT INTO "contract_option" VALUES (103,3,16);
INSERT INTO "contract_option" VALUES (128,2,34);
INSERT INTO "contract_option" VALUES (129,2,35);
INSERT INTO "contract_option" VALUES (130,1,21);

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
-- Dumping data for table "incompatible_options"
--

INSERT INTO "incompatible_options" VALUES (57,5,4);
INSERT INTO "incompatible_options" VALUES (58,4,5);
INSERT INTO "incompatible_options" VALUES (60,2,3);
INSERT INTO "incompatible_options" VALUES (61,3,2);


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

--
-- Dumping data for table "tariff_option"
--

INSERT INTO "tariff_option" VALUES (34,1,1);
INSERT INTO "tariff_option" VALUES (35,1,2);
INSERT INTO "tariff_option" VALUES (36,1,4);
INSERT INTO "tariff_option" VALUES (37,2,1);
INSERT INTO "tariff_option" VALUES (38,2,2);
INSERT INTO "tariff_option" VALUES (39,2,3);
INSERT INTO "tariff_option" VALUES (47,4,1);
INSERT INTO "tariff_option" VALUES (48,4,2);
INSERT INTO "tariff_option" VALUES (49,4,3);
INSERT INTO "tariff_option" VALUES (50,4,4);
INSERT INTO "tariff_option" VALUES (51,4,5);
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-10-19 17:27:06
