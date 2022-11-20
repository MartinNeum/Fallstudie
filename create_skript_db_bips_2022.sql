CREATE DATABASE  IF NOT EXISTS `bips_fs` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `bips_fs`;
-- MySQL dump 10.13  Distrib 8.0.31, for Linux (x86_64)
--
-- Host: localhost    Database: bips_fs
-- ------------------------------------------------------
-- Server version	8.0.31-0ubuntu0.20.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `abschlussarbeit`
--

DROP TABLE IF EXISTS `abschlussarbeit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `abschlussarbeit` (
  `id_abschlussarbeit` int NOT NULL,
  `ende_datum` date NOT NULL,
  `beginn_datum` date NOT NULL,
  `id_antrag` int NOT NULL,
  PRIMARY KEY (`id_abschlussarbeit`),
  KEY `fk_Abschlussarbeit_Antrag_idx` (`id_antrag`),
  CONSTRAINT `fk_a_has_a` FOREIGN KEY (`id_antrag`) REFERENCES `antrag` (`id_antrag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `abschlussarbeit`
--

LOCK TABLES `abschlussarbeit` WRITE;
/*!40000 ALTER TABLE `abschlussarbeit` DISABLE KEYS */;
/*!40000 ALTER TABLE `abschlussarbeit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `antrag`
--

DROP TABLE IF EXISTS `antrag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `antrag` (
  `id_antrag` int NOT NULL AUTO_INCREMENT,
  `id_studierender` int NOT NULL,
  `id_professor` int NOT NULL,
  `titel` varchar(45) NOT NULL,
  `typ` int NOT NULL,
  `genehmigt_prof` tinyint NOT NULL,
  `genehmigt_pav` tinyint NOT NULL DEFAULT '0',
  `genehmigt_ssb` tinyint NOT NULL DEFAULT '0',
  `begruendung_ablehnung` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`id_antrag`),
  KEY `fk_Antrag_Studierender1_idx` (`id_studierender`),
  KEY `fk_Antrag_Professor1_idx` (`id_professor`),
  CONSTRAINT `fk_a_has_p` FOREIGN KEY (`id_professor`) REFERENCES `professor` (`id_professor`),
  CONSTRAINT `fk_a_has_s` FOREIGN KEY (`id_studierender`) REFERENCES `studierender` (`id_studierender`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `antrag`
--

LOCK TABLES `antrag` WRITE;
/*!40000 ALTER TABLE `antrag` DISABLE KEYS */;
/*!40000 ALTER TABLE `antrag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `professor`
--

DROP TABLE IF EXISTS `professor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `professor` (
  `id_professor` int NOT NULL,
  `vorname` varchar(45) NOT NULL,
  `nachname` varchar(45) NOT NULL,
  `titel` varchar(20) NOT NULL,
  `mailadresse` varchar(45) NOT NULL,
  PRIMARY KEY (`id_professor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `professor`
--

LOCK TABLES `professor` WRITE;
/*!40000 ALTER TABLE `professor` DISABLE KEYS */;
INSERT INTO `professor` VALUES (1,'Frank','Höppner','Prof. Dr.','hoeppner@ostfalia.de'),(2,'Frank','Klawonn','Prof. Dr.','klawonn@ostfalia.de'),(3,'Kai','Gutenschwager','Prof. Dr.','gutenschwager@ostfalia.de'),(4,'Dirk','Lehmann','Prof. Dr.','lehmann@ostfalia.de'),(5,'Gert','Bikker','Prof. Dr.','bikker@ostfalia.de'),(6,'Reinhard','Gerndt','Prof. Dr.','gerndt@ostfalia.de'),(7,'Michaela','Huhn','Prof. Dr.','huhn@ostfalia.de'),(8,'Bernd','Müller','Prof. Dr.','mueller@ostfalia.de'),(9,'Hans','Grönniger','Prof. Dr.','groenniger@ostfalia.de'),(10,'Ina','Schiering','Prof. Dr.','schiering@ostfalia.de');
/*!40000 ALTER TABLE `professor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `professor_hat_stichpunkt`
--

DROP TABLE IF EXISTS `professor_hat_stichpunkt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `professor_hat_stichpunkt` (
  `id_professor` int NOT NULL,
  `id_stichpunkt` int NOT NULL,
  `gewicht` int NOT NULL COMMENT 'Gewicht zwischen 1 und 10: 1 kaum Relevanz, 10 höchste Relevanz',
  PRIMARY KEY (`id_professor`,`id_stichpunkt`),
  KEY `fk_Professor_has_Stichpunkt_Stichpunkt1_idx` (`id_stichpunkt`),
  KEY `fk_Professor_has_Stichpunkt_Professor1_idx` (`id_professor`),
  CONSTRAINT `fk_phs_has_p` FOREIGN KEY (`id_professor`) REFERENCES `professor` (`id_professor`),
  CONSTRAINT `fk_phs_has_s` FOREIGN KEY (`id_stichpunkt`) REFERENCES `stichpunkt` (`id_stichpunkt`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `professor_hat_stichpunkt`
--

LOCK TABLES `professor_hat_stichpunkt` WRITE;
/*!40000 ALTER TABLE `professor_hat_stichpunkt` DISABLE KEYS */;
INSERT INTO `professor_hat_stichpunkt` VALUES (1,1,9),(1,2,2),(1,4,10),(1,8,5),(2,8,10),(3,6,8),(3,9,5),(3,18,10),(3,19,9),(3,23,7),(3,24,2),(4,1,9),(4,5,1),(4,8,8),(4,10,6),(4,13,3),(4,14,5),(5,17,1),(5,20,10),(6,7,10),(6,11,2),(7,2,4),(7,5,2),(7,10,3),(7,12,10),(7,16,6),(7,22,5),(8,15,8),(8,16,8),(8,17,10),(8,23,5),(9,14,7),(9,15,6),(9,19,4),(9,22,6),(10,21,6),(10,22,2);
/*!40000 ALTER TABLE `professor_hat_stichpunkt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stichpunkt`
--

DROP TABLE IF EXISTS `stichpunkt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stichpunkt` (
  `id_stichpunkt` int NOT NULL,
  `titel` varchar(45) NOT NULL,
  `beschreibung` varchar(450) NOT NULL,
  PRIMARY KEY (`id_stichpunkt`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stichpunkt`
--

LOCK TABLES `stichpunkt` WRITE;
/*!40000 ALTER TABLE `stichpunkt` DISABLE KEYS */;
INSERT INTO `stichpunkt` VALUES (1,'Datenanalyse','Datenanalyse'),(2,'Algorithm','Algorithm'),(3,'Structured data','Structured data\''),(4,'Big data','Big data'),(5,'Cyber security','Cyber security'),(6,'Heuristics','Heuristics'),(7,'Robots','Robots'),(8,'Statistics','Statistics'),(9,'SAP-Entwiklung','SAP-Entwiklung'),(10,'Projektmanagement','Projektmanagement'),(11,'Vorgehensmodelle','Vorgehensmodelle'),(12,'Migration von Softwaresystemen','Migration von Softwaresystemen'),(13,'Information Retrieval','Information Retrieval'),(14,'Scrum','Scrum'),(15,'Architekturen großer Anwendungssysteme','Architekturen großer Anwendungssysteme'),(16,'Java','Java'),(17,'Web Engineering','Web Engineering'),(18,'Simulation','Simulation'),(19,'Optimierung','Optimierung'),(20,'Safety','Safety'),(21,'IT-Sicherheit','IT-Sicherheit'),(22,'Requirements Engineering','Requirements Engineering'),(23,'Datenbanken','Datenbanken'),(24,'No-SQL','No-SQL');
/*!40000 ALTER TABLE `stichpunkt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `studierender`
--

DROP TABLE IF EXISTS `studierender`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `studierender` (
  `id_studierender` int NOT NULL,
  `mailadresse` varchar(45) NOT NULL,
  `vorname` varchar(45) NOT NULL,
  `nachname` varchar(45) NOT NULL,
  PRIMARY KEY (`id_studierender`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `studierender`
--

LOCK TABLES `studierender` WRITE;
/*!40000 ALTER TABLE `studierender` DISABLE KEYS */;
/*!40000 ALTER TABLE `studierender` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-11-04 13:13:40
