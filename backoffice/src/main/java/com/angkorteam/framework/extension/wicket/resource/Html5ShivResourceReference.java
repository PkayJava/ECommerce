package com.angkorteam.framework.extension.wicket.resource;

import com.angkorteam.framework.extension.ResourceScope;
import org.apache.wicket.request.resource.PackageResourceReference;

public class Html5ShivResourceReference {

    public static final Javascript JAVASCRIPT = new Javascript();

    private static class Javascript extends PackageResourceReference {
        /**
         *
         */
        private static final long serialVersionUID = 877348883908975427L;

        public Javascript() {
            super(ResourceScope.class, "html5shiv/3.7.2/html5shiv.min.js");
        }
    }

}