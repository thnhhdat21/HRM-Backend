package vn.tdsoftware.hrm_backend.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dao.EmployeeDAO;
import vn.tdsoftware.hrm_backend.dao.LetterDAO;
import vn.tdsoftware.hrm_backend.dto.account.response.CurrentAccountDTO;
import vn.tdsoftware.hrm_backend.dto.employee.request.*;
import vn.tdsoftware.hrm_backend.dto.employee.response.*;
import vn.tdsoftware.hrm_backend.dto.letter.response.WorkTimeEmployee;
import vn.tdsoftware.hrm_backend.dto.letter.response.leave.LeaveLetterResponse;
import vn.tdsoftware.hrm_backend.dto.letter.response.overtime.OverTimeLetterResponse;
import vn.tdsoftware.hrm_backend.dto.salarytable.response.SalaryDetailResponse;
import vn.tdsoftware.hrm_backend.dto.salarytable.response.SalaryMonth;
import vn.tdsoftware.hrm_backend.dto.salarytable.response.TaxDTO;
import vn.tdsoftware.hrm_backend.dto.salarytable.response.TaxResponse;
import vn.tdsoftware.hrm_backend.entity.*;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.ResumeMapper;
import vn.tdsoftware.hrm_backend.repository.*;
import vn.tdsoftware.hrm_backend.service.*;
import vn.tdsoftware.hrm_backend.common.service.MinIOService;
import vn.tdsoftware.hrm_backend.util.EmployeeUtil;
import vn.tdsoftware.hrm_backend.util.PerEmployeeUtil;
import vn.tdsoftware.hrm_backend.util.SalaryUtil;
import vn.tdsoftware.hrm_backend.util.constant.AddressConstant;
import vn.tdsoftware.hrm_backend.util.constant.EmployeeConstant;
import vn.tdsoftware.hrm_backend.util.constant.FileConstant;
import vn.tdsoftware.hrm_backend.util.constant.TimeSheetConstant;


import java.time.*;
import java.util.*;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeDAO employeeDAO;
    private final EmployeeRepository employeeRepository;
    private final AddressRepository addressRepository;
    private final AccountBankRepository accountBankRepository;
    private final MinIOService minIOService;
    private final LetterDAO letterDAO;
    private final TimeKeepingRepository timeKeepingRepository;
    private final TimeSheetRepository timeSheetRepository;
    private final HolidayRepository holidayRepository;
    private final TimeKeepingHasLetterRepository timeKeepingHasLetterRepository;
    private final EmployeeUtil employeeUtil;
    private final EducationService educationService;
    private final FamilyService familyService;
    private final ContractService contractService;
    private final AccountService accountService;
    private final PerEmployeeUtil perEmployeeUtil;
    private final SalaryTableDetailRepository salaryTableDetailRepository;

    @Override
    public List<EmployeeOfDepartment> getListEmployeeFilter(EmployeeFilter filter) {
        perEmployeeUtil.checkSameDepartmentByFilter(filter);
        List<EmployeeResponse> response = employeeDAO.getListEmployeeFilter(filter);
        Map<String, List<EmployeeResponse>> mapDepartment = new HashMap<>();
        List<EmployeeOfDepartment> listResponse = new ArrayList<>();
        for (EmployeeResponse employeeResponse : response) {
            if (!mapDepartment.containsKey(employeeResponse.getDepartment())) {
                List<EmployeeResponse> employeeResponseList = new ArrayList<>();
                listResponse.add(EmployeeOfDepartment.builder()
                                .department(employeeResponse.getDepartment())
                                .employees(employeeResponseList)
                        .build());
                mapDepartment.put(employeeResponse.getDepartment(),employeeResponseList);
            }
            mapDepartment.get(employeeResponse.getDepartment()).add(employeeResponse);
        }
        if (listResponse.isEmpty()) {
            throw new BusinessException(ErrorCode.LIST_EMPLOYEE_IS_EMPTY);
        }
        return listResponse;
    }

    @Override
    public List<EmployeeTypeCount> getCountEmployeeFilter(EmployeeFilter filter) {
        perEmployeeUtil.checkSameDepartmentByFilter(filter);
        List<EmployeeTypeCount> response = employeeDAO.getCountEmployeeFilter(filter);
        if (response.isEmpty()) {
            throw new BusinessException(ErrorCode.TYPE_INVALID);
        }
        return response;
    }

    @Override
    public ResumeProfileResponse getResumeProfile(long employeeId) {
        perEmployeeUtil.checkWatchSameDepartmentByEmployeeId(employeeId);
        checkEmployeeValidator(employeeId);
        ResumeProfileResponse response = employeeDAO.getResumeProfile(employeeId);
        if (response == null) {
            throw new  BusinessException(ErrorCode.EMPLOYEE_IS_EMPTY);
        }
        return response;
    }

    @Override
    @Transactional
    public void updateResumeProfile(ResumeProfileRequest request) {
        perEmployeeUtil.checkUpdateSameDepartmentByEmployeeId(request.getId());
        Employee employeeEntity =  new Employee();
        if (request.getId() != null) {
            employeeEntity = checkEmployeeValidator(request.getId());
        }
        updateEmployee(employeeEntity, request);
    }

    @Override
    public void updateIdentityCard(IdentityCartRequest request) {
        perEmployeeUtil.checkUpdateSameDepartmentByEmployeeId(request.getEmployeeId());
        Employee employee = checkEmployeeValidator(request.getEmployeeId());
        if(request.getFontIdCard() != null) {
            String fileName = "/" + employee.getEmployeeCode() + "/" + request.getFontIdCard().getOriginalFilename();
            employee.setFontImageCCCD(fileName);
            minIOService.uploadFile(fileName, request.getFontIdCard());
        }
        if(request.getBackIdCard() != null) {
            String fileName = "/" + employee.getEmployeeCode() + "/" + request.getBackIdCard().getOriginalFilename();
            employee.setBackImageCCCD(fileName);
            minIOService.uploadFile(fileName, request.getBackIdCard());
        }
        employeeRepository.save(employee);
    }

    @Override
    public Employee checkEmployeeValidator(long id) {
        return employeeRepository.findByIdAndIsEnabled(id, true).orElseThrow(
                () -> new BusinessException(ErrorCode.EMPLOYEE_IS_EMPTY)
        );
    }

    @Override
    public List<EmployeeNameAndCode> getListEmployee() {
        List<EmployeeNameAndCode> response = employeeDAO.getListEmployee();
        if (response.isEmpty()) {
            throw new BusinessException(ErrorCode.EMPLOYEE_IS_EMPTY);
        }
        return response;
    }


    @Override
    public EmployeeTimeSheetResponse getTimeSheetEmployee(EmployeeTimeSheetRequest request) {
        YearMonth yearMonth = YearMonth.from(request.getYearMonth());
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.equals(YearMonth.from(LocalDate.now()))
                ? LocalDate.now()
                : yearMonth.atEndOfMonth();
        final int WORK_DAY_REAL = SalaryUtil.getWorkingDaysInMonth(startDate, endDate);
        List<EmployeeTimeSheet> list = employeeDAO.getTimeSheetEmployee(request.getEmployeeId(), startDate, endDate);
        List<EmployeeTimeSheet> result = new ArrayList<>();
        int index = 0;
        int totalWorkDay = 0;
        while (index < list.size()) {
            EmployeeTimeSheet employeeTimeSheet = list.get(index);
            if (employeeTimeSheet.getDateWorking() != null) {
                while( !startDate.isAfter(endDate)) {
                    if(startDate.equals(employeeTimeSheet.getDateWorking())) {
                        result.add(employeeTimeSheet);
                        totalWorkDay++;
                        startDate = startDate.plusDays(1);
                        break;
                    } else
                        result.add(EmployeeTimeSheet.builder()
                                        .dateWorking(startDate)
                                .build());
                    startDate = startDate.plusDays(1);
                }
            }
            index++;
        }
        return EmployeeTimeSheetResponse.builder()
                .workDayReal(WORK_DAY_REAL)
                .totalWorkDay(totalWorkDay)
                .employeeTimeSheets(result)
                .build();
    }

    @Override
    public void employeeCheckin(long employeeId) {
        perEmployeeUtil.checkSameEmployee(employeeId);
        LocalDate now = LocalDate.now();
        YearMonth yearMonth = YearMonth.from(now);
        LocalTime checkIn = LocalTime.now();
        LocalTime timeLate = null;
        boolean isLate = false;
        boolean workingOnHoliday = holidayRepository.existsByDateAndIsEnabled(now, true) || now.getDayOfWeek() == DayOfWeek.SATURDAY || now.getDayOfWeek() == DayOfWeek.SUNDAY;
        TimeSheet timeSheet = timeSheetRepository.findByYearMonthAndIsEnabled(yearMonth.toString(), true)
                .orElseThrow(() -> new BusinessException(ErrorCode.TIMEKEEPING_IS_EMPTY));
        LocalTime timeIn = TimeSheetConstant.TIME_IN;

        WorkTimeEmployee workTimeEmployee = letterDAO.getWorkTimeEmployee(now, employeeId);
        if (workTimeEmployee != null) {
            timeIn = timeIn.plusHours(workTimeEmployee.getGoLate().getHour())
                    .plusMinutes(workTimeEmployee.getGoLate().getMinute());
        }
        if (checkIn.isAfter(timeIn)) {
            Duration duration = Duration.between(timeIn, checkIn);
            timeLate = LocalTime.of(duration.toHoursPart(), duration.toMinutesPart());
            isLate = true;
        }
        TimeKeeping timeKeepingSaved =  timeKeepingRepository.save(TimeKeeping.builder()
                        .employeeId(employeeId)
                        .date(now)
                        .timeIn(checkIn)
                        .workDay(0.5)
                        .timeLate(timeLate)
                        .isLate(isLate)
                        .timeSheetId(timeSheet.getId())
                        .workingOnHoliday(workingOnHoliday)
                .build());

        if (workTimeEmployee != null) {
            timeKeepingHasLetterRepository.save(TimeKeepingHasLetter.builder()
                            .letterId(workTimeEmployee.getLetterId())
                            .timeKeepingId(timeKeepingSaved.getId())
                    .build());
        }
    }

    @Override
    public void employeeCheckout(long employeeId) {
        perEmployeeUtil.checkSameEmployee(employeeId);
        LocalDate now = LocalDate.now();
        YearMonth yearMonth = YearMonth.from(now);
        LocalTime checkout = LocalTime.now();
        LocalTime timeEarly = null;
        boolean workingOnHoliday = false;

        TimeSheet timeSheet = timeSheetRepository.findByYearMonthAndIsEnabled(yearMonth.toString(), true)
                .orElseThrow(() -> new BusinessException(ErrorCode.TIMEKEEPING_IS_EMPTY));
        LocalTime timeOut = TimeSheetConstant.TIME_OUT;

        WorkTimeEmployee workTimeEmployee = letterDAO.getWorkTimeEmployee(now, employeeId);
        if (workTimeEmployee != null) {
            timeOut = timeOut.minusHours(workTimeEmployee.getBackEarly().getHour())
                    .minusMinutes(workTimeEmployee.getBackEarly().getMinute());
        }

        if (checkout.isBefore(timeOut)) {
            Duration duration = Duration.between(checkout, timeOut);
            timeEarly = LocalTime.of(duration.toHoursPart(), duration.toMinutesPart());
        }
        TimeKeeping timeKeeping = timeKeepingRepository.findByEmployeeIdAndDate(employeeId, now).orElse(new TimeKeeping());
        timeKeeping.setEmployeeId(employeeId);
        timeKeeping.setDate(now);
        timeKeeping.setTimeOut(checkout);
        timeKeeping.setTimeEarly(timeEarly);
        timeKeeping.setWorkingOnHoliday(workingOnHoliday);
        timeKeeping.setTimeSheetId(timeSheet.getId());
        timeKeeping.setWorkDay(timeKeeping.getWorkDay() == null ? 0.5 : timeKeeping.getWorkDay() + 0.5);
        timeKeepingRepository.save(timeKeeping);
    }

    @Override
    public List<LeaveLetterResponse> getListLeaveLetter(long employeeId) {
        YearMonth yearMonth = YearMonth.now();
        List<LeaveLetterResponse> responses = employeeDAO.getListLeaveLetter(employeeId, yearMonth);
        if (responses.isEmpty()) {
            throw new BusinessException(ErrorCode.LETTER_IS_EMPTY);
        }
        return responses;
    }

    @Override
    public List<OverTimeLetterResponse> getListOverTimeLetter(long employeeId) {
        YearMonth yearMonth = YearMonth.now();
        List<OverTimeLetterResponse> responses = employeeDAO.getListOverTimeLetter(employeeId, yearMonth);
        if (responses.isEmpty()) {
            throw new BusinessException(ErrorCode.LETTER_IS_EMPTY);
        }
        return responses;
    }

    @Transactional
    @Override
    public void createEmployee(FullEmployeeRequest request, MultipartFile avatar, MultipartFile fontIdentityCard, MultipartFile backIdentityCard) {
        long start = System.currentTimeMillis();
        ResumeProfileRequest resumeProfileRequest = ResumeMapper.mapToResumeProfileRequest(request.getResumeRequest(), avatar);
        Employee employeeEntity = new Employee();
        if (request.getResumeRequest() != null) {
            employeeEntity = updateEmployee(employeeEntity, resumeProfileRequest);
        }
        if (employeeEntity.getId() != null) {
            if (!request.getEducationRequest().isEmpty())
                educationService.updateEducationProfile(request.getEducationRequest(), employeeEntity);

            if (!request.getFamilyRequest().isEmpty())
                familyService.updateFamilyOfEmployee(request.getFamilyRequest(), employeeEntity);

            if (!FileConstant.FILE_IS_EMPTY.equals(fontIdentityCard.getOriginalFilename()) && !FileConstant.FILE_IS_EMPTY.equals(backIdentityCard.getOriginalFilename())) {
                IdentityCartRequest identityCartRequest = new IdentityCartRequest();
                identityCartRequest.setEmployeeId(employeeEntity.getId());
                identityCartRequest.setFontIdCard(fontIdentityCard);
                identityCartRequest.setBackIdCard(backIdentityCard);
                updateIdentityCard(identityCartRequest);
            }

            if (request.getContractRequest() != null) {
                request.getContractRequest().setEmployeeId(employeeEntity.getId());
                contractService.createContract(request.getContractRequest());
            }
            if (request.getInsuranceRequest() != null) {
                request.getInsuranceRequest().setEmployeeId(employeeEntity.getId());
                updateInsurance(request.getInsuranceRequest());
            }
            accountService.createAccount(employeeEntity.getId(), employeeEntity.getFullName());
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    @Override
    public void updateInsurance(InsuranceEmployeeRequest request) {
        perEmployeeUtil.checkUpdateSameDepartmentByEmployeeId(request.getEmployeeId());
        Employee employee = checkEmployeeValidator(request.getEmployeeId());
        employee.setInsuranceNumber(request.getInsuranceNumber());
        employee.setInsuranceCard(request.getInsuranceCard());
        employeeRepository.save(employee);
    }

    @Override
    public InsuranceEmployeeResponse getInsuranceNumber(long employeeId) {
        perEmployeeUtil.checkWatchSameDepartmentByEmployeeId(employeeId);
        Employee employee = checkEmployeeValidator(employeeId);
        return InsuranceEmployeeResponse.builder()
                .employeeId(employee.getId())
                .insuranceNumber(employee.getInsuranceNumber())
                .insuranceCard(employee.getInsuranceCard())
                .build();
    }

    @Override
    public List<SalaryMonth> getListSalaryEmployee(long employeeId, String year) {
        perEmployeeUtil.checkWatchSameDepartmentByEmployeeId(employeeId);
        List<SalaryMonth> listSalary = employeeDAO.getListSalaryEmployee(employeeId, year);
        List<SalaryMonth> result = new ArrayList<>();
        int month = 1;
        for (SalaryMonth salaryMonth : listSalary) {
            if (salaryMonth.getYearMonth() != null) {
                while(month < salaryMonth.getYearMonth().getMonthValue()) {
                    result.add(SalaryMonth.builder()
                            .yearMonth(YearMonth.of(salaryMonth.getYearMonth().getYear(), month))
                            .salary(0)
                            .tax(0)
                            .build());
                    month++;
                }
                result.add(salaryMonth);
                month++;
            }
        }
        if (result.isEmpty()) {
            throw new BusinessException(ErrorCode.SALARY_TABLE_IS_EMPTY);
        }
        return result;
    }

    @Override
    public SalaryDetailResponse getSalaryDetailEmployee(long salaryDetailId) {
        SalaryTableDetail salaryTableDetail = salaryTableDetailRepository.findByIdAndIsEnabled(salaryDetailId, true).orElseThrow(
                () -> new BusinessException(ErrorCode.SALARY_DETAIL_IS_EMPTY));
        perEmployeeUtil.checkWatchSameDepartmentByEmployeeId(salaryTableDetail.getEmployeeId());
        SalaryDetailResponse response = employeeDAO.getSalaryDetailEmployee(salaryDetailId);
        if (response == null) {
            throw new BusinessException(ErrorCode.SALARY_DETAIL_IS_EMPTY);
        }
        return response;
    }

    @Override
    public SalaryAllowanceEmployee getSalaryAllowanceEmployee(long employeeId) {
        perEmployeeUtil.checkWatchSameDepartmentByEmployeeId(employeeId);
        SalaryAllowanceEmployee response = employeeDAO.getSalaryAllowanceEmployee(employeeId);
        if (response == null) {
            throw new BusinessException(ErrorCode.SALARY_DETAIL_IS_EMPTY);
        }
        return response;
    }

    @Override
    public EmployeeNameAndCode getEmployeeNameCode(long employeeId) {
        Employee employee = employeeRepository.findByIdAndIsEnabled(employeeId, true).orElseThrow(
                () -> new BusinessException(ErrorCode.EMPLOYEE_IS_EMPTY)
        );
        return EmployeeNameAndCode.builder()
                .employeeId(employee.getId())
                .employeeName(employee.getFullName())
                .employeeCode(employee.getEmployeeCode())
                .build();
    }

    @Override
    public EmployeeResponse getJobPositionByEmployeeId(long employeeId) {
        EmployeeResponse response = employeeDAO.getJobPositionByEmployeeId(employeeId);
        if (response == null)
            throw new BusinessException(ErrorCode.EMPLOYEE_IS_EMPTY);
        return response;
    }

    private void handleAddress(List<Address> list, String addressStr, Long id, int type, ErrorCode errorCode) {
        if (addressStr != null && !addressStr.isEmpty()) {
            Address address = updateAddress(id, addressStr, type, errorCode);
            list.add(address);
        }
    }

    private Address updateAddress(long employeeId, String addressString, int type, ErrorCode errorCode) {
        Address address = addressRepository.findByEmployeeIdAndTypeAndIsEnabled(employeeId, type, true).orElse(new Address());
        List<String> items =  Arrays.stream(addressString.split(",")).toList();
        if (items.size() != 4)
            throw new BusinessException(errorCode);
        address.setEmployeeId(employeeId);
        address.setStreet(items.get(0).trim());
        address.setDistrict(items.get(1).trim());
        address.setProvince(items.get(2).trim());
        address.setCity(items.get(3).trim());
        address.setType(type);
        return address;
    }

    private void checkValidatorRequest(ResumeProfileRequest request) {
        if(request.getFullName() == null || request.getFullName().isEmpty()) {
            throw new BusinessException(ErrorCode.NAME_INVALID);
        } else if (request.getNation() == null || request.getNation().isEmpty()) {
            throw new BusinessException(ErrorCode.NATION_IS_EMPTY);
        } else if (request.getPhoneNumber() == null || request.getPhoneNumber().isEmpty()) {
            throw new BusinessException(ErrorCode.PHONE_NUMBER_IS_EMPTY);
        } else if (request.getIdentityCard() == null || request.getIdentityCard().isEmpty()) {
            throw new BusinessException(ErrorCode.CCCD_IS_EMPTY);
        } else if (request.getIssueDateCCCD() == null || request.getIssueDateCCCD().toString().isEmpty()) {
            throw new BusinessException(ErrorCode.ISSUE_DATE_CCCD_IS_EMPTY);
        } else if (request.getPlaceCCCD() == null || request.getPlaceCCCD().isEmpty()) {
            throw new BusinessException(ErrorCode.PLACE_CCCD_IS_EMPTY);
        } else if (request.getTaxCode() == null || request.getTaxCode().isEmpty()) {
            throw new BusinessException(ErrorCode.TAX_CODE_IS_EMPTY);
        }
    }

    private Employee updateEmployee(Employee employeeEntity, ResumeProfileRequest request) {
        checkValidatorRequest(request);
        employeeEntity.setFullName(request.getFullName());
        employeeEntity.setDateOfBirth(request.getDateOfBirth());
        employeeEntity.setGender(request.isGender());
        employeeEntity.setMarriageStatus(request.isMarriageStatus());
        employeeEntity.setNation(request.getNation());
        employeeEntity.setPhoneNumber(request.getPhoneNumber());
        employeeEntity.setPlaceOfBirth(request.getPlaceOfBirth());
        employeeEntity.setReligion(request.getReligion());
        employeeEntity.setEthnic(request.getEthnic());
        employeeEntity.setIdentityCard(request.getIdentityCard());
        employeeEntity.setIssueDateCCCD(request.getIssueDateCCCD());
        employeeEntity.setPlaceCCCD(request.getPlaceCCCD());
        employeeEntity.setTaxCode(request.getTaxCode());
        employeeEntity.setType(request.getType());

        if(employeeEntity.getId() == null) {
            employeeEntity.setEmployeeCode(employeeUtil.generateEmployeeCode());
            employeeEntity.setEmail(request.getEmail());
            employeeEntity.setDateJoin(request.getDateJoin());
        }

        Employee employeeSaved =  employeeRepository.save(employeeEntity);

        // cập nhật địa chỉ
        List<Address> addressEntity = new ArrayList<>();
        handleAddress(addressEntity, request.getHomeTown(), employeeSaved.getId(), AddressConstant.HOMETOWN, ErrorCode.HOMETOWN_INVALID);
        handleAddress(addressEntity, request.getPermanentAddress(), employeeSaved.getId(), AddressConstant.PERMANENT_ADDRESS, ErrorCode.PERMANENT_ADDRESS_INVALID);
        handleAddress(addressEntity, request.getCurrentAddress(), employeeSaved.getId(), AddressConstant.CURRENT_ADDRESS, ErrorCode.CURRENT_ADDRESS_INVALID);

        AccountBank accountBankEntity = accountBankRepository.findByEmployeeIdAndIsEnabled(employeeSaved.getId(), true).orElse(
                new AccountBank()
        );
        accountBankEntity.setEmployeeId(employeeSaved.getId());
        accountBankEntity.setAccountName(request.getBankAccountName());
        accountBankEntity.setAccountNumber(request.getAccountBank());
        accountBankEntity.setBankName(request.getBankName());

        accountBankRepository.save(accountBankEntity);
        if (!addressEntity.isEmpty()) {
            addressRepository.saveAll(addressEntity);
        }

        if (request.getAvatar() != null && !FileConstant.FILE_IS_EMPTY.equals(request.getAvatar().getOriginalFilename())) {
            String fileName = "/" + employeeEntity.getEmployeeCode() + "/" + request.getAvatar().getOriginalFilename();
            employeeEntity.setAvatar(fileName);
            minIOService.uploadFile(fileName, request.getAvatar());
        }
        return employeeSaved;
    }
}
