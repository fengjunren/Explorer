/*Table structure for table `deng_mi` */

DROP TABLE IF EXISTS `deng_mi`;

CREATE TABLE `deng_mi` (
  `id` mediumint(9) NOT NULL AUTO_INCREMENT,
  `type` varchar(10) DEFAULT NULL,
  `riddle` varchar(100) DEFAULT NULL,
  `answer` varchar(20) DEFAULT NULL,
  `hint` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=513 DEFAULT CHARSET=utf8mb4;

/*Data for the table `deng_mi` */

insert  into `deng_mi`(`id`,`type`,`riddle`,`answer`,`hint`) values (1,'成语谜','龙','充耳不闻','打一成语'),(2,'成语谜','一','接二连三','打一成语'),(3,'成语谜','乖','乘人不备','打一成语'),(4,'成语谜','亚','有口难言','打一成语'),(5,'成语谜','主','一往无前','打一成语'),(6,'成语谜','呀','唇齿相依','打一成语'),(7,'成语谜','判','一刀两断','打一成语'),(8,'成语谜','者','有目共睹','打一成语'),(9,'成语谜','泵','水落石出','打一成语'),(10,'成语谜','扰','半推半就','打一成语'),(11,'成语谜','黯','有声有色','打一成语'),(12,'成语谜','田','挖空心思','打一成语'),(13,'成语谜','十','纵横交错','打一成语'),(14,'成语谜','板','残茶剩饭','打一成语'),(15,'成语谜','咄','脱口而出','打一成语'),(16,'成语谜','票','闻风而起','打一成语'),(17,'成语谜','骡','非驴非马','打一成语'),(18,'成语谜','桁','行将就木','打一成语'),(19,'成语谜','皇','白玉无瑕','打一成语'),(20,'成语谜','忘','死心塌地','打一成语'),(21,'成语谜','中的','矢无虚发','打一成语');

/*Table structure for table `gu_shi_ci` */

DROP TABLE IF EXISTS `gu_shi_ci`;

CREATE TABLE `gu_shi_ci` (
  `content` varchar(100) DEFAULT NULL,
  `origin` varchar(50) DEFAULT NULL,
  `author` varchar(10) DEFAULT NULL,
  `category` varchar(20) DEFAULT NULL,
  `c1` varchar(10) DEFAULT NULL,
  `c2` varchar(10) DEFAULT NULL,
  `c3` varchar(10) DEFAULT NULL,
  `id` mediumint(9) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=522 DEFAULT CHARSET=utf8mb4;

 
/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `userId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `openId` varchar(100) NOT NULL,
  `nickName` varchar(100) DEFAULT NULL,
  `headImgUrl` varchar(200) DEFAULT NULL,
  `isSubscribe` int(2) DEFAULT '1',
  PRIMARY KEY (`userId`,`openId`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;

 

/*Table structure for table `wx_config` */

DROP TABLE IF EXISTS `wx_config`;

CREATE TABLE `wx_config` (
  `wxId` varchar(100) NOT NULL,
  `appId` varchar(100) NOT NULL,
  `appSecret` varchar(100) NOT NULL,
  `accessToken` varchar(200) DEFAULT NULL,
  `updateTime` varchar(20) DEFAULT NULL,
  `jsApiTicket` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`wxId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

 
/*Table structure for table `wx_msg` */

DROP TABLE IF EXISTS `wx_msg`;

CREATE TABLE `wx_msg` (
  `ToUserName` varchar(100) DEFAULT NULL,
  `FromUserName` varchar(100) DEFAULT NULL,
  `CreateTime` bigint(15) DEFAULT NULL,
  `MsgType` varchar(20) DEFAULT NULL,
  `Content` varchar(500) DEFAULT NULL,
  `MsgId` bigint(30) DEFAULT NULL,
  `Event` varchar(50) DEFAULT NULL,
  `EventKey` varchar(50) DEFAULT NULL,
  `Status` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
 
/*Table structure for table `xie_hou_yu` */

DROP TABLE IF EXISTS `xie_hou_yu`;

CREATE TABLE `xie_hou_yu` (
  `id` mediumint(9) NOT NULL AUTO_INCREMENT,
  `riddle` varchar(50) DEFAULT NULL,
  `answer` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14033 DEFAULT CHARSET=utf8mb4;


/*Data for the table `xie_hou_yu` */

insert  into `xie_hou_yu`(`id`,`riddle`,`answer`) values (1,'做砖的坯子、插刀的鞘子','框框套套'),(2,'做贼碰上劫路人','坏到一块了；冤家路窄'),(3,'做贼的说梦话','想偷'),(4,'做贼的不用化装','贼眉贼眼'),(5,'做贼盗黄连','自讨苦吃'),(6,'做小本生意','斤斤计较'),(7,'做媳妇的没婆婆','好当家'),(8,'做生意讲信誉','理所当然'),(9,'做烧饼卖汤圆','多面手'),(10,'做烧饼的卖汤圆','多面手'),(11,'做旗袍用土布','不是那块料'),(12,'做年（旧指干以年计酬的长工活）遇见闰月','倒霉透了；背时'),(13,'做梦坐飞机','想人非非（飞飞）'),(14,'做梦抓大印','官迷心窍'),(15,'做梦游西湖','好景不长'),(16,'做梦游华山','好景不长'),(17,'做梦学吹打','快活一时'),(18,'做梦推磨子','想转了'),(19,'做梦拾元宝','空欢喜；欢喜不尽；一场空欢喜；想得美'),(20,'做梦娶媳妇','尽想好事；想得倒美；想偏了心；光想美事');
