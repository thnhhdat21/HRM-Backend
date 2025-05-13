package vn.tdsoftware.hrm_backend.dto.timekeeping.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EmployeeCountTimeKeeping {
    private long employeeId;
    private double countTimeKeeping;
}
