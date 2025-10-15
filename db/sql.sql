CREATE DATABASE `bus0` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

CREATE TABLE `order` (
                         `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
                         `user_id` bigint unsigned DEFAULT NULL COMMENT '用户id',
                         `order_number` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '订单号',
                         `status` int unsigned DEFAULT NULL COMMENT '订单状态',
                         `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                         `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `sys_dic` (
                           `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
                           `code` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '编码',
                           `value` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '值',
                           `create_time` datetime DEFAULT NULL COMMENT '创建日期',
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='字典表';

CREATE TABLE `user` (
                        `user_id` bigint unsigned NOT NULL COMMENT '用户id',
                        `account` bigint unsigned DEFAULT NULL COMMENT '登录账号',
                        `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                        PRIMARY KEY (`user_id`),
                        UNIQUE KEY `user_user_id_IDX` (`user_id`) USING BTREE,
                        UNIQUE KEY `user_account_IDX` (`account`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

CREATE DATABASE `bus1` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

CREATE TABLE `order` (
                         `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
                         `user_id` bigint unsigned DEFAULT NULL COMMENT '用户id',
                         `order_number` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '订单号',
                         `status` int unsigned DEFAULT NULL COMMENT '订单状态',
                         `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                         `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `sys_dic` (
                           `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
                           `code` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '编码',
                           `value` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '值',
                           `create_time` datetime DEFAULT NULL COMMENT '创建日期',
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='字典表';

CREATE TABLE `user` (
                        `user_id` bigint unsigned NOT NULL COMMENT '用户id',
                        `account` bigint unsigned DEFAULT NULL COMMENT '登录账号',
                        `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                        PRIMARY KEY (`user_id`),
                        UNIQUE KEY `user_user_id_IDX` (`user_id`) USING BTREE,
                        UNIQUE KEY `user_account_IDX` (`account`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';
