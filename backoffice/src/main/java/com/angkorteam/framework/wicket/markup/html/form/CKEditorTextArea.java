package com.angkorteam.framework.wicket.markup.html.form;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptUrlReferenceHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.IModel;

/**
 * Created by socheatkhauv on 2/10/15.
 */
public class CKEditorTextArea extends TextArea<String> {

    /**
     *
     */
    private static final long serialVersionUID = -326127357542736015L;

    public CKEditorTextArea(String id) {
        super(id);
    }

    public CKEditorTextArea(String id, IModel<String> model) {
        super(id, model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        setOutputMarkupId(true);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        JavaScriptUrlReferenceHeaderItem item = new JavaScriptUrlReferenceHeaderItem("http://cdn.ckeditor.com/4.5.9/full/ckeditor.js", "ckeditor", false, "UTF-8", "");
        response.render(item);
        String javascript = "CKEDITOR.replace( '" + getMarkupId(true) + "', {toolbar: [[ 'Bold', 'Italic','Underline' ],['TextColor', 'BGColor','SpecialChar' ]]})";
        response.render(OnDomReadyHeaderItem.forScript(javascript));
    }
}
