CREATE TABLE IF NOT EXISTS `sys_admin` (
                             `id` bigint NOT NULL AUTO_INCREMENT,
                             `mobile` varchar(128) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '手机号',
                             `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '账号状态：1-正常 2-禁用',
                             `role` tinyint NOT NULL COMMENT '角色：1-超级管理员 2-管理员 3-普通账号',
                             `last_login_time` datetime DEFAULT NULL COMMENT '最近登录时间',
                             `can_charge` tinyint(1) NOT NULL COMMENT '是否可充值：0 否 1是',
                             `organization_code` varchar(128) COLLATE utf8mb4_general_ci NOT NULL COMMENT '所属机构编码',
                             `ctime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `utime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                             `create_user_id` bigint DEFAULT NULL COMMENT '创建人',
                             `update_user_id` bigint DEFAULT NULL COMMENT '更新人',
                             `modules` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '所有的模块权限',
                             PRIMARY KEY (`id`),
                             UNIQUE KEY `idx_mobile` (`mobile`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='后台用户';

CREATE TABLE `user_tag_change` (
                                   `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                                   `user_id` bigint NOT NULL,
                                   `original_identity_tag` tinyint(1) NOT NULL COMMENT '原身份标签',
                                   `current_identity_tag` tinyint(1) NOT NULL COMMENT '当前身份标签',
                                   `ctime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                   `utime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                   PRIMARY KEY (`id`),
                                   UNIQUE KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='会员身份标签变更';



ALTER TABLE `user_plateform_info` ADD COLUMN `original_identity_tag` tinyint NULL COMMENT '首次身份标签';

