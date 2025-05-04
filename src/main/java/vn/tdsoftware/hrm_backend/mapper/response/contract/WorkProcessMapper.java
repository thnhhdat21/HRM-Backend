package vn.tdsoftware.hrm_backend.mapper.response.contract;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.contract.response.WorkProcessResponse;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;

public class WorkProcessMapper implements RowMapper<WorkProcessResponse> {

    @Override
    public WorkProcessResponse mapRow(ResultSet resultSet) {
        try {
            return WorkProcessResponse.builder()
                    .code(resultSet.getString("code"))
                    .dateStart(resultSet.getDate("dateStart"))
                    .dateSign(resultSet.getDate("dateSign"))
                    .status(resultSet.getInt("status"))
                    .department(resultSet.getString("department"))
                    .jobPosition(resultSet.getString("jobPosition"))
                    .duty(resultSet.getString("duty"))
                    .contractType(resultSet.getString("contractType"))
                    .build();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
