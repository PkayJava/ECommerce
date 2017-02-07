package com.angkorteam.ecommerce.validator;

import com.angkorteam.framework.extension.wicket.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.validation.AbstractFormValidator;
import org.apache.wicket.validation.ValidationError;

import java.util.Date;

/**
 * Created by socheatkhauv on 7/2/17.
 */
public class StartEndDateValidator extends AbstractFormValidator {

    private DateTextField startDateField;

    private DateTextField endDateField;

    private FormComponent[] formComponents;

    public StartEndDateValidator(DateTextField startDateField, DateTextField endDateField) {
        this.startDateField = startDateField;
        this.endDateField = endDateField;
        this.formComponents = new FormComponent[]{this.startDateField, this.endDateField};
    }

    @Override
    public FormComponent<?>[] getDependentFormComponents() {
        return this.formComponents;
    }

    @Override
    public void validate(Form<?> form) {
        Date startDate = this.startDateField.getModelObject();
        Date endDate = this.endDateField.getModelObject();
        if (startDate != null && endDate != null) {
            if (startDate.after(endDate)) {
                {
                    ValidationError error = new ValidationError();
                    error.addKey("Required");
                    this.startDateField.error(error);
                }
                {
                    ValidationError error = new ValidationError();
                    error.addKey("Required");
                    this.endDateField.error(error);
                }
            }
        }
    }

}
