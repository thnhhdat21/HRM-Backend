package vn.tdsoftware.hrm_backend.entity.onleave.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OnLeaveResponse {
    private long employeeId;
    private int totalDay;
    private int usedDay;
}
