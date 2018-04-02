USE virtual_vipcard;
SET NAMES utf8mb4;

/* **会员卡系统用户账号表** */
CREATE TABLE `tbl_user_account`(
  `id` INT(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `user_id` INT(8) NOT NULL DEFAULT 00000000 COMMENT '用户Id',
  `email_code` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '用户邮箱',
  `tecent_code` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '用户快速登录Id',
  `telphone_code` VARCHAR(32) NOt NULL DEFAULT '' COMMENT '用户手机号',
  `password` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '用户账号密码',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id`(`user_id`),
  UNIQUE KEY `email_code`(`email_code`),
  UNIQUE KEY `wechat_code`(`wechat_code`),
  UNIQUE KEY `telphone_code`(`telphone_code`)
)ENGINE=InnoDB CHARSET=utf8mb4 COMMENT='会员卡系统用户账号表';

/* **会员卡系统用户信息表** */
CREATE TABLE `tbl_user_info`(
  `id` INT(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `user_id` INT(8) NOT NULL DEFAULT 00000000 COMMENT '用户Id',
  `user_name` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '用户昵称',
  `user_sex` CHAR(1) DEFAULT 'F' COMMENT '用户性别',
  `user_age` INT(3) NOT NULL DEFAULT 0 COMMENT '用户年龄',
  `user_birth` DATETIME NOT NULL DEFAULT '1971-01-01 00:00:00' COMMENT '用户生日',
  `user_area` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '用户所处地区',
  `user_signature` VARCHAR(256) NOT NULL DEFAULT '' COMMENT '用户个性签名',
  `user_photo_path` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '用户头像路径',
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_userInfo` FOREIGN KEY (`user_id`) REFERENCES `tbl_user_account`(`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CHECK (`user_info` IN ('F', 'M')),
  INDEX `user_name`(`user_name`)
)ENGINE=InnoDB CHARSET=utf8mb4 COMMENT='会员卡系统用户信息表';
