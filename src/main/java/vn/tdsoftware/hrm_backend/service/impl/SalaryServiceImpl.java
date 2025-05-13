package vn.tdsoftware.hrm_backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dao.ContractGeneralDAO;
import vn.tdsoftware.hrm_backend.dto.contract.response.ContractGeneralDetail;
import vn.tdsoftware.hrm_backend.dto.contract.response.ContractHasAllowanceResponse;
import vn.tdsoftware.hrm_backend.dto.decision.response.RewardAndPenaltyOfEmployee;
import vn.tdsoftware.hrm_backend.dto.letter.response.OverTimeLetterOfEmployee;
import vn.tdsoftware.hrm_backend.entity.*;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.repository.*;
import vn.tdsoftware.hrm_backend.service.DecisionService;
import vn.tdsoftware.hrm_backend.service.LetterService;
import vn.tdsoftware.hrm_backend.service.SalaryService;
import vn.tdsoftware.hrm_backend.util.SalaryUtil;
import vn.tdsoftware.hrm_backend.util.TaxUtil;
import vn.tdsoftware.hrm_backend.util.constant.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalaryServiceImpl implements SalaryService {
    private final EmployeeRepository employeeRepository;
    private final TimeKeepingRepository timeKeepingRepository;
    private final HolidayRepository holidayRepository;
    private final ContractGeneralDAO contractGeneralDAO;
    private final DecisionService decisionService;
    private final LetterService letterService;
    private final FamilyRepository familyRepository;

    private final SalaryTableRepository salaryTableRepository;
    private final SalaryTableDetailRepository salaryTableDetailRepository;
    private final TimeSheetRepository timeSheetRepository;
    private final InsuranceRepository insuranceRepository;

    @Override
    public void calculationSalary() {
        Long start = System.currentTimeMillis();
        YearMonth yearMonth = YearMonth.now().minusMonths(1);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();
        final int WORK_DAY_REAL = SalaryUtil.getWorkingDaysInMonth(startDate, endDate);

        TimeSheet timeSheet = timeSheetRepository.findByYearMonthAndIsEnabled(yearMonth.toString(), true).orElse(null);
        SalaryTable salaryTable = salaryTableRepository.save(SalaryTable.builder()
                .yearMonth(yearMonth.toString())
                        .name("Bảng lương tháng " + yearMonth.getMonthValue() + "/" + yearMonth.getYear())
                .status(timeSheet == null ? TimeSheetConstant.STATUS_WAITING : TimeSheetConstant.STATUS_CHECKED)
                .build());

        if (timeSheet == null) {
            return;
        }
        List<Employee> employees = employeeRepository.findAllByStatusAndIsEnabled(EmployeeConstant.EMPLOYEE_STATUS_WORKING, true);

        Set<LocalDate> holidayDateSet = holidayRepository
                .findAllByIsEnabledAndDateBetween(true, startDate, endDate)
                .orElse(Collections.emptyList())
                .stream()
                .map(Holiday::getDate)
                .collect(Collectors.toSet());

        //danh sách đóng bảo him
        Set<Long> employeeIds = insuranceRepository
                .findAllByYearMonthAndStatusNotAndIsEnabled(yearMonth.toString(), InsuranceUtil.INSURANCE_STATUS_DECREASE, true)
                .orElse(Collections.emptyList())
                .stream()
                .map(Insurance::getEmployeeId)
                .collect(Collectors.toSet());

        List<SalaryTableDetail> response = new ArrayList<>();

        long totalAmount = 0;
        for (Employee employee : employees) {

            // 1. Hợp đồng tổng hợp
            ContractGeneralDetail contract = contractGeneralDAO.getContractGeneralDetail(employee.getId());
            int salaryGross = contract.getSalaryGross();

            // 2. Công và đi trễ
            int totalWorkDay = 0, totalGoLate = 0;
            if (!contract.isAutoCheckin()) {
                List<TimeKeeping> timeKeepingList = timeKeepingRepository
                        .findByEmployeeIdAndDateBetween(employee.getId(), startDate, endDate)
                        .orElseThrow(() -> new BusinessException(ErrorCode.TIMEKEEPING_IS_EMPTY));

                for (TimeKeeping t : timeKeepingList) {
                    if (Boolean.TRUE.equals(t.getWorkingOnHoliday())) continue;
                    if (Boolean.TRUE.equals(t.getIsLate())) totalGoLate++;
                    totalWorkDay++;
                }
            } else {
                totalWorkDay = WORK_DAY_REAL;
            }

            // 3. Lương theo ngày công
            int salaryWorkDay = (salaryGross * totalWorkDay) / WORK_DAY_REAL;

            // 4. Phụ cấp
            int totalAllowance = 0, totalPenalty = 0;
            for (ContractHasAllowanceResponse a : contract.getAllowances()) {
                if (AllowanceConstant.UNIT_DAY.equals(a.getUnit())) {
                    totalAllowance += a.getAmount() * totalWorkDay;
                } else {
                    totalAllowance += a.getAmount();
                }
            }
            totalPenalty += totalGoLate * SalaryUtil.PENALTY_GO_LATE;

            // 5. Khen thưởng & kỷ luật
            int totalReward = 0;
            List<RewardAndPenaltyOfEmployee> rewards = decisionService.getListRewardAndPenalty(employee.getId(), yearMonth);
            for (RewardAndPenaltyOfEmployee r : rewards) {
                if (r.getType() == DecisionConstant.DECISION_TYPE_PENALTY) totalPenalty += r.getAmount();
                else totalReward += r.getAmount();
            }

            // 6. Overtime
            int hourOTWeek = 0, hourOTWeekend = 0, hourOTHoliday = 0;
            List<OverTimeLetterOfEmployee> overTimes = letterService.getOverTimeLetter(employee.getId(), yearMonth);
            for (OverTimeLetterOfEmployee ot : overTimes) {
                if (holidayDateSet.contains(ot.getDateRegis())) {
                    hourOTHoliday += ot.getTotal();
                } else if (ot.getDateRegis().getDayOfWeek().getValue() >= 6) {
                    hourOTWeekend += ot.getTotal();
                } else {
                    hourOTWeek += ot.getTotal();
                }
            }

            double salaryPerHour = (double) salaryGross / (WORK_DAY_REAL * 8);
            int otSalaryWeek = (int) Math.round(hourOTWeek * 1.5 * salaryPerHour);
            int otSalaryWeekend = (int) Math.round(hourOTWeekend * 2.0 * salaryPerHour);
            int otSalaryHoliday = (int) Math.round(hourOTHoliday * 3.0 * salaryPerHour);


            // 7. Bảo hiểm
            int insurance = 0;
            if(employeeIds.contains(employee.getId())) {
                insurance = (int) ((Math.min(salaryGross, InsuranceConstant.MAX_BHXH_BHYT) *
                        (InsuranceConstant.RATIO_BHXH + InsuranceConstant.RATIO_BHYT))
                        + (Math.min(salaryGross, InsuranceConstant.MAX_BHTN) * InsuranceConstant.RATIO_BHTN));
            }

            // 8. Thuế TNCN
            int dependent = familyRepository.countByEmployeeIdAndDependentAndIsEnabled(employee.getId(), true, true);
            int salaryTaxBase = salaryGross - TaxConstant.DEDUCTION_PERSONAL - insurance - dependent * TaxConstant.DEDUCTION_DEPENDENT;
            int tax = salaryTaxBase > 0 ? TaxUtil.calculatePersonalIncomeTax(salaryTaxBase) : 0;

            // 9. Tính lương thực lãnh
            int realSalary = (int) (salaryWorkDay + totalAllowance + totalReward
                    + otSalaryWeek + otSalaryWeekend + otSalaryHoliday
                    - (totalPenalty + insurance + tax));

            totalAmount += realSalary;
            response.add(SalaryTableDetail.builder()
                    .employeeId(employee.getId())
                            .department(contract.getDepartment())
                            .jobPosition(contract.getJobPosition())
                    .salaryTableId(salaryTable.getId())
                    .workDayReal(WORK_DAY_REAL)
                    .totalWorkDay(totalWorkDay)
                    .salaryGross(salaryGross)
                    .salaryWorkDay(salaryWorkDay)
                    .totalAllowance(totalAllowance)
                    .penalty(totalPenalty)
                    .reward(totalReward)
                    .salaryOTOnWeek(otSalaryWeek)
                    .salaryOTLastWeek(otSalaryWeekend)
                    .salaryOTHoliday(otSalaryHoliday)
                    .totalInsurance(insurance)
                    .totalTax(tax)
                    .salaryReal(realSalary)
                    .build());
        }

        salaryTable.setNumberEmployee(employees.size());
        salaryTable.setTotalAmount(totalAmount);
        salaryTableRepository.save(salaryTable);
        salaryTableDetailRepository.saveAll(response);
        Long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
