package vn.tdsoftware.hrm_backend.controller.manage;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.tdsoftware.hrm_backend.common.dto.response.ResponseData;
import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;
import vn.tdsoftware.hrm_backend.dto.employee.request.FullEmployeeRequest;
import vn.tdsoftware.hrm_backend.dto.employee.response.EmployeeOfDepartment;
import vn.tdsoftware.hrm_backend.dto.employee.response.EmployeeTypeCount;
import vn.tdsoftware.hrm_backend.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/manage-employee")
@RequiredArgsConstructor
public class ManageEmployeeController {
    private final EmployeeService employeeService;

    @PostMapping("/get-list-employee")
    @PreAuthorize("hasAnyAuthority('ADMIN','ROLE_WATCH_EMPLOYEE_COMPANY', 'ROLE_WATCH_EMPLOYEE_DEPARTMENT')")
    public ResponseData<List<EmployeeOfDepartment>> getListEmployeeFilter(@RequestBody EmployeeFilter request) {
        List<EmployeeOfDepartment> responses= employeeService.getListEmployeeFilter(request);
        return ResponseData.<List<EmployeeOfDepartment>>builder()
                .code(1000)
                .data(responses)
                .message("Get List employee successfully")
                .build();
    }

    @PostMapping("/get-count-employee-type")
    @PreAuthorize("hasAnyAuthority('ADMIN','ROLE_WATCH_EMPLOYEE_COMPANY', 'ROLE_WATCH_EMPLOYEE_DEPARTMENT')")
    public ResponseData<List<EmployeeTypeCount>> getCountEmployeeFilter(@RequestBody EmployeeFilter request) {
        List<EmployeeTypeCount> responses= employeeService.getCountEmployeeFilter(request);
        return ResponseData.<List<EmployeeTypeCount>>builder()
                .code(1000)
                .data(responses)
                .message("Get List employee successfully")
                .build();
    }

    @PostMapping("/create-employee")
    @PreAuthorize("hasAnyAuthority('ADMIN','ROLE_MANAGE_EMPLOYEE')")
    public ResponseData<Void> createEmployee(
            @RequestPart("fullEmployeeRequest") FullEmployeeRequest resume,
            @RequestPart("avatar") MultipartFile avatar,
            @RequestPart("backIdentityCard") MultipartFile backIdentityCard,
            @RequestPart("fontIdentityCard") MultipartFile fontIdentityCard
    ) {
        employeeService.createEmployee(resume, avatar, fontIdentityCard, backIdentityCard);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("Get profile successfully")
                .build();
    }
}
