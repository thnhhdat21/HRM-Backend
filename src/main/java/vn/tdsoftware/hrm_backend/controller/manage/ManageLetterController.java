package vn.tdsoftware.hrm_backend.controller.manage;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.tdsoftware.hrm_backend.common.dto.response.ResponseData;
import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;
import vn.tdsoftware.hrm_backend.dto.letter.response.CountLetterResponse;
import vn.tdsoftware.hrm_backend.dto.letter.response.LetterResponse;
import vn.tdsoftware.hrm_backend.service.LetterService;

import java.util.List;


@RestController
@RequestMapping("/manage-letter")
@RequiredArgsConstructor
public class ManageLetterController {
    private final LetterService letterService;

    @PostMapping("/get-list-letter")
    @PreAuthorize("hasAnyAuthority('ADMIN','ROLE_WATCH_LETTER_COMPANY'," +
                                    "'ROLE_WATCH_LETTER_DEPARTMENT')")
    public ResponseData<List<LetterResponse>> getListLetter(@RequestBody EmployeeFilter filter) {
        List<LetterResponse> responses =  letterService.getListLetter(filter);
        return ResponseData.<List<LetterResponse>>builder()
                .code(1000)
                .data(responses)
                .message("success")
                .build();
    }

    @PostMapping("/get-count-letter")
    @PreAuthorize("hasAnyAuthority('ADMIN','ROLE_WATCH_LETTER_COMPANY'," +
                                    "'ROLE_WATCH_LETTER_DEPARTMENT')")
    public ResponseData<List<CountLetterResponse>> getCountLetter (@RequestBody EmployeeFilter filter) {
        List<CountLetterResponse> responses =  letterService.getCountLetter(filter);
        return ResponseData.<List<CountLetterResponse>>builder()
                .code(1000)
                .data(responses)
                .message("success")
                .build();
    }

    @PostMapping("/no-approve-letter")
    @PreAuthorize("hasAnyAuthority('ADMIN','ROLE_WATCH_LETTER_COMPANY'," +
                                    "'ROLE_WATCH_LETTER_DEPARTMENT')")
    public ResponseData<Void> noApproveLetter(@RequestParam long letterId) {
        letterService.noApprovalLetter(letterId);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("success")
                .build();
    }

    @PostMapping("/approve-letter")
    @PreAuthorize("hasAnyAuthority('ADMIN','ROLE_WATCH_LETTER_COMPANY'," +
                                    "'ROLE_WATCH_LETTER_DEPARTMENT')")
    public ResponseData<Void> approveLetter(@RequestParam long letterId) {
        letterService.approveLetter(letterId);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("approve successfully")
                .build();
    }
}
