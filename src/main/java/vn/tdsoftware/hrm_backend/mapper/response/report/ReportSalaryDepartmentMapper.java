package vn.tdsoftware.hrm_backend.mapper.response.report;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.report.response.ReportSalaryDepartment;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;

public class ReportSalaryDepartmentMapper implements RowMapper<ReportSalaryDepartment> {

    @Override
    public ReportSalaryDepartment mapRow(ResultSet resultSet) {
        try {
            return ReportSalaryDepartment.builder()
                    .department(resultSet.getString("name"))
                    .sumSalary(resultSet.getInt("sumSalary"))
                    .build();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
