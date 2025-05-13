package vn.tdsoftware.hrm_backend.mapper;

import vn.tdsoftware.hrm_backend.dto.holiday.response.HolidayResponse;
import vn.tdsoftware.hrm_backend.entity.Holiday;

public class HolidayMapper {
    public static HolidayResponse mapToHolidayResponse(Holiday holiday) {
        return HolidayResponse.builder()
                .id(holiday.getId())
                .reason(holiday.getReason())
                .type(holiday.getType())
                .date(holiday.getDate())
                .description(holiday.getDescription())
                .build();
    }
}
