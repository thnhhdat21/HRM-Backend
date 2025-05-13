package vn.tdsoftware.hrm_backend.mapper.response.timekeeping;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.timekeeping.response.EmployeeCountTimeKeeping;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;

public class EmployeeCountTimeKeepingMapper implements RowMapper<EmployeeCountTimeKeeping> {

    @Override
    public EmployeeCountTimeKeeping mapRow(ResultSet resultSet) {
        try {
            return EmployeeCountTimeKeeping.builder()
                    .employeeId(resultSet.getLong("employeeId"))
                    .countTimeKeeping(resultSet.getLong("workday"))
                    .build();
        }catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
