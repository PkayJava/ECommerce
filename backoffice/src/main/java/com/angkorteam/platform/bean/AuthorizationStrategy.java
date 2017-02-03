package com.angkorteam.platform.bean;

import com.angkorteam.framework.jdbc.JoinType;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.platform.Platform;
import com.angkorteam.platform.Session;
import com.angkorteam.platform.model.PlatformPage;
import org.apache.wicket.Component;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.component.IRequestableComponent;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.IResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by socheat on 11/3/16.
 */
public class AuthorizationStrategy implements IAuthorizationStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationStrategy.class);

    public AuthorizationStrategy() {
    }

    @Override
    public <T extends IRequestableComponent> boolean isInstantiationAuthorized(Class<T> componentClass) {
        Roles roles = Session.get().getRoles();
        if (roles == null) {
            roles = new Roles();
        }

        JdbcTemplate jdbcTemplate = Platform.getBean(JdbcTemplate.class);

        String javaClass = componentClass.getName();

        PlatformPage page = jdbcTemplate.queryForObject("select * from platform_page where java_class = ?", PlatformPage.class, javaClass);
        if (page != null) {

            SelectQuery selectQuery = new SelectQuery("platform_role");
            selectQuery.addField("platform_role.name");
            selectQuery.addJoin(JoinType.InnerJoin, "platform_page_role", "platform_role.platform_role_id = platform_page_role.platform_role_id");
            selectQuery.addWhere("platform_page_role.platform_page_id = ?");

            List<String> pageRoles = jdbcTemplate.queryForList(selectQuery.toSQL(), String.class, page.getPlatformPageId());
            if (pageRoles != null && !pageRoles.isEmpty()) {
                Roles r = new Roles();
                r.addAll(pageRoles);
                return roles.hasAnyRole(r);
            }
        }
        return true;
    }

    @Override
    public boolean isActionAuthorized(Component component, Action action) {
        return true;
    }

    @Override
    public boolean isResourceAuthorized(IResource resource, PageParameters parameters) {
        return true;
    }

}
