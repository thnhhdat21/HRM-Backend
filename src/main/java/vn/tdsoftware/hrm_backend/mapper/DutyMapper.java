package vn.tdsoftware.hrm_backend.mapper;

import vn.tdsoftware.hrm_backend.dto.duty.response.DutyResponse;
import vn.tdsoftware.hrm_backend.entity.Duty;

public class DutyMapper {
    public static DutyResponse mapToDutyResponse(Duty duty) {
        return DutyResponse.builder()
                .id(duty.getId())
                .name(duty.getName())
                .description(duty.getDescription())
                .build();
    }
}
