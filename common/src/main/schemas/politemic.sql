/*
 Navicat Premium Data Transfer

 Source Server         : 本地_v8
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : politemic

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 22/12/2021 11:33:25
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '评论id',
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发布评论的用户id',
  `type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评论类型 0-对帖子的评论 1-对评论的评论',
  `target_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '目标id 对帖子的评论则为帖子id 对评论的评论则为评论id',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '评论内容',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评论状态  1-待审核 2-正常 3-已删除',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '评论发布时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES ('0993a8ba4c114ce0b8ba486e04ad1e82', '10a0c6f04a694d0e8eb293e2f314f4e3', '0', '06f8b069b11d44d689f8b0355a67ad6f', '牛皮', '2', '2021-12-22 09:49:32');
INSERT INTO `comment` VALUES ('3384199c8e8649f583ecd15842338128', '10a0c6f04a694d0e8eb293e2f314f4e3', '0', '06f8b069b11d44d689f8b0355a67ad6f', 'ssdyy', '2', '2021-12-22 09:49:22');
INSERT INTO `comment` VALUES ('45101fe136dd4ece87cc1d125585860b', '10a0c6f04a694d0e8eb293e2f314f4e3', '0', '06f8b069b11d44d689f8b0355a67ad6f', '是呢 yyds', '2', '2021-12-21 09:15:38');
INSERT INTO `comment` VALUES ('9b40c10cb5a14fd48bed75aab1fa7049', '10a0c6f04a694d0e8eb293e2f314f4e3', '1', '45101fe136dd4ece87cc1d125585860b', 'yydss', '2', '2021-12-22 09:50:58');
INSERT INTO `comment` VALUES ('ba5e162a9c204c8598f3702894cc0f52', '10a0c6f04a694d0e8eb293e2f314f4e3', '1', '45101fe136dd4ece87cc1d125585860b', 'yyds', '2', '2021-12-22 09:46:07');

-- ----------------------------
-- Table structure for discuss_posts
-- ----------------------------
DROP TABLE IF EXISTS `discuss_posts`;
CREATE TABLE `discuss_posts`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '帖子id',
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '帖子所属用户id',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '帖子主题',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '帖子内容',
  `type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '帖子类型 1-普通 2-置顶',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '帖子状态\r\n 1-发表后待审核\r\n 2-正常\r\n 3-精华帖\r\n 4-管理删除、审核未通过的拉黑帖\r\n 5-仅自己可见的私密帖',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '帖子创建时间',
  `star_count` int(0) NULL DEFAULT NULL COMMENT '帖子点赞数',
  `comment_count` int(0) NULL DEFAULT NULL COMMENT '帖子评论数',
  `confessed` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '帖子公开性 1-公开 2-私密 仅自己可见',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of discuss_posts
-- ----------------------------
INSERT INTO `discuss_posts` VALUES ('06f8b069b11d44d689f8b0355a67ad6f', '10a0c6f04a694d0e8eb293e2f314f4e3', 'knife4j yyds', 'yydds', '1', '2', '2021-12-21 09:01:38', NULL, NULL, '1');
INSERT INTO `discuss_posts` VALUES ('1fc6a492d51c4c49b5c4e6493ae52ab8', '10a0c6f04a694d0e8eb293e2f314f4e3', '今天贼困!', '我的第二个帖子', '1', '2', '2021-12-15 10:19:38', NULL, NULL, '1');
INSERT INTO `discuss_posts` VALUES ('2559762712ce461fab1ddfdb0bbe57af', '10a0c6f04a694d0e8eb293e2f314f4e3', '铁子二', '这能算第二个铁子吗', '1', '2', '2021-12-17 09:04:36', NULL, NULL, '1');
INSERT INTO `discuss_posts` VALUES ('26730eb8b76247dca278e5b9ffd8c164', '10a0c6f04a694d0e8eb293e2f314f4e3', '去学校', '太阳当空照,花儿对我笑', '1', '2', '2021-12-15 10:28:59', NULL, NULL, '1');
INSERT INTO `discuss_posts` VALUES ('52b253e73d9d4d55a260079c880b127f', '10a0c6f04a694d0e8eb293e2f314f4e3', '健身', '今天去健身,棒棒哒!', '1', '2', '2021-12-16 18:03:47', NULL, NULL, '1');
INSERT INTO `discuss_posts` VALUES ('5e8c71a3d2294ac690c5886e3f6031c8', '10a0c6f04a694d0e8eb293e2f314f4e3', '后天周三', '后天的昨天是冬至', '1', '2', '2021-12-20 15:45:11', NULL, NULL, '1');
INSERT INTO `discuss_posts` VALUES ('68e9001e703e4755a124c29d7b10eda4', '10a0c6f04a694d0e8eb293e2f314f4e3', '卡路里', '清晨起来打开窗,阳光美美哒', '1', '2', '2021-12-15 10:26:05', NULL, NULL, '1');
INSERT INTO `discuss_posts` VALUES ('798827899a9f4b5b875b85a17ea54f96', '10a0c6f04a694d0e8eb293e2f314f4e3', '今天周五', '要放假', '1', '2', '2021-12-17 09:35:52', NULL, NULL, '1');
INSERT INTO `discuss_posts` VALUES ('898442f750564e61af3fc2bd22cfa4fe', '10a0c6f04a694d0e8eb293e2f314f4e3', '健身01', '今天还准备去健身,棒棒哒!', '1', '2', '2021-12-17 08:52:15', NULL, NULL, '1');
INSERT INTO `discuss_posts` VALUES ('9abcfc92271948b9b6bb3a363e48f49a', '10a0c6f04a694d0e8eb293e2f314f4e3', '今天周一', '努力工作', '1', '2', '2021-12-20 15:34:28', NULL, NULL, '1');
INSERT INTO `discuss_posts` VALUES ('ac538a36a4a448dead431d725b570d01', '10a0c6f04a694d0e8eb293e2f314f4e3', '我的第一个帖子', '今天有点困!', '1', '2', '2021-12-15 09:57:50', NULL, NULL, '2');
INSERT INTO `discuss_posts` VALUES ('bdc63aecded54132b5f6cb0fd1d3d02c', '10a0c6f04a694d0e8eb293e2f314f4e3', '今天周二', '周二是个好日子', '1', '2', '2021-12-21 08:44:21', NULL, NULL, '1');
INSERT INTO `discuss_posts` VALUES ('df71c9557ba14e118c7d28f56214ef1c', '10a0c6f04a694d0e8eb293e2f314f4e3', 'politemic', 'politemic论坛,冉冉升起', '1', '3', '2021-12-15 10:43:13', NULL, NULL, '1');
INSERT INTO `discuss_posts` VALUES ('f656fa7a21cd4e18a06891de4f190f29', '10a0c6f04a694d0e8eb293e2f314f4e3', '神丹', '神丹老人冻得不想出门', '1', '2', '2021-12-17 09:24:14', NULL, NULL, '1');
INSERT INTO `discuss_posts` VALUES ('fa43793c95e74f3e94705f9b83321b1b', '10a0c6f04a694d0e8eb293e2f314f4e3', '放假', '明天就放假了,就很开心', '1', '2', '2021-12-17 08:57:22', NULL, NULL, '1');
INSERT INTO `discuss_posts` VALUES ('fe7e17abaa644c979eefb9d68353d8fd', '10a0c6f04a694d0e8eb293e2f314f4e3', '明天周二', '明天是个特殊的日子', '1', '2', '2021-12-20 15:41:20', NULL, NULL, '1');

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '通知id',
  `from_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '通知方 不填为系统通知 有值则为私信(对应用户id) ',
  `to_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接收方',
  `content` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '通知内容',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '通知状态 0-未读 1-已读 2-删除',
  `time` datetime(0) NULL DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES (1, NULL, '10a0c6f04a694d0e8eb293e2f314f4e3', 'bababababbabababa', '1', '2021-10-20 15:49:46');
INSERT INTO `notice` VALUES (2, NULL, '10a0c6f04a694d0e8eb293e2f314f4e3', 'bibibibibibibibibbi', '1', '2021-11-01 08:30:31');
INSERT INTO `notice` VALUES (3, '1fabe517773745f8b07900fbc9fd56da', '10a0c6f04a694d0e8eb293e2f314f4e3', 'lililililililililililili', '1', '2021-12-02 08:32:07');
INSERT INTO `notice` VALUES (4, NULL, '10a0c6f04a694d0e8eb293e2f314f4e3', 'vivivivivivivivivivi', '1', '2021-11-03 08:34:28');
INSERT INTO `notice` VALUES (5, NULL, '10a0c6f04a694d0e8eb293e2f314f4e3', 'kikikikikikikikiki', '1', '2021-12-04 08:34:56');
INSERT INTO `notice` VALUES (6, '1fabe517773745f8b07900fbc9fd56da', '10a0c6f04a694d0e8eb293e2f314f4e3', 'uiuiuiuiuiuiuiuiuiui', '1', '2021-11-05 08:37:06');
INSERT INTO `notice` VALUES (7, NULL, '10a0c6f04a694d0e8eb293e2f314f4e3', 'yiyiyiyiyiyiyiyiyiyiyiyi', '1', '2022-01-06 08:37:59');
INSERT INTO `notice` VALUES (8, '1fabe517773745f8b07900fbc9fd56da', '10a0c6f04a694d0e8eb293e2f314f4e3', 'ioioioioioioioio', '1', '2021-11-28 15:27:41');
INSERT INTO `notice` VALUES (9, NULL, '10a0c6f04a694d0e8eb293e2f314f4e3', 'tititititititititititi', '1', '2021-11-10 15:28:16');
INSERT INTO `notice` VALUES (10, NULL, '10a0c6f04a694d0e8eb293e2f314f4e3', 'riririririririririririri', '1', '2021-11-15 15:28:55');
INSERT INTO `notice` VALUES (11, NULL, '10a0c6f04a694d0e8eb293e2f314f4e3', '帖子审核完成,审核结果: 通过!', '1', '2021-12-15 09:57:51');
INSERT INTO `notice` VALUES (12, NULL, '10a0c6f04a694d0e8eb293e2f314f4e3', '帖子审核完成,审核结果: 通过!', '1', '2021-12-15 10:19:39');
INSERT INTO `notice` VALUES (13, NULL, '10a0c6f04a694d0e8eb293e2f314f4e3', '帖子审核完成,审核结果: 通过!', '1', '2021-12-15 10:26:05');
INSERT INTO `notice` VALUES (14, NULL, '10a0c6f04a694d0e8eb293e2f314f4e3', '帖子审核完成,审核结果: 通过!', '0', '2021-12-15 10:28:59');
INSERT INTO `notice` VALUES (15, NULL, '10a0c6f04a694d0e8eb293e2f314f4e3', '帖子审核完成,审核结果: 通过!', '0', '2021-12-15 10:43:13');
INSERT INTO `notice` VALUES (16, NULL, '10a0c6f04a694d0e8eb293e2f314f4e3', '评论审核完成,审核结果: 通过!', '0', '2021-12-16 18:03:47');
INSERT INTO `notice` VALUES (17, NULL, '10a0c6f04a694d0e8eb293e2f314f4e3', '评论审核完成,审核结果: 通过!', '0', '2021-12-17 08:52:15');
INSERT INTO `notice` VALUES (18, NULL, '10a0c6f04a694d0e8eb293e2f314f4e3', '帖子审核完成,审核结果: 通过!', '0', '2021-12-17 08:57:23');
INSERT INTO `notice` VALUES (19, NULL, '10a0c6f04a694d0e8eb293e2f314f4e3', '评论审核完成,审核结果: 通过!', '0', '2021-12-17 09:04:36');
INSERT INTO `notice` VALUES (20, NULL, '10a0c6f04a694d0e8eb293e2f314f4e3', '评论审核完成,审核结果: 通过!', '0', '2021-12-17 09:24:14');
INSERT INTO `notice` VALUES (21, NULL, '10a0c6f04a694d0e8eb293e2f314f4e3', '评论审核完成,审核结果: 通过!', '0', '2021-12-17 09:35:52');
INSERT INTO `notice` VALUES (22, NULL, '10a0c6f04a694d0e8eb293e2f314f4e3', '帖子审核完成,审核结果: 通过!', '0', '2021-12-20 15:41:21');
INSERT INTO `notice` VALUES (23, NULL, '10a0c6f04a694d0e8eb293e2f314f4e3', '帖子审核完成,审核结果: 通过!', '0', '2021-12-20 15:45:11');
INSERT INTO `notice` VALUES (27, NULL, '10a0c6f04a694d0e8eb293e2f314f4e3', '帖子审核完成,审核结果: 通过!', '0', '2021-12-21 08:44:21');
INSERT INTO `notice` VALUES (30, NULL, '10a0c6f04a694d0e8eb293e2f314f4e3', '帖子审核完成,审核结果: 通过!', '0', '2021-12-21 09:01:38');
INSERT INTO `notice` VALUES (33, NULL, '10a0c6f04a694d0e8eb293e2f314f4e3', '评论审核完成,审核结果: 通过!', '0', '2021-12-21 09:15:38');
INSERT INTO `notice` VALUES (34, NULL, '10a0c6f04a694d0e8eb293e2f314f4e3', '评论审核完成,审核结果: 通过!', '0', '2021-12-22 09:46:07');
INSERT INTO `notice` VALUES (35, NULL, '10a0c6f04a694d0e8eb293e2f314f4e3', '评论审核完成,审核结果: 通过!', '0', '2021-12-22 09:49:22');
INSERT INTO `notice` VALUES (36, NULL, '10a0c6f04a694d0e8eb293e2f314f4e3', '评论审核完成,审核结果: 通过!', '0', '2021-12-22 09:49:32');
INSERT INTO `notice` VALUES (37, NULL, '10a0c6f04a694d0e8eb293e2f314f4e3', '评论审核完成,审核结果: 通过!', '0', '2021-12-22 09:50:58');

-- ----------------------------
-- Table structure for queue_msg
-- ----------------------------
DROP TABLE IF EXISTS `queue_msg`;
CREATE TABLE `queue_msg`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '消息id',
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '消息对应的用户id',
  `type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '消息类型\r\n 0:注册邮件消息\r\n 1:获取评论尾巴消息\r\n 2-帖子审核消息\r\n 3-评论审核消息',
  `msg_content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '消息主题内容',
  `msg_correlation_data` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '消息回执数据',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '消息状态\r\n0: 准备消息发送至队列\r\n1: 队列收到消息,准备发给消费者\r\n2: 消费者收到消息,处理',
  `product_time` datetime(0) NULL DEFAULT NULL COMMENT '消息生产时间',
  `consum_time` datetime(0) NULL DEFAULT NULL COMMENT '消息消费时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of queue_msg
-- ----------------------------
INSERT INTO `queue_msg` VALUES ('018b76e19c5e454a94f25b9f91bf9514', 'a9ca271d46d2432faf58cefd7c1f1fb3', '0', '{\"toUser\":\"161078369@qq.com\",\"id\":\"018b76e19c5e454a94f25b9f91bf9514\",\"subject\":\"PM官方\",\"content\":\"邮件内容 一串带有用户信息和验证码的html内容 链接前端激活页面 页面初始化时请求后端激活接口 完成激活\"}', '018b76e19c5e454a94f25b9f91bf9514', '2', '2021-12-14 13:07:17', '2021-12-14 13:07:19');
INSERT INTO `queue_msg` VALUES ('09c8939f71674a90be767841d03433b3', '10a0c6f04a694d0e8eb293e2f314f4e3', '2', '{\"id\":\"09c8939f71674a90be767841d03433b3\",\"postId\":\"ac538a36a4a448dead431d725b570d01\",\"userId\":\"10a0c6f04a694d0e8eb293e2f314f4e3\"}', '09c8939f71674a90be767841d03433b3', '2', '2021-12-15 09:57:51', '2021-12-15 09:57:51');
INSERT INTO `queue_msg` VALUES ('0aab62035111432f92d1e87e13036d63', '10a0c6f04a694d0e8eb293e2f314f4e3', '3', '{\"userId\":\"10a0c6f04a694d0e8eb293e2f314f4e3\",\"content\":\"牛皮\",\"commentId\":\"0993a8ba4c114ce0b8ba486e04ad1e82\",\"id\":\"0aab62035111432f92d1e87e13036d63\"}', '0aab62035111432f92d1e87e13036d63', '2', '2021-12-22 09:49:32', '2021-12-22 09:49:32');
INSERT INTO `queue_msg` VALUES ('11e00be6af024b4c9ab34602aa1e7c30', '10a0c6f04a694d0e8eb293e2f314f4e3', '3', '{\"userId\":\"10a0c6f04a694d0e8eb293e2f314f4e3\",\"content\":\"后天天气不错 真的\",\"commentId\":\"ad4726f4bc9e4e119aaf8f307e2e5573\",\"id\":\"11e00be6af024b4c9ab34602aa1e7c30\"}', '11e00be6af024b4c9ab34602aa1e7c30', '1', '2021-12-20 17:31:27', NULL);
INSERT INTO `queue_msg` VALUES ('13df0759f85b4e2c98af4cc12bd05d2e', '10a0c6f04a694d0e8eb293e2f314f4e3', '2', '{\"postId\":\"2559762712ce461fab1ddfdb0bbe57af\",\"title\":\"铁子二\",\"userId\":\"10a0c6f04a694d0e8eb293e2f314f4e3\",\"content\":\"这能算第二个铁子吗\",\"id\":\"13df0759f85b4e2c98af4cc12bd05d2e\"}', '13df0759f85b4e2c98af4cc12bd05d2e', '1', '2021-12-17 09:04:36', '2021-12-17 09:04:36');
INSERT INTO `queue_msg` VALUES ('1b0d9daa4b8a4b89b97a993aea83516b', '3fc43448004d4fe2987a194a415fca8c', '0', '{\"toUser\":\"161078369@qq.com\",\"id\":\"1b0d9daa4b8a4b89b97a993aea83516b\",\"subject\":\"PM官方\",\"content\":\"邮件内容 一串带有用户信息和验证码的html内容 链接前端激活页面 页面初始化时请求后端激活接口 完成激活\"}', '1b0d9daa4b8a4b89b97a993aea83516b', '2', '2021-12-10 13:19:08', '2021-12-10 13:19:10');
INSERT INTO `queue_msg` VALUES ('2a36bfed0bad46d29ca3e4680b32cff9', '10a0c6f04a694d0e8eb293e2f314f4e3', '3', '{\"userId\":\"10a0c6f04a694d0e8eb293e2f314f4e3\",\"content\":\"后天天气不错 真的\",\"commentId\":\"b1eaca93104644dabd54f38d1d75fedc\",\"id\":\"2a36bfed0bad46d29ca3e4680b32cff9\"}', '2a36bfed0bad46d29ca3e4680b32cff9', '1', '2021-12-20 17:53:29', NULL);
INSERT INTO `queue_msg` VALUES ('2c3b78a7cc5045f7b51f1b5f340d2882', '10a0c6f04a694d0e8eb293e2f314f4e3', '2', '{\"postId\":\"52b253e73d9d4d55a260079c880b127f\",\"title\":\"健身\",\"userId\":\"10a0c6f04a694d0e8eb293e2f314f4e3\",\"content\":\"今天去健身,棒棒哒!\",\"id\":\"2c3b78a7cc5045f7b51f1b5f340d2882\"}', '2c3b78a7cc5045f7b51f1b5f340d2882', '2', '2021-12-16 18:03:47', '2021-12-16 18:03:48');
INSERT INTO `queue_msg` VALUES ('328a631ef70e41bd88f41a758a0f5d8e', '10a0c6f04a694d0e8eb293e2f314f4e3', '3', '{\"userId\":\"10a0c6f04a694d0e8eb293e2f314f4e3\",\"content\":\"ssdyy\",\"commentId\":\"3384199c8e8649f583ecd15842338128\",\"id\":\"328a631ef70e41bd88f41a758a0f5d8e\"}', '328a631ef70e41bd88f41a758a0f5d8e', '2', '2021-12-22 09:49:22', '2021-12-22 09:49:22');
INSERT INTO `queue_msg` VALUES ('34323eea6d31445abf35134bc1ca9b67', '10a0c6f04a694d0e8eb293e2f314f4e3', '3', '{\"userId\":\"10a0c6f04a694d0e8eb293e2f314f4e3\",\"content\":\"yydss\",\"commentId\":\"9b40c10cb5a14fd48bed75aab1fa7049\",\"id\":\"34323eea6d31445abf35134bc1ca9b67\"}', '34323eea6d31445abf35134bc1ca9b67', '1', '2021-12-22 09:50:58', '2021-12-22 09:50:58');
INSERT INTO `queue_msg` VALUES ('38e3d94cd0ab4c75a3ed96d00b707ce4', 'f756fa0f857c48e187bc23cbf0034840', '1', '{\"userId\":\"f756fa0f857c48e187bc23cbf0034840\",\"url\":\"https://v1.jinrishici.com/all.json\",\"id\":\"38e3d94cd0ab4c75a3ed96d00b707ce4\"}', NULL, '2', NULL, '2021-12-10 13:27:54');
INSERT INTO `queue_msg` VALUES ('480c7adf958948858dfabcc14ccf0dc9', '10a0c6f04a694d0e8eb293e2f314f4e3', '2', '{\"postId\":\"898442f750564e61af3fc2bd22cfa4fe\",\"title\":\"健身01\",\"userId\":\"10a0c6f04a694d0e8eb293e2f314f4e3\",\"content\":\"今天还准备去健身,棒棒哒!\",\"id\":\"480c7adf958948858dfabcc14ccf0dc9\"}', '480c7adf958948858dfabcc14ccf0dc9', '2', '2021-12-17 08:52:15', '2021-12-17 08:52:15');
INSERT INTO `queue_msg` VALUES ('49e4f49c8070492086e963ccccbd151e', '10a0c6f04a694d0e8eb293e2f314f4e3', '2', '{\"postId\":\"f656fa7a21cd4e18a06891de4f190f29\",\"title\":\"神丹\",\"userId\":\"10a0c6f04a694d0e8eb293e2f314f4e3\",\"content\":\"神丹老人冻得不想出门\",\"id\":\"49e4f49c8070492086e963ccccbd151e\"}', '49e4f49c8070492086e963ccccbd151e', '1', '2021-12-17 09:24:14', '2021-12-17 09:24:14');
INSERT INTO `queue_msg` VALUES ('4c5a06311f334e049b0eb44551bdd94c', '10a0c6f04a694d0e8eb293e2f314f4e3', '2', '{\"id\":\"4c5a06311f334e049b0eb44551bdd94c\",\"postId\":\"df71c9557ba14e118c7d28f56214ef1c\",\"title\":\"politemic\",\"userId\":\"10a0c6f04a694d0e8eb293e2f314f4e3\",\"content\":\"politemic论坛,冉冉升起\"}', '4c5a06311f334e049b0eb44551bdd94c', '2', '2021-12-15 10:43:13', '2021-12-15 10:43:13');
INSERT INTO `queue_msg` VALUES ('55ac1af6e106491b869c1b3e2cea7c5c', '10a0c6f04a694d0e8eb293e2f314f4e3', '2', '{\"id\":\"55ac1af6e106491b869c1b3e2cea7c5c\",\"postId\":\"1fc6a492d51c4c49b5c4e6493ae52ab8\",\"title\":\"今天贼困!\",\"userId\":\"10a0c6f04a694d0e8eb293e2f314f4e3\",\"content\":\"我的第二个帖子\"}', '55ac1af6e106491b869c1b3e2cea7c5c', '2', '2021-12-15 10:19:39', '2021-12-15 10:19:39');
INSERT INTO `queue_msg` VALUES ('571e5b49b9424f5dbc2044f50ea04f30', '3683c7a5914945369044ed54bb10b475', '2', '{\"id\":\"571e5b49b9424f5dbc2044f50ea04f30\",\"postId\":\"9933eaa419734f3ba9aa35dc60f6b236\",\"title\":\"啦啦啦啦啦\",\"content\":\"kakakakakakakakakak\"}', '571e5b49b9424f5dbc2044f50ea04f30', '2', '2021-10-20 13:52:08', '2021-10-20 13:52:09');
INSERT INTO `queue_msg` VALUES ('60a3529f5d894917bd5cfa81e5c6b349', '3683c7a5914945369044ed54bb10b475', '2', '{\"id\":\"60a3529f5d894917bd5cfa81e5c6b349\",\"postId\":\"957d3ee16c4c4b8fb8ee30f28e44c71e\",\"title\":\"abab\",\"content\":\"palapo\"}', '60a3529f5d894917bd5cfa81e5c6b349', '2', '2021-10-20 15:17:59', '2021-10-20 15:17:59');
INSERT INTO `queue_msg` VALUES ('62dfabbb43614a888f4b0c35640b1ebd', '10a0c6f04a694d0e8eb293e2f314f4e3', '3', '{\"userId\":\"10a0c6f04a694d0e8eb293e2f314f4e3\",\"content\":\"后天天气不错 真的\",\"commentId\":\"27df3eef17c6461b842d5fd52c2d44e2\",\"id\":\"62dfabbb43614a888f4b0c35640b1ebd\"}', '62dfabbb43614a888f4b0c35640b1ebd', '1', '2021-12-20 18:05:44', NULL);
INSERT INTO `queue_msg` VALUES ('67d546861dcc4a1fa1dcfee3d4a5b47f', '10a0c6f04a694d0e8eb293e2f314f4e3', '2', '{\"id\":\"67d546861dcc4a1fa1dcfee3d4a5b47f\",\"postId\":\"68e9001e703e4755a124c29d7b10eda4\",\"title\":\"卡路里\",\"userId\":\"10a0c6f04a694d0e8eb293e2f314f4e3\",\"content\":\"清晨起来打开窗,阳光美美哒\"}', '67d546861dcc4a1fa1dcfee3d4a5b47f', '2', '2021-12-15 10:26:05', '2021-12-15 10:26:05');
INSERT INTO `queue_msg` VALUES ('6b2cd9ed10714aeeaa5fab00fee7f243', '10a0c6f04a694d0e8eb293e2f314f4e3', '3', '{\"userId\":\"10a0c6f04a694d0e8eb293e2f314f4e3\",\"content\":\"是呢 yyds\",\"commentId\":\"45101fe136dd4ece87cc1d125585860b\",\"id\":\"6b2cd9ed10714aeeaa5fab00fee7f243\"}', '6b2cd9ed10714aeeaa5fab00fee7f243', '2', '2021-12-21 09:15:39', '2021-12-21 09:15:39');
INSERT INTO `queue_msg` VALUES ('7743ac121b444979a720a984ecfb05bd', 'f756fa0f857c48e187bc23cbf0034840', '0', '{\"toUser\":\"161078369@qq.com\",\"id\":\"7743ac121b444979a720a984ecfb05bd\",\"subject\":\"PM官方\",\"content\":\"邮件内容 一串带有用户信息和验证码的html内容 链接前端激活页面 页面初始化时请求后端激活接口 完成激活\"}', NULL, '0', NULL, NULL);
INSERT INTO `queue_msg` VALUES ('812befc0e6004a9dadb9224de63f2424', '10a0c6f04a694d0e8eb293e2f314f4e3', '2', '{\"id\":\"812befc0e6004a9dadb9224de63f2424\",\"postId\":\"26730eb8b76247dca278e5b9ffd8c164\",\"title\":\"去学校\",\"userId\":\"10a0c6f04a694d0e8eb293e2f314f4e3\",\"content\":\"太阳当空照,花儿对我笑\"}', '812befc0e6004a9dadb9224de63f2424', '2', '2021-12-15 10:28:59', '2021-12-15 10:29:00');
INSERT INTO `queue_msg` VALUES ('84b943cfccfc4c6385de1609bcb80d64', '10a0c6f04a694d0e8eb293e2f314f4e3', '1', '{\"userId\":\"10a0c6f04a694d0e8eb293e2f314f4e3\",\"url\":\"https://v1.jinrishici.com/all.json\",\"id\":\"84b943cfccfc4c6385de1609bcb80d64\"}', '84b943cfccfc4c6385de1609bcb80d64', '2', '2021-12-10 13:30:16', '2021-12-10 13:30:18');
INSERT INTO `queue_msg` VALUES ('8b67c4941707440cbca38165d26d0729', '10a0c6f04a694d0e8eb293e2f314f4e3', '2', '{\"postId\":\"9abcfc92271948b9b6bb3a363e48f49a\",\"title\":\"今天周一\",\"userId\":\"10a0c6f04a694d0e8eb293e2f314f4e3\",\"content\":\"努力工作\",\"id\":\"8b67c4941707440cbca38165d26d0729\"}', '8b67c4941707440cbca38165d26d0729', '1', '2021-12-20 15:34:29', NULL);
INSERT INTO `queue_msg` VALUES ('995f94aa40a949db818f717a8e70394b', 'a9ca271d46d2432faf58cefd7c1f1fb3', '1', '{\"id\":\"995f94aa40a949db818f717a8e70394b\",\"userId\":\"a9ca271d46d2432faf58cefd7c1f1fb3\",\"url\":\"https://v1.jinrishici.com/all.json\"}', '995f94aa40a949db818f717a8e70394b', '2', '2021-12-14 13:07:18', '2021-12-14 13:07:19');
INSERT INTO `queue_msg` VALUES ('a159eab49c3c429083293abef45cfcba', '056fe0a9727347cf86fa829f6dc62f4f', '1', '{\"userId\":\"056fe0a9727347cf86fa829f6dc62f4f\",\"url\":\"https://v1.jinrishici.com/all.json\",\"id\":\"a159eab49c3c429083293abef45cfcba\"}', 'a159eab49c3c429083293abef45cfcba', '2', '2021-12-10 12:27:31', '2021-12-10 12:27:32');
INSERT INTO `queue_msg` VALUES ('ad5496bebf244d9d8e95fa564924e684', '10a0c6f04a694d0e8eb293e2f314f4e3', '2', '{\"postId\":\"fe7e17abaa644c979eefb9d68353d8fd\",\"title\":\"明天周二\",\"userId\":\"10a0c6f04a694d0e8eb293e2f314f4e3\",\"content\":\"明天是个特殊的日子\",\"id\":\"ad5496bebf244d9d8e95fa564924e684\"}', 'ad5496bebf244d9d8e95fa564924e684', '2', '2021-12-20 15:41:21', '2021-12-20 15:41:21');
INSERT INTO `queue_msg` VALUES ('ccd5c791b99d4e1b9eb2ee4c08a593e2', '10a0c6f04a694d0e8eb293e2f314f4e3', '2', '{\"postId\":\"798827899a9f4b5b875b85a17ea54f96\",\"title\":\"今天周五\",\"userId\":\"10a0c6f04a694d0e8eb293e2f314f4e3\",\"content\":\"要放假\",\"id\":\"ccd5c791b99d4e1b9eb2ee4c08a593e2\"}', 'ccd5c791b99d4e1b9eb2ee4c08a593e2', '1', '2021-12-17 09:35:52', '2021-12-17 09:35:52');
INSERT INTO `queue_msg` VALUES ('d61e3496aefc461dad013803d701800c', '10a0c6f04a694d0e8eb293e2f314f4e3', '3', '{\"userId\":\"10a0c6f04a694d0e8eb293e2f314f4e3\",\"content\":\"yyds\",\"commentId\":\"ba5e162a9c204c8598f3702894cc0f52\",\"id\":\"d61e3496aefc461dad013803d701800c\"}', 'd61e3496aefc461dad013803d701800c', '2', '2021-12-22 09:46:07', '2021-12-22 09:46:07');
INSERT INTO `queue_msg` VALUES ('d6cf828659c64f2d8cc65b268398aabf', '3fc43448004d4fe2987a194a415fca8c', '1', '{\"userId\":\"3fc43448004d4fe2987a194a415fca8c\",\"url\":\"https://v1.jinrishici.com/all.json\",\"id\":\"d6cf828659c64f2d8cc65b268398aabf\"}', 'd6cf828659c64f2d8cc65b268398aabf', '2', '2021-12-10 13:19:08', '2021-12-10 13:19:09');
INSERT INTO `queue_msg` VALUES ('dd75738cfc2e4ea8bae47d1a56ce2cbf', '10a0c6f04a694d0e8eb293e2f314f4e3', '0', '{\"toUser\":\"161078369@qq.com\",\"id\":\"dd75738cfc2e4ea8bae47d1a56ce2cbf\",\"subject\":\"PM官方\",\"content\":\"邮件内容 一串带有用户信息和验证码的html内容 链接前端激活页面 页面初始化时请求后端激活接口 完成激活\"}', 'dd75738cfc2e4ea8bae47d1a56ce2cbf', '2', '2021-12-10 13:30:16', '2021-12-10 13:30:23');
INSERT INTO `queue_msg` VALUES ('ead71f3094f048ab874285596e391873', '10a0c6f04a694d0e8eb293e2f314f4e3', '2', '{\"postId\":\"5e8c71a3d2294ac690c5886e3f6031c8\",\"title\":\"后天周三\",\"userId\":\"10a0c6f04a694d0e8eb293e2f314f4e3\",\"content\":\"后天的昨天是冬至\",\"id\":\"ead71f3094f048ab874285596e391873\"}', 'ead71f3094f048ab874285596e391873', '2', '2021-12-20 15:45:11', '2021-12-20 15:45:11');
INSERT INTO `queue_msg` VALUES ('eb79b2457466416484844162a977b0df', '10a0c6f04a694d0e8eb293e2f314f4e3', '2', '{\"postId\":\"fa43793c95e74f3e94705f9b83321b1b\",\"title\":\"放假\",\"userId\":\"10a0c6f04a694d0e8eb293e2f314f4e3\",\"content\":\"明天就放假了,就很开心\",\"id\":\"eb79b2457466416484844162a977b0df\"}', 'eb79b2457466416484844162a977b0df', '2', '2021-12-17 08:57:23', '2021-12-17 08:57:23');
INSERT INTO `queue_msg` VALUES ('ed3318a31fee47de8e8f35b63bd00e96', 'cc9b7ddcb8a747d1b0ef59e241737d38', '1', '{\"userId\":\"cc9b7ddcb8a747d1b0ef59e241737d38\",\"url\":\"https://v1.jinrishici.com/all.json\",\"id\":\"ed3318a31fee47de8e8f35b63bd00e96\"}', 'ed3318a31fee47de8e8f35b63bd00e96', '2', '2021-12-10 12:30:05', '2021-12-10 12:30:06');

-- ----------------------------
-- Table structure for schedule_task
-- ----------------------------
DROP TABLE IF EXISTS `schedule_task`;
CREATE TABLE `schedule_task`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '定时任务的id',
  `task_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `cron` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'cron表达式',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of schedule_task
-- ----------------------------
INSERT INTO `schedule_task` VALUES (1, 'schedule_task_send_failure_email', '0 0 2 ? * *', 'Y');
INSERT INTO `schedule_task` VALUES (2, 'schedule_task_take_tail', '0 10 2 ? * *', 'Y');
INSERT INTO `schedule_task` VALUES (3, 'schedule_task_audit_failure_post', '0 20 2 ? * *', 'Y');
INSERT INTO `schedule_task` VALUES (4, 'schedule_task_audit_failure_comment', '0 30 2 ? * *', 'Y');

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '系统配置-键',
  `value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统配置-值',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统配置-状态',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统配置-备注',
  PRIMARY KEY (`key`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES ('header_url', 'https://api.multiavatar.com/{}.svg', 'Y', '用户头像获取url');
INSERT INTO `sys_config` VALUES ('schedule_task_audit_failure_comment', 'schedule_task_audit_failure_comment', 'Y', '定时审核异常(服务器异常导致的)评论');
INSERT INTO `sys_config` VALUES ('schedule_task_audit_failure_post', 'schedule_task_audit_failure_post', 'Y', '定时审核(服务器异常导致的)异常帖子');
INSERT INTO `sys_config` VALUES ('schedule_task_send_failure_email', 'schedule_task_send_failure_email', 'Y', '定时发送邮件配置');
INSERT INTO `sys_config` VALUES ('schedule_task_take_tail', 'schedule_task_take_tail', 'Y', '定时发送失败尾巴请求');
INSERT INTO `sys_config` VALUES ('tail_url', 'https://v1.jinrishici.com/all.json', 'Y', '用户默认评论尾巴获取url');

-- ----------------------------
-- Table structure for sys_exception_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_exception_log`;
CREATE TABLE `sys_exception_log`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '异常日志的id',
  `trace` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '异常堆栈信息',
  `datetime` datetime(0) NULL DEFAULT NULL COMMENT '异常抛出时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_exception_log
-- ----------------------------
INSERT INTO `sys_exception_log` VALUES (1, 'org.springframework.http.converter.HttpMessageNotWritableException: No converter found for return value of type: class com.xbh.politemic.util.Result	at org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodProcessor.writeWithMessageConverters(AbstractMessageConverterMethodProcessor.java:220)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor.handleReturnValue(RequestResponseBodyMethodProcessor.java:181)\n	at org.springframework.web.method.support.HandlerMethodReturnValueHandlerComposite.handleReturnValue(HandlerMethodReturnValueHandlerComposite.java:82)\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:123)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:878)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:792)\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87)\n	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1,040)\n	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:943)\n	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1,006)\n	at org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:898)\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:626)\n	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:883)\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:733)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:231)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:53)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	at org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:100)\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	at org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:93)\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:201)\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:202)\n	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:97)\n	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:542)\n	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:143)\n	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:92)\n	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:78)\n	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:343)\n	at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:374)\n	at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:65)\n	at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:888)\n	at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1,597)\n	at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49)\n	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1,149)\n	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)\n	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)\n	at java.lang.Thread.run(Thread.java:748)\n', '2021-10-03 14:33:52');

-- ----------------------------
-- Table structure for sys_interface_auth
-- ----------------------------
DROP TABLE IF EXISTS `sys_interface_auth`;
CREATE TABLE `sys_interface_auth`  (
  `interface_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '接口路径 可以是正则',
  `desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `matchs_auth_level` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '允许那些auth_field_level访问',
  PRIMARY KEY (`interface_path`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_interface_auth
-- ----------------------------

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '日志id',
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求用户的userId',
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求路径',
  `params` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '请求参数',
  `model_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模块名',
  `behavior` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模块功能',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模块备注',
  `ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求ip',
  `time` datetime(0) NULL DEFAULT NULL COMMENT '请求时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES (1, 'abcd', '/user/user/test', '{\"test\":[\"test\"]}', '普通用户模块', '用于测试', 'test可选', '0:0:0:0:0:0:0:1', '2021-10-03 20:37:59');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `salt` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码盐',
  `user_pass` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '加盐密码',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '绑定邮箱',
  `auth_field_level` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限等级 0:超管 1:普通管理  2:普通用户',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户状态 0:未激活  1:已激活',
  `activation_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发送到邮箱的验证码',
  `header_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户头像url',
  `tail` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评论尾巴',
  `tail_status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '尾巴开启状态 Y-开启 N-关闭 默认关闭',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('10a0c6f04a694d0e8eb293e2f314f4e3', 'ww', '88c8f4', '027e5861620f902d1b0b8cf15794d67d', '', '2', '1', '797AC2E', 'https://api.multiavatar.com/10a0c6f04a694d0e8eb293e2f314f4e3.svg', '洛阳城里春光好，洛阳才子他乡老', 'N');
INSERT INTO `sys_user` VALUES ('1fabe517773745f8b07900fbc9fd56da', 'admin', '0bbf34', '8e8cbeef141ef1ba38c391aebb5f487a', '', '0', '1', '', NULL, NULL, NULL);
INSERT INTO `sys_user` VALUES ('a9ca271d46d2432faf58cefd7c1f1fb3', 'ee', '126516', 'fb6bf266a8c31be4fb085f9e9c9946bd', '161078369@qq.com', '2', '1', 'D4C4968', 'https://api.multiavatar.com/a9ca271d46d2432faf58cefd7c1f1fb3.svg', '秋草六朝寒，花雨空坛。更无人处一凭阑。', 'N');
INSERT INTO `sys_user` VALUES ('f756fa0f857c48e187bc23cbf0034840', 'qq', 'cc1518', 'b0ba8b6e80f388468c53e0696047d27f', '', '2', '1', '5B525B9', 'https://api.multiavatar.com/f756fa0f857c48e187bc23cbf0034840.svg', NULL, 'N');

-- ----------------------------
-- Table structure for user_token
-- ----------------------------
DROP TABLE IF EXISTS `user_token`;
CREATE TABLE `user_token`  (
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'token令牌',
  `expire` datetime(0) NULL DEFAULT NULL COMMENT '到期时间',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_token
-- ----------------------------
INSERT INTO `user_token` VALUES ('10a0c6f04a694d0e8eb293e2f314f4e3', 'b76c96290ebf43dc899c38aceb19b0d9', '2021-12-25 09:40:30');
INSERT INTO `user_token` VALUES ('1fabe517773745f8b07900fbc9fd56da', '0f2cd1a1e4354b4b8403c672f0b60245', '2021-12-12 17:03:01');
INSERT INTO `user_token` VALUES ('3683c7a5914945369044ed54bb10b475', 'e09724f5ea094f4f9f9626a634a3c973', '2021-12-13 13:16:57');

SET FOREIGN_KEY_CHECKS = 1;
