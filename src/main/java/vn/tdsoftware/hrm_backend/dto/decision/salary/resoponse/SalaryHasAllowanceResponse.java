package vn.tdsoftware.hrm_backend.dto.decision.salary.resoponse;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SalaryHasAllowanceResponse {
    private long id;
    private long allowanceId;
    private int amount;
    private String unit;
}
