package com.angkorteam.platform.provider;

import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
import com.angkorteam.platform.Platform;
import com.google.gson.Gson;

/**
 * Created by socheatkhauv on 21/1/17.
 */
public class OptionSingleChoiceProvider extends com.angkorteam.framework.wicket.markup.html.form.select2.OptionSingleChoiceProvider {

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
        return Platform.getBean(Gson.class);
    }

    @Override
    public NamedParameterJdbcTemplate getNamed() {
        return Platform.getBean(NamedParameterJdbcTemplate.class);
    }

}
