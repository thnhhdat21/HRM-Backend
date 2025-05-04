package vn.tdsoftware.hrm_backend.mapper.response.contract;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.contract.response.ContractOfEmployeeResponse;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;

public class ContractOfEmployeeResponseMapper implements RowMapper<ContractOfEmployeeResponse> {
    @Override
    public ContractOfEmployeeResponse mapRow(ResultSet resultSet) {
        try{
            return ContractOfEmployeeResponse.builder()
                    .id(resultSet.getLong("id"))
                    .code(resultSet.getString("code"))
                    .name(resultSet.getString("name"))
                    .status(resultSet.getInt("status"))
                    .state(resultSet.getInt("state"))
                    .dateSign(resultSet.getDate("dateSign"))
                    .dateStart(resultSet.getDate("dateStart"))
                    .build();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
