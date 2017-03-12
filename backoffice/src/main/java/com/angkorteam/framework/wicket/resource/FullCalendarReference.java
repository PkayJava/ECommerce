package com.angkorteam.framework.wicket.resource;

import com.angkorteam.framework.ResourceScope;
import org.apache.wicket.Application;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.PackageResourceReference;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by socheat on 6/25/16.
 */
public class FullCalendarReference {

    public static final Javascript JAVASCRIPT = new Javascript();

    public static final Style STYLE = new Style();

    public static final StylePrint STYLE_PRINT = new StylePrint();

    private static class Javascript extends PackageResourceReference {
        /**
         *
         */
        private static final long serialVersionUID = 6456839006034673443L;

        public Javascript() {
            super(ResourceScope.class, "AdminLTE/plugins/fullcalendar/fullcalendar.min.js");
        }

        @Override
        public List<HeaderItem> getDependencies() {
            List<HeaderItem> items = new LinkedList<>();
            items.add(JavaScriptHeaderItem.forReference(MomentReference.JAVASCRIPT));
            items.add(JavaScriptHeaderItem.forReference(Application.get().getJavaScriptLibrarySettings().getJQueryReference()));
            return items;
        }

    }

    private static class Style extends PackageResourceReference {
        /**
         *
         */
        private static final long serialVersionUID = -6051027241848636445L;

        public Style() {
            super(ResourceScope.class, "AdminLTE/plugins/fullcalendar/fullcalendar.min.css");
        }
    }

    private static class StylePrint extends PackageResourceReference {
        /**
         *
         */
        private static final long serialVersionUID = -6051027241848636445L;

        public StylePrint() {
            super(ResourceScope.class, "AdminLTE/plugins/fullcalendar/fullcalendar.print.css");
        }
    }

}
