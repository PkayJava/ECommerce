package com.angkorteam.platform.provider;

import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
import com.angkorteam.platform.Spring;
import com.google.gson.Gson;

/**
 * Created by socheatkhauv on 21/1/17.
 */
public class OptionSingleChoiceProvider extends com.angkorteam.framework.extension.wicket.markup.html.form.select2.OptionSingleChoiceProvider {

    public OptionSingleChoiceProvider(String table, String idField) {
        super(table, idField);
    }

    public OptionSingleChoiceProvider(String table, String idField, String queryField) {
        super(table, idField, queryField);
    }

    public OptionSingleChoiceProvider(String table, String idField, String queryField, String labelField) {
        super(table, idField, queryField, labelField);
    }

    @Override
    public Gson getGson() {
        return Spring.getBean(Gson.class);
    }

    @Override
    public NamedParameterJdbcTemplate getNamed() {
        return Spring.getBean(NamedParameterJdbcTemplate.class);
    }

}
