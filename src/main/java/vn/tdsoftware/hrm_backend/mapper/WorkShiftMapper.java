package vn.tdsoftware.hrm_backend.mapper;

import vn.tdsoftware.hrm_backend.dto.workshift.response.WorkShiftResponse;
import vn.tdsoftware.hrm_backend.entity.WorkShift;


public class WorkShiftMapper {
    public static WorkShiftResponse mapToWorkShiftResponse(WorkShift workShift) {
        return  WorkShiftResponse.builder()
                .id(workShift.getId())
                .code(workShift.getCode())
                .name(workShift.getName())
                .timeIn(workShift.getTimeIn())
                .timeOut(workShift.getTimeOut())
                .breakStartTime(workShift.getBreakStartTime())
                .breakEndTime(workShift.getBreakEndTime())
                .totalTime(workShift.getTotalTime())
                .checkinFirst(workShift.getCheckinFirst())
                .checkoutLater(workShift.getCheckoutLater())
                .build();
    }
}
