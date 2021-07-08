SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE `t_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
);

-- ----------------------------
-- Records of t_permission
-- ----------------------------
INSERT INTO `t_permission` VALUES (1, '首页', 'index', '/', NULL);
INSERT INTO `t_permission` VALUES (2, '产品管理', 'product_manager', '/products', NULL);
INSERT INTO `t_permission` VALUES (3, '订单管理', 'order_manager', '/orders', NULL);
INSERT INTO `t_permission` VALUES (4, '用户管理', 'user_manager', '/sys/users', NULL);
INSERT INTO `t_permission` VALUES (5, '日志管理', 'log_manager', '/sys/logs', NULL);

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
);

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES (1, '系统管理员', 'ROLE_ADMIN');
INSERT INTO `t_role` VALUES (2, '普通用户', 'ROLE_USER');
INSERT INTO `t_role` VALUES (3, '运营人员', 'ROLE_OPERATOR');

-- ----------------------------
-- Table structure for t_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_role_permission`;
CREATE TABLE `t_role_permission`  (
  `role_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  PRIMARY KEY (`role_id`, `permission_id`) USING BTREE
);

-- ----------------------------
-- Records of t_role_permission
-- ----------------------------
INSERT INTO `t_role_permission` VALUES (1, 1);
INSERT INTO `t_role_permission` VALUES (1, 2);
INSERT INTO `t_role_permission` VALUES (1, 3);
INSERT INTO `t_role_permission` VALUES (1, 4);
INSERT INTO `t_role_permission` VALUES (1, 5);
INSERT INTO `t_role_permission` VALUES (2, 1);
INSERT INTO `t_role_permission` VALUES (2, 2);
INSERT INTO `t_role_permission` VALUES (2, 3);
INSERT INTO `t_role_permission` VALUES (3, 1);

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `state` int(1) DEFAULT 0,
  `account_expired` int(1) DEFAULT 0,
  `password_expired` int(1) DEFAULT 0,
  `account_locked` int(1) DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
);

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, 'admin', '$2a$10$/EW4fX9gO9s9MmgDtPZMtePyHLOXSfyaDK3bf7t/lmtZtIoAb/Jea', 1, 0, 0, 0);
INSERT INTO `t_user` VALUES (2, 'John', '$2a$10$r6mUBJETz904kr7UOTanbeo93WCa49VO5QukwT6wCLeZxKvWhr7X2', 1, 0, 0, 0);
INSERT INTO `t_user` VALUES (3, 'Alice', '$2a$10$MxBdy53FSHB34obOOOY7k.IXVMoWvzCPhWba3TzkjX8sr0bebP96G', 1, 0, 0, 0);

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role`  (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
);

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES (1, 1);
INSERT INTO `t_user_role` VALUES (2, 2);
INSERT INTO `t_user_role` VALUES (3, 3);

SET FOREIGN_KEY_CHECKS = 1;
