---------------------------------------------------
--------------会员模块-----------------------------
---------------------------------------------------
DROP TABLE IF EXISTS `ums_member`;
CREATE TABLE `ums_member`(
    `id`                                bigint(11) NOT NULL COMMENT '主键ID',
    `username`                          varchar(16) NOT NULL COMMENT '会员名',
    `password`                          varchar(100) NOT NULL COMMENT '密码',
    `pay_password`                      varchar(100) DEFAULT '' COMMENT '支付密码',
    `google_key`                        varchar(32) DEFAULT '' COMMENT '谷歌验证码',
    `google_status`                     int(1) DEFAULT '0' COMMENT '谷歌验证码是否开启，默认0不开启, 1开启',
    `real_name`                         varchar(16) DEFAULT '' COMMENT '真实姓名',
    `email`                             varchar(50) DEFAULT '' COMMENT '邮箱',
    `phone`                             varchar(16) DEFAULT '' COMMENT '手机',
    `status`                            tinyint(2) DEFAULT 1 COMMENT '状态(0：无效 1：有效)',
    `version`                           bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                        tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                        varchar(50) NOT NULL COMMENT '创建者',
    `updated_by`                        varchar(50) NOT NULL COMMENT '更新者',
    `created_at`                        datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                        datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (`id`),
	UNIQUE KEY `idx_username` (`username`),
	UNIQUE KEY `idx_phone` (`phone`)
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT '会员表';


DROP TABLE IF EXISTS `ums_member_info`;
CREATE TABLE `ums_member_info`(
    `member_id`                         bigint(11) NOT NULL COMMENT '用户ID',
    `gender`                            tinyint(1) DEFAULT 0 COMMENT '性别(0:未知 1:男；2:女)',
    `birthday`                          datetime DEFAULT NULL COMMENT '生日',
    `country_code`                      varchar(5) DEFAULT '' COMMENT '国家码',
    `country`                           varchar(50) DEFAULT '' COMMENT '国家',
    `province`                          varchar(50) DEFAULT '' COMMENT '省份',
    `city`                              varchar(50) DEFAULT '' COMMENT '城市',
    `district`                          varchar(100) DEFAULT '' COMMENT '区域',
    `grade_level`                       varchar(20) DEFAULT '' COMMENT '会员等级',
    `login_count`                       bigint(11) NOT NULL DEFAULT 0 COMMENT '会员登录次数',
    `login_error_count`                 bigint(11) NOT NULL DEFAULT 0 COMMENT '会员登录错误次数',
    `last_login`                        datetime DEFAULT CURRENT_TIMESTAMP COMMENT '最后登录',
    `version`                           bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                        tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                        varchar(50) NOT NULL COMMENT '创建者',
    `updated_by`                        varchar(50) NOT NULL COMMENT '更新者',
    `created_at`                        datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                        datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (`member_id`)
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT '会员详情表';

DROP TABLE IF EXISTS `ums_member_address`;
CREATE TABLE `ums_member_address` (
    `id`                                bigint(20) NOT NULL COMMENT '主键ID',
    `member_id`                         bigint(20) NOT NULL COMMENT '用户ID',
    `reciver_name`                      varchar(50) NOT NULL COMMENT '收货人',
    `reciver_phone`                     varchar(50) NOT NULL COMMENT '收货人电话号码',
    `province`                          varchar(50) NOT NULL COMMENT '省',
    `city`                              varchar(50) NOT NULL COMMENT '市',
    `district`                          varchar(100) NOT NULL COMMENT '区',
    `detail_address`                    varchar(200) NOT NULL COMMENT '收货地址',
    `is_default`                        tinyint(1) NOT NULL default 0 comment '是否默认 1:默认 2:非默认',
    `version`                           bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                        tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                        varchar(50) NOT NULL COMMENT '创建者',
    `updated_by`                        varchar(50) NOT NULL COMMENT '更新者',
    `created_at`                        datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                        datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_member_id` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员收货地址表';


DROP TABLE IF EXISTS `ums_member_point`;
create table `ums_member_point` (
    `id`                                bigint(20) not null auto_increment comment '主键',
    `member_id`                         bigint(20) not null comment '会员账号ID',
    `point`                             bigint(20) not null comment '会员积分',
    `version`                           bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                        tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                        varchar(50) NOT NULL COMMENT '创建者',
    `updated_by`                        varchar(50) NOT NULL COMMENT '更新者',
    `created_at`                        datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                        datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    primary key (id),
    KEY `idx_member_id` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员积分表';

DROP TABLE IF EXISTS `ums_member_point_log`;
create table `ums_member_point_log`(
    `id`                                bigint(20) not null auto_increment comment '主键',
    `member_point_id`                   bigint(20) not null comment '会员积分ID',
    `before_point`                      bigint(20) not null comment '变动前的会员积分',
    `change_point`                      bigint(20) not null comment '变动的会员积分',
    `after_point`                       bigint(20) not null comment '变动后的会员积分',
    `update_reason`                     varchar(1024) not null comment '本次积分变动的原因',
    `version`                           bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                        tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                        varchar(50) NOT NULL COMMENT '创建者',
    `updated_by`                        varchar(50) NOT NULL COMMENT '更新者',
    `created_at`                        datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                        datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    primary key (id),
    KEY `idx_member_point_id` (`member_point_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员积分变动日志表';
