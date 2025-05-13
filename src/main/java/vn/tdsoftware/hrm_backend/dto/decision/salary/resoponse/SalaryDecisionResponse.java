package vn.tdsoftware.hrm_backend.dto.decision.salary.resoponse;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class SalaryDecisionResponse {
    private long decisionId;
    private String code;
    private LocalDate dateDecision;
    private long employeeId;
    private String reason;
    private LocalDate dateActive;
    private int amountOld;
    private int amountNew;
    private int state;
    private List<SalaryHasAllowanceResponse> allowances;

}
