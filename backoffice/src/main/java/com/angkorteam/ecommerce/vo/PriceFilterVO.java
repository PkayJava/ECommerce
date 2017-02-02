package com.angkorteam.ecommerce.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class PriceFilterVO implements Serializable {

    @Expose
    @SerializedName("id")
    private final Long id = 3L;

    @Expose
    @SerializedName("name")
    private final String name = "Price";

    @Expose
    @SerializedName("type")
    private final String type = "range";

    @Expose
    @SerializedName("label")
    private final String label = "price";

    @Expose
    @SerializedName("values")
    private final Object[] values = new Object[]{0, 100, "$"};

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

    public Object[] getValues() {
        return values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PriceFilterVO that = (PriceFilterVO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (label != null ? !label.equals(that.label) : that.label != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(values, that.values);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (label != null ? label.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(values);
        return result;
    }
}
