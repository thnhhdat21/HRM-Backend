package vn.tdsoftware.hrm_backend.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.common.exception.BusinessFlexException;
import vn.tdsoftware.hrm_backend.dao.ContractTypeDAO;
import vn.tdsoftware.hrm_backend.dto.contracttype.request.ContractTypeRequest;
import vn.tdsoftware.hrm_backend.dto.contracttype.request.ContractTypeUpdate;
import vn.tdsoftware.hrm_backend.dto.contracttype.response.ContractTypeDetail;
import vn.tdsoftware.hrm_backend.dto.contracttype.response.ContractTypeResponse;
import vn.tdsoftware.hrm_backend.dto.contracttype.response.CountContractTypeResponse;
import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;
import vn.tdsoftware.hrm_backend.entity.Allowance;
import vn.tdsoftware.hrm_backend.entity.ContractType;
import vn.tdsoftware.hrm_backend.entity.ContractTypeHasAllowance;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.ContractTypeMapper;
import vn.tdsoftware.hrm_backend.repository.AllowanceRepository;
import vn.tdsoftware.hrm_backend.repository.ContractTypeHasAllowanceRepository;
import vn.tdsoftware.hrm_backend.repository.ContractTypeRepository;
import vn.tdsoftware.hrm_backend.service.ContractTypeService;
import vn.tdsoftware.hrm_backend.util.constant.ContractConstant;
import vn.tdsoftware.hrm_backend.util.constant.StatusContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ContractTypeServiceImpl implements ContractTypeService {
    private final ContractTypeRepository typeContractRepository;
    private final ContractTypeHasAllowanceRepository contractTypeHasAllowanceRepository;
    private final ContractTypeRepository contractTypeRepository;
    private final AllowanceRepository allowanceRepository;
    private final ContractTypeDAO contractTypeDAO;

    @Override
    public List<ContractTypeResponse> getListContractType() {
        List<ContractType> typeContracts = typeContractRepository.findAllByIsEnabled(true);
        List<ContractTypeResponse> responses = new ArrayList<>();
        if (typeContracts.isEmpty()) {
            throw new BusinessException(ErrorCode.LIST_TYPE_CONTRACT_IS_EMPTY);
        }
        for (ContractType contractType : typeContracts) {
            responses.add(ContractTypeMapper.mapToTypeContractResponse(contractType));
        }
        return responses;
    }

    @Override
    @Transactional
    public ContractTypeResponse createContractType(ContractTypeRequest typeContractRequest) {
        checkValidator(typeContractRequest);
        ContractType contractTypeSaved = contractTypeRepository.save(ContractType.builder()
                        .name(typeContractRequest.getName())
                        .type(typeContractRequest.getType())
                        .method(typeContractRequest.getMethod())
                        .term(typeContractRequest.getTerm())
                        .unit(typeContractRequest.getUnit())
                        .insurance(typeContractRequest.getInsurance())
                        .status(StatusContract.ACTIVE)
                .build());
        if (typeContractRequest.getAllowances() != null) {
            checkAllowance(typeContractRequest.getAllowances());
            for(long iterm : typeContractRequest.getAllowances()) {
                contractTypeHasAllowanceRepository.save(ContractTypeHasAllowance.builder()
                                .contractTypeId(contractTypeSaved.getId())
                                .allowanceId(iterm)
                        .build());
            }
        }

        return ContractTypeMapper.mapToTypeContractResponse(contractTypeSaved);
    }

    @Override
    public ContractTypeDetail getContractType(long id) {
        ContractTypeDetail contractTypeDetail = contractTypeDAO.getContractType(id);
        if (contractTypeDetail == null) {
            throw new BusinessException(ErrorCode.TYPE_CONTRACT_IS_EMPTY);
        }
        return contractTypeDetail;
    }

    @Override
    @Transactional
    public ContractTypeResponse updateContractType(ContractTypeUpdate typeContractUpdate) {
        ContractType contractTypeEntity = contractTypeRepository.findByIdAndIsEnabled(typeContractUpdate.getId(), true).orElseThrow(
                () -> new BusinessException(ErrorCode.TYPE_CONTRACT_IS_EMPTY)
        );
        checkValidator(ContractTypeMapper.mapToTypeContractRequest(typeContractUpdate));
        contractTypeEntity.setName(typeContractUpdate.getName());
        contractTypeEntity.setType(typeContractUpdate.getType());
        contractTypeEntity.setTerm(typeContractUpdate.getTerm());
        contractTypeEntity.setUnit(typeContractUpdate.getUnit());
        contractTypeEntity.setInsurance(typeContractUpdate.getInsurance());
        contractTypeEntity.setMethod(typeContractUpdate.getMethod());
        contractTypeRepository.save(contractTypeEntity);
        if (typeContractUpdate.isUpdateAllowance()) {
            contractTypeDAO.deleteAllowance(typeContractUpdate.getId());
            if (typeContractUpdate.getAllowances() != null) {
                checkAllowance(typeContractUpdate.getAllowances());
                for(long iterm : typeContractUpdate.getAllowances()) {
                    contractTypeHasAllowanceRepository.save(ContractTypeHasAllowance.builder()
                            .contractTypeId(contractTypeEntity.getId())
                            .allowanceId(iterm)
                            .build());
                }
            }
        }
        return ContractTypeMapper.mapToTypeContractResponse(contractTypeEntity);
    }

    @Override
    public void deleteContractType(long id) {
        ContractType contractType = contractTypeRepository.findByIdAndIsEnabled(id, true).orElseThrow(
                () -> new BusinessException(ErrorCode.TYPE_CONTRACT_IS_EMPTY)
        );
        contractType.setEnabled(false);
        contractTypeRepository.save(contractType);
    }

    private void checkValidator (ContractTypeRequest request) {
        if (request.getName() == null || request.getName().isEmpty()) {
            throw new BusinessException(ErrorCode.NAME_INVALID);
        } else if (request.getType() == null || request.getType().isEmpty()){
            throw new BusinessException(ErrorCode.TYPE_CONTRACT_INVALID);
        } else if (request.getMethod() == null || request.getMethod().isEmpty()) {
            throw new BusinessException(ErrorCode.METHOD_INVALID);
        } else if (!ContractConstant.CONTRACT_HAS_NO_TERM.equals(request.getType())) {
            if (request.getTerm() <= 0) {
                throw  new BusinessException(ErrorCode.TERM_INVALID);
            } else if (request.getUnit() == null || request.getUnit().isEmpty()){
                throw  new BusinessException(ErrorCode.UNIT_INVALID);
            }
        }
    }

    private void checkAllowance(List<Long> allowances) {
        List<Allowance> listAllowance = allowanceRepository.findAllByIsEnabled(true);
        Map<Long, Allowance> mapAllowance = new HashMap<>();
        StringBuilder errorMessage = new StringBuilder();
        for (Allowance allowance : listAllowance) {
            mapAllowance.put(allowance.getId(), allowance);
        }
        for (Long id : allowances) {
            if (!mapAllowance.containsKey(id)) {
                errorMessage.append(id).append(", ");
            }
        }
        if (!errorMessage.isEmpty()) {
            ErrorCode errorCode = ErrorCode.ALLOWANCE_IS_EMPTY_FLEX;
            throw new BusinessFlexException(errorCode.getCode(),errorCode.getMessage() +  errorMessage + "]");
        }
    }
}
