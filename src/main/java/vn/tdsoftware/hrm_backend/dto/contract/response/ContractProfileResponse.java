package vn.tdsoftware.hrm_backend.dto.contract.response;

import lombok.*;
import vn.tdsoftware.hrm_backend.dto.allowance.response.AllowanceResponse;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContractProfileResponse {
    private String contractCode;
    private String employeeName;
    private String contractType;
    private String department;
    private String jobPosition;
    private String duty;
    private String method;
    private Date dateStart;
    private Date dateSign;
    private Integer contractStatus;
    private int salaryGross;
    private List<AllowanceResponse> allowances;

}
