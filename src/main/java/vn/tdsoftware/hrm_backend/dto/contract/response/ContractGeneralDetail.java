package vn.tdsoftware.hrm_backend.dto.contract.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
public class ContractGeneralDetail {
    private int salaryGross;
    private long contractId;
    private long department;
    private long jobPosition;
    private boolean isAutoCheckin;
    private List<ContractHasAllowanceResponse> allowances;
}
