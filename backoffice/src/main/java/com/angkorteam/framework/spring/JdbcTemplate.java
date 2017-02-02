package com.angkorteam.framework.spring;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by socheatkhauv on 30/1/17.
 */
public class JdbcTemplate extends org.springframework.jdbc.core.JdbcTemplate {

    public JdbcTemplate() {
    }

    public JdbcTemplate(DataSource dataSource) {
        super(dataSource);
    }

    public JdbcTemplate(DataSource dataSource, boolean lazyInit) {
        super(dataSource, lazyInit);
    }

    @Override
    public <T> T queryForObject(String sql, Object[] args, int[] argTypes, RowMapper<T> rowMapper) throws DataAccessException {
        try {
            return super.queryForObject(sql, args, argTypes, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public <T> T queryForObject(String sql, Object[] args, RowMapper<T> rowMapper) throws DataAccessException {
        try {
            return super.queryForObject(sql, args, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public <T> T queryForObject(String sql, RowMapper<T> rowMapper, Object... args) throws DataAccessException {
        try {
            return super.queryForObject(sql, rowMapper, args);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public <T> T queryForObject(String sql, Object[] args, int[] argTypes, Class<T> requiredType) throws DataAccessException {
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
                return super.queryForObject(sql, args, argTypes, requiredType);
            } else {
                return super.queryForObject(sql, args, argTypes, new BeanPropertyRowMapper<>(requiredType));
            }
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public <T> T queryForObject(String sql, Object[] args, Class<T> requiredType) throws DataAccessException {
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
                return super.queryForObject(sql, args, requiredType);
            } else {
                return super.queryForObject(sql, args, new BeanPropertyRowMapper<>(requiredType));
            }
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public <T> T queryForObject(String sql, Class<T> requiredType, Object... args) throws DataAccessException {
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
                return super.queryForObject(sql, requiredType, args);
            } else {
                return super.queryForObject(sql, new BeanPropertyRowMapper<>(requiredType), args);
            }
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Map<String, Object> queryForMap(String sql, Object[] args, int[] argTypes) throws DataAccessException {
        try {
            return super.queryForMap(sql, args, argTypes);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Map<String, Object> queryForMap(String sql, Object... args) throws DataAccessException {
        try {
            return super.queryForMap(sql, args);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Map<String, Object> queryForMap(String sql) throws DataAccessException {
        try {
            return super.queryForMap(sql);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public <T> T queryForObject(String sql, RowMapper<T> rowMapper) throws DataAccessException {
        try {
            return super.queryForObject(sql, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public <T> T queryForObject(String sql, Class<T> requiredType) throws DataAccessException {
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
                return super.queryForObject(sql, requiredType);
            } else {
                return super.queryForObject(sql, new BeanPropertyRowMapper<>(requiredType));
            }
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public <T> List<T> queryForList(String sql, Class<T> elementType) throws DataAccessException {
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
            return super.queryForList(sql, elementType);
        } else {
            return super.query(sql, new BeanPropertyRowMapper<>(elementType));
        }
    }


    @Override
    public <T> List<T> queryForList(String sql, Object[] args, int[] argTypes, Class<T> elementType) throws DataAccessException {
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
            return super.queryForList(sql, args, argTypes, elementType);
        } else {
            return super.query(sql, args, argTypes, new BeanPropertyRowMapper<>(elementType));
        }
    }

    @Override
    public <T> List<T> queryForList(String sql, Object[] args, Class<T> elementType) throws DataAccessException {
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
            return super.queryForList(sql, args, elementType);
        } else {
            return super.query(sql, args, new BeanPropertyRowMapper<>(elementType));
        }
    }

    @Override
    public <T> List<T> queryForList(String sql, Class<T> elementType, Object... args) throws DataAccessException {
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
            return super.queryForList(sql, elementType, args);
        } else {
            return super.query(sql, new BeanPropertyRowMapper<>(elementType), args);
        }
    }
}
