package vn.tdsoftware.hrm_backend.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dao.AutoCheckoutDAO;
import vn.tdsoftware.hrm_backend.dao.WorkShiftDAO;
import vn.tdsoftware.hrm_backend.dto.workshift.request.AutoCheckoutRequest;
import vn.tdsoftware.hrm_backend.dto.workshift.request.WorkShiftRequest;
import vn.tdsoftware.hrm_backend.dto.workshift.response.WorkShiftDetail;
import vn.tdsoftware.hrm_backend.dto.workshift.response.WorkShiftResponse;
import vn.tdsoftware.hrm_backend.entity.AutoCheckout;
import vn.tdsoftware.hrm_backend.entity.WorkShift;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.WorkShiftMapper;
import vn.tdsoftware.hrm_backend.repository.AutoCheckoutRepository;
import vn.tdsoftware.hrm_backend.repository.WorkShiftRepository;
import vn.tdsoftware.hrm_backend.service.WorkShiftService;
import vn.tdsoftware.hrm_backend.util.FieldStringUtil;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkShiftServiceImpl implements WorkShiftService {
    private final WorkShiftRepository workShiftRepository;
    private final AutoCheckoutRepository autoCheckoutRepository;
    private final WorkShiftDAO workShiftDAO;
    private final AutoCheckoutDAO autoCheckoutDAO;

    @Override
    public List<WorkShiftResponse> getList() {
        List<WorkShift> leaveSettingEntity = workShiftRepository.findAllByIsEnabled( true).orElseThrow(
                () -> new BusinessException(ErrorCode.LIST_WORK_SHIFT_IS_EMPTY)
        );
        List<WorkShiftResponse> workShiftResponseList = new ArrayList<>();
        for (WorkShift workShift : leaveSettingEntity) {
            workShiftResponseList.add(WorkShiftMapper.mapToWorkShiftResponse(workShift));
        }
        return workShiftResponseList;
    }

    @Override
    public WorkShiftResponse createWorkShift(WorkShiftRequest workShiftRequest) {
        checkTimeValid(workShiftRequest);
        WorkShift workShiftSaved = workShiftRepository.save(WorkShift.builder()
                        .code(workShiftRequest.getCode())
                        .name(workShiftRequest.getName())
                        .timeIn(workShiftRequest.getTimeIn())
                        .timeOut(workShiftRequest.getTimeOut())
                        .nextDayEnabled(workShiftRequest.isNextDayEnabled())
                        .breakStartTime(workShiftRequest.getBreakStartTime())
                        .breakEndTime(workShiftRequest.getBreakEndTime())
                        .totalTime(workShiftRequest.getTotalTime())
                        .totalWorkDay(workShiftRequest.getTotalWorkDay())
                        .checkinFirst(workShiftRequest.getCheckinFirst())
                        .checkoutLater(workShiftRequest.getCheckoutLater())
                        .autoTimeKeeping(workShiftRequest.isAutoTimeKeeping())
                        .autoCheckoutForPosition(workShiftRequest.isAutoCheckoutForPosition())
                .build());

        if (workShiftRequest.isAutoCheckoutForPosition()) {
            for (Long jobPositionId : workShiftRequest.getJobPositions()) {
                autoCheckoutRepository.save(AutoCheckout.builder()
                                .workShiftId(workShiftSaved.getId())
                                .JobPositionId(jobPositionId)
                        .build());
            }
        }
        return WorkShiftMapper.mapToWorkShiftResponse(workShiftSaved);
    }

    @Override
    @Transactional
    public WorkShiftResponse updateWorkShift(WorkShiftRequest workShiftRequest) {
        WorkShift workShiftEntity = workShiftRepository.findByIdAndIsEnabled(workShiftRequest.getId(), true).orElseThrow(
                () -> new BusinessException(ErrorCode.WORK_SHIFT_IS_EMPTY)
        );
        checkTimeValid(workShiftRequest);
        workShiftEntity.setCode(workShiftRequest.getCode());
        workShiftEntity.setName(workShiftRequest.getName());
        workShiftEntity.setTimeIn(workShiftRequest.getTimeIn());
        workShiftEntity.setTimeOut(workShiftRequest.getTimeOut());
        workShiftEntity.setNextDayEnabled(workShiftRequest.isNextDayEnabled());
        workShiftEntity.setBreakStartTime(workShiftRequest.getBreakStartTime());
        workShiftEntity.setBreakEndTime(workShiftRequest.getBreakEndTime());
        workShiftEntity.setTotalTime(workShiftRequest.getTotalTime());
        workShiftEntity.setTotalWorkDay(workShiftRequest.getTotalWorkDay());
        workShiftEntity.setCheckinFirst(workShiftRequest.getCheckinFirst());
        workShiftEntity.setCheckoutLater(workShiftRequest.getCheckoutLater());
        workShiftEntity.setAutoTimeKeeping(workShiftRequest.isAutoTimeKeeping());
        workShiftEntity.setAutoCheckoutForPosition(workShiftRequest.isAutoCheckoutForPosition());
        WorkShift workShiftSaved = workShiftRepository.save(workShiftEntity);
        return WorkShiftMapper.mapToWorkShiftResponse(workShiftSaved);
    }

    @Override
    public void updateAutoCheckout(AutoCheckoutRequest autoCheckoutRequests) {
        if (autoCheckoutRequests.getJobPositionId().isEmpty()) {
            throw new BusinessException(ErrorCode.JOB_POSITION_NOT_EXIST);
        }
        workShiftRepository.findByIdAndIsEnabled(autoCheckoutRequests.getId(), true).orElseThrow(
                () -> new BusinessException(ErrorCode.WORK_SHIFT_IS_EMPTY)
        );
        if (autoCheckoutRepository.existsByWorkShiftIdAndIsEnabled(autoCheckoutRequests.getId(), true)) {
            autoCheckoutDAO.deleteAutoCheckout(autoCheckoutRequests.getId());
        }
        for (Long item : autoCheckoutRequests.getJobPositionId()) {
            autoCheckoutRepository.save(AutoCheckout.builder()
                    .workShiftId(autoCheckoutRequests.getId())
                    .JobPositionId(item)
                    .build());
        }
    }

    @Override
    public void deleteWorkShift(long id) {
        workShiftRepository.findByIdAndIsEnabled(id, true).orElseThrow(
            () -> new BusinessException(ErrorCode.WORK_SHIFT_IS_EMPTY)
        );
        workShiftDAO.deleteWorkShift(id);
        if (autoCheckoutRepository.existsByWorkShiftIdAndIsEnabled(id, true)) {
            autoCheckoutDAO.deleteAutoCheckout(id);
        }
    }

    @Override
    public WorkShiftDetail getDetailWorkShift(long id) {
        WorkShiftDetail response = workShiftDAO.getDetailWorkShift(id);
        if (response == null) {
            throw new BusinessException(ErrorCode.WORK_SHIFT_IS_EMPTY);
        }
        return response;
    }

    private void checkTimeValid(WorkShiftRequest workShiftRequest) {
        FieldStringUtil.validatorNameAndCode(workShiftRequest.getName(), workShiftRequest.getCode());
        if (!workShiftRequest.isNextDayEnabled() && (workShiftRequest.getTimeIn().isAfter(workShiftRequest.getTimeOut()))) {
            throw new BusinessException(ErrorCode.TIME_IN_OR_TIME_OUT_INVALID);
        }
        if (workShiftRequest.getBreakStartTime() != null && workShiftRequest.getBreakEndTime() != null) {
            if (workShiftRequest.getBreakStartTime().isAfter(workShiftRequest.getBreakEndTime()) ||
                    workShiftRequest.getBreakStartTime().equals(workShiftRequest.getBreakEndTime()) ||
                    workShiftRequest.getBreakStartTime().equals(workShiftRequest.getTimeIn()) ||
                    (!workShiftRequest.isNextDayEnabled() && workShiftRequest.getBreakEndTime().isAfter(workShiftRequest.getTimeOut()))) {
                throw new BusinessException(ErrorCode.TIME_BREAK_INVALID);
            }
        }
    }
}
