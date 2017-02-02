package com.angkorteam.platform.provider;

import com.angkorteam.framework.extension.share.provider.JdbcProvider;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.platform.Spring;

/**
 * Created by socheat on 8/18/16.
 */
public class RestProvider extends JdbcProvider {

    public RestProvider() {
    }

    @Override
    protected JdbcTemplate getJdbcTemplate() {
        return Spring.getBean(JdbcTemplate.class);
    }
}
