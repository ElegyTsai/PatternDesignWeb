-- MySQL dump 10.13  Distrib 8.0.23, for macos10.15 (x86_64)
--
-- Host: localhost    Database: UserDB
-- ------------------------------------------------------
-- Server version	8.0.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `latestUsedMaterialLog`
--

DROP TABLE IF EXISTS `latestUsedMaterialLog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `latestUsedMaterialLog` (
  `logId` bigint NOT NULL AUTO_INCREMENT,
  `timeOfLastUsing` varchar(50) DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `materialUrl` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`logId`)
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `latestUsedMaterialLog`
--

LOCK TABLES `latestUsedMaterialLog` WRITE;
/*!40000 ALTER TABLE `latestUsedMaterialLog` DISABLE KEYS */;
INSERT INTO `latestUsedMaterialLog` VALUES (2,'1624089348808',100,'/test/1'),(3,'1624089348812',200,'/test/2'),(35,'1624089588329',1,'/test/user0'),(36,'1624089588367',1,'/test/user1'),(37,'1624089588369',1,'/test/user2'),(38,'1624089588371',1,'/test/user3'),(39,'1624089588377',1,'/test/user4'),(40,'1624089588379',1,'/test/user5'),(41,'1624089588382',1,'/test/user6'),(42,'1624089588385',1,'/test/user7'),(43,'1624089588389',1,'/test/user8'),(44,'1624089588392',1,'/test/user9'),(45,'1624096591546',1,'/test5/user1/1'),(46,'1624098974344',2,'/test2/user1/0'),(47,'1624098974411',2,'/test2/user1/1'),(48,'1624098974417',2,'/test2/user1/2'),(49,'1624098974420',2,'/test2/user1/3'),(50,'1624098974426',2,'/test2/user1/4'),(51,'1624098974429',2,'/test2/user1/5'),(52,'1624098974435',2,'/test2/user1/6'),(53,'1624098974443',2,'/test2/user1/7'),(54,'1624098974447',2,'/test2/user1/8'),(55,'1624098974450',2,'/test2/user1/9'),(73,'1624351036993',1912872048,'http://localhost:8081/api/img/public/getNail/bird/0715014dc3404039a1e48cb87c49f627.png'),(74,'1624351029701',1912872048,'http://localhost:8081/api/img/public/getNail/bird/ff9d85a0b02148c9b0d6c52af92253ab.png'),(75,'1624350972042',1912872048,'http://localhost:8081/api/img/public/getNail/bird/1b8391af9e124357afe00ff96d8ae306.png'),(76,'1624099991542',1912872048,'/1'),(77,'1624099560320',1912872048,'/123/123/1211212344'),(78,'1624099557665',1912872048,'/123/123/12112123'),(79,'1624099554686',1912872048,'/123/123/12112'),(80,'1623918646236',1912872048,'/123/123/123/34'),(81,'1623918611038',1912872048,'/123/123/123/3'),(82,'1623918608769',1912872048,'/123/123/123/2'),(83,'1623918606059',1912872048,'/123/123/123/'),(84,'1623918540263',1912872048,'/123/123/123');
/*!40000 ALTER TABLE `latestUsedMaterialLog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loginLog`
--

DROP TABLE IF EXISTS `loginLog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loginLog` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `loginTime` varchar(80) DEFAULT NULL,
  `loginip` varchar(40) DEFAULT NULL,
  `username` varchar(40) DEFAULT NULL,
  `states` int DEFAULT NULL,
  `way` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=205 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loginLog`
--

LOCK TABLES `loginLog` WRITE;
/*!40000 ALTER TABLE `loginLog` DISABLE KEYS */;
INSERT INTO `loginLog` VALUES (1,'1622468218615','127.0.0.1','admin',1,1),(2,'1622468307004','127.0.0.1','123@123.com',0,1),(3,'1622468937054','127.0.0.1','123@123.com',0,1),(4,'1622468962270','127.0.0.1','123@123.com',0,1),(5,'1622468969636','127.0.0.1','admin',1,1),(6,'1622469450120','127.0.0.1','admin',1,1),(7,'1622469765539','127.0.0.1','admin',1,1),(8,'1622469966876','127.0.0.1','admin',1,1),(9,'1622479572666','127.0.0.1','admin',1,1),(10,'1622527325047','127.0.0.1','admin',1,1),(11,'1622527979367','127.0.0.1','admin',1,1),(12,'1622532003271','127.0.0.1','admin',1,1),(13,'1622532253793','127.0.0.1','admin',1,1),(14,'1622532363512','127.0.0.1','admin',1,1),(15,'1622532477632','127.0.0.1','admin',1,1),(16,'1622532792982','127.0.0.1','admin',1,1),(17,'1622533111332','127.0.0.1','admin',1,1),(18,'1622533524288','127.0.0.1','admin',1,1),(19,'1622533673214','127.0.0.1','admin',1,1),(20,'1622533800598','127.0.0.1','admin',1,1),(21,'1622533873095','127.0.0.1','admin',1,1),(22,'1622534165597','127.0.0.1','admin',1,1),(23,'1622534985028','127.0.0.1','admin',1,2),(24,'1622535037437','127.0.0.1','unknown',0,2),(25,'1622535115378','127.0.0.1','unknown',0,2),(26,'1622536768514','127.0.0.1','admin',1,2),(27,'1622536813468','127.0.0.1','admin',0,2),(28,'1622536826869','127.0.0.1','admin',0,2),(29,'1623221207222','127.0.0.1','admin',1,1),(30,'1623221336181','127.0.0.1','admin',1,2),(31,'1623221575505','127.0.0.1','admin',1,2),(32,'1623221694737','127.0.0.1','admin',1,2),(33,'1623224107935','127.0.0.1','admin',1,1),(34,'1623224121338','127.0.0.1','admin',1,2),(35,'1623224208555','127.0.0.1','admin',1,1),(36,'1623224215811','127.0.0.1','admin',1,2),(37,'1623225689846','127.0.0.1','admin',1,2),(38,'1623225794262','127.0.0.1','admin',1,2),(39,'1623226446031','127.0.0.1','admin',1,2),(40,'1623226523125','127.0.0.1','admin',1,2),(41,'1623226835165','127.0.0.1','admin',1,2),(42,'1623227018916','127.0.0.1','admin',1,2),(43,'1623228219440','127.0.0.1','unknown',0,2),(44,'1623228308185','127.0.0.1','unknown',0,2),(45,'1623228669337','127.0.0.1','unknown',0,2),(46,'1623228823123','127.0.0.1','unknown',0,2),(47,'1623228895204','127.0.0.1','unknown',0,2),(48,'1623229177056','127.0.0.1','unknown',0,2),(49,'1623229210362','127.0.0.1','admin',1,1),(50,'1623229231213','127.0.0.1','admin',1,2),(51,'1623229240543','127.0.0.1','admin',1,2),(52,'1623229286644','127.0.0.1','admin',1,2),(53,'1623229449643','127.0.0.1','admin',1,2),(54,'1623229500575','127.0.0.1','admin',1,2),(55,'1623229717337','127.0.0.1','admin',1,2),(56,'1623229759677','127.0.0.1','admin',1,2),(57,'1623230355749','127.0.0.1','admin',1,2),(58,'1623231050891','127.0.0.1','admin',1,2),(59,'1623231170310','127.0.0.1','admin',1,2),(60,'1623231258700','127.0.0.1','admin',1,2),(61,'1623231272511','127.0.0.1','admin',1,2),(62,'1623232172865','127.0.0.1','admin',1,2),(63,'1623232193977','127.0.0.1','admin',1,2),(64,'1623232440480','127.0.0.1','admin',1,2),(65,'1623233367712','127.0.0.1','unknown',0,2),(66,'1623233398387','127.0.0.1','admin',1,1),(67,'1623233517864','127.0.0.1','admin',1,1),(68,'1623233537519','127.0.0.1','admin',1,2),(69,'1623233566456','127.0.0.1','admin',1,2),(70,'1623233572960','127.0.0.1','unknown',0,2),(71,'1623233603359','127.0.0.1','admin',1,2),(72,'1623233613232','127.0.0.1','admin',1,2),(73,'1623740855311','127.0.0.1',NULL,0,1),(74,'1623740915551','127.0.0.1',NULL,0,1),(75,'1623741092321','127.0.0.1',NULL,0,1),(76,'1623741342366','127.0.0.1',NULL,0,1),(77,'1623741414710','127.0.0.1',NULL,0,1),(78,'1623742035978','127.0.0.1',NULL,0,1),(79,'1623742073191','127.0.0.1',NULL,0,1),(80,'1623742094017','127.0.0.1',NULL,0,1),(81,'1623742248328','127.0.0.1',NULL,0,1),(82,'1623742271920','127.0.0.1',NULL,0,1),(83,'1623742462474','127.0.0.1','787263005',1,1),(84,'1623742970964','127.0.0.1','787263005',1,1),(85,'1623742978369','127.0.0.1','787263005',1,1),(86,'1623742986361','127.0.0.1',NULL,0,1),(87,'1623743045984','127.0.0.1',NULL,0,1),(88,'1623743049034','127.0.0.1',NULL,0,1),(89,'1623743382322','127.0.0.1','1912872048',1,1),(90,'1623743385407','127.0.0.1',NULL,0,1),(91,'1623743388865','127.0.0.1',NULL,0,1),(92,'1623744031172','127.0.0.1','test4',0,1),(93,'1623744043202','127.0.0.1','test4',1,1),(94,'1623817060764','127.0.0.1','test4',1,1),(95,'1623817102407','127.0.0.1','test4',1,1),(96,'1623817360545','127.0.0.1','test4',1,1),(97,'1623817945247','127.0.0.1',NULL,0,1),(98,'1623818236325','127.0.0.1',NULL,0,1),(99,'1623818237768','127.0.0.1',NULL,0,1),(100,'1623818261112','127.0.0.1',NULL,0,1),(101,'1623818650140','127.0.0.1',NULL,0,1),(102,'1623818651624','127.0.0.1',NULL,0,1),(103,'1623818652662','127.0.0.1',NULL,0,1),(104,'1623818658548','127.0.0.1',NULL,0,1),(105,'1623819454175','127.0.0.1',NULL,0,1),(106,'1623819484028','127.0.0.1',NULL,0,1),(107,'1623819523502','127.0.0.1',NULL,0,1),(108,'1623819584929','127.0.0.1',NULL,0,1),(109,'1623819588135','127.0.0.1',NULL,0,1),(110,'1623819599529','127.0.0.1','test4',1,1),(111,'1623819602357','127.0.0.1','test4',1,1),(112,'1623819621273','127.0.0.1','18868104441',1,1),(113,'1623819796644','127.0.0.1',NULL,0,1),(114,'1623819952329','127.0.0.1',NULL,0,1),(115,'1623819955140','127.0.0.1',NULL,0,1),(116,'1623820016319','127.0.0.1',NULL,0,1),(117,'1623820159802','127.0.0.1',NULL,0,1),(118,'1623820205839','127.0.0.1',NULL,0,1),(119,'1623820211932','127.0.0.1',NULL,0,1),(120,'1623820731025','127.0.0.1',NULL,0,1),(121,'1623820913198','127.0.0.1','18868104441',1,1),(122,'1623820937787','127.0.0.1',NULL,0,1),(123,'1623821862042','127.0.0.1',NULL,0,1),(124,'1623821914413','127.0.0.1',NULL,0,1),(125,'1623822083936','127.0.0.1',NULL,0,1),(126,'1623822124455','127.0.0.1','18868104441',0,1),(127,'1623822238843','127.0.0.1',NULL,0,1),(128,'1623823217357','127.0.0.1',NULL,0,1),(129,'1623823330192','127.0.0.1',NULL,0,1),(130,'1623823403828','127.0.0.1',NULL,0,1),(131,'1623824199847','127.0.0.1',NULL,0,1),(132,'1623824326258','127.0.0.1',NULL,0,1),(133,'1623824327835','127.0.0.1',NULL,0,1),(134,'1623824333720','127.0.0.1',NULL,0,1),(135,'1623824473293','127.0.0.1',NULL,0,1),(136,'1623824547024','127.0.0.1',NULL,0,1),(137,'1623824708211','127.0.0.1',NULL,0,1),(138,'1623824710567','127.0.0.1',NULL,0,1),(139,'1623824722281','127.0.0.1',NULL,0,1),(140,'1623824732405','127.0.0.1',NULL,0,1),(141,'1623824735541','127.0.0.1',NULL,0,1),(142,'1623824791875','127.0.0.1',NULL,0,1),(143,'1623824802478','127.0.0.1',NULL,0,1),(144,'1623824806941','127.0.0.1',NULL,0,1),(145,'1623824907107','127.0.0.1',NULL,0,1),(146,'1623824919338','127.0.0.1',NULL,0,1),(147,'1623824920233','127.0.0.1',NULL,0,1),(148,'1623824921022','127.0.0.1',NULL,0,1),(149,'1623824970933','127.0.0.1',NULL,0,1),(150,'1623824972174','127.0.0.1',NULL,0,1),(151,'1623824984073','127.0.0.1','18868104441',0,1),(152,'1623824988331','127.0.0.1','18868104441',1,1),(153,'1623838655662','127.0.0.1','18868104441',1,1),(154,'1623838697179','127.0.0.1','unknown',0,2),(155,'1623838937309','127.0.0.1','unknown',0,2),(156,'1623839129707','127.0.0.1','unknown',0,2),(157,'1623839261281','127.0.0.1','1912872048',1,2),(158,'1623839343079','127.0.0.1','1912872048',1,2),(159,'1623839542185','127.0.0.1','18868104441',1,1),(160,'1623839548491','127.0.0.1','18868104441',1,1),(161,'1623917121804','127.0.0.1','1912872048',1,2),(162,'1623917183276','127.0.0.1','1912872048',1,2),(163,'1623918536365','127.0.0.1','1912872048',1,2),(164,'1623918537749','127.0.0.1','1912872048',1,2),(165,'1623918540257','127.0.0.1','1912872048',1,2),(166,'1623918596047','127.0.0.1','1912872048',1,2),(167,'1623918606050','127.0.0.1','1912872048',1,2),(168,'1623918608759','127.0.0.1','1912872048',1,2),(169,'1623918611026','127.0.0.1','1912872048',1,2),(170,'1623918613575','127.0.0.1','1912872048',1,2),(171,'1623918634796','127.0.0.1','1912872048',1,2),(172,'1623918639041','127.0.0.1','1912872048',1,2),(173,'1623918642087','127.0.0.1','1912872048',1,2),(174,'1623918646227','127.0.0.1','1912872048',1,2),(175,'1623918648408','127.0.0.1','1912872048',1,2),(176,'1624099554451','127.0.0.1','1912872048',1,2),(177,'1624099557642','127.0.0.1','1912872048',1,2),(178,'1624099560300','127.0.0.1','1912872048',1,2),(179,'1624099568633','127.0.0.1','1912872048',1,2),(180,'1624099991150','127.0.0.1','1912872048',1,2),(181,'1624099994590','127.0.0.1','1912872048',1,2),(182,'1624100045523','127.0.0.1','1912872048',1,2),(183,'1624348056032','127.0.0.1','18868104441',1,1),(184,'1624349115770','127.0.0.1','18868104441',1,1),(185,'1624349192727','127.0.0.1','18868104441',1,1),(186,'1624349322775','127.0.0.1','188680104441',0,1),(187,'1624349368580','127.0.0.1','188680104441',0,1),(188,'1624349372398','127.0.0.1','188680104441',0,1),(189,'1624349387554','127.0.0.1','188680104441',0,1),(190,'1624349643618','127.0.0.1','188680104441',0,1),(191,'1624349653335','127.0.0.1','18868104441',1,1),(192,'1624349664204','127.0.0.1','18868104441',0,1),(193,'1624349668216','127.0.0.1','18868104441',1,1),(194,'1624349771166','127.0.0.1','18868104441',1,1),(195,'1624349903755','127.0.0.1','18868104441',1,1),(196,'1624350396360','127.0.0.1','18868104441',1,1),(197,'1624350949728','127.0.0.1','18868104441',1,1),(198,'1624350971845','127.0.0.1','1912872048',1,2),(199,'1624350971944','127.0.0.1','1912872048',1,2),(200,'1624351029451','127.0.0.1','1912872048',1,2),(201,'1624351036957','127.0.0.1','1912872048',1,2),(202,'1624351293256','127.0.0.1','1912872048',1,2),(203,'1624352822345','127.0.0.1','1912872048',1,2),(204,'1624787186574','127.0.0.1','unknown',0,2);
/*!40000 ALTER TABLE `loginLog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permission` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(10) DEFAULT NULL,
  `resourceType` varchar(10) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `available` int DEFAULT NULL,
  `permission` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission`
--

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
INSERT INTO `permission` VALUES (1,'userP1',NULL,'/user/**',1,'add|query'),(2,'userP2',NULL,'/user/**',1,'update'),(3,'userP3',NULL,'/user/**',1,'delete');
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `relationOfUserImage`
--

DROP TABLE IF EXISTS `relationOfUserImage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `relationOfUserImage` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `UUID` varchar(80) DEFAULT NULL,
  `myGroup` varchar(80) DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `image_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `userIm_id` (`user_id`),
  KEY `image_id` (`image_id`),
  CONSTRAINT `image_id` FOREIGN KEY (`image_id`) REFERENCES `userImage` (`id`),
  CONSTRAINT `userIm_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `relationOfUserImage`
--

LOCK TABLES `relationOfUserImage` WRITE;
/*!40000 ALTER TABLE `relationOfUserImage` DISABLE KEYS */;
INSERT INTO `relationOfUserImage` VALUES (1,'02cfe271f5d4469eab367e96756946b0.JPG','Default',15,5),(3,'06bef157d9a44fbf84d464e341c3d276.JPG','Default',15,5);
/*!40000 ALTER TABLE `relationOfUserImage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `rolename` varchar(30) DEFAULT NULL,
  `available` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (3,'No need to log in','ROLE_GUEST',1),(4,'common user','ROLE_USER',1),(5,'permit all','ROLE_ADMIN',1);
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sysImage`
--

DROP TABLE IF EXISTS `sysImage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sysImage` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `thumbnailPath` varchar(80) DEFAULT NULL,
  `imagePath` varchar(80) DEFAULT NULL,
  `imageName` varchar(80) DEFAULT NULL,
  `MD5` varchar(40) DEFAULT NULL,
  `available` int DEFAULT NULL,
  `permission` varchar(80) DEFAULT NULL,
  `tag` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sysImage`
--

LOCK TABLES `sysImage` WRITE;
/*!40000 ALTER TABLE `sysImage` DISABLE KEYS */;
INSERT INTO `sysImage` VALUES (41,'/Users/elegy/Desktop/Data/Material/bird/th_416f48abce52429786f7b8c75ebdeb17.png','/Users/elegy/Desktop/Data/Material/bird/416f48abce52429786f7b8c75ebdeb17.png','416f48abce52429786f7b8c75ebdeb17.png',NULL,1,'all','bird'),(42,'/Users/elegy/Desktop/Data/Material/bird/th_b49c64ae81d94637b49dd3ded7806b6d.png','/Users/elegy/Desktop/Data/Material/bird/b49c64ae81d94637b49dd3ded7806b6d.png','b49c64ae81d94637b49dd3ded7806b6d.png',NULL,1,'all','bird'),(43,'/Users/elegy/Desktop/Data/Material/bird/th_26cd0264aeae4c39b62c968a2ab9b6ae.png','/Users/elegy/Desktop/Data/Material/bird/26cd0264aeae4c39b62c968a2ab9b6ae.png','26cd0264aeae4c39b62c968a2ab9b6ae.png',NULL,1,'all','bird'),(44,'/Users/elegy/Desktop/Data/Material/bird/th_c7a618d8e851426c89c49e1ebed6c822.png','/Users/elegy/Desktop/Data/Material/bird/c7a618d8e851426c89c49e1ebed6c822.png','c7a618d8e851426c89c49e1ebed6c822.png',NULL,1,'all','bird'),(45,'/Users/elegy/Desktop/Data/material/bird/th_4e76ab30f1f44a538f3864ea76b38e42.png','/Users/elegy/Desktop/Data/material/bird/4e76ab30f1f44a538f3864ea76b38e42.png','4e76ab30f1f44a538f3864ea76b38e42.png',NULL,1,'all','bird'),(46,'/Users/elegy/Desktop/Data/material/bird/th_81e91514f4d84d869ff951a2cd8da4fd.png','/Users/elegy/Desktop/Data/material/bird/81e91514f4d84d869ff951a2cd8da4fd.png','81e91514f4d84d869ff951a2cd8da4fd.png',NULL,1,'all','bird'),(47,'/Users/elegy/Desktop/Data/material/bird/th_c62a74fb4a20415284c733560b92318c.png','/Users/elegy/Desktop/Data/material/bird/c62a74fb4a20415284c733560b92318c.png','c62a74fb4a20415284c733560b92318c.png',NULL,1,'all','bird'),(48,'/Users/elegy/Desktop/Data/material/bird/th_7d19f68b096349e1a2dbc944227ab169.png','/Users/elegy/Desktop/Data/material/bird/7d19f68b096349e1a2dbc944227ab169.png','7d19f68b096349e1a2dbc944227ab169.png',NULL,1,'all','bird'),(49,'/Users/elegy/Desktop/Data/material/bird/th_84cc31e079834c1da3cb454879514830.png','/Users/elegy/Desktop/Data/material/bird/84cc31e079834c1da3cb454879514830.png','84cc31e079834c1da3cb454879514830.png',NULL,1,'all','bird'),(50,'/Users/elegy/Desktop/Data/material/bird/th_4ce5e60f397d40d58fd2a9ae970a4910.png','/Users/elegy/Desktop/Data/material/bird/4ce5e60f397d40d58fd2a9ae970a4910.png','4ce5e60f397d40d58fd2a9ae970a4910.png',NULL,1,'all','bird'),(51,'/Users/elegy/Desktop/Data/material/bird/th_11c4adfb3c07459cab511d31fb848f05.png','/Users/elegy/Desktop/Data/material/bird/11c4adfb3c07459cab511d31fb848f05.png','11c4adfb3c07459cab511d31fb848f05.png',NULL,1,'all','bird');
/*!40000 ALTER TABLE `sysImage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sysrole`
--

DROP TABLE IF EXISTS `sysrole`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sysrole` (
  `id` int NOT NULL AUTO_INCREMENT,
  `roleper_id` int DEFAULT NULL,
  `permission_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `roleper_id` (`roleper_id`),
  KEY `permission_id` (`permission_id`),
  CONSTRAINT `permission_id` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`),
  CONSTRAINT `roleper_id` FOREIGN KEY (`roleper_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sysrole`
--

LOCK TABLES `sysrole` WRITE;
/*!40000 ALTER TABLE `sysrole` DISABLE KEYS */;
INSERT INTO `sysrole` VALUES (1,3,1),(2,4,1),(3,4,2),(4,5,1),(5,5,2),(6,5,3);
/*!40000 ALTER TABLE `sysrole` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sysuser`
--

DROP TABLE IF EXISTS `sysuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sysuser` (
  `id` int NOT NULL AUTO_INCREMENT,
  `enabled` tinyint(1) DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `role_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `role_id` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sysuser`
--

LOCK TABLES `sysuser` WRITE;
/*!40000 ALTER TABLE `sysuser` DISABLE KEYS */;
INSERT INTO `sysuser` VALUES (1,NULL,1,4),(2,NULL,2,4),(11,NULL,11,4),(12,NULL,15,3),(14,NULL,15,5),(15,NULL,16,NULL),(16,NULL,16,NULL),(17,NULL,16,NULL),(33,NULL,1912872048,4);
/*!40000 ALTER TABLE `sysuser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `template`
--

DROP TABLE IF EXISTS `template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `template` (
  `id` int NOT NULL AUTO_INCREMENT,
  `thumbnailUrl` varchar(100) DEFAULT NULL,
  `url` varchar(100) DEFAULT NULL,
  `tag` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `template`
--

LOCK TABLES `template` WRITE;
/*!40000 ALTER TABLE `template` DISABLE KEYS */;
INSERT INTO `template` VALUES (1,'/thu/123123/123','/123/123/123/4','bird'),(2,'/thu/123123/123/2','/123/123/123/2','bird'),(3,'http://localhost:8081/api/template/getNail/bird/d806d9b7c93e4405ad25d7d23bb485f3.png','http://localhost:8081/api/template/getJson/bird/d806d9b7c93e4405ad25d7d23bb485f3.json','bird'),(4,'http://localhost:8081/api/template/getNail/bird/5784449737764357b1a9e39db870c1bf.png','http://localhost:8081/api/template/getJson/bird/5784449737764357b1a9e39db870c1bf.json','bird'),(5,'http://localhost:8081/server_resource/bird/a184089604254eaf82a2fcf7d558c50a.png','http://localhost:8081/server_resource/bird/a184089604254eaf82a2fcf7d558c50a.json','bird'),(6,'http://localhost:8081/server_resource/template/bird/e69fed567fd64d118dae214c199cdf27.png','http://localhost:8081/server_resource/template/bird/e69fed567fd64d118dae214c199cdf27.json','bird');
/*!40000 ALTER TABLE `template` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `enabled` int DEFAULT NULL,
  `createTime` varchar(80) DEFAULT NULL,
  `lastModify` varchar(80) DEFAULT NULL,
  `outDate` varchar(80) DEFAULT NULL,
  `active` int DEFAULT NULL,
  `validationCode` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2081262515 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'www','myword','$2a$10$tu6nb4irjKFQ./F3j1anM.yd517LHSSPyciAv/2O/z9Nt2AOGkzyC',NULL,1,'1622204353756','1622204353756',NULL,NULL,NULL),(2,'wei','123@qq.com','$2a$10$W8I1LcqLTOQUVV8PooUC2.45cI2TbYCOpp8t8gs2A0rlnTVXee7Tu',NULL,1,'1622213758083','1622213758083','111111',NULL,'123'),(11,'wei_test','547442993@qq.com','$2a$10$Ij84tR.gCuszTfhcRWauFuBC56xFsi8ui.Cu7mkNeSY9Spy02ep76',NULL,1,'1622347895256','1622347895256',NULL,1,NULL),(14,'add test','111@123.com','$2a$10$/mL5ePJfmB1KS0SAhOf4vOKjjihgA/NrPZW1Ut0X0GOlNSDhIPJHy',NULL,1,'1622447481822','1622447481822',NULL,NULL,NULL),(15,'admin','123@123.com','$2a$10$ldTVhqnwRVWKLWeYNrQ6QuuLwSKf4.LHf/OzCgcUo.nwZGWAcKS8K',NULL,1,'1622464678385','1622464678385',NULL,NULL,NULL),(16,'caiwei_admin','12345@qq.com','password577','11111',1,'0','0',NULL,NULL,NULL),(1912872048,'test4',NULL,'$2a$10$K907QL.Bx9PqMQ3NOugfgOtHoKzqICzZ4O6.FqJngCSRhIVf63trW','18868104441',1,'1623743362542','1623743362542',NULL,1,NULL),(2081262514,'caiwei_admin','12345@qq.com','password577','11111',0,'0','0',NULL,0,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userImage`
--

DROP TABLE IF EXISTS `userImage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `userImage` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `thumbnailPath` varchar(80) DEFAULT NULL,
  `imagePath` varchar(80) DEFAULT NULL,
  `imageName` varchar(80) DEFAULT NULL,
  `MD5` varchar(40) DEFAULT NULL,
  `available` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userImage`
--

LOCK TABLES `userImage` WRITE;
/*!40000 ALTER TABLE `userImage` DISABLE KEYS */;
INSERT INTO `userImage` VALUES (2,'/home/desktop/1','/home/desktop/','testpic3','adasdasd',1),(3,'/home/desktop/1','/home/desktop/','testpic3','adasdsd',1),(4,'/home/desktop/1','/home/desktop/','testpic4','adasdsdssr',1),(5,'/Users/elegy/Desktop/ImageData/th_db638c55e1324843b29266ce6735d81b.JPG','/Users/elegy/Desktop/ImageData/db638c55e1324843b29266ce6735d81b.JPG','db638c55e1324843b29266ce6735d81b.JPG','cfbe4f9ffa4a5a9c33090f4323d03b04',1);
/*!40000 ALTER TABLE `userImage` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-07-31 13:22:56
