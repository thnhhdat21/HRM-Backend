package vn.tdsoftware.hrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.entity.ContractGeneralHasAllowance;

import java.util.List;

@Repository
public interface ContractGeneralHasAllowanceRepository extends JpaRepository<ContractGeneralHasAllowance, Long> {
    List<ContractGeneralHasAllowance> findByContractId(long contractId);
    boolean existsByContractId(long contractId);
}
