CREATE TABLE `ecommerce_branch_transport` (

  `ecommerce_branch_transport_id` BIGINT(19) NOT NULL,
  `ecommerce_branch_id`           BIGINT(19) NOT NULL,
  `icon_platform_file_id`         BIGINT(19),
  `text`                          VARCHAR(1000),

  KEY (`ecommerce_branch_id`),
  KEY (`icon_platform_file_id`),
  KEY (`text`),
  PRIMARY KEY (`ecommerce_branch_transport_id`)
);