package vn.tdsoftware.hrm_backend.service;

import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;
import vn.tdsoftware.hrm_backend.dto.letter.request.inoutandendword.InOutAndEndWorkRequest;
import vn.tdsoftware.hrm_backend.dto.letter.request.leave.LeaveLetterRequest;
import vn.tdsoftware.hrm_backend.dto.letter.request.overtime.OverTimeLetterRequest;
import vn.tdsoftware.hrm_backend.dto.letter.request.worktime.WorkTimeLetterRequest;
import vn.tdsoftware.hrm_backend.dto.letter.response.CountLetterResponse;
import vn.tdsoftware.hrm_backend.dto.letter.response.LetterResponse;
import vn.tdsoftware.hrm_backend.dto.letter.response.OverTimeLetterOfEmployee;
import vn.tdsoftware.hrm_backend.dto.letter.response.leave.LeaveLetterResponse;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public interface LetterService {
    List<LetterResponse> getListLetter(EmployeeFilter filter);
    List<CountLetterResponse> getCountLetter(EmployeeFilter filter);
    void updateLeaveLetter (LeaveLetterRequest request);
    void updateOverTimeLetter(OverTimeLetterRequest request);
    void updateWorkTimeLetter(WorkTimeLetterRequest request);
    void updateInOutAndEndWorkLetter(InOutAndEndWorkRequest request);
    void deleteLetter(long letterId);
    void noApprovalLetter(long letterId);
    Object getLetter(long letterId);
    void approveLetter(long letterId);
    List<OverTimeLetterOfEmployee> getOverTimeLetter(long employeeId, YearMonth yearMonth);
 }

