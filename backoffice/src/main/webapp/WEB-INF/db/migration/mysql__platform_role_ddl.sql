CREATE TABLE `platform_role` (

  `platform_role_id` BIGINT(19)   NOT NULL,
  `name`             VARCHAR(100) NOT NULL,
  `description`      VARCHAR(255),
  `version`          INT(11)      NOT NULL DEFAULT 1,

  KEY (`version`),
  KEY (`description`),
  UNIQUE KEY (`name`),
  PRIMARY KEY (`platform_role_id`)
);