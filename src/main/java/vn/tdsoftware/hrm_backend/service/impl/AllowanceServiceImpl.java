package vn.tdsoftware.hrm_backend.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dao.AllowanceDAO;
import vn.tdsoftware.hrm_backend.dto.allowance.request.AllowanceRequest;
import vn.tdsoftware.hrm_backend.dto.allowance.response.AllowanceResponse;
import vn.tdsoftware.hrm_backend.entity.Allowance;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.mapper.AllowanceMapper;
import vn.tdsoftware.hrm_backend.repository.AllowanceRepository;
import vn.tdsoftware.hrm_backend.service.AllowanceService;
import vn.tdsoftware.hrm_backend.util.constant.StatusContract;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AllowanceServiceImpl implements AllowanceService {
    private final AllowanceDAO allowanceDAO;
    private final AllowanceRepository allowanceRepository;

    @Override
    public List<AllowanceResponse> getListAllowance() {
        List<Allowance> listAllowanceEntity = allowanceRepository.findAllByIsEnabled(true);
        if (listAllowanceEntity.isEmpty()) {
            throw new BusinessException(ErrorCode.LIST_ALLOWANCE_IS_EMPTY);
        }
        List<AllowanceResponse> responseList = new ArrayList<>();
        for (Allowance allowance : listAllowanceEntity) {
            responseList.add(AllowanceMapper.mapToAllowanceResponse(allowance));
        }
        return responseList;
    }

    @Override
    @Transactional
    public List<AllowanceResponse> createAllowance(List<AllowanceRequest> allowanceRequest) {
        List<AllowanceResponse> responseList = new ArrayList<>();
        for (AllowanceRequest allowanceRequestEntity : allowanceRequest) {
            validator(allowanceRequestEntity);
            Allowance allowanceSaved = allowanceRepository.save(Allowance.builder()
                            .name(allowanceRequestEntity.getName())
                            .amount(allowanceRequestEntity.getAmount())
                            .unit(allowanceRequestEntity.getUnit())
                            .status(StatusContract.ACTIVE)
                    .build());
            responseList.add(AllowanceMapper.mapToAllowanceResponse(allowanceSaved));
        }
        return responseList;
    }

    @Override
    public AllowanceResponse updateAllowance(AllowanceRequest allowanceRequest) {
        Allowance allowanceEntity = allowanceRepository.findByIdAndIsEnabled(allowanceRequest.getId(), true)
                .orElseThrow(() -> new BusinessException(ErrorCode.ALLOWANCE_IS_EMPTY));
        validator(allowanceRequest);
        allowanceEntity.setName(allowanceRequest.getName());
        allowanceEntity.setAmount(allowanceRequest.getAmount());
        allowanceEntity.setUnit(allowanceRequest.getUnit());
        return AllowanceMapper.mapToAllowanceResponse(allowanceRepository.save(allowanceEntity));
    }

    @Override
    public void deleteAllowance(long id) {
        Allowance allowanceEntity = allowanceRepository.findByIdAndIsEnabled(id, true)
                .orElseThrow(() -> new BusinessException(ErrorCode.ALLOWANCE_IS_EMPTY));
        allowanceEntity.setEnabled(false);
        allowanceRepository.save(allowanceEntity);
    }


    private void validator(AllowanceRequest request) {
        if (request.getName() == null || request.getName().isEmpty()) {
            throw new BusinessException(ErrorCode.NAME_INVALID);
        } else if (request.getAmount() <= 0){
            throw new BusinessException(ErrorCode.AMOUNT_INVALID);
        } else if (request.getUnit() == null || request.getUnit().isEmpty()) {
            throw new BusinessException(ErrorCode.UNIT_INVALID);
        }
    }
}
