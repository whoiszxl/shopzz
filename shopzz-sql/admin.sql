
---------------------------------------------------
--------------系统管理员模块------------------------
---------------------------------------------------
DROP TABLE IF EXISTS `sys_admin`;
CREATE TABLE `sys_admin`(
    `id` int(7)                 NOT NULL AUTO_INCREMENT COMMENT '主键',
    `username`                  varchar(32) NOT NULL COMMENT '账号',
    `password`                  varchar(256) NOT NULL COMMENT '密码',
    `fullname`                  varchar(32) DEFAULT NULL COMMENT '姓名',
    `mobile`                    varchar(16) DEFAULT NULL COMMENT '手机号',
    `email`                     varchar(128) DEFAULT NULL COMMENT '邮箱',
    `google_code`               varchar(32) DEFAULT '' COMMENT '谷歌验证码',
    `google_status`             int(1) DEFAULT '0' COMMENT '谷歌验证码是否开启，默认不开启 0-不开启； 1-开启',
    `last_login_time`           datetime COMMENT '最后登录时间',
    `version`                   bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `status`                    tinyint(3) DEFAULT 0 COMMENT '业务状态',
    `is_deleted`                tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                varchar(50) NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`                varchar(50) NOT NULL DEFAULT '' COMMENT '更新者',
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT = '管理员表' ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `sys_admin_role`;
CREATE TABLE `sys_admin_role`(
  `id` int(7) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int(7) NOT NULL COMMENT '角色ID',
  `admin_id` int(7) NOT NULL COMMENT '管理员ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT = '管理员&角色关联表' ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`(
    `id`                        int(7) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`                      varchar(32) NOT NULL COMMENT '角色名称',
    `code`                      varchar(32) NOT NULL COMMENT '角色代码',
    `description`               varchar(128) DEFAULT NULL COMMENT '角色描述',
    `version`                   bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `status`                    tinyint(3) DEFAULT 0 COMMENT '业务状态',
    `is_deleted`                tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                varchar(50) NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`                varchar(50) NOT NULL DEFAULT '' COMMENT '更新者',
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT = '角色' ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `sys_role_authority`;
CREATE TABLE `sys_role_authority`(
  `id`              int(7) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id`         int(7) NOT NULL COMMENT '角色ID',
  `authority_type`  tinyint(1) NOT NULL COMMENT '权限类型 1:菜单 2:权限',
  `authority_id`    int(7) NOT NULL COMMENT '权限或菜单ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT = '角色&(权限|菜单)关联表' ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`(
    `id`                        int(7) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `parent_id`                 int(7) NULL DEFAULT NULL COMMENT '上级菜单ID',
    `name`                      varchar(64) NOT NULL COMMENT '名称',
    `description`               varchar(256) DEFAULT NULL COMMENT '描述',
    `target_url`                varchar(128) DEFAULT NULL COMMENT '目标地址',
    `sort`                      int(7) NULL DEFAULT NULL COMMENT '排序索引',
    `version`                   bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `status`                    tinyint(3) DEFAULT 0 COMMENT '业务状态',
    `is_deleted`                tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                varchar(50) NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`                varchar(50) NOT NULL DEFAULT '' COMMENT '更新者',
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT = '系统菜单' ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `sys_privilege`;
CREATE TABLE `sys_privilege`(
    `id`                        int(7) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `menu_id`                   int(7) NULL DEFAULT NULL COMMENT '所属菜单Id',
    `name`                      varchar(128) DEFAULT NULL COMMENT '权限名称',
    `code`                      varchar(128) DEFAULT NULL COMMENT '权限编码',
    `description`               varchar(255) DEFAULT NULL COMMENT '功能描述',
    `url`                       varchar(255) DEFAULT NULL COMMENT '请求接口URL',
    `method`                    varchar(255) DEFAULT NULL COMMENT '接口HTTP请求方式',
    `version`                   bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `status`                    tinyint(3) DEFAULT 0 COMMENT '业务状态',
    `is_deleted`                tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                varchar(50) NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`                varchar(50) NOT NULL DEFAULT '' COMMENT '更新者',
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `unq_name`(`name`)
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT = '权限配置' ROW_FORMAT = Dynamic;



DROP TABLE IF EXISTS `sys_admin_login_log`;
CREATE TABLE `sys_admin_login_log` (
    `id`                        bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `request_ip`                varchar(50) DEFAULT '' COMMENT '操作IP',
    `admin_id`                  bigint(20) DEFAULT NULL COMMENT '登录人ID',
    `admin_name`                varchar(50) DEFAULT NULL COMMENT '登录人姓名',
    `account`                   varchar(30) DEFAULT '' COMMENT '登录人账号',
    `description`               varchar(255) DEFAULT '' COMMENT '登录描述',
    `login_date`                date DEFAULT NULL COMMENT '登录时间',
    `ua`                        varchar(500) DEFAULT '0' COMMENT '浏览器请求头',
    `browser`                   varchar(100) DEFAULT NULL COMMENT '浏览器名称',
    `browser_version`           varchar(255) DEFAULT NULL COMMENT '浏览器版本',
    `operating_system`          varchar(100) DEFAULT NULL COMMENT '操作系统',
    `location`                  varchar(50) DEFAULT '' COMMENT '登录地点',
    `version`                   bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `status`                    tinyint(3) DEFAULT 0 COMMENT '业务状态',
    `is_deleted`                tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                varchar(50) NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`                varchar(50) NOT NULL DEFAULT '' COMMENT '更新者',
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `IDX_BROWSER` (`browser`) USING BTREE,
  KEY `IDX_OPERATING` (`operating_system`) USING BTREE,
  KEY `IDX_LOGIN_DATE` (`login_date`,`account`) USING BTREE,
  KEY `IDX_ACCOUNT_IP` (`account`,`request_ip`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='登录日志';


DROP TABLE IF EXISTS `sys_admin_opt_log`;
CREATE TABLE `sys_admin_opt_log` (
    `id`                        bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `request_ip`                varchar(50) DEFAULT '' COMMENT '操作IP',
    `type`                      varchar(5) DEFAULT 'OPT' COMMENT '日志类型\n#LogType{OPT:操作类型;EX:异常类型}',
    `admin_name`                varchar(50) DEFAULT '' COMMENT '操作人',
    `description`               varchar(255) DEFAULT '' COMMENT '操作描述',
    `class_path`                varchar(255) DEFAULT '' COMMENT '类路径',
    `action_method`             varchar(50) DEFAULT '' COMMENT '请求方法',
    `request_uri`               varchar(50) DEFAULT '' COMMENT '请求地址',
    `http_method`               varchar(10) DEFAULT 'GET' COMMENT '请求类型\n#HttpMethod{GET:GET请求;POST:POST请求;PUT:PUT请求;DELETE:DELETE请求;PATCH:PATCH请求;TRACE:TRACE请求;HEAD:HEAD请求;OPTIONS:OPTIONS请求;}',
    `params`                    longtext COMMENT '请求参数',
    `result`                    longtext COMMENT '返回值',
    `ex_desc`                   longtext COMMENT '异常详情信息',
    `ex_detail`                 longtext COMMENT '异常描述',
    `start_time`                timestamp NULL DEFAULT NULL COMMENT '开始时间',
    `finish_time`               timestamp NULL DEFAULT NULL COMMENT '完成时间',
    `consuming_time`            bigint(20) DEFAULT '0' COMMENT '消耗时间',
    `ua`                        varchar(500) DEFAULT '' COMMENT '浏览器',
    `version`                   bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `status`                    tinyint(3) DEFAULT 0 COMMENT '业务状态',
    `is_deleted`                tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                varchar(50) NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`                varchar(50) NOT NULL DEFAULT '' COMMENT '更新者',
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `index_type` (`type`) USING BTREE COMMENT '日志类型'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='系统日志';