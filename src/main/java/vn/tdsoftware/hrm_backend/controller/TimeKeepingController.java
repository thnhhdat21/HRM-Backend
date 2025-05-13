package vn.tdsoftware.hrm_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vn.tdsoftware.hrm_backend.common.dto.response.ResponseData;
import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;
import vn.tdsoftware.hrm_backend.dto.timekeeping.request.TimeKeepingClosing;
import vn.tdsoftware.hrm_backend.dto.timekeeping.request.WorkingDayRequest;
import vn.tdsoftware.hrm_backend.dto.timekeeping.response.EmployeeTimeKeepingResponse;
import vn.tdsoftware.hrm_backend.dto.timekeeping.response.WorkingDayResponse;
import vn.tdsoftware.hrm_backend.service.TimeKeepingService;

import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/time-keeping")
@RequiredArgsConstructor
public class TimeKeepingController {
    private final TimeKeepingService timeKeepingService;

    @PostMapping("/get-timekeeping-by-department")
    public ResponseData<List<EmployeeTimeKeepingResponse>> getListTimeKeeping(@RequestBody EmployeeFilter filter) {
        List<EmployeeTimeKeepingResponse> responses = timeKeepingService.getListTimeKeeping(filter);
        return ResponseData.<List<EmployeeTimeKeepingResponse>>builder()
                .code(1000)
                .data(responses)
                .message("Get list successfully")
                .build();
    }

    @PostMapping("/get-count-timekeeping")
    public ResponseData<Integer> getCountTimeKeeping(@RequestBody EmployeeFilter filter) {
       int count = timeKeepingService.getCountTimeKeeping(filter);
        return ResponseData.<Integer>builder()
                .code(1000)
                .data(count)
                .message("Get list successfully")
                .build();
    }

    @PostMapping("/time-sheet-state")
    public ResponseData<Boolean> timeSheetState(@RequestParam("yearMonth") YearMonth yearMonth) {
        Boolean response = timeKeepingService.timeSheetState(yearMonth);
        return ResponseData.<Boolean>builder()
                .code(1000)
                .data(response)
                .message("Get list successfully")
                .build();
    }

    @PostMapping("/closing-timekeeping")
    public ResponseData<Void> closingTimeKeeping(@RequestParam("yearMonth") YearMonth yearMonth) {
        timeKeepingService.closingTimeKeeping(yearMonth);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("Get list successfully")
                .build();
    }

    @PostMapping("/get-working-day")
    public ResponseData<WorkingDayResponse> getWorkingDay(@RequestBody WorkingDayRequest request) {
        WorkingDayResponse response = timeKeepingService.getWorkingDay(request);
        return ResponseData.<WorkingDayResponse>builder()
                .code(1000)
                .data(response)
                .message("Get list successfully")
                .build();
    }


}
