-- liquibase formatted sql

-- changeset whoiszxl:1
-- comment 初始化im表数据

INSERT INTO `im_friend` VALUES (1, 2, 'remark备注1', '通讯录', NULL, NULL, 1, 1, 0, '2023-08-21 17:24:49', '2023-08-21 17:24:49');
INSERT INTO `im_friend` VALUES (2, 1, 'remark备注2', '直接添加', NULL, NULL, 1, 1, 0, '2023-08-21 17:24:55', '2023-08-21 17:24:55');