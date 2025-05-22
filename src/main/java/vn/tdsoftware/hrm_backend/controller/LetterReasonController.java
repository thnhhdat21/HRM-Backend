package vn.tdsoftware.hrm_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vn.tdsoftware.hrm_backend.common.dto.response.ResponseData;
import vn.tdsoftware.hrm_backend.dto.letterreason.request.LetterReasonRequest;
import vn.tdsoftware.hrm_backend.dto.letterreason.response.LetterReasonDetail;
import vn.tdsoftware.hrm_backend.dto.letterreason.response.LetterReasonResponse;
import vn.tdsoftware.hrm_backend.service.LetterReasonService;

import java.util.List;

@RestController
@RequestMapping("/letter-reason")
@RequiredArgsConstructor
public class LetterReasonController {
    private final LetterReasonService letterReasonService;

    @PostMapping("/get-letter-reason")
    public ResponseData<List<LetterReasonResponse>> getLetterReason(@RequestParam("type") int type) {
        List<LetterReasonResponse> responses = letterReasonService.getLetterReason(type);
        return ResponseData.<List<LetterReasonResponse>>builder()
                .code(1000)
                .data(responses)
                .message("get list letter-reason")
                .build();
    }

    @PostMapping("/get-letter-reason-detail")
    public ResponseData<LetterReasonDetail> getLetterReasonDetail(@RequestParam("id") Long id) {
        LetterReasonDetail response = letterReasonService.getLetterReasonDetail(id);
        return ResponseData.<LetterReasonDetail>builder()
                .code(1000)
                .data(response)
                .message("Update letter-reason")
                .build();
    }
}
