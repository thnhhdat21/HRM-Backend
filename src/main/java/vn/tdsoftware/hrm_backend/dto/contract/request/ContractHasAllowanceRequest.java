package vn.tdsoftware.hrm_backend.dto.contract.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ContractHasAllowanceRequest {
    private long id;
    private long allowanceId;
    private int amount;
    private String unit;
    private String isUpdate;
}
