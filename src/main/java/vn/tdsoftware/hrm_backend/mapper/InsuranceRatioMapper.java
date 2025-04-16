package vn.tdsoftware.hrm_backend.mapper;

import vn.tdsoftware.hrm_backend.dto.insuranceratio.response.InsuranceRatioResponse;
import vn.tdsoftware.hrm_backend.entity.InsuranceRatio;

public class InsuranceRatioMapper {

    public static InsuranceRatioResponse maptoInsuranceRatioResponse(InsuranceRatio insuranceRatio) {
        return InsuranceRatioResponse.builder()
                .id(insuranceRatio.getId())
                .dateStart(insuranceRatio.getDateStart())
                .build();
    }
}
