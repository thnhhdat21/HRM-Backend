package vn.tdsoftware.hrm_backend.mapper.response.history.contract;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.history.contract.response.ContractHistoryResponse;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;

public class ContractHistoryResponseMapper implements RowMapper<ContractHistoryResponse> {
    @Override
    public ContractHistoryResponse mapRow(ResultSet resultSet) {
        try {
            return ContractHistoryResponse.builder()
                    .title(resultSet.getString("title"))
                    .state(resultSet.getInt("state"))
                    .createdAt(resultSet.getTimestamp("createdAt"))
                    .createdBy(resultSet.getString("createdBy"))
                    .build();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
