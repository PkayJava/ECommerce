package com.angkorteam.framework.extension.wicket.extensions.markup.html.repeater.data.table.filter;

import com.angkorteam.framework.extension.share.provider.TableProvider;
import com.angkorteam.framework.extension.wicket.WicketBiFunction;
import com.angkorteam.framework.extension.wicket.WicketTriConsumer;
import com.angkorteam.framework.extension.wicket.WicketTriFunction;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilteredColumn;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import java.util.List;
import java.util.Map;

/**
 * Created by socheat on 12/7/16.
 */
public class ChoiceFilterColumn extends LambdaColumn
        implements IFilteredColumn<Map<String, Object>, String> {

    private final IModel<List<String>> filterChoices;

    private final String propertyExpression;

    public ChoiceFilterColumn(TableProvider provider,
                              ItemClass itemClass,
                              IModel<String> displayModel,
                              String column,
                              IModel<List<String>> filterChoices,
                              WicketBiFunction<String, Map<String, Object>, ?> function) {
        super(provider, itemClass, displayModel, column, function);
        this.filterChoices = filterChoices;
        this.propertyExpression = column;
    }

    public ChoiceFilterColumn(TableProvider provider,
                              ItemClass itemClass,
                              IModel<String> displayModel,
                              String column,
                              IModel<List<String>> filterChoices,
                              WicketBiFunction<String, Map<String, Object>, ?> function,
                              WicketBiFunction<String, Map<String, Object>, ItemType> itemType,
                              WicketBiFunction<String, Map<String, Object>, Boolean> clickable,
                              WicketBiFunction<String, Map<String, Object>, ItemCss> itemCss,
                              WicketBiFunction<String, Map<String, Object>, Boolean> progressBarActive,
                              WicketTriConsumer<String, Map<String, Object>, AjaxRequestTarget> itemClick) {
        super(provider, itemClass, displayModel, column, function, itemType, clickable, itemCss, progressBarActive, itemClick);
        this.filterChoices = filterChoices;
        this.propertyExpression = column;
    }

    public ChoiceFilterColumn(TableProvider provider,
                              ItemClass itemClass,
                              IModel<String> displayModel,
                              String identity,
                              String column,
                              String sortProperty,
                              String propertyExpression,
                              IModel<List<String>> filterChoices,
                              WicketBiFunction<String, Map<String, Object>, ?> function,
                              WicketBiFunction<String, Map<String, Object>, ItemType> itemType,
                              WicketBiFunction<String, Map<String, Object>, Boolean> clickable,
                              WicketBiFunction<String, Map<String, Object>, ItemCss> itemCss,
                              WicketBiFunction<String, Map<String, Object>, Boolean> progressBarActive,
                              WicketTriConsumer<String, Map<String, Object>, AjaxRequestTarget> itemClick,
                              WicketTriFunction<String, Map<String, Object>, String, ? extends Component> itemCustom) {
        super(provider, itemClass, displayModel, identity, column, sortProperty, function, itemType, clickable, itemCss, progressBarActive, itemClick, itemCustom);
        this.filterChoices = filterChoices;
        this.propertyExpression = propertyExpression;
    }

    @Override
    public Component getFilter(final String componentId, final FilterForm<?> form) {
        ChoiceFilter<String> filter = new ChoiceFilter<>(componentId, getFilterModel(form), form,
                getFilterChoices(), enableAutoSubmit());
        IChoiceRenderer<String> renderer = getChoiceRenderer();
        if (renderer != null) {
            filter.getChoice().setChoiceRenderer(renderer);
        }
        return filter;
    }

    protected IModel<String> getFilterModel(final FilterForm<?> form) {
        return new PropertyModel<>(form.getDefaultModel(), this.propertyExpression);
    }

    protected IChoiceRenderer<String> getChoiceRenderer() {
        return null;
    }

    protected final IModel<List<String>> getFilterChoices() {
        return filterChoices;
    }

    public void detach() {
        super.detach();
        if (filterChoices != null) {
            filterChoices.detach();
        }
    }

    protected boolean enableAutoSubmit() {
        return true;
    }


    public static class Builder {

        private TableProvider provider;

        private ItemClass itemClass;

        private IModel<String> displayModel;

        private String column;

        private IModel<List<String>> filterChoices;

        private WicketBiFunction<String, Map<String, Object>, ?> function;

        private WicketBiFunction<String, Map<String, Object>, ItemType> itemType;

        private WicketBiFunction<String, Map<String, Object>, Boolean> clickable;

        private WicketBiFunction<String, Map<String, Object>, ItemCss> itemCss;

        private WicketBiFunction<String, Map<String, Object>, Boolean> progressBarActive;

        private WicketTriConsumer<String, Map<String, Object>, AjaxRequestTarget> itemClick;

        private WicketTriFunction<String, Map<String, Object>, String, ? extends Component> itemCustom;

        public Builder(TableProvider provider, ItemClass itemClass, IModel<String> displayModel, String column, IModel<List<String>> filterChoices, WicketBiFunction<String, Map<String, Object>, ?> function) {
            this.provider = provider;
            this.itemClass = itemClass;
            this.filterChoices = filterChoices;
            this.displayModel = displayModel;
            this.column = column;
            this.function = function;
        }

        public Builder withItemType(WicketBiFunction<String, Map<String, Object>, ItemType> itemType) {
            this.itemType = itemType;
            return this;
        }

        public Builder withClickable(WicketBiFunction<String, Map<String, Object>, Boolean> clickable) {
            this.clickable = clickable;
            return this;
        }

        public Builder withItemCss(WicketBiFunction<String, Map<String, Object>, ItemCss> itemCss) {
            this.itemCss = itemCss;
            return this;
        }

        public Builder withProgressBarActive(WicketBiFunction<String, Map<String, Object>, Boolean> progressBarActive) {
            this.progressBarActive = progressBarActive;
            return this;
        }

        public Builder withItemClick(WicketTriConsumer<String, Map<String, Object>, AjaxRequestTarget> itemClick) {
            this.itemClick = itemClick;
            return this;
        }

        public Builder withItemCustom(WicketTriFunction<String, Map<String, Object>, String, ? extends Component> itemCustom) {
            this.itemCustom = itemCustom;
            return this;
        }

        public ChoiceFilterColumn build() {
            ChoiceFilterColumn column = new ChoiceFilterColumn(this.provider, this.itemClass, this.displayModel, this.column, this.filterChoices, this.function);
            column.itemCustom = this.itemCustom;
            column.itemClick = this.itemClick;
            column.itemType = this.itemType;
            column.clickable = this.clickable;
            column.itemCss = this.itemCss;
            column.progressBarActive = this.progressBarActive;
            return column;
        }
    }

}
