package vn.tdsoftware.hrm_backend.dto.decision.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RewardAndPenaltyOfEmployee {
    private int type;
    private int amount;
}
