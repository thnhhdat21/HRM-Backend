package vn.tdsoftware.hrm_backend.dao;

import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;
import vn.tdsoftware.hrm_backend.dto.timekeeping.request.WorkingDayRequest;
import vn.tdsoftware.hrm_backend.dto.timekeeping.response.EmployeeCountTimeKeeping;
import vn.tdsoftware.hrm_backend.dto.timekeeping.response.EmployeeTimeKeeping;
import vn.tdsoftware.hrm_backend.dto.timekeeping.response.WorkingDayResponse;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Repository
public interface TimeKeepingDAO {
    List<EmployeeTimeKeeping> getListTimeKeeping(EmployeeFilter filter, LocalDate dateStart, LocalDate dateEnd);
    int getCountTimeKeeping(EmployeeFilter filter);
    void closingTimeKeeping(YearMonth yearMonth);
    WorkingDayResponse getWorkingDay(WorkingDayRequest request);
    List<EmployeeCountTimeKeeping> getListEmployeeTimeKeeping(int workDayReal, String yearMonth);
}
