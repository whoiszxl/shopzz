-- 财务中心跟物流公司的结算流水明细表
drop table if exists finance_logistics_settlement_detail;
create table finance_logistics_settlement_detail (
    `id`                                bigint not null auto_increment comment '主键',
    `order_id`                          bigint not null comment '订单ID',
    `order_no`                          varchar(200) not null comment '订单编号',
    `total_settlement_amount`           decimal(8,2) not null comment '结算金额',
    `bank_account`                      varchar(100) not null comment '银行账号',
    `version`                           bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                        tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                        varchar(50) NOT NULL COMMENT '创建者',
    `updated_by`                        varchar(50) NOT NULL COMMENT '更新者',
    `created_at`                        datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                        datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='财务中心跟物流公司的结算流水明细表';


-- 财务中心的采购结算单表
drop table if exists finance_purchase_settlement_order;
create table finance_purchase_settlement_order
(
    `id`                                        bigint not null auto_increment comment '主键',
    `purchase_inbound_order_id`                 bigint not null comment '采购入库单ID',
    `purchase_order_id`                         bigint not null comment '采购单ID',
    `supplier_id`                               bigint not null comment '供应商ID',
    `expect_arrival_time`                       datetime not null comment '预计到货时间',
    `arrival_time`                              datetime not null comment '实际到货时间',
    `purchase_contactor`                        varchar(50) not null comment '采购联系人',
    `purchase_contact_phone_number`             varchar(20) not null comment '采购联系电话',
    `purchase_contact_email`                    varchar(100) not null comment '采购联系邮箱',
    `comment`                                   varchar(256) not null comment '采购入库单的说明备注',
    `purchaser`                                 varchar(100) not null comment '采购员',
    `status`                                    tinyint(3) not null comment '采购入库单状态，1：编辑中，2：待审核，3：已完成',
    `total_settlement_amount`                   decimal(8,2) not null comment '总结算金额，每个采购商品的价格 * 实际到货数量',
    `version`                                   bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                                tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                                varchar(50) NOT NULL COMMENT '创建者',
    `updated_by`                                varchar(50) NOT NULL COMMENT '更新者',
    `created_at`                                datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='财务中心的采购结算单表';

-- 财务中心采购结算单条目明细表
drop table if exists finance_purchase_settlement_order_item;
create table finance_purchase_settlement_order_item
(
    `id`                                bigint not null auto_increment comment '主键',
    `purchase_settlement_order_id`      bigint not null comment '采购结算单ID',
    `product_sku_id`                    bigint not null comment '商品SKU ID',
    `purchase_quantity`                 int(10) not null comment '采购数量',
    `purchase_price`                    decimal(8,2) not null comment '采购价格',
    `qualified_count`                   int(10) not null comment '合格商品的数量',
    `arrival_count`                     int(10) not null comment '到货的商品数量',
    `version`                           bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                        tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                        varchar(50) NOT NULL COMMENT '创建者',
    `updated_by`                        varchar(50) NOT NULL COMMENT '更新者',
    `created_at`                        datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                        datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='财务中心采购结算单条目明细表';

-- 财务中心退货退款流水明细
drop table if exists finance_return_product_refund_detail;
create table finance_return_product_refund_detail
(
    `id`                                        bigint not null auto_increment comment '主键',
    `order_id`                                  bigint not null comment '订单ID',
    `order_no`                                  varchar(100) not null comment '订单编号',
    `return_product_worksheet_id`               bigint not null comment '退货工单ID',
    `return_product_warehouse_entry_order_id`   bigint not null comment '退货入库单ID',
    `member_id`                                 bigint(20) not null comment '用户账号ID',
    `refund_amount`                             decimal(8,2) not null comment '退款金额',
    `version`                                   bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                                tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                                varchar(50) NOT NULL COMMENT '创建者',
    `updated_by`                                varchar(50) NOT NULL COMMENT '更新者',
    `created_at`                                datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='财务中心退货退款流水明细';


-- 财务中心供应商结算明细表
drop table if exists finance_supplier_settlement_detail;
create table finance_supplier_settlement_detail (
    `id`                                bigint not null auto_increment comment '主键',
    `supplier_id`                       bigint not null comment '供应商ID',
    `settlement_period`                 tinyint(3) not null comment '结算周期，1：周，2：月，3：季度',
    `purcahse_settlement_order_ids`     varchar(1024) not null comment '本次结算关联的采购结算单的ids',
    `total_settlement_amount`           decimal(8,2) not null comment '本次结算总金额',
    `version`                           bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                        tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                        varchar(50) NOT NULL COMMENT '创建者',
    `updated_by`                        varchar(50) NOT NULL COMMENT '更新者',
    `created_at`                        datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                        datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='财务中心供应商结算明细表';
