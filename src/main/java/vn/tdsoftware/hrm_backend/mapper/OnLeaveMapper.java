package vn.tdsoftware.hrm_backend.mapper;

import vn.tdsoftware.hrm_backend.dto.onleave.response.OnLeaveResponse;
import vn.tdsoftware.hrm_backend.entity.OnLeave;

public class OnLeaveMapper {
    public static OnLeaveResponse mapToOnLeaveResponse(OnLeave onLeave) {
        return OnLeaveResponse.builder()
                .seniorDay(onLeave.getSeniorDay())
                .regulaDay(onLeave.getRegulaDay())
                .usedDay(onLeave.getUsedDay())
                .build();
    }
}
