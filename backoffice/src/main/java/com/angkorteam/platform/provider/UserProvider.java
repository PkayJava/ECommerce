package com.angkorteam.platform.provider;

import com.angkorteam.framework.extension.share.provider.JdbcProvider;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.platform.Platform;

/**
 * Created by socheat on 3/1/16.
 */
public class UserProvider extends JdbcProvider {

    public UserProvider() {
    }

    @Override
    protected JdbcTemplate getJdbcTemplate() {
        return Platform.getBean(JdbcTemplate.class);
    }

}
