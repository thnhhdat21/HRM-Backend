package vn.tdsoftware.hrm_backend.mapper.response.assetgroup;

import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.account.response.AccountCountResponse;
import vn.tdsoftware.hrm_backend.dto.assetgroup.response.AssetGroupResponse;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.RowMapper;

import java.sql.ResultSet;

public class AssetGroupResponseMapper implements RowMapper<AssetGroupResponse> {

    @Override
    public AssetGroupResponse mapRow(ResultSet resultSet) {
        try{
            return AssetGroupResponse.builder()
                    .id(resultSet.getLong("id"))
                    .name(resultSet.getString("name"))
                    .parentId(resultSet.getLong("parentId"))
                    .parentName(resultSet.getString("parentName"))
                    .status(resultSet.getString("status"))
                    .build();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SQL_MAPPER_ERROR);
        }
    }
}
