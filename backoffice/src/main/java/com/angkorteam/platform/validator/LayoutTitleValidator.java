package com.angkorteam.platform.validator;

import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
import com.angkorteam.platform.Platform;
import com.google.common.base.Strings;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

/**
 * Created by socheat on 11/13/16.
 */
public class LayoutTitleValidator implements IValidator<String> {

    private String layoutId;

    public LayoutTitleValidator() {
    }

    public LayoutTitleValidator(String layoutId) {
        this.layoutId = layoutId;
    }

    @Override
    public void validate(IValidatable<String> validatable) {
        NamedParameterJdbcTemplate named = Platform.getBean(NamedParameterJdbcTemplate.class);

        String title = validatable.getValue();
        if (!Strings.isNullOrEmpty(title)) {
            SelectQuery selectQuery = new SelectQuery("platform_layout");
            selectQuery.addField("count(*)");
            selectQuery.addWhere("name = :name", title);
            if (!Strings.isNullOrEmpty(this.layoutId)) {
                selectQuery.addWhere("platform_layout_id != :layout", layoutId);
            }
            int count = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), int.class);
            if (count > 0) {
                validatable.error(new ValidationError(this, "duplicated"));
            }
        }
    }

}
