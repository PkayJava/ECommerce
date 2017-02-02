package com.angkorteam.framework.extension.wicket.markup.html.form;

import com.angkorteam.framework.extension.wicket.resource.AdminLTEResourceReference;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.model.IModel;

import java.util.Date;

/**
 * Created by socheat on 6/1/16.
 */
public class DateTextField extends org.apache.wicket.extensions.markup.html.form.DateTextField {

    private static final String JAVA_PARTTERN = "dd/MM/yyyy";
    private static final String JAVASCRIPT_PARTTERN = "dd/mm/yyyy";

    public DateTextField(String id) {
        super(id, null, JAVA_PARTTERN);
    }

    public DateTextField(String id, IModel<Date> model) {
        super(id, model, JAVA_PARTTERN);
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
        response.render(CssHeaderItem.forReference(AdminLTEResourceReference.DATE_PICKER_STYLE));
        response.render(JavaScriptHeaderItem.forReference(AdminLTEResourceReference.DATE_PICKER_JAVASCRIPT));
        response.render(OnDomReadyHeaderItem.forScript("$('#" + markupId + "').datepicker({ autoclose:true, format:'" + JAVASCRIPT_PARTTERN + "', weekStart:1 })"));
    }
}
