package com.angkorteam.framework.wicket.markup.html;

import com.google.gson.Gson;
import org.apache.wicket.util.io.IClusterable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by socheat on 6/26/16.
 */
public abstract class FullCalendarProvider implements IClusterable {

    public List<FullCalendarItem> doQuery(Date start, Date end) {
        List<FullCalendarItem> items = query(start, end);
        if (items == null) {
            return new ArrayList<>();
        } else {
            return items;
        }
    }

    public abstract List<FullCalendarItem> query(Date start, Date end);

    public abstract Gson getGson();
}
