-- liquibase formatted sql

-- changeset whoiszxl:1
-- comment 初始化im表结构

DROP TABLE IF EXISTS `im_message`;
CREATE TABLE `im_message` (
    `content_id` bigint(11) NOT NULL COMMENT '消息内容ID',
    `from_member_id` bigint(11) NOT NULL COMMENT '发送用户的ID',
    `to_member_id` bigint(11) NOT NULL COMMENT '接收用户的ID',
    `owner_id` bigint(11) NOT NULL COMMENT '消息所属用户的ID',
    `message_type` tinyint(1) unsigned DEFAULT '1' COMMENT '消息类型',
    `sequence` bigint(20) unsigned NULL COMMENT '消息序列号',
    `status` tinyint(1) unsigned DEFAULT '1' COMMENT '业务状态',
    `version` bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted` tinyint(1) unsigned DEFAULT '0' COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    KEY `owner_id` (`owner_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='消息表';

DROP TABLE IF EXISTS `im_message_content`;
CREATE TABLE `im_message_content` (
    `id` bigint(11) NOT NULL COMMENT '主键',
    `message_content` text COMMENT '实际消息体',
    `extra` text COMMENT '扩展信息',
    `remove_status` tinyint(1) unsigned DEFAULT '1' COMMENT '撤回状态: 1-已撤回 0-未撤回',
    `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='消息内容表';

DROP TABLE IF EXISTS `im_group`;
CREATE TABLE `im_group` (
    `id` bigint(11) NOT NULL COMMENT '主键',
    `group_owner_id` bigint(11) NOT NULL COMMENT '群主ID',
    `group_name` varchar(64) NOT NULL COMMENT '群组名称',
    `group_type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '群组类型',
    `is_mute` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否禁言: 1-不禁言 2-禁言',
    `portrait` varchar(512) NOT NULL DEFAULT '' COMMENT '群组头像',
    `member_count` bigint(20) unsigned NOT NULL COMMENT '当前群成员数',
    `max_member_count` bigint(20) unsigned NOT NULL COMMENT '最大群成员数',
    `notice` varchar(512) NOT NULL DEFAULT '' COMMENT '群公告',
    `join_verification_type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '加群审核类型: 1-任何人可加入 2-需要群主审批 3-禁止任何人加入',
    `extra` text COMMENT '扩展信息',
    `sequence` bigint(20) unsigned NULL COMMENT '序列号',
    `status` tinyint(1) unsigned DEFAULT '1' COMMENT '群组状态 1-正常 2-解散',
    `version` bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted` tinyint(1) unsigned DEFAULT '0' COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='群组表';

DROP TABLE IF EXISTS `im_group_member`;
CREATE TABLE `im_group_member` (
    `id` bigint(11) NOT NULL COMMENT '主键',
    `group_id` bigint(11) NOT NULL COMMENT '群组ID',
    `member_id` bigint(11) NOT NULL COMMENT '用户账号ID',
    `member_type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '成员类型: 1-普通成员 2-群主',
    `alias` varchar(64) NOT NULL DEFAULT '' COMMENT '群昵称',
    `is_mute` tinyint(1) NOT NULL DEFAULT '1' COMMENT '成员是否被禁言: 1-不禁言 2-禁言',
    `join_type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '加群方式: 1-扫码 2-群主邀请',
    `join_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `extra` text COMMENT '扩展信息',
    `sequence` bigint(20) unsigned NULL COMMENT '序列号',
    `version` bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `status` tinyint(1) unsigned DEFAULT '1' COMMENT '群组状态 1-正常 2-解散',
    `is_deleted` tinyint(1) unsigned DEFAULT '0' COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='群组成员表';

DROP TABLE IF EXISTS `im_group_message`;
CREATE TABLE `im_group_message` (
    `id` bigint(11) NOT NULL COMMENT '主键',
    `group_id` bigint(11) NOT NULL COMMENT '群组ID',
    `content_id` bigint(11) NOT NULL COMMENT '消息内容ID',
    `owner_member_id` bigint(11) NOT NULL COMMENT '消息所属用户的ID',
    `message_type` tinyint(1) unsigned DEFAULT '1' COMMENT '消息类型',
    `sequence` bigint(20) unsigned NULL COMMENT '消息序列号',
    `status` tinyint(1) unsigned DEFAULT '1' COMMENT '业务状态',
    `version` bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted` tinyint(1) unsigned DEFAULT '0' COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='群组消息表';


DROP TABLE IF EXISTS `im_friend`;
CREATE TABLE IF NOT EXISTS `im_friend`(
    `from_member_id`            bigint(11) NOT NULL COMMENT '用户自身的ID',
    `to_member_id`              bigint(11) NOT NULL COMMENT '用户好友的ID',
    `remark`                    varchar(64) NOT NULL COMMENT '好友备注',
    `source`                    varchar(64) NOT NULL COMMENT '好友添加来源',
    `friend_sequence`           bigint(20) unsigned NULL COMMENT '好友序列号',
    `extra`                     text NULL COMMENT '扩展信息',
    `version`                   bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `status`                    tinyint(1) UNSIGNED DEFAULT 1 COMMENT '业务状态: 1-正常 2-删除 3-黑名单',
    `is_deleted`                tinyint(1) UNSIGNED DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`from_member_id`, `to_member_id`) USING BTREE
    ) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT = '好友表' ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `im_friend_request`;
CREATE TABLE IF NOT EXISTS `im_friend_request`(
    `id`                        bigint(11) NOT NULL COMMENT '主键',
    `from_member_id`            bigint(11) NOT NULL COMMENT '发起好友申请的用户ID',
    `to_member_id`              bigint(11) NOT NULL COMMENT '被发起好友申请的用户ID',
    `remark`                    varchar(64) NOT NULL COMMENT '好友申请备注',
    `source`                    varchar(64) NOT NULL COMMENT '好友申请来源',
    `friend_verification`       varchar(64) NOT NULL COMMENT '好友验证信息',
    `sequence`                  bigint(20) unsigned NULL COMMENT '序列号',
    `version`                   bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `status`                    tinyint(1) UNSIGNED DEFAULT 1 COMMENT '审批状态: 1-通过 2-拒绝',
    `is_deleted`                tinyint(1) UNSIGNED DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX (`from_member_id`) USING BTREE,
    INDEX (`to_member_id`) USING BTREE
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT = '好友申请表' ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `im_talk`;
CREATE TABLE IF NOT EXISTS `im_talk`(
    `id`                        bigint(11) NOT NULL COMMENT '主键',
    `talk_type`                 tinyint(2) NOT NULL COMMENT '对话类型: 1-单聊 2-群聊 3-ChatGPT 4-机器人',
    `from_member_id`            bigint(11) NOT NULL COMMENT '发起会话的用户ID',
    `from_member_info`          json DEFAULT NULL COMMENT '发起会话的用户信息(字段冗余,避免跨服务查询): 头像|岗位|姓名',
    `to_member_id`              bigint(11) NOT NULL COMMENT '接收会话的用户ID',
    `mute_status`               tinyint(2) NOT NULL COMMENT '静音状态: 0-未静音 1-已静音',
    `top_status`                tinyint(2) NOT NULL COMMENT '置顶状态: 0-未置顶 1-已置顶',
    `read_sequence`             bigint(20) unsigned NULL COMMENT '已读序列号，记录当前用户读到了哪里',
    `sequence`                  bigint(20) unsigned NULL COMMENT '序列号',
    `version`                   bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `status`                    tinyint(2) UNSIGNED DEFAULT 1 COMMENT '审批状态: 1-通过 2-拒绝',
    `is_deleted`                tinyint(2) UNSIGNED DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX (`from_member_id`) USING BTREE,
    INDEX (`to_member_id`) USING BTREE
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT = '对话表' ROW_FORMAT = Dynamic;