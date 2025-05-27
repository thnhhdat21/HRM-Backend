package vn.tdsoftware.hrm_backend.mapper.response.report;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.report.response.ReportCountEmployee;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;

public class ReportCountEmployeeMapper implements RowMapper<ReportCountEmployee> {

    @Override
    public ReportCountEmployee mapRow(ResultSet resultSet) {
        try {
            return ReportCountEmployee.builder()
                    .department(resultSet.getString("name"))
                    .count(resultSet.getInt("countEmployee"))
                    .build();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
