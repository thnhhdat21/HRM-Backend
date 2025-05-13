package vn.tdsoftware.hrm_backend.mapper.response.allowance;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.allowance.response.AllowanceResponse;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;

public class AllowanceResponseMapper implements RowMapper<AllowanceResponse> {
    @Override
    public AllowanceResponse mapRow(ResultSet resultSet) {
        try {
            return AllowanceResponse.builder()
                    .id(resultSet.getLong("id"))
                    .name(resultSet.getString("name"))
                    .amount(resultSet.getInt("amount"))
                    .unit(resultSet.getString("unit"))
                    .build();
        } catch (Exception e){


            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
