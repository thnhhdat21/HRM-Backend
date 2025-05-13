package vn.tdsoftware.hrm_backend.dto.decision.rewardandpenalty.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class RewardAndPenaltyDecisionResponse {
    private long decisionId;
    private String code;
    private LocalDate date;
    private long employeeId;
    private long rewardAndPenaltyId;
    private Integer amount;
    private int state;
    private int type;
}
