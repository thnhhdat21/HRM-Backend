package vn.tdsoftware.hrm_backend.service;


import vn.tdsoftware.hrm_backend.dto.insurancesetting.request.InsuranceSettingRequest;
import vn.tdsoftware.hrm_backend.dto.insurancesetting.response.InsuranceSettingResponse;

public interface InsuranceSettingService {
    InsuranceSettingResponse getInsuranceSetting();
    InsuranceSettingResponse updateInsuranceSetting(InsuranceSettingRequest settingRequest);
}
