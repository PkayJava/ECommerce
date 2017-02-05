package com.angkorteam.ecommerce.model;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class EcommerceCategory implements Serializable {

    private Long ecommerceCategoryId;
    private Long parentEcommerceCategoryId;
    private String name;
    private String type;
    private Integer order;
    private String code;
    private String fullCode;
    private String path;
    private String parentPath;

    public Long getEcommerceCategoryId() {
        return ecommerceCategoryId;
    }

    public void setEcommerceCategoryId(Long ecommerceCategoryId) {
        this.ecommerceCategoryId = ecommerceCategoryId;
    }

    public Long getParentEcommerceCategoryId() {
        return parentEcommerceCategoryId;
    }

    public void setParentEcommerceCategoryId(Long parentEcommerceCategoryId) {
        this.parentEcommerceCategoryId = parentEcommerceCategoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFullCode() {
        return fullCode;
    }

    public void setFullCode(String fullCode) {
        this.fullCode = fullCode;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getParentPath() {
        return parentPath;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EcommerceCategory that = (EcommerceCategory) o;

        if (ecommerceCategoryId != null ? !ecommerceCategoryId.equals(that.ecommerceCategoryId) : that.ecommerceCategoryId != null)
            return false;
        if (parentEcommerceCategoryId != null ? !parentEcommerceCategoryId.equals(that.parentEcommerceCategoryId) : that.parentEcommerceCategoryId != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (order != null ? !order.equals(that.order) : that.order != null) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (fullCode != null ? !fullCode.equals(that.fullCode) : that.fullCode != null) return false;
        if (path != null ? !path.equals(that.path) : that.path != null) return false;
        return parentPath != null ? parentPath.equals(that.parentPath) : that.parentPath == null;
    }

    @Override
    public int hashCode() {
        int result = ecommerceCategoryId != null ? ecommerceCategoryId.hashCode() : 0;
        result = 31 * result + (parentEcommerceCategoryId != null ? parentEcommerceCategoryId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (order != null ? order.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (fullCode != null ? fullCode.hashCode() : 0);
        result = 31 * result + (path != null ? path.hashCode() : 0);
        result = 31 * result + (parentPath != null ? parentPath.hashCode() : 0);
        return result;
    }
}
