package com.angkorteam.framework.wicket.markup.html.form;

import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.google.common.collect.Lists;
import org.apache.wicket.model.util.ListModel;

import java.util.List;

/**
 * Created by socheatkhauv on 20/1/17.
 */
public class OptionListModel extends ListModel<Option> {

    private List<String> where = null;

    public OptionListModel(JdbcTemplate jdbcTemplate, String table, String idField, String textField) {
        this(jdbcTemplate, table, idField, textField, Lists.newArrayList());
    }

    public OptionListModel(JdbcTemplate jdbcTemplate, String table, String idField, String textField, List<String> where) {
        this.where = where;
        SelectQuery selectQuery = new SelectQuery(table);
        selectQuery.addField(idField + " AS id");
        selectQuery.addField(textField + " AS text");
        selectQuery.addOrderBy(textField);
        if (this.where != null && !this.where.isEmpty()) {
            for (String wh : this.where) {
                selectQuery.addWhere(wh);
            }
        }
        List<Option> options = jdbcTemplate.queryForList(selectQuery.toSQL(), Option.class);
        setObject(options);
    }

}
