CREATE TABLE IF NOT EXISTS `user`(
    `id` INT(64) NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(255) DEFAULT NULL,
    `email` VARCHAR(255) DEFAULT NULL,
    `password` VARCHAR(255) DEFAULT NULL,
    `mobile` VARCHAR(255) DEFAULT NULL ,
    `enabled` INT(1) DEFAULT NUll ,
    `createTime` VARCHAR(80) DEFAULT NULL ,
    `lastModify` VARCHAR (80) DEFAULT NULL ,
    `outDate` VARCHAR(80) DEFAULT NULL,
    `active` INT(1) DEFAULT NULL,
    `validationCode` VARCHAR(255) DEFAULT NUll,
    PRIMARY KEY (`id`)
)ENGINE = InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `role`(
    `id` INT(64) NOT NULL AUTO_INCREMENT,
    `description` VARCHAR(255) DEFAULT NULL,
    `rolename` VARCHAR(30) DEFAULT NUll ,
    `available` INT(1) DEFAULT NULL,
    PRIMARY KEY (`id`)
    )ENGINE = InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `permission`(
    `id` INT(64) NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(10) DEFAULT NUll ,
    `resourceType` VARCHAR(10) DEFAULT NULL,
    `url` VARCHAR(255) DEFAULT NULL,
    `available` INT(1) DEFAULT NULL,
    `permission` VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
    )ENGINE = InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `sysuser`(
    `id` INT(64) NOT NULL AUTO_INCREMENT,
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
    `id` INT(64) NOT NULL AUTO_INCREMENT,
    `roleper_id` INT(20) DEFAULT NULL,
    `permission_id` INT(20) DEFAULT NULL,
    KEY `roleper_id` (`roleper_id`),
    CONSTRAINT `roleper_id` FOREIGN KEY (`roleper_id`) REFERENCES `role` (`id`),
    KEY `permission_id` (`permission_id`),
    CONSTRAINT `permission_id` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`),
    PRIMARY KEY (`id`)
)ENGINE = InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `loginLog`(
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `loginTime` VARCHAR(80) DEFAULT NULL,
    `loginip` VARCHAR(40) DEFAULT NULL,
    `username` VARCHAR(40) DEFAULT NULL,
    `states` INT(10) DEFAULT NULL,
    `way` INT(5) DEFAULT NULL,
    PRIMARY KEY (`id`)
)ENGINE = InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `sysImage`(
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `thumbnailPath` VARCHAR(80) DEFAULT NULL,
    `imagePath` VARCHAR(80) DEFAULT NULL,
    `imageName` VARCHAR(80) DEFAULT NULL,
    `MD5` VARCHAR(40) DEFAULT NULL,
    `available` INT(1) DEFAULT NULL,
    `permission` VARCHAR(80) DEFAULT NULL,
    `tag` VARCHAR(80) DEFAULT NULL,
    PRIMARY KEY (`id`)
)ENGINE = InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `userImage`(
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `thumbnailPath` VARCHAR(80) DEFAULT NULL,
    `imagePath` VARCHAR(80) DEFAULT NULL,
    `imageName` VARCHAR(80) DEFAULT NULL,
    `MD5` VARCHAR(40) DEFAULT NULL,
    `available` INT(1) DEFAULT NULL,
    PRIMARY KEY (`id`)
)ENGINE = InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `relationOfUserImage`(
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `UUID` VARCHAR(80) DEFAULT NULL,
    `myGroup` VARCHAR(80) DEFAULT NULL,
    `user_id` INT DEFAULT NULL,
    `image_id` BIGINT DEFAULT NULL,
    KEY `userIm_id` (`user_id`),
    CONSTRAINT `userIm_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    KEY `image_id` (`image_id`),
    CONSTRAINT `image_id` FOREIGN KEY (`image_id`) REFERENCES `userImage` (`id`),
    PRIMARY KEY (`id`)
)ENGINE = InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `latestUsedMaterialLog`(
    `logId` BIGINT NOT NULL AUTO_INCREMENT,
    `timeOfLastUsing` VARCHAR(50) DEFAULT NULL,
    `user_id` INT DEFAULT NULL,
    `materialUrl` VARCHAR(250) DEFAULT NULL,
    PRIMARY KEY (`logId`)
)ENGINE = InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `template`(
    `id` INT NOT NULL AUTO_INCREMENT,
    `thumbnailUrl` VARCHAR(100) DEFAULT NULL,
    `url` VARCHAR(100) DEFAULT NULL,
    `tag` VARCHAR(50) DEFAULT NULL,
    PRIMARY KEY (`id`)
)ENGINE = InnoDB DEFAULT CHARSET=utf8;
