package vn.tdsoftware.hrm_backend.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dao.ContractDAO;
import vn.tdsoftware.hrm_backend.dao.DepartmentDAO;
import vn.tdsoftware.hrm_backend.dto.account.response.CurrentAccountDTO;
import vn.tdsoftware.hrm_backend.dto.contract.request.ContractHasAllowanceRequest;
import vn.tdsoftware.hrm_backend.dto.contract.request.ContractRequest;
import vn.tdsoftware.hrm_backend.dto.contract.request.EndContractRequest;
import vn.tdsoftware.hrm_backend.dto.contract.response.*;
import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;
import vn.tdsoftware.hrm_backend.entity.*;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.repository.*;
import vn.tdsoftware.hrm_backend.service.ContractService;
import vn.tdsoftware.hrm_backend.util.PerContractUtil;
import vn.tdsoftware.hrm_backend.util.PerEmployeeUtil;
import vn.tdsoftware.hrm_backend.util.constant.ContractConstant;
import vn.tdsoftware.hrm_backend.util.constant.EmployeeConstant;
import vn.tdsoftware.hrm_backend.util.constant.InsuranceUtil;
import vn.tdsoftware.hrm_backend.util.constant.UpdateTypeConstant;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContractServiceImpl implements ContractService {
    private final ContractRepository contractRepository;
    private final ContractDAO contractDAO;
    private final EmployeeRepository employeeRepository;
    private final ContractTypeRepository contractTypeRepository;
    private final DepartmentRepository departmentRepository;
    private final JobPositionRepository jobPositionRepository;
    private final ContractHasAllowanceRepository contractHasAllowanceRepository;
    private final ContractGeneralRepository contractGeneralRepository;
    private final ContractGeneralHasAllowanceRepository contractgeneralHasAllowanceRepository;
    private final InsuranceRepository insuranceRepository;
    private final PerContractUtil perContractUtil;

    @Override
    public WorkProfileResponse getWorkProfile(long employeeId) {
        checkEmployeeValidator(employeeId);
        perContractUtil.checkSameDepartmentByEmployeeId(employeeId);
        WorkProfileResponse response = contractDAO.getWorkProfile(employeeId);

        if (response == null) {
            throw new BusinessException(ErrorCode.WORK_IS_EMPTY);
        }
        return response;
    }

    @Override
    public List<WorkProcessResponse> getWorkProcess(long employeeId) {
        checkEmployeeValidator(employeeId);
        perContractUtil.checkSameDepartmentByEmployeeId(employeeId);
        List<WorkProcessResponse> response = contractDAO.getWorkProcess(employeeId);
        if (response.isEmpty()) {
            throw new BusinessException(ErrorCode.CONTRACT_HISTORY_IS_EMPTY);
        }
        return response;
    }

    @Override
    public ContractProfileResponse getContractProfileByEmployee(long employeeId) {
        checkEmployeeValidator(employeeId);
        perContractUtil.checkSameDepartmentByEmployeeId(employeeId);
        ContractProfileResponse response = contractDAO.getContractProfileByEmployee(employeeId);
        if (response == null) {
            throw new BusinessException(ErrorCode.CONTRACT_IS_EMPTY);
        }
        return response;
    }

    @Override
    public ContractProfileResponse getContractProfileByContractId(long contractId) {
        perContractUtil.checkSameDepartmentByContractId(contractId);
        ContractProfileResponse response = contractDAO.getContractProfileByContractId(contractId);
        if (response == null) {
            throw new BusinessException(ErrorCode.CONTRACT_HISTORY_IS_EMPTY);
        }
        return response;
    }

    @Override
    public ContractDetailResponse getContractDetail(long contractId) {
        perContractUtil.checkSameDepartmentByContractId(contractId);
        ContractDetailResponse response = contractDAO.getContractDetail(contractId);
        if (response == null) {
            throw new BusinessException(ErrorCode.CONTRACT_HISTORY_IS_EMPTY);
        }
        return response;
    }

    @Override
    @Transactional
    public void updateContract(ContractRequest contractRequest) {
        Contract contract = contractRepository.findByIdAndIsEnabled(contractRequest.getContractId(), true).orElseThrow(
                () -> new BusinessException(ErrorCode.CONTRACT_IS_EMPTY)
        );
        checkAllExistParallel(contractRequest.getContractType(), contractRequest.getDepartment(), contractRequest.getJobPosition(), contractRequest.getCreateType());
        checkValidatorRequest(contractRequest);

        contract.setType(contractRequest.getContractType());
        contract.setDepartmentId(contract.getDepartmentId());
        contract.setJobPositionId(contract.getJobPositionId());
        contract.setMethod(contractRequest.getContractMethod());
        contract.setSalaryGross(contractRequest.getSalaryGross());
        contract.setDateStart(contractRequest.getDateStart());
        contract.setDateEnd(contractRequest.getDateEnd());
        contract.setDateSign(contractRequest.getDateSign());

        List<ContractHasAllowance> listAllowance  = new ArrayList<>();
        for (ContractHasAllowanceRequest item : contractRequest.getAllowances()) {
            if (Objects.equals(item.getIsUpdate(), UpdateTypeConstant.UPDATE)) {
                ContractHasAllowance contractHasAllowance = contractHasAllowanceRepository
                        .findByIdAndIsEnabled(item.getId(), true)
                        .orElse(new ContractHasAllowance());
                contractHasAllowance.setContractId(contract.getId());
                contractHasAllowance.setAllowanceId(item.getAllowanceId());
                contractHasAllowance.setAmount(item.getAmount());
                contractHasAllowance.setUnit(item.getUnit());

                listAllowance.add(contractHasAllowance);
            } else if (Objects.equals(item.getIsUpdate(), UpdateTypeConstant.DELETE)) {
                ContractHasAllowance contractHasAllowance = contractHasAllowanceRepository
                        .findByIdAndIsEnabled(item.getId(), true)
                        .orElseThrow(() -> new BusinessException(ErrorCode.CONTRACT_IS_EMPTY));
                contractHasAllowance.setEnabled(false);
                listAllowance.add(contractHasAllowance);
            }
        }
        contractHasAllowanceRepository.saveAll(listAllowance);
        contractRepository.save(contract);
    }

    @Override
    @Transactional
    public void createContract(ContractRequest contractRequest) {
        checkAllExistParallel(contractRequest.getContractType(), contractRequest.getDepartment(), contractRequest.getJobPosition(), contractRequest.getCreateType());
        checkValidatorRequest(contractRequest);
        boolean isActive = false;
        LocalDate today = LocalDate.now();

        ContractGeneral generalContract = contractGeneralRepository
                .findByEmployeeId(contractRequest.getEmployeeId())
                .orElse(new ContractGeneral());

        if(generalContract.getStatus() != null && generalContract.getStatus() == ContractConstant.CONTRACT_STATUS_ACTIVE &&
                !contractRequest.getDateStart().isAfter(today) &&
                contractRequest.getCreateType() == ContractConstant.CONTRACT_CREATE_NEW
        ) {
            throw new BusinessException(ErrorCode.NOT_CREATE_CONTRACT_NEW);
        }

        if ((!contractRequest.getDateStart().isAfter(today) || generalContract.getId() == null)) {
            isActive = true;
        }

        Contract contract = Contract.builder()
                .employeeId(contractRequest.getEmployeeId())
                .code(contractRequest.getContractCode())
                .type(contractRequest.getContractType())
                .departmentId(contractRequest.getDepartment())
                .jobPositionId(contractRequest.getJobPosition())
                .method(contractRequest.getContractMethod())
                .salaryGross(contractRequest.getSalaryGross())
                .dateStart(contractRequest.getDateStart())
                .dateSign(contractRequest.getDateSign())
                .isActive(isActive && contractRequest.getCreateType() == ContractConstant.CONTRACT_CREATE_NEW)
                .status(!contractRequest.getDateStart().isAfter(today) ?
                        ContractConstant.CONTRACT_STATUS_ACTIVE :
                        ContractConstant.CONTRACT_STATUS_NO_ACTIVE)
                .state(ContractConstant.CONTRACT_STATE_CHECKED)
                .dateEnd(contractRequest.getDateEnd())
                .dateSign(contractRequest.getDateSign())
                .description(contractRequest.getDescription())
                .build();

        //nếu trong trường hợp tạo phụ lục
        if (contractRequest.getCreateType() == ContractConstant.CONTRACT_CREATE_APPENDIX) {
            contract.setParent(contractRequest.getParent());
        }
        Contract contractSaved = contractRepository.save(contract);

        if (contractRequest.getAllowances() != null && !contractRequest.getAllowances().isEmpty()){
            List<ContractHasAllowance> listAllowance  = new ArrayList<>();
            for (ContractHasAllowanceRequest item : contractRequest.getAllowances()) {
                listAllowance.add(ContractHasAllowance.builder()
                        .contractId(contractSaved.getId())
                        .allowanceId(item.getAllowanceId())
                        .amount(item.getAmount())
                        .unit(item.getUnit())
                        .build());
            }
            contractHasAllowanceRepository.saveAll(listAllowance);
        }

        if (isActive) {
            activeContract(contractRequest.getEmployeeId(), contractSaved);
        }
    }

    @Override
    public List<ContractOfEmployeeResponse> getListContractOfEmployee(long employeeId) {
        perContractUtil.checkSameDepartmentByEmployeeId(employeeId);
        List<ContractOfEmployeeResponse> listAppendix = contractDAO.getListContractOfEmployee(employeeId);
        if (listAppendix.isEmpty()) {
            throw new BusinessException(ErrorCode.CONTRACT_HISTORY_IS_EMPTY);
        }
        return listAppendix;
    }

    @Override
    public List<ContractResponse> getListContract(EmployeeFilter filter) {
        perContractUtil.checkSameDepartmentByFilter(filter);
        List<ContractResponse> responses = contractDAO.getListContract(filter);
        if (responses.isEmpty()) {
            throw new BusinessException(ErrorCode.CONTRACT_IS_EMPTY);
        }
        return responses;
    }

    @Override
    public void endContract(EndContractRequest request) {
        Contract contract = contractRepository.findByIdAndIsEnabled(request.getContractId(), true).orElseThrow(
                () -> new BusinessException(ErrorCode.CONTRACT_IS_EMPTY)
        );
        LocalDate today = LocalDate.now();
        boolean isLiquid =  !request.getDateLiquidation().isAfter(today);
        List<Contract> contractLiquid = new ArrayList<>();
        if (isLiquid) {
            contract.setStatus(ContractConstant.CONTRACT_STATUS_LIQUID);
        }
        contract.setDateLiquidation(request.getDateLiquidation());
        contract.setReasonLiquidation(request.getReasonLiquidation());
        contractLiquid.add(contract);
        List<Contract> contractAppendix = contractRepository.findByParentAndIsEnabled(contract.getId(), true)
                .orElse(new ArrayList<>());

        if (!contractAppendix.isEmpty()) {
            for (Contract appendix : contractAppendix) {
                if (isLiquid) {
                    appendix.setStatus(ContractConstant.CONTRACT_STATUS_LIQUID);
                }
                appendix.setDateLiquidation(request.getDateLiquidation());
                appendix.setReasonLiquidation(request.getReasonLiquidation());
                contractLiquid.add(appendix);
            }
        }

        if (contractGeneralRepository.existsByEmployeeId(contract.getEmployeeId())) {
            Optional<ContractGeneral> general = contractGeneralRepository.findByEmployeeId(contract.getEmployeeId());
            if (general.isPresent()) {
                ContractGeneral contractGeneral = general.get();
                if (isLiquid) {
                    contractGeneral.setStatus(ContractConstant.CONTRACT_STATUS_LIQUID);
                    Employee employee = employeeRepository.findByIdAndIsEnabled(contract.getEmployeeId(), true).orElseThrow(
                            () -> new BusinessException(ErrorCode.EMPLOYEE_IS_EMPTY)
                    );
                    employee.setStatus(EmployeeConstant.EMPLOYEE_STATUS_QUIT);
                    employeeRepository.save(employee);
                }
                contractGeneral.setDateLiquidation(request.getDateLiquidation());
                contractGeneral.setReasonLiquidation(request.getReasonLiquidation());
                contractGeneralRepository.save(contractGeneral);
            }
        }
        contractRepository.saveAll(contractLiquid);

        // bảo hiểm
        YearMonth yearMonth = today.getDayOfMonth() > 14 ? YearMonth.now().plusMonths(1) : YearMonth.now();
        Insurance insurance = insuranceRepository.findInsuranceByEmployeeIdAndYearMonthAndIsEnabled(contract.getEmployeeId(), yearMonth.toString(), true).orElse(
                new Insurance());
        insurance.setEmployeeId(contract.getEmployeeId());
        insurance.setYearMonth(yearMonth.toString());
        insurance.setStatus(InsuranceUtil.INSURANCE_STATUS_DECREASE);
        insuranceRepository.save(insurance);
    }

    @Override
    public int countContractAppendix(long contractId) {
        perContractUtil.checkSameDepartmentByContractId(contractId);
        return contractDAO.countContractAppendix(contractId);
    }

    @Override
    public void expireContract(long contractId, boolean contractDisabled) {
        List<Contract> expireList = new ArrayList<>();
        Contract contractParent = contractRepository.findByIdAndIsEnabled(contractId, true).orElseThrow(
                () -> new BusinessException(ErrorCode.CONTRACT_IS_EMPTY));
        if (contractParent.getStatus() == ContractConstant.CONTRACT_STATUS_ACTIVE) {
            contractParent.setIsActive(!contractDisabled);
            if (contractParent.getStatus() != ContractConstant.CONTRACT_STATUS_LIQUID) {
                contractParent.setStatus(ContractConstant.CONTRACT_STATUS_EXPIRED);
                expireList.add(contractParent);
            }
            List<Contract> contractAppendix = contractRepository.findByParentAndIsEnabled(contractId, true).orElse(new ArrayList<>());
            if (!contractAppendix.isEmpty()) {
                for (Contract appendix : contractAppendix) {
                    if (appendix.getState() != ContractConstant.CONTRACT_STATUS_LIQUID) {
                        appendix.setStatus(ContractConstant.CONTRACT_STATUS_EXPIRED);
                        expireList.add(appendix);
                    }
                }
            }
            if (!expireList.isEmpty()) {
                contractRepository.saveAll(expireList);
            }
        }
    }

    private void checkValidatorRequest(ContractRequest request) {
        if(request.getCreateType() == ContractConstant.CONTRACT_CREATE_NEW && request.getSalaryGross() <= 0) {
            throw new BusinessException(ErrorCode.RANK_SALARY_INVALID);
        }else if (request.getDateStart() == null || request.getDateStart().toString().isEmpty()) {
            throw new BusinessException(ErrorCode.DATE_START_IN_VALID);
        } else if (request.getDateSign() == null || request.getDateSign().toString().isEmpty()) {
            throw new BusinessException(ErrorCode.DATE_SIGN_IN_VALID);
        }
    }

    public void checkAllExistParallel(long contractType, Long department, Long jobPosition, int createType) {
        if (!contractTypeRepository.existsById(contractType)) {
            throw new BusinessException(ErrorCode.TYPE_CONTRACT_IS_EMPTY);
        }
        if(createType == ContractConstant.CONTRACT_CREATE_NEW) {
            if (!departmentRepository.existsById(department)) {
                throw new BusinessException(ErrorCode.DEPARTMENT_IS_EMPTY);
            }
            if (!jobPositionRepository.existsById(jobPosition)) {
                throw new BusinessException(ErrorCode.JOB_POSITION_NOT_EXIST);
            }
        }
    }

    @Override
    @Transactional
    public void activeContract(long employeeId, Contract contract) {
        ContractGeneral generalContract = contractGeneralRepository
                .findByEmployeeId(employeeId)
                .orElse(new ContractGeneral());

        if (generalContract.getContractOriginal() != null && contract.getParent() == null) {
            expireContract(generalContract.getContractOriginal(), true);
        }
        contract.setStatus(ContractConstant.CONTRACT_STATUS_ACTIVE);
        generalContract.setEmployeeId(employeeId);
        generalContract.setType(contract.getType());
        generalContract.setStatus(ContractConstant.CONTRACT_STATUS_ACTIVE);
        generalContract.setState(ContractConstant.CONTRACT_STATE_CHECKED);
        generalContract.setDateEnd(contract.getDateEnd());
        generalContract.setMethod(contract.getMethod());

        if (!Objects.equals(generalContract.getDepartmentId(), contract.getDepartmentId()) && contract.getDepartmentId() != null) {
            generalContract.setDepartmentId(contract.getDepartmentId());
        }
        if (!Objects.equals(generalContract.getSalaryGross(), contract.getSalaryGross()) && contract.getSalaryGross() != 0) {
            generalContract.setSalaryGross(contract.getSalaryGross());
        }

        if (generalContract.getId() == null) {
            generalContract.setDateStart(contract.getDateStart());
            generalContract.setDateSign(contract.getDateSign());
            generalContract.setCode(contract.getCode());
        }

        if (!Objects.equals(generalContract.getContractOriginal(), contract.getId()) && contract.getParent() == null) {
            generalContract.setContractOriginal(contract.getId());
            Employee employee = employeeRepository.findByIdAndIsEnabled(employeeId, true).orElseThrow(() -> new BusinessException(ErrorCode.EMPLOYEE_IS_EMPTY));
            employee.setStatus(EmployeeConstant.EMPLOYEE_STATUS_WORKING);
            employeeRepository.save(employee);
        }

        if (!Objects.equals(generalContract.getJobPositionId(), contract.getJobPositionId()) && contract.getJobPositionId() != null) {
            generalContract.setJobPositionId(contract.getJobPositionId());
            generalContract.setDateOnBoard(contract.getDateStart());
        }

        ContractGeneral savedContractGeneral = contractGeneralRepository.save(generalContract);

        List<ContractHasAllowance> contractHasAllowanceList = contractHasAllowanceRepository
                .findAllByContractIdAndIsEnabled(contract.getId(), true);

        if (!contractHasAllowanceList.isEmpty()) {
            List<ContractGeneralHasAllowance> currentAllowances =
                    contractgeneralHasAllowanceRepository.findByContractId(savedContractGeneral.getId());

            Map<Long, ContractGeneralHasAllowance> mapAllowance = currentAllowances.stream()
                    .collect(Collectors.toMap(ContractGeneralHasAllowance::getAllowanceId, item -> item));

            for (ContractHasAllowance entity : contractHasAllowanceList) {
                ContractGeneralHasAllowance updated = mapAllowance.get(entity.getAllowanceId());
                if (updated != null) {
                    updated.setAmount(entity.getAmount());
                    updated.setUnit(entity.getUnit());
                    updated.setEnabled(entity.getAmount() != 0);
                } else {
                    currentAllowances.add(ContractGeneralHasAllowance.builder()
                            .allowanceId(entity.getAllowanceId())
                            .amount(entity.getAmount())
                            .contractId(savedContractGeneral.getId())
                            .unit(entity.getUnit())
                            .build());
                }
            }
            contractgeneralHasAllowanceRepository.saveAll(currentAllowances);
        }
    }

    private void checkEmployeeValidator(long employeeId) {
        employeeRepository.findByIdAndIsEnabled(employeeId, true).orElseThrow(
                () -> new BusinessException(ErrorCode.EMPLOYEE_IS_EMPTY)
        );
    }
}