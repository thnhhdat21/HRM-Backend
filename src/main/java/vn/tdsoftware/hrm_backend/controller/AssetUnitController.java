package vn.tdsoftware.hrm_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vn.tdsoftware.hrm_backend.common.dto.response.ResponseData;
import vn.tdsoftware.hrm_backend.dto.assetunit.request.AssetUnitRequest;
import vn.tdsoftware.hrm_backend.dto.assetunit.response.AssetUnitResponse;
import vn.tdsoftware.hrm_backend.service.AssetUnitService;

import java.util.List;

@RestController
@RequestMapping("/asset-unit")
@RequiredArgsConstructor
public class AssetUnitController {
    private final AssetUnitService assetUnitService;

    @PostMapping("/get-list")
    public ResponseData<List<AssetUnitResponse>> getList() {
        List<AssetUnitResponse> responses = assetUnitService.getList();
        return ResponseData.<List<AssetUnitResponse>>builder()
                .code(1000)
                .data(responses)
                .message("Get list successfully")
                .build();
    }

    @PostMapping("/create-asset-unit")
    public ResponseData<List<AssetUnitResponse>> createAssetUnit(@RequestBody List<AssetUnitRequest> request) {
        List<AssetUnitResponse> response = assetUnitService.createAssetUnit(request);
        return ResponseData.<List<AssetUnitResponse>>builder()
                .code(1000)
                .data(response)
                .message("Create asset unit successfully")
                .build();
    }

    @PostMapping("/update-asset-unit")
    public ResponseData<AssetUnitResponse> updateAssetUnit(@RequestBody AssetUnitRequest request) {
        AssetUnitResponse response = assetUnitService.updateAssetUnit(request);
        return ResponseData.<AssetUnitResponse>builder()
                .code(1000)
                .data(response)
                .message("Update asset unit successfully")
                .build();
    }

    @PostMapping("/delete-asset-unit")
    public ResponseData<Void> updateAssetUnit(@RequestParam("id") long id) {
        assetUnitService.deleteAssetUnit(id);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("Delete asset unit successfully")
                .build();
    }
}
