package vn.tdsoftware.hrm_backend.service.impl;

import com.google.gson.Gson;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dao.TimeKeepingDAO;
import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;
import vn.tdsoftware.hrm_backend.dto.timekeeping.request.TimeKeepingClosing;
import vn.tdsoftware.hrm_backend.dto.timekeeping.response.EmployeeTimeKeeping;
import vn.tdsoftware.hrm_backend.dto.timekeeping.response.EmployeeTimeKeepingResponse;
import vn.tdsoftware.hrm_backend.dto.timekeeping.response.TimeKeepingResponse;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.repository.DepartmentRepository;
import vn.tdsoftware.hrm_backend.service.DepartmentService;
import vn.tdsoftware.hrm_backend.service.TimeKeepingService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TimeKeepingServiceImpl implements TimeKeepingService {

    private final TimeKeepingDAO timeKeepingDAO;
    private final DepartmentRepository departmentRepository;

    @Override
    public List<EmployeeTimeKeepingResponse> getListTimeKeeping(EmployeeFilter filter) {
        YearMonth yearMonth = YearMonth.from(filter.getYearMonth());
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();
        List<EmployeeTimeKeeping> list = timeKeepingDAO.getListTimeKeeping(filter, startDate, endDate);
        if (list.isEmpty()) {
            throw new BusinessException(ErrorCode.EMPLOYEE_IS_EMPTY);
        }
        Set<String> employeeCodes = new HashSet<>();
        int index = 0;
        LocalDate nextDay = null;
        EmployeeTimeKeepingResponse employeeTimeKeepingResponse = null;
        List<EmployeeTimeKeepingResponse> responseList = new ArrayList<>();
        while (index < list.size()) {
            EmployeeTimeKeeping employeeTimeKeeping = list.get(index);
            if (!employeeCodes.contains(employeeTimeKeeping.getEmployeeCode())) {
                employeeCodes.add(employeeTimeKeeping.getEmployeeCode());

                employeeTimeKeepingResponse = new EmployeeTimeKeepingResponse();
                employeeTimeKeepingResponse.setEmployeeId(employeeTimeKeeping.getEmployeeId());
                employeeTimeKeepingResponse.setEmployeeCode(employeeTimeKeeping.getEmployeeCode());
                employeeTimeKeepingResponse.setEmployeeName(employeeTimeKeeping.getEmployeeName());
                employeeTimeKeepingResponse.setDepartment(employeeTimeKeeping.getDepartment());
                employeeTimeKeepingResponse.setJobPosition(employeeTimeKeeping.getJobPosition());
                employeeTimeKeepingResponse.setOnLeaveTotal(employeeTimeKeeping.getOnLeaveTotal());
                employeeTimeKeepingResponse.setOnLeaveUsed(employeeTimeKeeping.getOnLeaveUsed());
                employeeTimeKeepingResponse.setOverTimeTotal(employeeTimeKeeping.getOverTimeTotal());
                List<TimeKeepingResponse> timeKeepingResponseList = new ArrayList<>();
                employeeTimeKeepingResponse.setTimeKeeping(timeKeepingResponseList);
                responseList.add(employeeTimeKeepingResponse);
                nextDay = startDate;
            }
            while(!nextDay.isAfter(employeeTimeKeeping.getDateWorking())) {
                if (nextDay.equals(employeeTimeKeeping.getDateWorking())) {
                    employeeTimeKeepingResponse.getTimeKeeping().add(TimeKeepingResponse.builder()
                            .dateWorking(employeeTimeKeeping.getDateWorking())
                            .workDay(employeeTimeKeeping.getWorkDay())
                            .symbolLetter(employeeTimeKeeping.getSymbolLetter())
                            .build());
                    if(checkTime(employeeTimeKeeping.getTimeLate()))
                        employeeTimeKeepingResponse.setTotalLateDay(employeeTimeKeepingResponse.getTotalLateDay() + 1);
                } else {
                    employeeTimeKeepingResponse.getTimeKeeping().add(TimeKeepingResponse.builder()
                            .dateWorking(nextDay)
                            .workDay(0)
                            .build());
                }
                nextDay = nextDay.plusDays(1);
            }
            index++;
        }
        return responseList;
    }

    @Override
    @Transactional
    public void closingTimeKeeping(TimeKeepingClosing request) {
        if (request.getDepartments() != null && !request.getDepartments().isEmpty()) {
            for (Long department : request.getDepartments()) {
                if (!departmentRepository.existsByIdAndIsEnabled(department, true)) {
                    throw new BusinessException(ErrorCode.DEPARTMENT_IS_EMPTY);
                }
            }
        }
        YearMonth yearMonth = YearMonth.from(request.getYearMonth());
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();
        timeKeepingDAO.closingTimeKeeping(request.getDepartments(), startDate, endDate);
    }

    private boolean checkTime(LocalTime time) {
        return time != null && !time.equals(LocalTime.MIDNIGHT);
    }

}
