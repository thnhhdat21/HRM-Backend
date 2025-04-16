package vn.tdsoftware.hrm_backend.mapper.response.rewardandpenalty;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.rewardandpenalty.response.RewardAndPenaltyResponse;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;

public class RewardAndPenaltyResponseMapper implements RowMapper<RewardAndPenaltyResponse> {
    @Override
    public RewardAndPenaltyResponse mapRow(ResultSet resultSet) {
        try {
            return RewardAndPenaltyResponse.builder()
                    .id(resultSet.getLong("id"))
                    .name(resultSet.getString("name"))
                    .amount(resultSet.getInt("amount"))
                    .status(resultSet.getString("status"))
                    .des(resultSet.getString("des"))
                    .createBy(resultSet.getString("createBy"))
                    .build();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
