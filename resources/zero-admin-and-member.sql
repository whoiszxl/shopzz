
---------------------------------------------------
--------------会员模块-----------------------------
---------------------------------------------------
DROP TABLE IF EXISTS `ums_member`;
CREATE TABLE `ums_member`(
    `id`                                int(11) NOT NULL COMMENT '主键ID',
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
    `member_id`                         int(11) NOT NULL COMMENT '用户ID',
    `gender`                            tinyint(1) DEFAULT 0 COMMENT '性别(0:未知 1:男；2:女)',
    `birthday`                          datetime DEFAULT NULL COMMENT '生日',
    `country_code`                      varchar(5) DEFAULT '' COMMENT '国家码',
    `country`                           varchar(50) DEFAULT '' COMMENT '国家',
    `province`                          varchar(50) DEFAULT '' COMMENT '省份',
    `city`                              varchar(50) DEFAULT '' COMMENT '城市',
    `district`                          varchar(100) DEFAULT '' COMMENT '区域',
    `grade_level`                       varchar(20) DEFAULT '' COMMENT '会员等级',
    `login_count`                       int(11) NOT NULL DEFAULT 0 COMMENT '会员登录次数',
    `login_error_count`                 int(11) NOT NULL DEFAULT 0 COMMENT '会员登录错误次数',
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



---------------------------------------------------
--------------系统管理员模块------------------------
---------------------------------------------------


DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`(
  `id` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent_id` bigint(18) NULL DEFAULT NULL COMMENT '上级菜单ID',
  `parent_key` varchar(255) DEFAULT NULL COMMENT '上级菜单唯一KEY值',
  `type` tinyint(4) NOT NULL DEFAULT 2 COMMENT '类型 1-分类 2-节点',
  `name` varchar(64) NOT NULL COMMENT '名称',
  `desc` varchar(256) DEFAULT NULL COMMENT '描述',
  `target_url` varchar(128) DEFAULT NULL COMMENT '目标地址',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序索引',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态 0-无效； 1-有效；',
  `created_by` bigint(18) NULL DEFAULT NULL COMMENT '创建人',
  `updated_by` bigint(18) NULL DEFAULT NULL COMMENT '更新人',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT = '系统菜单' ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `sys_privilege`;
CREATE TABLE `sys_privilege`(
  `id` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `menu_id` bigint(18) NULL DEFAULT NULL COMMENT '所属菜单Id',
  `name` varchar(255) DEFAULT NULL COMMENT '功能点名称',
  `description` varchar(255) DEFAULT NULL COMMENT '功能描述',
  `url` varchar(255) DEFAULT NULL,
  `method` varchar(255) DEFAULT NULL,
  `created_by` bigint(18) NULL DEFAULT NULL COMMENT '创建人',
  `updated_by` bigint(18) NULL DEFAULT NULL COMMENT '更新人',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `unq_name`(`name`(191)) USING BTREE
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT = '权限配置' ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`(
  `id` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) NOT NULL COMMENT '名称',
  `code` varchar(32) NOT NULL COMMENT '代码',
  `description` varchar(128) DEFAULT NULL COMMENT '描述',
  `created_by` bigint(18) NULL DEFAULT NULL COMMENT '创建人',
  `updated_by` bigint(18) NULL DEFAULT NULL COMMENT '更新人',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态0:禁用 1:启用',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT = '角色' ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`(
  `id` bigint(18) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(18) NULL DEFAULT NULL,
  `menu_id` bigint(18) NULL DEFAULT NULL,
  `created_by` bigint(18) NULL DEFAULT NULL COMMENT '创建人',
  `updated_by` bigint(18) NULL DEFAULT NULL COMMENT '更新人',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT = '角色菜单' ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `sys_role_privilege`;
CREATE TABLE `sys_role_privilege`(
  `id` bigint(18) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(18) NOT NULL,
  `privilege_id` bigint(18) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT = '角色权限配置' ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `sys_role_privilege_user`;
CREATE TABLE `sys_role_privilege_user`(
  `id` bigint(18) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(18) NOT NULL COMMENT '角色Id',
  `user_id` bigint(18) NOT NULL COMMENT '用户Id',
  `privilege_id` bigint(18) NOT NULL COMMENT '权限Id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `pk_role_id_user_id_privilege_id`(`role_id`, `user_id`, `privilege_id`) USING BTREE
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT = '用户权限配置' ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`(
  `id` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(32) NOT NULL COMMENT '账号',
  `password` varchar(256) NOT NULL COMMENT '密码',
  `fullname` varchar(32) DEFAULT NULL COMMENT '姓名',
  `mobile` varchar(16) DEFAULT NULL COMMENT '手机号',
  `email` varchar(128) DEFAULT NULL COMMENT '邮箱',
  `google_code` varchar(32) DEFAULT '' COMMENT '谷歌验证码',
  `google_status` int(1) DEFAULT '0' COMMENT '谷歌验证码是否开启，默认不开启 0-不开启； 1-开启',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态 0-无效； 1-有效；',
  `created_by` bigint(18) NULL DEFAULT NULL COMMENT '创建人',
  `updated_by` bigint(18) NULL DEFAULT NULL COMMENT '更新人',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT = '平台用户' ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `sys_user_log`;
CREATE TABLE `sys_user_log`(
  `id` bigint(18) NOT NULL COMMENT '主键',
  `group` varchar(255) DEFAULT NULL COMMENT '组',
  `user_id` bigint(18) NULL DEFAULT NULL COMMENT '用户Id',
  `type` smallint(4) NULL DEFAULT NULL COMMENT '日志类型 1查询 2修改 3新增 4删除 5导出 6审核',
  `method` varchar(255) DEFAULT NULL COMMENT '方法',
  `params` text COMMENT '参数',
  `time` bigint(20) NULL DEFAULT NULL COMMENT '时间',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT = '系统日志' ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`(
  `id` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` bigint(18) NULL DEFAULT NULL COMMENT '角色ID',
  `user_id` bigint(18) NULL DEFAULT NULL COMMENT '用户ID',
  `created_by` bigint(18) NULL DEFAULT NULL COMMENT '创建人',
  `updated_by` bigint(18) NULL DEFAULT NULL COMMENT '更新人',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT = '用户角色配置' ROW_FORMAT = Dynamic;