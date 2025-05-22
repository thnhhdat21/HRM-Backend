package vn.tdsoftware.hrm_backend.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dao.TimeKeepingDAO;
import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;
import vn.tdsoftware.hrm_backend.dto.timekeeping.request.WorkingDayRequest;
import vn.tdsoftware.hrm_backend.dto.timekeeping.response.EmployeeTimeKeeping;
import vn.tdsoftware.hrm_backend.dto.timekeeping.response.EmployeeTimeKeepingResponse;
import vn.tdsoftware.hrm_backend.dto.timekeeping.response.TimeKeepingResponse;
import vn.tdsoftware.hrm_backend.dto.timekeeping.response.WorkingDayResponse;
import vn.tdsoftware.hrm_backend.entity.TimeSheet;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.repository.TimeKeepingRepository;
import vn.tdsoftware.hrm_backend.repository.TimeSheetRepository;
import vn.tdsoftware.hrm_backend.service.EmployeeService;
import vn.tdsoftware.hrm_backend.service.TimeKeepingService;
import vn.tdsoftware.hrm_backend.util.PerTimeSheetUtil;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TimeKeepingServiceImpl implements TimeKeepingService {

    private final TimeKeepingDAO timeKeepingDAO;
    private final EmployeeService employeeService;
    private final TimeKeepingRepository timeKeepingRepository;
    private final TimeSheetRepository timeSheetRepository;
    private final PerTimeSheetUtil perTimeSheetUtil;

    @Override
    public List<EmployeeTimeKeepingResponse> getListTimeKeeping(EmployeeFilter filter) {
        perTimeSheetUtil.checkSameDepartmentByFilter(filter);
        YearMonth yearMonth = YearMonth.from(filter.getYearMonth());
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.equals(YearMonth.from(LocalDate.now()))
                ? LocalDate.now()
                : yearMonth.atEndOfMonth();
        List<EmployeeTimeKeeping> list = timeKeepingDAO.getListTimeKeeping(filter, startDate, endDate);
        if (list.isEmpty()) {
            throw new BusinessException(ErrorCode.EMPLOYEE_IS_EMPTY);
        }
        Set<String> employeeCodes = new HashSet<>();
        int index = 0;
        LocalDate nextDay = startDate;
        EmployeeTimeKeepingResponse employeeTimeKeepingResponse = new EmployeeTimeKeepingResponse();
        List<EmployeeTimeKeepingResponse> responseList = new ArrayList<>();
        while (index < list.size() || !nextDay.isAfter(endDate)) {
            EmployeeTimeKeeping employeeTimeKeeping = new EmployeeTimeKeeping();
            if (index < list.size()) {
                employeeTimeKeeping = list.get(index);
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
            }

            while(!nextDay.isAfter(endDate)) {
                if (nextDay.equals(employeeTimeKeeping.getDateWorking())) {
                    employeeTimeKeepingResponse.getTimeKeeping().add(TimeKeepingResponse.builder()
                            .dateWorking(employeeTimeKeeping.getDateWorking())
                            .workDay(employeeTimeKeeping.getWorkDay())
                            .symbolLetter(employeeTimeKeeping.getSymbolLetter())
                            .isLate(employeeTimeKeeping.isLate())
                            .build());
                    employeeTimeKeepingResponse.setTotalWorkDay(employeeTimeKeepingResponse.getTotalWorkDay() + employeeTimeKeeping.getWorkDay());
                    if(employeeTimeKeeping.isLate())
                        employeeTimeKeepingResponse.setTotalLateDay(employeeTimeKeepingResponse.getTotalLateDay() + 1);

                    if (index + 1 < list.size() && Objects.equals(employeeTimeKeeping.getEmployeeCode(), list.get(index + 1).getEmployeeCode())) {
                        nextDay = nextDay.plusDays(1);
                        break;
                    }

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
    public int getCountTimeKeeping(EmployeeFilter filter) {
        perTimeSheetUtil.checkSameDepartmentByFilter(filter);
        return timeKeepingDAO.getCountTimeKeeping(filter);
    }

    @Override
    public Boolean timeSheetState(YearMonth yearMonth) {
        TimeSheet timeSheet = timeSheetRepository.findByYearMonthAndIsEnabled(yearMonth.toString(),true).orElse(null);
        return timeSheet == null ? null : timeSheet.getIsClosed();
    }

    @Override
    @Transactional
    public void closingTimeKeeping(YearMonth request) {
        timeKeepingDAO.closingTimeKeeping(request);
    }

    @Override
    public WorkingDayResponse getWorkingDay(WorkingDayRequest request) {
        perTimeSheetUtil.checkWatchSameDepartmentByEmployeeId(request.getEmployeeId());
        employeeService.checkEmployeeValidator(request.getEmployeeId());
        int date = request.getWorkingDay().getDayOfWeek().getValue();
        if (date == 6 || date == 7) {
            if(!timeKeepingRepository.existsByEmployeeIdAndDate(request.getEmployeeId(), request.getWorkingDay()))
                throw new BusinessException(ErrorCode.WORKING_DAY_LEAVE);
        }
        return timeKeepingDAO.getWorkingDay(request);
    }

}
