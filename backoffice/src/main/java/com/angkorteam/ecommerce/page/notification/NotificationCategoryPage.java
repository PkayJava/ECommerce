package com.angkorteam.ecommerce.page.notification;

import com.angkorteam.platform.page.MBaaSPage;
import org.apache.wicket.markup.html.border.Border;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by socheatkhauv on 26/1/17.
 */
public class NotificationCategoryPage extends MBaaSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationCategoryPage.class);

    @Override
    protected void doInitialize(Border layout) {
        add(layout);
        // place your initialization logic here
    }

}