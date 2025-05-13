package vn.tdsoftware.hrm_backend.mapper.response;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.letter.response.LeaveLetterUnpaidSalary;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;

public class LeaveLetterUnpaidSalaryMapper implements RowMapper<LeaveLetterUnpaidSalary> {


    @Override
    public LeaveLetterUnpaidSalary mapRow(ResultSet resultSet) {
        try {
            return LeaveLetterUnpaidSalary.builder()
                    .employeeId(resultSet.getLong("employeeId"))
                    .build();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
