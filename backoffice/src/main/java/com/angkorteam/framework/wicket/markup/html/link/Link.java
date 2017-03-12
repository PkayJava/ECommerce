package com.angkorteam.framework.wicket.markup.html.link;

import com.angkorteam.framework.wicket.WicketConsumer;
import org.apache.wicket.model.IModel;

/**
 * Created by socheat on 3/12/16.
 */
public class Link<T> extends org.apache.wicket.markup.html.link.Link<T> {

    private WicketConsumer<Link> onClick;

    public Link(String id) {
        super(id);
    }

    public Link(String id, IModel<T> model) {
        super(id, model);
    }

    public void setOnClick(WicketConsumer<Link> onClick) {
        this.onClick = onClick;
    }

    @Override
    public void onClick() {
        if (this.onClick != null) {
            this.onClick.accept(this);
        }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        setOutputMarkupId(true);
    }
}
