package com.angkorteam.framework.extension.wicket.markup.html;

import com.angkorteam.framework.extension.wicket.resource.FullCalendarReference;
import com.google.gson.Gson;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.wicket.IRequestListener;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Created by socheat on 6/25/16.
 */
public class FullCalendar extends WebComponent implements IRequestListener {

    private FullCalendarProvider provider;

    public FullCalendar(String id, FullCalendarProvider provider) {
        super(id);
        this.provider = provider;
        setOutputMarkupId(true);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        String url = this.urlForListener(new PageParameters()).toString();
        response.render(CssHeaderItem.forReference(FullCalendarReference.STYLE));
        response.render(CssHeaderItem.forReference(FullCalendarReference.STYLE_PRINT, "print"));
        response.render(JavaScriptHeaderItem.forReference(FullCalendarReference.JAVASCRIPT));
        String javascript = "$('#" + getMarkupId(true) + "').fullCalendar({events: {url: '" + url + "'}, header: {left: 'prev,next today',center: 'title',right: 'month,basicWeek,basicDay'}, defaultDate: '" + DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.format(new Date()) + "', editable: false, eventLimit: true})";
        response.render(OnDomReadyHeaderItem.forScript(javascript));
    }

    @Override
    public void onRequest() {
        RequestCycle requestCycle = RequestCycle.get();
        Request request = requestCycle.getRequest();
        IRequestParameters params = request.getRequestParameters();

        Gson gson = provider.getGson();

        String start = params.getParameterValue("start").toString("");
        String end = params.getParameterValue("end").toString("");

        Date startDate = null;
        try {
            startDate = DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.parse(start);
        } catch (ParseException e) {
            throw new WicketRuntimeException(e);
        }
        Date endDate = null;
        try {
            endDate = DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.parse(end);
        } catch (ParseException e) {
            throw new WicketRuntimeException(e);
        }

        List<FullCalendarItem> options = this.provider.doQuery(startDate, endDate);

        WebResponse webResponse = (WebResponse) requestCycle.getResponse();
        webResponse.setContentType("application/json");

        OutputStreamWriter stream = new OutputStreamWriter(webResponse.getOutputStream(), getRequest().getCharset());
        try {
            stream.write(gson.toJson(options));
            stream.flush();
        } catch (IOException e) {
        }
    }

    @Override
    public boolean rendersPage() {
        return false;
    }
}
