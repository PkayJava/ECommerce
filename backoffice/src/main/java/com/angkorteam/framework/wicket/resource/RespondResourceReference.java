package com.angkorteam.framework.wicket.resource;

import com.angkorteam.framework.ResourceScope;
import org.apache.wicket.request.resource.PackageResourceReference;

public class RespondResourceReference {

    public static final Javascript JAVASCRIPT = new Javascript();

    private static class Javascript extends PackageResourceReference {
        /**
         *
         */
        private static final long serialVersionUID = 6192006916739523698L;

        public Javascript() {
            super(ResourceScope.class, "respond/1.4.2/respond.min.js");
        }
    }

}