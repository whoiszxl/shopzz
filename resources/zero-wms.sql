-- 采购订单表
drop table if exists wms_purchase_order;
create table wms_purchase_order
(
    `id`                        bigint not null auto_increment comment '主键',
    `supplier_id`               bigint not null comment '供应商ID',
    `expect_arrival_time`       datetime not null comment '预计到货时间',
    `contactor`                 varchar(100) not null comment '联系人',
    `contact_phone_number`      varchar(20) not null comment '联系电话',
    `contact_email`             varchar(100) not null comment '联系邮箱',
    `comment`                   varchar(256) not null comment '说明备注',
    `purchaser`                 varchar(256) not null comment '采购员',
    `purchase_order_status`     tinyint not null comment '采购单状态，1：编辑中，2：待审核，3：已审核，4：待入库，5：待结算，6：已完成',
    `version`                   bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                varchar(50) NOT NULL COMMENT '创建者',
    `updated_by`                varchar(50) NOT NULL COMMENT '更新者',
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    primary key (id)
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT '采购订单表';

-- 采购订单商品详情表
drop table if exists wms_purchase_order_item;
create table wms_purchase_order_item
(
    `id`                        bigint not null auto_increment comment '主键',
    `purchase_order_id`         bigint not null comment '采购单ID',
    `product_sku_id`            bigint not null comment '商品SKU ID',
    `purchase_quantity`         bigint not null comment '采购数量',
    `purchase_price`            bigint not null comment '采购价格',
    `version`                   bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                varchar(50) NOT NULL COMMENT '创建者',
    `updated_by`                varchar(50) NOT NULL COMMENT '更新者',
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   primary key (id)
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT '采购订单商品详情表';

-- 采购供应商表
drop table if exists wms_purchase_supplier;
create table wms_purchase_supplier
(
    `id`                        bigint not null auto_increment comment '主键',
    `supplier_name`             varchar(100) not null comment '供应商名称',
    `company_name`              varchar(100) not null comment '公司名称',
    `company_address`           varchar(100) not null comment '公司地址',
    `contactor`                 varchar(50) not null comment '联系人',
    `contact_phone_number`      varchar(20) not null comment '联系电话',
    `account_period`            tinyint(1) not null comment '账期，1：周结算，2：月结算，3：季度结算',
    `bank_name`                 varchar(60) not null comment '银行名称',
    `bank_account`              varchar(100) not null comment '银行账户',
    `account_holder`            varchar(50) not null comment '开户人',
    `invoice_title`             varchar(200) not null comment '发票抬头',
    `taxpayer_id`               varchar(200) not null comment '纳税人识别号',
    `business_scope`            varchar(100) not null comment '经营范围',
    `supplier_comment`          varchar(256) comment '说明备注',
    `enterprise_qualification`  varchar(200) not null comment '企业资质',
    `cooperate_contract`        longblob not null comment '合作合同',
    `price_contract`            longblob not null comment '协议价合同',
    `purchase_contract`         longblob not null comment '采购合同',
    `version`                   bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                varchar(50) NOT NULL COMMENT '创建者',
    `updated_by`                varchar(50) NOT NULL COMMENT '更新者',
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   primary key (id)
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT '采购供应商表';



-- 货位表
drop table if exists wms_product_allocation;
create table wms_product_allocation
(
    `id`                            bigint not null auto_increment comment '主键',
    `product_allocation_code`       varchar(200) not null comment '货位编号',
    `product_allocation_comment`    varchar(256) comment '说明备注',
    `version`                       bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                    tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                    varchar(50) NOT NULL COMMENT '创建者',
    `updated_by`                    varchar(50) NOT NULL COMMENT '更新者',
    `created_at`                    datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                    datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='货位表';


-- 货位库存表
drop table if exists wms_product_allocation_stock;
create table wms_product_allocation_stock
(
    `id`                            bigint not null auto_increment comment '主键',
    `product_allocation_id`         bigint not null comment '货位ID',
    `product_sku_id`                bigint not null comment '商品SKU ID',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='货位库存表';


-- 货位库存详情表
drop table if exists wms_product_allocation_stock_detail;
create table wms_product_allocation_stock_detail (
    `id`                            bigint not null auto_increment comment '主键',
    `product_allocation_id`         bigint not null comment '货位ID',
    `product_sku_id`                bigint not null comment '商品SKU ID',
    `put_on_time`                   datetime not null comment '商品的上架时间',
    `put_on_quantity`               int(10) not null comment '商品的上架数量',
    `current_stock_quantity`        int(10) not null comment '上架的商品当前还剩余的库存数量',
    `locked_stock_quantity`         bigint not null comment '锁定库存数量',
    `version`                       bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                    tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                    varchar(50) NOT NULL COMMENT '创建者',
    `updated_by`                    varchar(50) NOT NULL COMMENT '更新者',
    `created_at`                    datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                    datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='货位库存详情表';


-- 采购入库订单表
drop table if exists wms_purchase_inbound_order;
create table wms_purchase_inbound_order
(
    `id`                                bigint not null auto_increment comment '主键',
    `purchase_order_id`                 bigint not null comment '采购单ID',
    `supplier_id`                       bigint not null comment '供应商ID',
    `expect_arrival_time`               datetime not null comment '预计到货时间',
    `arrival_time`                      datetime not null comment '实际到货时间',
    `purchase_contactor`                varchar(100) not null comment '采购联系人',
    `purchase_contact_phone_number`     varchar(20) not null comment '采购联系电话',
    `purchase_contact_email`            varchar(50) not null comment '采购联系邮箱',
    `purchase_inbound_order_comment`    varchar(256) not null comment '采购入库单的说明备注',
    `purchaser`                         varchar(30) not null comment '采购员',
    `purchase_inbound_order_status`     tinyint(3) not null comment '采购入库单状态，1：编辑中，2：待审核，3：已入库，4：待结算，5：已完成',
    `version`                           bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                        tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                        varchar(50) NOT NULL COMMENT '创建者',
    `updated_by`                        varchar(50) NOT NULL COMMENT '更新者',
    `created_at`                        datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                        datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='采购入库订单表';

-- 采购入库订单的商品条目表
drop table if exists wms_purchase_inbound_order_item;
create table wms_purchase_inbound_order_item
(
    `id`                                bigint not null auto_increment comment '主键',
    `purchase_inbound_order_id`           bigint not null comment '采购入库单ID',
    `product_sku_id`                    bigint not null comment '商品SKU ID',
    `purchase_count`                    int(10) not null comment '采购数量',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='采购入库订单条目表';

-- 采购入库订单条目关联的上架条目表
drop table if exists wms_purchase_inbound_on_item;
create table wms_purchase_inbound_on_item (
    `id`                                bigint not null auto_increment comment '主键',
    `purchase_inbound_order_item_id`    bigint not null comment '采购入库单条目ID',
    `product_allocation_id`             bigint not null comment '货位ID',
    `put_on_shelves_count`              int(10) not null comment '上架数量',
    `version`                           bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                        tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                        varchar(50) NOT NULL COMMENT '创建者',
    `updated_by`                        varchar(50) NOT NULL COMMENT '更新者',
    `created_at`                        datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                        datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='采购入库订单条目关联的上架条目表';


-- 退货入库订单表
drop table if exists wms_return_product_inbound_order;
create table wms_return_product_inbound_order(
    `id`                                    bigint not null auto_increment comment '主键',
    `member_id`                             bigint(20) comment '用户账号ID',
    `order_id`                              bigint not null comment '订单ID',
    `order_no`                              varchar(100) not null comment '订单编号',
    `return_product_inbound_order_status`   tinyint not null comment '退货入库单状态，1：编辑中，2：待审核：3：已完成',
    `receive_name`                          varchar(100) not null comment '收货人',
    `receive_address`                       varchar(200) not null comment '收货地址',
    `receive_phone`                         varchar(20) not null comment '收货人电话号码',
    `freight`                               decimal(8,2) not null comment '运费',
    `pay_type`                              tinyint(4) NOT NULL COMMENT '支付方式: [1:支付宝 2:微信 3:数字货币]',
    `total_amount`                          decimal(9,2) comment '订单总额',
    `freight_amount`                        decimal(9,2) comment '运费金额',
    `promotion_amount`                      decimal(9,2) comment '促销折扣金额',
    `point_amount`                          decimal(9,2) comment '积分抵扣金额',
    `coupon_amount`                         decimal(9,2) comment '优惠券抵扣金额',
    `admin_discount_amount`                 decimal(9,2) comment '后台调整订单使用的折扣金额',
    `pay_amount`                            decimal(9,2) comment '应付总额',
    `invoice_type`                          tinyint comment '发票类型[0->不开发票；1->电子发票；2->纸质发票]',
    `invoice_header`                        varchar(255) comment '发票抬头',
    `taxpayer_id`                           varchar(255) not null comment '纳税人识别号',
    `order_comment`                         varchar(255) NOT NULL COMMENT '订单备注',
    `return_reason`                         tinyint(4) NOT NULL COMMENT '退货原因，1：质量不好，2：商品不满意，3：买错了，4：无理由退货',
    `return_comment`                        varchar(255) NOT NULL COMMENT '退货备注',
    `arrival_time`                          datetime not null comment '到货时间',
    `version`                               bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                            tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                            varchar(50) NOT NULL COMMENT '创建者',
    `updated_by`                            varchar(50) NOT NULL COMMENT '更新者',
    `created_at`                            datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                            datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='退货入库订单表';

-- 退货入库订单条目表
drop table if exists wms_return_product_inbound_order_item;
create table wms_return_product_inbound_order_item (
    `id`                                    bigint(20) not null auto_increment comment '主键',
    `return_product_inbound_order_id`       bigint(20) not null comment '退货入库单ID',
    `product_sku_id`                        bigint(20) not null comment '商品sku ID',
    `product_sku_code`                      varchar(100) not null comment '商品sku编号',
    `product_name`                          varchar(200) not null comment '商品名称',
    `sale_properties`                       varchar(300) not null comment '销售属性，机身颜色:白色,内存容量:256G',
    `product_gross_weight`                  decimal(8,2) not null comment '商品毛重',
    `quantity`                              int(10) not null comment '退货数量',
    `price`                                 decimal(8,2) not null comment '退货商品购买价格',
    `promotion_activity_id`                 bigint(20) not null comment '促销活动ID',
    `product_length`                        decimal(8,2) not null comment '商品长度',
    `product_width`                         decimal(8,2) not null comment '商品宽度',
    `product_height`                        decimal(8,2) not null comment '商品高度',
    `qualified_count`                       int(10) not null comment '合格商品数量',
    `arrival_count`                         int(10) not null comment '商品到货数量',
    `version`                               bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                            tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                            varchar(50) NOT NULL COMMENT '创建者',
    `updated_by`                            varchar(50) NOT NULL COMMENT '更新者',
    `created_at`                            datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                            datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='退货入库订单条目表';


-- 退货入库订单条目关联的上架条目表
drop table if exists wms_return_product_inbound_put_on_item;
create table wms_return_product_inbound_put_on_item (
    `id`                                    bigint not null auto_increment comment '主键',
    `return_product_inbound_order_item_id`  bigint not null comment '退货入库订单条目ID',
    `product_allocation_id`                 bigint not null comment '货位ID',
    `put_on_shelves_count`                  bigint not null comment '上架数量',
    `version`                               bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                            tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                            varchar(50) NOT NULL COMMENT '创建者',
    `updated_by`                            varchar(50) NOT NULL COMMENT '更新者',
    `created_at`                            datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                            datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='退货入库订单条目关联的上架条目表';

-- 销售出库订单表
drop table if exists wms_sale_delivery_order;
create table wms_sale_delivery_order (
    `id`                                    bigint not null auto_increment comment '主键',
    `member_id`                             bigint(20) comment '用户账号ID',
    `order_id`                              bigint not null comment '订单ID',
    `order_no`                              varchar(100) not null comment '订单编号',
    `receive_name`                          varchar(100) not null comment '收货人',
    `receive_address`                       varchar(200) not null comment '收货地址',
    `receive_phone`                         varchar(20) not null comment '收货人电话号码',
    `freight`                               decimal(8,2) not null comment '运费',
    `pay_type`                              tinyint(4) NOT NULL COMMENT '支付方式: [1:支付宝 2:微信 3:数字货币]',
    `total_amount`                          decimal(9,2) comment '订单总额',
    `freight_amount`                        decimal(9,2) comment '运费金额',
    `promotion_amount`                      decimal(9,2) comment '促销折扣金额',
    `point_amount`                          decimal(9,2) comment '积分抵扣金额',
    `coupon_amount`                         decimal(9,2) comment '优惠券抵扣金额',
    `admin_discount_amount`                 decimal(9,2) comment '后台调整订单使用的折扣金额',
    `pay_amount`                            decimal(9,2) comment '应付总额',
    `invoice_type`                          tinyint comment '发票类型[0->不开发票；1->电子发票；2->纸质发票]',
    `invoice_header`                        varchar(255) comment '发票抬头',
    `taxpayer_id`                           varchar(255) not null comment '纳税人识别号',
    `order_comment`                         varchar(255) NOT NULL COMMENT '订单备注',
    `sale_delivery_order_status`            tinyint not null comment '销售出库单的状态，1：编辑中，2：待审核，3：已完成',
    `delivery_time`                         datetime not null comment '发货时间',
    `version`                               bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                            tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                            varchar(50) NOT NULL COMMENT '创建者',
    `updated_by`                            varchar(50) NOT NULL COMMENT '更新者',
    `created_at`                            datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                            datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='销售出库订单表';

-- 销售出库订单条目表
drop table if exists wms_sale_delivery_order_item;
create table wms_sale_delivery_order_item (
    `id`                                    bigint not null auto_increment comment '主键',
    `sale_delivery_order_id`                bigint not null comment '销售出库单ID',
    `product_sku_id`                        bigint not null comment '商品sku ID',
    `product_sku_code`                      varchar(100) not null comment '商品sku编号',
    `product_name`                          varchar(200) not null comment '商品名称',
    `sale_properties`                       varchar(300) not null comment '销售属性，机身颜色:白色,内存容量:256G',
    `product_gross_weight`                  decimal(8,2) not null comment '商品毛重',
    `quantity`                              int(10) not null comment '销售出库数量',
    `price`                                 decimal(8,2) not null comment '销售出库商品购买价格',
    `promotion_activity_id`                 bigint not null comment '促销活动ID',
    `product_length`                        decimal(8,2) not null comment '商品长度',
    `product_width`                         decimal(8,2) not null comment '商品宽度',
    `product_height`                        decimal(8,2) not null comment '商品高度',
    `version`                               bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                            tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                            varchar(50) NOT NULL COMMENT '创建者',
    `updated_by`                            varchar(50) NOT NULL COMMENT '更新者',
    `created_at`                            datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                            datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='销售出库订单条目表';

-- 销售出库订单拣货条目表
drop table if exists wms_sale_delivery_picking_item;
create table wms_sale_delivery_picking_item (
    `id`                                    bigint not null auto_increment comment '主键',
    `sale_delivery_order_item_id`           bigint not null comment '销售出库单条目ID',
    `product_allocation_id`                 bigint not null comment '货位ID',
    `picking_count`                         int(10) not null comment '发多少件商品',
    `version`                               bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                            tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                            varchar(50) NOT NULL COMMENT '创建者',
    `updated_by`                            varchar(50) NOT NULL COMMENT '更新者',
    `created_at`                            datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                            datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='销售出库订单拣货条目表';


-- 仓库商品库存表
drop table if exists wms_warehouse_product_stock;
create table wms_warehouse_product_stock (
    `id`                                    bigint(20) not null auto_increment comment '主键',
    `product_sku_id`                        bigint(20) not null comment '商品sku ID',
    `available_stock_quantity`              int(10) not null comment '可用库存数量',
    `locked_stock_quantity`                 int(10) not null comment '锁定库存数量',
    `deliveried_stock_quantity`             int(10) not null comment '已出库库存数量',
    `version`                               bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                            tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                            varchar(50) NOT NULL COMMENT '创建者',
    `updated_by`                            varchar(50) NOT NULL COMMENT '更新者',
    `created_at`                            datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                            datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='仓库商品库存表';
