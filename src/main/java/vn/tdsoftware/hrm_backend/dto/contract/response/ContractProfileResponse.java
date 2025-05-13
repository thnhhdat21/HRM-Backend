package vn.tdsoftware.hrm_backend.dto.contract.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import vn.tdsoftware.hrm_backend.dto.allowance.response.AllowanceResponse;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContractProfileResponse {
    private long contractId;
    private String contractCode;
    private String employeeName;
    private String employeeCode;
    private String contractType;
    private String department;
    private String jobPosition;
    private String duty;
    private String method;
    private Date dateStart;
    private Date dateEnd;
    private Date dateSign;
    private Integer contractStatus;
    private int salaryGross;
    private int state;
    private Long parent;
    private String description;
    private List<AllowanceResponse> allowances;
}
