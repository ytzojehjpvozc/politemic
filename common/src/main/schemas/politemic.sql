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

 Date: 30/12/2021 18:01:18
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
INSERT INTO `discuss_posts` VALUES ('1ca63bd455e941c9902c221e5d8937f8', '10a0c6f04a694d0e8eb293e2f314f4e3', '专题:Kibana', 'Kibana今天星期二，微风，有太阳', '1', '2', '2021-12-28 17:53:40', NULL, NULL, '1');
INSERT INTO `discuss_posts` VALUES ('6a20835bcc1243e7a02144489dbee4ea', '10a0c6f04a694d0e8eb293e2f314f4e3', '专题:Kibana', 'Kibana今天星期二，微风', '1', '2', '2021-12-28 17:24:25', NULL, NULL, '1');
INSERT INTO `discuss_posts` VALUES ('6ed6e67b5aa243feae60b2bda46a8472', '10a0c6f04a694d0e8eb293e2f314f4e3', '专题:Logstash', 'Logstash今天微风，有太阳', '1', '2', '2021-12-28 17:23:51', NULL, NULL, '1');
INSERT INTO `discuss_posts` VALUES ('7ae3b5c9d2174fafa40ca496f339d181', '10a0c6f04a694d0e8eb293e2f314f4e3', '专题:二狗', 'Kibana今天星期二，微风，有一个人对着太阳笑', '1', '2', '2021-12-30 17:55:48', NULL, NULL, '1');
INSERT INTO `discuss_posts` VALUES ('95fff630dddb4495b99a15a616da3a39', '10a0c6f04a694d0e8eb293e2f314f4e3', '专题:Elasticsearch', 'Elasticsearch今天星期二，有太阳', '1', '2', '2021-12-28 17:23:31', NULL, NULL, '1');
INSERT INTO `discuss_posts` VALUES ('961f457c888b4c7491efde9c884a4fe1', '10a0c6f04a694d0e8eb293e2f314f4e3', '专题:Kibana', 'Kibana今天星期二，微风', '1', '2', '2021-12-28 17:35:06', NULL, NULL, '1');
INSERT INTO `discuss_posts` VALUES ('c079f92d65e64d3e9f017473c792cdc7', '10a0c6f04a694d0e8eb293e2f314f4e3', '专题:ELK', 'ELK今天星期二，微风，有太阳', '1', '2', '2021-12-28 17:22:57', NULL, NULL, '1');

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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES (1, NULL, '10a0c6f04a694d0e8eb293e2f314f4e3', 'bababababbabababa', '1', '2021-10-20 15:49:46');

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
INSERT INTO `queue_msg` VALUES ('d61e3496aefc461dad013803d701800c', '10a0c6f04a694d0e8eb293e2f314f4e3', '3', '{\"userId\":\"10a0c6f04a694d0e8eb293e2f314f4e3\",\"content\":\"yyds\",\"commentId\":\"ba5e162a9c204c8598f3702894cc0f52\",\"id\":\"d61e3496aefc461dad013803d701800c\"}', 'd61e3496aefc461dad013803d701800c', '2', '2021-12-22 09:46:07', '2021-12-22 09:46:07');
INSERT INTO `queue_msg` VALUES ('d6cf828659c64f2d8cc65b268398aabf', '3fc43448004d4fe2987a194a415fca8c', '1', '{\"userId\":\"3fc43448004d4fe2987a194a415fca8c\",\"url\":\"https://v1.jinrishici.com/all.json\",\"id\":\"d6cf828659c64f2d8cc65b268398aabf\"}', 'd6cf828659c64f2d8cc65b268398aabf', '2', '2021-12-10 13:19:08', '2021-12-10 13:19:09');
INSERT INTO `queue_msg` VALUES ('d78fa1367d95473ba44d652b86184dcc', '10a0c6f04a694d0e8eb293e2f314f4e3', '2', '{\"postId\":\"6bfae343f98d49d3860be4b815b03175\",\"title\":\"专题:Elasticsearch\",\"userId\":\"10a0c6f04a694d0e8eb293e2f314f4e3\",\"content\":\"ELK是三个开源软件的缩写，分别表示：Elasticsearch , 它们都是开源软件。新增了一个FileBeat，它是一个轻量级的日志收集处理工具(Agent)，Filebeat占用资源少，适合于在各个服务器上搜集日志后传输给Logstash，官方也推荐此工具。\",\"id\":\"d78fa1367d95473ba44d652b86184dcc\"}', 'd78fa1367d95473ba44d652b86184dcc', '2', '2021-12-28 16:48:26', '2021-12-28 16:48:26');
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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_exception_log
-- ----------------------------

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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_log
-- ----------------------------

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
INSERT INTO `sys_user` VALUES ('10a0c6f04a694d0e8eb293e2f314f4e3', 'ww', '88c8f4', '027e5861620f902d1b0b8cf15794d67d', NULL, '2', '1', '797AC2E', 'https://api.multiavatar.com/10a0c6f04a694d0e8eb293e2f314f4e3.svg', '我的尾巴', 'Y');
INSERT INTO `sys_user` VALUES ('1fabe517773745f8b07900fbc9fd56da', 'admin', '0bbf34', '8e8cbeef141ef1ba38c391aebb5f487a', '', '0', '1', '', NULL, NULL, NULL);

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


SET FOREIGN_KEY_CHECKS = 1;
