
package vn.tdsoftware.hrm_backend.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dao.LetterDAO;
import vn.tdsoftware.hrm_backend.dto.contract.request.EndContractRequest;
import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;
import vn.tdsoftware.hrm_backend.dto.letter.request.inoutandendword.InOutAndEndWorkRequest;
import vn.tdsoftware.hrm_backend.dto.letter.request.leave.LeaveLetterRequest;
import vn.tdsoftware.hrm_backend.dto.letter.request.overtime.OverTimeLetterRequest;
import vn.tdsoftware.hrm_backend.dto.letter.request.worktime.WorkTimeLetterRequest;
import vn.tdsoftware.hrm_backend.dto.letter.response.CountLetterResponse;
import vn.tdsoftware.hrm_backend.dto.letter.response.LetterResponse;
import vn.tdsoftware.hrm_backend.dto.letter.response.OverTimeLetterOfEmployee;
import vn.tdsoftware.hrm_backend.dto.letter.response.inoutandendwork.InOutAndEndWorkResponse;
import vn.tdsoftware.hrm_backend.dto.letter.response.leave.LeaveLetterResponse;
import vn.tdsoftware.hrm_backend.dto.letter.response.overtime.OverTimeLetterResponse;
import vn.tdsoftware.hrm_backend.dto.letter.response.worktime.WorkTimeLetterResponse;
import vn.tdsoftware.hrm_backend.entity.*;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.repository.*;
import vn.tdsoftware.hrm_backend.service.ContractService;
import vn.tdsoftware.hrm_backend.service.EmployeeService;
import vn.tdsoftware.hrm_backend.service.LetterService;
import vn.tdsoftware.hrm_backend.service.TimeSheetService;
import vn.tdsoftware.hrm_backend.util.LetterUtil;
import vn.tdsoftware.hrm_backend.util.PerLetterUtil;
import vn.tdsoftware.hrm_backend.util.constant.LetterConstant;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LetterServiceImpl implements LetterService {
    private final EmployeeService employeeService;
    private final LetterRepository letterRepository;
    private final LetterReasonRepository letterReasonRepository;
    private final LeaveLetterRepository leaveLetterRepository;
    private final OverTimeLetterRepository overTimeLetterRepository;
    private final WorkTimeLetterRepository workTimeLetterRepository;
    private final LetterReasonRepository reasonRepository;
    private final InOutAndEndWorkLetterRepository inOutAndEndWorkLetterRepository;
    private final LetterDAO letterDAO;
    private final ContractService contractService;
    private final ContractGeneralRepository contractGeneralRepository;
    private final TimeKeepingRepository timeKeepingRepository;
    private final TimeKeepingHasLetterRepository timeKeepingHasLetterRepository;
    private final TimeSheetService timeSheetService;
    private final OnLeaveRepository onLeaveRepository;
    private final PerLetterUtil perLetterUtil;

    @Override
    public List<LetterResponse> getListLetter(EmployeeFilter filter) {
        perLetterUtil.checkSameDepartmentByFilter(filter);
        List<LetterResponse> response = letterDAO.getListLetter(filter);
        if (response.isEmpty()) {
            throw new BusinessException(ErrorCode.LETTER_IS_EMPTY);
        }
        return response;
    }

    @Override
    public List<CountLetterResponse> getCountLetter(EmployeeFilter filter) {
        perLetterUtil.checkSameDepartmentByFilter(filter);
        List<CountLetterResponse> responses = letterDAO.getCountLetter(filter);
        if (responses.isEmpty()) {
            throw new BusinessException(ErrorCode.LETTER_IS_EMPTY);
        }
        return responses;
    }

    @Override
    @Transactional
    public void updateLeaveLetter(LeaveLetterRequest request) {
        perLetterUtil.checkSameEmployee(request.getEmployeeId());
        perLetterUtil.checkManageOrCreateLetter(request.getLetterId());
        employeeService.checkEmployeeValidator(request.getEmployeeId());
        validator(request.getDateStart(), request.getDateEnd(), request.getTotal());

        Letter letter = saveLetter(request.getLetterId(),
                request.getLetterReasonId(),
                request.getEmployeeId(),
                request.getDescription());

        LeaveLetter leaveLetter = leaveLetterRepository.findByLetterIdAndIsEnabled(letter.getId(), true).orElse(new LeaveLetter());
        leaveLetter.setLetterId(letter.getId());
        leaveLetter.setDateStart(request.getDateStart());
        leaveLetter.setDateEnd(request.getDateEnd());
        leaveLetter.setTotal(request.getTotal());
        leaveLetterRepository.save(leaveLetter);

        if (request.getLetterId() == 0 && request.getDateStart().toLocalDate().equals(LocalDate.now())) {
            addLetterToTimeKeeping(request.getEmployeeId(), request.getDateStart().toLocalDate(), letter.getId());
        }
    }

    @Override
    @Transactional
    public void updateOverTimeLetter(OverTimeLetterRequest request) {
        perLetterUtil.checkSameEmployee(request.getEmployeeId());
        perLetterUtil.checkManageOrCreateLetter(request.getLetterId());
        employeeService.checkEmployeeValidator(request.getEmployeeId());
        validator(request.getTimeStart(), request.getTimeEnd(), request.getTotal());

        Letter letter = saveLetter(request.getLetterId(),
                request.getLetterReasonId(),
                request.getEmployeeId(),
                request.getDescription());

        OverTimeLetter overTimeLetter = overTimeLetterRepository.findByLetterIdAndIsEnabled(letter.getId(), true)
                .orElse(new OverTimeLetter());
        overTimeLetter.setDateRegis(request.getDateRegis());
        overTimeLetter.setLetterId(letter.getId());
        overTimeLetter.setTimeStart(request.getTimeStart());
        overTimeLetter.setTimeEnd(request.getTimeEnd());
        overTimeLetter.setTotal(request.getTotal());
        overTimeLetter.setIsNextDay(request.getIsNextDay());
        overTimeLetter.setDescription(request.getDescription());
        overTimeLetterRepository.save(overTimeLetter);

        if (request.getLetterId() == 0 && !request.getDateRegis().isAfter(LocalDate.now())) {
            addLetterToTimeKeeping(request.getEmployeeId(), request.getDateRegis(), letter.getId());
        }
    }

    @Override
    @Transactional
    public void updateWorkTimeLetter(WorkTimeLetterRequest request) {
        perLetterUtil.checkSameEmployee(request.getEmployeeId());
        perLetterUtil.checkManageOrCreateLetter(request.getLetterId());
        employeeService.checkEmployeeValidator(request.getEmployeeId());
        validator(request.getDateStart().atStartOfDay(), request.getDateEnd().atStartOfDay(), null);

        Letter letter = saveLetter(request.getLetterId(),
                request.getLetterReasonId(),
                request.getEmployeeId(),
                request.getDescription());

        WorkTimeLetter workTimeLetter = workTimeLetterRepository.findByLetterIdAndIsEnabled(letter.getId(), true)
                .orElse(new WorkTimeLetter());
        workTimeLetter.setLetterId(letter.getId());
        workTimeLetter.setDateStart(request.getDateStart());
        workTimeLetter.setDateEnd(request.getDateEnd());
        workTimeLetterRepository.save(workTimeLetter);

    }

    @Override
    @Transactional
    public void updateInOutAndEndWorkLetter(InOutAndEndWorkRequest request) {
        perLetterUtil.checkSameEmployee(request.getEmployeeId());
        perLetterUtil.checkManageOrCreateLetter(request.getLetterId());
        employeeService.checkEmployeeValidator(request.getEmployeeId());
        Letter letter =  saveLetter(request.getLetterId(),
                request.getLetterReasonId(),
                request.getEmployeeId(),
                request.getDescription());

        InOutAndEndWorkLetter inOutAndEndWorkLetter = inOutAndEndWorkLetterRepository.findByLetterIdAndIsEnabled(letter.getId(), true)
                        .orElse(new InOutAndEndWorkLetter());

        inOutAndEndWorkLetter.setLetterId(letter.getId());
        inOutAndEndWorkLetter.setDateRegis(request.getDateRegis());
        inOutAndEndWorkLetterRepository.save(inOutAndEndWorkLetter);
        // Khi tạo mới giải trình
        if (request.getLetterId() == 0 && request.getType() == LetterConstant.LETTER_TYPE_INOUT) {
            TimeKeeping timeKeeping = timeKeepingRepository.findByEmployeeIdAndDate(request.getEmployeeId(), request.getDateRegis())
                    .orElseThrow(() -> new BusinessException(ErrorCode.TIMEKEEPING_IS_EMPTY));
            timeKeepingHasLetterRepository.save(TimeKeepingHasLetter.builder()
                            .timeKeepingId(timeKeeping.getId())
                            .letterId(letter.getId())
                    .build());
        }
    }

    @Override
    public void deleteLetter(long letterId) {
        Letter letter = letterRepository.findByIdAndIsEnabled(letterId, true)
                .orElseThrow(() -> new BusinessException(ErrorCode.LETTER_IS_EMPTY));
        perLetterUtil.checkManageSameDepartmentByEmployeeId(letter.getEmployeeId());

        LetterReason letterReason = reasonRepository.findByIdAndIsEnabled(letter.getReasonId(), true)
                .orElseThrow(() -> new BusinessException(ErrorCode.REASON_IS_EMPTY));

        switch (letterReason.getLetterTypeId()) {
            case LetterConstant.LETTER_TYPE_LEAVE -> deleteLeaveLetter(letter);

            case LetterConstant.LETTER_TYPE_OVERTIME ->  deleteOverTimeLetter(letter);

            case LetterConstant.LETTER_TYPE_WORKTIME -> deleteWorkTimeLetter(letter);

            case LetterConstant.LETTER_TYPE_INOUT,
                 LetterConstant.LETTER_TYPE_END_WORK -> deleteInOutAndEndWorkLetter(letter);
        }
    }

    @Override
    public void noApprovalLetter(long letterId) {
        Letter letter = letterRepository.findByIdAndIsEnabled(letterId, true)
                .orElseThrow(() -> new BusinessException(ErrorCode.LETTER_IS_EMPTY));
        perLetterUtil.checkApproveSameDepartmentByEmployeeId(letter.getEmployeeId());
        letter.setState(LetterConstant.LETTER_STATE_NO_APPROVE);
        letterRepository.save(letter);
    }

    @Override
    public Object getLetter(long letterId) {
        Letter letter = letterRepository.findByIdAndIsEnabled(letterId, true)
                .orElseThrow(() -> new BusinessException(ErrorCode.LETTER_IS_EMPTY));
        perLetterUtil.checkWatchSameDepartmentByEmployeeId(letter.getEmployeeId());
        LetterReason letterReason = reasonRepository.findByIdAndIsEnabled(letter.getReasonId(), true)
                .orElseThrow(() -> new BusinessException(ErrorCode.REASON_IS_EMPTY));

        switch (letterReason.getLetterTypeId()) {
            case LetterConstant.LETTER_TYPE_LEAVE -> {
                return getLeaveLetter(letter);
            }

            case LetterConstant.LETTER_TYPE_OVERTIME ->  {
                return getOverTimeLetter(letter);
            }

            case LetterConstant.LETTER_TYPE_WORKTIME -> {
                return getWorkTimeLetter(letter);
            }

            case LetterConstant.LETTER_TYPE_INOUT,
                 LetterConstant.LETTER_TYPE_END_WORK -> {
                return getInOutAndEndWorkLetter(letter);
            }
            default -> throw new BusinessException(ErrorCode.LETTER_IS_EMPTY);
        }
    }

    @Override
    @Transactional
    public void approveLetter(long letterId) {
        Letter letter = letterRepository.findByIdAndIsEnabled(letterId, true)
                .orElseThrow(() -> new BusinessException(ErrorCode.LETTER_IS_EMPTY));
        perLetterUtil.checkApproveSameDepartmentByEmployeeId(letter.getEmployeeId());
        LetterReason letterReason = letterReasonRepository.findByIdAndIsEnabled(letter.getReasonId(), true)
                .orElseThrow(() -> new BusinessException(ErrorCode.REASON_IS_EMPTY));

        switch (letterReason.getLetterTypeId()) {
            case LetterConstant.LETTER_TYPE_OVERTIME,
                 LetterConstant.LETTER_TYPE_WORKTIME-> {
                letter.setState(LetterConstant.LETTER_STATE_APPROVE);
                letterRepository.save(letter);
            }
            case LetterConstant.LETTER_TYPE_LEAVE -> approveLeaveLetter(letter, letterReason);

            case  LetterConstant.LETTER_TYPE_INOUT->  approveInOutLetter(letter);

            case LetterConstant.LETTER_TYPE_END_WORK -> approveEndWorkLetter(letter, letterReason);

        }
    }

    @Override
    public List<OverTimeLetterOfEmployee> getOverTimeLetter(long employeeId, YearMonth yearMonth) {
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();
        return letterDAO.getOverTimeLetter(employeeId, startDate, endDate);
    }

    private void approveEndWorkLetter(Letter letter, LetterReason letterReason) {
        letter.setState(LetterConstant.LETTER_STATE_APPROVE);
        letterRepository.save(letter);

        InOutAndEndWorkLetter inOutAndEndWorkLetter = inOutAndEndWorkLetterRepository.findByLetterIdAndIsEnabled(letter.getId(), true)
                .orElseThrow(() -> new BusinessException(ErrorCode.LETTER_IS_EMPTY));

        if (!inOutAndEndWorkLetter.getDateRegis().isAfter(LocalDate.now())) {
            ContractGeneral contractGeneral = contractGeneralRepository.findByEmployeeId(letter.getEmployeeId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.CONTRACT_IS_EMPTY));

            contractService.endContract(EndContractRequest.builder()
                    .contractId(contractGeneral.getContractOriginal())
                    .dateLiquidation(inOutAndEndWorkLetter.getDateRegis())
                    .reasonLiquidation(letterReason.getReason())
                    .build());
        }
    }

    private void approveLeaveLetter(Letter letter, LetterReason letterReason) {
        letter.setState(LetterConstant.LETTER_STATE_APPROVE);
        letterRepository.save(letter);
        LeaveLetter leaveLetter = leaveLetterRepository.findByLetterIdAndIsEnabled(letter.getId(), true).orElseThrow(
                () -> new BusinessException(ErrorCode.LETTER_IS_EMPTY));

        if (!leaveLetter.getDateStart().toLocalDate().isAfter(LocalDate.now())) {
            LocalDate end =  leaveLetter.getDateEnd().toLocalDate().isAfter(LocalDate.now()) ? LocalDate.now() : leaveLetter.getDateEnd().toLocalDate();
            LocalDate start = leaveLetter.getDateStart().toLocalDate();
            TimeSheet timeSheet = timeSheetService.getTimeSheet(start.format(DateTimeFormatter.ofPattern("yyyy-MM")));
            while(!start.isAfter(end)) {
                TimeKeeping timeKeeping = timeKeepingRepository.findByEmployeeIdAndDate(letter.getEmployeeId(), start).orElse(new TimeKeeping());
                timeKeeping.setEmployeeId(letter.getEmployeeId());
                timeKeeping.setDate(start);
                timeKeeping.setTimeSheetId(timeSheet.getId());
                if (letterReason.getWorkDayEnabled()) {
                    double workDay = LetterUtil.workDayOnLeave(start, leaveLetter.getDateStart(), leaveLetter.getDateEnd());
                    timeKeeping.setWorkDay(timeKeeping.getWorkDay() == null ? workDay : timeKeeping.getWorkDay() + workDay);
                }
                TimeKeeping timeKeepingSaved = timeKeepingRepository.save(timeKeeping);
                timeKeepingHasLetterRepository.save(TimeKeepingHasLetter.builder()
                        .timeKeepingId(timeKeepingSaved.getId())
                        .letterId(letter.getId())
                        .build());

                start = start.plusDays(1);
            }
        }

        if (letterReason.getWorkDayEnabled()) {
            OnLeave onLeave = onLeaveRepository.findByEmployeeIdAndYearAndIsEnabled(letter.getEmployeeId(), leaveLetter.getDateStart().toLocalDate().getYear(), true)
                    .orElseThrow(() -> new BusinessException(ErrorCode.ON_LEAVE_IS_EMPTY));
            onLeave.setUsedDay(onLeave.getUsedDay() + leaveLetter.getTotal());
            onLeaveRepository.save(onLeave);
        }
    }

    private void approveInOutLetter(Letter letter) {
        letter.setState(LetterConstant.LETTER_STATE_APPROVE);
        letterRepository.save(letter);

        InOutAndEndWorkLetter inOutAndEndWorkLetter = inOutAndEndWorkLetterRepository.findByLetterIdAndIsEnabled(letter.getId(), true)
                .orElseThrow(() -> new BusinessException(ErrorCode.LETTER_IS_EMPTY));

        TimeKeeping timeKeeping = timeKeepingRepository.findByEmployeeIdAndDate(letter.getEmployeeId(), inOutAndEndWorkLetter.getDateRegis())
                .orElseThrow(() -> new BusinessException(ErrorCode.TIMEKEEPING_IS_EMPTY));
        timeKeeping.setIsLate(false);
        timeKeepingRepository.save(timeKeeping);
    }

    private LeaveLetterResponse getLeaveLetter(Letter letter) {
        LeaveLetter leaveLetter = leaveLetterRepository.findByLetterIdAndIsEnabled(letter.getId(), true)
                .orElseThrow(() -> new BusinessException(ErrorCode.LETTER_IS_EMPTY));
        LetterReason letterReason = letterReasonRepository.findByIdAndIsEnabled(letter.getReasonId(), true)
                .orElseThrow();
        return LeaveLetterResponse.builder()
                .letterId(letter.getId())
                .employeeId(letter.getEmployeeId())
                .letterReasonId(letter.getReasonId())
                .letterReason(letterReason.getReason())
                .letterType(letterReason.getLetterTypeId())
                .letterState(letter.getState())
                .dateStart(leaveLetter.getDateStart())
                .dateEnd(leaveLetter.getDateEnd())
                .total(leaveLetter.getTotal())
                .description(letter.getDescription())
                .build();
    }

    private OverTimeLetterResponse getOverTimeLetter (Letter letter) {
        OverTimeLetter overTimeLetter = overTimeLetterRepository.findByLetterIdAndIsEnabled(letter.getId(), true)
                .orElseThrow(() -> new BusinessException(ErrorCode.LETTER_IS_EMPTY));
        LetterReason letterReason = letterReasonRepository.findByIdAndIsEnabled(letter.getReasonId(), true)
                .orElseThrow();
        return OverTimeLetterResponse.builder()
                .dateRegis(overTimeLetter.getDateRegis())
                .letterId(letter.getId())
                .isNextDay(overTimeLetter.getIsNextDay())
                .employeeId(letter.getEmployeeId())
                .letterReasonId(letter.getReasonId())
                .letterReason(letterReason.getReason())
                .letterType(letterReason.getLetterTypeId())
                .letterState(letter.getState())
                .timeStart(overTimeLetter.getTimeStart())
                .timeEnd(overTimeLetter.getTimeEnd())
                .total(overTimeLetter.getTotal())
                .description(letter.getDescription())
                .build();
    }

    private WorkTimeLetterResponse  getWorkTimeLetter (Letter letter) {
        WorkTimeLetter workTimeLetter = workTimeLetterRepository.findByLetterIdAndIsEnabled(letter.getId(), true)
                .orElseThrow(() -> new BusinessException(ErrorCode.LETTER_IS_EMPTY));
        LetterReason letterReason = letterReasonRepository.findByIdAndIsEnabled(letter.getReasonId(), true)
                .orElseThrow(() -> new BusinessException(ErrorCode.LIST_LETTER_REASON_IS_EMPTY));
        return WorkTimeLetterResponse.builder()
                .letterId(letter.getId())
                .employeeId(letter.getEmployeeId())
                .letterReasonId(letter.getReasonId())
                .letterReason(letterReason.getReason())
                .letterType(letterReason.getLetterTypeId())
                .letterState(letter.getState())
                .dateStart(workTimeLetter.getDateStart())
                .dateEnd(workTimeLetter.getDateEnd())
                .goLate(letterReason.getGoLate())
                .backEarly(letterReason.getBackEarly())
                .description(letter.getDescription())
                .build();
    }

    private InOutAndEndWorkResponse getInOutAndEndWorkLetter (Letter letter) {
        InOutAndEndWorkLetter inOutAndEndWorkLetter = inOutAndEndWorkLetterRepository.findByLetterIdAndIsEnabled(letter.getId(), true)
                .orElseThrow(() -> new BusinessException(ErrorCode.LETTER_IS_EMPTY));
        LetterReason letterReason = letterReasonRepository.findByIdAndIsEnabled(letter.getReasonId(), true)
                .orElseThrow();
        return InOutAndEndWorkResponse.builder()
                .letterId(letter.getId())
                .employeeId(letter.getEmployeeId())
                .dateRegis(inOutAndEndWorkLetter.getDateRegis())
                .letterReasonId(letter.getReasonId())
                .letterReason(letterReason.getReason())
                .letterType(letterReason.getLetterTypeId())
                .letterState(letter.getState())
                .description(letter.getDescription())
                .build();
    }




    private void deleteLeaveLetter(Letter letter) {
        letter.setEnabled(false);
        LeaveLetter leaveLetter = leaveLetterRepository.findByLetterIdAndIsEnabled(letter.getId(), true)
                .orElseThrow(() -> new BusinessException(ErrorCode.LETTER_IS_EMPTY));
        leaveLetter.setEnabled(false);
        letterRepository.save(letter);
        leaveLetterRepository.save(leaveLetter);
    }

    private void deleteOverTimeLetter(Letter letter) {
        letter.setEnabled(false);
        OverTimeLetter overTimeLetter = overTimeLetterRepository.findByLetterIdAndIsEnabled(letter.getId(), true)
                .orElseThrow(() -> new BusinessException(ErrorCode.LETTER_IS_EMPTY));
        overTimeLetter.setEnabled(false);
        letterRepository.save(letter);
        overTimeLetterRepository.save(overTimeLetter);
    }

    private void deleteWorkTimeLetter(Letter letter) {
        letter.setEnabled(false);
        WorkTimeLetter workTimeLetter = workTimeLetterRepository.findByLetterIdAndIsEnabled(letter.getId(), true)
                .orElseThrow(() -> new BusinessException(ErrorCode.LETTER_IS_EMPTY));
        workTimeLetter.setEnabled(false);
        letterRepository.save(letter);
        workTimeLetterRepository.save(workTimeLetter);
    }


    private void deleteInOutAndEndWorkLetter(Letter letter) {
        letter.setEnabled(false);

        InOutAndEndWorkLetter inOutAndEndWorkLetter = inOutAndEndWorkLetterRepository.findByLetterIdAndIsEnabled(letter.getId(), true)
                .orElseThrow(() -> new BusinessException(ErrorCode.LETTER_IS_EMPTY));
        inOutAndEndWorkLetter.setEnabled(false);
        letterRepository.save(letter);
        inOutAndEndWorkLetterRepository.save(inOutAndEndWorkLetter);
    }

    private Letter saveLetter(long letterId,
                              long letterReasonId,
                              long employeeId,
                              String description) {
        Letter letter = letterRepository.findByIdAndIsEnabled(letterId, true)
                .orElse(new Letter());

        LetterReason letterReason = letterReasonRepository.findByIdAndIsEnabled(letterReasonId, true)
                .orElseThrow(() -> new BusinessException(ErrorCode.LIST_LETTER_REASON_IS_EMPTY));

        letter.setEmployeeId(employeeId);
        letter.setReasonId(letterReason.getId());
        letter.setState(LetterConstant.LETTER_STATE_WAITING);
        letter.setDescription(description);
        return letterRepository.save(letter);
    }

    private void validator (LocalDateTime dateStart, LocalDateTime dateEnd, Double total) {
//        if (dateStart.isBefore(LocalDateTime.now())) {
//            throw new BusinessException(ErrorCode.DATE_START_IN_VALID);
//        } else if (dateEnd != null && dateEnd.isBefore(LocalDateTime.now())) {
//            throw new BusinessException(ErrorCode.DATE_END_IN_VALID);
//        } else if (dateStart.isAfter(dateEnd)) {
//            throw new BusinessException(ErrorCode.DATE_IN_VALID);
//        } else if (total != null && total < 0) {
//            throw new BusinessException(ErrorCode.TOTAL_IN_VALID);
//        }
    }

    private void validator(LocalTime timeStart, LocalTime timeEnd, double total) {
        if (timeStart == null) {
            throw new BusinessException(ErrorCode.DATE_START_IN_VALID);
        } else if (timeEnd == null) {
            throw new BusinessException(ErrorCode.DATE_END_IN_VALID);
        }  else if (total < 0) {
            throw new BusinessException(ErrorCode.TOTAL_IN_VALID);
        }
    }

    public void addLetterToTimeKeeping(long employeeId, LocalDate date, long letterId) {
        TimeKeeping timeKeeping = timeKeepingRepository.findByEmployeeIdAndDate(employeeId,date)
                .orElse(new TimeKeeping());

        if (timeKeeping.getId() == null) {
            timeKeeping.setEmployeeId(employeeId);
            timeKeeping = timeKeepingRepository.save(timeKeeping);
        }

        timeKeepingHasLetterRepository.save(TimeKeepingHasLetter.builder()
                .timeKeepingId(timeKeeping.getId())
                .letterId(letterId)
                .build());
    }


}
