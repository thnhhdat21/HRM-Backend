package vn.tdsoftware.hrm_backend.mapper.response.employee;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.employee.response.EmployeeTypeCount;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;

public class EmployeeTypeCountMapper implements RowMapper<EmployeeTypeCount> {

    @Override
    public EmployeeTypeCount mapRow(ResultSet resultSet) {
        try {
            return EmployeeTypeCount.builder()
                    .type(resultSet.getInt("type"))
                    .count(resultSet.getInt("count"))
                    .build();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
