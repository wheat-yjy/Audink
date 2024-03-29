-- MySQL dump 10.13  Distrib 5.7.18, for Linux (x86_64)
--
-- Host: localhost    Database: audink
-- ------------------------------------------------------
-- Server version	5.7.18-0ubuntu0.16.04.1

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
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `book` (
  `bookId` int(11) NOT NULL AUTO_INCREMENT,
  `uploader` varchar(255) DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  `imageUrl` varchar(255) DEFAULT NULL,
  `bookname` varchar(255) DEFAULT NULL,
  `classify` varchar(10) DEFAULT NULL,
  `summary` text,
  PRIMARY KEY (`bookId`),
  UNIQUE KEY `book_bookId_uindex` (`bookId`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES (6,'M_jsk','顾漫','http://120.24.70.191:8080/audinkapp/upload/img/1526538755993sPNFvBx1uIbEVSIr4wbYoGa7lTtGvNnQhtoWKXjf66CmsiRWtX.jpg','微微一笑很倾城','言情','作品讲述A大计算机系花贝微微与同校师哥肖奈在游戏《梦游江湖》中结伴成亲，并在现实中相恋相守的故事。'),(7,'匿名用户','桐华','http://120.24.70.191:8080/audinkapp/upload/img/15265422917181tEu3ZhPpFMs1basbDHTjc6HpHgIpJKHP3DOlOJwBFdnUilx0F.jpg','步步惊心','言情','讲述现代白领张晓因车祸穿越到清朝康熙年间，成为满族少女马尔泰·若曦，身不由己地卷入“九子夺嫡”的纷争。她看透所有人的命运，却无法掌握自己的结局，个人情感夹杂在争斗的惨烈中备受煎熬。经历几番爱恨嗔痴，身心俱疲的故事。'),(8,'NAFinal','顾漫','http://120.24.70.191:8080/audinkapp/upload/img/152654272537780BGEgko1ZwhB65sekejwKsjTsSYTzDZ0h2N49Uq1VzIife6UH.jpg','何以笙箫默','言情','讲述了何以琛和赵默笙因一段年少时的爱恋牵出一生的情缘，并执着于等待和相爱的故事。'),(9,'NAFinal','鱼人二代','http://120.24.70.191:8080/audinkapp/upload/img/1526543539545xMrw14RTEulHyqgp65VkrP6maiYqSOr0Kdo0glvz7wtET6Aqfn.jpg','校花的贴身高手','都市','故事讲述了从大山里走出来的绝世高手，一块能预知未来的神秘玉佩…身负重任，追校花！还是奉校花老爸之命！虽然林逸很不想跟这位难伺候的大小姐打交道，但是长辈之命难违抗，他不得不千里迢迢的转学到松山市，给大小姐鞍前马后的当跟班..于是史上最牛跟班出现了。'),(10,'NAFinal','黑暗崛起','http://120.24.70.191:8080/audinkapp/upload/img/1526543655414II3VKUiMeXXkFYtr4FvX9TGQ80njsYUlNZwBItsvPboF7fTdEY.jpg','特种教师','都市','主要讲述杀手之王叶皇回渝城成为一名教师后发生的一切。'),(11,'江枫眠','打眼','http://120.24.70.191:8080/audinkapp/upload/img/1526544401093XjTEcDElz0uoEKuW5QQPGLBiOPwtDW4idEoXA11BVwdPY20mnW.jpg','宝鉴','都市','一局安百变，叵测是人心！\n三教九流，五行三家，尽在宝鉴之中！\n前期都市为主。三界山后下副本转修仙。'),(12,'江枫眠','辰东','http://120.24.70.191:8080/audinkapp/upload/img/1526544549634bptfsVDOkvuA4WWJhwA4b0S1QNBvnGSL6I8GXg2Csy2J9avFSP.jpg','完美世界','玄幻','粒尘可填海，一根草斩尽日月星辰，弹指间天翻地覆。群雄并起，万族林立，诸圣争霸，乱天动地；问苍茫大地，谁主沉浮？一个少年从大荒中走出，一切从这里开始。'),(13,'M_jsk','天蚕土豆','http://120.24.70.191:8080/audinkapp/upload/img/1526544674251UAcVr28GxJlVq1JZeIKhjgFq0iJlrPdoTDObrC0BEph1esiEhZ.jpg','斗破苍穹','玄幻','这里是属于斗气的世界，没有花俏艳丽的魔法，有的，仅仅是繁衍到巅峰的斗气！\n心潮澎湃，无限幻想，迎风挥击千层浪，少年不败热血！'),(14,'NAFinal','唐家三少','http://120.24.70.191:8080/audinkapp/upload/img/1526544794326YMpT7AAo7S9hg2AVgOdFsgMgkmMCTXJDDZ5UEuTwRaRxUtWwUn.jpg','绝世唐门','玄幻','《绝世唐门》是湖南少年儿童出版社出版于2012年12月的一本玄幻异界网络小说，作者是唐家三少。是《斗罗大陆》的第二部，连载于起点中文网。'),(15,'M_jsk','海晏','http://120.24.70.191:8080/audinkapp/upload/img/7a899e510fb30f24ca6c8c4dc295d143ac4b038d.jpg','琅琊榜','言情','小说营造了一个尔虞我诈的世界，通过主人公梅长苏的视角书写了一幕幕惊心动魄的权谋之斗。宫廷内外，无数的谜团交织在刀光血影中，明枪暗箭中带出一段段离奇的故事，而“麒麟才子”梅长苏肩负着无数的冤魂和血泪，毅然行走于雪冤之路，巧妙掀起了一场又一场步步为营的精彩暗战。'),(16,'M_jsk','匪我思存','http://120.24.70.191:8080/audinkapp/upload/img/7a899e510fb30f24ca6c8c4dc295d143ac4b038d.jpghttp://120.24.70.191:8080/audinkapp/upload/img/f703738da97739121f98e2dcf2198618377ae2d5.jpg','寂寞空庭春欲晚','言情','讲述了清圣祖仁皇帝康熙与浣衣女卫琳琅之间的剜心虐恋。'),(17,'匿名用户','唐七公子','http://120.24.70.191:8080/audinkapp/upload/img/810a19d8bc3eb135c95231aba71ea8d3fd1f440d.jpg','华胥引','言情','讲述了在乱世里，发生在依靠鲛珠死而复生的卫国公主叶蓁和陈国公子苏誉的身上的故事。'),(18,'匿名用户','余华','http://120.24.70.191:8080/audinkapp/upload/img/huozhe.jpeg','活着','经典','讲述一个人一生的故事，这是一个历尽世间沧桑和磨难老人的人生感言，是一幕演绎人生苦难经历的戏剧，并以一种渗透的表现手法完成了一次对生命意义的哲学追问。余华因这部小说于2004年荣获法兰西文学和艺术骑士勋章。'),(19,'匿名用户','钱钟书','http://120.24.70.191:8080/audinkapp/upload/img/weicheng.jpeg','围城','经典','《围城》是一幅栩栩如生的世井百态图，是中国现代文学史上一部风格独特的讽刺小说，被誉为“新儒林外史”'),(20,'匿名用户','余华','http://120.24.70.191:8080/audinkapp/upload/img/xusanguan.jpeg','许三观卖血记','经典',' 本书以博大的温情描绘了磨难中的人生，以激烈的故事形式表达了人在面对厄运时求生的欲望。本书入选韩国《中央日报》评选的“100部必读书”、中国百位批评家和文学编辑评选的“20世纪90年代最有影响的10部作品”。');
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chapter`
--

DROP TABLE IF EXISTS `chapter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chapter` (
  `chapterId` int(11) NOT NULL AUTO_INCREMENT,
  `bookId` int(11) DEFAULT NULL,
  `content` text,
  `chaptername` varchar(255) DEFAULT NULL,
  `audioUrl` varchar(255) DEFAULT NULL,
  `lrc` text,
  `abst` text,
  PRIMARY KEY (`chapterId`),
  UNIQUE KEY `chapter_chapterId_uindex` (`chapterId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chapter`
--

LOCK TABLES `chapter` WRITE;
/*!40000 ALTER TABLE `chapter` DISABLE KEYS */;
INSERT INTO `chapter` VALUES (2,6,'“微微，来忘情岛，我们把婚离了。”\n\n\n    贝微微一上游戏，就看见游戏里的“老公”真水无香发过来这样一条消息。微微不由有点傻眼。不是吧，不过是宿舍宽带坏了修了半个月，才这十几天的功夫，就“情变”了？\n\n\n    微微老半天才回过去：“为什么呀？”\n\n\n    真水无香：“微微，抱歉了，原因你别问了，我送你一套仙器装备做为补偿。”\n\n\n    还有赡养费？微微有点发囧。“不用了啦。”\n\n\n    游戏里结婚本来就当不得真的，当初会和真水无香结婚，也是为了做任务，有个任务只能夫妻去做，于是帮派里的单身男女们纷纷结婚，真水无香发了条消息问微微能不能和他结婚，微微想了想就同意了。\n\n\n    到现在结婚也有半年了，虽然微微从不肉麻兮兮的老公来老公去，一直直呼真水的名字，但是合作默契，并肩做战多了，似乎也有点革命感情了。\n\n\n    然而游戏嘛……\n\n\n    微微回消息过去：“我马上就过去。”\n\n\n    微微游戏里的人物“芦苇微微”骑上马，开始向忘情岛奔去。\n\n\n    “芦苇微微”是一个一身劲装的红衣女侠。\n\n\n    微微玩的这款《梦游江湖》游戏是目前市场上最热的武侠网游之一，其实这款游戏其他方面并没有什么突出之处，唯独美工非常强大，角色也特别多，男女角色各有18个可供选择。微微选择的红衣女侠是比较少有人选的，倒不是说女侠外表不漂亮，而是因为她的武器是一把巨大的刀。\n\n\n    巨大的刀，比起优雅的翠玉笛子，比起舞动的雪白丝带，比起秋水如泓的软剑，比起峨嵋刺，实在很没美感，很没女人味，所以选择的女孩子很少，但是微微喜欢，微微觉得很彪悍，很符合她的形象。\n\n\n    跑到忘情岛，两人一起喝下忘情水，系统宣布：“芦苇微微”与“真水无香”感情破裂，宣布离婚，从此男婚女嫁各不相干。\n\n\n    真水无香要给微微一套仙器装备，微微点了取消，没有接受，发了个笑脸，红衣女侠很豪迈的走掉了。\n\n\n    结果第二天中午，微微吃完午饭上线，帮派里交情比较好的一个女孩子雷神妮妮就发消息过来：“微微，怎么回事？你和真水离婚了？听说他晚上八点要和小雨妖妖结婚哎！”\n\n\n    微微：“……”\n\n\n    雷神妮妮：“真的离了啊？”\n\n\n    微微：“是啊。”\n\n\n    雷神妮妮：“好可惜哦，真水人满不错的，没想到也会为色所迷啊，不过那个小雨妖妖的确满漂亮的哦。”\n\n\n    妮妮所说的漂亮当然不是指游戏人物，而是指现实中的。三个月前，游戏公司举办了一次玩家真人秀评选活动，得票前三的玩家会得到高级套装，小雨妖妖凭着几张照片，一段视频，以超高的人气夺冠，这事立刻就轰动了微微所在的服务器，小雨妖妖也成为本服众色狼垂涎的目标。\n\n\n    “前夫”转眼就娶了别的女人，虽然和真水只有一些革命感情，微微还是忍不住郁闷了，用头磕桌子（这孩子郁闷就这样……），大喊：“不带这样的，以貌取人啊！！！！！”\n\n\n    这句话微微不是在游戏里喊的，而是在宿舍里，于是微微立刻被上铺用枕头砸了。\n\n\n    “贝微微！你这个名副其实的大美女还这么喊，我们还要不要活了。”\n\n\n    的确，贝微微是美女，而且是超级大美女。可是美女也分好多种的，有优雅型的，有知性型的，有甜美型的，有温柔型的，有贤淑型的……\n\n\n    还有微微这种——花瓶型的……\n\n\n    虽然微微一直好好学习天天向上努力向学识型靠了，然而……\n\n\n    美艳的眉眼，勾人的眼波，永远嫣红的唇色，火爆的身材，贝微微就算穿着理工大学那套很挫的校服出去，也不会有人觉得她真是名牌大学的大学生。\n\n\n    微微想起了生平恨事，继续用头磕桌子。\n\n\n    电脑里雷神妮妮继续八卦：“以前听说小雨妖妖要嫁给等级榜上的那谁，没想到会跟真水一起哎，不过前阵子常常看见真水和她一起练级。”\n\n\n    果然是在她不在的时候发展了“奸情”，微微发了个黑乎乎烧焦的表情过去。\n\n\n    “其实也不怪真水拉，微微啊，其实……”\n\n\n    “其实什么？”微微磕完桌子了，拿起茶杯喝水，单手打字。\n\n\n    “其实……你操作这么强大，PK榜上排第六，而且从来不问男的要装备，其实大家都怀疑你是人妖哎！”\n\n\n    “噗！”微微把水喷显示屏上了。','Part 1 被抛弃了','http://120.24.70.191:8080/audinkapp/upload/mp3.mp3','NULL','NULL'),(3,6,'晚上八点整。\n\n\n    红衣女侠背着大刀威风凛凛的站在长安城朱雀桥上。\n\n\n    这是结婚时花轿必经之处。\n\n\n    《梦游江湖》里结婚可以很豪华可以很朴素，当初微微和真水结婚就很朴素，就去月老那公证了一下，那时候大家都穷嘛，而且是为了任务而结婚的，不必太在意形式。\n\n\n    而今天真水和小雨妖妖结婚的场面无疑是超豪华的。\n\n\n    上百发的礼花，锣鼓开道，八人抬花轿游街，高级酒楼包场婚宴，据说婚宴现场每人还会派发888金的红包。\n\n\n    游戏里早就为了这场婚礼议论纷纷了，毕竟小雨妖妖名气在那，而真水无香也不是无名之辈，据说这事连别的服的玩家都惊动了，还有人注册了小号来看婚礼的。\n\n\n    朱雀桥上，红衣女侠继续一动不动的站着，身影单薄，表情肃杀。\n\n\n    此时此刻，世界频道已经炸了。\n\n\n    『世界』[花吹花雨]：大家看到没有，我看见真水无香的前妻站在朱雀桥上！\n\n\n    『世界』[莪愛伱]：看見叻，莪看見叻。\n\n\n    『世界』[伤心桥下春波绿]：看见了，叹息，我觉得她的背影好悲伤。叹世间，从来只见新人笑，哪曾见得旧人哭。\n\n\n    『世界』[哦呵呵]：我靠，又见火星文，老子最讨厌火星文\n\n\n    『世界』[◎olo◎]：早就看到了，我就站在她边上，哇塞，今天有热闹看了！我觉得她要抢亲。\n\n\n    『世界』[一贱九州寒]：支持芦苇微微抢新郎！！！小雨妖妖归我！！！\n\n\n    『世界』[莪愛伱]：吙煋攵怎仫啦，地浗攵很叻鈈起仫，伱煋浗歧視\n\n\n    『世界』[巧克力奶茶]：支持芦苇微微抢新郎！一贱滚一边去，小雨妖妖是我的！\n\n\n    『世界』[哦呵呵]：……\n\n\n    接着打抱不平的出来了。\n\n\n    『世界』[anny]：虽然小雨妖妖是美女，可是我也要说，这事她做得不地道，抢人家老公还这么招摇，不怕天谴哦。\n\n\n    『世界』[霹雳叫哇]：是啊，芦苇微微人挺好的，满仗义的。\n\n\n    『世界』[伤心桥下春波绿]：你看芦苇，伤心得一句话都不说。\n\n\n    『世界』[稻草520]：第三者。。。卿本佳人。。。。。。何必呢\n\n\n    『世界』[小雨妖妖不要脸108]：嘿嘿，为什么这么招摇，抢了人家的老公当然要出来现现啊，小雨妖妖不要脸不要脸不要脸不要脸不要脸不要脸不要脸不要脸，￥％％￥※×……（脏话被系统屏蔽）\n\n\n    这个“小雨妖妖不要脸108”是小雨妖妖的死对头，不知道有什么深仇大恨，平时没事都会出来骂几句，据说也被杀过很多次，但是越杀越勇，被禁言了就重新注册，据说他ID后面的数字就代表被禁次数。\n\n\n    此人一出，小雨妖妖的亲友团也忍不住了。\n\n\n    『世界』[小雨霏霏]：那个不要脸闭嘴！还有，谁说妖妖是第三者，不要太看得起自己了，够格让我们妖妖做第三者吗！！！\n\n\n    『世界』[小雨青青]：谁要来抢亲试试啊，不要以为上了PK榜就了不起，我们小雨家族都在这里，就怕人不敢来呢！\n\n\n    『世界』[wsn]：上PK榜的女人肯定是巨型恐龙，真水的选择没错。\n\n\n    随着世界频道炸频，人民群众纷纷跑往朱雀桥。\n\n\n    贝微微从洗手间里出来，走到自己的电脑前，就看到这么一副景象，红衣女侠的周围重重叠叠挤满了人，但是很有默契的在女侠周围空出一个圈来，让女侠孤独的，背影凄凉的站在中间。\n\n\n    然后周围那些人头顶不停的冒出字来，频率最多的是——“支持芦苇微微抢亲！支持芦苇微微抢亲！支持芦苇微微抢亲！支持芦苇微微抢亲！支持芦苇微微抢亲！支持芦苇微微抢亲！支持芦苇微微抢亲！支持芦苇微微抢亲！”\n\n\n    微微目瞪口呆。\n\n\n    她不过是上了游戏，然后站在朱雀桥上，然后去上了个厕所，最多不过五分钟，发生了什么事了？\n\n\n    对，游戏中的确是可以抢亲的，不过一来要交纳系统巨额的费用，二来要连续PK三场全赢，最后还要得到被抢对象的同意……要是对方不同意，还是判定抢亲失败，所以除了被爱冲昏头垂死挣扎的，脑残才去抢亲。\n\n\n    她到底干了什么让大家误会她要抢亲啊？\n\n\n    微微满脸黑线的看来看去，心里琢磨着如果她现在狂奔而去的话，会不会被这群八卦的群众判定成伤心泪奔啊？\n\n\n    微微一边在奔与不奔之间挣扎，一边点着跳个不停的好友栏，都是朋友们来询问怎么回事的，微微一个都没回复，统统关闭。帮派频道里也是议论纷纷，男的多数支持真水的，女的多数支持微微，还有一些人在劝微微不要想不开，都是一个帮的，别做得太绝。\n\n\n    这时好友栏又跳起来，微微点开，来信人是真水无香。他大概也有点火了，语气并不客气。“怎么回事，你昨天不是很爽快吗？而且我也给你补偿了，你自己不要的。”\n\n\n    微微欲哭无泪，无力的敲字回过去：“同学，我只是来……看……热……闹……而……已……”\n\n\n微微一笑很倾城  正文 第3章 又结婚了\n\n\n    微微进退两难，落荒而逃很不女侠，站在这里很像劫匪，于是微微急中生智，一撩衣服，坐下了。\n\n\n    梦游江湖里人物坐下，可以是休息，可以是打坐修内功，还有一种是就是微微现在这样——坐下摆摊卖东西。\n\n\n    当微微打出摊名——“药品便宜卖”的时候，周围玩家的头上不由纷纷冒出黑线。微微飞快的打字吆喝：“瞧一瞧，看一看啊，满级药师新出炉药品，八折便宜卖了！”\n\n\n    此句一出，本来等着看热闹的玩家们顿时沸腾了。\n\n\n    开玩笑，满级药师做的药品系统商店可是没得卖的，只有打怪偶尔能爆几个，属于有价无市的东西，虽然本服满级药师有好几个，但是他们都是为自己的帮派服务的，很少拿自己做的药品出来卖。现在居然有便宜的满级药！朱雀桥的玩家们激动了，顿时一涌而上，眨眼就把红衣女侠小小的身影淹没了。\n\n\n    花轿来了，花轿飞快的去了。一直慢悠悠的花轿不知怎么的，路过朱雀桥的时候忽然加快了速度，瞬间就消失在桥头。\n\n\n    微微的药也在很短的时间内抢购完毕，玩家们看没什么好戏可看，纷纷散了，平时就很少人来，只有玩家结婚时才会热闹一把的朱雀桥上一时又恢复了冷清。\n\n\n    微微收起摊子站起来，正准备离开，忽然看见桥下有人喊她。\n\n\n    “芦苇微微。”\n\n\n    微微朝那个玩家看去。\n\n\n    桥边斜栽着一棵杨柳，那人就站在杨柳树下，有风轻送，柳枝微拂，树下的男子一身白衣纤尘不染，携着一把古琴，衣袂飘飘，很有几分潇洒出尘的味道。\n\n\n    微微眼睛都看直了\n\n\n    看直了当然不是因为白衣男子很帅，虽然的确很帅，但是这个白衣琴师的形象游戏里并不少见，看多了也就麻木了。\n\n\n    而是因为男子的名字。\n\n\n    一笑奈何。\n\n\n    本服第一高手一笑奈何。\n\n\n    在这个游戏里，一个人被称为第一高手，不仅仅是等级高而已，他必须装备强，必须PK猛，必须操作准，最关键的一点是，必须人民币多。\n\n\n    所以芦苇微微第一次见到一笑奈何，忍不住一眼冒红心，一直冒金币。\n\n\n    大神啊！\n\n\n    偶像啊！\n\n\n    有钱人啊！\n\n\n    微微跑下桥去，打了个笑脸：“奈何兄久仰了，你叫我啊？”\n\n\n    一笑奈何“嗯”了一声，然后系统提示微微：一笑奈何加你为好友。\n\n\n    微微连忙点了同意，顺便加上了一笑奈何。一笑奈何可不是这么容易加的，顶级高手们的好友开关都是常年关闭的，微微有时候加的人多了，也会关闭一下。\n\n\n    一笑奈何片刻后发消息来：“你觉得这场婚礼怎么样？”\n\n\n    微微囧，难道大神也是来八卦的？微微很外交地回复：“很盛大啊。”\n\n\n    一笑奈何：“那你想不想要一个更盛大的婚礼？”\n\n\n    微微呆。\n\n\n    一笑奈何：“跟我结婚吧。”\n\n\n    微微觉得，幸好自己今天没有在喝水，不然显示器估计又要被喷了。微微心有余悸的把手边的茶杯放远点，看向屏幕。\n\n\n    屏幕上，一笑奈何的人物还是安静的站在柳树下，几分飘逸，几分洒脱。微微看了半晌，发过去：“大神……你被盗号了么＝＝”\n\n\n    一笑奈何显然不是很欣赏微微的幽默，难得的发了个“默”的表情：“你有没有看官网的最新消息，关于夫妻PK大赛的。”\n\n\n    微微：“等等，我去看下。”\n\n\n    微微打开官网，一眼就看到页首“夫妻PK大赛”几个大字。快速的浏览了一下，大致有了了解。夫妻PK大赛，顾名思义，就是以夫妻为单位进行的PK大赛，报名的玩家夫妻先在本服PK，每个服务器的前三名可以得到一定的奖励，然后每个服务器的第一名在全服PK，前三名可以得到超级奖励，第一名的奖励更是丰富得让微微垂涎三尺。\n\n\n    微微：“你找我结婚就是为了这个PK大赛？”\n\n\n    一笑奈何：“嗯。”\n\n\n    微微：“汗，为什么找我啊？”\n\n\n    一笑奈何也不废话：“你是唯一上PK榜前十的女玩家。”\n\n\n    微微有点心动了，这位奈何先生可是很强的，PK榜上第一，财富榜上第一，武器和宠物都是神级的，如果和他联手，本服的第一起码有一半的把握。但是她第一次结婚是为了任务，难道第二次结婚是因为PK？\n\n\n    微微忍不住黑线。\n\n\n    不过话说回来，如果不是为了任务，不是为了PK，她又干吗去跟个陌生人结婚呢？而且奖励那么丰厚……\n\n\n    微微一咬牙：“结吧！”\n\n\n    既然下了决定，微微就很爽快，“走，我们组队去月老那。”\n\n\n    “等等。”\n\n\n    “怎么？”难道她答应太爽快，奈何先生反而被吓跑了－－\n\n\n    不过显然不是像微微想的那样，一笑奈何说：“我需要准备一下，三天后，八点。”\n\n\n    微微一愣，打字飞快：“准备什么？”\n\n\n    “婚礼。”\n\n\n    微微：“公证一下就好啦，只是为了比赛结婚，不用太隆重吧？”\n\n\n    一笑奈何：“不行，我一笑奈何的婚礼怎么可以随便。”\n\n\n    微微：“……”','Part 2 微微抢亲','http://120.24.70.191:8080/audinkapp/upload/mp3.mp3','NULL','NULL'),(4,6,'既然决定要结婚，那大家就算认识了。\n\n\n    咳，这句话的逻辑似乎有点怪……\n\n\n    于是微微接下来两天一直跟着一笑奈何混，还加入了一笑奈何的固定队伍，奈何的队伍里也都是高手，其中有几个微微还和他们PK过，不过对他们的认识也仅限于ID而已。\n\n\n    微微第一次和他们见面是这样的。\n\n\n    猴子酒：“hoho，新人。”\n\n\n    愚公爬山：“奈何，这个mm是谁？”\n\n\n    一笑奈何：“我未婚妻。”\n\n\n    他说得还真自然，微微黑线了一下，打出笑脸：“大家好^_^”\n\n\n    莫扎他：“哇，三嫂。”\n\n\n    愚公爬山：“我靠，万年光棍也要结婚了。”\n\n\n    众人七嘴八舌的调侃了一番，恭喜了一番，忽然有人说：“三嫂的ID有点熟悉。”\n\n\n    “你这么一说我也觉得了，三嫂似乎上过榜？”\n\n\n    心直口快的猴子酒说：“我想起来了，芦苇微微，不是真水无香的前妻吗？”\n\n\n    队伍里顿时安静了，气氛有点古怪。微微正要说他们是为了PK大赛结婚的，就见奈何很淡定的打出一行字：“嗯，你们三嫂她以前所嫁非人，大家不要歧视她。”\n\n\n    微微被薯片呛到了。\n\n\n    从此微微就有了一个觉悟，和奈何兄一起打怪做任务的时候，千万不要吃零食不要喝水，否则一旦他语出惊人，不是她被呛，就是显示器被喷。\n\n\n    和这群人刷boss做任务无疑是巨爽的，首先大家相处融洽，其次效率实在是高。以前微微和帮派里的人组队要打老久的boss，这里居然几下就搞定了。说到这里不得不提一下奈何大神，他的职业是琴师。琴师这个职业，在梦游江湖里有点尴尬，属于什么都会一点，但是又什么都不顶级的那种。简单的说，就是他会单砍，会群攻，会助攻，会疗伤，但是攻击不如刀客剑客，助攻疗伤不如专门的辅助职业，实在是有点鸡肋。\n\n\n    但是在奈何大神身上就不一样了。\n\n\n    奈何大神的疗伤技能跟职业医生比居然毫不逊色，然后操作强大，跑位精确，队友们全无后顾之忧，也不必分心照顾他。有一次，在boss打得差不多的时候，一直充当医生角色的一笑奈何忽然从古琴中抽出剑，一招琴中剑刺中boss的致命部位，boss头顶冒出巨大的红字后轰然倒地。\n\n\n    琴中剑是琴师唯一的攻击技能，微微见过许多次了，但是从来没见过这么大的伤害值，都快赶上她了，微微嫉妒得想流泪，一定是他的装备太强了啊啊啊！\n\n\n    有这个事例在前，等微微见识到一笑奈何的宝宝的时候，已经没什么想法了。\n\n\n    奈何的宝宝是一只白色的小猫。\n\n\n    微微的宝宝是一只威风的老虎。\n\n\n    当老虎都打不过小猫的时候，微微还能有什么想法呢。\n\n\n    倒是奈何，看见微微的宝宝后端详了很久，然后说：“等我们结婚后，宝宝们也结婚一下吧。”\n\n\n    宝宝能结婚是梦游江湖不同于其他网游的另一项功能，男女玩家如果结婚，然后宝宝种族又相同的话，宝宝也可以结婚，然后会随机产生一个小技能，据说触发一定的条件后，还有可能生下后代。\n\n\n    微微说：“可是他们种族不同啊。”\n\n\n    一笑奈何说：“我这只是变异幼虎。”\n\n\n    原来这只小猫也是老虎啊，怪不得额头有“王”字花纹，微微总算找回了一点自尊心，老虎输给老虎，也不算太丢脸了。\n\n\n    “好啊好啊，让他们结婚。”微微喜滋滋的，人家小猫可是神兽级，她家老虎不过是自己捉的普通老虎，怎么想都是高攀了，微微随口问：“你这只小猫是母的？”\n\n\n    “不是，公的。”\n\n\n    “……我家老虎也是公的。”\n\n\n    结婚那天是周六，一大早微微照常跑去图书馆上自习。说起来，微微同学还是很刻苦的，不刻苦不行啊，在这样的学校，在计算机这样的系，哪个学生不是脑子一流，稍稍落后就要挨打的。\n\n\n    自习到下午4点多，微微有点坐不住了，不停的看表，5点一到迅速的收拾好书本奔向食堂。吃完饭回宿舍上游戏，一笑奈何不在，队伍里其他人的名字也暗着，升级狂人贝微微独自背着大刀去砍怪，正砍得happy，舍友回来了。\n\n\n    微微的宿舍是四人间，除了微微外，还住了晓铃，丝丝和二喜，都是计算机系的，而她们四个也是计算机系大二仅有的四个女生。的\n\n\n    “微微，别玩了，马上六点钟我们系和生化系篮球赛，一起去看啦。”晓玲边换衣服边说。\n\n\n    微微砍着怪摇头：“你们去吧，今天我有事。”\n\n\n    “少来了，你能有什么事，不就是玩游戏嘛，走啦，大钟说今天肖奈会来哦。”\n\n\n    “啊！真的吗？肖奈！！”丝丝和二喜一起喊起来。\n\n\n    微微也星星眼的转头。\n\n\n    晓玲虽然要的就是这种效果，可是看到舍友这样，“受不了你们了，花痴不花痴啊，你们看微微，多镇定。”\n\n\n    微微连忙举手：“别冤枉我，我也花。”\n\n\n    说实在的，微微宿舍的四个女生已经算很不花痴很不花痴的那种了，但是这世界上总有这样的人物，叫人觉得不花痴一下都不正常。\n\n\n    比如说肖奈。\n\n\n    计算机系的肖奈，A大顶尖风云人物，如果A大也像游戏那样弄个等级榜的话，那么肖奈排NO。1绝对是众望所归。先不说他在计算机软件方面那令人惊异的天才，以及入校三年多领队在ACM等国际编程大赛中为学校获得的荣誉，只他居然能者无所不能般地擅古筝围棋，还曾作为游泳选手代表学校参赛夺金等等，就令一众学子望尘莫及了，兼之其人外表清俊雅致、风采佳绝，想不令人倾倒都很难。\n\n\n    不过花痴归花痴，本系的女生们是没人敢上前跟他搭讪的，一方面是他很少来学校，虽然一个系却不熟悉，另一方面是，他实在站在太高处，也实在太傲慢，神情中仿佛总带着几分旁若无人的味道，让人不敢接近。\n\n\n    微微只少少地远远地见过他几次，其中一次就是见他拒绝一个别系的女生，那个女生拦住他递给他信件一类的东西，大概是情书吧，结果人家别说接信，连眼睛都没瞄一眼，脚步都没慢一下，就走过去了。\n\n\n    奇怪的是，他这么傲慢，在男生那边人缘却不错，本系的男生都很服气他，据说他早早就在外面注册了个公司，本系不少高手都被他挖去了。\n\n\n    关于肖奈的传说还有很多，不少还是从老师那传来的，因为他的父母是本校历史系和考古系的教授。据说肖奈父母都是清高古板且固守清贫的性格，到肖奈却基因突变，初中就知道找亲戚合资开网吧，那时电脑很不普及，正是网吧生意最好做的时候，还有传说他炒房炒股大赚的，纷纷杂杂，已经不知道真假了。\n\n\n    做为计算机系的一只小虾米，微微当然也是拜大神派的，她电脑里几个很好用的小软件，就是大神N年前的闲暇手笔。大神今年已经大四，以后在学校肯定更看不到他了，微微看向游戏，一笑奈何的头像还暗着，再看看时间，五点四十，当机立断的关了电脑，和舍友们一起奔向篮球馆。\n\n\n    到的时候篮球馆里已经人声鼎沸，晓玲的男友大钟站在门口接她们，把她们带到预留的位置上。\n\n\n    晓玲边走边看说：“大钟，肖奈呢，你不是说他会上场的？”\n\n\n    大钟不满：“你是来看我的还是肖哥的。”大钟是计算机系篮球队的主力后卫。\n\n\n    晓玲不屑他：“当然是看肖奈的，你有什么好看的。”\n\n\n    其余三个女生一起点头，大钟气绝，郁闷了好久才说：“肖哥肯定不上场，来不来看不一定，好像临时有事。”\n\n\n    女生们顿时有些失望，如果说刚刚眼睛里还闪着100瓦的光，现在大概只剩10瓦了，不过还好，篮球馆里热烈的气氛很快又让她们兴奋起来。\n\n\n    正等着球赛开始，一个高大的穿着计算机系球服的男生忽然走到她们面前，准确的说，是走到微微面前，低着头，欲言又止的样子，耳朵都是红的。\n\n\n    晓玲她们心里登时冒出三个字——又来了！\n\n\n    果然，扭捏了半天，高大健朗的男孩期期艾艾的说：“微微师姐，如果我们赢了，我能不能请你去吃夜宵？”\n\n\n    微微反问说：“你会输吗？”\n\n\n    “当然不会!”男孩受激地扬起头。\n\n\n    “那加油啊！”微微露出长辈般慈爱（？）的微笑，很大姐大地鼓励小师弟。\n\n\n    “嗯！师姐！我一定会赢！”\n\n\n    小师弟很受鼓舞的抱着皮球跑进球场热身去了。\n\n\n    “……”\n\n\n    “……”\n\n\n    “……”\n\n\n    以上为晓玲，二喜，丝丝三人的心声。\n\n\n    二喜说：“这个小师弟真好骗啊。”\n\n\n    丝丝不以为然：“最讨厌这种小P孩，拿球赛输赢来要挟人，要是微微拒绝了，会不会打输了还要怪微微啊。”\n\n\n    晓玲：“咱们微微拒绝人的手段真是越来越厉害了啊！”\n\n\n    微微嘿嘿笑两声，故作谦虚状：“没办法，次数多了嘛，唯熟练尔。”\n\n\n    毫无疑问的被暴打了，典型的欠扁啊。\n\n','Part 3 被求婚了','http://120.24.70.191:8080/audinkapp/upload/mp3.mp3','NULL','NULL');
/*!40000 ALTER TABLE `chapter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `collect`
--

DROP TABLE IF EXISTS `collect`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `collect` (
  `bookId` int(11) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  `mode` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `collect`
--

LOCK TABLES `collect` WRITE;
/*!40000 ALTER TABLE `collect` DISABLE KEYS */;
INSERT INTO `collect` VALUES (6,NULL,2),(6,NULL,2),(6,NULL,2),(6,NULL,2),(6,NULL,2),(6,NULL,2),(6,NULL,2),(6,NULL,2),(6,NULL,2),(6,NULL,2),(6,NULL,2),(6,NULL,2),(6,NULL,2),(6,NULL,2),(6,1,2),(6,1,2),(6,1,2),(6,1,2),(6,1,2),(6,1,2),(7,NULL,2),(7,NULL,2),(7,NULL,2),(8,NULL,2),(9,NULL,2),(10,NULL,2),(10,NULL,2),(11,NULL,2),(11,NULL,2),(11,NULL,2),(12,NULL,2),(13,NULL,2),(14,NULL,2),(14,-1,2),(13,-1,2),(6,1,1),(6,-1,2),(6,6,2),(6,6,1),(11,6,2),(11,6,1),(12,6,2),(12,6,1),(9,6,2),(9,6,1),(6,6,2),(14,6,2),(13,6,2),(8,6,2),(14,6,2),(6,6,2),(6,6,2),(9,-1,2),(16,6,2),(6,-1,2),(12,-1,2),(13,-1,2),(13,7,2),(13,7,1),(14,7,2),(14,7,1),(9,7,2),(9,7,2),(10,7,2),(17,7,2),(8,7,2),(9,7,2),(6,7,2),(6,7,2),(20,-1,2),(9,-1,2),(6,19,2),(18,-1,2),(18,-1,2),(18,-1,2),(18,-1,2),(19,-1,2),(18,20,2),(18,20,2),(18,20,2),(18,20,2),(18,20,2),(18,20,1),(18,20,2),(18,20,2),(16,20,2),(8,20,2),(18,11,2),(18,19,2),(19,19,2),(20,19,2),(6,19,2),(18,20,2),(18,11,2),(18,11,1),(19,11,2),(19,11,1);
/*!40000 ALTER TABLE `collect` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `record`
--

DROP TABLE IF EXISTS `record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `record` (
  `recordId` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL,
  `uploadTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`recordId`),
  UNIQUE KEY `record_recordId_uindex` (`recordId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `record`
--

LOCK TABLES `record` WRITE;
/*!40000 ALTER TABLE `record` DISABLE KEYS */;
/*!40000 ALTER TABLE `record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `user_userId_uindex` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'匿名用户','5c1a485b4f6c34db4fdee67afa3fe3e79bc5d0d20543f47b2be4e2d37ad824c4'),(3,'M_jsk','694b96f315ea82da226828ba7efeb5113ff34ed95b3f1db6dc74518179b63c9c'),(4,'NAFinal','da49520600aaecfe443b86d47b9411e65b4280c9166b521928c6449ed5d24271'),(5,'江枫眠','0b9c4ea9dcc8cf8f675787ec62d2885aecb35eea7aaf9dbd8fe410c3dc872c65'),(6,'tom','eb3f1ea63051d053f74b59e777fbd2ca43c1aaa6d99b60e18c37c1802c612add'),(7,'aqilayi','e36ed903810c7cd1e32ae0ac438efc42c87177984072811271ba24bfc1496905'),(8,'于钧一','f92ab3ea267aee7b1e75e6ca4a54673add699448556d93eeb76580248b6ac292'),(9,'hhe','f5147ee4b1601cfb4926d24a5114b715b0259373bd7273909252adbdbd62969d'),(10,'啦啦啦','84f86e917323ba47f2cdccec419e6616bcf009bf87cb352af48b06a911272173'),(11,'朋克西瓜','3bc2118261f6bc8652ca2a5183cb0812ce648e5998e83679f0d9de5887b725aa'),(12,'啪啪鹏','de1bc07a10e5a0f187724628f6a90f3a9b73f078c065ef3af28ed985f5c962b4'),(13,'喔喔喔','4eb83663994b9465f64a861f872fec392b10d0af4b22538dd73d6c801e7a7496'),(14,'哇哇的哭','f4f732efa023255b325155e01c6e35a4415452834c80c27910f865bcb7c0d267'),(15,'pppppp','6966ca65ab0d15a215726244fe70d7648b34d7020a9e28de5e345be89e4fd538'),(16,'大大大','5ff9fd9048f0ac383d062708a0a0b5713924dd9b131b5fcad56c45289e82f8d3'),(17,'tomm','61042d9585378e6b44d40a703ff8bdcdf54e772f2326355de2e4c2e667c74dcb'),(18,'人人网','31a1170159fd0557b14407c8a9081dfe1d0393015171361c422fbafa73b4a6b0'),(19,'哒哒哒','b50ebcfd034067fd48fc7e092c6585e88d9fa7dca44c0db8e99d40cbe8d32962'),(20,'jsk','375befa7b432dab3ae837812f0bd7029e02e12617093d2b842285967ffd71a13'),(21,'testuser3','cec2a8a4384e0c96550625a51e2d81ba6dd6137859b100192c34fa1b48c3a275');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-18  5:15:05
