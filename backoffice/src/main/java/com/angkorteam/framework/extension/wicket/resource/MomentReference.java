package com.angkorteam.framework.extension.wicket.resource;

import com.angkorteam.framework.extension.ResourceScope;
import org.apache.wicket.Application;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.PackageResourceReference;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by socheat on 6/25/16.
 */
public class MomentReference {

    public static final Javascript JAVASCRIPT = new Javascript();

    private static class Javascript extends PackageResourceReference {
        /**
         *
         */
        private static final long serialVersionUID = 6456839006034673443L;

        public Javascript() {
            super(ResourceScope.class, "moment/2.13.0/moment-with-locales.min.js");
        }

        @Override
        public List<HeaderItem> getDependencies() {
            List<HeaderItem> items = new LinkedList<>();
            items.add(JavaScriptHeaderItem.forReference(Application.get().getJavaScriptLibrarySettings().getJQueryReference()));
            return items;
        }

    }
}
