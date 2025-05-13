package vn.tdsoftware.hrm_backend.dto.decision.transferandappoint.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TransferDecisionRequest {
    private long decisionId;
    private String code;
    private LocalDate date;
    private long employeeId;
    private String reason;
    private long departmentNewId;
    private long jobPositionNewId;
    private int type;
}
