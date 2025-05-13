package vn.tdsoftware.hrm_backend.mapper.response.insurance;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.insurance.response.InsuranceResponse;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;

public class InsuranceResponseMapper implements RowMapper<InsuranceResponse> {

    @Override
    public InsuranceResponse mapRow(ResultSet resultSet) {
        try {
            return InsuranceResponse.builder()
                    .id(resultSet.getLong("id"))
                    .code(resultSet.getString("code"))
                    .fullName(resultSet.getString("fullName"))
                    .department(resultSet.getString("department"))
                    .jobPosition(resultSet.getString("jobPosition"))
                    .duty(resultSet.getString("duty"))
                    .insuranceNumber(resultSet.getString("insuranceNumber"))
                    .insuranceCard(resultSet.getString("insuranceCard"))
                    .build();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
