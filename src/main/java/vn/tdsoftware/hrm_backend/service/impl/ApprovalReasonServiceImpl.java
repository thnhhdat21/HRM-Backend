package vn.tdsoftware.hrm_backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.approvalreason.request.ApprovalReasonRequest;
import vn.tdsoftware.hrm_backend.dto.approvalreason.response.ApprovalReasonDetail;
import vn.tdsoftware.hrm_backend.dto.approvalreason.response.ApprovalReasonResponse;
import vn.tdsoftware.hrm_backend.entity.ApprovalReason;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.ApprovalReasonMapper;
import vn.tdsoftware.hrm_backend.repository.ApprovalReasonRepository;
import vn.tdsoftware.hrm_backend.service.ApprovalReasonService;
import vn.tdsoftware.hrm_backend.util.constant.ReasonTypeConstant;
import vn.tdsoftware.hrm_backend.util.constant.StatusContract;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApprovalReasonServiceImpl implements ApprovalReasonService {
    private final ApprovalReasonRepository approvalReasonRepository;

    @Override
    public List<ApprovalReasonResponse> getApprovalReason(int type) {
        List<ApprovalReason> approvalReasonList = approvalReasonRepository.findApprovalReasonByApprovalTypeIdAndIsEnabled(type, true).orElseThrow(
                () -> new BusinessException(ErrorCode.LIST_APPROVAL_REASON_IS_EMPTY)
        );
        List<ApprovalReasonResponse> approvalReasonResponseList = new ArrayList<>();
        for (ApprovalReason approvalReason : approvalReasonList) {
            approvalReasonResponseList.add(ApprovalReasonMapper.mapToApprovalReasonResponse(approvalReason));
        }
        return approvalReasonResponseList;
    }

    @Override
    public List<ApprovalReasonResponse> createApprovalReason(List<ApprovalReasonRequest> request) {
        if (request == null || request.isEmpty()) {
            throw new BusinessException(ErrorCode.LIST_APPROVAL_REASON_REQUEST_IS_EMPTY);
        }
        List<ApprovalReason> approvalReasonList = new ArrayList<>();
        for (ApprovalReasonRequest approvalReasonRequest : request) {
            validatorReason(approvalReasonRequest);
            approvalReasonList.add(ApprovalReason.builder()
                            .approvalTypeId(approvalReasonRequest.getType())
                            .reason(approvalReasonRequest.getReason())
                            .symbol(approvalReasonRequest.getSymbol())
                            .maximum(approvalReasonRequest.getMaximum())
                            .unit(approvalReasonRequest.getUnit())
                            .workDayEnabled(approvalReasonRequest.getWorkDayEnabled())
                            .goLate(approvalReasonRequest.getGoLate())
                            .backEarly(approvalReasonRequest.getBackEarly())
                            .status(StatusContract.ACTIVE)
                            .description(approvalReasonRequest.getReason())
                    .build());
        }
        List<ApprovalReason> approvalReasonSaved = new ArrayList<>(approvalReasonRepository.saveAll(approvalReasonList));
        List<ApprovalReasonResponse> approvalReasonResponse = new ArrayList<>();
        for (ApprovalReason approvalReason : approvalReasonSaved) {
            approvalReasonResponse.add(ApprovalReasonMapper.mapToApprovalReasonResponse(approvalReason));
        }
        return approvalReasonResponse;
    }


    @Override
    public ApprovalReasonResponse updateApprovalReason(ApprovalReasonRequest request) {
        ApprovalReason approvalReasonEntity = approvalReasonRepository.findApprovalReasonByIdAndIsEnabled(request.getId(), true).orElseThrow(
                () -> new BusinessException(ErrorCode.REASON_IS_EMPTY)
        );
        validatorReason(request);
        approvalReasonEntity.setReason(request.getReason());
        approvalReasonEntity.setSymbol(request.getSymbol());
        approvalReasonEntity.setMaximum(request.getMaximum());
        approvalReasonEntity.setUnit(request.getUnit());
        approvalReasonEntity.setWorkDayEnabled(request.getWorkDayEnabled());
        approvalReasonEntity.setGoLate(request.getGoLate());
        approvalReasonEntity.setBackEarly(request.getBackEarly());
        approvalReasonEntity.setDescription(request.getDes());
        return ApprovalReasonMapper.mapToApprovalReasonResponse(approvalReasonRepository.save(approvalReasonEntity));
    }

    @Override
    public void deleteApprovalReason(Long id) {
        ApprovalReason approvalReasonEntity = approvalReasonRepository.findApprovalReasonByIdAndIsEnabled(id, true).orElseThrow(
                () -> new BusinessException(ErrorCode.REASON_IS_EMPTY)
        );
        approvalReasonEntity.setEnabled(false);
        approvalReasonRepository.save(approvalReasonEntity);
    }

    @Override
    public ApprovalReasonDetail getApprovalReasonDetail(Long id) {
        ApprovalReason approvalReasonEntity = approvalReasonRepository.findApprovalReasonByIdAndIsEnabled(id, true).orElseThrow(
                () -> new BusinessException(ErrorCode.REASON_IS_EMPTY)
        );
        return ApprovalReasonMapper.mapToApprovalReasonDetail(approvalReasonEntity);
    }

    private  void validatorReason(ApprovalReasonRequest approvalReasonRequest) {
        if (approvalReasonRequest.getReason() == null || approvalReasonRequest.getReason().isEmpty()) {
            throw new BusinessException(ErrorCode.REASON_INVALID);
        }
        if (approvalReasonRequest.getType() == ReasonTypeConstant.LEAVE || approvalReasonRequest.getType() == ReasonTypeConstant.WORK_TIME) {
            if (approvalReasonRequest.getMaximum() <= 0) {
                throw new BusinessException(ErrorCode.MAXIMUM_INVALID);
            } else if (approvalReasonRequest.getUnit() == null || approvalReasonRequest.getUnit().isEmpty()) {
                throw new BusinessException(ErrorCode.UNIT_INVALID);
            }
        }

        if (approvalReasonRequest.getType() == ReasonTypeConstant.LEAVE) {
            if (approvalReasonRequest.getSymbol() == null || approvalReasonRequest.getSymbol().isEmpty()) {
                throw new BusinessException(ErrorCode.SYMBOL_INVALID);
            } else if (approvalReasonRequest.getWorkDayEnabled() == null) {
                throw new BusinessException(ErrorCode.WORK_DAY_INVALID);
            }
        }

        if (approvalReasonRequest.getType() == ReasonTypeConstant.WORK_TIME) {
            if (approvalReasonRequest.getGoLate() == null) {
                throw new BusinessException(ErrorCode.GO_LATE_INVALID);
            } else if (approvalReasonRequest.getBackEarly() == null) {
                throw new BusinessException(ErrorCode.BACK_EARLY_INVALID);
            }
        }
    }
}
