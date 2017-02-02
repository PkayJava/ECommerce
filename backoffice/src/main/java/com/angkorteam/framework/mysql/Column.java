package com.angkorteam.framework.mysql;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 31/1/17.
 */
public class Column implements Serializable {

    private String field;
    private String type;
    private String key;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Column column = (Column) o;

        if (field != null ? !field.equals(column.field) : column.field != null) return false;
        if (type != null ? !type.equals(column.type) : column.type != null) return false;
        return key != null ? key.equals(column.key) : column.key == null;
    }

    @Override
    public int hashCode() {
        int result = field != null ? field.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (key != null ? key.hashCode() : 0);
        return result;
    }
}
