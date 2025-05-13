package vn.tdsoftware.hrm_backend.dto.decision.rewardandpenalty.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RewardAndPenaltyDecisionRequest {
    private long decisionId;
    private String code;
    private LocalDate date;
    private long employeeId;
    private long rewardAndPenaltyId;
    private Integer amount;
    private int type;
}
