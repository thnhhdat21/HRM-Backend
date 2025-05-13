package vn.tdsoftware.hrm_backend.mapper.response.holiday;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.holiday.response.HolidayResponse;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;

public class HolidayResponseMapper implements RowMapper<HolidayResponse> {

    @Override
    public HolidayResponse mapRow(ResultSet resultSet) {
        try {
            return HolidayResponse.builder()
                    .id(resultSet.getInt("holidayId"))
                    .reason(resultSet.getString("reason"))
                    .type(resultSet.getInt("type"))
                    .date(resultSet.getDate("date").toLocalDate())
                    .description(resultSet.getString("description"))
                    .build();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
