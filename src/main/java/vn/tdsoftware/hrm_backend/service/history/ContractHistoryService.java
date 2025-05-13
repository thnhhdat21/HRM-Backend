package vn.tdsoftware.hrm_backend.service.history;

import vn.tdsoftware.hrm_backend.dto.history.contract.response.ContractHistoryResponse;

import java.util.List;

public interface ContractHistoryService {
    List<ContractHistoryResponse> getContractHistory(long contractId);
    void createContractHistory(long contractId, int state, String title);
}
