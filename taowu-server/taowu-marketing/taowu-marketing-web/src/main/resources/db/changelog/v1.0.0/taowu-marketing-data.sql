-- liquibase formatted sql

-- changeset whoiszxl:1
-- comment 初始化promotion表数据

INSERT INTO `spms_activity` (`id`, `name`, `descs`, `start_time`, `end_time`, `img`, `status`, `version`, `is_deleted`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES (1, '一个小小的打折', '', '2023-05-17 22:12:08', '2023-05-17 22:12:09', NULL, 1, 1, 0, '', '', '2023-05-17 22:12:18', '2023-05-17 22:12:20');

INSERT INTO `spms_seckill_item` (`id`, `seckill_id`, `sku_id`, `sku_name`, `sku_descs`, `sku_img`, `spu_name`, `start_time`, `end_time`, `init_stock_quantity`, `available_stock_quantity`, `warm_up_status`, `sku_price`, `seckill_price`, `status`, `version`, `is_deleted`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES (1, 1, 1, 'Apple iPhone 14 (A2884) 支持移动联通电信5G 双卡双待手机 午夜色-128GB', '午夜色-128GB', '//img14.360buyimg.com/n4/jfs/t1/203031/12/29450/34184/6377410eE3d586cb7/f68117f493819d55.jpg','Apple iPhone 14 (A2884) 支持移动联通电信5G 双卡双待手机', '2023-02-03 14:41:18', '2023-02-24 14:41:21', 100, 100, 1, 5999.00, 599.00, 1, 1, 0, '', '', '2023-05-17 22:05:50', '2023-05-17 22:05:50');
INSERT INTO `spms_seckill_item` (`id`, `seckill_id`, `sku_id`, `sku_name`, `sku_descs`, `sku_img`, `spu_name`,  `start_time`, `end_time`, `init_stock_quantity`, `available_stock_quantity`, `warm_up_status`, `sku_price`, `seckill_price`, `status`, `version`, `is_deleted`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES (2, 2, 1, 'Apple iPhone 14 (A2884) 支持移动联通电信5G 双卡双待手机 午夜色-128GB', '午夜色-128GB', '//img14.360buyimg.com/n4/jfs/t1/203031/12/29450/34184/6377410eE3d586cb7/f68117f493819d55.jpg','Apple iPhone 14 (A2884) 支持移动联通电信5G 双卡双待手机', '2023-02-03 14:41:18', '2023-02-24 14:41:21', 100, 100, 1, 5999.00, 599.00, 1, 1, 0, '', '', '2023-05-17 22:05:50', '2023-05-17 22:05:50');
INSERT INTO `spms_seckill_item` (`id`, `seckill_id`, `sku_id`, `sku_name`, `sku_descs`, `sku_img`, `spu_name`, `start_time`, `end_time`, `init_stock_quantity`, `available_stock_quantity`, `warm_up_status`, `sku_price`, `seckill_price`, `status`, `version`, `is_deleted`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES (3, 3, 1, 'Apple iPhone 14 (A2884) 支持移动联通电信5G 双卡双待手机 午夜色-128GB', '午夜色-128GB', '//img14.360buyimg.com/n4/jfs/t1/203031/12/29450/34184/6377410eE3d586cb7/f68117f493819d55.jpg','Apple iPhone 14 (A2884) 支持移动联通电信5G 双卡双待手机', '2023-02-03 14:41:18', '2023-02-24 14:41:21', 100, 100, 1, 5999.00, 599.00, 1, 1, 0, '', '', '2023-05-17 22:05:50', '2023-05-17 22:05:50');
INSERT INTO `spms_seckill_item` (`id`, `seckill_id`, `sku_id`, `sku_name`, `sku_descs`, `sku_img`, `spu_name`, `start_time`, `end_time`, `init_stock_quantity`, `available_stock_quantity`, `warm_up_status`, `sku_price`, `seckill_price`, `status`, `version`, `is_deleted`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES (4, 4, 1, 'Apple iPhone 14 (A2884) 支持移动联通电信5G 双卡双待手机 午夜色-128GB', '午夜色-128GB', '//img14.360buyimg.com/n4/jfs/t1/203031/12/29450/34184/6377410eE3d586cb7/f68117f493819d55.jpg','Apple iPhone 14 (A2884) 支持移动联通电信5G 双卡双待手机', '2023-02-03 14:41:18', '2023-02-24 14:41:21', 100, 100, 1, 5999.00, 599.00, 1, 1, 0, '', '', '2023-05-17 22:05:50', '2023-05-17 22:06:58');

INSERT INTO `spms_seckill` (`id`, `name`, `descs`, `start_time`, `end_time`, `img`, `status`, `version`, `is_deleted`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES (1, '春季大甩卖', '春季钜惠', '2023-05-17 22:02:27', '2023-05-17 22:02:30', 'https://zero-mall.oss-cn-shenzhen.aliyuncs.com/banner/banner1.jpg', 1, 1, 0, '', '', '2023-05-17 22:02:38', '2023-05-17 22:02:38');
INSERT INTO `spms_seckill` (`id`, `name`, `descs`, `start_time`, `end_time`, `img`, `status`, `version`, `is_deleted`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES (2, '夏季大甩卖', '夏季钜惠', '2023-05-17 22:03:24', '2023-05-17 22:03:26', 'https://zero-mall.oss-cn-shenzhen.aliyuncs.com/banner/banner1.jpg', 1, 1, 0, '', '', '2023-05-17 22:03:32', '2023-05-17 22:03:32');
INSERT INTO `spms_seckill` (`id`, `name`, `descs`, `start_time`, `end_time`, `img`, `status`, `version`, `is_deleted`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES (3, '秋季大甩卖', '秋季钜惠', '2023-05-17 22:03:52', '2023-05-17 22:03:54', 'https://zero-mall.oss-cn-shenzhen.aliyuncs.com/banner/banner1.jpg', 1, 1, 0, '', '', '2023-05-17 22:04:03', '2023-05-17 22:04:03');
INSERT INTO `spms_seckill` (`id`, `name`, `descs`, `start_time`, `end_time`, `img`, `status`, `version`, `is_deleted`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES (4, '冬季大甩卖', '冬季钜惠', '2023-05-17 22:04:18', '2023-05-17 22:04:20', 'https://zero-mall.oss-cn-shenzhen.aliyuncs.com/banner/banner1.jpg', 1, 1, 0, '', '', '2023-05-17 22:04:26', '2023-05-17 22:04:26');

INSERT INTO `spms_recommend_product` (`id`, `spu_id`) VALUES (1, 1);
INSERT INTO `spms_recommend_product` (`id`, `spu_id`) VALUES (2, 2);
INSERT INTO `spms_recommend_product` (`id`, `spu_id`) VALUES (3, 3);
INSERT INTO `spms_recommend_product` (`id`, `spu_id`) VALUES (4, 4);
INSERT INTO `spms_recommend_product` (`id`, `spu_id`) VALUES (5, 5);
INSERT INTO `spms_recommend_product` (`id`, `spu_id`) VALUES (6, 6);
INSERT INTO `spms_recommend_product` (`id`, `spu_id`) VALUES (7, 7);

INSERT INTO `spms_product_column_spu` (`id`, `column_id`, `spu_id`) VALUES (1, 1, 1);
INSERT INTO `spms_product_column_spu` (`id`, `column_id`, `spu_id`) VALUES (2, 1, 2);
INSERT INTO `spms_product_column_spu` (`id`, `column_id`, `spu_id`) VALUES (3, 1, 3);
INSERT INTO `spms_product_column_spu` (`id`, `column_id`, `spu_id`) VALUES (4, 1, 4);
INSERT INTO `spms_product_column_spu` (`id`, `column_id`, `spu_id`) VALUES (5, 1, 5);
INSERT INTO `spms_product_column_spu` (`id`, `column_id`, `spu_id`) VALUES (6, 1, 6);
INSERT INTO `spms_product_column_spu` (`id`, `column_id`, `spu_id`) VALUES (7, 1, 7);

INSERT INTO `spms_product_column` (`id`, `name`, `descs`, `enter_img`, `banner_img`, `status`, `click_count`, `sort`, `version`, `is_deleted`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES (1, '数码专栏', '电子产品推荐', 'http://shopzz.oss-cn-guangzhou.aliyuncs.com/2022/03/4.png\', \'https://zero-mall.oss-cn-shenzhen.aliyuncs.com/banner/banner1.jpg', 'http://shopzz.oss-cn-guangzhou.aliyuncs.com/2022/03/4.png\', \'https://zero-mall.oss-cn-shenzhen.aliyuncs.com/banner/banner1.jpg', 1, 0, 0, 1, 0, '', '', '2023-05-17 22:20:23', '2023-05-17 22:20:23');

INSERT INTO `spms_coupon_category` (`id`, `coupon_id`, `category_id`, `parent_category_id`) VALUES (1, 4, 2, 0);

INSERT INTO `spms_coupon` (`id`, `name`, `sub_name`, `start_time`, `end_time`, `use_threshold`, `discount_amount`, `discount_rate`, `type`, `full_limited`, `status`, `version`, `is_deleted`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES (1, '满100减90', '100-90', '2022-05-17 22:14:05', '2022-04-01 13:58:23', 100.00, 90.00, 0.00, 1, 1, 1, 1, 0, '', '', '2023-05-17 22:14:32', '2023-05-17 22:14:32');
INSERT INTO `spms_coupon` (`id`, `name`, `sub_name`, `start_time`, `end_time`, `use_threshold`, `discount_amount`, `discount_rate`, `type`, `full_limited`, `status`, `version`, `is_deleted`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES (2, '无门槛10元券', '-10', '2022-05-17 22:14:05', '2022-04-01 13:58:23', 0.00, 10.00, 0.00, 3, 1, 1, 1, 0, '', '', '2023-05-17 22:15:06', '2023-05-17 22:18:32');
INSERT INTO `spms_coupon` (`id`, `name`, `sub_name`, `start_time`, `end_time`, `use_threshold`, `discount_amount`, `discount_rate`, `type`, `full_limited`, `status`, `version`, `is_deleted`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES (3, '满1000元9折券', '-10%', '2023-05-17 22:15:23', '2023-05-17 22:15:24', 1000.00, 0.00, 0.10, 2, 1, 1, 1, 0, '', '', '2023-05-17 22:15:44', '2023-05-17 22:18:32');
INSERT INTO `spms_coupon` (`id`, `name`, `sub_name`, `start_time`, `end_time`, `use_threshold`, `discount_amount`, `discount_rate`, `type`, `full_limited`, `status`, `version`, `is_deleted`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES (4, '男装-无门槛立减10元券', '-10元', '2023-05-17 22:16:11', '2023-05-17 22:16:13', 0.00, 10.00, 0.00, 3, 1, 1, 1, 0, '', '', '2023-05-17 22:16:24', '2023-05-17 22:18:32');

INSERT INTO `spms_banner` (`id`, `name`, `type`, `biz_id`, `pic`, `status`, `click_count`, `url`, `sort`, `version`, `is_deleted`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES (1, 'one', 1, 1, 'https://zero-mall.oss-cn-shenzhen.aliyuncs.com/banner/banner1.jpg', 1, 0, '', 0, 1, 0, '', '', '2023-05-17 22:23:23', '2023-05-17 22:23:23');
INSERT INTO `spms_banner` (`id`, `name`, `type`, `biz_id`, `pic`, `status`, `click_count`, `url`, `sort`, `version`, `is_deleted`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES (2, 'two', 1, 1, 'https://zero-mall.oss-cn-shenzhen.aliyuncs.com/banner/banner3.jpg', 1, 0, '', 0, 1, 0, '', '', '2023-05-17 22:23:23', '2023-05-17 22:23:23');
INSERT INTO `spms_banner` (`id`, `name`, `type`, `biz_id`, `pic`, `status`, `click_count`, `url`, `sort`, `version`, `is_deleted`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES (3, 'three', 1, 1, 'https://zero-mall.oss-cn-shenzhen.aliyuncs.com/banner/banner4.jpg', 1, 0, '', 0, 1, 0, '', '', '2023-05-17 22:23:23', '2023-05-17 22:23:23');

INSERT INTO `spms_activity_coupon` (`id`, `activity_id`, `coupon_id`) VALUES (1, 1, 1);
INSERT INTO `spms_activity_coupon` (`id`, `activity_id`, `coupon_id`) VALUES (2, 1, 2);
INSERT INTO `spms_activity_coupon` (`id`, `activity_id`, `coupon_id`) VALUES (3, 1, 3);

INSERT INTO `spms_activity_category` (`id`, `activity_id`, `category_id`) VALUES (1, 1, 1);
INSERT INTO `spms_activity_category` (`id`, `activity_id`, `category_id`) VALUES (2, 1, 2);
