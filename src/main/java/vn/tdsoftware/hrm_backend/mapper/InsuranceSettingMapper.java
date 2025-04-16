package vn.tdsoftware.hrm_backend.mapper;

import vn.tdsoftware.hrm_backend.dto.insurancesetting.response.InsuranceSettingResponse;
import vn.tdsoftware.hrm_backend.entity.InsuranceSetting;

public class InsuranceSettingMapper {
    public static InsuranceSettingResponse maptoInsuranceSettingResponse(InsuranceSetting insuranceSetting) {
        return InsuranceSettingResponse.builder()
                .id(insuranceSetting.getId())
                .closingDateIncrease(insuranceSetting.getClosingDateIncrease())
                .singedContract(insuranceSetting.getSingedContract())
                .returnedFromMaternity(insuranceSetting.getReturnedFromMaternity())
                .returnedFromUnpaidLeave(insuranceSetting.getReturnedFromUnpaidLeave())
                .increasedContribution(insuranceSetting.getIncreasedContribution())
                .closingDateDecrease(insuranceSetting.getClosingDateDecrease())
                .contractTerminated(insuranceSetting.getContractTerminated())
                .maternityLeave(insuranceSetting.getMaternityLeave())
                .decreasedContribution(insuranceSetting.getDecreasedContribution())
                .unpaidLeave(insuranceSetting.getUnpaidLeave())
                .maxUnpaidLeaveDay(insuranceSetting.getMaxUnpaidLeaveDay())
                .build();
    }
}
