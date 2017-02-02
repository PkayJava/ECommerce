package com.angkorteam.framework.jdbc;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by socheatkhauv on 29/1/17.
 */
public class DeleteQuery extends WhereQuery {

    private final String tableName;

    public DeleteQuery(String tableName) {
        this.tableName = tableName;
    }

    public String toSQL() {
        if (!this.dirty) {
            return cached;
        }
        StringBuilder builder = new StringBuilder();
        builder.append("DELETE FROM ").append(this.tableName);
        if (!this.where.isEmpty()) {
            builder.append(" WHERE ").append(StringUtils.join(this.where, " AND "));
        }
        this.cached = builder.toString();
        this.dirty = false;
        return this.cached;
    }

}
