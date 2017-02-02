package com.angkorteam.framework.jdbc;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by socheatkhauv on 29/1/17.
 */
public class SelectQuery extends WhereQuery {

    private final String tableName;

    private final List<String> field = Lists.newLinkedList();
    private final List<String> join = Lists.newLinkedList();
    private final List<String> orderBy = Lists.newLinkedList();
    private final List<String> having = Lists.newLinkedList();
    private final List<String> groupBy = Lists.newLinkedList();
    private boolean pagination = false;
    private long offset;
    private long number;

    public SelectQuery(String tableName) {
        this.tableName = tableName;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
        this.dirty = true;
        this.pagination = true;
    }

    public long getOffset() {
        return this.offset;
    }

    public void setNumber(long number) {
        this.number = number;
        this.dirty = true;
        this.pagination = true;
    }

    public long getNumber() {
        return this.number;
    }

    public void addField(String field) {
        this.field.add(field);
        this.dirty = true;
    }

    public void addField(String... fields) {
        if (fields != null && fields.length > 0) {
            for (String field : fields) {
                addField(field);
            }
            this.dirty = true;
        }
    }

    public void addHaving(String criteria) {
        if (criteria.contains(":")) {
            throw new RuntimeException(": is invalid");
        }
        this.having.add(criteria);
        this.dirty = true;
    }

    public void addHaving(String criteria, Object value) {
        addInternalCriteria(this.having, this.param, criteria, value);
    }

    public <T> void addHaving(String criteria, Class<T> typeClass, List<T> values) {
        addInternalCriteria(this.having, this.param, criteria, typeClass, values);
    }

    public <T> void addHaving(String criteria, Class<T> typeClass, T value1, T value2) {
        addInternalCriteria(this.having, this.param, criteria, typeClass, value1, value2);
    }

    public void addOrderBy(String field) {
        addOrderBy(field, SortType.Asc);
    }

    public void addOrderBy(String field, SortType sort) {
        if (sort == SortType.Asc) {
            this.orderBy.add(field + " ASC");
        } else if (sort == SortType.Desc) {
            this.orderBy.add(field + " DESC");
        }
        this.dirty = true;
    }

    public void addGroupBy(String field) {
        this.groupBy.add(field);
        this.dirty = true;
    }

    public void addJoin(JoinType join, String tableName, String on) {
        if (join == JoinType.LeftJoin) {
            this.join.add("LEFT JOIN " + tableName + " ON " + on);
        } else if (join == JoinType.RightJoin) {
            this.join.add("RIGHT JOIN " + tableName + " ON " + on);
        } else if (join == JoinType.InnerJoin) {
            this.join.add("INNER JOIN " + tableName + " ON " + on);
        }
        this.dirty = true;
    }

    public void setLimit(Long offset, Long number) {
        this.offset = offset;
        this.number = number;
        this.dirty = true;
        this.pagination = true;
    }

    public String toSQL() {
        if (!this.dirty) {
            return this.cached;
        }
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT ");
        if (this.field.isEmpty()) {
            this.field.add("*");
        }
        builder.append(StringUtils.join(this.field, ", "));
        builder.append(" FROM ");
        builder.append(this.tableName);
        if (!join.isEmpty()) {
            builder.append(" ").append(StringUtils.join(this.join, " "));
        }
        if (!this.where.isEmpty()) {
            builder.append(" WHERE ").append(StringUtils.join(this.where, " AND "));
        }
        if (!this.groupBy.isEmpty()) {
            builder.append(" GROUP BY ").append(StringUtils.join(this.groupBy, ", "));
        }
        if (!this.having.isEmpty()) {
            builder.append(" HAVING ").append(StringUtils.join(this.having, " AND "));
        }
        if (!this.orderBy.isEmpty()) {
            builder.append(" ORDER BY ").append(StringUtils.join(this.orderBy, ", "));
        }
        if (pagination) {
            builder.append(" LIMIT ").append(this.offset).append(",").append(this.number);
        }
        this.cached = builder.toString();
        this.dirty = false;
        return this.cached;
    }

}
