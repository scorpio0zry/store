/*
Navicat MySQL Data Transfer

Source Server         : scoprio0zry
Source Server Version : 50549
Source Host           : localhost:3306
Source Database       : store

Target Server Type    : MYSQL
Target Server Version : 50549
File Encoding         : 65001

Date: 2018-02-23 06:59:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart` (
`uid`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`total`  double NULL DEFAULT NULL ,
FOREIGN KEY (`uid`) REFERENCES `user` (`uid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
INDEX `uid` (`uid`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for cartitem
-- ----------------------------
DROP TABLE IF EXISTS `cartitem`;
CREATE TABLE `cartitem` (
`uid`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`num`  int(11) NULL DEFAULT NULL ,
`subtotal`  double NULL DEFAULT NULL ,
`pid`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
FOREIGN KEY (`uid`) REFERENCES `user` (`uid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`pid`) REFERENCES `product` (`pid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
INDEX `uid` (`uid`) USING BTREE ,
INDEX `pid` (`pid`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
`cid`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`cname`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`cid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for orderitem
-- ----------------------------
DROP TABLE IF EXISTS `orderitem`;
CREATE TABLE `orderitem` (
`itemid`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`count`  int(11) NULL DEFAULT NULL ,
`subtotal`  double NULL DEFAULT NULL ,
`pid`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`oid`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`itemid`),
FOREIGN KEY (`pid`) REFERENCES `product` (`pid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`oid`) REFERENCES `orders` (`oid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
INDEX `fk_0001` (`pid`) USING BTREE ,
INDEX `fk_0002` (`oid`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
`oid`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`ordertime`  datetime NULL DEFAULT NULL ,
`total`  double NULL DEFAULT NULL ,
`state`  int(11) NULL DEFAULT NULL ,
`address`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`name`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`telephone`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`uid`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`oid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
`pid`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`pname`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`market_price`  double NULL DEFAULT NULL ,
`shop_price`  double NULL DEFAULT NULL ,
`pimage`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pdate`  date NULL DEFAULT NULL ,
`is_hot`  int(11) NULL DEFAULT NULL ,
`pdesc`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pflag`  int(11) NULL DEFAULT NULL ,
`cid`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`pid`),
FOREIGN KEY (`cid`) REFERENCES `category` (`cid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
INDEX `sfk_0001` (`cid`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
`uid`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`username`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`password`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`name`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`email`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`telephone`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`birthday`  date NULL DEFAULT NULL ,
`sex`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`state`  int(11) NULL DEFAULT NULL ,
`code`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`uid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;
