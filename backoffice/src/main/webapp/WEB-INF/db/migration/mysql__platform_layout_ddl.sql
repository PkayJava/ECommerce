CREATE TABLE `platform_layout` (

  `platform_layout_id` BIGINT(19)   NOT NULL,
  `name`               VARCHAR(100) NOT NULL,
  `description`        VARCHAR(255) NOT NULL,
  `java_class`         VARCHAR(255),

  KEY (`description`),
  UNIQUE KEY (`name`),
  UNIQUE KEY (`java_class`),
  PRIMARY KEY (`platform_layout_id`)
);