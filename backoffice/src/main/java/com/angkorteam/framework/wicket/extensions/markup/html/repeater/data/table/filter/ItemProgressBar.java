package com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter;

import com.angkorteam.framework.wicket.WicketBiFunction;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import java.util.Map;

/**
 * Created by socheat on 12/7/16.
 */
public class ItemProgressBar extends Panel {

    public ItemProgressBar(String id, IModel<Map<String, Object>> rowModel, IModel<?> model, WicketBiFunction<String, Map<String, Object>, ItemCss> itemCss, WicketBiFunction<String, Map<String, Object>, Boolean> progressBarActive, String identity) {
        super(id);
        WebMarkupContainer progressBar = new WebMarkupContainer("progressBar");
        add(progressBar);
        WebMarkupContainer progressValue = new WebMarkupContainer("progressValue");
        progressBar.add(progressValue);

        boolean active = false;
        if (progressBarActive != null) {
            active = progressBarActive.apply(identity, rowModel.getObject());
        }

        if (active) {
            progressBar.add(AttributeModifier.replace("class", "progress progress-xs progress-striped active"));
        } else {
            progressBar.add(AttributeModifier.replace("class", "progress progress-xs"));
        }

        ItemCss htmlLambdaCss = itemCss.apply(identity, rowModel.getObject());
        if (htmlLambdaCss == null || htmlLambdaCss == ItemCss.NONE) {
            htmlLambdaCss = ItemCss.PRIMARY;
        }

        if (htmlLambdaCss == ItemCss.SUCCESS) {
            progressValue.add(AttributeModifier.replace("class", "progress-bar progress-bar-success"));
        } else if (htmlLambdaCss == ItemCss.PRIMARY) {
            progressValue.add(AttributeModifier.replace("class", "progress-bar progress-bar-primary"));
        } else if (htmlLambdaCss == ItemCss.WARNING) {
            progressValue.add(AttributeModifier.replace("class", "progress-bar progress-bar-yellow"));
        } else if (htmlLambdaCss == ItemCss.DANGER) {
            progressValue.add(AttributeModifier.replace("class", "progress-bar progress-bar-danger"));
        }
        progressValue.add(AttributeModifier.replace("style", model));
    }
}
