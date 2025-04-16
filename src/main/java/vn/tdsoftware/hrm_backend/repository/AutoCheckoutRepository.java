package vn.tdsoftware.hrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.tdsoftware.hrm_backend.entity.AutoCheckout;

import java.util.Optional;

public interface AutoCheckoutRepository extends JpaRepository<AutoCheckout, Long> {
    Optional<AutoCheckout> findByIdAndIsEnabled(Long autoCheckoutId, boolean isEnabled);
    Boolean existsByWorkShiftIdAndIsEnabled(Long workShiftId, boolean isEnabled);
}
