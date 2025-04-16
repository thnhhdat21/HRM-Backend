package vn.tdsoftware.hrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.entity.InsuranceRatio;

import java.util.List;
import java.util.Optional;

@Repository
public interface InsuranceRatioRepository extends JpaRepository<InsuranceRatio, Integer> {
    List<InsuranceRatio> findAllByIsEnabled(boolean isEnable);
    Optional<InsuranceRatio> findByIdAndIsEnabled(Integer id, boolean isEnable);
}
