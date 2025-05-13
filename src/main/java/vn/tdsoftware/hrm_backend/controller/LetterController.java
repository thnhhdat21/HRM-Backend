package vn.tdsoftware.hrm_backend.controller;


import lombok.RequiredArgsConstructor;
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

    @PostMapping("/get-list-letter")
    public ResponseData<List<LetterResponse>> getListLetter(@RequestBody EmployeeFilter filter) {
        List<LetterResponse> responses =  letterService.getListLetter(filter);
        return ResponseData.<List<LetterResponse>>builder()
                .code(1000)
                .data(responses)
                .message("success")
                .build();
    }

    @PostMapping("/get-count-letter")
    public ResponseData<List<CountLetterResponse>> getCountLetter (@RequestBody EmployeeFilter filter) {
        List<CountLetterResponse> responses =  letterService.getCountLetter(filter);
        return ResponseData.<List<CountLetterResponse>>builder()
                .code(1000)
                .data(responses)
                .message("success")
                .build();
    }

    @PostMapping("/update-leave-letter")
    public ResponseData<Void> updateLeaveLetter (@RequestBody LeaveLetterRequest request) {
        letterService.updateLeaveLetter(request);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("success")
                .build();
    }

    @PostMapping("/update-overtime-letter")
    public ResponseData<Void> updateOverTimeLetter (@RequestBody OverTimeLetterRequest request) {
        letterService.updateOverTimeLetter(request);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("success")
                .build();
    }

    @PostMapping("/update-worktime-letter")
    public ResponseData<Void> updateWorkTimeLetter (@RequestBody WorkTimeLetterRequest request) {
        letterService.updateWorkTimeLetter(request);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("success")
                .build();
    }


    @PostMapping("/update-inout-endwork-letter")
    public ResponseData<Void> updateInOutAndEndWorkLetter (@RequestBody InOutAndEndWorkRequest request) {
        letterService.updateInOutAndEndWorkLetter(request);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("success")
                .build();
    }

    @PostMapping("/delete-letter")
    public ResponseData<Void> deleteLetter(@RequestParam long letterId) {
        letterService.deleteLetter(letterId);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("success")
                .build();
    }

    @PostMapping("/get-letter-by-id")
    public ResponseData<Object> getLetterBy(@RequestParam long letterId) {
        Object response = letterService.getLetter(letterId);
        return ResponseData.builder()
                .code(1000)
                .data(response)
                .message("success")
                .build();
    }

    @PostMapping("/no-approve-letter")
    public ResponseData<Void> noApproveLetter(@RequestParam long letterId) {
        letterService.noApprovalLetter(letterId);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("success")
                .build();
    }

    @PostMapping("/approve-letter")
    public ResponseData<Void> approveLetter(@RequestParam long letterId) {
        letterService.approveLetter(letterId);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("approve successfully")
                .build();
    }


}
