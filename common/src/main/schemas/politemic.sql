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

 Date: 13/12/2021 17:50:47
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
  `from_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '通知方 不填为系统通知 有值则为私信(对应用户id) ',
  `to_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接收方',
  `content` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '通知内容',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '通知状态 0-未读 1-已读 2-删除',
  `time` datetime(0) NULL DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES (1, NULL, '10a0c6f04a694d0e8eb293e2f314f4e3', 'bababababbabababa', '0', '2021-10-20 15:49:46');

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
INSERT INTO `schedule_task` VALUES (3, 'schedule_task_audit_failure_post', '0 20 2 ? * *', 'Y');

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
INSERT INTO `sys_user` VALUES ('10a0c6f04a694d0e8eb293e2f314f4e3', 'ww', '88c8f4', '027e5861620f902d1b0b8cf15794d67d', '161078369@qq.com', '2', '1', '797AC2E', 'https://api.multiavatar.com/10a0c6f04a694d0e8eb293e2f314f4e3.svg', '{\n  \"content\" : \"洛阳城里春光好，洛阳才子他乡老。\",\n  \"origin\" : \"菩萨蛮\",\n  \"author\" : \"韦庄\",\n  \"category\" : \"古诗文-人生-青春\"\n}', 'N');
INSERT INTO `sys_user` VALUES ('1fabe517773745f8b07900fbc9fd56da', 'admin', '0bbf34', '8e8cbeef141ef1ba38c391aebb5f487a', '', '0', '1', '', NULL, NULL, NULL);
INSERT INTO `sys_user` VALUES ('f756fa0f857c48e187bc23cbf0034840', 'qq', 'cc1518', 'b0ba8b6e80f388468c53e0696047d27f', '', '2', '0', '5B525B9', 'https://api.multiavatar.com/f756fa0f857c48e187bc23cbf0034840.svg', NULL, 'N');

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
INSERT INTO `user_token` VALUES ('10a0c6f04a694d0e8eb293e2f314f4e3', '126cf40c7e5843d699c8be9ec111f6f2', '2021-12-16 17:17:36');
INSERT INTO `user_token` VALUES ('1fabe517773745f8b07900fbc9fd56da', '0f2cd1a1e4354b4b8403c672f0b60245', '2021-12-12 17:03:01');
INSERT INTO `user_token` VALUES ('3683c7a5914945369044ed54bb10b475', 'e09724f5ea094f4f9f9626a634a3c973', '2021-12-13 13:16:57');
INSERT INTO `user_token` VALUES ('cf9cb27f79bb49fa9b334bbec0c3f7a7', '5f4d084d55a74e74b8aeb30eed484685', '2021-10-19 12:50:49');

SET FOREIGN_KEY_CHECKS = 1;
