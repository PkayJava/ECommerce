package com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * Created by socheat on 5/14/15.
 */
public class DownloadPanel extends Panel {

    private ExternalLink link;

    private String href;

    public DownloadPanel(String id, String href) {
        super(id);
        this.href = href;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        this.link = new ExternalLink("link", this.href, "Download");
        this.link.add(AttributeModifier.replace("target", "_blank"));
        add(this.link);
    }
}
