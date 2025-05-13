package vn.tdsoftware.hrm_backend.mapper.response.employee;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.employee.response.EmployeeTimeSheet;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;

public class EmployeeTimeSheetMapper implements RowMapper<EmployeeTimeSheet> {

    @Override
    public EmployeeTimeSheet mapRow(ResultSet resultSet) {
        try {
            return EmployeeTimeSheet.builder()
                    .dateWorking(resultSet.getDate("dateWorking").toLocalDate())
                    .workDay(resultSet.getDouble("workDay"))
                    .timeIn((resultSet.getTime("timeIn") != null
                            ? resultSet.getTime("timeIn").toLocalTime()
                            : null))
                    .timeOut((resultSet.getTime("timeOut") != null
                            ? resultSet.getTime("timeOut").toLocalTime()
                            : null))
                    .timeLate((resultSet.getTime("timeLate") != null
                            ? resultSet.getTime("timeLate").toLocalTime()
                            : null))
                    .timeEarly((resultSet.getTime("timeEarly") != null
                            ? resultSet.getTime("timeEarly").toLocalTime()
                            : null))
                    .isLate(resultSet.getBoolean("isLate"))
                    .letterId(resultSet.getLong("letterId"))
                    .letterState(resultSet.getInt("letterState"))
                    .build();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
