package vn.tdsoftware.hrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.entity.RoleHasPermission;

import java.util.Optional;

@Repository
public interface RoleHasPermissionRepository extends JpaRepository<RoleHasPermission, Integer> {
    Optional<RoleHasPermission> findByIdAndIsEnabled(long id, boolean isEnabled);
}
