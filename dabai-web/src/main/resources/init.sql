/*
 Navicat Premium Data Transfer

 Source Server         : daibai
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : bj-cdb-867r0ta8.sql.tencentcdb.com:60697
 Source Schema         : dabai-test

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 30/05/2022 21:14:03
*/
-- ----------------------------
-- Table structure for cash_snapshot
-- ----------------------------
DROP TABLE IF EXISTS `cash_snapshot`;
CREATE TABLE `cash_snapshot` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '会员平台userId',
  `request_no` varchar(255) DEFAULT '' COMMENT '商户下发请求单号',
  `deal_no` varchar(255) DEFAULT '' COMMENT '处理单号',
  `name` varchar(128) NOT NULL DEFAULT '' COMMENT '用户名',
  `id_card` varchar(255) NOT NULL DEFAULT '' COMMENT '身份证',
  `mobile` varchar(128) NOT NULL DEFAULT '' COMMENT '手机号',
  `bank_card` varchar(255) NOT NULL DEFAULT '' COMMENT '银行卡',
  `sign_source` varchar(128) DEFAULT '' COMMENT '签约主体：FC/ZX',
  `business_source` varchar(128) DEFAULT '' COMMENT '下发公司：RFT/XDB',
  `status` tinyint DEFAULT NULL COMMENT '提现状态:1-成功、2-失败、3-提现中',
  `cashed_amount` decimal(10,2) DEFAULT NULL COMMENT '提现金额',
  `third_response` text COMMENT '三方接口返回结果',
  `remark` varchar(255) DEFAULT '' COMMENT '备注，失败原因等',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP,
  `utime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`) USING BTREE,
  KEY `idx_request_no` (`request_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='提现快照';

-- ----------------------------
-- Table structure for policy_info
-- ----------------------------
DROP TABLE IF EXISTS `policy_info`;
CREATE TABLE `policy_info` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '会员平台id',
  `order_id` varchar(128) DEFAULT '' COMMENT '订单id',
  `policy_status` tinyint DEFAULT NULL COMMENT '状态：（0已退款1已完成2已失效）',
  `commission_amount` decimal(10,2) DEFAULT NULL COMMENT '提成',
  `policy_no` varchar(128) DEFAULT '' COMMENT '保单号',
  `product_code` varchar(255) DEFAULT '' COMMENT '产品编码',
  `product_name` varchar(255) DEFAULT '' COMMENT '产品名称',
  `insure_name` varchar(255) DEFAULT '' COMMENT '投保人姓名',
  `assured_name` varchar(255) DEFAULT '' COMMENT '被保人姓名',
  `ensure_time` int DEFAULT NULL COMMENT '保障期间（年）',
  `premium` decimal(10,2) DEFAULT NULL COMMENT '保费（元）',
  `start_date` varchar(128) DEFAULT NULL COMMENT '投保起期 yyyy-MM-dd',
  `end_date` varchar(128) DEFAULT NULL COMMENT '投保止期 yyyy-MM-dd',
  `ele_policy_addr` varchar(255) DEFAULT '' COMMENT '电子保单链接',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`) USING BTREE,
  KEY `idx_policy_no` (`policy_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='保单表';

-- ----------------------------
-- Table structure for product_info
-- ----------------------------
DROP TABLE IF EXISTS `product_info`;
CREATE TABLE `product_info` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(128) DEFAULT '' COMMENT '产品编码',
  `name` varchar(255) DEFAULT '' COMMENT '产品名称',
  `label` varchar(128) DEFAULT '' COMMENT '标签：热销爆款',
  `img_url` varchar(255) DEFAULT '' COMMENT '图片地址',
  `h5_url` varchar(255) DEFAULT '' COMMENT '产品跳转链接',
  `min_amount` decimal(10,3) DEFAULT NULL COMMENT '最小起订金额(元)',
  `brief_introduction` varchar(255) DEFAULT '' COMMENT '产品简介',
  `category` varchar(128) DEFAULT '' COMMENT '保险品类：车险/财产险',
  `commission_radio` decimal(10,3) DEFAULT NULL COMMENT '推广费比例',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP,
  `utime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `valid` tinyint DEFAULT '1' COMMENT '0:废弃；1：可用',
  PRIMARY KEY (`id`),
  KEY `idx_code` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nick_name` varchar(128) DEFAULT '' COMMENT '昵称',
  `open_id` varchar(128) NOT NULL DEFAULT '',
  `mobile` varchar(128) NOT NULL DEFAULT '' COMMENT '手机号',
  `gender` varchar(64) DEFAULT '' COMMENT '性别',
  `city` varchar(64) DEFAULT '' COMMENT '城市',
  `province` varchar(64) DEFAULT '' COMMENT '省份',
  `country` varchar(64) DEFAULT '' COMMENT '国家',
  `avatar_url` varchar(256) DEFAULT '' COMMENT '头像',
  `union_id` varchar(128) DEFAULT '',
  `id_card` varchar(128) DEFAULT '' COMMENT '身份证',
  `bank_card` varchar(128) DEFAULT '' COMMENT '银行卡号',
  `name` varchar(128) DEFAULT '' COMMENT '姓名',
  `bank_name` varchar(128) DEFAULT '' COMMENT '开户行',
  `parent_user_id` bigint DEFAULT NULL COMMENT '推广人',
  `valid` int DEFAULT NULL COMMENT '是否有效账号',
  `ctime` datetime DEFAULT NULL,
  `utime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_open_id` (`open_id`) USING BTREE,
  KEY `idx_mobile` (`mobile`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户基本信息表';

-- ----------------------------
-- Table structure for user_plateform_info
-- ----------------------------
DROP TABLE IF EXISTS `user_plateform_info`;
CREATE TABLE `user_plateform_info` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '会员平台id',
  `code` varchar(128) DEFAULT '' COMMENT '华保星会员编码',
  `plateform` varchar(128) DEFAULT '' COMMENT '用户所属平台：华保星用户',
  `identity_tag` tinyint DEFAULT NULL COMMENT '华保星身份标签：1：会员 2：代理人',
  `organization_code` varchar(128) DEFAULT '' COMMENT '所属机构编码：10北京',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP,
  `utime` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_user_id` (`user_id`),
  UNIQUE KEY `idx_code_plateform` (`code`,`plateform`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户平台信息';

-- ----------------------------
-- Table structure for user_sign_info
-- ----------------------------
DROP TABLE IF EXISTS `user_sign_info`;
CREATE TABLE `user_sign_info` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '会员平台id',
  `sign_source` varchar(64) NOT NULL DEFAULT '' COMMENT '签约主体：FC/ZX',
  `business_source` varchar(64) NOT NULL DEFAULT '' COMMENT '下发公司：RFT/XDB',
  `sign_status` tinyint DEFAULT NULL COMMENT '签约状态 1 成功 2失败 3 签约中',
  `sign_deal_no` varchar(128) DEFAULT '' COMMENT '签约流水号',
  `area` varchar(64) DEFAULT '' COMMENT '城市地区',
  `remark` varchar(512) DEFAULT '' COMMENT '备注失败原因',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP,
  `utime` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户签约信息';

-- ----------------------------
-- Table structure for wallet_flow
-- ----------------------------
DROP TABLE IF EXISTS `wallet_flow`;
CREATE TABLE `wallet_flow` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '会员平台id',
  `wallet_id` bigint NOT NULL COMMENT '钱包id',
  `amount` decimal(10,2) DEFAULT NULL COMMENT '金额（收入/提现/退款）',
  `flow_type` tinyint DEFAULT NULL COMMENT '类型：1：收入 2：提现 3: 退款',
  `policy_no` varchar(128) DEFAULT '' COMMENT '保单编号',
  `cash_request_no` varchar(128) DEFAULT '' COMMENT '提现请求单号',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP,
  `utime` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='钱包流水';

-- ----------------------------
-- Table structure for wallet_info
-- ----------------------------
DROP TABLE IF EXISTS `wallet_info`;
CREATE TABLE `wallet_info` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL DEFAULT '-1' COMMENT '会员平台id',
  `available_amount` decimal(10,3) NOT NULL DEFAULT '0.000' COMMENT '可用金额',
  `cashed_amount` decimal(10,3) NOT NULL DEFAULT '0.000' COMMENT '已提现金额',
  `total_amount` decimal(10,3) NOT NULL DEFAULT '0.000' COMMENT '累计收入',
  `last_cash_time` datetime DEFAULT NULL COMMENT '上一次提现时间',
  `ctime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `utime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `cashing_amount` decimal(10,3) NOT NULL DEFAULT '0.000' COMMENT '提现中金额',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='钱包表';
