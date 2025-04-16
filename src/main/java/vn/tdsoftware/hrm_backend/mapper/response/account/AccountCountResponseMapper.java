package vn.tdsoftware.hrm_backend.mapper.response.account;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.account.response.AccountCountResponse;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;

public class AccountCountResponseMapper implements RowMapper<AccountCountResponse> {
    @Override
    public AccountCountResponse mapRow(ResultSet resultSet) {
       try{
            return AccountCountResponse.builder()
                    .accountActive(resultSet.getInt("countActive"))
                    .accountNotActive(resultSet.getInt("countNotActive"))
                    .accountLock(resultSet.getInt("countLock"))
                    .build();
       } catch (Exception e) {
           throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
       }
    }
}
