package vn.tdsoftware.hrm_backend.mapper.response.contract;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.contract.response.ContractResponse;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;

public class ContractResponseMapper implements RowMapper<ContractResponse> {
    @Override
    public ContractResponse mapRow(ResultSet resultSet) {
        try {
            return ContractResponse.builder()
                    .contractId(resultSet.getLong("contractId"))
                    .createdBy(resultSet.getString("createdBy"))
                    .contractCode(resultSet.getString("contractCode"))
                    .employeeId(resultSet.getLong("employeeId"))
                    .employeeCode(resultSet.getString("employeeCode"))
                    .employeeName(resultSet.getString("employeeName"))
                    .department(resultSet.getString("department"))
                    .contractName(resultSet.getString("contractName"))
                    .contractStatus(resultSet.getString("contractStatus"))
                    .dateSign(resultSet.getDate("dateSign"))
                    .dateEnd(resultSet.getDate("dateEnd"))
                    .contractState(resultSet.getInt("contractState"))
                    .contractDateLiquid(resultSet.getDate("contractDateLiquid"))
                    .build();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
