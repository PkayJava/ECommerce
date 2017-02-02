package com.angkorteam.framework.jdbc;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.Map;

/**
 * Created by socheatkhauv on 29/1/17.
 */
public class InsertQuery extends ParamQuery {

    private final String tableName;
    private final Map<String, String> field = Maps.newLinkedHashMap();

    public InsertQuery(String tableName) {
        this.tableName = tableName;
    }

    public void addValue(String criteria) {
        if (criteria.contains(":")) {
            throw new RuntimeException(": instanceof invalid");
        }
        int index = criteria.indexOf("=");
        if (index == -1) {
            throw new RuntimeException("= instanceof not found");
        }
        String name = criteria.substring(0, index).trim();
        String value = criteria.substring(index + 1).trim();
        this.field.put(name, value);
        this.dirty = true;
    }

    public void addValue(String criteria, Object value) {
        if (value != null) {
            if (value instanceof Boolean
                    || value instanceof Byte
                    || value instanceof Short
                    || value instanceof Integer
                    || value instanceof Long
                    || value instanceof Float
                    || value instanceof Double
                    || value instanceof Date
                    || value instanceof Character
                    || value instanceof String) {
            } else {
                throw new RuntimeException(value + " instanceof not support");
            }
        }
        int index = criteria.indexOf("=");
        if (index == -1) {
            throw new RuntimeException("= instanceof not found");
        }
        String name = criteria.substring(0, index).trim();
        String param = criteria.substring(index + 1).trim();
        if (param.startsWith(":")) {
            this.field.put(name, param);
            this.param.put(param.substring(1), value);
            this.dirty = true;
        } else {
            throw new RuntimeException("param for value instanceof not defined");
        }
    }

    public String toSQL() {
        if (!this.dirty) {
            return this.cached;
        }
        this.cached = "INSERT INTO " + this.tableName +
                "(" + StringUtils.join(this.field.keySet(), ", ") + ")" +
                " " + "VALUES" + "(" + StringUtils.join(this.field.values(), ", ") + ")";
        this.dirty = false;
        return this.cached;
    }
}
