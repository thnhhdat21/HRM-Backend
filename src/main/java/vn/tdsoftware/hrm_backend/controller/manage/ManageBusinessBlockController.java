package vn.tdsoftware.hrm_backend.controller.manage;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vn.tdsoftware.hrm_backend.common.dto.response.ResponseData;
import vn.tdsoftware.hrm_backend.dto.businessblock.request.BusinessBlockRequest;
import vn.tdsoftware.hrm_backend.dto.businessblock.response.BusinessBlockResponse;
import vn.tdsoftware.hrm_backend.service.BusinessBlockService;

import java.util.List;

@RestController
@RequestMapping("/admin/business")
@RequiredArgsConstructor
public class ManageBusinessBlockController {
    private final BusinessBlockService businessService;

    @PostMapping("/get-business-block")
    public ResponseData<List<BusinessBlockResponse>> getListBusinessBlock() {
        List<BusinessBlockResponse> response = businessService.getListBusinessBlock();
        return ResponseData.< List<BusinessBlockResponse>>builder()
                .code(1000)
                .message("Get list business block successfully")
                .data(response)
                .build();
    }

    @PostMapping("/create-business-block")
    public ResponseData<BusinessBlockResponse> createBusinessBlock(@RequestBody BusinessBlockRequest request) {
        BusinessBlockResponse response = businessService.createBusinessBlock(request);
        return ResponseData.<BusinessBlockResponse>builder()
                .code(1000)
                .message("create business block successfully")
                .data(response)
                .build();
    }

    @PostMapping("/update-business-block")
    public ResponseData<BusinessBlockResponse> updateBusinessBlock(@RequestBody BusinessBlockRequest request) {
        BusinessBlockResponse response = businessService.updateBusinessBlock(request);
        return ResponseData.<BusinessBlockResponse>builder()
                .code(1000)
                .message("create business block successfully")
                .data(response)
                .build();
    }

    @PostMapping("/delete-business-block")
    public ResponseData<Void> deleteBusinessBlock(@RequestParam int id) {
        businessService.deleteBusinessBlock(id);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("Delete role successfully")
                .build();
    }
}


