package vn.tdsoftware.hrm_backend.mapper.response.contract;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.contract.response.EndJobCurrentDate;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;

public class EndJobCurrentDateMapper implements RowMapper<EndJobCurrentDate> {

    @Override
    public EndJobCurrentDate mapRow(ResultSet resultSet) {
        try {
            return EndJobCurrentDate.builder()
                    .currentDate(resultSet.getDate("currentDate").toLocalDate())
                    .contractId(resultSet.getLong("contractId"))
                    .reason(resultSet.getString("reason"))
                    .build();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
