package vn.tdsoftware.hrm_backend.mapper.response.letter;


import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.letter.response.leave.LeaveLetterResponse;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;

public class LeaveLetterResponseMapper implements RowMapper<LeaveLetterResponse> {

    @Override
    public LeaveLetterResponse mapRow(ResultSet resultSet) {
        try {
            return LeaveLetterResponse.builder()
                    .letterId(resultSet.getLong("letterId"))
                    .employeeId(resultSet.getLong("employeeId"))
                    .letterState(resultSet.getInt("letterState"))
                    .letterReason(resultSet.getString("letterReason"))
                    .letterType(resultSet.getInt("letterType"))
                    .dateStart(resultSet.getTimestamp("dateStart").toLocalDateTime())
                    .dateEnd(resultSet.getTimestamp("dateEnd").toLocalDateTime())
                    .dateRegis(resultSet.getDate("dateRegis"))
                    .workdayEnabled(resultSet.getBoolean("workdayEnabled"))
                    .total(resultSet.getDouble("total"))
                    .description(resultSet.getString("description"))
                    .build();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
