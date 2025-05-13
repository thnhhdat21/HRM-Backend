package vn.tdsoftware.hrm_backend.scheduled;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vn.tdsoftware.hrm_backend.service.SalaryService;

@Component
@RequiredArgsConstructor
public class SalaryScheduler {
    private final SalaryService salaryService;

    @Scheduled(cron = "0 0 0 1 * *")
    public void calculateSalary() {
        salaryService.calculationSalary();
    }

}
