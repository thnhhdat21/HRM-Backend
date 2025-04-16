package vn.tdsoftware.hrm_backend.dto.leavesetting.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeaveSettingRequest {
    private int id;
    private int annualLeaveDays;
    private int leaveCycleStart;
    private int leaveCycleEnd;
    private String leaveCycleUnit;
    private int accrualMethod;
    private int receiveNewLeaveDate;
    private boolean seniorLeaveEnabled;
    private int seniorYears;
}
