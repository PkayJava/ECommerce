package com.angkorteam.ecommerce;

import com.angkorteam.framework.jdbc.Repository;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.platform.bean.HttpServletRequestDataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by socheatkhauv on 3/11/17.
 */
public class Installer {

    private static HttpServletRequestDataSource dataSource = null;

    public static void main(String[] args) throws IOException, SQLException {
        // master
        // history
        // history_summary
        // draft
        // draft_summary
        // review
        // review_summary

        /**
         DROP TABLE IF EXISTS user;
         CREATE TABLE user(user_id VARCHAR(100) NOT NULL, name VARCHAR(255), PRIMARY KEY(user_id));
         CREATE INDEX user_001 ON user(name ASC);
         DROP TABLE IF EXISTS skh_animal;
         CREATE TABLE skh_animal (skh_animal_id VARCHAR(100) NOT NULL, name VARCHAR(255), sys_date DATE, sys_time TIME, sys_datetime DATETIME, PRIMARY KEY (skh_animal_id));
         **/

        BasicDataSource s = new BasicDataSource();
        s.setDriverClassName("com.mysql.jdbc.Driver");
        s.setUrl("jdbc:mysql://192.168.0.5:3306/ecommerce?useSSL=false");
        s.setUsername("root");
        s.setPassword("password");
        HttpServletRequestDataSource dataSource = new HttpServletRequestDataSource(s);

        System.out.println(dataSource.getConnection().getMetaData().getDatabaseProductName());

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        Map<String, String> pp = new HashMap<>();
        pp.put("platform_file", "platform_file_id");

        String userTable = "platform_user";
        String userPk = userTable + "_id";

        List<StringBuffer> scripts = new ArrayList<>();

        scripts.addAll(Repository.installJdbc(dataSource));
        scripts.addAll(Repository.installTable(dataSource, userTable, userPk, true, 2, 2, 2, 2, 2, pp));

        File scriptFile = new File("/Users/socheatkhauv/Desktop/script.sql");
        scriptFile.delete();
        for (StringBuffer script : scripts) {
            FileUtils.write(scriptFile, script, "UTF-8", true);
            FileUtils.write(scriptFile, "\n\n", "UTF-8", true);
        }

    }

}
