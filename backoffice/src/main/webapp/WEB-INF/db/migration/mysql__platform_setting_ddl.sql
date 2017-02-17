CREATE TABLE `platform_setting` (

  `platform_setting_id` BIGINT(19)   NOT NULL,
  `key`                 VARCHAR(100) NOT NULL,
  `description`         VARCHAR(255),
  `value`               VARCHAR(255),
  `version`             INT(11)      NOT NULL DEFAULT 1,

  KEY (`version`),
  KEY (`description`),
  KEY (`value`),
  UNIQUE KEY (`key`),
  PRIMARY KEY (`platform_setting_id`)
);