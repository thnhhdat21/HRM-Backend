package vn.tdsoftware.hrm_backend.mapper.response.onleave;


import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.onleave.response.EmployeeOnLeaveResponse;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;

public class EmployeeOnLeaveMapper implements RowMapper<EmployeeOnLeaveResponse> {
    @Override
    public EmployeeOnLeaveResponse mapRow(ResultSet resultSet) {
        try {
            return EmployeeOnLeaveResponse.builder()
                    .employeeId(resultSet.getString("employeeId"))
                    .employeeCode(resultSet.getString("employeeCode"))
                    .employeeName(resultSet.getString("employeeName"))
                    .department(resultSet.getString("department"))
                    .jobPosition(resultSet.getString("jobPosition"))
                    .seniorDay(resultSet.getDouble(("seniorDay")))
                    .regulaDay(resultSet.getDouble(("regulaDay")))
                    .usedDay(resultSet.getDouble(("usedDay")))
                    .build();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
