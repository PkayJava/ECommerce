package com.angkorteam.framework.share.provider;

import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.Calendar;

/**
 * Created by socheat on 11/7/16.
 */
public interface TableProvider {

    void selectField(String column, Class<?> columnClass);

    void selectField(String column, String s, Calendar date);

    void boardField(String jdbcColumn, String aliasName, Class<?> clazz);

    void boardField(String jdbcColumn, String aliasName, Calendar calendar);

    void selectField(String aliasName, Calendar calendar);

}
