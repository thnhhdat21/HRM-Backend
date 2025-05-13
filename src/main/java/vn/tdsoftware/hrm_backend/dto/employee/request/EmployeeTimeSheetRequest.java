package vn.tdsoftware.hrm_backend.dto.employee.request;

import lombok.Getter;
import lombok.Setter;

import java.time.YearMonth;

@Getter
@Setter
public class EmployeeTimeSheetRequest {
    private long employeeId;
    private YearMonth yearMonth;
}
