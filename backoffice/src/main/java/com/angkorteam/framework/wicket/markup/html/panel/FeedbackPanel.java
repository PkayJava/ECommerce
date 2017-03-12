package com.angkorteam.framework.wicket.markup.html.panel;

import org.apache.wicket.feedback.IFeedbackMessageFilter;

/**
 * Created by socheat on 3/16/15.
 */
public class FeedbackPanel extends org.apache.wicket.markup.html.panel.FeedbackPanel {

    public FeedbackPanel(String id) {
        super(id);
    }

    public FeedbackPanel(String id, IFeedbackMessageFilter filter) {
        super(id, filter);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        setOutputMarkupId(true);
    }
}
