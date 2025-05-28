package vn.tdsoftware.hrm_backend.controller;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.tdsoftware.hrm_backend.common.dto.response.ResponseData;
import vn.tdsoftware.hrm_backend.dto.account.response.CurrentAccountDTO;
import vn.tdsoftware.hrm_backend.dto.employee.request.*;
import vn.tdsoftware.hrm_backend.dto.employee.response.*;
import vn.tdsoftware.hrm_backend.dto.letter.response.leave.LeaveLetterResponse;
import vn.tdsoftware.hrm_backend.dto.letter.response.overtime.OverTimeLetterResponse;
import vn.tdsoftware.hrm_backend.dto.salarytable.response.SalaryDetailResponse;
import vn.tdsoftware.hrm_backend.dto.salarytable.response.SalaryMonth;
import vn.tdsoftware.hrm_backend.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    private final Gson gson;

    @PostMapping("/get-resume-profile-employee")
    @PreAuthorize("hasAnyAuthority('ROLE_WATCH_EMPLOYEE_COMPANY'," +
                                    "'ROLE_WATCH_EMPLOYEE_DEPARTMENT'," +
                                    "'ROLE_WATCH_SELF_EMPLOYEE', 'ADMIN')")
    public ResponseData<ResumeProfileResponse> getResumeProfile(@RequestParam("employeeId") long employeeId) {
        ResumeProfileResponse response = employeeService.getResumeProfile(employeeId);
        return ResponseData.<ResumeProfileResponse>builder()
                .code(1000)
                .data(response)
                .message("Get profile successfully")
                .build();
    }

    @PostMapping(path = "/update-resume-profile-employee",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGE_EMPLOYEE', 'ROLE_MANAGE_SELF_EMPLOYEE', 'ADMIN')")
    public ResponseData<Void> updateResumeProfile(ResumeProfileRequest request) {
        employeeService.updateResumeProfile(request);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("Get profile successfully")
                .build();
    }

    @PostMapping(path = "/update-identity-card",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGE_EMPLOYEE', 'ROLE_MANAGE_SELF_EMPLOYEE', 'ADMIN')")
    public ResponseData<Void> updateIdentityCard(IdentityCartRequest request) {
        employeeService.updateIdentityCard(request);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("Get profile successfully")
                .build();
    }

    @PostMapping("/get-list-employee")
    public ResponseData<List<EmployeeNameAndCode>> getListEmployee() {
        List<EmployeeNameAndCode> response = employeeService.getListEmployee();
        return ResponseData.<List<EmployeeNameAndCode>>builder()
                .code(1000)
                .data(response)
                .message("Get list successfully")
                .build();
    }

    @PostMapping("/get-employee-timekeeping")
    @PreAuthorize("hasAnyAuthority('ROLE_WATCH_SELF_EMPLOYEE', 'ADMIN')")
    public ResponseData<EmployeeTimeSheetResponse> getTimeSheetEmployee(@RequestBody EmployeeTimeSheetRequest request) {
        EmployeeTimeSheetResponse response =  employeeService.getTimeSheetEmployee(request);
        return ResponseData.<EmployeeTimeSheetResponse>builder()
                .code(1000)
                .data(response)
                .message("Get list successfully")
                .build();
    }

    @PostMapping("/check-in")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGE_SELF_EMPLOYEE', 'ADMIN')")
    public ResponseData<Void> employeeCheckin() {
        employeeService.employeeCheckin(CurrentAccountDTO.getEmployeeId());
        return ResponseData.<Void>builder()
                .code(1000)
                .message("Checkin successfully")
                .build();
    }

    @PostMapping("/check-out")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGE_SELF_EMPLOYEE', 'ADMIN')")
    public ResponseData<Void> employeeCheckout() {
        employeeService.employeeCheckout(CurrentAccountDTO.getEmployeeId());
        return ResponseData.<Void>builder()
                .code(1000)
                .message("Checkout successfully")
                .build();
    }

    @PostMapping("/get-list-leave-letter")
    @PreAuthorize("hasAnyAuthority('ROLE_WATCH_EMPLOYEE_COMPANY'," +
                                    "'ROLE_WATCH_EMPLOYEE_DEPARTMENT'," +
                                    "'ROLE_WATCH_SELF_EMPLOYEE', 'ADMIN')")
    public ResponseData<List<LeaveLetterResponse>> getListLeaveLetter(@RequestParam("employeeId") long employeeId) {
        List<LeaveLetterResponse> responses = employeeService.getListLeaveLetter(employeeId);
        return ResponseData.<List<LeaveLetterResponse>>builder()
                .code(1000)
                .data(responses)
                .message("Checkout successfully")
                .build();
    }

    @PostMapping("/get-list-overtime-letter")
    @PreAuthorize("hasAnyAuthority('ROLE_WATCH_EMPLOYEE_COMPANY'," +
                                    "'ROLE_WATCH_EMPLOYEE_DEPARTMENT'," +
                                    "'ROLE_WATCH_SELF_EMPLOYEE', 'ADMIN')")
    public ResponseData<List<OverTimeLetterResponse>> getListOverTimeLetter(@RequestParam("employeeId") long employeeId) {
        List<OverTimeLetterResponse> responses = employeeService.getListOverTimeLetter(employeeId);
        return ResponseData.<List<OverTimeLetterResponse>>builder()
                .code(1000)
                .data(responses)
                .message("Checkout successfully")
                .build();
    }

    @PostMapping("/update-insurance")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGE_EMPLOYEE', 'ROLE_MANAGE_SELF_EMPLOYEE', 'ADMIN')")
    public ResponseData<Void> updateInsurance(@RequestBody InsuranceEmployeeRequest request) {
        employeeService.updateInsurance(request);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("Checkout successfully")
                .build();
    }

    @PostMapping("/get-insurance-number")
    @PreAuthorize("hasAnyAuthority('ROLE_WATCH_EMPLOYEE_COMPANY'," +
                                    "'ROLE_WATCH_EMPLOYEE_DEPARTMENT'," +
                                    "'ROLE_WATCH_SELF_EMPLOYEE', 'ADMIN')")
    public ResponseData<InsuranceEmployeeResponse> getInsuranceNumber(@RequestParam("employeeId") long employeeId) {
        InsuranceEmployeeResponse response = employeeService.getInsuranceNumber(employeeId);
        return ResponseData.<InsuranceEmployeeResponse>builder()
                .code(1000)
                .data(response)
                .message("Checkout successfully")
                .build();
    }

    @PostMapping("/get-list-salary-employee")
    @PreAuthorize("hasAnyAuthority('ROLE_WATCH_EMPLOYEE_COMPANY'," +
                                    "'ROLE_WATCH_EMPLOYEE_DEPARTMENT'," +
                                    "'ROLE_WATCH_SELF_EMPLOYEE', 'ADMIN')")
    public ResponseData<List<SalaryMonth>> getListSalaryEmployee(@RequestParam("employeeId") long employeeId, @RequestParam("year") String year) {
        List<SalaryMonth> response = employeeService.getListSalaryEmployee(employeeId, year);
        return ResponseData.<List<SalaryMonth>>builder()
                .code(1000)
                .data(response)
                .message("Checkout successfully")
                .build();
    }

    @PostMapping("/get-salary-detail-employee")
    @PreAuthorize("hasAnyAuthority('ROLE_WATCH_EMPLOYEE_COMPANY'," +
                                    "'ROLE_WATCH_EMPLOYEE_DEPARTMENT'," +
                                    "'ROLE_WATCH_SELF_EMPLOYEE' , 'ADMIN')")
    public ResponseData<SalaryDetailResponse> getSalaryDetailEmployee(@RequestParam("salaryDetailId") long salaryDetailId) {
        SalaryDetailResponse response = employeeService.getSalaryDetailEmployee(salaryDetailId);
        return ResponseData.<SalaryDetailResponse>builder()
                .code(1000)
                .data(response)
                .message("Checkout successfully")
                .build();
    }

    @PostMapping("/get-salary-allowance-employee")
    @PreAuthorize("hasAnyAuthority('ROLE_WATCH_EMPLOYEE_COMPANY'," +
                                    "'ROLE_WATCH_EMPLOYEE_DEPARTMENT'," +
                                    "'ROLE_WATCH_SELF_EMPLOYEE' , 'ADMIN')")
    public ResponseData<SalaryAllowanceEmployee> getSalaryAllowanceEmployee(@RequestParam("employeeId") long employeeId) {
        SalaryAllowanceEmployee response = employeeService.getSalaryAllowanceEmployee(employeeId);
        return ResponseData.<SalaryAllowanceEmployee>builder()
                .code(1000)
                .data(response)
                .message("Checkout successfully")
                .build();
    }

    @PostMapping("/get-employee-name-code")
    public ResponseData<EmployeeNameAndCode> getEmployeeNameCode(@RequestParam("employeeId") long employeeId) {
        EmployeeNameAndCode response = employeeService.getEmployeeNameCode(employeeId);
        return ResponseData.<EmployeeNameAndCode>builder()
                .code(1000)
                .data(response)
                .message("Checkout successfully")
                .build();
    }

    @PostMapping("/get-employee-job-position")
    public ResponseData<EmployeeResponse> getJobPositionByEmployeeId(@RequestParam("employeeId") long employeeId) {
        EmployeeResponse response = employeeService.getJobPositionByEmployeeId(employeeId);
        return ResponseData.<EmployeeResponse>builder()
                .code(1000)
                .data(response)
                .message("Checkout successfully")
                .build();
    }

}
