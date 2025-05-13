package vn.tdsoftware.hrm_backend.controller;

import lombok.RequiredArgsConstructor;
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
    public ResponseData<List<FamilyResponse>> getFamilyOfEmployee(@RequestParam("employeeId") long employeeId) {
        List<FamilyResponse> response = familyService.getFamilyOfEmployee(employeeId);
        return ResponseData.<List<FamilyResponse>>builder()
                .code(1000)
                .data(response)
                .message("Get family successfully")
                .build();
    }

    @PostMapping("/update-family-profile-employee")
    public ResponseData<Void> updateFamilyOfEmployee(@RequestBody List<FamilyRequest> requests) {
        familyService.updateFamilyOfEmployee(requests);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("Get family successfully")
                .build();
    }

}

