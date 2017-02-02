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

    public final String getUserId() {
        return this.userId;
    }

    @Override
    protected boolean authenticate(String username, String password) {
        SelectQuery selectQuery = null;
        selectQuery = new SelectQuery("user");
        selectQuery.addWhere("login = :login", username);
        selectQuery.addWhere("password = MD5(:password)", password);
        PlatformUser user = getNamed().queryForObject(selectQuery.toSQL(), selectQuery.getParam(), PlatformUser.class);
        if (user != null) {
            String role = getJdbcTemplate().queryForObject("select name from role where role_id = ?", String.class, user.getRoleId());
            if (!Strings.isNullOrEmpty(role)) {
                this.roles.add(role);
            }
            this.userId = String.valueOf(user.getUserId());
        }
        return user != null;
    }

    @Override
    public Roles getRoles() {
        return this.roles;
    }

    protected NamedParameterJdbcTemplate getNamed() {
        return Spring.getBean(NamedParameterJdbcTemplate.class);
    }

    protected JdbcTemplate getJdbcTemplate() {
        return Spring.getBean(JdbcTemplate.class);
    }
}
