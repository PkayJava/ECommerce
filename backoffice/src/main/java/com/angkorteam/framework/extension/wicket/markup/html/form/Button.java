package com.angkorteam.framework.extension.wicket.markup.html.form;

import com.angkorteam.framework.extension.wicket.WicketConsumer;
import org.apache.wicket.model.IModel;

/**
 * Created by socheat on 10/20/15.
 */
public class Button extends org.apache.wicket.markup.html.form.Button {

    private WicketConsumer<Button> onSubmit;

    private WicketConsumer<Button> onError;

    public Button(String id) {
        super(id);
    }

    public Button(String id, IModel<String> model) {
        super(id, model);
    }

    public void setOnSubmit(WicketConsumer<Button> onSubmit) {
        this.onSubmit = onSubmit;
    }

    public void setOnError(WicketConsumer<Button> onError) {
        this.onError = onError;
    }

    @Override
    public final void onSubmit() {
        if (this.onSubmit != null) {
            this.onSubmit.accept(this);
        }
    }

    @Override
    public final void onError() {
        if (this.onError != null) {
            this.onError.accept(this);
        }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        setOutputMarkupId(true);
    }
}
