package com.angkorteam.platform.validator;

import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
import com.angkorteam.platform.Spring;
import com.google.common.base.Strings;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

/**
 * Created by socheat on 11/13/16.
 */
public class SectionTitleValidator implements IValidator<String> {

    private String sectionId;

    public SectionTitleValidator() {
    }

    public SectionTitleValidator(String sectionId) {
        this.sectionId = sectionId;
    }

    @Override
    public void validate(IValidatable<String> validatable) {
        NamedParameterJdbcTemplate named = Spring.getBean(NamedParameterJdbcTemplate.class);
        String title = validatable.getValue();
        if (!Strings.isNullOrEmpty(title)) {
            SelectQuery selectQuery = new SelectQuery("section");
            selectQuery.addField("count(*)");
            selectQuery.addWhere("title = :title", title);
            if (!Strings.isNullOrEmpty(this.sectionId)) {
                selectQuery.addWhere("section_id != :section_id", this.sectionId);
            }
            int count = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), int.class);
            if (count > 0) {
                validatable.error(new ValidationError(this, "duplicated"));
            }
        }
    }

}
