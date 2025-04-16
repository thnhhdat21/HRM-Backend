package vn.tdsoftware.hrm_backend.dao.impl;

import org.springframework.stereotype.Component;
import vn.tdsoftware.hrm_backend.dao.RewardAndPenaltyDAO;
import vn.tdsoftware.hrm_backend.dto.rewardandpenalty.response.RewardAndPenaltyResponse;
import vn.tdsoftware.hrm_backend.entity.RewardAndPenalty;
import vn.tdsoftware.hrm_backend.mapper.response.rewardandpenalty.RewardAndPenaltyResponseMapper;

import java.util.List;

@Component
public class RewardAndPenaltyDAOImpl extends AbstractDao<RewardAndPenalty> implements RewardAndPenaltyDAO {

    @Override
    public List<RewardAndPenaltyResponse> getList(int type) {
        String sql = "select rewardAndPenalty.id, " +
                "rewardAndPenalty.name, " +
                "rewardAndPenalty.amount, " +
                "rewardAndPenalty.status, " +
                "nv1.fullName as createBy, " +
                "rewardAndPenalty.description as des " +
                "from rewardAndPenalty " +
                "left join employee nv1 on rewardAndPenalty.createdBy = nv1.id " +
                "where rewardAndPenalty.isEnabled = true and rewardAndPenalty.type = ?";
        return query(sql, new RewardAndPenaltyResponseMapper(), type);
    }
}
