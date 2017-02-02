package com.angkorteam.framework.extension.wicket.markup.html.form;

import com.angkorteam.framework.extension.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.spring.JdbcTemplate;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.IModel;

import java.util.List;

/**
 * Created by socheatkhauv on 20/1/17.
 */
public class OptionDropDownChoice extends DropDownChoice<Option> {

    public OptionDropDownChoice(String id, IModel<Option> model, JdbcTemplate jdbcTemplate, String table, String idField, String textField) {
        super(id, model, new OptionListModel(jdbcTemplate, table, idField, textField), new OptionChoiceRenderer());
    }

    public OptionDropDownChoice(String id, IModel<Option> model, JdbcTemplate jdbcTemplate, String table, String idField, String textField, List<String> filter) {
        super(id, model, new OptionListModel(jdbcTemplate, table, idField, textField, filter), new OptionChoiceRenderer());
    }

}