package vn.tdsoftware.hrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.entity.ContractHasAllowance;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContractHasAllowanceRepository extends JpaRepository<ContractHasAllowance, Long> {
    Optional<ContractHasAllowance> findByIdAndIsEnabled(long id, boolean isEnabled);
    List<ContractHasAllowance> findAllByContractIdAndIsEnabled(long contractId, boolean isEnabled);
}
