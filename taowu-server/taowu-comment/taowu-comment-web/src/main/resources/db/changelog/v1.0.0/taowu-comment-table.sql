-- liquibase formatted sql

-- changeset whoiszxl:1
-- comment 初始化评论表结构
DROP TABLE IF EXISTS `cmt_comment`;
CREATE TABLE IF NOT EXISTS `cmt_comment`(
    `id`                        bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `module`                    tinyint(1) NOT NULL COMMENT '所属模块: 1-视频 2-商品',
    `obj_id`                    bigint(11) NOT NULL COMMENT '对象ID: 视频 || 商品',
    `parent_id`                 bigint(11) NOT NULL COMMENT '父评论ID，为 0 则是根评论',
    `reply_id`                  bigint(11) NOT NULL COMMENT '回复评论ID，为 0 则是回复根评论的',
    `member_id`                 bigint(11) NOT NULL COMMENT '用户ID',
    `avatar`                    varchar(255) NOT NULL COMMENT '用户头像',
    `username`                  varchar(32) NOT NULL COMMENT '用户名称',
    `content`                   text COMMENT '评论内容',
    `up_count`                  int(7) UNSIGNED DEFAULT 0 COMMENT '模糊计数的点赞数量',
    `down_count`                int(7) UNSIGNED DEFAULT 0 COMMENT '模糊计数的点踩数量',
    `pic_list`                  text DEFAULT NULL COMMENT '评论图片，多个图片以英文逗号分隔',
    `hot_status`                tinyint(1) UNSIGNED DEFAULT 0 COMMENT '热门状态: 0-非热门 1-是热门',
    `top_status`                tinyint(1) UNSIGNED DEFAULT 0 COMMENT '置顶状态: 0-未置顶 1-已置顶',
    `child_comment_count`       int(8) NOT NULL DEFAULT 0 COMMENT '子评论数',
    `client_ip`                 varchar(16) NOT NULL DEFAULT '0' COMMENT '客户端IP地址',
    `ip_address`                varchar(16) NOT NULL DEFAULT '未知' COMMENT '客户端IP地址所属地',
    `version`                   bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `status`                    tinyint(1) UNSIGNED DEFAULT 1 COMMENT '业务状态',
    `is_deleted`                tinyint(1) UNSIGNED DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                varchar(64) NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`                varchar(64) NOT NULL DEFAULT '' COMMENT '更新者',
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT = '评论主表' ROW_FORMAT = Dynamic;

