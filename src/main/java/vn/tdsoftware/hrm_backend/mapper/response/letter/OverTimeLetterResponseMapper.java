package vn.tdsoftware.hrm_backend.mapper.response.letter;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.letter.response.leave.LeaveLetterResponse;
import vn.tdsoftware.hrm_backend.dto.letter.response.overtime.OverTimeLetterResponse;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;

public class OverTimeLetterResponseMapper implements RowMapper<OverTimeLetterResponse> {

    @Override
    public OverTimeLetterResponse mapRow(ResultSet resultSet) {
        try {
            return OverTimeLetterResponse.builder()
                    .letterId(resultSet.getLong("letterId"))
                    .employeeId(resultSet.getLong("employeeId"))
                    .letterState(resultSet.getInt("letterState"))
                    .letterReason(resultSet.getString("letterReason"))
                    .letterType(resultSet.getInt("letterType"))
                    .timeStart(resultSet.getTime("timeStart").toLocalTime())
                    .timeEnd(resultSet.getTime("timeEnd").toLocalTime())
                    .dateRegis(resultSet.getDate("dateRegis").toLocalDate())
                    .description(resultSet.getString("description"))
                    .build();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
