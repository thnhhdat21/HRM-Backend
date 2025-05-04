package vn.tdsoftware.hrm_backend.mapper;

import vn.tdsoftware.hrm_backend.dto.letterreason.response.LetterReasonDetail;
import vn.tdsoftware.hrm_backend.dto.letterreason.response.LetterReasonResponse;
import vn.tdsoftware.hrm_backend.entity.LetterReason;

public class LetterReasonMapper {

    public static LetterReasonResponse mapToLetterReasonResponse(LetterReason reasonLetter) {
        return LetterReasonResponse.builder()
                .id(reasonLetter.getId())
                .reason(reasonLetter.getReason())
                .maximum(reasonLetter.getMaximum())
                .workDayEnabled(reasonLetter.getWorkDayEnabled())
                .createdAt(reasonLetter.getCreatedAt())
                .des(reasonLetter.getDescription())
                .status(reasonLetter.getStatus())
                .build();
    }

    public static LetterReasonDetail mapToLetterReasonDetail(LetterReason reasonLetter) {
        return LetterReasonDetail.builder()
                .id(reasonLetter.getId())
                .reason(reasonLetter.getReason())
                .symbol(reasonLetter.getReason())
                .maximum(reasonLetter.getMaximum())
                .unit(reasonLetter.getUnit())
                .workDayEnabled(reasonLetter.getWorkDayEnabled())
                .goLate(reasonLetter.getGoLate())
                .backEarly(reasonLetter.getBackEarly())
                .des(reasonLetter.getDescription())
                .build();
    }
}
