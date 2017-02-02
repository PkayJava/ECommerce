package com.angkorteam.framework.extension.wicket.markup.html.form;

import com.angkorteam.framework.extension.wicket.resource.AdminLTEResourceReference;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by socheat on 6/1/16.
 */
public class TimeTextField extends TextField<Date> {

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("hh:mm a");

    public TimeTextField(String id) {
        super(id, Date.class);
    }

    public TimeTextField(String id, IModel<Date> model) {
        super(id, model, Date.class);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        setOutputMarkupId(true);
    }

    @Override
    public <C> IConverter<C> getConverter(Class<C> type) {
        return (IConverter<C>) new Converter();
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        String markupId = this.getMarkupId(true);
        response.render(CssHeaderItem.forReference(AdminLTEResourceReference.TIME_PICKER_STYLE));
        response.render(JavaScriptHeaderItem.forReference(AdminLTEResourceReference.TIME_PICKER_JAVASCRIPT));
        response.render(OnDomReadyHeaderItem.forScript("$('#" + markupId + "').timepicker({showInputs: false})"));
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);
        String htmlClass = tag.getAttribute("class");
        if (htmlClass == null) {
            htmlClass = "bootstrap-timepicker";
        } else {
            htmlClass = htmlClass + " bootstrap-timepicker";
        }
        tag.put("class", htmlClass);
    }

    public static class Converter implements IConverter<Date> {

        @Override
        public Date convertToObject(String value, Locale locale) throws ConversionException {
            if (value == null || "".equals(value)) {
                return null;
            }
            try {
                return FORMAT.parse(value);
            } catch (ParseException e) {
                throw new ConversionException(e);
            }
        }

        @Override
        public String convertToString(Date value, Locale locale) {
            if (value == null) {
                return null;
            }
            return FORMAT.format(value);
        }

    }
}