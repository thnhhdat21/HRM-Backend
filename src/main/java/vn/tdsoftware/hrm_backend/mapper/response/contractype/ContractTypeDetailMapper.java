package vn.tdsoftware.hrm_backend.mapper.response.contractype;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.contracttype.response.ContractTypeDetail;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;

public class ContractTypeDetailMapper implements RowMapper<ContractTypeDetail> {
    @Override
    public ContractTypeDetail mapRow(ResultSet resultSet) {
        try{
            return ContractTypeDetail.builder()
                    .id(resultSet.getLong("id"))
                    .name(resultSet.getString("name"))
                    .type(resultSet.getString("type"))
                    .method(resultSet.getString("method"))
                    .term(resultSet.getInt("term"))
                    .unit(resultSet.getString("unit"))
                    .insurance(resultSet.getBoolean("insurance"))
                    .allowances(resultSet.getString("allowances"))
                    .build();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
