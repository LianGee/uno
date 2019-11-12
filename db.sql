---sample table
CREATE TABLE `user` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `created_at` bigint(11) DEFAULT NULL COMMENT '创建时间',
  `updated_at` bigint(11) DEFAULT NULL COMMENT '更新时间',
  `is_delete` tinyint(3) DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_created_at` (`created_at`),
  KEY `idx_updated_at` (`updated_at`),
  KEY `idx_is_delete` (`is_delete`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表'

--- user
CREATE TABLE `user` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户名（英文）',
  `chineseName` varchar(50) NOT NULL DEFAULT '' COMMENT '中文名',
  `email` varchar(50) NOT NULL DEFAULT '' COMMENT '邮箱',
  `active` tinyint(3) DEFAULT '1' COMMENT '是否在职',
  `password` varchar(20) DEFAULT '' COMMENT '密码',
  `created_at` bigint(11) DEFAULT NULL COMMENT '创建时间',
  `updated_at` bigint(11) DEFAULT NULL COMMENT '更新时间',
  `is_delete` tinyint(3) DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name_uindex` (`name`),
  KEY `idx_created_at` (`created_at`),
  KEY `idx_updated_at` (`updated_at`),
  KEY `idx_is_delete` (`is_delete`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='用户表'

--- projectPoJo
CREATE TABLE `projectPoJo` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) NOT NULL COMMENT '项目名',
  `owner` varchar(1024) DEFAULT '[]' COMMENT '负责人',
  `level` tinyint(3) NOT NULL DEFAULT '0' COMMENT '项目等级',
  `type` tinyint(3) NOT NULL DEFAULT '0' COMMENT '项目类型',
  `language` varchar(128) NOT NULL DEFAULT '[]' COMMENT '语言',
  `business_id` bigint(11) DEFAULT NULL COMMENT '所属业务线',
  `backend_host` varchar(512) NOT NULL DEFAULT '' COMMENT '后端域名',
  `frontend_host` varchar(512) DEFAULT '' COMMENT '前端域',
  `description` varchar(1024) NOT NULL DEFAULT '' COMMENT '项目描述',
  `created_at` bigint(11) DEFAULT NULL COMMENT '创建时间',
  `updated_at` bigint(11) DEFAULT NULL COMMENT '更新时间',
  `is_delete` tinyint(3) DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`),
  KEY `idx_created_at` (`created_at`),
  KEY `idx_updated_at` (`updated_at`),
  KEY `idx_is_delete` (`is_delete`),
  KEY `idk_type` (`type`),
  KEY `idk_business_id` (`business_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='项目'

--- businessPoJo
CREATE TABLE `businessPoJo` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '业务线名称',
  `chinese_name` varchar(128) NOT NULL DEFAULT '' COMMENT '中文名',
  `owner` varchar(500) DEFAULT '[]' COMMENT '业务线负责人',
  `created_at` bigint(11) DEFAULT NULL COMMENT '创建时间',
  `updated_at` bigint(11) DEFAULT NULL COMMENT '更新时间',
  `is_delete` tinyint(3) DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`),
  KEY `idx_created_at` (`created_at`),
  KEY `idx_updated_at` (`updated_at`),
  KEY `idx_is_delete` (`is_delete`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='业务线'
