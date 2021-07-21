drop table if exists inventory_product_stock;
create table inventory_product_stock(
    `id`                                bigint not null auto_increment comment '主键',
    `product_sku_id`                    bigint not null comment '商品sku ID',
    `sale_stock_quantity`               int(10) not null comment '销售库存',
    `locked_stock_quantity`             int(10) not null comment '锁定库存',
    `saled_stock_quantity`              int(10) not null comment '已销售库存',
    `stock_status`                      tinyint(3) not null comment '库存状态，0：无库存，1：有库存',
    `version`                           bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                        tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                        varchar(50) NOT NULL COMMENT '创建者',
    `updated_by`                        varchar(50) NOT NULL COMMENT '更新者',
    `created_at`                        datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                        datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存中心的商品库存表';

