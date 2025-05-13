package vn.tdsoftware.hrm_backend.dto.employee.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class EmployeeTimeSheetResponse {
    private int workDayReal;
    private int totalWorkDay;
    private List<EmployeeTimeSheet> employeeTimeSheets;
}
