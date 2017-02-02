package com.angkorteam.ecommerce.page.order;

import com.angkorteam.platform.page.MBaaSPage;
import org.apache.wicket.markup.html.border.Border;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by socheatkhauv on 26/1/17.
 */
public class OrderDetailPage extends MBaaSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderDetailPage.class);

    @Override
    protected void doInitialize(Border layout) {
        add(layout);
        // place your initialization logic here
    }

}