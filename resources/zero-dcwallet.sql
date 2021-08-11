DROP TABLE IF EXISTS `dc_currency`;
CREATE TABLE `dc_currency` (
    `id`                            int(10) NOT NULL AUTO_INCREMENT COMMENT '币种ID',
    `currency_name`                 varchar(32) NOT NULL COMMENT '货币名称',
    `currency_logo`                 varchar(255) NOT NULL COMMENT '货币logo',
    `currency_type`                 varchar(10) NOT NULL DEFAULT 'mainnet' COMMENT '货币类型： mainnet：主网币 token：代币',
    `currency_content`              text NOT NULL COMMENT '币种描述',
    `currency_decimals_num`         int(3) DEFAULT 18 COMMENT '币种小数位',
    `currency_url`                  varchar(128) NOT NULL COMMENT '该币种的链接地址',
    `contract_abi`                  varchar(2000) COMMENT '智能合约abi接口',
    `contract_address`              varchar(128) NOT NULL DEFAULT '' COMMENT '智能合约地址',
    `cold_address`                  varchar(128) NOT NULL DEFAULT '' COMMENT '冷钱包地址',
    `cold_threshold`                decimal(36,18) NOT NULL DEFAULT '0' COMMENT '转冷钱包阈值',
    `fee_withdraw`                  decimal(36,18) NOT NULL DEFAULT '0' COMMENT '提币手续费',
    `wallet_key`                    varchar(64) NOT NULL DEFAULT '' COMMENT '钱包密钥',
    `confirms`                      tinyint(2) NOT NULL DEFAULT '1' COMMENT '充值确认数',
    `status`                        tinyint(1) NOT NULL DEFAULT '0' COMMENT '币种状态，0：关闭 1：开启',
    `version`                       bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                    tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                    varchar(50) NOT NULL COMMENT '创建者',
    `updated_by`                    varchar(50) NOT NULL COMMENT '更新者',
    `created_at`                    datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                    datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='加密货币表';


DROP TABLE IF EXISTS `dc_recharge`;
CREATE TABLE `dc_recharge` (
    `id`                            bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `order_id`                      varchar(255) NOT NULL COMMENT '订单ID，与对接业务的主键ID进行关联',
    `currency_id`                   int(10) NOT NULL COMMENT '币种ID',
    `currency_name`                 varchar(32) NOT NULL COMMENT '货币名称',
    `tx_hash`                       varchar(255) NOT NULL COMMENT '交易hash',
    `amount`                        decimal(40,18) NOT NULL COMMENT '充值的金额',
    `from_address`                  varchar(255) DEFAULT NULL COMMENT '用户的出币地址',
    `to_address`                    varchar(255) DEFAULT NULL COMMENT '关联的充值地址',
    `upchain_at`                    datetime COMMENT '上链时间',
    `upchain_success_at`            datetime COMMENT '上链成功时间',
    `upchain_status`                tinyint(1) NOT NULL DEFAULT '2' COMMENT '上链状态，1：上链并确认成功 2：等待确认中 3：未上链',
    `current_confirm`               int(20) DEFAULT NULL COMMENT '当前交易确认数',
    `height`                        int(20) DEFAULT NULL COMMENT '当前交易所处区块的高度',
    `version`                       bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                    tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                    varchar(50) NOT NULL COMMENT '创建者',
    `updated_by`                    varchar(50) NOT NULL COMMENT '更新者',
    `created_at`                    datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                    datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='充值关联表';



DROP TABLE IF EXISTS `dc_cold_record`;
CREATE TABLE `dc_cold_record` (
    `id`                            bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `currency_id`                   int(10) NOT NULL COMMENT '币种ID',
    `currency_name`                 varchar(32) NOT NULL COMMENT '货币名称',
    `amount`                        decimal(40,18) NOT NULL COMMENT '转冷金额',
    `tx_hash`                       varchar(255) NOT NULL COMMENT '转冷钱包交易hash',
    `from_address`                  varchar(255) DEFAULT NULL COMMENT '业务系统中交易或其他绑定的地址',
    `to_address`                    varchar(255) DEFAULT NULL COMMENT '冷钱包地址',
    `upchain_at`                    datetime COMMENT '上链时间',
    `upchain_success_at`            datetime COMMENT '上链成功时间',
    `upchain_status`                tinyint(1) NOT NULL DEFAULT '2' COMMENT '上链状态，1：成功 2：失败 3：上链后等待确认中',
    `version`                       bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                    tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                    varchar(50) NOT NULL COMMENT '创建者',
    `updated_by`                    varchar(50) NOT NULL COMMENT '更新者',
    `created_at`                    datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                    datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='转冷钱包记录';



DROP TABLE IF EXISTS `dc_height`;
CREATE TABLE `dc_height` (
    `currency_id`                   int(10) NOT NULL COMMENT '币种ID',
    `currency_name`                 varchar(32) NOT NULL COMMENT '货币名称',
    `current_height`                bigint(20) NOT NULL COMMENT '当前服务扫描区块高度',
    `version`                       bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                    tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                    varchar(50) NOT NULL COMMENT '创建者',
    `updated_by`                    varchar(50) NOT NULL COMMENT '更新者',
    `created_at`                    datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                    datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`currency_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='区块高度同步记录';


DROP TABLE IF EXISTS `dc_currency_account`;
CREATE TABLE `dc_currency_account` (
    `id`                            bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `currency_id`                   int(10) NOT NULL COMMENT '币种ID',
    `currency_name`                 varchar(32) NOT NULL COMMENT '货币名称',
    `keystore_name`                 varchar(256) DEFAULT NULL COMMENT 'keystore文件名',
    `mnemonic`                      varchar(256) DEFAULT NULL COMMENT '助记词',
    `address`                       varchar(256) NOT NULL COMMENT '地址',
    `version`                       bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `is_deleted`                    tinyint(3) DEFAULT 0 COMMENT '逻辑删除 1: 已删除， 0: 未删除',
    `created_by`                    varchar(50) NOT NULL COMMENT '创建者',
    `updated_by`                    varchar(50) NOT NULL COMMENT '更新者',
    `created_at`                    datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                    datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账号管理表';