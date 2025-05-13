package vn.tdsoftware.hrm_backend.mapper;

import vn.tdsoftware.hrm_backend.dto.letterreason.response.LetterReasonDetail;
import vn.tdsoftware.hrm_backend.dto.letterreason.response.LetterReasonResponse;
import vn.tdsoftware.hrm_backend.entity.LetterReason;

public class LetterReasonMapper {

    public static LetterReasonResponse mapToLetterReasonResponse(LetterReason letterReason) {
        return LetterReasonResponse.builder()
                .id(letterReason.getId())
                .reason(letterReason.getReason())
                .maximum(letterReason.getMaximum())
                .workDayEnabled(letterReason.getWorkDayEnabled())
                .createdAt(letterReason.getCreatedAt())
                .des(letterReason.getDescription())
                .status(letterReason.getStatus())
                .build();
    }

    public static LetterReasonDetail  mapToLetterReasonDetail(LetterReason letterReason) {
        return LetterReasonDetail.builder()
                .id(letterReason.getId())
                .reason(letterReason.getReason())
                .symbol(letterReason.getReason())
                .maximum(letterReason.getMaximum())
                .unit(letterReason.getUnit())
                .workDayEnabled(letterReason.getWorkDayEnabled())
                .goLate(letterReason.getGoLate())
                .backEarly(letterReason.getBackEarly())
                .des(letterReason.getDescription())
                .build();
    }
}
