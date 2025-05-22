package vn.tdsoftware.hrm_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.tdsoftware.hrm_backend.common.dto.response.ResponseData;
import vn.tdsoftware.hrm_backend.dto.family.request.FamilyRequest;
import vn.tdsoftware.hrm_backend.dto.family.resposne.FamilyResponse;
import vn.tdsoftware.hrm_backend.service.FamilyService;

import java.util.List;

@RestController
@RequestMapping("/family")
@RequiredArgsConstructor
public class FamilyController {
    private final FamilyService familyService;

    @PostMapping("/get-family-profile-employee")
    @PreAuthorize("hasAnyAuthority('ROLE_WATCH_EMPLOYEE_COMPANY'," +
                                "'ROLE_WATCH_EMPLOYEE_DEPARTMENT'," +
                                "'ROLE_WATCH_SELF_EMPLOYEE', 'ADMIN')")
    public ResponseData<List<FamilyResponse>> getFamilyOfEmployee(@RequestParam("employeeId") long employeeId) {
        List<FamilyResponse> response = familyService.getFamilyOfEmployee(employeeId);
        return ResponseData.<List<FamilyResponse>>builder()
                .code(1000)
                .data(response)
                .message("Get family successfully")
                .build();
    }

    @PostMapping("/update-family-profile-employee")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGE_EMPLOYEE'," +
                                "'ROLE_MANAGE_SELF_EMPLOYEE', 'ADMIN')")
    public ResponseData<Void> updateFamilyOfEmployee(@RequestBody List<FamilyRequest> requests) {
        familyService.updateFamilyOfEmployee(requests);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("Get family successfully")
                .build();
    }

}

