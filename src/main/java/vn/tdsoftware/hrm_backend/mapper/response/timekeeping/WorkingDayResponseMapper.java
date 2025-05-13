package vn.tdsoftware.hrm_backend.mapper.response.timekeeping;


import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.timekeeping.response.WorkingDayResponse;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;
import java.util.Arrays;
import java.util.stream.Collectors;

public class WorkingDayResponseMapper implements RowMapper<WorkingDayResponse> {

    @Override
    public WorkingDayResponse mapRow(ResultSet resultSet) {
        try {

             WorkingDayResponse response =  WorkingDayResponse.builder()
                .checkin((resultSet.getTime("timeIn") != null
                        ? resultSet.getTime("timeIn").toLocalTime()
                        : null))
                .checkout((resultSet.getTime("timeOut") != null
                        ? resultSet.getTime("timeOut").toLocalTime()
                        : null))
                .timeLate((resultSet.getTime("timeLate") != null
                        ? resultSet.getTime("timeLate").toLocalTime()
                        : null))
                .timeEarly((resultSet.getTime("timeEarly") != null
                        ? resultSet.getTime("timeEarly").toLocalTime()
                        : null))
                .isLate(resultSet.getBoolean("isLate"))
                .workDay(resultSet.getDouble("workDay"))
                .build();
            String letterIds = resultSet.getString("letterIds");
            if (letterIds != null && !letterIds.isEmpty()){
                response.setLetterIds(Arrays.stream(letterIds.split(","))
                        .map(Long::parseLong)
                        .collect(Collectors.toList()));
            }
            return response;
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
