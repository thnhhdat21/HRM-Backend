package vn.tdsoftware.hrm_backend.mapper.response.employee;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.employee.response.EmployeeResponse;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;

public class EmployeeBasicResponseMapper implements RowMapper<EmployeeResponse> {
    @Override
    public EmployeeResponse mapRow(ResultSet resultSet) {
        try {
            return EmployeeResponse.builder()
                    .id(resultSet.getLong("id"))
                    .code(resultSet.getString("code"))
                    .fullName(resultSet.getString("fullName"))
                    .department(resultSet.getString("department"))
                    .jobPosition(resultSet.getString("jobPosition"))
                    .duty(resultSet.getString("duty"))
                    .build();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
