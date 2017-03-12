package com.angkorteam.framework.wicket.resource;

import com.angkorteam.framework.ResourceScope;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.request.resource.PackageResourceReference;

import java.util.LinkedList;
import java.util.List;

public class BootstrapSubmenuResourceReference {

    public static final Javascript JAVASCRIPT = new Javascript();

    public static final Style STYLE = new Style();

    private static class Style extends PackageResourceReference {
        /**
         *
         */
        private static final long serialVersionUID = -6051027241848636445L;

        public Style() {
            super(ResourceScope.class, "bootstrap-submenu-2.0.1/css/bootstrap-submenu.min.css");
        }
    }

    private static class Javascript extends PackageResourceReference {

        /**
         *
         */
        private static final long serialVersionUID = 4037301510391116867L;

        public Javascript() {
            super(ResourceScope.class, "bootstrap-submenu-2.0.1/js/bootstrap-submenu.min.js");
        }

        @Override
        public List<HeaderItem> getDependencies() {
            List<HeaderItem> items = new LinkedList<>();
            items.add(JavaScriptHeaderItem.forReference(BootstrapResourceReference.JAVASCRIPT));
            items.add(OnDomReadyHeaderItem.forScript("$('.dropdown-submenu > a').submenupicker();"));
            return items;
        }
    }
}