package vn.tdsoftware.hrm_backend.dao.history;

import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.dto.history.contract.response.ContractHistoryResponse;

import java.util.List;

@Repository
public interface ContractHistoryDAO {
    List<ContractHistoryResponse> getContractHistory(long contractId);
}
