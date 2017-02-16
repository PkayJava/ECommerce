package com.angkorteam.ecommerce.page.settlement;

import com.angkorteam.ecommerce.mobile.delivery.Payment;
import com.angkorteam.ecommerce.model.EcommerceSettlement;
import com.angkorteam.framework.extension.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.extension.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.extension.wicket.extensions.markup.html.repeater.data.table.filter.*;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
import com.angkorteam.platform.Platform;
import com.angkorteam.platform.page.MBaaSPage;
import com.angkorteam.platform.provider.JdbcProvider;
import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Environment;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.google.common.collect.Lists;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by socheatkhauv on 15/2/17.
 */
public class SettlementBrowsePage extends MBaaSPage {

    private DataTable<Map<String, Object>, String> dataTable;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        JdbcProvider provider = new JdbcProvider("ecommerce_settlement");
        provider.boardField("ecommerce_settlement_id", "ecommerceSettlementId", Long.class);
        provider.boardField("ecommerce_order_id", "ecommerceOrderId", Long.class);
        provider.boardField("payment_type", "paymentType", String.class);
        provider.boardField("amount", "amount", Double.class);
        provider.boardField("status", "status", String.class);
        provider.boardField("date_created", "dateCreated", Calendar.Date);
        provider.setSort("dateCreated", SortOrder.DESCENDING);

        FilterForm<Map<String, String>> filterForm = new FilterForm<>("filter-form", provider);
        layout.add(filterForm);

        List<IColumn<Map<String, Object>, String>> columns = new ArrayList<>();
        columns.add(new TextFilterColumn(provider, ItemClass.Long, Model.of("ID"), "ecommerceSettlementId"));
        columns.add(new TextFilterColumn(provider, ItemClass.Long, Model.of("Order"), "ecommerceOrderId"));
        columns.add(new TextFilterColumn(provider, ItemClass.String, Model.of("Payment"), "paymentType"));
        columns.add(new TextFilterColumn(provider, ItemClass.Double, Model.of("Amount"), "amount"));
        columns.add(new TextFilterColumn(provider, ItemClass.Boolean, Model.of("Status"), "status"));
        columns.add(new TextFilterColumn(provider, ItemClass.Boolean, Model.of("When"), "dateCreated"));
        columns.add(new ActionFilterColumn(Model.of("action"), this::actions, this::itemClick));

        this.dataTable = new DefaultDataTable<>("table", columns, provider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, filterForm));
        filterForm.add(this.dataTable);

        BookmarkablePageLink<Void> refreshLink = new BookmarkablePageLink<>("refreshLink", SettlementBrowsePage.class);
        layout.add(refreshLink);
    }

    private List<ActionItem> actions(String name, Map<String, Object> object) {
        List<ActionItem> actionItems = Lists.newArrayList();
        String status = (String) object.get("status");
        if (EcommerceSettlement.STATUS_CAPTURED.equalsIgnoreCase(status)) {
            actionItems.add(new ActionItem("Refund", Model.of("Refund"), ItemCss.DANGER));
            actionItems.add(new ActionItem("Settlement", Model.of("Settlement"), ItemCss.DANGER));
        }
        return actionItems;
    }

    private void itemClick(String link, Map<String, Object> object, AjaxRequestTarget target) {
        Long ecommerceSettlementId = (Long) object.get("ecommerceSettlementId");
        NamedParameterJdbcTemplate named = Platform.getBean(NamedParameterJdbcTemplate.class);
        JdbcTemplate jdbcTemplate = Platform.getBean(JdbcTemplate.class);
        SelectQuery selectQuery = new SelectQuery("ecommerce_settlement");
        selectQuery.addWhere("ecommerce_settlement_id = :ecommerce_settlement_id", ecommerceSettlementId);
        EcommerceSettlement ecommerceSettlement = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), EcommerceSettlement.class);
        if ("Refund".equalsIgnoreCase(link)) {
            if (Payment.TYPE_PAYPAL.equalsIgnoreCase(ecommerceSettlement.getPaymentType())) {
                BraintreeGateway gateway = new BraintreeGateway(ecommerceSettlement.getServerParam1());
                Result<Transaction> result = gateway.transaction().refund(ecommerceSettlement.getTransactionParam1());
                if (result.isSuccess()) {
                    jdbcTemplate.update("update ecommerce_settlement set status = ? where ecommerce_settlement_id = ?", EcommerceSettlement.STATUS_REFUNDED, ecommerceSettlementId);
                }
            } else if (Payment.TYPE_BRAIN_TREE.equalsIgnoreCase(ecommerceSettlement.getPaymentType())) {
                Environment environment = null;
                String merchantId = ecommerceSettlement.getServerParam2();
                String publicKey = ecommerceSettlement.getServerParam3();
                String privateKey = ecommerceSettlement.getServerParam4();
                if ("DEVELOPMENT".equalsIgnoreCase(ecommerceSettlement.getServerParam1())) {
                    environment = Environment.DEVELOPMENT;
                } else if ("PRODUCTION".equalsIgnoreCase(ecommerceSettlement.getServerParam1())) {
                    environment = Environment.PRODUCTION;
                } else if ("QA".equalsIgnoreCase(ecommerceSettlement.getServerParam1())) {
                    environment = Environment.QA;
                } else if ("SANDBOX".equalsIgnoreCase(ecommerceSettlement.getServerParam1())) {
                    environment = Environment.SANDBOX;
                }
                BraintreeGateway gateway = new BraintreeGateway(environment, merchantId, publicKey, privateKey);
                Result<Transaction> result = gateway.transaction().refund(ecommerceSettlement.getTransactionParam1());
                if (result.isSuccess()) {
                    jdbcTemplate.update("update ecommerce_settlement set status = ? where ecommerce_settlement_id = ?", EcommerceSettlement.STATUS_REFUNDED, ecommerceSettlementId);
                }
            }
        } else if ("Settlement".equalsIgnoreCase(link)) {
            if (Payment.TYPE_PAYPAL.equalsIgnoreCase(ecommerceSettlement.getPaymentType())) {
                BraintreeGateway gateway = new BraintreeGateway(ecommerceSettlement.getServerParam1());
                Result<Transaction> result = gateway.transaction().submitForSettlement(ecommerceSettlement.getTransactionParam1());
                if (result.isSuccess()) {
                    jdbcTemplate.update("update ecommerce_settlement set status = ? where ecommerce_settlement_id = ?", EcommerceSettlement.STATUS_SETTLED, ecommerceSettlementId);
                }
            } else if (Payment.TYPE_BRAIN_TREE.equalsIgnoreCase(ecommerceSettlement.getPaymentType())) {
                Environment environment = null;
                String merchantId = ecommerceSettlement.getServerParam2();
                String publicKey = ecommerceSettlement.getServerParam3();
                String privateKey = ecommerceSettlement.getServerParam4();
                if ("DEVELOPMENT".equalsIgnoreCase(ecommerceSettlement.getServerParam1())) {
                    environment = Environment.DEVELOPMENT;
                } else if ("PRODUCTION".equalsIgnoreCase(ecommerceSettlement.getServerParam1())) {
                    environment = Environment.PRODUCTION;
                } else if ("QA".equalsIgnoreCase(ecommerceSettlement.getServerParam1())) {
                    environment = Environment.QA;
                } else if ("SANDBOX".equalsIgnoreCase(ecommerceSettlement.getServerParam1())) {
                    environment = Environment.SANDBOX;
                }
                BraintreeGateway gateway = new BraintreeGateway(environment, merchantId, publicKey, privateKey);
                Result<Transaction> result = gateway.transaction().submitForSettlement(ecommerceSettlement.getTransactionParam1());
                if (result.isSuccess()) {
                    jdbcTemplate.update("update ecommerce_settlement set status = ? where ecommerce_settlement_id = ?", EcommerceSettlement.STATUS_SETTLED, ecommerceSettlementId);
                }
            }
        }
        target.add(this.dataTable);
    }

}
