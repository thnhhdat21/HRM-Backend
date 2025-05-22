package vn.tdsoftware.hrm_backend.dto.contract.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
public class ContractDetailResponse {
    private Long employeeId;
    private String employeeName;
    private String employeeCode;
    private String contractCode;
    private Long contractId;
    private long contractType;
    private long department;
    private long jobPosition;
    private long duty;
    private String contractMethod;
    private int salaryGross;
    private Date dateStart;
    private Date dateEnd;
    private Date dateSign;
    private Long parent;
    private List<ContractHasAllowanceResponse> allowances;
}
