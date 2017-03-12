package com.angkorteam.platform.page;

import com.angkorteam.framework.wicket.AdminLTEPage;
import com.angkorteam.platform.Application;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;

/**
 * Created by socheat on 10/23/16.
 */
public class LogoutPage extends AdminLTEPage {


    @Override
    protected void onInitialize() {
        super.onInitialize();
        AbstractAuthenticatedWebSession.get().invalidateNow();
        setResponsePage(Application.get().getHomePage());
    }

}
