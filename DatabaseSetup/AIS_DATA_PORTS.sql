-- MySQL dump 10.13  Distrib 5.7.12, for osx10.9 (x86_64)
--
-- Host: 127.0.0.1    Database: AIS_DATA
-- ------------------------------------------------------
-- Server version	5.5.5-10.1.16-MariaDB

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
-- Table structure for table `PORTS`
--

DROP TABLE IF EXISTS `PORTS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PORTS` (
  `PORTNAME` varchar(255) NOT NULL,
  `COUNTRY` varchar(255) DEFAULT NULL,
  `LATITUDE` float DEFAULT NULL,
  `LONGITUDE` float DEFAULT NULL,
  PRIMARY KEY (`PORTNAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PORTS`
--

LOCK TABLES `PORTS` WRITE;
/*!40000 ALTER TABLE `PORTS` DISABLE KEYS */;
INSERT INTO `PORTS` VALUES ('AASIAAT','GL',68.7,-52.8667),('AKUREYRI','IS',65.6833,-18.0833),('ALGOMA','US',44.6,-87.4333),('ASHLAND','US',46.6,-90.8667),('ATTU','GL',67.95,-53.7333),('BARAGA','US',46.7833,-88.4833),('BATCHAWANA BAY','CA',46.9167,-84.6),('BATEAU HARBOR','CA',53.4,-55.7667),('BATTLE HARBOR','CA',52.2667,-55.5833),('BAYFIELD','US',46.8,-90.8167),('BILDUDALUR','IS',65.6833,-23.6),('BLONDUOS','IS',65.6667,-20.3),('BOLUNGAVIK','IS',66.1667,-23.2333),('BONAVISTA HARBOR','CA',48.65,-53.1167),('BOTWOOD HARBOR','CA',49.15,-55.3333),('BUDIR','IS',64.9333,-14.0167),('BUFFINGTON','US',41.6333,-87.4167),('CALUMET HARBOR','US',41.7333,-87.5167),('CARTWRIGHT HARBOR','CA',53.7,-57.0333),('CATALINA HARBOR','CA',48.5,-53.0833),('CHARLEVOIX','US',45.3167,-85.2667),('CHEBOYGAN','US',45.65,-84.4667),('CHICAGO','US',41.8833,-87.6),('CHURCHILL','CA',58.7833,-94.1833),('CONCHE','CA',50.8833,-55.9),('COPPER HARBOR','US',47.4667,-87.8667),('CORNUCOPIA','US',46.8667,-91.1),('DALVIK','IS',65.9833,-18.5167),('DEPERE','US',44.45,-88.0667),('DJUPIVOGUR','IS',64.6667,-14.2833),('DULUTH','US',46.7667,-92.1),('EAGLE HARBOR','US',47.4667,-88.1667),('ESCANABA','US',45.7667,-87.05),('ESKIFJORDHUR','IS',65.0667,-14.0167),('FORTUNE HARBOR','CA',49.5333,-55.2333),('FRANKFORT','US',44.6333,-86.25),('GARGANTUA','CA',47.5667,-84.9667),('GARY','US',41.6167,-87.3333),('GLADSTONE','US',45.85,-87.0167),('GOOSE BAY','CA',53.3667,-60.3),('GOULAIS BAY','CA',46.7333,-84.5),('GRAND HAVEN','US',43.0667,-86.2333),('GRAND MARAIS','US',46.6833,-85.9667),('GREAT LAKES','US',42.3,-87.8333),('GREEN BAY','US',44.5167,-88.0167),('GRONNEDAL (KANGILINNGUIT);','GL',61.2333,-48.1),('GRUNDARTANGI','IS',64.2667,-22),('HAFNARFJORDUR','IS',64.0667,-21.95),('HARBOR SPRINGS','US',45.4333,-84.9833),('HERON BAY','CA',48.65,-86.3167),('HOLLAND','US',42.7833,-86.1333),('HOPEDALE HARBOR','CA',55.45,-60.2),('HORNABJORDUR','IS',64.25,-15.2333),('HOUGHTON','US',47.1167,-88.5667),('HUSAVIK','IS',66.0333,-17.3333),('ILLULISSAT (JAKOBSHAVN);','GL',69.2167,-51.1),('INDIANA HARBOR','US',41.6667,-87.4333),('ISAFJORDUR','IS',66.0667,-23.1167),('KAJALLEQ UPERNAVIK','GL',72.15,-55.5333),('KANGAMIUT','GL',65.8167,-53.3),('KANGERLUARSORUSEQ','GL',63.7,-51.55),('KANGERLUSUAQ','GL',66.9667,-50.95),('KAUKAUNA','US',44.2833,-88.2667),('KENOSHA','US',42.5833,-87.8167),('KEWAUNEE','US',44.4667,-87.5),('KOPASKER','IS',66.2833,-16.45),('KUSANARTOQ','GL',61.7667,-42.2167),('L ANSE','US',46.7667,-88.4667),('LA POINTE','US',46.7833,-90.7833),('LA SCIE HARBOR','CA',49.9667,-55.6167),('LELAND','US',45.0167,-85.7667),('LEWISPORTE','CA',49.25,-55.05),('LITTLE BAY','CA',49.6167,-56),('LUDINGTON','US',43.95,-86.45),('MACKINAC ISLAND','US',45.85,-84.6167),('MACKINAW CITY','US',45.7833,-84.7167),('MANIITSOQ','GL',65.4167,-52.9167),('MANISTEE','US',44.25,-86.3167),('MANISTIQUE','US',45.95,-86.25),('MANITOWOC','US',44.1,-87.65),('MARICOURT','CA',61.6,-71.95),('MARINETTE','US',45.0833,-87.6),('MARQUETTE','US',46.5333,-87.3833),('MENOMINEE','US',45.1,-87.6),('MICHIGAN CITY','US',41.7333,-86.9),('MICHIPICOTEN','CA',47.9667,-84.9),('MILWAUKEE','US',43.0333,-87.8833),('MUNISING','US',46.4167,-86.65),('MUSKEGON','US',43.2333,-86.2667),('NAHMA','US',45.8333,-86.6667),('NANORTALIK HAVN','GL',60.1167,-45.2),('NARSAQ','GL',60.9167,-46.05),('NARSARSSUAQ','GL',61.15,-45.4333),('NESKAUPSTADUR','IS',65.15,-13.6833),('NIAQORNAT','GL',70.8,-53.6667),('NORTH WEST RIVER','CA',53.5333,-60.15),('NUUK','GL',64.1833,-51.75),('OCONTO','US',44.9,-87.8333),('OLAFSFJORDHUR','IS',66.0833,-18.6333),('ONTONAGON','US',46.8833,-89.3333),('PAAMUIT (FREDERIKSHAB);','GL',62,-49.6667),('PADLOPING ISLAND','CA',67.0333,-62.7333),('PANGNIRTUNG','CA',66.1333,-65.75),('PETOSKEY','US',45.3667,-84.9667),('PITUFFIK (THULE AIR BASE);','GL',76.5333,-68.8667),('POND INLET','CA',72.75,-76.75),('PORT INLAND','US',45.9667,-85.8667),('PORT WASHINGTON','US',43.3833,-87.8667),('PORT WING','US',46.7833,-91.3833),('PRESQUE ISLE','US',46.5667,-87.3833),('QAQURTOQ','GL',60.7167,-46.05),('QASIGIANNGUIT-CHRISTIANSHAB','GL',68.8167,-51.1833),('QEQERTARSUAQ','GL',69.2333,-53.5333),('QUEBEC HARBOUR','CA',47.7,-85.8),('QUTDLEQ','GL',61.5167,-42.2167),('RACINE','US',42.7333,-87.7833),('RAUFARHOFN','IS',66.45,-15.9333),('REYKJAVIK','IS',64.15,-21.9333),('RIGOLET','CA',54.1833,-58.4167),('RODDICKTON','CA',50.8667,-56.1333),('ROGERS CITY','US',45.45,-83.8167),('SAGLEK BAY','CA',58.5833,-62.8333),('SAUDARKROKUR','IS',65.75,-19.6667),('SAUGATUCK','US',42.6667,-86.2),('SAULT STE MARIE','US',46.5,-84.35),('SEAL COVE','CA',49.9333,-56.3833),('SEYDHISFJORDHUR','IS',65.2667,-14),('SHEBOYGAN','US',43.75,-87.7),('SIGLUFJORHURD','IS',66.15,-18.9167),('SISIMIUT','GL',66.9167,-53.7),('SKAGASTROND','IS',65.8333,-20.3167),('SKERJAFJORDUR','IS',64.15,-22.0167),('SOUTH HAVEN','US',42.4,-86.2833),('SPRINGDALE','CA',49.5,-56.0667),('ST ANTHONY HARBOR','CA',51.3667,-55.5833),('ST IGNACE','US',45.85,-84.7167),('ST JOSEPH','US',42.1167,-86.4833),('ST. JAMES HARBOR','US',45.7333,-85.5167),('STRAUMSVIK','IS',64.05,-22.05),('STURGEON BAY','US',44.8333,-87.3833),('SUPERIOR','US',46.7333,-92.0667),('TERRINGTON BASIN','CA',53.35,-60.4),('THINGEYRI','IS',65.8833,-23.4833),('THUNDER BAY','CA',48.4,-89.2333),('TRAVERSE CITY','US',44.7667,-85.6167),('TWILLINGATE HARBOR','CA',49.6667,-54.7667),('TWO HARBORS','US',47.0167,-91.65),('TWO RIVERS','US',44.15,-87.5667),('UPERNAVIK','GL',72.7667,-56.15),('UUMMANNAQ HARBOR','GL',70.6833,-52.15),('VESTMANNAEYJAR','IS',63.4333,-20.2833),('VOPNAFJORDHUR','IS',65.75,-14.8333),('WASHBURN','US',46.6667,-90.8833),('WAUKEGAN','US',42.3667,-87.8167),('WESTPORT COVE','CA',49.7833,-56.6167),('WHITE LAKE','US',43.3833,-86.35),('WILMETTE','US',42.0833,-87.6833);
/*!40000 ALTER TABLE `PORTS` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-09-21 14:28:13
