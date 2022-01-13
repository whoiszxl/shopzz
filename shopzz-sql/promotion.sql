DROP TABLE IF EXISTS `promotion_activity`;
CREATE TABLE `promotion_activity` (
    `id`                        int(10) NOT NULL AUTO_INCREMENT COMMENT '自增主键ID',
    `name`                      varchar(1024) NOT NULL COMMENT '促销活动名称',
    `start_time`                datetime NOT NULL COMMENT '促销活动开始时间',
    `end_time`                  datetime NOT NULL COMMENT '促销活动结束时间',
    `remark`                    varchar(1024) DEFAULT NULL COMMENT '促销活动说明备注',
    `status`                    tinyint(4) NOT NULL COMMENT '促销活动的状态，1:启用，2:停用',
    `rule`                      varchar(1024) NOT NULL COMMENT '促销活动的规则',
    `type`                      tinyint(4) NOT NULL COMMENT '促销活动的类型，1:满减；2:多买优惠；3:单品促销；4:满赠促销；5:赠品促销',
    `version`                   bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                varchar(50) NOT NULL COMMENT '创建者',
    `updated_by`                varchar(50) NOT NULL COMMENT '更新者',
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB CHARSET=utf8mb4 COMMENT='促销活动表';

DROP TABLE IF EXISTS `promotion_activity_product_relation`;
CREATE TABLE `promotion_activity_product_relation` (
    `id`                        int(10) NOT NULL AUTO_INCREMENT COMMENT '自增主键ID',
    `promotion_activity_id`     bigint(20) NOT NULL COMMENT '促销活动ID',
    `product_id`                bigint(20) NOT NULL COMMENT '关联的某个商品sku的ID，如果将这个字段的值设置为-1，那么代表针对全部商品',
    `version`                   bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                varchar(50) NOT NULL COMMENT '创建者',
    `updated_by`                varchar(50) NOT NULL COMMENT '更新者',
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COMMENT='促销活动跟商品sku的关联关系表';


DROP TABLE IF EXISTS `promotion_coupon`;
CREATE TABLE `promotion_coupon` (
    `id`                        bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`                      varchar(1024) NOT NULL COMMENT '优惠券名称',
    `type`                      tinyint(4) NOT NULL COMMENT '优惠券类型，1:现金券，2:满减券',
    `rule`                      varchar(1024) NOT NULL COMMENT '优惠券规则',
    `start_time`                datetime NOT NULL COMMENT '有效期开始时间',
    `end_time`                  datetime NOT NULL COMMENT '有效期结束时间',
    `all_count`                 bigint(20) NOT NULL COMMENT '优惠券总量',
    `received_count`            bigint(20) NOT NULL COMMENT '优惠券已经领取的数量',
    `give_type`                 tinyint(4) NOT NULL COMMENT '优惠券发放方式，1:可发放可领取，2:仅可发放，3:仅可领取',
    `status`                    tinyint(4) NOT NULL COMMENT '优惠券状态，1:未开始；2:发放中，3:已发完；4:已过期',
    `version`                   bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                varchar(50) NOT NULL COMMENT '创建者',
    `updated_by`                varchar(50) NOT NULL COMMENT '更新者',
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='优惠券表';

DROP TABLE IF EXISTS `promotion_coupon_received_record`;
CREATE TABLE `promotion_coupon_received_record` (
    `id`                        bigint(20) NOT NULL COMMENT '主键ID',
    `coupon_id`                 bigint(20) NOT NULL COMMENT '优惠券ID',
    `member_id`                 bigint(20) NOT NULL COMMENT '用户账号ID',
    `is_used`                   tinyint(4) NOT NULL COMMENT '是否使用过这个优惠券，1:使用了，0:未使用',
    `used_time`                 datetime DEFAULT NULL COMMENT '使用优惠券的时间',
    `version`                   bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                varchar(50) NOT NULL COMMENT '创建者',
    `updated_by`                varchar(50) NOT NULL COMMENT '更新者',
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_coupon_id_member_id` (`coupon_id`,`member_id`),
  KEY `idx_member_id` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='优惠券领取记录表';