package com.angkorteam.framework.extension.wicket.markup.html.form.select2;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by socheatkhauv on 20/1/17.
 */
public class OptionMapper implements RowMapper<Option> {

    private final String idField;

    private final String textField;

    public OptionMapper() {
        this.idField = "id";
        this.textField = "text";
    }

    public OptionMapper(String idField, String textField) {
        this.idField = idField;
        this.textField = textField;
    }

    @Override
    public Option mapRow(ResultSet rs, int rowNum) throws SQLException {
        String id = rs.getString(this.idField);
        String text = rs.getString(this.textField);
        return new Option(id, text);
    }

}
