DROP TABLE IF EXISTS `rms_banner_info`;
CREATE TABLE `rms_banner_info` (
    `id`                        int(10) NOT NULL AUTO_INCREMENT COMMENT '自增主键ID',
    `name`                      varchar(100) DEFAULT '' COMMENT '轮播图名称',
    `type`                      tinyint(1) DEFAULT '0' COMMENT '轮播位置:0->PC首页轮播；1->app首页轮播 2->app导航小组件',
    `pic`                       varchar(512) DEFAULT '' COMMENT '图片地址',
    `status`                    int(1) DEFAULT '1' COMMENT '上下线状态:0->下线；1->上线',
    `click_count`               int(11) DEFAULT '0' COMMENT '点击数',
    `url`                       varchar(512) DEFAULT '' COMMENT '链接地址',
    `note`                      varchar(256) DEFAULT '' COMMENT '备注',
    `start_time`                datetime COMMENT '展示开始时间',
    `start_time`                datetime COMMENT '展示结束时间',
    `sort`                      int(3) DEFAULT '0' COMMENT '排序',
    `version`                   bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                varchar(50) NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`                varchar(50) NOT NULL DEFAULT '' COMMENT '更新者',
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT '轮播表';