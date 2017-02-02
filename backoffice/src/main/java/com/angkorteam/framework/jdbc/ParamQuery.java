package com.angkorteam.framework.jdbc;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by socheatkhauv on 29/1/17.
 */
public abstract class ParamQuery {

    protected boolean dirty = true;

    protected String cached = "";

    protected final Map<String, Object> param = Maps.newLinkedHashMap();

    public abstract String toSQL();

    public Map<String, Object> getParam() {
        return param;
    }

}
