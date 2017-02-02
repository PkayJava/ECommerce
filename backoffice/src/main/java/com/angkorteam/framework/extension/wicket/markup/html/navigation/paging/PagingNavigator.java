package com.angkorteam.framework.extension.wicket.markup.html.navigation.paging;

import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.html.navigation.paging.IPagingLabelProvider;

/**
 * @author Socheat KHAUV
 */
public class PagingNavigator extends org.apache.wicket.markup.html.navigation.paging.PagingNavigator {

    /**
     *
     */
    private static final long serialVersionUID = -7091600049473723084L;

    public PagingNavigator(String id, IPageable pageable, IPagingLabelProvider labelProvider) {
        super(id, pageable, labelProvider);
    }

    public PagingNavigator(String id, IPageable pageable) {
        super(id, pageable);
    }

    @Override
    protected PagingNavigation newNavigation(String id, IPageable pageable, IPagingLabelProvider labelProvider) {
        PagingNavigation pagingNavigation = new PagingNavigation(id, pageable, labelProvider);
        return pagingNavigation;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        setOutputMarkupId(true);
    }
}