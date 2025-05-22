package vn.tdsoftware.hrm_backend.service;

import org.springframework.web.multipart.MultipartFile;
import vn.tdsoftware.hrm_backend.dto.employee.request.*;
import vn.tdsoftware.hrm_backend.dto.employee.response.*;
import vn.tdsoftware.hrm_backend.dto.letter.response.leave.LeaveLetterResponse;
import vn.tdsoftware.hrm_backend.dto.letter.response.overtime.OverTimeLetterResponse;
import vn.tdsoftware.hrm_backend.dto.salarytable.response.SalaryDetailResponse;
import vn.tdsoftware.hrm_backend.dto.salarytable.response.SalaryMonth;
import vn.tdsoftware.hrm_backend.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<EmployeeOfDepartment> getListEmployeeFilter(EmployeeFilter filter);
    List<EmployeeTypeCount> getCountEmployeeFilter(EmployeeFilter filter);
    ResumeProfileResponse getResumeProfile(long employeeId);
    void updateResumeProfile(ResumeProfileRequest request);
    void updateIdentityCard(IdentityCartRequest request);
    Employee checkEmployeeValidator(long id);
    List<EmployeeNameAndCode> getListEmployee();
    EmployeeTimeSheetResponse getTimeSheetEmployee(EmployeeTimeSheetRequest request);
    void employeeCheckin(long employeeId);
    void employeeCheckout(long employeeId);

    List<LeaveLetterResponse> getListLeaveLetter(long employeeId);
    List<OverTimeLetterResponse> getListOverTimeLetter(long employeeId);
    void createEmployee(FullEmployeeRequest request, MultipartFile avatar, MultipartFile fontIdentityCard, MultipartFile backIdentityCard);
    void updateInsurance(InsuranceEmployeeRequest request);
    InsuranceEmployeeResponse getInsuranceNumber(long employeeId);
    List<SalaryMonth> getListSalaryEmployee(long employeeId, String year);
    SalaryDetailResponse getSalaryDetailEmployee(long salaryDetailId);
    SalaryAllowanceEmployee getSalaryAllowanceEmployee(long employeeId);
    EmployeeNameAndCode getEmployeeNameCode(long employeeId);
    EmployeeResponse getJobPositionByEmployeeId(long employeeId);
}

