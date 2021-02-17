DROP TABLE IF EXISTS `t_employee`;
CREATE TABLE `t_employee`
(
    `id`      bigint(11) NOT NULL AUTO_INCREMENT,
    `name`    varchar(50)  DEFAULT NULL,
    `age`     int(4)       DEFAULT NULL,
    `dept_id` bigint(11)   DEFAULT NULL,
    `email`   varchar(255) DEFAULT NULL,
    `created_time`   TIMESTAMP NULL,
    PRIMARY KEY (`id`),
    KEY `i_dept_id` (`dept_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

DROP TABLE IF EXISTS `t_dept`;
CREATE TABLE `t_dept`
(
    `dept_id`   bigint(11)  NOT NULL AUTO_INCREMENT,
    `dept_name` varchar(50) NOT NULL,
    PRIMARY KEY (`dept_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

INSERT INTO `test`.`t_dept`(`dept_id`, `dept_name`) VALUES (1259510564693286913, 'Sales');
INSERT INTO `test`.`t_dept`(`dept_id`, `dept_name`) VALUES (1259510569210552321, 'R&D');
INSERT INTO `test`.`t_dept`(`dept_id`, `dept_name`) VALUES (1259510569256689665, 'Market');
INSERT INTO `test`.`t_dept`(`dept_id`, `dept_name`) VALUES (1259510569575456770, 'QA');

INSERT INTO `test`.`t_employee`(`id`, `name`, `age`, `dept_id`, `email`, created_time) VALUES (1259514746502393858, 'Adele', 31, 1259510564693286913, 'adele@gmail.com', now());
INSERT INTO `test`.`t_employee`(`id`, `name`, `age`, `dept_id`, `email`, created_time) VALUES (1259514750507954177, 'Taylor', 42, 1259510564693286913, 'taylor@gmail.com', now());
INSERT INTO `test`.`t_employee`(`id`, `name`, `age`, `dept_id`, `email`, created_time) VALUES (1259514750533120001, 'John', 68, 1259510564693286913, 'john@gmail.com', now());
INSERT INTO `test`.`t_employee`(`id`, `name`, `age`, `dept_id`, `email`, created_time) VALUES (1259514750554091522, 'Paul', 54, 1259510564693286913, 'paul@gmail.com', now());
INSERT INTO `test`.`t_employee`(`id`, `name`, `age`, `dept_id`, `email`, created_time) VALUES (1259514750570868737, 'Bill', 65, 1259510569210552321, 'bill@hotmail.com', now());
INSERT INTO `test`.`t_employee`(`id`, `name`, `age`, `dept_id`, `email`, created_time) VALUES (1259514750596034562, 'Tim', 79, 1259510569256689665, 'tim@apple.com', now());
INSERT INTO `test`.`t_employee`(`id`, `name`, `age`, `dept_id`, `email`, created_time) VALUES (1259514750621200386, 'Page', 53, 1259510569210552321, 'page@gmail.com', now());
INSERT INTO `test`.`t_employee`(`id`, `name`, `age`, `dept_id`, `email`, created_time) VALUES (1259514750642171906, 'Jobs', 69, 1259510569575456770, 'jobs@apple.com', now());
INSERT INTO `test`.`t_employee`(`id`, `name`, `age`, `dept_id`, `email`, created_time) VALUES (1259514750663143425, 'Jeff', 57, 1259510569256689665, 'jeff@amazon.com', now());

