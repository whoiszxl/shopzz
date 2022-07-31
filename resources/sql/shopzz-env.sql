DROP TABLE IF EXISTS `env_server`;
CREATE TABLE `env_server` (
    `id`                            int(10) NOT NULL AUTO_INCREMENT COMMENT '服务器主键ID',
    `instance_id`                   varchar(100) NOT NULL COMMENT '服务实例ID',
    `server_name`                   varchar(100) NOT NULL COMMENT '服务器host名称',
    `server_outer_ip`               varchar(100) NOT NULL COMMENT '服务器外网ip地址',
    `server_inner_ip`               varchar(100) NOT NULL COMMENT '服务器内网ip地址',
    `server_port`                   varchar(100) NOT NULL COMMENT '服务器端口',
    `server_username`               varchar(100) DEFAULT NULL COMMENT '服务器用户名',
    `server_password`               varchar(100) DEFAULT NULL COMMENT '服务器密码',
    `server_detail`                 json DEFAULT NULL COMMENT '服务器详情',
    `version`                       bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `status`                        tinyint(3) DEFAULT 0 COMMENT '业务状态',
    `is_deleted`                    tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                    varchar(64) NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`                    varchar(64) NOT NULL DEFAULT '' COMMENT '更新者',
    `created_at`                    datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                    datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务器表';


DROP TABLE IF EXISTS `env_config`;
CREATE TABLE `env_config` (
    `id`                            int(10) NOT NULL AUTO_INCREMENT COMMENT '配置主键ID',
    `config_key`                    varchar(100) NOT NULL COMMENT '配置键',
    `config_value`                  varchar(1000) NOT NULL COMMENT '配置值',
    `version`                       bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `status`                        tinyint(3) DEFAULT 0 COMMENT '业务状态',
    `is_deleted`                    tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                    varchar(64) NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`                    varchar(64) NOT NULL DEFAULT '' COMMENT '更新者',
    `created_at`                    datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                    datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='基础配置表';


DROP TABLE IF EXISTS `env_software`;
CREATE TABLE `env_software` (
    `id`                            int(10) NOT NULL AUTO_INCREMENT COMMENT '组件主键ID',
    `software_name`                 varchar(300) NOT NULL COMMENT '组件名',
    `software_filename`             varchar(300) NOT NULL COMMENT '组件文件名',
    `software_path`                 varchar(300) NOT NULL COMMENT '组件文件路径',
    `install_path`                  varchar(300) NOT NULL COMMENT '组件安装路径',
    `env_path`                      varchar(300) NOT NULL COMMENT '环境变量路径',
    `env_content`                   varchar(1000) NOT NULL COMMENT '环境变量内容',
    `install_status`                tinyint(1) DEFAULT 1 COMMENT '安装状态：1 未安装 2 部分安装 3 全安装',
    `install_server_ids`            varchar(300) COMMENT '安装了此组件的服务ID集合， 逗号分隔 : 1,2,3',
    `version`                       bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `status`                        tinyint(3) DEFAULT 0 COMMENT '业务状态',
    `is_deleted`                    tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                    varchar(64) NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`                    varchar(64) NOT NULL DEFAULT '' COMMENT '更新者',
    `created_at`                    datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                    datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='基础组件表';

DROP TABLE IF EXISTS `env_software_config`;
CREATE TABLE `env_software_config` (
    `id`                            int(10) NOT NULL AUTO_INCREMENT COMMENT '组件配置文件主键ID',
    `software_name`                 varchar(300) NOT NULL COMMENT '组件文件名',
    `config_name`                   varchar(300) NOT NULL COMMENT '配置文件名',
    `config_path`                   varchar(300) NOT NULL COMMENT '配置文件路径',
    `config_template`               text NOT NULL COMMENT '配置文件模板',
    `config_template_params`        varchar(2000) NOT NULL COMMENT '配置文件模板参数，JSON格式保存，example: {"namenode_port":10000}',
    `install_status`                tinyint(1) DEFAULT 1 COMMENT '安装状态：1 未安装 2 安装成功',
    `version`                       bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `status`                        tinyint(3) DEFAULT 0 COMMENT '业务状态',
    `is_deleted`                    tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                    varchar(64) NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`                    varchar(64) NOT NULL DEFAULT '' COMMENT '更新者',
    `created_at`                    datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                    datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='基础组件配置表';


DROP TABLE IF EXISTS `env_script`;
CREATE TABLE `env_script` (
    `id`                            int(10) NOT NULL AUTO_INCREMENT COMMENT '脚本文件主键ID',
    `script_name`                   varchar(300) NOT NULL COMMENT '脚本名称',
    `script_path`                   varchar(300) NOT NULL COMMENT '脚本路径',
    `script_content`                text NOT NULL COMMENT '脚本内容',
    `script_desc`                   varchar(300) NOT NULL COMMENT '脚本描述',
    `version`                       bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `status`                        tinyint(3) DEFAULT 0 COMMENT '业务状态',
    `is_deleted`                    tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                    varchar(64) NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`                    varchar(64) NOT NULL DEFAULT '' COMMENT '更新者',
    `created_at`                    datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                    datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='SH脚本表';


DROP TABLE IF EXISTS `env_table_process`;
CREATE TABLE `env_table_process` (
    `id`                            int(10) NOT NULL AUTO_INCREMENT COMMENT '脚本文件主键ID',
    `source_table`                  varchar(128) NOT NULL COMMENT '来源表',
    `operate_type`                  varchar(16) NOT NULL COMMENT '操作类型:insert,update,delete',
    `sink_type`                     varchar(16) NOT NULL COMMENT '输出类型:hbase,kafka',
    `sink_table`                    varchar(300) NOT NULL COMMENT '输出表或topic',
    `sink_columns`                  varchar(300) NOT NULL COMMENT '输出字段',
    `sink_pk`                       varchar(300) NOT NULL COMMENT '主键字段',
    `sink_extend`                   varchar(300) NOT NULL COMMENT '建表扩展',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='表动态分流处理表';
