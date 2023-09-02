-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: cems
-- ------------------------------------------------------
-- Server version	8.0.32

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
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course` (
  `number` varchar(128) NOT NULL,
  `name` varchar(128) DEFAULT '',
  `subjectNum` varchar(128) DEFAULT '',
  PRIMARY KEY (`number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` VALUES ('10','Algebra','22'),('11','Electrical circuits','25'),('12','Algorithms','23'),('13','Logic','22'),('14','Mechanics','26');
/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hod_subject`
--

DROP TABLE IF EXISTS `hod_subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hod_subject` (
  `hodId` varchar(45) DEFAULT NULL,
  `subjectNumber` varchar(45) NOT NULL,
  PRIMARY KEY (`subjectNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hod_subject`
--

LOCK TABLES `hod_subject` WRITE;
/*!40000 ALTER TABLE `hod_subject` DISABLE KEYS */;
INSERT INTO `hod_subject` VALUES ('41234561','22'),('41234560','23'),('41234562','24'),('41234563','25'),('41234564','26');
/*!40000 ALTER TABLE `hod_subject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `question` (
  `id` varchar(128) NOT NULL,
  `number` int DEFAULT '0',
  `question` varchar(1024) DEFAULT '',
  `subjectNum` varchar(128) DEFAULT '',
  `lecturerId` varchar(128) DEFAULT '',
  `answer1` varchar(1024) DEFAULT '',
  `answer2` varchar(1024) DEFAULT '',
  `answer3` varchar(1024) DEFAULT '',
  `answer4` varchar(1024) DEFAULT '',
  `correctAnswer` int DEFAULT '0',
  `instructions` varchar(2056) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES ('22101',101,'Which logical operator represents the conjunction (AND) operation?','23','31234560','OR','AND','IF-THEN','NOT',2,'Marking a correct answer is done by clicking the button next to the correct answer in your opinion'),('22102',102,'Which logical operator represents the disjunction (OR) operation?','23','31234560','NOT','OR','AND','IF-THEN',2,'Marking a correct answer is done by clicking the button next to the correct answer in your opinion'),('22103',103,'Which logical operator represents the negation (NOT) operation?','23','31234560','NOT','AND','IF-THEN','OR',1,'Marking a correct answer is done by clicking the button next to the correct answer in your opinion'),('22104',104,'Which logical operator represents the implication (IF-THEN) operation?','23','31234560','OR','AND','NOT','IF-THEN',4,'Marking a correct answer is done by clicking the button next to the correct answer in your opinion'),('22105',105,'Which logical operator represents the biconditional (IF AND ONLY IF) operation?','23','31234560','IF-THEN','NOT','OR','AND',3,'Marking a correct answer is done by clicking the button next to the correct answer in your opinion'),('22106',106,'Which property of addition states that changing the order of the numbers being added does not affect the sum?','22','31234561','Associative Property','Commutative Property','Distributive Property','Identity Property',2,'Marking a correct answer is done by clicking the button next to the correct answer in your opinion'),('22107',107,'Which mathematical operation is the inverse of multiplication?','22','31234561','Addition','Subtraction','Division','Exponentiation',3,'Marking a correct answer is done by clicking the button next to the correct answer in your opinion'),('22108',108,'What is the solution to the equation 3x - 7 = 12?','22','31234561','x = 3','x = 5','x = 7','x = 17',2,'Marking a correct answer is done by clicking the button next to the correct answer in your opinion'),('22109',109,'Which property  state that the order of multiplication doesnt affect the product?','22','31234561','Distributive Property','Associative Property','Identity Property','Commutative Property',4,'Marking a correct answer is done by clicking the button next to the correct answer in your opinion'),('22110',110,'Which mathematical operation is the inverse of subtraction?','22','31234561','Subtraction','Exponentiation','Division','Addition',4,'Marking a correct answer is done by clicking the button next to the correct answer in your opinion'),('23101',101,'Which data structure follows the First-In-First-Out (FIFO) principle?','23','31234562','Tree','Linked list','Queue','Stack',3,'Marking a correct answer is done by clicking the button next to the correct answer in your opinion'),('23102',102,'Which data structure organizes elements in a sorted manner?','23','31234562','Stack','Queue','Array','Binary search tree',4,'Marking a correct answer is done by clicking the button next to the correct answer in your opinion'),('23103',103,'Which data structure provides constant-time access to its elements?','23','31234562','Array','Queue','Stack','Singly linked list',1,'Marking a correct answer is done by clicking the button next to the correct answer in your opinion'),('23104',104,'Which data structure allows efficient searching, insertion, and deletion operations, assuming the keys are unique?','23','31234562','Stack','Heap','Hash table','Queue',3,'Marking a correct answer is done by clicking the button next to the correct answer in your opinion'),('23105',105,'Which data structure represents a hierarchical structure with a set of connected nodes?','23','31234562','Queue','Tree','Array','Stack',2,'Marking a correct answer is done by clicking the button next to the correct answer in your opinion'),('23106',106,'Which algorithmic paradigm focuses on making locally optimal choices at each stage to achieve a global optimum?','23','31234562','Dynamic programming','Greedy algorithms','Backtracking','Divide-and-conquer',2,'Marking a correct answer is done by clicking the button next to the correct answer in your opinion'),('23107',107,'What is the time complexity of the QuickSort algorithm in the average case?','23','31234562','O(n)','O(n log n)','O(n^2)','O(log n)',2,'Marking a correct answer is done by clicking the button next to the correct answer in your opinion'),('23108',108,'Which data structure is commonly used to implement a queue?','23','31234562','Stack','Queue','Linked List','Heap',3,'Marking a correct answer is done by clicking the button next to the correct answer in your opinion'),('23109',109,'What is the main idea behind the Breadth-First Search algorithm?','23','31234561','Exploring the graph in a depthward motion','Exploring the graph in a breadthward motion','Selecting the maximum element in an array','Finding the shortest path in a weighted graph',2,'Marking a correct answer is done by clicking the button next to the correct answer in your opinion'),('23110',110,'What is the primary purpose of the Merge Sort algorithm?','23','31234561','Searching for an element in an array','Sorting elements in ascending order','Rearranging elements in a random order','Removing duplicates from an array',2,'Marking a correct answer is done by clicking the button next to the correct answer in your opinion'),('23111',111,'What is the purpose of the bubble sort algorithm?','23','31234561','To search for a specific element in an array','To sort an array in ascending orderc element in an array','To calculate the factorial of a number','To find the maximum value in an array',2,'Bubble sort compares adjacent elements and swaps them if they are in the wrong order.'),('23112',112,'What is the time complexity of the binary search algorithm?','23','31234561','O(1)','O(log n)','O(n)','O(n^2)',2,'Binary search repeatedly divides the search interval in half.'),('23113',113,'What is the purpose of the depth-first search (DFS) algorithm?','23','31234561','To find the shortest path in a graph','To traverse a tree or graph data structure','To determine if a graph is acyclic','To find the minimum spanning tree in a graph',2,'DFS explores as far as possible along each branch before backtracking.'),('23114',114,'What does the term \"recursion\" refer to in computer programming?','23','31234561','The process of dividing a problem into smaller subproblems','The process of repeating a set of instructions until a certain condition is met','The process of a function calling itself','The process of converting high-level code into machine code',3,'Recursion involves solving a problem by breaking it down into smaller instances of the same problem'),('23125',125,'What is the time complexity of inserting an element at the end of an array?','23','31234562','O(1)','O(n)','O(log n)','O(n^2)',1,'Consider the underlying implementation of arrays and how elements are accessed in memory.'),('23126',126,'Which data structure follows the Last-In-First-Out (LIFO) principle?','23','31234562','Queue','Stack','Linked List','Binary Tree',2,'Visualize a stack of plates or books to understand the concept of LIFO.'),('23127',127,'What is the purpose of a hash table?','23','31234562','To maintain a sorted collection of elements','To implement a FIFO queue','To perform quick searching and insertion operations','To represent hierarchical relationships between elements',3,'Think about how a hash function can efficiently map keys to their corresponding values.'),('23128',128,'Which data structure is typically used for implementing a breadth-first search (BFS)?','23','31234562','Queue','Stack','Linked List','Binary Tree',1,'Consider the order in which elements are processed in a BFS traversal.'),('23129',129,'What is the purpose of a priority queue?','23','31234562','To maintain a sorted collection of elements','To implement a FIFO queue','To perform quick searching and insertion operations','To represent hierarchical relationships between elements',1,'Consider how priority is assigned to elements and how they are ordered within the priority queue.'),('26115',115,'What is the formula for calculating the force exerted by a spring?','26','31234561','F = mv','F = ma','F = kx','F = mg',3,'Consider Hooke\'s Law and the relationship between force and displacement in a spring.'),('26116',116,'What is the unit of measurement for angular velocity?','26','31234561','Meters per second (m/s)','Radians per second (rad/s)','Kilograms (kg)','Newtons (N)',2,'Angular velocity is the rate of change of angular displacement and is measured in radians per second.'),('26117',117,'What is the formula for calculating work done?','26','31234561','W = Fd','W = mg','W = pt','W = mv^2',1,'Think about the relationship between force and displacement when work is done.'),('26118',118,'Which principle states that the total momentum of a system remains constant if no external forces act on it?','26','31234561','Archimedes\' principle','Newton\'s third law of motion','Law of conservation of energy','Law of conservation of momentum',4,'Consider the conservation principle related to the motion of objects.'),('26119',119,'What is the formula for calculating the moment of inertia of a rotating object?','26','31234561','I = mv','I = Fd','I = 1/2 mv^2','I = mr^2',4,'Think about the distribution of mass and the distance from the axis of rotation.'),('26120',120,'What is the relationship between force, mass, and acceleration according to Newton\'s second law of motion?','26','31234561','F = ma','F = mg','F = m/v','F = m^2a',1,'Consider how force affects the acceleration of an object based on its mass.'),('26121',121,'Which law of motion states that for every action, there is an equal and opposite reaction?','26','31234561','Newton\'s first law of motion','Newton\'s second law of motion','Newton\'s third law of motion','Newton\'s law of universal gravitation',3,'Think about the interaction between two objects and the forces they exert on each other.'),('26122',122,'What is the formula for calculating the velocity of an object undergoing constant acceleration?','26','31234561','v = u + at','v = s/t','v = F/m','v = m^2a',1,'Consider the initial velocity, acceleration, and time taken for an object to reach a certain velocity.'),('26123',123,'What is the formula for calculating the gravitational potential energy of an object?','26','31234561','PE = mgh','PE = mv','PE = Fd','PE = kx^2',1,'Think about the relationship between an object\'s mass, height, and the acceleration due to gravity.'),('26124',124,'What is the relationship between force, mass, and acceleration in circular motion?','26','31234561','F = ma','F = mg','F = mv^2/r','F = m^2a',3,'Consider the centripetal force and the relationship between velocity, radius of curvature, and acceleration.');
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question_course`
--

DROP TABLE IF EXISTS `question_course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `question_course` (
  `questionId` varchar(128) NOT NULL,
  `courseNum` varchar(128) NOT NULL,
  PRIMARY KEY (`questionId`,`courseNum`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question_course`
--

LOCK TABLES `question_course` WRITE;
/*!40000 ALTER TABLE `question_course` DISABLE KEYS */;
INSERT INTO `question_course` VALUES ('22101','13'),('22102','13'),('22103','13'),('22104','13'),('22105','13'),('22106','10'),('22107','10'),('22108','10'),('22109','10'),('22110','10'),('23101','12'),('23102','12'),('23103','12'),('23104','12'),('23105','12'),('23106','12'),('23107','12'),('23108','12'),('23109','12'),('23110','12'),('23111','12'),('23112','12'),('23113','12'),('23114','12'),('23125','12'),('23126','12'),('23127','12'),('23128','12'),('23129','12'),('26115','14'),('26116','14'),('26117','14'),('26118','14'),('26119','14'),('26120','14'),('26121','14'),('26122','14'),('26123','14'),('26124','14');
/*!40000 ALTER TABLE `question_course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `request`
--

DROP TABLE IF EXISTS `request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `request` (
  `testCode` int NOT NULL,
  `lecturerId` varchar(128) NOT NULL DEFAULT '',
  `hodId` varchar(128) DEFAULT '',
  `duration` int DEFAULT '0',
  `explanation` varchar(2056) DEFAULT '',
  PRIMARY KEY (`testCode`,`lecturerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `request`
--

LOCK TABLES `request` WRITE;
/*!40000 ALTER TABLE `request` DISABLE KEYS */;
/*!40000 ALTER TABLE `request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `studenttest`
--

DROP TABLE IF EXISTS `studenttest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `studenttest` (
  `studentId` varchar(128) NOT NULL,
  `testCode` int NOT NULL,
  `timePassed` int DEFAULT '0',
  `answers` varchar(128) DEFAULT '',
  `grade` int DEFAULT '0',
  `lecturerNotes` varchar(2056) DEFAULT '',
  `approved` varchar(128) DEFAULT 'false',
  `changeReason` varchar(128) DEFAULT '',
  PRIMARY KEY (`studentId`,`testCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `studenttest`
--

LOCK TABLES `studenttest` WRITE;
/*!40000 ALTER TABLE `studenttest` DISABLE KEYS */;
INSERT INTO `studenttest` VALUES ('21234560',1000,80,'22223',100,'Grate Job!','true',''),('21234560',1001,103,'23233',60,'Pass','true',''),('21234560',1002,20,'32142',80,'Nice!','false',''),('21234560',1003,82,'13113',100,'Grate Job!','false',''),('21234560',1004,82,'13140',60,'Pass','true',''),('21234561',1000,60,'12223',80,'Nice!','false',''),('21234561',1001,62,'13244',80,'Nice!','false',''),('21234561',1002,30,'32124',80,'Nice!','false',''),('21234561',1003,77,'13112',80,'Nice!','false',''),('21234561',1004,77,'24111',60,'Pass','false',''),('21234562',1000,102,'11223',60,'Pass','false',''),('21234562',1001,100,'23244',100,'Grate Job!','false',''),('21234562',1002,40,'12133',40,'Next time will be better','false',''),('21234562',1003,78,'23113',80,'Nice!','false',''),('21234562',1004,78,'32432',40,'Next time will be better','false',''),('21234563',1000,58,'22111',40,'Next time will be better','false',''),('21234563',1001,115,'33444',60,'Pass','false',''),('21234563',1002,20,'44144',60,'Pass','false',''),('21234563',1003,60,'24113',60,'Pass','false',''),('21234563',1004,60,'24131',80,'Nice!','false',''),('21234564',1000,70,'22224',80,'Nice!','false',''),('21234564',1001,30,'23244',100,'Grate Job!','false',''),('21234564',1002,49,'32144',100,'Grate Job!','false',''),('21234564',1003,10,'22213',40,'Next time will be better','false',''),('21234564',1004,10,'24132',100,'Grate Job!','false',''),('21234565',1000,50,'22223',100,'Grate Job!','false',''),('21234565',1001,50,'13242',60,'Pass','false',''),('21234565',1002,88,'22144',80,'Nice!','false',''),('21234565',1003,30,'13113',100,'Grate Job!','false',''),('21234565',1004,30,'24132',100,'Grate Job!','false',''),('21234566',1000,20,'23232',40,'Next time will be better','false',''),('21234566',1001,70,'11334',20,'You must repeat the course','false',''),('21234566',1002,56,'33333',20,'You must repeat the course','false',''),('21234566',1003,35,'13213',80,'Nice!','false',''),('21234566',1004,35,'14132',80,'Nice!','false',''),('21234567',1000,60,'14123',40,'Next time will be better','false',''),('21234567',1001,180,'00000',0,'What happened? :(','false',''),('21234567',1002,72,'23143',40,'Next time will be better','false',''),('21234567',1003,39,'13122',60,'Pass','false',''),('21234567',1004,39,'11132',60,'Pass','false',''),('21234568',1000,102,'11143',20,'You must repeat the course','false',''),('21234568',1001,180,'00000',0,'What happened? :(','false',''),('21234568',1002,56,'32144',100,'Grate Job!','false',''),('21234568',1003,44,'13123',80,'Nice!','false',''),('21234568',1004,44,'24140',80,'Nice!','false','');
/*!40000 ALTER TABLE `studenttest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subject`
--

DROP TABLE IF EXISTS `subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subject` (
  `number` varchar(128) NOT NULL,
  `Name` varchar(128) DEFAULT '',
  PRIMARY KEY (`number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subject`
--

LOCK TABLES `subject` WRITE;
/*!40000 ALTER TABLE `subject` DISABLE KEYS */;
INSERT INTO `subject` VALUES ('22','Math'),('23','Software'),('24','Biotechnology'),('25','Electrical'),('26','Physics');
/*!40000 ALTER TABLE `subject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test`
--

DROP TABLE IF EXISTS `test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `test` (
  `id` varchar(128) NOT NULL,
  `number` varchar(128) DEFAULT '',
  `courseNumber` varchar(128) DEFAULT '',
  `duration` int DEFAULT '0',
  `instructionsForStudent` varchar(2056) DEFAULT '',
  `instructionsForLecturer` varchar(2056) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test`
--

LOCK TABLES `test` WRITE;
/*!40000 ALTER TABLE `test` DISABLE KEYS */;
INSERT INTO `test` VALUES ('221002','02','10',180,'Ensure that you have a reliable internet connection and a suitable environment for taking the test.','Students will have a specific time limit of two hours to complete the test.'),('231201','01','12',120,'Ensure that you have a reliable internet connection and a suitable environment for taking the test.','Intermediate level test, please note that the test is two hours'),('231205','05','12',120,'Ensure that you have a reliable internet connection and a suitable environment for taking the test.','Intermediate level test, please note that the test is two hours'),('261403','03','14',90,'Ensure that you have a reliable internet connection and a suitable environment for taking the test.','Intermediate level test, please note that the test is two hours'),('261404','04','14',120,'Ensure that you have a reliable internet connection and a suitable environment for taking the test.','Intermediate level test, please note that the test is two hours');
/*!40000 ALTER TABLE `test` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test_question`
--

DROP TABLE IF EXISTS `test_question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `test_question` (
  `testId` varchar(128) NOT NULL,
  `questionId` varchar(128) NOT NULL,
  `points` int DEFAULT '0',
  PRIMARY KEY (`testId`,`questionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_question`
--

LOCK TABLES `test_question` WRITE;
/*!40000 ALTER TABLE `test_question` DISABLE KEYS */;
INSERT INTO `test_question` VALUES ('221002','22106',20),('221002','22107',20),('221002','22108',20),('221002','22109',20),('221002','22110',20),('231201','23110',20),('231201','23111',20),('231201','23112',20),('231201','23113',20),('231201','23114',20),('231205','23101',20),('231205','23102',20),('231205','23103',20),('231205','23104',20),('231205','23105',20),('261403','26115',20),('261403','26116',20),('261403','26117',20),('261403','26118',20),('261403','26119',20),('261404','26120',20),('261404','26121',20),('261404','26122',20),('261404','26123',20),('261404','26124',20);
/*!40000 ALTER TABLE `test_question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `testtoexecute`
--

DROP TABLE IF EXISTS `testtoexecute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `testtoexecute` (
  `testCode` int NOT NULL,
  `testId` varchar(128) DEFAULT '',
  `testingType` varchar(128) DEFAULT '',
  `date` varchar(128) DEFAULT '',
  `average` double DEFAULT '0',
  `median` double DEFAULT '0',
  `lock` varchar(128) DEFAULT 'false',
  `timeExtenstion` int DEFAULT '0',
  `lecturerId` varchar(128) DEFAULT '0',
  `numberOfStudentStarted` int DEFAULT '0',
  `numberOfStudentFinished` int DEFAULT '0',
  `numberOfStudent` int DEFAULT '0',
  `distrubition1` int DEFAULT '0',
  `distrubition2` int DEFAULT '0',
  `distrubition3` int DEFAULT '0',
  `distrubition4` int DEFAULT '0',
  `distrubition5` int DEFAULT '0',
  `distrubition6` int DEFAULT '0',
  `distrubition7` int DEFAULT '0',
  `distrubition8` int DEFAULT '0',
  `distrubition9` int DEFAULT '0',
  `distrubition10` int DEFAULT '0',
  PRIMARY KEY (`testCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `testtoexecute`
--

LOCK TABLES `testtoexecute` WRITE;
/*!40000 ALTER TABLE `testtoexecute` DISABLE KEYS */;
INSERT INTO `testtoexecute` VALUES (1000,'231201','online','22/06/2023',62.22222222,60,'true',0,'31234561',9,9,0,0,1,0,3,0,1,0,2,0,2),(1001,'221002','online','22/06/2023',53.33333333,60,'true',0,'31234561',9,7,2,0,1,0,1,0,3,0,1,0,2),(1002,'261403','online','22/06/2023',66.66666667,80,'true',0,'31234561',9,9,0,0,1,0,2,0,1,0,3,0,2),(1003,'261404','online','22/06/2023',75.55555556,80,'true',0,'31234561',9,9,0,0,0,0,1,0,2,0,4,0,2),(1004,'231205','online','22/06/2023',73.33333333,80,'true',0,'31234562',9,9,0,0,0,0,1,0,3,0,3,0,2);
/*!40000 ALTER TABLE `testtoexecute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` varchar(45) NOT NULL,
  `name` varchar(45) DEFAULT '',
  `username` varchar(45) NOT NULL,
  `password` varchar(45) DEFAULT '',
  `premission` varchar(45) DEFAULT '',
  `loggedin` varchar(45) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('21234560','Mor Shmuel','MorShmuel','MorShmuel123','Student','no'),('21234561','Dor Shabat','DorShabat','DorShabat123','Student','no'),('21234562','Lior Zucker','LiorZucker','LiorZucker123','Student','no'),('21234563','Yuval Rozner','YuvalRozner','YuvalRozner123','Student','no'),('21234564','Yoni Azeraf','YoniAzeraf','YoniAzeraf123','Student','no'),('21234565','Itamar Kraus','ItamarKraus','ItamarKraus123','Student','no'),('21234566','Maayan Avittan','MaayanAvittan','MaayanAvittan123','Student','no'),('21234567','Rotem Porat','RotemPorat','RotemPorat123','Student','no'),('21234568','OfekAvraham','OfekAvraham','OfekAvraham123','Student','no'),('31234560','Dan Lemberg','DanLemberg','DanLemberg123','Lecturer','no'),('31234561','Sarai Shinold','SaraiShinold','SaraiShinold123','Lecturer','no'),('31234562','Elena Kramer','ElenaKramer','ElenaKramer123','Lecturer','no'),('41234560','Dvora Toledano','DvoraToledano','DvoraToledano123','Hod','no'),('41234561','Ninet Tayeb','NinetTayeb','NinetTayeb123','Hod','no'),('41234562','Dana Frider','DanaFrider','DanaFrider123','Hod','no'),('41234563','Yuda Levi','YudaLevi','YudaLevi123','Hod','no'),('41234564','Ravid Plotnik','RavidPlotnik','RavidPlotnik123','Hod','no');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_subject`
--

DROP TABLE IF EXISTS `user_subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_subject` (
  `userId` varchar(128) NOT NULL,
  `subjectNum` varchar(128) NOT NULL DEFAULT '',
  PRIMARY KEY (`userId`,`subjectNum`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_subject`
--

LOCK TABLES `user_subject` WRITE;
/*!40000 ALTER TABLE `user_subject` DISABLE KEYS */;
INSERT INTO `user_subject` VALUES ('31234560','22'),('31234560','23'),('31234561','22'),('31234561','23'),('31234561','26'),('31234562','23');
/*!40000 ALTER TABLE `user_subject` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-19 13:34:23
