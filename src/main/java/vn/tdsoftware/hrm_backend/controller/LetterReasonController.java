package vn.tdsoftware.hrm_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vn.tdsoftware.hrm_backend.common.dto.response.ResponseData;
import vn.tdsoftware.hrm_backend.dto.letterreason.request.ReasonLetterRequest;
import vn.tdsoftware.hrm_backend.dto.letterreason.response.ReasonLetterDetail;
import vn.tdsoftware.hrm_backend.dto.letterreason.response.ReasonLetterResponse;
import vn.tdsoftware.hrm_backend.service.ReasonLetterService;

import java.util.List;

@RestController
@RequestMapping("/letter-reason")
@RequiredArgsConstructor
public class LetterReasonController {
    private final ReasonLetterService letterReasonService;

    @PostMapping("/get-letter-reason")
    public ResponseData<List<ReasonLetterResponse>> getLetterReason(@RequestParam("type") int type) {
        List<ReasonLetterResponse> responses = letterReasonService.getReasonLetter(type);
        return ResponseData.<List<ReasonLetterResponse>>builder()
                .code(1000)
                .data(responses)
                .message("get list letter-reason")
                .build();
    }

    @PostMapping("/create-letter-reason")
    public ResponseData<List<ReasonLetterResponse>> createLetterReason(@RequestBody List<ReasonLetterRequest> request) {
        List<ReasonLetterResponse> response = letterReasonService.createReasonLetter(request);
        return ResponseData.<List<ReasonLetterResponse>>builder()
                .code(1000)
                .data(response)
                .message("Create letter-reason")
                .build();
    }

    @PostMapping("/update-letter-reason")
    public ResponseData<ReasonLetterResponse> updateLetterReason(@RequestBody ReasonLetterRequest request) {
        ReasonLetterResponse response = letterReasonService.updateReasonLetter(request);
        return ResponseData.<ReasonLetterResponse>builder()
                .code(1000)
                .data(response)
                .message("Update letter-reason")
                .build();
    }

    @PostMapping("/delete-letter-reason")
    public ResponseData<Void> deleteLetterReason(@RequestParam("id") Long id) {
        letterReasonService.deleteReasonLetter(id);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("Update letter-reason")
                .build();
    }

    @PostMapping("/get-letter-reason-detail")
    public ResponseData<ReasonLetterDetail> getReasonLetterDetail(@RequestParam("id") Long id) {
        ReasonLetterDetail response = letterReasonService.getReasonLetterDetail(id);
        return ResponseData.<ReasonLetterDetail>builder()
                .code(1000)
                .data(response)
                .message("Update letter-reason")
                .build();
    }
}
