package vn.tdsoftware.hrm_backend.mapper.response;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.contracttype.response.CountContractTypeResponse;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;

public class CountContractTypeMapper implements RowMapper<CountContractTypeResponse> {
    @Override
    public CountContractTypeResponse mapRow(ResultSet resultSet) {
        try{
            return CountContractTypeResponse.builder()
                    .id(resultSet.getLong("id"))
                    .name(resultSet.getString("name"))
                    .count(resultSet.getInt("count"))
                    .build();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
