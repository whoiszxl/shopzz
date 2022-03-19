-- 调度中心货位库存表
drop table if exists dispatch_product_allocation_stock;
create table dispatch_product_allocation_stock(
    `id`                            bigint not null auto_increment comment '主键',
    `product_allocation_id`         bigint not null comment '货位ID',
    `product_sku_id`                bigint not null comment '商品sku ID',
    `available_stock_quantity`      bigint not null comment '可用库存数量',
    `locked_stock_quantity`         bigint not null comment '锁定库存数量',
    `deliveried_stock_quantity`     bigint not null comment '已出库库存数量',
    `version`                       bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                    tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                    varchar(50) NOT NULL COMMENT '创建者',
    `updated_by`                    varchar(50) NOT NULL COMMENT '更新者',
    `created_at`                    datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                    datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT '调度中心货位库存表';


-- 调度中心货位库存明细表
drop table if exists dispatch_product_allocation_stock_detail;
create table dispatch_product_allocation_stock_detail(
    `id`                            bigint not null auto_increment comment '主键',
    `product_allocation_id`         bigint not null comment '货位ID',
    `product_sku_id`                bigint not null comment '商品sku ID',
    `put_on_time`                   datetime not null comment '上架时间',
    `put_on_quantity`               int(10) not null comment '上架数量',
    `current_stock_quantity`        int(10) not null comment '当前剩余库存数量',
    `locked_stock_quantity`         int(10) not null comment '当前锁定的库存数量',
    `version`                       bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                    tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                    varchar(50) NOT NULL COMMENT '创建者',
    `updated_by`                    varchar(50) NOT NULL COMMENT '更新者',
    `created_at`                    datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                    datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT '调度中心货位库存明细表';


-- 调度中心商品库存表
drop table if exists dispatch_warehouse_product_stock;
create table dispatch_warehouse_product_stock(
    `id`                            bigint not null auto_increment comment '主键',
    `product_sku_id`                bigint not null comment '商品SKU ID',
    `available_stock_quantity`      int(10) not null comment '可用库存数量',
    `locked_stock_quantity`         int(10) not null comment '锁定库存数量',
    `deliveried_stock_quantity`     bigint not null comment '已出库库存数量',
    `version`                       bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                    tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                    varchar(50) NOT NULL COMMENT '创建者',
    `updated_by`                    varchar(50) NOT NULL COMMENT '更新者',
    `created_at`                    datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                    datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   primary key (id)
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT '调度中心商品库存表';

