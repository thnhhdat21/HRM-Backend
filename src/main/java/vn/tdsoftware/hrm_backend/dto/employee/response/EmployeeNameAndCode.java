package vn.tdsoftware.hrm_backend.dto.employee.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EmployeeNameAndCode {
    private long employeeId;
    private String employeeName;
    private String employeeCode;
}
