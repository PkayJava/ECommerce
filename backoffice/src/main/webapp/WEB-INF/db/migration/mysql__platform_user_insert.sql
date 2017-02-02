INSERT INTO `platform_user` (`platform_user_id`, `platform_role_id`, `login`, `password`, `full_name`, `system`, `status`, `version`, `access_token`)
VALUES
  (1, 1, 'admin', md5('admin'), 'admin', TRUE, 'ACTIVE', 1, uuid()),
  (2, 2, 'system', md5('system'), 'system', TRUE, 'ACTIVE', 1, uuid()),
  (3, 3, 'service', md5('service'), 'service', TRUE, 'ACTIVE', 1, uuid());