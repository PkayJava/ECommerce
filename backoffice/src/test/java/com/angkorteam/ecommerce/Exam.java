package com.angkorteam.ecommerce;

import com.angkorteam.framework.jdbc.Jdbc;
import com.angkorteam.platform.bean.HttpServletRequestDataSource;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by socheatkhauv on 3/11/17.
 */
public class Exam {

    private static Jdbc jdbc = null;

    public static void main(String[] args) {

        BasicDataSource s = new BasicDataSource();
        s.setDriverClassName("com.mysql.jdbc.Driver");
        s.setUrl("jdbc:mysql://192.168.0.5:3306/engine?useSSL=false");
        s.setUsername("root");
        s.setPassword("password");
        HttpServletRequestDataSource dataSource = new HttpServletRequestDataSource(s);

        try {

            jdbc = new Jdbc(dataSource, "user", "user_id");

            String taskId = null;
//            taskId = "5602aadb-06e3-11e7-86b0-28d2445452c1";
            taskId = insert();
            draftApprove(taskId);
            draftApprove(taskId);

            reviewApprove(taskId);
            reviewReject(taskId);
            reviewReject(taskId);

            taskId = insert();
            draftApprove(taskId);
            draftApprove(taskId);

            reviewApprove(taskId);
            reviewReject(taskId);
            reviewApprove(taskId);

            taskId = delete();
            draftApprove(taskId);
            draftApprove(taskId);

            reviewApprove(taskId);
            reviewApprove(taskId);

            taskId = insert();
            draftApprove(taskId);
            draftApprove(taskId);
            reviewApprove(taskId);
            reviewApprove(taskId);

            taskId = update();
            draftApprove(taskId);
            draftApprove(taskId);
            reviewApprove(taskId);
            reviewApprove(taskId);

            taskId = delete();
            draftApprove(taskId);
            draftApprove(taskId);

            reviewApprove(taskId);
            reviewApprove(taskId);

            dataSource.getConnection().commit();

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                dataSource.getConnection().rollback();
            } catch (SQLException e1) {
            }
        }
    }

    public static String delete() throws SQLException {
        Map<String, Object> wheres = new HashMap<>();
        wheres.put("skh_animal_id", "1");
        Map<String, Object> systems = new HashMap<>();
        systems.put("user_id", UUID.randomUUID().toString());
        systems.put("sys_tid", UUID.randomUUID().toString());
        return jdbc.delete("skh_animal", wheres, systems);
    }

    public static String update() throws SQLException {
        Map<String, Object> values = new HashMap<>();
        values.put("name", "Dota");
        Map<String, Object> wheres = new HashMap<>();
        wheres.put("skh_animal_id", "1");
        Map<String, Object> systems = new HashMap<>();
        systems.put("user_id", UUID.randomUUID().toString());
        systems.put("sys_tid", UUID.randomUUID().toString());
        return jdbc.update("skh_animal", values, wheres, systems);
    }

    public static String insert() throws SQLException {
        Map<String, Object> values = new HashMap<>();
        values.put("skh_animal_id", "1");
        values.put("name", "Socheat KHAUV");
        values.put("sys_time", new Date());
        values.put("sys_date", new Date());
        values.put("sys_datetime", new Date());
        Map<String, Object> systems = new HashMap<>();
        systems.put("user_id", UUID.randomUUID().toString());
        systems.put("sys_tid", UUID.randomUUID().toString());
        return jdbc.insert("skh_animal", values, systems);
    }

    public static void draftReject(String taskId) throws SQLException {
        Map<String, Object> systems = new HashMap<>();
        systems.put("user_id", UUID.randomUUID().toString());
        jdbc.draftReject(taskId, "I Don't Like", systems);
    }

    public static void draftApprove(String taskId) throws SQLException {
        Map<String, Object> systems = new HashMap<>();
        systems.put("user_id", UUID.randomUUID().toString());
        jdbc.draftApprove(taskId, systems);
    }

    public static void reviewReject(String taskId) throws SQLException {
        Map<String, Object> systems = new HashMap<>();
        systems.put("user_id", UUID.randomUUID().toString());
        jdbc.reviewReject(taskId, "I Don't Like", systems);
    }

    public static void reviewApprove(String taskId) throws SQLException {
        Map<String, Object> systems = new HashMap<>();
        systems.put("user_id", UUID.randomUUID().toString());
        jdbc.reviewApprove(taskId, systems);
    }

}
