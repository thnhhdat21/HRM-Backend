package vn.tdsoftware.hrm_backend.mapper.response;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.employee.response.EmployeeSelectResponse;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;

public class EmployeeSelectMapper implements RowMapper<EmployeeSelectResponse> {

    @Override
    public EmployeeSelectResponse mapRow(ResultSet resultSet) {
        try{
            return EmployeeSelectResponse.builder()
                    .employeeId(resultSet.getLong("id"))
                    .employeeName(resultSet.getString("name"))
                    .employeeCode(resultSet.getString("code"))
                    .build();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
