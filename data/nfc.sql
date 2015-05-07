/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50621
Source Host           : localhost:3306
Source Database       : nfc

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2015-05-07 21:28:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for cards
-- ----------------------------
DROP TABLE IF EXISTS `cards`;
CREATE TABLE `cards` (
  `id` varchar(32) NOT NULL,
  `type` varchar(32) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `created` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `user` (`user_id`),
  CONSTRAINT `user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Table for cards and tags';

-- ----------------------------
-- Records of cards
-- ----------------------------
INSERT INTO `cards` VALUES ('044A69415B2380', '4400', 'NXP test card', '1', '2015-05-07 17:42:42');
INSERT INTO `cards` VALUES ('17A14700', '88000400', 'unknown', null, '2015-04-01 11:11:11');

-- ----------------------------
-- Table structure for history
-- ----------------------------
DROP TABLE IF EXISTS `history`;
CREATE TABLE `history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `card_id` varchar(32) DEFAULT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `slot_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `card` (`card_id`),
  KEY `slot` (`slot_id`),
  CONSTRAINT `card` FOREIGN KEY (`card_id`) REFERENCES `cards` (`id`) ON DELETE SET NULL,
  CONSTRAINT `slot` FOREIGN KEY (`slot_id`) REFERENCES `slots` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='collect usage data';

-- ----------------------------
-- Records of history
-- ----------------------------
INSERT INTO `history` VALUES ('1', '044A69415B2380', '2015-05-07 21:19:16', '6');
INSERT INTO `history` VALUES ('2', '044A69415B2380', '2015-05-07 21:19:55', '1');
INSERT INTO `history` VALUES ('3', '044A69415B2380', '2015-05-07 21:20:53', '2');
INSERT INTO `history` VALUES ('4', '044A69415B2380', '2015-05-06 21:21:37', '2');
INSERT INTO `history` VALUES ('5', '044A69415B2380', '2015-05-07 21:23:45', '1');
INSERT INTO `history` VALUES ('6', '17A14700', '2015-04-08 21:24:30', '5');
INSERT INTO `history` VALUES ('7', '17A14700', '2015-04-01 21:25:11', '4');

-- ----------------------------
-- Table structure for slots
-- ----------------------------
DROP TABLE IF EXISTS `slots`;
CREATE TABLE `slots` (
  `id` int(11) NOT NULL,
  `drink` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='llist slots and drinks';

-- ----------------------------
-- Records of slots
-- ----------------------------
INSERT INTO `slots` VALUES ('1', 'Cola');
INSERT INTO `slots` VALUES ('2', 'Apfelschorle');
INSERT INTO `slots` VALUES ('3', 'Fanta');
INSERT INTO `slots` VALUES ('4', 'MezzoMix');
INSERT INTO `slots` VALUES ('5', 'Sprite');
INSERT INTO `slots` VALUES ('6', 'Tab');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='Table for user data';

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', 'Demo', 'User', 'test');
INSERT INTO `users` VALUES ('2', 'Dennis', 'Ahrens', 'admin');
INSERT INTO `users` VALUES ('3', 'Fabian', 'Kalkofen', null);
