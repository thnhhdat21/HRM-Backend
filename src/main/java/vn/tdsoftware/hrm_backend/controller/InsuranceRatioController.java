package vn.tdsoftware.hrm_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vn.tdsoftware.hrm_backend.common.dto.response.ResponseData;
import vn.tdsoftware.hrm_backend.dto.insuranceratio.request.InsuranceRatioRequest;
import vn.tdsoftware.hrm_backend.dto.insuranceratio.response.InsuranceRatioDetailResponse;
import vn.tdsoftware.hrm_backend.dto.insuranceratio.response.InsuranceRatioResponse;
import vn.tdsoftware.hrm_backend.service.InsuranceRatioService;

import java.util.List;

@RestController
@RequestMapping("/insurance-ratio")
@RequiredArgsConstructor
public class InsuranceRatioController {
    private final InsuranceRatioService insuranceRatioService;

    @PostMapping("/get-insurance-ratios")
    public ResponseData<List<InsuranceRatioResponse>> getInsuranceRatio() {
        List<InsuranceRatioResponse> responses = insuranceRatioService.getInsuranceRatio();
        return ResponseData.<List<InsuranceRatioResponse>>builder()
                .code(1000)
                .data(responses)
                .message("Get insurance ratio successfully")
                .build();
    }

    @PostMapping("/get-insurance-ratio-detail")
    public ResponseData<InsuranceRatioDetailResponse> getInsuranceRatioDetail(@RequestParam("id") int id) {
        InsuranceRatioDetailResponse responses = insuranceRatioService.getInsuranceRatioDetail(id);
        return ResponseData.<InsuranceRatioDetailResponse>builder()
                .code(1000)
                .data(responses)
                .message("Get insurance ratio successfully")
                .build();
    }

    @PostMapping("/create-insurance-ratio")
    public ResponseData<List<InsuranceRatioResponse>> createInsuranceRatio(@RequestBody List<InsuranceRatioRequest> request) {
        List<InsuranceRatioResponse> response = insuranceRatioService.createInsuranceRatio(request);
        return ResponseData.<List<InsuranceRatioResponse>>builder()
                .code(1000)
                .data(response)
                .message("Get insurance ratio successfully")
                .build();
    }

    @PostMapping("/update-insurance-ratio")
    public ResponseData<Void> updateInsuranceRatio(@RequestBody List<InsuranceRatioRequest> request) {
        insuranceRatioService.updateInsuranceRatio(request);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("Get insurance ratio successfully")
                .build();
    }

    @PostMapping("/delete-insurance-ratio")
    public ResponseData<Void> deleteInsuranceRatio(@RequestBody List<Integer> request) {
        insuranceRatioService.deleteInsuranceRatio(request);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("Delete insurance ratio successfully")
                .build();
    }

}
