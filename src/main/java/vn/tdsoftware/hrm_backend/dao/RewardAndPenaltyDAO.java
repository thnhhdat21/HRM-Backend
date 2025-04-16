package vn.tdsoftware.hrm_backend.dao;

import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.dto.rewardandpenalty.response.RewardAndPenaltyResponse;

import java.util.List;

@Repository
public interface RewardAndPenaltyDAO {
    List<RewardAndPenaltyResponse> getList(int type);
}
