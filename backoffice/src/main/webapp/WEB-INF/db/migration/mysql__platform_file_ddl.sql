CREATE TABLE `platform_file` (

  `platform_file_id` BIGINT(19)   NOT NULL,
  `name`             VARCHAR(255),
  `label`            VARCHAR(255),
  `path`             VARCHAR(255) NOT NULL,
  `mime`             VARCHAR(100),
  `extension`        VARCHAR(10),
  `length`           INT(11)      NOT NULL,
  `system`           BIT(1)       NOT NULL DEFAULT 0,
  `version`          INT(11)      NOT NULL DEFAULT 1,

  KEY (`system`),
  KEY (`name`),
  KEY (`label`),
  KEY (`path`),
  KEY (`mime`),
  KEY (`extension`),
  KEY (`length`),
  KEY (`version`),
  PRIMARY KEY (`platform_file_id`)
);
