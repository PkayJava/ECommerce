package com.angkorteam.ecommerce.page.product;

import com.angkorteam.ecommerce.vo.ProductReviewVO;
import com.angkorteam.framework.extension.wicket.ajax.markup.html.AjaxLink;
import com.angkorteam.framework.extension.wicket.markup.html.form.Button;
import com.angkorteam.framework.extension.wicket.markup.html.form.Form;
import com.angkorteam.framework.extension.wicket.markup.html.panel.TextFeedbackPanel;
import com.angkorteam.framework.jdbc.JoinType;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.jdbc.UpdateQuery;
import com.angkorteam.platform.Platform;
import com.angkorteam.platform.page.MBaaSPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.image.ExternalImage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;

/**
 * Created by socheatkhauv on 25/1/17.
 */
public class ProductReviewPage extends MBaaSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductReviewPage.class);

    private String ecommerceProductId;

    private Double shippingPrice;
    private TextField<Double> shippingPriceField;
    private TextFeedbackPanel shippingPriceFeedback;

    private String login;
    private Label loginLabel;

    private String fullName;
    private Label fullNameLabel;

    private String reference;
    private Label referenceLabel;

    private String name;
    private Label nameLabel;

    private String description;
    private Label descriptionLabel;

    private Double normalPrice;
    private Label normalPriceLabel;

    private Double discountPrice;
    private Label discountPriceLabel;

    private String brand;
    private Label brandLabel;

    private String category;
    private Label categoryLabel;

    private String mainImage;
    private ExternalImage mainImageLabel;

    private String mainImageHighRes;
    private ExternalImage mainImageHighResLabel;

    private Form<Void> form;
    private Button saveButton;
    private BookmarkablePageLink<Void> closeButton;
    private AjaxLink<Void> deleteButton;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        this.ecommerceProductId = getPageParameters().get("ecommerceProductId").toString("");

        SelectQuery selectQuery = null;

        String currency = Platform.getSetting("currency");
        String asset = Platform.getSetting("asset");
        DecimalFormat priceFormat = new DecimalFormat(Platform.getSetting("price_format"));

        SelectQuery productQuery = new SelectQuery("ecommerce_product");
        productQuery.addField("ecommerce_product.ecommerce_product_id AS productId");
        productQuery.addField("ecommerce_product.name AS name");
        productQuery.addField("ecommerce_product.normal_price AS normalPrice");
        productQuery.addField("ecommerce_category.path AS category");
        productQuery.addField("ecommerce_brand.name AS brand");
        productQuery.addField("platform_user.login AS login");
        productQuery.addField("platform_user.full_name AS fullName");
        productQuery.addField("ecommerce_product.price AS discountPrice");
        productQuery.addField("ecommerce_product.shipping_price AS shippingPrice");
        productQuery.addField("ecommerce_product.reference AS reference");
        productQuery.addField("ecommerce_product.description AS description");
        productQuery.addField("CONCAT('" + asset + "','/api/resource', main_image.path, '/', main_image.name) AS mainImage");
        productQuery.addField("CONCAT('" + asset + "','/api/resource', main_image_high_res.path, '/', main_image_high_res.name) AS mainImageHighRes");
        productQuery.addJoin(JoinType.LeftJoin, "platform_file AS main_image", "ecommerce_product.main_image_platform_file_id = main_image.platform_file_id");
        productQuery.addJoin(JoinType.LeftJoin, "platform_file AS main_image_high_res", "ecommerce_product.main_image_high_res_platform_file_id = main_image_high_res.platform_file_id");
        productQuery.addJoin(JoinType.LeftJoin, "ecommerce_category", "ecommerce_product.ecommerce_category_id = ecommerce_category.ecommerce_category_id");
        productQuery.addJoin(JoinType.LeftJoin, "ecommerce_brand", "ecommerce_product.ecommerce_brand_id = ecommerce_brand.ecommerce_brand_id");
        productQuery.addJoin(JoinType.InnerJoin, "platform_user", "ecommerce_product.platform_user_id = platform_user.platform_user_id");
        productQuery.addWhere("ecommerce_product.ecommerce_product_id = :ecommerce_product_id", this.ecommerceProductId);
        ProductReviewVO productRecord = getNamed().queryForObject(productQuery.toSQL(), productQuery.getParam(), ProductReviewVO.class);

        this.form = new Form<>("form");
        layout.add(this.form);

        if (productRecord.getShippingPrice() != null) {
            this.shippingPrice = productRecord.getShippingPrice();
        } else {
            this.shippingPrice = 0d;
        }
        this.shippingPriceField = new TextField<>("shippingPriceField", new PropertyModel<>(this, "shippingPrice"));
        this.shippingPriceField.setRequired(true);
        this.form.add(this.shippingPriceField);
        this.shippingPriceFeedback = new TextFeedbackPanel("shippingPriceFeedback", this.shippingPriceField);
        this.form.add(this.shippingPriceFeedback);

        this.login = productRecord.getLogin();
        this.loginLabel = new Label("loginLabel", new PropertyModel<>(this, "login"));
        this.form.add(this.loginLabel);

        this.fullName = productRecord.getFullName();
        this.fullNameLabel = new Label("fullNameLabel", new PropertyModel<>(this, "fullName"));
        this.form.add(this.fullNameLabel);

        this.reference = productRecord.getReference();
        this.referenceLabel = new Label("referenceLabel", new PropertyModel<>(this, "reference"));
        this.form.add(this.referenceLabel);

        this.name = productRecord.getName();
        this.nameLabel = new Label("nameLabel", new PropertyModel<>(this, "name"));
        this.form.add(this.nameLabel);

        this.description = productRecord.getDescription();
        this.descriptionLabel = new Label("descriptionLabel", new PropertyModel<>(this, "description"));
        this.form.add(this.descriptionLabel);

        if (productRecord.getNormalPrice() != null) {
            this.normalPrice = productRecord.getNormalPrice();
        }
        this.normalPriceLabel = new Label("normalPriceLabel", new PropertyModel<>(this, "normalPrice"));
        this.form.add(this.normalPriceLabel);

        if (productRecord.getDiscountPrice() != null) {
            this.discountPrice = productRecord.getDiscountPrice();
        }
        this.discountPriceLabel = new Label("discountPriceLabel", new PropertyModel<>(this, "discountPrice"));
        this.form.add(this.discountPriceLabel);

        this.brand = productRecord.getBrand();
        this.brandLabel = new Label("brandLabel", new PropertyModel<>(this, "brand"));
        this.form.add(this.brandLabel);

        this.category = productRecord.getCategory();
        this.categoryLabel = new Label("categoryLabel", new PropertyModel<>(this, "category"));
        this.form.add(this.categoryLabel);

        this.mainImage = productRecord.getMainImage();
        this.mainImageLabel = new ExternalImage("mainImageLabel", new PropertyModel<>(this, "mainImage"));
        this.form.add(this.mainImageLabel);

        this.mainImageHighRes = productRecord.getMainImageHighRes();
        this.mainImageHighResLabel = new ExternalImage("mainImageHighResLabel", new PropertyModel<>(this, "mainImageHighRes"));
        this.form.add(this.mainImageHighResLabel);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonOnSubmit);
        this.form.add(this.saveButton);

        this.closeButton = new BookmarkablePageLink<>("closeButton", ProductBrowsePage.class);
        this.form.add(this.closeButton);

        this.deleteButton = new AjaxLink<>("deleteButton");
        this.form.add(this.deleteButton);
    }

    private void saveButtonOnSubmit(Button button) {
        UpdateQuery updateQuery = new UpdateQuery("ecommerce_product");
        updateQuery.addValue("shipping_price = :shipping_price", this.shippingPrice);
        updateQuery.addValue("ready = :ready", true);
        updateQuery.addWhere("ecommerce_product_id = :ecommerce_product_id", this.ecommerceProductId);
        getNamed().update(updateQuery.toSQL(), updateQuery.getParam());
        setResponsePage(ProductBrowsePage.class);
    }

}