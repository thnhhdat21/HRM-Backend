package vn.tdsoftware.hrm_backend.mapper.response.account;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.account.response. AccountTypeCount;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;

public class AccountTypeCountMapper implements RowMapper< AccountTypeCount> {
    @Override
    public  AccountTypeCount mapRow(ResultSet resultSet) {
       try{
            return  AccountTypeCount.builder()
                    .statusId(resultSet.getInt("status"))
                    .count(resultSet.getInt("count"))
                    .build();
       } catch (Exception e) {
           throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
       }
    }
}
