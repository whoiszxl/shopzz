-- liquibase formatted sql

-- changeset whoiszxl:1
-- comment 初始化order表结构
DROP TABLE IF EXISTS `oms_order`;
CREATE TABLE `oms_order` (
    `id`                        bigint(11) NOT NULL COMMENT '主键',
    `order_no`                  varchar(20) NOT NULL COMMENT '订单编号',
    `member_id`                 bigint(11) DEFAULT NULL COMMENT '用户ID',
    `username`                  varchar(64) NOT NULL COMMENT '用户名',
    `order_status`              tinyint(4) NOT NULL COMMENT '订单状态,1:待付款,2:已取消,3:待发货,4:待收货,5:已完成,6:售后中（退货申请待审核）,7:交易关闭（退货审核不通过）,8:交易中（待寄送退货商品）,9:售后中（退货商品待收货）,10:售后中（退货待入库）,11:（1）售后中（退货已入库）,12:交易关闭（完成退款）',
    `snapshot_address`          json NOT NULL comment '收货人姓名',
    `pay_type`                  tinyint(4) NOT NULL COMMENT '支付方式: [1:支付宝 2:微信 3:数字货币]',
    `total_amount`              decimal(9,2) comment '订单总额',
    `freight_amount`            decimal(9,2) comment '运费金额',
    `promotion_amount`          decimal(9,2) comment '促销折扣金额',
    `point_amount`              decimal(9,2) comment '积分抵扣金额',
    `coupon_amount`             decimal(9,2) comment '优惠券抵扣金额',
    `admin_discount_amount`     decimal(9,2) comment '后台调整订单使用的折扣金额',
    `final_pay_amount`          decimal(9,2) comment '最终订单应付总额',
    `order_comment`             varchar(255) NOT NULL COMMENT '订单备注',
    `payment_time`              datetime comment '支付时间',
    `delivery_time`             datetime comment '发货时间',
    `receive_time`              datetime comment '确认收货时间',
    `comment_time`              datetime comment '评价时间',
    `version`                   bigint(11) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除, 0: 未删除',
    `created_by`                varchar(64) NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`                varchar(64) NOT NULL DEFAULT '' COMMENT '更新者',
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_member_id` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';


DROP TABLE IF EXISTS `oms_order_item`;
CREATE TABLE `oms_order_item` (
    `id`                        bigint(11) NOT NULL COMMENT '主键',
    `order_id`                  bigint(11) NOT NULL COMMENT '订单ID',
    `order_no`                  varchar(20) NOT NULL COMMENT '订单编号',
    `product_id`                bigint(11) NOT NULL COMMENT '商品id',
    `category_id`               bigint(11) NOT NULL COMMENT '分类id',
    `sku_id`                    bigint(11) NOT NULL COMMENT 'sku id',
    `sku_name`                  varchar(128) NOT NULL COMMENT 'sku名称',
    `sku_pic`                   varchar(128) NOT NULL COMMENT 'sku图片地址',
    `sku_price`                 decimal(9,2) NOT NULL COMMENT 'sku价格',
    `quantity`                  int(3) NOT NULL COMMENT '购买数量',
    `sku_attrs`                 varchar(255) comment '商品销售属性',
    `promotion_activity_id`     bigint(11) DEFAULT NULL COMMENT '促销活动ID',
    `promotion_amount`          decimal(9,2) comment '商品促销分解金额',
    `coupon_amount`             decimal(9,2) comment '优惠券优惠分解金额',
    `point_amount`              decimal(9,2) comment '积分优惠分解金额',
    `real_amount`               decimal(9,2) comment '该商品经过优惠后的分解金额',
    `version`                   bigint(11) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除, 0: 未删除',
    `created_by`                varchar(64) NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`                varchar(64) NOT NULL DEFAULT '' COMMENT '更新者',
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单明细表';

DROP TABLE IF EXISTS `oms_order_invoice`;
CREATE TABLE `oms_order_invoice` (
    `id`                        bigint(11) NOT NULL COMMENT '主键',
    `order_id`                  bigint(11) NOT NULL COMMENT '订单ID',
    `invoice_type`              tinyint comment '发票类型[0->不开发票;1->电子发票;2->纸质发票]',
    `invoice_title`             varchar(255) comment '发票抬头',
    `invoice_content`           varchar(255) comment '发票内容',
    `invoice_tax_no`            varchar(255) comment '发票税号',
    `invoice_receiver_phone`    varchar(32) comment '收票人电话',
    `invoice_receiver_email`    varchar(64) comment '收票人邮箱',   
    `invoice_receiver_address`  varchar(64) comment '收票人收货地址',
    `total_amount`              decimal(9,2) comment '开票金额',
    `company_name`              varchar(64) comment '公司名称[增值税]',
    `company_address`           varchar(64) comment '公司地址[增值税]',
    `telphone`                  varchar(64) comment '联系电话[增值税]',
    `bank_name`                 varchar(64) comment '开户银行[增值税]',
    `bank_account`              varchar(64) comment '银行账号[增值税]',
    `version`                   bigint(11) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除, 0: 未删除',
    `created_by`                varchar(64) NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`                varchar(64) NOT NULL DEFAULT '' COMMENT '更新者',
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单发票表';

DROP TABLE IF EXISTS `oms_order_operate_history`;
CREATE TABLE `oms_order_operate_history` (
    `id`                        bigint(11) NOT NULL COMMENT '主键',
    `order_id`                  bigint(11) NOT NULL COMMENT '订单ID',
    `operate_type`              tinyint(4) NOT NULL COMMENT '操作类型,1:创建订单,2:手动取消订单,3:自动取消订单,4:支付订单,5:手动确认收货,6:自动确认收货,7:商品发货,8:申请退货,9:退货审核不通过,10:退货审核通过,11:寄送退货商品,12:确认收到退货,13:退货已入库,14:完成退款',
    `operate_note`              varchar(1024) NOT NULL COMMENT '操作备注',
    `version`                   bigint(11) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除, 0: 未删除',
    `created_by`                varchar(64) NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`                varchar(64) NOT NULL DEFAULT '' COMMENT '更新者',
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单操作历史记录表';



DROP TABLE IF EXISTS `oms_order_return_apply`;
CREATE TABLE `oms_order_return_apply` (
    `id`                        bigint(11) NOT NULL COMMENT '主键',
    `order_id`                  bigint(11) NOT NULL COMMENT '订单ID',
    `order_item_id`             bigint(11) NOT NULL COMMENT '订单明细项ID',
    `order_no`                  varchar(20) NOT NULL COMMENT '订单编号',
    `sku_id`                    bigint(11) NOT NULL COMMENT '订单中SKU的ID',
    `username`                  varchar(64) NOT NULL COMMENT '用户名',
    `freight`                   decimal(8,2) not null comment '运费',
    `return_count`              int(10) NOT NULL comment '退货数量',
    `return_reason`             tinyint(4) NOT NULL COMMENT '退货原因,1:质量不好,2:商品不满意,3:买错了,4:无理由退货',
    `return_comment`            varchar(255) NOT NULL COMMENT '退货备注',
    `return_pic`                varchar(1024) NOT NULL COMMENT '退货图片备注,逗号分割',
    `return_apply_status`       tinyint(4) NOT NULL COMMENT '退货记录状态,1:待审核,2:审核不通过,3:审核通过',
    `return_logistic_code`      varchar(128) DEFAULT NULL COMMENT '退货快递单号',
    `return_receive_name`       varchar(128) comment '收货人',
    `return_receive_note`       varchar(128) comment '收货备注',
    `return_receive_phone`      varchar(20) comment '收货电话',
    `return_company_address`    varchar(128) comment '公司收货地址',
    `return_receive_time`       datetime comment '收货时间',
    `handle_note`               varchar(128) comment '处理备注',
    `handle_by`                 varchar(64) comment '处理人员',
    `version`                   bigint(11) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除, 0: 未删除',
    `created_by`                varchar(64) NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`                varchar(64) NOT NULL DEFAULT '' COMMENT '更新者',
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单退货表';


DROP TABLE IF EXISTS `oms_pay_info`;
CREATE TABLE `oms_pay_info` (
    `id`                        bigint(11) NOT NULL COMMENT '主键',
    `order_id`                  bigint(11) NOT NULL COMMENT '订单ID',
    `order_no`                  varchar(20) NOT NULL COMMENT '订单编号',
    `member_id`                 bigint(11) DEFAULT NULL COMMENT '用户ID',
    `total_amount`              decimal(8,2) NOT NULL COMMENT '订单总支付金额',
    `trade_channel`             tinyint(3) NOT NULL COMMENT '交易渠道,1:支付宝,2:微信',
    `trade_no`                  varchar(500) DEFAULT NULL COMMENT '交易流水号,第三方支付平台生成',
    `status`                    tinyint(3) NOT NULL COMMENT '支付状态,1:待支付,2:支付成功,3:支付失败,4:交易关闭;5:退款',
    `complated_time`            datetime DEFAULT NULL COMMENT '完成第三方支付的时间',  
    `version`                   bigint(11) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除, 0: 未删除',
    `created_by`                varchar(64) NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`                varchar(64) NOT NULL DEFAULT '' COMMENT '更新者',
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='第三方支付信息表';


DROP TABLE IF EXISTS `oms_pay_info_dc`;
CREATE TABLE `oms_pay_info_dc` (
    `id`                        bigint(11) NOT NULL COMMENT '主键',
    `order_id`                  bigint(11) NOT NULL COMMENT '订单ID',
    `order_no`                  varchar(20) NOT NULL COMMENT '订单编号',
    `member_id`                 bigint(11) DEFAULT NULL COMMENT '用户ID',
    `currency_id`               int(10) NOT NULL COMMENT '币种ID',
    `currency_name`             varchar(32) NOT NULL COMMENT '货币名称',
    `tx_hash`                   varchar(255) NOT NULL DEFAULT '' COMMENT '交易hash',
    `total_amount`              decimal(8,2) NOT NULL COMMENT '订单总支付金额',
    `from_address`              varchar(255) DEFAULT NULL DEFAULT '' COMMENT '用户的出币地址',
    `to_address`                varchar(255) DEFAULT NULL COMMENT '关联的充值地址',
    `qrcode_data`               varchar(255) DEFAULT NULL DEFAULT '' COMMENT '二维码数据',
    `upchain_at`                datetime COMMENT '上链时间',
    `upchain_success_at`        datetime COMMENT '上链成功时间',
    `upchain_status`            tinyint(1) NOT NULL DEFAULT '2' COMMENT '上链状态,1:上链并确认成功 2:等待确认中 3:未上链',
    `current_confirm`           bigint(11) DEFAULT NULL COMMENT '当前交易确认数',
    `height`                    bigint(11) DEFAULT NULL COMMENT '当前交易所处区块的高度',
    `version`                   bigint(11) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除, 0: 未删除',
    `created_by`                varchar(64) NOT NULL DEFAULT '' COMMENT '创建者',
    `updated_by`                varchar(64) NOT NULL DEFAULT '' COMMENT '更新者',
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='数字货币支付信息表';

DROP TABLE IF EXISTS `oms_cart`;
CREATE TABLE `oms_cart` (
    `id`                            bigint(20) NOT NULL COMMENT '购物车主键ID',
    `member_id`                     bigint(20) NOT NULL COMMENT '购物车所属用户ID',
    `product_id`                    bigint(20) NOT NULL COMMENT '商品SPU ID',
    `sku_id`                        bigint(20) NOT NULL COMMENT '商品SKU ID',
    `sku_name`                      varchar(255) DEFAULT NULL COMMENT 'SKU名称',
    `sku_pic`                       varchar(255) DEFAULT NULL COMMENT 'SKU图片',
    `quantity`                      int(3) DEFAULT NULL COMMENT '购买数量',
    `price`                         decimal(10,2) DEFAULT NULL COMMENT '价格',
    `sale_attr`                     varchar(50) DEFAULT NULL COMMENT '销售属性',
    `checked`                       tinyint(1) DEFAULT 1 COMMENT '是否选中 0未选中 1选中',
    `status`                        tinyint(1) DEFAULT 1 COMMENT '状态:0失效 1有效',
    `version`                       bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                    tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除, 0: 未删除',
    `created_by`                    varchar(50) NOT NULL COMMENT '创建者',
    `updated_by`                    varchar(50) NOT NULL COMMENT '更新者',
    `created_at`                    datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                    datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='购物车记录表';