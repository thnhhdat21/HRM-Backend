package vn.tdsoftware.hrm_backend.mapper.response.account;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.account.response.AccountDetailResponse;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;

public class AccountDetailMapper implements RowMapper<AccountDetailResponse> {
    @Override
    public AccountDetailResponse mapRow(ResultSet resultSet) {
        try {
            return AccountDetailResponse.builder()
                    .id(resultSet.getLong("id"))
                    .name(resultSet.getString("fullName"))
                    .username(resultSet.getString("username"))
                    .department(resultSet.getString("department"))
                    .email(resultSet.getString("email"))
                    .dateOfBirth(resultSet.getDate("dateOfBirth"))
                    .phoneNumber(resultSet.getString("phoneNumber"))
                    .status(resultSet.getInt("status"))
                    .roleId(resultSet.getInt("roleId"))
                    .permissions(resultSet.getString("permissions"))
                    .build();

        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
