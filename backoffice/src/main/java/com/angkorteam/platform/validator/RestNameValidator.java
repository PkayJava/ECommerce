package com.angkorteam.platform.validator;

import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
import com.angkorteam.platform.Spring;
import com.google.common.base.Strings;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

/**
 * Created by socheat on 11/3/16.
 */
public class RestNameValidator implements IValidator<String> {

    private String restId;

    public RestNameValidator() {
    }

    public RestNameValidator(String restId) {
        this.restId = restId;
    }

    @Override
    public void validate(IValidatable<String> validatable) {
        NamedParameterJdbcTemplate named = Spring.getBean(NamedParameterJdbcTemplate.class);

        String name = validatable.getValue();
        if (!Strings.isNullOrEmpty(name)) {
            SelectQuery selectQuery = new SelectQuery("rest");
            selectQuery.addField("count(*)");
            selectQuery.addWhere("name = :name", name);
            if (!Strings.isNullOrEmpty(this.restId)) {
                selectQuery.addWhere("rest_id != :rest_id", restId);
            }
            int count = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), int.class);
            if (count > 0) {
                validatable.error(new ValidationError(this, "duplicated"));
            }
        }
    }

}
