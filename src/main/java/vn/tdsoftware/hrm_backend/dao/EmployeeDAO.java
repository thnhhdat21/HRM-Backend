package vn.tdsoftware.hrm_backend.dao;

import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;
import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeTimeSheetRequest;
import vn.tdsoftware.hrm_backend.dto.employee.response.*;
import vn.tdsoftware.hrm_backend.dto.letter.response.leave.LeaveLetterResponse;
import vn.tdsoftware.hrm_backend.dto.letter.response.overtime.OverTimeLetterResponse;
import vn.tdsoftware.hrm_backend.dto.salarytable.response.SalaryDetailResponse;
import vn.tdsoftware.hrm_backend.dto.salarytable.response.SalaryMonth;
import vn.tdsoftware.hrm_backend.entity.SalaryTable;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Repository
public interface EmployeeDAO {
    List<EmployeeResponse> getListEmployeeFilter(EmployeeFilter filter);
    List<EmployeeTypeCount> getCountEmployeeFilter(EmployeeFilter filter);
    ResumeProfileResponse getResumeProfile(Long id);
    List<EmployeeNameAndCode> getListEmployee();
    List<EmployeeTimeSheet> getTimeSheetEmployee(long employeeId, LocalDate dateStart, LocalDate dateEnd);
    List<LeaveLetterResponse> getListLeaveLetter(long employeeId, YearMonth yearMonth);
    List<OverTimeLetterResponse> getListOverTimeLetter(long employeeId, YearMonth yearMonth);
    List<SalaryMonth> getListSalaryEmployee(long employeeId, String year);
    SalaryDetailResponse getSalaryDetailEmployee(long salaryDetailId);
    SalaryAllowanceEmployee getSalaryAllowanceEmployee(long employeeId);
    EmployeeResponse getJobPositionByEmployeeId(long employeeId);
}
