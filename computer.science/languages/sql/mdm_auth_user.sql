/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50619
 Source Host           : localhost
 Source Database       : mdm_reactor

 Target Server Type    : MySQL
 Target Server Version : 50619
 File Encoding         : utf-8

 Date: 08/24/2014 21:02:21 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `mdm_auth_user`
-- ----------------------------
DROP TABLE IF EXISTS `mdm_auth_user`;
CREATE TABLE `mdm_auth_user` (
  `id` varchar(32) NOT NULL,
  `domain` varchar(64) NOT NULL,
  `password` varchar(255) NOT NULL,
  `status` int(11) NOT NULL,
  `username` varchar(128) NOT NULL,
  `name` varchar(100) DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`,`domain`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `mdm_auth_user`
-- ----------------------------
BEGIN;
INSERT INTO `mdm_auth_user` VALUES ('user_000000000000000000000000001', '30000000', 'x9Jq1zOsVDq4HRn6lhFumgl55OCCUIWWvrwtH4lSeq2oJORwbdxNhmfKRfI1GbdD', '51001', '1::pekall@pekall.com', '京联云');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
