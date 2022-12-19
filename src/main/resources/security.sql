/*
 Navicat Premium Data Transfer

 Source Server         : test
 Source Server Type    : MySQL
 Source Server Version : 50736 (5.7.36)
 Source Host           : localhost:3306
 Source Schema         : security

 Target Server Type    : MySQL
 Target Server Version : 50736 (5.7.36)
 File Encoding         : 65001

 Date: 19/12/2022 13:58:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name_zh` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('15d1322b50bb4a9392fc9c851ea73227', 'ROLE_GUEST', '游客');
INSERT INTO `role` VALUES ('e290e2b8b56c4d2bb1e593ca249a9ed6', 'ROLE_USER', '普通用户');
INSERT INTO `role` VALUES ('f58146d5fa0a4d6b969c186686ace07e', 'ROLE_ADMIN', '系统管理员');

-- ----------------------------
-- Table structure for url_resource
-- ----------------------------
DROP TABLE IF EXISTS `url_resource`;
CREATE TABLE `url_resource`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `pattern` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of url_resource
-- ----------------------------
INSERT INTO `url_resource` VALUES ('4e4717ae1ba441a1aff8f271b51270a6', '/admin/**');
INSERT INTO `url_resource` VALUES ('5f8e352206544a1ebc478aaa6ae71a23', '/user/**');
INSERT INTO `url_resource` VALUES ('baaa83a7ec704b9dbcbc65a604c8600a', '/guest/**');

-- ----------------------------
-- Table structure for url_resource_role
-- ----------------------------
DROP TABLE IF EXISTS `url_resource_role`;
CREATE TABLE `url_resource_role`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `url_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `rid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `mid`(`url_id`) USING BTREE,
  INDEX `rid`(`rid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of url_resource_role
-- ----------------------------
INSERT INTO `url_resource_role` VALUES ('118bc34c72fd47ffae4ed42467ca8b59', '5f8e352206544a1ebc478aaa6ae71a23', 'e290e2b8b56c4d2bb1e593ca249a9ed6');
INSERT INTO `url_resource_role` VALUES ('645041a60c6041f68e4e06eedb5e0101', 'baaa83a7ec704b9dbcbc65a604c8600a', 'e290e2b8b56c4d2bb1e593ca249a9ed6');
INSERT INTO `url_resource_role` VALUES ('6dbb2f77de0e46f991c866ce15867d1f', 'baaa83a7ec704b9dbcbc65a604c8600a', '15d1322b50bb4a9392fc9c851ea73227');
INSERT INTO `url_resource_role` VALUES ('f1060ce198aa407dbbd153d9784caf44', '4e4717ae1ba441a1aff8f271b51270a6', 'f58146d5fa0a4d6b969c186686ace07e');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `enabled` tinyint(1) NULL DEFAULT NULL,
  `locked` tinyint(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('4ea7e8c4363f47a7aa8398924fcfd63e', 'user', '{dsy_password_encoder}$2a$31$.q5Fujz98qluh/2ynk3iiurgi02pFz1.2GSzrzL/W8tX7nE.fuVXS', 1, 0);
INSERT INTO `user` VALUES ('7e2b89b3ba654584a614cfaa949bcf15', 'admin', '{dsy_password_encoder}$2a$31$y0f.bGwRusW0iyOg/iK.ceoFWc4/Lx7TMWn.KbM0uYigFx0jKCg3W', 1, 0);
INSERT INTO `user` VALUES ('eae2ad7de14d4387aedcbf5e648273cc', 'javaboy', '{dsy_password_encoder}$2a$31$voxNJ6lctNyLjSLxvzAGju21pSUK5e5kxFv6J0VQlPgXcKxbD5L4C', 1, 0);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `uid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `rid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `uid`(`uid`) USING BTREE,
  INDEX `rid`(`rid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('0e0e21996eee4d0fbaeb87809d3da6de', 'eae2ad7de14d4387aedcbf5e648273cc', '15d1322b50bb4a9392fc9c851ea73227');
INSERT INTO `user_role` VALUES ('a217c067642e42528631d0290a51faf8', '7e2b89b3ba654584a614cfaa949bcf15', 'e290e2b8b56c4d2bb1e593ca249a9ed6');
INSERT INTO `user_role` VALUES ('e1c81d0690d44ce4b94d661bf60601af', '7e2b89b3ba654584a614cfaa949bcf15', 'f58146d5fa0a4d6b969c186686ace07e');
INSERT INTO `user_role` VALUES ('fda09db6220940c1bf0594d486b6353a', '4ea7e8c4363f47a7aa8398924fcfd63e', 'e290e2b8b56c4d2bb1e593ca249a9ed6');

SET FOREIGN_KEY_CHECKS = 1;
