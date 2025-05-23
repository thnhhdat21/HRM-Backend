package vn.tdsoftware.hrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.entity.Account;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByIdAndIsEnabled(Long id, Boolean isEnabled);
    Account findByEmployeeIdAndIsEnabled(Long employeeId, Boolean isEnabled);

    Optional<Account>  findByUsernameAndIsEnabled(String username, Boolean isEnabled);
}
