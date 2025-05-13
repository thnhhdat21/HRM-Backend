package vn.tdsoftware.hrm_backend.dto.contract.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ContractHasAllowanceResponse {
    private long id;
    private long allowanceId;
    private int amount;
    private String unit;
}
