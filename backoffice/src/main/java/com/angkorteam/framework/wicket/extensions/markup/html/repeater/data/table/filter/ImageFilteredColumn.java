package com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter;

import com.angkorteam.framework.share.provider.TableProvider;
import com.angkorteam.framework.wicket.validation.Validator;
import org.apache.commons.io.FilenameUtils;
import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.TextFilteredPropertyColumn;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;

import java.util.Map;

/**
 * Created by socheat on 9/26/15.
 */
public class ImageFilteredColumn extends TextFilteredPropertyColumn<Map<String, Object>, Map<String, String>, String> {

    private Class<?> columnClass;

    private int width;

    private int height;

    public <T> ImageFilteredColumn(Class<T> columnClass, IModel<String> displayModel, String column, TableProvider provider, int width, int height) {
        super(displayModel, column, column);
        this.columnClass = columnClass;
        this.width = width;
        this.height = height;
        provider.selectField(column, columnClass);
    }

    @Override
    public void populateItem(Item<ICellPopulator<Map<String, Object>>> item, String componentId, IModel<Map<String, Object>> rowModel) {
        String streamAddress = (String) getDataModel(rowModel).getObject();
        ImagePanel image = new ImagePanel(componentId, shrink(streamAddress, width, height));
        item.add(image);
    }

    public static String shrink(String thumbnailAddress, int width, int height) {
        if (thumbnailAddress == null || "".equals(thumbnailAddress)) {
            return "";
        }
        return FilenameUtils.getPath(thumbnailAddress) + FilenameUtils.getBaseName(thumbnailAddress) + "_w" + width + "-h" + height + "." + FilenameUtils.getExtension(thumbnailAddress);
    }

    public static String shrinkWidth(String thumbnailAddress, int width) {
        if (thumbnailAddress == null || "".equals(thumbnailAddress)) {
            return "";
        }
        return FilenameUtils.getPath(thumbnailAddress) + FilenameUtils.getBaseName(thumbnailAddress) + "_w" + width + "." + FilenameUtils.getExtension(thumbnailAddress);
    }

    public static String shrinkHeight(String thumbnailAddress, int height) {
        if (thumbnailAddress == null || "".equals(thumbnailAddress)) {
            return "";
        }
        return FilenameUtils.getPath(thumbnailAddress) + FilenameUtils.getBaseName(thumbnailAddress) + "_h" + height + "." + FilenameUtils.getExtension(thumbnailAddress);
    }

    @Override
    public Component getFilter(final String componentId, final FilterForm<?> form) {
        IModel<Map<String, String>> filterModel = getFilterModel(form);
        TextFilter<Map<String, String>> filter = new TextFilter<>(componentId, filterModel, form);
        filter.getFilter().add(new Validator<>(this.columnClass));
        return filter;
    }
}
