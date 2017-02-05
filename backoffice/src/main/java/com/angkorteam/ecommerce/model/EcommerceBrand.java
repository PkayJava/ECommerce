package com.angkorteam.ecommerce.model;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class EcommerceBrand implements Serializable {

    private Long ecommerceBrandId;
    private String name;
    private Integer order;

    public Long getEcommerceBrandId() {
        return ecommerceBrandId;
    }

    public void setEcommerceBrandId(Long ecommerceBrandId) {
        this.ecommerceBrandId = ecommerceBrandId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EcommerceBrand that = (EcommerceBrand) o;

        if (ecommerceBrandId != null ? !ecommerceBrandId.equals(that.ecommerceBrandId) : that.ecommerceBrandId != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return order != null ? order.equals(that.order) : that.order == null;
    }

    @Override
    public int hashCode() {
        int result = ecommerceBrandId != null ? ecommerceBrandId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (order != null ? order.hashCode() : 0);
        return result;
    }
}
