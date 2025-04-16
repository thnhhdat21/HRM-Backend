package vn.tdsoftware.hrm_backend.mapper.response.role;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;

public class ListPermissionByRoleMapper implements RowMapper<String> {
    @Override
    public String mapRow(ResultSet resultSet) {
        try {
            return resultSet.getString("permissions");
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
