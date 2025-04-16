package vn.tdsoftware.hrm_backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.leavesetting.request.LeaveSettingRequest;
import vn.tdsoftware.hrm_backend.dto.leavesetting.response.LeaveSettingResponse;
import vn.tdsoftware.hrm_backend.entity.LeaveSetting;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.LeaveSettingMapper;
import vn.tdsoftware.hrm_backend.repository.LeaveSettingRepository;
import vn.tdsoftware.hrm_backend.service.LeaveSettingService;

@Service
@RequiredArgsConstructor
public class LeaveSettingServiceImpl implements LeaveSettingService {
    private final LeaveSettingRepository leaveSettingRepository;

    @Override
    public LeaveSettingResponse getSetting() {
        LeaveSetting leaveSettingEntity = leaveSettingRepository.findLeaveSettingByIsEnabled(true).orElseThrow(
                () -> new BusinessException(ErrorCode.LEAVE_SETTING_IS_EMPTY)
        );
        return LeaveSettingMapper.mapToLeaveSettingResponse(leaveSettingEntity);
    }

    @Override
    public LeaveSettingResponse updateSetting(LeaveSettingRequest leaveSettingRequest) {
        LeaveSetting leaveSettingEntity = leaveSettingRepository.findLeaveSettingByIdAndIsEnabled(leaveSettingRequest.getId(), true).orElseThrow(
                () -> new BusinessException(ErrorCode.LEAVE_SETTING_IS_EMPTY)
        );
        leaveSettingEntity.setAnnualLeaveDays(leaveSettingRequest.getAnnualLeaveDays());
        leaveSettingEntity.setLeaveCycleStart(leaveSettingRequest.getLeaveCycleStart());
        leaveSettingEntity.setLeaveCycleEnd(leaveSettingRequest.getLeaveCycleEnd());
        leaveSettingEntity.setLeaveCycleUnit(leaveSettingRequest.getLeaveCycleUnit());
        leaveSettingEntity.setAccrualMethod(leaveSettingRequest.getAccrualMethod());
        leaveSettingEntity.setReceiveNewLeaveDate(leaveSettingRequest.getReceiveNewLeaveDate());
        leaveSettingEntity.setSeniorLeaveEnabled(leaveSettingRequest.isSeniorLeaveEnabled());
        leaveSettingEntity.setSeniorYears(leaveSettingRequest.getSeniorYears());

        return LeaveSettingMapper.mapToLeaveSettingResponse(leaveSettingRepository.save(leaveSettingEntity));
    }
}
