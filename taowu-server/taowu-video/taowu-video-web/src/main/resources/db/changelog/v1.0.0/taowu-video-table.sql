-- liquibase formatted sql

-- changeset whoiszxl:1
-- comment 初始化video表结构

DROP TABLE IF EXISTS `vms_video`;
CREATE TABLE `vms_video`(
    `id`                        bigint(11) NOT NULL COMMENT '视频主键ID',
    `member_id`                 bigint(11) NOT NULL COMMENT '会员ID',
    `member_username`           varchar(32) NOT NULL COMMENT '用户名称',
    `member_avatar`             varchar(256) DEFAULT '' COMMENT '头像',
    `descs`                     varchar(140) NOT NULL COMMENT '文字内容',
    `cover`                     varchar(300) NOT NULL COMMENT '视频封面',
    `video_url`                 varchar(300) NOT NULL COMMENT '视频文件地址',
    `image_url`                 json COMMENT '图片文件地址，json数组保存',
    `seconds`                   float(6,2) DEFAULT NULL COMMENT '视频秒数',
    `width`                     int(6) DEFAULT NULL COMMENT '视频宽度',
    `height`                    int(6) DEFAULT NULL COMMENT '视频高度',
    `resource_type`             tinyint(1) DEFAULT 1 COMMENT '资源类型：（1:视频 2:图片）',
    `watch_type`                tinyint(1) DEFAULT 1 COMMENT '观看类型：（1:所有人 2:私密 3:好友可见 4:部分可见 5:对谁不可见）',
    `can_watch_member`          varchar(1024) DEFAULT '' COMMENT '可见用户的id列表，使用逗号 , 分隔',
    `cannot_watch_member`       varchar(1024) DEFAULT '' COMMENT '不可见用户的id列表，使用逗号 , 分隔',
    `channel`                   varchar(10) NOT NULL DEFAULT '' COMMENT '来自什么渠道',
    `address`                   varchar(100) NOT NULL DEFAULT '' COMMENT '定位地址',
    `longitude`                 decimal(10,7) NOT NULL DEFAULT 0 COMMENT '经度',
    `latitude`                  decimal(10,7) NOT NULL DEFAULT 0 COMMENT '纬度',
    `ip`                        varchar(30) NOT NULL DEFAULT '' COMMENT 'ip地址',
    `status`                    tinyint(3) DEFAULT 0 COMMENT '业务状态',
    `version`                   bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT '视频表';

DROP TABLE IF EXISTS `vms_bgmusic`;
CREATE TABLE `vms_bgmusic`(
    `id`                        bigint(11) NOT NULL COMMENT '背景音乐主键ID',
    `music_name`                varchar(100) NOT NULL COMMENT '音乐名',
    `singer`                    varchar(100) NOT NULL COMMENT '歌手',
    `cover`                     varchar(255) NOT NULL COMMENT '音乐封面',
    `music_composer`            varchar(100) NOT NULL COMMENT '作曲者',
    `music_lyricist`            varchar(100) NOT NULL COMMENT '作词者',
    `music_copyright`           varchar(255) NOT NULL COMMENT '音乐版权',
    `music_seconds`             float(6,2) DEFAULT NULL COMMENT '音乐秒数',
    `version`                   bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `status`                    tinyint(3) DEFAULT 0 COMMENT '业务状态',
    `is_deleted`                tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT '背景音乐表';

DROP TABLE IF EXISTS `vms_video_counter`;
CREATE TABLE `vms_video_counter`(
    `video_id`                  bigint(11) NOT NULL COMMENT '视频ID，也是主键ID',
    `counter_type`              tinyint(2) NOT NULL DEFAULT 1 COMMENT '计数类型： 1:观看量 2:转发量 3:评论量 4:点赞量',
    `counter_value`             bigint(11) NOT NULL DEFAULT 1 COMMENT '计数值',
    `version`                   bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `status`                    tinyint(3) DEFAULT 0 COMMENT '业务状态',
    `is_deleted`                tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`video_id`)
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT '视频计数表';

DROP TABLE IF EXISTS `vms_video_like`;
CREATE TABLE `vms_video_like`(
    `id`                        bigint(11) NOT NULL COMMENT '主键ID',
    `member_id`                 bigint(11) NOT NULL COMMENT '会员ID',
    `like_type`                 tinyint(2) NOT NULL DEFAULT 1 COMMENT '点赞类型: 1:短视频 2:评论',
    `like_id`                   bigint(11) NOT NULL DEFAULT 1 COMMENT '点赞类型相关记录的主键ID',
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT '点赞表';

DROP TABLE IF EXISTS `vms_video_barrage`;
CREATE TABLE `vms_video_barrage`(
    `id`                        bigint(11) NOT NULL COMMENT '主键ID',
    `member_id`                 bigint(11) NOT NULL COMMENT '会员ID',
    `video_id`                  bigint(11) NOT NULL COMMENT '视频ID',
    `content`                   varchar(128) NOT NULL COMMENT '弹幕内容',
    `send_at`                   datetime DEFAULT CURRENT_TIMESTAMP COMMENT '弹幕发送时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT '视频弹幕表';


CREATE TABLE IF NOT EXISTS `base_counter_member`(
    `member_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `praised_count` int(11) UNSIGNED DEFAULT '0' COMMENT '获赞数',
    `like_count` int(11) UNSIGNED DEFAULT '0' COMMENT '点赞数',
    `friend_count` int(11) UNSIGNED DEFAULT '0' COMMENT '朋友数',
    `attention_count` int(11) UNSIGNED DEFAULT '0' COMMENT '关注数',
    `follower_count` int(11) UNSIGNED DEFAULT '0' COMMENT '粉丝数',
    PRIMARY KEY (`member_id`) USING BTREE
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT = '用户计数表' ROW_FORMAT = Dynamic;

CREATE TABLE IF NOT EXISTS `base_counter_video`(
    `video_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `like_count` int(11) UNSIGNED DEFAULT '0' COMMENT '获赞数',
    `comment_count` int(11) UNSIGNED DEFAULT '0' COMMENT '评论数',
    `share_count` int(11) UNSIGNED DEFAULT '0' COMMENT '分享数',
    `watch_count` int(11) UNSIGNED DEFAULT '0' COMMENT '浏览数',
    PRIMARY KEY (`video_id`) USING BTREE
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT = '视频计数表' ROW_FORMAT = Dynamic;

CREATE TABLE IF NOT EXISTS `base_counter_comment`(
    `comment_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `like_count` int(11) UNSIGNED DEFAULT '0' COMMENT '获赞数',
    PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT = '评论计数表' ROW_FORMAT = Dynamic;