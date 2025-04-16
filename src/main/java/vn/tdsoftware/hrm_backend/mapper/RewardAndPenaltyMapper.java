package vn.tdsoftware.hrm_backend.mapper;

import vn.tdsoftware.hrm_backend.dto.rewardandpenalty.response.RewardAndPenaltyResponse;
import vn.tdsoftware.hrm_backend.entity.RewardAndPenalty;

public class RewardAndPenaltyMapper {
    public static RewardAndPenaltyResponse mapToRewardAndPenaltyResponse(RewardAndPenalty rewardAndPenalty) {
        return RewardAndPenaltyResponse.builder()
                .id(rewardAndPenalty.getId())
                .name(rewardAndPenalty.getName())
                .amount(rewardAndPenalty.getAmount())
                .status(rewardAndPenalty.getStatus())
                .des(rewardAndPenalty.getDescription())
                .build();
    }
}
