package com.angkorteam.ecommerce.page.order;

import com.angkorteam.ecommerce.model.EcommerceOrder;
import com.angkorteam.ecommerce.model.EcommerceOrderItem;
import com.angkorteam.framework.extension.wicket.ajax.markup.html.AjaxLink;
import com.angkorteam.framework.jdbc.UpdateQuery;
import com.angkorteam.platform.page.MBaaSPage;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by socheatkhauv on 26/1/17.
 */
public class CustomerOrderReviewPage extends MBaaSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerOrderReviewPage.class);

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

    private String grandTotalAmount;
    private Label grandTotalAmountLabel;

    private String shipping;
    private Label shippingLabel;

    private String totalAmount;
    private Label totalAmountLabel;

    private String note;
    private Label noteLabel;

    private String payment;
    private Label paymentLabel;

    private BookmarkablePageLink<Void> closeButton;
    private AjaxLink<Void> confirmReceiveButton;
    private AjaxLink<Void> openDisputeButton;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        DateFormat datetimeFormat = new SimpleDateFormat(getJdbcTemplate().queryForObject("select `value` from setting where `key` = ?", String.class, "datetime_format"));
        DecimalFormat priceFormat = new DecimalFormat(getJdbcTemplate().queryForObject("select `value` from setting where `key` = ?", String.class, "price_format"));

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

        this.closeButton = new BookmarkablePageLink<>("closeButton", CustomerOrderBrowsePage.class);
        layout.add(this.closeButton);

        this.confirmReceiveButton = new AjaxLink<>("confirmReceiveButton");
        this.confirmReceiveButton.setOnClick(this::confirmReceiveButtonOnClick);
        layout.add(this.confirmReceiveButton);

        this.openDisputeButton = new AjaxLink<>("openDisputeButton");
        this.openDisputeButton.setOnClick(this::openDisputeButtonOnClick);
        layout.add(this.openDisputeButton);

        this.confirmReceiveButton.setVisible(false);
        this.openDisputeButton.setVisible(false);

        String orderStatus = orderRecord.getOrderStatus();
        String buyerStatus = orderRecord.getBuyerStatus();

        if ("Delivering".equals(buyerStatus) && "Packaged & Shipped".equals(orderStatus)) {
            this.openDisputeButton.setVisible(true);
            this.confirmReceiveButton.setVisible(true);
        } else if ("Received".equals(buyerStatus) && "Packaged & Shipped".equals(orderStatus)) {
            this.openDisputeButton.setVisible(false);
            this.confirmReceiveButton.setVisible(false);
        } else if ("Open Disputed".equals(buyerStatus) && "Packaged & Shipped".equals(orderStatus)) {
            this.openDisputeButton.setVisible(false);
            this.confirmReceiveButton.setVisible(false);
        } else if ("Canceled".equals(buyerStatus)) {
            this.openDisputeButton.setVisible(false);
            this.confirmReceiveButton.setVisible(false);
        }

    }

    private void confirmReceiveButtonOnClick(AjaxLink ajaxLink, AjaxRequestTarget target) {
        UpdateQuery updateQuery = null;

        updateQuery = new UpdateQuery("ecommerce_order");
        updateQuery.addValue("buyer_status = :buyer_status", "Received");
        updateQuery.addWhere("ecommerce_order_id = :ecommerce_order_id", this.ecommerceOrderId);
        getNamed().update(updateQuery.toSQL(), updateQuery.getParam());

        updateQuery = new UpdateQuery("ecommerce_vendor_order");
        updateQuery.addValue("order_status = :order_status", "Closed");
        updateQuery.addWhere("ecommerce_order_id = :ecommerce_order_id", this.ecommerceOrderId);
        getNamed().update(updateQuery.toSQL(), updateQuery.getParam());

        setResponsePage(CustomerOrderBrowsePage.class);
    }

    private void openDisputeButtonOnClick(AjaxLink ajaxLink, AjaxRequestTarget target) {
        UpdateQuery updateQuery = new UpdateQuery("ecommerce_order");
        updateQuery.addValue("buyer_status = :buyer_status", "Open Disputed");
        updateQuery.addWhere("ecommerce_order_id = :ecommerce_order_id", this.ecommerceOrderId);
        getNamed().update(updateQuery.toSQL(), updateQuery.getParam());
        setResponsePage(CustomerOrderBrowsePage.class);
    }

}