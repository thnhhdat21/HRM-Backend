package vn.tdsoftware.hrm_backend.scheduled;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vn.tdsoftware.hrm_backend.entity.Employee;
import vn.tdsoftware.hrm_backend.entity.LeaveSetting;
import vn.tdsoftware.hrm_backend.entity.OnLeave;
import vn.tdsoftware.hrm_backend.repository.EmployeeRepository;
import vn.tdsoftware.hrm_backend.repository.LeaveSettingRepository;
import vn.tdsoftware.hrm_backend.repository.OnLeaveRepository;
import vn.tdsoftware.hrm_backend.util.constant.EmployeeConstant;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OnLeaveScheduler {
    private final EmployeeRepository employeeRepository;
    private final OnLeaveRepository leaveRepository;
    private final LeaveSettingRepository leaveSettingRepository;

    @Scheduled(cron = "0 0 0 1 1 *")
    public void addOnLeaveEmployee() {
        YearMonth yearMonth = YearMonth.now();
        LocalDate currentDate = LocalDate.now();
        List<Employee> employees = employeeRepository.findAllByStatusNotAndIsEnabled(EmployeeConstant.EMPLOYEE_STATUS_QUIT, true);
        List<OnLeave> onLeaves = new ArrayList<>();
        LeaveSetting leaveSetting = leaveSettingRepository.findLeaveSettingByIsEnabled(true).orElse(null);
        int maxDay = leaveSetting == null ? 0 : leaveSetting.getAnnualLeaveDays();
        int seniorDays = leaveSetting == null ? 0 : leaveSetting.getSeniorYears();
        boolean seniorEnabled = leaveSetting != null && leaveSetting.getSeniorLeaveEnabled();
        for (Employee employee : employees) {
            double seniorDay = 0.0;
            if (seniorEnabled) {
                seniorDay = calculateExtraLeaveDays(employee.getDateJoin(), currentDate, seniorDays);
            }
            onLeaves.add(OnLeave.builder()
                            .employeeId(employee.getId())
                            .regulaDay((double) maxDay)
                            .usedDay(0.0)
                            .year(yearMonth.getYear())
                            .seniorDay(seniorDay)
                    .build());
        }
        if (!onLeaves.isEmpty()) {
            leaveRepository.saveAll(onLeaves);
        }

    }

    public int calculateExtraLeaveDays(LocalDate joinedDate, LocalDate currentDate, int seniorDays) {
        if (joinedDate == null || currentDate == null || joinedDate.isAfter(currentDate)) {
            return 0;
        }

        long years = ChronoUnit.YEARS.between(joinedDate, currentDate);
        return (int) (years / seniorDays);
    }

}
