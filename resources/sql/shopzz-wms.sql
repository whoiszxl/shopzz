-- 采购订单表
drop table if exists wms_purchase_order;
create table wms_purchase_order
(
    `id`                                    bigint not null auto_increment comment '主键',
    `supplier_id`                           bigint not null comment '供应商ID',
    `expect_arrival_time`                   datetime not null comment '预计到货时间',
    `arrival_time`                          datetime comment '实际到货时间',
    `purchase_contactor`                    varchar(128) not null comment '采购联系人',
    `purchase_contact_phone_number`         varchar(16) not null comment '采购人联系电话',
    `purchase_contact_email`                varchar(128) not null comment '采购人联系邮箱',
    `comment`                               varchar(256) not null comment '说明备注',
    `purchaser`                             varchar(256) not null comment '采购员',
    `purchase_order_status`                 tinyint not null comment '采购单状态, 1:编辑中, 2:待审核, 3:已审核, 4:待入库, 5:审核待入库, 6:已入库, 7:待结算, 8:已完成',
    `version`                               bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                            tinyint(1) DEFAULT 0 COMMENT '逻辑删除 1: 已删除,  0: 未删除',
    `created_by`                            varchar(64) NOT NULL COMMENT '创建者',
    `updated_by`                            varchar(64) NOT NULL COMMENT '更新者',
    `created_at`                            datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                            datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    primary key (id)
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT '采购订单表';

-- 采购订单商品详情表
drop table if exists wms_purchase_order_item;
create table wms_purchase_order_item
(
    `id`                        bigint not null auto_increment comment '主键',
    `purchase_order_id`         bigint not null comment '采购单ID',
    `sku_id`                    bigint not null comment '商品SKU ID',
    `purchase_quantity`         bigint not null comment '采购数量',
    `purchase_price`            decimal(8,2) not null comment '采购价格',
    `qualified_count`           int(10) not null DEFAULT 0 comment '合格商品的数量',
    `arrival_count`             int(10) not null DEFAULT 0 comment '到货的商品数量',
    `warehouse_id`              bigint not null comment '仓库ID',
    `warehouse_shelf_id`        bigint not null comment '仓库货架ID',
    `version`                   bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                tinyint(1) DEFAULT 0 COMMENT '逻辑删除 1: 已删除,  0: 未删除',
    `created_by`                varchar(64) NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`                varchar(64) NOT NULL DEFAULT '' COMMENT '更新者',
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   primary key (id)
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT '采购订单商品详情表';

-- 采购供应商表
drop table if exists wms_purchase_supplier;
create table wms_purchase_supplier
(
    `id`                        bigint not null auto_increment comment '主键',
    `supplier_name`             varchar(128) not null comment '供应商名称',
    `company_name`              varchar(128) not null comment '公司名称',
    `company_address`           varchar(128) not null comment '公司地址',
    `contactor`                 varchar(64) not null comment '联系人',
    `contact_phone_number`      varchar(16) not null comment '联系电话',
    `account_period`            tinyint(1) not null comment '账期, 1:周结算, 2:月结算, 3:季度结算',
    `bank_name`                 varchar(60) not null comment '银行名称',
    `bank_account`              varchar(128) not null comment '银行账户',
    `account_holder`            varchar(64) not null comment '开户人',
    `invoice_title`             varchar(256) not null comment '发票抬头',
    `taxpayer_id`               varchar(256) not null comment '纳税人识别号',
    `business_scope`            varchar(128) not null comment '经营范围',
    `supplier_comment`          varchar(256) comment '说明备注',
    `enterprise_qualification`  varchar(256) not null comment '企业资质',
    `cooperate_contract`        longblob not null comment '合作合同',
    `price_contract`            longblob not null comment '协议价合同',
    `purchase_contract`         longblob not null comment '采购合同',
    `version`                   bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                tinyint(1) DEFAULT 0 COMMENT '逻辑删除 1: 已删除,  0: 未删除',
    `created_by`                varchar(64) NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`                varchar(64) NOT NULL DEFAULT '' COMMENT '更新者',
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   primary key (id)
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT '采购供应商表';





-- WMS仓库表
drop table if exists wms_warehouse;
create table wms_warehouse
(
    `id`                            bigint not null auto_increment comment '主键',
    `supplier_name`                 varchar(128) not null comment '供应商名称',
    `warehouse_name`                varchar(128) not null comment '仓库名称',
    `warehouse_type`                tinyint(1) not null default 1 comment '仓库类型: 1-自营仓',
    `warehouse_address`             varchar(64) not null comment '仓库地址',
    `warehouse_admin_name`          varchar(64) not null comment '仓库管理员姓名',
    `warehouse_admin_phone`         varchar(16) not null comment '仓库管理员联系电话',
    `version`                       bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                    tinyint(1) DEFAULT 0 COMMENT '逻辑删除 1: 已删除,  0: 未删除',
    `created_by`                    varchar(64) NOT NULL COMMENT '创建者',
    `updated_by`                    varchar(64) NOT NULL COMMENT '更新者',
    `created_at`                    datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                    datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='WMS仓库表';


-- WMS仓库货架表
drop table if exists wms_warehouse_shelf;
create table wms_warehouse_shelf
(
    `id`                            bigint not null auto_increment comment '主键',
    `warehouse_id`                  bigint not null comment '仓库ID',
    `shelf_code`                    varchar(256) not null comment '货架编号',
    `shelf_comment`                 varchar(256) comment '说明备注',
    `version`                       bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                    tinyint(1) DEFAULT 0 COMMENT '逻辑删除 1: 已删除,  0: 未删除',
    `created_by`                    varchar(64) NOT NULL COMMENT '创建者',
    `updated_by`                    varchar(64) NOT NULL COMMENT '更新者',
    `created_at`                    datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                    datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='WMS仓库货架表';

-- WMS仓库SKU表
drop table if exists wms_warehouse_sku;
create table wms_warehouse_sku (
    `id`                            bigint not null auto_increment comment '主键',
    `shelf_id`                      bigint not null comment '货架ID',
    `sku_id`                        bigint not null comment '商品SKU ID',
    `sku_code`                      varchar(128) not null comment '商品sku编号',
    `sku_name`                      varchar(256) not null comment '商品SKU名称',
    `purchase_price`                decimal(8,2) not null comment '商品采购价格',
    `length`                        decimal(8,2) not null comment '商品长度',
    `width`                         decimal(8,2) not null comment '商品宽度',
    `height`                        decimal(8,2) not null comment '商品高度',
    `gross_weight`                  decimal(8,2) not null comment '商品毛重',
    `version`                       bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                    tinyint(1) DEFAULT 0 COMMENT '逻辑删除 1: 已删除,  0: 未删除',
    `created_by`                    varchar(64) NOT NULL COMMENT '创建者',
    `updated_by`                    varchar(64) NOT NULL COMMENT '更新者',
    `created_at`                    datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                    datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='WMS仓库SKU表';

-- WMS仓库商品库存表
drop table if exists wms_warehouse_sku_stock;
create table wms_warehouse_sku_stock (
    `id`                            bigint not null auto_increment comment '主键',
    `sku_id`                        bigint not null comment '商品SKU ID',
    `sku_name`                      varchar(256) not null comment '商品SKU名称',
    `available_stock_quantity`      int(10) not null comment '可用库存数量',
    `locked_stock_quantity`         int(10) not null comment '锁定库存数量',
    `warn_stock_quantity`           int(10) not null comment '预警库存数量',
    `version`                       bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                    tinyint(1) DEFAULT 0 COMMENT '逻辑删除 1: 已删除,  0: 未删除',
    `created_by`                    varchar(64) NOT NULL COMMENT '创建者',
    `updated_by`                    varchar(64) NOT NULL COMMENT '更新者',
    `created_at`                    datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                    datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='WMS仓库商品库存表';










-- 销售出库订单表
drop table if exists wms_outbound_sell_order;
create table wms_outbound_sell_order (
    `id`                                    bigint not null auto_increment comment '主键',
    `warehouse_id`                          bigint(20) comment '仓库ID',
    `warehouse_shelf_id`                    bigint(20) comment '货架ID',
    `member_id`                             bigint(20) comment '用户账号ID',
    `order_id`                              bigint not null comment '订单ID',
    `order_no`                              varchar(128) not null comment '订单编号',
    `receive_name`                          varchar(128) not null comment '收货人',
    `receive_address`                       varchar(256) not null comment '收货地址',
    `receive_phone`                         varchar(16) not null comment '收货人电话号码',
    `express_no`                            varchar(64) comment '快递单号',
    `express_code`                          varchar(64) comment '快递公司编码',
    `express_freight`                       decimal(8,2) not null comment '运费',
    `pay_type`                              tinyint(4) NOT NULL COMMENT '支付方式: [1:支付宝 2:微信 3:数字货币]',
    `total_amount`                          decimal(9,2) comment '订单总额',
    `freight_amount`                        decimal(9,2) comment '运费金额',
    `promotion_amount`                      decimal(9,2) comment '促销折扣金额',
    `point_amount`                          decimal(9,2) comment '积分抵扣金额',
    `coupon_amount`                         decimal(9,2) comment '优惠券抵扣金额',
    `admin_discount_amount`                 decimal(9,2) comment '后台调整订单使用的折扣金额',
    `pay_amount`                            decimal(9,2) comment '应付总额',
    `order_comment`                         varchar(256) NOT NULL COMMENT '订单备注',
    `sell_outbound_order_status`            tinyint not null comment '销售出库单的状态, 1:编辑中, 2:待审核, 3:已完成',
    `outbound_time`                         datetime not null comment '发货时间',
    `version`                               bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                            tinyint(1) DEFAULT 0 COMMENT '逻辑删除 1: 已删除,  0: 未删除',
    `created_by`                            varchar(64) NOT NULL COMMENT '创建者',
    `updated_by`                            varchar(64) NOT NULL COMMENT '更新者',
    `created_at`                            datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                            datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='销售出库订单表';

-- 销售出库订单item表
drop table if exists wms_outbound_sell_order_item;
create table wms_outbound_sell_order_item (
    `id`                                    bigint not null auto_increment comment '主键',
    `outbound_sell_order_id`                bigint not null comment '销售出库单ID',
    `sku_id`                                bigint not null comment '商品sku ID',
    `sku_code`                              varchar(128) not null comment '商品sku编号',
    `sku_name`                              varchar(256) not null comment '商品名称',
    `sell_properties`                       varchar(300) not null comment '销售属性, 机身颜色:白色,内存容量:256G',
    `quantity`                              int(10) not null comment '销售出库数量',
    `price`                                 decimal(8,2) not null comment '销售出库商品购买价格',
    `final_price`                           decimal(8,2) not null comment '销售出库商品优惠后折算最终价格',
    `version`                               bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                            tinyint(1) DEFAULT 0 COMMENT '逻辑删除 1: 已删除,  0: 未删除',
    `created_by`                            varchar(64) NOT NULL COMMENT '创建者',
    `updated_by`                            varchar(64) NOT NULL COMMENT '更新者',
    `created_at`                            datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                            datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='销售出库订单item表';












-- 退货入库订单表
drop table if exists wms_inbound_return_order;
create table wms_inbound_return_order(
    `id`                                    bigint not null auto_increment comment '主键',
    `warehouse_id`                          bigint(20) comment '仓库ID',
    `warehouse_shelf_id`                    bigint(20) comment '货架ID',
    `member_id`                             bigint(20) comment '用户账号ID',
    `order_id`                              bigint not null comment '订单ID',
    `order_no`                              varchar(128) not null comment '订单编号',
    `inbound_return_order_status`           tinyint not null comment '退货入库单状态, 1:编辑中, 2:待审核:3:已完成',
    `receive_name`                          varchar(128) not null comment '收货人',
    `receive_address`                       varchar(256) not null comment '收货地址',
    `receive_phone`                         varchar(16) not null comment '收货人电话号码',
    `freight`                               decimal(8,2) not null comment '用户支付运费',
    `pay_type`                              tinyint(4) NOT NULL COMMENT '支付方式: [1:支付宝 2:微信 3:数字货币]',
    `total_amount`                          decimal(9,2) comment '订单总额',
    `freight_amount`                        decimal(9,2) comment '运费金额',
    `promotion_amount`                      decimal(9,2) comment '促销折扣金额',
    `point_amount`                          decimal(9,2) comment '积分抵扣金额',
    `coupon_amount`                         decimal(9,2) comment '优惠券抵扣金额',
    `admin_discount_amount`                 decimal(9,2) comment '后台调整订单使用的折扣金额',
    `pay_amount`                            decimal(9,2) comment '应付总额',
    `order_comment`                         varchar(256) NOT NULL COMMENT '订单备注',
    `return_reason`                         tinyint(4) NOT NULL COMMENT '退货原因, 1:质量不好, 2:商品不满意, 3:买错了, 4:无理由退货',
    `return_comment`                        varchar(256) NOT NULL COMMENT '退货备注',
    `arrival_time`                          datetime not null comment '到货时间',
    `version`                               bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                            tinyint(1) DEFAULT 0 COMMENT '逻辑删除 1: 已删除,  0: 未删除',
    `created_by`                            varchar(64) NOT NULL COMMENT '创建者',
    `updated_by`                            varchar(64) NOT NULL COMMENT '更新者',
    `created_at`                            datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                            datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='退货入库订单表';

-- 退货入库订单条目表
drop table if exists wms_inbound_return_order_item;
create table wms_inbound_return_order_item (
    `id`                                    bigint(20) not null auto_increment comment '主键',
    `inbound_return_order_id`               bigint(20) not null comment '退货入库单ID',
    `sku_id`                                bigint(20) not null comment '商品sku ID',
    `sku_code`                              varchar(128) not null comment '商品sku编号',
    `sku_name`                              varchar(256) not null comment '商品名称',
    `sell_properties`                       varchar(300) not null comment '销售属性, 机身颜色:白色,内存容量:256G',
    `quantity`                              int(10) not null comment '退货数量',
    `price`                                 decimal(8,2) not null comment '退货商品购买价格',
    `final_price`                           decimal(8,2) not null comment '商品优惠后折算最终价格,最终能退的价格',
    `arrival_quantity`                      int(10) not null comment '商品到货数量',
    `qualified_quantity`                    int(10) not null comment '合格商品数量',
    `version`                               bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                            tinyint(1) DEFAULT 0 COMMENT '逻辑删除 1: 已删除,  0: 未删除',
    `created_by`                            varchar(64) NOT NULL COMMENT '创建者',
    `updated_by`                            varchar(64) NOT NULL COMMENT '更新者',
    `created_at`                            datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                            datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='退货入库订单条目表';