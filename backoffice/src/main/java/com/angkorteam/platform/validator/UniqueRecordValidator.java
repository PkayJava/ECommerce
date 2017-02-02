package com.angkorteam.platform.validator;

import com.angkorteam.framework.extension.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
import com.angkorteam.platform.Spring;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.springframework.jdbc.BadSqlGrammarException;

/**
 * Created by socheat on 12/6/16.
 */
public class UniqueRecordValidator<T> implements IValidator<T> {

    private final String tableName;

    private final String fieldName;

    private String idFieldName;

    private Object idFieldValue;

    public UniqueRecordValidator(String tableName, String fieldName) {
        this.tableName = tableName;
        this.fieldName = fieldName;
    }

    public UniqueRecordValidator(String tableName, String fieldName, String idFieldName, String idFieldValue) {
        this.tableName = tableName;
        this.fieldName = fieldName;
        this.idFieldName = idFieldName;
        this.idFieldValue = idFieldValue;
    }

    public UniqueRecordValidator(String tableName, String fieldName, String idFieldName, Number idFieldValue) {
        this.tableName = tableName;
        this.fieldName = fieldName;
        this.idFieldName = idFieldName;
        this.idFieldValue = String.valueOf(idFieldValue);
    }

    public UniqueRecordValidator(String tableName, String fieldName, String idFieldName, Option idFieldValue) {
        this.tableName = tableName;
        this.fieldName = fieldName;
        this.idFieldName = idFieldName;
        this.idFieldValue = idFieldValue.getId();
    }

    public UniqueRecordValidator(String tableName, String fieldName, String idFieldName, Boolean idFieldValue) {
        this.tableName = tableName;
        this.fieldName = fieldName;
        this.idFieldName = idFieldName;
        this.idFieldValue = idFieldValue;
    }

    public UniqueRecordValidator(String tableName, String fieldName, String idFieldName, Character idFieldValue) {
        this.tableName = tableName;
        this.fieldName = fieldName;
        this.idFieldName = idFieldName;
        this.idFieldValue = String.valueOf(idFieldValue);
    }

    @Override
    public void validate(IValidatable<T> validatable) {
        T value = validatable.getValue();
        Object newValue = null;
        if (value != null) {
            if (value instanceof Option) {
                newValue = ((Option) value).getId();
            } else {
                newValue = value;
            }
        }
        if (newValue != null) {
            try {
                NamedParameterJdbcTemplate named = Spring.getBean(NamedParameterJdbcTemplate.class);
                SelectQuery selectQuery = new SelectQuery(this.tableName);
                selectQuery.addField("count(*)");
                selectQuery.addWhere(this.fieldName + " = :newValue", newValue);
                if (this.idFieldValue != null) {
                    selectQuery.addWhere(idFieldName + " != :value", this.idFieldValue);
                }
                int count = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), int.class);
                if (count > 0) {
                    validatable.error(new ValidationError(this, "duplicated"));
                }
            } catch (BadSqlGrammarException e) {
                validatable.error(new ValidationError(this, "error").setVariable("message", e.getMessage()));
            }
        }
    }

}
