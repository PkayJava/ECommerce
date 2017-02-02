CREATE TABLE `platform_section` (

  `platform_section_id` BIGINT(19)   NOT NULL,
  `title`               VARCHAR(100) NOT NULL,
  `order`               INT(11)      NOT NULL DEFAULT 0,
  `version`             INT(11)      NOT NULL DEFAULT 1,

  KEY (`version`),
  KEY (`order`),
  UNIQUE KEY (`title`),
  PRIMARY KEY (`platform_section_id`)
);