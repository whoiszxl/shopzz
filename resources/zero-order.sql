DROP TABLE IF EXISTS `oms_order`;
CREATE TABLE `oms_order` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `order_sn` char(16) NOT NULL COMMENT '订单编号',
  `member_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `order_status` tinyint(4) NOT NULL COMMENT '订单状态，1：待付款，2：已取消，3：待发货，4：待收货，5：已完成，6：售后中（退货申请待审核），7：交易关闭（退货审核不通过），8：交易中（待寄送退货商品），9：售后中（退货商品待收货），10：售后中（退货待入库），11：（1）售后中（退货已入库），12：交易关闭（完成退款）',
  `receiver_name`  varchar(100) comment '收货人姓名',
  `receiver_phone` varchar(32) comment '收货人电话',
  `receiver_post_code` varchar(32) comment '收货人邮编',
  `receiver_province` varchar(32) comment '省份/直辖市',
  `receiver_city` varchar(32) comment '城市',
  `receiver_region` varchar(32) comment '区',
  `receiver_detail_address` varchar(200) comment '详细地址',
  `pay_type` tinyint(4) NOT NULL COMMENT '支付方式: [1:支付宝 2:微信 3:数字货币]',
  `total_amount` decimal(9,2) comment '订单总额',
  `freight_amount` decimal(9,2) comment '运费金额',
  `promotion_amount` decimal(9,2) comment '促销折扣金额',
  `point_amount` decimal(9,2) comment '积分抵扣金额',
  `coupon_amount` decimal(9,2) comment '优惠券抵扣金额',
  `admin_discount_amount` decimal(9,2) comment '后台调整订单使用的折扣金额',
  `pay_amount` decimal(9,2) comment '应付总额',
  `invoice_type` tinyint comment '发票类型[0->不开发票；1->电子发票；2->纸质发票]',
  `invoice_header` varchar(255) comment '发票抬头',
  `invoice_content` varchar(255) comment '发票内容',
  `invoice_receiver_phone` varchar(32) comment '收票人电话',
  `invoice_receiver_email` varchar(64) comment '收票人邮箱',   
  `invoice_receiver_address` varchar(64) comment '收票人收货地址',
  `order_comment` varchar(255) NOT NULL COMMENT '订单备注',
  `payment_time` datetime comment '支付时间',
  `delivery_time` datetime comment '发货时间',
  `receive_time` datetime comment '确认收货时间',
  `comment_time` datetime comment '评价时间',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_member_id` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';


DROP TABLE IF EXISTS `oms_order_item`;

CREATE TABLE `oms_order_item` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `order_id` bigint(20) NOT NULL COMMENT '订单ID',
  `order_sn` char(16) NOT NULL COMMENT '订单编号',
  `product_id` bigint(20) NOT NULL COMMENT '商品id',
  `category_id` bigint(20) NOT NULL COMMENT '分类id',
  `sku_id` bigint(20) NOT NULL COMMENT 'sku id',
  `sku_name` varchar(200) NOT NULL COMMENT 'sku名称',
  `sku_pic` varchar(200) NOT NULL COMMENT 'sku图片地址',
  `sku_price` decimal(9,2) NOT NULL COMMENT 'sku价格',
  `quantity` int(3) NOT NULL COMMENT '购买数量',
  `sku_attrs` varchar(255) comment '商品销售属性',
  `promotion_activity_id` bigint(20) DEFAULT NULL COMMENT '促销活动ID',
  `promotion_amount` decimal(9,2) comment '商品促销分解金额',
  `coupon_amount` decimal(9,2) comment '优惠券优惠分解金额',
  `point_amount` decimal(9,2) comment '积分优惠分解金额',
  `real_amount` decimal(9,2) comment '该商品经过优惠后的分解金额',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单明细表';


DROP TABLE IF EXISTS `oms_order_operate_history`;
CREATE TABLE `oms_order_operate_history` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `order_id` bigint(20) NOT NULL COMMENT '订单ID',
  `operate_by_type` tinyint(2) NOT NULL COMMENT '操作人类型[1:用户；2:系统；3:后台管理员]',
  `operate_by_name` varchar(50) NOT NULL COMMENT '操作人名称',
  `operate_type` tinyint(4) NOT NULL COMMENT '操作类型，1：创建订单，2：手动取消订单，3：自动取消订单，4：支付订单，5：手动确认收货，6：自动确认收货，7：商品发货，8：申请退货，9：退货审核不通过，10：退货审核通过，11：寄送退货商品，12：确认收到退货，13：退货已入库，14：完成退款',
  `operate_note` varchar(1024) NOT NULL COMMENT '操作备注',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单操作历史记录表';




DROP TABLE IF EXISTS `oms_order_return_apply`;

CREATE TABLE `oms_order_return_apply` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `order_id` bigint(20) NOT NULL COMMENT '订单ID',
  `order_item_id` bigint(20) NOT NULL COMMENT '订单明细项ID',
  `order_sn` char(16) NOT NULL COMMENT '订单编号',
  `sku_id` bigint(20) NOT NULL COMMENT '订单中SKU的ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `return_count` int(3) NOT NULL comment '退货数量',
  `return_reason` tinyint(4) NOT NULL COMMENT '退货原因，1：质量不好，2：商品不满意，3：买错了，4：无理由退货',
  `return_comment` varchar(255) NOT NULL COMMENT '退货备注',
  `return_pic` varchar(2000) NOT NULL COMMENT '退货图片备注，逗号分割',
  `return_apply_status` tinyint(4) NOT NULL COMMENT '退货记录状态，1：待审核，2：审核不通过，3：审核通过',
  `return_logistic_code` varchar(1024) DEFAULT NULL COMMENT '退货快递单号',
  `return_receive_name` varchar(100) comment '收货人',
  `return_receive_note` varchar(200) comment '收货备注',
  `return_receive_phone` varchar(20) comment '收货电话',
  `return_company_address` varchar(500) comment '公司收货地址',
  `return_receive_time` datetime comment '收货时间',
  `handle_note` varchar(200) comment '处理备注',
  `handle_by` varchar(50) comment '处理人员',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单退货表';


DROP TABLE IF EXISTS `oms_pay_info`;

CREATE TABLE `oms_pay_info` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `order_id` bigint(20) NOT NULL COMMENT '订单ID',
  `order_sn` char(16) NOT NULL COMMENT '订单编号',
  `member_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `total_amount` decimal(8,2) NOT NULL COMMENT '订单总支付金额',
  `trade_channel` tinyint(4) NOT NULL COMMENT '交易渠道，1：支付宝，2：微信',
  `trade_no` varchar(1024) DEFAULT NULL COMMENT '交易流水号，第三方支付平台生成',
  `status` varchar(1024) NOT NULL COMMENT '支付状态，1：待支付，2：支付成功，3：支付失败，4：交易关闭；5：退款',
  `complated_time` datetime DEFAULT NULL COMMENT '完成第三方支付的时间',  
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='第三方支付信息表';


CREATE TABLE `oms_dc_pay_info` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `order_id` bigint(20) NOT NULL COMMENT '订单ID',
  `order_sn` char(16) NOT NULL COMMENT '订单编号',
  `member_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `currency_id` int(10) NOT NULL COMMENT '币种ID',
  `currency_name` varchar(32) NOT NULL COMMENT '货币名称',
  `tx_hash` varchar(255) NOT NULL COMMENT '交易hash',
  `total_amount` decimal(8,2) NOT NULL COMMENT '订单总支付金额',
  `from_address` varchar(255) DEFAULT NULL COMMENT '用户的出币地址',
  `to_address` varchar(255) DEFAULT NULL COMMENT '关联的充值地址',
  `upchain_at` datetime COMMENT '上链时间',
  `upchain_success_at` datetime COMMENT '上链成功时间',
  `upchain_status` tinyint(1) NOT NULL DEFAULT '2' COMMENT '上链状态，1：上链并确认成功 2：等待确认中 3：未上链',
  `current_confirm` int(20) DEFAULT NULL COMMENT '当前交易确认数',
  `height` int(20) DEFAULT NULL COMMENT '当前交易所处区块的高度',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='数字货币支付信息表';