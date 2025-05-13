package vn.tdsoftware.hrm_backend.service.history.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dao.history.ContractHistoryDAO;
import vn.tdsoftware.hrm_backend.dto.history.contract.response.ContractHistoryResponse;
import vn.tdsoftware.hrm_backend.entity.history.ContractHistory;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.repository.ContractRepository;
import vn.tdsoftware.hrm_backend.repository.history.ContractHistoryRepository;
import vn.tdsoftware.hrm_backend.service.history.ContractHistoryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractHistoryImpl implements ContractHistoryService {
    private final ContractHistoryDAO contractHistoryDAO;
    private final ContractHistoryRepository contractHistoryRepository;

    @Override
    public List<ContractHistoryResponse> getContractHistory(long contractId) {
        List<ContractHistoryResponse> listEntity = contractHistoryDAO.getContractHistory(contractId);
        if (listEntity.isEmpty()) {
            throw new BusinessException(ErrorCode.CONTRACT_HISTORY_IS_EMPTY);
        }
        return listEntity;
    }

    @Override
    public void createContractHistory(long contractId, int state, String title) {
        contractHistoryRepository.save(ContractHistory.builder()
                        .contractId(contractId)
                        .state(state)
                        .title(title)
                .build());
    }
}
