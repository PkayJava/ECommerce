package com.angkorteam.framework.wicket.markup.html.navigation.paging;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.list.LoopItem;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.html.navigation.paging.IPagingLabelProvider;

/**
 * Created by socheat on 4/15/15.
 */
public class PagingNavigation extends org.apache.wicket.markup.html.navigation.paging.PagingNavigation {

    public PagingNavigation(String id, IPageable pageable) {
        super(id, pageable);
    }

    public PagingNavigation(String id, IPageable pageable, IPagingLabelProvider labelProvider) {
        super(id, pageable, labelProvider);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        setOutputMarkupId(true);
    }

    @Override
    protected void populateItem(LoopItem loopItem) {
        final long pageIndex = getStartIndex() + loopItem.getIndex();
        if (this.pageable.getCurrentPage() == pageIndex) {
            loopItem.add(AttributeModifier.replace("class", "active"));
        } else {
            loopItem.add(AttributeModifier.replace("class", ""));
        }
        super.populateItem(loopItem);
    }
}
