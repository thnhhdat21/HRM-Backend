package vn.tdsoftware.hrm_backend.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dao.DecisionDAO;
import vn.tdsoftware.hrm_backend.dto.contract.request.ContractHasAllowanceRequest;
import vn.tdsoftware.hrm_backend.dto.contract.request.ContractRequest;
import vn.tdsoftware.hrm_backend.dto.contract.request.EndContractRequest;
import vn.tdsoftware.hrm_backend.dto.decision.response.CountDecisionResponse;
import vn.tdsoftware.hrm_backend.dto.decision.response.DecisionResponse;
import vn.tdsoftware.hrm_backend.dto.decision.response.RewardAndPenaltyOfEmployee;
import vn.tdsoftware.hrm_backend.dto.decision.rewardandpenalty.request.RewardAndPenaltyDecisionRequest;
import vn.tdsoftware.hrm_backend.dto.decision.rewardandpenalty.response.RewardAndPenaltyDecisionResponse;
import vn.tdsoftware.hrm_backend.dto.decision.salary.request.SalaryDecisionRequest;
import vn.tdsoftware.hrm_backend.dto.decision.salary.request.SalaryHasAllowanceRequest;
import vn.tdsoftware.hrm_backend.dto.decision.salary.resoponse.SalaryDecisionResponse;
import vn.tdsoftware.hrm_backend.dto.decision.salary.resoponse.SalaryHasAllowanceResponse;
import vn.tdsoftware.hrm_backend.dto.decision.termination.resquest.TerminationDecisionRequest;
import vn.tdsoftware.hrm_backend.dto.decision.transferandappoint.response.TransferAndAppointDecisionResponse;
import vn.tdsoftware.hrm_backend.dto.decision.transferandappoint.request.TransferDecisionRequest;
import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;
import vn.tdsoftware.hrm_backend.entity.*;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.repository.*;
import vn.tdsoftware.hrm_backend.service.ContractService;
import vn.tdsoftware.hrm_backend.service.DecisionService;
import vn.tdsoftware.hrm_backend.service.EmployeeService;
import vn.tdsoftware.hrm_backend.util.DecisionUtil;
import vn.tdsoftware.hrm_backend.util.constant.ContractConstant;
import vn.tdsoftware.hrm_backend.util.constant.DecisionConstant;
import vn.tdsoftware.hrm_backend.util.constant.UpdateTypeConstant;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DecisionServiceImpl implements DecisionService {
    private final RewardAndPenaltyRepository rewardAndPenaltyRepository;
    private final DecisionRepository decisionRepository;
    private final RewardAndPenaltyDecisionRepository rewardAndPenaltyDecisionRepository;
    private final TransferAndAppointDecisionRepository transferAndAppointDecisionRepository;
    private final EmployeeService employeeService;
    private final ContractGeneralRepository contractGeneralRepository;
    private final SalaryDecisionRepository salaryDecisionRepository;
    private final SalaryDecisionHasAllowanceRepository salaryDecisionHasAllowanceRepository;
    private final DecisionDAO decisionDAO;
    private final ContractService contractService;

    @Override
    public List<DecisionResponse> getListDecision(EmployeeFilter filter) {
        List<DecisionResponse> responses = decisionDAO.getListDecision(filter);
        if (responses.isEmpty()) {
            throw new BusinessException(ErrorCode.DECISION_IS_EMPTY);
        }
        return responses;
    }

    @Override
    public List<CountDecisionResponse> getCountDecision(EmployeeFilter filter) {
        List<CountDecisionResponse> responses = decisionDAO.getCountDecision(filter);
        if (responses.isEmpty()) {
            throw new BusinessException(ErrorCode.DECISION_IS_EMPTY);
        }
        return responses;
    }

    @Override
    @Transactional
    public void updateRewardAndPenaltyDecisionEmployee(List<RewardAndPenaltyDecisionRequest> request) {
        for (RewardAndPenaltyDecisionRequest requestItem : request) {
            validator(requestItem.getCode(), requestItem.getDate());
            if (requestItem.getAmount() != null && requestItem.getAmount() <= 0) {
                throw new BusinessException(ErrorCode.AMOUNT_INVALID);
            }
            RewardAndPenalty rewardAndPenalty = rewardAndPenaltyRepository.findByIdAndIsEnabled(requestItem.getRewardAndPenaltyId(), true).orElseThrow(
                () -> new BusinessException(ErrorCode.REWARD_OR_PENALTY_IS_EMPTY)
            );

            Decision decisionSaved =  decisionRepository.save(Decision.builder()
                    .employeeId(requestItem.getEmployeeId())
                    .code(requestItem.getCode())
                    .date(requestItem.getDate())
                    .reason(rewardAndPenalty.getName())
                    .type(requestItem.getType())
                    .build());

            rewardAndPenaltyDecisionRepository.save(RewardAndPenaltyDecision.builder()
                            .decisionId(decisionSaved.getId())
                            .rewardAndPenaltyId(rewardAndPenalty.getId())
                            .amount(requestItem.getAmount())
                    .build());
        }
    }

    @Override
    public void updateRewardAndPenaltyDecision(RewardAndPenaltyDecisionRequest request) {
        employeeService.checkEmployeeValidator(request.getEmployeeId());
        validator(request.getCode(), request.getDate());

        Decision decision = decisionRepository.findByIdAndIsEnabled(request.getDecisionId(), true).orElse(new Decision());
        RewardAndPenalty rewardAndPenalty = rewardAndPenaltyRepository.findByIdAndIsEnabled(request.getRewardAndPenaltyId(), true).orElseThrow(
                () -> new BusinessException(ErrorCode.REWARD_OR_PENALTY_IS_EMPTY)
        );
        decision.setCode(request.getCode());
        decision.setDate(request.getDate());
        decision.setEmployeeId(request.getEmployeeId());
        decision.setReason(rewardAndPenalty.getName());
        decision.setState(DecisionConstant.DECISION_STATE_WAITING);
        decision.setType(request.getType());
        Decision decisionSaved = decisionRepository.save(decision);

        RewardAndPenaltyDecision rewardAndPenaltyDecision = rewardAndPenaltyDecisionRepository
                .findByDecisionIdAndIsEnabled(decisionSaved.getId(), true).orElse(new RewardAndPenaltyDecision());
        rewardAndPenaltyDecision.setDecisionId(decisionSaved.getId());
        rewardAndPenaltyDecision.setRewardAndPenaltyId(rewardAndPenalty.getId());
        rewardAndPenaltyDecision.setAmount(request.getAmount());
        rewardAndPenaltyDecisionRepository.save(rewardAndPenaltyDecision);
    }

    @Override
    @Transactional
    public void updateTransferAndAppointmentDecision(TransferDecisionRequest request) {
        employeeService.checkEmployeeValidator(request.getEmployeeId());
        validator(request.getCode(), request.getDate());
        Decision decision = decisionRepository.findByIdAndIsEnabled(request.getDecisionId(), true).orElse(new Decision());

        ContractGeneral contractGeneral = contractGeneralRepository.findByEmployeeId(request.getEmployeeId()).orElseThrow(
                () -> new BusinessException(ErrorCode.CONTRACT_IS_EMPTY)
        );

        decision.setCode(request.getCode());
        decision.setDate(request.getDate());
        decision.setEmployeeId(request.getEmployeeId());
        decision.setReason(request.getReason());
        decision.setState(DecisionConstant.DECISION_STATE_WAITING);
        decision.setType(request.getType());
        Decision decisionSaved = decisionRepository.save(decision);

        TransferAndAppointDecision transferAndAppointDecision =
                transferAndAppointDecisionRepository.findByDecisionIdAndIsEnabled(decisionSaved.getId(), true)
                        .orElse(new TransferAndAppointDecision());
        transferAndAppointDecision.setDecisionId(decisionSaved.getId());
        transferAndAppointDecision.setDepartmentOld(contractGeneral.getDepartmentId());
        transferAndAppointDecision.setJobPositionOld(contractGeneral.getJobPositionId());
        transferAndAppointDecision.setDepartmentNew(request.getDepartmentNewId());
        transferAndAppointDecision.setJobPositionNew(request.getJobPositionNewId());
        transferAndAppointDecisionRepository.save(transferAndAppointDecision);
    }

    @Override
    @Transactional
    public void updateSalaryDecision(SalaryDecisionRequest request) {
        employeeService.checkEmployeeValidator(request.getEmployeeId());
        validator(request.getCode(), request.getDateDecision());
        validatorSalary(request.getReason(), request.getDateActive());
        Decision decision = decisionRepository.findByIdAndIsEnabled(request.getDecisionId(), true).orElse(new Decision());
        ContractGeneral contractGeneral = contractGeneralRepository.findByEmployeeId(request.getEmployeeId()).orElseThrow(
                () -> new BusinessException(ErrorCode.CONTRACT_IS_EMPTY)
        );
        decision.setCode(request.getCode());
        decision.setDate(request.getDateDecision());
        decision.setEmployeeId(request.getEmployeeId());
        decision.setReason(request.getReason());
        decision.setState(DecisionConstant.DECISION_STATE_WAITING);
        decision.setType(DecisionConstant.DECISION_TYPE_SALARY);
        Decision decisionSaved = decisionRepository.save(decision);

        SalaryDecision salaryDecision = salaryDecisionRepository.
                findByDecisionIdAndIsEnabled(decisionSaved.getId(), true).orElse(new SalaryDecision());

        salaryDecision.setDecisionId(decisionSaved.getId());
        salaryDecision.setDateActive(request.getDateActive());
        salaryDecision.setAmountOld(contractGeneral.getSalaryGross());
        salaryDecision.setAmountNew(request.getAmountNew());
        SalaryDecision salaryDecisionSaved = salaryDecisionRepository.save(salaryDecision);

        List<SalaryDecisionHasAllowance> listAllowance  = new ArrayList<>();
        SalaryDecisionHasAllowance salaryHasAllowance;
        for (SalaryHasAllowanceRequest item : request.getAllowances()) {
            if (item.getIsUpdate() == null)
                continue;
            switch (item.getIsUpdate()) {
                case UpdateTypeConstant.UPDATE -> {
                    salaryHasAllowance = salaryDecisionHasAllowanceRepository
                            .findByIdAndIsEnabled(item.getId(), true)
                            .orElse(new SalaryDecisionHasAllowance());
                    salaryHasAllowance.setSalaryDecisionId(salaryDecisionSaved.getId());
                    salaryHasAllowance.setAllowanceId(item.getAllowanceId());
                    salaryHasAllowance.setAmount(item.getAmount());
                    salaryHasAllowance.setUnit(item.getUnit());
                    listAllowance.add(salaryHasAllowance);
                }
                case UpdateTypeConstant.DELETE -> {
                    salaryHasAllowance = salaryDecisionHasAllowanceRepository
                            .findByIdAndIsEnabled(item.getId(), true)
                            .orElseThrow(() -> new BusinessException(ErrorCode.SALARY_HAS_ALLOWANCE_IS_EMPTY));
                    salaryHasAllowance.setEnabled(false);
                    listAllowance.add(salaryHasAllowance);
                }
            }
        }
        salaryDecisionHasAllowanceRepository.saveAll(listAllowance);
    }

    @Override
    public void updateTerminationDecision(TerminationDecisionRequest request) {
        validator(request.getCode(), request.getDate());
        employeeService.checkEmployeeValidator(request.getEmployeeId());
        Decision decision = decisionRepository.findByIdAndIsEnabled(request.getDecisionId(), true).orElse(new Decision());
        decision.setCode(request.getCode());
        decision.setDate(request.getDate());
        decision.setEmployeeId(request.getEmployeeId());
        decision.setReason(request.getReason());
        decision.setType(DecisionConstant.DECISION_TYPE_TERMINATION);
        decision.setState(DecisionConstant.DECISION_STATE_WAITING);
        decisionRepository.save(decision);
    }

    @Override
    @Transactional
    public void deleteDecision(long decisionId) {
        Decision decision = decisionRepository.findById(decisionId).orElseThrow(
                () -> new BusinessException(ErrorCode.DECISION_IS_EMPTY)
        );
        decision.setEnabled(false);
        switch (decision.getType()) {
            case DecisionConstant.DECISION_TYPE_REWARD,
                 DecisionConstant.DECISION_TYPE_PENALTY -> {
                RewardAndPenaltyDecision rewardAndPenaltyDecision = rewardAndPenaltyDecisionRepository
                        .findByDecisionId(decision.getId());
                rewardAndPenaltyDecision.setEnabled(false);
                rewardAndPenaltyDecisionRepository.save(rewardAndPenaltyDecision);
            }

            case DecisionConstant.DECISION_TYPE_APPOINT,
                 DecisionConstant.DECISION_TYPE_TRANSFER -> {
                TransferAndAppointDecision transferAndAppointDecision = transferAndAppointDecisionRepository
                        .findByDecisionId(decision.getId());
                transferAndAppointDecision.setEnabled(false);
                transferAndAppointDecisionRepository.save(transferAndAppointDecision);
            }

            case DecisionConstant.DECISION_TYPE_SALARY -> {
                SalaryDecision salaryDecision = salaryDecisionRepository
                        .findByDecisionId(decision.getId());
                salaryDecision.setEnabled(false);
                salaryDecisionRepository.save(salaryDecision);
                List<SalaryDecisionHasAllowance> listAllowance  = salaryDecisionHasAllowanceRepository
                        .findBySalaryDecisionIdAndIsEnabled(salaryDecision.getId(), true).orElse(
                                new ArrayList<>()
                        );
                if(!listAllowance.isEmpty()) {
                    List<SalaryDecisionHasAllowance> listDelete = new ArrayList<>();
                    for (SalaryDecisionHasAllowance item : listAllowance) {
                        item.setEnabled(false);
                        listDelete.add(item);
                    }
                    salaryDecisionHasAllowanceRepository.saveAll(listDelete);
                }
            }
        }
    }

    @Override
    public Object getDecisionById(long decisionId) {
        Decision decision = decisionRepository.findById(decisionId).orElseThrow(
                () -> new BusinessException(ErrorCode.DECISION_IS_EMPTY)
        );
        switch (decision.getType()) {
            case DecisionConstant.DECISION_TYPE_REWARD,
                 DecisionConstant.DECISION_TYPE_PENALTY -> {
                return getTypeRewardAndPenaltyDecision(decision);
            }
            case DecisionConstant.DECISION_TYPE_APPOINT,
                 DecisionConstant.DECISION_TYPE_TRANSFER -> {
                return getTransferAndAppointDecision(decision);
            }
            case DecisionConstant.DECISION_TYPE_SALARY -> {
                return getSalaryDecision(decision);
            }
            case DecisionConstant.DECISION_TYPE_TERMINATION-> {
                return decision;
            }
            default -> throw new BusinessException(ErrorCode.DECISION_IS_EMPTY);
        }
    }

    @Override
    public void noApproveDecision(long decisionId) {
        Decision decision = decisionRepository.findByIdAndIsEnabled(decisionId, true).orElseThrow(
                () -> new BusinessException(ErrorCode.DECISION_IS_EMPTY)
        );
        decision.setState(DecisionConstant.DECISION_STATE_NO_APPROVE);
        decisionRepository.save(decision);
    }

    @Override
    @Transactional
    public void approveDecision(long decisionId) {
        Decision decision = decisionRepository.findById(decisionId).orElseThrow(
                () -> new BusinessException(ErrorCode.DECISION_IS_EMPTY)
        );
        switch (decision.getType()) {
            case DecisionConstant.DECISION_TYPE_APPOINT,
                 DecisionConstant.DECISION_TYPE_TRANSFER -> approveTransferAndAppoint(decision);

            case DecisionConstant.DECISION_TYPE_SALARY -> approveSalary(decision);

            case DecisionConstant.DECISION_TYPE_TERMINATION-> approveTermination(decision);

            default -> {
                decision.setState(DecisionConstant.DECISION_STATE_APPROVE);
                decisionRepository.save(decision);
            }
        }
    }

    @Override
    public List<RewardAndPenaltyOfEmployee> getListRewardAndPenalty(long employeeId, YearMonth yearMonth) {
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();
        return decisionDAO.getListRewardAndPenalty(employeeId, startDate, endDate);
    }

    private RewardAndPenaltyDecisionResponse getTypeRewardAndPenaltyDecision(Decision decision) {
        RewardAndPenaltyDecision rewardAndPenaltyDecision = rewardAndPenaltyDecisionRepository
                .findByDecisionId(decision.getId());

        return RewardAndPenaltyDecisionResponse.builder()
                .decisionId(decision.getId())
                .code(decision.getCode())
                .date(decision.getDate())
                .employeeId(decision.getEmployeeId())
                .rewardAndPenaltyId(rewardAndPenaltyDecision.getRewardAndPenaltyId())
                .amount(rewardAndPenaltyDecision.getAmount())
                .type(decision.getType())
                .state(decision.getState())
                .build();

    }

    private TransferAndAppointDecisionResponse getTransferAndAppointDecision(Decision decision) {
        TransferAndAppointDecision transferAndAppointDecision = transferAndAppointDecisionRepository
                .findByDecisionId(decision.getId());

        return TransferAndAppointDecisionResponse.builder()
                .decisionId(decision.getId())
                .code(decision.getCode())
                .date(decision.getDate())
                .employeeId(decision.getEmployeeId())
                .reason(decision.getReason())
                .departmentNewId(transferAndAppointDecision.getDepartmentNew())
                .jobPositionNewId(transferAndAppointDecision.getJobPositionNew())
                .departmentOldId(transferAndAppointDecision.getDepartmentOld())
                .jobPositionOldId(transferAndAppointDecision.getJobPositionOld())
                .type(decision.getType())
                .state(decision.getState())
                .build();


    }

    public SalaryDecisionResponse getSalaryDecision (Decision decision) {
        SalaryDecision salaryDecision = salaryDecisionRepository.findByDecisionId(decision.getId());

        Optional<List<SalaryDecisionHasAllowance>> listAllowance  = salaryDecisionHasAllowanceRepository
                .findBySalaryDecisionIdAndIsEnabled(salaryDecision.getId(), true);

        SalaryDecisionResponse salaryDecisionResponse = SalaryDecisionResponse.builder()
                .decisionId(decision.getId())
                .code(decision.getCode())
                .dateDecision(decision.getDate())
                .employeeId(decision.getEmployeeId())
                .reason(decision.getReason())
                .dateActive(salaryDecision.getDateActive())
                .amountOld(salaryDecision.getAmountOld())
                .amountNew(salaryDecision.getAmountNew())
                .state(decision.getState())
                .build();

        if(listAllowance.isPresent()) {
            List<SalaryDecisionHasAllowance> listDelete = listAllowance.get();
            List<SalaryHasAllowanceResponse> listResponse = new ArrayList<>();
            for (SalaryDecisionHasAllowance item : listDelete) {
                listResponse.add(SalaryHasAllowanceResponse.builder()
                                .id(item.getId())
                                .allowanceId(item.getAllowanceId())
                                .amount(item.getAmount())
                                .unit(item.getUnit())
                        .build());
            }
            salaryDecisionResponse.setAllowances(listResponse);
        }
    return salaryDecisionResponse;
    }

    private void approveTransferAndAppoint(Decision decision) {
        TransferAndAppointDecision transferAndAppointDecision = transferAndAppointDecisionRepository
                .findByDecisionId(decision.getId());

        ContractGeneral contractGeneral = contractGeneralRepository.findByEmployeeId(decision.getEmployeeId()).orElseThrow(
                () -> new BusinessException(ErrorCode.CONTRACT_IN_VALID)
        );
        decision.setState(DecisionConstant.DECISION_STATE_APPROVE);
        int count =  contractService.countContractAppendix(contractGeneral.getContractOriginal());
        decisionRepository.save(decision);
        contractService.createContract(ContractRequest.builder()
                        .employeeId(decision.getEmployeeId())
                        .contractCode(contractGeneral.getCode() + "_" +count)
                        .contractType(contractGeneral.getType())
                        .department(transferAndAppointDecision.getDepartmentNew())
                        .jobPosition(transferAndAppointDecision.getJobPositionNew())
                        .dateStart(LocalDate.now())
                        .dateSign(LocalDate.now())
                        .parent(contractGeneral.getContractOriginal())
                        .createType(ContractConstant.CONTRACT_CREATE_APPENDIX)
                        .description("Tạo phụ lục: " + DecisionUtil.DecisionType.get(decision.getType()))
                .build());
    }

    private void approveSalary(Decision decision) {
        SalaryDecision salaryDecision = salaryDecisionRepository.findByDecisionId(decision.getId());

        List<SalaryDecisionHasAllowance> salaryDecisionHasAllowance = salaryDecisionHasAllowanceRepository
                .findBySalaryDecisionIdAndIsEnabled(salaryDecision.getId(), true).orElse(new ArrayList<>());

        ContractGeneral contractGeneral = contractGeneralRepository.findByEmployeeId(decision.getEmployeeId()).orElseThrow(
                () -> new BusinessException(ErrorCode.CONTRACT_IN_VALID)
        );
        int count =  contractService.countContractAppendix(contractGeneral.getContractOriginal());
        decisionRepository.save(decision);

        ContractRequest contractRequest = ContractRequest.builder()
                .employeeId(decision.getEmployeeId())
                .contractCode(contractGeneral.getCode() + "_" +count)
                .contractType(contractGeneral.getType())
                .dateStart(salaryDecision.getDateActive())
                .salaryGross(salaryDecision.getAmountNew())
                .dateSign(LocalDate.now())
                .parent(contractGeneral.getContractOriginal())
                .createType(ContractConstant.CONTRACT_CREATE_APPENDIX)
                .description("Tạo phụ lục: " + DecisionUtil.DecisionType.get(decision.getType()))
                .build();
        if (!salaryDecisionHasAllowance.isEmpty()) {
            List<ContractHasAllowanceRequest> contractHasAllowanceRequests = new ArrayList<>();
            for (SalaryDecisionHasAllowance item : salaryDecisionHasAllowance) {
                contractHasAllowanceRequests.add(ContractHasAllowanceRequest.builder()
                                .allowanceId(item.getAllowanceId())
                                .amount(item.getAmount())
                                .unit(item.getUnit())
                        .build());
            }
            contractRequest.setAllowances(contractHasAllowanceRequests);
        }
        decision.setState(DecisionConstant.DECISION_STATE_APPROVE);
        decisionRepository.save(decision);
        contractService.createContract(contractRequest);
    }

    private void approveTermination(Decision decision) {
        ContractGeneral contractGeneral = contractGeneralRepository.findByEmployeeId(decision.getEmployeeId()).orElseThrow(
                () -> new BusinessException(ErrorCode.CONTRACT_IN_VALID)
        );
        decision.setState(DecisionConstant.DECISION_STATE_APPROVE);
        decisionRepository.save(decision);
        contractService.endContract(EndContractRequest.builder()
                        .contractId(contractGeneral.getContractOriginal())
                        .dateLiquidation(LocalDate.now())
                        .reasonLiquidation(decision.getReason())
                .build());
    }


    private void validator (String code, LocalDate date) {
        if (code == null || code.isEmpty()) {
            throw new BusinessException(ErrorCode.CODE_IN_VALID);
        } else if (date == null || date.toString().isEmpty()) {
            throw new BusinessException(ErrorCode.DATE_IN_VALID);
        }
    }

    private void validatorSalary(String reason, LocalDate dateActive) {
        if (reason == null || reason.isEmpty()) {
            throw new BusinessException(ErrorCode.CODE_IN_VALID);
        } else if (dateActive == null || dateActive.toString().isEmpty()) {
            throw new BusinessException(ErrorCode.DATE_IN_VALID);
        }
    }
}