------商品相关的表


DROP TABLE IF EXISTS `pms_product`;
CREATE TABLE `pms_product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `name` varchar(255) NOT NULL COMMENT '商品名称',
  `sub_name` varchar(255) NOT NULL COMMENT '商品副名称',
  `default_price` decimal(8,2) NOT NULL COMMENT '默认价格',
  `default_pic` varchar(255) NOT NULL COMMENT '商品默认图片地址',
  `category_id` bigint(20) NOT NULL COMMENT '类目ID',
  `brand_id` bigint(20) NOT NULL COMMENT '品牌ID',
  `brand_name` varchar(50) NOT NULL COMMENT '品牌名称',
  `gross_weight` decimal(8,2) NOT NULL COMMENT '商品毛重，单位:g',
  `length` decimal(8,2) NOT NULL COMMENT '外包装长度，单位:cm',
  `width` decimal(8,2) NOT NULL COMMENT '外包装宽，单位:cm',
  `height` decimal(8,2) NOT NULL COMMENT '外包装高，单位:cm',
  `service_guarantees` varchar(255) NOT NULL COMMENT '服务保证，多标语逗号隔开',
  `delete_status` tinyint(1) DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  `publish_status` tinyint(1) DEFAULT NULL COMMENT '上架状态：0->下架；1->上架',
  `verify_status` tinyint(1) DEFAULT NULL COMMENT '审核状态：0->未审核；1->审核通过',
  `package_list` varchar(1024) NOT NULL COMMENT '包装清单',
  `freight_template_id` bigint(20) NOT NULL COMMENT '运费模板ID',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_category_id` (`category_id`), 
  KEY `idx_brand_id` (`brand_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品基础信息表';


DROP TABLE IF EXISTS `pms_product_images`;
CREATE TABLE `pms_product_images` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `product_id` bigint(20) DEFAULT NULL COMMENT '商品ID',
  `img_name` varchar(200) DEFAULT NULL COMMENT '图片名',
  `img_url` varchar(255) DEFAULT NULL COMMENT '图片地址',
  `sort` int(11) DEFAULT NULL COMMENT '排序，降序排列',
  `default_img` tinyint(4) DEFAULT NULL COMMENT '是否默认图',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品SPU图片表';




DROP TABLE IF EXISTS `pms_product_detail`;
CREATE TABLE `pms_product_detail` (
  `product_id` bigint(20) NOT NULL COMMENT '商品ID',
  `detail_html` text COMMENT 'PC商品详情富文本内容',
  `detail_mobile` text COMMENT '移动端商品详情富文本内容',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品详情页数据表';



DROP TABLE IF EXISTS `pms_sku`;
CREATE TABLE `pms_sku` (
  `id` bigint(20) NOT NULL COMMENT 'sku主键ID',
  `product_id` bigint(20) DEFAULT NULL COMMENT '商品SPU的ID',
  `category_id` bigint(20) DEFAULT NULL COMMENT '所属分类id',
  `brand_id` bigint(20) DEFAULT NULL COMMENT '品牌id',
  `img_url` varchar(255) DEFAULT NULL COMMENT 'sku缩略图片地址',
  `purchase_price` decimal(8,2) NOT NULL COMMENT '采购价格',
  `promotion_price` decimal(8,2) NOT NULL COMMENT '促销价格',
  `sale_price` decimal(8,2) NOT NULL COMMENT '销售价格',
  `sale_data` varchar(1024) NOT NULL COMMENT '商品销售属性，json格式',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='sku信息表';


DROP TABLE IF EXISTS `pms_attribute`;
CREATE TABLE `pms_attribute` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '属性主键id',
  `attribute_name` varchar(255) NOT NULL COMMENT '属性名称',
  `icon` varchar(255) DEFAULT NULL COMMENT '属性图标地址',  
  `search_type` tinyint(4) DEFAULT NULL COMMENT '是否需要检索[0-不需要，1-需要]',
  `value_type` tinyint(4) DEFAULT NULL COMMENT '值类型[0-为单个值，1-可以选择多个值]',
  `value_select` char(255) DEFAULT NULL COMMENT '可选值列表[用分号分隔]',
  `attribute_type` tinyint(1) DEFAULT NULL COMMENT '属性类型[0-销售属性，1-基本属性]',
  `status` tinyint(1) DEFAULT NULL COMMENT '启用状态[0 - 禁用，1 - 启用]',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品属性';


DROP TABLE IF EXISTS `pms_product_attribute_value`;
CREATE TABLE `pms_product_attribute_value` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `product_id` bigint(20) NOT NULL COMMENT '商品ID',
  `attribute_id` bigint(20) DEFAULT NULL COMMENT '属性id',
  `attribute_name` varchar(200) DEFAULT NULL COMMENT '属性名',
  `attribute_value` varchar(200) DEFAULT NULL COMMENT '属性值',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品属性值表';



DROP TABLE IF EXISTS `pms_sku_sale_attribute_value`;
CREATE TABLE `pms_sku_sale_attribute_value` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sku_id` bigint(20) DEFAULT NULL COMMENT 'sku_id',
  `attribute_id` bigint(20) DEFAULT NULL COMMENT 'attribute_id',
  `attribute_name` varchar(200) DEFAULT NULL COMMENT '销售属性名',
  `attribute_value` varchar(200) DEFAULT NULL COMMENT '销售属性值',
  `attribute_sort` int(11) DEFAULT NULL COMMENT '顺序',
  PRIMARY KEY (`id`),
  KEY `idx_sku_id` (`sku_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='sku销售属性值表'


DROP TABLE IF EXISTS `pms_attribute_group`;
CREATE TABLE `pms_attribute_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '属性分组主键id',
  `group_name` char(20) DEFAULT NULL COMMENT '组名',
  `category_id` bigint(20) DEFAULT NULL COMMENT '所属分类id',
  `desc` varchar(255) DEFAULT NULL COMMENT '分组描述',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
    `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='属性分组表';


DROP TABLE IF EXISTS `pms_attribute_attrgroup_relation`;
CREATE TABLE `pms_attribute_attrgroup_relation` (
  `attribute_id` bigint(20) DEFAULT NULL COMMENT '属性id',
  `attribute_group_id` bigint(20) DEFAULT NULL COMMENT '属性分组id',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='属性&属性分组关联表';


DROP TABLE IF EXISTS `pms_brand`;
CREATE TABLE `pms_brand` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '品牌主键id',
  `chinese_name` varchar(100) NOT NULL COMMENT '品牌中文名',
  `english_name` varchar(100) NOT NULL COMMENT '品牌英文名',
  `alias_name` varchar(50) NOT NULL COMMENT '品牌别名',
  `first_letter` varchar(2) DEFAULT NULL COMMENT '首字母',
  `logo` varchar(255) DEFAULT NULL COMMENT '品牌logo地址',
  `description` varchar(1024) NOT NULL COMMENT '品牌介绍',
  `auth_pic` varchar(1024) NOT NULL COMMENT '品牌授权图片',
  `location` varchar(1024) NOT NULL COMMENT '品牌所在地',
  `show_status` tinyint(4) DEFAULT NULL COMMENT '显示状态[0-不显示；1-显示]',
  `remark` varchar(1024) DEFAULT NULL COMMENT '品牌说明备注',
  `sort` int(3) DEFAULT '0' COMMENT '排序',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='品牌表';



DROP TABLE IF EXISTS `pms_category`;
CREATE TABLE `pms_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '分类id',
  `name` varchar(10) DEFAULT NULL COMMENT '分类名称',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父类目的主键',
  `level` tinyint(1) DEFAULT NULL COMMENT '分类级别：1->1级；2->2级 3->3级',
  `status` tinyint(1) DEFAULT NULL COMMENT '是否显示[0-不显示，1显示]',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `icon` char(255) DEFAULT NULL COMMENT '图标地址',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品三级分类表';



DROP TABLE IF EXISTS `pms_category_brand_relation`;
CREATE TABLE `pms_category_brand_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `brand_id` bigint(20) DEFAULT NULL COMMENT '品牌id',
  `category_id` bigint(20) DEFAULT NULL COMMENT '分类id',
  `brand_name` varchar(100) DEFAULT NULL,
  `category_name` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='品牌&分类关联表';
