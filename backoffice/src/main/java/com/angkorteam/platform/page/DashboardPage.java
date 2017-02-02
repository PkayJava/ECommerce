package com.angkorteam.platform.page;

import com.angkorteam.platform.page.MBaaSPage;
import org.apache.wicket.markup.html.border.Border;

/**
 * Created by socheat on 3/1/16.
 */
public class DashboardPage extends MBaaSPage {

    @Override
    protected void doInitialize(Border layout) {
        add(layout);
    }

}
