package com.angkorteam.framework.wicket.markup.html.form.select2;

import com.google.gson.Gson;
import org.apache.wicket.markup.html.form.IChoiceRenderer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by socheat on 5/25/16.
 */
public abstract class IChoiceProvider<T> implements IChoiceRenderer<T>, Serializable {

    public static int LIMIT = 10;

    public abstract List<Option> query(String term, int page);

    public abstract Gson getGson();

    public abstract boolean hasMore(String term, int page);

    public final List<Option> doQuery(String term, int page) {
        List<Option> options = query(term, page);
        if (options == null) {
            return new ArrayList<>();
        } else {
            return options;
        }
    }

}
