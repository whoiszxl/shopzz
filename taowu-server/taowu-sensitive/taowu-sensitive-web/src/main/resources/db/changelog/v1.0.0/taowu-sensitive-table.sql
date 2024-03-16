-- liquibase formatted sql

-- changeset whoiszxl:1
-- comment 初始化 sensitive 表结构
DROP TABLE IF EXISTS `common_sensitive_word`;
CREATE TABLE `common_sensitive_word`(
    `id`                        bigint(11) NOT NULL COMMENT '主键ID',
    `keyword`                   varchar(32) NOT NULL COMMENT '敏感词',
    `keyword_type`              tinyint(2) NOT NULL COMMENT '敏感词状态：1: 允许 2：禁止',
    `status`                    tinyint(3) DEFAULT 0 COMMENT '业务状态',
    `version`                   bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT '敏感词表';