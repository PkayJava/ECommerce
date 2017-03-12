package com.angkorteam.platform.validator;

import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.validation.AbstractFormValidator;
import org.apache.wicket.validation.ValidationError;

/**
 * Created by socheat on 10/28/16.
 */
public class MenuFormValidator extends AbstractFormValidator {

    private DropDownChoice<Option> menuField;

    private DropDownChoice<Option> sectionField;

    private FormComponent<?>[] formComponents;

    public MenuFormValidator(DropDownChoice<Option> menuField, DropDownChoice<Option> sectionField) {
        this.menuField = menuField;
        this.sectionField = sectionField;
        this.formComponents = new FormComponent<?>[]{this.menuField, this.sectionField};
    }

    @Override
    public FormComponent<?>[] getDependentFormComponents() {
        return this.formComponents;
    }

    @Override
    public void validate(Form<?> form) {
        if (this.menuField.getConvertedInput() != null && this.sectionField.getConvertedInput() != null) {
            {
                ValidationError error = new ValidationError();
                error.addKey("ambiguous");
                this.menuField.error(error);
            }
            {
                ValidationError error = new ValidationError();
                error.addKey("ambiguous");
                this.sectionField.error(error);
            }
        } else if (this.menuField.getConvertedInput() == null && this.sectionField.getConvertedInput() == null) {
            {
                ValidationError error = new ValidationError();
                error.addKey("Required");
                this.menuField.error(error);
            }
            {
                ValidationError error = new ValidationError();
                error.addKey("Required");
                this.sectionField.error(error);
            }
        }
    }
}
