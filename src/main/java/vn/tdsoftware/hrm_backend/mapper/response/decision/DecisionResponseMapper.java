package vn.tdsoftware.hrm_backend.mapper.response.decision;


import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.decision.response.DecisionResponse;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;

public class DecisionResponseMapper implements RowMapper<DecisionResponse> {
    @Override
    public DecisionResponse mapRow(ResultSet resultSet) {
        try {
            return DecisionResponse.builder()
                    .decisionId(resultSet.getInt("decisionId"))
                    .createdBy(resultSet.getString("createdBy"))
                    .employeeName(resultSet.getString("employeeName"))
                    .decisionCode(resultSet.getString("decisionCode"))
                    .decisionDate(resultSet.getDate("decisionDate"))
                    .decisionType(resultSet.getInt("decisionType"))
                    .decisionState(resultSet.getString("decisionState"))
                    .createdAt(resultSet.getTimestamp("createdAt"))
                    .build();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
