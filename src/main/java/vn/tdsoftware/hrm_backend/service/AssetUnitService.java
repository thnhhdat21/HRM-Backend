package vn.tdsoftware.hrm_backend.service;

import vn.tdsoftware.hrm_backend.dto.assetunit.request.AssetUnitRequest;
import vn.tdsoftware.hrm_backend.dto.assetunit.response.AssetUnitResponse;

import java.util.List;

public interface AssetUnitService {
    List<AssetUnitResponse> getList();
    List<AssetUnitResponse> createAssetUnit(List<AssetUnitRequest> request);
    AssetUnitResponse updateAssetUnit(AssetUnitRequest request);
    void deleteAssetUnit(long id);
}
