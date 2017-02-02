package com.angkorteam.framework.extension.wicket.extensions.markup.html.repeater.data.table.filter;

import com.angkorteam.framework.extension.share.provider.TableProvider;
import com.angkorteam.framework.extension.wicket.WicketBiFunction;
import com.angkorteam.framework.extension.wicket.WicketTriConsumer;
import com.angkorteam.framework.extension.wicket.WicketTriFunction;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.export.IExportableColumn;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;

import java.util.Map;

/**
 * Created by socheat on 12/7/16.
 */
public class LambdaColumn extends AbstractColumn<Map<String, Object>, String>
        implements IExportableColumn<Map<String, Object>, String> {

    private final String identity;

    protected ItemClass itemClass;

    protected WicketBiFunction<String, Map<String, Object>, ?> function;

    protected WicketBiFunction<String, Map<String, Object>, ItemType> itemType;

    protected WicketBiFunction<String, Map<String, Object>, Boolean> clickable;

    protected WicketBiFunction<String, Map<String, Object>, ItemCss> itemCss;

    protected WicketBiFunction<String, Map<String, Object>, Boolean> progressBarActive;

    protected WicketTriConsumer<String, Map<String, Object>, AjaxRequestTarget> itemClick;

    protected WicketTriFunction<String, Map<String, Object>, String, ? extends Component> itemCustom;

    public LambdaColumn(TableProvider provider,
                        ItemClass itemClass,
                        IModel<String> displayModel,
                        String column) {
        this(provider, itemClass, displayModel, column, column, column, LambdaColumn::defaultFunction, LambdaColumn::defaultItemType, LambdaColumn::defaultClickable, LambdaColumn::defaultItemCss, LambdaColumn::defaultProgressBarActive, null, null);
    }

    public LambdaColumn(TableProvider provider,
                        ItemClass itemClass,
                        IModel<String> displayModel,
                        String column,
                        WicketBiFunction<String, Map<String, Object>, ?> function) {
        this(provider, itemClass, displayModel, column, column, column, function, LambdaColumn::defaultItemType, LambdaColumn::defaultClickable, LambdaColumn::defaultItemCss, LambdaColumn::defaultProgressBarActive, null, null);
    }

    public LambdaColumn(TableProvider provider,
                        ItemClass itemClass,
                        IModel<String> displayModel,
                        String column,
                        WicketBiFunction<String, Map<String, Object>, ?> function,
                        WicketBiFunction<String, Map<String, Object>, ItemType> itemType,
                        WicketBiFunction<String, Map<String, Object>, Boolean> clickable,
                        WicketBiFunction<String, Map<String, Object>, ItemCss> itemCss,
                        WicketBiFunction<String, Map<String, Object>, Boolean> progressBarActive,
                        WicketTriConsumer<String, Map<String, Object>, AjaxRequestTarget> itemClick) {
        this(provider, itemClass, displayModel, column, column, column, function, itemType, clickable, itemCss, progressBarActive, itemClick, null);
    }

    public LambdaColumn(TableProvider provider,
                        ItemClass itemClass,
                        IModel<String> displayModel,
                        String column,
                        WicketBiFunction<String, Map<String, Object>, ItemType> itemType,
                        WicketBiFunction<String, Map<String, Object>, Boolean> clickable,
                        WicketBiFunction<String, Map<String, Object>, ItemCss> itemCss,
                        WicketBiFunction<String, Map<String, Object>, Boolean> progressBarActive,
                        WicketTriConsumer<String, Map<String, Object>, AjaxRequestTarget> itemClick) {
        this(provider, itemClass, displayModel, column, column, column, LambdaColumn::defaultFunction, itemType, clickable, itemCss, progressBarActive, itemClick, null);
    }

    public LambdaColumn(TableProvider provider,
                        ItemClass itemClass,
                        IModel<String> displayModel,
                        String identity,
                        String column,
                        String sortProperty,
                        WicketBiFunction<String, Map<String, Object>, ItemType> itemType,
                        WicketBiFunction<String, Map<String, Object>, Boolean> clickable,
                        WicketBiFunction<String, Map<String, Object>, ItemCss> itemCss,
                        WicketBiFunction<String, Map<String, Object>, Boolean> progressBarActive,
                        WicketTriConsumer<String, Map<String, Object>, AjaxRequestTarget> itemClick,
                        WicketTriFunction<String, Map<String, Object>, String, ? extends Component> itemCustom) {
        this(provider, itemClass, displayModel, identity, column, sortProperty, LambdaColumn::defaultFunction, itemType, clickable, itemCss, progressBarActive, itemClick, itemCustom);
    }

    public LambdaColumn(TableProvider provider,
                        ItemClass itemClass,
                        IModel<String> displayModel,
                        String identity,
                        String column,
                        String sortProperty,
                        WicketBiFunction<String, Map<String, Object>, ?> function,
                        WicketBiFunction<String, Map<String, Object>, ItemType> itemType,
                        WicketBiFunction<String, Map<String, Object>, Boolean> clickable,
                        WicketBiFunction<String, Map<String, Object>, ItemCss> itemCss,
                        WicketBiFunction<String, Map<String, Object>, Boolean> progressBarActive,
                        WicketTriConsumer<String, Map<String, Object>, AjaxRequestTarget> itemClick,
                        WicketTriFunction<String, Map<String, Object>, String, ? extends Component> itemCustom) {
        super(displayModel, sortProperty);
        this.itemClass = itemClass;
        this.function = function;
        this.identity = identity;
        this.itemType = itemType;
        this.clickable = clickable;
        this.itemCss = itemCss;
        this.progressBarActive = progressBarActive;
        this.itemClick = itemClick;
        this.itemCustom = itemCustom;
        if (itemClass == ItemClass.Byte) {
            provider.selectField(column, Byte.class);
        } else if (itemClass == ItemClass.Short) {
            provider.selectField(column, Short.class);
        } else if (itemClass == ItemClass.Integer) {
            provider.selectField(column, Integer.class);
        } else if (itemClass == ItemClass.Long) {
            provider.selectField(column, Long.class);
        } else if (itemClass == ItemClass.Float) {
            provider.selectField(column, Float.class);
        } else if (itemClass == ItemClass.Double) {
            provider.selectField(column, Double.class);
        } else if (itemClass == ItemClass.Boolean) {
            provider.selectField(column, Boolean.class);
        } else if (itemClass == ItemClass.String) {
            provider.selectField(column, String.class);
        } else if (itemClass == ItemClass.DateTime) {
            provider.selectField(column, "yyyy-MM-dd HH:mm:ss", Calendar.DateTime);
        } else if (itemClass == ItemClass.Date) {
            provider.selectField(column, "yyyy-MM-dd", Calendar.Date);
        } else if (itemClass == ItemClass.Time) {
            provider.selectField(column, "HH:mm:ss", Calendar.Time);
        }
    }

    @Override
    public void populateItem(Item<ICellPopulator<Map<String, Object>>> item, String componentId, IModel<Map<String, Object>> rowModel) {
        ItemType lambdaColumnType = ItemType.TEXT;
        if (this.itemType != null) {
            lambdaColumnType = this.itemType.apply(this.identity, rowModel.getObject());
        }
        boolean clickable = false;
        if (this.clickable != null) {
            clickable = this.clickable.apply(this.identity, rowModel.getObject());
        }
        if (lambdaColumnType == ItemType.TEXT) {
            if (clickable) {
                item.add(new ItemTextLink(componentId, rowModel, getDataModel(rowModel), this.itemCss, this.itemClick, this.identity));
            } else {
                item.add(new ItemText(componentId, rowModel, getDataModel(rowModel), this.itemCss, this.identity));
            }
        } else if (lambdaColumnType == ItemType.PROGRESS_BAR) {
            if (clickable) {
                item.add(new ItemProgressBarLink(componentId, rowModel, getDataModel(rowModel), this.itemCss, this.progressBarActive, this.itemClick, this.identity));
            } else {
                item.add(new ItemProgressBar(componentId, rowModel, getDataModel(rowModel), this.itemCss, this.progressBarActive, this.identity));
            }
        } else if (lambdaColumnType == ItemType.CUSTOM) {
            Component component = this.itemCustom.apply(this.identity, rowModel.getObject(), componentId);
            item.add(component);
        }
    }

    @Override
    public IModel<?> getDataModel(IModel<Map<String, Object>> rowModel) {
        IModel<Object> dataModel = new IModel<Object>() {
            @Override
            public Object getObject() {
                Map<String, Object> before = rowModel.getObject();

                if (before == null) {
                    return null;
                } else {
                    return function.apply(identity, before);
                }
            }

            @Override
            public void detach() {
                rowModel.detach();
            }
        };
        return dataModel;
    }

    protected static Object defaultFunction(String name, Map<String, Object> object) {
        return object.get(name);
    }

    protected static ItemType defaultItemType(String link, Map<String, Object> object) {
        return ItemType.TEXT;
    }

    protected static boolean defaultClickable(String link, Map<String, Object> object) {
        return false;
    }

    protected static ItemCss defaultItemCss(String link, Map<String, Object> object) {
        return ItemCss.NONE;
    }

    protected static boolean defaultProgressBarActive(String link, Map<String, Object> object) {
        return false;
    }

}
