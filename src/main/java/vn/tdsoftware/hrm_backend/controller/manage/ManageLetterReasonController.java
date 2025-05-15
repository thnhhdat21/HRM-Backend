package vn.tdsoftware.hrm_backend.controller.manage;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vn.tdsoftware.hrm_backend.common.dto.response.ResponseData;
import vn.tdsoftware.hrm_backend.dto.letterreason.request.LetterReasonRequest;
import vn.tdsoftware.hrm_backend.dto.letterreason.response.LetterReasonDetail;
import vn.tdsoftware.hrm_backend.dto.letterreason.response.LetterReasonResponse;
import vn.tdsoftware.hrm_backend.service.LetterReasonService;

import java.util.List;

@RestController
@RequestMapping("/admin/letter-reason")
@RequiredArgsConstructor
public class ManageLetterReasonController {
    private final LetterReasonService letterReasonService;

    @PostMapping("/create-letter-reason")
    public ResponseData<List<LetterReasonResponse>> createLetterReason(@RequestBody List<LetterReasonRequest> request) {
        List<LetterReasonResponse> response = letterReasonService.createLetterReason(request);
        return ResponseData.<List<LetterReasonResponse>>builder()
                .code(1000)
                .data(response)
                .message("Create letter-reason")
                .build();
    }

    @PostMapping("/update-letter-reason")
    public ResponseData<LetterReasonResponse> updateLetterReason(@RequestBody LetterReasonRequest request) {
        LetterReasonResponse response = letterReasonService.updateLetterReason(request);
        return ResponseData.<LetterReasonResponse>builder()
                .code(1000)
                .data(response)
                .message("Update letter-reason")
                .build();
    }

    @PostMapping("/delete-letter-reason")
    public ResponseData<Void> deleteLetterReason(@RequestParam("id") Long id) {
        letterReasonService.deleteLetterReason(id);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("Update letter-reason")
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
