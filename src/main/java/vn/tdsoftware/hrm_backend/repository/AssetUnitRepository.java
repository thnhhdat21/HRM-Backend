package vn.tdsoftware.hrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.entity.AssetUnit;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssetUnitRepository extends JpaRepository<AssetUnit, Long> {
    List<AssetUnit> findAllByIsEnabled(boolean isEnabled);
    Optional<AssetUnit> findByIdAndIsEnabled(Long id, boolean isEnabled);
}
