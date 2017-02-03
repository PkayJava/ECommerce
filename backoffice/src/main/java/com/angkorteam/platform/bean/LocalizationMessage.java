package com.angkorteam.platform.bean;

import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
import com.angkorteam.platform.Platform;
import com.google.common.base.Strings;
import org.apache.wicket.Component;
import org.apache.wicket.Localizer;
import org.apache.wicket.model.IModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.MissingResourceException;

/**
 * Created by socheatkhauv on 21/1/17.
 */
public class LocalizationMessage extends Localizer {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocalizationMessage.class);

    @Override
    public String getString(String key, Component component) throws MissingResourceException {
        LOGGER.debug("1 key {} component {}", key, component.getClass().getName());
        return super.getString(key, component);
    }

    @Override
    public String getString(String key, Component component, String defaultValue) throws MissingResourceException {
        LOGGER.debug("2 key {} component {} defaultValue {}", key, component.getClass().getName(), defaultValue);
        return super.getString(key, component, defaultValue);
    }

    @Override
    public String getString(String key, Component component, IModel<?> model) throws MissingResourceException {
        LOGGER.debug("3 key {} component {} model {}", key, component.getClass().getName(), model == null ? null : model.getObject());
        return super.getString(key, component, model);
    }

    @Override
    public String getString(String key, Component component, IModel<?> model, String defaultValue) throws MissingResourceException {
        LOGGER.debug("4 key {} component {} model {} defaultValue {}", key, component.getClass().getName(), model == null ? null : model.getObject(), defaultValue);
        return super.getString(key, component, model, defaultValue);
    }

    @Override
    public String getString(String key, Component component, IModel<?> model, Locale locale, String style, String defaultValue) throws MissingResourceException {
        NamedParameterJdbcTemplate named = Platform.getBean(NamedParameterJdbcTemplate.class);
        SelectQuery selectQuery = new SelectQuery("platform_localization");
        selectQuery.addField("label");
        selectQuery.addWhere("lower(`key`) = lower(:key)", key);
        if (locale != null) {
            selectQuery.addWhere("lower(language) = lower(:language)", locale.getLanguage());
        } else {
            selectQuery.addWhere("lower(language) = lower(:language)", "");
        }
        String message = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), String.class);
        if (Strings.isNullOrEmpty(message)) {
            if (locale == null) {
                LOGGER.debug("key {} is not found", key);
            } else {
                LOGGER.debug("key {} language {} is not found", key, locale.getLanguage());
            }
            return key;
        }
        return message;
    }

    @Override
    public String getString(String key, Component component, IModel<?> model, Locale locale, String style, IModel<String> defaultValue) throws MissingResourceException {
        LOGGER.debug("6 key {} component {} model {} locale {} style {} defaultValue {}", key, component.getClass().getName(), model == null ? null : model.getObject(), locale == null ? null : locale.getLanguage(), style, defaultValue == null ? null : defaultValue.getObject());
        return super.getString(key, component, model, locale, style, defaultValue);
    }

    @Override
    protected String getCacheKey(String key, Component component, Locale locale, String style, String variation) {
        LOGGER.debug("7 key {} component {} locale {} style {} variation {}", key, component.getClass().getName(), locale == null ? null : locale.getLanguage(), style, variation);
        return super.getCacheKey(key, component, locale, style, variation);
    }

    @Override
    protected String getFromCache(String cacheKey) {
        LOGGER.debug("8 cacheKey {}", cacheKey);
        return super.getFromCache(cacheKey);
    }

    @Override
    public String getStringIgnoreSettings(String key, Component component, IModel<?> model, String defaultValue) {
        LOGGER.debug("9 key {} component {} model {} defaultValue {}", key, component.getClass().getName(), model == null ? null : model.getObject(), defaultValue);
        return super.getStringIgnoreSettings(key, component, model, defaultValue);
    }

    @Override
    public String getStringIgnoreSettings(String key, Component component, IModel<?> model, Locale locale, String style, String defaultValue) {
        LOGGER.debug("10 key {} component {} model {} locale {} style {} defaultValue {}", key, component.getClass().getName(), model == null ? null : model.getObject(), locale == null ? null : locale.getLanguage(), style, defaultValue);
        return super.getStringIgnoreSettings(key, component, model, locale, style, defaultValue);
    }
}
