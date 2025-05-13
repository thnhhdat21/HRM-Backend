package vn.tdsoftware.hrm_backend.controller;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.tdsoftware.hrm_backend.common.dto.response.ResponseData;
import vn.tdsoftware.hrm_backend.dto.education.request.EducationRequest;
import vn.tdsoftware.hrm_backend.dto.employee.request.*;
import vn.tdsoftware.hrm_backend.dto.employee.response.*;
import vn.tdsoftware.hrm_backend.dto.family.request.FamilyRequest;
import vn.tdsoftware.hrm_backend.dto.letter.response.leave.LeaveLetterResponse;
import vn.tdsoftware.hrm_backend.dto.letter.response.overtime.OverTimeLetterResponse;
import vn.tdsoftware.hrm_backend.dto.salarytable.response.SalaryDetailResponse;
import vn.tdsoftware.hrm_backend.dto.salarytable.response.SalaryMonth;
import vn.tdsoftware.hrm_backend.service.EmployeeService;

import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    private final Gson gson;

    @PostMapping("/get-resume-profile-employee")
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
    public ResponseData<Void> updateResumeProfile(ResumeProfileRequest request) {
        employeeService.updateResumeProfile(request);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("Get profile successfully")
                .build();
    }

    @PostMapping("/create-employee")
    public ResponseData<Void> createEmployee(
            @RequestPart("fullEmployeeRequest") FullEmployeeRequest resume,
            @RequestPart("avatar") MultipartFile avatar,
            @RequestPart("backIdentityCard") MultipartFile backIdentityCard,
            @RequestPart("fontIdentityCard") MultipartFile fontIdentityCard
    ) {
//        System.out.println(resume);
        employeeService.createEmployee(resume, avatar, fontIdentityCard, backIdentityCard);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("Get profile successfully")
                .build();
    }

    @PostMapping(path = "/update-identity-card",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseData<Void> updateIdentityCard(IdentityCartRequest request) {
        employeeService.updateIdentityCard(request);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("Get profile successfully")
                .build();
    }

    @PostMapping("/get-list-employee")
    public ResponseData<List<EmployeeSelectResponse>> getListEmployee() {
        List<EmployeeSelectResponse> response = employeeService.getListEmployee();
        return ResponseData.<List<EmployeeSelectResponse>>builder()
                .code(1000)
                .data(response)
                .message("Get list successfully")
                .build();
    }

    @PostMapping("/get-employee-timekeeping")
    public ResponseData<EmployeeTimeSheetResponse> getTimeSheetEmployee(@RequestBody EmployeeTimeSheetRequest request) {
        EmployeeTimeSheetResponse response =  employeeService.getTimeSheetEmployee(request);
        return ResponseData.<EmployeeTimeSheetResponse>builder()
                .code(1000)
                .data(response)
                .message("Get list successfully")
                .build();
    }

    @PostMapping("/check-in")
    public ResponseData<Void> employeeCheckin(@RequestParam("employeeId") long employeeId) {
        employeeService.employeeCheckin(employeeId);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("Checkin successfully")
                .build();
    }

    @PostMapping("/check-out")
    public ResponseData<Void> employeeCheckout(@RequestParam("employeeId") long employeeId) {
        employeeService.employeeCheckout(employeeId);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("Checkout successfully")
                .build();
    }

    @PostMapping("/get-list-leave-letter")
    public ResponseData<List<LeaveLetterResponse>> getListLeaveLetter(@RequestParam("employeeId") long employeeId) {
        List<LeaveLetterResponse> responses = employeeService.getListLeaveLetter(employeeId);
        return ResponseData.<List<LeaveLetterResponse>>builder()
                .code(1000)
                .data(responses)
                .message("Checkout successfully")
                .build();
    }

    @PostMapping("/get-list-overtime-letter")
    public ResponseData<List<OverTimeLetterResponse>> getListOverTimeLetter(@RequestParam("employeeId") long employeeId) {
        List<OverTimeLetterResponse> responses = employeeService.getListOverTimeLetter(employeeId);
        return ResponseData.<List<OverTimeLetterResponse>>builder()
                .code(1000)
                .data(responses)
                .message("Checkout successfully")
                .build();
    }

    @PostMapping("/update-insurance")
    public ResponseData<Void> updateInsurance(@RequestBody InsuranceEmployeeRequest request) {
        employeeService.updateInsurance(request);
        return ResponseData.<Void>builder()
                .code(1000)
                .message("Checkout successfully")
                .build();
    }

    @PostMapping("/get-insurance-number")
    public ResponseData<InsuranceEmployeeResponse> getInsuranceNumber(@RequestParam("employeeId") long employeeId) {
        InsuranceEmployeeResponse response = employeeService.getInsuranceNumber(employeeId);
        return ResponseData.<InsuranceEmployeeResponse>builder()
                .code(1000)
                .data(response)
                .message("Checkout successfully")
                .build();
    }

    @PostMapping("/get-list-salary-employee")
    public ResponseData<List<SalaryMonth>> getListSalaryEmployee(@RequestParam("employeeId") long employeeId, @RequestParam("year") String year) {
        List<SalaryMonth> response = employeeService.getListSalaryEmployee(employeeId, year);
        return ResponseData.<List<SalaryMonth>>builder()
                .code(1000)
                .data(response)
                .message("Checkout successfully")
                .build();
    }

    @PostMapping("/get-salary-detail-employee")
    public ResponseData<SalaryDetailResponse> getSalaryDetailEmployee(@RequestParam("salaryDetailId") long salaryDetailId) {
        SalaryDetailResponse response = employeeService.getSalaryDetailEmployee(salaryDetailId);
        return ResponseData.<SalaryDetailResponse>builder()
                .code(1000)
                .data(response)
                .message("Checkout successfully")
                .build();
    }

    @PostMapping("/get-salary-allowance-employee")
    public ResponseData<SalaryAllowanceEmployee> getSalaryAllowanceEmployee(@RequestParam("employeeId") long employeeId) {
        SalaryAllowanceEmployee response = employeeService.getSalaryAllowanceEmployee(employeeId);
        return ResponseData.<SalaryAllowanceEmployee>builder()
                .code(1000)
                .data(response)
                .message("Checkout successfully")
                .build();
    }

}
