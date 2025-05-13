package vn.tdsoftware.hrm_backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.insurancesetting.request.InsuranceSettingRequest;
import vn.tdsoftware.hrm_backend.dto.insurancesetting.response.InsuranceSettingResponse;
import vn.tdsoftware.hrm_backend.entity.InsuranceSetting;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.InsuranceSettingMapper;
import vn.tdsoftware.hrm_backend.repository.InsuranceSettingRepository;
import vn.tdsoftware.hrm_backend.service.InsuranceSettingService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InsuranceSettingServiceImpl implements InsuranceSettingService {
    private final InsuranceSettingRepository insuranceSettingRepository;
    @Override
    public InsuranceSettingResponse getInsuranceSetting() {
        InsuranceSetting insuranceSettingEntity = insuranceSettingRepository.findInsuranceSettingByIsEnabled(true).orElseThrow(
                () -> new BusinessException(ErrorCode.INSURANCE_SETTING_IS_EMPTY)
        );
        return InsuranceSettingMapper.maptoInsuranceSettingResponse(insuranceSettingEntity);
    }

    @Override
    public InsuranceSettingResponse updateInsuranceSetting(InsuranceSettingRequest settingRequest) {
        InsuranceSetting insuranceSettingEntity = insuranceSettingRepository.findInsuranceSettingByIdAndIsEnabled(settingRequest.getId(),true).orElseThrow(
                () -> new BusinessException(ErrorCode.INSURANCE_SETTING_ERROR)
        );
        if (settingRequest.getHasUpdate() != null && settingRequest.getHasUpdate()) {
            insuranceSettingEntity.setSingedContract(settingRequest.getSingedContract());
            insuranceSettingEntity.setReturnedLeaveTmp(settingRequest.getReturnedLeaveTmp());
            insuranceSettingEntity.setLeaveTmp(settingRequest.getLeaveTmp());
            insuranceSettingEntity.setUnpaidLeave(settingRequest.getUnpaidLeave());
            insuranceSettingRepository.save(insuranceSettingEntity);
        }
        return InsuranceSettingMapper.maptoInsuranceSettingResponse(insuranceSettingEntity);
    }
}
