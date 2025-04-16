package vn.tdsoftware.hrm_backend.service;

import vn.tdsoftware.hrm_backend.dto.rewardandpenalty.request.RewardAndPenaltyRequest;
import vn.tdsoftware.hrm_backend.dto.rewardandpenalty.response.RewardAndPenaltyResponse;

import java.util.List;

public interface RewardAndPenaltyService {
    List<RewardAndPenaltyResponse> getList(int type);
    List<RewardAndPenaltyResponse> createNew(List<RewardAndPenaltyRequest> request);
    RewardAndPenaltyResponse update(RewardAndPenaltyRequest request);
    void delete(long id);
}
