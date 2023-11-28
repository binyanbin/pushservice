CREATE TABLE `pl_message` (
                              `id` BIGINT(19) NOT NULL,
                              `session_id` VARCHAR(45) NOT NULL COLLATE 'utf8mb3_general_ci',
                              `content` VARCHAR(1000) NOT NULL DEFAULT '' COLLATE 'utf8mb3_general_ci',
                              `created_time` DATETIME NOT NULL,
                              `send_time` DATETIME NULL DEFAULT NULL,
                              `is_read` TINYINT(3) NULL DEFAULT '0',
                              `branch_id` BIGINT(19) NULL DEFAULT NULL,
                              PRIMARY KEY (`id`) USING BTREE,
                              INDEX `receiver_index` (`session_id`) USING BTREE
);
CREATE TABLE `pl_session` (
                              `id` BIGINT(19) NOT NULL COMMENT '标识',
                              `session_id` VARCHAR(128) NULL DEFAULT NULL COMMENT '会话id' COLLATE 'utf8mb4_general_ci',
                              `user_id` BIGINT(19) NOT NULL COMMENT '账户标识',
                              `client_type` INT(10) NOT NULL COMMENT '客户端类型',
                              `device_id` VARCHAR(128) NULL DEFAULT NULL COMMENT '设备id' COLLATE 'utf8mb4_general_ci',
                              `version_id` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '版本id' COLLATE 'utf8mb4_general_ci',
                              `secret_key` VARCHAR(128) NOT NULL COMMENT '密鑰' COLLATE 'utf8mb4_general_ci',
                              `branch_id` BIGINT(19) NULL DEFAULT NULL COMMENT '门店id',
                              `employee_id` BIGINT(19) NULL DEFAULT NULL COMMENT '员工id',
                              `revision` INT(10) NOT NULL COMMENT '乐观锁',
                              `created_time` DATETIME NOT NULL COMMENT '创建时间',
                              `updated_time` DATETIME NOT NULL COMMENT '更新时间',
                              PRIMARY KEY (`id`) USING BTREE
)