package com.angkorteam.ecommerce.page.order;

import com.angkorteam.ecommerce.model.EcommerceVendorOrder;
import com.angkorteam.ecommerce.model.EcommerceVendorOrderItem;
import com.angkorteam.framework.wicket.ajax.markup.html.AjaxLink;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.jdbc.UpdateQuery;
import com.angkorteam.platform.Platform;
import com.angkorteam.platform.page.MBaaSPage;
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
import java.util.List;

/**
 * Created by socheatkhauv on 26/1/17.
 */
public class VendorOrderReviewPage extends MBaaSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(VendorOrderReviewPage.class);

    private String ecommerceVendorOrderId;

    private String orderNo;
    private Label orderNoLabel;

    private String createdDate;
    private Label createdDateLabel;

    private String totalAmount;
    private Label totalAmountLabel;

    private BookmarkablePageLink<Void> closeButton;
    private AjaxLink<Void> confirmStockButton;
    private AjaxLink<Void> packageAndDeliveryButton;
    private AjaxLink<Void> cancelButton;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        DateFormat datetimeFormat = new SimpleDateFormat(Platform.getSetting("datetime_format"));
        DecimalFormat priceFormat = new DecimalFormat(Platform.getSetting("price_format"));

        this.ecommerceVendorOrderId = getPageParameters().get("ecommerceVendorOrderId").toString("");

        SelectQuery selectQuery = null;
        selectQuery = new SelectQuery("ecommerce_vendor_order");
        selectQuery.addWhere("ecommerce_vendor_order_id = :ecommerce_vendor_order_id", this.ecommerceVendorOrderId);
        EcommerceVendorOrder vendorOrderRecord = getNamed().queryForObject(selectQuery.toSQL(), selectQuery.getParam(), EcommerceVendorOrder.class);

        this.orderNo = "Invoice " + StringUtils.upperCase(this.ecommerceVendorOrderId);
        this.orderNoLabel = new Label("orderNoLabel", new PropertyModel<>(this, "orderNo"));
        layout.add(this.orderNoLabel);

        this.createdDate = datetimeFormat.format(vendorOrderRecord.getDateCreated());
        this.createdDateLabel = new Label("createdDateLabel", new PropertyModel<>(this, "createdDate"));
        layout.add(this.createdDateLabel);

        this.totalAmount = priceFormat.format(vendorOrderRecord.getTotal());
        this.totalAmountLabel = new Label("totalAmountLabel", new PropertyModel<>(this, "totalAmount"));
        layout.add(this.totalAmountLabel);

        RepeatingView view = new RepeatingView("items");
        layout.add(view);

        selectQuery = new SelectQuery("ecommerce_vendor_order_item");
        selectQuery.addWhere("ecommerce_vendor_order_id = :ecommerce_vendor_order_id", this.ecommerceVendorOrderId);
        List<EcommerceVendorOrderItem> items = getNamed().queryForList(selectQuery.toSQL(), selectQuery.getParam(), EcommerceVendorOrderItem.class);

        if (items != null && !items.isEmpty()) {
            for (EcommerceVendorOrderItem item : items) {
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

        this.closeButton = new BookmarkablePageLink<>("closeButton", VendorOrderBrowsePage.class);
        layout.add(this.closeButton);

        this.confirmStockButton = new AjaxLink<>("confirmStockButton");
        this.confirmStockButton.setOnClick(this::confirmStockButtonOnClick);
        layout.add(this.confirmStockButton);

        this.packageAndDeliveryButton = new AjaxLink<>("packageAndDeliveryButton");
        this.packageAndDeliveryButton.setOnClick(this::packageAndDeliveryButtonOnClick);
        layout.add(this.packageAndDeliveryButton);

        this.cancelButton = new AjaxLink<>("cancelButton");
        this.cancelButton.setOnClick(this::cancelButtonOnClick);
        layout.add(this.cancelButton);

        this.confirmStockButton.setVisible(false);
        this.packageAndDeliveryButton.setVisible(false);
        this.cancelButton.setVisible(false);

        String orderStatus = vendorOrderRecord.getOrderStatus();
        String vendorStatus = vendorOrderRecord.getVendorStatus();

        if ("Check Stock".equals(orderStatus) && "New".equals(vendorStatus)) {
            this.confirmStockButton.setVisible(true);
            this.packageAndDeliveryButton.setVisible(false);
            this.cancelButton.setVisible(true);
        } else if ("Check Stock".equals(orderStatus) && "In Stock".equals(vendorStatus)) {
            this.confirmStockButton.setVisible(false);
            this.packageAndDeliveryButton.setVisible(false);
            this.cancelButton.setVisible(true);
        } else if ("Shipping Request".equals(orderStatus) && "In Stock".equals(vendorStatus)) {
            this.confirmStockButton.setVisible(false);
            this.packageAndDeliveryButton.setVisible(true);
            this.cancelButton.setVisible(false);
        } else if ("Shipping Request".equals(orderStatus) && "Packaged & Shipped".equals(vendorStatus)) {
            this.confirmStockButton.setVisible(false);
            this.packageAndDeliveryButton.setVisible(false);
            this.cancelButton.setVisible(false);
        } else if ("Closed".equals(orderStatus)) {
            this.confirmStockButton.setVisible(false);
            this.packageAndDeliveryButton.setVisible(false);
            this.cancelButton.setVisible(false);
        } else if ("Canceled".equals(vendorStatus)) {
            this.confirmStockButton.setVisible(false);
            this.packageAndDeliveryButton.setVisible(false);
            this.cancelButton.setVisible(false);
        }

    }

    private void confirmStockButtonOnClick(AjaxLink ajaxLink, AjaxRequestTarget target) {
        SelectQuery selectQuery = new SelectQuery("ecommerce_vendor_order");
        selectQuery.addWhere("ecommerce_vendor_order_id = :ecommerce_vendor_order_id", this.ecommerceVendorOrderId);
        EcommerceVendorOrder vendorOrderRecord = getNamed().queryForObject(selectQuery.toSQL(), selectQuery.getParam(), EcommerceVendorOrder.class);
        String vendorOrderStatus = vendorOrderRecord.getVendorStatus();
        if (!"New".equals(vendorOrderStatus)) {
            PageParameters parameters = new PageParameters();
            parameters.add("vendorOrderId", this.ecommerceVendorOrderId);
            setResponsePage(VendorOrderReviewPage.class, parameters);
            return;
        }

        UpdateQuery updateQuery = new UpdateQuery("ecommerce_vendor_order");
        updateQuery.addValue("vendor_status = :vendor_status", "In Stock");
        updateQuery.addWhere("ecommerce_vendor_order_id = :ecommerce_vendor_order_id", this.ecommerceVendorOrderId);
        getNamed().update(updateQuery.toSQL(), updateQuery.getParam());

        setResponsePage(VendorOrderBrowsePage.class);
    }

    private void packageAndDeliveryButtonOnClick(AjaxLink ajax, AjaxRequestTarget target) {
        SelectQuery selectQuery = new SelectQuery("ecommerce_vendor_order");
        selectQuery.addWhere("ecommerce_vendor_order_id = :ecommerce_vendor_order_id", this.ecommerceVendorOrderId);
        EcommerceVendorOrder vendorOrderRecord = getNamed().queryForObject(selectQuery.toSQL(), selectQuery.getParam(), EcommerceVendorOrder.class);
        String orderStatus = vendorOrderRecord.getOrderStatus();
        if (!"Shipping Request".equals(orderStatus)) {
            PageParameters parameters = new PageParameters();
            parameters.add("ecommerceVendorOrderId", this.ecommerceVendorOrderId);
            setResponsePage(VendorOrderReviewPage.class, parameters);
            return;
        }

        UpdateQuery updateQuery = new UpdateQuery("ecommerce_vendor_order");
        updateQuery.addValue("vendor_status = :vendor_status", "Packaged & Shipped");
        updateQuery.addWhere("ecommerce_vendor_order_id = :ecommerce_vendor_order_id", this.ecommerceVendorOrderId);
        getNamed().update(updateQuery.toSQL(), updateQuery.getParam());

        setResponsePage(VendorOrderBrowsePage.class);
    }


    private void cancelButtonOnClick(AjaxLink ajaxLink, AjaxRequestTarget target) {
        SelectQuery selectQuery = new SelectQuery("ecommerce_vendor_order");
        selectQuery.addWhere("ecommerce_vendor_order_id = :ecommerce_vendor_order_id", this.ecommerceVendorOrderId);
        EcommerceVendorOrder vendorOrderRecord = getNamed().queryForObject(selectQuery.toSQL(), selectQuery.getParam(), EcommerceVendorOrder.class);

        Long orderId = vendorOrderRecord.getEcommerceOrderId();

        UpdateQuery updateQuery = null;

        updateQuery = new UpdateQuery("ecommerce_order");
        updateQuery.addValue("buyer_status = :buyer_status", "Canceled");
        updateQuery.addValue("order_status = :order_status", "Canceled");
        updateQuery.addWhere("ecommerce_order_id = :ecommerce_order_id", orderId);
        getNamed().update(updateQuery.toSQL(), updateQuery.getParam());

        updateQuery = new UpdateQuery("ecommerce_vendor_order");
        updateQuery.addValue("vendor_status = :vendor_status", "Canceled");
        updateQuery.addValue("order_status = :order_status", "Canceled");
        updateQuery.addWhere("ecommerce_order_id = :ecommerce_order_id", orderId);
        getNamed().update(updateQuery.toSQL(), updateQuery.getParam());

        setResponsePage(VendorOrderBrowsePage.class);
    }

}
