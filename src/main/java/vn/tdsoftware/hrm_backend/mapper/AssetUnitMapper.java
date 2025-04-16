package vn.tdsoftware.hrm_backend.mapper;

import vn.tdsoftware.hrm_backend.dto.assetunit.response.AssetUnitResponse;
import vn.tdsoftware.hrm_backend.entity.AssetUnit;

public class AssetUnitMapper {

    public static AssetUnitResponse mapToAssetUnitResponse(AssetUnit assetUnit) {
        return AssetUnitResponse.builder()
                .id(assetUnit.getId())
                .name(assetUnit.getName())
                .status(assetUnit.getStatus())
                .build();
    }
}
