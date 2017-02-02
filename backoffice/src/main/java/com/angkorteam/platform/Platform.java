package com.angkorteam.platform;

import com.angkorteam.ecommerce.vo.LinksVO;
import com.angkorteam.framework.jdbc.InsertQuery;
import com.angkorteam.framework.jdbc.JoinType;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
import com.angkorteam.platform.model.PlatformRole;
import com.angkorteam.platform.model.PlatformUser;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.configuration.XMLPropertiesConfiguration;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by socheatkhauv on 27/1/17.
 */
public abstract class Platform {

    public static PlatformUser getCurrentUser(HttpServletRequest request) throws UnsupportedEncodingException {
        String authorization = request.getHeader("Authorization");
        byte[] base64Token = authorization.substring(6).getBytes("UTF-8");
        byte[] decoded = Base64.decodeBase64(base64Token);
        String token = new String(decoded, "UTF-8");
        Integer delim = token.indexOf(":");
        String accessToken = token.substring(0, delim);
        JdbcTemplate jdbcTemplate = Spring.getBean(JdbcTemplate.class);
        return jdbcTemplate.queryForObject("select * from user where access_token = ?", PlatformUser.class, accessToken);
    }

    public static LinksVO buildLinks(HttpServletRequest request, long total, long limit) {
        String queryString = request.getQueryString();
        List<String> params = Lists.newArrayList();
        if (!Strings.isNullOrEmpty(queryString)) {
            String[] temps = StringUtils.split(queryString, '&');
            for (String temp : temps) {
                if (!StringUtils.startsWithIgnoreCase(temp, "page=")) {
                    params.add(temp);
                }
            }
        }
        String url = request.getRequestURL().toString();
        long page = ServletRequestUtils.getLongParameter(request, "page", 1L);
        if (page < 1) {
            page = 1;
        }
        LinksVO linksVO = new LinksVO();
        {
            // first page
            long currentPage = 1L;
            List<String> actions = new ArrayList<>(params);
            actions.add("page=" + currentPage);
            linksVO.setFirst(url + "?" + StringUtils.join(actions, '&'));
        }
        {
            // last page
            long currentPage = 0L;
            if (total % limit == 0L) {
                currentPage = total / limit;
            } else {
                currentPage = (total / limit) + 1L;
            }
            List<String> actions = new ArrayList<>(params);
            actions.add("page=" + currentPage);
            linksVO.setLast(url + "?" + StringUtils.join(actions, '&'));
        }
        {
            // current page
            long currentPage = page;
            List<String> actions = new ArrayList<>(params);
            actions.add("page=" + currentPage);
            linksVO.setSelf(url + "?" + StringUtils.join(actions, '&'));
        }
        {
            // next page
            long currentPage = page;
            if (total % limit == 0) {
                if (currentPage < (total / limit)) {
                    currentPage = currentPage + 1;
                    List<String> actions = new ArrayList<>(params);
                    actions.add("page=" + currentPage);
                    linksVO.setNext(url + "?" + StringUtils.join(actions, '&'));
                } else {
                    linksVO.setNext(null);
                }
            } else {
                if (currentPage <= (total / limit)) {
                    currentPage = currentPage + 1;
                    List<String> actions = new ArrayList<>(params);
                    actions.add("page=" + currentPage);
                    linksVO.setNext(url + "?" + StringUtils.join(actions, '&'));
                } else {
                    linksVO.setNext(null);
                }
            }

        }
        {
            // previous page
            long currentPage = page;
            if (currentPage > 1) {
                currentPage = currentPage - 1;
                List<String> actions = new ArrayList<>(params);
                actions.add("page=" + currentPage);
                linksVO.setPrevious(url + "?" + StringUtils.join(actions, '&'));
            } else {
                linksVO.setPrevious(null);
            }
        }
        return linksVO;
    }

    public static boolean hasAccess(HttpServletRequest request, Class<?> clazz) {

        String authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Basic ")) {
            return false;
        }
        byte[] base64Token = null;
        try {
            base64Token = authorization.substring(6).getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            return false;
        }
        byte[] decoded;
        try {
            decoded = Base64.decodeBase64(base64Token);
        } catch (IllegalArgumentException e) {
            return false;
        }

        String token = null;
        try {
            token = new String(decoded, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return false;
        }
        int delim = token.indexOf(":");
        if (delim == -1) {
            return false;
        }
        String accessToken = token.substring(0, delim);

        JdbcTemplate jdbcTemplate = Spring.getBean(JdbcTemplate.class);
        PlatformUser userRecord = jdbcTemplate.queryForObject("select * from platform_user where access_token = ?", PlatformUser.class, accessToken);
        PlatformRole roleRecord = jdbcTemplate.queryForObject("select * from platform_role where platform_role_id = ?", PlatformRole.class, userRecord.getRoleId());

        SelectQuery selectQuery = new SelectQuery("platform_role role");
        selectQuery.addField("role.name");
        selectQuery.addJoin(JoinType.InnerJoin, "rest_role", "role.role_id = rest_role.role_id");
        selectQuery.addJoin(JoinType.InnerJoin, "rest", "rest_role.rest_id = rest.rest_id");
        selectQuery.addWhere("rest.java_class = ?");
        List<String> serviceRoles = jdbcTemplate.queryForList(selectQuery.toSQL(), String.class, clazz.getName());
        if (serviceRoles == null || serviceRoles.isEmpty()) {
        } else {
            String userRole = roleRecord.getName();
            if (userRole == null || "".equals(userRole) || !serviceRoles.contains(userRole)) {
                return false;
            }
        }
        return true;
    }

    public static String getSetting(String key) {
        JdbcTemplate jdbcTemplate = Spring.getBean(JdbcTemplate.class);
        return jdbcTemplate.queryForObject("select value from setting where `key` = ?", String.class, key);
    }

    public static void putSetting(String key, String value) {
        JdbcTemplate jdbcTemplate = Spring.getBean(JdbcTemplate.class);
        int count = jdbcTemplate.queryForObject("select count(*) from setting where `key` = ?", int.class, key);
        if (count > 0) {
            jdbcTemplate.update("update setting set value = ? where `key` = ?", value, key);
        } else {
            InsertQuery insertQuery = new InsertQuery("setting");
            insertQuery.addValue("setting_id = :setting_id", randomUUIDLong());
            insertQuery.addValue("description = :description", "");
            insertQuery.addValue("version = :version", 1);
            insertQuery.addValue("`key` = :key", key);
            NamedParameterJdbcTemplate named = Spring.getBean(NamedParameterJdbcTemplate.class);
            named.update(insertQuery.toSQL(), insertQuery.getParam());
        }
    }

    public static Integer randomUUIDInteger(String tableName) {
        JdbcTemplate jdbcTemplate = Spring.getBean(JdbcTemplate.class);
        int value = jdbcTemplate.queryForObject("select value from `platform_uuid` where table_name = ? for update", Integer.class, tableName);
        value = value + 1;
        jdbcTemplate.update("update `platform_uuid` set value = ? where table_name = ?", value, tableName);
        return value;
    }

    public static Long randomUUIDLong() {
        JdbcTemplate jdbcTemplate = Spring.getBean(JdbcTemplate.class);
        return jdbcTemplate.queryForObject("select uuid_short() from dual", Long.class);
    }

    public static String randomUUIDString() {
        JdbcTemplate jdbcTemplate = Spring.getBean(JdbcTemplate.class);
        return jdbcTemplate.queryForObject("select uuid() from dual", String.class);
    }

    public static long saveFile(File file) {
        NamedParameterJdbcTemplate named = Spring.getBean(NamedParameterJdbcTemplate.class);

        XMLPropertiesConfiguration configuration = Spring.getBean(XMLPropertiesConfiguration.class);

        String patternFolder = configuration.getString(Configuration.PATTERN_FOLDER);

        String repo = configuration.getString(Configuration.RESOURCE_REPO);

        String fileRepo = DateFormatUtils.format(new Date(), patternFolder);
        File container = new File(repo, fileRepo);
        String extension = StringUtils.lowerCase(FilenameUtils.getExtension(file.getName()));


        Long uuid = randomUUIDLong();
        String name = uuid + "." + extension;
        container.mkdirs();
        try {
            FileUtils.copyFile(file, new File(container, name));
        } catch (Exception e) {
        }

        long length = file.length();
        String path = fileRepo;
        String mime = parseMimeType(file.getName());
        String label = file.getName();

        Long fileId = randomUUIDLong();
        InsertQuery insertQuery = new InsertQuery("file");
        insertQuery.addValue("file_id = :file_id", fileId);
        insertQuery.addValue("path = :path", path);
        insertQuery.addValue("mime = :mime", mime);
        insertQuery.addValue("extension = :extension", extension);
        insertQuery.addValue("`length` = :length", length);
        insertQuery.addValue("label = :label", label);
        insertQuery.addValue("name = :name", name);
        named.update(insertQuery.toSQL(), insertQuery.getParam());
        return fileId;
    }

    public static String parseMimeType(String filename) {
        String extension = FilenameUtils.getExtension(filename);
        if (StringUtils.equalsIgnoreCase("png", extension)) {
            return "image/png";
        } else if (StringUtils.equalsIgnoreCase("jpg", extension)) {
            return "image/jpg";
        } else if (StringUtils.equalsIgnoreCase("gif", extension)) {
            return "image/gif";
        } else if (StringUtils.equalsIgnoreCase("tiff", extension)) {
            return "image/tiff";
        } else if (StringUtils.equalsIgnoreCase("txt", extension)) {
            return "text/plain";
        } else {
            return "application/octet-stream";
        }
    }
}
