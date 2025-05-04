package vn.tdsoftware.hrm_backend.dto.decision.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RewardAndPenaltyDecisionRequest {
    private String code;
    private LocalDate date;
    private long employeeId;
    private long rewardAndPenaltyId;
    private Integer amount;
}
