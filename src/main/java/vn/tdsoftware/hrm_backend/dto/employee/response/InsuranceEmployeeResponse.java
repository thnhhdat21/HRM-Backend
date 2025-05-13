package vn.tdsoftware.hrm_backend.dto.employee.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class InsuranceEmployeeResponse {
    private long employeeId;
    private String insuranceNumber;
    private String insuranceCard;
}
