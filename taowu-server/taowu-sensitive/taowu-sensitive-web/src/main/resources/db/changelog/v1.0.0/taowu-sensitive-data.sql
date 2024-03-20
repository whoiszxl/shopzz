-- liquibase formatted sql

-- changeset whoiszxl:1
-- comment 初始化 sensitive 表数据

INSERT INTO `common_sensitive_word` (`id`, `keyword`, `keyword_type`, `status`, `version`, `is_deleted`, `created_at`, `updated_at`) VALUES (1, '床前明月光', 2, 0, 1, 0, NOW(), NOW());
INSERT INTO `common_sensitive_word` (`id`, `keyword`, `keyword_type`, `status`, `version`, `is_deleted`, `created_at`, `updated_at`) VALUES (2, '举头望明月', 2, 0, 1, 0, NOW(), NOW());
