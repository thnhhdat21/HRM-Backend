package vn.tdsoftware.hrm_backend.mapper.response.decision;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.decision.response.CountDecisionResponse;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;

public class CountDecisionMapper implements RowMapper<CountDecisionResponse> {
    @Override
    public CountDecisionResponse mapRow(ResultSet resultSet) {
        try {
            return CountDecisionResponse.builder()
                    .id(resultSet.getInt("id"))
                    .name(resultSet.getString("name"))
                    .count(resultSet.getInt("count"))
                    .build();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
