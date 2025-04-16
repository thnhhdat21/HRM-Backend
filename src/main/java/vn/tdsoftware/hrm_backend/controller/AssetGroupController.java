package vn.tdsoftware.hrm_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vn.tdsoftware.hrm_backend.common.dto.response.ResponseData;
import vn.tdsoftware.hrm_backend.dto.assetgroup.request.AssetGroupRequest;
import vn.tdsoftware.hrm_backend.dto.assetgroup.response.AssetGroupResponse;
import vn.tdsoftware.hrm_backend.service.AssetGroupService;

import java.util.List;

@RestController
@RequestMapping("/asset-group")
@RequiredArgsConstructor
public class AssetGroupController {
    private final AssetGroupService assetGroupService;

    @PostMapping("/get-list")
    public ResponseData<List<AssetGroupResponse>> getList() {
        List<AssetGroupResponse> response = assetGroupService.getList();
        return ResponseData.<List<AssetGroupResponse>>builder()
                .code(1000)
                .data(response)
                .message("Get list successfully")
                .build();
    }

    @PostMapping("/create-asset-group")
    public ResponseData<AssetGroupResponse> createAssetGroup(@RequestBody  AssetGroupRequest assetGroupRequest) {
        AssetGroupResponse response = assetGroupService.createAssetGroup(assetGroupRequest);
        return ResponseData.<AssetGroupResponse>builder()
                .code(1000)
                .data(response)
                .message("Create asset group successfully")
                .build();
    }

    @PostMapping("/update-asset-group")
    public ResponseData<AssetGroupResponse> updateAssetGroup(@RequestBody  AssetGroupRequest assetGroupRequest) {
        AssetGroupResponse response = assetGroupService.updateAssetGroup(assetGroupRequest);
        return ResponseData.<AssetGroupResponse>builder()
                .code(1000)
                .data(response)
                .message("Update asset group successfully")
                .build();
    }

    @PostMapping("/delete-asset-group")
    public ResponseData<Void> deleteAssetGroup(@RequestParam("id") long id) {
        assetGroupService.deleteAssetGroup(id);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("Delete asset group successfully")
                .build();
    }
}
