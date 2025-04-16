package vn.tdsoftware.hrm_backend.mapper.response.account;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.account.response.AccountResponse;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;

public class AccountResponseMapper implements RowMapper<AccountResponse> {
    @Override
    public AccountResponse mapRow(ResultSet resultSet) {
        try {
            return AccountResponse.builder()
                    .id(resultSet.getLong("id"))
                    .username(resultSet.getString("username"))
                    .role(resultSet.getString("role"))
                    .employeeCode(resultSet.getString("employeeCode"))
                    .fullName(resultSet.getString("fullName"))
                    .department(resultSet.getString("department"))
                    .createDate(resultSet.getDate("createDate"))
                    .activeDate(resultSet.getDate("activeDate"))
                    .build();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
