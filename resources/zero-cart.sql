DROP TABLE IF EXISTS `oms_cart`;
CREATE TABLE `oms_cart` (
  `id` bigint(20) NOT NULL COMMENT '购物车主键ID',
  `member_id` bigint(20) NOT NULL COMMENT '购物车所属用户ID',
  `product_id` bigint(20) NOT NULL COMMENT '商品SPU ID',
  `sku_id` bigint(20) NOT NULL COMMENT '商品SKU ID',
  `sku_name` varchar(255) DEFAULT NULL COMMENT 'SKU名称',
  `sku_pic` varchar(255) DEFAULT NULL COMMENT 'SKU图片',
  `quantity` int(3) DEFAULT NULL COMMENT '购买数量',
  `price` decimal(10,2) DEFAULT NULL COMMENT '价格',
  `sale_attr` varchar(50) DEFAULT NULL COMMENT '销售属性',
  `checked` tinyint(1) DEFAULT 1 COMMENT '是否选中 0未选中 1选中',
  `status` tinyint(1) DEFAULT 1 COMMENT '状态：0失效 1有效',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='购物车记录表';
