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
