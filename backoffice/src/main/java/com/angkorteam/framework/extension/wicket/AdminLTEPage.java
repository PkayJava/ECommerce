package com.angkorteam.framework.extension.wicket;

import com.angkorteam.framework.extension.wicket.resource.AdminLTEResourceReference;
import com.angkorteam.framework.extension.wicket.resource.BootstrapResourceReference;
import com.angkorteam.framework.extension.wicket.resource.NotifyJSResourceReference;
import org.apache.wicket.Application;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * Created by socheat on 3/10/16.
 */
public abstract class AdminLTEPage extends WebPage {

    public AdminLTEPage() {
    }

    public AdminLTEPage(IModel<?> model) {
        super(model);
    }

    public AdminLTEPage(PageParameters parameters) {
        super(parameters);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(CssHeaderItem.forReference(BootstrapResourceReference.STYLE));
        response.render(CssHeaderItem.forReference(AdminLTEResourceReference.FONT_AWESOME_STYLE));
        response.render(CssHeaderItem.forReference(AdminLTEResourceReference.ION_ICON_STYLE));
        response.render(CssHeaderItem.forReference(AdminLTEResourceReference.STYLE));
        response.render(CssHeaderItem.forReference(AdminLTEResourceReference.SKIN_STYLE));
        response.render(JavaScriptHeaderItem.forReference(Application.get().getJavaScriptLibrarySettings().getJQueryReference()));
        response.render(JavaScriptHeaderItem.forReference(AdminLTEResourceReference.JAVASCRIPT));
        response.render(JavaScriptHeaderItem.forReference(AdminLTEResourceReference.SLIM_SCROLL_JAVASCRIPT));
        response.render(JavaScriptHeaderItem.forReference(NotifyJSResourceReference.JAVASCRIPT));
    }

}
