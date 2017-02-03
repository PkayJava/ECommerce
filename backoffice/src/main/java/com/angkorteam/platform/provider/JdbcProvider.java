package com.angkorteam.platform.provider;

import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.platform.Platform;

/**
 * Created by socheatkhauv on 21/1/17.
 */
public class JdbcProvider extends com.angkorteam.framework.extension.share.provider.JdbcProvider {

    public JdbcProvider(String from) {
        super(from);
    }

    @Override
    protected JdbcTemplate getJdbcTemplate() {
        return Platform.getBean(JdbcTemplate.class);
    }

}
