CREATE TABLE `platform_rest` (

  `platform_rest_id` BIGINT(19)   NOT NULL,
  `name`             VARCHAR(255) NOT NULL,
  `description`      VARCHAR(255),
  `java_class`       VARCHAR(255) NOT NULL,
  `version`          INT(11)      NOT NULL DEFAULT 1,

  KEY (`version`),
  KEY (`description`),
  KEY (`name`),
  UNIQUE KEY (`java_class`),
  PRIMARY KEY (`platform_rest_id`)
);