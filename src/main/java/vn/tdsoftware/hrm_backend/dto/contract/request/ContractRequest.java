package vn.tdsoftware.hrm_backend.dto.contract.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class ContractRequest {
    private Long employeeId;
    private String contractCode;
    private long contractId;
    private long contractType;
    private Long department;
    private Long jobPosition;
    private String contractMethod;
    private int salaryGross;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private LocalDate dateSign;
    private Long parent;
    private int createType;
    private String description;
    private List<ContractHasAllowanceRequest> allowances;
}
