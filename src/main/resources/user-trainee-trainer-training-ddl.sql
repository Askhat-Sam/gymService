USE `gym-service`;
DROP TABLE IF EXISTS `trainee_trainer`;
DROP TABLE IF EXISTS `training`;
DROP TABLE IF EXISTS `trainee`;
DROP TABLE IF EXISTS `trainer`;
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `training_type`;

SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE `user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50)  NOT NULL,
  `last_name` varchar(50)  NOT NULL,
  `user_name` varchar(50)  NOT NULL,
  `password` varchar(50)  NOT NULL,
  `is_active` boolean  NOT NULL,
   PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO user
VALUES
(1,'Ivan','Ivanov', 'Ivan.Ivanov', '12hU76gt5(', 1),
(2,'Denis','Denisov', 'Denis.Denisov', '32hU46gt5(', 1),
(3,'Andrey','Andreyev', 'Andrey.Andreyev', 'j7hU76gt58', 1),
(4,'Vladislav','Bekmeev', 'Vladislav.Bekmeev', '128646gt5(', 1),
(5,'Sergey','Sergeyev', 'Sergey.Sergeyev', '3(9U46gt5(', 1),
(6,'Maksim','Maksimov', 'Maksim.Maksimov', 'j7hU76gt58', 1);

SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE `trainee` (
  `date_of_birth` date  DEFAULT NULL,
  `address` varchar(50)  DEFAULT NULL,
  `user_id` BIGINT  NOT NULL,
   PRIMARY KEY (id),
KEY FK_TRAINEE_USER_ID_idx (`user_id`),
CONSTRAINT FK_TRAINEE_USER_ID_idx
FOREIGN KEY (`user_id`)
REFERENCES user (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO `trainee`
VALUES
(1, '1990-02-01', 'Furmanova 1', 1),
(2, '1989-03-04', 'Mailina 65', 2),
(3, '1991-05-09', 'Akhmetova 3',3);

CREATE TABLE `training_type` (
`id` BIGINT NOT NULL AUTO_INCREMENT,
`training_type_name` VARCHAR(50) NOT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO `training_type`
VALUES
(1, 'Swimming'),
(2, 'Yoga'),
(3, 'Cardio');

CREATE TABLE `trainer` (
`specialization` BIGINT NOT NULL,
`user_id` BIGINT NOT NULL,
PRIMARY KEY (`id`),
KEY `FK_TRAINER_USER_ID_idx` (`user_id`),
  CONSTRAINT `FK_TRAINER_USER_ID_idx`
  FOREIGN KEY (`user_id`)
  REFERENCES `user` (`id`),
KEY `FK_TRAINING_TYPE_idx` (`specialization`),
  CONSTRAINT `FK_TRAINING_TYPE_idx`
  FOREIGN KEY (`specialization`)
  REFERENCES `training_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO `trainer`
VALUES
(4, 1, 4),
(5, 2, 5),
(6, 3, 6);

CREATE TABLE `trainee_trainer` (
`trainee_id` BIGINT NOT NULL,
`trainer_id` BIGINT NOT NULL,
PRIMARY KEY (`trainee_id`, `trainer_id`),
KEY `FK_TRAINEE_TRAINER_idx` (`trainee_id`),
  CONSTRAINT `FK_TRAINEE_TRAINER_idx`
  FOREIGN KEY (`trainee_id`)
  REFERENCES `trainee` (`user_id`),
  CONSTRAINT `FK_TRAINEr_TRAINEE_idx`
  FOREIGN KEY (`trainer_id`)
  REFERENCES `trainer` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


INSERT INTO trainee_trainer (trainee_id, trainer_id) VALUES (1, 4), (2,4);
INSERT INTO trainee_trainer (trainee_id, trainer_id) VALUES (3, 4), (2, 5);
INSERT INTO trainee_trainer (trainee_id, trainer_id) VALUES (3, 6), (2,6);

CREATE TABLE `training` (
`id` BIGINT NOT NULL AUTO_INCREMENT,
`trainee_id` BIGINT NOT NULL,
`trainer_id` BIGINT NOT NULL,
`training_name` VARCHAR(50) NOT NULL,
`training_type_id` BIGINT NOT NULL,
`training_date` DATE NOT NULL,
`training_duration` BIGINT NOT NULL,
PRIMARY KEY (`id`),
  CONSTRAINT `FK_TRAINEE_ID_idx`
  FOREIGN KEY (`trainee_id`)
  REFERENCES `trainee` (`id`) ON DELETE CASCADE,

  CONSTRAINT `FK_TRAINER_ID_idx`
  FOREIGN KEY (`trainer_id`)
  REFERENCES `trainer` (`id`) ON DELETE CASCADE,

  CONSTRAINT `FK_TRAINING_TYPE_ID_idx`
  FOREIGN KEY (`training_type_id`)
  REFERENCES `training_type` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO `training`
VALUES
(1, 1, 4, 'Swimming', 1, '2025-02-26', 55),
(2, 2, 4, 'Swimming', 2, '2025-01-03', 40),
(3, 3, 4, 'Swimming', 3, '2025-02-01', 45),
(4, 2, 5, 'Yoga', 1, '2025-02-26', 55),
(5, 3, 6, 'Yoga', 2, '2025-01-03', 40),
(6, 2, 6, 'Cardio', 3, '2025-02-01', 45);

