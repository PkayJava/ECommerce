package com.angkorteam.framework.jdbc;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by socheatkhauv on 29/1/17.
 */
public abstract class WhereQuery extends ParamQuery {

    protected final List<String> where = Lists.newLinkedList();

    public void addWhere(String criteria) {
        if (criteria.contains(":")) {
            throw new RuntimeException(": is invalid");
        }
        this.where.add(criteria);
        this.dirty = true;
    }

    public void addWhere(String criteria, Object value) {
        addInternalCriteria(this.where, this.param, criteria, value);
    }

    public void addWhere(String criteria, String paramName, Object paramValue) {
        this.where.add(criteria);
        this.param.put(paramName, paramValue);
        this.dirty = true;
    }

    public <T> void addWhere(String criteria, Class<T> typeClass, List<T> values) {
        addInternalCriteria(this.where, this.param, criteria, typeClass, values);
    }

    public <T> void addWhere(String criteria, Class<T> typeClass, T value1, T value2) {
        addInternalCriteria(this.where, this.param, criteria, typeClass, value1, value2);
    }

    protected <T> void addInternalCriteria(List<String> list, Map<String, Object> listParam, String criteria, Class<T> typeClass, T value1, T value2) {
        if (typeClass == Boolean.class || typeClass == boolean.class
                || typeClass == Byte.class || typeClass == byte.class
                || typeClass == Short.class || typeClass == short.class
                || typeClass == Integer.class || typeClass == int.class
                || typeClass == Long.class || typeClass == long.class
                || typeClass == Float.class || typeClass == float.class
                || typeClass == Double.class || typeClass == double.class
                || typeClass == Date.class
                || typeClass == Character.class || typeClass == char.class
                || typeClass == String.class) {
        } else {
            throw new RuntimeException(typeClass + " is not support");
        }
        boolean beginColon = false;
        String paramName = "";
        int paramIndex = 0;
        boolean value1Param = false;
        boolean value2Param = false;
        for (Character ch : criteria.toCharArray()) {
            if (ch == ':') {
                beginColon = true;
                paramIndex++;
                continue;
            }
            if (beginColon) {
                if (Character.isDigit(ch) || CharacterExtension.isAlphabet(ch) || ch == '_') {
                    paramName += ch;
                } else {
                    if (!Strings.isNullOrEmpty(paramName)) {
                        if (paramIndex == 1) {
                            listParam.put(paramName, value1);
                            value1Param = true;
                        } else if (paramIndex == 2) {
                            listParam.put(paramName, value2);
                            value2Param = true;
                        }
                        paramName = "";
                    }
                }
            }
        }
        if (paramIndex == 1) {
            listParam.put(paramName, value1);
            value1Param = true;
        } else if (paramIndex == 2) {
            listParam.put(paramName, value2);
            value2Param = true;
        }
        if (!value1Param && !value2Param) {
            throw new RuntimeException("parameter for value1/value2 must be defined");
        }
        if (!value1Param) {
            throw new RuntimeException("parameter for value1 must be defined");
        }
        if (!value2Param) {
            throw new RuntimeException("parameter for value2 must be defined");
        }
        list.add(criteria);
        this.dirty = true;
    }

    protected void addInternalCriteria(List<String> list, Map<String, Object> listParam, String criteria, Object value) {
        if (value != null) {
            if (value instanceof Boolean
                    || value instanceof Byte
                    || value instanceof Short
                    || value instanceof Integer
                    || value instanceof Long
                    || value instanceof Float
                    || value instanceof Double
                    || value instanceof Date
                    || value instanceof Character
                    || value instanceof String) {
            } else {
                throw new RuntimeException(value + " is not support");
            }
        }
        boolean beginColon = false;
        String paramName = "";
        boolean valueParam = false;
        for (Character ch : criteria.toCharArray()) {
            if (ch == ':') {
                beginColon = true;
                continue;
            }
            if (beginColon) {
                if (Character.isDigit(ch) || CharacterExtension.isAlphabet(ch) || ch == '_') {
                    paramName += ch;
                } else {
                    if (!Strings.isNullOrEmpty(paramName)) {
                        listParam.put(paramName, value);
                        paramName = "";
                        valueParam = true;
                    }
                    break;
                }
            }
        }
        if (!Strings.isNullOrEmpty(paramName)) {
            listParam.put(paramName, value);
            valueParam = true;
        }
        if (!valueParam) {
            throw new RuntimeException("parameter for value must be defined");
        }
        list.add(criteria);
        this.dirty = true;
    }

    protected <T> void addInternalCriteria(List<String> list, Map<String, Object> listParam, String criteria, Class<T> typeClass, List<T> values) {
        if (typeClass == Boolean.class || typeClass == boolean.class
                || typeClass == Byte.class || typeClass == byte.class
                || typeClass == Short.class || typeClass == short.class
                || typeClass == Integer.class || typeClass == int.class
                || typeClass == Long.class || typeClass == long.class
                || typeClass == Float.class || typeClass == float.class
                || typeClass == Double.class || typeClass == double.class
                || typeClass == Date.class
                || typeClass == Character.class || typeClass == char.class
                || typeClass == String.class) {
        } else {
            throw new RuntimeException(typeClass + " is not support");
        }

        boolean beginColon = false;
        String paramName = "";
        boolean valueParam = false;
        for (Character ch : criteria.toCharArray()) {
            if (ch == ':') {
                beginColon = true;
                continue;
            }
            if (beginColon) {
                if (Character.isDigit(ch) || CharacterExtension.isAlphabet(ch) || ch == '_') {
                    paramName += ch;
                } else {
                    if (!Strings.isNullOrEmpty(paramName)) {
                        listParam.put(paramName, values);
                        paramName = "";
                        valueParam = true;
                    }
                    break;
                }
            }
        }
        if (!Strings.isNullOrEmpty(paramName)) {
            listParam.put(paramName, values);
            valueParam = true;
        }
        if (!valueParam) {
            throw new RuntimeException("parameter for values must be defined");
        }
        list.add(criteria);
        this.dirty = true;
    }

}
