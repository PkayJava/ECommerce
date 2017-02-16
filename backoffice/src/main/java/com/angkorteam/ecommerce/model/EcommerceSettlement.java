package com.angkorteam.ecommerce.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by socheatkhauv on 15/2/17.
 */
public class EcommerceSettlement implements Serializable {

    public static final String STATUS_CAPTURED = "Captured";

    public static final String STATUS_FAILED = "Failed";

    public static final String STATUS_REFUNDED = "Refunded";

    public static final String STATUS_SETTLED = "Settled";

    private Long ecommerceSettlementId;

    private Long ecommerceOrderId;

    private Long ecommercePaymentId;

    private Double amount;

    private String status;

    private String paymentType;

    private String serverParam1;

    private String serverParam2;

    private String serverParam3;

    private String serverParam4;

    private String serverParam5;

    private String transactionParam1;

    private String transactionParam2;

    private String transactionParam3;

    private String transactionParam4;

    private String transactionParam5;

    private Date dateCreated;

    public Long getEcommerceSettlementId() {
        return ecommerceSettlementId;
    }

    public void setEcommerceSettlementId(Long ecommerceSettlementId) {
        this.ecommerceSettlementId = ecommerceSettlementId;
    }

    public Long getEcommerceOrderId() {
        return ecommerceOrderId;
    }

    public void setEcommerceOrderId(Long ecommerceOrderId) {
        this.ecommerceOrderId = ecommerceOrderId;
    }

    public Long getEcommercePaymentId() {
        return ecommercePaymentId;
    }

    public void setEcommercePaymentId(Long ecommercePaymentId) {
        this.ecommercePaymentId = ecommercePaymentId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getServerParam1() {
        return serverParam1;
    }

    public void setServerParam1(String serverParam1) {
        this.serverParam1 = serverParam1;
    }

    public String getServerParam2() {
        return serverParam2;
    }

    public void setServerParam2(String serverParam2) {
        this.serverParam2 = serverParam2;
    }

    public String getServerParam3() {
        return serverParam3;
    }

    public void setServerParam3(String serverParam3) {
        this.serverParam3 = serverParam3;
    }

    public String getServerParam4() {
        return serverParam4;
    }

    public void setServerParam4(String serverParam4) {
        this.serverParam4 = serverParam4;
    }

    public String getServerParam5() {
        return serverParam5;
    }

    public void setServerParam5(String serverParam5) {
        this.serverParam5 = serverParam5;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getTransactionParam1() {
        return transactionParam1;
    }

    public void setTransactionParam1(String transactionParam1) {
        this.transactionParam1 = transactionParam1;
    }

    public String getTransactionParam2() {
        return transactionParam2;
    }

    public void setTransactionParam2(String transactionParam2) {
        this.transactionParam2 = transactionParam2;
    }

    public String getTransactionParam3() {
        return transactionParam3;
    }

    public void setTransactionParam3(String transactionParam3) {
        this.transactionParam3 = transactionParam3;
    }

    public String getTransactionParam4() {
        return transactionParam4;
    }

    public void setTransactionParam4(String transactionParam4) {
        this.transactionParam4 = transactionParam4;
    }

    public String getTransactionParam5() {
        return transactionParam5;
    }

    public void setTransactionParam5(String transactionParam5) {
        this.transactionParam5 = transactionParam5;
    }
}
