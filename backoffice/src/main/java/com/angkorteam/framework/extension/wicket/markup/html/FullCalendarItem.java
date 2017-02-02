package com.angkorteam.framework.extension.wicket.markup.html;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by socheat on 6/26/16.
 */
public class FullCalendarItem implements Serializable {

    @Expose
    @SerializedName("end")
    private String end;

    @Expose
    @SerializedName("id")
    private String id;

    @Expose
    @SerializedName("resourceId")
    private String resourceId;

    @Expose
    @SerializedName("start")
    private String start;

    @Expose
    @SerializedName("title")
    private String title;

    @Expose
    @SerializedName("borderColor")
    private String borderColor;

    @Expose
    @SerializedName("backgroundColor")
    private String backgroundColor;

    @Expose
    @SerializedName("allDay")
    private boolean allDay;

    @Expose
    @SerializedName("url")
    private String url;

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public boolean isAllDay() {
        return allDay;
    }

    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
