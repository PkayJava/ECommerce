package com.angkorteam.framework.extension.wicket.markup.html.form;

import com.angkorteam.framework.extension.wicket.markup.html.form.select2.Option;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

import java.util.List;

/**
 * Created by socheatkhauv on 20/1/17.
 */
public class OptionChoiceRenderer implements IChoiceRenderer<Option> {

    public OptionChoiceRenderer() {
    }

    @Override
    public Object getDisplayValue(Option object) {
        return object.getText();
    }

    @Override
    public String getIdValue(Option object, int index) {
        return object.getId();
    }

    @Override
    public Option getObject(String id, IModel<? extends List<? extends Option>> choices) {
        for (Option option : choices.getObject()) {
            if (option.getId().equals(id)) {
                return option;
            }
        }
        return null;
    }
}
