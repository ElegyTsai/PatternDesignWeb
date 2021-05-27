CREATE TABLE IF NOT EXISTS `user`(
    `id` INT(20) NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(255) DEFAULT NULL,
    `email` VARCHAR(255) DEFAULT NULL,
    `password` VARCHAR(255) DEFAULT NULL,
    `mobile` VARCHAR(255) DEFAULT NULL ,
    `enabled` INT(1) DEFAULT NUll ,
    `createTime` VARCHAR(255) DEFAULT NULL ,
    `lastModify` VARCHAR (255) DEFAULT NULL ,
    PRIMARY KEY (`id`)
)ENGINE = InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `role`(
    `id` INT(20) NOT NULL AUTO_INCREMENT,
    `description` VARCHAR(255) DEFAULT NULL,
    `rolename` VARCHAR(30) DEFAULT NUll ,
    `available` INT(1) DEFAULT NULL,
    PRIMARY KEY (`id`)
    )ENGINE = InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `permission`(
    `id` INT(20) NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(10) DEFAULT NUll ,
    `resourceType` VARCHAR(10) DEFAULT NULL,
    `url` VARCHAR(255) DEFAULT NULL,
    `available` INT(1) DEFAULT NULL,
    `permission` VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
    )ENGINE = InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `sysuser`(
    `id` INT(20) NOT NULL AUTO_INCREMENT,
    `enabled` TINYINT(1) DEFAULT NUll ,
    `user_id` INT(20) DEFAULT NULL,
    `role_id` INT(20) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `user_id` (`user_id`),
    CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    KEY `role_id` (`role_id`),
    CONSTRAINT `role_id` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
)ENGINE = InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `sysrole`(
    `id` INT(20) NOT NULL AUTO_INCREMENT,
    `roleper_id` INT(20) DEFAULT NULL,
    `permission_id` INT(20) DEFAULT NULL,
    KEY `roleper_id` (`roleper_id`),
    CONSTRAINT `roleper_id` FOREIGN KEY (`roleper_id`) REFERENCES `role` (`id`),
    KEY `permission_id` (`permission_id`),
    CONSTRAINT `permission_id` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`),
    PRIMARY KEY (`id`)
)ENGINE = InnoDB DEFAULT CHARSET=utf8;

