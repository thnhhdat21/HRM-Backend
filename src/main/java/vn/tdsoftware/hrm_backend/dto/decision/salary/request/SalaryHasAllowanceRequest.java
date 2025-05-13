package vn.tdsoftware.hrm_backend.dto.decision.salary.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalaryHasAllowanceRequest {
    private long id;
    private long allowanceId;
    private int amount;
    private String unit;
    private String isUpdate;
}
