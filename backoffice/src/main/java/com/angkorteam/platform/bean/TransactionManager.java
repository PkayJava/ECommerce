package com.angkorteam.platform.bean;

import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

/**
 * Created by socheatkhauv on 3/2/17.
 */
public class TransactionManager {

    private DataSource dataSource;

    public TransactionManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public JdbcTemplate createJdbcTemplate() {
        return new JdbcTemplate(this.dataSource);
    }

    public NamedParameterJdbcTemplate createNamedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(this.dataSource);
    }

}
