package vn.tdsoftware.hrm_backend.dto.leavesetting.response;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LeaveSettingResponse {
    private Integer id;
    private Integer annualLeaveDays;
    private Integer leaveCycleStart;
    private Integer leaveCycleEnd;
    private String leaveCycleUnit;
    private Integer accrualMethod;
    private Integer receiveNewLeaveDate;
    private Boolean seniorLeaveEnabled;
    private Integer seniorYears;
}
