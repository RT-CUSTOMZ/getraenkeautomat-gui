/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50621
Source Host           : localhost:3306
Source Database       : nfc

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2015-06-28 17:56:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for cards
-- ----------------------------
DROP TABLE IF EXISTS `cards`;
CREATE TABLE `cards` (
  `id` varchar(255) NOT NULL,
  `created` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `ctype` varchar(255) NOT NULL,
  `version` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_3bfgcx07q69k0m90f6hqvuq5q` (`user_id`),
  CONSTRAINT `FK_3bfgcx07q69k0m90f6hqvuq5q` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cards
-- ----------------------------
INSERT INTO `cards` VALUES ('044A69415B1', '2016-06-14 22:55:03', 'penatibus et', 'CDC731A0', '0', '9');
INSERT INTO `cards` VALUES ('044A69415B10', '2014-06-15 06:03:38', 'vitae dolor. Donec fringilla.', '66F6CEA6', '2', '1');
INSERT INTO `cards` VALUES ('044A69415B100', '2014-04-05 14:10:31', 'pede. Nunc', '4A8396F8', '2', '20');
INSERT INTO `cards` VALUES ('044A69415B11', '2015-11-27 14:57:51', 'magnis dis', 'E7376901', '1', '6');
INSERT INTO `cards` VALUES ('044A69415B12', '2013-11-01 02:21:08', 'lobortis tellus justo sit amet', '6B03CEAE', '2', '15');
INSERT INTO `cards` VALUES ('044A69415B13', '2015-12-18 13:14:58', 'Sed auctor odio a', 'D3236AEF', '3', '5');
INSERT INTO `cards` VALUES ('044A69415B14', '2014-12-07 14:47:47', 'est arcu ac orci. Ut', 'A26F2303', '0', '3');
INSERT INTO `cards` VALUES ('044A69415B15', '2016-03-21 00:45:05', 'Proin vel', '8302FACC', '0', '21');
INSERT INTO `cards` VALUES ('044A69415B16', '2013-11-25 18:03:34', 'massa. Quisque porttitor', '7EA24046', '2', '24');
INSERT INTO `cards` VALUES ('044A69415B17', '2013-02-11 07:07:40', 'mauris ipsum porta', '13590E1A', '0', '20');
INSERT INTO `cards` VALUES ('044A69415B18', '2015-04-10 17:22:26', 'nec, cursus a, enim.', '501BC0B5', '2', '14');
INSERT INTO `cards` VALUES ('044A69415B19', '2013-02-26 22:26:38', 'Mauris', 'D843B1B6', '2', '2');
INSERT INTO `cards` VALUES ('044A69415B2', '2014-01-21 05:57:00', 'eros', '1B94E155', '1', '26');
INSERT INTO `cards` VALUES ('044A69415B20', '2013-07-30 01:19:12', 'magna tellus', 'C4409ED8', '3', '19');
INSERT INTO `cards` VALUES ('044A69415B21', '2014-09-05 21:13:24', 'semper et,', 'F923E111', '2', '10');
INSERT INTO `cards` VALUES ('044A69415B22', '2014-01-27 01:40:04', 'Aliquam', '5D988180', '3', '30');
INSERT INTO `cards` VALUES ('044A69415B23', '2015-07-19 18:20:42', 'egestas. Fusce aliquet magna a', 'E0A026CB', '0', '11');
INSERT INTO `cards` VALUES ('044A69415B2380', '2015-05-07 17:42:42', 'NXP test card 1', '4400', '13', null);
INSERT INTO `cards` VALUES ('044A69415B24', '2014-11-21 16:43:45', 'Quisque tincidunt', 'C3F5610D', '2', '18');
INSERT INTO `cards` VALUES ('044A69415B25', '2014-04-25 05:16:39', 'lorem ac risus. Morbi metus.', 'E1E6E44A', '0', '22');
INSERT INTO `cards` VALUES ('044A69415B26', '2014-03-12 02:49:06', 'consequat enim', '64C2F8E9', '3', '4');
INSERT INTO `cards` VALUES ('044A69415B27', '2015-10-06 07:02:44', 'venenatis a, magna.', '76464621', '1', '2');
INSERT INTO `cards` VALUES ('044A69415B28', '2013-08-14 02:17:51', 'Phasellus', 'FC956D18', '2', '19');
INSERT INTO `cards` VALUES ('044A69415B29', '2016-01-08 13:21:54', 'Nam consequat dolor vitae', '6BF6F8C5', '3', '16');
INSERT INTO `cards` VALUES ('044A69415B3', '2013-07-07 20:49:03', 'feugiat nec,', '2AC7FB52', '1', '27');
INSERT INTO `cards` VALUES ('044A69415B30', '2015-11-16 06:45:25', 'Cras dictum', 'F5DA3A2D', '3', '9');
INSERT INTO `cards` VALUES ('044A69415B31', '2014-02-28 08:39:17', 'Sed eget', '1764CDF5', '1', '4');
INSERT INTO `cards` VALUES ('044A69415B32', '2016-05-18 19:32:04', 'nisi. Aenean eget metus. In', 'F71582BF', '1', '22');
INSERT INTO `cards` VALUES ('044A69415B33', '2013-12-01 03:32:49', 'vulputate mauris', 'D441EA05', '2', '10');
INSERT INTO `cards` VALUES ('044A69415B34', '2014-03-22 02:17:46', 'tincidunt, neque vitae semper', '82CF3696', '3', '4');
INSERT INTO `cards` VALUES ('044A69415B35', '2013-12-25 08:06:42', 'ipsum. Curabitur consequat, lectus sit', '9C0609F2', '2', '28');
INSERT INTO `cards` VALUES ('044A69415B36', '2013-09-06 22:08:15', 'sit amet diam', 'F54CC060', '3', '1');
INSERT INTO `cards` VALUES ('044A69415B37', '2013-01-10 20:31:42', 'in magna.', '0BDE9F96', '2', '17');
INSERT INTO `cards` VALUES ('044A69415B38', '2014-10-01 10:46:28', 'justo', 'D2F660F7', '1', '27');
INSERT INTO `cards` VALUES ('044A69415B39', '2014-04-13 15:03:52', 'sociis natoque penatibus et magnis', 'F4229276', '3', '14');
INSERT INTO `cards` VALUES ('044A69415B4', '2014-05-28 19:25:25', 'ligula. Aenean', '9AACA6E2', '1', '18');
INSERT INTO `cards` VALUES ('044A69415B40', '2015-11-15 22:20:58', 'vulputate', '9412C926', '2', '27');
INSERT INTO `cards` VALUES ('044A69415B41', '2014-10-30 18:02:23', 'iaculis enim,', '7682EE60', '1', '1');
INSERT INTO `cards` VALUES ('044A69415B42', '2013-07-22 04:24:02', 'felis orci, adipiscing non,', '9CEBB870', '3', '22');
INSERT INTO `cards` VALUES ('044A69415B43', '2014-07-08 05:46:15', 'Nullam lobortis quam a felis', '862D2EF9', '0', '13');
INSERT INTO `cards` VALUES ('044A69415B44', '2014-12-05 00:19:04', 'neque pellentesque massa lobortis', '83B70FC6', '2', '17');
INSERT INTO `cards` VALUES ('044A69415B45', '2014-12-09 00:53:02', 'tempor lorem,', '25341B64', '0', '27');
INSERT INTO `cards` VALUES ('044A69415B46', '2014-07-31 02:54:57', 'dis parturient', '6137F776', '1', '11');
INSERT INTO `cards` VALUES ('044A69415B47', '2013-07-09 08:59:08', 'Sed nunc est, mollis non,', '6FD63B39', '1', '1');
INSERT INTO `cards` VALUES ('044A69415B48', '2015-06-27 17:01:13', 'eu turpis. Nulla aliquet.', 'D1EF48FA', '0', '26');
INSERT INTO `cards` VALUES ('044A69415B49', '2016-02-21 00:47:46', 'magna. Duis dignissim tempor arcu.', 'CD2031AF', '2', '5');
INSERT INTO `cards` VALUES ('044A69415B5', '2015-12-21 22:29:22', 'in, hendrerit consectetuer, cursus', '97EAE236', '1', '24');
INSERT INTO `cards` VALUES ('044A69415B50', '2013-09-21 19:30:24', 'eu lacus.', '0CC92876', '1', '4');
INSERT INTO `cards` VALUES ('044A69415B51', '2014-10-18 04:54:23', 'Suspendisse aliquet molestie', 'F0DF3160', '2', '8');
INSERT INTO `cards` VALUES ('044A69415B52', '2015-11-26 23:37:39', 'facilisis vitae,', '43623C5A', '0', '9');
INSERT INTO `cards` VALUES ('044A69415B53', '2013-11-25 05:12:40', 'dui, nec tempus mauris', 'EB391C77', '1', '21');
INSERT INTO `cards` VALUES ('044A69415B54', '2014-10-17 05:44:02', 'tempor, est ac', '16B71272', '1', '6');
INSERT INTO `cards` VALUES ('044A69415B55', '2015-12-18 19:52:07', 'enim. Suspendisse aliquet, sem ut', '45E1507E', '2', '7');
INSERT INTO `cards` VALUES ('044A69415B56', '2014-04-27 06:58:38', 'mattis. Cras eget nisi dictum', '42FB37A6', '1', '12');
INSERT INTO `cards` VALUES ('044A69415B57', '2014-03-26 11:26:31', 'tellus lorem eu', '45A82120', '3', '14');
INSERT INTO `cards` VALUES ('044A69415B58', '2015-09-05 12:52:55', 'mi, ac', '840A09AA', '0', '20');
INSERT INTO `cards` VALUES ('044A69415B59', '2014-02-12 16:04:57', 'quis, pede. Praesent eu dui.', '54588B5B', '3', '7');
INSERT INTO `cards` VALUES ('044A69415B6', '2014-10-18 22:03:35', 'hendrerit id, ante. Nunc mauris', 'CBBA0CDA', '2', '24');
INSERT INTO `cards` VALUES ('044A69415B60', '2013-09-30 04:37:29', 'accumsan', 'F76FC6AE', '2', '22');
INSERT INTO `cards` VALUES ('044A69415B61', '2015-01-26 17:08:18', 'In scelerisque scelerisque dui.', '25125E8A', '1', '6');
INSERT INTO `cards` VALUES ('044A69415B62', '2015-10-02 01:19:16', 'mauris eu', 'CA774E81', '3', '14');
INSERT INTO `cards` VALUES ('044A69415B63', '2014-08-10 16:50:05', 'In tincidunt congue turpis. In', 'CB8351EC', '0', '13');
INSERT INTO `cards` VALUES ('044A69415B64', '2015-08-22 14:08:10', 'in magna. Phasellus dolor elit,', '98C15F15', '3', '30');
INSERT INTO `cards` VALUES ('044A69415B65', '2014-11-09 06:06:33', 'sit amet diam', '27C7138D', '0', '19');
INSERT INTO `cards` VALUES ('044A69415B66', '2014-10-03 01:05:01', 'purus.', '9259498E', '3', '21');
INSERT INTO `cards` VALUES ('044A69415B67', '2013-07-13 00:51:57', 'libero at auctor', '2548CD51', '3', '1');
INSERT INTO `cards` VALUES ('044A69415B68', '2013-09-09 16:11:12', 'lectus', '813CE703', '1', '28');
INSERT INTO `cards` VALUES ('044A69415B69', '2015-02-21 05:17:56', 'felis ullamcorper viverra. Maecenas iaculis', 'C6C5A71B', '2', '18');
INSERT INTO `cards` VALUES ('044A69415B7', '2015-12-27 05:15:49', 'orci lobortis augue scelerisque mollis.', 'D27F727B', '2', '17');
INSERT INTO `cards` VALUES ('044A69415B70', '2015-03-14 23:17:38', 'nisl. Nulla', '9C247E1F', '3', '19');
INSERT INTO `cards` VALUES ('044A69415B71', '2016-02-24 23:44:34', 'malesuada fringilla est. Mauris', 'E50F02C4', '3', '27');
INSERT INTO `cards` VALUES ('044A69415B72', '2014-08-04 07:46:50', 'ultrices. Vivamus', 'FB6302F5', '1', '29');
INSERT INTO `cards` VALUES ('044A69415B73', '2016-02-02 10:43:36', 'mattis ornare,', '3B78CE3C', '3', '25');
INSERT INTO `cards` VALUES ('044A69415B74', '2015-12-19 02:31:47', 'neque pellentesque massa lobortis', '2602F1F0', '2', '18');
INSERT INTO `cards` VALUES ('044A69415B75', '2014-06-05 00:05:36', 'a, enim. Suspendisse aliquet, sem', '77A9D8CE', '3', '30');
INSERT INTO `cards` VALUES ('044A69415B76', '2013-08-11 03:12:22', 'nec,', 'CD0696B3', '3', '8');
INSERT INTO `cards` VALUES ('044A69415B77', '2013-03-02 11:59:10', 'egestas', '212EDBE5', '1', '22');
INSERT INTO `cards` VALUES ('044A69415B78', '2013-04-24 21:25:29', 'Phasellus dolor elit, pellentesque a,', '772A5C32', '3', '23');
INSERT INTO `cards` VALUES ('044A69415B79', '2013-05-16 06:30:56', 'Pellentesque tincidunt tempus risus.', '13FFA7E5', '0', '9');
INSERT INTO `cards` VALUES ('044A69415B8', '2014-07-09 21:40:53', 'Quisque varius.', '2075BCAF', '2', '2');
INSERT INTO `cards` VALUES ('044A69415B80', '2015-01-02 20:14:43', 'Mauris ut', 'B5E3517D', '1', '3');
INSERT INTO `cards` VALUES ('044A69415B81', '2014-06-12 10:10:44', 'consequat dolor', '9BBB0194', '3', '14');
INSERT INTO `cards` VALUES ('044A69415B82', '2013-02-16 09:22:48', 'scelerisque, lorem ipsum sodales purus,', 'B5F2851E', '1', '30');
INSERT INTO `cards` VALUES ('044A69415B83', '2013-07-19 08:02:31', 'mi lacinia mattis. Integer eu', 'ECDBBF41', '2', '21');
INSERT INTO `cards` VALUES ('044A69415B84', '2014-08-15 05:01:13', 'Nunc commodo auctor velit. Aliquam', '207BC431', '1', '25');
INSERT INTO `cards` VALUES ('044A69415B85', '2015-09-05 23:43:00', 'vulputate velit', 'A8C7F772', '1', '11');
INSERT INTO `cards` VALUES ('044A69415B86', '2016-04-25 03:56:30', 'facilisis, magna tellus', '32042268', '0', '2');
INSERT INTO `cards` VALUES ('044A69415B87', '2015-10-16 07:36:00', 'hendrerit. Donec', '43258044', '1', '13');
INSERT INTO `cards` VALUES ('044A69415B88', '2014-09-28 13:44:51', 'eu', '7B93BC81', '1', '15');
INSERT INTO `cards` VALUES ('044A69415B89', '2013-01-28 09:43:40', 'ipsum. Donec', '8953A56E', '3', '2');
INSERT INTO `cards` VALUES ('044A69415B9', '2015-10-01 10:03:30', 'ac, feugiat non, lobortis quis,', '365F7250', '1', '26');
INSERT INTO `cards` VALUES ('044A69415B90', '2015-04-05 12:54:29', 'quis', '338EA5FE', '2', '22');
INSERT INTO `cards` VALUES ('044A69415B91', '2015-05-08 08:25:21', 'pharetra. Nam ac nulla. In', '01BEE43A', '0', '14');
INSERT INTO `cards` VALUES ('044A69415B92', '2013-08-02 05:27:52', 'eu, ultrices sit amet, risus.', '83850356', '3', '28');
INSERT INTO `cards` VALUES ('044A69415B93', '2014-01-28 19:35:26', 'interdum. Curabitur', 'F8DB38AA', '2', '29');
INSERT INTO `cards` VALUES ('044A69415B94', '2016-01-23 15:35:46', 'ullamcorper. Duis cursus, diam', 'ADE91D95', '3', '23');
INSERT INTO `cards` VALUES ('044A69415B95', '2014-06-22 20:19:24', 'vulputate velit eu sem. Pellentesque', '8D84F19E', '0', '21');
INSERT INTO `cards` VALUES ('044A69415B96', '2013-04-04 03:54:48', 'pede', 'F7B409A7', '0', '7');
INSERT INTO `cards` VALUES ('044A69415B97', '2015-03-23 21:40:21', 'metus. Aliquam erat volutpat.', 'AEAA618F', '2', '19');
INSERT INTO `cards` VALUES ('044A69415B98', '2014-12-21 22:24:39', 'Vestibulum accumsan neque et', 'B04D9F4A', '3', '1');
INSERT INTO `cards` VALUES ('044A69415B99', '2013-04-05 20:27:35', 'fringilla mi', '77C4F6B0', '1', '9');
INSERT INTO `cards` VALUES ('17A14700', '2015-04-01 11:11:11', 'unknown', '88000400', '7', '2');

-- ----------------------------
-- Table structure for history
-- ----------------------------
DROP TABLE IF EXISTS `history`;
CREATE TABLE `history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `htime` datetime NOT NULL,
  `version` int(11) NOT NULL,
  `card_id` varchar(255) DEFAULT NULL,
  `slot_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_97x1xu3ojqmpeftppdbo65kw4` (`card_id`),
  KEY `FK_hrp4t3tjkydm5lj8jb25cidln` (`slot_id`),
  CONSTRAINT `FK_97x1xu3ojqmpeftppdbo65kw4` FOREIGN KEY (`card_id`) REFERENCES `cards` (`id`),
  CONSTRAINT `FK_hrp4t3tjkydm5lj8jb25cidln` FOREIGN KEY (`slot_id`) REFERENCES `slots` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=508 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of history
-- ----------------------------
INSERT INTO `history` VALUES ('1', '2015-05-07 21:19:16', '1', '044A69415B2380', '6');
INSERT INTO `history` VALUES ('2', '2015-05-07 21:19:55', '1', '044A69415B2380', '1');
INSERT INTO `history` VALUES ('3', '2015-05-07 21:20:53', '1', '044A69415B2380', '2');
INSERT INTO `history` VALUES ('4', '2015-05-06 21:21:37', '1', '044A69415B2380', '2');
INSERT INTO `history` VALUES ('5', '2015-05-07 21:23:45', '1', '044A69415B2380', '1');
INSERT INTO `history` VALUES ('6', '2015-04-08 21:24:30', '1', '17A14700', '5');
INSERT INTO `history` VALUES ('7', '2015-04-01 21:25:11', '1', '17A14700', '4');
INSERT INTO `history` VALUES ('8', '2015-04-22 08:55:02', '0', '044A69415B29', '5');
INSERT INTO `history` VALUES ('9', '2013-06-17 21:13:26', '2', '044A69415B52', '3');
INSERT INTO `history` VALUES ('10', '2014-12-10 05:32:02', '2', '044A69415B42', '3');
INSERT INTO `history` VALUES ('11', '2014-11-25 04:16:22', '3', '044A69415B7', '5');
INSERT INTO `history` VALUES ('12', '2014-11-13 22:54:05', '3', '044A69415B52', '5');
INSERT INTO `history` VALUES ('13', '2014-04-28 08:04:09', '3', '044A69415B97', '5');
INSERT INTO `history` VALUES ('14', '2013-06-23 02:38:19', '1', '044A69415B25', '5');
INSERT INTO `history` VALUES ('15', '2015-03-27 14:36:29', '0', '044A69415B43', '1');
INSERT INTO `history` VALUES ('16', '2015-06-15 09:29:13', '1', '044A69415B92', '6');
INSERT INTO `history` VALUES ('17', '2015-02-06 06:01:54', '2', '044A69415B4', '5');
INSERT INTO `history` VALUES ('18', '2013-09-05 11:56:37', '1', '044A69415B86', '5');
INSERT INTO `history` VALUES ('19', '2013-01-14 16:08:28', '0', '044A69415B100', '2');
INSERT INTO `history` VALUES ('20', '2015-06-25 20:07:55', '3', '044A69415B81', '4');
INSERT INTO `history` VALUES ('21', '2014-12-16 19:32:00', '0', '044A69415B59', '5');
INSERT INTO `history` VALUES ('22', '2013-07-25 07:43:49', '1', '044A69415B27', '4');
INSERT INTO `history` VALUES ('23', '2015-01-24 12:45:46', '3', '044A69415B38', '3');
INSERT INTO `history` VALUES ('24', '2014-06-19 19:32:36', '3', '044A69415B31', '1');
INSERT INTO `history` VALUES ('25', '2013-03-28 21:19:20', '1', '044A69415B51', '2');
INSERT INTO `history` VALUES ('26', '2013-05-15 20:26:48', '0', '044A69415B58', '5');
INSERT INTO `history` VALUES ('27', '2013-03-07 23:14:34', '2', '044A69415B96', '3');
INSERT INTO `history` VALUES ('28', '2013-07-06 21:31:42', '2', '044A69415B39', '2');
INSERT INTO `history` VALUES ('29', '2013-01-29 04:44:05', '0', '044A69415B90', '6');
INSERT INTO `history` VALUES ('30', '2014-05-14 01:46:25', '2', '044A69415B3', '4');
INSERT INTO `history` VALUES ('31', '2014-04-20 14:36:04', '1', '044A69415B18', '3');
INSERT INTO `history` VALUES ('32', '2014-12-27 11:09:53', '2', '044A69415B1', '5');
INSERT INTO `history` VALUES ('33', '2015-05-08 06:24:04', '0', '044A69415B93', '1');
INSERT INTO `history` VALUES ('34', '2014-08-17 07:06:09', '3', '044A69415B74', '4');
INSERT INTO `history` VALUES ('35', '2014-03-09 20:42:32', '3', '044A69415B25', '4');
INSERT INTO `history` VALUES ('36', '2014-03-20 12:06:14', '2', '044A69415B84', '3');
INSERT INTO `history` VALUES ('37', '2013-03-03 22:41:23', '2', '044A69415B10', '2');
INSERT INTO `history` VALUES ('38', '2013-09-11 19:13:50', '2', '044A69415B73', '3');
INSERT INTO `history` VALUES ('39', '2013-05-11 14:58:28', '0', '044A69415B21', '1');
INSERT INTO `history` VALUES ('40', '2015-06-04 15:26:02', '1', '044A69415B4', '6');
INSERT INTO `history` VALUES ('41', '2014-04-29 22:36:58', '0', '044A69415B10', '5');
INSERT INTO `history` VALUES ('42', '2015-02-19 10:45:07', '2', '044A69415B67', '5');
INSERT INTO `history` VALUES ('43', '2014-03-01 00:43:03', '1', '044A69415B87', '1');
INSERT INTO `history` VALUES ('44', '2015-01-08 17:11:43', '3', '044A69415B61', '3');
INSERT INTO `history` VALUES ('45', '2014-07-31 23:41:00', '1', '044A69415B63', '2');
INSERT INTO `history` VALUES ('46', '2014-07-23 01:03:12', '3', '044A69415B68', '1');
INSERT INTO `history` VALUES ('47', '2014-07-04 13:04:12', '0', '044A69415B81', '6');
INSERT INTO `history` VALUES ('48', '2015-01-02 10:57:49', '2', '044A69415B73', '1');
INSERT INTO `history` VALUES ('49', '2013-09-25 03:14:24', '1', '044A69415B59', '2');
INSERT INTO `history` VALUES ('50', '2014-06-12 18:13:01', '1', '044A69415B59', '4');
INSERT INTO `history` VALUES ('51', '2013-10-18 14:55:09', '0', '044A69415B10', '3');
INSERT INTO `history` VALUES ('52', '2015-05-24 11:41:24', '2', '044A69415B97', '4');
INSERT INTO `history` VALUES ('53', '2014-07-20 10:00:03', '0', '044A69415B15', '4');
INSERT INTO `history` VALUES ('54', '2013-12-24 05:53:18', '2', '044A69415B38', '6');
INSERT INTO `history` VALUES ('55', '2015-02-20 22:30:07', '2', '044A69415B85', '6');
INSERT INTO `history` VALUES ('56', '2013-08-20 18:36:45', '2', '044A69415B18', '4');
INSERT INTO `history` VALUES ('57', '2015-01-22 06:18:11', '3', '044A69415B96', '4');
INSERT INTO `history` VALUES ('58', '2013-06-23 02:38:10', '3', '044A69415B78', '6');
INSERT INTO `history` VALUES ('59', '2013-01-27 08:18:35', '1', '044A69415B6', '6');
INSERT INTO `history` VALUES ('60', '2014-10-31 18:28:20', '0', '044A69415B75', '3');
INSERT INTO `history` VALUES ('61', '2013-02-18 16:17:27', '3', '044A69415B2', '1');
INSERT INTO `history` VALUES ('62', '2014-09-25 17:37:17', '0', '044A69415B68', '6');
INSERT INTO `history` VALUES ('63', '2015-05-07 14:52:36', '1', '044A69415B59', '6');
INSERT INTO `history` VALUES ('64', '2013-01-15 01:27:14', '3', '044A69415B90', '5');
INSERT INTO `history` VALUES ('65', '2014-04-29 14:07:33', '3', '044A69415B61', '4');
INSERT INTO `history` VALUES ('66', '2013-07-25 07:41:19', '3', '044A69415B22', '6');
INSERT INTO `history` VALUES ('67', '2013-03-17 08:22:44', '0', '044A69415B55', '2');
INSERT INTO `history` VALUES ('68', '2015-01-05 14:57:40', '2', '044A69415B21', '3');
INSERT INTO `history` VALUES ('69', '2014-12-29 17:11:21', '2', '044A69415B7', '6');
INSERT INTO `history` VALUES ('70', '2014-06-30 08:40:07', '0', '044A69415B15', '5');
INSERT INTO `history` VALUES ('71', '2013-07-07 23:25:11', '2', '044A69415B4', '2');
INSERT INTO `history` VALUES ('72', '2013-04-25 01:03:16', '0', '044A69415B47', '1');
INSERT INTO `history` VALUES ('73', '2014-09-11 16:46:47', '3', '044A69415B41', '6');
INSERT INTO `history` VALUES ('74', '2013-07-23 21:42:46', '1', '044A69415B62', '1');
INSERT INTO `history` VALUES ('75', '2014-04-07 15:38:31', '2', '044A69415B64', '3');
INSERT INTO `history` VALUES ('76', '2013-11-02 16:01:05', '1', '044A69415B33', '5');
INSERT INTO `history` VALUES ('77', '2014-04-08 16:47:13', '3', '044A69415B37', '4');
INSERT INTO `history` VALUES ('78', '2013-06-05 23:51:55', '3', '044A69415B63', '6');
INSERT INTO `history` VALUES ('79', '2013-02-20 14:09:05', '3', '044A69415B31', '6');
INSERT INTO `history` VALUES ('80', '2014-01-20 15:19:47', '2', '044A69415B37', '5');
INSERT INTO `history` VALUES ('81', '2015-02-07 18:03:27', '1', '044A69415B20', '1');
INSERT INTO `history` VALUES ('82', '2014-09-16 09:23:41', '0', '044A69415B54', '3');
INSERT INTO `history` VALUES ('83', '2013-07-21 14:37:54', '3', '044A69415B5', '4');
INSERT INTO `history` VALUES ('84', '2013-03-23 07:35:39', '2', '044A69415B53', '2');
INSERT INTO `history` VALUES ('85', '2015-01-09 08:58:37', '3', '044A69415B79', '4');
INSERT INTO `history` VALUES ('86', '2013-10-22 10:44:14', '3', '044A69415B76', '6');
INSERT INTO `history` VALUES ('87', '2013-10-18 04:02:56', '3', '044A69415B93', '3');
INSERT INTO `history` VALUES ('88', '2013-03-22 21:05:36', '3', '044A69415B72', '5');
INSERT INTO `history` VALUES ('89', '2015-05-29 23:16:40', '1', '044A69415B68', '3');
INSERT INTO `history` VALUES ('90', '2014-09-21 12:12:50', '0', '044A69415B21', '5');
INSERT INTO `history` VALUES ('91', '2013-01-08 23:49:35', '1', '044A69415B80', '6');
INSERT INTO `history` VALUES ('92', '2015-02-03 08:28:10', '3', '044A69415B67', '5');
INSERT INTO `history` VALUES ('93', '2013-03-06 08:03:11', '1', '044A69415B22', '6');
INSERT INTO `history` VALUES ('94', '2013-02-26 01:25:24', '1', '044A69415B79', '4');
INSERT INTO `history` VALUES ('95', '2015-04-23 14:46:16', '1', '044A69415B40', '1');
INSERT INTO `history` VALUES ('96', '2013-04-15 21:44:46', '3', '044A69415B62', '2');
INSERT INTO `history` VALUES ('97', '2013-01-20 01:30:40', '2', '044A69415B94', '4');
INSERT INTO `history` VALUES ('98', '2014-10-02 01:56:48', '3', '044A69415B86', '4');
INSERT INTO `history` VALUES ('99', '2013-01-23 03:00:31', '0', '044A69415B78', '6');
INSERT INTO `history` VALUES ('100', '2013-02-15 21:45:07', '3', '044A69415B93', '5');
INSERT INTO `history` VALUES ('101', '2013-08-11 01:33:38', '3', '044A69415B74', '1');
INSERT INTO `history` VALUES ('102', '2015-03-31 12:06:55', '1', '044A69415B83', '2');
INSERT INTO `history` VALUES ('103', '2014-04-29 07:49:36', '2', '044A69415B11', '4');
INSERT INTO `history` VALUES ('104', '2014-05-09 07:05:02', '3', '044A69415B28', '5');
INSERT INTO `history` VALUES ('105', '2014-08-29 02:43:20', '2', '044A69415B100', '6');
INSERT INTO `history` VALUES ('106', '2015-03-26 02:40:02', '2', '044A69415B17', '3');
INSERT INTO `history` VALUES ('107', '2013-10-02 03:33:15', '1', '044A69415B8', '6');
INSERT INTO `history` VALUES ('108', '2014-10-19 01:24:47', '2', '044A69415B41', '1');
INSERT INTO `history` VALUES ('109', '2015-02-23 21:03:16', '1', '044A69415B49', '5');
INSERT INTO `history` VALUES ('110', '2013-11-13 00:51:12', '3', '044A69415B45', '6');
INSERT INTO `history` VALUES ('111', '2013-08-29 03:25:32', '2', '044A69415B35', '2');
INSERT INTO `history` VALUES ('112', '2014-07-02 22:46:41', '0', '044A69415B67', '6');
INSERT INTO `history` VALUES ('113', '2014-02-25 07:22:14', '2', '044A69415B35', '3');
INSERT INTO `history` VALUES ('114', '2015-02-06 21:12:54', '3', '044A69415B46', '3');
INSERT INTO `history` VALUES ('115', '2013-11-03 20:05:38', '3', '044A69415B30', '3');
INSERT INTO `history` VALUES ('116', '2014-07-26 13:25:30', '3', '044A69415B97', '3');
INSERT INTO `history` VALUES ('117', '2013-07-07 20:38:17', '1', '044A69415B20', '6');
INSERT INTO `history` VALUES ('118', '2014-10-13 20:52:30', '3', '044A69415B62', '5');
INSERT INTO `history` VALUES ('119', '2014-01-07 04:31:46', '2', '044A69415B71', '5');
INSERT INTO `history` VALUES ('120', '2015-04-22 07:06:38', '1', '044A69415B51', '3');
INSERT INTO `history` VALUES ('121', '2013-09-06 04:51:35', '1', '044A69415B32', '5');
INSERT INTO `history` VALUES ('122', '2014-05-16 00:05:54', '2', '044A69415B44', '1');
INSERT INTO `history` VALUES ('123', '2014-10-17 19:21:45', '1', '044A69415B71', '3');
INSERT INTO `history` VALUES ('124', '2015-03-27 15:52:30', '1', '044A69415B58', '3');
INSERT INTO `history` VALUES ('125', '2013-04-06 13:54:05', '2', '044A69415B49', '6');
INSERT INTO `history` VALUES ('126', '2014-02-17 13:01:49', '1', '044A69415B92', '3');
INSERT INTO `history` VALUES ('127', '2013-01-14 04:35:08', '1', '044A69415B15', '1');
INSERT INTO `history` VALUES ('128', '2013-11-17 19:30:11', '2', '044A69415B49', '1');
INSERT INTO `history` VALUES ('129', '2013-01-13 03:19:22', '3', '044A69415B93', '4');
INSERT INTO `history` VALUES ('130', '2013-05-12 17:46:17', '3', '044A69415B29', '6');
INSERT INTO `history` VALUES ('131', '2015-01-19 20:12:54', '3', '044A69415B67', '6');
INSERT INTO `history` VALUES ('132', '2014-08-06 04:04:13', '2', '044A69415B40', '1');
INSERT INTO `history` VALUES ('133', '2015-04-11 13:38:19', '0', '044A69415B96', '2');
INSERT INTO `history` VALUES ('134', '2015-03-01 03:44:15', '1', '044A69415B36', '1');
INSERT INTO `history` VALUES ('135', '2014-02-20 12:51:33', '3', '044A69415B11', '4');
INSERT INTO `history` VALUES ('136', '2015-03-05 10:58:31', '3', '044A69415B80', '6');
INSERT INTO `history` VALUES ('137', '2014-06-30 05:46:39', '2', '044A69415B10', '3');
INSERT INTO `history` VALUES ('138', '2015-05-08 11:57:34', '1', '044A69415B42', '3');
INSERT INTO `history` VALUES ('139', '2014-01-25 15:22:55', '1', '044A69415B60', '1');
INSERT INTO `history` VALUES ('140', '2014-11-28 15:08:24', '1', '044A69415B5', '5');
INSERT INTO `history` VALUES ('141', '2014-08-25 16:34:53', '2', '044A69415B23', '6');
INSERT INTO `history` VALUES ('142', '2014-07-05 00:39:25', '2', '044A69415B15', '3');
INSERT INTO `history` VALUES ('143', '2013-03-13 10:24:39', '0', '044A69415B61', '2');
INSERT INTO `history` VALUES ('144', '2014-04-15 10:45:15', '3', '044A69415B59', '6');
INSERT INTO `history` VALUES ('145', '2014-09-28 10:26:02', '1', '044A69415B53', '2');
INSERT INTO `history` VALUES ('146', '2014-05-19 03:57:56', '1', '044A69415B22', '6');
INSERT INTO `history` VALUES ('147', '2014-06-07 11:43:39', '2', '044A69415B18', '3');
INSERT INTO `history` VALUES ('148', '2014-02-09 12:51:04', '3', '044A69415B49', '2');
INSERT INTO `history` VALUES ('149', '2013-11-10 08:31:42', '1', '044A69415B100', '1');
INSERT INTO `history` VALUES ('150', '2014-07-17 07:38:01', '2', '044A69415B33', '4');
INSERT INTO `history` VALUES ('151', '2013-03-24 09:17:17', '3', '044A69415B76', '6');
INSERT INTO `history` VALUES ('152', '2015-06-22 11:12:50', '0', '044A69415B41', '3');
INSERT INTO `history` VALUES ('153', '2013-12-10 01:31:46', '3', '044A69415B43', '4');
INSERT INTO `history` VALUES ('154', '2014-03-27 00:38:04', '1', '044A69415B81', '6');
INSERT INTO `history` VALUES ('155', '2014-02-05 23:10:12', '0', '044A69415B30', '6');
INSERT INTO `history` VALUES ('156', '2013-12-03 13:18:17', '3', '044A69415B85', '5');
INSERT INTO `history` VALUES ('157', '2014-11-23 23:51:55', '3', '044A69415B25', '2');
INSERT INTO `history` VALUES ('158', '2014-12-23 18:57:17', '1', '044A69415B54', '3');
INSERT INTO `history` VALUES ('159', '2014-08-28 13:28:09', '0', '044A69415B14', '2');
INSERT INTO `history` VALUES ('160', '2014-02-28 23:52:24', '0', '044A69415B20', '2');
INSERT INTO `history` VALUES ('161', '2013-05-22 11:09:21', '3', '044A69415B52', '3');
INSERT INTO `history` VALUES ('162', '2015-06-06 00:58:12', '3', '044A69415B5', '3');
INSERT INTO `history` VALUES ('163', '2015-01-17 11:44:11', '2', '044A69415B29', '6');
INSERT INTO `history` VALUES ('164', '2014-12-30 20:45:09', '1', '044A69415B10', '4');
INSERT INTO `history` VALUES ('165', '2013-05-31 12:40:38', '1', '044A69415B80', '6');
INSERT INTO `history` VALUES ('166', '2013-10-24 17:20:16', '3', '044A69415B43', '3');
INSERT INTO `history` VALUES ('167', '2014-01-24 22:14:11', '2', '044A69415B91', '3');
INSERT INTO `history` VALUES ('168', '2013-10-28 19:18:06', '3', '044A69415B17', '6');
INSERT INTO `history` VALUES ('169', '2013-10-24 17:59:14', '3', '044A69415B55', '4');
INSERT INTO `history` VALUES ('170', '2013-05-31 03:07:34', '0', '044A69415B42', '2');
INSERT INTO `history` VALUES ('171', '2013-06-03 23:52:25', '2', '044A69415B86', '4');
INSERT INTO `history` VALUES ('172', '2014-02-08 03:19:06', '3', '044A69415B1', '3');
INSERT INTO `history` VALUES ('173', '2015-02-23 08:51:04', '0', '044A69415B79', '4');
INSERT INTO `history` VALUES ('174', '2014-07-02 19:17:14', '0', '044A69415B73', '5');
INSERT INTO `history` VALUES ('175', '2014-08-20 00:52:58', '1', '044A69415B74', '1');
INSERT INTO `history` VALUES ('176', '2013-06-09 21:43:50', '2', '044A69415B27', '3');
INSERT INTO `history` VALUES ('177', '2013-09-03 16:43:24', '3', '044A69415B15', '6');
INSERT INTO `history` VALUES ('178', '2014-03-26 18:31:53', '0', '044A69415B55', '5');
INSERT INTO `history` VALUES ('179', '2013-09-16 22:33:00', '1', '044A69415B55', '4');
INSERT INTO `history` VALUES ('180', '2013-10-13 12:29:58', '0', '044A69415B47', '3');
INSERT INTO `history` VALUES ('181', '2014-02-17 23:44:19', '3', '044A69415B11', '1');
INSERT INTO `history` VALUES ('182', '2013-11-06 06:51:11', '0', '044A69415B23', '1');
INSERT INTO `history` VALUES ('183', '2015-01-12 17:58:47', '0', '044A69415B78', '2');
INSERT INTO `history` VALUES ('184', '2013-12-23 23:16:31', '1', '044A69415B2', '2');
INSERT INTO `history` VALUES ('185', '2013-12-19 18:27:02', '2', '044A69415B45', '6');
INSERT INTO `history` VALUES ('186', '2013-11-06 05:23:26', '3', '044A69415B82', '4');
INSERT INTO `history` VALUES ('187', '2014-11-04 03:20:58', '2', '044A69415B91', '1');
INSERT INTO `history` VALUES ('188', '2014-12-22 10:20:17', '3', '044A69415B14', '6');
INSERT INTO `history` VALUES ('189', '2015-01-02 02:04:42', '0', '044A69415B4', '3');
INSERT INTO `history` VALUES ('190', '2013-03-25 18:30:42', '3', '044A69415B27', '2');
INSERT INTO `history` VALUES ('191', '2014-11-11 23:15:35', '1', '044A69415B28', '1');
INSERT INTO `history` VALUES ('192', '2013-07-04 17:17:46', '3', '044A69415B51', '6');
INSERT INTO `history` VALUES ('193', '2014-09-09 17:09:19', '2', '044A69415B39', '1');
INSERT INTO `history` VALUES ('194', '2014-11-17 21:47:43', '0', '044A69415B27', '1');
INSERT INTO `history` VALUES ('195', '2013-12-18 12:40:56', '2', '044A69415B66', '6');
INSERT INTO `history` VALUES ('196', '2015-03-20 23:11:31', '3', '044A69415B20', '4');
INSERT INTO `history` VALUES ('197', '2014-05-26 02:40:20', '2', '044A69415B85', '1');
INSERT INTO `history` VALUES ('198', '2013-03-11 23:17:02', '0', '044A69415B11', '5');
INSERT INTO `history` VALUES ('199', '2014-09-27 09:06:52', '3', '044A69415B75', '6');
INSERT INTO `history` VALUES ('200', '2015-05-28 02:08:27', '1', '044A69415B45', '4');
INSERT INTO `history` VALUES ('201', '2014-03-14 06:44:11', '1', '044A69415B44', '2');
INSERT INTO `history` VALUES ('202', '2013-06-26 11:48:13', '0', '044A69415B98', '2');
INSERT INTO `history` VALUES ('203', '2013-08-25 13:32:11', '0', '044A69415B94', '1');
INSERT INTO `history` VALUES ('204', '2013-08-31 08:54:54', '0', '044A69415B81', '1');
INSERT INTO `history` VALUES ('205', '2013-03-13 09:55:09', '0', '044A69415B87', '5');
INSERT INTO `history` VALUES ('206', '2015-04-20 23:54:09', '1', '044A69415B84', '3');
INSERT INTO `history` VALUES ('207', '2015-02-11 22:58:45', '1', '044A69415B79', '6');
INSERT INTO `history` VALUES ('208', '2013-11-12 12:07:45', '1', '044A69415B80', '3');
INSERT INTO `history` VALUES ('209', '2014-07-09 12:13:41', '2', '044A69415B33', '1');
INSERT INTO `history` VALUES ('210', '2013-06-16 03:51:37', '0', '044A69415B17', '3');
INSERT INTO `history` VALUES ('211', '2014-07-12 10:31:03', '1', '044A69415B68', '1');
INSERT INTO `history` VALUES ('212', '2015-04-08 15:49:15', '2', '044A69415B90', '3');
INSERT INTO `history` VALUES ('213', '2013-03-08 13:18:54', '3', '044A69415B88', '1');
INSERT INTO `history` VALUES ('214', '2013-08-28 01:02:34', '2', '044A69415B97', '3');
INSERT INTO `history` VALUES ('215', '2013-10-16 10:47:12', '0', '044A69415B86', '4');
INSERT INTO `history` VALUES ('216', '2014-01-04 11:46:14', '0', '044A69415B27', '5');
INSERT INTO `history` VALUES ('217', '2013-03-25 03:49:23', '2', '044A69415B48', '1');
INSERT INTO `history` VALUES ('218', '2014-09-06 10:52:01', '1', '044A69415B50', '4');
INSERT INTO `history` VALUES ('219', '2013-06-20 13:49:18', '1', '044A69415B59', '2');
INSERT INTO `history` VALUES ('220', '2014-03-13 19:24:23', '2', '044A69415B28', '1');
INSERT INTO `history` VALUES ('221', '2014-12-13 19:29:01', '0', '044A69415B77', '1');
INSERT INTO `history` VALUES ('222', '2015-03-30 13:10:51', '2', '044A69415B32', '5');
INSERT INTO `history` VALUES ('223', '2013-05-24 05:51:23', '0', '044A69415B2', '6');
INSERT INTO `history` VALUES ('224', '2014-10-14 16:02:34', '0', '044A69415B88', '5');
INSERT INTO `history` VALUES ('225', '2014-10-24 15:20:11', '3', '044A69415B91', '1');
INSERT INTO `history` VALUES ('226', '2014-02-03 11:00:19', '1', '044A69415B53', '1');
INSERT INTO `history` VALUES ('227', '2014-05-30 23:24:32', '0', '044A69415B64', '1');
INSERT INTO `history` VALUES ('228', '2014-07-14 21:01:54', '3', '044A69415B34', '6');
INSERT INTO `history` VALUES ('229', '2014-05-05 17:30:24', '2', '044A69415B65', '6');
INSERT INTO `history` VALUES ('230', '2013-09-13 17:48:01', '2', '044A69415B80', '2');
INSERT INTO `history` VALUES ('231', '2014-10-15 05:49:42', '2', '044A69415B87', '5');
INSERT INTO `history` VALUES ('232', '2014-10-29 08:34:23', '2', '044A69415B17', '6');
INSERT INTO `history` VALUES ('233', '2013-07-01 03:01:56', '0', '044A69415B19', '5');
INSERT INTO `history` VALUES ('234', '2013-07-03 16:22:21', '1', '044A69415B24', '3');
INSERT INTO `history` VALUES ('235', '2014-11-14 13:18:48', '3', '044A69415B78', '4');
INSERT INTO `history` VALUES ('236', '2014-10-19 16:18:49', '2', '044A69415B26', '4');
INSERT INTO `history` VALUES ('237', '2013-05-16 14:32:35', '0', '044A69415B4', '6');
INSERT INTO `history` VALUES ('238', '2014-05-01 13:00:31', '1', '044A69415B5', '1');
INSERT INTO `history` VALUES ('239', '2014-09-19 10:53:57', '2', '044A69415B14', '4');
INSERT INTO `history` VALUES ('240', '2015-04-09 21:34:00', '1', '044A69415B68', '3');
INSERT INTO `history` VALUES ('241', '2014-12-15 06:54:37', '0', '044A69415B57', '5');
INSERT INTO `history` VALUES ('242', '2013-03-15 01:25:11', '3', '044A69415B20', '2');
INSERT INTO `history` VALUES ('243', '2013-08-29 17:52:42', '2', '044A69415B94', '2');
INSERT INTO `history` VALUES ('244', '2013-08-10 04:53:50', '1', '044A69415B50', '4');
INSERT INTO `history` VALUES ('245', '2013-02-27 00:12:47', '0', '044A69415B1', '2');
INSERT INTO `history` VALUES ('246', '2014-09-29 17:20:19', '0', '044A69415B46', '4');
INSERT INTO `history` VALUES ('247', '2015-05-31 05:36:00', '2', '044A69415B49', '4');
INSERT INTO `history` VALUES ('248', '2013-02-21 14:30:58', '1', '044A69415B26', '5');
INSERT INTO `history` VALUES ('249', '2013-09-17 20:08:20', '0', '044A69415B13', '6');
INSERT INTO `history` VALUES ('250', '2014-04-12 10:09:14', '3', '044A69415B12', '4');
INSERT INTO `history` VALUES ('251', '2015-05-26 02:50:33', '2', '044A69415B65', '2');
INSERT INTO `history` VALUES ('252', '2013-04-13 10:58:07', '0', '044A69415B36', '1');
INSERT INTO `history` VALUES ('253', '2015-06-16 12:06:27', '3', '044A69415B63', '2');
INSERT INTO `history` VALUES ('254', '2014-10-04 17:13:30', '1', '044A69415B89', '6');
INSERT INTO `history` VALUES ('255', '2015-04-28 00:48:50', '2', '044A69415B6', '2');
INSERT INTO `history` VALUES ('256', '2013-09-24 12:12:58', '0', '044A69415B73', '1');
INSERT INTO `history` VALUES ('257', '2014-05-11 12:32:03', '2', '044A69415B51', '1');
INSERT INTO `history` VALUES ('258', '2013-11-30 19:24:57', '0', '044A69415B28', '3');
INSERT INTO `history` VALUES ('259', '2014-02-04 02:05:20', '1', '044A69415B43', '4');
INSERT INTO `history` VALUES ('260', '2014-03-31 03:41:40', '0', '044A69415B34', '3');
INSERT INTO `history` VALUES ('261', '2014-02-08 02:41:22', '0', '044A69415B90', '1');
INSERT INTO `history` VALUES ('262', '2013-01-03 04:41:36', '0', '044A69415B39', '5');
INSERT INTO `history` VALUES ('263', '2013-03-18 17:26:50', '1', '044A69415B25', '5');
INSERT INTO `history` VALUES ('264', '2015-03-06 05:18:40', '3', '044A69415B73', '5');
INSERT INTO `history` VALUES ('265', '2014-07-10 06:52:45', '2', '044A69415B2', '1');
INSERT INTO `history` VALUES ('266', '2015-01-16 17:02:57', '2', '044A69415B23', '5');
INSERT INTO `history` VALUES ('267', '2013-08-15 02:01:42', '2', '044A69415B67', '6');
INSERT INTO `history` VALUES ('268', '2014-07-11 01:05:13', '1', '044A69415B64', '2');
INSERT INTO `history` VALUES ('269', '2014-03-12 20:11:58', '2', '044A69415B98', '5');
INSERT INTO `history` VALUES ('270', '2013-02-10 18:22:42', '1', '044A69415B50', '2');
INSERT INTO `history` VALUES ('271', '2014-04-02 03:19:12', '2', '044A69415B8', '4');
INSERT INTO `history` VALUES ('272', '2015-05-23 05:05:20', '0', '044A69415B2', '6');
INSERT INTO `history` VALUES ('273', '2014-09-27 10:56:16', '1', '044A69415B47', '1');
INSERT INTO `history` VALUES ('274', '2014-05-07 15:26:17', '1', '044A69415B85', '1');
INSERT INTO `history` VALUES ('275', '2013-11-12 12:30:39', '1', '044A69415B82', '5');
INSERT INTO `history` VALUES ('276', '2013-09-21 05:13:39', '2', '044A69415B57', '6');
INSERT INTO `history` VALUES ('277', '2014-10-05 19:10:16', '0', '044A69415B83', '3');
INSERT INTO `history` VALUES ('278', '2013-04-24 06:25:18', '2', '044A69415B34', '6');
INSERT INTO `history` VALUES ('279', '2014-02-22 07:03:21', '1', '044A69415B29', '2');
INSERT INTO `history` VALUES ('280', '2015-03-03 05:25:02', '1', '044A69415B90', '4');
INSERT INTO `history` VALUES ('281', '2013-10-03 11:25:44', '2', '044A69415B31', '6');
INSERT INTO `history` VALUES ('282', '2013-11-08 10:35:34', '2', '044A69415B78', '6');
INSERT INTO `history` VALUES ('283', '2013-01-12 06:02:37', '1', '044A69415B86', '3');
INSERT INTO `history` VALUES ('284', '2013-04-24 06:27:53', '2', '044A69415B38', '2');
INSERT INTO `history` VALUES ('285', '2014-05-23 13:58:32', '1', '044A69415B49', '2');
INSERT INTO `history` VALUES ('286', '2013-05-15 00:33:36', '3', '044A69415B17', '2');
INSERT INTO `history` VALUES ('287', '2014-07-28 02:21:55', '3', '044A69415B52', '2');
INSERT INTO `history` VALUES ('288', '2014-12-23 05:49:08', '0', '044A69415B90', '2');
INSERT INTO `history` VALUES ('289', '2013-11-14 23:14:19', '2', '044A69415B48', '6');
INSERT INTO `history` VALUES ('290', '2013-09-05 19:38:43', '3', '044A69415B75', '2');
INSERT INTO `history` VALUES ('291', '2015-01-29 20:21:43', '0', '044A69415B48', '4');
INSERT INTO `history` VALUES ('292', '2014-01-30 16:55:48', '2', '044A69415B20', '3');
INSERT INTO `history` VALUES ('293', '2015-05-11 13:23:03', '2', '044A69415B86', '5');
INSERT INTO `history` VALUES ('294', '2014-05-31 17:32:15', '1', '044A69415B98', '1');
INSERT INTO `history` VALUES ('295', '2014-07-10 03:54:53', '2', '044A69415B49', '3');
INSERT INTO `history` VALUES ('296', '2014-09-24 22:39:18', '1', '044A69415B32', '3');
INSERT INTO `history` VALUES ('297', '2014-06-13 07:41:09', '0', '044A69415B13', '5');
INSERT INTO `history` VALUES ('298', '2014-11-12 00:19:11', '3', '044A69415B100', '4');
INSERT INTO `history` VALUES ('299', '2014-10-03 05:10:00', '0', '044A69415B83', '4');
INSERT INTO `history` VALUES ('300', '2013-06-14 23:17:59', '3', '044A69415B23', '3');
INSERT INTO `history` VALUES ('301', '2014-06-11 08:27:26', '0', '044A69415B69', '3');
INSERT INTO `history` VALUES ('302', '2015-01-23 20:31:02', '2', '044A69415B48', '3');
INSERT INTO `history` VALUES ('303', '2014-08-18 15:09:30', '0', '044A69415B84', '3');
INSERT INTO `history` VALUES ('304', '2013-12-10 10:49:49', '2', '044A69415B51', '1');
INSERT INTO `history` VALUES ('305', '2013-01-08 18:54:26', '0', '044A69415B86', '5');
INSERT INTO `history` VALUES ('306', '2013-10-05 15:10:39', '1', '044A69415B51', '5');
INSERT INTO `history` VALUES ('307', '2013-05-05 23:23:29', '1', '044A69415B28', '5');
INSERT INTO `history` VALUES ('308', '2014-05-06 22:17:17', '0', '044A69415B22', '2');
INSERT INTO `history` VALUES ('309', '2014-09-10 23:49:18', '2', '044A69415B87', '2');
INSERT INTO `history` VALUES ('310', '2013-08-06 17:05:04', '3', '044A69415B10', '6');
INSERT INTO `history` VALUES ('311', '2014-06-12 15:46:56', '1', '044A69415B81', '1');
INSERT INTO `history` VALUES ('312', '2013-08-16 00:28:41', '1', '044A69415B34', '4');
INSERT INTO `history` VALUES ('313', '2013-01-15 18:30:13', '0', '044A69415B37', '5');
INSERT INTO `history` VALUES ('314', '2013-06-07 21:46:18', '0', '044A69415B1', '6');
INSERT INTO `history` VALUES ('315', '2013-12-23 03:29:04', '0', '044A69415B58', '1');
INSERT INTO `history` VALUES ('316', '2013-06-28 08:37:06', '1', '044A69415B72', '6');
INSERT INTO `history` VALUES ('317', '2014-09-13 21:52:24', '2', '044A69415B97', '6');
INSERT INTO `history` VALUES ('318', '2013-07-04 23:49:08', '1', '044A69415B8', '6');
INSERT INTO `history` VALUES ('319', '2015-03-03 07:19:30', '0', '044A69415B66', '2');
INSERT INTO `history` VALUES ('320', '2015-06-11 19:40:08', '2', '044A69415B19', '6');
INSERT INTO `history` VALUES ('321', '2013-02-22 01:28:17', '1', '044A69415B94', '3');
INSERT INTO `history` VALUES ('322', '2015-04-18 06:29:07', '0', '044A69415B88', '2');
INSERT INTO `history` VALUES ('323', '2015-06-16 00:02:22', '0', '044A69415B96', '2');
INSERT INTO `history` VALUES ('324', '2015-05-26 10:07:55', '3', '044A69415B60', '2');
INSERT INTO `history` VALUES ('325', '2013-07-20 19:36:12', '2', '044A69415B22', '1');
INSERT INTO `history` VALUES ('326', '2013-02-04 12:25:58', '0', '044A69415B59', '5');
INSERT INTO `history` VALUES ('327', '2014-07-01 21:22:45', '0', '044A69415B72', '5');
INSERT INTO `history` VALUES ('328', '2013-08-15 11:32:32', '2', '044A69415B50', '4');
INSERT INTO `history` VALUES ('329', '2013-05-20 23:17:11', '3', '044A69415B20', '1');
INSERT INTO `history` VALUES ('330', '2013-06-03 04:11:23', '2', '044A69415B8', '5');
INSERT INTO `history` VALUES ('331', '2014-12-24 14:05:30', '2', '044A69415B81', '4');
INSERT INTO `history` VALUES ('332', '2014-11-03 07:49:43', '2', '044A69415B69', '2');
INSERT INTO `history` VALUES ('333', '2014-08-20 22:58:34', '3', '044A69415B99', '5');
INSERT INTO `history` VALUES ('334', '2013-04-19 01:56:14', '2', '044A69415B32', '1');
INSERT INTO `history` VALUES ('335', '2013-09-09 06:55:48', '2', '044A69415B17', '1');
INSERT INTO `history` VALUES ('336', '2015-02-01 09:07:51', '2', '044A69415B96', '2');
INSERT INTO `history` VALUES ('337', '2013-09-17 11:06:11', '1', '044A69415B26', '4');
INSERT INTO `history` VALUES ('338', '2014-08-19 00:17:05', '3', '044A69415B61', '6');
INSERT INTO `history` VALUES ('339', '2013-05-25 01:43:05', '2', '044A69415B72', '5');
INSERT INTO `history` VALUES ('340', '2014-01-24 15:33:20', '0', '044A69415B56', '6');
INSERT INTO `history` VALUES ('341', '2013-06-15 05:10:35', '2', '044A69415B60', '3');
INSERT INTO `history` VALUES ('342', '2014-11-25 21:35:28', '2', '044A69415B4', '5');
INSERT INTO `history` VALUES ('343', '2014-02-18 10:48:08', '1', '044A69415B20', '5');
INSERT INTO `history` VALUES ('344', '2014-01-24 00:30:27', '0', '044A69415B72', '6');
INSERT INTO `history` VALUES ('345', '2013-01-30 22:41:20', '3', '044A69415B86', '4');
INSERT INTO `history` VALUES ('346', '2014-07-20 02:20:58', '3', '044A69415B59', '6');
INSERT INTO `history` VALUES ('347', '2014-08-03 16:36:45', '0', '044A69415B27', '4');
INSERT INTO `history` VALUES ('348', '2013-02-16 12:48:14', '2', '044A69415B69', '6');
INSERT INTO `history` VALUES ('349', '2014-10-07 08:51:09', '3', '044A69415B89', '5');
INSERT INTO `history` VALUES ('350', '2015-03-03 17:41:58', '0', '044A69415B30', '6');
INSERT INTO `history` VALUES ('351', '2014-05-23 05:31:46', '2', '044A69415B28', '1');
INSERT INTO `history` VALUES ('352', '2014-08-08 20:01:48', '0', '044A69415B63', '2');
INSERT INTO `history` VALUES ('353', '2013-01-19 21:02:26', '1', '044A69415B74', '1');
INSERT INTO `history` VALUES ('354', '2014-03-05 22:06:09', '3', '044A69415B10', '5');
INSERT INTO `history` VALUES ('355', '2015-03-23 07:31:22', '0', '044A69415B89', '3');
INSERT INTO `history` VALUES ('356', '2013-07-12 09:39:10', '2', '044A69415B4', '5');
INSERT INTO `history` VALUES ('357', '2015-04-25 12:33:03', '1', '044A69415B70', '5');
INSERT INTO `history` VALUES ('358', '2014-12-15 05:40:45', '0', '044A69415B2', '2');
INSERT INTO `history` VALUES ('359', '2013-05-06 04:31:33', '3', '044A69415B99', '2');
INSERT INTO `history` VALUES ('360', '2014-08-30 04:17:25', '3', '044A69415B6', '3');
INSERT INTO `history` VALUES ('361', '2014-10-15 16:57:21', '3', '044A69415B54', '4');
INSERT INTO `history` VALUES ('362', '2015-05-21 07:00:06', '3', '044A69415B73', '1');
INSERT INTO `history` VALUES ('363', '2014-04-08 08:10:41', '1', '044A69415B89', '3');
INSERT INTO `history` VALUES ('364', '2013-12-26 10:59:36', '1', '044A69415B32', '4');
INSERT INTO `history` VALUES ('365', '2013-09-20 21:44:00', '2', '044A69415B34', '5');
INSERT INTO `history` VALUES ('366', '2014-05-31 10:47:37', '3', '044A69415B95', '5');
INSERT INTO `history` VALUES ('367', '2014-01-21 20:53:21', '1', '044A69415B4', '2');
INSERT INTO `history` VALUES ('368', '2015-04-30 04:28:35', '2', '044A69415B10', '2');
INSERT INTO `history` VALUES ('369', '2014-01-27 05:09:47', '0', '044A69415B20', '5');
INSERT INTO `history` VALUES ('370', '2014-06-19 15:18:31', '1', '044A69415B9', '2');
INSERT INTO `history` VALUES ('371', '2015-03-02 07:30:38', '3', '044A69415B69', '6');
INSERT INTO `history` VALUES ('372', '2014-01-03 02:50:00', '0', '044A69415B84', '3');
INSERT INTO `history` VALUES ('373', '2013-09-16 07:07:38', '0', '044A69415B98', '5');
INSERT INTO `history` VALUES ('374', '2015-05-31 23:10:42', '2', '044A69415B75', '3');
INSERT INTO `history` VALUES ('375', '2013-10-26 23:05:55', '2', '044A69415B25', '2');
INSERT INTO `history` VALUES ('376', '2013-12-02 22:35:02', '1', '044A69415B87', '4');
INSERT INTO `history` VALUES ('377', '2013-01-04 18:55:18', '3', '044A69415B68', '2');
INSERT INTO `history` VALUES ('378', '2013-09-07 03:30:54', '3', '044A69415B12', '3');
INSERT INTO `history` VALUES ('379', '2015-01-29 22:49:47', '1', '044A69415B43', '4');
INSERT INTO `history` VALUES ('380', '2013-07-03 12:16:49', '2', '044A69415B52', '6');
INSERT INTO `history` VALUES ('381', '2013-11-27 06:40:37', '0', '044A69415B75', '6');
INSERT INTO `history` VALUES ('382', '2013-09-13 18:12:02', '3', '044A69415B10', '1');
INSERT INTO `history` VALUES ('383', '2013-04-13 14:40:25', '1', '044A69415B71', '4');
INSERT INTO `history` VALUES ('384', '2013-07-30 10:58:11', '2', '044A69415B38', '2');
INSERT INTO `history` VALUES ('385', '2013-07-14 16:35:18', '0', '044A69415B42', '6');
INSERT INTO `history` VALUES ('386', '2015-02-26 08:01:29', '0', '044A69415B4', '4');
INSERT INTO `history` VALUES ('387', '2013-12-15 01:13:06', '1', '044A69415B77', '6');
INSERT INTO `history` VALUES ('388', '2013-11-18 13:05:42', '3', '044A69415B4', '4');
INSERT INTO `history` VALUES ('389', '2015-05-07 13:16:21', '1', '044A69415B71', '4');
INSERT INTO `history` VALUES ('390', '2015-05-21 13:59:46', '3', '044A69415B55', '2');
INSERT INTO `history` VALUES ('391', '2014-05-21 18:27:35', '3', '044A69415B11', '6');
INSERT INTO `history` VALUES ('392', '2013-02-28 19:50:29', '0', '044A69415B24', '1');
INSERT INTO `history` VALUES ('393', '2013-06-14 07:56:45', '1', '044A69415B24', '3');
INSERT INTO `history` VALUES ('394', '2013-03-08 14:51:04', '0', '044A69415B32', '3');
INSERT INTO `history` VALUES ('395', '2014-12-14 03:15:43', '1', '044A69415B46', '6');
INSERT INTO `history` VALUES ('396', '2014-05-31 20:44:11', '2', '044A69415B89', '3');
INSERT INTO `history` VALUES ('397', '2014-07-03 22:47:23', '1', '044A69415B68', '2');
INSERT INTO `history` VALUES ('398', '2014-06-14 18:10:05', '3', '044A69415B22', '4');
INSERT INTO `history` VALUES ('399', '2013-11-26 22:47:22', '3', '044A69415B69', '3');
INSERT INTO `history` VALUES ('400', '2013-07-29 10:40:10', '0', '044A69415B62', '2');
INSERT INTO `history` VALUES ('401', '2013-04-15 22:11:50', '1', '044A69415B52', '3');
INSERT INTO `history` VALUES ('402', '2013-12-15 21:03:46', '0', '044A69415B19', '2');
INSERT INTO `history` VALUES ('403', '2014-04-09 08:05:13', '0', '044A69415B2', '6');
INSERT INTO `history` VALUES ('404', '2013-11-28 13:44:29', '0', '044A69415B9', '2');
INSERT INTO `history` VALUES ('405', '2013-04-12 21:36:22', '0', '044A69415B28', '6');
INSERT INTO `history` VALUES ('406', '2015-02-26 22:34:03', '1', '044A69415B89', '1');
INSERT INTO `history` VALUES ('407', '2014-03-20 17:12:11', '3', '044A69415B54', '1');
INSERT INTO `history` VALUES ('408', '2013-06-03 22:29:25', '3', '044A69415B86', '6');
INSERT INTO `history` VALUES ('409', '2014-02-19 05:33:20', '1', '044A69415B79', '2');
INSERT INTO `history` VALUES ('410', '2014-03-20 19:48:41', '1', '044A69415B17', '3');
INSERT INTO `history` VALUES ('411', '2014-05-18 23:15:54', '2', '044A69415B44', '3');
INSERT INTO `history` VALUES ('412', '2015-03-18 11:40:59', '2', '044A69415B28', '4');
INSERT INTO `history` VALUES ('413', '2013-06-13 12:35:42', '0', '044A69415B3', '2');
INSERT INTO `history` VALUES ('414', '2014-07-02 07:56:20', '1', '044A69415B71', '2');
INSERT INTO `history` VALUES ('415', '2015-05-21 00:31:43', '0', '044A69415B69', '2');
INSERT INTO `history` VALUES ('416', '2013-01-04 01:54:42', '0', '044A69415B72', '6');
INSERT INTO `history` VALUES ('417', '2013-03-19 03:02:32', '0', '044A69415B48', '6');
INSERT INTO `history` VALUES ('418', '2015-03-30 21:07:47', '0', '044A69415B70', '1');
INSERT INTO `history` VALUES ('419', '2013-02-08 23:00:55', '0', '044A69415B17', '1');
INSERT INTO `history` VALUES ('420', '2015-03-14 15:22:02', '0', '044A69415B99', '2');
INSERT INTO `history` VALUES ('421', '2013-01-21 07:08:58', '1', '044A69415B33', '4');
INSERT INTO `history` VALUES ('422', '2015-03-02 11:49:41', '2', '044A69415B62', '5');
INSERT INTO `history` VALUES ('423', '2013-11-23 20:42:36', '1', '044A69415B19', '1');
INSERT INTO `history` VALUES ('424', '2013-09-05 13:49:31', '2', '044A69415B67', '5');
INSERT INTO `history` VALUES ('425', '2014-04-30 09:11:50', '2', '044A69415B34', '1');
INSERT INTO `history` VALUES ('426', '2014-11-30 12:05:08', '1', '044A69415B17', '3');
INSERT INTO `history` VALUES ('427', '2015-06-21 14:13:00', '1', '044A69415B82', '3');
INSERT INTO `history` VALUES ('428', '2015-04-29 01:51:14', '3', '044A69415B16', '6');
INSERT INTO `history` VALUES ('429', '2014-01-12 05:54:35', '0', '044A69415B72', '2');
INSERT INTO `history` VALUES ('430', '2014-01-19 12:17:33', '3', '044A69415B51', '4');
INSERT INTO `history` VALUES ('431', '2014-11-30 18:29:10', '2', '044A69415B73', '6');
INSERT INTO `history` VALUES ('432', '2014-03-19 13:26:56', '1', '044A69415B34', '2');
INSERT INTO `history` VALUES ('433', '2014-05-14 01:36:42', '1', '044A69415B88', '5');
INSERT INTO `history` VALUES ('434', '2014-02-26 20:48:42', '2', '044A69415B69', '3');
INSERT INTO `history` VALUES ('435', '2015-04-14 00:04:08', '2', '044A69415B13', '3');
INSERT INTO `history` VALUES ('436', '2014-04-03 05:25:35', '1', '044A69415B75', '4');
INSERT INTO `history` VALUES ('437', '2013-03-16 04:39:02', '0', '044A69415B47', '2');
INSERT INTO `history` VALUES ('438', '2013-11-28 14:48:57', '2', '044A69415B93', '5');
INSERT INTO `history` VALUES ('439', '2013-10-09 05:40:10', '3', '044A69415B41', '6');
INSERT INTO `history` VALUES ('440', '2013-08-11 11:44:31', '0', '044A69415B48', '6');
INSERT INTO `history` VALUES ('441', '2014-06-30 08:51:39', '2', '044A69415B52', '1');
INSERT INTO `history` VALUES ('442', '2013-08-06 03:39:08', '1', '044A69415B53', '2');
INSERT INTO `history` VALUES ('443', '2013-12-23 13:38:49', '3', '044A69415B28', '2');
INSERT INTO `history` VALUES ('444', '2015-04-05 20:28:20', '3', '044A69415B19', '4');
INSERT INTO `history` VALUES ('445', '2013-07-27 19:30:56', '3', '044A69415B95', '2');
INSERT INTO `history` VALUES ('446', '2013-04-09 13:05:57', '3', '044A69415B64', '6');
INSERT INTO `history` VALUES ('447', '2014-09-03 16:20:52', '2', '044A69415B33', '6');
INSERT INTO `history` VALUES ('448', '2015-05-06 16:16:59', '3', '044A69415B37', '5');
INSERT INTO `history` VALUES ('449', '2014-03-15 10:40:17', '0', '044A69415B92', '4');
INSERT INTO `history` VALUES ('450', '2013-01-05 06:12:41', '3', '044A69415B18', '2');
INSERT INTO `history` VALUES ('451', '2014-08-09 19:13:38', '1', '044A69415B83', '1');
INSERT INTO `history` VALUES ('452', '2014-05-09 13:10:41', '2', '044A69415B59', '4');
INSERT INTO `history` VALUES ('453', '2013-07-04 09:01:04', '1', '044A69415B15', '1');
INSERT INTO `history` VALUES ('454', '2014-12-21 08:54:04', '2', '044A69415B33', '3');
INSERT INTO `history` VALUES ('455', '2014-11-29 12:37:25', '1', '044A69415B79', '1');
INSERT INTO `history` VALUES ('456', '2013-03-04 00:18:12', '1', '044A69415B92', '2');
INSERT INTO `history` VALUES ('457', '2015-05-30 06:02:25', '3', '044A69415B31', '3');
INSERT INTO `history` VALUES ('458', '2013-08-22 22:15:46', '3', '044A69415B61', '1');
INSERT INTO `history` VALUES ('459', '2015-05-16 06:21:21', '2', '044A69415B45', '2');
INSERT INTO `history` VALUES ('460', '2013-09-28 23:46:04', '3', '044A69415B41', '4');
INSERT INTO `history` VALUES ('461', '2013-12-04 21:05:48', '0', '044A69415B36', '2');
INSERT INTO `history` VALUES ('462', '2014-02-21 14:24:36', '2', '044A69415B46', '1');
INSERT INTO `history` VALUES ('463', '2013-06-17 18:25:15', '2', '044A69415B32', '1');
INSERT INTO `history` VALUES ('464', '2014-01-22 07:10:52', '0', '044A69415B4', '6');
INSERT INTO `history` VALUES ('465', '2014-03-12 07:00:22', '2', '044A69415B88', '5');
INSERT INTO `history` VALUES ('466', '2014-05-26 00:29:20', '1', '044A69415B6', '3');
INSERT INTO `history` VALUES ('467', '2014-04-17 21:53:32', '0', '044A69415B21', '1');
INSERT INTO `history` VALUES ('468', '2015-01-10 22:37:51', '0', '044A69415B49', '2');
INSERT INTO `history` VALUES ('469', '2014-05-06 00:12:06', '1', '044A69415B9', '4');
INSERT INTO `history` VALUES ('470', '2014-01-21 04:34:00', '2', '044A69415B71', '1');
INSERT INTO `history` VALUES ('471', '2013-07-18 04:59:41', '2', '044A69415B6', '6');
INSERT INTO `history` VALUES ('472', '2013-08-22 17:56:23', '2', '044A69415B15', '2');
INSERT INTO `history` VALUES ('473', '2014-09-08 08:48:58', '3', '044A69415B24', '3');
INSERT INTO `history` VALUES ('474', '2015-01-10 13:51:44', '1', '044A69415B17', '4');
INSERT INTO `history` VALUES ('475', '2014-06-17 04:02:06', '0', '044A69415B63', '6');
INSERT INTO `history` VALUES ('476', '2013-02-24 20:15:24', '3', '044A69415B38', '5');
INSERT INTO `history` VALUES ('477', '2015-05-20 13:56:32', '0', '044A69415B51', '1');
INSERT INTO `history` VALUES ('478', '2014-01-01 10:25:51', '1', '044A69415B16', '2');
INSERT INTO `history` VALUES ('479', '2013-10-09 04:53:41', '3', '044A69415B11', '1');
INSERT INTO `history` VALUES ('480', '2015-02-09 11:17:20', '3', '044A69415B11', '6');
INSERT INTO `history` VALUES ('481', '2014-06-05 04:47:36', '1', '044A69415B9', '4');
INSERT INTO `history` VALUES ('482', '2014-11-10 22:57:37', '3', '044A69415B22', '5');
INSERT INTO `history` VALUES ('483', '2013-11-25 06:07:48', '3', '044A69415B51', '5');
INSERT INTO `history` VALUES ('484', '2015-04-21 01:09:27', '3', '044A69415B59', '2');
INSERT INTO `history` VALUES ('485', '2014-02-24 21:08:30', '3', '044A69415B5', '1');
INSERT INTO `history` VALUES ('486', '2014-11-20 08:41:40', '2', '044A69415B32', '1');
INSERT INTO `history` VALUES ('487', '2014-02-28 01:26:12', '2', '044A69415B71', '6');
INSERT INTO `history` VALUES ('488', '2014-10-31 05:01:53', '2', '044A69415B65', '5');
INSERT INTO `history` VALUES ('489', '2014-03-14 15:53:35', '2', '044A69415B54', '1');
INSERT INTO `history` VALUES ('490', '2013-05-07 00:47:29', '0', '044A69415B77', '5');
INSERT INTO `history` VALUES ('491', '2015-03-01 02:07:12', '0', '044A69415B28', '2');
INSERT INTO `history` VALUES ('492', '2013-01-19 12:36:34', '1', '044A69415B87', '2');
INSERT INTO `history` VALUES ('493', '2013-05-18 09:05:16', '0', '044A69415B24', '4');
INSERT INTO `history` VALUES ('494', '2013-09-02 04:36:35', '0', '044A69415B37', '1');
INSERT INTO `history` VALUES ('495', '2013-03-31 04:23:58', '3', '044A69415B10', '6');
INSERT INTO `history` VALUES ('496', '2014-01-28 03:31:17', '2', '044A69415B5', '2');
INSERT INTO `history` VALUES ('497', '2013-09-19 01:45:19', '1', '044A69415B26', '4');
INSERT INTO `history` VALUES ('498', '2013-01-04 09:28:58', '1', '044A69415B14', '6');
INSERT INTO `history` VALUES ('499', '2013-05-19 15:51:18', '0', '044A69415B44', '2');
INSERT INTO `history` VALUES ('500', '2014-12-19 16:51:34', '0', '044A69415B100', '6');
INSERT INTO `history` VALUES ('501', '2014-04-19 06:57:53', '1', '044A69415B98', '5');
INSERT INTO `history` VALUES ('502', '2014-01-04 16:31:38', '1', '044A69415B6', '1');
INSERT INTO `history` VALUES ('503', '2014-09-27 05:37:01', '0', '044A69415B26', '3');
INSERT INTO `history` VALUES ('504', '2015-03-20 08:28:31', '2', '044A69415B64', '6');
INSERT INTO `history` VALUES ('505', '2014-08-07 10:10:59', '3', '044A69415B84', '3');
INSERT INTO `history` VALUES ('506', '2013-11-24 03:40:44', '1', '044A69415B16', '3');
INSERT INTO `history` VALUES ('507', '2014-12-06 08:57:13', '2', '044A69415B64', '6');

-- ----------------------------
-- Table structure for slots
-- ----------------------------
DROP TABLE IF EXISTS `slots`;
CREATE TABLE `slots` (
  `id` int(11) NOT NULL,
  `drink` varchar(255) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of slots
-- ----------------------------
INSERT INTO `slots` VALUES ('1', 'Cola', '1');
INSERT INTO `slots` VALUES ('2', 'Apfelschorle', '1');
INSERT INTO `slots` VALUES ('3', 'Fanta', '1');
INSERT INTO `slots` VALUES ('4', 'MezzoMix', '1');
INSERT INTO `slots` VALUES ('5', 'Sprite', '1');
INSERT INTO `slots` VALUES ('6', 'Tab', '8');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', 'Demo', 'User', 'test', '1');
INSERT INTO `users` VALUES ('2', 'Dennis', 'Ahrens', 'admin', '1');
INSERT INTO `users` VALUES ('3', 'Fabian', 'Kalkofen', 'ohne Rechte', '4');
INSERT INTO `users` VALUES ('4', 'LangerVorname', 'LangerNachname', 'LangerSpitzname', '1');
INSERT INTO `users` VALUES ('5', 'Norbert', 'Dirks', 'norbäääääärt', '0');
INSERT INTO `users` VALUES ('6', 'Jason', 'Cotton', 'elit. Curabitur', '2');
INSERT INTO `users` VALUES ('7', 'Trevor', 'Clemons', 'luctus', '2');
INSERT INTO `users` VALUES ('8', 'Honorato', 'Hall', 'cursus a,', '3');
INSERT INTO `users` VALUES ('9', 'Jasper', 'Collins', 'magna', '0');
INSERT INTO `users` VALUES ('10', 'Amir', 'Kline', 'nunc sed', '1');
INSERT INTO `users` VALUES ('11', 'Quamar', 'Mercado', 'Mauris', '2');
INSERT INTO `users` VALUES ('12', 'Abraham', 'Cook', 'Suspendisse', '0');
INSERT INTO `users` VALUES ('13', 'Hiram', 'Bruce', 'Maecenas', '1');
INSERT INTO `users` VALUES ('14', 'Dustin', 'Wright', 'laoreet lectus', '1');
INSERT INTO `users` VALUES ('15', 'Theodore', 'Best', 'ante', '2');
INSERT INTO `users` VALUES ('16', 'Zeus', 'Best', 'Integer in', '2');
INSERT INTO `users` VALUES ('17', 'Dorian', 'Combs', 'velit', '3');
INSERT INTO `users` VALUES ('18', 'Barrett', 'Bentley', 'ullamcorper, velit', '0');
INSERT INTO `users` VALUES ('19', 'Cade', 'Cook', 'odio. Etiam', '2');
INSERT INTO `users` VALUES ('20', 'Jacob', 'Kelley', 'in felis.', '0');
INSERT INTO `users` VALUES ('21', 'Austin', 'Joyner', 'consectetuer adipiscing', '1');
INSERT INTO `users` VALUES ('22', 'Gil', 'Fernandez', 'risus varius', '3');
INSERT INTO `users` VALUES ('23', 'Bernard', 'Yang', 'augue eu', '2');
INSERT INTO `users` VALUES ('24', 'Cullen', 'Wells', 'Cras', '3');
INSERT INTO `users` VALUES ('25', 'Graham', 'Campbell', 'pulvinar', '3');
INSERT INTO `users` VALUES ('26', 'Wade', 'Fields', 'turpis', '1');
INSERT INTO `users` VALUES ('27', 'Macon', 'Rich', 'malesuada augue', '2');
INSERT INTO `users` VALUES ('28', 'Edward', 'Vincent', 'magnis dis', '2');
INSERT INTO `users` VALUES ('29', 'Demetrius', 'Evans', 'parturient', '2');
INSERT INTO `users` VALUES ('30', 'Someone', 'Else', 'dummy', '0');
