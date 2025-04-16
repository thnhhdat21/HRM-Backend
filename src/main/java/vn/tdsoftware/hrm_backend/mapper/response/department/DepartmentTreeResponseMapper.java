package vn.tdsoftware.hrm_backend.mapper.response.department;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.department.response.DepartmentTreeResponse;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;

public class DepartmentTreeResponseMapper implements RowMapper<DepartmentTreeResponse> {
    @Override
    public DepartmentTreeResponse mapRow(ResultSet resultSet) {
        try {
            return DepartmentTreeResponse.builder()
                    .id(resultSet.getLong("id"))
                    .name(resultSet.getString("name"))
                    .departmentLevel(resultSet.getString("departmentLevel"))
                    .businessBlock(resultSet.getString("businessBlockName"))
                    .parentId(resultSet.getLong("parentId"))
                    .build();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
