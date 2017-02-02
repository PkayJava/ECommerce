package com.angkorteam.framework.extension.wicket.extensions.markup.html.repeater.data.table.filter;

import com.angkorteam.framework.extension.wicket.WicketBiFunction;
import com.angkorteam.framework.extension.wicket.WicketTriConsumer;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.util.Map;

/**
 * Created by socheat on 12/7/16.
 */
public class ItemProgressBarLink extends Panel {

    public ItemProgressBarLink(String id,
                               IModel<Map<String, Object>> rowModel,
                               IModel<?> model,
                               WicketBiFunction<String, Map<String, Object>, ItemCss> itemCss,
                               WicketBiFunction<String, Map<String, Object>, Boolean> progressBarActive,
                               WicketTriConsumer<String, Map<String, Object>, AjaxRequestTarget> itemClick,
                               String identity) {
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

        WebMarkupContainer progressBar = new WebMarkupContainer("progressBar");
        link.add(progressBar);
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
        progressValue.add(AttributeModifier.replace("style", Model.of("width: ").combineWith(model, this::prefix).combineWith(Model.of("%"), this::suffix)));
    }

    private String prefix(String s, Object u) {
        if (u == null || !(u instanceof Number)) {
            return s + "0";
        } else {
            double percentage = ((Number) u).doubleValue();
            if (percentage < 0) {
                percentage = 0;
            } else if (percentage > 100) {
                percentage = 100;
            }
            return s + String.valueOf(percentage);
        }
    }

    private String suffix(String s, String u) {
        return s + u;
    }
}
