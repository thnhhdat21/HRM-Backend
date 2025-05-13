package vn.tdsoftware.hrm_backend.dto.onleave.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EmployeeOnLeaveResponse {
    private String employeeId;
    private String employeeCode;
    private String employeeName;
    private String department;
    private String jobPosition;
    private double seniorDay;
    private double regulaDay;
    private double usedDay;
}
