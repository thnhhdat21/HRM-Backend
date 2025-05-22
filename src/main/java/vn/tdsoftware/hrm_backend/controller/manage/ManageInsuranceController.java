package vn.tdsoftware.hrm_backend.controller.manage;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.tdsoftware.hrm_backend.common.dto.response.ResponseData;
import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;
import vn.tdsoftware.hrm_backend.dto.insurance.response.InsuranceResponse;
import vn.tdsoftware.hrm_backend.service.InsuranceService;

import java.util.List;

@RestController
@RequestMapping("/manage-insurance")
@RequiredArgsConstructor
public class ManageInsuranceController {

    private final InsuranceService insuranceService;

    @PostMapping("/get-list-insurance")
    @PreAuthorize("hasAnyAuthority('ADMIN','ROLE_WATCH_INSURANCE_COMPANY', 'ROLE_WATCH_INSURANCE_DEPARTMENT')")
    ResponseData<List<InsuranceResponse>> getListInsurance(@RequestBody EmployeeFilter filter) {
        List<InsuranceResponse> responses = insuranceService.getListInsurance(filter);
        return ResponseData.<List<InsuranceResponse>>builder()
                .code(1000)
                .data(responses)
                .message("Get list successfully")
                .build();
    }

    @PostMapping("/count-insurance")
    @PreAuthorize("hasAnyAuthority('ADMIN','ROLE_WATCH_INSURANCE_COMPANY', 'ROLE_WATCH_INSURANCE_DEPARTMENT')")
    ResponseData<Integer> getCountInsurance(@RequestBody EmployeeFilter filter) {
        int responses = insuranceService.getCountInsurance(filter);
        return ResponseData.<Integer>builder()
                .code(1000)
                .data(responses)
                .message("Get list successfully")
                .build();
    }
}
