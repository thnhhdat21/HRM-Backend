package vn.tdsoftware.hrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.entity.AssetGroup;

import java.util.Optional;

@Repository
public interface AssetGroupRepository extends JpaRepository<AssetGroup, Long> {
    Optional<AssetGroup> findByIdAndIsEnabled(Long id, boolean isEnabled);
    Boolean existsByParentIdAndIsEnabled(Long parentId, boolean isEnabled);
}
