package com.angkorteam.framework.extension.wicket.markup.html.form;

import com.angkorteam.framework.extension.wicket.WicketConsumer;
import org.apache.wicket.model.IModel;

/**
 * Created by socheat on 3/4/16.
 */
public class Form<T> extends org.apache.wicket.markup.html.form.Form<T> {

    private WicketConsumer<Form> onSubmit;
    private WicketConsumer<Form> onError;

    public Form(String id) {
        super(id);
    }

    public Form(String id, IModel<T> model) {
        super(id, model);
    }

    @Override
    protected final void onSubmit() {
        if (this.onSubmit != null) {
            this.onSubmit.accept(this);
        }
    }

    @Override
    protected final void onError() {
        if (this.onError != null) {
            this.onError.accept(this);
        }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        setOutputMarkupId(true);
    }

    public void setOnSubmit(WicketConsumer<Form> onSubmit) {
        this.onSubmit = onSubmit;
    }

    public void setOnError(WicketConsumer<Form> onError) {
        this.onError = onError;
    }

}
