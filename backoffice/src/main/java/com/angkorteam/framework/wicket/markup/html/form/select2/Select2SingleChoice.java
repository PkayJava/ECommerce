package com.angkorteam.framework.wicket.markup.html.form.select2;

import com.angkorteam.framework.wicket.resource.AdminLTEResourceReference;
import com.google.gson.Gson;
import org.apache.wicket.IRequestListener;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.string.AppendingStringBuffer;
import org.apache.wicket.util.string.Strings;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * Created by socheat on 7/25/15.
 */
public class Select2SingleChoice<T> extends FormComponent<T> implements IRequestListener {

    private final SingleChoiceProvider<T> provider;

    public Select2SingleChoice(String id, IModel<T> model, SingleChoiceProvider<T> provider) {
        super(id, model);
        this.provider = provider;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        setOutputMarkupId(true);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(CssHeaderItem.forReference(AdminLTEResourceReference.SELECT_2_STYLE));
        response.render(JavaScriptHeaderItem.forReference(AdminLTEResourceReference.SELECT_2_JAVASCRIPT));
        String markupId = getMarkupId();
        String url = this.urlForListener(new PageParameters()).toString();
        response.render(OnDomReadyHeaderItem.forScript("$('#" + markupId + "')." + "select2({ allowClear: true, placeholder: 'Select One', ajax: { url: '" + url + "', dataType: 'json', delay: 250, data: function (params) { return { q: params.term, page: params.page }; }, processResults: function (data, params) { params.page = params.page || 1; return { results: data.items, pagination: { more: data.more } }; }, cache: true }, escapeMarkup: function (markup) { return markup; }, minimumInputLength: 1});"));
    }

    @Override
    protected T convertValue(String[] value) throws ConversionException {
        if (value != null && value.length > 0) {
            return this.provider.toChoice(value[0]);
        } else {
            return null;
        }
    }

    @Override
    public void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag) {
        T choice = getModelObject();
        final AppendingStringBuffer buffer = new AppendingStringBuffer("");
        if (choice != null) {
            appendOptionHtml(buffer, choice, 0);
        }
        buffer.append('\n');
        replaceComponentTagBody(markupStream, openTag, buffer);
    }

    protected void appendOptionHtml(AppendingStringBuffer buffer, T choice, int index) {
        Object objectValue = this.provider.getDisplayValue(choice);
        Class<?> objectClass = (objectValue == null ? null : objectValue.getClass());

        String displayValue = "";
        if (objectClass != null && objectClass != String.class) {
            @SuppressWarnings("rawtypes")
            IConverter converter = getConverter(objectClass);
            displayValue = converter.convertToString(objectValue, getLocale());
        } else if (objectValue != null) {
            displayValue = objectValue.toString();
        }

        buffer.append("\n<option ");
        setOptionAttributes(buffer, choice, index);
        buffer.append('>');

        String display = displayValue;
        if (localizeDisplayValues()) {
            display = getLocalizer().getString(displayValue, this, displayValue);
        }

        CharSequence escaped = display;
        if (getEscapeModelStrings()) {
            escaped = escapeOptionHtml(display);
        }

        buffer.append(escaped);
        buffer.append("</option>");
    }

    protected CharSequence escapeOptionHtml(String displayValue) {
        return Strings.escapeMarkup(displayValue);
    }

    protected boolean localizeDisplayValues() {
        return false;
    }

    protected void setOptionAttributes(AppendingStringBuffer buffer, T choice, int index) {
        buffer.append("selected=\"selected\" ");
        buffer.append("value=\"");
        buffer.append(Strings.escapeMarkup(provider.getIdValue(choice, index)));
        buffer.append('"');
    }

    @Override
    public void onRequest() {
        RequestCycle requestCycle = RequestCycle.get();
        Request request = requestCycle.getRequest();
        IRequestParameters params = request.getRequestParameters();

        String term = params.getParameterValue("q").toOptionalString();
        int page = params.getParameterValue("page").toInt(1);

        Gson gson = this.provider.getGson();
        List<Option> options = this.provider.doQuery(term, page);

        Select2Response response = new Select2Response();
        response.setMore(this.provider.hasMore(term, page));
        response.setItems(options);

        WebResponse webResponse = (WebResponse) requestCycle.getResponse();
        webResponse.setContentType("application/json");

        OutputStreamWriter stream = new OutputStreamWriter(webResponse.getOutputStream(), getRequest().getCharset());
        try {
            stream.write(gson.toJson(response));
            stream.flush();
        } catch (IOException e) {
        }
    }

    @Override
    public boolean rendersPage() {
        return false;
    }
}