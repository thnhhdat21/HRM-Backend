package vn.tdsoftware.hrm_backend.dto.employee.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InsuranceEmployeeRequest {
    private long employeeId;
    private String insuranceNumber;
    private String insuranceCard;
}
