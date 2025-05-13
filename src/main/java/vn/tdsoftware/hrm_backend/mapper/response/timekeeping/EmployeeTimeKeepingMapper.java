package vn.tdsoftware.hrm_backend.mapper.response.timekeeping;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.timekeeping.response.EmployeeTimeKeeping;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.Optional;

public class EmployeeTimeKeepingMapper implements RowMapper<EmployeeTimeKeeping> {

    @Override
    public EmployeeTimeKeeping mapRow(ResultSet resultSet) {
        try{
            return EmployeeTimeKeeping.builder()
                    .employeeId(resultSet.getLong("employeeId"))
                    .employeeCode(resultSet.getString("employeeCode"))
                    .employeeName(resultSet.getString("employeeName"))
                    .department(resultSet.getString("department"))
                    .jobPosition(resultSet.getString("jobPosition"))
                    .onLeaveTotal(resultSet.getDouble("onLeaveTotal"))
                    .onLeaveUsed(resultSet.getDouble("onLeaveUsed"))
                    .overTimeTotal(resultSet.getDouble("overTimeTotal"))
                    .isLate(resultSet.getBoolean("isLate"))
                    .dateWorking(Optional.ofNullable(resultSet.getDate("dateWorking"))
                            .map(Date::toLocalDate)
                            .orElse(null))
                    .workDay(resultSet.getDouble(("workDay")))
                    .symbolLetter(resultSet.getString("symbolLetter"))
                    .build();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
