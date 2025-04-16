package vn.tdsoftware.hrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.tdsoftware.hrm_backend.entity.Allowance;

import java.util.List;
import java.util.Optional;

public interface AllowanceRepository extends JpaRepository<Allowance, Long> {
    List<Allowance> findAllByIsEnabled(boolean isEnabled);
    Optional<Allowance> findByIdAndIsEnabled(long id, boolean isEnabled);
}
