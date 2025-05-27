package vn.tdsoftware.hrm_backend.mapper.response.report;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.report.response.ReportSalaryMonth;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportSalaryMonthMapper implements RowMapper<ReportSalaryMonth> {


    @Override
    public ReportSalaryMonth mapRow(ResultSet resultSet) {
        try {
            String department = null;
            if (hasColumn(resultSet)) {
                department = resultSet.getString("department");
            }

            return ReportSalaryMonth.builder()
                    .yearMonth(resultSet.getString("yearMonth"))
                    .department(department)
                    .sumSalary(resultSet.getLong("sumSalary"))
                    .build();

        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }

    private boolean hasColumn(ResultSet rs) {
        try {
            rs.findColumn("department");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }


}
