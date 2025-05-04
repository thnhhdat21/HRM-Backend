package vn.tdsoftware.hrm_backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dto.letterreason.request.LetterReasonRequest;
import vn.tdsoftware.hrm_backend.dto.letterreason.response.LetterReasonDetail;
import vn.tdsoftware.hrm_backend.dto.letterreason.response.LetterReasonResponse;
import vn.tdsoftware.hrm_backend.entity.LetterReason;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.LetterReasonMapper;
import vn.tdsoftware.hrm_backend.repository.LetterReasonRepository;
import vn.tdsoftware.hrm_backend.service.LetterReasonService;
import vn.tdsoftware.hrm_backend.util.constant.ReasonTypeConstant;
import vn.tdsoftware.hrm_backend.util.constant.StatusContract;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LetterReasonServiceImpl implements LetterReasonService {
    private final LetterReasonRepository letterReasonRepository;

    @Override
    public List<LetterReasonResponse> getLetterReason(int type) {
        List<LetterReason> letterReasonList = letterReasonRepository.findByLetterTypeIdAndIsEnabled(type, true).orElseThrow(
                () -> new BusinessException(ErrorCode.LIST_LETTER_REASON_IS_EMPTY)
        );
        List<LetterReasonResponse> letterReasonResponseList = new ArrayList<>();
        for (LetterReason letterReason : letterReasonList) {
            letterReasonResponseList.add(LetterReasonMapper.mapToLetterReasonResponse(letterReason));
        }
        return letterReasonResponseList;
    }

    @Override
    public List<LetterReasonResponse> createLetterReason(List<LetterReasonRequest> request) {
        if (request == null || request.isEmpty()) {
            throw new BusinessException(ErrorCode.LIST_LETTER_REASON_REQUEST_IS_EMPTY);
        }
        List<LetterReason> letterReasonList = new ArrayList<>();
        for (LetterReasonRequest letterReasonRequest : request) {
            validatorReason(letterReasonRequest);
            letterReasonList.add(LetterReason.builder()
                            .letterTypeId(letterReasonRequest.getType())
                            .reason(letterReasonRequest.getReason())
                            .symbol(letterReasonRequest.getSymbol())
                            .maximum(letterReasonRequest.getMaximum())
                            .unit(letterReasonRequest.getUnit())
                            .workDayEnabled(letterReasonRequest.getWorkDayEnabled())
                            .goLate(letterReasonRequest.getGoLate())
                            .backEarly(letterReasonRequest.getBackEarly())
                            .status(StatusContract.ACTIVE)
                            .description(letterReasonRequest.getReason())
                    .build());
        }
        List<LetterReason> letterReasonSaved = new ArrayList<>(letterReasonRepository.saveAll(letterReasonList));
        List<LetterReasonResponse> letterReasonResponse = new ArrayList<>();
        for (LetterReason letterReason : letterReasonSaved) {
            letterReasonResponse.add(LetterReasonMapper.mapToLetterReasonResponse(letterReason));
        }
        return letterReasonResponse;
    }


    @Override
    public LetterReasonResponse updateLetterReason(LetterReasonRequest request) {
        LetterReason letterReasonEntity = letterReasonRepository.findByIdAndIsEnabled(request.getId(), true).orElseThrow(
                () -> new BusinessException(ErrorCode.REASON_IS_EMPTY)
        );
        validatorReason(request);
        letterReasonEntity.setReason(request.getReason());
        letterReasonEntity.setSymbol(request.getSymbol());
        letterReasonEntity.setMaximum(request.getMaximum());
        letterReasonEntity.setUnit(request.getUnit());
        letterReasonEntity.setWorkDayEnabled(request.getWorkDayEnabled());
        letterReasonEntity.setGoLate(request.getGoLate());
        letterReasonEntity.setBackEarly(request.getBackEarly());
        letterReasonEntity.setDescription(request.getDes());
        return LetterReasonMapper.mapToLetterReasonResponse(letterReasonRepository.save(letterReasonEntity));
    }

    @Override
    public void deleteLetterReason(Long id) {
        LetterReason letterReasonEntity = letterReasonRepository.findByIdAndIsEnabled(id, true).orElseThrow(
                () -> new BusinessException(ErrorCode.REASON_IS_EMPTY)
        );
        letterReasonEntity.setEnabled(false);
        letterReasonRepository.save(letterReasonEntity);
    }

    @Override
    public LetterReasonDetail getLetterReasonDetail(Long id) {
        LetterReason letterReasonEntity = letterReasonRepository.findByIdAndIsEnabled(id, true).orElseThrow(
                () -> new BusinessException(ErrorCode.REASON_IS_EMPTY)
        );
        return LetterReasonMapper.mapToLetterReasonDetail(letterReasonEntity);
    }

    private  void validatorReason(LetterReasonRequest letterReasonRequest) {
        if (letterReasonRequest.getReason() == null || letterReasonRequest.getReason().isEmpty()) {
            throw new BusinessException(ErrorCode.REASON_INVALID);
        }
        if (letterReasonRequest.getType() == ReasonTypeConstant.LEAVE || letterReasonRequest.getType() == ReasonTypeConstant.WORK_TIME) {
            if (letterReasonRequest.getMaximum() <= 0) {
                throw new BusinessException(ErrorCode.MAXIMUM_INVALID);
            } else if (letterReasonRequest.getUnit() == null || letterReasonRequest.getUnit().isEmpty()) {
                throw new BusinessException(ErrorCode.UNIT_INVALID);
            }
        }

        if (letterReasonRequest.getType() == ReasonTypeConstant.LEAVE) {
            if (letterReasonRequest.getSymbol() == null || letterReasonRequest.getSymbol().isEmpty()) {
                throw new BusinessException(ErrorCode.SYMBOL_INVALID);
            } else if (letterReasonRequest.getWorkDayEnabled() == null) {
                throw new BusinessException(ErrorCode.WORK_DAY_INVALID);
            }
        }

        if (letterReasonRequest.getType() == ReasonTypeConstant.WORK_TIME) {
            if (letterReasonRequest.getGoLate() == null) {
                throw new BusinessException(ErrorCode.GO_LATE_INVALID);
            } else if (letterReasonRequest.getBackEarly() == null) {
                throw new BusinessException(ErrorCode.BACK_EARLY_INVALID);
            }
        }
    }
}
