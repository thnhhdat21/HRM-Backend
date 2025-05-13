package vn.tdsoftware.hrm_backend.dto.onleave.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OnLeaveRequest {
    private long employeeId;
    private double seniorDay;
    private double regulaDay;
    private double usedDay;
}
