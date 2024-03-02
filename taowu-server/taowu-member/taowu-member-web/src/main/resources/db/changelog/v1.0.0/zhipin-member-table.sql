-- liquibase formatted sql

-- changeset whoiszxl:1
-- comment 初始化member模块表结构

DROP TABLE IF EXISTS `ums_member`;
CREATE TABLE `ums_member`(
    `id`                                bigint(11) NOT NULL COMMENT '主键ID',
    `phone`                             varchar(16) NOT NULL COMMENT '手机',
    `email`                             varchar(64) DEFAULT '' COMMENT '邮箱(选填)',
    `password`                          varchar(128) DEFAULT '' COMMENT '登录密码',
    `full_name`                         varchar(32) DEFAULT '' COMMENT '全名',
    `work_date`                         datetime DEFAULT NULL COMMENT '参加工作时间',
    `wx_code`                           varchar(64) DEFAULT '' COMMENT '微信号',
    `birthday`                          datetime DEFAULT NULL COMMENT '生日',
    `country`                           varchar(64) DEFAULT '' COMMENT '国家',
    `province`                          varchar(64) DEFAULT '' COMMENT '省份',
    `city`                              varchar(64) DEFAULT '' COMMENT '城市',
    `district`                          varchar(64) DEFAULT '' COMMENT '区域',
    `gender`                            tinyint(2) DEFAULT 3 COMMENT '状态(1:男 2:女 3:未知)',
    `avatar`                            varchar(256) DEFAULT '' COMMENT '头像',
    `ip`                                varchar(256) DEFAULT '' COMMENT 'IP地址',
    `login_count`                       bigint(11) NOT NULL DEFAULT 0 COMMENT '会员登录次数',
    `login_error_count`                 bigint(11) NOT NULL DEFAULT 0 COMMENT '会员登录错误次数',
    `last_login`                        datetime DEFAULT CURRENT_TIMESTAMP COMMENT '最后登录',
    `status`                            tinyint(2) DEFAULT 1 COMMENT '状态(0:无效 1:有效)',
    `version`                           bigint(11) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                        tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除, 0: 未删除',
    `created_at`                        datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                        datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (`id`),
	UNIQUE KEY `idx_phone` (`phone`)
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT '会员表';


DROP TABLE IF EXISTS `ums_member_follower`;
CREATE TABLE `ums_member_follower`(
    `id`                                bigint(11) NOT NULL COMMENT '主键ID',
    `member_id`                         bigint(11) NOT NULL COMMENT '用户ID',
    `follower_id`                       bigint(11) NOT NULL COMMENT '粉丝ID',
    `cancel`                            tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否取消关注: 0-未取消 1-取消',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uni_idx_member_id` (`member_id`)
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT '会员粉丝表';

DROP TABLE IF EXISTS `ums_member_attention`;
CREATE TABLE `ums_member_attention`(
    `id`                                bigint(11) NOT NULL COMMENT '主键ID',
    `member_id`                         bigint(11) NOT NULL COMMENT '用户ID',
    `attention_id`                      bigint(11) NOT NULL COMMENT '被关注者ID',
    `cancel`                            tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否取消关注: 0-未取消 1-取消',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uni_idx_member_id` (`member_id`)
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT '会员关注表';