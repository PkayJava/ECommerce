package com.angkorteam.framework.extension.wicket.extensions.markup.html.repeater.data.table.filter;

import org.apache.wicket.model.IModel;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 22/1/17.
 */
public class ActionItem implements Serializable, Comparable<ActionItem> {

    private final String link;

    private final IModel<String> label;

    private final ItemCss css;

    private final Integer order;

    public ActionItem(String link, IModel<String> label) {
        this.link = link;
        this.label = label;
        this.css = ItemCss.INFO;
        this.order = 0;
    }

    public ActionItem(String link, IModel<String> label, int order) {
        this.link = link;
        this.label = label;
        this.css = ItemCss.INFO;
        this.order = order;
    }

    public ActionItem(String link, IModel<String> label, ItemCss css) {
        this.link = link;
        this.label = label;
        this.css = css;
        this.order = 0;
    }

    public ActionItem(String link, IModel<String> label, ItemCss css, int order) {
        this.link = link;
        this.label = label;
        this.css = css;
        this.order = order;
    }

    public String getLink() {
        return link;
    }

    public IModel<String> getLabel() {
        return label;
    }

    public ItemCss getCss() {
        return css;
    }

    @Override
    public int compareTo(ActionItem o) {
        return this.order.compareTo(o.order);
    }
}
