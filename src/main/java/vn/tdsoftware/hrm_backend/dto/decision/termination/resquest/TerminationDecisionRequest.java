package vn.tdsoftware.hrm_backend.dto.decision.termination.resquest;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TerminationDecisionRequest {
    private long decisionId;
    private String code;
    private LocalDate date;
    private long employeeId;
    private String reason;
}
