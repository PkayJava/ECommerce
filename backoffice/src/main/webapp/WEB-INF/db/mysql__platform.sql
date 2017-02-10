-- # mysql__platform_file_ddl.sql
-- ################################################################
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



-- # mysql__platform_layout_ddl.sql
-- ################################################################
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


-- # mysql__platform_layout_insert.sql
-- ################################################################
INSERT INTO `platform_layout` (`platform_layout_id`, `name`, `description`, `java_class`)
VALUES
  (1, 'MBaaS Layout', 'MBaaS Layout', 'com.angkorteam.platform.layout.MBaaSLayout');


-- # mysql__platform_localization_ddl.sql
-- ################################################################
CREATE TABLE `platform_localization` (

  `platform_localization_id` BIGINT(19)   NOT NULL,
  `key`             VARCHAR(100) NOT NULL,
  `language`        VARCHAR(10)  NOT NULL,
  `label`           VARCHAR(255) NOT NULL,
  `version`         INT(11)      NOT NULL DEFAULT 1,

  KEY (`version`),
  KEY (`key`),
  KEY (`language`),
  KEY (`label`),
  UNIQUE KEY (`key`, `language`),
  PRIMARY KEY (`platform_localization_id`)
);


-- # mysql__platform_localization_insert.sql
-- ################################################################
INSERT INTO platform_localization (platform_localization_id, `key`, language, label, version)
VALUES
  (1, 'null', '', 'Select One', 1),
  (2, 'label.login', '', 'Login', 1),
  (3, 'label.password', '', 'Password', 1),
  (4, 'button.login', '', 'Login', 1),
  (5, 'required', '', ' is required.', 1),
  (6, 'incorrect', '', ' is incorrect.', 1),
  (7, 'brand.text.mini', '', ' Shop', 1),
  (8, 'brand.text', '', ' ShopKH', 1),
  (9, 'nullValid', '', 'Select One', 1);


-- # mysql__platform_menu_ddl.sql
-- ################################################################
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


-- # mysql__platform_menu_insert.sql
-- ################################################################
INSERT INTO `platform_menu` (`platform_menu_id`, `platform_section_id`, `parent_platform_menu_id`, `title`, `path`, `icon`, `order`, `version`)
VALUES
  (1, 1, NULL, 'Content', 'Admin Console > Content', 'fa-align-justify', 1, 1),
  (2, 1, NULL, 'Security', 'Admin Console > Security', 'fa-lock', 2, 1),
  (3, 2, NULL, 'Catalog', 'User Console > Catalog', '', 1, 1),
  (4, 2, NULL, 'Alert', 'User Console > Alert', '', 2, 1),
  (5, 2, NULL, 'Market Place', 'User Console > Market Place', '', 3, 1),
  (6, 2, NULL, 'Cart', 'User Console > Cart', '', 4, 1);


-- # mysql__platform_menu_item_ddl.sql
-- ################################################################
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


-- # mysql__platform_menu_item_insert.sql
-- ################################################################
INSERT INTO `platform_menu_item` (`platform_menu_item_id`, `platform_section_id`, `platform_menu_id`, `title`, `icon`, `order`, `platform_page_id`)
VALUES
  (26, 2, NULL, 'Order', '', 4, 167),
  (25, 2, NULL, 'Order', '', 4, 165),
  (24, 2, NULL, 'Order', '', 4, 162),
  (23, NULL, 5, 'Shop', '', 1, 161),
  (22, NULL, 6, 'Shipping', '', 1, 158),
  (21, NULL, 3, 'Product', '', 1, 145),
  (20, NULL, 3, 'Discount', '', 1, 144),
  (19, NULL, 3, 'Category', '', 1, 141),
  (18, NULL, 3, 'Brand', '', 1, 138),
  (17, NULL, 6, 'Payment', '', 1, 135),
  (16, NULL, 3, 'Size', '', 1, 132),
  (15, NULL, 5, 'Branch', '', 1, 124),
  (14, NULL, 3, 'Article', '', 1, 121),
  (13, NULL, 3, 'Banner', '', 1, 116),
  (1, NULL, 1, 'Section', 'fa-leaf', 1, 8),
  (2, NULL, 1, 'Menu', 'fa-list-ul', 2, 2),
  (3, NULL, 1, 'Menu Item', 'fa-home', 3, 5),
  (4, NULL, 1, 'Layout', 'fa-columns', 4, 11),
  (5, NULL, 1, 'Page', 'fa-text-height', 5, 16),
  (6, NULL, 2, 'Role', 'fa-key', 1, 19),
  (8, NULL, 1, 'File', 'fa-floppy-o', 6, 22),
  (9, NULL, 2, 'User', 'fa-users', 2, 23),
  (10, 1, NULL, 'Service', 'fa-code', 4, 29),
  (11, NULL, 1, 'Setting', 'fa-cogs', 7, 32),
  (12, NULL, 3, 'Color', '', 1, 114);


-- # mysql__platform_page_ddl.sql
-- ################################################################
CREATE TABLE `platform_page` (

  `platform_page_id`   BIGINT(19)   NOT NULL,
  `platform_layout_id` BIGINT(19)   NOT NULL,
  `java_class`         VARCHAR(255) NOT NULL,
  `path`               VARCHAR(255) NOT NULL,
  `html_title`         VARCHAR(255) NOT NULL,
  `page_title`         VARCHAR(255) NOT NULL,
  `page_description`   VARCHAR(255) NOT NULL,
  `version`            INT(11)      NOT NULL DEFAULT 1,

  KEY (`platform_layout_id`),
  KEY (`html_title`),
  KEY (`page_title`),
  KEY (`version`),
  KEY (`page_description`),
  UNIQUE KEY (`java_class`),
  UNIQUE KEY (`path`),
  PRIMARY KEY (`platform_page_id`)
);


-- # mysql__platform_page_insert.sql
-- ################################################################
INSERT INTO `platform_page` (`platform_page_id`, `platform_layout_id`, `path`, `html_title`, `page_title`, `page_description`, `java_class`, `version`)
VALUES
  (172, 1, '/platform/user/profile', 'Platform User Profile', 'Platform User Profile', 'Platform User Profile', 'com.angkorteam.platform.page.user.UserProfilePage', 1),
  (171, 1, '/ecommerce/discount/coupon/generate', 'eCommerce Discount Coupon Generate', 'eCommerce Discount Coupon Generate', 'eCommerce Discount Coupon Generate', 'com.angkorteam.ecommerce.page.discount.DiscountCouponGeneratePage', 1),
  (170, 1, '/ecommerce/discount/coupon/browse', 'eCommerce Discount Coupon Browse', 'eCommerce Discount Coupon Browse', 'eCommerce Discount Coupon Browse', 'com.angkorteam.ecommerce.page.discount.DiscountCouponBrowsePage', 1),
  (169, 1, '/ecommerce/vendor/register', 'eCommerce Vendor Register', 'eCommerce Vendor Register', 'eCommerce Vendor Register', 'com.angkorteam.ecommerce.page.VendorRegisterPage', 1),
  (168, 1, '/ecommerce/vendor/order/review', 'eCommerce Vendor Order Review', 'eCommerce Vendor Order Review', 'eCommerce Vendor Order Review', 'com.angkorteam.ecommerce.page.order.VendorOrderReviewPage', 1),
  (167, 1, '/ecommerce/vendor/order/browse', 'eCommerce Vendor Order Browse', 'eCommerce Vendor Order Browse', 'eCommerce Vendor Order Browse', 'com.angkorteam.ecommerce.page.order.VendorOrderBrowsePage', 1),
  (166, 1, '/ecommerce/customer/order/review', 'eCommerce Customer Order Review', 'eCommerce Customer Order Review', 'eCommerce Customer Order Review', 'com.angkorteam.ecommerce.page.order.CustomerOrderReviewPage', 1),
  (165, 1, '/ecommerce/customer/order/browse', 'eCommerce Customer Order Browse', 'eCommerce Customer Order Browse', 'eCommerce Customer Order Browse', 'com.angkorteam.ecommerce.page.order.CustomerOrderBrowsePage', 1),
  (164, 1, '/ecommerce/order/review', 'eCommerce Order Review', 'eCommerce Order Review', 'eCommerce Order Review', 'com.angkorteam.ecommerce.page.order.OrderReviewPage', 1),
  (163, 1, '/ecommerce/order/detail', 'eCommerce Order Detail', 'eCommerce Order Detail', 'eCommerce Order Detail', 'com.angkorteam.ecommerce.page.order.OrderDetailPage', 1),
  (162, 1, '/ecommerce/order/browse', 'eCommerce Order Browse', 'eCommerce Order Browse', 'eCommerce Order Browse', 'com.angkorteam.ecommerce.page.order.OrderBrowsePage', 1),
  (159, 1, '/ecommerce/shop/modify', 'eCommerce Shop Modify', 'eCommerce Shop Modify', 'eCommerce Shop Modify', 'com.angkorteam.ecommerce.page.shop.ShopModifyPage', 1),
  (160, 1, '/ecommerce/shop/create', 'eCommerce Shop Create', 'eCommerce Shop Create', 'eCommerce Shop Create', 'com.angkorteam.ecommerce.page.shop.ShopCreatePage', 1),
  (161, 1, '/ecommerce/shop/browse', 'eCommerce Shop Browse', 'eCommerce Shop Browse', 'eCommerce Shop Browse', 'com.angkorteam.ecommerce.page.shop.ShopBrowsePage', 1),
  (156, 1, '/ecommerce/shipping/modify', 'eCommerce Shipping Modify', 'eCommerce Shipping Modify', 'eCommerce Shipping Modify', 'com.angkorteam.ecommerce.page.shipping.ShippingModifyPage', 1),
  (157, 1, '/ecommerce/shipping/create', 'eCommerce Shipping Create', 'eCommerce Shipping Create', 'eCommerce Shipping Create', 'com.angkorteam.ecommerce.page.shipping.ShippingCreatePage', 1),
  (158, 1, '/ecommerce/shipping/browse', 'eCommerce Shipping Browse', 'eCommerce Shipping Browse', 'eCommerce Shipping Browse', 'com.angkorteam.ecommerce.page.shipping.ShippingBrowsePage', 1),
  (154, 1, '/ecommerce/product/variant/gallery/create', 'eCommerce Product Variant Gallery Create', 'eCommerce Product Variant Gallery Create', 'eCommerce Product Variant Gallery Create', 'com.angkorteam.ecommerce.page.product.ProductVariantGalleryCreatePage', 1),
  (155, 1, '/ecommerce/product/variant/gallery/browse', 'eCommerce Product Variant Gallery Browse', 'eCommerce Product Variant Gallery Browse', 'eCommerce Product Variant Gallery Browse', 'com.angkorteam.ecommerce.page.product.ProductVariantGalleryBrowsePage', 1),
  (151, 1, '/ecommerce/product/variant/modify', 'eCommerce Product Variant Modify', 'eCommerce Product Variant Modify', 'eCommerce Product Variant Modify', 'com.angkorteam.ecommerce.page.product.ProductVariantModifyPage', 1),
  (152, 1, '/ecommerce/product/variant/create', 'eCommerce Product Variant Create', 'eCommerce Product Variant Create', 'eCommerce Product Variant Create', 'com.angkorteam.ecommerce.page.product.ProductVariantCreatePage', 1),
  (153, 1, '/ecommerce/product/variant/browse', 'eCommerce Product Variant Browse', 'eCommerce Product Variant Browse', 'eCommerce Product Variant Browse', 'com.angkorteam.ecommerce.page.product.ProductVariantBrowsePage', 1),
  (149, 1, '/ecommerce/product/gallery/create', 'eCommerce Product Gallery Create', 'eCommerce Product Gallery Create', 'eCommerce Product Gallery Create', 'com.angkorteam.ecommerce.page.product.ProductGalleryCreatePage', 1),
  (150, 1, '/ecommerce/product/gallery/browse', 'eCommerce Product Gallery Browse', 'eCommerce Product Gallery Browse', 'eCommerce Product Gallery Browse', 'com.angkorteam.ecommerce.page.product.ProductGalleryBrowsePage', 1),
  (148, 1, '/ecommerce/product/review', 'eCommerce Product Review', 'eCommerce Product Review', 'eCommerce Product Review', 'com.angkorteam.ecommerce.page.product.ProductReviewPage', 1),
  (147, 1, '/ecommerce/product/modify', 'eCommerce Product Modify', 'eCommerce Product Modify', 'eCommerce Product Modify', 'com.angkorteam.ecommerce.page.product.ProductModifyPage', 1),
  (146, 1, '/ecommerce/product/create', 'eCommerce Product Create', 'eCommerce Product Create', 'eCommerce Product Create', 'com.angkorteam.ecommerce.page.product.ProductCreatePage', 1),
  (145, 1, '/ecommerce/product/browse', 'eCommerce Product Browse', 'eCommerce Product Browse', 'eCommerce Product Browse', 'com.angkorteam.ecommerce.page.product.ProductBrowsePage', 1),
  (142, 1, '/ecommerce/discount/modify', 'eCommerce Discount Modify', 'eCommerce Discount Modify', 'eCommerce Discount Modify', 'com.angkorteam.ecommerce.page.discount.DiscountModifyPage', 1),
  (143, 1, '/ecommerce/discount/create', 'eCommerce Discount Create', 'eCommerce Discount Create', 'eCommerce Discount Create', 'com.angkorteam.ecommerce.page.discount.DiscountCreatePage', 1),
  (144, 1, '/ecommerce/discount/browse', 'eCommerce Discount Browse', 'eCommerce Discount Browse', 'eCommerce Discount Browse', 'com.angkorteam.ecommerce.page.discount.DiscountBrowsePage', 1),
  (139, 1, '/ecommerce/category/modify', 'eCommerce Category Modify', 'eCommerce Category Modify', 'eCommerce Category Modify', 'com.angkorteam.ecommerce.page.category.CategoryModifyPage', 1),
  (140, 1, '/ecommerce/category/create', 'eCommerce Category Create', 'eCommerce Category Create', 'eCommerce Category Create', 'com.angkorteam.ecommerce.page.category.CategoryCreatePage', 1),
  (141, 1, '/ecommerce/category/browse', 'eCommerce Category Browse', 'eCommerce Category Browse', 'eCommerce Category Browse', 'com.angkorteam.ecommerce.page.category.CategoryBrowsePage', 1),
  (136, 1, '/ecommerce/brand/modify', 'eCommerce Brand Modify', 'eCommerce Brand Modify', 'eCommerce Brand Modify', 'com.angkorteam.ecommerce.page.brand.BrandModifyPage', 1),
  (137, 1, '/ecommerce/brand/create', 'eCommerce Brand Create', 'eCommerce Brand Create', 'eCommerce Brand Create', 'com.angkorteam.ecommerce.page.brand.BrandCreatePage', 1),
  (138, 1, '/ecommerce/brand/browse', 'eCommerce Brand Browse', 'eCommerce Brand Browse', 'eCommerce Brand Browse', 'com.angkorteam.ecommerce.page.brand.BrandBrowsePage', 1),
  (133, 1, '/ecommerce/payment/modify', 'eCommerce Payment Modify', 'eCommerce Payment Modify', 'eCommerce Payment Modify', 'com.angkorteam.ecommerce.page.payment.PaymentModifyPage', 1),
  (134, 1, '/ecommerce/payment/create', 'eCommerce Payment Create', 'eCommerce Payment Create', 'eCommerce Payment Create', 'com.angkorteam.ecommerce.page.payment.PaymentCreatePage', 1),
  (135, 1, '/ecommerce/payment/browse', 'eCommerce Payment Browse', 'eCommerce Payment Browse', 'eCommerce Payment Browse', 'com.angkorteam.ecommerce.page.payment.PaymentBrowsePage', 1),
  (131, 1, '/ecommerce/size/create', 'eCommerce Size Create', 'eCommerce Size Create', 'eCommerce Size Create', 'com.angkorteam.ecommerce.page.size.SizeCreatePage', 1),
  (132, 1, '/ecommerce/size/browse', 'eCommerce Size Browse', 'eCommerce Size Browse', 'eCommerce Size Browse', 'com.angkorteam.ecommerce.page.size.SizeBrowsePage', 1),
  (128, 1, '/ecommerce/branch/transport/modify', 'eCommerce Branch Transport Modify', 'eCommerce Branch Transport Modify', 'eCommerce Branch Transport Modify', 'com.angkorteam.ecommerce.page.branch.BranchTransportModifyPage', 1),
  (129, 1, '/ecommerce/branch/transport/create', 'eCommerce Branch Transport Create', 'eCommerce Branch Transport Create', 'eCommerce Branch Transport Create', 'com.angkorteam.ecommerce.page.branch.BranchTransportCreatePage', 1),
  (130, 1, '/ecommerce/branch/transport/browse', 'eCommerce Branch Transport Browse', 'eCommerce Branch Transport Browse', 'eCommerce Branch Transport Browse', 'com.angkorteam.ecommerce.page.branch.BranchTransportBrowsePage', 1),
  (125, 1, '/ecommerce/branch/opening/modify', 'eCommerce Branch Opening Modify', 'eCommerce Branch Opening Modify', 'eCommerce Branch Opening Modify', 'com.angkorteam.ecommerce.page.branch.BranchOpeningModifyPage', 1),
  (126, 1, '/ecommerce/branch/opening/create', 'eCommerce Branch Opening Create', 'eCommerce Branch Opening Create', 'eCommerce Branch Opening Create', 'com.angkorteam.ecommerce.page.branch.BranchOpeningCreatePage', 1),
  (127, 1, '/ecommerce/branch/opening/browse', 'eCommerce Branch Opening Browse', 'eCommerce Branch Opening Browse', 'eCommerce Branch Opening Browse', 'com.angkorteam.ecommerce.page.branch.BranchOpeningBrowsePage', 1),
  (122, 1, '/ecommerce/branch/modify', 'eCommerce Branch Modify', 'eCommerce Branch Modify', 'eCommerce Branch Modify', 'com.angkorteam.ecommerce.page.branch.BranchModifyPage', 1),
  (123, 1, '/ecommerce/branch/create', 'eCommerce Branch Create', 'eCommerce Branch Create', 'eCommerce Branch Create', 'com.angkorteam.ecommerce.page.branch.BranchCreatePage', 1),
  (124, 1, '/ecommerce/branch/browse', 'eCommerce Branch Browse', 'eCommerce Branch Browse', 'eCommerce Branch Browse', 'com.angkorteam.ecommerce.page.branch.BranchBrowsePage', 1),
  (119, 1, '/ecommerce/article/modify', 'eCommerce Article Modify', 'eCommerce Article Modify', 'eCommerce Article Modify', 'com.angkorteam.ecommerce.page.article.ArticleModifyPage', 1),
  (120, 1, '/ecommerce/article/create', 'eCommerce Article Create', 'eCommerce Article Create', 'eCommerce Article Create', 'com.angkorteam.ecommerce.page.article.ArticleCreatePage', 1),
  (121, 1, '/ecommerce/article/browse', 'eCommerce Article Browse', 'eCommerce Article Browse', 'eCommerce Article Browse', 'com.angkorteam.ecommerce.page.article.ArticleBrowsePage', 1),
  (116, 1, '/ecommerce/banner/browse', 'eCommerce Banner Browse', 'eCommerce Banner Browse', 'eCommerce Banner Browse', 'com.angkorteam.ecommerce.page.banner.BannerBrowsePage', 1),
  (117, 1, '/ecommerce/banner/create', 'eCommerce Banner Create', 'eCommerce Banner Create', 'eCommerce Banner Create', 'com.angkorteam.ecommerce.page.banner.BannerCreatePage', 1),
  (118, 1, '/ecommerce/banner/modify', 'eCommerce Banner Modify', 'eCommerce Banner Modify', 'eCommerce Banner Modify', 'com.angkorteam.ecommerce.page.banner.BannerModifyPage', 1),
  (1, 1, '/platform/dashboard', 'Platform Dashboard', 'Platform Dashboard', 'Platform Dashboard', 'com.angkorteam.platform.page.DashboardPage', 1),
  (2, 1, '/platform/menu/browse', 'Platform Menu Browse', 'Platform Menu Browse', 'Platform Menu Browse', 'com.angkorteam.platform.page.menu.MenuBrowsePage', 1),
  (5, 1, '/platform/menu/item/browse', 'Platform Menu Item Browse', 'Platform Menu Item Browse', 'Platform Menu Item Browse', 'com.angkorteam.platform.page.menuitem.MenuItemBrowsePage', 1),
  (8, 1, '/platform/section/browse', 'Platform Section Browse', 'Platform Section Browse', 'Platform Section Browse', 'com.angkorteam.platform.page.section.SectionBrowsePage', 1),
  (11, 1, '/platform/layout/browse', 'Platform Layout Browse', 'Platform Layout Browse', 'Platform Layout Browse', 'com.angkorteam.platform.page.layout.LayoutBrowsePage', 1),
  (16, 1, '/platform/page/browse', 'Platform Page Browse', 'Platform Page Browse', 'Platform Page Browse', 'com.angkorteam.platform.page.page.PageBrowsePage', 1),
  (19, 1, '/platform/role/browse', 'Platform Role Browse', 'Platform Role Browse', 'Platform Role Browse', 'com.angkorteam.platform.page.role.RoleBrowsePage', 1),
  (22, 1, '/platform/file/browse', 'Platform File Browse', 'Platform File Browse', 'Platform File Browse', 'com.angkorteam.platform.page.file.FileBrowsePage', 1),
  (27, 1, '/platform/login', 'Platform Login', 'Platform Login', 'Platform Login', 'com.angkorteam.platform.page.LoginPage', 1),
  (28, 1, '/platform/logout', 'Platform Logout', 'Platform Logout', 'Platform Logout', 'com.angkorteam.platform.page.LogoutPage', 1),
  (29, 1, '/platform/rest/browse', 'Platform Rest Browse', 'Platform Rest Browse', 'Platform Rest Browse', 'com.angkorteam.platform.page.rest.RestBrowsePage', 1),
  (32, 1, '/platform/setting', 'Platform Setting', 'Platform Setting', 'Platform Setting', 'com.angkorteam.platform.page.SettingPage', 1),
  (23, 1, '/platform/user/browse', 'Platform User Browse', 'Platform User Browse', 'Platform User Browse', 'com.angkorteam.platform.page.user.UserBrowsePage', 1),
  (100, 1, '/platform/file/create', 'Platform File Create', 'Platform File Create', 'Platform File Create', 'com.angkorteam.platform.page.file.FileCreatePage', 1),
  (101, 1, '/platform/file/modify', 'Platform File Modify', 'Platform File Modify', 'Platform File Modify', 'com.angkorteam.platform.page.file.FileModifyPage', 1),
  (103, 1, '/platform/section/modify', 'Platform Section Modify', 'Platform Section Modify', 'Platform Section Modify', 'com.angkorteam.platform.page.section.SectionModifyPage', 1),
  (104, 1, '/platform/menu/item/modify', 'Platform Menu Item Modify', 'Platform Menu Item Modify', 'Platform Menu Item Modify', 'com.angkorteam.platform.page.menuitem.MenuItemModifyPage', 1),
  (105, 1, '/platform/menu/modify', 'Platform Menu Modify', 'Platform Menu Modify', 'Platform Menu Modify', 'com.angkorteam.platform.page.menu.MenuModifyPage', 1),
  (106, 1, '/platform/layout/modify', 'Platform Layout Modify', 'Platform Layout Modify', 'Platform Layout Modify', 'com.angkorteam.platform.page.layout.LayoutModifyPage', 1),
  (107, 1, '/platform/page/modify', 'Platform Page Modify', 'Platform Page Modify', 'Platform Page Modify', 'com.angkorteam.platform.page.page.PageModifyPage', 1),
  (108, 1, '/platform/role/create', 'Platform Role Create', 'Platform Role Create', 'Platform Role Create', 'com.angkorteam.platform.page.role.RoleCreatePage', 1),
  (109, 1, '/platform/role/modify', 'Platform Role Modify', 'Platform Role Modify', 'Platform Role Modify', 'com.angkorteam.platform.page.role.RoleModifyPage', 1),
  (110, 1, '/platform/user/modify', 'Platform User Modify', 'Platform User Modify', 'Platform User Modify', 'com.angkorteam.platform.page.user.UserModifyPage', 1),
  (111, 1, '/platform/user/create', 'Platform User Create', 'Platform User Create', 'Platform User Create', 'com.angkorteam.platform.page.user.UserCreatePage', 1),
  (112, 1, '/platform/user/password', 'Platform User Password', 'Platform User Password', 'Platform User Password', 'com.angkorteam.platform.page.user.UserPasswordPage', 1),
  (113, 1, '/platform/rest/modify', 'Platform Rest Modify', 'Platform Rest Modify', 'Platform Rest Modify', 'com.angkorteam.platform.page.rest.RestModifyPage', 1),
  (114, 1, '/ecommerce/color/browse', 'eCommerce Color Browse', 'eCommerce Color Browse', 'eCommerce Color Browse', 'com.angkorteam.ecommerce.page.color.ColorBrowsePage', 1),
  (115, 1, '/ecommerce/color/create', 'eCommerce Color Create', 'eCommerce Color Create', 'eCommerce Color Create', 'com.angkorteam.ecommerce.page.color.ColorCreatePage', 1);


-- # mysql__platform_page_role_ddl.sql
-- ################################################################
CREATE TABLE `platform_page_role` (

  `platform_page_role_id` BIGINT(19) NOT NULL,
  `platform_page_id`      BIGINT(19) NOT NULL,
  `platform_role_id`      BIGINT(19) NOT NULL,

  UNIQUE KEY (`platform_page_id`, `platform_role_id`),
  PRIMARY KEY (`platform_page_role_id`)
);


-- # mysql__platform_page_role_insert.sql
-- ################################################################
INSERT INTO platform_page_role (platform_page_role_id, platform_role_id, platform_page_id)
VALUES
  (102, 7, 172),
  (101, 6, 172),
  (100, 4, 172),
  (99, 3, 172),
  (98, 2, 172),
  (97, 1, 172),
  (94, 1, 171),
  (95, 1, 170),
  (96, 1, 124),
  (1, 5, 1),
  (2, 4, 1),
  (3, 4, 155),
  (4, 4, 154),
  (5, 4, 151),
  (6, 4, 152),
  (7, 4, 153),
  (9, 4, 149),
  (10, 4, 150),
  (11, 4, 148),
  (12, 4, 147),
  (13, 4, 146),
  (14, 4, 145),
  (15, 4, 168),
  (16, 4, 167),
  (17, 5, 166),
  (18, 5, 165),
  (19, 1, 164),
  (20, 1, 163),
  (21, 1, 162),
  (22, 1, 159),
  (23, 1, 160),
  (24, 1, 161),
  (25, 1, 156),
  (26, 1, 157),
  (27, 1, 158),
  (28, 1, 154),
  (29, 1, 155),
  (30, 1, 151),
  (31, 1, 152),
  (32, 1, 153),
  (33, 1, 149),
  (34, 1, 150),
  (35, 1, 148),
  (36, 1, 147),
  (37, 1, 146),
  (38, 1, 145),
  (39, 1, 142),
  (40, 1, 143),
  (41, 1, 144),
  (42, 1, 139),
  (43, 1, 140),
  (44, 1, 141),
  (45, 1, 136),
  (46, 1, 137),
  (47, 1, 138),
  (48, 1, 133),
  (49, 1, 134),
  (50, 1, 135),
  (51, 1, 131),
  (52, 1, 132),
  (53, 1, 128),
  (54, 1, 129),
  (56, 1, 130),
  (57, 1, 125),
  (58, 1, 126),
  (59, 1, 127),
  (60, 1, 122),
  (61, 1, 123),
  (62, 1, 119),
  (63, 1, 120),
  (64, 1, 121),
  (65, 1, 116),
  (66, 1, 117),
  (67, 1, 118),
  (68, 1, 1),
  (69, 1, 100),
  (70, 1, 101),
  (71, 1, 103),
  (72, 1, 22),
  (73, 1, 8),
  (74, 1, 2),
  (75, 1, 5),
  (76, 1, 104),
  (77, 1, 105),
  (78, 1, 106),
  (79, 1, 11),
  (80, 1, 16),
  (81, 1, 107),
  (82, 1, 19),
  (83, 1, 108),
  (84, 1, 109),
  (85, 1, 32),
  (86, 1, 110),
  (87, 1, 23),
  (88, 1, 111),
  (89, 1, 112),
  (90, 1, 29),
  (91, 1, 113),
  (92, 1, 114),
  (93, 1, 115);


-- # mysql__platform_rest_ddl.sql
-- ################################################################
CREATE TABLE `platform_rest` (

  `platform_rest_id` BIGINT(19)   NOT NULL,
  `name`             VARCHAR(255) NOT NULL,
  `description`      VARCHAR(255),
  `java_class`       VARCHAR(255) NOT NULL,
  `version`          INT(11)      NOT NULL DEFAULT 1,

  KEY (`version`),
  KEY (`description`),
  KEY (`name`),
  UNIQUE KEY (`java_class`),
  PRIMARY KEY (`platform_rest_id`)
);


-- # mysql__platform_rest_insert.sql
-- ################################################################
INSERT INTO platform_rest (platform_rest_id, name, description, java_class, version)
VALUES
  (18, 'Orders Single Service Get', 'Orders Single Service Get', 'com.angkorteam.ecommerce.controller.OrdersSingleServiceGet', 1),
  (17, 'Orders Service Get', 'Orders Service Get', 'com.angkorteam.ecommerce.controller.OrdersServiceGet', 1),
  (16, 'Orders Service Post', 'Orders Service Post', 'com.angkorteam.ecommerce.controller.OrdersServicePost', 1),
  (15, 'Cart Discounts Service Post', 'Cart Discounts Service Post', 'com.angkorteam.ecommerce.controller.CartDiscountsServicePost', 1),
  (14, 'Cart Discounts Single Service Delete', 'Cart Discounts Single Service Delete', 'com.angkorteam.ecommerce.controller.CartDiscountsSingleServiceDelete', 1),
  (13, 'Cart Item Service Delete', 'Cart Item Service Delete', 'com.angkorteam.ecommerce.controller.CartItemServiceDelete', 1),
  (12, 'Cart Item Service Put', 'Cart Item Service Put', 'com.angkorteam.ecommerce.controller.CartItemServicePut', 1),
  (11, 'Cart Service Post', 'Cart Service Post', 'com.angkorteam.ecommerce.controller.CartServicePost', 1),
  (10, 'Cart Info Service Get', 'Cart Info Service Get', 'com.angkorteam.ecommerce.controller.CartInfoServiceGet', 1),
  (9, 'Cart Service Get', 'Cart Service Get', 'com.angkorteam.ecommerce.controller.CartServiceGet', 1),
  (8, 'Wishlist Single Service Delete', 'Wishlist Single Service Delete', 'com.angkorteam.ecommerce.controller.WishlistSingleServiceDelete', 1),
  (7, 'Wishlist Service Post', 'Wishlist Service Post', 'com.angkorteam.ecommerce.controller.WishlistServicePost', 1),
  (6, 'Wishlist Is In Wishlist Service Get', 'Wishlist Is In Wishlist Service Get', 'com.angkorteam.ecommerce.controller.WishlistIsInWishlistServiceGet', 1),
  (5, 'User Change Password Service Put', 'User Change Password Service Put', 'com.angkorteam.ecommerce.controller.UserChangePasswordServicePut', 1),
  (4, 'User Single Service Put', 'User Single Service Put', 'com.angkorteam.ecommerce.controller.UserSingleServicePut', 1),
  (1, 'System Controller', 'System Controller', 'com.angkorteam.ecommerce.controller.SystemController', 1),
  (2, 'Wishlist Service Get', 'Wishlist Service Get', 'com.angkorteam.ecommerce.controller.WishlistServiceGet', 1),
  (3, 'User Single Service Get', 'User Single Service Get', 'com.angkorteam.ecommerce.controller.UserSingleServiceGet', 1);


-- # mysql__platform_rest_role_ddl.sql
-- ################################################################
CREATE TABLE `platform_rest_role` (

  `platform_rest_role_id` BIGINT(19) NOT NULL,
  `platform_rest_id`      BIGINT(19) NOT NULL,
  `platform_role_id`      BIGINT(19) NOT NULL,

  UNIQUE KEY (`platform_rest_id`, `platform_role_id`),
  PRIMARY KEY (`platform_rest_role_id`)
);


-- # mysql__platform_rest_role_insert.sql
-- ################################################################
INSERT INTO platform_rest_role (platform_rest_role_id, platform_role_id, platform_rest_id)
VALUES
  (1, 5, 18),
  (2, 5, 17),
  (3, 5, 16),
  (4, 5, 15),
  (5, 5, 14),
  (6, 5, 13),
  (7, 5, 12),
  (8, 5, 11),
  (9, 5, 10),
  (10, 5, 9),
  (11, 5, 8),
  (12, 5, 7),
  (13, 5, 6),
  (14, 5, 5),
  (15, 5, 4),
  (16, 5, 2),
  (17, 5, 3);


-- # mysql__platform_role_ddl.sql
-- ################################################################
CREATE TABLE `platform_role` (

  `platform_role_id` BIGINT(19)   NOT NULL,
  `name`             VARCHAR(100) NOT NULL,
  `description`      VARCHAR(255),
  `version`          INT(11)      NOT NULL DEFAULT 1,

  KEY (`version`),
  KEY (`description`),
  UNIQUE KEY (`name`),
  PRIMARY KEY (`platform_role_id`)
);


-- # mysql__platform_role_insert.sql
-- ################################################################
INSERT INTO `platform_role` (`platform_role_id`, `name`, `description`, `version`)
VALUES
  (4, 'ecommerce_vendor', 'vendor role', 1),
  (5, 'ecommerce_user', 'user role', 1),
  (6, 'ecommerce_advance_vendor', 'advance vendor role', 1),
  (1, 'administrator', 'administrative role, users belonging to this role are Gods', 1),
  (2, 'system', 'system role to give users maintain system', 1),
  (3, 'service', 'service role to give access resource to call api', 1);


-- # mysql__platform_section_ddl.sql
-- ################################################################
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


-- # mysql__platform_section_insert.sql
-- ################################################################
INSERT INTO `platform_section` (`platform_section_id`, `title`, `order`, `version`)
VALUES
  (1, 'Admin Console', 1, 1),
  (2, 'User Console', 2, 1);


-- # mysql__platform_setting_ddl.sql
-- ################################################################
CREATE TABLE `platform_setting` (

  `platform_setting_id` BIGINT(19)   NOT NULL,
  `key`                 VARCHAR(100) NOT NULL,
  `description`         VARCHAR(255),
  `value`               VARCHAR(255) NOT NULL,
  `version`             INT(11)      NOT NULL DEFAULT 1,

  KEY (`version`),
  KEY (`description`),
  KEY (`value`),
  UNIQUE KEY (`key`),
  PRIMARY KEY (`platform_setting_id`)
);


-- # mysql__platform_setting_insert.sql
-- ################################################################
INSERT INTO `platform_setting` (`platform_setting_id`, `key`, `value`, `description`, `version`)
VALUES
  (10, 'smtp_user', '', 'SMTP User', 1),
  (9, 'smtp_password', '', 'SMTP Password', 1),
  (1, 'home_page', 'Home Page', 'Home Page', 1),
  (2, 'google_api_key', '', 'Google Api Key', 1),
  (3, 'asset', 'http://192.168.0.114:8080', 'Asset', 1),
  (4, 'price_format', '$#,###,##0.00', 'Price Format', 1),
  (5, 'currency', 'USD', 'Currency', 1),
  (6, 'time_format', 'hh:mm a', 'Time Format', 1),
  (7, 'date_format', 'yyyy-MM-dd', 'Date Format', 1),
  (8, 'datetime_format', 'dd.MM.yyyy hh:mm a', 'Datetime Format', 1);


-- # mysql__platform_user_ddl.sql
-- ################################################################
CREATE TABLE `platform_user` (

  `platform_user_id` BIGINT(19)   NOT NULL,
  `login`            VARCHAR(255) NOT NULL,
  `password`         VARCHAR(255) NOT NULL,
  `full_name`        VARCHAR(255) NOT NULL,
  `platform_role_id` BIGINT(19)   NOT NULL,
  `system`           BIT(1)       NOT NULL DEFAULT 0,
  `status`           VARCHAR(20)  NOT NULL DEFAULT 'ACTIVE',
  `version`          INT(11)      NOT NULL DEFAULT 1,
  `access_token`     VARCHAR(255) NOT NULL,
  `street`           VARCHAR(255),
  `city`             VARCHAR(255),
  `house_number`     VARCHAR(255),
  `zip`              VARCHAR(255),
  `phone`            VARCHAR(255),
  `gender`           VARCHAR(10),
  `country`          VARCHAR(100),

  KEY (`street`),
  KEY (`city`),
  KEY (`house_number`),
  KEY (`zip`),
  KEY (`phone`),
  KEY (`gender`),
  KEY (`country`),
  KEY (`full_name`),
  KEY (`password`),
  KEY (`platform_role_id`),
  KEY (`system`),
  KEY (`status`),
  KEY (`version`),
  UNIQUE KEY (`access_token`),
  UNIQUE KEY (`login`),
  PRIMARY KEY (`platform_user_id`)
);


-- # mysql__platform_user_insert.sql
-- ################################################################
INSERT INTO `platform_user` (`platform_user_id`, `platform_role_id`, `login`, `password`, `full_name`, `system`, `status`, `version`, `access_token`)
VALUES
  (1, 1, 'admin', md5('admin'), 'admin', TRUE, 'ACTIVE', 1, uuid()),
  (2, 2, 'system', md5('system'), 'system', TRUE, 'ACTIVE', 1, uuid()),
  (3, 3, 'service', md5('service'), 'service', TRUE, 'ACTIVE', 1, uuid());


-- # mysql__platform_uuid_ddl.sql
-- ################################################################
CREATE TABLE `platform_uuid` (

  `platform_uuid_id` BIGINT(19)   NOT NULL AUTO_INCREMENT,
  `table_name`       VARCHAR(255) NOT NULL,
  `value`            BIGINT(19)   NOT NULL,

  KEY (`value`),
  UNIQUE KEY (`table_name`),
  PRIMARY KEY (`platform_uuid_id`)

);



-- # mysql__platform_uuid_insert.sql
-- ################################################################
INSERT INTO `platform_uuid` (table_name, value)
  VALUE
  ('ecommerce_discount_coupon', 1000),
  ('ecommerce_banner', 1000),
  ('ecommerce_brand', 1000),
  ('ecommerce_page', 1000),
  ('ecommerce_category', 1000),
  ('platform_setting', 1000),
  ('platform_file', 1000),
  ('ecommerce_branch', 1000),
  ('ecommerce_branch_opening', 1000),
  ('ecommerce_branch_transport', 1000),
  ('ecommerce_color', 1000),
  ('ecommerce_discount', 1000),
  ('ecommerce_vendor_order', 1000),
  ('ecommerce_vendor_order_item', 1000),
  ('ecommerce_payment', 1000),
  ('ecommerce_product', 1000),
  ('ecommerce_product_related', 1000),
  ('ecommerce_product_variant', 1000),
  ('ecommerce_product_variant_image', 1000),
  ('ecommerce_shipping_payment', 1000),
  ('ecommerce_product_image', 1000),
  ('ecommerce_shipping', 1000),
  ('ecommerce_shop', 1000),
  ('ecommerce_size', 1000),
  ('platform_user', 1000),
  ('platform_page_role', 1000),
  ('platform_rest_role', 1000),
  ('platform_role', 1000),
  ('ecommerce_cart_product_item', 1000),
  ('ecommerce_order', 1000),
  ('ecommerce_order_item', 1000),
  ('ecommerce_cart', 1000),
  ('ecommerce_device', 1000),
  ('ecommerce_wishlist', 1000);



