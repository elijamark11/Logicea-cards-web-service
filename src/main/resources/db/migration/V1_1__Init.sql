DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions`
(
    `id`          bigint      NOT NULL AUTO_INCREMENT,
    `description` varchar(255) DEFAULT NULL,
    `name`        varchar(64) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 16
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

LOCK
TABLES `permissions` WRITE;
INSERT INTO `permissions`
VALUES (1, 'Permission to create user', 'can_view_card'),
       (2, 'Permission to edit user', 'can_create_card'),
       (3, 'Permission to view user', 'can_update_card'),
       (4, 'Permission to delete user', 'can_delete_card');
UNLOCK
TABLES;

DROP TABLE IF EXISTS `user_types`;
CREATE TABLE `user_types`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `name`        varchar(100) DEFAULT NULL,
    `description` varchar(250) DEFAULT NULL,
    `updated_by`  bigint       DEFAULT NULL,
    `created_by`  bigint       DEFAULT NULL,
    `created_on`  datetime     DEFAULT NULL,
    `updated_on`  datetime     DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

LOCK
TABLES `user_types` WRITE;
INSERT INTO `user_types`
VALUES (1, 'Admin', 'Super Admin', 1, 1, '2023-08-24 16:37:50', '2023-08-24 16:37:50'),
       (2, 'Member', 'Just a member', 1, 1, '2023-08-24 16:37:50', '2023-08-24 16:37:50');
UNLOCK
TABLES;

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`
(
    `id`         bigint NOT NULL AUTO_INCREMENT,
    `name`       varchar(100) DEFAULT NULL,
    `phone`      varchar(20)  DEFAULT NULL,
    `email`      varchar(250) DEFAULT NULL,
    `user_type`  bigint       DEFAULT NULL,
    `enabled`    smallint     DEFAULT NULL,
    `password`   varchar(100) DEFAULT NULL,
    `updated_by` bigint       DEFAULT NULL,
    `created_by` bigint       DEFAULT NULL,
    `created_on` datetime     DEFAULT NULL,
    `updated_on` datetime     DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY          `FKibk1e3kaxy5sfyeekp8hbhnim` (`created_by`),
    KEY          `FKi7ssavr9oe6crtjyyshv79dyh` (`updated_by`),
    KEY          `FKas1xh9ffcl617omqb321ivj67` (`user_type`),
    CONSTRAINT `FKas1xh9ffcl617omqb321ivj67` FOREIGN KEY (`user_type`) REFERENCES `user_types` (`id`),
    CONSTRAINT `FKi7ssavr9oe6crtjyyshv79dyh` FOREIGN KEY (`updated_by`) REFERENCES `users` (`id`),
    CONSTRAINT `FKibk1e3kaxy5sfyeekp8hbhnim` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

INSERT INTO `users`
VALUES (1, 'Elijah Mulwa', '+254701000000', 'admin@live.com', 1, 1,
        '$2a$10$4bt.TKw8oXaPiTRiwseuLuBMh9mTWgKzCpWgsP1YliaOfrUjs8HRS', 1, 1, '2023-08-24 16:37:50',
        '2023-08-24 16:37:50'),
       (2, 'Joe Doe', '+254700000000', 'joe@live.com', 2, 1,
        '$2a$10$4bt.TKw8oXaPiTRiwseuLuBMh9mTWgKzCpWgsP1YliaOfrUjs8HRS', 1, 1, '2023-08-24 16:37:50',
        '2023-08-24 16:37:50');