package vn.tdsoftware.hrm_backend.mapper;

import java.sql.ResultSet;

public interface RowMapper<T> {
    T mapRow(ResultSet resultSet);
}
