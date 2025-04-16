package vn.tdsoftware.hrm_backend.controller;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.tdsoftware.hrm_backend.common.dto.response.ResponseData;
import vn.tdsoftware.hrm_backend.dto.insurancesetting.request.InsuranceSettingRequest;
import vn.tdsoftware.hrm_backend.dto.insurancesetting.response.InsuranceSettingResponse;
import vn.tdsoftware.hrm_backend.service.InsuranceSettingService;

@RestController
@RequestMapping("/insurance-setting")
@RequiredArgsConstructor
public class InsuranceSettingController {
    private final InsuranceSettingService insuranceSettingService;
    private final Gson gson;

    @PostMapping("/get-setting")
    public ResponseData<InsuranceSettingResponse> getSetting() {
        InsuranceSettingResponse response = insuranceSettingService.getInsuranceSetting();
        return ResponseData.<InsuranceSettingResponse>builder()
                .code(1000)
                .data(response)
                .message("Get setting successfully")
                .build();
    }

    @PostMapping("/update-setting")
    public ResponseData<InsuranceSettingResponse> update(@RequestBody InsuranceSettingRequest request) {
        System.out.println(gson.toJson(request));
        InsuranceSettingResponse response = insuranceSettingService.updateInsuranceSetting(request);
        return ResponseData.<InsuranceSettingResponse>builder()
                .code(1000)
                .data(response)
                .message("Update setting successfully")
                .build();
    }
}
