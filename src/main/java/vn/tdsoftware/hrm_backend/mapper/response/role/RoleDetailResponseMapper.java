package vn.tdsoftware.hrm_backend.mapper.response.role;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.role.response.RoleDetailResponse;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;

public class RoleDetailResponseMapper  implements RowMapper<RoleDetailResponse> {
    @Override
    public RoleDetailResponse mapRow(ResultSet resultSet) {
        try {
            StringBuilder permissions = new StringBuilder();
            boolean accountAdmin = false;
            boolean accountDefault = false;

            if (resultSet.next()) {
                RoleDetailResponse roleDetailResponse = RoleDetailResponse.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .code(resultSet.getString("code"))
                        .description(resultSet.getString("description"))
                        .build();
                do {
                    if (resultSet.getBoolean("accountAdmin")) {
                        accountAdmin = true;
                        break;
                    } else if (resultSet.getBoolean("accountDefault")) {
                        accountDefault = true;
                        break;
                    } else {
                        if (!resultSet.getString("permissionId").isEmpty()) {
                            if (!permissions.isEmpty()) {
                                permissions.append(", ");
                            }
                            permissions.append(resultSet.getString("permissionId"));
                        }
                    }
                } while (resultSet.next());

                roleDetailResponse.setAccountAdmin(accountAdmin);
                roleDetailResponse.setAccountDefault(accountDefault);
                roleDetailResponse.setPermissions(permissions.toString());

                return roleDetailResponse;
            }
            return null;
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
