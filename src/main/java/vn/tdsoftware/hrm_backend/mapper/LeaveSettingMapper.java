package vn.tdsoftware.hrm_backend.mapper;

import vn.tdsoftware.hrm_backend.dto.leavesetting.response.LeaveSettingResponse;
import vn.tdsoftware.hrm_backend.entity.LeaveSetting;

public class LeaveSettingMapper {
    public static LeaveSettingResponse mapToLeaveSettingResponse(LeaveSetting leaveSetting) {
        return LeaveSettingResponse.builder()
                .id(leaveSetting.getId())
                .annualLeaveDays(leaveSetting.getAnnualLeaveDays())
                .leaveCycleStart(leaveSetting.getLeaveCycleStart())
                .leaveCycleEnd(leaveSetting.getLeaveCycleEnd())
                .leaveCycleUnit(leaveSetting.getLeaveCycleUnit())
                .accrualMethod(leaveSetting.getAccrualMethod())
                .receiveNewLeaveDate(leaveSetting.getReceiveNewLeaveDate())
                .seniorLeaveEnabled(leaveSetting.getSeniorLeaveEnabled())
                .seniorYears(leaveSetting.getSeniorYears())
                .build();
    }
}
