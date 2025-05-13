package vn.tdsoftware.hrm_backend.mapper.response.onleave;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.onleave.response.OnLeaveResponse;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;

public class OnLeaveResponseMapper implements RowMapper<OnLeaveResponse> {
    @Override
    public OnLeaveResponse mapRow(ResultSet resultSet) {
        try {
//            return OnLeaveResponse.builder()
//                    .totalDay(resultSet.getDouble("totalDay"))
//                    .usedDay(resultSet.getDouble("usedDay"))
//                    .build();
            return null;
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
