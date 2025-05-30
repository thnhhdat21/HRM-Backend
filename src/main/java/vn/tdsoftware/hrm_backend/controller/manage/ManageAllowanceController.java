package vn.tdsoftware.hrm_backend.controller.manage;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vn.tdsoftware.hrm_backend.common.dto.response.ResponseData;
import vn.tdsoftware.hrm_backend.dto.allowance.request.AllowanceRequest;
import vn.tdsoftware.hrm_backend.dto.allowance.response.AllowanceResponse;
import vn.tdsoftware.hrm_backend.service.AllowanceService;

import java.util.List;

@RestController
@RequestMapping("/admin/allowance")
@RequiredArgsConstructor
public class ManageAllowanceController {
    private final AllowanceService allowanceService;


    @PostMapping("/create-allowance")
    public ResponseData<List<AllowanceResponse>> createAllowance(@RequestBody List<AllowanceRequest> request) {
        List<AllowanceResponse> response = allowanceService.createAllowance(request);
        return ResponseData.<List<AllowanceResponse>>builder()
                .code(1000)
                .data(response)
                .message("Create successfully")
                .build();
    }

    @PostMapping("/update-allowance")
    public ResponseData<AllowanceResponse> update(@RequestBody AllowanceRequest request) {
        AllowanceResponse response = allowanceService.updateAllowance(request);
        return ResponseData.<AllowanceResponse>builder()
                .code(1000)
                .data(response)
                .message("Update successfully")
                .build();
    }

    @PostMapping("/delete-allowance")
    public ResponseData<Void> delete(@RequestParam long id) {
        allowanceService.deleteAllowance(id);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("Delete successfully")
                .build();
    }
}
