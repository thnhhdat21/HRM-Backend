package vn.tdsoftware.hrm_backend.mapper.response.employee;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.salarytable.response.SalaryMonth;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;
import java.time.YearMonth;

public class SalaryMonthEmployeeMapper implements RowMapper<SalaryMonth> {

    @Override
    public SalaryMonth mapRow(ResultSet resultSet) {
        try {
            return SalaryMonth.builder()
                    .salaryDetailId(resultSet.getLong("salaryDetailId"))
                    .yearMonth(resultSet.getString("yearMonth") != null? YearMonth.parse(resultSet.getString("yearMonth")) : null)
                    .salary(resultSet.getInt("salaryReal"))
                    .tax(resultSet.getInt("totalTax"))
                    .build();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
