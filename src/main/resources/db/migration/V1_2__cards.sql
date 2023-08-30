DROP TABLE IF EXISTS `cards`;
CREATE TABLE `cards`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `name`        varchar(100) DEFAULT NULL,
    `description` varchar(250) DEFAULT NULL,
    `color`       varchar(7) DEFAULT NULL,
    `status`      varchar(16)  DEFAULT NULL,
    `updated_by`  bigint       DEFAULT NULL,
    `created_by`  bigint       DEFAULT NULL,
    `created_on`  datetime     DEFAULT NULL,
    `updated_on`  datetime     DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY           `FKfjT90dj02fkfueekp8hf0nim` (`created_by`),
    KEY           `FKi790efjjfLop90w2wq6rdyh` (`updated_by`),
    CONSTRAINT `FKi7sjuioaoau3490q099w7djfh` FOREIGN KEY (`updated_by`) REFERENCES `users` (`id`),
    CONSTRAINT `FKibkuiidodnskkk239didjfiok` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;