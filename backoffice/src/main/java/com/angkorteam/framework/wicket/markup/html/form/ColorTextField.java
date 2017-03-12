package com.angkorteam.framework.wicket.markup.html.form;

import com.angkorteam.framework.wicket.resource.AdminLTEResourceReference;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;

/**
 * Created by socheat on 6/1/16.
 */
public class ColorTextField extends TextField<String> {

    public ColorTextField(String id) {
        super(id, String.class);
    }

    public ColorTextField(String id, IModel<String> model) {
        super(id, model, String.class);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        setOutputMarkupId(true);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        String markupId = this.getMarkupId(true);
        response.render(CssHeaderItem.forReference(AdminLTEResourceReference.COLOR_PICKER_STYLE));
        response.render(JavaScriptHeaderItem.forReference(AdminLTEResourceReference.COLOR_PICKER_JAVASCRIPT));
        response.render(OnDomReadyHeaderItem.forScript("$('#" + markupId + "').colorpicker()"));
    }
}