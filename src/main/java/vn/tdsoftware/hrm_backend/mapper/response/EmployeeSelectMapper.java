package vn.tdsoftware.hrm_backend.mapper.response;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.employee.response.EmployeeNameAndCode;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;

public class EmployeeSelectMapper implements RowMapper<EmployeeNameAndCode> {

    @Override
    public EmployeeNameAndCode mapRow(ResultSet resultSet) {
        try{
            return EmployeeNameAndCode.builder()
                    .employeeId(resultSet.getLong("id"))
                    .employeeName(resultSet.getString("name"))
                    .employeeCode(resultSet.getString("code"))
                    .build();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
