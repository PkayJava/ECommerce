package com.angkorteam.framework.wicket.ajax.markup.html.form;

import com.angkorteam.framework.wicket.WicketBiConsumer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;

/**
 * Created by socheat on 10/14/15.
 */
public class AjaxButton extends org.apache.wicket.ajax.markup.html.form.AjaxButton {

    private WicketBiConsumer<AjaxButton, AjaxRequestTarget> onSubmit;

    private WicketBiConsumer<AjaxButton, AjaxRequestTarget> onError;

    public AjaxButton(String id) {
        super(id);
    }

    public AjaxButton(String id, IModel<String> model) {
        super(id, model);
    }

    public AjaxButton(String id, Form<?> form) {
        super(id, form);
    }

    public AjaxButton(String id, IModel<String> model, Form<?> form) {
        super(id, model, form);
    }

    @Override
    protected void onSubmit(AjaxRequestTarget target) {
        if (this.onSubmit != null) {
            this.onSubmit.accept(this, target);
        }
    }

    @Override
    protected final void onError(AjaxRequestTarget target) {
        if (this.onError != null) {
            this.onError.accept(this, target);
        }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        setOutputMarkupId(true);
    }

    public void setOnSubmit(WicketBiConsumer<AjaxButton, AjaxRequestTarget> onSubmit) {
        this.onSubmit = onSubmit;
    }

    public void setOnError(WicketBiConsumer<AjaxButton, AjaxRequestTarget> onError) {
        this.onError = onError;
    }
}
