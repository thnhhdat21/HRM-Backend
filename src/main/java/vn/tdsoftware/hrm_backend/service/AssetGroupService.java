package vn.tdsoftware.hrm_backend.service;

import vn.tdsoftware.hrm_backend.dto.assetgroup.request.AssetGroupRequest;
import vn.tdsoftware.hrm_backend.dto.assetgroup.response.AssetGroupResponse;

import java.util.List;

public interface AssetGroupService {
    List<AssetGroupResponse> getList();
    AssetGroupResponse createAssetGroup(AssetGroupRequest assetGroupRequest);
    AssetGroupResponse updateAssetGroup(AssetGroupRequest assetGroupRequest);
    void deleteAssetGroup(long id);
}
