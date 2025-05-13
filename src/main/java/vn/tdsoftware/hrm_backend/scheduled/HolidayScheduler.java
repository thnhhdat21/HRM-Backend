package vn.tdsoftware.hrm_backend.scheduled;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vn.tdsoftware.hrm_backend.entity.Employee;
import vn.tdsoftware.hrm_backend.entity.Holiday;
import vn.tdsoftware.hrm_backend.entity.TimeKeeping;
import vn.tdsoftware.hrm_backend.entity.TimeSheet;
import vn.tdsoftware.hrm_backend.repository.EmployeeRepository;
import vn.tdsoftware.hrm_backend.repository.HolidayRepository;
import vn.tdsoftware.hrm_backend.repository.TimeKeepingRepository;
import vn.tdsoftware.hrm_backend.repository.TimeSheetRepository;
import vn.tdsoftware.hrm_backend.service.TimeSheetService;
import vn.tdsoftware.hrm_backend.util.constant.EmployeeConstant;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class HolidayScheduler {
    private final HolidayRepository holidayRepository;
    private final TimeKeepingRepository timeKeepingRepository;
    private final EmployeeRepository employeeRepository;
    private final TimeSheetService timeSheetService;

    @Scheduled(cron = "0 0 0 * * *")
    public void addTimeKeepingHoliday () {
        long startTime = System.currentTimeMillis();
        log.info("start addTimeKeepingHoliday");
        LocalDate today = LocalDate.now();
        if (holidayRepository.existsByDateAndIsEnabled(today, true)){
            List<Employee> employees = employeeRepository.findAllByStatusAndIsEnabled(EmployeeConstant.EMPLOYEE_STATUS_WORKING, true);
            TimeSheet timeSheet = timeSheetService.getTimeSheet(today.format(DateTimeFormatter.ofPattern("yyyy-MM")));
            List<TimeKeeping> listTimeKeepingSave = new ArrayList<>();
            for (Employee employee : employees) {
                listTimeKeepingSave.add(TimeKeeping.builder()
                                .date(today)
                                .employeeId(employee.getId())
                                .workDay(1.0)
                                .timeSheetId(timeSheet.getId())
                        .build());
            }
            if (!listTimeKeepingSave.isEmpty()) {
                timeKeepingRepository.saveAll(listTimeKeepingSave);
            }
        }
        long end = System.currentTimeMillis();
        log.info("end addTimeKeepingHoliday {}", end - startTime);
    }
}
