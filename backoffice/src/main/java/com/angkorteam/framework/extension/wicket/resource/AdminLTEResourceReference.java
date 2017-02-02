package com.angkorteam.framework.extension.wicket.resource;

import com.angkorteam.framework.extension.ResourceScope;
import org.apache.wicket.Application;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.StringHeaderItem;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.UrlResourceReference;

import java.util.LinkedList;
import java.util.List;

public class AdminLTEResourceReference {

    public static final Javascript JAVASCRIPT = new Javascript();

    private static class Javascript extends PackageResourceReference {
        private static final long serialVersionUID = 4037301510391116867L;

        public Javascript() {
            super(ResourceScope.class, "AdminLTE/dist/js/app.min.js");
        }

        @Override
        public List<HeaderItem> getDependencies() {
            List<HeaderItem> items = new LinkedList<>();
            items.add(JavaScriptHeaderItem.forReference(Application.get().getJavaScriptLibrarySettings().getJQueryReference()));
            items.add(JavaScriptHeaderItem.forReference(BootstrapResourceReference.JAVASCRIPT));
            items.add(StringHeaderItem.forString("<!--[if lt IE 9]>\n"));
            items.add(JavaScriptHeaderItem.forReference(new UrlResourceReference(Url.parse("https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"))));
            items.add(JavaScriptHeaderItem.forReference(new UrlResourceReference(Url.parse("https://oss.maxcdn.com/respond/1.4.2/respond.min.js"))));
            items.add(StringHeaderItem.forString("<![endif]-->\n"));
            return items;
        }
    }

    public static final SlimScrollJavascript SLIM_SCROLL_JAVASCRIPT = new SlimScrollJavascript();

    private static class SlimScrollJavascript extends PackageResourceReference {
        private static final long serialVersionUID = -6051027241848636445L;

        public SlimScrollJavascript() {
            super(ResourceScope.class, "AdminLTE/plugins/slimScroll/jquery.slimscroll.min.js");
        }

        @Override
        public List<HeaderItem> getDependencies() {
            List<HeaderItem> items = new LinkedList<>();
            items.add(JavaScriptHeaderItem.forReference(Application.get().getJavaScriptLibrarySettings().getJQueryReference()));
            return items;
        }
    }

    public static final Select2Javascript SELECT_2_JAVASCRIPT = new Select2Javascript();

    private static class Select2Javascript extends PackageResourceReference {
        private static final long serialVersionUID = -6051027241848636445L;

        public Select2Javascript() {
            super(ResourceScope.class, "AdminLTE/plugins/select2/select2.full.min.js");
        }

        @Override
        public List<HeaderItem> getDependencies() {
            LinkedList items = new LinkedList();
            items.add(JavaScriptHeaderItem.forReference(Application.get().getJavaScriptLibrarySettings().getJQueryReference()));
            return items;
        }
    }

    public static final Select2Style SELECT_2_STYLE = new Select2Style();

    private static class Select2Style extends PackageResourceReference {
        private static final long serialVersionUID = -6051027241848636445L;

        public Select2Style() {
            super(ResourceScope.class, "AdminLTE/plugins/select2/select2.min.css");
        }
    }

    public static final FontAwesomeStyle FONT_AWESOME_STYLE = new FontAwesomeStyle();

    private static class FontAwesomeStyle extends UrlResourceReference {
        private static final long serialVersionUID = -6051027241848636445L;

        public FontAwesomeStyle() {
            super(Url.parse("https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css"));
        }
    }

    public static final IonIconStyle ION_ICON_STYLE = new IonIconStyle();

    private static class IonIconStyle extends UrlResourceReference {
        private static final long serialVersionUID = -6051027241848636445L;

        public IonIconStyle() {
            super(Url.parse("https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css"));
        }
    }

    public static final Style STYLE = new Style();

    private static class Style extends PackageResourceReference {
        private static final long serialVersionUID = -6051027241848636445L;

        public Style() {
            super(ResourceScope.class, "AdminLTE/dist/css/AdminLTE.min.css");
        }
    }

    public static final SkinStyle SKIN_STYLE = new SkinStyle();

    private static class SkinStyle extends PackageResourceReference {
        private static final long serialVersionUID = -6051027241848636445L;

        public SkinStyle() {
            super(ResourceScope.class, "AdminLTE/dist/css/skins/_all-skins.min.css");
        }
    }

    public static final DatePickerStyle DATE_PICKER_STYLE = new DatePickerStyle();

    private static class DatePickerStyle extends PackageResourceReference {
        private static final long serialVersionUID = -6051021241848636445L;

        public DatePickerStyle() {
            super(ResourceScope.class, "AdminLTE/plugins/datepicker/datepicker3.css");
        }
    }

    public static final DatePickerJavascript DATE_PICKER_JAVASCRIPT = new DatePickerJavascript();

    private static class DatePickerJavascript extends PackageResourceReference {
        private static final long serialVersionUID = -6051027241848636445L;

        public DatePickerJavascript() {
            super(ResourceScope.class, "AdminLTE/plugins/datepicker/bootstrap-datepicker.js");
        }

        @Override
        public List<HeaderItem> getDependencies() {
            LinkedList items = new LinkedList();
            items.add(JavaScriptHeaderItem.forReference(Application.get().getJavaScriptLibrarySettings().getJQueryReference()));
            items.add(JavaScriptHeaderItem.forReference(BootstrapResourceReference.JAVASCRIPT));
            return items;
        }
    }

    public static final ColorPickerStyle COLOR_PICKER_STYLE = new ColorPickerStyle();

    private static class ColorPickerStyle extends PackageResourceReference {
        private static final long serialVersionUID = -6051021241848636445L;

        public ColorPickerStyle() {
            super(ResourceScope.class, "AdminLTE/plugins/colorpicker/bootstrap-colorpicker.min.css");
        }
    }

    public static final ColorPickerJavascript COLOR_PICKER_JAVASCRIPT = new ColorPickerJavascript();

    private static class ColorPickerJavascript extends PackageResourceReference {
        private static final long serialVersionUID = -6051027241848636445L;

        public ColorPickerJavascript() {
            super(ResourceScope.class, "AdminLTE/plugins/colorpicker/bootstrap-colorpicker.min.js");
        }

        @Override
        public List<HeaderItem> getDependencies() {
            LinkedList items = new LinkedList();
            items.add(JavaScriptHeaderItem.forReference(Application.get().getJavaScriptLibrarySettings().getJQueryReference()));
            items.add(JavaScriptHeaderItem.forReference(BootstrapResourceReference.JAVASCRIPT));
            return items;
        }
    }

    public static final TimePickerStyle TIME_PICKER_STYLE = new TimePickerStyle();

    private static class TimePickerStyle extends PackageResourceReference {
        private static final long serialVersionUID = -6051021241848636445L;

        public TimePickerStyle() {
            super(ResourceScope.class, "AdminLTE/plugins/timepicker/bootstrap-timepicker.min.css");
        }
    }

    public static final TimePickerJavascript TIME_PICKER_JAVASCRIPT = new TimePickerJavascript();

    private static class TimePickerJavascript extends PackageResourceReference {
        private static final long serialVersionUID = -6051027241848636445L;

        public TimePickerJavascript() {
            super(ResourceScope.class, "AdminLTE/plugins/timepicker/bootstrap-timepicker.min.js");
        }

        @Override
        public List<HeaderItem> getDependencies() {
            LinkedList items = new LinkedList();
            items.add(JavaScriptHeaderItem.forReference(Application.get().getJavaScriptLibrarySettings().getJQueryReference()));
            items.add(JavaScriptHeaderItem.forReference(Application.get().getJavaScriptLibrarySettings().getJQueryReference()));
            return items;
        }
    }
}