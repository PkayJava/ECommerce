package com.angkorteam.ecommerce.vo;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ColorFilterVO implements Serializable {

    @Expose
    @SerializedName("id")
    private final Long id = 1L;

    @Expose
    @SerializedName("name")
    private final String name = "Color";

    @Expose
    @SerializedName("type")
    private final String type = "color";

    @Expose
    @SerializedName("label")
    private final String label = "color";

    @Expose
    @SerializedName("values")
    private final List<ColorValueVO> values = Lists.newArrayList();

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getLabel() {
        return label;
    }

    public List<ColorValueVO> getValues() {
        return values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ColorFilterVO that = (ColorFilterVO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (label != null ? !label.equals(that.label) : that.label != null) return false;
        return values != null ? values.equals(that.values) : that.values == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (label != null ? label.hashCode() : 0);
        result = 31 * result + (values != null ? values.hashCode() : 0);
        return result;
    }
}