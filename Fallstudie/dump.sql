CREATE DATABASE  IF NOT EXISTS `bips` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `bips`;
-- MySQL dump 10.13  Distrib 8.0.31, for Linux (x86_64)
--
-- Host: localhost    Database: bips
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
-- Table structure for table `keyword`
--

DROP TABLE IF EXISTS `keyword`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `keyword` (
  `id_keyword` int NOT NULL AUTO_INCREMENT,
  `text` varchar(255) NOT NULL,
  PRIMARY KEY (`id_keyword`,`text`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `keyword`
--

LOCK TABLES `keyword` WRITE;
/*!40000 ALTER TABLE `keyword` DISABLE KEYS */;
INSERT INTO `keyword` VALUES (1,'Datenanalyse'),
                             (2,'Algorithm'),
                             (3,'Structured data'),
                             (4,'Big data'),
                             (5,'Cyber security'),
                             (6,'Heuristic'),
                             (7,'Robot'),
                             (8,'Statistic');
/*!40000 ALTER TABLE `keyword` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supervisor`
--

DROP TABLE IF EXISTS `supervisor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supervisor` (
  `id_supervisor` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `id_keyword` int NOT NULL,
  PRIMARY KEY (`id_supervisor`,`name`,`id_keyword`),
  KEY `fk_s_has_k_idx` (`id_keyword`),
  CONSTRAINT `fk_s_has_k` FOREIGN KEY (`id_keyword`) REFERENCES `keyword` (`id_keyword`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supervisor`
--

LOCK TABLES `supervisor` WRITE;
/*!40000 ALTER TABLE `supervisor` DISABLE KEYS */;
INSERT INTO `supervisor` VALUES (6,'Prof. Dr. Ludwig',1),
                                (3,'Prof. Dr. Schiering ',2),
                                (5,'Prof. Dr. Dirk Lehmann',3),
                                (2,'Prof. Dr. Höppner ',4),
                                (7,'Prof. Dr. Meyer',5),
                                (1,'Prof. Dr. Gutenschwager',6),
                                (8,'Prof. Dr. Gerndt ',7),
                                (4,'Prof. Dr. Frank Klawonn ',8);
/*!40000 ALTER TABLE `supervisor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supervisor_has_keyword`
--

DROP TABLE IF EXISTS `supervisor_has_keyword`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supervisor_has_keyword` (
  `id_supervisor` int NOT NULL,
  `id_keyword` int NOT NULL,
  PRIMARY KEY (`id_supervisor`,`id_keyword`),
  KEY `fk_shk_supervisor_idx` (`id_supervisor`),
  KEY `fk_shk_has_k_idx` (`id_keyword`),
  CONSTRAINT `fk_shk_has_k` FOREIGN KEY (`id_keyword`) REFERENCES `keyword` (`id_keyword`),
  CONSTRAINT `fk_shk_has_s` FOREIGN KEY (`id_supervisor`) REFERENCES `supervisor` (`id_supervisor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supervisor_has_keyword`
--

LOCK TABLES `supervisor_has_keyword` WRITE;
/*!40000 ALTER TABLE `supervisor_has_keyword` DISABLE KEYS */;
INSERT INTO `supervisor_has_keyword` VALUES (1,6),(2,1),(2,4),(3,2),(4,8),(5,3),(6,1),(7,5),(8,7);
/*!40000 ALTER TABLE `supervisor_has_keyword` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-10-25 16:43:58
