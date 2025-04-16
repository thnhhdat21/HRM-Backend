package vn.tdsoftware.hrm_backend.mapper.response.duty;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.duty.response.DutyResponse;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;

public class DutyResponseMapper implements RowMapper<DutyResponse> {
    @Override
    public DutyResponse mapRow(ResultSet resultSet) {
        try {
            return DutyResponse.builder()
                    .id(resultSet.getLong("id"))
                    .name(resultSet.getString("name"))
                    .description(resultSet.getString("description"))
                    .status(resultSet.getString("status"))
                    .createdBy(resultSet.getString("createdBy"))
                    .build();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
