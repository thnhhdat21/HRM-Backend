package vn.tdsoftware.hrm_backend.mapper.response.letter;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.letter.response.WorkTimeEmployee;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;

public class WorkTimeEmployeeMapper implements RowMapper<WorkTimeEmployee> {

    @Override
    public WorkTimeEmployee mapRow(ResultSet resultSet) {
        try {
            return WorkTimeEmployee.builder()
                    .employeeId(resultSet.getLong("id"))
                    .letterId(resultSet.getLong("letterId"))
                    .backEarly(resultSet.getTime("backEarly").toLocalTime())
                    .goLate(resultSet.getTime("goLate").toLocalTime())
                    .build();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
