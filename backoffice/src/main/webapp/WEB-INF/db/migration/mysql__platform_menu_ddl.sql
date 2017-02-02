CREATE TABLE `platform_menu` (

  `platform_menu_id`        BIGINT(19)   NOT NULL,
  `title`                   VARCHAR(100) NOT NULL,
  `path`                    VARCHAR(255) NOT NULL,
  `icon`                    VARCHAR(100),
  `platform_section_id`     BIGINT(19),
  `parent_platform_menu_id` BIGINT(19),
  `order`                   INT(11)      NOT NULL DEFAULT 0,
  `version`                 INT(11)      NOT NULL DEFAULT 1,

  KEY (`title`),
  KEY (`path`),
  KEY (`icon`),
  KEY (`order`),
  KEY (`platform_section_id`),
  KEY (`parent_platform_menu_id`),
  PRIMARY KEY (`platform_menu_id`)
);