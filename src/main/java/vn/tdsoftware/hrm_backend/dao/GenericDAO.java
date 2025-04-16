package vn.tdsoftware.hrm_backend.dao;

import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.util.List;

public interface GenericDAO<T> {
    Boolean query(String sql);
    <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... parameters);
    <T> List<T> queryDetails(String sql, RowMapper<T> rowMapper, Object... parameters);
    void update (String sql, Object... parameters);
    Long insert (String sql, Object... parameters);
    int count(String sql, Object... parameters);
}
