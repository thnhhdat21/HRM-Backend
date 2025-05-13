package vn.tdsoftware.hrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.entity.AccountBank;

import java.util.Optional;

@Repository
public interface AccountBankRepository extends JpaRepository<AccountBank, Long> {
    Optional<AccountBank> findByEmployeeIdAndIsEnabled(long employeeId, boolean isEnabled);
}
