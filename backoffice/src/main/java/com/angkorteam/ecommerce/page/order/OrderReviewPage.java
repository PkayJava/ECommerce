package com.angkorteam.ecommerce.page.order;

import com.angkorteam.ecommerce.model.EcommerceOrder;
import com.angkorteam.ecommerce.model.EcommerceOrderItem;
import com.angkorteam.ecommerce.model.EcommerceProduct;
import com.angkorteam.framework.wicket.ajax.markup.html.AjaxLink;
import com.angkorteam.framework.jdbc.InsertQuery;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.jdbc.UpdateQuery;
import com.angkorteam.platform.Platform;
import com.angkorteam.platform.page.MBaaSPage;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by socheatkhauv on 26/1/17.
 */
public class OrderReviewPage extends MBaaSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderReviewPage.class);

    private String ecommerceOrderId;

    private String orderNo;
    private Label orderNoLabel;

    private String fromName;
    private Label fromNameLabel;

    private String addressLine1;
    private Label addressLine1Label;

    private String addressLine2;
    private Label addressLine2Label;

    private String phone;
    private Label phoneLabel;

    private String email;
    private Label emailLabel;

    private String createdDate;
    private Label createdDateLabel;

    private String paymentName;
    private Label paymentNameLabel;

    private String subTotal;
    private Label subTotalLabel;

    private String total;
    private Label totalLabel;

    private String discount;
    private Label discountLabel;

    private String shipping;
    private Label shippingLabel;

    private String totalAmount;
    private Label totalAmountLabel;

    private String note;
    private Label noteLabel;

    private String payment;
    private Label paymentLabel;

    private String grandTotalAmount;
    private Label grandTotalAmountLabel;

    private BookmarkablePageLink<Void> closeButton;
    private AjaxLink<Void> checkStockButton;
    private AjaxLink<Void> shippingRequestButton;
    private AjaxLink<Void> packageAndDeliveryButton;
    private AjaxLink<Void> cancelButton;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        DateFormat datetimeFormat = new SimpleDateFormat(Platform.getSetting("datetime_format"));
        DecimalFormat priceFormat = new DecimalFormat(Platform.getSetting("price_format"));

        this.ecommerceOrderId = getPageParameters().get("ecommerceOrderId").toString("");

        EcommerceOrder orderRecord = getJdbcTemplate().queryForObject("select * from ecommerce_order where ecommerce_order_id = ?", EcommerceOrder.class, this.ecommerceOrderId);

        this.orderNo = "Invoice #" + orderRecord.getEcommerceOrderId();
        this.orderNoLabel = new Label("orderNoLabel", new PropertyModel<>(this, "orderNo"));
        layout.add(this.orderNoLabel);

        this.fromName = orderRecord.getName();
        this.fromNameLabel = new Label("fromNameLabel", new PropertyModel<>(this, "fromName"));
        layout.add(this.fromNameLabel);

        String houseNumber = orderRecord.getHouseNumber();
        String street = orderRecord.getStreet();
        List<String> line1 = Lists.newArrayList();
        if (!Strings.isNullOrEmpty(houseNumber)) {
            line1.add(houseNumber.startsWith("#") ? houseNumber : "#" + houseNumber);
        }
        if (!Strings.isNullOrEmpty(street)) {
            line1.add("st. " + street);
        }
        if (!line1.isEmpty()) {
            this.addressLine1 = StringUtils.join(line1, ", ");
        }
        this.addressLine1Label = new Label("addressLine1Label", new PropertyModel<>(this, "addressLine1"));
        this.addressLine1Label.setVisible(!Strings.isNullOrEmpty(this.addressLine1));
        layout.add(this.addressLine1Label);

        String city = orderRecord.getCity();
        String zip = orderRecord.getZip();
        List<String> line2 = Lists.newArrayList();
        if (!Strings.isNullOrEmpty(city)) {
            line2.add(city);
        }
        if (!Strings.isNullOrEmpty(zip)) {
            line2.add(zip);
        }
        if (!line2.isEmpty()) {
            this.addressLine2 = StringUtils.join(line2, ", ");
        }
        this.addressLine2Label = new Label("addressLine2Label", new PropertyModel<>(this, "addressLine2"));
        this.addressLine2Label.setVisible(!Strings.isNullOrEmpty(this.addressLine2));
        layout.add(this.addressLine2Label);

        this.email = orderRecord.getEmail();
        this.emailLabel = new Label("emailLabel", new PropertyModel<>(this, "email"));
        this.emailLabel.setVisible(!Strings.isNullOrEmpty(this.email));
        layout.add(this.emailLabel);

        this.phone = orderRecord.getPhone();
        this.phoneLabel = new Label("phoneLabel", new PropertyModel<>(this, "phone"));
        this.phoneLabel.setVisible(!Strings.isNullOrEmpty(this.phone));
        layout.add(this.phoneLabel);

        this.createdDate = datetimeFormat.format(orderRecord.getDateCreated());
        this.createdDateLabel = new Label("createdDateLabel", new PropertyModel<>(this, "createdDate"));
        layout.add(this.createdDateLabel);

        this.note = Strings.isNullOrEmpty(orderRecord.getNote()) ? "N/A" : orderRecord.getNote();
        this.noteLabel = new Label("noteLabel", new PropertyModel<>(this, "note"));
        layout.add(this.noteLabel);

        this.paymentName = orderRecord.getPaymentName();
        this.paymentNameLabel = new Label("paymentNameLabel", new PropertyModel<>(this, "paymentName"));
        layout.add(this.paymentNameLabel);

        this.subTotal = priceFormat.format(orderRecord.getSubTotalAmount());
        this.subTotalLabel = new Label("subTotalLabel", new PropertyModel<>(this, "subTotal"));
        layout.add(this.subTotalLabel);

        this.shipping = priceFormat.format(orderRecord.getShippingPrice() + orderRecord.getShippingPriceAddon()) + " (" + orderRecord.getShippingName() + ")";
        this.shippingLabel = new Label("shippingLabel", new PropertyModel<>(this, "shipping"));
        layout.add(this.shippingLabel);
        this.shippingLabel.setVisible(orderRecord.getShippingPrice() > 0);

        this.discount = priceFormat.format(orderRecord.getDiscountAmount());
        this.discountLabel = new Label("discountLabel", new PropertyModel<>(this, "discount"));
        layout.add(this.discountLabel);
        this.discountLabel.setVisible(orderRecord.getDiscountAmount() > 0);

        this.totalAmount = priceFormat.format(orderRecord.getTotalAmount());
        this.totalAmountLabel = new Label("totalAmountLabel", new PropertyModel<>(this, "totalAmount"));
        layout.add(this.totalAmountLabel);

        this.grandTotalAmount = priceFormat.format(orderRecord.getGrandTotalAmount());
        this.grandTotalAmountLabel = new Label("grandTotalAmountLabel", new PropertyModel<>(this, "grandTotalAmount"));
        layout.add(this.grandTotalAmountLabel);

        this.payment = priceFormat.format(orderRecord.getPaymentPrice()) + " (" + orderRecord.getPaymentName() + ")";
        this.paymentLabel = new Label("paymentLabel", new PropertyModel<>(this, "payment"));
        layout.add(this.paymentLabel);
        this.paymentLabel.setVisible(orderRecord.getPaymentPrice() > 0);

        RepeatingView view = new RepeatingView("items");
        layout.add(view);

        List<EcommerceOrderItem> items = getJdbcTemplate().queryForList("select * from ecommerce_order_item where ecommerce_order_id = ?", EcommerceOrderItem.class, this.ecommerceOrderId);
        if (items != null && !items.isEmpty()) {
            for (EcommerceOrderItem item : items) {
                String id = view.newChildId();
                Double price = item.getProductPrice();
                Double totalPrice = item.getTotalPrice();
                Fragment fragment = new Fragment(id, "itemTemplate", this);
                view.add(fragment);
                Label quantityLabel = new Label("quantityLabel", new PropertyModel<>(item, "quantity"));
                fragment.add(quantityLabel);
                Label productLabel = new Label("productLabel", new PropertyModel<>(item, "productName"));
                fragment.add(productLabel);
                Label codeLabel = new Label("codeLabel", new PropertyModel<>(item, "productReference"));
                fragment.add(codeLabel);
                Label colorLabel = new Label("colorLabel", new PropertyModel<>(item, "colorValue"));
                fragment.add(colorLabel);
                Label sizeLabel = new Label("sizeLabel", new PropertyModel<>(item, "sizeValue"));
                fragment.add(sizeLabel);
                Label unitPriceLabel = new Label("unitPriceLabel", priceFormat.format(price));
                fragment.add(unitPriceLabel);
                Label amountLabel = new Label("amountLabel", priceFormat.format(totalPrice));
                fragment.add(amountLabel);
            }
        }

        this.closeButton = new BookmarkablePageLink<Void>("closeButton", OrderBrowsePage.class);
        layout.add(this.closeButton);

        this.checkStockButton = new AjaxLink<>("checkStockButton");
        this.checkStockButton.setOnClick(this::checkStockButtonOnClick);
        layout.add(this.checkStockButton);

        this.shippingRequestButton = new AjaxLink<>("shippingRequestButton");
        this.shippingRequestButton.setOnClick(this::shippingRequestButtonOnClick);
        layout.add(this.shippingRequestButton);

        this.packageAndDeliveryButton = new AjaxLink<>("packageAndDeliveryButton");
        this.packageAndDeliveryButton.setOnClick(this::packageAndDeliveryButtonOnClick);
        layout.add(this.packageAndDeliveryButton);

        this.cancelButton = new AjaxLink<>("cancelButton");
        this.cancelButton.setOnClick(this::cancelButtonOnClick);
        layout.add(this.cancelButton);

        this.checkStockButton.setVisible(false);
        this.shippingRequestButton.setVisible(false);
        this.packageAndDeliveryButton.setVisible(false);
        this.cancelButton.setVisible(false);

        String orderStatus = orderRecord.getOrderStatus();
        String buyerStatus = orderRecord.getBuyerStatus();
        Integer vendorCount = getJdbcTemplate().queryForObject("select count(*) from ecommerce_vendor_order where ecommerce_order_id = ?", int.class, this.ecommerceOrderId);
        if ("New".equals(orderStatus) && "Reviewing".equals(buyerStatus)) {
            this.checkStockButton.setVisible(true);
            this.shippingRequestButton.setVisible(false);
            this.packageAndDeliveryButton.setVisible(false);
            this.cancelButton.setVisible(true);
        } else if ("Check Stock".equals(orderStatus) && "Progressing".equals(buyerStatus)) {
            this.checkStockButton.setVisible(false);
            this.packageAndDeliveryButton.setVisible(false);
            this.cancelButton.setVisible(true);
            SelectQuery selectQuery = new SelectQuery("ecommerce_vendor_order");
            selectQuery.addField("count(*)");
            selectQuery.addWhere("ecommerce_order_id = :ecommerce_order_id", this.ecommerceOrderId);
            selectQuery.addWhere("order_status = :order_status", "Check Stock");
            selectQuery.addWhere("vendor_status = :vendor_status", "In Stock");
            int inStockCount = getNamed().queryForObject(selectQuery.toSQL(), selectQuery.getParam(), int.class);
            this.shippingRequestButton.setVisible(inStockCount == vendorCount);
        } else if ("Shipping Request".equals(orderStatus) && "Progressing".equals(buyerStatus)) {
            this.checkStockButton.setVisible(false);
            this.shippingRequestButton.setVisible(false);
            this.cancelButton.setVisible(true);
            SelectQuery selectQuery = new SelectQuery("ecommerce_vendor_order");
            selectQuery.addField("count(*)");
            selectQuery.addWhere("ecommerce_order_id = :ecommerce_order_id", this.ecommerceOrderId);
            selectQuery.addWhere("order_status = :order_status", "Shipping Request");
            selectQuery.addWhere("vendor_status = :vendor_status", "Packaged & Shipped");
            int shippedCount = getNamed().queryForObject(selectQuery.toSQL(), selectQuery.getParam(), int.class);
            this.cancelButton.setVisible(shippedCount == 0);
            this.packageAndDeliveryButton.setVisible(shippedCount == vendorCount);
        } else if ("Packaged & Shipped".equals(orderStatus) && "Delivering".equals(buyerStatus)) {
            this.checkStockButton.setVisible(false);
            this.shippingRequestButton.setVisible(false);
            this.packageAndDeliveryButton.setVisible(false);
            this.cancelButton.setVisible(false);
        } else if ("Packaged & Shipped".equals(orderStatus) && "Received".equals(buyerStatus)) {
            this.checkStockButton.setVisible(false);
            this.shippingRequestButton.setVisible(false);
            this.packageAndDeliveryButton.setVisible(false);
            this.cancelButton.setVisible(false);
        } else if ("Canceled".equals(orderStatus)) {
            this.checkStockButton.setVisible(false);
            this.shippingRequestButton.setVisible(false);
            this.packageAndDeliveryButton.setVisible(false);
            this.cancelButton.setVisible(false);
        }

    }

    private void checkStockButtonOnClick(AjaxLink link, AjaxRequestTarget target) {

        EcommerceOrder ecommerceOrder = getJdbcTemplate().queryForObject("select * from ecommerce_order where ecommerce_order_id = ?", EcommerceOrder.class, this.ecommerceOrderId);

        if (!"New".equals(ecommerceOrder.getOrderStatus())) {
            PageParameters parameters = new PageParameters();
            parameters.add("ecommerceOrderId", this.ecommerceOrderId);
            setResponsePage(OrderReviewPage.class, parameters);
            return;
        }

        UpdateQuery updateQuery = null;
        updateQuery = new UpdateQuery("ecommerce_order");
        updateQuery.addValue("buyer_status = :buyer_status", "Progressing");
        updateQuery.addValue("order_status = :order_status", "Check Stock");
        updateQuery.addWhere("ecommerce_order_id = :ecommerce_order_id", this.ecommerceOrderId);
        getNamed().update(updateQuery.toSQL(), updateQuery.getParam());

        List<EcommerceOrderItem> orderItemRecords = getJdbcTemplate().queryForList("select * from ecommerce_order_item where ecommerce_order_id = ?", EcommerceOrderItem.class, this.ecommerceOrderId);

        Map<Long, List<EcommerceOrderItem>> vendorsOrderItems = Maps.newHashMap();
        for (EcommerceOrderItem orderItemRecord : orderItemRecords) {
            SelectQuery selectQuery = new SelectQuery("ecommerce_product");
            selectQuery.addWhere("ecommerce_product_id = :ecommerce_product_id", orderItemRecord.getEcommerceProductId());
            EcommerceProduct productRecord = getNamed().queryForObject(selectQuery.toSQL(), selectQuery.getParam(), EcommerceProduct.class);
            Long userId = productRecord.getPlatformUserId();
            if (!vendorsOrderItems.containsKey(userId)) {
                vendorsOrderItems.put(userId, Lists.newArrayList());
            }
            List<EcommerceOrderItem> vendorOrderItems = vendorsOrderItems.get(userId);
            vendorOrderItems.add(orderItemRecord);
        }

        InsertQuery insertQuery = null;
        for (Map.Entry<Long, List<EcommerceOrderItem>> sellerItems : vendorsOrderItems.entrySet()) {
            Long sellerId = sellerItems.getKey();
            double total = 0;
            Long vendorOrderId = Platform.randomUUIDLong("ecommerce_vendor_order");
            Map<String, Object> params = Maps.newHashMap();
            insertQuery = new InsertQuery("ecommerce_vendor_order");
            insertQuery.addValue("ecommerce_vendor_order_id = :ecommerce_vendor_order_id", vendorOrderId);
            insertQuery.addValue("ecommerce_order_id = :ecommerce_order_id", this.ecommerceOrderId);
            insertQuery.addValue("date_created = :date_created", new Date());
            insertQuery.addValue("order_status = :order_status", "Check Stock");
            insertQuery.addValue("vendor_status = :vendor_status", "New");
            insertQuery.addValue("platform_user_id = :user_id", sellerId);
            insertQuery.addValue("total = :total", total);
            getNamed().update(insertQuery.toSQL(), insertQuery.getParam());
            for (EcommerceOrderItem item : sellerItems.getValue()) {
                insertQuery = new InsertQuery("ecommerce_vendor_order_item");
                insertQuery.addValue("ecommerce_vendor_order_item_id = :ecommerce_vendor_order_item_id", Platform.randomUUIDLong("ecommerce_vendor_order_item"));
                insertQuery.addValue("ecommerce_vendor_order_id = :ecommerce_vendor_order_id", vendorOrderId);
                insertQuery.addValue("ecommerce_category_id = :ecommerce_category_id", item.getEcommerceCategoryId());
                insertQuery.addValue("quantity = :quantity", item.getQuantity());
                insertQuery.addValue("total_price = :total_price", item.getTotalPrice());
                insertQuery.addValue("ecommerce_product_id = :ecommerce_product_id", item.getEcommerceProductId());
                insertQuery.addValue("ecommerce_product_variant_id = :ecommerce_product_variant_id", item.getEcommerceProductVariantId());
                insertQuery.addValue("product_url = :product_url", item.getProductUrl());
                insertQuery.addValue("product_name = :product_name", item.getProductName());
                insertQuery.addValue("product_price = :product_price", item.getProductPrice());
                insertQuery.addValue("product_reference = :product_reference", item.getProductReference());
                insertQuery.addValue("product_discount_price = :product_discount_price", item.getProductDiscountPrice());
                insertQuery.addValue("product_description = :product_description", item.getProductDescription());
                insertQuery.addValue("product_main_image = :product_main_image", item.getProductMainImage());
                insertQuery.addValue("product_main_image_platform_file_id = :product_main_image_file_id", item.getProductMainImagePlatformFileId());
                insertQuery.addValue("variant_reference = :variant_reference", item.getVariantReference());
                insertQuery.addValue("ecommerce_color_id = :ecommerce_color_id", item.getEcommerceColorId());
                insertQuery.addValue("color_value = :color_value", item.getColorValue());
                insertQuery.addValue("color_code = :color_code", item.getColorCode());
                insertQuery.addValue("color_img = :color_img", item.getColorImg());
                insertQuery.addValue("color_reference = :color_reference", item.getColorReference());
                insertQuery.addValue("color_img_platform_file_id = :color_img_file_id", item.getColorImgPlatformFileId());
                insertQuery.addValue("ecommerce_size_id = :ecommerce_size_id", item.getEcommerceSizeId());
                insertQuery.addValue("size_value = :size_value", item.getSizeValue());
                insertQuery.addValue("size_reference = :size_reference", item.getSizeReference());
                getNamed().update(insertQuery.toSQL(), insertQuery.getParam());
                total = (item.getTotalPrice() == null ? 0d : item.getTotalPrice()) + total;
            }
            updateQuery = new UpdateQuery("ecommerce_vendor_order");
            updateQuery.addValue("total = :total", total);
            updateQuery.addWhere("ecommerce_vendor_order_id = :ecommerce_vendor_order_id", vendorOrderId);
            getNamed().update(updateQuery.toSQL(), updateQuery.getParam());
        }

        setResponsePage(OrderBrowsePage.class);
    }

    private void shippingRequestButtonOnClick(AjaxLink ajaxLink, AjaxRequestTarget target) {
        SelectQuery selectQuery = null;
        selectQuery = new SelectQuery("ecommerce_order");
        selectQuery.addWhere("ecommerce_order_id = :ecommerce_order_id", this.ecommerceOrderId);
        EcommerceOrder ecommerceOrder = getNamed().queryForObject(selectQuery.toSQL(), selectQuery.getParam(), EcommerceOrder.class);

        String orderStatus = ecommerceOrder.getOrderStatus();

        selectQuery = new SelectQuery("ecommerce_vendor_order");
        selectQuery.addField("count(*)");
        selectQuery.addWhere("ecommerce_order_id = :ecommerce_order_id", this.ecommerceOrderId);
        selectQuery.addWhere("vendor_status != :vendor_status", "In Stock");
        int vendorOrders = getNamed().queryForObject(selectQuery.toSQL(), selectQuery.getParam(), int.class);

        if (!"Check Stock".equals(orderStatus) || vendorOrders > 0) {
            PageParameters parameters = new PageParameters();
            parameters.add("ecommerceOrderId", this.ecommerceOrderId);
            setResponsePage(OrderReviewPage.class, parameters);
            return;
        }

        UpdateQuery updateQuery = null;
        updateQuery = new UpdateQuery("ecommerce_order");
        updateQuery.addValue("order_status = :order_status", "Shipping Request");
        updateQuery.addWhere("ecommerce_order_id = :ecommerce_order_id", this.ecommerceOrderId);
        getNamed().update(updateQuery.toSQL(), updateQuery.getParam());

        updateQuery = new UpdateQuery("ecommerce_vendor_order");
        updateQuery.addValue("order_status = :order_status", "Shipping Request");
        updateQuery.addWhere("ecommerce_order_id = :ecommerce_order_id", this.ecommerceOrderId);
        getNamed().update(updateQuery.toSQL(), updateQuery.getParam());

        setResponsePage(OrderBrowsePage.class);
    }

    private void packageAndDeliveryButtonOnClick(AjaxLink ajaxLink, AjaxRequestTarget target) {

        SelectQuery selectQuery = null;
        selectQuery = new SelectQuery("ecommerce_order");
        selectQuery.addWhere("ecommerce_order_id = :ecommerce_order_id", this.ecommerceOrderId);

        EcommerceOrder orderRecord = getNamed().queryForObject(selectQuery.toSQL(), selectQuery.getParam(), EcommerceOrder.class);

        String orderStatus = orderRecord.getOrderStatus();

        selectQuery = new SelectQuery("ecommerce_vendor_order");
        selectQuery.addField("count(*)");
        selectQuery.addWhere("ecommerce_order_id = :ecommerce_order_id", this.ecommerceOrderId);
        selectQuery.addWhere("vendor_status != :vendor_status", "Packaged & Shipped");

        int vendorOrders = getNamed().queryForObject(selectQuery.toSQL(), selectQuery.getParam(), int.class);

        if ((!"Shipping Request".equals(orderStatus) && !"Packaged & Shipped".equals(orderStatus)) || vendorOrders > 0) {
            PageParameters parameters = new PageParameters();
            parameters.add("ecommerceOrderId", this.ecommerceOrderId);
            setResponsePage(OrderReviewPage.class, parameters);
            return;
        }

        UpdateQuery updateQuery = null;
        updateQuery = new UpdateQuery("ecommerce_order");
        updateQuery.addValue("buyer_status = :buyer_status", "Delivering");
        updateQuery.addValue("order_status = :order_status", "Packaged & Shipped");
        updateQuery.addWhere("ecommerce_order_id = :ecommerce_order_id", this.ecommerceOrderId);
        getNamed().update(updateQuery.toSQL(), updateQuery.getParam());

        updateQuery = new UpdateQuery("ecommerce_vendor_order");
        updateQuery.addValue("order_status = :order_status", "Received");
        updateQuery.addWhere("ecommerce_order_id = :ecommerce_order_id", this.ecommerceOrderId);
        getNamed().update(updateQuery.toSQL(), updateQuery.getParam());

        setResponsePage(OrderBrowsePage.class);

    }

    private void cancelButtonOnClick(AjaxLink ajaxLink, AjaxRequestTarget target) {
        UpdateQuery updateQuery = null;
        updateQuery = new UpdateQuery("ecommerce_order");
        updateQuery.addValue("buyer_status = :buyer_status", "Canceled");
        updateQuery.addValue("order_status = :order_status", "Canceled");
        updateQuery.addWhere("ecommerce_order_id = :ecommerce_order_id", this.ecommerceOrderId);
        getNamed().update(updateQuery.toSQL(), updateQuery.getParam());

        updateQuery = new UpdateQuery("ecommerce_vendor_order");
        updateQuery.addValue("buyer_status = :buyer_status", "Canceled");
        updateQuery.addValue("order_status = :order_status", "Canceled");
        updateQuery.addWhere("ecommerce_order_id = :ecommerce_order_id", this.ecommerceOrderId);
        getNamed().update(updateQuery.toSQL(), updateQuery.getParam());

        setResponsePage(OrderBrowsePage.class);
    }

}
