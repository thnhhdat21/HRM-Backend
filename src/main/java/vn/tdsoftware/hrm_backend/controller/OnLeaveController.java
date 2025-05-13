package vn.tdsoftware.hrm_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vn.tdsoftware.hrm_backend.common.dto.response.ResponseData;
import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;
import vn.tdsoftware.hrm_backend.dto.onleave.request.OnLeaveRequest;
import vn.tdsoftware.hrm_backend.dto.onleave.response.EmployeeOnLeaveResponse;
import vn.tdsoftware.hrm_backend.dto.onleave.response.OnLeaveResponse;
import vn.tdsoftware.hrm_backend.service.OnLeaveService;

import java.util.List;

@RestController
@RequestMapping("/on-leave")
@RequiredArgsConstructor
public class OnLeaveController {
    private final OnLeaveService onLeaveService;

    @PostMapping("/get-on-leave-profile-employee")
    public ResponseData<OnLeaveResponse> getOnLeaveProfile(@RequestParam("employeeId") long employeeId) {
        OnLeaveResponse response = onLeaveService.getOnLeaveProfile(employeeId);
        return ResponseData.<OnLeaveResponse>builder()
                .code(1000)
                .data(response)
                .message("Get on leave successfully")
                .build();
    }

    @PostMapping("/update-on-leave-profile-employee")
    public ResponseData<Void> updateOnLeaveProfile(@RequestBody OnLeaveRequest request) {
        onLeaveService.updateOnLeaveProfile(request);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("Update on leave successfully")
                .build();
    }

    @PostMapping("/get-list-on-leave")
    public ResponseData<List<EmployeeOnLeaveResponse>> getEmployeeOnLeave(@RequestBody EmployeeFilter filter) {
        List<EmployeeOnLeaveResponse> responses = onLeaveService.getEmployeeOnLeave(filter);
        return ResponseData.<List<EmployeeOnLeaveResponse>>builder()
                .code(1000)
                .data(responses)
                .message("Update on leave successfully")
                .build();
    }

    @PostMapping("/get-count-on-leave")
    public ResponseData<Integer> getCountEmployee(@RequestBody EmployeeFilter filter) {
        int responses = onLeaveService.getCountEmployee(filter);
        return ResponseData.<Integer>builder()
                .code(1000)
                .data(responses)
                .message("Update on leave successfully")
                .build();
    }
}
