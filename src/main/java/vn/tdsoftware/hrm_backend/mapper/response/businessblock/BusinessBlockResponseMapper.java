package vn.tdsoftware.hrm_backend.mapper.response.businessblock;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.businessblock.response.BusinessBlockResponse;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;

public class BusinessBlockResponseMapper  implements RowMapper<BusinessBlockResponse> {
    @Override
    public BusinessBlockResponse mapRow(ResultSet resultSet) {
        try {
            return BusinessBlockResponse.builder()
                    .id(resultSet.getInt("id"))
                    .name(resultSet.getString("name"))
                    .code(resultSet.getString("code"))
                    .build();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
