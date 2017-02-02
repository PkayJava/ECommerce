package com.angkorteam.framework.extension.wicket.extensions.markup.html.repeater.data.table.filter;

import com.angkorteam.framework.extension.wicket.WicketBiFunction;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import java.util.Map;

/**
 * Created by socheat on 12/7/16.
 */
public class ItemText extends Panel {

    public ItemText(String id, IModel<Map<String, Object>> rowModel, IModel<?> model, WicketBiFunction<String, Map<String, Object>, ItemCss> itemCss, String identity) {
        super(id);

        Label text = new Label("text", model);
        add(text);

        if (itemCss == null) {
            text.setRenderBodyOnly(true);
        } else {
            ItemCss htmlLambdaCss = itemCss.apply(identity, rowModel.getObject());
            if (htmlLambdaCss == null || htmlLambdaCss == ItemCss.NONE) {
                text.setRenderBodyOnly(true);
            } else {
                if (htmlLambdaCss == ItemCss.SUCCESS) {
                    text.add(AttributeModifier.replace("class", "label label-success"));
                } else if (htmlLambdaCss == ItemCss.PRIMARY) {
                    text.add(AttributeModifier.replace("class", "label label-primary"));
                } else if (htmlLambdaCss == ItemCss.WARNING) {
                    text.add(AttributeModifier.replace("class", "label label-warning"));
                } else if (htmlLambdaCss == ItemCss.DANGER) {
                    text.add(AttributeModifier.replace("class", "label label-danger"));
                } else if (htmlLambdaCss == ItemCss.INFO) {
                    text.add(AttributeModifier.replace("class", "label label-info"));
                }
            }
        }
    }
}
