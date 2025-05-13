package vn.tdsoftware.hrm_backend.dto.decision.transferandappoint.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class TransferAndAppointDecisionResponse {
    private long decisionId;
    private String code;
    private LocalDate date;
    private long employeeId;
    private String reason;
    private long departmentNewId;
    private long jobPositionNewId;
    private long departmentOldId;
    private long jobPositionOldId;
    private int state;
    private int type;
}
