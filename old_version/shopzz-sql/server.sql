DROP TABLE IF EXISTS `zxl_server`;
CREATE TABLE `zxl_server` (
    `id`                            int(10) NOT NULL AUTO_INCREMENT COMMENT '服务器主键ID',
    `server_name`                   varchar(100) NOT NULL COMMENT '服务器host名称',
    `server_outer_ip`               varchar(100) NOT NULL COMMENT '服务器外网ip地址',
    `server_inner_ip`               varchar(100) NOT NULL COMMENT '服务器内网ip地址',
    `server_port`                   varchar(100) NOT NULL COMMENT '服务器端口',
    `server_username`               varchar(100) NOT NULL COMMENT '服务器用户名',
    `server_password`               varchar(100) NOT NULL COMMENT '服务器密码',
    `server_config`                 json NOT NULL COMMENT '服务器配置，json存储',
    `dead_time`                     datetime NULL DEFAULT NULL COMMENT '失效时间，如果配置了的话',
    `status`                        tinyint(1) DEFAULT 1 COMMENT '状态：0失效 1有效',
    `created_at`                    datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                    datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务器表';


DROP TABLE IF EXISTS `zxl_config`;
CREATE TABLE `zxl_config` (
    `id`                            int(10) NOT NULL AUTO_INCREMENT COMMENT '配置主键ID',
    `key`                           varchar(100) NOT NULL COMMENT '配置键',
    `value`                         varchar(1000) NOT NULL COMMENT '配置值',
    `status`                        tinyint(1) DEFAULT 1 COMMENT '状态：0失效 1有效',
    `created_at`                    datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                    datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='基础配置表';


DROP TABLE IF EXISTS `zxl_software`;
CREATE TABLE `zxl_software` (
    `id`                            int(10) NOT NULL AUTO_INCREMENT COMMENT '组件主键ID',
    `software_name`                 varchar(300) NOT NULL COMMENT '组件名',
    `software_filename`             varchar(300) NOT NULL COMMENT '组件文件名',
    `software_path`                 varchar(300) NOT NULL COMMENT '组件文件路径',
    `install_path`                  varchar(300) NOT NULL COMMENT '组件安装路径',
    `env_path`                      varchar(300) NOT NULL COMMENT '环境变量路径',
    `env_content`                   varchar(1000) NOT NULL COMMENT '环境变量内容',
    `install_status`                tinyint(1) DEFAULT 1 COMMENT '安装状态：1 未安装 2 部分安装 3 全安装',
    `install_server_ids`            varchar(300) COMMENT '安装了此组件的服务ID集合， 逗号分隔 : 1,2,3',
    `created_at`                    datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                    datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='基础组件表';

DROP TABLE IF EXISTS `zxl_software_config`;
CREATE TABLE `zxl_software_config` (
    `id`                            int(10) NOT NULL AUTO_INCREMENT COMMENT '组件配置文件主键ID',
    `software_name`                 varchar(300) NOT NULL COMMENT '组件文件名',
    `config_name`                   varchar(300) NOT NULL COMMENT '配置文件名',
    `config_path`                   varchar(300) NOT NULL COMMENT '配置文件路径',
    `config_template`               text NOT NULL COMMENT '配置文件模板',
    `config_template_params`        varchar(2000) NOT NULL COMMENT '配置文件模板参数，JSON格式保存，example: {"namenode_port":10000}',
    `install_status`                tinyint(1) DEFAULT 1 COMMENT '安装状态：1 未安装 2 安装成功',
    `created_at`                    datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                    datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='基础组件配置表';


DROP TABLE IF EXISTS `zxl_script`;
CREATE TABLE `zxl_script` (
    `id`                            int(10) NOT NULL AUTO_INCREMENT COMMENT '脚本文件主键ID',
    `script_name`                   varchar(300) NOT NULL COMMENT '脚本名称',
    `script_path`                   varchar(300) NOT NULL COMMENT '脚本路径',
    `script_content`                text NOT NULL COMMENT '脚本内容',
    `script_desc`                   varchar(300) NOT NULL COMMENT '脚本描述',
    `status`                        tinyint(1) DEFAULT 1 COMMENT '状态：1 未同步 2 已同步',
    `created_at`                    datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                    datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='SH脚本表';


DROP TABLE IF EXISTS `zxl_admin`;
CREATE TABLE `zxl_admin`(
  `id`                                  int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username`                            varchar(32) NOT NULL COMMENT '账号',
  `password`                            varchar(256) NOT NULL COMMENT '密码',
  `fullname`                            varchar(32) DEFAULT NULL COMMENT '姓名',
  `mobile`                              varchar(16) DEFAULT NULL COMMENT '手机号',
  `email`                               varchar(128) DEFAULT NULL COMMENT '邮箱',
  `google_code`                         varchar(32) DEFAULT '' COMMENT '谷歌验证码',
  `google_status`                       int(1) DEFAULT '0' COMMENT '谷歌验证码是否开启，默认不开启 0-不开启； 1-开启',
  `status`                              tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态 0-无效； 1-有效；',
  `created_at`                          datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at`                          datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT = '管理员表';

DROP TABLE IF EXISTS `zxl_admin_log`;
CREATE TABLE `zxl_admin_log`(
  `id`                                  int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `admin_id`                            bigint(18) NULL DEFAULT NULL COMMENT '用户Id',
  `type`                                tinyint(1) NULL DEFAULT NULL COMMENT '日志类型 1查询 2修改 3新增 4删除 5导出 6审核',
  `method`                              varchar(255) DEFAULT NULL COMMENT '方法',
  `params`                              text COMMENT '参数',
  `time`                                bigint(20) NULL DEFAULT NULL COMMENT '时间',
  `ip`                                  varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `description`                         varchar(255) DEFAULT NULL COMMENT '描述',
  `created_at`                          datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at`                          datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT = '管理员操作日志表';