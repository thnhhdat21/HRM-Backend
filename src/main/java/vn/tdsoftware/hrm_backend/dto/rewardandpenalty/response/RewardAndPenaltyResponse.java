package vn.tdsoftware.hrm_backend.dto.rewardandpenalty.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RewardAndPenaltyResponse {
    private long id;
    private String name;
    private int amount;
    private String status;
    private String des;
    private String createBy;
}
