CREATE TABLE `platform_localization` (

  `platform_localization_id` BIGINT(19)   NOT NULL,
  `key`             VARCHAR(100) NOT NULL,
  `language`        VARCHAR(10)  NOT NULL,
  `label`           VARCHAR(255) NOT NULL,
  `version`         INT(11)      NOT NULL DEFAULT 1,

  KEY (`version`),
  KEY (`key`),
  KEY (`language`),
  KEY (`label`),
  UNIQUE KEY (`key`, `language`),
  PRIMARY KEY (`platform_localization_id`)
);