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

 Date: 09/12/2021 09:10:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '帖子状态\r\n 1-发表后待审核\r\n 2-正常\r\n 3-精华帖\r\n 4-管理删除、审核未通过的拉黑帖',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '帖子创建时间',
  `star_count` int(0) NULL DEFAULT NULL COMMENT '帖子点赞数',
  `comment_count` int(0) NULL DEFAULT NULL COMMENT '帖子评论数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of discuss_posts
-- ----------------------------
INSERT INTO `discuss_posts` VALUES ('957d3ee16c4c4b8fb8ee30f28e44c71e', '3683c7a5914945369044ed54bb10b475', 'abab', 'palapo', '1', '2', '2021-10-20 15:17:58', NULL, NULL);
INSERT INTO `discuss_posts` VALUES ('9933eaa419734f3ba9aa35dc60f6b236', '3683c7a5914945369044ed54bb10b475', '啦啦啦啦啦', 'kakakakakakakakakak', '1', '2', '2021-10-20 13:52:08', NULL, NULL);

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '通知id',
  `from_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '通知方',
  `to_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接收方',
  `content` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '通知内容',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '通知状态 0-未读 1-已读 2-删除',
  `time` datetime(0) NULL DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES (1, NULL, NULL, 'bababababbabababa', NULL, '2021-10-20 15:49:46');

-- ----------------------------
-- Table structure for queue_msg
-- ----------------------------
DROP TABLE IF EXISTS `queue_msg`;
CREATE TABLE `queue_msg`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '消息id',
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '消息对应的用户id',
  `type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '消息类型\r\n 0:注册邮件消息\r\n 1:获取评论尾巴消息\r\n 2-帖子审核消息',
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
INSERT INTO `queue_msg` VALUES ('571e5b49b9424f5dbc2044f50ea04f30', '3683c7a5914945369044ed54bb10b475', '2', '{\"id\":\"571e5b49b9424f5dbc2044f50ea04f30\",\"postId\":\"9933eaa419734f3ba9aa35dc60f6b236\",\"title\":\"啦啦啦啦啦\",\"content\":\"kakakakakakakakakak\"}', '571e5b49b9424f5dbc2044f50ea04f30', '2', '2021-10-20 13:52:08', '2021-10-20 13:52:09');
INSERT INTO `queue_msg` VALUES ('60a3529f5d894917bd5cfa81e5c6b349', '3683c7a5914945369044ed54bb10b475', '2', '{\"id\":\"60a3529f5d894917bd5cfa81e5c6b349\",\"postId\":\"957d3ee16c4c4b8fb8ee30f28e44c71e\",\"title\":\"abab\",\"content\":\"palapo\"}', '60a3529f5d894917bd5cfa81e5c6b349', '2', '2021-10-20 15:17:59', '2021-10-20 15:17:59');

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
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of schedule_task
-- ----------------------------
INSERT INTO `schedule_task` VALUES (1, 'schedule_task_send_failure_email', '0 0 2 ? * *', 'Y');
INSERT INTO `schedule_task` VALUES (2, 'schedule_task_take_tail', '0 10 2 ? * *', 'Y');

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
INSERT INTO `sys_config` VALUES ('name', 'val', 'Y', 'bz');
INSERT INTO `sys_config` VALUES ('schedule_task_send_failure_email', 'schedule_task_send_failure_email', 'Y', '定时发送邮件配置');
INSERT INTO `sys_config` VALUES ('schedule_task_take_tail', 'schedule_task_take_tail', 'Y', '定时发送失败尾巴请求');

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
INSERT INTO `sys_user` VALUES ('1fabe517773745f8b07900fbc9fd56da', 'admin', '0bbf34', '8e8cbeef141ef1ba38c391aebb5f487a', '', '0', '1', '', NULL, NULL, NULL);
INSERT INTO `sys_user` VALUES ('3683c7a5914945369044ed54bb10b475', 'qq', '30e58d', '1a02244cd33b2e241c1c7fa9486faa2b', '161078369@qq.com', '2', '1', '923063D', 'https://api.multiavatar.com/3683c7a5914945369044ed54bb10b475.svg', '{  \"content\" : \"纸上得来终觉浅，绝知此事要躬行。\",  \"origin\" : \"冬夜读书示子聿\",  \"author\" : \"陆游\",  \"category\" : \"古诗文-人生-读书\"}', 'N');

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
INSERT INTO `user_token` VALUES ('3683c7a5914945369044ed54bb10b475', '057bcbf450fc4263a847fd8f2180f71a', '2021-10-23 15:16:13');
INSERT INTO `user_token` VALUES ('cf9cb27f79bb49fa9b334bbec0c3f7a7', '5f4d084d55a74e74b8aeb30eed484685', '2021-10-19 12:50:49');

SET FOREIGN_KEY_CHECKS = 1;
