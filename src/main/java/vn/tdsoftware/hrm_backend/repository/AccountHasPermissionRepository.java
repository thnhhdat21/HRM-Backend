package vn.tdsoftware.hrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.tdsoftware.hrm_backend.entity.AccountHasPermission;

public interface AccountHasPermissionRepository extends JpaRepository<AccountHasPermission, Long> {
    Boolean existsByAccountIdAndIsEnabled(Long accountId, boolean isEnabled);
}
