package vn.tdsoftware.hrm_backend.repository.history;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.entity.history.ContractHistory;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContractHistoryRepository extends JpaRepository<ContractHistory, Long> {
    Optional<List<ContractHistory>> findAllByContractIdAndIsEnabled(Long contractId, boolean isEnabled);
}
