package com.angkorteam.framework.extension.wicket.extensions.markup.html.repeater.data.table.filter;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * Created by socheat on 5/14/15.
 */
public class ImagePanel extends Panel {

    private WebMarkupContainer image;

    private String streamAddress;

    public ImagePanel(String id, String streamAddress) {
        super(id);
        this.streamAddress = streamAddress;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        this.image = new WebMarkupContainer("image");
        this.image.add(AttributeModifier.replace("src", this.streamAddress));
        add(this.image);
    }
}
