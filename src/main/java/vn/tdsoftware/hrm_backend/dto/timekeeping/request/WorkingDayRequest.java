package vn.tdsoftware.hrm_backend.dto.timekeeping.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class WorkingDayRequest {
    private Long employeeId;
    private LocalDate workingDay;
}
