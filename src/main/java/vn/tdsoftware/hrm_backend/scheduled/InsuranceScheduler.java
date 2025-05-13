package vn.tdsoftware.hrm_backend.scheduled;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vn.tdsoftware.hrm_backend.dao.LetterDAO;
import vn.tdsoftware.hrm_backend.dao.TimeKeepingDAO;
import vn.tdsoftware.hrm_backend.dto.letter.response.LeaveLetterUnpaidSalary;
import vn.tdsoftware.hrm_backend.dto.timekeeping.response.EmployeeCountTimeKeeping;
import vn.tdsoftware.hrm_backend.entity.Employee;
import vn.tdsoftware.hrm_backend.entity.Holiday;
import vn.tdsoftware.hrm_backend.entity.Insurance;
import vn.tdsoftware.hrm_backend.entity.InsuranceSetting;
import vn.tdsoftware.hrm_backend.mapper.response.timekeeping.EmployeeCountTimeKeepingMapper;
import vn.tdsoftware.hrm_backend.repository.EmployeeRepository;
import vn.tdsoftware.hrm_backend.repository.InsuranceRepository;
import vn.tdsoftware.hrm_backend.repository.InsuranceSettingRepository;
import vn.tdsoftware.hrm_backend.util.SalaryUtil;
import vn.tdsoftware.hrm_backend.util.constant.EmployeeConstant;
import vn.tdsoftware.hrm_backend.util.constant.InsuranceUtil;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class InsuranceScheduler {
    private final InsuranceRepository insuranceRepository;
    private final InsuranceSettingRepository insuranceSettingRepository;
    private final EmployeeRepository employeeRepository;
    private final TimeKeepingDAO timeKeepingDAO;
    private final LetterDAO letterDAO;

    @Scheduled(cron = "0 0 0 1 * *")
    public void addEmployeeToInsurance() {
        List<Employee> employees = employeeRepository.findAllByStatusAndIsEnabled(EmployeeConstant.EMPLOYEE_STATUS_WORKING, true);
        List<Insurance> insurances = new ArrayList<>();
        YearMonth yearMonth = YearMonth.now();
        for (Employee employee : employees) {
            insurances.add(Insurance.builder()
                            .employeeId(employee.getId())
                            .status(InsuranceUtil.INSURANCE_STATUS_NORMAL)
                            .yearMonth(yearMonth.toString())
                    .build());
        }
        if (!insurances.isEmpty()) {
            insuranceRepository.saveAll(insurances);
        }
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void addInsuranceSetting() {
        LocalDate today = LocalDate.now();
        YearMonth yearMonth = YearMonth.now();
        if (today.getDayOfMonth() > 14) {
            yearMonth = yearMonth.plusMonths(1);
        }
        InsuranceSetting insuranceSetting = insuranceSettingRepository.findInsuranceSettingByIsEnabled(true).orElse(null);
        if (insuranceSetting != null) {
            List<Insurance> insurances = new ArrayList<>();
            //Tang
            boolean signContract = insuranceSetting.getSingedContract();
            boolean returnedLeaveTmp = insuranceSetting.getReturnedLeaveTmp();

            //Giam
            boolean leaveTmp = insuranceSetting.getLeaveTmp();
            boolean unpaidLeave = insuranceSetting.getUnpaidLeave();

            if (signContract) {
                Employee employee = employeeRepository.findByDateJoinAndIsEnabled(today, true).orElse(null);
                if (employee != null) {
                    insurances.add(Insurance.builder()
                                    .employeeId(employee.getId())
                                    .status(InsuranceUtil.INSURANCE_STATUS_INCREASE)
                                    .yearMonth(yearMonth.toString())
                            .build());
                }
            }

            if (returnedLeaveTmp) {
                List<LeaveLetterUnpaidSalary> unpaidSalaryList = letterDAO.getListLeaveLetterUnpaidSalary(today, 2);
                if (!unpaidSalaryList.isEmpty()) {
                    for (LeaveLetterUnpaidSalary unpaidSalary : unpaidSalaryList) {
                        Insurance insurance = insuranceRepository.findInsuranceByEmployeeIdAndYearMonthAndIsEnabled(unpaidSalary.getEmployeeId(), yearMonth.toString(), true).orElse(
                                new Insurance());
                        insurance.setEmployeeId(unpaidSalary.getEmployeeId());
                        insurance.setYearMonth(yearMonth.toString());
                        insurance.setStatus(InsuranceUtil.INSURANCE_STATUS_INCREASE);
                        insurances.add(insurance);
                    }
                }
            }

            if (leaveTmp) {
                List<LeaveLetterUnpaidSalary> unpaidSalaryList = letterDAO.getListLeaveLetterUnpaidSalary(today, 1);
                if (!unpaidSalaryList.isEmpty()) {
                    for (LeaveLetterUnpaidSalary unpaidSalary : unpaidSalaryList) {
                        Insurance insurance = insuranceRepository.findInsuranceByEmployeeIdAndYearMonthAndIsEnabled(unpaidSalary.getEmployeeId(), yearMonth.toString(), true).orElse(
                                new Insurance());
                        insurance.setEmployeeId(unpaidSalary.getEmployeeId());
                        insurance.setYearMonth(yearMonth.toString());
                        insurance.setStatus(InsuranceUtil.INSURANCE_STATUS_DECREASE);
                        insurances.add(insurance);
                    }
                }
            }

            if (unpaidLeave) {
                YearMonth yearMonthLeave = YearMonth.now();
                LocalDate startDate = yearMonthLeave.atDay(1);
                final int WORK_DAY_REAL = SalaryUtil.getWorkingDaysInMonth(startDate, today);
                if (WORK_DAY_REAL > 14) {
                    List<EmployeeCountTimeKeeping> list = timeKeepingDAO.getListEmployeeTimeKeeping(WORK_DAY_REAL, yearMonthLeave.toString());
                    Set<Long> employeeIds = insuranceRepository
                            .findAllByYearMonthAndStatusAndIsEnabled(yearMonthLeave.toString(), InsuranceUtil.INSURANCE_STATUS_DECREASE, true)
                            .orElse(Collections.emptyList())
                            .stream()
                            .map(Insurance::getEmployeeId)
                            .collect(Collectors.toSet());
                    if (!list.isEmpty()) {
                        for (EmployeeCountTimeKeeping employeeCountTimeKeeping : list) {
                            if (employeeIds.contains(employeeCountTimeKeeping.getEmployeeId()))
                                continue;
                            Insurance insurance = insuranceRepository.findInsuranceByEmployeeIdAndYearMonthAndIsEnabled(employeeCountTimeKeeping.getEmployeeId(), yearMonthLeave.toString(), true).orElse(
                                    new Insurance());
                            insurance.setEmployeeId(employeeCountTimeKeeping.getEmployeeId());
                            insurance.setYearMonth(yearMonthLeave.toString());
                            insurance.setStatus(InsuranceUtil.INSURANCE_STATUS_DECREASE);
                            insurances.add(insurance);
                        }
                    }
                }
            }
        }
    }
}
