package com.angkorteam.framework.share.provider;

import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.Calendar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.Expression;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.Operator;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.util.MapModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by socheat on 3/4/15.
 */
public abstract class JdbcProvider extends SortableDataProvider<Map<String, Object>, String> implements IFilterStateLocator<Map<String, String>>, TableProvider {

    /**
     *
     */
    private static final long serialVersionUID = 2453015465083001162L;

    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcProvider.class);

    private static final char HIDDEN_SPACE = '\u200B';

    private Map<String, String> filterState;

    private Map<String, Class<?>> itemClass;
    private Map<String, String> negotiator;
    private Map<String, String> pattern;
    private Map<String, Calendar> calendar;
    private String groupBy;
    private List<String> fields;

    protected String from;

    private final List<String> join;

    private final List<String> userWhere;

    private final List<String> userHaving;

    private final List<String> aggregate;

    public JdbcProvider() {
        this("");
    }

    public JdbcProvider(String from) {
        this.from = from;
        this.join = new ArrayList<>();
        this.aggregate = new ArrayList<>();
        this.negotiator = new HashMap<>();
        this.fields = new ArrayList<>();
        this.itemClass = new HashMap<>();
        this.filterState = new HashMap<>();
        this.pattern = new HashMap<>();
        this.calendar = new HashMap<>();
        this.userWhere = new ArrayList<>();
        this.userHaving = new ArrayList<>();
    }

    protected String getFrom() {
        return this.from;
    }

    private List<String> buildWhere(Map<String, Class<?>> params, Map<String, Object> values) {
        List<String> where = new ArrayList<>();
        if (this.filterState != null && !this.filterState.isEmpty()) {
            for (Map.Entry<String, String> entry : this.filterState.entrySet()) {
                if (!this.aggregate.contains(entry.getKey())) {
                    Class<?> clazz = this.itemClass.get(entry.getKey());
                    String filterText = StringUtils.trimToEmpty(entry.getValue());
                    Expression expression = Expression.parse(filterText);
                    if (clazz == String.class
                            || clazz == Character.class || clazz == char.class) {
                        String condition = buildStringCondition(params, values, this.negotiator.get(entry.getKey()), expression);
                        where.add(condition);
                    } else if (clazz == Byte.class || clazz == byte.class
                            || clazz == Short.class || clazz == short.class
                            || clazz == Integer.class || clazz == int.class
                            || clazz == Long.class || clazz == long.class
                            || clazz == Float.class || clazz == float.class
                            || clazz == Double.class || clazz == double.class) {
                        String condition = buildNumberCondition(params, values, this.negotiator.get(entry.getKey()), clazz, expression);
                        where.add(condition);
                    } else if (clazz == Boolean.class || clazz == boolean.class) {
                        String condition = buildBooleanCondition(params, values, this.negotiator.get(entry.getKey()), expression);
                        where.add(condition);
                    } else if (clazz == Date.class) {
                        String condition = buildDateTimeCondition(params, values, entry.getKey(), this.negotiator.get(entry.getKey()), expression);
                        if (!Strings.isNullOrEmpty(condition)) {
                            where.add(condition);
                        }
                    }
                }
            }
        }
        List<String> userWhere = where();
        if (userWhere != null && !userWhere.isEmpty()) {
            where.addAll(userWhere);
        }
        return where;
    }

    public void addWhere(String filter) {
        this.userWhere.add(filter);
    }

    public void addHaving(String filter) {
        this.userHaving.add(filter);
    }

    public void addJoin(String join) {
        this.join.add(join);
    }

    private List<String> buildHaving(Map<String, Class<?>> params, Map<String, Object> values) {
        List<String> having = new ArrayList<>();
        if (this.filterState != null && !this.filterState.isEmpty()) {
            for (Map.Entry<String, String> entry : this.filterState.entrySet()) {
                if (this.aggregate.contains(entry.getKey())) {
                    Class<?> clazz = this.itemClass.get(entry.getKey());
                    String filterText = StringUtils.trimToEmpty(entry.getValue());
                    Expression expression = Expression.parse(filterText);
                    if (clazz == String.class
                            || clazz == Character.class || clazz == char.class) {
                        String condition = buildStringCondition(params, values, "`" + entry.getKey() + "`", expression);
                        having.add(condition);
                    } else if (clazz == Byte.class || clazz == byte.class
                            || clazz == Short.class || clazz == short.class
                            || clazz == Integer.class || clazz == int.class
                            || clazz == Long.class || clazz == long.class
                            || clazz == Float.class || clazz == float.class
                            || clazz == Double.class || clazz == double.class) {
                        String condition = buildNumberCondition(params, values, "`" + entry.getKey() + "`", clazz, expression);
                        having.add(condition);
                    } else if (clazz == Boolean.class || clazz == boolean.class) {
                        String condition = buildBooleanCondition(params, values, "`" + entry.getKey() + "`", expression);
                        having.add(condition);
                    } else if (clazz == Date.class) {
                        String condition = buildDateTimeCondition(params, values, entry.getKey(), "`" + entry.getKey() + "`", expression);
                        if (!Strings.isNullOrEmpty(condition)) {
                            having.add(condition);
                        }
                    }
                }
            }
        }
        List<String> userHaving = having();
        if (userHaving != null && !userHaving.isEmpty()) {
            having.addAll(userHaving);
        }
        return having;
    }

    protected String buildOrderBy() {
        String orderBy = null;
        if (getSort() != null) {
            if (getSort().isAscending()) {
                orderBy = "`" + getSort().getProperty() + "` asc";
            } else {
                orderBy = "`" + getSort().getProperty() + "` desc";
            }
        }
        return orderBy;
    }

    public List<String> getFields() {
        List<String> fields = new ArrayList<>();
        for (Map.Entry<String, String> entry : this.negotiator.entrySet()) {
            String alias = entry.getKey();
            String value = entry.getValue();
            if (this.fields.contains(alias) && !fields.contains(value)) {
                fields.add(value + " " + "`" + alias + "`");
            }
        }
        return fields;
    }

    @Override
    public void boardField(String jdbcColumn, String aliasName, Class<?> clazz) {
        this.negotiator.put(aliasName, jdbcColumn);
        if (isAggregate(jdbcColumn)) {
            this.aggregate.add(aliasName);
        }
        if (clazz == Date.class) {
            this.calendar.put(aliasName, Calendar.DateTime);
        }
    }

    @Override
    public void boardField(String jdbcColumn, String aliasName, Calendar calendar) {
        this.negotiator.put(aliasName, jdbcColumn);
        if (isAggregate(jdbcColumn)) {
            this.aggregate.add(aliasName);
        }
        this.calendar.put(aliasName, calendar);
    }

    @Override
    public void selectField(String aliasName, Class<?> clazz) {
        this.fields.add(aliasName);
        this.itemClass.put(aliasName, clazz);
        if (clazz == Date.class) {
            this.calendar.put(aliasName, Calendar.DateTime);
        }
    }

    @Override
    public void selectField(String aliasName, Calendar calendar) {
        this.fields.add(aliasName);
        this.itemClass.put(aliasName, Date.class);
        this.calendar.put(aliasName, calendar);
    }

    @Override
    public void selectField(String aliasName, String pattern, Calendar calendar) {
        this.fields.add(aliasName);
        this.itemClass.put(aliasName, Date.class);
        this.calendar.put(aliasName, calendar);
        this.pattern.put(aliasName, pattern);
    }

    private boolean isAggregate(String jdbcColumn) {
        String column = StringUtils.upperCase(jdbcColumn);
        if (StringUtils.startsWith(column, "AVG(") || StringUtils.startsWith(column, "AVG (")
                || StringUtils.startsWith(column, "BIT_AND(") || StringUtils.startsWith(column, "BIT_AND (")
                || StringUtils.startsWith(column, "BIT_OR(") || StringUtils.startsWith(column, "BIT_OR (")
                || StringUtils.startsWith(column, "BIT_XOR(") || StringUtils.startsWith(column, "BIT_XOR (")
                || StringUtils.startsWith(column, "COUNT(") || StringUtils.startsWith(column, "COUNT (")
                || StringUtils.startsWith(column, "GROUP_CONCAT(") || StringUtils.startsWith(column, "GROUP_CONCAT (")
                || StringUtils.startsWith(column, "MAX(") || StringUtils.startsWith(column, "MAX (")
                || StringUtils.startsWith(column, "MIN(") || StringUtils.startsWith(column, "MIN (")
                || StringUtils.startsWith(column, "STD(") || StringUtils.startsWith(column, "STD (")
                || StringUtils.startsWith(column, "STDDEV(") || StringUtils.startsWith(column, "STDDEV (")
                || StringUtils.startsWith(column, "STDDEV_POP(") || StringUtils.startsWith(column, "STDDEV_POP (")
                || StringUtils.startsWith(column, "STDDEV_SAMP(") || StringUtils.startsWith(column, "STDDEV_SAMP (")
                || StringUtils.startsWith(column, "SUM(") || StringUtils.startsWith(column, "SUM (")
                || StringUtils.startsWith(column, "VAR_POP(") || StringUtils.startsWith(column, "VAR_POP (")
                || StringUtils.startsWith(column, "VAR_SAMP(") || StringUtils.startsWith(column, "VAR_SAMP (")
                || StringUtils.startsWith(column, "VARIANCE(") || StringUtils.startsWith(column, "VARIANCE (")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public final IModel<Map<String, Object>> model(Map<String, Object> object) {
        return new MapModel<>(object);
    }

    @Override
    public final Map<String, String> getFilterState() {
        return this.filterState;
    }

    @Override
    public final void setFilterState(Map<String, String> state) {
        this.filterState = state;
    }

    protected QueryBuilder buildQuery(Map<String, Class<?>> params, Map<String, Object> values) {
        return buildQuery(params, values, null);
    }

    protected QueryBuilder buildQuery(Map<String, Class<?>> params, Map<String, Object> values, String orderBy) {
        if (params instanceof LinkedHashMap) {
        } else {
            throw new WicketRuntimeException("params must be LinkedHashMap");
        }
        List<String> where = buildWhere(params, values);
        List<String> having = buildHaving(params, values);
        QueryBuilder builder = new QueryBuilder();
        builder.setFrom(getFrom());
        if (!this.join.isEmpty()) {
            for (String join : this.join) {
                builder.addJoin(join);
            }
        }
        if (!getFields().isEmpty()) {
            for (String field : getFields()) {
                builder.addSelect(field);
            }
        }
        if (!where.isEmpty()) {
            for (String s : where) {
                builder.addWhere(s);
            }
        }
        if (!having.isEmpty()) {
            for (String s : having) {
                builder.addHaving(s);
            }
        }
        if (!Strings.isNullOrEmpty(this.groupBy)) {
            builder.addGroupBy(this.groupBy);
        }
        if (!Strings.isNullOrEmpty(orderBy)) {
            builder.addOrderBy(orderBy);
        }
        return builder;
    }

    protected List<String> where() {
        return this.userWhere;
    }

    protected List<String> having() {
        return this.userHaving;
    }

    @Override
    public Iterator<Map<String, Object>> iterator(long first, long count) {
        Map<String, Class<?>> params = new LinkedHashMap<>();
        Map<String, Object> values = new HashMap<>();
        String orderBy = buildOrderBy();
        QueryBuilder select = buildQuery(params, values, orderBy);
        select.setLimit(first, count);
        String query = select.toSQL();
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        Object[] args = new Object[values.size()];
        int i = 0;
        for (Map.Entry<String, Class<?>> param : params.entrySet()) {
            Object value = values.get(param.getKey());
            args[i] = value;
            i = i + 1;
        }
        List<Map<String, Object>> result = jdbcTemplate.queryForList(query, args);
        return result.iterator();
    }

    @Override
    public long size() {
        Map<String, Class<?>> params = new LinkedHashMap<>();
        Map<String, Object> values = new HashMap<>();
        QueryBuilder select = buildQuery(params, values);
        String query = select.toSQL();
        String countQuery = "SELECT COUNT(*) FROM (" + query + ") pp";
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        Object[] args = new Object[values.size()];
        int i = 0;
        for (Map.Entry<String, Class<?>> param : params.entrySet()) {
            Object value = values.get(param.getKey());
            args[i] = value;
            i = i + 1;
        }
        return jdbcTemplate.queryForObject(countQuery, long.class, args);
    }

    public void setGroupBy(String groupBy) {
        this.groupBy = groupBy;
    }

    public String getGroupBy() {
        return groupBy;
    }

    protected abstract JdbcTemplate getJdbcTemplate();

    private String buildDateTimeCondition(Map<String, Class<?>> params, Map<String, Object> values, String name, String field, Expression expression) {
        Calendar calendar = this.calendar.get(name);
        String condition = null;
        String pattern = this.pattern.get(name);
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date v1 = null;
        try {
            v1 = dateFormat.parse(expression.getFirstOperand());
        } catch (ParseException e) {
        }

        if (expression.getOperator() == Operator.Equal
                || expression.getOperator() == Operator.Like) {
            if (v1 != null) {
                if (Calendar.Date == calendar) {
                    condition = field + " = ?";
                    params.put(field, java.sql.Date.class);
                    values.put(field, new java.sql.Date(v1.getTime()));
                } else if (Calendar.Time == calendar) {
                    condition = field + " = ?";
                    params.put(field, Time.class);
                    values.put(field, new Time(v1.getTime()));
                } else if (Calendar.DateTime == calendar) {
                    condition = field + " = ?";
                    params.put(field, Timestamp.class);
                    values.put(field, new Timestamp(v1.getTime()));
                }
            }
        } else if (expression.getOperator() == Operator.NotEqual) {
            if (v1 != null) {
                if (Calendar.Date == calendar) {
                    condition = field + " != ?";
                    params.put(field, java.sql.Date.class);
                    values.put(field, new java.sql.Date(v1.getTime()));
                } else if (Calendar.Time == calendar) {
                    condition = field + " != ?";
                    params.put(field, Time.class);
                    values.put(field, new Time(v1.getTime()));
                } else if (Calendar.DateTime == calendar) {
                    condition = field + " != ?";
                    params.put(field, Timestamp.class);
                    values.put(field, new Timestamp(v1.getTime()));
                }
            }
        } else if (expression.getOperator() == Operator.GreaterThan) {
            if (v1 != null) {
                if (Calendar.Date == calendar) {
                    condition = field + " > ?";
                    params.put(field, java.sql.Date.class);
                    values.put(field, new java.sql.Date(v1.getTime()));
                } else if (Calendar.Time == calendar) {
                    condition = field + " > ?";
                    params.put(field, Time.class);
                    values.put(field, new Time(v1.getTime()));
                } else if (Calendar.DateTime == calendar) {
                    condition = field + " > ?";
                    params.put(field, Timestamp.class);
                    values.put(field, new Timestamp(v1.getTime()));
                }
            }
        } else if (expression.getOperator() == Operator.GreaterThanOrEqual) {
            if (v1 != null) {
                if (Calendar.Date == calendar) {
                    condition = field + " >= ?";
                    params.put(field, java.sql.Date.class);
                    values.put(field, new java.sql.Date(v1.getTime()));
                } else if (Calendar.Time == calendar) {
                    condition = field + " >= ?";
                    params.put(field, Time.class);
                    values.put(field, new Time(v1.getTime()));
                } else if (Calendar.DateTime == calendar) {
                    condition = field + " >= ?";
                    params.put(field, Timestamp.class);
                    values.put(field, new Timestamp(v1.getTime()));
                }
            }
        } else if (expression.getOperator() == Operator.LessThan) {
            if (v1 != null) {
                if (Calendar.Date == calendar) {
                    condition = field + " < ?";
                    params.put(field, java.sql.Date.class);
                    values.put(field, new java.sql.Date(v1.getTime()));
                } else if (Calendar.Time == calendar) {
                    condition = field + " < ?";
                    params.put(field, Time.class);
                    values.put(field, new Time(v1.getTime()));
                } else if (Calendar.DateTime == calendar) {
                    condition = field + " < ?";
                    params.put(field, Timestamp.class);
                    values.put(field, new Timestamp(v1.getTime()));
                }
            }
        } else if (expression.getOperator() == Operator.LessThanOrEqual) {
            if (v1 != null) {
                if (Calendar.Date == calendar) {
                    condition = field + " <= ?";
                    params.put(field, java.sql.Date.class);
                    values.put(field, new java.sql.Date(v1.getTime()));
                } else if (Calendar.Time == calendar) {
                    condition = field + " <= ?";
                    params.put(field, Time.class);
                    values.put(field, new Time(v1.getTime()));
                } else if (Calendar.DateTime == calendar) {
                    condition = field + " <= ?";
                    params.put(field, Timestamp.class);
                    values.put(field, new Timestamp(v1.getTime()));
                }
            }
        } else if (expression.getOperator() == Operator.Between) {
            Date v2 = null;
            try {
                v2 = dateFormat.parse(expression.getSecondOperand());
            } catch (ParseException e) {
            }
            if (v1 != null && v2 != null) {
                if (Calendar.Date == calendar) {
                    java.sql.Date d1 = new java.sql.Date(v1.getTime());
                    java.sql.Date d2 = new java.sql.Date(v2.getTime());
                    if (d1.before(d2)) {
                        condition = field + " BETWEEN ? AND ?";
                        params.put(field + "_from", java.sql.Date.class);
                        params.put(field + "_to", java.sql.Date.class);
                        values.put(field + "_from", d1);
                        values.put(field + "_to", d2);
                    } else if (d1.after(d2)) {
                        condition = field + " BETWEEN ? AND ?";
                        params.put(field + "_from", java.sql.Date.class);
                        params.put(field + "_to", java.sql.Date.class);
                        values.put(field + "_from", d2);
                        values.put(field + "_to", d1);
                    } else {
                        condition = field + " = ?";
                        params.put(field, java.sql.Date.class);
                        values.put(field, new java.sql.Date(v1.getTime()));
                    }
                } else if (Calendar.Time == calendar) {
                    Time t1 = new Time(v1.getTime());
                    Time t2 = new Time(v2.getTime());
                    if (t1.before(t2)) {
                        condition = field + " BETWEEN ? AND ?";
                        params.put(field + "_from", Time.class);
                        params.put(field + "_to", Time.class);
                        values.put(field + "_from", t1);
                        values.put(field + "_to", t2);
                    } else if (t1.after(t2)) {
                        condition = field + " BETWEEN ? AND ?";
                        params.put(field + "_from", Time.class);
                        params.put(field + "_to", Time.class);
                        values.put(field + "_from", t2);
                        values.put(field + "_to", t1);
                    } else {
                        condition = field + " = ?";
                        params.put(field, Time.class);
                        values.put(field, new Time(v1.getTime()));
                    }
                } else if (Calendar.DateTime == calendar) {
                    Timestamp dt1 = new Timestamp(v1.getTime());
                    Timestamp dt2 = new Timestamp(v2.getTime());
                    if (dt1.before(dt2)) {
                        condition = field + " BETWEEN ? AND ?";
                        params.put(field + "_from", Timestamp.class);
                        params.put(field + "_to", Timestamp.class);
                        values.put(field + "_from", dt1);
                        values.put(field + "_to", dt2);
                    } else if (dt1.after(dt2)) {
                        condition = field + " BETWEEN ? AND ?";
                        params.put(field + "_from", Timestamp.class);
                        params.put(field + "_to", Timestamp.class);
                        values.put(field + "_from", dt2);
                        values.put(field + "_to", dt1);
                    } else {
                        condition = field + " = ?";
                        params.put(field, Timestamp.class);
                        values.put(field, new Timestamp(v1.getTime()));
                    }
                }
            }
        }
        return condition;
    }

    private String buildStringCondition(Map<String, Class<?>> params, Map<String, Object> values, String field, Expression expression) {
        String condition = null;
        String firstValue = expression.getFirstOperand();
        if (expression.getOperator() == Operator.Equal) {
            if (expression.getFilter().startsWith("=")) {
                condition = field + " = ?";
                params.put(field, String.class);
                values.put(field, firstValue);
            } else {
                condition = field + " RLIKE ?";
                params.put(field, String.class);
                values.put(field, StringUtils.lowerCase(buildLikeRegxExpression(firstValue)));
            }
        } else if (expression.getOperator() == Operator.NotEqual) {
            condition = field + " != ?";
            params.put(field, String.class);
            values.put(field, firstValue);
        } else if (expression.getOperator() == Operator.GreaterThan) {
            condition = field + " > ?";
            params.put(field, String.class);
            values.put(field, firstValue);
        } else if (expression.getOperator() == Operator.GreaterThanOrEqual) {
            condition = field + " >= ?";
            params.put(field, String.class);
            values.put(field, firstValue);
        } else if (expression.getOperator() == Operator.LessThan) {
            condition = field + " < ?";
            params.put(field, String.class);
            values.put(field, firstValue);
        } else if (expression.getOperator() == Operator.LessThanOrEqual) {
            condition = field + " <= ?";
            params.put(field, String.class);
            values.put(field, firstValue);
        } else if (expression.getOperator() == Operator.Like) {
            condition = field + " LIKE ?";
            params.put(field, String.class);
            values.put(field, firstValue + "%");
        } else if (expression.getOperator() == Operator.Between) {
            String secondValue = expression.getSecondOperand();
            condition = field + " BETWEEN ? AND ?";
            params.put(field + "_from", String.class);
            params.put(field + "_to", String.class);
            values.put(field + "_from", firstValue);
            values.put(field + "_to", secondValue);
        }
        return condition;
    }

    private String buildNumberCondition(Map<String, Class<?>> params, Map<String, Object> values, String field, Class<?> clazz, Expression expression) {
        String condition = null;
        String firstValue = expression.getFirstOperand();
        if (expression.getOperator() == Operator.Equal) {
            condition = field + " = ?";
            params.put(field, clazz);
            values.put(field, firstValue);
        } else if (expression.getOperator() == Operator.NotEqual) {
            condition = field + " != ?";
            params.put(field, clazz);
            values.put(field, firstValue);
        } else if (expression.getOperator() == Operator.GreaterThan) {
            condition = field + " > ?";
            params.put(field, clazz);
            values.put(field, firstValue);
        } else if (expression.getOperator() == Operator.GreaterThanOrEqual) {
            condition = field + " >= ?";
            params.put(field, clazz);
            values.put(field, firstValue);
        } else if (expression.getOperator() == Operator.LessThan) {
            condition = field + " < ?";
            params.put(field, clazz);
            values.put(field, firstValue);
        } else if (expression.getOperator() == Operator.LessThanOrEqual) {
            condition = field + " <= ?";
            params.put(field, clazz);
            values.put(field, firstValue);
        } else if (expression.getOperator() == Operator.Like) {
        } else if (expression.getOperator() == Operator.Between) {
            String secondValue = expression.getSecondOperand();
            if (Double.valueOf(firstValue) < Double.valueOf(secondValue)) {
                condition = field + " BETWEEN ? AND ?";
                params.put(field + "_from", clazz);
                params.put(field + "_to", clazz);
                values.put(field + "_from", firstValue);
                values.put(field + "_to", secondValue);
            } else {
                condition = field + " BETWEEN ? AND ?";
                params.put(field + "_from", clazz);
                params.put(field + "_to", clazz);
                values.put(field + "_from", secondValue);
                values.put(field + "_to", firstValue);
            }
        }
        return condition;
    }

    private String buildBooleanCondition(Map<String, Class<?>> params, Map<String, Object> values, String field, Expression expression) {
        String condition = null;
        Boolean firstValue = Boolean.parseBoolean(expression.getFirstOperand());
        if (expression.getOperator() == Operator.Equal) {
            condition = field + " = ?";
            params.put(field, Boolean.class);
            values.put(field, firstValue);
        } else if (expression.getOperator() == Operator.NotEqual) {
            condition = field + " != ?";
            params.put(field, Boolean.class);
            values.put(field, firstValue);
        }
        return condition;
    }

    private String buildLikeRegxExpression(String searchText) {
        StringBuffer result = new StringBuffer(searchText.length());
        boolean space = false;
        for (char tmp : searchText.trim().toCharArray()) {
            if (tmp == ' ' || tmp == HIDDEN_SPACE) {
                if (!space) {
                    result.append('|');
                    space = true;
                }
            } else {
                space = false;
                result.append(Character.toLowerCase(tmp));
            }
        }
        return "^.*(" + result.toString() + ").*$";
    }


}