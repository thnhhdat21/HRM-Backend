package vn.tdsoftware.hrm_backend.mapper.response.role;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.role.response.RoleResponse;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;


public class RoleResponseMapper implements RowMapper<RoleResponse> {
    @Override
    public RoleResponse mapRow(ResultSet resultSet) {
        try {
            return RoleResponse.builder()
                    .id(resultSet.getInt("id"))
                    .name(resultSet.getString("name"))
                    .accountAdmin(resultSet.getBoolean("accountAdmin"))
                    .accountDefault(resultSet.getBoolean("accountDefault"))
                    .count(resultSet.getInt("count"))
                    .createdBy(resultSet.getString("createdBy"))
                    .updatedBy(resultSet.getString("updatedBy"))
                    .createdDate(resultSet.getDate("createdAt"))
                    .updatedDate(resultSet.getDate("updatedAt"))
                    .build();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
