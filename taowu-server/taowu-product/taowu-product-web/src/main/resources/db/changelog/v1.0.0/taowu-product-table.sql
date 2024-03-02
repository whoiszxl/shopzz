-- liquibase formatted sql

-- changeset whoiszxl:1
-- comment 初始化product表结构

DROP TABLE IF EXISTS `pms_spu`;
CREATE TABLE `pms_spu` (
    `id`                        bigint(11) NOT NULL AUTO_INCREMENT COMMENT '商品id',
    `name`                      varchar(128) NOT NULL COMMENT '商品名称',
    `sub_name`                  varchar(255) NOT NULL COMMENT '商品副名称',
    `default_price`             decimal(8,2) NOT NULL COMMENT '默认价格',
    `default_pic`               varchar(255) NOT NULL COMMENT '商品默认图片地址',
    `pic_list`                  text NOT NULL COMMENT '商品轮播图片列表，多个图片以英文逗号分隔',
    `category_id`               bigint(11) NOT NULL COMMENT '类目ID',
    `parent_category_id`        bigint(11) NOT NULL COMMENT '父类目ID',
    `brand_id`                  bigint(11) NOT NULL COMMENT '品牌ID',
    `brand_name`                varchar(50) NOT NULL COMMENT '品牌名称',
    `delete_status`             tinyint(1) DEFAULT NULL COMMENT '删除状态:0->未删除; 1->已删除',
    `publish_status`            tinyint(1) DEFAULT NULL COMMENT '上架状态:0->下架; 1->上架',
    `verify_status`             tinyint(1) DEFAULT NULL COMMENT '审核状态:0->未审核; 1->审核通过',
    `package_list`              varchar(1024) NOT NULL COMMENT '包装清单',
    `default_sku_id`            bigint(11) NOT NULL COMMENT '默认选中的SKU ID',
    `version`                   bigint(11) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除, 0: 未删除',
    `created_by`                varchar(50) NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`                varchar(50) NOT NULL DEFAULT '' COMMENT '更新者',
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_category_id` (`category_id`),
  KEY `idx_brand_id` (`brand_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品基础信息表';

DROP TABLE IF EXISTS `pms_spu_detail`;
CREATE TABLE `pms_spu_detail` (
    `spu_id`                    bigint(11) NOT NULL COMMENT '商品ID',
    `detail_html`               text COMMENT 'PC商品详情富文本内容',
    `detail_mobile`             text COMMENT '移动端商品详情富文本内容',
  PRIMARY KEY (`spu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品详情页数据表';


DROP TABLE IF EXISTS `pms_spu_images`;
CREATE TABLE `pms_spu_images` (
    `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `spu_id` bigint(11) DEFAULT NULL COMMENT '商品ID',
    `img_url` varchar(255) DEFAULT NULL COMMENT '图片地址',
    `sort` int(6) DEFAULT NULL COMMENT '排序,降序排列',
    `is_default` tinyint(1) DEFAULT NULL COMMENT '是否默认图',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品SPU图片表';


DROP TABLE IF EXISTS `pms_sku`;
CREATE TABLE `pms_sku` (
    `id`                        bigint(11) NOT NULL COMMENT 'sku主键ID',
    `spu_id`                    bigint(11) DEFAULT NULL COMMENT '商品SPU的ID',
    `sku_name`                  varchar(255) DEFAULT NULL COMMENT 'sku名称',
    `sku_img`                   varchar(255) DEFAULT NULL COMMENT 'sku缩略图片地址',
    `sale_price`                decimal(8,2) NOT NULL COMMENT '销售价格',
    `promotion_price`           decimal(8,2) NOT NULL COMMENT '促销价格',
    `sku_code`                  varchar(255) DEFAULT NULL COMMENT 'SKU编码',
    `version`                   bigint(11) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除, 0: 未删除',
    `created_by`                varchar(50) NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`                varchar(50) NOT NULL DEFAULT '' COMMENT '更新者',
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='sku信息表';

drop table if exists `pms_sku_stock`;
create table `pms_sku_stock`(
    `id`                                bigint not null auto_increment comment '主键',
    `sku_id`                            bigint not null comment '商品sku ID',
    `sale_stock_quantity`               int(10) not null comment '销售库存',
    `locked_stock_quantity`             int(10) not null comment '锁定库存',
    `saled_stock_quantity`              int(10) not null comment '已销售库存',
    `stock_status`                      tinyint(3) not null comment '库存状态,0:无库存,1:有库存',
    `version`                           bigint(11) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                        tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除, 0: 未删除',
    `created_by`                        varchar(50) NOT NULL COMMENT '创建者',
    `updated_by`                        varchar(50) NOT NULL COMMENT '更新者',
    `created_at`                        datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                        datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品SKU库存表';


drop table if exists `pms_sku_attribute`;
create table `pms_sku_attribute`(
    `id`                                bigint not null auto_increment comment '主键',
    `spu_id`                            bigint(11) not null comment '商品spu ID',
    `sku_id`                            bigint(11) not null comment '商品sku ID',
    `key_id`                            bigint(11) not null comment '属性key ID',
    `value_id`                          bigint(11) not null comment '属性value ID',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='SKU属性关联表';


DROP TABLE IF EXISTS `pms_attribute_key`;
CREATE TABLE `pms_attribute_key` (
    `id`                        bigint(11) NOT NULL AUTO_INCREMENT COMMENT '属性key主键id',
    `name`                      varchar(255) NOT NULL COMMENT '属性名称',
    `unit`                      varchar(255) DEFAULT NULL COMMENT '属性单位',
    `standard`                  tinyint NOT NULL DEFAULT '1' COMMENT '是否为标准属性',
    `type`                      tinyint(1) DEFAULT NULL COMMENT '属性类型[0-销售属性,1-基本属性]',

    `version`                   bigint(11) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除, 0: 未删除',
    `created_by`                varchar(50) NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`                varchar(50) NOT NULL DEFAULT '' COMMENT '更新者',
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='属性键表';

DROP TABLE IF EXISTS `pms_attribute_value`;
CREATE TABLE `pms_attribute_value` (
    `id`                        bigint(11) NOT NULL AUTO_INCREMENT COMMENT '属性value主键id',
    `key_id`                    bigint(11) NOT NULL COMMENT '属性key主键id',
    `value`                     varchar(255) DEFAULT NULL COMMENT '属性值',
    `version`                   bigint(11) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除, 0: 未删除',
    `created_by`                varchar(50) NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`                varchar(50) NOT NULL DEFAULT '' COMMENT '更新者',
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='属性值表';


DROP TABLE IF EXISTS `pms_spu_key`;
CREATE TABLE `pms_spu_key` (
    `id`                        bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'SPU与属性key关联表主键id',
    `spu_id`                    bigint(11) NOT NULL COMMENT 'SPU ID',
    `key_id`                    bigint(11) NOT NULL COMMENT '属性key主键id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='SPU与属性key关联表';

CREATE TABLE `pms_spu_attribute` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `spu_id` bigint(11) NOT NULL COMMENT '商品spu ID',
    `key_id` bigint(11) NOT NULL COMMENT '属性key ID',
    `value_id` bigint(11) NOT NULL COMMENT '属性value ID',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8mb4 COMMENT='SPU和属性关联表';

DROP TABLE IF EXISTS `pms_brand`;
CREATE TABLE `pms_brand` (
    `id`                        bigint(11) NOT NULL AUTO_INCREMENT COMMENT '品牌主键id',
    `chinese_name`              varchar(100) NOT NULL COMMENT '品牌中文名',
    `english_name`              varchar(100) NOT NULL COMMENT '品牌英文名',
    `alias_name`                varchar(50) NOT NULL COMMENT '品牌别名',
    `first_letter`              varchar(2) DEFAULT NULL COMMENT '首字母',
    `logo`                      varchar(255) DEFAULT NULL COMMENT '品牌logo地址',
    `description`               varchar(1024) NOT NULL COMMENT '品牌介绍',
    `auth_pic`                  varchar(1024) NOT NULL COMMENT '品牌授权图片',
    `location`                  varchar(1024) NOT NULL COMMENT '品牌所在地',
    `show_status`               tinyint(4) DEFAULT NULL COMMENT '显示状态[0-不显示; 1-显示]',
    `remark`                    varchar(1024) DEFAULT NULL COMMENT '品牌说明备注',
    `sort`                      int(3) DEFAULT '0' COMMENT '排序',
    `version`                   bigint(11) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除, 0: 未删除',
    `created_by`                varchar(50) NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`                varchar(50) NOT NULL DEFAULT '' COMMENT '更新者',
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='品牌表';


DROP TABLE IF EXISTS `pms_category`;
CREATE TABLE `pms_category` (
    `id`                        bigint(11) NOT NULL AUTO_INCREMENT COMMENT '分类id',
    `name`                      varchar(10) DEFAULT NULL COMMENT '分类名称',
    `parent_id`                 bigint(11) DEFAULT 0 COMMENT '父类目的主键',
    `level`                     tinyint(1) DEFAULT NULL COMMENT '分类级别:1->1级; 2->2级 3->3级',
    `status`                    tinyint(1) DEFAULT NULL COMMENT '是否显示[0-不显示,1显示]',
    `sort`                      int(11) DEFAULT NULL COMMENT '排序',
    `icon`                      char(255) DEFAULT NULL COMMENT '图标地址',
    `version`                   bigint(11) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除, 0: 未删除',
    `created_by`                varchar(50) NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`                varchar(50) NOT NULL DEFAULT '' COMMENT '更新者',
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品三级分类表';