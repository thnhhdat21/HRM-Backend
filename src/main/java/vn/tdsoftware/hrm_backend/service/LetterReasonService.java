package vn.tdsoftware.hrm_backend.service;


import vn.tdsoftware.hrm_backend.dto.letterreason.request.LetterReasonRequest;
import vn.tdsoftware.hrm_backend.dto.letterreason.response.LetterReasonDetail;
import vn.tdsoftware.hrm_backend.dto.letterreason.response.LetterReasonResponse;

import java.util.List;

public interface LetterReasonService {
    List<LetterReasonResponse> getLetterReason(int type);
    List<LetterReasonResponse> createLetterReason(List<LetterReasonRequest> request);
    LetterReasonResponse updateLetterReason(LetterReasonRequest request);
    void deleteLetterReason(Long id);
    LetterReasonDetail getLetterReasonDetail(Long id);
}
