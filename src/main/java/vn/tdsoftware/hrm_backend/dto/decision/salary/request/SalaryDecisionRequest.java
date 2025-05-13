package vn.tdsoftware.hrm_backend.dto.decision.salary.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class SalaryDecisionRequest {
    private long decisionId;
    private String code;
    private LocalDate dateDecision;
    private long employeeId;
    private String reason;
    private LocalDate dateActive;
    private int amountNew;
    private List<SalaryHasAllowanceRequest> allowances;
}
