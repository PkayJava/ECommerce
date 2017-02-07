package com.angkorteam.framework.extension.wicket.extensions.markup.html.repeater.data.table.filter;

import com.angkorteam.framework.extension.share.provider.TableProvider;
import com.angkorteam.framework.extension.wicket.WicketBiFunction;
import com.angkorteam.framework.extension.wicket.WicketTriConsumer;
import com.angkorteam.framework.extension.wicket.WicketTriFunction;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilteredColumn;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import java.util.Map;

/**
 * Created by socheat on 12/7/16.
 */
public class TextFilterColumn extends LambdaColumn
        implements IFilteredColumn<Map<String, Object>, String> {

    private final String propertyExpression;

    public TextFilterColumn(TableProvider provider,
                            ItemClass itemClass,
                            IModel<String> displayModel,
                            String column,
                            WicketBiFunction<String, Map<String, Object>, ?> function) {
        super(provider, itemClass, displayModel, column, function);
        this.propertyExpression = column;
    }

    public TextFilterColumn(TableProvider provider,
                            ItemClass itemClass,
                            IModel<String> displayModel,
                            String column) {
        super(provider, itemClass, displayModel, column);
        this.propertyExpression = column;
    }

    public TextFilterColumn(TableProvider provider,
                            ItemClass itemClass,
                            IModel<String> displayModel,
                            String column,
                            WicketBiFunction<String, Map<String, Object>, ItemType> itemType,
                            WicketBiFunction<String, Map<String, Object>, Boolean> clickable,
                            WicketBiFunction<String, Map<String, Object>, ItemCss> itemCss,
                            WicketBiFunction<String, Map<String, Object>, Boolean> progressBarActive,
                            WicketTriConsumer<String, Map<String, Object>, AjaxRequestTarget> itemClick,
                            WicketBiFunction<String, Map<String, Object>, ?> function) {
        super(provider, itemClass, displayModel, column, function, itemType, clickable, itemCss, progressBarActive, itemClick);
        this.propertyExpression = column;
    }

    public TextFilterColumn(TableProvider provider,
                            ItemClass itemClass,
                            IModel<String> displayModel,
                            String column,
                            WicketBiFunction<String, Map<String, Object>, ItemType> itemType,
                            WicketBiFunction<String, Map<String, Object>, Boolean> clickable,
                            WicketBiFunction<String, Map<String, Object>, ItemCss> itemCss,
                            WicketBiFunction<String, Map<String, Object>, Boolean> progressBarActive,
                            WicketTriConsumer<String, Map<String, Object>, AjaxRequestTarget> itemClick) {
        super(provider, itemClass, displayModel, column, itemType, clickable, itemCss, progressBarActive, itemClick);
        this.propertyExpression = column;
    }

    public TextFilterColumn(TableProvider provider,
                            ItemClass itemClass,
                            IModel<String> displayModel,
                            String identity,
                            String column,
                            String sortProperty,
                            String propertyExpression,
                            WicketBiFunction<String, Map<String, Object>, ItemType> itemType,
                            WicketBiFunction<String, Map<String, Object>, Boolean> clickable,
                            WicketBiFunction<String, Map<String, Object>, ItemCss> itemCss,
                            WicketBiFunction<String, Map<String, Object>, Boolean> progressBarActive,
                            WicketTriConsumer<String, Map<String, Object>, AjaxRequestTarget> itemClick,
                            WicketTriFunction<String, Map<String, Object>, String, ? extends Component> itemCustom,
                            WicketBiFunction<String, Map<String, Object>, ?> function) {
        super(provider, itemClass, displayModel, identity, column, sortProperty, function, itemType, clickable, itemCss, progressBarActive, itemClick, itemCustom);
        this.propertyExpression = propertyExpression;
    }

    public TextFilterColumn(TableProvider provider,
                            ItemClass itemClass,
                            IModel<String> displayModel,
                            String identity,
                            String column,
                            String sortProperty,
                            String propertyExpression,
                            WicketBiFunction<String, Map<String, Object>, ItemType> itemType,
                            WicketBiFunction<String, Map<String, Object>, Boolean> clickable,
                            WicketBiFunction<String, Map<String, Object>, ItemCss> itemCss,
                            WicketBiFunction<String, Map<String, Object>, Boolean> progressBarActive,
                            WicketTriConsumer<String, Map<String, Object>, AjaxRequestTarget> itemClick,
                            WicketTriFunction<String, Map<String, Object>, String, ? extends Component> itemCustom) {
        super(provider, itemClass, displayModel, identity, column, sortProperty, itemType, clickable, itemCss, progressBarActive, itemClick, itemCustom);
        this.propertyExpression = propertyExpression;
    }

    @Override
    public Component getFilter(final String componentId, final FilterForm<?> form) {
        TextFilter<String> filter = new TextFilter<>(componentId, getFilterModel(form), form);
        filter.getFilter().add(new ItemValidator(this.itemClass));
        return filter;
    }

    protected IModel<String> getFilterModel(final FilterForm<?> form) {
        return new PropertyModel<>(form.getDefaultModel(), this.propertyExpression);
    }

}
