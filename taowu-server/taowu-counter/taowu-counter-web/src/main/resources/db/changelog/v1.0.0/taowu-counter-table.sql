-- liquibase formatted sql

-- changeset whoiszxl:1
-- comment 初始化 counter 表结构
DROP TABLE IF EXISTS `common_counter`;
CREATE TABLE `common_counter`(
    `id`                        bigint(11) NOT NULL COMMENT '主键ID',
    `dim_type`                  tinyint(2) NOT NULL COMMENT '维度类型: 1-用户维度 2-内容维度 3-标签维度 4-评论维度',
    `obj_type`                  int(5) NOT NULL COMMENT '对象类型: 10-发布内容数 11-被点赞数 12-被收藏数 13-关注数 14-粉丝数 15-点赞内容数 16-收藏内容数 20-内容点赞数 21-内容阅读数 22-内容分享数 23-内容收藏数 24-内容评论数 30-话题内容数 31-特效内容数 32-商品内容数 33-品牌内容数 40-评论点赞数',
    `obj_id`                    bigint(11) NOT NULL COMMENT '对象ID: 用户ID || 视频ID || 评论ID || 其他ID',
    `count_value`               bigint(11) NOT NULL COMMENT '计数值',
    `version`                   bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT '计数表';