CREATE TABLE `platform_menu_item` (

  `platform_menu_item_id` BIGINT(19)   NOT NULL,
  `platform_menu_id`      BIGINT(19),
  `title`                 VARCHAR(100) NOT NULL,
  `icon`                  VARCHAR(100),
  `order`                 INT(11)      NOT NULL DEFAULT 0,
  `platform_page_id`      BIGINT(19)   NOT NULL,
  `platform_section_id`   BIGINT(19),
  `version`               INT(11)      NOT NULL DEFAULT 1,

  KEY (`title`),
  KEY (`order`),
  KEY (`icon`),
  KEY (`platform_menu_id`),
  KEY (`platform_section_id`),
  KEY (`version`),
  UNIQUE KEY (`platform_page_id`),
  PRIMARY KEY (`platform_menu_item_id`)
);