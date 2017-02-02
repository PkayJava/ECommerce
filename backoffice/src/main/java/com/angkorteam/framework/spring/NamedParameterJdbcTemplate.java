package com.angkorteam.framework.spring;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by socheatkhauv on 30/1/17.
 */
public class NamedParameterJdbcTemplate extends org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate {

    public NamedParameterJdbcTemplate(DataSource dataSource) {
        super(dataSource);
    }

    public NamedParameterJdbcTemplate(JdbcOperations classicJdbcTemplate) {
        super(classicJdbcTemplate);
    }

    @Override
    public <T> T queryForObject(String sql, SqlParameterSource paramSource, Class<T> requiredType) throws DataAccessException {
        try {
            if (requiredType == Boolean.class || requiredType == boolean.class
                    || requiredType == Byte.class || requiredType == byte.class
                    || requiredType == Short.class || requiredType == short.class
                    || requiredType == Integer.class || requiredType == int.class
                    || requiredType == Long.class || requiredType == long.class
                    || requiredType == Float.class || requiredType == float.class
                    || requiredType == Double.class || requiredType == double.class
                    || requiredType == Character.class || requiredType == char.class
                    || requiredType == String.class
                    || requiredType == Date.class) {
                return super.queryForObject(sql, paramSource, requiredType);
            } else {
                return super.queryForObject(sql, paramSource, new BeanPropertyRowMapper<>(requiredType));
            }
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public <T> T queryForObject(String sql, Map<String, ?> paramMap, Class<T> requiredType) throws DataAccessException {
        try {
            if (requiredType == Boolean.class || requiredType == boolean.class
                    || requiredType == Byte.class || requiredType == byte.class
                    || requiredType == Short.class || requiredType == short.class
                    || requiredType == Integer.class || requiredType == int.class
                    || requiredType == Long.class || requiredType == long.class
                    || requiredType == Float.class || requiredType == float.class
                    || requiredType == Double.class || requiredType == double.class
                    || requiredType == Character.class || requiredType == char.class
                    || requiredType == String.class
                    || requiredType == Date.class) {
                return super.queryForObject(sql, paramMap, requiredType);
            } else {
                return super.queryForObject(sql, paramMap, new BeanPropertyRowMapper<>(requiredType));
            }
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Map<String, Object> queryForMap(String sql, SqlParameterSource paramSource) throws DataAccessException {
        try {
            return super.queryForMap(sql, paramSource);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Map<String, Object> queryForMap(String sql, Map<String, ?> paramMap) throws DataAccessException {
        try {
            return super.queryForMap(sql, paramMap);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public <T> List<T> queryForList(String sql, SqlParameterSource paramSource, Class<T> elementType) throws DataAccessException {
        if (elementType == Boolean.class || elementType == boolean.class
                || elementType == Byte.class || elementType == byte.class
                || elementType == Short.class || elementType == short.class
                || elementType == Integer.class || elementType == int.class
                || elementType == Long.class || elementType == long.class
                || elementType == Float.class || elementType == float.class
                || elementType == Double.class || elementType == double.class
                || elementType == Character.class || elementType == char.class
                || elementType == String.class
                || elementType == Date.class) {
            return super.queryForList(sql, paramSource, elementType);
        } else {
            return super.query(sql, paramSource, new BeanPropertyRowMapper<>(elementType));
        }
    }

    @Override
    public <T> List<T> queryForList(String sql, Map<String, ?> paramMap, Class<T> elementType) throws DataAccessException {
        if (elementType == Boolean.class || elementType == boolean.class
                || elementType == Byte.class || elementType == byte.class
                || elementType == Short.class || elementType == short.class
                || elementType == Integer.class || elementType == int.class
                || elementType == Long.class || elementType == long.class
                || elementType == Float.class || elementType == float.class
                || elementType == Double.class || elementType == double.class
                || elementType == Character.class || elementType == char.class
                || elementType == String.class
                || elementType == Date.class) {
            return super.queryForList(sql, paramMap, elementType);
        } else {
            return super.query(sql, paramMap, new BeanPropertyRowMapper<>(elementType));
        }
    }
}
