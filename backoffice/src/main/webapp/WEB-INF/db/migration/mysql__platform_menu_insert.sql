INSERT INTO `platform_menu` (`platform_menu_id`, `platform_section_id`, `platform_parent_menu_id`, `title`, `path`, `icon`, `order`, `version`)
VALUES
  (1, 1, NULL, 'Content', 'Admin Console > Content', 'fa-align-justify', 1, 1),
  (2, 1, NULL, 'Security', 'Admin Console > Security', 'fa-lock', 2, 1),
  (3, 2, NULL, 'Catalog', 'User Console > Catalog', '', 1, 1),
  (4, 2, NULL, 'Alert', 'User Console > Alert', '', 2, 1),
  (5, 2, NULL, 'Market Place', 'User Console > Market Place', '', 3, 1),
  (6, 2, NULL, 'Cart', 'User Console > Cart', '', 4, 1);