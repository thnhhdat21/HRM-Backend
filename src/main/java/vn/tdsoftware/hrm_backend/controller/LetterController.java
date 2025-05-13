package vn.tdsoftware.hrm_backend.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.tdsoftware.hrm_backend.common.dto.response.ResponseData;
import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;
import vn.tdsoftware.hrm_backend.dto.letter.request.inoutandendword.InOutAndEndWorkRequest;
import vn.tdsoftware.hrm_backend.dto.letter.request.leave.LeaveLetterRequest;
import vn.tdsoftware.hrm_backend.dto.letter.request.overtime.OverTimeLetterRequest;
import vn.tdsoftware.hrm_backend.dto.letter.request.worktime.WorkTimeLetterRequest;
import vn.tdsoftware.hrm_backend.dto.letter.response.CountLetterResponse;
import vn.tdsoftware.hrm_backend.dto.letter.response.LetterResponse;
import vn.tdsoftware.hrm_backend.service.LetterService;

import java.util.List;


@RestController
@RequestMapping("/letter")
@RequiredArgsConstructor
public class LetterController {
    private final LetterService letterService;

    @PostMapping("/update-leave-letter")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGE_SELF_LETTER', " +
                                    "'ROLE_CREATE_SELF_LETTER')")
    public ResponseData<Void> updateLeaveLetter (@RequestBody LeaveLetterRequest request) {
        letterService.updateLeaveLetter(request);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("success")
                .build();
    }

    @PostMapping("/update-overtime-letter")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGE_SELF_LETTER', " +
            "'ROLE_CREATE_SELF_LETTER')")
    public ResponseData<Void> updateOverTimeLetter (@RequestBody OverTimeLetterRequest request) {
        letterService.updateOverTimeLetter(request);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("success")
                .build();
    }

    @PostMapping("/update-worktime-letter")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGE_SELF_LETTER', " +
            "'ROLE_CREATE_SELF_LETTER')")
    public ResponseData<Void> updateWorkTimeLetter (@RequestBody WorkTimeLetterRequest request) {
        letterService.updateWorkTimeLetter(request);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("success")
                .build();
    }


    @PostMapping("/update-inout-endwork-letter")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGE_SELF_LETTER', " +
                                    "'ROLE_CREATE_SELF_LETTER')")
    public ResponseData<Void> updateInOutAndEndWorkLetter (@RequestBody InOutAndEndWorkRequest request) {
        letterService.updateInOutAndEndWorkLetter(request);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("success")
                .build();
    }

    @PostMapping("/delete-letter")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGE_LETTER_COMPANY'," +
                                    "'ROLE_MANAGE_LETTER_DEPARTMENT', " +
                                    "'ROLE_MANAGE_SELF_LETTER')")
    public ResponseData<Void> deleteLetter(@RequestParam long letterId) {
        letterService.deleteLetter(letterId);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("success")
                .build();
    }

    @PostMapping("/get-letter-by-id")
    @PreAuthorize("hasAnyAuthority('ROLE_WATCH_LETTER_COMPANY'," +
                                    "'ROLE_WATCH_LETTER_DEPARTMENT', " +
                                    "'ROLE_WATCH_SELF_LETTER')")
    public ResponseData<Object> getLetterBy(@RequestParam long letterId) {
        Object response = letterService.getLetter(letterId);
        return ResponseData.builder()
                .code(1000)
                .data(response)
                .message("success")
                .build();
    }
}
