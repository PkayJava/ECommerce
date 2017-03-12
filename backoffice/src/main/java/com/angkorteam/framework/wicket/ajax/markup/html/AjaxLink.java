package com.angkorteam.framework.wicket.ajax.markup.html;

import com.angkorteam.framework.wicket.WicketBiConsumer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;

/**
 * Created by socheat on 10/23/15.
 */
public class AjaxLink<T> extends org.apache.wicket.ajax.markup.html.AjaxLink<T> {

    private WicketBiConsumer<AjaxLink, AjaxRequestTarget> onClick;

    public AjaxLink(String id) {
        super(id);
    }

    public AjaxLink(String id, IModel<T> model) {
        super(id, model);
    }

    @Override
    public final void onClick(AjaxRequestTarget target) {
        if (this.onClick != null) {
            this.onClick.accept(this, target);
        }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        setOutputMarkupId(true);
    }

    public void setOnClick(WicketBiConsumer<AjaxLink, AjaxRequestTarget> onClick) {
        this.onClick = onClick;
    }

}
