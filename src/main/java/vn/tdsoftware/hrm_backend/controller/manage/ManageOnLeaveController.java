package vn.tdsoftware.hrm_backend.controller.manage;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.tdsoftware.hrm_backend.common.dto.response.ResponseData;
import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;
import vn.tdsoftware.hrm_backend.dto.onleave.response.EmployeeOnLeaveResponse;
import vn.tdsoftware.hrm_backend.service.OnLeaveService;

import java.util.List;

@RestController
@RequestMapping("/manage-on-leave")
@RequiredArgsConstructor
public class ManageOnLeaveController {
    private final OnLeaveService onLeaveService;

    @PostMapping("/get-list-on-leave")
    @PreAuthorize("hasAnyAuthority('ROLE_WATCH_TIMESHEET_COMPANY'," +
                                    "'ROLE_WATCH_TIMESHEET_DEPARTMENT')")
    public ResponseData<List<EmployeeOnLeaveResponse>> getEmployeeOnLeave(@RequestBody EmployeeFilter filter) {
        List<EmployeeOnLeaveResponse> responses = onLeaveService.getEmployeeOnLeave(filter);
        return ResponseData.<List<EmployeeOnLeaveResponse>>builder()
                .code(1000)
                .data(responses)
                .message("Update on leave successfully")
                .build();
    }

    @PostMapping("/get-count-on-leave")
    @PreAuthorize("hasAnyAuthority('ROLE_WATCH_TIMESHEET_COMPANY'," +
                                    "'ROLE_WATCH_TIMESHEET_DEPARTMENT')")
    public ResponseData<Integer> getCountEmployee(@RequestBody EmployeeFilter filter) {
        int responses = onLeaveService.getCountEmployee(filter);
        return ResponseData.<Integer>builder()
                .code(1000)
                .data(responses)
                .message("Update on leave successfully")
                .build();
    }
}
