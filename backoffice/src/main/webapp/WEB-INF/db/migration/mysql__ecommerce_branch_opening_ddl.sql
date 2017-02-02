CREATE TABLE `ecommerce_branch_opening` (

  `ecommerce_branch_opening_id` BIGINT(19) NOT NULL,
  `ecommerce_branch_id`         BIGINT(19) NOT NULL,
  `day`                         VARCHAR(255),
  `opening`                     VARCHAR(255),

  KEY (`ecommerce_branch_id`),
  KEY (`day`),
  KEY (`opening`),
  PRIMARY KEY (`ecommerce_branch_opening_id`)
);