package vn.tdsoftware.hrm_backend.mapper.response.decision;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.decision.response.RewardAndPenaltyOfEmployee;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;

public class RewardAndPenaltyOfEmployeeMapper implements RowMapper<RewardAndPenaltyOfEmployee> {

    @Override
    public RewardAndPenaltyOfEmployee mapRow(ResultSet resultSet) {
        try {
            return RewardAndPenaltyOfEmployee.builder()
                    .type(resultSet.getInt("type"))
                    .amount(resultSet.getInt("amount"))
                    .build();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
