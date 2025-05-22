package vn.tdsoftware.hrm_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.tdsoftware.hrm_backend.common.dto.response.ResponseData;
import vn.tdsoftware.hrm_backend.dto.education.request.EducationRequest;
import vn.tdsoftware.hrm_backend.dto.education.response.EducationResponse;
import vn.tdsoftware.hrm_backend.service.EducationService;

import java.util.List;

@RestController
@RequestMapping("/education")
@RequiredArgsConstructor
public class EducationController {
    private final EducationService educationService;

    @PostMapping("/get-education-profile-employee")
    @PreAuthorize("hasAnyAuthority('ROLE_WATCH_EMPLOYEE_COMPANY'," +
                                    "'ROLE_WATCH_EMPLOYEE_DEPARTMENT'," +
                                    "'ROLE_WATCH_SELF_EMPLOYEE', 'ADMIN')")
    public ResponseData<List<EducationResponse>> getEducationProfile(@RequestParam("employeeId") long employeeId) {
        List<EducationResponse> response = educationService.getEducationProfile(employeeId);
        return ResponseData.<List<EducationResponse>>builder()
                .code(1000)
                .data(response)
                .message("Get education successfully")
                .build();
    }

    @PostMapping("/update-education-profile-employee")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGE_EMPLOYEE'," +
                                    "'ROLE_MANAGE_SELF_EMPLOYEE', 'ADMIN')")
    public ResponseData<Void> updateEducationProfile(@RequestBody List<EducationRequest> requests) {
        educationService.updateEducationProfile(requests);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("Get education successfully")
                .build();
    }
}
