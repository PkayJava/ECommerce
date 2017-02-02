package com.angkorteam.framework.extension.wicket.resource;

import com.angkorteam.framework.extension.ResourceScope;
import org.apache.wicket.Application;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.PackageResourceReference;

import java.util.LinkedList;
import java.util.List;

public class BootstrapResourceReference {

    public static final Javascript JAVASCRIPT = new Javascript();

    public static final Style STYLE = new Style();

    private static class Style extends PackageResourceReference {
        /**
         *
         */
        private static final long serialVersionUID = -6051027241848636445L;

        public Style() {
            super(ResourceScope.class, "AdminLTE/bootstrap/css/bootstrap.min.css");
        }
    }

    private static class Javascript extends PackageResourceReference {

        /**
         *
         */
        private static final long serialVersionUID = 4037301510391116867L;

        public Javascript() {
            super(ResourceScope.class, "AdminLTE/bootstrap/js/bootstrap.min.js");
        }

        @Override
        public List<HeaderItem> getDependencies() {
            List<HeaderItem> items = new LinkedList<>();
            items.add(JavaScriptHeaderItem.forReference(Application.get().getJavaScriptLibrarySettings().getJQueryReference()));
            return items;
        }
    }
}