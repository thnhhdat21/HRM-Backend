package vn.tdsoftware.hrm_backend.service;


import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;
import vn.tdsoftware.hrm_backend.dto.timekeeping.request.TimeKeepingClosing;
import vn.tdsoftware.hrm_backend.dto.timekeeping.request.WorkingDayRequest;
import vn.tdsoftware.hrm_backend.dto.timekeeping.response.EmployeeTimeKeepingResponse;
import vn.tdsoftware.hrm_backend.dto.timekeeping.response.WorkingDayResponse;

import java.time.YearMonth;
import java.util.List;

public interface TimeKeepingService {
    List<EmployeeTimeKeepingResponse> getListTimeKeeping(EmployeeFilter filter);
    int getCountTimeKeeping(EmployeeFilter filter);
    Boolean timeSheetState(YearMonth yearMonth);
    void closingTimeKeeping(YearMonth yearMonth);
    WorkingDayResponse getWorkingDay(WorkingDayRequest request);
}
