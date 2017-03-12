package com.angkorteam.framework.wicket.markup.html.form.select2;

import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
import com.google.common.collect.Lists;
import org.apache.wicket.model.IModel;

import java.util.List;

/**
 * Created by socheat on 12/5/16.
 */
public abstract class OptionMultipleChoiceProvider extends MultipleChoiceProvider<Option> {

    private final String table;

    private final String idField;

    private final String queryField;

    private final String labelField;

    private final List<String> where;

    public OptionMultipleChoiceProvider(String table, String idField) {
        this(table, idField, idField);
    }

    public OptionMultipleChoiceProvider(String table, String idField, String queryField) {
        this(table, idField, queryField, queryField);
    }

    public OptionMultipleChoiceProvider(String table, String idField, String queryField, String labelField) {
        this.table = table;
        this.idField = idField;
        this.labelField = labelField;
        this.queryField = queryField;
        this.where = Lists.newArrayList();
    }

    public void addWhere(String filter) {
        this.where.add(filter);
    }

    @Override
    public List<Option> toChoices(List<String> ids) {
        NamedParameterJdbcTemplate named = getNamed();
        SelectQuery selectQuery = new SelectQuery(this.table);
        selectQuery.addField(this.idField + " AS id");
        selectQuery.addField(this.labelField + " AS text");
        selectQuery.addWhere(this.idField + " in (:id)", String.class, ids);
        return named.queryForList(selectQuery.toSQL(), selectQuery.getParam(), Option.class);
    }

    @Override
    public List<Option> query(String s, int i) {
        NamedParameterJdbcTemplate named = getNamed();
        SelectQuery selectQuery = new SelectQuery(this.table);
        selectQuery.addField(this.idField + " AS id");
        selectQuery.addField(this.labelField + " AS text");
        selectQuery.addWhere("LOWER(" + this.queryField + ") like LOWER(:value)", "value", s + "%");
        if (this.where != null && !this.where.isEmpty()) {
            for (String where : this.where) {
                selectQuery.addWhere(where);
            }
        }
        selectQuery.addOrderBy(this.queryField);
        return named.queryForList(selectQuery.toSQL(), selectQuery.getParam(), Option.class);
    }

    @Override
    public boolean hasMore(String s, int i) {
        return false;
    }

    @Override
    public Object getDisplayValue(Option object) {
        return object.getText();
    }

    @Override
    public String getIdValue(Option object, int index) {
        return object.getId();
    }

    @Override
    public Option getObject(String id, IModel<? extends List<? extends Option>> choices) {
        NamedParameterJdbcTemplate named = getNamed();
        SelectQuery selectQuery = new SelectQuery(this.table);
        selectQuery.addField(this.idField + " AS id");
        selectQuery.addField(this.labelField + " AS text");
        selectQuery.addWhere(this.idField + " = :id", id);
        return named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.class);
    }

    public abstract NamedParameterJdbcTemplate getNamed();

}
