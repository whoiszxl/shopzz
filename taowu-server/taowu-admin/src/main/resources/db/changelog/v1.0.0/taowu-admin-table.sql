-- liquibase formatted sql

-- changeset whoiszxl:1
-- comment 初始化admin表结构
CREATE TABLE IF NOT EXISTS `sys_admin`(
    `id` int(7)                 NOT NULL AUTO_INCREMENT COMMENT '主键',
    `username`                  varchar(32) NOT NULL COMMENT '账号',
    `password`                  varchar(256) NOT NULL COMMENT '密码',
    `avatar`                    varchar(255) DEFAULT NULL COMMENT '头像',
    `real_name`                 varchar(32) DEFAULT NULL COMMENT '姓名',
    `gender`                    tinyint(1) UNSIGNED DEFAULT 0 COMMENT '性别（0未知 1男 2女）',
    `mobile`                    varchar(16) DEFAULT NULL COMMENT '手机号',
    `email`                     varchar(128) DEFAULT NULL COMMENT '邮箱',
    `google_code`               varchar(32) DEFAULT '' COMMENT '谷歌验证码',
    `google_status`             tinyint(1) UNSIGNED DEFAULT '0' COMMENT '谷歌验证码是否开启，默认不开启 0-不开启； 1-开启',
    `last_login_time`           datetime COMMENT '最后登录时间',
    `version`                   bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `status`                    tinyint(1) UNSIGNED DEFAULT 1 COMMENT '业务状态',
    `is_deleted`                tinyint(1) UNSIGNED DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                varchar(64) NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`                varchar(64) NOT NULL DEFAULT '' COMMENT '更新者',
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uk_username`(`username`) USING BTREE
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT = '管理员表' ROW_FORMAT = Dynamic;

CREATE TABLE IF NOT EXISTS `sys_admin_role`(
  `id` int(7) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int(7) UNSIGNED NOT NULL COMMENT '角色ID',
  `admin_id` int(7) UNSIGNED NOT NULL COMMENT '管理员ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT = '管理员&角色关联表' ROW_FORMAT = Dynamic;


CREATE TABLE IF NOT EXISTS `sys_role`(
    `id`                        int(7) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`                      varchar(32) NOT NULL COMMENT '角色名称',
    `code`                      varchar(32) NOT NULL COMMENT '角色代码',
    `description`               varchar(128) DEFAULT NULL COMMENT '角色描述',
    `version`                   bigint(20) UNSIGNED unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `status`                    tinyint(1) UNSIGNED DEFAULT 1 COMMENT '业务状态',
    `is_deleted`                tinyint(1) UNSIGNED DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                varchar(64) NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`                varchar(64) NOT NULL DEFAULT '' COMMENT '更新者',
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT = '角色' ROW_FORMAT = Dynamic;

CREATE TABLE IF NOT EXISTS `sys_role_menu`(
  `id`              int(7) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id`         int(7) UNSIGNED NOT NULL COMMENT '角色ID',
  `menu_id`         int(7) UNSIGNED NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT = '角色与菜单关联表' ROW_FORMAT = Dynamic;

CREATE TABLE IF NOT EXISTS `sys_menu`(
    `id`                        int(7) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`                      varchar(64) NOT NULL COMMENT '名称',
    `parent_id`                 int(7) UNSIGNED NULL DEFAULT 0 COMMENT '上级菜单ID',
    `type`                      tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '菜单类型 1-目录 2-菜单 3-按钮',
    `path`                      varchar(256) DEFAULT NULL COMMENT '路由地址',
    `component`                 varchar(256) DEFAULT NULL COMMENT '组件路径',
    `query`                     varchar(256) DEFAULT NULL COMMENT '路由参数',
    `is_frame`                  tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否外链 1-是 0-否',
    `is_cache`                  tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '是否缓存 1-是 0-否',
    `visible`                   tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '是否可见 1-是 0-否',
    `permission`                varchar(255) DEFAULT NULL COMMENT '权限标识',
    `icon`                      varchar(255) DEFAULT NULL COMMENT '菜单图标',
    `sort`                      int(7) UNSIGNED NULL DEFAULT NULL COMMENT '排序索引',
    `version`                   bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `status`                    tinyint(1) UNSIGNED DEFAULT 1 COMMENT '业务状态',
    `is_deleted`                tinyint(1) UNSIGNED DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                varchar(64) NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`                varchar(64) NOT NULL DEFAULT '' COMMENT '更新者',
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT = '系统菜单' ROW_FORMAT = Dynamic;


CREATE TABLE IF NOT EXISTS `sys_admin_opt_log` (
    `id`                        bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `admin_name`                varchar(64) DEFAULT '' COMMENT '操作人',
    `request_ip`                varchar(64) DEFAULT '' COMMENT '操作IP',
    `request_uri`               varchar(64) DEFAULT '' COMMENT '请求地址',
    `request_http_method`       varchar(10) DEFAULT 'GET' COMMENT '请求类型\n#HttpMethod{GET:GET请求;POST:POST请求;PUT:PUT请求;DELETE:DELETE请求;PATCH:PATCH请求;TRACE:TRACE请求;HEAD:HEAD请求;OPTIONS:OPTIONS请求;}',
    `request_headers`           text DEFAULT NULL COMMENT '请求头',
    `request_body`              text DEFAULT NULL COMMENT '请求体',
    `status_code`               int(8) unsigned NOT NULL COMMENT '状态码',
    `description`               varchar(255) DEFAULT '' COMMENT '操作描述',
    `class_path`                varchar(255) DEFAULT '' COMMENT '类路径',
    `action_method`             varchar(64) DEFAULT '' COMMENT '请求方法',
    `response_header`           longtext DEFAULT NULL  COMMENT '响应头',
    `response_result`           longtext DEFAULT NULL COMMENT '响应体',
    `ex_desc`                   longtext DEFAULT NULL COMMENT '异常详情信息',
    `ex_detail`                 longtext DEFAULT NULL COMMENT '异常描述',
    `start_time`                timestamp NULL DEFAULT NULL COMMENT '开始时间',
    `finish_time`               timestamp NULL DEFAULT NULL COMMENT '完成时间',
    `consuming_time`            bigint(20) UNSIGNED DEFAULT '0' COMMENT '消耗时间(ms)',
    `ua`                        varchar(500) DEFAULT '' COMMENT '浏览器',
    `version`                   bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `status`                    tinyint(1) UNSIGNED DEFAULT 1 COMMENT '业务状态 1:成功 2:失败',
    `is_deleted`                tinyint(1) UNSIGNED DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                varchar(64) NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`                varchar(64) NOT NULL DEFAULT '' COMMENT '更新者',
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='系统日志';