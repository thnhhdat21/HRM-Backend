package vn.tdsoftware.hrm_backend.mapper;

import vn.tdsoftware.hrm_backend.dto.insurancesetting.response.InsuranceSettingResponse;
import vn.tdsoftware.hrm_backend.entity.InsuranceSetting;

public class InsuranceSettingMapper {
    public static InsuranceSettingResponse maptoInsuranceSettingResponse(InsuranceSetting insuranceSetting) {
        return InsuranceSettingResponse.builder()
                .id(insuranceSetting.getId())
                .singedContract(insuranceSetting.getSingedContract())
                .returnedLeaveTmp(insuranceSetting.getReturnedLeaveTmp())
                .leaveTmp(insuranceSetting.getLeaveTmp())
                .unpaidLeave(insuranceSetting.getUnpaidLeave())
                .build();
    }
}
