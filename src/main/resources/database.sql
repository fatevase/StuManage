-- MySQL dump 10.13  Distrib 5.7.29, for Win64 (x86_64)
--
-- Host: localhost    Database: db_stumanage
-- ------------------------------------------------------
-- Server version	5.7.29-log

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
-- Table structure for table `t_choosecourse`
--

DROP TABLE IF EXISTS `t_choosecourse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_choosecourse` (
  `ccid` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `coid` int(11) NOT NULL,
  `starttime` int(11) DEFAULT '0',
  `endtime` int(11) DEFAULT '0',
  `chosetime` int(11) DEFAULT '0',
  PRIMARY KEY (`ccid`),
  KEY `StudentCC_ID` (`uid`),
  KEY `CourseCC_ID` (`coid`),
  CONSTRAINT `CourseCC_ID` FOREIGN KEY (`coid`) REFERENCES `t_courses` (`coid`),
  CONSTRAINT `StudentCC_ID` FOREIGN KEY (`uid`) REFERENCES `t_users` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_choosecourse`
--

LOCK TABLES `t_choosecourse` WRITE;
/*!40000 ALTER TABLE `t_choosecourse` DISABLE KEYS */;
INSERT INTO `t_choosecourse` VALUES (1,7,2,1577808000,1605052800,0),(2,3,4,1325347200,1325347200,1595174400),(3,1,3,1587513600,1596067200,1595260800),(4,1,5,0,0,1595260800),(5,1,1,1325347200,1325347200,1595260800),(6,1,2,1577808000,1605052800,1595260800);
/*!40000 ALTER TABLE `t_choosecourse` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_courses`
--

DROP TABLE IF EXISTS `t_courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_courses` (
  `coid` int(11) NOT NULL AUTO_INCREMENT,
  `coursename` varchar(64) NOT NULL,
  `coursecredit` int(11) DEFAULT '0',
  `teachername` varchar(32) DEFAULT '未知',
  `teacherid` int(11) DEFAULT '0',
  PRIMARY KEY (`coid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_courses`
--

LOCK TABLES `t_courses` WRITE;
/*!40000 ALTER TABLE `t_courses` DISABLE KEYS */;
INSERT INTO `t_courses` VALUES (1,'高等数学',3,'高晓松',-1),(2,'JSP高级框架',3,'高晓松',-1),(3,'JAVA高级语言',4,'高晓松',2),(4,'PYTHON网络应用',2,'朱莉',-1),(5,'大学语文',2,'李丽',0);
/*!40000 ALTER TABLE `t_courses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_users`
--

DROP TABLE IF EXISTS `t_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_users` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) DEFAULT NULL,
  `password` varchar(64) DEFAULT NULL,
  `schoolid` int(11) DEFAULT NULL,
  `sex` varchar(2) DEFAULT '男',
  `age` int(11) DEFAULT '0',
  `hometown` varchar(64) DEFAULT NULL,
  `department` varchar(64) DEFAULT NULL,
  `validatekey` varchar(64) DEFAULT '0',
  `created` int(11) DEFAULT '0',
  `activated` int(11) DEFAULT '0',
  `groups` varchar(16) DEFAULT 'student',
  `uclass` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_users`
--

LOCK TABLES `t_users` WRITE;
/*!40000 ALTER TABLE `t_users` DISABLE KEYS */;
INSERT INTO `t_users` VALUES (1,'蒋白','e10adc3949ba59abbe56e057f20f883e',2016,'男',22,'黑龙江省哈尔滨市','电子信息工程学院','',0,0,'student','软件一班'),(2,'高晓松','96e79218965eb72c92a549dd5a330112',201652545,'男',21,'北京朝阳','电子信息工程学院','0',0,0,'teacher','教研组'),(3,'admin','21232f297a57a5a743894a0e4a801fc3',666666,'男',21,'admin','admin','0',0,0,'admin','3班'),(4,'柏柏','e10adc3949ba59abbe56e057f20f883e',2019125,'男',777,'666','444','0',1594224000,1594224000,'student','555'),(5,'李白','444',2019128,'男',21,'333','444','0',1594224000,1594224000,'student','33331'),(6,'晓厄','96e79218965eb72c92a549dd5a330112',2019129,'男',11,'111','1111','0',1595174400,1595174400,'student','3333'),(7,'杜甫','b0baee9d279d34fa1dfd71aadb908c3f',2019130,'男',1111,'11','1111','0',1595174400,1595174400,'student','111');
/*!40000 ALTER TABLE `t_users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-07-22  8:42:54
