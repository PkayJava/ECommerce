package com.angkorteam.platform;

import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
import com.angkorteam.platform.model.PlatformUser;
import com.google.common.base.Strings;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

/**
 * Created by socheat on 10/23/16.
 */
public class Session extends AuthenticatedWebSession {

    private Roles roles;

    private String userId;

    public Session(Request request) {
        super(request);
        this.roles = new Roles();
    }

    public final String getPlatformUserId() {
        return this.userId;
    }

    @Override
    protected boolean authenticate(String username, String password) {
        SelectQuery selectQuery = null;
        selectQuery = new SelectQuery("platform_user");
        selectQuery.addWhere("login = :login", username);
        selectQuery.addWhere("password = MD5(:password)", password);
        PlatformUser user = getNamed().queryForObject(selectQuery.toSQL(), selectQuery.getParam(), PlatformUser.class);
        if (user != null) {
            String role = getJdbcTemplate().queryForObject("select name from platform_role where platform_role_id = ?", String.class, user.getPlatformRoleId());
            if (!Strings.isNullOrEmpty(role)) {
                this.roles.add(role);
            }
            this.userId = String.valueOf(user.getPlatformUserId());
        }
        return user != null;
    }

    @Override
    public Roles getRoles() {
        return this.roles;
    }

    protected NamedParameterJdbcTemplate getNamed() {
        return Platform.getBean(NamedParameterJdbcTemplate.class);
    }

    protected JdbcTemplate getJdbcTemplate() {
        return Platform.getBean(JdbcTemplate.class);
    }
}
