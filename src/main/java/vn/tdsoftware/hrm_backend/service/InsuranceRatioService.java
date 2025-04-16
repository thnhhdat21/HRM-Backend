package vn.tdsoftware.hrm_backend.service;

import vn.tdsoftware.hrm_backend.dto.insuranceratio.request.InsuranceRatioRequest;
import vn.tdsoftware.hrm_backend.dto.insuranceratio.response.InsuranceRatioDetailResponse;
import vn.tdsoftware.hrm_backend.dto.insuranceratio.response.InsuranceRatioResponse;

import java.util.List;

public interface InsuranceRatioService {
    List<InsuranceRatioResponse> getInsuranceRatio();
    InsuranceRatioDetailResponse getInsuranceRatioDetail(int id);
    List<InsuranceRatioResponse> createInsuranceRatio(List<InsuranceRatioRequest> insuranceRatioRequest);
    void updateInsuranceRatio(List<InsuranceRatioRequest> insuranceRatioRequest);
    void deleteInsuranceRatio(List<Integer> requests);

}
