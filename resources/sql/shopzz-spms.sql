DROP TABLE IF EXISTS `spms_banner`;
CREATE TABLE `spms_banner` (
    `id`                        bigint(10) NOT NULL AUTO_INCREMENT COMMENT '自增主键ID',
    `name`                      varchar(100) DEFAULT '' COMMENT '轮播图名称',
    `type`                      tinyint(1) DEFAULT '0' COMMENT '轮播位置:0->PC首页轮播;1->app首页轮播 2->app导航小组件',
    `biz_id`                    bigint(10) NOT NULL COMMENT '业务ID: spu_id or other',
    `pic`                       varchar(255) DEFAULT '' COMMENT '图片地址',
    `status`                    int(1) DEFAULT '1' COMMENT '上下线状态:0->下线;1->上线',
    `click_count`               int(11) DEFAULT '0' COMMENT '点击数',
    `url`                       varchar(255) DEFAULT '' COMMENT '链接地址',
    `sort`                      int(3) DEFAULT '0' COMMENT '排序',
    `version`                   bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                varchar(50) NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`                varchar(50) NOT NULL DEFAULT '' COMMENT '更新者',
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT '轮播表';


DROP TABLE IF EXISTS `spms_product_column`;
CREATE TABLE `spms_product_column` (
    `id`                        bigint(10) NOT NULL AUTO_INCREMENT COMMENT '自增主键ID',
    `name`                      varchar(128) NOT NULL COMMENT '专栏名称',
    `descs`                     varchar(128) NOT NULL COMMENT '专栏描述',
    `enter_img`                 varchar(255) DEFAULT '' COMMENT '入口图片地址',
    `banner_img`                varchar(255) DEFAULT '' COMMENT '内部banner图片地址',
    `status`                    int(1) DEFAULT '1' COMMENT '上下线状态:0->下线;1->上线',
    `click_count`               int(11) DEFAULT '0' COMMENT '点击数',
    `sort`                      int(3) DEFAULT '0' COMMENT '排序',
    `version`                   bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                varchar(50) NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`                varchar(50) NOT NULL DEFAULT '' COMMENT '更新者',
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT '商品专栏表';

DROP TABLE IF EXISTS `spms_product_column_spu`;
CREATE TABLE `spms_product_column_spu` (
    `id`                        bigint(10) NOT NULL AUTO_INCREMENT COMMENT '自增主键ID',
    `column_id`                 bigint(10) NOT NULL COMMENT '专栏主键ID',
    `spu_id`                    bigint(10) NOT NULL COMMENT 'SPU主键ID',
	PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT '商品专栏跟SPU关联表';

DROP TABLE IF EXISTS `spms_activity`;
CREATE TABLE `spms_activity` (
    `id`                        bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键ID',
    `name`                      varchar(255) NOT NULL COMMENT '促销活动名称',
    `descs`                     varchar(255) NOT NULL COMMENT '活动描述',
    `start_time`                datetime NOT NULL COMMENT '促销活动开始时间',
    `end_time`                  datetime NOT NULL COMMENT '促销活动结束时间',
    `img`                       varchar(255) DEFAULT NULL COMMENT '活动banner图',
    `status`                    tinyint(4) NOT NULL COMMENT '促销活动的状态,1:启用,2:停用',
    `version`                   bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除, 0: 未删除',
    `created_by`                varchar(50) NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`                varchar(50) NOT NULL DEFAULT '' COMMENT '更新者',
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB CHARSET=utf8mb4 COMMENT='促销活动表';

DROP TABLE IF EXISTS `spms_activity_category`;
CREATE TABLE `spms_activity_category` (
    `id`                        bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键ID',
    `activity_id`               bigint(11) NOT NULL COMMENT '促销活动ID',
    `category_id`               bigint(11) NOT NULL COMMENT '分类ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COMMENT='促销活动跟分类的关联关系表';

DROP TABLE IF EXISTS `spms_activity_coupon`;
CREATE TABLE `spms_activity_coupon` (
    `id`                        bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键ID',
    `activity_id`               bigint(11) NOT NULL COMMENT '促销活动ID',
    `coupon_id`                 bigint(11) NOT NULL COMMENT '优惠券ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COMMENT='促销活动跟优惠券的关联关系表';


DROP TABLE IF EXISTS `spms_coupon`;
CREATE TABLE `spms_coupon` (
    `id`                        bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键ID',
    `name`                      varchar(255) NOT NULL COMMENT '优惠券名称',
    `sub_name`                  varchar(255) NOT NULL COMMENT '优惠券副名称',
    `start_time`                datetime NOT NULL COMMENT '优惠券有效期起始时间',
    `end_time`                  datetime NOT NULL COMMENT '优惠券有效期结束时间',
    `use_threshold`             decimal(10,2) NOT NULL COMMENT '优惠券使用阈值',
    `discount_amount`           decimal(10,2) NOT NULL COMMENT '折扣金额',
    `discount_rate`             decimal(10,2) NOT NULL COMMENT '折扣比率',
    `type`                      tinyint(3) DEFAULT 1 COMMENT '优惠券类型 1: 满减券, 2: 满减折扣券 3: 无门槛立减券',
    `full_limited`              tinyint(3) DEFAULT 1 COMMENT '是否有全场限制 1: 全场通用, 2: 分类限制',
    `status`                    tinyint(3) DEFAULT 1 COMMENT '优惠券状态 1: 有效, 2: 失效(超出有效期), 3: 系统停用',
    `version`                   bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除, 0: 未删除',
    `created_by`                varchar(50) NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`                varchar(50) NOT NULL DEFAULT '' COMMENT '更新者',
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COMMENT='优惠券表';


DROP TABLE IF EXISTS `spms_coupon_category`;
CREATE TABLE `spms_coupon_category` (
    `id`                        bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键ID',
    `coupon_id`                 bigint(11) NOT NULL COMMENT '优惠券ID',
    `category_id`               bigint(11) NOT NULL COMMENT '一级分类ID (为0则为全场通用)',
    `parent_category_id`        bigint(11) NOT NULL COMMENT '二级分类ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COMMENT='优惠券跟分类的关联关系表';


DROP TABLE IF EXISTS `spms_member_coupon`;
CREATE TABLE `spms_member_coupon` (
    `id`                        bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键ID',
    `member_id`                 bigint(11) NOT NULL COMMENT '会员ID',
    `coupon_id`                 bigint(11) NOT NULL COMMENT '优惠券ID',
    `status`                    tinyint(3) NOT NULL DEFAULT 1 COMMENT '优惠券状态: 1-未使用 2-已使用 3-已过期',
    `get_time`                  datetime NOT NULL COMMENT '优惠券的领取时间',
    `use_time`                  datetime NULL COMMENT '优惠券的使用时间',
    `version`                   bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除, 0: 未删除',
    `created_by`                varchar(50) NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`                varchar(50) NOT NULL DEFAULT '' COMMENT '更新者',
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COMMENT='用户领取优惠券表';



DROP TABLE IF EXISTS `spms_recommend_product`;
CREATE TABLE `spms_recommend_product` (
    `id`                        bigint(10) NOT NULL AUTO_INCREMENT COMMENT '自增主键ID',
    `spu_id`                    bigint(10) NOT NULL COMMENT 'SPU主键ID',
	PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT '首页通用推荐商品表';