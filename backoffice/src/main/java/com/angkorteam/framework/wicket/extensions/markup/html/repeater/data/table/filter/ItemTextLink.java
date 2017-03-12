package com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter;

import com.angkorteam.framework.wicket.WicketBiFunction;
import com.angkorteam.framework.wicket.WicketTriConsumer;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import java.util.Map;

/**
 * Created by socheat on 12/7/16.
 */
public class ItemTextLink extends Panel {

    public ItemTextLink(String id, IModel<Map<String, Object>> rowModel, IModel<?> model, WicketBiFunction<String, Map<String, Object>, ItemCss> itemCss, WicketTriConsumer<String, Map<String, Object>, AjaxRequestTarget> itemClick, String identity) {
        super(id);

        AjaxLink<Map<String, Object>> link = new AjaxLink<Map<String, Object>>("link", rowModel) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                if (itemClick != null) {
                    itemClick.accept(identity, rowModel.getObject(), target);
                }
            }
        };
        add(link);

        Label text = new Label("text", model);
        text.setRenderBodyOnly(true);
        link.add(text);

        ItemCss htmlLambdaCss = itemCss.apply(identity, rowModel.getObject());
        if (htmlLambdaCss == ItemCss.SUCCESS) {
            link.add(AttributeModifier.replace("class", "btn-xs btn-success"));
        } else if (htmlLambdaCss == ItemCss.PRIMARY) {
            link.add(AttributeModifier.replace("class", "btn-xs btn-primary"));
        } else if (htmlLambdaCss == ItemCss.WARNING) {
            link.add(AttributeModifier.replace("class", "btn-xs btn-warning"));
        } else if (htmlLambdaCss == ItemCss.DANGER) {
            link.add(AttributeModifier.replace("class", "btn-xs btn-danger"));
        } else if (htmlLambdaCss == ItemCss.INFO) {
            link.add(AttributeModifier.replace("class", "btn-xs btn-info"));
        }
    }

    public ItemTextLink(String id, IModel<Map<String, Object>> rowModel, IModel<?> model, ItemCss itemCss, WicketTriConsumer<String, Map<String, Object>, AjaxRequestTarget> itemClick, String identity) {
        super(id);

        AjaxLink<Map<String, Object>> link = new AjaxLink<Map<String, Object>>("link", rowModel) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                if (itemClick != null) {
                    itemClick.accept(identity, rowModel.getObject(), target);
                }
            }
        };
        add(link);

        Label text = new Label("text", model);
        text.setRenderBodyOnly(true);
        link.add(text);

        ItemCss htmlLambdaCss = itemCss;
        if (htmlLambdaCss == ItemCss.SUCCESS) {
            link.add(AttributeModifier.replace("class", "btn-xs btn-success"));
        } else if (htmlLambdaCss == ItemCss.PRIMARY) {
            link.add(AttributeModifier.replace("class", "btn-xs btn-primary"));
        } else if (htmlLambdaCss == ItemCss.WARNING) {
            link.add(AttributeModifier.replace("class", "btn-xs btn-warning"));
        } else if (htmlLambdaCss == ItemCss.DANGER) {
            link.add(AttributeModifier.replace("class", "btn-xs btn-danger"));
        } else if (htmlLambdaCss == ItemCss.INFO) {
            link.add(AttributeModifier.replace("class", "btn-xs btn-info"));
        }
    }

}
