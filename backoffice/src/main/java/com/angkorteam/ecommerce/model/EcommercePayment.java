package com.angkorteam.ecommerce.model;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class EcommercePayment implements Serializable {

    private Long ecommercePaymentId;
    private String name;
    private String description;
    private Double price;
    private String type;
    private String clientParam1;
    private String clientParam2;
    private String clientParam3;
    private String clientParam4;
    private String clientParam5;
    private String serverParam1;
    private String serverParam2;
    private String serverParam3;
    private String serverParam4;
    private String serverParam5;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClientParam1() {
        return clientParam1;
    }

    public void setClientParam1(String clientParam1) {
        this.clientParam1 = clientParam1;
    }

    public String getClientParam2() {
        return clientParam2;
    }

    public void setClientParam2(String clientParam2) {
        this.clientParam2 = clientParam2;
    }

    public String getClientParam3() {
        return clientParam3;
    }

    public void setClientParam3(String clientParam3) {
        this.clientParam3 = clientParam3;
    }

    public String getClientParam4() {
        return clientParam4;
    }

    public void setClientParam4(String clientParam4) {
        this.clientParam4 = clientParam4;
    }

    public String getClientParam5() {
        return clientParam5;
    }

    public void setClientParam5(String clientParam5) {
        this.clientParam5 = clientParam5;
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

    public Long getEcommercePaymentId() {
        return ecommercePaymentId;
    }

    public void setEcommercePaymentId(Long ecommercePaymentId) {
        this.ecommercePaymentId = ecommercePaymentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
