package vn.tdsoftware.hrm_backend.dto.insurance.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class InsuranceResponse {
    private long id;
    private String code;
    private String fullName;
    private String department;
    private String jobPosition;
    private String duty;
    private String insuranceNumber;
    private String insuranceCard;
}
