package vn.tdsoftware.hrm_backend.dto.rewardandpenalty.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RewardAndPenaltyRequest {
    private Long id;
    private String name;
    private int amount;
    private String des;
    private Integer type;
}
