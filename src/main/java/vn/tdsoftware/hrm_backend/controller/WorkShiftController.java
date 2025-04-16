package vn.tdsoftware.hrm_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vn.tdsoftware.hrm_backend.common.dto.response.ResponseData;
import vn.tdsoftware.hrm_backend.dto.workshift.request.AutoCheckoutRequest;
import vn.tdsoftware.hrm_backend.dto.workshift.request.WorkShiftRequest;
import vn.tdsoftware.hrm_backend.dto.workshift.response.WorkShiftDetail;
import vn.tdsoftware.hrm_backend.dto.workshift.response.WorkShiftResponse;
import vn.tdsoftware.hrm_backend.service.WorkShiftService;

import java.util.List;

@RestController
@RequestMapping("/work-shift")
@RequiredArgsConstructor
public class WorkShiftController {
    private final WorkShiftService workShiftService;

    @PostMapping("/get-list")
    public ResponseData<List<WorkShiftResponse>> getList() {
        List<WorkShiftResponse> responses = workShiftService.getList();
        return ResponseData.<List<WorkShiftResponse>>builder()
                .code(1000)
                .data(responses)
                .message("Get list successfully")
                .build();
    }

    @PostMapping("/create-work-shift")
    public ResponseData<WorkShiftResponse> createWorkShift(@RequestBody WorkShiftRequest request) {
        WorkShiftResponse responses = workShiftService.createWorkShift(request);
        return ResponseData.<WorkShiftResponse>builder()
                .code(1000)
                .data(responses)
                .message("Get list successfully")
                .build();
    }

    @PostMapping("/update-work-shift")
    public ResponseData<WorkShiftResponse> updateWorkShift(@RequestBody WorkShiftRequest request) {
        WorkShiftResponse responses = workShiftService.updateWorkShift(request);
        return ResponseData.<WorkShiftResponse>builder()
                .code(1000)
                .data(responses)
                .message("Get list successfully")
                .build();
    }

    @PostMapping("/update-job-position")
    public ResponseData<Void> updateWorkShift(@RequestBody AutoCheckoutRequest request) {
        workShiftService.updateAutoCheckout(request);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("Get list successfully")
                .build();
    }

    @PostMapping("/delete-work-shift")
    public ResponseData<Void> deleteWorkShift(@RequestParam long id) {
        workShiftService.deleteWorkShift(id);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("Get list successfully")
                .build();
    }

    @PostMapping("/get-detail")
    public ResponseData<WorkShiftDetail> getDetailWorkShift(@RequestParam long id) {
        WorkShiftDetail response = workShiftService.getDetailWorkShift(id);
        return ResponseData.<WorkShiftDetail>builder()
                .code(1000)
                .data(response)
                .message("Get detail successfully")
                .build();
    }
}
