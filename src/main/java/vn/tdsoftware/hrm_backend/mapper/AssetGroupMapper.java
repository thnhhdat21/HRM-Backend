package vn.tdsoftware.hrm_backend.mapper;

import vn.tdsoftware.hrm_backend.dto.assetgroup.response.AssetGroupResponse;
import vn.tdsoftware.hrm_backend.entity.AssetGroup;

public class AssetGroupMapper {

    public static AssetGroupResponse mapToAssetGroupResponse(AssetGroup assetGroup) {
        return AssetGroupResponse.builder()
                .id(assetGroup.getId())
                .name(assetGroup.getName())
                .build();
    }
}
