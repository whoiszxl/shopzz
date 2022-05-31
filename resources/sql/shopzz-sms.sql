DROP TABLE IF EXISTS `sms_channel`;
CREATE TABLE `sms_channel`(
    `id`                                bigint(11) NOT NULL COMMENT '主键ID',
    `name`                              varchar(64) NOT NULL COMMENT '名称',
    `platform`                          varchar(64) NOT NULL COMMENT '平台',
    `domain`                            varchar(128) NOT NULL COMMENT '域名',
    `access_key_id`                     varchar(128) NOT NULL COMMENT '秘钥id',
    `access_key_secret`                 varchar(128) NOT NULL COMMENT '秘钥密码',
    `other_config`                      json NOT NULL COMMENT '其他配置',
    `level`                             tinyint(1) NOT NULL DEFAULT 1 COMMENT '级别',
    `channel_type`                      tinyint(1) NOT NULL DEFAULT 1 COMMENT '类型: 1-文字 2-语音 3-推送',
    `status`                            tinyint(1) DEFAULT 1 COMMENT '状态(0:无效 1:有效)',
    `version`                           bigint(11) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                        tinyint(1) DEFAULT 0 COMMENT '逻辑删除 1: 已删除, 0: 未删除',
    `created_by`                        varchar(64) NULL COMMENT '创建者',
    `updated_by`                        varchar(64) NULL COMMENT '更新者',
    `created_at`                        datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                        datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT '短信通道表';

DROP TABLE IF EXISTS `sms_channel_signature`;
CREATE TABLE `sms_channel_signature`(
    `id`                                bigint(11) NOT NULL COMMENT '主键ID',
    `channel_id`                        bigint(11) NOT NULL COMMENT '通道主键ID',
    `signature_id`                      bigint(11) NOT NULL COMMENT '签名主键ID',
    `channel_signature_code`            varchar(64) NOT NULL COMMENT '通道签名CPDE',
    `status`                            tinyint(1) DEFAULT 1 COMMENT '状态(0:无效 1:有效)',
    `version`                           bigint(11) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                        tinyint(1) DEFAULT 0 COMMENT '逻辑删除 1: 已删除, 0: 未删除',
    `created_by`                        varchar(64) NULL COMMENT '创建者',
    `updated_by`                        varchar(64) NULL COMMENT '更新者',
    `created_at`                        datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                        datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT '短信通道签名表';

DROP TABLE IF EXISTS `sms_channel_template`;
CREATE TABLE `sms_channel_template`(
    `id`                                bigint(11) NOT NULL COMMENT '主键ID',
    `channel_id`                        bigint(11) NOT NULL COMMENT '通道主键ID',
    `template_id`                       bigint(11) NOT NULL COMMENT '签名主键ID',
    `channel_template_code`             varchar(64) NOT NULL COMMENT '通道模板CODE',
    `status`                            tinyint(1) DEFAULT 1 COMMENT '状态(0:无效 1:有效)',
    `version`                           bigint(11) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                        tinyint(1) DEFAULT 0 COMMENT '逻辑删除 1: 已删除, 0: 未删除',
    `created_by`                        varchar(64) NULL COMMENT '创建者',
    `updated_by`                        varchar(64) NULL COMMENT '更新者',
    `created_at`                        datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                        datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT '短信通道模板表';

DROP TABLE IF EXISTS `sms_platform`;
CREATE TABLE `sms_platform`(
    `id`                                bigint(11) NOT NULL COMMENT '主键ID',
    `name`                              varchar(64) NOT NULL COMMENT '平台名称',
    `access_key_id`                     varchar(128) NOT NULL COMMENT '秘钥id',
    `access_key_secret`                 varchar(128) NOT NULL COMMENT '秘钥密码',
    `ip_address`                        varchar(255) NOT NULL DEFAULT '' COMMENT 'IP绑定,多个以逗号分隔',
    `need_auth`                         tinyint(1) DEFAULT 0 COMMENT '是否需要鉴权: 0-不需要 1-需要',
    `level`                             tinyint(1) NOT NULL DEFAULT 1 COMMENT '级别',
    `status`                            tinyint(1) DEFAULT 1 COMMENT '状态(0:无效 1:有效)',
    `version`                           bigint(11) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                        tinyint(1) DEFAULT 0 COMMENT '逻辑删除 1: 已删除, 0: 未删除',
    `created_by`                        varchar(64) NULL COMMENT '创建者',
    `updated_by`                        varchar(64) NULL COMMENT '更新者',
    `created_at`                        datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                        datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT '短信平台表';

DROP TABLE IF EXISTS `sms_signature`;
CREATE TABLE `sms_signature`(
    `id`                                bigint(11) NOT NULL COMMENT '主键ID',
    `name`                              varchar(64) NOT NULL COMMENT '签名名称',
    `code`                              varchar(64) NOT NULL COMMENT '签名编码',
    `content`                           varchar(64) NOT NULL COMMENT '签名内容',
    `status`                            tinyint(1) DEFAULT 1 COMMENT '状态(0:无效 1:有效)',
    `version`                           bigint(11) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                        tinyint(1) DEFAULT 0 COMMENT '逻辑删除 1: 已删除, 0: 未删除',
    `created_by`                        varchar(64) NULL COMMENT '创建者',
    `updated_by`                        varchar(64) NULL COMMENT '更新者',
    `created_at`                        datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                        datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT '短信签名表';

DROP TABLE IF EXISTS `sms_template`;
CREATE TABLE `sms_template`(
    `id`                                bigint(11) NOT NULL COMMENT '主键ID',
    `name`                              varchar(64) NOT NULL COMMENT '模板名称',
    `code`                              varchar(64) NOT NULL COMMENT '模板编码',
    `content`                           varchar(64) NOT NULL COMMENT '模板内容',
    `type`                              tinyint(1) DEFAULT 1 COMMENT '模板类型: 1-验证码 2-营销',
    `status`                            tinyint(1) DEFAULT 1 COMMENT '状态(0:无效 1:有效)',
    `version`                           bigint(11) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                        tinyint(1) DEFAULT 0 COMMENT '逻辑删除 1: 已删除, 0: 未删除',
    `created_by`                        varchar(64) NULL COMMENT '创建者',
    `updated_by`                        varchar(64) NULL COMMENT '更新者',
    `created_at`                        datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                        datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT '短信模板表';


DROP TABLE IF EXISTS `sms_timing_push`;
CREATE TABLE `sms_timing_push`(
    `id`                                bigint(11) NOT NULL COMMENT '主键ID',
    `template`                          varchar(64) NOT NULL COMMENT '模板',
    `signature`                         varchar(64) NOT NULL COMMENT '签名',
    `mobile`                            varchar(12) NOT NULL COMMENT '手机号',
    `request`                           varchar(512) DEFAULT 1 COMMENT '模板类型: 1-验证码 2-营销',
    `timing`                            datetime COMMENT '发送时间',
    `status`                            tinyint(1) DEFAULT 0 COMMENT '状态: 0-未处理 1-已处理',
    `version`                           bigint(11) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                        tinyint(1) DEFAULT 0 COMMENT '逻辑删除 1: 已删除, 0: 未删除',
    `created_by`                        varchar(64) NULL COMMENT '创建者',
    `updated_by`                        varchar(64) NULL COMMENT '更新者',
    `created_at`                        datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                        datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT '短信定时发送表';



DROP TABLE IF EXISTS `sms_receive_log`;
CREATE TABLE `sms_receive_log`(
    `id`                                bigint(11) NOT NULL COMMENT '主键ID',
    `platform_id`                       bigint(11) NOT NULL COMMENT '平台主键ID',
    `platform_name`                     varchar(64) NOT NULL COMMENT '平台名称',
    `business_info`                     varchar(128) NOT NULL COMMENT '业务信息',
    `channel_ids`                       varchar(512) NOT NULL COMMENT '渠道ID集合',
    `template`                          varchar(64) NOT NULL COMMENT '模板',
    `signature`                         varchar(64) NOT NULL COMMENT '签名',
    `mobile`                            varchar(12) NOT NULL COMMENT '手机号',
    `request`                           varchar(2048) DEFAULT 1 COMMENT '模板类型: 1-验证码 2-营销',
    `error`                             text NOT NULL DEFAULT '' COMMENT '错误信息',
    `time_consuming`                    int(8) NOT NULL COMMENT '耗时',
    `api_log_id`                        bigint(11) NOT NULL COMMENT '日志ID',
    `status`                            tinyint(1) DEFAULT 0 COMMENT '状态: 0-未处理 1-已处理',
    `version`                           bigint(11) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                        tinyint(1) DEFAULT 0 COMMENT '逻辑删除 1: 已删除, 0: 未删除',
    `created_by`                        varchar(64) NULL COMMENT '创建者',
    `updated_by`                        varchar(64) NULL COMMENT '更新者',
    `created_at`                        datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                        datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT '短信接收日志表';


DROP TABLE IF EXISTS `sms_send_log`;
CREATE TABLE `sms_send_log`(
    `id`                                bigint(11) NOT NULL COMMENT '主键ID',
    `channel_id`                        bigint(11) NOT NULL COMMENT '渠道主键ID',
    `channel_platform`                  varchar(64) NOT NULL COMMENT '渠道平台',
    `channel_name`                      varchar(64) NOT NULL COMMENT '渠道名称',
    `template`                          varchar(64) NOT NULL COMMENT '模板',
    `signature`                         varchar(64) NOT NULL COMMENT '签名',
    `mobile`                            varchar(12) NOT NULL COMMENT '手机号',
    `request`                           varchar(2048) DEFAULT '' COMMENT '请求参数',
    `response`                          varchar(2048) DEFAULT '' COMMENT '返回参数',
    `error`                             text NOT NULL DEFAULT '' COMMENT '错误信息',
    `time_consuming`                    int(8) NOT NULL COMMENT '耗时',
    `api_log_id`                        bigint(11) NOT NULL COMMENT '日志ID',
    `status`                            tinyint(1) DEFAULT 0 COMMENT '状态: 0-失败 1-成功',
    `version`                           bigint(11) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                        tinyint(1) DEFAULT 0 COMMENT '逻辑删除 1: 已删除, 0: 未删除',
    `created_by`                        varchar(64) NULL COMMENT '创建者',
    `updated_by`                        varchar(64) NULL COMMENT '更新者',
    `created_at`                        datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                        datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT '短信发送日志表';

DROP TABLE IF EXISTS `sms_manual_task`;
CREATE TABLE `sms_manual_task`(
    `id`                                bigint(11) NOT NULL COMMENT '主键ID',
    `template`                          varchar(64) NOT NULL COMMENT '模板',
    `signature`                         varchar(64) NOT NULL COMMENT '签名',
    `mobile`                            varchar(12) NOT NULL COMMENT '手机号',
    `request`                           varchar(512) DEFAULT '' COMMENT '请求参数',
    `channel_ids`                       varchar(512) NOT NULL COMMENT '渠道ID集合',
    `status`                            tinyint(1) DEFAULT 0 COMMENT '状态: 0-新建 1-处理中 2-处理成功 3-处理失败',
    `version`                           bigint(11) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                        tinyint(1) DEFAULT 0 COMMENT '逻辑删除 1: 已删除, 0: 未删除',
    `created_by`                        varchar(64) NULL COMMENT '创建者',
    `updated_by`                        varchar(64) NULL COMMENT '更新者',
    `created_at`                        datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                        datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT '短信人工处理任务表';


DROP TABLE IF EXISTS `sms_blacklist`;
CREATE TABLE `sms_blacklist`(
    `id`                                bigint(11) NOT NULL COMMENT '主键ID',
    `mobile`                            varchar(20) NOT NULL COMMENT '手机号',
    `remark`                            varchar(64) NOT NULL COMMENT '备注',
    `status`                            tinyint(1) DEFAULT 0 COMMENT '状态',
    `version`                           bigint(11) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                        tinyint(1) DEFAULT 0 COMMENT '逻辑删除 1: 已删除, 0: 未删除',
    `created_by`                        varchar(64) NULL COMMENT '创建者',
    `updated_by`                        varchar(64) NULL COMMENT '更新者',
    `created_at`                        datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                        datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT '短信人工处理任务表';