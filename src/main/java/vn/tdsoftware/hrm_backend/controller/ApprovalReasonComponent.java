package vn.tdsoftware.hrm_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vn.tdsoftware.hrm_backend.common.dto.response.ResponseData;
import vn.tdsoftware.hrm_backend.dto.approvalreason.request.ApprovalReasonRequest;
import vn.tdsoftware.hrm_backend.dto.approvalreason.response.ApprovalReasonDetail;
import vn.tdsoftware.hrm_backend.dto.approvalreason.response.ApprovalReasonResponse;
import vn.tdsoftware.hrm_backend.service.ApprovalReasonService;

import java.util.List;

@RestController
@RequestMapping("/approval-reason")
@RequiredArgsConstructor
public class ApprovalReasonComponent {
    private final ApprovalReasonService approvalReasonService;

    @PostMapping("/get-approval-reason")
    public ResponseData<List<ApprovalReasonResponse>> getApprovalReason(@RequestParam("type") int type) {
        List<ApprovalReasonResponse> responses = approvalReasonService.getApprovalReason(type);
        return ResponseData.<List<ApprovalReasonResponse>>builder()
                .code(1000)
                .data(responses)
                .message("get list approval-reason")
                .build();
    }

    @PostMapping("/create-approval-reason")
    public ResponseData<List<ApprovalReasonResponse>> createApprovalReason(@RequestBody List<ApprovalReasonRequest> request) {
        List<ApprovalReasonResponse> response = approvalReasonService.createApprovalReason(request);
        return ResponseData.<List<ApprovalReasonResponse>>builder()
                .code(1000)
                .data(response)
                .message("Create approval-reason")
                .build();
    }

    @PostMapping("/update-approval-reason")
    public ResponseData<ApprovalReasonResponse> updateApprovalReason(@RequestBody ApprovalReasonRequest request) {
        ApprovalReasonResponse response = approvalReasonService.updateApprovalReason(request);
        return ResponseData.<ApprovalReasonResponse>builder()
                .code(1000)
                .data(response)
                .message("Update approval-reason")
                .build();
    }

    @PostMapping("/delete-approval-reason")
    public ResponseData<Void> deleteApprovalReason(@RequestParam("id") Long id) {
        approvalReasonService.deleteApprovalReason(id);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("Update approval-reason")
                .build();
    }

    @PostMapping("/get-approval-reason-detail")
    public ResponseData<ApprovalReasonDetail> getApprovalReasonDetail(@RequestParam("id") Long id) {
        ApprovalReasonDetail response = approvalReasonService.getApprovalReasonDetail(id);
        return ResponseData.<ApprovalReasonDetail>builder()
                .code(1000)
                .data(response)
                .message("Update approval-reason")
                .build();
    }
}
