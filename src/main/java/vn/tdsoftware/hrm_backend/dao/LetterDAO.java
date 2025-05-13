package vn.tdsoftware.hrm_backend.dao;

import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.dto.contract.response.EndJobCurrentDate;
import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;
import vn.tdsoftware.hrm_backend.dto.letter.response.*;
import vn.tdsoftware.hrm_backend.dto.letter.response.leave.LeaveLetterResponse;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LetterDAO {
    List<LetterResponse> getListLetter(EmployeeFilter filter);
    List<CountLetterResponse> getCountLetter(EmployeeFilter filter);
    List<OverTimeLetterOfEmployee> getOverTimeLetter(long employeeId, LocalDate startDate, LocalDate endDate);
    WorkTimeEmployee getWorkTimeEmployee(LocalDate date, long employeeId);
    List<LeaveLetterResponse> getListLeaveLetterApproved(LocalDate dateCurrent);
    List<EndJobCurrentDate> getListLetterCurrentDate(LocalDate date);
    List<LeaveLetterUnpaidSalary> getListLeaveLetterUnpaidSalary(LocalDate date, int dateStartOrEnd);
}
