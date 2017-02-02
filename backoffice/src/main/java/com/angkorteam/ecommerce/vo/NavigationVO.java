package com.angkorteam.ecommerce.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class NavigationVO implements Serializable {

    @Expose
    @SerializedName("id")
    private Long id;

    private Long navigationId;

    @Expose
    @SerializedName("original_id")
    private Long originalId;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("weight")
    private Long weight;

    @Expose
    @SerializedName("graph_id")
    private Long graphId;

    @Expose
    @SerializedName("children")
    private List<NavigationVO> children;

    @Expose
    @SerializedName("type")
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNavigationId() {
        return navigationId;
    }

    public void setNavigationId(Long navigationId) {
        this.navigationId = navigationId;
    }

    public Long getOriginalId() {
        return originalId;
    }

    public void setOriginalId(Long originalId) {
        this.originalId = originalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public Long getGraphId() {
        return graphId;
    }

    public void setGraphId(Long graphId) {
        this.graphId = graphId;
    }

    public List<NavigationVO> getChildren() {
        return children;
    }

    public void setChildren(List<NavigationVO> children) {
        this.children = children;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NavigationVO that = (NavigationVO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (navigationId != null ? !navigationId.equals(that.navigationId) : that.navigationId != null) return false;
        if (originalId != null ? !originalId.equals(that.originalId) : that.originalId != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (weight != null ? !weight.equals(that.weight) : that.weight != null) return false;
        if (graphId != null ? !graphId.equals(that.graphId) : that.graphId != null) return false;
        if (children != null ? !children.equals(that.children) : that.children != null) return false;
        return type != null ? type.equals(that.type) : that.type == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (navigationId != null ? navigationId.hashCode() : 0);
        result = 31 * result + (originalId != null ? originalId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (weight != null ? weight.hashCode() : 0);
        result = 31 * result + (graphId != null ? graphId.hashCode() : 0);
        result = 31 * result + (children != null ? children.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
