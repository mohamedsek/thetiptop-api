CREATE DATABASE game_contest CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;


CREATE USER 'f2iuser'@'%' IDENTIFIED BY 'f2ipassword';

FLUSH PRIVILEGES;

GRANT ALL PRIVILEGES ON game_contest.* TO 'f2iuser'@'%';

<!-- GAIN DATA -->

INSERT INTO `game_contest`.`gain` (`id`,`chance`, `title`) VALUES (1, '60', 'un infuseur à thé');
INSERT INTO `game_contest`.`gain` (`id`,`chance`, `title`) VALUES (2, '20', 'une boite de 100g d’un thé détox ou d’infusion');
INSERT INTO `game_contest`.`gain` (`id`,`chance`, `title`) VALUES (3, '10', 'une boite de 100g d’un thé signature');
INSERT INTO `game_contest`.`gain` (`id`,`chance`, `title`) VALUES (4, '6', 'un coffret découverte d’une valeur de 39€');
INSERT INTO `game_contest`.`gain` (`id`,`chance`, `title`) VALUES (5, '4', 'un coffret découverte d’une valeur de 69€');
INSERT INTO `game_contest`.`gain` (`id`,`chance`, `title`) VALUES (6, '0', 'un an de thé d’une valeur de 360€');

<!-- USER ROLES -->
-- roles will be created using InitDataRunner
--INSERT INTO `game_contest`.`user_role` (`id`, `name`) VALUES ('4', 'ROLE_CUSTOMER');
--INSERT INTO `game_contest`.`user_role` (`id`, `name`) VALUES ('2', 'ADMIN');
--INSERT INTO `game_contest`.`user_role` (`id`, `name`) VALUES ('3', 'CHECKOUTMACHINE');