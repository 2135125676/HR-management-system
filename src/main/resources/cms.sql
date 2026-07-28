/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80042 (8.0.42)
 Source Host           : localhost:3306
 Source Schema         : cms

 Target Server Type    : MySQL
 Target Server Version : 80042 (8.0.42)
 File Encoding         : 65001

 Date: 31/12/2025 10:10:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for base_config
-- ----------------------------
DROP TABLE IF EXISTS `base_config`;
CREATE TABLE `base_config`  (
  `config_id` int NOT NULL AUTO_INCREMENT COMMENT '编号',
  `config_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '系统名称',
  `config_info` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '系统信息',
  `config_icon` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '系统图标地址',
  `config_status` int NULL DEFAULT NULL COMMENT '状态：0-禁用，1-启用',
  `deleted` int NOT NULL COMMENT '是否删除：0 未删除 1 已删除',
  PRIMARY KEY (`config_id`) USING BTREE,
  UNIQUE INDEX `name_un`(`config_name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of base_config
-- ----------------------------
INSERT INTO `base_config` VALUES (10, '教师管理系统', '教师信息、课程排课管理', 'https://iconfont.alicdn.com/p/illus_3d/file/Rt3GifeFDVw5/441d3cca-e3e0-488e-9d05-d0b28d624e76.png', 0, 0);
INSERT INTO `base_config` VALUES (11, '课程管理系统', '课程创建、选课管理', 'https://iconfont.alicdn.com/p/illus_3d/file/Rt3GifeFDVw5/f40225fa-a907-45bc-9222-d20c7dcaadbf.png', 0, 0);
INSERT INTO `base_config` VALUES (12, '成绩管理系统', '成绩录入、查询统计', 'https://iconfont.alicdn.com/p/illus_3d/file/Rt3GifeFDVw5/df4ba509-52e2-4dd5-94eb-3174692f77a6.png', 0, 0);
INSERT INTO `base_config` VALUES (13, '旧版考勤系统', '历史考勤数据', 'https://iconfont.alicdn.com/p/illus_3d/file/Rt3GifeFDVw5/a66434b5-93df-4c8b-85ce-b4012e16ebaf.png', 0, 1);
INSERT INTO `base_config` VALUES (36, '学生信息管理系统', '学生档案、学籍状态维护', 'https://iconfont.alicdn.com/p/illus_3d/file/Rt3GifeFDVw5/edc0dba4-a0cf-4359-a76b-46c9a2373512.png', 0, 0);
INSERT INTO `base_config` VALUES (37, '宿舍管理系统', '宿舍分配、卫生检查记录', 'https://iconfont.alicdn.com/p/illus_3d/file/Rt3GifeFDVw5/caadf696-0264-4e97-8584-ea68d0cbb019.png', 0, 0);
INSERT INTO `base_config` VALUES (38, '图书馆借阅系统', '图书借阅、预约与归还管理', 'https://iconfont.alicdn.com/p/illus_3d/file/Rt3GifeFDVw5/b395e325-8bad-4bc5-946f-83335230d8f1.png', 0, 0);
INSERT INTO `base_config` VALUES (39, '校园缴费系统', '学费、住宿费等费用缴纳', 'https://iconfont.alicdn.com/p/illus_3d/file/Rt3GifeFDVw5/8807f5be-6974-4186-8a7f-807e2a3adcf6.png', 0, 0);
INSERT INTO `base_config` VALUES (40, '校园公告系统', '学校通知、活动公告发布', 'https://iconfont.alicdn.com/p/illus_3d/file/Rt3GifeFDVw5/d7e93866-9d37-40bb-92b7-65b278c8baa4.png', 0, 0);
INSERT INTO `base_config` VALUES (41, '实验室管理系统', '实验室预约、设备借用登记', 'https://iconfont.alicdn.com/p/illus_3d/file/ZsWruISgVCKK/0f6c7c46-8fe2-46d8-be99-25f766fe009a.png', 0, 0);
INSERT INTO `base_config` VALUES (42, '新版考勤系统', '人脸识别考勤、打卡统计', 'https://iconfont.alicdn.com/p/illus_3d/file/ZsWruISgVCKK/cb4d087b-5618-4718-b29c-9033664161a8.png', 0, 0);
INSERT INTO `base_config` VALUES (43, '就业服务系统', '招聘信息、简历投递管理', 'https://iconfont.alicdn.com/p/illus_3d/file/ZsWruISgVCKK/00e2a40a-6a52-4c82-bbd2-7391eb3dc15a.png', 0, 0);
INSERT INTO `base_config` VALUES (44, '校友管理系统', '校友信息维护、活动组织', 'https://iconfont.alicdn.com/p/illus_3d/file/ZsWruISgVCKK/42b9f2aa-f236-4b9b-82ed-8d4a455fd474.png', 0, 0);
INSERT INTO `base_config` VALUES (45, '校园报修系统', '设施故障申报、维修进度查询', 'https://iconfont.alicdn.com/p/illus_3d/file/ZsWruISgVCKK/1f1f4873-fd44-4247-92ef-d5f4b6cfc620.png', 0, 0);
INSERT INTO `base_config` VALUES (46, '校园社团活动管理系统', '管理社团活动时长，信息等', 'https://iconfont.alicdn.com/p/illus_3d/file/Rt3GifeFDVw5/be730847-2183-4eeb-b1ef-b3d06aa10c99.png', 1, 0);

-- ----------------------------
-- Table structure for base_log
-- ----------------------------
DROP TABLE IF EXISTS `base_log`;
CREATE TABLE `base_log`  (
  `log_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '编号',
  `log_username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '访问用户账号',
  `log_realname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '访问用户真实姓名',
  `log_request_method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求的方式，get post delete put',
  `log_request_uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求的地址',
  `log_time` datetime NULL DEFAULT NULL COMMENT '请求的时间',
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of base_log
-- ----------------------------
INSERT INTO `base_log` VALUES ('0123b195bbc0a1d840fa555c5d766596', 'admin', '罗123', 'POST', '/login', '2025-12-26 10:34:57');
INSERT INTO `base_log` VALUES ('01c6bb5f1e46946b5175f676e2361a08', 'admin', '罗123', 'POST', '/logout', '2025-12-25 08:53:01');
INSERT INTO `base_log` VALUES ('029f522b608a2efa760fab80f490d708', 'admin', '罗123', 'POST', '/login', '2025-12-25 21:07:58');
INSERT INTO `base_log` VALUES ('0669934d99af21b184a5d96c8147c90b', 'admin', '张三', 'POST', '/login', '2025-12-27 11:18:40');
INSERT INTO `base_log` VALUES ('199d6cf13932aac99f2b87069c1911dd', 'admin', '罗123', 'POST', '/login', '2025-12-25 15:57:25');
INSERT INTO `base_log` VALUES ('1a02dcfccf51ed8b5d5e5ff7045173d6', 'admin', '张三', 'POST', '/login', '2025-12-27 11:18:46');
INSERT INTO `base_log` VALUES ('1a862e684115a815fb827462d8cbf4e6', 'admin', '罗123', 'POST', '/login', '2025-12-25 20:32:16');
INSERT INTO `base_log` VALUES ('26b147773d8d3be54815f63da3a5ce10', 'admin', '罗123', 'POST', '/login', '2025-12-25 15:59:57');
INSERT INTO `base_log` VALUES ('26b25f0110c4f9605b6db7733ca98ab9', 'admin', '罗123', 'POST', '/login', '2025-12-25 20:00:44');
INSERT INTO `base_log` VALUES ('2b0c1fb1bae82883d09b27bd5ecd8ba0', 'admin', '罗123', 'POST', '/login', '2025-12-25 21:04:10');
INSERT INTO `base_log` VALUES ('32a732719d1f8644561b78f6554a5c83', 'admin', '罗123', 'POST', '/login', '2025-12-25 16:54:59');
INSERT INTO `base_log` VALUES ('40c1fe64b49c1624610b98d7a68b84b6', 'admin', '罗123', 'POST', '/login', '2025-12-25 16:19:45');
INSERT INTO `base_log` VALUES ('41062727a49bb9b133d2a61af6c1c173', 'admin', '罗123', 'POST', '/login', '2025-12-25 21:08:47');
INSERT INTO `base_log` VALUES ('415b41f2ad84e86de3e07e6dc53902dc', 'admin', '罗123', 'POST', '/login', '2025-12-25 20:50:24');
INSERT INTO `base_log` VALUES ('49962dc551bee4a86900e20239868240', 'admin', '罗123', 'POST', '/login', '2025-12-26 10:41:43');
INSERT INTO `base_log` VALUES ('4dea924d1b11304e752208f05c6d6c9d', 'admin', '罗123', 'POST', '/login', '2025-12-25 21:10:21');
INSERT INTO `base_log` VALUES ('4e924469b09053bb9ead597dc30f34e7', 'admin', '罗123', 'POST', '/login', '2025-12-25 20:02:39');
INSERT INTO `base_log` VALUES ('58b40e37aff0cd63a7814ff9c2e1dc11', 'admin', '罗123', 'POST', '/login', '2025-12-25 21:07:06');
INSERT INTO `base_log` VALUES ('5a3678028299b0f61d9613fe561e5551', 'admin', '张三', 'POST', '/login', '2025-12-27 11:29:46');
INSERT INTO `base_log` VALUES ('615f364954a0a197fb2368e1bc8864b1', 'admin', '张三', 'POST', '/login', '2025-12-27 11:18:46');
INSERT INTO `base_log` VALUES ('65ad7fda2c24d96312a67a034184f5ea', 'admin', '罗123', 'POST', '/login', '2025-12-26 10:24:35');
INSERT INTO `base_log` VALUES ('66d0149da52a0bc8e49d95d83c9c57ba', 'admin', '罗123', 'POST', '/login', '2025-12-25 16:33:04');
INSERT INTO `base_log` VALUES ('6d18cbfb2ec16207777ee9f579332ace', 'admin', '罗123', 'POST', '/login', '2025-12-25 17:57:16');
INSERT INTO `base_log` VALUES ('718835abea04486e3585bdb247d7fcb8', 'admin', '罗123', 'POST', '/login', '2025-12-25 17:26:03');
INSERT INTO `base_log` VALUES ('725b889a17f47611acb90385e07fd515', 'admin', '罗123', 'POST', '/login', '2025-12-25 17:16:00');
INSERT INTO `base_log` VALUES ('7475451fd74e27d3b06fbe2d7ef692b7', 'admin', '罗123', 'POST', '/login', '2025-12-25 20:45:03');
INSERT INTO `base_log` VALUES ('77863f683ed4bb1b7b4501bd338d326c', 'admin', '罗123', 'POST', '/login', '2025-12-26 14:51:38');
INSERT INTO `base_log` VALUES ('79377d5ac093ae38b156357c2ecbd35b', 'user', 'asdasd', 'POST', '/login', '2025-12-27 11:20:54');
INSERT INTO `base_log` VALUES ('80dbfb50839838864e11eb60a0ce5d18', 'admin', '张三', 'POST', '/login', '2025-12-27 15:01:29');
INSERT INTO `base_log` VALUES ('89cbfa47fdd44ab89f432ba60b54888f', 'admin', '罗123', 'POST', '/login', '2025-12-26 10:27:43');
INSERT INTO `base_log` VALUES ('8ae72d339d58e9d71485b5326751f93f', 'admin', '罗123', 'POST', '/login', '2025-12-26 10:43:58');
INSERT INTO `base_log` VALUES ('9cd6f93283769347a1b736ca6e889aef', 'admin', '罗123', 'POST', '/login', '2025-12-25 17:05:28');
INSERT INTO `base_log` VALUES ('ab89c056e552fe2290140ad3656a1ac7', 'admin', '罗123', 'POST', '/login', '2025-12-25 19:57:18');
INSERT INTO `base_log` VALUES ('afef99a1e4677b2d4fca209fb4b968f4', 'user', 'asdasd', 'POST', '/login', '2025-12-27 11:20:59');
INSERT INTO `base_log` VALUES ('b05195bf7adbc9181621a476926a00a0', 'admin', '罗123', 'POST', '/login', '2025-12-25 16:02:59');
INSERT INTO `base_log` VALUES ('b25a54661851e2894e77a6a53abb9087', 'admin', '张三', 'POST', '/login', '2025-12-29 14:35:34');
INSERT INTO `base_log` VALUES ('b2efd8bc5ec45d3a165606382bf0deb5', 'admin', '罗123', 'POST', '/login', '2025-12-25 15:56:47');
INSERT INTO `base_log` VALUES ('b80cb25e72ce9b5e8b4a788a4c6a04d7', 'admin', '罗123', 'POST', '/login', '2025-12-25 20:48:39');
INSERT INTO `base_log` VALUES ('b873a9c2c8297d8c31abbb20e42f2e98', 'admin', '罗123', 'POST', '/login', '2025-12-25 20:55:24');
INSERT INTO `base_log` VALUES ('bee5754388eeb23742c5469e1da3552e', 'admin', '罗123', 'POST', '/login', '2025-12-25 15:57:59');
INSERT INTO `base_log` VALUES ('c2974e2e71d329b6aa6e7071d602e353', 'admin', '罗123', 'POST', '/login', '2025-12-25 17:20:07');
INSERT INTO `base_log` VALUES ('c4c48c54df532a3f702e629ea8664d55', 'admin', '罗123', 'POST', '/login', '2025-12-27 08:47:16');
INSERT INTO `base_log` VALUES ('c554e7789ec127536727c9de95c0e1e7', 'admin', '罗123', 'POST', '/login', '2025-12-26 10:39:52');
INSERT INTO `base_log` VALUES ('c804cb2442a7a2dd2660c53b9bc0bd16', 'admin', '罗123', 'POST', '/login', '2025-12-25 18:00:34');
INSERT INTO `base_log` VALUES ('c902e93e483017927a4d3d65b06585fd', 'admin', '罗123', 'POST', '/login', '2025-12-26 15:03:34');
INSERT INTO `base_log` VALUES ('cd1c81205b315ef7c36e84aa4d88a17e', 'admin', '罗123', 'POST', '/login', '2025-12-26 08:03:31');
INSERT INTO `base_log` VALUES ('cefaed6cb162944ff6ec0567dc095bf1', 'admin', '张三', 'POST', '/login', '2025-12-29 17:24:45');
INSERT INTO `base_log` VALUES ('dbee5e5269e39441ae89e9488ee68a2a', 'admin', '罗123', 'POST', '/login', '2025-12-25 21:00:36');
INSERT INTO `base_log` VALUES ('dd8b88fd3981cca5833786e3dd11112c', 'admin', '张三', 'POST', '/login', '2025-12-27 15:00:00');
INSERT INTO `base_log` VALUES ('e0e94e962b02b1203dc9f1dbc737045c', 'admin', '罗123', 'POST', '/login', '2025-12-26 10:26:58');
INSERT INTO `base_log` VALUES ('e501915239b0d0c0ac89a395c19e8918', 'admin', '罗123', 'POST', '/login', '2025-12-25 17:08:10');
INSERT INTO `base_log` VALUES ('e80ff209d5d27a8276e904e199afa8cb', 'admin', '罗123', 'POST', '/login', '2025-12-25 16:21:03');
INSERT INTO `base_log` VALUES ('eb68139e3ea2ddf28ce5aebe297632be', 'admin', '罗123', 'POST', '/login', '2025-12-26 10:41:21');
INSERT INTO `base_log` VALUES ('f6a98bbecbfd72223bc5dc93bf8a3337', 'admin', '张三', 'POST', '/login', '2025-12-27 11:54:08');
INSERT INTO `base_log` VALUES ('f836e2eae80c3ef79351f52f036590b2', 'admin', '罗123', 'POST', '/login', '2025-12-25 16:09:14');
INSERT INTO `base_log` VALUES ('fce189ab6162d88b4872100622caa84f', 'admin', '罗123', 'POST', '/login', '2025-12-26 10:49:07');
INSERT INTO `base_log` VALUES ('fe1ee40fbe4abe697fdd39d588048e9c', 'admin', '罗123', 'POST', '/login', '2025-12-25 16:58:19');

-- ----------------------------
-- Table structure for base_user
-- ----------------------------
DROP TABLE IF EXISTS `base_user`;
CREATE TABLE `base_user`  (
  `id` int NOT NULL COMMENT '编号',
  `username` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '密码',
  `telephone` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '电话',
  `email` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '邮箱',
  `realname` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户真实姓名',
  `icon` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '头像地址',
  `gender` int NULL DEFAULT NULL COMMENT '性别',
  `dob` date NULL DEFAULT NULL COMMENT '生日',
  `register_time` datetime NOT NULL COMMENT '注册时间',
  `status` int NOT NULL COMMENT '账户状态：0-正常，1-禁用',
  `role` int NOT NULL COMMENT '角色：0-管理员，1-普通用户',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `telephone`(`telephone` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of base_user
-- ----------------------------
INSERT INTO `base_user` VALUES (1, 'user', 'dc2ada59b357d6ae7d1a809e4c8ce7bf', '19324567734', 'user@qq.com', '李四', 'https://ts1.tc.mm.bing.net/th/id/OIP-C.PyIpUUVKzLFI1KSmQtY50wHaGk?w=220&h=211&c=8&rs=1&qlt=90&o=6&dpr=2&pid=3.1&rm=2', 1, '2025-12-27', '2025-12-28 11:52:21', 1, 0);
INSERT INTO `base_user` VALUES (2, 'tom', 'dc2ada59b357d6ae7d1a809e4c8ce7bf', '18893132246', 'tom@qq.com', '汤姆', 'https://tse4-mm.cn.bing.net/th/id/OIP-C.Q6ZlKjw0USQG_saGGsLE8QAAAA?w=224&h=220&c=7&r=0&o=7&dpr=2&pid=1.7&rm=3', 0, '2025-02-13', '2025-12-30 11:34:52', 0, 1);
INSERT INTO `base_user` VALUES (3, 'marry', 'dc2ada59b357d6ae7d1a809e4c8ce7bf', '13345672345', 'marry@qq.com', '马丽', 'https://tse3-mm.cn.bing.net/th/id/OIP-C.prP-j0ZxewtwKq43DQUW-gHaHa?w=228&h=220&c=7&r=0&o=7&dpr=2&pid=1.7&rm=3', 0, '2025-04-16', '2025-12-30 11:36:50', 0, 1);
INSERT INTO `base_user` VALUES (10, 'admin', 'dc2ada59b357d6ae7d1a809e4c8ce7bf', '13700001111', 'jsck@briup.com', '张三', 'https://inews.gtimg.com/om_bt/OLAdLXMIwnQe9UpFNyZtIK8_0tir6H93F2aXrxIqpnZ-UAA/641', 1, '2000-10-15', '2025-12-22 17:53:40', 0, 0);

-- ----------------------------
-- Table structure for cms_activity
-- ----------------------------
DROP TABLE IF EXISTS `cms_activity`;
CREATE TABLE `cms_activity`  (
  `activity_id` int NOT NULL,
  `activity_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `activity_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `activity_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `activity_status` bigint NULL DEFAULT NULL,
  `activity_startTime` datetime NULL DEFAULT NULL,
  `deleted` bigint NULL DEFAULT 0,
  `activity_createTime` datetime NULL DEFAULT NULL,
  `activity_publisher` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `activity_participants` int NULL DEFAULT 0,
  PRIMARY KEY (`activity_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of cms_activity
-- ----------------------------
INSERT INTO `cms_activity` VALUES (1, '思想成长', '元旦晚会', '炖鸭楼B101', 0, '2025-12-30 11:48:04', 0, '2025-12-26 11:48:33', 'admin', 150);
INSERT INTO `cms_activity` VALUES (2, '思想成长', '社团思想引领座谈会', '焜鹏馆B202', 0, '2025-12-31 14:00:00', 0, '2025-12-27 10:30:15', 'admin', 35);
INSERT INTO `cms_activity` VALUES (3, '文体活动', '文艺社元旦文艺汇演', '学校大礼堂', 0, '2026-01-01 19:00:00', 0, '2025-12-27 14:20:00', 'user', 20);
INSERT INTO `cms_activity` VALUES (4, '学术交流', '辩论社校际辩论赛筹备会', '活动室301', 0, '2025-12-31 09:30:00', 0, '2025-12-28 09:15:30', 'user', 45);
INSERT INTO `cms_activity` VALUES (5, '志愿公益', '志愿者社团社区敬老服务', '阳光社区活动中心', 0, '2026-01-02 08:30:00', 0, '2025-12-28 11:20:00', 'admin', 10);
INSERT INTO `cms_activity` VALUES (6, '兴趣拓展', '摄影社校园冬日采风活动', '学校银杏大道', 0, '2025-12-31 10:00:00', 0, '2025-12-29 10:00:00', '摄影社社长', 26);
INSERT INTO `cms_activity` VALUES (7, '思想成长', '红色主题社团观影分享会', '焜鹏馆B305', 0, '2026-01-03 16:00:00', 0, '2025-12-29 15:40:00', 'admin', 75);
INSERT INTO `cms_activity` VALUES (8, '文体活动', '篮球社迎新友谊赛', '学校室外篮球场', 0, '2026-01-04 14:30:00', 0, '2025-12-30 08:50:00', '篮球社部长', 85);
INSERT INTO `cms_activity` VALUES (9, '学术交流', '文学社读书心得分享会', '图书馆阅览室202', 0, '2026-01-05 15:00:00', 0, '2025-12-30 13:10:00', '文学社社长', 55);
INSERT INTO `cms_activity` VALUES (10, '志愿公益', '环保社校园垃圾分类宣传', '学校一食堂门口', 0, '2026-01-06 11:00:00', 0, '2025-12-30 16:20:00', '环保社负责人', 30);

-- ----------------------------
-- Table structure for cms_article
-- ----------------------------
DROP TABLE IF EXISTS `cms_article`;
CREATE TABLE `cms_article`  (
  `article_id` int NOT NULL AUTO_INCREMENT,
  `article_title` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '标题',
  `article_content` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '内容',
  `article_publish_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '发布时间',
  `article_read_times` int NULL DEFAULT 0 COMMENT '阅读次数',
  `article_status` int NULL DEFAULT 0 COMMENT '状态：0-待审核，1-不通过，2-通过，3-推荐',
  `article_thump_up` int NULL DEFAULT 0 COMMENT '点赞数量',
  `article_cover` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '封面图片地址',
  `article_user_id` int NULL DEFAULT NULL COMMENT '所属用户id',
  `article_category_id` int NULL DEFAULT NULL COMMENT '所属栏目id',
  `deleted` int NOT NULL COMMENT '是否删除：0 未删除 1 已删除',
  PRIMARY KEY (`article_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of cms_article
-- ----------------------------
INSERT INTO `cms_article` VALUES (18, '【紧急通知】环保社植树节活动集合时间变更', '原定于3月12日8:30的植树节活动，集合时间调整为9:00，地点不变（学校西门），请社员相互转告~', '2025-12-30 21:18:51', 132, 2, 15, 'https://ts1.tc.mm.bing.net/th/id/OIP-C.GCBA_ySdlMkmSnRRG8yfkQHaHa?w=194&h=211&c=8&rs=1&qlt=90&o=6&dpr=2&pid=3.1&rm=2', 3, 2, 0);
INSERT INTO `cms_article` VALUES (19, '社团招新转化率提升50%的3个技巧', '分享社团招新的宣传渠道选择（朋友圈+校园墙+线下体验）、话术设计、福利吸引等实操方法...', '2025-12-30 21:15:40', 207, 2, 48, 'https://ts1.tc.mm.bing.net/th/id/OIP-C.bRXmxSNrIGE-W2U7GcOwfgHaHa?w=199&h=211&c=8&rs=1&qlt=90&o=6&dpr=2&pid=3.1&rm=2', 5, 3, 0);
INSERT INTO `cms_article` VALUES (20, '滑板社社长：从“摔出来”的社团文化', '滑板社社长王宇分享：社团从5人到30人的成长，如何通过“以滑会友”打造凝聚力...', '2026-03-10 16:00:00', 185, 2, 33, 'https://ts1.tc.mm.bing.net/th/id/OIP-C.bN2ahTSmay-E77mloYsDNAHaHa?w=199&h=211&c=8&rs=1&qlt=90&o=6&dpr=2&pid=3.1&rm=2', 2, 4, 0);
INSERT INTO `cms_article` VALUES (21, '剪映10分钟做社团活动宣传短视频', '教程：导入素材→添加转场→配BGM→加字幕→导出，附社团活动常用BGM清单...', '2026-03-08 14:00:00', 241, 3, 56, 'https://ts1.tc.mm.bing.net/th/id/OIP-C.OUl30RWraGqVwGTIltRZ6wAAAA?w=182&h=211&c=8&rs=1&qlt=90&o=6&dpr=2&pid=3.1&rm=2', 4, 7, 0);
INSERT INTO `cms_article` VALUES (22, '社团路演不紧张：3个临场救场小技巧', '分享路演忘词、设备故障、观众冷场时的应对方法，附1分钟即兴演讲模板...', '2026-03-07 10:30:00', 169, 2, 27, 'https://ts1.tc.mm.bing.net/th/id/OIP-C.wXVybNq4waU8463cPb55mwHaEJ?w=250&h=211&c=8&rs=1&qlt=90&o=6&dpr=2&pid=3.1&rm=2', 1, 8, 0);
INSERT INTO `cms_article` VALUES (23, '社团公益活动必备：免费物料&场地资源', '整理校园周边可免费申请的公益活动场地、宣传物料赞助渠道（如文具店、打印店）...', '2026-03-06 11:00:00', 153, 3, 31, 'https://ts1.tc.mm.bing.net/th/id/OIP-C.REWPdEPBfQpUc4HkOEqnSQHaE8?w=237&h=211&c=8&rs=1&qlt=90&o=6&dpr=2&pid=3.1&rm=2', 3, 11, 0);
INSERT INTO `cms_article` VALUES (24, '文学社成员诗歌作品：《社团里的青春》', '收录文学社5位成员的原创诗歌，以社团生活为主题，字里行间满是青春气息...', '2026-03-12 19:00:00', 147, 2, 22, 'https://ts1.tc.mm.bing.net/th/id/OIP-C.wlljzDiOH97LlFPHD-4VpgHaHa?w=199&h=211&c=8&rs=1&qlt=90&o=6&dpr=2&pid=3.1&rm=2', 5, 14, 0);
INSERT INTO `cms_article` VALUES (25, '动漫社“春日漫展”门票开始预约', '4月1日动漫社将举办校园漫展，含cosplay走秀、同人作品展示，预约链接：xxx...', '2026-03-11 14:00:00', 268, 2, 63, 'https://ts1.tc.mm.bing.net/th/id/OIP-C.c-DWFLg-ZY0knz5fOqLhKgHaE8?w=233&h=211&c=8&rs=1&qlt=90&o=6&dpr=2&pid=3.1&rm=2', 2, 2, 0);
INSERT INTO `cms_article` VALUES (26, '用Word做社团活动签到表，这2个功能太好用', '讲解Word的“表格自动求和”“复选框控件”功能，快速统计签到人数...', '2026-03-05 09:30:00', 178, 3, 35, 'https://ts1.tc.mm.bing.net/th/id/OIP-C.KDUI9NRD9PQaR4Jy4evCQgHaHa?w=217&h=211&c=8&rs=1&qlt=90&o=6&dpr=2&pid=3.1&rm=2', 4, 6, 0);
INSERT INTO `cms_article` VALUES (27, '话剧社《青春之歌》公演：台下的掌声与泪水', '回顾话剧社3个月排练的《青春之歌》公演现场，演员幕后故事+观众反馈...', '2026-03-04 20:00:00', 215, 2, 42, 'https://ts1.tc.mm.bing.net/th/id/OIP-C.uHHM2IjlFSFSlJiEh8TSYgHaHa?w=187&h=211&c=8&rs=1&qlt=90&o=6&dpr=2&pid=3.1&rm=2', 1, 13, 0);
INSERT INTO `cms_article` VALUES (28, '体育活动，乒乓球有益记忆', '<p>爱上邓丽君</p>', '2025-12-30 21:17:53', 0, 0, 0, 'https://inews.gtimg.com/om_bt/OLAdLXMIwnQe9UpFNyZtIK8_0tir6H93F2aXrxIqpnZ-UAA/641', 10, 1, 1);
INSERT INTO `cms_article` VALUES (29, '体育赛事的背后....', '<p>每个运动员的努力....</p>', '2025-12-30 22:38:23', 0, 0, 0, 'http://t80rtluyp.hn-bkt.clouddn.com/ee93fa2e-9672-4c95-a4d7-63d33d4c5af0.png', 10, 4, 1);
INSERT INTO `cms_article` VALUES (30, '体育的运动员....', '<p>每个运动员背后....</p>', '2025-12-30 22:40:26', 0, 0, 0, NULL, 10, 3, 1);

-- ----------------------------
-- Table structure for cms_carousel
-- ----------------------------
DROP TABLE IF EXISTS `cms_carousel`;
CREATE TABLE `cms_carousel`  (
  `carousel_id` int NOT NULL AUTO_INCREMENT COMMENT '编号',
  `carousel_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '轮播图名称',
  `carousel_info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '轮播图信息',
  `carousel_url` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '轮播图地址',
  `carousel_status` int NULL DEFAULT NULL COMMENT '状态：0-禁用，1-启用',
  PRIMARY KEY (`carousel_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of cms_carousel
-- ----------------------------
INSERT INTO `cms_carousel` VALUES (1, '社团活动图', '这是一个社团活动图', 'https://img.tukuppt.com/ad_preview/01/91/20/642648f705531.jpg!/fw/780', 1);
INSERT INTO `cms_carousel` VALUES (3, '社团招新图', '这是一个社团招新图', 'https://img.tukuppt.com/png_preview/02/91/13/tDdiaS89kQ.jpg!/fw/780', 1);
INSERT INTO `cms_carousel` VALUES (4, '团结友爱图', '这是一个团结友爱图', 'https://img.tukuppt.com/ad_preview/00/21/31/5f45f0126d4b7.jpg!/fw/780', 1);
INSERT INTO `cms_carousel` VALUES (5, '青春活力图', '这是一个青春活力图', 'https://img.tukuppt.com/bg_grid/01/81/70/J5DowiSxz4.jpg!/fh/350', 1);

-- ----------------------------
-- Table structure for cms_category
-- ----------------------------
DROP TABLE IF EXISTS `cms_category`;
CREATE TABLE `cms_category`  (
  `category_id` int NOT NULL AUTO_INCREMENT COMMENT '编号',
  `category_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '分类名称',
  `category_description` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '栏目描述',
  `category_order` int NULL DEFAULT NULL COMMENT '栏目序号',
  `category_parent_id` int NULL DEFAULT NULL COMMENT '栏目所属的父栏目',
  `deleted` int NULL DEFAULT NULL COMMENT '是否删除：0 未删除 1 已删除',
  PRIMARY KEY (`category_id`) USING BTREE,
  INDEX `fk_category_category`(`category_parent_id` ASC) USING BTREE,
  CONSTRAINT `cms_category_ibfk_1` FOREIGN KEY (`category_parent_id`) REFERENCES `cms_category` (`category_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of cms_category
-- ----------------------------
INSERT INTO `cms_category` VALUES (1, '社团专区', '校园各社团相关的内容聚合', 1, NULL, 0);
INSERT INTO `cms_category` VALUES (2, '社团活动资讯', '社团活动的预告、通知、实时动态', 2, 1, 0);
INSERT INTO `cms_category` VALUES (3, '社团经验库', '社团运营、活动策划的经验分享', 3, 1, 0);
INSERT INTO `cms_category` VALUES (4, '社团人物志', '优秀社团成员、干部的专访内容', 4, 1, 0);
INSERT INTO `cms_category` VALUES (5, '校园技能', '学生实用技能相关的文章集合', 5, NULL, 0);
INSERT INTO `cms_category` VALUES (6, '办公技能教程', 'Excel、PPT等办公软件的干货教程', 6, 5, 0);
INSERT INTO `cms_category` VALUES (7, '创意设计干货', 'PS、剪映等设计/剪辑工具的使用技巧', 7, 5, 0);
INSERT INTO `cms_category` VALUES (8, '演讲表达指南', '社团路演、汇报的表达技巧类文章', 8, 5, 0);
INSERT INTO `cms_category` VALUES (9, '校园公益', '志愿、公益类活动及文章专区', 9, NULL, 0);
INSERT INTO `cms_category` VALUES (10, '志愿活动纪实', '各类志愿服务的图文回顾', 10, 9, 1);
INSERT INTO `cms_category` VALUES (11, '公益资源汇总', '公益活动所需的物料、渠道资分类', 11, 9, 0);
INSERT INTO `cms_category` VALUES (12, '校园文创', '文艺、创新类内容专区', 12, NULL, 0);
INSERT INTO `cms_category` VALUES (13, '文艺活动回顾', '音乐会、展览等文艺活动的总结', 13, 12, 0);
INSERT INTO `cms_category` VALUES (14, '学生创作展示', '社团成员的文学、艺术创作作品', 14, 12, 0);
INSERT INTO `cms_category` VALUES (15, '美食品鉴活动', '邀请各个同学前来品鉴美食....', 16, 5, 0);

-- ----------------------------
-- Table structure for cms_comment
-- ----------------------------
DROP TABLE IF EXISTS `cms_comment`;
CREATE TABLE `cms_comment`  (
  `comment_id` int NOT NULL AUTO_INCREMENT COMMENT '编号',
  `comment_content` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '评论内容',
  `comment_time` datetime NULL DEFAULT NULL COMMENT '评论时间',
  `comment_status` int NULL DEFAULT NULL COMMENT '状态：0-待审核，1-不通过，2-通过',
  `comment_user_id` int NULL DEFAULT NULL COMMENT '所属用户id',
  `comment_article_id` int NULL DEFAULT NULL COMMENT '所属文章id',
  `comment_parent_id` int NULL DEFAULT NULL COMMENT '回复的评论父id',
  `deleted` int NULL DEFAULT NULL COMMENT '是否删除：0 未删除 1 已删除',
  PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of cms_comment
-- ----------------------------
INSERT INTO `cms_comment` VALUES (1, '这个活动真不错', '2025-12-26 16:47:25', 2, 10, 1, NULL, 0);
INSERT INTO `cms_comment` VALUES (2, '想去参加', '2025-12-28 11:45:03', 1, 10, 1, NULL, 0);
INSERT INTO `cms_comment` VALUES (3, '有没有一起的啊', '2025-12-28 10:59:28', 1, 10, 1, 2, 0);
INSERT INTO `cms_comment` VALUES (4, '我去，之前怎么没发现这个好有道理啊', '2025-12-30 14:43:13', 2, 2, 3, NULL, 0);
INSERT INTO `cms_comment` VALUES (5, '天呐，感觉有点了解这个世界了', '2025-12-30 14:45:06', 2, 3, 3, 4, 0);
INSERT INTO `cms_comment` VALUES (6, '有没有参加过的，怎么个事', '2025-12-28 14:48:06', 2, 3, 4, NULL, 0);
INSERT INTO `cms_comment` VALUES (7, '楼上，真心推荐', '2025-12-29 14:49:08', 2, 1, 4, 6, 0);
INSERT INTO `cms_comment` VALUES (8, '那我要去参加', '2025-12-29 14:50:05', 2, 3, 4, 7, 0);
INSERT INTO `cms_comment` VALUES (9, '这个话剧太有意义了', '2025-12-27 14:51:54', 1, 2, 8, NULL, 0);
INSERT INTO `cms_comment` VALUES (10, '什么时候才可以开放啊，想去看', '2025-12-28 14:53:04', 1, 1, 8, NULL, 0);

SET FOREIGN_KEY_CHECKS = 1;
